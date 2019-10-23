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
 * Amount in Words for Polish
 * <p>
 * <ul>
 * <li>2006-02-13 - teo_sarca - integrated Gavin Dunse patch [ 1658661 ] translate polish AmtInWords_PL.java Windows-1250 to UTF-8 
 * </ul>
 * 
 * @author Marek Mosiewicz - http://www.rgagnon.com/javadetails/java-0426.html
 * @version $Id: AmtInWords_PL.java,v 1.4 2005/12/31 06:33:21 jjanke Exp $
 */
public class AmtInWords_PL implements AmtInWords {
	/**
	 * AmtInWords_EN
	 */
	public AmtInWords_PL() {
		super();
	} // AmtInWords_PL

	/** Thousands plus */
	private static final String[][] majorNames = { { "", "", "" },
			{ "tysiąc", "tysiące", "tysięcy" },
			{ "milion", "miliony", "milionów" },
			{ "miliard", "miliardy", "miliardów" },
			{ "bilion", "biliony", "bilionów" },
			{ "trylion", "tryliony", "trylionów" },
			{ "quadrylion", "quadryliony", "quadrylionów" } };

	/** Ten to Ninety */
	private static final String[] tensNames = { "", "dziesięć", "dwadzieścia",
			"trzydzieści", "czterdzieści", "pięćdziesiąt", "sześćdziesiąt",
			"siedemdziesiąt", "osiemdziesiąt", "dziewięćdziesiąt" };
	
	private static final String[] hundredNames = { "", "sto", "dwieście",
			"trzysta", "czterysta", "pięćset", "sześćset", "siedemset",
			"osiemset", "dziewięćset" };
	
	/** numbers to 19 */
	private static final String[] numNames = { "", "jeden", "dwa", "trzy",
			"cztery", "pięć", "sześć", "siedem", "osiem", "dzieiwięć",
			"dziesięć", "jedenaście", "dwanaście", "trzynaście", "czternaście",
			"pietnaście", "szescnaście", "siedemnaście", "osiemnaście",
			"dziewiętnaście" };

	/**
	 * Convert Less Than One Thousand
	 * 
	 * @param number
	 * @return amt
	 */
	private String convertLessThanOneThousand(int number) {
		String soFar;
		// Below 20
		if (number % 100 < 20) {
			soFar = numNames[number % 100];
			number /= 100;
		} else {
			soFar = numNames[number % 10];
			number /= 10;
			soFar = tensNames[number % 10] + " " + soFar;
			number /= 10;

		}
		soFar = hundredNames[number % 10] + " "+ soFar;
		number /= 10;
		return soFar;
	} // convertLessThanOneThousand

	/**
	 * Convert
	 * 
	 * @param number
	 * @return amt
	 */
	private String convert (long number)
	{
		/* special case */
		if (number == 0)
		{
			return "zero";
		}
		String prefix = "";
		if (number < 0)
		{
			number = -number;
			prefix = "minus ";
		}
		String soFar = "";
		int place = 0;
		do
		{
			long n = number % 1000;
			if (n != 0)
			{
				String s = convertLessThanOneThousand ((int)n);
				int pos;
				if(number%10==1){
					pos=0;
				}else if (number%10<5){
					pos =1;
				}else{
					pos=2;
				}
				if(number>9 && number <20){
					pos = 2;
				}
				soFar = s + " " + majorNames[place][pos] + " "+soFar;
			}
			place++;
			number /= 1000;
		}
		while (number > 0);
		return (prefix + soFar).trim ();
	}	// convert

	/***************************************************************************
	 * Get Amount in Words
	 * 
	 * @param amount
	 *            numeric amount (352.80)
	 * @return amount in words (three*five*two 80/100)
	 */
	public String getAmtInWords(String amount) throws Exception {
		if (amount == null)
			return amount;
		//
		StringBuffer sb = new StringBuffer();
		int pos = amount.lastIndexOf('.');
		int pos2 = amount.lastIndexOf(',');
		if (pos2 > pos)
			pos = pos2;
		String oldamt = amount;
		amount = amount.replaceAll(",", "");
		int newpos = amount.lastIndexOf('.');
		long dollars = Long.parseLong(amount.substring(0, newpos));
		sb.append(convert(dollars));
		for (int i = 0; i < oldamt.length(); i++) {
			if (pos == i) // we are done
			{
				String cents = oldamt.substring(i + 1);
				sb.append(' ').append(cents).append("/100");
				break;
			}
		}
		return sb.toString();
	} // getAmtInWords

	/**
	 * Test Print
	 * 
	 * @param amt
	 *            amount
	 */
	private void print(String amt) {
		try {
			System.out.println(amt + " = " + getAmtInWords(amt));
		} catch (Exception e) {
			e.printStackTrace();
		}
	} // print

	/**
	 * Test
	 * 
	 * @param args
	 *            ignored
	 */
	public static void main(String[] args) {
		AmtInWords_PL aiw = new AmtInWords_PL();
		// aiw.print (".23"); Error
		aiw.print("0.23");
		aiw.print("1.23");
		aiw.print("12.345");
		aiw.print("123.45");
		aiw.print("1234.56");
		aiw.print("12345.78");
		aiw.print("10345.78");
		aiw.print("123457.89");
		aiw.print("323457.89");
		aiw.print("23457.89");
		aiw.print("1,234,578.90");
	} // main

} // AmtInWords_EN
