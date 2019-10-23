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
 * Amount in Words for French
 * 
 * @author Jorg Janke - http://www.rgagnon.com/javadetails/java-0426.html
 * @version $Id: AmtInWords_FR.java,v 1.3 2006/07/30 00:54:35 jjanke Exp $
 */
public class AmtInWords_FR implements AmtInWords
{

	/**
	 * AmtInWords_FR
	 */
	public AmtInWords_FR ()
	{
		super ();
	}	// AmtInWords_FR

	private static final String[]	majorNames	= {
		"", 
		" mille", 
		" million",
		" milliard", 
		" trillion", 
		" quadrillion", 
		" quintillion"
		};

	private static final String[]	tensNames	= { 
		"", 
		" dix", 
		" vingt",
		" trente", 
		" quarante", 
		" cinquante", 
		" soixante", 
		" soixante-dix",
		" quatre-vingt", 
		" quatre-vingt-dix"	
		};

	private static final String[]	numNames	= { 
		"", 
		" un", 
		" deux",
		" trois", 
		" quatre", 
		" cinq", 
		" six", 
		" sept", 
		" huit", 
		" neuf",
		" dix", 
		" onze", 
		" douze", 
		" treize", 
		" quatorze", 
		" quinze", 
		" seize",
		" dix-sept", 
		" dix-huit", 
		" dix-neuf"	
		};

	/**
	 * Convert Less Than One Thousand
	 * @param number number
	 * @return string
	 */
	private String convertLessThanOneThousand (int number)
	{
		String soFar;
		if (number % 100 < 20)
		{
			// 19 et moins
			soFar = numNames[number % 100];
			number /= 100;
		}
		else if ((number % 100 < 80) && (number % 100 > 70))
		{
			// 71 ->79 (soixante et onze )
			soFar = numNames[(number-60) % 100];
			number /= 10;
			soFar = tensNames[ (number-1) % 10] + soFar;
			number /= 10;	
		}
		else if ((number % 100 < 100) && (number % 100 > 90))
		{
			// 91->99
			soFar = numNames[(number-80) % 100];
			number /= 10;
			soFar = tensNames[ (number-1) % 10] + soFar;
			number /= 10;	
						
		}
		
		else
		{
			// 9 et moins
			soFar = numNames[number % 10];
			number /= 10;
			// 90, 80, ... 20
			soFar = tensNames[number % 10] + soFar;
			number /= 10;
		}
		// reste les centaines
		// y'en a pas
		if (number == 0)
			return soFar;
		if (number == 1)
			// on ne retourne "un cent xxxx" mais "cent xxxx"
			return " cent" + soFar;
		else
			return numNames[number] + " cent" + soFar;
	}	//	convertLessThanOneThousand

	/**
	 * Convert
	 * @param number number
	 * @return string
	 */
	private String convert (int number)
	{
		if (number == 0)
			return "zero";
		String prefix = "";
		if (number < 0)
		{
			number = -number;
			prefix = "moins";
		}
		String soFar = "";
		int place = 0;
		boolean pluralPossible = true;
		boolean pluralForm = false;
		do
		{
			int n = number % 1000;
			// par tranche de 1000
			if (n != 0)
			{
				String s = convertLessThanOneThousand (n);
				if (s.trim ().equals ("un") && place == 1)
				{
					// on donne pas le un pour mille
					soFar = majorNames[place] + soFar;
				}
				else
				{
					if (place == 0)
					{
						if (s.trim ().endsWith ("cent")
							&& !s.trim ().startsWith ("cent"))
						{
							// nnn200 ... nnn900 avec "s"
							pluralForm = true;
						}
						else
						{
							// pas de "s" jamais
							pluralPossible = false;
						}
					}
					if (place > 0 && pluralPossible)
					{
						if (!s.trim ().startsWith ("un"))
						{
							// avec "s"
							pluralForm = true;
						}
						else
						{
							// jamis de "s"
							pluralPossible = false;
						}
					}
					soFar = s + majorNames[place] + soFar;
				}
			}
			place++;
			number /= 1000;
		}
		while (number > 0);
		String result = (prefix + soFar).trim ();
		return (pluralForm ? result + "s" : result);
	}	//	convert
	
	/**************************************************************************
	 * 	Get Amount in Words
	 * 	@param amount numeric amount (352.80)
	 * 	@return amount in words (three*five*two 80/100)
	 * 	@throws Exception
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
		
		String amttobetranslate = amount.substring (0, (pos));

		// Here we clean unexpected space in the amount  
		String finalamount = new String();
	    char[] mychararray = amttobetranslate.toCharArray(); 
		for (int i = 0; i < amttobetranslate.length (); i++)
		{
			if ( !Character.isSpaceChar(mychararray[i])) 
			{
				finalamount = finalamount.concat(String.valueOf(mychararray[i]));
			}	
		}

		int pesos = Integer.parseInt (finalamount);
		sb.append (convert (pesos));
		for (int i = 0; i < oldamt.length (); i++)
		{
			if (pos == i) //	we are done
			{
				String cents = oldamt.substring (i + 1);
				sb.append (' ').append (cents).append ("/100");
				break;
			}
		}
		return sb.toString ();
	}	//	getAmtInWords

}	// AmtInWords_FR
