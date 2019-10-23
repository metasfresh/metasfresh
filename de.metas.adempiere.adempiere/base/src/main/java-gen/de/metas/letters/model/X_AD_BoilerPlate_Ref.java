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
package de.metas.letters.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_BoilerPlate_Ref
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_BoilerPlate_Ref extends org.compiere.model.PO implements I_AD_BoilerPlate_Ref, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 484631826L;

    /** Standard Constructor */
    public X_AD_BoilerPlate_Ref (Properties ctx, int AD_BoilerPlate_Ref_ID, String trxName)
    {
      super (ctx, AD_BoilerPlate_Ref_ID, trxName);
      /** if (AD_BoilerPlate_Ref_ID == 0)
        {
			setAD_BoilerPlate_ID (0);
			setRef_BoilerPlate_ID (0);
        } */
    }

    /** Load Constructor */
    public X_AD_BoilerPlate_Ref (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_BoilerPlate_Ref[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public de.metas.letters.model.I_AD_BoilerPlate getAD_BoilerPlate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class);
	}

	@Override
	public void setAD_BoilerPlate(de.metas.letters.model.I_AD_BoilerPlate AD_BoilerPlate)
	{
		set_ValueFromPO(COLUMNNAME_AD_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class, AD_BoilerPlate);
	}

	/** Set Textbaustein.
		@param AD_BoilerPlate_ID Textbaustein	  */
	@Override
	public void setAD_BoilerPlate_ID (int AD_BoilerPlate_ID)
	{
		if (AD_BoilerPlate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_BoilerPlate_ID, Integer.valueOf(AD_BoilerPlate_ID));
	}

	/** Get Textbaustein.
		@return Textbaustein	  */
	@Override
	public int getAD_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.letters.model.I_AD_BoilerPlate getRef_BoilerPlate() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_Ref_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class);
	}

	@Override
	public void setRef_BoilerPlate(de.metas.letters.model.I_AD_BoilerPlate Ref_BoilerPlate)
	{
		set_ValueFromPO(COLUMNNAME_Ref_BoilerPlate_ID, de.metas.letters.model.I_AD_BoilerPlate.class, Ref_BoilerPlate);
	}

	/** Set Referenced template.
		@param Ref_BoilerPlate_ID Referenced template	  */
	@Override
	public void setRef_BoilerPlate_ID (int Ref_BoilerPlate_ID)
	{
		if (Ref_BoilerPlate_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_Ref_BoilerPlate_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_Ref_BoilerPlate_ID, Integer.valueOf(Ref_BoilerPlate_ID));
	}

	/** Get Referenced template.
		@return Referenced template	  */
	@Override
	public int getRef_BoilerPlate_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_Ref_BoilerPlate_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}