import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.TreeMap;

public class Main {

    static HashMap<String, ArrayList<Integer>> hmZip;
    static TreeMap<String, ArrayList<Integer>> hmDate;

    static String INPUTFILE = "input/itcont.txt";
    static String OUTPUTFILE1 = "output/medianvals_by_zip.txt";
    static String OUTPUTFILE2 = "output/medianvals_by_date.txt";

    public static void main(String[] args) {

        hmZip = new HashMap<String, ArrayList<Integer>>();
        hmDate = new TreeMap<String, ArrayList<Integer>>();

        BufferedWriter bw = null;

//        System.out.println("CMTE_ID == ZIP_CODE == TRANSACTION_DT == TRANSACTION_AMT");
        int i = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(INPUTFILE))) {
            Record record;
            String line;
            CharSequence cs = "|";
            bw = new BufferedWriter(new FileWriter(OUTPUTFILE1));

            while ((line = br.readLine()) != null && line.contains(cs)) {
//                System.out.println(line);

                //Splitting the data
                String[] split = line.split("\\|");

                String cmteId = split[0];
                String zipCode = split[10];
                String transDate = split[13];
                String transAmt = split[14];
                String otherId = split[15];

                //OtherId == null
                //cmteId.length != 0
                //transAmt.length != 0
                if (otherId.length() == 0 && cmteId.length() != 0 && transAmt.length() != 0) {

                    record = new Record(cmteId, Integer.parseInt(transAmt));
                    record.setZipCode(zipCode);
                    record.setTransDate(transDate);

                    //ZipCode.length == 5
                    if (zipCode.length() == 5) {
                        processMedianValByZip(/*hmZip,*/ bw, record);
                    }

                    //Date.length() == 8
                    if (transDate.length() == 8) {
                        processMedianValbyDate(/*hmDate,*/ record);
                    }

                }
                i++;
                //System.out.println(i++);
                if (i == 100)
                    break;
            }
            br.close();
            bw.close();

            //Printing to File2 - medianvals_by_date.txt
            printMedianValByDate();

        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("\nTotal = " + i);
    }

    static void processMedianValByZip(BufferedWriter bw, Record record) throws IOException {

        String key = record.getCmteId() + record.getZipCode();

        if (!hmZip.containsKey(key)) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(record.getTransAmt());

            hmZip.put(key, arrayList);

        } else {
            hmZip.get(key).add(record.getTransAmt());
        }

        Collections.sort(hmZip.get(key));

        Integer median = calculateMedian(hmZip.get(key));
        Integer totalAmount = calculateTotalAmount(hmZip.get(key));

        String op = record.getCmteId() + "|" + record.getZipCode() + "|" + median + "|" + hmZip.get(key).size() + "|" + totalAmount + "\n";
//        System.out.println(op);
        bw.write(op);
    }

    static void processMedianValbyDate(Record record) {
        String key = record.getCmteId() + record.getTransDate();

        if (!hmDate.containsKey(key)) {
            ArrayList<Integer> arrayList = new ArrayList<Integer>();
            arrayList.add(record.getTransAmt());

            hmDate.put(key, arrayList);

        } else {
            hmDate.get(key).add(record.getTransAmt());
        }
    }

    static void printMedianValByDate() throws IOException {

        BufferedWriter bw2 = new BufferedWriter(new FileWriter(OUTPUTFILE2));

        for (String key : hmDate.keySet()) {
            Collections.sort(hmDate.get(key));

            Integer median = calculateMedian(hmDate.get(key));
            Integer totalAmount = calculateTotalAmount(hmDate.get(key));

            if (key.length() == 17) {
                String op = key.substring(0, 8) + "|" + key.substring(9, 17) + "|" + median + "|" + hmDate.get(key).size() + "|" + totalAmount + "\n";
                bw2.write(op);
            }
        }

        bw2.close();
    }

    static Integer calculateMedian(ArrayList<Integer> arrayList) {

        if (arrayList.size() == 1)
            return arrayList.get(0);

        if (arrayList.size() % 2 != 0) {
            Integer median = arrayList.get((arrayList.size() / 2));
            return median;
        } else {
            Integer median = (arrayList.get((arrayList.size() / 2)) + arrayList.get((arrayList.size() / 2) - 1)) / 2;
            return median;
        }
    }

    static Integer calculateTotalAmount(ArrayList<Integer> arrayList) {
        Integer totalAmount = 0;
        for (Integer i : arrayList)
            totalAmount += i;

        return totalAmount;
    }
}


