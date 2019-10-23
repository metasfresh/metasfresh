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

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AmtInWords_HU implements AmtInWords {
	
	static String thousandseparator=",";
	static String wholeseparator=".";
	public String getAmtInWords(String amount) throws Exception {
		assert(amount!=null);
		amount = amount.replaceAll(" ", "").replaceAll("\u00A0", "");
		amount = amount.replaceAll("\\"+thousandseparator, "");

		int pos = amount.lastIndexOf(wholeseparator);
		String amountWhole;
		String cents=null;
		if(pos>=0)
		{
			amountWhole=amount.substring(0,pos);
			cents=amount.substring(pos+1);
		}
		else
		{
			amountWhole=amount;
		}

		BigInteger num = new BigInteger(amountWhole);
		StringBuilder ret=new StringBuilder();
		if(num.compareTo(BigInteger.valueOf(0))<0)
		{
			num=num.negate();
			ret.append("mínusz ");
		}
		boolean needDivisor=true;
		if(num.compareTo(BigInteger.valueOf(2000))<0)
		{
			needDivisor=false;
		}
		List<Integer> pieces=new ArrayList<Integer>();
		if(BigInteger.valueOf(0).equals(num))
		{
			return numbers[0];
		}
		while(num.compareTo(BigInteger.valueOf(0))>0)
		{
			BigInteger[] divAndMod=num.divideAndRemainder(BigInteger.valueOf(1000));
			int mod=divAndMod[1].intValue();
			num=divAndMod[0];
			pieces.add(mod);
		}
		Collections.reverse(pieces);
		boolean firstPiece=true;
		for(int i=0;i<pieces.size();++i) {
			int piece=pieces.get(i);
			int weight=pieces.size()-i-1;
			if(piece!=0||firstPiece)
			{
				if(!firstPiece&&needDivisor)
				{
					ret.append("-");
				}
				firstPiece=false;
				ret.append(getAmtInWordsTo1000(piece));
				if(majorNames.length>weight)
				{
					ret.append(majorNames[weight]);
				}else
				{
					throw new NumberFormatException("number too big for converting amount to word"+amount);
				}
			}
		}
		if (cents!=null) {
			ret.append(" egész ");
			ret.append(getAmtInWords(cents));
			ret.append(" század");
		}
		
		return ret.toString();
	}
	
	String[] numbers = new String[] { "nulla", "egy", "kettő", "három", "négy",
			"öt", "hat", "hét", "nyolc", "kilenc" };
	String[] tens_solo = new String[] { null, "tíz", "húsz", "harminc", "negyven",
			"ötven", "hatvan", "hetven", "nyolcvan", "kilencven" };
	String[] tens_connected = new String[] { null, "tizen", "huszon",
			"harminc", "negyven", "ötven", "hatvan", "hetven", "nyolcvan",
			"kilencven" };
	String[] majorNames = { "", "ezer", "millió", "billió", "trillió",
			"kvadrillió", "kvintillió" };

	public String getAmtInWordsTo1000(int amount) {
		StringBuilder ret = new StringBuilder();
		int hundred = amount / 100;
		int ten = (amount - (amount / 100)*100) / 10;
		int one = (amount - (amount / 10)*10);
		if (hundred != 0) {
			if(hundred!=0)
			{
				ret.append(numbers[hundred]);
			}
			ret.append("száz");
		}
		if (ten != 0) {
			if (one != 0) {
				ret.append(tens_connected[ten]);
			} else {
				ret.append(tens_solo[ten]);
			}
		}
		if (one != 0||(hundred==0&&ten==0)) {
			ret.append(numbers[one]);
		}
		return ret.toString();
	}

}
