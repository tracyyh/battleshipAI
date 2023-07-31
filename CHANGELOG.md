Create Json Package:
  - within include FleetJson, MessageJson, ShipJson, CoordinatesJson, CoordJson, EndGameJson, JoinJson, MessageJson, 
    SetupJson records and JsonUtils class
  - These records will allow us to process messages that are given to our code through the server.
  - It specifies what information is in the arguments
Driver Class: main run method:
  - Changed the main method to check for how many arguments are in the command line; If there are no arguments run a 
    regular manual BattleSalvo game; If there are two arguments connect the client to the server given the host and 
    port arguments (if not possible throw an illegal argument exception)
  - Changed the main method to check for how many arguments are in the command line; If there are no arguments run a 
    regular manual BattleSalvo game; If there are two arguments connect the client to the server given the host and 
    port arguments (if not possible throw an illegal argument exception)
View: UserInput Class: Create a new method that asks a prompt and returns a string 
  - Originally hardcoded name into the BattleSalvo game, creating the new method would allow for us to ask the user for 
    a name as well as the game type that they want to play
Created ControlInt interface:
  - lifted the run method from BattleSalvo and ProxyDealer classes to created the ControlInt interface
Controller: BattleSalvo Class: split the run method into smaller methods 
  - Take apart the method by which part of the game that it handles (ie taking shots, returning damage…) Each part will
    have its own method that way when the server returns a message for the game it can be fed into the method that it 
    is specifically meant for
  - removed most code from the constructor and moved it into the run class
Abstracted Player Class:
  - lifted identical methods from AIPlayer and HumanPlayer classes into an abstract player class that implements the 
    player interface in order to clean up code
ProxyDealer:
  - created a ProxyDealer class that implements the ControlInt interface and handles JSON and server inputs/outputs










