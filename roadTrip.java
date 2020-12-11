import java.io.BufferedReader;
import java.util.*;
import java.io.*;

public class roadTrip extends graph
{
    Hashtable<String, String> places;
    Hashtable<String, String> previous;
    Hashtable<String, Boolean> isVisited; 
    Hashtable<String, Integer> distance;

    HashSet<String> cityList; 
    int milesTaken, time;
    graph map; 

    public roadTrip() {
        places = new Hashtable<>();
        previous = new Hashtable<>();
        isVisited = new Hashtable<>();
        distance = new Hashtable<>();
        cityList = new HashSet<>();
        map = new graph();
        milesTaken = 0;
        time = 0;
    }

    List<String> route(String start, String end, List<String> placesOfInterest)
    {
        ArrayList<String> path = new ArrayList<>();
        map.addEdge(start,start,0,0);
        Iterator<String> cityIndex = cityList.iterator();
        while(cityIndex.hasNext())
        {
            String city = cityIndex.next();
            if(city!=null)
            {
                isVisited.put(city,false);
                distance.put(city,Integer.MAX_VALUE);
            }
        }

        distance.put(start, 0);
        visitCities(cityList);

        ArrayList<Integer> sortInterest = new ArrayList<>();
        ArrayList<String> attractionsList= new ArrayList<>();
        Hashtable<Integer, String> conversion = new Hashtable<>();
        Iterator<String> attractionList = placesOfInterest.iterator();
        while(attractionList.hasNext())
        {
            String attractionIndex = attractionList.next();
            sortInterest.add(distance.get(places.get(attractionIndex)));
            conversion.put(distance.get(places.get(attractionIndex)),attractionIndex);
        }

        Collections.sort(sortInterest);

        for(int index : sortInterest)
        {
            attractionsList.add(places.get(conversion.get(index)));
        }

        attractionsList.add(0,start);
        if(attractionsList.contains(end))
        {
            attractionsList.remove(end);
            attractionsList.add(end);
        }else{
            attractionsList.add(end);
        }
        Stack locationList = new Stack();

        for(int i =0;i<attractionsList.size()-1;i++)
        {
            String currentAttraction = attractionsList.get(i);
            String nextAttraction = attractionsList.get(i++);
            String temp = attractionsList.get(i++);

            locationList.push(nextAttraction);
            
            while(!currentAttraction.equals(nextAttraction))
            {
                String prevAttraction = previous.get(nextAttraction);
                milesTaken+=getimportance(nextAttraction,prevAttraction);
                time+=calculateTime(nextAttraction,prevAttraction);
                locationList.add(prevAttraction);
                nextAttraction=previous.get(nextAttraction);
            }

            while(!locationList.isEmpty())
            {
                path.add((String)locationList.pop());
            }
            isVisited = new Hashtable<>();
            previous = new Hashtable<>();
            distance = new Hashtable<>();
            for(String cities: cityList)
            {
                if(cities!= null)
                {
                    isVisited.put(cities,false);
                    distance.put(cities,Integer.MAX_VALUE);
                }
            }
            distance.put(temp,0);
            visitCities(cityList);
        }
        return path;
    }

    public void parseRoads(String roadFile){
        String roads = roadFile;
        String roadContent = "";

        BufferedReader read = new BufferedReader(new FileReader(roads));
            while ((roadContent = read.readLine()) != null)
            {
                String[] line = roadContent.split(",");
                Integer distance = Integer.parseInt(line[2]);

                if (line[3].equals("10a")) //theres a typo in the sheet
                {
                    line[3] = "100";
                }

                Integer time = Integer.parseInt(line[3]);
                if (line[0] != null && line[1] != null) 
                {
                    map.addEdge(line[0], line[1], distance, time);
                    cityList.add(line[0]);
                    cityList.add(line[1]);
                }
            }
    
    }

    public void parseAttractions(String attractionFile) 
    {
        String attraction = attractionFile;
        String attractionContent = "";

        BufferedReader read = new BufferedReader(new FileReader(attraction));
            while ((attractionContent = read.readLine()) != null) 
            {
                String[] line = attractionContent.split(",");
                places.put(line[0],line[1]);
            }
        places.remove("Attraction");
    }

    private String shortestDistance()
    {
        String vertex ="";
        int x = Integer.MAX_VALUE;

        for (String cities:cityList)
        {
            if(!isVisited.get(cities) && distance.get(cities)<=x)
            {
                x = distance.get(cities);
                vertex = cities;
            }
        } 
        return vertex;
    }
    
    private void visitCities(HashSet<String> cityList)
    {
        for(String cities: cityList)
        {
            while(!isVisited.get(cities))
            {
                String vertex = shortestDistance();
                if(vertex!= null)
                {
                    isVisited.put(vertex, true);
                }
                for(String value: map.adjacentList.get(vertex))
                {
                    int importance = getimportance(vertex,value);
                    if(distance.get(value)>distance.get(vertex)+importance&&!value.equals(vertex))
                    {
                        distance.put(value,distance.get(vertex)+importance);
                        previous.put(value, vertex);
                    }
                }   
            }
        }
    }

    private int calculateTime(String v1, String v2)
    {
        int minutes = 0;
        for(edge edgePoint: map.edgeCases)
        {
            if(edgePoint.start.equals(v1)&&edgePoint.end.equals(v2))
            {
                return edgePoint.minutes;
            }
            else if (edgePoint.start.equals(v2)&&edgePoint.end.equals(v1))
            {
                return edgePoint.minutes;
            }
        }
        return minutes;
    }
    private int getimportance(String v1, String v2)
    {
        int importance = 0;
        for(edge edgePoint: map.edgeCases)
        {
            if(edgePoint.start.equals(v1)&&edgePoint.end.equals(v2))
            {
                return edgePoint.importance;
            }
            else if (edgePoint.start.equals(v2)&&edgePoint.end.equals(v1))
            {
                return edgePoint.importance;
            }
        }
        return importance;
    }

    public void printRoads(List<String> routes)
    {
        System.out.println("\n We've gone this many miles:"+ milesTaken);
        System.out.println("\n We've taken this many minutes: "+ time);
        System.out.println(routes.toString());
    }

    public static void main(String[] args){
        String roads = args[0];
        String attractions = args[1];
        List<String> attractiontrips= new ArrayList<>();

        roadTrip trip = new roadTrip();
        trip.parseRoads(roads);
        trip.parseAttractions(attractions);
    
        attractiontrips.add("Alcatraz");
        attractiontrips.add("Niagra Falls");
        attractiontrips.add("Mesa Arch");

        List<String> path = trip.route("San Jose CA", "Mystic CA",attractiontrips);
        trip.printRoads(path);
    }
}
