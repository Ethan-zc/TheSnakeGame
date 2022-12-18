# TheSnakeGame
<h4> Zhangnan Jiang(zj2028), Zichen Yang(zy2486)</h4>
<h4> Final Project for CS9053 </h4>
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
After the game is finished, one user could also check the leader board for the highest scores in the database. 


<h3> Install and Run</h3>
As For install, please use <b>IntelliJ</b> as the IDE to run the project. <br>
Choose <b>New > Project From Version Control</b>, and paste the address of this project: <br>
<b>https://github.com/Ethan-zc/TheSnakeGame.git </b>, 
clone the project onto the local computer. <br>
<img src="/src/main/resources/imgs/newProj.png"/>
Please choose <b>SDK 17</b> as the SDK for the project. <br>
For the server, please change the server: address: 192.168.1.156 to the local ip address of the server computer. <br>
About how to find local IP address: <a href="https://www.avast.com/c-how-to-find-ip-address">Click Here</a> <br>
In the meantime, change the localhostIP in /src/main/java/TheGameClient to the corresponding local IP address of the server
for all users in the same local network. 
<img src="/src/main/resources/imgs/changeIP.png"/>
<img src="/src/main/resources/imgs/clientIP.png"/>

As for the server, please run TheSnakeGameApplication. After seeing that the server is running, 
different computers under the same local network could run TheGameClient to as client to login and play the game. <br>

For detailed process, please refer to the video: <a href="https://www.avast.com/c-how-to-find-ip-address">Video</a>

Following are detailed introduction for different part of the project. 

<h3> SpringBoot, MyBatis, and SQLite</h3>

In this project, SpringBoot is used for API construction. Controller, Service and ServiceImplements are implemented for 
APIs. Followings are the list for important APIs in this project. 
1. <b>localIP:8080/account/register</b>: Used for new user to be registered. 
2. <b>localIP:8080/account/login</b>: Used for users to login with their username and password. Notice that the password that stored in the database would be encrypted using md5. 
3. <b>localIP:8080/account/getLeaderBoard</b>: Used for get the leaderboard of all scores stored in db with username. 
4. <b>localIP:8080/game/addgame</b>: Used for server to add a record of new game into game table. 
5. <b>localIP:8080/game/addscore</b>: Used for adding score of certain user in certain game into the database.

MyBatis is used to map java functions into sql query to select, insert data into our database. Mappers are constructed to
do this work. <b>AccountMapper and GameMapper</b> are used for AccountService and GameService to do interactions with database. 

SQLite is used as the database of the project. Using /resources/DatabaseCreation.sql with SQLite, we get the database file schema.db. 
Then we used the application-sqlite.yml to configure that the server would take the schema.db as our database. <br>
The database is created as following: <br>
<img src="/src/main/resources/imgs/db.png"/>

<h3> Socket, Thread and Waiting Room </h3>
Here Socket is mainly used for the information exchange during the waiting room between client and server. 
Two classes are created to handle the socket. <br>
<b>WaitingClient</b>: This one is served for the client, when the user enter the waiting room, the WaitingCLient would be created
and the client would send a message with username. If there are already users in the waiting room, the client would then receive a list
of users that currently in the waiting room. When the game start, the client who press the button would send a message "GAME START!" to the server, 
and then create a Client class to handle the game process. 
<b>WaitingRoom</b>: This class is served as the server of waiting room. It would receive message from user and broadcast the 
user list to all users in the waiting room dynamically. When it receive the message of "GAME START" from user, it would broadcast
"GAME START" to all users in the waiting room, and the close the socket, start the threads for GameHandler to start the game. 


<h3> UI, UPD transmission, and the game </h3>
