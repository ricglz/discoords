package me.ricglz.discoords;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CoderResult;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

import me.ricglz.discoords.exceptions.InvalidWorldException;

/**
 * Class used to handle the configuration file of the program
 */
public class DiscoordsConf extends YamlConfiguration {
    protected static final Logger LOGGER = Logger.getLogger("Discoords");
    protected final File configFile;
    private final AtomicInteger pendingDiskWrites = new AtomicInteger(0);
    private final byte[] bytebuffer = new byte[1024];
    protected static final Charset UTF8 = StandardCharsets.UTF_8;

    /**
     * @param configFile
     */
    public DiscoordsConf(final File configFile) {
        super();
        this.configFile = configFile.getAbsoluteFile();
    }

    /**
     * Deletes files where the first character is 0, as in most cases they are
     * broken.
     */
    private synchronized void removedBrokenFiles() {
        if (configFile.exists() && configFile.length() != 0) {
            try (final InputStream input = new FileInputStream(configFile);) {
                if (input.read() == 0) {
                    boolean success = configFile.delete();
                    if (!success) {
                        LOGGER.log(Level.SEVERE, "Not able to delete the file");
                    }
                }
            } catch (final IOException ex) {
                LOGGER.log(Level.SEVERE, null, ex);
            }
        }
    }

    private ByteBuffer resizeBuffer(ByteBuffer buffer, FileInputStream inputStream) throws IOException {
        int length;
        while ((length = inputStream.read(bytebuffer)) != -1) {
            if (length > buffer.remaining()) {
                final ByteBuffer resize = ByteBuffer.allocate(buffer.capacity() + length - buffer.remaining());
                final int resizePosition = buffer.position();
                // Fix builds compiled against Java 9+ breaking on Java 8
                buffer.rewind();
                resize.put(buffer);
                resize.position(resizePosition);
                buffer = resize;
            }
            buffer.put(bytebuffer, 0, length);
        }
        buffer.rewind();
        return buffer;
    }

    /**
     * Loads the configuration file
     */
    public synchronized void load() {
        if (pendingDiskWrites.get() != 0) {
            LOGGER.log(Level.INFO, "File {0} not read, because it''s not yet written to disk.", configFile);
            return;
        }
        if (!configFile.getParentFile().exists() && !configFile.getParentFile().mkdirs()) {
            LOGGER.log(Level.SEVERE, "Failed to create config");
        }

        removedBrokenFiles();

        if (!configFile.exists()) {
            return;
        }

        try {
            try (final FileInputStream inputStream = new FileInputStream(configFile)) {
                final long startSize = configFile.length();
                if (startSize > Integer.MAX_VALUE) {
                    throw new InvalidConfigurationException("File too big");
                }
                ByteBuffer buffer = ByteBuffer.allocate((int) startSize);
                buffer = resizeBuffer(buffer, inputStream);
                final CharBuffer data = CharBuffer.allocate(buffer.capacity());
                CharsetDecoder decoder = UTF8.newDecoder();
                CoderResult result = decoder.decode(buffer, data, true);
                if (result.isError()) {
                    buffer.rewind();
                    buffer.clear();
                    String errorMsg = String.format("File %s is not utf-8 encoded, trying %s",
                            configFile.getAbsolutePath(), Charset.defaultCharset().displayName());
                    LOGGER.log(Level.INFO, errorMsg);
                    decoder = Charset.defaultCharset().newDecoder();
                    result = decoder.decode(buffer, data, true);
                    if (result.isError()) {
                        throw new InvalidConfigurationException(
                                "Invalid Characters in file " + configFile.getAbsolutePath());
                    } else {
                        decoder.flush(data);
                    }
                } else {
                    decoder.flush(data);
                }
                final int end = data.position();
                data.rewind();
                super.loadFromString(data.subSequence(0, end).toString());
            }
        } catch (final IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (final InvalidConfigurationException ex) {
            final File broken = new File(configFile.getAbsolutePath() + ".broken." + System.currentTimeMillis());
            boolean success = configFile.renameTo(broken);
            String errorMsg;
            if (success) {
                errorMsg = String.format("The file %s is broken, it has been renamed to %s", configFile.toString(),
                        broken.toString());
            } else {
                errorMsg = "Unable to rename broken file";
            }
            LOGGER.log(Level.SEVERE, errorMsg, ex.getCause());
        }
    }

    public void save() {
        try {
            save(configFile);
        } catch (final IOException ex) {
            LOGGER.log(Level.SEVERE, ex.getMessage(), ex);
        }
    }

    public void saveWithError() throws IOException {
        save(configFile);
    }

    public Location getLocation(final String path, final Server server) throws InvalidWorldException {
        final String worldString = (path == null ? "" : path + ".") + "world";
        final String worldName = getString(worldString);
        if (worldName == null || worldName.isEmpty()) {
            return null;
        }
        final World world = server.getWorld(worldName);
        if (world == null) {
            throw new InvalidWorldException(worldName);
        }
        return new Location(world, getDouble((path == null ? "" : path + ".") + "x", 0),
                getDouble((path == null ? "" : path + ".") + "y", 0),
                getDouble((path == null ? "" : path + ".") + "z", 0),
                (float) getDouble((path == null ? "" : path + ".") + "yaw", 0),
                (float) getDouble((path == null ? "" : path + ".") + "pitch", 0));
    }

    public void setProperty(final String path, final Location loc) {
        set((path == null ? "" : path + ".") + "world", loc.getWorld().getName());
        set((path == null ? "" : path + ".") + "x", loc.getX());
        set((path == null ? "" : path + ".") + "y", loc.getY());
        set((path == null ? "" : path + ".") + "z", loc.getZ());
        set((path == null ? "" : path + ".") + "yaw", loc.getYaw());
        set((path == null ? "" : path + ".") + "pitch", loc.getPitch());
    }

    public void setProperty(final String path, final Object object) {
        set(path, object);
    }
}
