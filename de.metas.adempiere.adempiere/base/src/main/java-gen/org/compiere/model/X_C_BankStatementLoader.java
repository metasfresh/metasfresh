/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 1999-2007 ComPiere, Inc. All Rights Reserved.                *
 * This program is free software, you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY, without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program, if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 * For the text or an alternative of this public license, you may reach us    *
 * ComPiere, Inc., 2620 Augustine Dr. #245, Santa Clara, CA 95054, USA        *
 * or via info@compiere.org or http://www.compiere.org/license.html           *
 *****************************************************************************/
/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_BankStatementLoader
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_BankStatementLoader extends org.compiere.model.PO implements I_C_BankStatementLoader, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 519921840L;

    /** Standard Constructor */
    public X_C_BankStatementLoader (Properties ctx, int C_BankStatementLoader_ID, String trxName)
    {
      super (ctx, C_BankStatementLoader_ID, trxName);
      /** if (C_BankStatementLoader_ID == 0)
        {
			setC_BankStatementLoader_ID (0);
			setC_BP_BankAccount_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_BankStatementLoader (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
      */
    @Override
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_C_BankStatementLoader[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Konto-Nr..
		@param AccountNo 
		Account Number
	  */
	@Override
	public void setAccountNo (java.lang.String AccountNo)
	{
		set_Value (COLUMNNAME_AccountNo, AccountNo);
	}

	/** Get Konto-Nr..
		@return Account Number
	  */
	@Override
	public java.lang.String getAccountNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_AccountNo);
	}

	/** Set Branch ID.
		@param BranchID 
		Bank Branch ID
	  */
	@Override
	public void setBranchID (java.lang.String BranchID)
	{
		set_Value (COLUMNNAME_BranchID, BranchID);
	}

	/** Get Branch ID.
		@return Bank Branch ID
	  */
	@Override
	public java.lang.String getBranchID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_BranchID);
	}

	/** Set Ladeprogramm Bankauszug.
		@param C_BankStatementLoader_ID 
		Definition of Bank Statement Loader (SWIFT, OFX)
	  */
	@Override
	public void setC_BankStatementLoader_ID (int C_BankStatementLoader_ID)
	{
		if (C_BankStatementLoader_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLoader_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BankStatementLoader_ID, Integer.valueOf(C_BankStatementLoader_ID));
	}

	/** Get Ladeprogramm Bankauszug.
		@return Definition of Bank Statement Loader (SWIFT, OFX)
	  */
	@Override
	public int getC_BankStatementLoader_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BankStatementLoader_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BP_BankAccount getC_BP_BankAccount() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class);
	}

	@Override
	public void setC_BP_BankAccount(org.compiere.model.I_C_BP_BankAccount C_BP_BankAccount)
	{
		set_ValueFromPO(COLUMNNAME_C_BP_BankAccount_ID, org.compiere.model.I_C_BP_BankAccount.class, C_BP_BankAccount);
	}

	/** Set Bankverbindung.
		@param C_BP_BankAccount_ID 
		Bankverbindung des Geschäftspartners
	  */
	@Override
	public void setC_BP_BankAccount_ID (int C_BP_BankAccount_ID)
	{
		if (C_BP_BankAccount_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_BP_BankAccount_ID, Integer.valueOf(C_BP_BankAccount_ID));
	}

	/** Get Bankverbindung.
		@return Bankverbindung des Geschäftspartners
	  */
	@Override
	public int getC_BP_BankAccount_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BP_BankAccount_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Datums-Format.
		@param DateFormat 
		Date format used in the imput format
	  */
	@Override
	public void setDateFormat (java.lang.String DateFormat)
	{
		set_Value (COLUMNNAME_DateFormat, DateFormat);
	}

	/** Get Datums-Format.
		@return Date format used in the imput format
	  */
	@Override
	public java.lang.String getDateFormat () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DateFormat);
	}

	/** Set Tag letzter Lauf.
		@param DateLastRun 
		Date the process was last run.
	  */
	@Override
	public void setDateLastRun (java.sql.Timestamp DateLastRun)
	{
		set_Value (COLUMNNAME_DateLastRun, DateLastRun);
	}

	/** Get Tag letzter Lauf.
		@return Date the process was last run.
	  */
	@Override
	public java.sql.Timestamp getDateLastRun () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DateLastRun);
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set File Name.
		@param FileName 
		Name of the local file or URL
	  */
	@Override
	public void setFileName (java.lang.String FileName)
	{
		set_Value (COLUMNNAME_FileName, FileName);
	}

	/** Get File Name.
		@return Name of the local file or URL
	  */
	@Override
	public java.lang.String getFileName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FileName);
	}

	/** Set Financial Institution ID.
		@param FinancialInstitutionID 
		The ID of the Financial Institution / Bank
	  */
	@Override
	public void setFinancialInstitutionID (java.lang.String FinancialInstitutionID)
	{
		set_Value (COLUMNNAME_FinancialInstitutionID, FinancialInstitutionID);
	}

	/** Get Financial Institution ID.
		@return The ID of the Financial Institution / Bank
	  */
	@Override
	public java.lang.String getFinancialInstitutionID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_FinancialInstitutionID);
	}

	/** Set Host Address.
		@param HostAddress 
		Host Address URL or DNS
	  */
	@Override
	public void setHostAddress (java.lang.String HostAddress)
	{
		set_Value (COLUMNNAME_HostAddress, HostAddress);
	}

	/** Get Host Address.
		@return Host Address URL or DNS
	  */
	@Override
	public java.lang.String getHostAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_HostAddress);
	}

	/** Set Host port.
		@param HostPort 
		Host Communication Port
	  */
	@Override
	public void setHostPort (int HostPort)
	{
		set_Value (COLUMNNAME_HostPort, Integer.valueOf(HostPort));
	}

	/** Get Host port.
		@return Host Communication Port
	  */
	@Override
	public int getHostPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_HostPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	@Override
	public void setName (java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
	}

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public org.compiere.util.KeyNamePair getKeyNamePair() 
    {
        return new org.compiere.util.KeyNamePair(get_ID(), getName());
    }

	/** Set Kennwort.
		@param Password 
		Password of any length (case sensitive)
	  */
	@Override
	public void setPassword (java.lang.String Password)
	{
		set_Value (COLUMNNAME_Password, Password);
	}

	/** Get Kennwort.
		@return Password of any length (case sensitive)
	  */
	@Override
	public java.lang.String getPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Password);
	}

	/** Set PIN.
		@param PIN 
		Personal Identification Number
	  */
	@Override
	public void setPIN (java.lang.String PIN)
	{
		set_Value (COLUMNNAME_PIN, PIN);
	}

	/** Get PIN.
		@return Personal Identification Number
	  */
	@Override
	public java.lang.String getPIN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PIN);
	}

	/** Set Proxy address.
		@param ProxyAddress 
		 Address of your proxy server
	  */
	@Override
	public void setProxyAddress (java.lang.String ProxyAddress)
	{
		set_Value (COLUMNNAME_ProxyAddress, ProxyAddress);
	}

	/** Get Proxy address.
		@return  Address of your proxy server
	  */
	@Override
	public java.lang.String getProxyAddress () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProxyAddress);
	}

	/** Set Proxy logon.
		@param ProxyLogon 
		Logon of your proxy server
	  */
	@Override
	public void setProxyLogon (java.lang.String ProxyLogon)
	{
		set_Value (COLUMNNAME_ProxyLogon, ProxyLogon);
	}

	/** Get Proxy logon.
		@return Logon of your proxy server
	  */
	@Override
	public java.lang.String getProxyLogon () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProxyLogon);
	}

	/** Set Proxy-Passwort.
		@param ProxyPassword 
		Password of your proxy server
	  */
	@Override
	public void setProxyPassword (java.lang.String ProxyPassword)
	{
		set_Value (COLUMNNAME_ProxyPassword, ProxyPassword);
	}

	/** Get Proxy-Passwort.
		@return Password of your proxy server
	  */
	@Override
	public java.lang.String getProxyPassword () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProxyPassword);
	}

	/** Set Proxy port.
		@param ProxyPort 
		Port of your proxy server
	  */
	@Override
	public void setProxyPort (int ProxyPort)
	{
		set_Value (COLUMNNAME_ProxyPort, Integer.valueOf(ProxyPort));
	}

	/** Get Proxy port.
		@return Port of your proxy server
	  */
	@Override
	public int getProxyPort () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ProxyPort);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Statement Loader Class.
		@param StmtLoaderClass 
		Class name of the bank statement loader
	  */
	@Override
	public void setStmtLoaderClass (java.lang.String StmtLoaderClass)
	{
		set_Value (COLUMNNAME_StmtLoaderClass, StmtLoaderClass);
	}

	/** Get Statement Loader Class.
		@return Class name of the bank statement loader
	  */
	@Override
	public java.lang.String getStmtLoaderClass () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StmtLoaderClass);
	}

	/** Set User ID.
		@param UserID 
		User ID or account number
	  */
	@Override
	public void setUserID (java.lang.String UserID)
	{
		set_Value (COLUMNNAME_UserID, UserID);
	}

	/** Get User ID.
		@return User ID or account number
	  */
	@Override
	public java.lang.String getUserID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_UserID);
	}
}