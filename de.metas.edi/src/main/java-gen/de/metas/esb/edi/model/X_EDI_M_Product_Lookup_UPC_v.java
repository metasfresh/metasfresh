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
package de.metas.esb.edi.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EDI_M_Product_Lookup_UPC_v
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EDI_M_Product_Lookup_UPC_v extends org.compiere.model.PO implements I_EDI_M_Product_Lookup_UPC_v, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -868088354L;

    /** Standard Constructor */
    public X_EDI_M_Product_Lookup_UPC_v (Properties ctx, int EDI_M_Product_Lookup_UPC_v_ID, String trxName)
    {
      super (ctx, EDI_M_Product_Lookup_UPC_v_ID, trxName);
      /** if (EDI_M_Product_Lookup_UPC_v_ID == 0)
        {
        } */
    }

    /** Load Constructor */
    public X_EDI_M_Product_Lookup_UPC_v (Properties ctx, ResultSet rs, String trxName)
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

	/** Set GLN.
		@param GLN GLN	  */
	@Override
	public void setGLN (java.lang.String GLN)
	{
		set_Value (COLUMNNAME_GLN, GLN);
	}

	/** Get GLN.
		@return GLN	  */
	@Override
	public java.lang.String getGLN () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_GLN);
	}
}