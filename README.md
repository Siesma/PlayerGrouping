# PlayerGrouping

Using this project is pretty easy.
Importing Players can be done by the Following format:

  PlayerID, PlayerFirstName, PlayerSecondName, ...
  
Seperating them using "," is currently necessary, although adding a "match" condition is not hard and could be added using the following code:
  if(!fileContent.split(\n).matches("[0-9]+,(| )[a-zA-Z]+,(| )[a-zA-Z]+,(| )[0-9]+.[0-9]+,(| )[0-9]+.[0-9]+,(| )[0-9]+.[0-9]+")) {continue;}
In the file "Handler.java" within the Function "LoadPlayerList" inside the first for loop in the very beginning.

Importing their respective "likings" is using the following format:
  PlayerIDA, PlayerIDB, Value
  PlayerIDA refers to the player who is giving PlayerIDB a respective "liking" Value.
  
This class is already catched by a matching requirement, which basically says asks whether the file is formatted as "int, int, int" 
  
Having the groups more randomized is fairly easy as well.
Within the file "Group.java" there is a method called "getWeightForPlayer" and within that function there is a double "intensity". This double determines how randomized each group will be exopnentially. For no randomness at all it should be used as "1", otherwise at something > 1.

