/**
 * 
 */
package de.metas.payment.sepa.interfaces;

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
public interface I_C_Country extends org.compiere.model.I_C_Country
{
	/** Column name BankCodeLenght */
    public static final String COLUMNNAME_BankCodeLength = "BankCodeLength";
	public void setBankCodeLength(String BankCodeLength);
	public String getBankCodeLength();
	
	/** Column name BankCodeCharType */
    public static final String COLUMNNAME_BankCodeCharType = "BankCodeCharType";
	public void setBankCodeCharType(String BankCodeCharType);
	public String getBankCodeCharType();
	
	/** Column name BranchCodeLenght */
    public static final String COLUMNNAME_BranchCodeLength = "BranchCodeLength";
	public void setBranchCodeLength(String BranchCodeLength);
	public String getBranchCodeLength();
	
	/** Column name BranchCodeCharType */
    public static final String COLUMNNAME_BranchCodeCharType = "BranchCodeCharType";
	public void setBranchCodeCharType(String BranchCodeCharType);
	public String getBranchCodeCharType();
	
	/** Column name AccountNumberCharType */
    public static final String COLUMNNAME_AccountNumberCharType = "AccountNumberCharType";
	public void setAccountNumberCharType(String AccountNumberCharType);
	public String getAccountNumberCharType();
	
	/** Column name AccountNumberLength */
    public static final String COLUMNNAME_AccountNumberLength = "AccountNumberLength";
	public void setAccountNumberLength(String AccountNumberLength);
	public String getAccountNumberLength();
	
	/** Column name AccountTypeLength */
    public static final String COLUMNNAME_AccountTypeLength = "AccountTypeLength";
	public void setAccountTypeLength(String AccountTypeLength);
	public String getAccountTypeLength();
	
	/** Column name AccountTypeCharType */
    public static final String COLUMNNAME_AccountTypeCharType = "AccountTypeCharType";
	public void setAccountTypeCharType(String AccountTypeCharType);
	public String getAccountTypeCharType();
	
	/** Column name NationalCheckDigitLength */
    public static final String COLUMNNAME_NationalCheckDigitLength = "NationalCheckDigitLength";
	public void setNationalCheckDigitLength(String NationalCheckDigitLength);
	public String getNationalCheckDigitLength();
	
	/** Column name NationalCheckDigitCharType */
    public static final String COLUMNNAME_NationalCheckDigit = "NationalCheckDigitCharType";
	public void setNationalCheckDigitCharType(String NationalCheckDigitCharType);
	public String getNationalCheckDigitCharType();
	
	/** Column name OwnerAccountNumberLength */
    public static final String COLUMNNAME_OwnerAccountNumberLength = "OwnerAccountNumberLength";
	public void setOwnerAccountNumberLength(String OwnerAccountNumberLength);
	public String getOwnerAccountNumberLength();
	
	/** Column name OwnerAccountNumberCharType */
    public static final String COLUMNNAME_OwnerAccountNumberCharType = "OwnerAccountNumberCharType";
	public void setOwnerAccountNumberCharType(String OwnerAccountNumberCharType);
	public String getOwnerAccountNumberCharType();
	
	/** Column name BankCodeSeqNo */
    public static final String COLUMNNAME_BankCodeSeqNo = "BankCodeSeqNo";
	public void setBankCodeSeqNo(String BankCodeSeqNo);
	public String getBankCodeSeqNo();
	
	/** Column name BranchCodeSeqNo */
    public static final String COLUMNNAME_BranchCodeSeqNo = "BranchCodeSeqNo";
	public void setBranchCodeSeqNo(String BranchCodeSeqNo);
	public String getBranchCodeSeqNo();
	
	/** Column name AccountNumberSeqNo */
    public static final String COLUMNNAME_AccountNumberSeqNo = "AccountNumberSeqNo";
	public void setAccountNumberSeqNo(String AccountNumberSeqNo);
	public String getAccountNumberSeqNo();
	
	/** Column name AccountTypeSeqNo */
    public static final String COLUMNNAME_AccountTypeSeqNo = "AccountTypeSeqNo";
	public void setAccountTypeSeqNo(String AccountTypeSeqNo);
	public String getAccountTypeSeqNo();
	
	/** Column name OwnerAccountNumberSeqNo */
    public static final String COLUMNNAME_OwnerAccountNumberSeqNo = "OwnerAccountNumberSeqNo";
	public void setOwnerAccountNumberSeqNo(String OwnerAccountNumberSeqNo);
	public String getOwnerAccountNumberSeqNo();
	
	/** Column name NationalCheckDigitSeqNo */
    public static final String COLUMNNAME_NationalCheckDigitSeqNo = "NationalCheckDigitSeqNo";
	public void setNationalCheckDigitSeqNo(String NationalCheckDigitSeqNo);
	public String getNationalCheckDigitSeqNo();
	
}
