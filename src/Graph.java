import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Graph {
    private final String[][] arrMatrix;

    Graph(int Y, int X) {
        arrMatrix = new String[Y][X];
    }

    private void plotGraphAxis() {
        for (int i = 0; i < arrMatrix.length; i++) {
            for (int j = 0; j < arrMatrix[i].length; j++) {
                if (j == 3) {
                    arrMatrix[i][j] = "|";
                } else {
                    arrMatrix[i][j] = " ";
                }
                if (i == arrMatrix.length - 2 & (j != 3)) {
                    arrMatrix[i][j] = "_";
                }

                if (j == 1 & i == arrMatrix.length / 2 - 2) { arrMatrix[i][j] = "B";}
                if (j == 1 & i == arrMatrix.length / 2 - 1) { arrMatrix[i][j] = "M";}
                if (j == 1 & i == arrMatrix.length / 2 ) { arrMatrix[i][j] = "I";}

                if (j == arrMatrix[0].length / 2 - 10 & i == arrMatrix.length - 1) { arrMatrix[i][j] = "-( Measurement date )-";}

            }
        }
    }

    private double getXrange(ArrayList<String> bmiLog) {
        // timetamps
        int lowX = 0; int highX = 0;
        ArrayList<String> timestamps = new ArrayList<>();
        for (String pair : bmiLog) {
            timestamps.add(pair.split("-")[1]);
            for (String timestamp : timestamps) {
                if (lowX == 0 || highX == 0) {
                    lowX = Integer.parseInt(timestamp);
                    highX = Integer.parseInt(timestamp);
                }
                if (Integer.parseInt(timestamp) < lowX) {
                    lowX = Integer.parseInt(timestamp);
                }
                if (Integer.parseInt(timestamp) > highX) {
                    highX = Integer.parseInt(timestamp);
                }
            }
        }
        return (highX-lowX) * 1.15;
    }

    private double getYrange(ArrayList<String> bmiLog) {
        // bmi values
        double lowY = 0; double highY = 0;
        ArrayList<String> bmiList = new ArrayList<>();
        for (String pair : bmiLog) {
            bmiList.add(pair.split("-")[0]);
            for (String bmi : bmiList) {
                if (lowY == 0 || highY == 0) {
                    lowY = Double.parseDouble(bmi);
                    highY = Double.parseDouble(bmi);
                }
                if (Double.parseDouble(bmi) < lowY) {
                    lowY = Double.parseDouble(bmi);
                }
                if (Double.parseDouble(bmi) > highY) {
                    highY = Double.parseDouble(bmi);
                }
            }
        }
        return (highY-lowY) * 1.1;
    }

    private int getLowestX(ArrayList<String> bmiLog) {
        int lowX = 0;
        ArrayList<String> timestamps = new ArrayList<>();
        for (String pair : bmiLog) {
            timestamps.add(pair.split("-")[1]);
            for (String timestamp : timestamps) {
                if (lowX == 0) {
                    lowX = Integer.parseInt(timestamp);
                } else if (Integer.parseInt(timestamp) < lowX) {
                    lowX = Integer.parseInt(timestamp);
                }
            }
        }
        return lowX;
    }

    private double getLowestY(ArrayList<String> bmiLog) {
        double lowY = 0;
        ArrayList<String> bmiList = new ArrayList<>();
        for (String pair : bmiLog) {
            bmiList.add(pair.split("-")[0]);
            for (String bmi : bmiList) {
                if (lowY == 0) {
                    lowY = Double.parseDouble(bmi);
                } else if (Double.parseDouble(bmi) < lowY) {
                    lowY = Double.parseDouble(bmi);
                }
            }
        }
        return lowY;
    }

    private void plotter(ArrayList<String> bmiLog) {
        double xTimePerCell = getXrange(bmiLog) / (arrMatrix[0].length - 7); // - 7 is aantal vrije cells links
        for (String entry : bmiLog) {
            int relativeTimestamp = Integer.parseInt(entry.split("-")[1]) - getLowestX(bmiLog);
            for (int i = 6; i < arrMatrix[0].length ; i++){
                if (relativeTimestamp <= (i - 6) * xTimePerCell ) {
                    double yBMIPerCell = getYrange(bmiLog) / (arrMatrix.length - 5); // vrije cellen onder
                    double relativeBMI = Double.parseDouble(entry.split("-")[0]) - getLowestY(bmiLog);
                    for (int j = 4; j < arrMatrix.length ; j++){
                        if (relativeBMI <= (j - 4) * yBMIPerCell ) {
                            // plot dit puntje
                            arrMatrix[arrMatrix.length - j][i - 1] = "(";
                            arrMatrix[arrMatrix.length - j][i] = "*";
                            arrMatrix[arrMatrix.length - j][i + 1] = ")";

                            // plot bmi waarde
                            for (int p = 0; p < entry.split("-")[0].length(); p++) {
                                try {
                                    arrMatrix[arrMatrix.length - j - 1][i + p - 1] = String.valueOf(entry.split("-")[0].charAt(p));
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //arrMatrix[arrMatrix.length - j][i + 1 + p] = String.valueOf(entry.split("-")[0].charAt(p));
                                }
                            }

                            // plot dates
                            LocalDateTime dateTime = LocalDateTime.ofEpochSecond(Long.parseLong(entry.split("-")[1]),0, ZoneOffset.UTC);
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d MMM");
                            String formattedDate = dateTime.format(formatter);
                            for (int p = 0; p < formattedDate.length(); p++) {
                                try {
                                    arrMatrix[arrMatrix.length - j + 1][i + p - 2] = String.valueOf(formattedDate.charAt(p));
                                } catch (ArrayIndexOutOfBoundsException e) {
                                    //arrMatrix[arrMatrix.length - j][i + 1 + p] = String.valueOf(entry.split("-")[0].charAt(p));
                                }
                            }
                            break;
                        }
                    }
                    break;
                }
            }
        }
    }

    public void showGraph(Patient patient) {
        // create the graph
        plotGraphAxis();
        plotter(patient.getBMILog());

        // draw graph
        for (String[] xline : arrMatrix) {
            System.out.println();
            for (String coord : xline) {
                System.out.print(coord);
            }
        }
    }
    public void animateGraph() {
        for (int i = 7; i < 40; i++) {
            Graph graph = new Graph(i,i * 4);
            graph.showGraph(Administration.currentPatient);
            System.out.println("\n\n"); i++;
            try { Thread.sleep(200); } catch (Exception e){};
        }

    }
}
