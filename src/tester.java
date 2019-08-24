import java.util.ArrayList;

public class tester {

	ArrayList<Integer> a;
	
	public tester() {
		a = new ArrayList<Integer>();
		a.add(1);
		a.add(2);
	}
	public void test() {
		ArrayList<Integer> b = new ArrayList<Integer>(a);
		b.add(3);
		System.out.println(a);
		System.out.println(b);
	}
	
	public static void main(String[] args) {
		tester t = new tester();
		t.test();
	}
}
