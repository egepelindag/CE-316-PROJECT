
import java.util.Scanner;


public class proje{
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Java Code");
        int a;
         for (int i = 0; i < 2; i++) {
            a = sc.nextInt();
            System.out.println("number"+ (i+1)+": "+a);
        }

    }
}
