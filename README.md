# TheSnakeGame
<h4> Zhangnan Jiang(zj2028), Zichen Yang(zy2486)</h4>
<h4> Final Project for CS9053 </h4>
<h4> Link to the Project Repo: <a href = "https://www.youtube.com/watch?v=wDBBOvSAy58&ab_channel=ZichenYang">https://github.com/Ethan-zc/TheSnakeGame</a> </h4>

<h4>Video for SetUp and simple demo: <a href="https://www.avast.com/c-how-to-find-ip-address">Click Here</a></h4>

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
The snake that is controlled by the current user would be drawn in blue, while other snakes would be drawn in black. 
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

<h4>For detailed process, please refer to the video: <a href="https://www.youtube.com/watch?v=wDBBOvSAy58&ab_channel=ZichenYang">Video</a></h4>

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

By default, we have added data into the database when it is created, the added data are: <br>

INSERT INTO `acc` (`accname`, `pwd`) VALUES ('zichenyang', 'e10adc3949ba59abbe56e057f20f883e'); <br>
INSERT INTO `acc` (`accname`, `pwd`) VALUES ('zhangnan', 'e10adc3949ba59abbe56e057f20f883e'); <br>
INSERT INTO `acc` (`accname`, `pwd`) VALUES ('pochita', 'e10adc3949ba59abbe56e057f20f883e'); <br>

INSERT INTO `game` (`starttime`) VALUES ('2020-09-14 23:18:17'); <br>
INSERT INTO `game` (`starttime`) VALUES ('2022-12-14 23:18:17'); <br>

INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('123456', '1', '1'); <br>
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('23441', '2', '2'); <br>
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('12314123', '3', '1'); <br>
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '1', '2'); <br>
INSERT INTO `acc_game` (`score`, `accid`, `gameid`) VALUES ('1312', '3', '2'); <br>

Here the password is md5 encrypted, which is 123456 for these three users

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


<h3> UI, UDP transmission, and the game </h3>
1. When each user starts the client after a server started, a Signing Up page would show up: <img src="/src/main/resources/imgs/SignUp.png"/><br>
Users can choose to register a new account (the <code>account/register</code> API will be called) or Sign In using an existing account: <img src="/src/main/resources/imgs/LogIn.png"/><br>
Error handling will be made by the <code>account/login</code> API. <br>
2. A welcome page would come up after logged in successfully:  <img src="/src/main/resources/imgs/Welcome.png"/><br>
User at here could choose to view the leader board or start the game directly. <br>
3. If the user choose to view the leader board, the leader board with 6 highest scores with their players would display: 
<img src="/src/main/resources/imgs/LeaderBoard.png"/><br>
4. If the user choose to start the game, he/she will be added to the waiting room automatically: <img src="/src/main/resources/imgs/Waiting.png"/><br>
After joining the waiting room, players can see all players joined the game with usernames listed. the server will start to communicate with clients using sockets. 
All clients would inform the server with their joining (implemented in the <code>src/main/java/com/seven/zichen/snakegame/socket/WaitingClient.java</code>), 
and clients would know others joining (implemented in <code>src/main/java/com/seven/zichen/snakegame/socket/WaitingRoom.java</code>). <br>
5. All UI panels are implemented in the folder <code>src/main/java/com/seven/zichen/snakegame/models/</code><br>
6. After any one of the waiting player choose to start the game, the game will start and the game frame would be drawn: <img src="/src/main/resources/imgs/Game.png"/><br>
which is implemented in <code>src/main/java/com/seven/zichen/snakegame/client/DrawGame.java</code><br>
When the game starts, the server would start to communicate with clients through UDP transmission. There are 4 types of UDP datagrams: Game Initialization, Direction Change, Grid Handling and Score Sending. <br>
Inside the <code>~/client</code> folder, there is a <code>ClientListener</code> implements methods listening UDP transmission from the server and a <code>ClientListener</code> 
responsible for sending UDP to the server. At the beginning, the <code>GameManager</code> inside the <code>~/game</code> folder through <code>GameHandlerManager</code> and <code>GameHandlerOutput</code> 
would send out a Game Initialization UDP to all clients, <code>ClientListener</code> after receiving the UDP, will initialize the <code>DrawGame</code> through <code>Client</code>. <code>MoveSnakes</code> will 
continuously update the whole game grid with different colors for each pixel (Different Colors: Self Snake, Other Snake(s), Apple) and send grids to clients by Grid Handling. <code>HandleReturnedGrid</code> on the client side will handle the grid sent by the server 
and update the game frame through <code>DrawGame</code>. If players press keyboard to change the snake direction, <code>HandleInputDirection</code> on the client side will handle the key event and 
a Direction Change UDP would be sent to the server. The <code>MoveSnake</code> on the server side will update the direction of the requested snake and update the grid. <br>
When the game is over(all snakes are dead or time runs up), <code>Game Manager</code> on the server side will send Score Sending UDP to all clients, clients after receiving scores will display the game over page: <img src="/src/main/resources/imgs/GameOver.png"/><br>
Users could choose to view the leader board with the latest scores updated.  <br>
UDP Transmission Code Reference: https://github.com/TheoCabannes/SnakeGame 