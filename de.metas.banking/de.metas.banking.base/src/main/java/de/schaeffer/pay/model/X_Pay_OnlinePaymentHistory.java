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
package de.schaeffer.pay.model;

/*
 * #%L
 * de.metas.banking.base
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

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_C_Payment;
import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for Pay_OnlinePaymentHistory
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_Pay_OnlinePaymentHistory extends PO implements I_Pay_OnlinePaymentHistory, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20100224L;

    /** Standard Constructor */
    public X_Pay_OnlinePaymentHistory (Properties ctx, int Pay_OnlinePaymentHistory_ID, String trxName)
    {
      super (ctx, Pay_OnlinePaymentHistory_ID, trxName);
      /** if (Pay_OnlinePaymentHistory_ID == 0)
        {
			setC_Payment_ID (0);
			setPay_OnlinePaymentHistory_ID (0);
        } */
    }

    /** Load Constructor */
    public X_Pay_OnlinePaymentHistory (Properties ctx, ResultSet rs, String trxName)
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
	protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    @Override
	public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_Pay_OnlinePaymentHistory[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Betrag.
		@param Amount 
		Betrag in einer definierten Waehrung
	  */
	@Override
	public void setAmount (BigDecimal Amount)
	{
		set_Value (COLUMNNAME_Amount, Amount);
	}

	/** Get Betrag.
		@return Betrag in einer definierten Waehrung
	  */
	@Override
	public BigDecimal getAmount () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Amount);
		if (bd == null)
		{
			return Env.ZERO;
		}
		return bd;
	}

	/** CCPaymentState AD_Reference_ID=540092 */
	public static final int CCPAYMENTSTATE_AD_Reference_ID=540092;
	/** Reserviert = RE */
	public static final String CCPAYMENTSTATE_Reserviert = "RE";
	/** Umgekehrt = RV */
	public static final String CCPAYMENTSTATE_Umgekehrt = "RV";
	/** Kredit = CR */
	public static final String CCPAYMENTSTATE_Kredit = "CR";
	/** Rueckzahlung = RF */
	public static final String CCPAYMENTSTATE_Rueckzahlung = "RF";
	/** Gekauft = PU */
	public static final String CCPAYMENTSTATE_Gekauft = "PU";
	/** Kein Status = NO */
	public static final String CCPAYMENTSTATE_KeinStatus = "NO";
	/** Fehler = ER */
	public static final String CCPAYMENTSTATE_Fehler = "ER";
	/** Eingezogen = CA */
	public static final String CCPAYMENTSTATE_Eingezogen = "CA";
	/** Set Status der KK-Zahlung.
		@param CCPaymentState 
		Status der Kreditkarten-Zahlung
	  */
	@Override
	public void setCCPaymentState (String CCPaymentState)
	{

		set_Value (COLUMNNAME_CCPaymentState, CCPaymentState);
	}

	/** Get Status der KK-Zahlung.
		@return Status der Kreditkarten-Zahlung
	  */
	@Override
	public String getCCPaymentState () 
	{
		return (String)get_Value(COLUMNNAME_CCPaymentState);
	}

	@Override
	public I_C_Payment getC_Payment() throws RuntimeException
    {
		return (I_C_Payment)MTable.get(getCtx(), I_C_Payment.Table_Name)
			.getPO(getC_Payment_ID(), get_TrxName());	}

	/** Set Payment.
		@param C_Payment_ID 
		Payment identifier
	  */
	@Override
	public void setC_Payment_ID (int C_Payment_ID)
	{
		if (C_Payment_ID < 1)
		{
			set_Value (COLUMNNAME_C_Payment_ID, null);
		}
		else
		{
			set_Value (COLUMNNAME_C_Payment_ID, Integer.valueOf(C_Payment_ID));
		}
	}

	/** Get Payment.
		@return Payment identifier
	  */
	@Override
	public int getC_Payment_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Payment_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Kreditkarte.
		@param CreditCardValue Kreditkarte	  */
	@Override
	public void setCreditCardValue (String CreditCardValue)
	{
		set_Value (COLUMNNAME_CreditCardValue, CreditCardValue);
	}

	/** Get Kreditkarte.
		@return Kreditkarte	  */
	@Override
	public String getCreditCardValue () 
	{
		return (String)get_Value(COLUMNNAME_CreditCardValue);
	}

	/** Set Fehlermeldung.
		@param ErrorMsg Fehlermeldung	  */
	@Override
	public void setErrorMsg (String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	/** Get Fehlermeldung.
		@return Fehlermeldung	  */
	@Override
	public String getErrorMsg () 
	{
		return (String)get_Value(COLUMNNAME_ErrorMsg);
	}

	/** Set Original Transaction ID.
		@param Orig_TrxID 
		Original Transaction ID
	  */
	@Override
	public void setOrig_TrxID (String Orig_TrxID)
	{
		set_Value (COLUMNNAME_Orig_TrxID, Orig_TrxID);
	}

	/** Get Original Transaction ID.
		@return Original Transaction ID
	  */
	@Override
	public String getOrig_TrxID () 
	{
		return (String)get_Value(COLUMNNAME_Orig_TrxID);
	}

	/** Set Onlinezahlungsverlauf.
		@param Pay_OnlinePaymentHistory_ID Onlinezahlungsverlauf	  */
	@Override
	public void setPay_OnlinePaymentHistory_ID (int Pay_OnlinePaymentHistory_ID)
	{
		if (Pay_OnlinePaymentHistory_ID < 1)
		{
			set_ValueNoCheck (COLUMNNAME_Pay_OnlinePaymentHistory_ID, null);
		}
		else
		{
			set_ValueNoCheck (COLUMNNAME_Pay_OnlinePaymentHistory_ID, Integer.valueOf(Pay_OnlinePaymentHistory_ID));
		}
	}

	/** Get Onlinezahlungsverlauf.
		@return Onlinezahlungsverlauf	  */
	@Override
	public int getPay_OnlinePaymentHistory_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Pay_OnlinePaymentHistory_ID);
		if (ii == null)
		{
			return 0;
		}
		return ii.intValue();
	}

	/** Set Authorization Code.
		@param R_AuthCode 
		Authorization Code returned
	  */
	@Override
	public void setR_AuthCode (String R_AuthCode)
	{
		set_Value (COLUMNNAME_R_AuthCode, R_AuthCode);
	}

	/** Get Authorization Code.
		@return Authorization Code returned
	  */
	@Override
	public String getR_AuthCode () 
	{
		return (String)get_Value(COLUMNNAME_R_AuthCode);
	}

//	/** TenderType AD_Reference_ID=214 */
//	public static final int TENDERTYPE_AD_Reference_ID=214;
//	/** Kreditkarte = C */
//	public static final String TENDERTYPE_Kreditkarte = "C";
//	/** Scheck = K */
//	public static final String TENDERTYPE_Scheck = "K";
//	/** ueberweisung = A */
//	public static final String TENDERTYPE_ueberweisung = "A";
//	/** Bankeinzug = D */
//	public static final String TENDERTYPE_Bankeinzug = "D";
//	/** Account = T */
//	public static final String TENDERTYPE_Account = "T";
//	/** Bar = X */
//	public static final String TENDERTYPE_Bar = "X";
	/** Set Tender type.
		@param TenderType 
		Method of Payment
	  */
	@Override
	public void setTenderType (String TenderType)
	{

		set_Value (COLUMNNAME_TenderType, TenderType);
	}

	/** Get Tender type.
		@return Method of Payment
	  */
	@Override
	public String getTenderType () 
	{
		return (String)get_Value(COLUMNNAME_TenderType);
	}

//	/** TrxType AD_Reference_ID=215 */
//	public static final int TRXTYPE_AD_Reference_ID=215;
//	/** Verkauf = S */
//	public static final String TRXTYPE_Verkauf = "S";
//	/** Delayed Capture = D */
//	public static final String TRXTYPE_DelayedCapture = "D";
//	/** Kredit (Zahlung) = C */
//	public static final String TRXTYPE_KreditZahlung = "C";
//	/** Voice Authorization = F */
//	public static final String TRXTYPE_VoiceAuthorization = "F";
//	/** Autorisierung = A */
//	public static final String TRXTYPE_Autorisierung = "A";
//	/** Loeschen = V */
//	public static final String TRXTYPE_Loeschen = "V";
//	/** Rueckzahlung = R */
//	public static final String TRXTYPE_Rueckzahlung = "R";
	/** Set Transaction Type.
		@param TrxType 
		Type of credit card transaction
	  */
	@Override
	public void setTrxType (String TrxType)
	{

		set_Value (COLUMNNAME_TrxType, TrxType);
	}

	/** Get Transaction Type.
		@return Type of credit card transaction
	  */
	@Override
	public String getTrxType () 
	{
		return (String)get_Value(COLUMNNAME_TrxType);
	}
}
