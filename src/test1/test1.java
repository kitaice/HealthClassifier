package test1;
public class test1 {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int[] a = {3,4,7,9,5,2,6,1};
		int j=6;
		while(j>=0){
			int key = a[j];
			int i=j+1;
			while(i<=7 && a[i]<key){
				a[i-1]=a[i];
				i++;
			}
			a[i-1]=key;
			j--;
			for(int k=0;k<8;k++) System.out.print(a[k]+" ");
			System.out.println();
		}
//		int i=1;
//		while(i<a.length){
//			int key = a[i];
//			int j=i-1;
//			while(j>=0 && a[j]>key){
//				a[j+1]=a[j];
//				j--;
//			}
//			a[j+1]=key;
//			i++;
//			for(int k=0;k<8;k++) System.out.print(a[k]+" ");
//			System.out.println();
//		}
	}

}
