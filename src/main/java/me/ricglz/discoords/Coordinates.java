package me.ricglz.discoords;

import me.ricglz.discoords.exceptions.CoordinatesExistException;
import me.ricglz.discoords.exceptions.CoordinatesNotFoundException;
import me.ricglz.discoords.exceptions.InvalidWorldException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import org.bukkit.Location;
import org.bukkit.Server;

/**
 * Class to abstract the list of coordinates saved in the server.
 */
public class Coordinates {
    private final Map<StringIgnoreCase, DiscoordsConf> coordMap = new HashMap<>();
    private final File coordsFolder;
    private final Server server;
    private final Logger logger;

    /**
     * Public constructor to create the Coordinates instance
     *
     * @param server     The server instance of bukkit
     * @param dataFolder The File instance of the folder to save the information of
     *                   the coordinates
     * @param logger     Logger instance of the application
     */
    public Coordinates(final Server server, final File dataFolder, Logger logger) {
        this.server = server;
        this.logger = logger;
        coordsFolder = new File(dataFolder, "coords");
        if (!coordsFolder.exists()) {
            coordsFolder.mkdirs();
        }
        reloadConfig();
    }

    public boolean isEmpty() {
        return coordMap.isEmpty();
    }

    /**
     *
     * @return All stored coordinates as a Collection instance
     */
    public Collection<String> getList() {
        final List<String> keys = new ArrayList<>();
        for (final StringIgnoreCase filename : coordMap.keySet()) {
            keys.add(filename.getString());
        }
        keys.sort(String.CASE_INSENSITIVE_ORDER);
        return keys;
    }

    /**
     * @param coords Key name that was used to save the coordinates
     * @return The location instance of the coordinates
     * @throws CoordinatesNotFoundException
     * @throws InvalidWorldException
     */
    public Location getCoordinates(final String coords) throws CoordinatesNotFoundException, InvalidWorldException {
        final DiscoordsConf conf = coordMap.get(new StringIgnoreCase(coords));
        if (conf == null) {
            throw new CoordinatesNotFoundException();
        }
        return conf.getLocation(null, server);
    }

    private String sanitizeFileName(final String name) {
        final Pattern invalidFileChars = Pattern.compile("[^a-z0-9-]");
        return invalidFileChars.matcher(name.toLowerCase(Locale.ENGLISH)).replaceAll("_");
    }

    /**
     * Sets/Saves the coordinates by saving a file with the given name and writing
     * there the coordinates
     *
     * @param name Name of the coordinates, this will probably also be the name of
     *             the file
     * @param loc  Location instance of the coordinates
     * @throws CoordinatesExistException
     * @throws IOException
     */
    public void setCoordinates(final String name, final Location loc) throws CoordinatesExistException, IOException {
        final String filename = sanitizeFileName(name);
        final StringIgnoreCase ignoreCaseName = new StringIgnoreCase(name);
        DiscoordsConf conf = coordMap.get(ignoreCaseName);
        if (conf == null) {
            final File confFile = new File(coordsFolder, filename + ".yml");
            if (confFile.exists()) {
                throw new CoordinatesExistException();
            }
            conf = new DiscoordsConf(confFile);
            coordMap.put(ignoreCaseName, conf);
        }
        conf.setProperty(null, loc);
        conf.setProperty("name", name);
        try {
            conf.saveWithError();
        } catch (final IOException ex) {
            throw new IOException("That was an invalid coordinates label");
        }
    }

    /**
     * Reloads the config folder to once again obtain the saved coordinates
     */
    public final void reloadConfig() {
        coordMap.clear();
        final File[] listOfFiles = coordsFolder.listFiles();
        if (listOfFiles.length >= 1) {
            for (final File listOfFile : listOfFiles) {
                final String filename = listOfFile.getName();
                if (listOfFile.isFile() && filename.endsWith(".yml")) {
                    try {
                        final DiscoordsConf conf = new DiscoordsConf(listOfFile);
                        conf.load();
                        final String name = conf.getString("name");
                        if (name != null) {
                            coordMap.put(new StringIgnoreCase(name), conf);
                        }
                    } catch (final Exception ex) {
                        logger.severe(ex.getMessage());
                    }
                }
            }
        }
    }

    public int getCount() {
        return getList().size();
    }

    /**
     * Class used to abstract the comparison of string while ignoring the casing
     */
    private static class StringIgnoreCase {
        private final String string;

        StringIgnoreCase(final String string) {
            this.string = string;
        }

        @Override
        public int hashCode() {
            return getString().toLowerCase(Locale.ENGLISH).hashCode();
        }

        @Override
        public boolean equals(final Object o) {
            if (o instanceof StringIgnoreCase) {
                return getString().equalsIgnoreCase(((StringIgnoreCase) o).getString());
            }
            return false;
        }

        public String getString() {
            return string;
        }
    }
}
