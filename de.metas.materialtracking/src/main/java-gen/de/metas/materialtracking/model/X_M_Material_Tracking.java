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
package de.metas.materialtracking.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.util.Env;

/**
 * Generated Model for M_Material_Tracking
 * 
 * @author Adempiere (generated)
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Tracking extends org.compiere.model.PO implements I_M_Material_Tracking, org.compiere.model.I_Persistent
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1114458830L;

	/** Standard Constructor */
	public X_M_Material_Tracking(Properties ctx, int M_Material_Tracking_ID, String trxName)
	{
		super(ctx, M_Material_Tracking_ID, trxName);
		/**
		 * if (M_Material_Tracking_ID == 0)
		 * {
		 * setC_BPartner_ID (0);
		 * setLot (null);
		 * setM_Material_Tracking_ID (0);
		 * setM_Product_ID (0);
		 * setProcessed (false);
		 * // N
		 * setQtyIssued (Env.ZERO);
		 * // 0
		 * setQtyReceived (Env.ZERO);
		 * // 0
		 * setValidFrom (new Timestamp( System.currentTimeMillis() ));
		 * }
		 */
	}

	/** Load Constructor */
	public X_M_Material_Tracking(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		org.compiere.model.POInfo poi = org.compiere.model.POInfo.getPOInfo(ctx, Table_Name, get_TrxName());
		return poi;
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

	/**
	 * Set Geschäftspartner.
	 * 
	 * @param C_BPartner_ID
	 *            Bezeichnet einen Geschäftspartner
	 */
	@Override
	public void setC_BPartner_ID(int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1)
			set_Value(COLUMNNAME_C_BPartner_ID, null);
		else
			set_Value(COLUMNNAME_C_BPartner_ID, Integer.valueOf(C_BPartner_ID));
	}

	/**
	 * Get Geschäftspartner.
	 * 
	 * @return Bezeichnet einen Geschäftspartner
	 */
	@Override
	public int getC_BPartner_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_BPartner_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Beschreibung.
	 * 
	 * @param Description Beschreibung
	 */
	@Override
	public void setDescription(java.lang.String Description)
	{
		set_Value(COLUMNNAME_Description, Description);
	}

	/**
	 * Get Beschreibung.
	 * 
	 * @return Beschreibung
	 */
	@Override
	public java.lang.String getDescription()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/**
	 * Set Los-Nr..
	 * 
	 * @param Lot
	 *            Los-Nummer (alphanumerisch)
	 */
	@Override
	public void setLot(java.lang.String Lot)
	{
		set_Value(COLUMNNAME_Lot, Lot);
	}

	/**
	 * Get Los-Nr..
	 * 
	 * @return Los-Nummer (alphanumerisch)
	 */
	@Override
	public java.lang.String getLot()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Lot);
	}

	@Override
	public org.compiere.model.I_M_AttributeValue getM_AttributeValue() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class);
	}

	@Override
	public void setM_AttributeValue(org.compiere.model.I_M_AttributeValue M_AttributeValue)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeValue_ID, org.compiere.model.I_M_AttributeValue.class, M_AttributeValue);
	}

	/**
	 * Set Merkmals-Wert.
	 * 
	 * @param M_AttributeValue_ID
	 *            Product Attribute Value
	 */
	@Override
	public void setM_AttributeValue_ID(int M_AttributeValue_ID)
	{
		if (M_AttributeValue_ID < 1)
			set_Value(COLUMNNAME_M_AttributeValue_ID, null);
		else
			set_Value(COLUMNNAME_M_AttributeValue_ID, Integer.valueOf(M_AttributeValue_ID));
	}

	/**
	 * Get Merkmals-Wert.
	 * 
	 * @return Product Attribute Value
	 */
	@Override
	public int getM_AttributeValue_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeValue_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Material-Vorgang-ID.
	 * 
	 * @param M_Material_Tracking_ID Material-Vorgang-ID
	 */
	@Override
	public void setM_Material_Tracking_ID(int M_Material_Tracking_ID)
	{
		if (M_Material_Tracking_ID < 1)
			set_ValueNoCheck(COLUMNNAME_M_Material_Tracking_ID, null);
		else
			set_ValueNoCheck(COLUMNNAME_M_Material_Tracking_ID, Integer.valueOf(M_Material_Tracking_ID));
	}

	/**
	 * Get Material-Vorgang-ID.
	 * 
	 * @return Material-Vorgang-ID
	 */
	@Override
	public int getM_Material_Tracking_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_M_Product getM_Product() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class);
	}

	@Override
	public void setM_Product(org.compiere.model.I_M_Product M_Product)
	{
		set_ValueFromPO(COLUMNNAME_M_Product_ID, org.compiere.model.I_M_Product.class, M_Product);
	}

	/**
	 * Set Produkt.
	 * 
	 * @param M_Product_ID
	 *            Produkt, Leistung, Artikel
	 */
	@Override
	public void setM_Product_ID(int M_Product_ID)
	{
		if (M_Product_ID < 1)
			set_Value(COLUMNNAME_M_Product_ID, null);
		else
			set_Value(COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/**
	 * Get Produkt.
	 * 
	 * @return Produkt, Leistung, Artikel
	 */
	@Override
	public int getM_Product_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Verarbeitet.
	 * 
	 * @param Processed
	 *            Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public void setProcessed(boolean Processed)
	{
		set_Value(COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	/**
	 * Get Verarbeitet.
	 * 
	 * @return Checkbox sagt aus, ob der Beleg verarbeitet wurde.
	 */
	@Override
	public boolean isProcessed()
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

	/**
	 * Set Qty Issued.
	 * 
	 * @param QtyIssued Qty Issued
	 */
	@Override
	public void setQtyIssued(java.math.BigDecimal QtyIssued)
	{
		set_Value(COLUMNNAME_QtyIssued, QtyIssued);
	}

	/**
	 * Get Qty Issued.
	 * 
	 * @return Qty Issued
	 */
	@Override
	public java.math.BigDecimal getQtyIssued()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyIssued);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	/**
	 * Set Empfangene Menge.
	 * 
	 * @param QtyReceived
	 *            Empfangene Menge
	 */
	@Override
	public void setQtyReceived(java.math.BigDecimal QtyReceived)
	{
		set_Value(COLUMNNAME_QtyReceived, QtyReceived);
	}

	/**
	 * Get Empfangene Menge.
	 * 
	 * @return Empfangene Menge
	 */
	@Override
	public java.math.BigDecimal getQtyReceived()
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReceived);
		if (bd == null)
			return Env.ZERO;
		return bd;
	}

	@Override
	public org.compiere.model.I_AD_User getSalesRep() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setSalesRep(org.compiere.model.I_AD_User SalesRep)
	{
		set_ValueFromPO(COLUMNNAME_SalesRep_ID, org.compiere.model.I_AD_User.class, SalesRep);
	}

	/**
	 * Set Sales Representative.
	 * 
	 * @param SalesRep_ID
	 *            Sales Representative or Company Agent
	 */
	@Override
	public void setSalesRep_ID(int SalesRep_ID)
	{
		if (SalesRep_ID < 1)
			set_Value(COLUMNNAME_SalesRep_ID, null);
		else
			set_Value(COLUMNNAME_SalesRep_ID, Integer.valueOf(SalesRep_ID));
	}

	/**
	 * Get Sales Representative.
	 * 
	 * @return Sales Representative or Company Agent
	 */
	@Override
	public int getSalesRep_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SalesRep_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

	/**
	 * Set Gültig ab.
	 * 
	 * @param ValidFrom
	 *            Gültig ab inklusiv (erster Tag)
	 */
	@Override
	public void setValidFrom(java.sql.Timestamp ValidFrom)
	{
		set_Value(COLUMNNAME_ValidFrom, ValidFrom);
	}

	/**
	 * Get Gültig ab.
	 * 
	 * @return Gültig ab inklusiv (erster Tag)
	 */
	@Override
	public java.sql.Timestamp getValidFrom()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidFrom);
	}

	/**
	 * Set Gültig bis.
	 * 
	 * @param ValidTo
	 *            Gültig bis inklusiv (letzter Tag)
	 */
	@Override
	public void setValidTo(java.sql.Timestamp ValidTo)
	{
		set_Value(COLUMNNAME_ValidTo, ValidTo);
	}

	/**
	 * Get Gültig bis.
	 * 
	 * @return Gültig bis inklusiv (letzter Tag)
	 */
	@Override
	public java.sql.Timestamp getValidTo()
	{
		return (java.sql.Timestamp)get_Value(COLUMNNAME_ValidTo);
	}

	@Override
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	/**
	 * Set Pauschale - Vertragsperiode.
	 * 
	 * @param C_Flatrate_Term_ID Pauschale - Vertragsperiode
	 */
	@Override
	public void setC_Flatrate_Term_ID(int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1)
			set_Value(COLUMNNAME_C_Flatrate_Term_ID, null);
		else
			set_Value(COLUMNNAME_C_Flatrate_Term_ID, Integer.valueOf(C_Flatrate_Term_ID));
	}

	/**
	 * Get Pauschale - Vertragsperiode.
	 * 
	 * @return Pauschale - Vertragsperiode
	 */
	@Override
	public int getC_Flatrate_Term_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Flatrate_Term_ID);
		if (ii == null)
			return 0;
		return ii.intValue();
	}

}