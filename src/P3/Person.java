package P3;

/**
 * Created with IntelliJ IDEA.
 * Description:
 * User: xinyu
 * Date: 2023-03-06
 * Time: 20:25
 */
public class Person {

    public Person(String name) {
        this.name = name;
    }

    private final String name;
    public String getName(){
        return this.name;
    }
   /* public boolean isSameName(String name){
        return this.name.equals(name);//当前对象名字是否与给定名字相同
    }*/
}
