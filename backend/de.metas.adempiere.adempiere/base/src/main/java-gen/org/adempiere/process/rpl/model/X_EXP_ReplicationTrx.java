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
package org.adempiere.process.rpl.model;

=======
// Generated Model - DO NOT CHANGE
package org.adempiere.process.rpl.model;

import javax.annotation.Nullable;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for EXP_ReplicationTrx
<<<<<<< HEAD
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_EXP_ReplicationTrx extends org.compiere.model.PO implements I_EXP_ReplicationTrx, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 1450402696L;

    /** Standard Constructor */
    public X_EXP_ReplicationTrx (Properties ctx, int EXP_ReplicationTrx_ID, String trxName)
    {
      super (ctx, EXP_ReplicationTrx_ID, trxName);
      /** if (EXP_ReplicationTrx_ID == 0)
        {
			setEXP_ReplicationTrx_ID (0);
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_EXP_ReplicationTrx (Properties ctx, ResultSet rs, String trxName)
=======
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_EXP_ReplicationTrx extends org.compiere.model.PO implements I_EXP_ReplicationTrx, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1145021183L;

    /** Standard Constructor */
    public X_EXP_ReplicationTrx (final Properties ctx, final int EXP_ReplicationTrx_ID, @Nullable final String trxName)
    {
      super (ctx, EXP_ReplicationTrx_ID, trxName);
    }

    /** Load Constructor */
    public X_EXP_ReplicationTrx (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
      StringBuffer sb = new StringBuffer ("X_EXP_ReplicationTrx[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Replikationstransaktion.
		@param EXP_ReplicationTrx_ID Replikationstransaktion	  */
	@Override
	public void setEXP_ReplicationTrx_ID (int EXP_ReplicationTrx_ID)
=======
	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public void setErrorMsg (final @Nullable java.lang.String ErrorMsg)
	{
		set_Value (COLUMNNAME_ErrorMsg, ErrorMsg);
	}

	@Override
	public java.lang.String getErrorMsg() 
	{
		return get_ValueAsString(COLUMNNAME_ErrorMsg);
	}

	@Override
	public void setEXP_ReplicationTrx_ID (final int EXP_ReplicationTrx_ID)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		if (EXP_ReplicationTrx_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrx_ID, null);
		else 
<<<<<<< HEAD
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrx_ID, Integer.valueOf(EXP_ReplicationTrx_ID));
	}

	/** Get Replikationstransaktion.
		@return Replikationstransaktion	  */
	@Override
	public int getEXP_ReplicationTrx_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_EXP_ReplicationTrx_ID);
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
=======
			set_ValueNoCheck (COLUMNNAME_EXP_ReplicationTrx_ID, EXP_ReplicationTrx_ID);
	}

	@Override
	public int getEXP_ReplicationTrx_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_EXP_ReplicationTrx_ID);
	}

	@Override
	public void setIsError (final boolean IsError)
	{
		set_Value (COLUMNNAME_IsError, IsError);
	}

	@Override
	public boolean isError() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsError);
	}

	@Override
	public void setIsReplicationTrxFinished (final boolean IsReplicationTrxFinished)
	{
		set_Value (COLUMNNAME_IsReplicationTrxFinished, IsReplicationTrxFinished);
	}

	@Override
	public boolean isReplicationTrxFinished() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReplicationTrxFinished);
	}

	@Override
	public void setName (final java.lang.String Name)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		set_Value (COLUMNNAME_Name, Name);
	}

<<<<<<< HEAD
	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	@Override
	public java.lang.String getName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Name);
=======
	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setNote (final @Nullable java.lang.String Note)
	{
		set_Value (COLUMNNAME_Note, Note);
	}

	@Override
	public java.lang.String getNote() 
	{
		return get_ValueAsString(COLUMNNAME_Note);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}