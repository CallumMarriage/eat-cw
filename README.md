# eat-cw

## Prerequisites to running this project
> Java 8
> Maven 

## Running the application

This is a Spring boot application packaged by maven.


### Running from the CLI

To run via maven please use the following commands in the root directy:

> mvn clean install 
> java -jar target/coursework-project-1.0.jar

### Running from an IDE

To run via the IDE add a new configuration with the IDE referencing the Application class 
at the following directory: src/main/java/com/coursework/Application.java

## Attempted Questions

### Mandatory 

> a) Login

> b) View a list of ALL free lessons

> c) Choose a lesson

> d) View their chosen lessons

> e) Finalise a booking

> f) Log out

### Advanced 

> Section 2: Restrict the number of lessons per user

> Section 3: Implement user sign-up functionality

> Section 4: AJAX implementation to check if selected username exists

> Section 5: Client-side validation of username
 
## Design

The main concept that has influenced my design the most is separation of concerns, I have done this by ordering my code in a layered service first approach, 
with all the code relevant to a specific purpose (User or Lesson) in their own directories and all of the code within that directory layered. 

Each Service is essentially a microservice with a model, view(UI view) controller, service and repository.


###The layers:

Presentation Layer
Service Layer
Business Layer
Repository Layer

Each layer of the code can only talk to the layer beneath it and the lower layer always defines an "interface or contract"
that the above layer has to adhere to, I have done this to effectively silo logic in each layer, the moment logic starts "leaking" down the 
architecture complexity starts building and it becomes more difficult to maintain.

The main benefit of this approach is that the service layer does not care which database it is talking to or which language it
is responding in, some of the requests respond with an XML response but all the Service knows is that it receives a Java 
object and returns a java object, all the logic that deals with the conversion is in the controller (presentation tier).


### User Service

The User service is a simple implementation, it may seem overkill having separate files for the service and the controller 
but I think it is important that I maintain that separation so that 
if I wanted to replace the Controller with something else it can easily plug into all the logic done without repeating code.


### Lesson Service

The Lesson service is more complex, starting with a controller that has only got an instance of Lesson Service which in turn has two repositories.
The reason this service has two repositories is because the logic of Lesson Booked is tightly coupled with Lessons.
I could have made a separate service for this on it is own, it could be argued that it is just as tightly couples with the user service but for simplicity
I have added it into the Lesson Service and instead pass in the Client ID on the request.

A noticeable facet of my design is the split between db objects and presentation objects, the db objects effectively represent what is in the 
database that we will be querying and the presentation object represents what will be returned to the presentation tier. 

The mapping is done in the business layer, triggered by the service layer, this ensures that the dao layer and the presentation layer
only have references to the correct object type.
 
In this application the business layer is very limited as there is very little business logic 
in the brief. Any logic specific to the business case would go in that layer away from the service layer so that if 
the business rules were to change, it should not impact any of the rest of the application, as long as it continues to 
deliver the expected Contract it has to the Service Layer.

### The benefits of the Contract / Interface approach

Using contracts allows for different layers to only do operations that are relevant to them with confidence that all the 
services they need to use will behave in a certain way. This is done by having specific entry points into a layer that will return a certain way when fed certain attributes.
If we were not to use contracts and have services use different methods within another layer, the application will become tightly coupled
and would be more difficult to test.

Testing with contracts is very easy, you can just mock out whatever service you are using with tools such as Mockito and 
test what your layer needs to do.

### Decoupling the lesson service from the User service

A major difference in my design than what is probably in other versions is that I completely separate the logic of users and Lessons,
even when dealing with the Lesson Booked I have preferred the approach of first making a request to retrieve the Client ID 
and then making a separate request with that client ID to book a lesson. The reason I have gone with this approach is so that 
I can maintain the separation, the Lesson Service should not have any knowledge of the existence of the User service as otherwise the two would 
become tightly coupled.

Doing this logic in the UI is also beneficial as it now means that the UI can source the Client information from anywhere and 
the Lesson Service will still behave the same.

### Decoupling the session state functionality from the controller

In my design the logic to control the session is placed in a common class (This is because both services use it).
There are two main reasons as to why I have abstracted this logic away. The first reason is to avoid duplication, some of 
the logic is duplicated in both services across multiple methods. The second reason is to separate concerns, with my designs
the controller does not have to worry about the session and instead just deals with standard java objects such as lessons 
and clients.

### Choice of Lot Selection structure

The structure I have used for the lot selection is very simple, it is an array list stored as a session cookie.

The reason I chose this is that in the brief it suggests that the selection should be session dependent.
By storing the selection in the session, the next time a new session is created, the selection will be wiped and
a new list will be created. 

If you wanted the selection to persist between sessions it would be better to use a java structure, this
could be a listMultiMap with the username as the key and a the lesson Ids as the values.


### The use of side effects in the support methods

In the common directory, the majority of methods use pass by reference, this means that the changes in that method
have side effects on the rest of the code. In my opinion the use of side effects in code is a bad practise as it 
makes the code harder to predict but in this code I have used to it make it simpler.

