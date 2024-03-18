package mechanics.hw2;

public class Task6 {

    private static double g = 9.8;

    private static double timeIncrease = 0.1;

    private static double v1 = 0; //the speed of M1
    private static double v2 = 0; //the speed of M2 (and M3)

    private static double function(double m1, double m2, double m3, double mi1, double mi2, double mi3, double l, double dt){
        double a3 = m3/m2 * g - (mi2 * m2 * g);
        a3 = a3 > 0 ? a3 : 0;
        double a2 = a3;

        v2 += a3 * dt;

        //we want to calculate the time it takes "t" for m2 to hit the pulley.
        //because I have created a3 with the opposite direction from the given xy-coordinate, let's assume that O (center point of xy-coordinate) is located at the left bottom corner of M2 (with opposite directions).
        //x(t) (when m2 hits the pulley) = l
        //x(0) = 0
        //we need V(t) = V(0) + integral(a2) = a3 * t
        // x(t) - x(0) = integral(V(t)) => l = a3* t^2 => t^2 = l/a3

        double t = Math.sqrt(l/a3);

        double a1 = 0;

        if(dt >= t){
            a1 = (a3 * t - (mi1 * m1 * (m1 + m2) * g) * (dt - t))/m1;
            v1 += a1 * (dt + timeIncrease) - a1 * (dt - t);
            a2 = 0;
            a3 = 0;
            v2 = 0;


        }

        /*
        System.out.println("REPORTING AT TIME t = " + dt);
        System.out.println("acceleration for M1: " + a1);
        System.out.println("acceleration for M2: " + a2);
        System.out.println("acceleration for M3: " + a3);
        */


        //to compute maximal distance for M1, let's define the xy-plane, at the bottom left of M1.
        //x(0) = 0
        //x(t) = x(0) + integral(V(t))
        //V(0) = 0
        //V(t) = V(0) + integral(a(t)) = a1 * t
        //x(t) = a1 * t^2


        double timeToStop = 0;


        if((v1 > 0 || v2 > 0) || dt == 0) timeToStop = function(m1, m2, m3, mi1, mi2, mi3, l, dt + timeIncrease);
        if(dt == 0){
            double x1max = ((a3 * t - (mi1 * m1 * (m1 + m2) * g) * (dt - t))/m1) * timeToStop * timeToStop; //a1 * dt^2
            System.out.println("M1 moved by " + x1max + " meters (approximately).");
            System.out.println(timeToStop + " seconds have passed for everything to stop.");
        }

        return timeToStop + timeIncrease;
    }


    public static void main(String[] agrs){
        //changing masses
        System.out.println("EXAMPLES WITH DIFFERENT MASSES:");
        function(5, 5, 5, 0.01, 0.01, 0, 5, 0);
        function(10, 10, 10, 0.01, 0.01, 0, 5, 0);
        function(10, 5, 5, 0.01, 0.01, 0, 5, 0);

        //changing friction of M2
        System.out.println();
        System.out.println("EXAMPLES WITH DIFFERENT FRICTIONS FOR M2");
        function(5, 5, 5, 0.01, 0.0, 0, 5, 0);
        function(5, 5, 5, 0.01, 0.05, 0, 5, 0);

        //changing friction of M1
        System.out.println();
        System.out.println("EXAMPLES WITH DIFFERENT FRICTIONS FOR M1");
        function(5, 5, 5, 0.05, 0.01, 0, 5, 0);
        function(5, 5, 5, 0.10, 0.01, 0, 5, 0);


        //sorry that the prints look bad, I got too tired :/

        //I have no idea what interesting cases there are, I barely know what I am doing.
    }
}
