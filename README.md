## What is Machine Learning
> “Machine Learning is the science of getting computers to learn and act like humans do, and improve their learning over time in autonomous fashion, by feeding them data and information in the form of observations and real-world interactions.”

Machine learning is an application of artificial intelligence (AI) that provides systems the ability to automatically learn and improve from experience without being explicitly programmed. Machine learning focuses on the development of computer programs that can access data and use it learn for themselves.

## How We Get Machines to Learn
There are different approaches to getting machines to learn, from using basic decision trees to clustering to layers of artificial neural networks, depending on what task you’re trying to accomplish and the type and amount of data that you have available.
## How MyMaster works

![image](https://user-images.githubusercontent.com/24621701/48917959-e08bf900-ee89-11e8-92b8-a95ee4239ffb.png)

1) We collected a dataset of differente successful master students in differente in masters with the skills they had to accomplish that.
2) We used a classification algorithm to detect what classe the user belongs to.
3) The user answers a serie of questions throw the android app, after every question the app collects the data it need to send to the Web API.
4) The API compute the data and send back a result to the android app.
5) The user gets the best match making master throw their skills with percentages and also with second and third choice.

## Screenshots
<img width="280" src="https://user-images.githubusercontent.com/24621701/48918256-baffef00-ee8b-11e8-94e8-f005726ab5e4.png" />  <img width="280" src="https://user-images.githubusercontent.com/24621701/48918260-bfc4a300-ee8b-11e8-8af7-feac3c9f3801.png" />  <img width="280" src="https://user-images.githubusercontent.com/24621701/48918266-c4895700-ee8b-11e8-9459-09d97dea492d.png" />

## Technologies used
The Web API is made using the Microsoft ML.NET framework, its an open source, cross-platform machine learning framework which makes machine learning accessible to .NET developers.

The android app is made using native java as our main clients are android ones with the gson library to manage restfull calls to the API.

## Team

[Amine Smahi](https://github.com/Amine-Smahi)

[Mohammed Benotmane](https://github.com/Mohammed-Benotmane)

[Oussama Zaoui](https://github.com/oussama-zaoui)

## License
The project is under MIT license.
