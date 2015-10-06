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
package org.compiere.model;

import java.util.Calendar;
import java.util.StringTokenizer;

import org.compiere.util.CLogger;


/**
 *	Payment Validion Routines
 *	
 *  @author Jorg Janke
 *  @version $Id: MPaymentValidate.java,v 1.2 2006/07/30 00:51:05 jjanke Exp $
 */
public class MPaymentValidate
{
	/**	Static Logger	*/
	private static CLogger	s_log	= CLogger.getCLogger (MPaymentValidate.class);

	
	/**
	 *  Is this a valid Credit Card Exp Date?
	 *	@param mmyy Exp in form of mmyy
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardExp (String mmyy)
	{
		String exp = checkNumeric(mmyy);
		if (exp.length() != 4)
			return "CreditCardExpFormat";
		//
		String mmStr = exp.substring(0,2);
		String yyStr = exp.substring(2,4);
		//
		int mm = 0;
		int yy = 0;
		try
		{
			mm = Integer.parseInt(mmStr);
			yy = Integer.parseInt(yyStr);
		}
		catch (Exception e)
		{
			return "CreditCardExpFormat";
		}
		return validateCreditCardExp(mm,yy);
	}   //  validateCreditCardExp

	/**
	 *  Return Month of Exp
	 *  @param mmyy  Exp in form of mmyy
	 *  @return month
	 */
	public static int getCreditCardExpMM (String mmyy)
	{
		String mmStr = mmyy.substring(0,2);
		int mm = 0;
		try
		{
			mm = Integer.parseInt(mmStr);
		}
		catch (Exception e)
		{
		}
		return mm;
	}   //  getCreditCardExpMM

	/**
	 *  Return Year of Exp
	 *  @param mmyy  Exp in form of mmyy
	 *  @return year
	 */
	public static int getCreditCardExpYY (String mmyy)
	{
		String yyStr = mmyy.substring(2);
		int yy = 0;
		try
		{
			yy = Integer.parseInt(yyStr);
		}
		catch (Exception e)
		{
		}
		return yy;
	}   //  getCreditCardExpYY

	/**
	 *  Is this a valid Credit Card Exp Date?
	 *  @param mm month
	 *  @param yy year
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardExp (int mm, int yy)
	{
		if (mm < 1 || mm > 12)
			return "CreditCardExpMonth";
	//	if (yy < 0 || yy > EXP_YEAR)
	//		return "CreditCardExpYear";

		//  Today's date
		Calendar cal = Calendar.getInstance();
		int year = cal.get(Calendar.YEAR) - 2000;   //  two digits
		int month = cal.get(Calendar.MONTH) + 1;    //  zero based
		//
		if (yy < year)
			return "CreditCardExpired";
		else if (yy == year && mm < month)
			return "CreditCardExpired";
		return "";
	}   //  validateCreditCardExp

	
	/**
	 *  Validate Credit Card Number.
	 *  - Based on LUHN formula
	 *  @param creditCardNumber credit card number
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardNumber (String creditCardNumber)
	{
		if (creditCardNumber == null || creditCardNumber.length() == 0)
			return "CreditCardNumberError";

		/**
		 *  1:  Double the value of alternate digits beginning with
		 *      the	first right-hand digit (low order).
		 *  2:  Add the individual digits comprising the products
		 *      obtained in step 1 to each of the unaffected digits
		 *      in the original number.
		 *  3:  Subtract the total obtained in step 2 from the next higher
		 *      number ending in 0 [this in the equivalent of calculating
		 *      the "tens complement" of the low order digit (unit digit)
		 *      of the total].
		 *      If the total obtained in step 2 is a number ending in zero
		 *      (30, 40 etc.), the check digit is 0.
		 *  Example:
		 *  Account number: 4992 73 9871 6
		 *
		 *  4  9  9  2  7  3  9  8  7  1  6
		 *    x2    x2    x2    x2    x2
		 *  -------------------------------
		 *  4 18  9  4  7  6  9 16  7  2  6
		 *
		 *  4 + 1 + 8 + 9 + 4 + 7 + 6 + 9 + 1 + 6 + 7 + 2 + 6 = 70
		 *  70 % 10 = 0
		 */

		//  Clean up number
		String ccNumber1 = checkNumeric(creditCardNumber);
		int ccLength = ccNumber1.length();
		//  Reverse string
		StringBuffer buf = new StringBuffer();
		for (int i = ccLength; i != 0; i--)
			buf.append(ccNumber1.charAt(i-1));
		String ccNumber = buf.toString();

		int sum = 0;
		for (int i = 0; i < ccLength; i++)
		{
			int digit = Character.getNumericValue(ccNumber.charAt(i));
			if (i % 2 == 1)
			{
				digit *= 2;
				if (digit > 9)
					digit -= 9;
			}
			sum += digit;
		}
		if (sum % 10 == 0)
			return "";

		s_log.fine("validateCreditCardNumber - " + creditCardNumber + " -> "
			+ ccNumber + ", Luhn=" + sum);
		return "CreditCardNumberError";
	}   //  validateCreditCardNumber

	/**
	 *  Validate Credit Card Number.
	 *  - Check Card Type and Length
	 *  @param creditCardNumber CC Number
	 *  @param creditCardType CC Type
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardNumber (String creditCardNumber, String creditCardType)
	{
		if (creditCardNumber == null || creditCardType == null)
			return "CreditCardNumberError";

		//  http://www.beachnet.com/~hstiles/cardtype.html
		//	http://staff.semel.fi/~kribe/document/luhn.htm

		String ccStartList = "";    //  comma separated list of starting numbers
		String ccLengthList = "";   //  comma separated list of lengths
		//
		if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_MasterCard))
		{
			ccStartList = "51,52,53,54,55";
			ccLengthList = "16";
		}
		else if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Visa))
		{
			ccStartList = "4";
			ccLengthList = "13,16";
		}
		else if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Amex))
		{
			ccStartList = "34,37";
			ccLengthList = "15";
		}
		else if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Discover))
		{
			ccStartList = "6011";
			ccLengthList = "16";
		}
		else if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Diners))
		{
			ccStartList = "300,301,302,303,304,305,36,38";
			ccLengthList = "14";
		}
		else
		{
			//  enRouteCard
			ccStartList = "2014,2149";
			ccLengthList = "15";
			//  JCBCard
			ccStartList += ",3088,3096,3112,3158,3337,3528";
			ccLengthList += ",16";
			//  JCBCard
			ccStartList += ",2131,1800";
			ccLengthList += ",15";
		}

		//  Clean up number
		String ccNumber = checkNumeric(creditCardNumber);

		/**
		 *  Check Length
		 */
		int ccLength = ccNumber.length();
		boolean ccLengthOK = false;
		StringTokenizer st = new StringTokenizer(ccLengthList, ",", false);
		while (st.hasMoreTokens() && !ccLengthOK)
		{
			int l = Integer.parseInt(st.nextToken());
			if (ccLength == l)
				ccLengthOK = true;
		}
		if (!ccLengthOK)
		{
			s_log.fine("validateCreditCardNumber Length="
				+ ccLength + " <> " + ccLengthList);
			return "CreditCardNumberError";
		}

		/**
		 *  Check Start Digits
		 */
		boolean ccIdentified = false;
		st = new StringTokenizer(ccStartList, ",", false);
		while (st.hasMoreTokens() && !ccIdentified)
		{
			if (ccNumber.startsWith(st.nextToken()))
				ccIdentified = true;
		}
		if (!ccIdentified)
			s_log.fine("validateCreditCardNumber Type="
				+ creditCardType + " <> " + ccStartList);

		//
		String check = validateCreditCardNumber(ccNumber);
		if (check.length() != 0)
			return check;
		if (!ccIdentified)
			return "CreditCardNumberProblem?";
		return "";
	}   //  validateCreditCardNumber
	
	
	/**
	 *  Validate Validation Code
	 *  @param creditCardVV CC Verification Code
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardVV (String creditCardVV)
	{
		if (creditCardVV == null)
			return "";
		int length = checkNumeric(creditCardVV).length();
		if (length == 3 || length == 4)
			return "";
		try
		{
			Integer.parseInt (creditCardVV);
			return "";
		}
		catch (NumberFormatException ex)
		{
			s_log.fine("validateCreditCardVV - " + ex);
		}
		s_log.fine("validateCreditCardVV - length=" + length);
		return "CreditCardVVError";
	}   //  validateCreditCardVV

	/**
	 *  Validate Validation Code
	 *  @param creditCardVV CC Verification Code
	 *  @param creditCardType CC Type see CC_
	 *  @return "" or Error AD_Message
	 */
	public static String validateCreditCardVV (String creditCardVV, String creditCardType)
	{
		//	no data
		if (creditCardVV == null || creditCardVV.length() == 0
			|| creditCardType == null || creditCardType.length() == 0)
			return "";

		int length = checkNumeric(creditCardVV).length();

		//	Amex = 4 digits
		if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Amex))
		{
			if (length == 4)
			{
				try
				{
					Integer.parseInt (creditCardVV);
					return "";
				}
				catch (NumberFormatException ex)
				{
					s_log.fine("validateCreditCardVV - " + ex);
				}
			}
			s_log.fine("validateCreditCardVV(4) CC=" + creditCardType + ", length=" + length);
			return "CreditCardVVError";
		}
		//	Visa & MasterCard - 3 digits
		if (creditCardType.equals(X_C_Payment.CREDITCARDTYPE_Visa) 
			|| creditCardType.equals(X_C_Payment.CREDITCARDTYPE_MasterCard))
		{
			if (length == 3)
			{
				try
				{
					Integer.parseInt (creditCardVV);
					return "";
				}
				catch (NumberFormatException ex)
				{
					s_log.fine("validateCreditCardVV - " + ex);
				}
			}
			s_log.fine("validateCreditCardVV(3) CC=" + creditCardType + ", length=" + length);
			return "CreditCardVVError";
		}

		//	Other
		return "";
	}   //  validateCreditCardVV
	
	
	/**************************************************************************
	 *  Validate Routing Number
	 *  @param routingNo Routing No
	 *  @return "" or Error AD_Message
	 */
	public static String validateRoutingNo (String routingNo)
	{
		int length = checkNumeric(routingNo).length();
		//  US - length 9
		//  Germany - length 8
		//	Japan - 7
		//	CH - 5
		//	Issue: Bank account country
		if (length > 0)
			return "";
		return "PaymentBankRoutingNotValid";
	}   //  validateBankRoutingNo

	/**
	 *  Validate Account No
	 *  @param AccountNo AccountNo
	 *  @return "" or Error AD_Message
	 */
	public static String validateAccountNo (String AccountNo)
	{
		int length = checkNumeric(AccountNo).length();
		if (length > 0)
			return "";
		return "PaymentBankAccountNotValid";
	}   //  validateBankAccountNo

	/**
	 *  Validate Check No
	 *  @param CheckNo CheckNo
	 *  @return "" or Error AD_Message
	 */
	public static String validateCheckNo (String CheckNo)
	{
		int length = checkNumeric(CheckNo).length();
		if (length > 0)
			return "";
		return "PaymentBankCheckNotValid";
	}   //  validateBankCheckNo
	
	/**
	 *  Check Numeric
	 *  @param data input
	 *  @return the digits of the data - ignore the rest
	 */
	public static String checkNumeric (String data)
	{
		if (data == null || data.length() == 0)
			return "";
		//  Remove all non Digits
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < data.length(); i++)
		{
			if (Character.isDigit(data.charAt(i)))
				sb.append(data.charAt(i));
		}
		return sb.toString();
	}   //  checkNumeric

	
}	//	MPaymentValidate
