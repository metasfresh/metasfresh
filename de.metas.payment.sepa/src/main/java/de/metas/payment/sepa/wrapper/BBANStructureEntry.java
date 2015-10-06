/**
 * 
 */
package de.metas.payment.sepa.wrapper;

/*
 * #%L
 * de.metas.payment.sepa
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
 * @author cg
 *
 */
public class BBANStructureEntry
{
	private BBANCodeEntryType codeType;
	private EntryCharacterType characterType;
	private int length;
	private String seqNo;

	public BBANStructureEntry()
	{
		super();
	}

	public void setCharacterType(EntryCharacterType characterType)
	{
		this.characterType = characterType;
	}

	public EntryCharacterType getCharacterType()
	{
		return characterType;
	}
	
	public void setLength(int length)
	{
		this.length = length;
	}
	
	public int getLength()
	{
		return length;
	}

	public void setSeqNo(String seqNo)
	{
		this.seqNo = seqNo;
	}
	
	public String getSeqNo()
	{
		return seqNo;
	}
	
	
	public BBANCodeEntryType getCodeType()
	{
		return codeType;
	}
	
	public void setCodeType(BBANCodeEntryType codeType)
	{
		this.codeType = codeType;
	}
	
	/**
	 * Basic Bank Account Number Entry Types.
	 */
	public enum BBANCodeEntryType 
	{
	        bank_code,
	        branch_code,
	        account_number,
	        national_check_digit,
	        account_type,
	        owener_account_type,
	        seqNo
	}

	
	public enum EntryCharacterType
	{
		n,  // Digits (numeric characters 0 to 9 only)
		a,  // Upper case letters (alphabetic characters A-Z only)
		c  // upper and lower case alphanumeric characters (A-Z, a-z and 0-9)
	}
	
}
