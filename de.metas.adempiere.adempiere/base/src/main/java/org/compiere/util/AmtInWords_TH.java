/******************************************************************************
 * The contents of this file are subject to the   Compiere License  Version 1.1
 * ("License"); You may not use this file except in compliance with the License
 * You may obtain a copy of the License at http://www.compiere.org/license.html
 * Software distributed under the License is distributed on an  "AS IS"  basis,
 * WITHOUT WARRANTY OF ANY KIND, either express or implied. See the License for
 * the specific language governing rights and limitations under the License.
 * The Original Code is                  Compiere  ERP & CRM  Business Solution
 * The Initial Developer of the Original Code is Jorg Janke  and ComPiere, Inc.
 * Portions created by Jorg Janke are Copyright (C) 1999-2005 Jorg Janke, parts
 * created by ComPiere are Copyright (C) ComPiere, Inc.;   All Rights Reserved.
 * Contributor(s): ______________________________________.
 *****************************************************************************/
package org.compiere.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */



/**
 *      Amount in Words for Thai
 *
 *  @author Sureeraya Limpaibul - http://www.rgagnon.com/javadetails/java-0426.h
tml
 *  @version $Id: AmtInWords_TH.java,v 1.2 2005/04/19 04:43:31 jjanke Exp $
 */
public class AmtInWords_TH implements AmtInWords
{

        /**
         *      AmtInWords_TH
		 *
		 *	0.23 = \u0e28\u0e39\u0e19\u0e22\u0e4c 23/100
		 *	1.23 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e1a\u0e32\u0e17 23/100
		 *	11.45 = \u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 45/100
		 *	121.45 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e23\u0e49\u0e2d\u0e22\u0e22\u0e35\u0e48\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 45/100
		 *	1231.56 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e1e\u0e31\u0e19\u0e2a\u0e2d\u0e07\u0e23\u0e49\u0e2d\u0e22\u0e2a\u0e32\u0e21\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 56/100
		 *	12341.78 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e2a\u0e2d\u0e07\u0e1e\u0e31\u0e19\u0e2a\u0e32\u0e21\u0e23\u0e49\u0e2d\u0e22\u0e2a\u0e35\u0e48\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 78/100
		 *	123451.89 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e41\u0e2a\u0e19\u0e2a\u0e2d\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e2a\u0e32\u0e21\u0e1e\u0e31\u0e19\u0e2a\u0e35\u0e48\u0e23\u0e49\u0e2d\u0e22\u0e2b\u0e49\u0e32\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 89/100
		 *	12234571.90 = \u0e2a\u0e34\u0e1a\u0e2a\u0e2d\u0e07\u0e25\u0e49\u0e32\u0e19 \u0e2a\u0e2d\u0e07\u0e41\u0e2a\u0e19\u0e2a\u0e32\u0e21\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e2a\u0e35\u0e48\u0e1e\u0e31\u0e19\u0e2b\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22\u0e40\u0e08\u0e47\u0e14\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 90/100
		 *	123234571.90 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e23\u0e49\u0e2d\u0e22\u0e22\u0e35\u0e48\u0e2a\u0e34\u0e1a\u0e2a\u0e32\u0e21\u0e25\u0e49\u0e32\u0e19 \u0e2a\u0e2d\u0e07\u0e41\u0e2a\u0e19\u0e2a\u0e32\u0e21\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e2a\u0e35\u0e48\u0e1e\u0e31\u0e19\u0e2b\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22\u0e40\u0e08\u0e47\u0e14\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 90/100
		 *	1987234571.90 = \u0e2b\u0e19\u0e36\u0e48\u0e07\u0e1e\u0e31\u0e19\u0e40\u0e01\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22\u0e41\u0e1b\u0e14\u0e2a\u0e34\u0e1a\u0e40\u0e08\u0e47\u0e14\u0e25\u0e49\u0e32\u0e19 \u0e2a\u0e2d\u0e07\u0e41\u0e2a\u0e19\u0e2a\u0e32\u0e21\u0e2b\u0e21\u0e37\u0e48\u0e19\u0e2a\u0e35\u0e48\u0e1e\u0e31\u0e19\u0e2b\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22\u0e40\u0e08\u0e47\u0e14\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14\u0e1a\u0e32\u0e17 90/100
		 *	0.00 = \u0e28\u0e39\u0e19\u0e22\u0e4c 00/100
		 *
         */
        public AmtInWords_TH ()
        {
                super ();
        }       //      AmtInWords_TH

        private static final String[]   majorNames      = {
                "",
                "\u0e25\u0e49\u0e32\u0e19 ",
                };
        private static final String[]   hundredThousandNames    = {
                "",
                "\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e41\u0e2a\u0e19",
                "\u0e2a\u0e2d\u0e07\u0e41\u0e2a\u0e19",
                "\u0e2a\u0e32\u0e21\u0e41\u0e2a\u0e19",
                "\u0e2a\u0e35\u0e48\u0e41\u0e2a\u0e19",
                "\u0e2b\u0e49\u0e32\u0e41\u0e2a\u0e19",
                "\u0e2b\u0e01\u0e41\u0e2a\u0e19",
                "\u0e40\u0e08\u0e47\u0e14\u0e41\u0e2a\u0e19",
                "\u0e41\u0e1b\u0e14\u0e41\u0e2a\u0e19",
                "\u0e40\u0e01\u0e49\u0e32\u0e41\u0e2a\u0e19"
        };

        private static final String[]   tenThousandNames        = {
                "",
                "\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e2a\u0e2d\u0e07\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e2a\u0e32\u0e21\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e2a\u0e35\u0e48\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e2b\u0e49\u0e32\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e2b\u0e01\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e40\u0e08\u0e47\u0e14\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e41\u0e1b\u0e14\u0e2b\u0e21\u0e37\u0e48\u0e19",
                "\u0e40\u0e01\u0e49\u0e32\u0e2b\u0e21\u0e37\u0e48\u0e19"
       };

       private static final String[]    thousandNames   = {
                "",
                "\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e1e\u0e31\u0e19",
                "\u0e2a\u0e2d\u0e07\u0e1e\u0e31\u0e19",
                "\u0e2a\u0e32\u0e21\u0e1e\u0e31\u0e19",
                "\u0e2a\u0e35\u0e48\u0e1e\u0e31\u0e19",
                "\u0e2b\u0e49\u0e32\u0e1e\u0e31\u0e19",
                "\u0e2b\u0e01\u0e1e\u0e31\u0e19",
                "\u0e40\u0e08\u0e47\u0e14\u0e1e\u0e31\u0e19",
                "\u0e41\u0e1b\u0e14\u0e1e\u0e31\u0e19",
                "\u0e40\u0e01\u0e49\u0e32\u0e1e\u0e31\u0e19"
       };

       private static final String[]    hundredNames    = {
                "",
                "\u0e2b\u0e19\u0e36\u0e48\u0e07\u0e23\u0e49\u0e2d\u0e22",
                "\u0e2a\u0e2d\u0e07\u0e23\u0e49\u0e2d\u0e22",
                "\u0e2a\u0e32\u0e21\u0e23\u0e49\u0e2d\u0e22",
                "\u0e2a\u0e35\u0e48\u0e23\u0e49\u0e2d\u0e22",
                "\u0e2b\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22",
                "\u0e2b\u0e01\u0e23\u0e49\u0e2d\u0e22",
                "\u0e40\u0e08\u0e47\u0e14\u0e23\u0e49\u0e2d\u0e22",
                "\u0e41\u0e1b\u0e14\u0e23\u0e49\u0e2d\u0e22",
                "\u0e40\u0e01\u0e49\u0e32\u0e23\u0e49\u0e2d\u0e22"
                };

       private static final String[]    tensNames       = {
                "",
                "\u0e2a\u0e34\u0e1a",
                "\u0e22\u0e35\u0e48\u0e2a\u0e34\u0e1a",
                "\u0e2a\u0e32\u0e21\u0e2a\u0e34\u0e1a",
                "\u0e2a\u0e35\u0e48\u0e2a\u0e34\u0e1a",
                "\u0e2b\u0e49\u0e32\u0e2a\u0e34\u0e1a",
                "\u0e2b\u0e01\u0e2a\u0e34\u0e1a",
                "\u0e40\u0e08\u0e47\u0e14\u0e2a\u0e34\u0e1a",
                "\u0e41\u0e1b\u0e14\u0e2a\u0e34\u0e1a",
                "\u0e40\u0e01\u0e49\u0e32\u0e2a\u0e34\u0e1a"
        };

        private static final String[]   numNames        = {
                "",
                "\u0e2b\u0e19\u0e36\u0e48\u0e07",
                "\u0e2a\u0e2d\u0e07",
                "\u0e2a\u0e32\u0e21",
                "\u0e2a\u0e35\u0e48",
                "\u0e2b\u0e49\u0e32",
                "\u0e2b\u0e01",
                "\u0e40\u0e08\u0e47\u0e14",
                "\u0e41\u0e1b\u0e14",
                "\u0e40\u0e01\u0e49\u0e32",
                "\u0e2a\u0e34\u0e1a",
                "\u0e2a\u0e34\u0e1a\u0e40\u0e2d\u0e47\u0e14",
                "\u0e2a\u0e34\u0e1a\u0e2a\u0e2d\u0e07",
                "\u0e2a\u0e34\u0e1a\u0e2a\u0e32\u0e21",
                "\u0e2a\u0e34\u0e1a\u0e2a\u0e35\u0e48",
                "\u0e2a\u0e34\u0e1a\u0e2b\u0e49\u0e32",
                "\u0e2a\u0e34\u0e1a\u0e2b\u0e01",
                "\u0e2a\u0e34\u0e1a\u0e40\u0e08\u0e47\u0e14",
                "\u0e2a\u0e34\u0e1a\u0e41\u0e1b\u0e14",
                "\u0e2a\u0e34\u0e1a\u0e40\u0e01\u0e49\u0e32"
                };

        /**
         *      Convert Less Than One Thousand
         *      @param number
         *      @return
         */
        private String convertLessThanOneMillion (int number)
        {
                String soFar = new String();
                // Esta dentro de los 1os. diecinueve?? ISCAP
                System.out.println("[convertLessThanOneMillion] number = " + number);
/*                if (number % 100 < 20)
                {
                        soFar = numNames[(number % 100)];
                        number /= 100;
                }
*/
                if (number != 0)
                {
                        soFar = numNames[number % 10];
                        if (number != 1 && soFar.equals("\u0e2b\u0e19\u0e36\u0e48\u0e07")) {
                              soFar = "\u0e40\u0e2d\u0e47\u0e14";
                        }
                        number /= 10;

                        soFar = tensNames[number % 10] + soFar;
                        number /= 10;

                        soFar = hundredNames[number % 10] + soFar;
                        number /= 10;

                        soFar = thousandNames[number % 10] + soFar;
                        number /= 10;

                        soFar = tenThousandNames[number % 10] + soFar;
                        number /= 10;

                        soFar = hundredThousandNames[number % 10] + soFar;
                        number /= 10;

                }
                if (number == 0) {
                    return soFar;
                }
                return numNames[number] + "\u0e23\u0e49\u0e2d\u0e22\u0e25\u0e49\u0e32\u0e19" + soFar;
        }       //      convertLessThanOneThousand

        /**
         *      Convert
         *      @param number
         *      @return
         */
        private String convert (int number)
        {
                /* special case */
                if (number == 0)
                {
                        return "\u0e28\u0e39\u0e19\u0e22\u0e4c";
                }
                String prefix = "";
                // \u0e1a\u0e32\u0e17
//                String subfix = "\u0e1a\u0e32\u0e17";

                if (number < 0)
                {
                        number = -number;
                        prefix = "\u0e25\u0e1a ";
                }
                String soFar = "";
                int place = 0;
                do
                {
                        double d =  number % 1000000;
                        int n =  (int) d;
                        if (n != 0)
                        {
                                String s = convertLessThanOneMillion (n);
                                place = place > 0 ? 1 : 0;

                                soFar = s + majorNames[place] + soFar;
                        }
                        place++;
                        number /= 1000000d;
                } while (number > 0);

                return (prefix + soFar).trim ();
//                return (prefix + soFar + subfix).trim ();
        }       //      convert


        /***********************************************************************
***
         *      Get Amount in Words
         *      @param amount numeric amount (352.80)
         *      @return amount in words (three*five*two 80/100)
         */
        public String getAmtInWords (String amount) throws Exception
        {
                if (amount == null)
                        return amount;
                //
                StringBuffer sb = new StringBuffer ();
                int pos = amount.lastIndexOf ('.');
                int pos2 = amount.lastIndexOf (',');
                if (pos2 > pos)
                        pos = pos2;
                String oldamt = amount;
                amount = amount.replaceAll (",", "");
                int newpos = amount.lastIndexOf ('.');
                if (newpos != -1) {
                    int pesos = Integer.parseInt(amount.substring(0, newpos));
                    System.out.println(pesos);
                    sb.append(convert(pesos)).append("\u0e1a\u0e32\u0e17");
                    for (int i = 0; i < oldamt.length(); i++) {
                        if (pos == i) { //        we are done
                            String cents = oldamt.substring(i + 1);
                            int stang = Integer.parseInt(cents);
                            if (stang != 0) {
                                sb.append(convert(stang)).append("\u0e2a\u0e15\u0e32\u0e07\u0e04\u0e4c");
                            } else {
                                sb.append("\u0e16\u0e49\u0e27\u0e19");
                            }
//                                sb.append (' ').append (cents).append ("/100");
                            break;
                        }
                    }
                } else {
                    int pesos = Integer.parseInt(amount);
                    sb.append(convert(pesos)).append("\u0e1a\u0e32\u0e17").append("\u0e16\u0e49\u0e27\u0e19");
                }
                return sb.toString ();
        }       //      getAmtInWords

        /***********************************************************************
***
         *      Get Amount in Words
         *      @param amount numeric amount (352.80)
         *      @return amount in words (three*five*two 80/100)
         */
        public String getAmtInWords (String amount, String iso) throws Exception
        {
                if (amount == null)
                        return amount;
                //
                StringBuffer sb = new StringBuffer ();
                int pos = amount.lastIndexOf ('.');
                int pos2 = amount.lastIndexOf (',');
                if (pos2 > pos)
                        pos = pos2;
                String oldamt = amount;
                amount = amount.replaceAll (",", "");
                int newpos = amount.lastIndexOf ('.');
                if (newpos != -1) {
                    int pesos = Integer.parseInt(amount.substring(0, newpos));
                    System.out.println(pesos);
                    if (iso.equals("THB")) {
                        sb.append(convert(pesos)).append("\u0e1a\u0e32\u0e17");
                        for (int i = 0; i < oldamt.length(); i++) {
                            if (pos == i) { //        we are done
                                String cents = oldamt.substring(i + 1);
                                int stang = Integer.parseInt(cents);
                                if (stang != 0) {
                                    sb.append(convert(stang)).append("\u0e2a\u0e15\u0e32\u0e07\u0e04\u0e4c");
                                } else {
                                    sb.append("\u0e16\u0e49\u0e27\u0e19");
                                }
//                                sb.append (' ').append (cents).append ("/100");
                                break;
                            }
                        }
                    } else {
                        sb.append(convert(pesos)).append("\u0e40\u0e2b\u0e23\u0e35\u0e22\u0e0d");
                        for (int i = 0; i < oldamt.length(); i++) {
                            if (pos == i) { //        we are done
                                String cents = oldamt.substring(i + 1);
                                int stang = Integer.parseInt(cents);
                                if (stang != 0) {
                                    sb.append(convert(stang)).append("\u0e40\u0e0b\u0e47\u0e19\u0e15\u0e4c").append( " [" +iso +"]");
                                }
                                break;
                            }
                        }
                    }
                } else {
                    int pesos = Integer.parseInt(amount);
                    if (iso.equals("THB")) {
                        sb.append(convert(pesos)).append("\u0e1a\u0e32\u0e17").
                                append("\u0e16\u0e49\u0e27\u0e19");
                    } else {
                        sb.append(convert(pesos)).append("\u0e40\u0e2b\u0e23\u0e35\u0e22\u0e0d").append( " [" +iso +"]");
                    }
                }
                return sb.toString ();
        }       //      getAmtInWords

        /**
         *      Test Print
         *      @param amt amount
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
        }       //      print
        /**
         *      Test Print
         *      @param amt amount
         */
        private void print (String amt, String currency)
        {
                try
                {
                        System.out.println(amt + " = " + getAmtInWords(amt));
                }
                catch (Exception e)
                {
                        e.printStackTrace();
                }
        }       //      print

        /**
         *      Test
         *      @param args ignored
         */
        public static void main (String[] args)
        {
                AmtInWords_TH aiw = new AmtInWords_TH();
        //      aiw.print (".23");      Error
//                aiw.print ("0.23");
//                aiw.print ("1.23");
//                aiw.print ("11.45");
//                aiw.print ("121.45");
                aiw.print ("3,026.00");
                aiw.print ("65341.78");
//                aiw.print ("123451.89");
//                aiw.print ("12234571.90");
//                aiw.print ("123234571.90");
//                aiw.print ("1,987,234,571.90");
//              aiw.print ("11123234571.90");
//              aiw.print ("123123234571.90");
//              aiw.print ("2123123234571.90");
//              aiw.print ("23,123,123,234,571.90");
//              aiw.print ("100,000,000,000,000.90");
//                aiw.print ("0.00");
        }       //      main

}       //      AmtInWords_TH
