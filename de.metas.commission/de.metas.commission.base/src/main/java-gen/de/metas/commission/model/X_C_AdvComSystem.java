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

/** Generated Model for C_AdvComSystem
 *  @author Adempiere (generated) 
 */
@SuppressWarnings("javadoc")
public class X_C_AdvComSystem extends org.compiere.model.PO implements I_C_AdvComSystem, org.compiere.model.I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = -1309987842L;

    /** Standard Constructor */
    public X_C_AdvComSystem (Properties ctx, int C_AdvComSystem_ID, String trxName)
    {
      super (ctx, C_AdvComSystem_ID, trxName);
      /** if (C_AdvComSystem_ID == 0)
        {
			setC_AdvComRankCollection_ID (0);
// 0
			setC_AdvComSystem_ID (0);
			setC_Sponsor_Root_ID (0);
			setIsDefault (false);
// N
			setName (null);
        } */
    }

    /** Load Constructor */
    public X_C_AdvComSystem (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_C_AdvComSystem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	@Override
	public org.compiere.model.I_AD_Role getAD_Role_Admin() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_Role_Admin_ID, org.compiere.model.I_AD_Role.class);
	}

	@Override
	public void setAD_Role_Admin(org.compiere.model.I_AD_Role AD_Role_Admin)
	{
		set_ValueFromPO(COLUMNNAME_AD_Role_Admin_ID, org.compiere.model.I_AD_Role.class, AD_Role_Admin);
	}

	/** Set Admin-Rolle.
		@param AD_Role_Admin_ID Admin-Rolle	  */
	@Override
	public void setAD_Role_Admin_ID (int AD_Role_Admin_ID)
	{
		if (AD_Role_Admin_ID < 1) 
			set_Value (COLUMNNAME_AD_Role_Admin_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Role_Admin_ID, Integer.valueOf(AD_Role_Admin_ID));
	}

	/** Get Admin-Rolle.
		@return Admin-Rolle	  */
	@Override
	public int getAD_Role_Admin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Role_Admin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public org.compiere.model.I_AD_User getAD_User_Admin() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_AD_User_Admin_ID, org.compiere.model.I_AD_User.class);
	}

	@Override
	public void setAD_User_Admin(org.compiere.model.I_AD_User AD_User_Admin)
	{
		set_ValueFromPO(COLUMNNAME_AD_User_Admin_ID, org.compiere.model.I_AD_User.class, AD_User_Admin);
	}

	/** Set Admin-Benutzer.
		@param AD_User_Admin_ID Admin-Benutzer	  */
	@Override
	public void setAD_User_Admin_ID (int AD_User_Admin_ID)
	{
		if (AD_User_Admin_ID < 1) 
			set_Value (COLUMNNAME_AD_User_Admin_ID, null);
		else 
			set_Value (COLUMNNAME_AD_User_Admin_ID, Integer.valueOf(AD_User_Admin_ID));
	}

	/** Get Admin-Benutzer.
		@return Admin-Benutzer	  */
	@Override
	public int getAD_User_Admin_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_User_Admin_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_AdvComRankCollection getC_AdvComRankCollection() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_AdvComRankCollection_ID, de.metas.commission.model.I_C_AdvComRankCollection.class);
	}

	@Override
	public void setC_AdvComRankCollection(de.metas.commission.model.I_C_AdvComRankCollection C_AdvComRankCollection)
	{
		set_ValueFromPO(COLUMNNAME_C_AdvComRankCollection_ID, de.metas.commission.model.I_C_AdvComRankCollection.class, C_AdvComRankCollection);
	}

	/** Set Vergütungsgruppensammlung.
		@param C_AdvComRankCollection_ID Vergütungsgruppensammlung	  */
	@Override
	public void setC_AdvComRankCollection_ID (int C_AdvComRankCollection_ID)
	{
		if (C_AdvComRankCollection_ID < 1) 
			set_Value (COLUMNNAME_C_AdvComRankCollection_ID, null);
		else 
			set_Value (COLUMNNAME_C_AdvComRankCollection_ID, Integer.valueOf(C_AdvComRankCollection_ID));
	}

	/** Get Vergütungsgruppensammlung.
		@return Vergütungsgruppensammlung	  */
	@Override
	public int getC_AdvComRankCollection_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComRankCollection_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Vergütungsplan.
		@param C_AdvComSystem_ID Vergütungsplan	  */
	@Override
	public void setC_AdvComSystem_ID (int C_AdvComSystem_ID)
	{
		if (C_AdvComSystem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_AdvComSystem_ID, Integer.valueOf(C_AdvComSystem_ID));
	}

	/** Get Vergütungsplan.
		@return Vergütungsplan	  */
	@Override
	public int getC_AdvComSystem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_AdvComSystem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	@Override
	public de.metas.commission.model.I_C_Sponsor getC_Sponsor_Root() throws RuntimeException
	{
		return get_ValueAsPO(COLUMNNAME_C_Sponsor_Root_ID, de.metas.commission.model.I_C_Sponsor.class);
	}

	@Override
	public void setC_Sponsor_Root(de.metas.commission.model.I_C_Sponsor C_Sponsor_Root)
	{
		set_ValueFromPO(COLUMNNAME_C_Sponsor_Root_ID, de.metas.commission.model.I_C_Sponsor.class, C_Sponsor_Root);
	}

	/** Set Wurzel-Sponsor.
		@param C_Sponsor_Root_ID Wurzel-Sponsor	  */
	@Override
	public void setC_Sponsor_Root_ID (int C_Sponsor_Root_ID)
	{
		if (C_Sponsor_Root_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Root_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Sponsor_Root_ID, Integer.valueOf(C_Sponsor_Root_ID));
	}

	/** Get Wurzel-Sponsor.
		@return Wurzel-Sponsor	  */
	@Override
	public int getC_Sponsor_Root_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Sponsor_Root_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Beschreibung.
		@param Description Beschreibung	  */
	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Beschreibung.
		@return Beschreibung	  */
	@Override
	public java.lang.String getDescription () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	/** Set Interner Name.
		@param InternalName 
		Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public void setInternalName (java.lang.String InternalName)
	{
		set_ValueNoCheck (COLUMNNAME_InternalName, InternalName);
	}

	/** Get Interner Name.
		@return Eindeutiger system-interner Bezeichner des Datensatzes.
	  */
	@Override
	public java.lang.String getInternalName () 
	{
		return (java.lang.String)get_Value(COLUMNNAME_InternalName);
	}

	/** Set Standard.
		@param IsDefault 
		Default value
	  */
	@Override
	public void setIsDefault (boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, Boolean.valueOf(IsDefault));
	}

	/** Get Standard.
		@return Default value
	  */
	@Override
	public boolean isDefault () 
	{
		Object oo = get_Value(COLUMNNAME_IsDefault);
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
}