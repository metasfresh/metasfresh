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
package org.adempiere.ad.migration.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Migration
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_AD_Migration extends org.compiere.model.PO implements I_AD_Migration, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 283394035L;

    /** Standard Constructor */
    public X_AD_Migration (Properties ctx, int AD_Migration_ID, String trxName)
    {
      super (ctx, AD_Migration_ID, trxName);
      /** if (AD_Migration_ID == 0)
        {
			setAD_Migration_ID (0);
			setEntityType (null);
// 'U'
			setIsDeferredConstraints (false);
// N
			setName (null);
			setSeqNo (0);
        } */
    }

    /** Load Constructor */
    public X_AD_Migration (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_AD_Migration[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Migration.
		@param AD_Migration_ID 
		Migration change management.
	  */
	@Override
	public void setAD_Migration_ID (int AD_Migration_ID)
	{
		if (AD_Migration_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Migration_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Migration_ID, Integer.valueOf(AD_Migration_ID));
	}

	/** Get Migration.
		@return Migration change management.
	  */
	@Override
	public int getAD_Migration_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Migration_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * Apply AD_Reference_ID=53312
	 * Reference name: AD_Migration Apply/Rollback
	 */
	public static final int APPLY_AD_Reference_ID=53312;
	/** Apply = A */
	public static final String APPLY_Apply = "A";
	/** Rollback = R */
	public static final String APPLY_Rollback = "R";
	/** Set Apply.
		@param Apply 
		Apply migration
	  */
	@Override
	public void setApply (java.lang.String Apply)
	{

		set_Value (COLUMNNAME_Apply, Apply);
	}

	/** Get Apply.
		@return Apply migration
	  */
	@Override
	public java.lang.String getApply () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Apply);
	}

	/** Set Comments.
		@param Comments 
		Comments or additional information
	  */
	@Override
	public void setComments (java.lang.String Comments)
	{
		set_Value (COLUMNNAME_Comments, Comments);
	}

	/** Get Comments.
		@return Comments or additional information
	  */
	@Override
	public java.lang.String getComments () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Comments);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	/** Set Entity Type.
		@param EntityType 
		Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public void setEntityType (java.lang.String EntityType)
	{

		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	/** Get Entity Type.
		@return Dictionary Entity Type; Determines ownership and synchronization
	  */
	@Override
	public java.lang.String getEntityType () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_EntityType);
	}

	/** Set Export to XML.
		@param ExportXML 
		Export this record to XML
	  */
	@Override
	public void setExportXML (java.lang.String ExportXML)
	{
		set_Value (COLUMNNAME_ExportXML, ExportXML);
	}

	/** Get Export to XML.
		@return Export this record to XML
	  */
	@Override
	public java.lang.String getExportXML () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ExportXML);
	}

	/** Set Defer Constraints.
		@param IsDeferredConstraints Defer Constraints	  */
	@Override
	public void setIsDeferredConstraints (boolean IsDeferredConstraints)
	{
		set_Value (COLUMNNAME_IsDeferredConstraints, Boolean.valueOf(IsDeferredConstraints));
	}

	/** Get Defer Constraints.
		@return Defer Constraints	  */
	@Override
	public boolean isDeferredConstraints () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeferredConstraints);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
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

	/** Set Release No.
		@param ReleaseNo 
		Internal Release Number
	  */
	@Override
	public void setReleaseNo (java.lang.String ReleaseNo)
	{
		set_Value (COLUMNNAME_ReleaseNo, ReleaseNo);
	}

	/** Get Release No.
		@return Internal Release Number
	  */
	@Override
	public java.lang.String getReleaseNo () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReleaseNo);
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	@Override
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	@Override
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** 
	 * StatusCode AD_Reference_ID=53311
	 * Reference name: AD_Migration Status
	 */
	public static final int STATUSCODE_AD_Reference_ID=53311;
	/** Applied = A */
	public static final String STATUSCODE_Applied = "A";
	/** Unapplied = U */
	public static final String STATUSCODE_Unapplied = "U";
	/** Failed = F */
	public static final String STATUSCODE_Failed = "F";
	/** Partially applied = P */
	public static final String STATUSCODE_PartiallyApplied = "P";
	/** Set Status Code.
		@param StatusCode Status Code	  */
	@Override
	public void setStatusCode (java.lang.String StatusCode)
	{

		set_Value (COLUMNNAME_StatusCode, StatusCode);
	}

	/** Get Status Code.
		@return Status Code	  */
	@Override
	public java.lang.String getStatusCode () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_StatusCode);
	}
}