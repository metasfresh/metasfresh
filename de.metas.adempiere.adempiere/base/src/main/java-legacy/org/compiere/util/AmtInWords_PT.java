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
 *	Portuguese Amount in Words
 *	
 *  @author Jorg Janke - http://www.rgagnon.com/javadetails/java-0426.html
 *  @translator emontenegro
 *  @version $Id: AmtInWords_PT.java,v 1.0 2008/01/07 00:54:36 jjanke Exp $
 */
public class AmtInWords_PT implements AmtInWords
{
	/**
	 * 	AmtInWords_PT
	 */
	public AmtInWords_PT ()
	{
		super ();
	} //	AmtInWords_PT

	private static final String[]	majorNames	= {
		"", 
		" Mil", 
		" Milhão",
		" Bilhão", 
		" Trilhão", 
		" Quatrilhão", 
		" Quinquilhão"  
		};

	private static final String[]	majorNamesPlural	= {
		"", 
		" Mil", 
		" Milhões",
		" Bilhões", 
		" Trilhões", 
		" Quatrilhões", 
		" Quinquilhões"  
		};

	private static final String[]	tensNames	= { 
		"", 
		" Dez", 
		" Vinte",
		" Trinta", 
		" Quarenta", 
		" Cinqüenta", 
		" Sessenta", 
		" Setenta",
		" Oitenta", 
		" Noventa"
		};

	private static final String[]	numNames	= { 
		"", 
		" Um",
		" Dois",
		" Três", 
		" Quatro", 
		" Cinco", 
		" Seis", 
		" Sete", 
		" Oito", 
		" Nove",
		" Dez", 
		" Onze", 
		" Doze", 
		" Treze", 
		" Quatorze", 
		" Quinze",
		" Dezesseis", 
		" Dezessete", 
		" Dezoito", 
		" Dezenove"
		};

	/**
	 * 	Convert Less Than One Thousand
	 *	@param number
	 *	@return amt
	 */
	private String convertLessThanOneThousand (int number)
	{
		String soFar;
		// Esta dentro de los 1os. diecinueve?? ISCAP
		if (number % 100 < 20)
		{
			soFar = numNames[number % 100];
			number /= 100;
		}
		else
		{
			soFar = numNames[number % 10];
			number /= 10;
			String s = Double.toString (number);
			if (s.endsWith ("2") && !soFar.equals(""))
				soFar = " Vinte e " + soFar.trim ();
			else if (soFar.equals(""))
				soFar = tensNames[number % 10] + " e" + soFar;
			else
				soFar = tensNames[number % 10] + " e" + soFar;
			number /= 10;
		}
		if (number == 0)
		//return soFar;
		// Begin e-Evolution ogi-cd 		
			return tensNames[number % 10] + soFar; // e-Evolution ogi-cd
		// End e-Evolution ogi-cd
		if (number > 1)
			soFar = "s e" + soFar;
		if (number == 1 && !soFar.equals(""))
			number = 0;
			soFar = " e" + soFar;
		return numNames[number] + " Cento" + soFar;
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
			return "";
		if (number == 1)
			return "Um";
		if (number == -1)
			return "Menos Um";
		String prefix = "";
		if (number < 0)
		{
			number = -number;
			prefix = "Menos";
		}
		if (number > 1000000 && number < 2000000){
			prefix = "Um";
		}
		String soFar = "";
		int place = 0;
		do
		{
			int n = number % 1000;
			if (n != 0)
			{
				String s = convertLessThanOneThousand (n);
				if (s.startsWith ("Um Cento e", 1))
				{
					s = s.replaceFirst ("Um Cento e", "Cem");
				}
				if (s.startsWith ("Dois Cento es", 1))
				{
					s = s.replaceFirst ("Dois Cento es", "Duzentos");
				}
				if (s.startsWith ("Três Cento es", 1))
				{
					s = s.replaceFirst ("Três Cento es", "Trezentos");
				}
				if (s.startsWith ("Quatro Cento es", 1))
				{
					s = s.replaceFirst ("Quatro Cento es", "Quatrocentos");
				}
				if (s.startsWith ("Cinco Cento es", 1))
				{
					s = s.replaceFirst ("Cinco Cento es", "Quinhentos");
				}
				if (s.startsWith ("Seis Cento es", 1))
				{
					s = s.replaceFirst ("Seis Cento es", "Seiscentos");
				}
				if (s.startsWith ("Sete Cento es", 1))
				{
					s = s.replaceFirst ("Sete Cento es", "Setecentos");
				}
				if (s.startsWith ("Oito Cento es", 1))
				{
					s = s.replaceFirst ("Oito Cento es", "Oitocentos");
				}
				if (s.startsWith ("Nove Cento es", 1))
				{
					s = s.replaceFirst ("Nove Cento es", "Novecentos");
				}
				if (s.equals(" Um"))
				{
					soFar = majorNames[place] + soFar;
				}
				else {
					if (n > 1) {
						soFar = s + majorNamesPlural[place] + soFar;
					} else {
					soFar = s + majorNames[place] + soFar;
			}
				}
			}
			place++;	
			number /= 1000;
		}
		while (number > 0);
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
		StringBuffer sb = new StringBuffer ();
    //	int pos = amount.lastIndexOf ('.');    // Old
		int pos = amount.lastIndexOf (',');  		
    //  int pos2 = amount.lastIndexOf (',');   // Old		
		int pos2 = amount.lastIndexOf ('.');
		if (pos2 > pos)
			pos = pos2;
		String oldamt = amount;

    //  amount = amount.replaceAll (",", "");   // Old
		String vlr = amount.replaceAll (",", ".");
		amount = amount.replaceAll( "\\.","");

	//	int newpos = amount.lastIndexOf ('.');  // Old
		int newpos = amount.lastIndexOf (',');
		int pesos =  Integer.parseInt (amount.substring (0, newpos));
		double valor = Double.parseDouble(vlr);
		sb.append (convert (pesos));
		for (int i = 0; i < oldamt.length (); i++)
		{
			if (pos == i) //	we are done
			{
			String cents = oldamt.substring (i + 1);
			if (valor > 0 && valor < 1) {
				if (Integer.parseInt(cents) > 0)
				{
					if (Integer.parseInt(cents) > 1)
					{
						//sb.append (' ')
							//.append("e ")
							sb.append (convert(Integer.parseInt(cents)))
							.append(" Centavos");
						//	.append ("/100");
						//	.append ("/100 PESOS");
					}
					else {
						//sb.append (' ')
						//.append("e ")
						sb.append (convert(Integer.parseInt(cents)))
						.append(" Centavo");
					//	.append ("/100");
					//	.append ("/100 PESOS");
					}
			}
			}
			else if ((valor > 1 && valor < 2) || (valor > -2 && valor < -1)){
			if (Integer.parseInt(cents) > 0)
			{
				if (Integer.parseInt(cents) > 1)
				{
					sb.append (' ')
						.append("Real e ")
						.append (convert(Integer.parseInt(cents)))
						.append(" Centavos");
					//	.append ("/100");
					//	.append ("/100 PESOS");
				}
				else {
					sb.append (' ')
					.append("Real e ")
					.append (convert(Integer.parseInt(cents)))
					.append(" Centavo");
				//	.append ("/100");
				//	.append ("/100 PESOS");
				}
				break;
			}
			}
			else if (valor > -1 && valor < 0){
				if (Integer.parseInt(cents) > 0)
				{
					if (Integer.parseInt(cents) > 1)
					{
						sb.append ("Menos ")
						//	.append("Real e ")
							.append (convert(Integer.parseInt(cents)))
							.append(" Centavos");
						//	.append ("/100");
						//	.append ("/100 PESOS");
					}
					else {
						sb.append ("Menos ")
						//.append("Real e ")
						.append (convert(Integer.parseInt(cents)))
						.append(" Centavo");
					//	.append ("/100");
					//	.append ("/100 PESOS");
					}
					break;
				}
				}
			else {
				if (Integer.parseInt(cents) > 0)
				{
					if (Integer.parseInt(cents) > 1)
					{
						sb.append (' ')
							.append("Reais e ")
							.append (convert(Integer.parseInt(cents)))
							.append(" Centavos");
						//	.append ("/100");
						//	.append ("/100 PESOS");
					}
					else {
						sb.append (' ')
						.append("Reais e ")
						.append (convert(Integer.parseInt(cents)))
						.append(" Centavo");
					//	.append ("/100");
					//	.append ("/100 PESOS");
					}
					break;
				}
				}
			}
		}
		return sb.toString ();
	}	//	getAmtInWords

	public static void main(String[] args) throws Exception {
		AmtInWords_PT aiw = new AmtInWords_PT();
		// for (int i=0; i<=2147000000; i++)
			// System.out.println(aiw.getAmtInWords(i+",00"));	
		System.out.println(aiw.getAmtInWords("134502932,01"));
	}
	
}	//	AmtInWords_PT
