Optimal Roadtrip Path Calculator

This project includes two Java files, Graph.java and RoadTrip.java, which together implement Dijkstra's algorithm to calculate the optimal road trip path between cities, while also considering various points of interest along the way.
Overview

The provided Java classes (Graph and RoadTrip) are designed to model a graph of cities connected by roads, where each edge represents a road between two cities. The RoadTrip class extends Graph and applies Dijkstra's algorithm to find the shortest path based on various attributes such as distance, time, and attractions. This project can be used to calculate an optimal road trip based on given parameters such as distance and time.
Features

    Graph Representation: Represents cities as nodes and roads between them as edges.
    Dijkstra's Algorithm: Utilized to find the shortest path between two cities.
    Attraction Points: Can incorporate points of interest along the route.
    Customizable Weights: Each road has attributes such as distance and time, which are used to determine the optimal path.

Getting Started

To run the project, you'll need to have the following prerequisites:
Prerequisites

    Java 8 or later installed
    A code editor (e.g., IntelliJ, VS Code, Eclipse)
    Road data and attraction data in CSV format

Project Structure

The project contains two main files:

    Graph.java: Defines the structure of the graph.
        Manages nodes and edges.
        Contains methods to add nodes and edges.

    RoadTrip.java: Extends the Graph class and calculates the optimal road trip.
        Parses road and attraction data.
        Implements Dijkstra's algorithm to find the shortest route.
        Manages attraction points to be included in the route.

Files

    Graph.java: Implements the graph and manages nodes and edges.
    RoadTrip.java: Implements the core functionality of the road trip calculation.

Usage
Running the Code

Compile and run the project using the following commands:

sh

javac Graph.java RoadTrip.java
java RoadTrip <roadFile> <attractionFile>

    roadFile: A CSV file containing the road data in the format: start_city,end_city,distance,time.
    attractionFile: A CSV file containing the attraction data in the format: attraction_name,city.

For example:

sh

java RoadTrip roads.csv attractions.csv

Example CSV Files

    roads.csv:

San Jose CA,Los Angeles CA,340,300
Los Angeles CA,San Diego CA,120,120
San Jose CA,San Francisco CA,50,60

attractions.csv:

    Attraction,City
    Alcatraz,San Francisco CA
    Niagra Falls,Niagra Falls NY
    Mesa Arch,Moab UT

Customizing the Road Trip

You can specify the starting city, ending city, and any attractions you want to visit. This is done in the main method of RoadTrip.java:

java

List<String> attractiontrips= new ArrayList<>();
attractiontrips.add("Alcatraz");
attractiontrips.add("Niagra Falls");
attractiontrips.add("Mesa Arch");

List<String> path = trip.route("San Jose CA", "Mystic CA", attractiontrips);
trip.printRoads(path);

Modify the list to include your preferred attractions.
Output

After running the program, it will print:

    Total Distance and Time: The distance and time taken to complete the journey.
    Route: The list of cities and attractions visited during the road trip.

Implementation Details
Graph.java

    addNode(String location): Adds a city to the graph.
    addEdge(String start, String end, int distance, int time): Adds a road between two cities, with attributes like distance and time.
    printGraph(): Prints all the connections in the graph.

RoadTrip.java

    parseRoads(String roadFile): Parses road data from a CSV file.
    parseAttractions(String attractionFile): Parses attraction data from a CSV file.
    route(String start, String end, List<String> placesOfInterest): Uses Dijkstra's algorithm to calculate the optimal path, incorporating points of interest.
    printRoads(List<String> routes): Prints the entire route, along with the distance and time taken.

Example Output

vbnet

We've gone this many miles: 550
We've taken this many minutes: 480
[San Jose CA, San Francisco CA, Alcatraz, Los Angeles CA, Niagra Falls, Mystic CA]

Future Improvements

    GUI Implementation: Develop a user-friendly graphical interface to visualize the graph and the calculated road trip.
    Optimization Parameters: Allow users to prioritize distance, time, or importance in calculating the route.
    Error Handling: Improve error handling, especially while parsing CSV files.
