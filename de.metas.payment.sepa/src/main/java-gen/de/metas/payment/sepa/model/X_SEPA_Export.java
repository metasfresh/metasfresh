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
package de.metas.payment.sepa.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for SEPA_Export
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_SEPA_Export extends org.compiere.model.PO implements I_SEPA_Export, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -351064060L;

    /** Standard Constructor */
    public X_SEPA_Export (Properties ctx, int SEPA_Export_ID, String trxName)
    {
      super (ctx, SEPA_Export_ID, trxName);
      /** if (SEPA_Export_ID == 0)
        {
			setDocumentNo (null);
			setIBAN (null);
			setPaymentDate (new Timestamp( System.currentTimeMillis() ));
			setProcessed (false);
// N	
			setSEPA_CreditorIdentifier (null);
			setSEPA_Export_ID (0);
			setSwiftCode (null);
        } */
    }

    /** Load Constructor */
    public X_SEPA_Export (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


    /** Load Meta Data */
    @Override
    protected org.compiere.model.POInfo initPO (Properties ctx)
    {
      org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo (ctx, Table_Name, get_TrxName());
      return poi;
    }

    @Override
    public String toString()
    {
      StringBuilder sb = new StringBuilder ("X_SEPA_Export[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Table getAD_Table() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class);
	}

	@Override
	public void setAD_Table(org.compiere.model.I_AD_Table AD_Table)
	{
		set_ValueFromPO(COLUMNNAME_AD_Table_ID, org.compiere.model.I_AD_Table.class, AD_Table);
	}

	/** Set DB-Tabelle.
		@param AD_Table_ID 
		Database Table information
	  */
	@Override
	public void setAD_Table_ID (int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Table_ID, Integer.valueOf(AD_Table_ID));
	}

	/** Get DB-Tabelle.
		@return Database Table information
	  */
	@Override
	public int getAD_Table_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Table_ID);
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

	/** Set Document No.
		@param DocumentNo 
		Document sequence number of the document
	  */
	@Override
	public void setDocumentNo (java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
	}

	/** Get Document No.
		@return Document sequence number of the document
	  */
	@Override
	public java.lang.String getDocumentNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DocumentNo);
	}

	/** Set IBAN.
		@param IBAN 
		International Bank Account Number
	  */
	@Override
	public void setIBAN (java.lang.String IBAN)
	{
		set_Value (COLUMNNAME_IBAN, IBAN);
	}

	/** Get IBAN.
		@return International Bank Account Number
	  */
	@Override
	public java.lang.String getIBAN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_IBAN);
	}

	/** Set Zahldatum.
		@param PaymentDate Zahldatum	  */
	@Override
	public void setPaymentDate (java.sql.Timestamp PaymentDate)
	{
		set_Value (COLUMNNAME_PaymentDate, PaymentDate);
	}

	/** Get Zahldatum.
		@return Zahldatum	  */
	@Override
	public java.sql.Timestamp getPaymentDate () 
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_PaymentDate);
	}

	/** Set Verarbeitet.
		@param Processed 
		Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/** Get Verarbeitet.
		@return Checkbox sagt aus, ob der Beleg verarbeitet wurde. 
	  */
	@Override
	public boolean isProcessed () 
	{
		Object oo = get_Value(COLUMNNAME_Processed);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Datensatz-ID.
		@param Record_ID 
		Direct internal record ID
	  */
	@Override
	public void setRecord_ID (int Record_ID)
	{
		if (Record_ID < 0) 
			set_ValueNoCheck (COLUMNNAME_Record_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Record_ID, Integer.valueOf(Record_ID));
	}

	/** Get Datensatz-ID.
		@return Direct internal record ID
	  */
	@Override
	public int getRecord_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Record_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set SEPA Creditor Identifier.
		@param SEPA_CreditorIdentifier SEPA Creditor Identifier	  */
	@Override
	public void setSEPA_CreditorIdentifier (java.lang.String SEPA_CreditorIdentifier)
	{
		set_Value (COLUMNNAME_SEPA_CreditorIdentifier, SEPA_CreditorIdentifier);
	}

	/** Get SEPA Creditor Identifier.
		@return SEPA Creditor Identifier	  */
	@Override
	public java.lang.String getSEPA_CreditorIdentifier () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SEPA_CreditorIdentifier);
	}

	/** Set SEPA Export.
		@param SEPA_Export_ID SEPA Export	  */
	@Override
	public void setSEPA_Export_ID (int SEPA_Export_ID)
	{
		if (SEPA_Export_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_SEPA_Export_ID, Integer.valueOf(SEPA_Export_ID));
	}

	/** Get SEPA Export.
		@return SEPA Export	  */
	@Override
	public int getSEPA_Export_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SEPA_Export_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Swift code.
		@param SwiftCode 
		Swift Code or BIC
	  */
	@Override
	public void setSwiftCode (java.lang.String SwiftCode)
	{
		set_Value (COLUMNNAME_SwiftCode, SwiftCode);
	}

	/** Get Swift code.
		@return Swift Code or BIC
	  */
	@Override
	public java.lang.String getSwiftCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_SwiftCode);
	}
}