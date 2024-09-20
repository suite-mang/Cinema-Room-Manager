package cinema;
import java.util.Scanner;

public class Cinema {
    static Scanner scanner = new Scanner(System.in);
    static int state;

    static int currentIncome= 0 ;
    static int purchasedTickets = 0;

    public static void main(String[] args) {
        // Write your code here

        String option = """
                1. Show the seats
                2. Buy a ticket
                3. Statistics
                0. Exit
                """;

        String[][] seats;
        state = 1;


        System.out.println("Enter the number of rows:");
        int row = scanner.nextInt() ;
        System.out.println("Enter the number of seats in each row");
        int seatInEachRow = scanner.nextInt();
        seats = seatPlan(row,seatInEachRow);

        while (!isWorking()) {
            System.out.print("\n" + option);
            int instruction = scanner.nextInt();
            switch (instruction) {  
                case 1:
                    printSeats(seats);
                    break;
                case 2:
                    seats =buyTicket(seats);
                    break;
                case 3:
                     statistics(seats);
                    break;
                case 0:
                    state = 0;
                    break;
            }
        }

        scanner.close();

    }
   static boolean isWorking(){

        return  state != 1;
    }
    private static String[][] seatPlan(int row, int seatInEachRow){
        String[][] seats= new String[row + 1][seatInEachRow + 1];
        for  (int rowIndex = 0; rowIndex < seats.length; rowIndex++) {
            for (int column = 0; column  < seats[rowIndex].length; column ++) {
                if  (rowIndex == 0 && column == 0) {
                    seats[rowIndex][column] =" ";
                }else if (rowIndex == 0) {
                    seats[rowIndex][column] = String.valueOf(column);
                } else if (column == 0) {
                    seats[rowIndex][column] = String.valueOf(rowIndex);
                } else{
                    seats[rowIndex][column] ="S";
                }
            }
        }
        return seats;
    }

    private static void  printSeats(String[][] seats) {

        System.out.println("\nCinema:");
        for (String[] seat : seats) {
            for (int column = 0; column < seats[0].length; column++) {
                System.out.print(seat[column]);
                if (column != seats[0].length - 1) {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    private  static String[][] buyTicket(String[][] seats) {

        int normalSeatsCount= 60;
        int totalRow =seats.length-1;
        int totalColumn =seats[0].length-1;
        int totalSeat = totalRow * totalColumn;
        boolean isError = false;
        int rowNumber = 0;
        int seatNumber = 0;

        while(!isError) {


            System.out.println("\nEnter a row number:");
            rowNumber = scanner.nextInt();
            System.out.println("Enter a seat number in that row:");
            seatNumber = scanner.nextInt();
            isError = true;
            try{
                if ( seats[rowNumber][seatNumber].equals("B")){
                    isError = false;
                    System.out.println("\nThat ticket has already been purchased!");
                }
            } catch (ArrayIndexOutOfBoundsException e){
                System.out.println("\nWrong input!");
                isError = false;
            }
        }

        if (totalSeat < normalSeatsCount){
            currentIncome += 10;
            System.out.println("\nTicket price: $10");
        } else if (totalSeat > normalSeatsCount) {
            if(totalRow % 2 ==0){


                if (rowNumber <= totalSeat/2){
                    currentIncome += 10;
                    System.out.println("\nTicket price: $10");
                }else{
                    currentIncome += 8;
                    System.out.println("\nTicket price: $8");
                }
            }else{

                int frontSeat=(totalRow - 1)/2;
                if (rowNumber <= frontSeat){
                    currentIncome += 10;
                     System.out.println("\nTicket price: $10");
                }else{
                    currentIncome += 8;
                    System.out.println("\nTicket price: $8");
                }
            }

        }
        purchasedTickets++;
        seats[rowNumber][seatNumber] = "B";
        return seats;
    }
    private static void statistics(String[][] seats) {
        int totalRow = seats.length - 1;
        int totalColumn = seats[0].length - 1;
        int totalIncome = getTotalIncome(totalRow, totalColumn);

        double percent= 100 * (  (double) purchasedTickets/ (totalRow * totalColumn));
        String percentage = String.format("%.2f",percent);
        System.out.println("\nNumber of purchased tickets: " + purchasedTickets);
        System.out.println("Percentage: "+ percentage + "%");
        System.out.println("Current income: $"+ currentIncome);
        System.out.println("Total income: $"+ totalIncome);
    }

    private static int getTotalIncome(int totalRow, int totalColumn) {
        int totalSeat = totalRow * totalColumn;
        int normalSeatsCount = 60;
        int totalIncome = 0;
        if (totalSeat < normalSeatsCount) {
            totalIncome += totalSeat * 10;
        } else if (totalSeat > normalSeatsCount) {
            if (totalRow % 2 == 0) {
                int totalFrontSeat = totalColumn * (totalRow /2);
                totalIncome += totalFrontSeat* 10 + totalFrontSeat* 8;
            } else {
                int frontSeat = (totalRow - 1) / 2;
                totalIncome += (totalColumn * frontSeat * 10) + ((totalColumn * (frontSeat + 1) )* 8);
            }
        }
        return totalIncome;
    }


}