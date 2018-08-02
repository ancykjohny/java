AGE CHECK
import java.util.*;
class agemar
{
public static void main(String args[])
{

Scanner sc=new Scanner(System.in);
System.out.println("enter the details");
String n[]=new String[2];
String ad[]=new String[2];
int a[]=new int[2];
int m[]=new int[2];

for(int i=0;i<2;i++)
{
n[i]=sc.nextLine();
ad[i]=sc.nextLine();
a[i]=sc.nextInt();
if(a[i]>18)
{
int count=0;
count=count++;
}
System.out.println(count);
}

}
}
CALCULATOR
import java.util.*;
class Calculator
{
public static void main(String[] args) 
{
        int c;
	Scanner s=new Scanner(System.in);
	System.out.println("Enter the var1");
	int v1=s.nextInt();
	System.out.println("Enter the  var2");
	int v2=s.nextInt();
	System.out.println("------CHOICE------");
	System.out.println("1.add\n 2.sub\n 3.mul\n 4.div\n 5.per\n 6.exit");
	System.out.println("Enter the choice:");
	int choice =s.nextInt();
	switch(choice)
	{
	case 1:
		c=v1+v2;
	System.out.println(c);
	break;
	case 2:
	            c=v1-v2;
	            System.out.println(c);
                     break;
	case 3:
		 	c=v1*v2;
	              System.out.println(c);
		break;
	case 4:
			c=v1/v2;
	          System.out.println(c);
		break;
	case 5:
			c=v1%v2;
	         System.out.println(c); 
		break;
		default:
			System.out.println("Exit");
	}
}
}


