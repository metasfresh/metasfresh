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
package de.metas.async.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_Queue_Processor_Assign
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_Queue_Processor_Assign extends org.compiere.model.PO implements I_C_Queue_Processor_Assign, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1059089867L;

    /** Standard Constructor */
    public X_C_Queue_Processor_Assign (Properties ctx, int C_Queue_Processor_Assign_ID, String trxName)
    {
      super (ctx, C_Queue_Processor_Assign_ID, trxName);
      /** if (C_Queue_Processor_Assign_ID == 0)
        {
			setC_Queue_PackageProcessor_ID (0);
			setC_Queue_Processor_Assign_ID (0);
			setC_Queue_Processor_ID (0);
        } */
    }

    /** Load Constructor */
    public X_C_Queue_Processor_Assign (Properties ctx, ResultSet rs, String trxName)
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
	public de.metas.async.model.I_C_Queue_PackageProcessor getC_Queue_PackageProcessor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class);
	}

	@Override
	public void setC_Queue_PackageProcessor(de.metas.async.model.I_C_Queue_PackageProcessor C_Queue_PackageProcessor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_PackageProcessor_ID, de.metas.async.model.I_C_Queue_PackageProcessor.class, C_Queue_PackageProcessor);
	}

	/** Set WorkPackage Processor.
		@param C_Queue_PackageProcessor_ID WorkPackage Processor	  */
	@Override
	public void setC_Queue_PackageProcessor_ID (int C_Queue_PackageProcessor_ID)
	{
		if (C_Queue_PackageProcessor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_PackageProcessor_ID, Integer.valueOf(C_Queue_PackageProcessor_ID));
	}

	/** Get WorkPackage Processor.
		@return WorkPackage Processor	  */
	@Override
	public int getC_Queue_PackageProcessor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_PackageProcessor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Assigned Workpackage Processors.
		@param C_Queue_Processor_Assign_ID Assigned Workpackage Processors	  */
	@Override
	public void setC_Queue_Processor_Assign_ID (int C_Queue_Processor_Assign_ID)
	{
		if (C_Queue_Processor_Assign_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Processor_Assign_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Processor_Assign_ID, Integer.valueOf(C_Queue_Processor_Assign_ID));
	}

	/** Get Assigned Workpackage Processors.
		@return Assigned Workpackage Processors	  */
	@Override
	public int getC_Queue_Processor_Assign_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Processor_Assign_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.async.model.I_C_Queue_Processor getC_Queue_Processor() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Queue_Processor_ID, de.metas.async.model.I_C_Queue_Processor.class);
	}

	@Override
	public void setC_Queue_Processor(de.metas.async.model.I_C_Queue_Processor C_Queue_Processor)
	{
		set_ValueFromPO(COLUMNNAME_C_Queue_Processor_ID, de.metas.async.model.I_C_Queue_Processor.class, C_Queue_Processor);
	}

	/** Set Queue Processor Definition.
		@param C_Queue_Processor_ID Queue Processor Definition	  */
	@Override
	public void setC_Queue_Processor_ID (int C_Queue_Processor_ID)
	{
		if (C_Queue_Processor_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Processor_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Queue_Processor_ID, Integer.valueOf(C_Queue_Processor_ID));
	}

	/** Get Queue Processor Definition.
		@return Queue Processor Definition	  */
	@Override
	public int getC_Queue_Processor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Queue_Processor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}