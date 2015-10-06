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
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/

package org.compiere.util;

/**
 *	Amount in Words for Bahasa Indonesia
 *	
 *  Bugs item #1569711: remove hard-coded "Rupiah" to support all currency
 *  Contributor: Armen Rizal (www.goodwill.co.id)
 *  
 *  @author Halim Englen
 *  @version $Id: AmtInWords_IN.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 */
public class AmtInWords_IN implements AmtInWords
{
	/**
	 * 	AmtInWords_IN
	 */
	public AmtInWords_IN ()
	{
		super ();
	}	//	AmtInWords_IN	

    private static final String[] basenumbers = new String[]{
        "Nol",
        "Satu",
        "Dua",
        "Tiga",
        "Empat",
        "Lima",
        "Enam",
        "Tujuh",
        "Delapan",
        "Sembilan",
        "Sepuluh",
        "Sebelas",
  };
  
  private static final long POWER_THREE = 1000L;
  private static final long POWER_SIX = 1000000L;
  private static final long POWER_NINE = 1000000000L;
  private static final long POWER_TWELVE = 1000000000000L;
  private static final long POWER_FIFTEEN = 1000000000000000L;
  
	/**	Static Logger				*/
	private static CLogger	s_log	= CLogger.getCLogger (AmtInWords_IN.class);

//-------------------------- STATIC METHODS --------------------------

  /**
  * Convenient method for {@link #sayNumber(StringBuffer, long)}.
  *
  * @param number number to say
  * @return said number
  */
  public static String sayNumber(double number) {
        StringBuffer result = new StringBuffer();
        sayNumber(result, number);
        //result.append(" Rupiah");
        return result.toString();
  }

  /**
   * Say a number. This method will append the result to the given string buffer.
   *
   * @param appendTo the string buffer
   * @param number   number to say
   * @return said number
   * @throws IllegalArgumentException if the number equals to {@link Long#MIN_VALUE}
   */
  public static String sayNumber(StringBuffer appendTo, double number)
  throws IllegalArgumentException {
	  if (number == Double.MIN_VALUE) {
		  throw new IllegalArgumentException("Out of range");
	  }
	  if (number < 0) {
		  appendTo.append("Minus ");
	  }
	  double abs = Math.abs(number);
	  // s_log.warning("Debug=" + abs);
	  if (abs < POWER_THREE) {
		  saySimpleNumber(appendTo, (int) abs);
	  } else if (abs < 2000) {
		  int thousand = (int) (abs % POWER_THREE);
		  appendTo.append("Seribu ");
		  saySimpleNumber(appendTo, thousand);
	  } else if (abs < POWER_SIX) {
		  int thousand = (int) (abs % POWER_SIX / POWER_THREE);
		  saySimpleNumber(appendTo, thousand);
		  appendTo.append(" Ribu");
		  double remainder = abs - thousand * POWER_THREE;
		  if (remainder > 0) {
			  appendTo.append(' ');
			  sayNumber(appendTo, remainder);
		  }
	  } else if (abs < POWER_NINE) {
		  int million = (int) (abs % POWER_NINE / POWER_SIX);
		  saySimpleNumber(appendTo, million);
		  appendTo.append(" Juta");
		  double remainder = abs - million * POWER_SIX;
		  if (remainder > 0) {
			  appendTo.append(' ');
			  sayNumber(appendTo, remainder);
		  }
	  } else if (abs < POWER_TWELVE) {
		  int billion = (int) (abs % POWER_TWELVE / POWER_NINE);
		  saySimpleNumber(appendTo, billion);
		  appendTo.append(" Milyar");
		  double remainder = abs - billion * POWER_NINE;
		  if (remainder > 0) {
			  appendTo.append(' ');
			  sayNumber(appendTo, remainder);
		  }
	  } else if (abs < POWER_FIFTEEN) {
		  int trillion = (int) (abs % POWER_FIFTEEN / POWER_TWELVE);
		  saySimpleNumber(appendTo, trillion);
		  appendTo.append(" Trilyun");
		  double remainder = abs - trillion * POWER_TWELVE;
		  if (remainder > 0) {
			  appendTo.append(' ');
			  sayNumber(appendTo, remainder);
		  }
	  } else {
		  appendTo.append("Lebih Dari Seribu Triliun");
	  }
	  return appendTo.toString();
  }

   
   
   static void saySimpleNumber(StringBuffer appendTo, int number) {
	     
		 
         assert number < 1000 && number >= 0: "Must be between 0 and 1000";
		 
         if (number < 12) {
               assert number < 12 && number >= 0: "Must be between 0 and 11";
               appendTo.append(basenumbers[number]);
         } else if (number < 20) {
               assert number >= 12 && number <= 19: "Must be between 12 and 19";
               int belasan = number % 10;
               appendTo.append(basenumbers[belasan]);
               appendTo.append(" Belas");
         } else if (number < 100) {
               assert number >= 20 && number <= 99: "Must be between 20 and 99";
               int puluhan = number / 10;
               appendTo.append(basenumbers[puluhan]);
               appendTo.append(" Puluh");
               int remainder = number - puluhan * 10;
               if (remainder > 0) {
                     appendTo.append(' ');
                     saySimpleNumber(appendTo, remainder);
               }
         } else if (number < 200) {
               assert number >= 100 && number <= 199: "Must be between 100 and 199";
               appendTo.append("Seratus");

               int remainder = number - 100;
               if (remainder > 0) {
                     appendTo.append(' ');
                     saySimpleNumber(appendTo, remainder);
               }
         } else if (number != 0) {
               assert number >= 200 && number <= 999: "Must be between 200 and 999";

               int ratusan = number % 1000 / 100;
               assert ratusan > 0 && ratusan < 10 :"1-9";
               appendTo.append(basenumbers[ratusan]);
               appendTo.append(" Ratus");
               int remainder = number - ratusan * 100;
               if (remainder > 0) {
                     appendTo.append(' ');
                     saySimpleNumber(appendTo, remainder);
               }
         }          
   }
  
	
	/**************************************************************************
	 * 	Get Amount in Words
	 * 	@param amount numeric amount (352.80)
	 * 	@return amount in words (three*five*two 80/100)
	 */
	public String getAmtInWords (String amount) throws Exception
	{
		if (amount == null)
			return amount;
		//
		StringBuffer result = new StringBuffer();
		
		int pos = amount.lastIndexOf ('.');
		String oldamt = amount;
		amount = amount.replaceAll (",", "");
		String cents = pos > 0 ? oldamt.substring (pos + 1):null;
		double numDouble = Double.parseDouble(amount);
		sayNumber(result, numDouble);
		if (cents != null)
		{	
			result.append(" Koma ");
			numDouble = Double.parseDouble(cents);
			sayNumber(result, numDouble);
		}
		//result.append(" Rupiah");
		return result.toString();
	}	//	getAmtInWords

	/**
	 * 	Test Print
	 *	@param amt amount
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
	}	//	print
	
	/**
	 * 	Test
	 *	@param args ignored
	 */
	public static void main (String[] args)
	{
		AmtInWords_IN aiw = new AmtInWords_IN();
        aiw.print ("0.00");		
        aiw.print ("0.23");
        aiw.print ("1.23876787");
        aiw.print ("11.45");
        aiw.print ("121.45");
        aiw.print ("1231.56");
        aiw.print ("10341.78");
        aiw.print ("12341.78");
        aiw.print ("123451.89");
        aiw.print ("12234571.90");
        aiw.print ("123234571.90");
        aiw.print ("1987234571.90");
        aiw.print ("11123234571.90");
        aiw.print ("123123234571.90");
        aiw.print ("2123123234571.90");
        aiw.print ("23,123,123,234,571.90");
        aiw.print ("100,000,000,000,000.90");
        aiw.print ("111,111,111");
        aiw.print ("222,222,222,222,222");
        aiw.print ("222,222,222,222,222,222,222");        
	}	//	main
	
}	//	AmtInWords_IN
