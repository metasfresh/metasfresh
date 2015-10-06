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

import java.sql.ResultSet;
import java.util.Properties;

import org.compiere.model.I_Persistent;
import org.compiere.model.PO;
import org.compiere.model.POInfo;

/** Generated Model for C_IncidentLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_C_IncidentLine extends PO implements I_C_IncidentLine, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20120120L;

    /** Standard Constructor */
    public X_C_IncidentLine (Properties ctx, int C_IncidentLine_ID, String trxName)
    {
      super (ctx, C_IncidentLine_ID, trxName);
      /** if (C_IncidentLine_ID == 0)
        {
			setC_IncidentLine_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_IncidentLine (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 3 - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_C_IncidentLine[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set C_IncidentLine_ID.
		@param C_IncidentLine_ID C_IncidentLine_ID	  */
	public void setC_IncidentLine_ID (int C_IncidentLine_ID)
	{
		if (C_IncidentLine_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_IncidentLine_ID, Integer.valueOf(C_IncidentLine_ID));
	}

	/** Get C_IncidentLine_ID.
		@return C_IncidentLine_ID	  */
	public int getC_IncidentLine_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_IncidentLine_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}