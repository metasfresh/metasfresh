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

import java.sql.ResultSet;
import java.util.Properties;
import org.compiere.util.KeyNamePair;

/** Generated Model for K_Synonym
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_K_Synonym extends PO implements I_K_Synonym, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_K_Synonym (Properties ctx, int K_Synonym_ID, String trxName)
    {
      super (ctx, K_Synonym_ID, trxName);
      /** if (K_Synonym_ID == 0)
        {
			setAD_Language (null);
			setK_Synonym_ID (0);
			setName (null);
			setSynonymName (null);
        } */
    }

    /** Load Constructor */
    public X_K_Synonym (Properties ctx, ResultSet rs, String trxName)
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
      StringBuffer sb = new StringBuffer ("X_K_Synonym[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	/** AD_Language AD_Reference_ID=106 */
	public static final int AD_LANGUAGE_AD_Reference_ID=106;
	/** Set Language.
		@param AD_Language 
		Language for this entity
	  */
	public void setAD_Language (String AD_Language)
	{

		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	/** Get Language.
		@return Language for this entity
	  */
	public String getAD_Language () 
	{
		return (String)get_Value(COLUMNNAME_AD_Language);
	}

	/** Set Knowledge Synonym.
		@param K_Synonym_ID 
		Knowlege Keyword Synonym
	  */
	public void setK_Synonym_ID (int K_Synonym_ID)
	{
		if (K_Synonym_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_K_Synonym_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_K_Synonym_ID, Integer.valueOf(K_Synonym_ID));
	}

	/** Get Knowledge Synonym.
		@return Knowlege Keyword Synonym
	  */
	public int getK_Synonym_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_K_Synonym_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
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

    /** Get Record ID/ColumnName
        @return ID/ColumnName pair
      */
    public KeyNamePair getKeyNamePair() 
    {
        return new KeyNamePair(get_ID(), getName());
    }

	/** Set Synonym Name.
		@param SynonymName 
		The synonym for the name
	  */
	public void setSynonymName (String SynonymName)
	{
		set_Value (COLUMNNAME_SynonymName, SynonymName);
	}

	/** Get Synonym Name.
		@return The synonym for the name
	  */
	public String getSynonymName () 
	{
		return (String)get_Value(COLUMNNAME_SynonymName);
	}
}