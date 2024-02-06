import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class OOP {

    String parameterString;
    int parameterInt;
    char parameterChar;

    public OOP() {}
    public OOP(String text) {
        this.parameterString = text;

    }

    public OOP(String text, String text2) {
        this.parameterString = text;

    }
    public OOP(int number) {
        this.parameterInt = number;
    }

    public OOP(int number1, int number2) {
        System.out.println(number1 + number2);
    }

    public OOP(int number1, String number2) {
        System.out.println(number1 + number2);
    }

    public OOP(TC01 tc01) {

    }


    public static void main(String[] args) {
        String hw = new String();
        TC01 tc01 = new TC01();

        OOP oop = new OOP();
        OOP oop1 = new OOP("Hello");
        System.out.println(oop1.parameterString);
        System.out.println(oop.parameterString);

        OOP oop2 = new OOP(123);
        System.out.println(oop2.parameterInt + "      " + oop2.parameterString);


        OOP oop3 = new OOP (100, 200);
        System.out.println(oop3.parameterString);
        System.out.println(oop3.parameterInt);
        System.out.println(oop3.parameterChar);

        //5 ways
        //1. keyword new + constructor method
        //2. new instance method of class ---> sum(Integer) = sum(new Integer(5))
        //3. new instance using constructor class
        //4. by cloning objects
        //5. deserialization


    }
}
