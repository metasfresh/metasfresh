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
 *	Amount in Words for Vietnamese
 *  @author Vietsourcing-The class is based on the AmtInWords_EN.java written by jjanke
 *   NOTE: 
 *  	1)In Vietnamese, the linking word "AND" between hundreds and following numbers
 *  	only applies for numbers less than 10, eg: 
 *  		109 - a hundred and nine, but 
 *  		119 - a hundred and nineteen
 *		2)For numbers of thousands level upward, it is a common practice to pronounce 
 *		'zero hundred', eg:
 *			1020 - one thousand zero hundred twenty
 *			1010030 - one million zero hundred ten thousand zero hundred thirty
 *		3)Change of tone for tens+1 for tens >20, eg:
 *			1 - "một" but 21 -> "hai mươi mốt"
 *		4) Change of words for tens+4 (optional) and tens+5, eg:
 *			4 - "bốn" but 24 = "hai mươi bốn" or "hai mươi tư". We choose not to change
 *			the word
 *			5 - "năm" but 15 = "mười lăm"
 *			
 *  TEST RESULTS:
 * 0.23 = không phẩy hai mươi ba
 * 1.23 = một phẩy hai mươi ba
 * 12,345 = mười hai nghìn ba trăm bốn mươi lăm
 * 103.45 = một trăm linh ba phẩy bốn mươi lăm
 * 114,45 = một trăm mười bốn phẩy bốn mươi lăm
 * 123.45 = một trăm hai mươi ba phẩy bốn mươi lăm
 * 1023.45 = một nghìn không trăm hai mươi ba phẩy bốn mươi lăm
 * 1234.56 = một nghìn hai trăm ba mươi bốn phẩy năm mươi sáu
 * 12345.78 = mười hai nghìn ba trăm bốn mươi lăm phẩy bẩy mươi tám
 * 100457.89 = một trăm nghìn bốn trăm năm mươi bẩy phẩy tám mươi chín
 * 100,234,578.90 = một trăm triệu hai trăm ba mươi bốn nghìn năm trăm bẩy mươi tám phẩy chín mươi
 * 12,034,578.90 = mười hai triệu không trăm ba mươi bốn nghìn năm trăm bẩy mươi tám phẩy chín mươi
 * 103,004,008.90 = một trăm linh ba triệu không trăm linh bốn nghìn không trăm linh tám phẩy chín mươi
 * 1,201,034,578.90 = một tỉ hai trăm linh một triệu không trăm ba mươi bốn nghìn năm trăm bẩy mươi tám phẩy chín mươi
 * 12,201,034,578.90 = mười hai tỉ hai trăm linh một triệu không trăm ba mươi bốn nghìn năm trăm bẩy mươi tám phẩy chín mươi
 * 10220134578 = mười tỉ hai trăm hai mươi triệu một trăm ba mươi bốn nghìn năm trăm bẩy mươi tám
 * 1.093.201.034.578 = một nghìn không trăm chín mươi ba tỉ hai trăm linh một triệu không trăm ba mươi bốn nghìn năm trăm bẩy mươi tám
 * 100,932,010,345,780 = một trăm nghìn chín trăm ba mươi hai tỉ mười triệu ba trăm bốn mươi lăm nghìn bẩy trăm tám mươi
 * 109.320.103,48 = một trăm linh chín triệu ba trăm hai mươi nghìn một trăm linh ba phẩy bốn mươi tám

 *  	 

 *  @version $Id: AmtInWords_VI.java,v 1.1 2009/04/08 00:28:00$
 */
public class AmtInWords_VI implements AmtInWords
{
	/**
	 * 	AmtInWords_VI
	 */
	public AmtInWords_VI ()
	{
		super ();
	}	//	AmtInWords_VI

	/** Thousands plus				*/
	private static final String[]	majorNames	= {
		"", 
		" nghìn ", 
		" triệu ",
		" tỉ ", 
		" nghìn ", 
		" triệu ",
		" tỉ "
	};
	/** numbers to 99				*/
	private static final String[]	numNames	= { 
		"", 
		"một", 
		"hai",
		"ba", 
		"bốn", 
		"năm", 
		"sáu", 
		"bẩy", 
		"tám", 
		"chín",
		"mười", 
		"mười một", 
		"mười hai", 
		"mười ba", 
		"mười bốn", 
		"mười lăm", //list it here so no programming is needed. see note 3,4
		"mười sáu", 
		"mười bẩy", 
		"mười tám", 
		"mười chín",
		"hai mươi",
		"hai mươi mốt", //list it here so no programming is needed. see note 3,4
		"hai mươi hai",
		"hai mươi ba",
		"hai mươi bốn",
		"hai mươi lăm",//list it here so no programming is needed. see note 3,4
		"hai mươi sáu",
		"hai mươi bẩy",
		"hai mươi tám",
		"hai mươi chín",
		"ba mươi",
		"ba mươi mốt",//list it here so no programming is needed. see note 3,4
		"ba mươi hai",
		"ba mươi ba",
		"ba mươi bốn",
		"ba mươi lăm",//list it here so no programming is needed. see note 3,4
		"ba mươi sáu",
		"ba mươi bẩy",
		"ba mươi tám",
		"ba mươi chín",
		"bốn mươi",
		"bốn mươi mốt",//list it here so no programming is needed. see note 3,4
		"bốn mươi hai",
		"bốn mươi ba",
		"bốn mươi bốn",
		"bốn mươi lăm",//list it here so no programming is needed. see note 3,4
		"bốn mươi sáu",
		"bốn mươi bẩy",
		"bốn mươi tám",
		"bốn mươi chín",
		"năm mươi",
		"năm mươi mốt",//list it here so no programming is needed. see note 3,4
		"năm mươi hai",
		"năm mươi ba",
		"năm mươi bốn",
		"năm mươi lăm",//list it here so no programming is needed. see note 3,4
		"năm mươi sáu",
		"năm mươi bẩy",
		"năm mươi tám",
		"năm mươi chín",
		"sáu mươi",
		"sáu mươi mốt",//list it here so no programming is needed. see note 3,4
		"sáu mươi hai",
		"sáu mươi ba",
		"sáu mươi bốn",
		"sáu mươi lăm",//list it here so no programming is needed. see note 3,4
		"sáu mươi sáu",
		"sáu mươi bẩy",
		"sáu mươi tám",
		"sáu mươi chín",
		"bẩy mươi",
		"bẩy mươi mốt",//list it here so no programming is needed. See note 3,4
		"bẩy mươi hai",
		"bẩy mươi ba",
		"bẩy mươi bốn",
		"bẩy mươi lăm",//list it here so no programming is needed. See note 3,4
		"bẩy mươi sáu",
		"bẩy mươi bẩy",
		"bẩy mươi tám",
		"bẩy mươi chín",
		"tám mươi",
		"tám mươi mốt",//list it here so no programming is needed. See note 3,4
		"tám mươi hai",
		"tám mươi ba",
		"tám mươi bốn",
		"tám mươi lăm",//list it here so no programming is needed. See note 3,4
		"tám mươi sáu",
		"tám mươi bẩy",
		"tám mươi tám",
		"tám mươi chín",
		"chín mươi",
		"chín mươi mốt",//list it here so no programming is needed. See note 3,4
		"chín mươi hai",
		"chín mươi ba",
		"chín mươi bốn",
		"chín mươi lăm",//list it here so no programming is needed. See note 3,4
		"chín mươi sáu",
		"chín mươi bẩy",
		"chín mươi tám",
		"chín mươi chín"
	};

	/**
	 * 	Convert Less Than One Thousand
	 *	@param number
	 *	@return amt
	 */
	
	private String convertLessThanOneThousand (int number)
	{
		String soFar = "";
		//	Below 10
		if (number % 100 < 10 && number > 100)
			soFar = " linh "; //see Note 1
			soFar = soFar + numNames[number % 100];
			number /= 100;

		if (number == 0)
			return soFar;
		
		return numNames[number] + " trăm " + soFar;
	}	//	convertLessThanOneThousand

	/**
	 * 	Convert
	 *	@param number
	 *	@return amt
	 */
	private String convert (long number)
	{
		/* special case */
		if (number == 0)
		{
			return "không";
		}
		String prefix = "";
		if (number < 0)
		{
			number = -number;
			prefix = "âm ";
		}
		String soFar = "";
		
		int place = 0;
	do
		{
			long n = number % 1000;
			if (n != 0)
			{
				String s = convertLessThanOneThousand ((int)n);
				if (n <100 && number >1000) //see Note 2
				{
					if (n>10)
						s = "không trăm " + s;
					if (n<10)
								s = "không trăm linh " + s;
					
				}
				soFar = s + majorNames[place] + soFar;
			}
			place++;
			number /= 1000;
		}
		while (number > 1000);
		long m = number % 1000; //see Note 2, this rule does not apply for biggest major name 
		if (m != 0)
		{
			String t = convertLessThanOneThousand ((int)m);
			soFar = t + majorNames[place] + soFar;
		}
		return (prefix + soFar).trim ();
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
		int numberOfCommas = 0;
		int numberOfPeriods = 0;
		StringBuffer sb = new StringBuffer ();
		int period = amount.lastIndexOf ('.'); 
		numberOfPeriods = amount.replaceAll("[^\\.]","").length();
		int comma = amount.lastIndexOf (','); 
		numberOfCommas = amount.replaceAll("[^,]","").length();
		int newpos = 0;
		String decamt ="";
		if (comma > period) //like 1.000.000,89 or 1,000,000 or 120,355 (a hundred and twenty 355/1000)
		{	
			if (period != -1) //like 1.000.000,89
			{
				decamt = amount.substring(comma+1,amount.length());
				amount = amount.replaceAll ("\\.", "");
				newpos = amount.lastIndexOf (',')+1;
			}
			else if ((amount.length()-comma-1) <=2 )
				{
					decamt = amount.substring(comma+1,amount.length());
					newpos = comma+1;
				}
				else	//like 1,000,000
				{	decamt = "";
					amount = amount.replaceAll (",", "");
					newpos = 0;
				}
		}
		if (comma < period) //like 1,000.09 or 1.000.000 or 120.355 (a hundred and twenty 355/100)
		{	
			if ((comma !=-1) | (numberOfPeriods ==1))//like 1,000.09
			{
				decamt = amount.substring(period+1,amount.length());
				amount = amount.replaceAll (",", "");
				newpos = amount.lastIndexOf ('.')+1;
			} 
			else //like 1.000.000
			{
				decamt = "";
				amount = amount.replaceAll ("\\.", "");
				newpos = 0;
			}
		}
		else if ((comma==-1) && (period ==-1)) //like 1000000
				{
					decamt = "";
					newpos = 0;
				}
		long dollars = 0;
		long decima = 0;
		if (newpos !=0)
		{	dollars = Long.parseLong(amount.substring (0, newpos-1));
			sb.append (convert (dollars));
			decima = Long.parseLong(decamt);
			sb.append(" phẩy ").append(convert(decima));
		}
		else 
		{	
			dollars = Long.parseLong(amount.substring(0,amount.length()));
			sb.append (convert(dollars));
		}
	return sb.toString ().replaceAll("  ", " ").replaceAll("linh nghìn", "nghìn").replaceAll("linh triệu","triệu").replaceAll("linh tỉ","tỉ");
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
		AmtInWords_VI aiw = new AmtInWords_VI();
	//	aiw.print (".23");	Error
		aiw.print ("0.23");
		aiw.print ("1.23");
		aiw.print ("12,345");
		aiw.print ("103.45");
		aiw.print ("114,45");
		aiw.print ("123.45");
		aiw.print ("500000000");
		aiw.print ("1234.56");
		aiw.print ("12345.78");
		aiw.print ("100457.89");
		aiw.print ("100,234,578.90");
		aiw.print ("12,034,578.90");
		aiw.print ("103,004,008.90");
		aiw.print ("1,201,034,578.90");
		aiw.print ("12,201,034,578.90");
		aiw.print ("10220134578");
		aiw.print ("1.093.201.034.578");
		aiw.print ("100,932,010,345,780");
		aiw.print ("109.320.103,48");
	}	//	main
	
}	//	AmtInWords_VI

 	  	 