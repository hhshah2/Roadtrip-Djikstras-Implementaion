import java.util.*;
public class graph
{
    Hashtable<String,List<String>> adjacentList;
    List<edge> edgeCases;

   public graph()
   {
       adjacentList = new Hashtable<>();
       edgeCases = new ArrayList();
   }

   public class edge
   {
       String start;
       String end;
       int importance;
       int minutes;

       public edge( String start, String end, int importance, int minutes)
       {
           this.start = start;
           this.end = end;
           this.importance = importance;
           this.minutes = minutes;
       }
   }

   public void addNode(String location)
   {
       adjacentList.putIfAbsent(location,new ArrayList<>());
   }

   public void removeNode(String location)
   {
       adjacentList.remove(location);
   }

   public void addEdge(String start, String end, int distance, int time)
   {
       addNode(start);
       addNode(end);
       adjacentList.get(start).add(end);
       adjacentList.get(end).add(start);
       edgeCases.add(new edge(start,end,distance,time));
   }

   public void printGraph()
   {
       String connections ="";
       for( edge edge: edgeCases)
       {
           connections+=edge.start
           + "--->"+edge.end
           +" "+edge.importance
           +" "+edge.minutes
           +"\n";
       }
       System.out.println(connections);
   }
}