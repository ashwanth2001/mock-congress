import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Optimizer {

	int size;
	int bl;
	int br;
	int pl;
	int pr;
	
	ArrayList<String> names;
	ArrayList<int[]> list;
	int[] score;
	
	ArrayList<int[]> finch;
	ArrayList<int[]> finsc;
	
	Scanner input;
	
	public Optimizer() {
		input = new Scanner(System.in);
		size = input.nextInt();
		
		score = new int[6];
		score[0] = input.nextInt();
		score[1] = input.nextInt();
		score[2] = input.nextInt();
		score[3] = input.nextInt();
		score[4] = 0;
		score[5] = 0;
		
		pl = input.nextInt();
		pr = input.nextInt();
		
		names = new ArrayList<String>();
		list = new ArrayList<int[]>();
		int[] a;
		for(int i = 0; i<size; i++) {
			names.add(input.next());
			a = new int[4];
			for(int j = 0; j<3; j++) {
				a[j] = input.nextInt();
			}
			list.add(a);
		}
		
		finch = new ArrayList<int[]>();
		finsc = new ArrayList<int[]>();
		
		System.out.println("started");
	}
	
	public void optimize() {
		int[] choices = new int[size];
		recur(0, score, choices);
		organize();
	}
	
	public void recur(int num, int[] score, int[] choices) {
		if(num==size) {
			score[4] = (int)(Math.min((double)score[0]/score[1], 1.0)*10000);
			score[4]+=(int)(0.05*score[2]/(pl)*10000);
			score[5] = (int)(Math.min((double)score[1]/score[0], 1.0)*10000);
			score[5]+=(int)(0.05*(score[3])/(pr)*10000);
			finsc.add(score);
			finch.add(choices);
			return;
		}
		
		int[] sc;
		int[] ch;
		for(int i = 0; i<2; i++) {
			sc = score.clone();
			sc[(list.get(num)[0]+i)%2]+=10;
			
			sc[0]+=5*list.get(num)[1]*((i+1)%2);
			sc[1]+=5*list.get(num)[2]*((i+1)%2);
			sc[2]+=list.get(num)[1]*((i+1)%2);
			sc[3]+=list.get(num)[2]*((i+1)%2);
			
			ch = choices.clone();
			ch[num] = i;
			
			recur(num+1, sc, ch);
		}
	}

	public void organize() {
		for(int i = 0; i<finsc.size()-1; i++) {
			for(int j = 0; j<finsc.size()-i-1; j++) {
				if(finsc.get(j)[2]+finsc.get(j)[3]<finsc.get(j+1)[2]+finsc.get(j+1)[3]) {
					int[] temp = finsc.get(j);
					finsc.set(j, finsc.get(j+1));
					finsc.set(j+1, temp);
					int[] temp1 = finch.get(j);
					finch.set(j, finch.get(j+1));
					finch.set(j+1, temp1);
				}
			}
		}
		
		for(int i = 0; i<finsc.size()-1; i++) {
			for(int j = 0; j<finsc.size()-i-1; j++) {
				double val1 = Math.min((double)finsc.get(j)[0], (double)finsc.get(j)[1])/Math.max((double)finsc.get(j)[0], (double)finsc.get(j)[1]);
				double val2 = Math.min((double)finsc.get(j+1)[0], (double)finsc.get(j+1)[1])/Math.max((double)finsc.get(j+1)[0], (double)finsc.get(j+1)[1]);
					
				//double val1 = (double)(finsc.get(j)[4]*pl+finsc.get(j)[5]*pr)/(pl+pr);
				//double val2 = (double)(finsc.get(j+1)[4]*pl+finsc.get(j+1)[5]*pr)/(pl+pr);
				
				if(val1==1)
					val1 = 0;
				if(val2==1)
					val2 = 0;
				
				if(val1<val2) {
					int[] temp = finsc.get(j);
					finsc.set(j, finsc.get(j+1));
					finsc.set(j+1, temp);
					int[] temp1 = finch.get(j);
					finch.set(j, finch.get(j+1));
					finch.set(j+1, temp1);
				}
			}
		}
		int num = 0;
		int i = 0;
		while(num<Math.min(10, finsc.size())) {
			//if(finsc.get(i)[0]>finsc.get(i)[1]) {
				//System.out.print(Arrays.toString(finsc.get(i)));
				System.out.print("L: " + finsc.get(i)[0] + ", ");
				System.out.print("R: " + finsc.get(i)[1] + " - ");
				
				int[] arr = finch.get(i);
				for(int j = 0; j<names.size(); j++) {
					if(arr[j]==0)
						System.out.print(names.get(j) + ", ");
				}
				System.out.println("");
				num++;
			//}
			i++;
		}
	}
	
	public void inSession() {
			String n = input.next();
			String d = input.next();
			verdict(n, d);
	}
	
	public void verdict(String n, String d) {
		int[] l = findList(n);
		if(d.equalsIgnoreCase("yes")) {
			score[l[1]]+=10;
			score[0]+=5*list.get(l[0])[1];
			score[1]+=5*list.get(l[0])[2];
			score[2]+=list.get(l[0])[1];
			score[3]+=list.get(l[0])[2];
			
			
		}
		list.remove(l[0]);
		names.remove(l[0]);
		optimize();
	}
	
	public int[] findList(String n) {
		int[] ret = new int[2];
		for(int i = 0; i<names.size(); i++) {
			if(names.get(i).equalsIgnoreCase(n)) {
				ret[0] = i;
				break;
			}
		}
		ret[1] = list.get(ret[0])[0];
		return ret;
	}
	
	public static void main(String[] args) {
		Optimizer o = new Optimizer();
		o.optimize();
		//o.inSession();
	}
}
		