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
import java.sql.Timestamp;

import org.compiere.model.I_C_Payment;
import org.compiere.model.MTable;
import org.compiere.util.KeyNamePair;

/** Generated Interface for Pay_OnlinePaymentHistory
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_Pay_OnlinePaymentHistory 
{

    /** TableName=Pay_OnlinePaymentHistory */
    public static final String Table_Name = "Pay_OnlinePaymentHistory";

    /** AD_Table_ID=540145 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 3 - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(3);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Mandant.
	  * Mandant fuer diese Installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organisation.
	  * Organisatorische Einheit des Mandanten
	  */
	public int getAD_Org_ID();

    /** Column name Amount */
    public static final String COLUMNNAME_Amount = "Amount";

	/** Set Betrag.
	  * Betrag in einer definierten Waehrung
	  */
	public void setAmount (BigDecimal Amount);

	/** Get Betrag.
	  * Betrag in einer definierten Waehrung
	  */
	public BigDecimal getAmount();

    /** Column name CCPaymentState */
    public static final String COLUMNNAME_CCPaymentState = "CCPaymentState";

	/** Set Status der KK-Zahlung.
	  * Status der Kreditkarten-Zahlung
	  */
	public void setCCPaymentState (String CCPaymentState);

	/** Get Status der KK-Zahlung.
	  * Status der Kreditkarten-Zahlung
	  */
	public String getCCPaymentState();

    /** Column name C_Payment_ID */
    public static final String COLUMNNAME_C_Payment_ID = "C_Payment_ID";

	/** Set Payment.
	  * Payment identifier
	  */
	public void setC_Payment_ID (int C_Payment_ID);

	/** Get Payment.
	  * Payment identifier
	  */
	public int getC_Payment_ID();

	public I_C_Payment getC_Payment() throws RuntimeException;

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Erstellt.
	  * Datum, an dem dieser Eintrag erstellt wurde
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Erstellt durch.
	  * Nutzer, der diesen Eintrag erstellt hat
	  */
	public int getCreatedBy();

    /** Column name CreditCardValue */
    public static final String COLUMNNAME_CreditCardValue = "CreditCardValue";

	/** Set Kreditkarte	  */
	public void setCreditCardValue (String CreditCardValue);

	/** Get Kreditkarte	  */
	public String getCreditCardValue();

    /** Column name ErrorMsg */
    public static final String COLUMNNAME_ErrorMsg = "ErrorMsg";

	/** Set Fehlermeldung	  */
	public void setErrorMsg (String ErrorMsg);

	/** Get Fehlermeldung	  */
	public String getErrorMsg();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public void setIsActive (boolean IsActive);

	/** Get Aktiv.
	  * Der Eintrag ist im System aktiv
	  */
	public boolean isActive();

    /** Column name Orig_TrxID */
    public static final String COLUMNNAME_Orig_TrxID = "Orig_TrxID";

	/** Set Original Transaction ID.
	  * Original Transaction ID
	  */
	public void setOrig_TrxID (String Orig_TrxID);

	/** Get Original Transaction ID.
	  * Original Transaction ID
	  */
	public String getOrig_TrxID();

    /** Column name Pay_OnlinePaymentHistory_ID */
    public static final String COLUMNNAME_Pay_OnlinePaymentHistory_ID = "Pay_OnlinePaymentHistory_ID";

	/** Set Onlinezahlungsverlauf	  */
	public void setPay_OnlinePaymentHistory_ID (int Pay_OnlinePaymentHistory_ID);

	/** Get Onlinezahlungsverlauf	  */
	public int getPay_OnlinePaymentHistory_ID();

    /** Column name R_AuthCode */
    public static final String COLUMNNAME_R_AuthCode = "R_AuthCode";

	/** Set Authorization Code.
	  * Authorization Code returned
	  */
	public void setR_AuthCode (String R_AuthCode);

	/** Get Authorization Code.
	  * Authorization Code returned
	  */
	public String getR_AuthCode();

    /** Column name TenderType */
    public static final String COLUMNNAME_TenderType = "TenderType";

	/** Set Tender type.
	  * Method of Payment
	  */
	public void setTenderType (String TenderType);

	/** Get Tender type.
	  * Method of Payment
	  */
	public String getTenderType();

    /** Column name TrxType */
    public static final String COLUMNNAME_TrxType = "TrxType";

	/** Set Transaction Type.
	  * Type of credit card transaction
	  */
	public void setTrxType (String TrxType);

	/** Get Transaction Type.
	  * Type of credit card transaction
	  */
	public String getTrxType();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Aktualisiert.
	  * Datum, an dem dieser Eintrag aktualisiert wurde
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Aktualisiert durch.
	  * Nutzer, der diesen Eintrag aktualisiert hat
	  */
	public int getUpdatedBy();
}
