/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2006 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.util;


/**
 *Amount in Words for Bulgarian
 *        
 *@author Kostadin Mitev -The class is based on the AmtInWords_EN.java written by jjanke
 *@version $Id: AmtInWords_BG.java,v 0.1 2007/12/21 $
 *@version $Id: v, 0.2 2008/06/10 -Simplified. Changed to work with both a dot and a full stop as a separator and without a separator.
 */
public class AmtInWords_BG implements AmtInWords
{
        /**
         *         AmtInWords_BG
         */
        public AmtInWords_BG ()
        {
                super ();
        }       
        
        private static final String zero = "\u041D\u0443\u043B\u0430";     // "Nula";
        private static final String negative = "\u041D\u0443\u043B\u0430"; // "minus";
        private static final String concat = "\u0438";                     // "i";
        private static final String lev[] = {"\u043B\u0435\u0432","\u043B\u0435\u0432\u0430"};  // {"lev","leva"};
        /** Thousands plus                                */
        private static final String[][]        majorNames        = {
                {"",""}, 
                {"\u0425\u0438\u043B\u044F\u0434\u0430","\u0425\u0438\u043B\u044F\u0434\u0438"},              //{"Hiliada","Hiliadi"}, 
                {"\u041C\u0438\u043B\u0438\u043E\u043D","\u041C\u0438\u043B\u0438\u043E\u043D\u0430"},        //{"Million","Miliona"},
                {"\u041C\u0438\u043B\u0438\u0430\u0440\u0434","\u041C\u0438\u043B\u0438\u0430\u0440\u0434\u0430"},  //{"Miliard","Miliarda"}, 
                {"\u0422\u0440\u0438\u043B\u0438\u043E\u043D","\u0422\u0440\u0438\u043B\u0438\u043E\u043D\u0430"},  //{"Trilion","Triliona"}, 
                {"\u041A\u0432\u0430\u0434\u0440\u0430\u043B\u0438\u043E\u043D","\u041A\u0432\u0430\u0434\u0440\u0430\u043B\u0438\u043E\u043D\u0430"},  //{"Quadrilion","Quadriliona"},
                {"\u041A\u0432\u0438\u043D\u0442\u0438\u043B\u0438\u043E\u043D","\u041A\u0432\u0438\u043D\u0442\u0438\u043B\u0438\u043E\u043D\u0430"}   //{"Quintillion","Quintillion"}
        };

        /** Ten to Ninety                                */
        private static final String[]        tensNames        = { 
                "", 
                "\u0414\u0435\u0441\u0435\u0442",                // "Deset", 
                "\u0414\u0432\u0430\u0434\u0435\u0441\u0435\u0442",   // "Dvadeset",
                "\u0422\u0440\u0438\u0434\u0435\u0441\u0435\u0442",   // "Trideset", 
                "\u0427\u0435\u0442\u0438\u0440\u0438\u0434\u0435\u0441\u0435\u0442", //"Chetirdeset", 
                "\u041F\u0435\u0442\u0434\u0435\u0441\u0435\u0442",                   // "Petdeset", 
                "\u0428\u0435\u0441\u0442\u0434\u0435\u0441\u0435\u0442",             // "Shestdeset", 
                "\u0421\u0435\u0434\u0435\u043C\u0434\u0435\u0441\u0435\u0442",       // "Sedemdeset",
                "\u041E\u0441\u0435\u043C\u0434\u0435\u0441\u0435\u0442",             // "Osemdeset", 
                "\u0414\u0435\u0432\u0435\u0442\u0434\u0435\u0441\u0435\u0442"        // "Devetdeset"
        };
        
        /* 100-1000 */
        private static final String [] hundredNames = {
                "",
                "\u0421\u0442\u043E",                				   // "Sto",
                "\u0414\u0432\u0435\u0441\u0442\u0430",                // "Dvesta",
                "\u0422\u0440\u0438\u0441\u0442\u0430",                // "Trista",
                "\u0427\u0435\u0442\u0438\u0440\u0438\u0441\u0442\u043E\u0442\u0438\u043D",   //"Chetiristotin",
                "\u041F\u0435\u0442\u0441\u0442\u043E\u0442\u0438\u043D",                     //"Petstotin",
                "\u0428\u0435\u0441\u0442\u0441\u0442\u043E\u0442\u0438\u043D",               //"Sheststotin",
                "\u0421\u0435\u0434\u0435\u043C\u0441\u0442\u043E\u0442\u0438\u043D",         //"Sedemstotin",
                "\u041E\u0441\u0435\u043C\u0441\u0442\u043E\u0442\u0438\u043D",               // "Osemstotin",
                "\u0414\u0435\u0432\u0435\u0442\u0441\u0442\u043E\u0442\u0438\u043D"          //"Devetstotin"
        };
        
        /** numbers to 19                                */
        private static final String[][]        numNames        = { 
                {"",""}, 
                {"\u0415\u0434\u0438\u043D","\u0415\u0434\u043D\u0430"},    // {"Edin","Edna"}, 
                {"\u0414\u0432\u0430","\u0414\u0432\u0435"},                // {"Dva","Dve"},
                {"\u0422\u0440\u0438","\u0422\u0440\u0438"},                // {"Tri","Tri"}, 
                {"\u0427\u0435\u0442\u0438\u0440\u0438","\u0427\u0435\u0442\u0438\u0440\u0438"},   //{"Chetiri","Chetiri"}, 
                {"\u041F\u0435\u0442","\u041F\u0435\u0442"},                					   // {"Pet","Pet"}, 
                {"\u0428\u0435\u0441\u0442","\u0428\u0435\u0441\u0442"},                           // {"Shest","Shest"}, 
                {"\u0421\u0435\u0434\u0435\u043C","\u0421\u0435\u0434\u0435\u043C"},               //{"Sedem","Sedem"}, 
                {"\u041E\u0441\u0435\u043C","\u041E\u0441\u0435\u043C"},                           // {"Osem","Osem"}, 
                {"\u0414\u0435\u0432\u0435\u0442","\u0414\u0435\u0432\u0435\u0442"},                                                                                        //{"Devet","Devet"},
                {"\u0414\u0435\u0441\u0435\u0442","\u0414\u0435\u0441\u0435\u0442"},                                                                                        //{"Deset","Deset"}, 
                {"\u0415\u0434\u0438\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0415\u0434\u0438\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                            //{"Edinadeset","Edinadeset"}, 
                {"\u0414\u0432\u0430\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0414\u0432\u0430\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                            //{"Dvanadeset","Dvanadeset"}, 
                {"\u0422\u0440\u0438\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0422\u0440\u0438\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                            //{"Trinadeset","Trinadeset"}, 
                {"\u0427\u0435\u0442\u0438\u0440\u0438\u043D\u0430\u0434\u0435\u0441\u0442","\u0427\u0435\u0442\u0438\u0440\u0438\u043D\u0430\u0434\u0435\u0441\u0442"},    //{"Cheterinadeset","Cheterinadeset"}, 
                {"\u041F\u0435\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u041F\u0435\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                            //{"Petnadeset","Petnadeset"},
                {"\u0428\u0435\u0441\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0428\u0435\u0441\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                //{"Shestnadeset","Shestnadeset"}, 
                {"\u0421\u0435\u0434\u0435\u043C\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0421\u0435\u0434\u0435\u043C\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},    //{"Sedemnadeset","Sedemnadeset"}, 
                {"\u041E\u0441\u0435\u043C\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u041E\u0441\u0435\u043C\u043D\u0430\u0434\u0435\u0441\u0435\u0442"},                //{"Osemnadeset","Osemnadeset"}, 
                {"\u0414\u0435\u0432\u0435\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442","\u0414\u0435\u0432\u0435\u0442\u043D\u0430\u0434\u0435\u0441\u0435\u0442"}     //{"Devetnadest","Devetnadest"}
        };

        /**
         *Convert Less Than One Thousand
         *@param number
         *@return amt
         */
        private String convertLessThanOneThousand (int number,int w)
        {
                String soFar;
                //Below 20
                if (number % 100 < 20)
                {
                        soFar = numNames[number % 100][w];
                        number /= 100;
                }
                else
                {
                        soFar = numNames[number % 10][w];
                        number /= 10;
                        if(soFar.compareTo("") == 0){
                                soFar = tensNames[number % 10];
                        } else {
                                soFar = tensNames[number % 10] + " " + concat +  " " + soFar;
                        }
                        number /= 10;
                }
                if (number == 0)
                        return soFar;
                return hundredNames[number] + " " + soFar;
        }        //convertLessThanOneThousand

        /**
         *Convert
         *@param number
         *@return amt
         */
        private String convert (long number)
        {        
                /* special case */
                if (number == 0)
                {
                        return zero;
                }
                String prefix = "";
                if (number < 0)
                {
                        number = -number;
                        prefix = negative + " ";
                }
                String soFar = "";
                int place = 0;
                do
                {
                        long n = number % 1000;
                        if (n != 0)
                        {
                                String s = convertLessThanOneThousand ((int)n,place == 1? 1:0);
                                if((place == 1) && (s.compareTo(numNames[1][1]) == 0)){
                                        soFar = " " + majorNames[place][0] + " " + soFar;
                                } else {
                                        soFar = s + " " + majorNames[place][n > 1?1:0] + " " + soFar;
                                }
                        }
                        place++;
                        number /= 1000;
                }
                while (number > 0);
                return (prefix + soFar).trim ();
        }        //convert

        
        /**************************************************************************
         *Get Amount in Words
         *@param amount numeric amount (352.80)
         *@return amount in words (three*five*two 80/100)
         *@throws Exception
         */
        public String getAmtInWords (String amount) throws Exception
        {
                if (amount == null)
                        return amount;
 
                StringBuffer sb = new StringBuffer ();
                amount = amount.replaceAll (" ", "").replaceAll("\u00A0", "");
                char sep = amount.contains(",")?',':'.'; //Try to determine the separator either comma or a full stop       
                int pos = amount.lastIndexOf (sep);
                long levs = Long.parseLong((pos >=0)?amount.substring (0, pos):amount);
                sb.append (convert (levs) + " " + lev[levs ==1?0:1]);
                if(pos > 0) {
                	String stotinki = amount.substring (pos + 1);
                	 if(stotinki.length() > 2){
                     	stotinki = stotinki.substring(0,2);
                     }
                     sb.append (" " + concat + " ")
                       .append (convert(Long.parseLong(stotinki)))
                       .append (" \u0441\u0442\u043E\u0442\u0438\u043D\u043A\u0438"); //stotinki"
                }
                return sb.toString ();
        }        //getAmtInWords

        /**
         *Test Print
         *@param amt amount
         */
        private void print (String amt)
        {
                try
                {
                        System.out.println(amt + " = " + getAmtInWords(amt));
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }        //print
        
        /**
         *Test
         *@param args ignored
         */
        public static void main (String[] args)
        {
                AmtInWords_BG aiw = new AmtInWords_BG();
                aiw.print("0.23");
                aiw.print("23");
                aiw.print ("0,23");
                aiw.print ("1,23");
                aiw.print ("12,345");
                aiw.print ("123,45");
                aiw.print ("1 234,56");
                aiw.print ("12 345,78");
                aiw.print ("123 457,89");
                aiw.print ("1 234 578,90");
                aiw.print("10,00");
                aiw.print("50,00");
                aiw.print("100,00");
                aiw.print("300,00");
                aiw.print("1 000,00");
                aiw.print("3 000,00");
                aiw.print("10 000,00");
                aiw.print("1 000 000,00");
                aiw.print("100 000 000,00");
                aiw.print("100 000 000 000 000 0000,00");
                
        }        //main
        
}        //AmtInWords_BG