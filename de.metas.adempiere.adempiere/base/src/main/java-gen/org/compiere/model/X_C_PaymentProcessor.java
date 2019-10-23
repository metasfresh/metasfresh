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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for C_PaymentProcessor
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_PaymentProcessor extends org.compiere.model.PO implements I_C_PaymentProcessor, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1134484153L;

    /** Standard Constructor */
    public X_C_PaymentProcessor (Properties ctx, int C_PaymentProcessor_ID, String trxName)
    {
      super (ctx, C_PaymentProcessor_ID, trxName);
      /** if (C_PaymentProcessor_ID == 0)
        {
			setAcceptAMEX (false);
			setAcceptATM (false);
			setAcceptCheck (false);
			setAcceptCorporate (false);
			setAcceptDiners (false);
			setAcceptDirectDebit (false);
			setAcceptDirectDeposit (false);
			setAcceptDiscover (false);
			setAcceptMC (false);
			setAcceptVisa (false);
			setC_BP_BankAccount_ID (0);
			setCommission (Env.ZERO);
			setCostPerTrx (Env.ZERO);
			setC_PaymentProcessor_ID (0);
			setHostAddress (null);
			setHostPort (0);
			setName (null);
			setPassword (null);
			setRequireVV (false);
			setUserID (null);
        } */
    }

    /** Load Constructor */
    public X_C_PaymentProcessor (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_C_PaymentProcessor[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Accept AMEX.
		@param AcceptAMEX 
		Accept American Express Card
	  */
	@Override
	public void setAcceptAMEX (boolean AcceptAMEX)
	{
		set_Value (COLUMNNAME_AcceptAMEX, Boolean.valueOf(AcceptAMEX));
	}

	/** Get Accept AMEX.
		@return Accept American Express Card
	  */
	@Override
	public boolean isAcceptAMEX () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptAMEX);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept ATM.
		@param AcceptATM 
		Accept Bank ATM Card
	  */
	@Override
	public void setAcceptATM (boolean AcceptATM)
	{
		set_Value (COLUMNNAME_AcceptATM, Boolean.valueOf(AcceptATM));
	}

	/** Get Accept ATM.
		@return Accept Bank ATM Card
	  */
	@Override
	public boolean isAcceptATM () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptATM);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Electronic Check.
		@param AcceptCheck 
		Accept ECheck (Electronic Checks)
	  */
	@Override
	public void setAcceptCheck (boolean AcceptCheck)
	{
		set_Value (COLUMNNAME_AcceptCheck, Boolean.valueOf(AcceptCheck));
	}

	/** Get Accept Electronic Check.
		@return Accept ECheck (Electronic Checks)
	  */
	@Override
	public boolean isAcceptCheck () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptCheck);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Corporate.
		@param AcceptCorporate 
		Accept Corporate Purchase Cards
	  */
	@Override
	public void setAcceptCorporate (boolean AcceptCorporate)
	{
		set_Value (COLUMNNAME_AcceptCorporate, Boolean.valueOf(AcceptCorporate));
	}

	/** Get Accept Corporate.
		@return Accept Corporate Purchase Cards
	  */
	@Override
	public boolean isAcceptCorporate () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptCorporate);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Diners.
		@param AcceptDiners 
		Accept Diner's Club
	  */
	@Override
	public void setAcceptDiners (boolean AcceptDiners)
	{
		set_Value (COLUMNNAME_AcceptDiners, Boolean.valueOf(AcceptDiners));
	}

	/** Get Accept Diners.
		@return Accept Diner's Club
	  */
	@Override
	public boolean isAcceptDiners () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptDiners);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Direct Debit.
		@param AcceptDirectDebit 
		Accept Direct Debits (vendor initiated)
	  */
	@Override
	public void setAcceptDirectDebit (boolean AcceptDirectDebit)
	{
		set_Value (COLUMNNAME_AcceptDirectDebit, Boolean.valueOf(AcceptDirectDebit));
	}

	/** Get Accept Direct Debit.
		@return Accept Direct Debits (vendor initiated)
	  */
	@Override
	public boolean isAcceptDirectDebit () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptDirectDebit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Direct Deposit.
		@param AcceptDirectDeposit 
		Accept Direct Deposit (payee initiated)
	  */
	@Override
	public void setAcceptDirectDeposit (boolean AcceptDirectDeposit)
	{
		set_Value (COLUMNNAME_AcceptDirectDeposit, Boolean.valueOf(AcceptDirectDeposit));
	}

	/** Get Accept Direct Deposit.
		@return Accept Direct Deposit (payee initiated)
	  */
	@Override
	public boolean isAcceptDirectDeposit () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptDirectDeposit);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Discover.
		@param AcceptDiscover 
		Accept Discover Card
	  */
	@Override
	public void setAcceptDiscover (boolean AcceptDiscover)
	{
		set_Value (COLUMNNAME_AcceptDiscover, Boolean.valueOf(AcceptDiscover));
	}

	/** Get Accept Discover.
		@return Accept Discover Card
	  */
	@Override
	public boolean isAcceptDiscover () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptDiscover);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept MasterCard.
		@param AcceptMC 
		Accept Master Card
	  */
	@Override
	public void setAcceptMC (boolean AcceptMC)
	{
		set_Value (COLUMNNAME_AcceptMC, Boolean.valueOf(AcceptMC));
	}

	/** Get Accept MasterCard.
		@return Accept Master Card
	  */
	@Override
	public boolean isAcceptMC () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptMC);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Accept Visa.
		@param AcceptVisa 
		Accept Visa Cards
	  */
	@Override
	public void setAcceptVisa (boolean AcceptVisa)
	{
		set_Value (COLUMNNAME_AcceptVisa, Boolean.valueOf(AcceptVisa));
	}

	/** Get Accept Visa.
		@return Accept Visa Cards
	  */
	@Override
	public boolean isAcceptVisa () 
	{
		Object oo = get_Value(COLUMNNAME_AcceptVisa);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	@Override
	public org.compiere.model.I_AD_Sequence getAD_Sequence() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class);
	}

	@Override
	public void setAD_Sequence(org.compiere.model.I_AD_Sequence AD_Sequence)
	{
		set_ValueFromPO(COLUMNNAME_AD_Sequence_ID, org.compiere.model.I_AD_Sequence.class, AD_Sequence);
	}

	/** Set Reihenfolge.
		@param AD_Sequence_ID 
		Document Sequence
	  */
	@Override
	public void setAD_Sequence_ID (int AD_Sequence_ID)
	{
		if (AD_Sequence_ID < 1) 
			set_Value (COLUMNNAME_AD_Sequence_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Sequence_ID, Integer.valueOf(AD_Sequence_ID));
	}

	/** Get Reihenfolge.
		@return Document Sequence
	  */
	@Override
	public int getAD_Sequence_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Sequence_ID);
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

	@Override
	public org.compiere.model.I_C_Currency getC_Currency() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class);
	}

	@Override
	public void setC_Currency(org.compiere.model.I_C_Currency C_Currency)
	{
		set_ValueFromPO(COLUMNNAME_C_Currency_ID, org.compiere.model.I_C_Currency.class, C_Currency);
	}

	/** Set Währung.
		@param C_Currency_ID 
		The Currency for this record
	  */
	@Override
	public void setC_Currency_ID (int C_Currency_ID)
	{
		if (C_Currency_ID < 1) 
			set_Value (COLUMNNAME_C_Currency_ID, null);
		else 
			set_Value (COLUMNNAME_C_Currency_ID, Integer.valueOf(C_Currency_ID));
	}

	/** Get Währung.
		@return The Currency for this record
	  */
	@Override
	public int getC_Currency_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Currency_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Prov. %.
		@param Commission 
		Commission stated as a percentage
	  */
	@Override
	public void setCommission (java.math.BigDecimal Commission)
	{
		set_Value (COLUMNNAME_Commission, Commission);
	}

	/** Get Prov. %.
		@return Commission stated as a percentage
	  */
	@Override
	public java.math.BigDecimal getCommission () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Commission);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Kosten pro Transaktion.
		@param CostPerTrx 
		Fixed cost per transaction
	  */
	@Override
	public void setCostPerTrx (java.math.BigDecimal CostPerTrx)
	{
		set_Value (COLUMNNAME_CostPerTrx, CostPerTrx);
	}

	/** Get Kosten pro Transaktion.
		@return Fixed cost per transaction
	  */
	@Override
	public java.math.BigDecimal getCostPerTrx () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_CostPerTrx);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zahlungs-Prozessor.
		@param C_PaymentProcessor_ID 
		Payment processor for electronic payments
	  */
	@Override
	public void setC_PaymentProcessor_ID (int C_PaymentProcessor_ID)
	{
		if (C_PaymentProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_PaymentProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_PaymentProcessor_ID, Integer.valueOf(C_PaymentProcessor_ID));
	}

	/** Get Zahlungs-Prozessor.
		@return Payment processor for electronic payments
	  */
	@Override
	public int getC_PaymentProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_PaymentProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

	/** Set Minimum Amt.
		@param MinimumAmt 
		Minumum Amout in Document Currency
	  */
	@Override
	public void setMinimumAmt (java.math.BigDecimal MinimumAmt)
	{
		set_Value (COLUMNNAME_MinimumAmt, MinimumAmt);
	}

	/** Get Minimum Amt.
		@return Minumum Amout in Document Currency
	  */
	@Override
	public java.math.BigDecimal getMinimumAmt () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_MinimumAmt);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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

	/** Set Partner ID.
		@param PartnerID 
		Partner ID or Account for the Payment Processor
	  */
	@Override
	public void setPartnerID (java.lang.String PartnerID)
	{
		set_Value (COLUMNNAME_PartnerID, PartnerID);
	}

	/** Get Partner ID.
		@return Partner ID or Account for the Payment Processor
	  */
	@Override
	public java.lang.String getPartnerID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PartnerID);
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

	/** Set Payment Processor Class.
		@param PayProcessorClass 
		Payment Processor Java Class
	  */
	@Override
	public void setPayProcessorClass (java.lang.String PayProcessorClass)
	{
		set_Value (COLUMNNAME_PayProcessorClass, PayProcessorClass);
	}

	/** Get Payment Processor Class.
		@return Payment Processor Java Class
	  */
	@Override
	public java.lang.String getPayProcessorClass () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_PayProcessorClass);
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

	/** Set Require CreditCard Verification Code.
		@param RequireVV 
		Require 3/4 digit Credit Verification Code
	  */
	@Override
	public void setRequireVV (boolean RequireVV)
	{
		set_Value (COLUMNNAME_RequireVV, Boolean.valueOf(RequireVV));
	}

	/** Get Require CreditCard Verification Code.
		@return Require 3/4 digit Credit Verification Code
	  */
	@Override
	public boolean isRequireVV () 
	{
		Object oo = get_Value(COLUMNNAME_RequireVV);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Vendor ID.
		@param VendorID 
		Vendor ID for the Payment Processor
	  */
	@Override
	public void setVendorID (java.lang.String VendorID)
	{
		set_Value (COLUMNNAME_VendorID, VendorID);
	}

	/** Get Vendor ID.
		@return Vendor ID for the Payment Processor
	  */
	@Override
	public java.lang.String getVendorID () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_VendorID);
	}
}