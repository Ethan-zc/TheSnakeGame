# TheSnakeGame
<h4> Zhangnan Jiang(zj2028), Zichen Yang(zy2486)</h4>
<h4> Final Project for CS9053
<h3> Introduction</h3>
This is a multiplayer snake game that based on <b>SpringBoot, Mybatis, SQLite, Socket, and UDP</b> using Java. 
By running TheSnakeGameApplication as the server with its local IP address, other players on different computer
could run TheGameClient as the client to connect to the server and play the game together. The basic structure of the
project could be described as the following image: 
<img src="/src/main/resources/imgs/ProjectStructure.png"/>
Password and username would be required to login, with the verification of valid login achieved by SpringBoot, 
Mybatis and SQLite, new user could also register a new account to be able to login. 
After login, user could click start game button to enter the waiting room. The username in the waiting room
would be updated based on the user that joins the waiting room. When one of the user in waiting room clicked the
"Start Game" button, all users in the waiting room would start to play the snake game together on the server. 
The snake that is been controlled by the current user would be draw in blue, while other snakes would be draw in black. 
The snake could be controlled by arrow button to move around. Following are the basic rules for the game: <br>
1. Every second one user live, the user would get 10 points. <br>
2. When one snake eat the apple, the snake would get 100 points.  <br>
3. When one snake cause a collision, which means that one snake dead because it head collide on the killer snake's body, 
the killer snake would get 1000 point, and the other one would die. <br>
4. When the time runs out or all snakes are dead, the game would end and the points for each snake would be demonstrated. <br> 
After the game is finished, one user could also check the leader board for the highest scores in the 

<h3> Install and Run</h3>

<h3> SpringBoot, MyBatis, and SQLite</h3>

<h3> Socket, Thread and Waiting Room </h3>

<h3> UI, UPD transmission, and the game </h3>
