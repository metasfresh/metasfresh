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
package de.metas.materialtracking.ch.lagerkonf.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for M_Material_Tracking_Report_Line
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Material_Tracking_Report_Line extends org.compiere.model.PO implements I_M_Material_Tracking_Report_Line, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 47217588L;

    /** Standard Constructor */
    public X_M_Material_Tracking_Report_Line (Properties ctx, int M_Material_Tracking_Report_Line_ID, String trxName)
    {
      super (ctx, M_Material_Tracking_Report_Line_ID, trxName);
      /** if (M_Material_Tracking_Report_Line_ID == 0)
        {
			setM_Material_Tracking_Report_ID (0);
			setM_Material_Tracking_Report_Line_ID (0);
			setM_Product_ID (0);
        } */
    }

    /** Load Constructor */
    public X_M_Material_Tracking_Report_Line (Properties ctx, ResultSet rs, String trxName)
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

	/** Set Differenz.
		@param DifferenceQty 
		Unterschiedsmenge
	  */
	@Override
	public void setDifferenceQty (java.math.BigDecimal DifferenceQty)
	{
		set_Value (COLUMNNAME_DifferenceQty, DifferenceQty);
	}

	/** Get Differenz.
		@return Unterschiedsmenge
	  */
	@Override
	public java.math.BigDecimal getDifferenceQty () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_DifferenceQty);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Zeilen-Aggregationsmerkmal.
		@param LineAggregationKey Zeilen-Aggregationsmerkmal	  */
	@Override
	public void setLineAggregationKey (java.lang.String LineAggregationKey)
	{
		set_Value (COLUMNNAME_LineAggregationKey, LineAggregationKey);
	}

	/** Get Zeilen-Aggregationsmerkmal.
		@return Zeilen-Aggregationsmerkmal	  */
	@Override
	public java.lang.String getLineAggregationKey () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LineAggregationKey);
	}

	@Override
	public org.compiere.model.I_M_AttributeSetInstance getM_AttributeSetInstance() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class);
	}

	@Override
	public void setM_AttributeSetInstance(org.compiere.model.I_M_AttributeSetInstance M_AttributeSetInstance)
	{
		set_ValueFromPO(COLUMNNAME_M_AttributeSetInstance_ID, org.compiere.model.I_M_AttributeSetInstance.class, M_AttributeSetInstance);
	}

	/** Set Ausprägung Merkmals-Satz.
		@param M_AttributeSetInstance_ID 
		Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public void setM_AttributeSetInstance_ID (int M_AttributeSetInstance_ID)
	{
		if (M_AttributeSetInstance_ID < 0) 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, null);
		else 
			set_Value (COLUMNNAME_M_AttributeSetInstance_ID, Integer.valueOf(M_AttributeSetInstance_ID));
	}

	/** Get Ausprägung Merkmals-Satz.
		@return Instanz des Merkmals-Satzes zum Produkt
	  */
	@Override
	public int getM_AttributeSetInstance_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_AttributeSetInstance_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report getM_Material_Tracking_Report() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_Material_Tracking_Report_ID, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report.class);
	}

	@Override
	public void setM_Material_Tracking_Report(de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report M_Material_Tracking_Report)
	{
		set_ValueFromPO(COLUMNNAME_M_Material_Tracking_Report_ID, de.metas.materialtracking.ch.lagerkonf.model.I_M_Material_Tracking_Report.class, M_Material_Tracking_Report);
	}

	/** Set M_Material_Tracking_Report.
		@param M_Material_Tracking_Report_ID M_Material_Tracking_Report	  */
	@Override
	public void setM_Material_Tracking_Report_ID (int M_Material_Tracking_Report_ID)
	{
		if (M_Material_Tracking_Report_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_ID, Integer.valueOf(M_Material_Tracking_Report_ID));
	}

	/** Get M_Material_Tracking_Report.
		@return M_Material_Tracking_Report	  */
	@Override
	public int getM_Material_Tracking_Report_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Report_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set M_Material_Tracking_Report_Line.
		@param M_Material_Tracking_Report_Line_ID M_Material_Tracking_Report_Line	  */
	@Override
	public void setM_Material_Tracking_Report_Line_ID (int M_Material_Tracking_Report_Line_ID)
	{
		if (M_Material_Tracking_Report_Line_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Material_Tracking_Report_Line_ID, Integer.valueOf(M_Material_Tracking_Report_Line_ID));
	}

	/** Get M_Material_Tracking_Report_Line.
		@return M_Material_Tracking_Report_Line	  */
	@Override
	public int getM_Material_Tracking_Report_Line_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Material_Tracking_Report_Line_ID);
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

	/** Set Produkt.
		@param M_Product_ID 
		Produkt, Leistung, Artikel
	  */
	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	/** Get Produkt.
		@return Produkt, Leistung, Artikel
	  */
	@Override
	public int getM_Product_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_Product_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Ausgelagerte Menge.
		@param QtyIssued Ausgelagerte Menge	  */
	@Override
	public void setQtyIssued (java.math.BigDecimal QtyIssued)
	{
		set_Value (COLUMNNAME_QtyIssued, QtyIssued);
	}

	/** Get Ausgelagerte Menge.
		@return Ausgelagerte Menge	  */
	@Override
	public java.math.BigDecimal getQtyIssued () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyIssued);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Empfangene Menge.
		@param QtyReceived Empfangene Menge	  */
	@Override
	public void setQtyReceived (java.math.BigDecimal QtyReceived)
	{
		set_Value (COLUMNNAME_QtyReceived, QtyReceived);
	}

	/** Get Empfangene Menge.
		@return Empfangene Menge	  */
	@Override
	public java.math.BigDecimal getQtyReceived () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_QtyReceived);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}