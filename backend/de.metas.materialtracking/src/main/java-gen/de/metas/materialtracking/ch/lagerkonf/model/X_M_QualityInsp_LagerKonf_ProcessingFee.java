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

/** Generated Model for M_QualityInsp_LagerKonf_ProcessingFee
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_QualityInsp_LagerKonf_ProcessingFee extends org.compiere.model.PO implements I_M_QualityInsp_LagerKonf_ProcessingFee, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1136705644L;

    /** Standard Constructor */
    public X_M_QualityInsp_LagerKonf_ProcessingFee (Properties ctx, int M_QualityInsp_LagerKonf_ProcessingFee_ID, String trxName)
    {
      super (ctx, M_QualityInsp_LagerKonf_ProcessingFee_ID, trxName);
      /** if (M_QualityInsp_LagerKonf_ProcessingFee_ID == 0)
        {
			setC_UOM_ID (0);
			setM_QualityInsp_LagerKonf_ProcessingFee_ID (0);
			setM_QualityInsp_LagerKonf_Version_ID (0);
			setPercentFrom (Env.ZERO);
			setProcessing_Fee_Amt_Per_UOM (Env.ZERO);
        } */
    }

    /** Load Constructor */
    public X_M_QualityInsp_LagerKonf_ProcessingFee (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_QualityInsp_LagerKonf_ProcessingFee[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_C_UOM getC_UOM() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class);
	}

	@Override
	public void setC_UOM(org.compiere.model.I_C_UOM C_UOM)
	{
		set_ValueFromPO(COLUMNNAME_C_UOM_ID, org.compiere.model.I_C_UOM.class, C_UOM);
	}

	/** Set Maßeinheit.
		@param C_UOM_ID 
		Maßeinheit
	  */
	@Override
	public void setC_UOM_ID (int C_UOM_ID)
	{
		if (C_UOM_ID < 1) 
			set_Value (COLUMNNAME_C_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_C_UOM_ID, Integer.valueOf(C_UOM_ID));
	}

	/** Get Maßeinheit.
		@return Maßeinheit
	  */
	@Override
	public int getC_UOM_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_UOM_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Verarbeitungskosten (Sortierkosten).
		@param M_QualityInsp_LagerKonf_ProcessingFee_ID Verarbeitungskosten (Sortierkosten)	  */
	@Override
	public void setM_QualityInsp_LagerKonf_ProcessingFee_ID (int M_QualityInsp_LagerKonf_ProcessingFee_ID)
	{
		if (M_QualityInsp_LagerKonf_ProcessingFee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_ProcessingFee_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_ProcessingFee_ID, Integer.valueOf(M_QualityInsp_LagerKonf_ProcessingFee_ID));
	}

	/** Get Verarbeitungskosten (Sortierkosten).
		@return Verarbeitungskosten (Sortierkosten)	  */
	@Override
	public int getM_QualityInsp_LagerKonf_ProcessingFee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_QualityInsp_LagerKonf_ProcessingFee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class);
	}

	@Override
	public void setM_QualityInsp_LagerKonf_Version(I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version)
	{
		set_ValueFromPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class, M_QualityInsp_LagerKonf_Version);
	}

	/** Set Lagerkonferenz-Version.
		@param M_QualityInsp_LagerKonf_Version_ID Lagerkonferenz-Version	  */
	@Override
	public void setM_QualityInsp_LagerKonf_Version_ID (int M_QualityInsp_LagerKonf_Version_ID)
	{
		if (M_QualityInsp_LagerKonf_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, Integer.valueOf(M_QualityInsp_LagerKonf_Version_ID));
	}

	/** Get Lagerkonferenz-Version.
		@return Lagerkonferenz-Version	  */
	@Override
	public int getM_QualityInsp_LagerKonf_Version_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set % ab.
		@param PercentFrom % ab	  */
	@Override
	public void setPercentFrom (java.math.BigDecimal PercentFrom)
	{
		set_Value (COLUMNNAME_PercentFrom, PercentFrom);
	}

	/** Get % ab.
		@return % ab	  */
	@Override
	public java.math.BigDecimal getPercentFrom () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PercentFrom);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Betrag pro Einheit.
		@param Processing_Fee_Amt_Per_UOM Betrag pro Einheit	  */
	@Override
	public void setProcessing_Fee_Amt_Per_UOM (java.math.BigDecimal Processing_Fee_Amt_Per_UOM)
	{
		set_Value (COLUMNNAME_Processing_Fee_Amt_Per_UOM, Processing_Fee_Amt_Per_UOM);
	}

	/** Get Betrag pro Einheit.
		@return Betrag pro Einheit	  */
	@Override
	public java.math.BigDecimal getProcessing_Fee_Amt_Per_UOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Processing_Fee_Amt_Per_UOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}