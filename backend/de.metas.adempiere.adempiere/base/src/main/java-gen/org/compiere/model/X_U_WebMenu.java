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
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.Env;

/** Generated Model for U_WebMenu
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_U_WebMenu extends PO implements I_U_WebMenu, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_U_WebMenu (Properties ctx, int U_WebMenu_ID, String trxName)
    {
      super (ctx, U_WebMenu_ID, trxName);
      /** if (U_WebMenu_ID == 0)
        {
			setHasSubMenu (false);
// 'N'
			setMenuLink (null);
			setModule (null);
			setName (null);
			setU_WebMenu_ID (0);
        } */
    }

    /** Load Constructor */
    public X_U_WebMenu (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 4 - System 
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
      StringBuffer sb = new StringBuffer ("X_U_WebMenu[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** Set Category.
		@param Category Category	  */
	public void setCategory (String Category)
	{
		set_Value (COLUMNNAME_Category, Category);
	}

	/** Get Category.
		@return Category	  */
	public String getCategory () 
	{
		return (String)get_Value(COLUMNNAME_Category);
	}

	/** Set Description.
		@param Description 
		Optional short description of the record
	  */
	public void setDescription (String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	/** Get Description.
		@return Optional short description of the record
	  */
	public String getDescription () 
	{
		return (String)get_Value(COLUMNNAME_Description);
	}

	/** Set Has SubMenu.
		@param HasSubMenu Has SubMenu	  */
	public void setHasSubMenu (boolean HasSubMenu)
	{
		set_Value (COLUMNNAME_HasSubMenu, Boolean.valueOf(HasSubMenu));
	}

	/** Get Has SubMenu.
		@return Has SubMenu	  */
	public boolean isHasSubMenu () 
	{
		Object oo = get_Value(COLUMNNAME_HasSubMenu);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Comment/Help.
		@param Help 
		Comment or Hint
	  */
	public void setHelp (String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	/** Get Comment/Help.
		@return Comment or Hint
	  */
	public String getHelp () 
	{
		return (String)get_Value(COLUMNNAME_Help);
	}

	/** Set Image Link.
		@param ImageLink Image Link	  */
	public void setImageLink (String ImageLink)
	{
		set_Value (COLUMNNAME_ImageLink, ImageLink);
	}

	/** Get Image Link.
		@return Image Link	  */
	public String getImageLink () 
	{
		return (String)get_Value(COLUMNNAME_ImageLink);
	}

	/** Set Menu Link.
		@param MenuLink Menu Link	  */
	public void setMenuLink (String MenuLink)
	{
		set_Value (COLUMNNAME_MenuLink, MenuLink);
	}

	/** Get Menu Link.
		@return Menu Link	  */
	public String getMenuLink () 
	{
		return (String)get_Value(COLUMNNAME_MenuLink);
	}

	/** Set Module.
		@param Module Module	  */
	public void setModule (String Module)
	{
		set_Value (COLUMNNAME_Module, Module);
	}

	/** Get Module.
		@return Module	  */
	public String getModule () 
	{
		return (String)get_Value(COLUMNNAME_Module);
	}

	/** Set Name.
		@param Name 
		Alphanumeric identifier of the entity
	  */
	public void setName (String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	/** Get Name.
		@return Alphanumeric identifier of the entity
	  */
	public String getName () 
	{
		return (String)get_Value(COLUMNNAME_Name);
	}

	public I_U_WebMenu getParentMenu() throws RuntimeException
    {
		return (I_U_WebMenu)MTable.get(getCtx(), I_U_WebMenu.Table_Name)
			.getPO(getParentMenu_ID(), get_TrxName());	}

	/** Set Parent Menu.
		@param ParentMenu_ID Parent Menu	  */
	public void setParentMenu_ID (int ParentMenu_ID)
	{
		if (ParentMenu_ID < 1) 
			set_Value (COLUMNNAME_ParentMenu_ID, null);
		else 
			set_Value (COLUMNNAME_ParentMenu_ID, Integer.valueOf(ParentMenu_ID));
	}

	/** Get Parent Menu.
		@return Parent Menu	  */
	public int getParentMenu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ParentMenu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Position.
		@param Position Position	  */
	public void setPosition (String Position)
	{
		set_Value (COLUMNNAME_Position, Position);
	}

	/** Get Position.
		@return Position	  */
	public String getPosition () 
	{
		return (String)get_Value(COLUMNNAME_Position);
	}

	/** Set Sequence.
		@param Sequence Sequence	  */
	public void setSequence (BigDecimal Sequence)
	{
		set_Value (COLUMNNAME_Sequence, Sequence);
	}

	/** Get Sequence.
		@return Sequence	  */
	public BigDecimal getSequence () 
	{
		BigDecimal bd = (BigDecimal)get_Value(COLUMNNAME_Sequence);
		if (bd == null)
			 return Env.ZERO;
		return bd;
	}

	/** Set Web Menu.
		@param U_WebMenu_ID Web Menu	  */
	public void setU_WebMenu_ID (int U_WebMenu_ID)
	{
		if (U_WebMenu_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_U_WebMenu_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_U_WebMenu_ID, Integer.valueOf(U_WebMenu_ID));
	}

	/** Get Web Menu.
		@return Web Menu	  */
	public int getU_WebMenu_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_U_WebMenu_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}