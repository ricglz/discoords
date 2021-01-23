# Discoords

A bukkit plugin specifically created to be able to send your current coordinates to a discord server.

# Installation

This is explained in the following [wiki page](https://github.com/ricglz/discoords/wiki/Installation)

# Features

- Sends message to Discord that the server is up
- `/discoords [message]` command. Allows to send your current coordinates to an specific message channel. If a label is passed as argument it will also sent the label to the discord text channel.

Ex.

```
/discoords

-> Sent to discord: "(0, 0, 0) - by player"

/discoords diamonds

-> Sent to discord: "(0, 0, 0) - diamonds - by player"

/discoords a very big fortress
-> Sent to discord: "(0, 0, 0) - a very big fortress - by player"
```
- `/distance` command. Calculates the distance between 2 locations, optionally being one of those your current location
- `/save-coords <label>`. Saves your current location to later on search it locally, but will not be sent through discord
- `/search-coords <label>`. Searches for a coordinate based on the given label

# Possible Future Features

- [X] Stores all the locations also in Minecraft to be able to manage those other locations
- [ ] Add command to save the current location without sending it to discord
- [ ] Add command to send those locations that still haven't been sent but are stored
- [ ] Don't send message if the coordinate or location desired probably have already been sent
