package test1;
class animal {
	public animal(){
	}
	public void say(){
		System.out.println("animal");
	}
}
class dog extends animal{
	
	public dog(){
	}
	public void say(){
		System.out.println("dog");
	}
}
public class test2 {
	public  static void main(String[] args){
		animal a;
		dog d = new dog();
		a = d;
		a.say();
	}
}