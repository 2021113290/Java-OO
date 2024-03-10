package P3;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-06
 * Time: 20:25
 */
public class FriendshipGraph {
    //public List<>
    public Map<Person, ArrayList<Person>> Graph=new HashMap<Person, ArrayList<Person>>();
    public void addVertex(Person person){
        for (Person P:Graph.keySet()) {
            if(P.getName().equals(person.getName())){//图中添加了两个重名的会报错
                System.out.println(person.getName()+"添加的person姓名重复");
                System.exit(0);
                //return false;
            }
        }
        ArrayList<Person> Array=new ArrayList<Person>();
        this.Graph.put(person,Array);
        //return true;
    }
    public void addEdge(Person person1, Person person2){
        if (Graph.containsKey(person1)){
            Graph.get(person1).add(person2);
        }else
        {
            System.out.println(person1.getName()+"友谊图中没有该人");//给一个不在图中的人添加边会报错
            //return false;
            System.exit(0);
        }
        //return true;
    }
    public int getDistance(Person person1,Person person2){
        //广度优先算法
        if(!Graph.containsKey(person1)||!Graph.containsKey(person2))return -1;
        if(person1.getName().equals(person2.getName()))return 0;
        Person start=person1,end=person1;
        int i,dis=0;
        Queue<Person> friend=new LinkedList<Person>();
        ArrayList<Person> visited=new ArrayList<Person>();
        if(person1==person2)return 0;
        friend.add(person1);
        visited.add(person1);
        while (!friend.isEmpty()){
            start=friend.poll();
            dis++;
            for(i=0;i<Graph.get(start).size();i++){
                end=Graph.get(start).get(i);//获取start的第i个朋友
                if(end==person2)return dis;
                if(!visited.contains(end)){
                    friend.add(end);
                    visited.add(end);
                }
            }
            i=0;
        }
        return -1;
    }
    public static void main(String[] args){
        FriendshipGraph graph = new FriendshipGraph();
        Person rachel = new Person("Rachel");
        Person ross = new Person("Rachel");
        Person ben = new Person("Ben");
        Person kramer = new Person("Kramer");
        graph.addVertex(rachel);
        graph.addVertex(ross);
        graph.addVertex(ben);
        graph.addVertex(kramer);
        graph.addEdge(rachel, ross);
        graph.addEdge(ross, rachel);
        graph.addEdge(ross, ben);
        graph.addEdge(ben, ross);
        System.out.println(graph.getDistance(rachel, ross));
        System.out.println(graph.getDistance(rachel, ben));
        System.out.println(graph.getDistance(rachel, rachel));
        System.out.println(graph.getDistance(rachel, kramer));
    }
}

