package org.countryinfo;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Country {

    public static void main(String[] args) {

        Boolean Running = true;

        System.out.println("Welcome to the Country info data app");
        System.out.println("\t");
        System.out.println("This app allows you to view a list countries and currencies");
        System.out.println("\t");
        do{
            Scanner sc =new Scanner(System.in);
            System.out.println("To view the list of different data enter");
            try {
                int data = dataOptions(sc);
                if (data==1)  {
                    Utils.getCountryList();
                    System.out.println("\t");
                }
                else if(data==2) {
                    Utils.getCurrencyList();
                    System.out.println("\t");
                }
                else if(data==3){
                    Running = false;
                }
                else {
                    System.out.println("Please specify a number 1-3 to choose what data you want to display ");
                    data = dataOptions(sc);
                }

                System.out.println("Would you like to check more data on the sports app?");
                System.out.println("Enter any integer to continue or 55 to end the program");
                if(sc.nextInt() == 55){
                    Running = false;
                }
                else
                {
                    Running = true;
                }
            }catch (InputMismatchException ex) {
                System.out.println("Invalid input! You have to enter a number");
            }
        }while(Running);
        System.out.println("Thank you. You've successfully completed the program");
        System.out.println("\t");
        System.out.println("You may close the session");
        }
    private static int dataOptions(Scanner sc) {
        int data;
        System.out.println("1.Countries");
        System.out.println("2.Currencies");

        System.out.println("3.To end the program\t");
        data = sc.nextInt();
        return data;
    }
    }

