# BrickThrower v1.3.1 Source

BrickThrower is a simple plugin for Minecraft Servers where you can throw bricks with right click. For more advanced information please visit our wiki here: https://github.com/Twocoolguy/BrickThrower/wiki

## Notice
I want to make clear about what I said in the 1.3.0 update about no more addition updates. When I say that there will not be anymore "content" what I mean by this is that I will not be coming up with more content myself. If a good idea is suggested it may be added, and depending on some patches there might be an addition for disabling it (like how there is in 1.3.1). I just wanted to make it clear that I was not really going to come up with additions unless they had to do with bugs. Thanks.

## Contributing
There is now a new branch called "wip". This branch will be for any kind of update (bug patches, supporting new versions of minecraft, and documentation). Any change I make to the code will go to that branch. Any pull requests made that are accepted will also go to this branch. Once there is enough changes (which is under my discretion as to what constitutes as "enough") the wip branch will be merged into the main branch and I will release an update onto spigot (after testing of course). For any big changes to the plugin please make a suggestion on our discord server here: https://discord.gg/TyZM6ePB65
## Patches that are Finished and Tested (on all versions):
- Remove ability to use smithing table with stuff. (V - 1.16)
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

# Need to do:
Change it so instead of checking when an entity is hit, you check when the player clicks with a weapon/brickthrower item in their hand. This will allow throwing bricks to do damage.

## All of these patches must be tested on each main version
V = Tested and works properly

X = Not tested.

O = Tested but does not work properly.

- Ability to enchant items (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (V - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to smelt items in a furnace. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to fix stuff with anvil. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to use brewing stand with stuff. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to use leads from brickthrower on mobs. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to dye sheep from brickthrower. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Fixed a big bug where when the item disappear it would still do damage to an entity if it got near it. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to operate with tools obtained through brickthrower. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Add a check for if your inventory is full with brickthrower get. (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)
- Remove ability to place lily pads with brickthrower (They appear but are ghost blocks). (X - 1.8) (X - 1.9) (X - 1.10) (X - 1.11) (X - 1.12) (V - 1.13) (V - 1.14) (V - 1.15) (V - 1.16)

## Things that have been "patched" but need to be tested
- N/A.

## May be fixed later but not in this patch
- Remove ability to use horse armor from brickthrower. (1.8+)
- Remove ability to use arrows from brickthrower. (1.8+)
- Remove ability to wear armor/elytras from brickthrower. (1.8+)
- Remove ability to wear heads from brickthrower. (1.8+)
- Remove ability to use shields from brickthrower. (1.9+)

Most of these will also have some kind of toggle to them in the config. This is again due to performance.
After these patches stuff must be retested. Also the native version changed to 1.16.5

## Note
1.3.0 is the final content update of BrickThrower. Things will not be added after this. Any updates are for: 
- Support for newer versions of Minecraft
- Bug patches
- Documentation

This is not due to me not having interest in the plugin, more that the plugin is complete. There is nothing else that I would have interest into adding to the plugin as anything more would push away from the point of the plugin. I also want to work on other plugins. This plugin has been a lot of fun to work on. :)

## Finished Updates

### 1.2.2:
- Added ability to also get nether_bricks instead of normal bricks by typing: /brickthrower get nether 
- Started using NBT tags to store no_craft data instead of lore.

### 1.2.3:
- Added the ability to change what the default item you get from /brickthrower get is (you can change this in the config).
- Added the ability to have a list of items you can use (you can add and remove items in the config)
- Added the ability to change the name of the item /brickthrower get gives you (you can change this in the config).
- Added new alias for the brickthrower command, brth instead of brickthrower
- Performance Improvements

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


### 1.3.0:
- Added damage to any living entity when bricks are thrown at them. Set damage to 0 in the config to toggle this off.
- Allowed older versions to use items of their choice
- Stoped allowing players to eat food that has our NBT data and do not allow players to place the blocks/items that has our NBT data. This allows them to be used with brickthrower.
- Added the ability to allow console to reload config even if the option is set to false in the config.
- Improved performance of the plugin.
- Removed the checkconfig command because it was no longer useful.
