# BrickThrower v2.1.3 Source

BrickThrower is a simple plugin for Minecraft Servers where you can throw bricks with right click. For more advanced information please visit our wiki here: https://github.com/Twocoolguy/BrickThrower/wiki

## Notice
**I will not be adding new content myself (from my own ideas).**
Most updates will be for:
- Updating to allow support for new Minecraft versions
- Fixing bugs/issues
- Updating Documentation

If there is any requests for an update for new features I would not be against adding stuff if people want it.

## Contributing
There is now a new branch called "wip". This branch will be for any kind of update (bug patches, supporting new versions of minecraft, and documentation). Any change I make to the code will go to that branch. Any pull requests made that are accepted will also go to this branch. Once there is enough changes (which is under my discretion as to what constitutes as "enough") the wip branch will be merged into the main branch and I will release an update onto spigot (after testing of course). For any big changes to the plugin please make a suggestion on our discord server here: https://discord.gg/TyZM6ePB65

# Patch Notes

## 2.1.3
- Added brickthrower tab completer
- Fixed legacy material loading

## 2.1.1
- Fixed null in console

## 2.1.0
- NBT-API was used instead of standard NBT.
- Renamed BrickThrower to BrickThrowerX
- Updated native version to 1.21 (supporting 1.8 - 1.21)
- Fixed player knockback in spectators.

---

# Old Patch Notes

## 2.0.0
- Entire codebase was refactored as it was not very easy to read in some places and I felt many things needed to be moved around. Useless code was also removed.
- People in creative mode no longer lose bricks from their inventory
- When people are in creative mode they no longer take knockback from bricks.
- When shielding you no longer take knockback from bricks.
- Updated native version to 1.20.4 (supporting 1.8 - 1.20.4)
- Performance should be improved as a lot of useless code was removed
- Many random bug fixes
- Keep in mind this update may break some things as much of the code was changed (though a lot was tested), if you find any issues try to report them on the github!

## 1.3.3:
- Added two new config options: "item-veloicty-multipler" this allows you to change the multiplier of the velocity of the item when it is thrown. "kb-velocity-multiplier" which allows you to change the multiplier on the velocity of knockback a player takes when they are hit.
- Added support for versions 1.18-1.19.3

## 1.3.2:
- Added a new config option to allow you to throw bricks without obtaining them from the command. This config option is called "requires-command" and by default is set to true.

## 1.3.1:
- Remove ability to use smithing table with stuff.
- Added a new config option for performance, if you do not care about being able to interact with items from brickthrower set allow-interacts to true.
- This also means anything that you interact with by default will not be able to be interacted with if it is from brickthrower. This did remove a lot of bugs.
- Added a new config option for performance, if you do not care about being able to use guis with items from brickthrower set allow-guis to true (crafting tables will still be disabled to use them with though.)
- Fixed a bug where sometimes getNBTDataString from NBT14 (versions 1.14+) would cause a null pointer exception.
- Removed the ability to cook brickthrower items/smelt brickthrower items in smokers.
- Removed the ability to cook brickthrower items/smelt brickthrower items in blast furnaces.
- Removed ability to use brickthrower items in a composter.
- Removed ability to use brickthrower items with a stonecutter.
- Removed ability to use brickthrower items with a cartography table.
- Removed ability to use a loom with brickthrower items.
- Removed ability to use a trident obtained through brickthrower. 
- Removed ability to enchant items obtained through brickthrower with an enchanting table.
- Removed ability to smelt brickthrower items in a furnace. 
- Removed the ability to use items from brickthrower in a brewing stand (except 1.8). 
- Removed ability to use leads from brickthrower on mobs.
- Removed ability to dye sheep with dye obtained from brickthrower.
- Fixed a big bug where when the item disappeared it would still do damage to an entity if it got near it.
- Removed ability to operate with tools obtained through brickthrower.
- Added a check to see if your inventory was full when doing /brth get
- Removed the ability to place lily pads with brickthrower (they still appear as ghost blocks sadly).
- Removed the ability to fix brickthrower items with an anvil (except 1.8).


### 1.3.0:
- Added damage to any living entity when bricks are thrown at them. Set damage to 0 in the config to toggle this off.
- Allowed older versions to use items of their choice
- Stoped allowing players to eat food that has our NBT data and do not allow players to place the blocks/items that has our NBT data. This allows them to be used with brickthrower.
- Added the ability to allow console to reload config even if the option is set to false in the config.
- Improved performance of the plugin.
- Removed the checkconfig command because it was no longer useful.


### 1.2.4:
- Refactored the code for performance improvements and readability.
- Fixed many bugs. One in particular that would delete an item in your offhand when trying to throw bricks.
- Added a new config option to change how long an item stays appearing for.
- Added Support for more versions of Minecraft:
  - 1.12
  - 1.11
  - 1.10
  - 1.9
  - 1.8

### 1.2.3:
- Added the ability to change what the default item you get from /brickthrower get is (you can change this in the config).
- Added the ability to have a list of items you can use (you can add and remove items in the config)
- Added the ability to change the name of the item /brickthrower get gives you (you can change this in the config).
- Added new alias for the brickthrower command, brth instead of brickthrower
- Performance Improvements

### 1.2.2:
- Added ability to also get nether_bricks instead of normal bricks by typing: /brickthrower get nether 
- Started using NBT tags to store no_craft data instead of lore.

## Issues that are known and need to be fixed eventually
- Remove ability to use horse armor from brickthrower. (1.8+)
- Remove ability to use arrows from brickthrower. (1.8+)
- Remove ability to wear armor/elytras from brickthrower. (1.8+)
- Remove ability to wear heads from brickthrower. (1.8+)
*who knows if I will ever get to these lol*
