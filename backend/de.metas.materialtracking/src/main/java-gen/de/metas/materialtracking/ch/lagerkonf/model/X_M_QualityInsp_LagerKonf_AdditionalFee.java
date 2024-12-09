<<<<<<< HEAD
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

/** Generated Model for M_QualityInsp_LagerKonf_AdditionalFee
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_QualityInsp_LagerKonf_AdditionalFee extends org.compiere.model.PO implements I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -869690858L;

    /** Standard Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (Properties ctx, int M_QualityInsp_LagerKonf_AdditionalFee_ID, String trxName)
    {
      super (ctx, M_QualityInsp_LagerKonf_AdditionalFee_ID, trxName);
      /** if (M_QualityInsp_LagerKonf_AdditionalFee_ID == 0)
        {
			setAdditional_Fee_Amt_Per_UOM (Env.ZERO);
			setM_Product_ID (0);
			setM_QualityInsp_LagerKonf_AdditionalFee_ID (0);
			setM_QualityInsp_LagerKonf_Version_ID (0);
			setSeqNo (0);
// @SQL=SELECT COALESCE(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_Field WHERE M_QualityInsp_LagerKonf_ID=@M_QualityInsp_LagerKonf_ID@
        } */
    }

    /** Load Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (Properties ctx, ResultSet rs, String trxName)
=======
// Generated Model - DO NOT CHANGE
package de.metas.materialtracking.ch.lagerkonf.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_QualityInsp_LagerKonf_AdditionalFee
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_M_QualityInsp_LagerKonf_AdditionalFee extends org.compiere.model.PO implements I_M_QualityInsp_LagerKonf_AdditionalFee, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -468170247L;

    /** Standard Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (final Properties ctx, final int M_QualityInsp_LagerKonf_AdditionalFee_ID, @Nullable final String trxName)
    {
      super (ctx, M_QualityInsp_LagerKonf_AdditionalFee_ID, trxName);
    }

    /** Load Constructor */
    public X_M_QualityInsp_LagerKonf_AdditionalFee (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
    {
      super (ctx, rs, trxName);
    }


<<<<<<< HEAD
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
      StringBuilder sb = new StringBuilder ("X_M_QualityInsp_LagerKonf_AdditionalFee[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Beitrag pro Einheit.
		@param Additional_Fee_Amt_Per_UOM Beitrag pro Einheit	  */
	@Override
	public void setAdditional_Fee_Amt_Per_UOM (java.math.BigDecimal Additional_Fee_Amt_Per_UOM)
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setAdditional_Fee_Amt_Per_UOM (final BigDecimal Additional_Fee_Amt_Per_UOM)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Additional_Fee_Amt_Per_UOM, Additional_Fee_Amt_Per_UOM);
	}

<<<<<<< HEAD
	/** Get Beitrag pro Einheit.
		@return Beitrag pro Einheit	  */
	@Override
	public java.math.BigDecimal getAdditional_Fee_Amt_Per_UOM () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Additional_Fee_Amt_Per_UOM);
		if (bd == null)
			 return Env.ZERO;
		return bd;
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
=======
	@Override
	public BigDecimal getAdditional_Fee_Amt_Per_UOM() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Additional_Fee_Amt_Per_UOM);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	/** 
	 * ApplyFeeTo AD_Reference_ID=541889
	 * Reference name: QualityInsp_AdditionalFee_ApplyFeeTo
	 */
	public static final int APPLYFEETO_AD_Reference_ID=541889;
	/** Produced Total Without By Products = ProducedTotalWithoutByProducts */
	public static final String APPLYFEETO_ProducedTotalWithoutByProducts = "ProducedTotalWithoutByProducts";
	/** Raw = Raw */
	public static final String APPLYFEETO_Raw = "Raw";
	@Override
	public void setApplyFeeTo (final String ApplyFeeTo)
	{
		set_Value (COLUMNNAME_ApplyFeeTo, ApplyFeeTo);
	}

	@Override
	public String getApplyFeeTo() 
	{
		return get_ValueAsString(COLUMNNAME_ApplyFeeTo);
	}

	@Override
	public void setM_Product_ID (final int M_Product_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
<<<<<<< HEAD
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

	/** Set Zusätzlicher Beitrag.
		@param M_QualityInsp_LagerKonf_AdditionalFee_ID Zusätzlicher Beitrag	  */
	@Override
	public void setM_QualityInsp_LagerKonf_AdditionalFee_ID (int M_QualityInsp_LagerKonf_AdditionalFee_ID)
=======
			set_Value (COLUMNNAME_M_Product_ID, M_Product_ID);
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_QualityInsp_LagerKonf_AdditionalFee_ID (final int M_QualityInsp_LagerKonf_AdditionalFee_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_QualityInsp_LagerKonf_AdditionalFee_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID, Integer.valueOf(M_QualityInsp_LagerKonf_AdditionalFee_ID));
	}

	/** Get Zusätzlicher Beitrag.
		@return Zusätzlicher Beitrag	  */
	@Override
	public int getM_QualityInsp_LagerKonf_AdditionalFee_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version() throws RuntimeException
=======
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID, M_QualityInsp_LagerKonf_AdditionalFee_ID);
	}

	@Override
	public int getM_QualityInsp_LagerKonf_AdditionalFee_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_QualityInsp_LagerKonf_AdditionalFee_ID);
	}

	@Override
	public I_M_QualityInsp_LagerKonf_Version getM_QualityInsp_LagerKonf_Version()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class);
	}

	@Override
<<<<<<< HEAD
	public void setM_QualityInsp_LagerKonf_Version(I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version)
=======
	public void setM_QualityInsp_LagerKonf_Version(final I_M_QualityInsp_LagerKonf_Version M_QualityInsp_LagerKonf_Version)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_ValueFromPO(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, I_M_QualityInsp_LagerKonf_Version.class, M_QualityInsp_LagerKonf_Version);
	}

<<<<<<< HEAD
	/** Set Lagerkonferenz-Version.
		@param M_QualityInsp_LagerKonf_Version_ID Lagerkonferenz-Version	  */
	@Override
	public void setM_QualityInsp_LagerKonf_Version_ID (int M_QualityInsp_LagerKonf_Version_ID)
=======
	@Override
	public void setM_QualityInsp_LagerKonf_Version_ID (final int M_QualityInsp_LagerKonf_Version_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (M_QualityInsp_LagerKonf_Version_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, null);
		else 
<<<<<<< HEAD
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

	/** Set Reihenfolge.
		@param SeqNo 
		Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Reihenfolge.
		@return Zur Bestimmung der Reihenfolge der Einträge; die kleinste Zahl kommt zuerst
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
=======
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID, M_QualityInsp_LagerKonf_Version_ID);
	}

	@Override
	public int getM_QualityInsp_LagerKonf_Version_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_QualityInsp_LagerKonf_Version_ID);
	}

	@Override
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}