import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Arrays;

public class SpeedDemon {

    private final int MAGIC_NUMBER = 68791;

    public int processData(String filename) {
        int result = 0;
        try {
            FileReader dataFile = new FileReader(filename);
            BufferedReader bufferedDataFile = new BufferedReader(dataFile);
            String line = bufferedDataFile.readLine();
            int num = Integer.parseInt(line);
            HashMap<Data, Data> map = new HashMap<>();
            while (num-- > 0 && line != null) {
                line = bufferedDataFile.readLine();
                Data data = new Data(line);
                // if (map.containsKey(data)) {
                //     Data tmp = map.get(data);
                //     if (data.equals(tmp)) {
                //         tmp.count++;
                //         //System.out.println("(" + tmp + ", " + data + ")");
                //     } else {
                //         map.put(data, data);
                //     }
                // } else {
                //     map.put(data, data);
                // }
                if (map.containsKey(data)) {
                    Data temp = map.get(data);
                    temp.count++;
                } else {
                    map.put(data, data);
                }
            }
            bufferedDataFile.close();
            for (Data data : map.values()) {
                result += data.count * (data.count - 1) / 2;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return result;
    }

    private class Data {
        public String s;
        public int count = 1;

        public Data(String s) {
            this.s = s;
        }

        @Override
        public int hashCode() {
            char ch1[] = this.s.toCharArray();
            Arrays.sort(ch1);
            String str = String.valueOf(ch1);
            int hash = str.hashCode();
            // return hash % MAGIC_NUMBER;
            return hash;
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof Data)) {
                return false;
            } else {
                Data d = (Data) obj;
                if (this.s.length() != d.s.length()) {
                    return false;
                } else {
                    char ch1[] = this.s.toCharArray();
                    char ch2[] = d.s.toCharArray();
                    Arrays.sort(ch1);
                    Arrays.sort(ch2);
                    for (int i = 0; i < ch1.length; i++) {
                        if (ch1[i] != ch2[i]) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }

        @Override
        public String toString() {
            return this.s.toString();
        }
    }

    // Do not edit this method for contest submission
    public static void main(String[] args) {
        SpeedDemon dataProcessor = new SpeedDemon();
        // StopWatch w = new StopWatch();
        // w.start();
        int answer = dataProcessor.processData(args[0]);
        // w.stop();
        // System.out.println(w.getTime());
        System.out.println(answer);
    }
}

