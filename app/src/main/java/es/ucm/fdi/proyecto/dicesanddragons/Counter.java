package es.ucm.fdi.proyecto.dicesanddragons;

public class Counter {
    private int value;
    private String name;

    public Counter(int val,String n) {
        this.value = val;
        this.name=n;
    }

    public Counter(int val){
        this.value=0;
        this.name=null;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int v){
        this.value=v;
    }

    public String getName(){
        return name;
    }

    public void setName(String n){
        this.name=n;
    }

    public void increment() {
        value++;
    }

    public void decrement() {
        if (value > 0) value--;
    }
}
