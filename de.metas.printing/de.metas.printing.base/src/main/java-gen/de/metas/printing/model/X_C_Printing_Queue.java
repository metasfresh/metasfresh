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
package de.metas.printing.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Printing_Queue
 *  @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_C_Printing_Queue extends org.compiere.model.PO implements I_C_Printing_Queue, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = -544191654L;

    /** Standard Constructor */
    public X_C_Printing_Queue (Properties ctx, int C_Printing_Queue_ID, String trxName)
    {
      super (ctx, C_Printing_Queue_ID, trxName);
      /** if (C_Printing_Queue_ID == 0)
        {
			setAD_Archive_ID (0);
			setCopies (0);
// 1
			setC_Printing_Queue_ID (0);
			setIsPrintoutForOtherUser (false);
// N
			setProcessed (false);
// N
        } */
    }

    /** Load Constructor */
    public X_C_Printing_Queue (Properties ctx, ResultSet rs, String trxName)
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
	public org.compiere.model.I_AD_Archive getAD_Archive() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class);
	}

	@Override
	public void setAD_Archive(org.compiere.model.I_AD_Archive AD_Archive)
	{
		set_ValueFromPO(COLUMNNAME_AD_Archive_ID, org.compiere.model.I_AD_Archive.class, AD_Archive);
	}

	/** Set Archiv.
		@param AD_Archive_ID
		Archiv fĂĽr Belege und Berichte
	  */
	@Override
	public void setAD_Archive_ID (int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1)
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, Integer.valueOf(AD_Archive_ID));
	}

	/** Get Archiv.
		@return Archiv fĂĽr Belege und Berichte
	  */
	@Override
	public int getAD_Archive_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Archive_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sprache.
	@param AD_Language Language for this entity
	  */
	@Override
	public void setAD_Language (java.lang.String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Sprache.
		@return Language for this entity
	  */
	@Override
	public java.lang.String getAD_Language ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	/** Set Prozess.
		@param AD_Process_ID
		Prozess oder Bericht
	  */
	@Override
	public void setAD_Process_ID (int AD_Process_ID)
	{
		if (AD_Process_ID < 1)
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else
			set_Value (COLUMNNAME_AD_Process_ID, Integer.valueOf(AD_Process_ID));
	}

	/** Get Prozess.
		@return Prozess oder Bericht
	  */
	@Override
	public int getAD_Process_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Process_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_Role getAD_Role() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role(org.compiere.model.I_AD_Role AD_Role)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_ID, org.compiere.model.I_AD_Role.class, AD_Role);
	}

	/** Set Rolle.
		@param AD_Role_ID
		Responsibility Role
	  */
	@Override
	public void setAD_Role_ID (int AD_Role_ID)
	{
		if (AD_Role_ID < 0)
			set_Value (COLUMNNAME_AD_Role_ID, null);
		else
			set_Value (COLUMNNAME_AD_Role_ID, Integer.valueOf(AD_Role_ID));
	}

	/** Get Rolle.
		@return Responsibility Role
	  */
	@Override
	public int getAD_Role_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User(org.compiere.model.I_AD_User AD_User)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_ID, org.compiere.model.I_AD_User.class, AD_User);
	}

	/** Set Ansprechpartner.
		@param AD_User_ID
		User within the system - Internal or Business Partner Contact
	  */
	@Override
	public void setAD_User_ID (int AD_User_ID)
	{
		if (AD_User_ID < 0)
			set_Value (COLUMNNAME_AD_User_ID, null);
		else
			set_Value (COLUMNNAME_AD_User_ID, Integer.valueOf(AD_User_ID));
	}

	/** Get Ansprechpartner.
		@return User within the system - Internal or Business Partner Contact
	  */
	@Override
	public int getAD_User_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getBill_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setBill_BPartner(org.compiere.model.I_C_BPartner Bill_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_Bill_BPartner_ID, org.compiere.model.I_C_BPartner.class, Bill_BPartner);
	}

	/** Set Rechnungspartner.
		@param Bill_BPartner_ID
		Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_BPartner_ID (int Bill_BPartner_ID)
	{
		if (Bill_BPartner_ID < 1)
			set_Value (COLUMNNAME_Bill_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_Bill_BPartner_ID, Integer.valueOf(Bill_BPartner_ID));
	}

	/** Get Rechnungspartner.
		@return Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_BPartner_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getBill_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setBill_Location(org.compiere.model.I_C_BPartner_Location Bill_Location)
	{
		set_ValueFromPO(COLUMNNAME_Bill_Location_ID, org.compiere.model.I_C_BPartner_Location.class, Bill_Location);
	}

	/** Set Rechnungsstandort.
		@param Bill_Location_ID
		Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public void setBill_Location_ID (int Bill_Location_ID)
	{
		if (Bill_Location_ID < 1)
			set_Value (COLUMNNAME_Bill_Location_ID, null);
		else
			set_Value (COLUMNNAME_Bill_Location_ID, Integer.valueOf(Bill_Location_ID));
	}

	/** Get Rechnungsstandort.
		@return Standort des Geschäftspartners für die Rechnungsstellung
	  */
	@Override
	public int getBill_Location_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Bill_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner getC_BPartner() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class);
	}

	@Override
	public void setC_BPartner(org.compiere.model.I_C_BPartner C_BPartner)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.class, C_BPartner);
	}

	/** Set Geschäftspartner.
		@param C_BPartner_ID
		Bezeichnet einen Geschäftspartner
	  */
	@Override
	public void setC_BPartner_ID (int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/** Get Geschäftspartner.
		@return Bezeichnet einen Geschäftspartner
	  */
	@Override
	public int getC_BPartner_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_BPartner_Location getC_BPartner_Location() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class);
	}

	@Override
	public void setC_BPartner_Location(org.compiere.model.I_C_BPartner_Location C_BPartner_Location)
	{
		set_ValueFromPO(COLUMNNAME_C_BPartner_Location_ID, org.compiere.model.I_C_BPartner_Location.class, C_BPartner_Location);
	}

	/** Set Standort.
		@param C_BPartner_Location_ID
		Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public void setC_BPartner_Location_ID (int C_BPartner_Location_ID)
	{
		if (C_BPartner_Location_ID < 1)
			set_Value (COLUMNNAME_C_BPartner_Location_ID, null);
		else
			set_Value (COLUMNNAME_C_BPartner_Location_ID, Integer.valueOf(C_BPartner_Location_ID));
	}

	/** Get Standort.
		@return Identifiziert die (Liefer-) Adresse des Geschäftspartners
	  */
	@Override
	public int getC_BPartner_Location_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_Location_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_C_DocType getC_DocType() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class);
	}

	@Override
	public void setC_DocType(org.compiere.model.I_C_DocType C_DocType)
	{
		set_ValueFromPO(COLUMNNAME_C_DocType_ID, org.compiere.model.I_C_DocType.class, C_DocType);
	}

	/** Set Belegart.
		@param C_DocType_ID
		Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public void setC_DocType_ID (int C_DocType_ID)
	{
		if (C_DocType_ID < 0)
			set_Value (COLUMNNAME_C_DocType_ID, null);
		else
			set_Value (COLUMNNAME_C_DocType_ID, Integer.valueOf(C_DocType_ID));
	}

	/** Get Belegart.
		@return Belegart oder Verarbeitungsvorgaben
	  */
	@Override
	public int getC_DocType_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_DocType_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Kopien.
		@param Copies
		Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public void setCopies (int Copies)
	{
		set_Value (COLUMNNAME_Copies, Integer.valueOf(Copies));
	}

	/** Get Kopien.
		@return Anzahl der zu erstellenden/zu druckenden Exemplare
	  */
	@Override
	public int getCopies ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Copies);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Druck-Warteschlangendatensatz.
		@param C_Printing_Queue_ID Druck-Warteschlangendatensatz	  */
	@Override
	public void setC_Printing_Queue_ID (int C_Printing_Queue_ID)
	{
		if (C_Printing_Queue_ID < 1)
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, null);
		else
			set_ValueNoCheck (COLUMNNAME_C_Printing_Queue_ID, Integer.valueOf(C_Printing_Queue_ID));
	}

	/** Get Druck-Warteschlangendatensatz.
		@return Druck-Warteschlangendatensatz	  */
	@Override
	public int getC_Printing_Queue_ID ()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Printing_Queue_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Lieferdatum.
		@param DeliveryDate Lieferdatum	  */
	@Override
	public void setDeliveryDate (java.sql.Timestamp DeliveryDate)
	{
		set_Value (COLUMNNAME_DeliveryDate, DeliveryDate);
	}

	/** Get Lieferdatum.
		@return Lieferdatum	  */
	@Override
	public java.sql.Timestamp getDeliveryDate ()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_DeliveryDate);
	}

	/** Set Abweichender Rechnungspartner.
		@param IsDifferentInvoicingPartner Abweichender Rechnungspartner	  */
	@Override
	public void setIsDifferentInvoicingPartner (boolean IsDifferentInvoicingPartner)
	{
		throw new IllegalArgumentException ("IsDifferentInvoicingPartner is virtual column");	}

	/** Get Abweichender Rechnungspartner.
		@return Abweichender Rechnungspartner	  */
	@Override
	public boolean isDifferentInvoicingPartner ()
	{
		Object oo = get_Value(COLUMNNAME_IsDifferentInvoicingPartner);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Abw. Druck-Empfänger.
		@param IsPrintoutForOtherUser Abw. Druck-Empfänger	  */
	@Override
	public void setIsPrintoutForOtherUser (boolean IsPrintoutForOtherUser)
	{
		set_Value (COLUMNNAME_IsPrintoutForOtherUser, Boolean.valueOf(IsPrintoutForOtherUser));
	}

	/** Get Abw. Druck-Empfänger.
		@return Abw. Druck-Empfänger	  */
	@Override
	public boolean isPrintoutForOtherUser ()
	{
		Object oo = get_Value(COLUMNNAME_IsPrintoutForOtherUser);
		if (oo != null)
		{
			 if (oo instanceof Boolean)
				 return ((Boolean)oo).booleanValue();
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Warteschlangen-Aggregationsmerkmal.
		@param PrintingQueueAggregationKey Warteschlangen-Aggregationsmerkmal	  */
	@Override
	public void setPrintingQueueAggregationKey (java.lang.String PrintingQueueAggregationKey)
	{
		set_Value (COLUMNNAME_PrintingQueueAggregationKey, PrintingQueueAggregationKey);
	}

	/** Get Warteschlangen-Aggregationsmerkmal.
		@return Warteschlangen-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getPrintingQueueAggregationKey ()
	{
		return (java.lang.String)get_Value(COLUMNNAME_PrintingQueueAggregationKey);
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
}