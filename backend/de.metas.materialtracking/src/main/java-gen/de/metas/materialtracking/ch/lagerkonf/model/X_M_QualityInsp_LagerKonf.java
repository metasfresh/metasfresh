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

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_QualityInsp_LagerKonf
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_QualityInsp_LagerKonf extends org.compiere.model.PO implements I_M_QualityInsp_LagerKonf, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1359568399L;

    /** Standard Constructor */
    public X_M_QualityInsp_LagerKonf (Properties ctx, int M_QualityInsp_LagerKonf_ID, String trxName)
    {
      super (ctx, M_QualityInsp_LagerKonf_ID, trxName);
      /** if (M_QualityInsp_LagerKonf_ID == 0)
        {
			setM_QualityInsp_LagerKonf_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_M_QualityInsp_LagerKonf (Properties ctx, ResultSet rs, String trxName)
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
      StringBuilder sb = new StringBuilder ("X_M_QualityInsp_LagerKonf[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Lagerkonferenz.
		@param M_QualityInsp_LagerKonf_ID Lagerkonferenz	  */
	@Override
	public void setM_QualityInsp_LagerKonf_ID (int M_QualityInsp_LagerKonf_ID)
	{
		if (M_QualityInsp_LagerKonf_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_QualityInsp_LagerKonf_ID, Integer.valueOf(M_QualityInsp_LagerKonf_ID));
	}

	/** Get Lagerkonferenz.
		@return Lagerkonferenz	  */
	@Override
	public int getM_QualityInsp_LagerKonf_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_M_QualityInsp_LagerKonf_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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
}