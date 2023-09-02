<h1>CS 360 - Mobile Architecture and Programming</h1>
<h2>By Adam Sissoko<br>
Southern New Hampshire University</h2>

<h3>Project Overview</h3>
<p>
This repository is the consolidated collection of all projects and assignments undertaken as part of the CS 360 Mobile Architecture and Programming course at Southern New Hampshire University (SNHU). The ultimate deliverable of this course was the development of an event tracking Android application. This application was engineered using a variety of Android frameworks, APIs, and subsystems to create a robust and user-friendly experience.
</p>

<h3>Core Technologies and Libraries</h3>
<ul>
  <li><strong>Android Studio:</strong> The primary Integrated Development Environment (IDE) for Android app development.</li>
  <li><strong>SQLite Database:</strong> Used for persistent local storage of user credentials and event details.</li>
  <li><strong>RecyclerView:</strong> An Android UI component used for efficiently displaying large sets of data.</li>
  <li><strong>Alarm Manager:</strong> An Android system service for scheduling tasks and notifications.</li>
</ul>

<h3>Detailed Features</h3>
<p>
The application's primary functionalities are broken down as follows:
</p>
<ul>
  <li>
    <strong>Authentication:</strong> 
    <p>Upon opening the app, users encounter a login screen. The authentication system is built using SQLite to securely store usernames and hashed passwords. Invalid login attempts are flagged, and error messages are displayed to guide the user.</p>
  </li>
  <li>
    <strong>Event Management:</strong>
    <p>Post-authentication, users are navigated to the event management dashboard. Here, users can create new events, which are then displayed in a RecyclerView for easier navigation. Each event entry includes details such as the event name, description, and scheduled time.</p>
  </li>
  <li>
    <strong>Notifications:</strong>
    <p>Users have the option to set reminders for each event. The Android Alarm Manager API is leveraged to schedule these notifications, ensuring that reminders are pushed even if the app is not in the foreground.</p>
  </li>
  <li>
    <strong>Data Persistence:</strong>
    <p>SQLite databases are utilized to ensure that all user data and event details are stored persistently. This enables a seamless user experience, allowing access to past events and user settings across different sessions.</p>
  </li>
</ul>

<h3>Repository Structure and Additional Documentation</h3>
<p>
For those interested in diving deeper into the codebase, the complete set of project files can be accessed in the <a href="https://github.com/adamsissoko/CS360/tree/main/Apps/CS%20360%20Final%20Project" target="_blank">Final Project folder</a>.
</p>
<p>
Supplementary materials include:
</p>
<ul>
  <li><a href="https://github.com/adamsissoko/CS360/blob/main/Apps/CS%20360%20Final%20Project/2-3%20Milestone%20-%20Goals%20and%20Users.docx.pdf" target="_blank">User Requirements Document</a> - An initial analysis of user needs and expectations.</li>
  <li><a href="https://github.com/adamsissoko/CS360/blob/main/Apps/CS%20360%20Final%20Project/3-3%20Project%20One%20-%20App%20Proposal.docx.pdf" target="_blank">Project Proposal</a> - The original project design and scope, including the various phases of development.</li>
</ul>

<hr>

<h3>Development Methodology and Approach</h3>
<p>
The development of the application followed a systematic and iterative process. The methodology can be broken down into the following stages:
</p>
<ol>
  <li><strong>Research Phase:</strong> To identify gaps in the market and understand the scope of existing solutions, an in-depth comparative study was conducted. </li>
  <li><strong>Requirement Gathering:</strong> This phase involved consolidating the findings from the research phase, and aligning them with the project scope and objectives.</li>
  <li><strong>Design and Prototyping:</strong> Based on the requirements, wireframes and mockups were created to visualize the end product.</li>
  <li><strong>Development:</strong> Initial versions were developed focusing on core functionalities like user authentication. Gradually, more complex features such as event management and notifications were implemented.</li>
  <li><strong>Testing:</strong> Rigorous unit tests and usability tests were performed to identify bugs and areas for improvement.</li>
  <li><strong>Deployment:</strong> Once tested, the app was made ready for deployment with considerations for future scalability and feature addition.</li>
</ol>

<p>
Each development phase was marked by regular code reviews and debugging sessions, ensuring code quality and feature reliability. Further, the development involved regular commits to this repository, offering a version-controlled history of the project.
</p>

<h3>Future Considerations</h3>
<p>
As the application stands, it fulfills its core functionalities effectively. However, several features could enhance its capabilities in future versions. These may include:
</p>
<ul>
  <li>Integration with cloud services for data backup</li

>
  <li>Social sharing options for events</li>
  <li>Advanced filtering and sorting of events</li>
  <li>User profiles with more customization options</li>
</ul> 

<h3>Conclusion</h3>
<p>
This project represents the application of various Android development techniques and best practices learned during the CS 360 course. It showcases an ability to conceptualize, design, develop, and test a full-fledged Android application with multiple features and functionalities. The repository serves as both a portfolio piece and a learning resource for others interested in Android development.
</p>
  </p>
