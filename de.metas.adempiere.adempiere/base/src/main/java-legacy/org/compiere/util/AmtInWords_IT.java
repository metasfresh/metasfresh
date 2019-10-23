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
 *	Italian Amount in Words
 *	
 *  @author Jorg Janke - http://www.rgagnon.com/javadetails/java-0426.html
 *  @translator Angelo Dabala' (genied) - nectosoft - Italian localization - www.nectosoft.it
 *  @version $Id: AmtInWords_IT.java,v 1.3 2006/07/30 00:54:36 jjanke Exp $
 */
public class AmtInWords_IT implements AmtInWords
{
	/**
	 * 	AmtInWords_IT
	 */
	public AmtInWords_IT ()
	{
		super ();
	} //	AmtInWords_IT

	private static final String[]	majorNames	= {
		"", 
		"MILLE", 		// 10^3
		"UNMILIONE",	// 10^6
		"UNMILIARDO", 	// 10^9
		"UNBILIONE", 	// 10^12
		"UNBILIARDO", 	// 10^15
		"UNTRILIONE"  	// 10^18
		};

	private static final String[]	majorNamesPlural	= {
		"", 
		"MILA", 		// 10^3
		"MILIONI",		// 10^6
		"MILIARDI", 	// 10^9
		"BILIONI", 		// 10^12
		"BILIARDI", 	// 10^15
		"TRILIONI"  	// 10^18
		};

	private static final String[]	tensNames	= { 
		"", 
		"DIECI", 
		"VENTI",
		"TRENTA", 
		"QUARANTA", 
		"CINQUANTA", 
		"SESSANTA", 
		"SETTANTA",
		"OTTANTA", 
		"NOVANTA"
		};

	private static final String[]	numNames	= { 
		"", 
		"UNO",
		"DUE",
		"TRE", 
		"QUATTRO", 
		"CINQUE", 
		"SEI", 
		"SETTE", 
		"OTTO", 
		"NOVE",
		"DIECI", 
		"UNDICI", 
		"DODICI", 
		"TREDICI", 
		"QUATTORDICI", 
		"QUINDICI",
		"SEDICI", 
		"DICIASSETTE", 
		"DICIOTTO", 
		"DICIANNOVE"
		};

	/**
	 * 	Convert Less Than One Thousand
	 *	@param number
	 *	@return amt
	 */
	private String convertLessThanOneThousand (int number)
	{
		int unit = 0;
		int tens = 0;
		String soFar;
		//  Sotto 20
		if (number % 100 < 20)
		{
			soFar = numNames[number % 100];
			number /= 100;
		}
		else
		{
			unit = number % 10;
			soFar = numNames[unit];
			number /= 10;
			// 
			tens = number % 10;
			// Uno e Otto iniziano con una vocale, quindi elido la finale dalle decine es. TRENTAUNO->TRENTUNO
			if (unit == 1 || unit == 8)
				soFar = tensNames[tens].substring(0, tensNames[tens].length()-1) + soFar;
			else
				soFar = tensNames[tens] + soFar;
			number /= 10;
		}
		if (number == 0)
			return soFar;
		// Sopra 200
		if (number > 1)
			return numNames[number] + "CENTO" + soFar;
		// Tra 100 e 199
		else
			return "CENTO" + soFar;
	}	//	convertLessThanOneThousand

	/**
	 * 	Convert
	 *	@param number
	 *	@return amt
	 */
	private String convert (int number)
	{
		/* special case */
		if (number == 0)
			return "ZERO";
		String prefix = "";
		if (number < 0)
		{
			number = -number;
			prefix = "MENO ";
		}
		String soFar = "";
		int place = 0;
		do
		{
			long n = number % 1000;
			if (n != 0)
			{
				String s = convertLessThanOneThousand ((int)n);
				if (n == 1)
					soFar = majorNames[place] + soFar;
				else
					soFar = s + majorNamesPlural[place] + soFar;
					
			}
			place++;
			number /= 1000;
		}
		while (number > 0);
		return (prefix + soFar).trim ();
	}	//	convert

	
	/**************************************************************************
	 * 	Get Amount in Words
	 * 	@param amount numeric amount (352.80 or 352,80)
	 * 	@return amount in words (TRECENTOCINQUANTADUE/80)
	 * 	@throws Exception
	 */
	public String getAmtInWords (String amount) throws Exception
	{
		if (amount == null)
			return amount;
		//
		StringBuffer sb = new StringBuffer ();
		int pos = amount.lastIndexOf (',');  		
		int pos2 = amount.lastIndexOf ('.');
		if (pos2 > pos)
			pos = pos2;
		String oldamt = amount;

		amount = amount.replaceAll( "\\.","");

		int newpos = amount.lastIndexOf (',');
		int amt = Integer.parseInt (amount.substring (0, newpos));
		sb.append (convert (amt));
		for (int i = 0; i < oldamt.length (); i++)
		{
			if (pos == i) //	we are done
			{
				String cents = oldamt.substring (i + 1);
				sb.append ("/").append (cents);
				break;
			}
		}
		return sb.toString ();
	}	//	getAmtInWords

	public static void main(String[] args) throws Exception {
		AmtInWords_IT aiw = new AmtInWords_IT();
		System.out.println(aiw.getAmtInWords("0,00"));
		System.out.println(aiw.getAmtInWords("1000,00"));
		System.out.println(aiw.getAmtInWords("14000,99"));
		System.out.println(aiw.getAmtInWords("28000000,99"));
		System.out.println(aiw.getAmtInWords("301000000,00"));
		System.out.println(aiw.getAmtInWords("200000,99"));
		System.out.println(aiw.getAmtInWords("-1234567890,99"));
		System.out.println(aiw.getAmtInWords("2147483647,99"));
	}
	
}	//	AmtInWords_IT
