package isgw;

import java.util.HashMap;

public class DataSets {

    static class Lighting {

        static HashMap<Double, Double> bargraph() {

            HashMap<Double, Double> map = new HashMap<>();
            map.put(1d, 200d);
            map.put(2d, 212d);
            map.put(3d, 215d);
            map.put(4d, 210d);
            map.put(5d, 208d);
            map.put(6d, 262d);
            map.put(7d, 280d);
            map.put(8d, 276d);
            map.put(9d, 266d);
            map.put(10d, 250d);
            map.put(11d, 198d);
            map.put(12d, 150d);

            return map;
        }

        static HashMap<Double, Double> livedata() {
            HashMap<Double, Double> map = new HashMap<>();

            // AVG is 1.6


            return map;
        }
    }

    static class Entertainment {

        static HashMap<Double, Double> bargraph() {

            HashMap<Double, Double> map = new HashMap<>();
            map.put(1d, 140d);
            map.put(2d, 200d);
            map.put(3d, 120d);
            map.put(4d, 160d);
            map.put(5d, 145d);
            map.put(6d, 150d);
            map.put(7d, 165d);
            map.put(8d, 149d);
            map.put(9d, 155d);
            map.put(10d, 166d);
            map.put(11d, 146d);
            map.put(12d, 151d);

            return map;
        }

        static HashMap<Double, Double> livedata() {
            HashMap<Double, Double> map = new HashMap<>();

            // AVG is 2.1

            return map;
        }
    }

    static class White {

        static HashMap<Double, Double> bargraph() {

            HashMap<Double, Double> map = new HashMap<>();
            map.put(1d, 260d);
            map.put(2d, 250d);
            map.put(3d, 110d);
            map.put(4d, 80d);
            map.put(5d, 145d);
            map.put(6d, 135d);
            map.put(7d, 140d);
            map.put(8d, 138d);
            map.put(9d, 111d);
            map.put(10d, 93d);
            map.put(11d, 81d);
            map.put(12d, 77d);

            return map;
        }

        static HashMap<Double, Double> livedata() {
            HashMap<Double, Double> map = new HashMap<>();


            return map;
        }
    }

    static class Thermal {

        static HashMap<Double, Double> bargraph() {

            HashMap<Double, Double> map = new HashMap<>();
            map.put(1d, 280d);
            map.put(2d, 200d);
            map.put(3d, 140d);
            map.put(4d, 220d);
            map.put(5d, 300d);
            map.put(6d, 450d);
            map.put(7d, 550d);
            map.put(8d, 525d);
            map.put(9d, 325d);
            map.put(10d, 280d);
            map.put(11d, 190d);
            map.put(12d, 180d);

            return map;
        }

        static HashMap<Double, Double> livedata() {
            HashMap<Double, Double> map = new HashMap<>();


            return map;
        }
    }

    static HashMap<Double, Double> monthlyElectricityData() {
        HashMap<Double, Double> map = new HashMap<>();
        map.put(1d, 707d);
        map.put(2d, 801d);
        map.put(3d, 1000d);
        map.put(4d, 1203d);
        map.put(5d, 1300d);
        map.put(6d, 1350d);
        map.put(7d, 1400d);
        map.put(8d, 1389d);
        map.put(9d, 1117d);
        map.put(10d, 931d);
        map.put(11d, 815d);
        map.put(12d, 777d);

        return map;
    }


}
