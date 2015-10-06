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
package de.metas.commission.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.POInfo;
import org.compiere.util.Env;

/** Generated Model for C_AdvComSponsorPoints_v1
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_AdvComSponsorPoints_v1 extends PO implements I_C_AdvComSponsorPoints_v1, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_AdvComSponsorPoints_v1 (Properties ctx, int C_AdvComSponsorPoints_v1_ID, String trxName)
    {
      super (ctx, C_AdvComSponsorPoints_v1_ID, trxName);
      /** if (C_AdvComSponsorPoints_v1_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_C_AdvComSponsorPoints_v1 (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 1 - Org 
      */
    protected int get_AccessLevel()
    {
      return accessLevel.intValue();
    }

    /** Load Meta Data */
    protected POInfo initPO (Properties ctx)
    {
      POInfo poi = POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
      return poi;
    }

    public String toString()
    {
      StringBuffer sb = new StringBuffer ("X_C_AdvComSponsorPoints_v1[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Ebenen Netto-UVP.
		@param AbsoluteDownlineVolume Ebenen Netto-UVP	  */
	public void setAbsoluteDownlineVolume (BigDecimal AbsoluteDownlineVolume)
	{
		set_Value (COLUMNNAME_AbsoluteDownlineVolume, AbsoluteDownlineVolume);
	}

	/** Get Ebenen Netto-UVP.
		@return Ebenen Netto-UVP	  */
	public BigDecimal getAbsoluteDownlineVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AbsoluteDownlineVolume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Netto-UVP.
		@param AbsolutePersonalVolume Netto-UVP	  */
	public void setAbsolutePersonalVolume (BigDecimal AbsolutePersonalVolume)
	{
		set_Value (COLUMNNAME_AbsolutePersonalVolume, AbsolutePersonalVolume);
	}

	/** Get Netto-UVP.
		@return Netto-UVP	  */
	public BigDecimal getAbsolutePersonalVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_AbsolutePersonalVolume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	public de.metas.commission.model.I_C_AdvComSystem getC_AdvComSystem() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_AdvComSystem)MTable.get(getCtx(), de.metas.commission.model.I_C_AdvComSystem.Table_Name)
			.getPO(getC_AdvComSystem_ID(), get_TrxName());	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public org.compiere.model.I_C_Period getC_Period() throws RuntimeException
    {
		return (org.compiere.model.I_C_Period)MTable.get(getCtx(), org.compiere.model.I_C_Period.Table_Name)
			.getPO(getC_Period_ID(), get_TrxName());	}

	/** Set Periode.
		@param C_Period_ID 
		Periode des Kalenders
	  */
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	/** Get Periode.
		@return Periode des Kalenders
	  */
	public int getC_Period_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Period_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public de.metas.commission.model.I_C_Sponsor getC_Sponsor() throws RuntimeException
    {
		return (de.metas.commission.model.I_C_Sponsor)MTable.get(getCtx(), de.metas.commission.model.I_C_Sponsor.Table_Name)
			.getPO(getC_Sponsor_ID(), get_TrxName());	}

	/** Set Sponsor.
		@param C_Sponsor_ID Sponsor	  */
	public void setC_Sponsor_ID (int C_Sponsor_ID)
	{
		if (C_Sponsor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_ID, Integer.valueOf(C_Sponsor_ID));
	}

	/** Get Sponsor.
		@return Sponsor	  */
	public int getC_Sponsor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Pers. Netto-UVP.
		@param PersonalVolume Pers. Netto-UVP	  */
	public void setPersonalVolume (BigDecimal PersonalVolume)
	{
		set_Value (COLUMNNAME_PersonalVolume, PersonalVolume);
	}

	/** Get Pers. Netto-UVP.
		@return Pers. Netto-UVP	  */
	public BigDecimal getPersonalVolume () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_PersonalVolume);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}
}