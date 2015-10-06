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

package org.compiere.model;

/**
 * Generated Model for C_Location.
 * Entity Types: [D]
 * @author Adempiere (generated)
 * @version
 */
public class X_C_Location
extends
	org.compiere.model.PO
implements
	org.compiere.model.I_C_Location
	,org.compiere.model.I_Persistent
{
	private static final long serialVersionUID = -1484945173L;
	
	/**
	 * Standard Constructor
	 */
	public X_C_Location(java.util.Properties ctx, int C_Location_ID, java.lang.String trxName)
	{
		super (ctx, C_Location_ID, trxName);
		/** if (C_Location_ID == 0)
		{
			setC_Country_ID(null);
			setC_Location_ID(null);
			setIsPostalValidated(null); // N
			} */
	}
	
	/**
	 * Load Constructor
	 */
	public X_C_Location(java.util.Properties ctx, java.sql.ResultSet rs, java.lang.String trxName)
	{
		super (ctx, rs, trxName);
	}
	
	/**
	 * Load Meta Data
	 */
	@Override
	protected org.compiere.model.POInfo initPO(java.util.Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo (ctx, Table_ID, get_TrxName());
	}
	
	@Override
	public String toString()
	{
		StringBuffer sb = new StringBuffer ("X_C_Location[")
			.append(get_ID()).append("]");
		return sb.toString();
	}
	
	/**
	 * Get Record ID/ColumnName
	 * @return ID/ColumnName pair
	 */
	@Deprecated
	public org.compiere.util.KeyNamePair getKeyNamePair()
	{
		return new org.compiere.util.KeyNamePair(get_ID(), getCity());
	}
	
	/**
	 * Set Adresszeile 1.
	 * Address line 1 for this location.
	 * The Address 1 identifies the address for an entity's location
	 */
	@Override
	public void setAddress1(java.lang.String Address1)
	{
		set_Value(COLUMNNAME_Address1, Address1);
	}
	
	/**
	 * Get Adresszeile 1.
	 * Address line 1 for this location.
	 * The Address 1 identifies the address for an entity's location
	 */
	@Override
	public java.lang.String getAddress1()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address1);
	}
	
	/**
	 * Set Adresszeile 2.
	 * Address line 2 for this location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public void setAddress2(java.lang.String Address2)
	{
		set_Value(COLUMNNAME_Address2, Address2);
	}
	
	/**
	 * Get Adresszeile 2.
	 * Address line 2 for this location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public java.lang.String getAddress2()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address2);
	}
	
	/**
	 * Set Adresszeile 3.
	 * Address Line 3 for the location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public void setAddress3(java.lang.String Address3)
	{
		set_Value(COLUMNNAME_Address3, Address3);
	}
	
	/**
	 * Get Adresszeile 3.
	 * Address Line 3 for the location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public java.lang.String getAddress3()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address3);
	}
	
	/**
	 * Set Adresszeile 4.
	 * Address Line 4 for the location.
	 * The Address 4 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public void setAddress4(java.lang.String Address4)
	{
		set_Value(COLUMNNAME_Address4, Address4);
	}
	
	/**
	 * Get Adresszeile 4.
	 * Address Line 4 for the location.
	 * The Address 4 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	@Override
	public java.lang.String getAddress4()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Address4);
	}
	
	/**
	 * Set Ort.
	 * City.
	 * City in a country
	 */
	@Override
	public void setC_City_ID(int C_City_ID)
	{
		set_Value(COLUMNNAME_C_City_ID, C_City_ID);
	}
	
	/**
	 * Get Ort.
	 * City.
	 * City in a country
	 */
	@Override
	public int getC_City_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_City_ID);
		return (ii == null ? 0 : ii.intValue());
	}
	
	/**
	 * Get model  Ort.
	 * City.
	 * City in a country
	 */
	@Override
	public org.compiere.model.I_C_City getC_City() throws java.lang.RuntimeException
	{
		return (org.compiere.model.I_C_City)org.compiere.model.MTable.get(getCtx(), org.compiere.model.I_C_City.Table_Name)
			.getPO(getC_City_ID(), get_TrxName());
	}
	
	/**
	 * Set Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	@Override
	public void setC_Country_ID(int C_Country_ID)
	{
		set_Value(COLUMNNAME_C_Country_ID, C_Country_ID);
	}
	
	/**
	 * Get Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	@Override
	public int getC_Country_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Country_ID);
		return (ii == null ? 0 : ii.intValue());
	}
	
	/**
	 * Get model  Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	@Override
	public org.compiere.model.I_C_Country getC_Country() throws java.lang.RuntimeException
	{
		return (org.compiere.model.I_C_Country)org.compiere.model.MTable.get(getCtx(), org.compiere.model.I_C_Country.Table_Name)
			.getPO(getC_Country_ID(), get_TrxName());
	}
	
	/**
	 * Set Anschrift.
	 * Location or Address.
	 * The Location / Address field defines the location of an entity.
	 */
	@Override
	public void setC_Location_ID(int C_Location_ID)
	{
		set_ValueNoCheck(COLUMNNAME_C_Location_ID, C_Location_ID);
	}
	
	/**
	 * Get Anschrift.
	 * Location or Address.
	 * The Location / Address field defines the location of an entity.
	 */
	@Override
	public int getC_Location_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Location_ID);
		return (ii == null ? 0 : ii.intValue());
	}
	
	/**
	 * Set Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	@Override
	public void setC_Region_ID(int C_Region_ID)
	{
		set_Value(COLUMNNAME_C_Region_ID, C_Region_ID);
	}
	
	/**
	 * Get Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	@Override
	public int getC_Region_ID()
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_C_Region_ID);
		return (ii == null ? 0 : ii.intValue());
	}
	
	/**
	 * Get model  Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	@Override
	public org.compiere.model.I_C_Region getC_Region() throws java.lang.RuntimeException
	{
		return (org.compiere.model.I_C_Region)org.compiere.model.MTable.get(getCtx(), org.compiere.model.I_C_Region.Table_Name)
			.getPO(getC_Region_ID(), get_TrxName());
	}
	
	/**
	 * Set C/O.
	 * In care of
	 */
	@Override
	public void setCareOf(java.lang.String CareOf)
	{
		set_Value(COLUMNNAME_CareOf, CareOf);
	}
	
	/**
	 * Get C/O.
	 * In care of
	 */
	@Override
	public java.lang.String getCareOf()
	{
		return (java.lang.String)get_Value(COLUMNNAME_CareOf);
	}
	
	/**
	 * Set Ort.
	 * Identifies a City.
	 * The City identifies a unique City for this Country or Region.
	 */
	@Override
	public void setCity(java.lang.String City)
	{
		set_Value(COLUMNNAME_City, City);
	}
	
	/**
	 * Get Ort.
	 * Identifies a City.
	 * The City identifies a unique City for this Country or Region.
	 */
	@Override
	public java.lang.String getCity()
	{
		return (java.lang.String)get_Value(COLUMNNAME_City);
	}
	
	/**
	 * Set PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde..
	 * Postleitzahlen können automatisch gegen vorhandene DPD-Routendaten verifiziert oder manuell durch den Benutzer angelegt und damit ebenfalls verifiziert werden.
	 */
	@Override
	public void setIsPostalValidated(boolean IsPostalValidated)
	{
		set_Value(COLUMNNAME_IsPostalValidated, IsPostalValidated);
	}
	
	/**
	 * Get PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde..
	 * Postleitzahlen können automatisch gegen vorhandene DPD-Routendaten verifiziert oder manuell durch den Benutzer angelegt und damit ebenfalls verifiziert werden.
	 */
	@Override
	public boolean isPostalValidated()
	{
		Object oo = get_Value(COLUMNNAME_IsPostalValidated);
		return (oo instanceof Boolean ? ((Boolean)oo).booleanValue() : "Y".equals(oo));
	}
	
	/**
	 * Set PO Box
	 */
	@Override
	public void setPOBox(java.lang.String POBox)
	{
		set_Value(COLUMNNAME_POBox, POBox);
	}
	
	/**
	 * Get PO Box
	 */
	@Override
	public java.lang.String getPOBox()
	{
		return (java.lang.String)get_Value(COLUMNNAME_POBox);
	}
	
	/**
	 * Set PLZ.
	 * Postal code.
	 * The Postal Code or ZIP identifies the postal code for this entity's address.
	 */
	@Override
	public void setPostal(java.lang.String Postal)
	{
		set_Value(COLUMNNAME_Postal, Postal);
	}
	
	/**
	 * Get PLZ.
	 * Postal code.
	 * The Postal Code or ZIP identifies the postal code for this entity's address.
	 */
	@Override
	public java.lang.String getPostal()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal);
	}
	
	/**
	 * Set -.
	 * Additional ZIP or Postal code.
	 * The Additional ZIP or Postal Code identifies, if appropriate, any additional Postal Code information.
	 */
	@Override
	public void setPostal_Add(java.lang.String Postal_Add)
	{
		set_Value(COLUMNNAME_Postal_Add, Postal_Add);
	}
	
	/**
	 * Get -.
	 * Additional ZIP or Postal code.
	 * The Additional ZIP or Postal Code identifies, if appropriate, any additional Postal Code information.
	 */
	@Override
	public java.lang.String getPostal_Add()
	{
		return (java.lang.String)get_Value(COLUMNNAME_Postal_Add);
	}
	
	/**
	 * Set Region.
	 * Name of the Region.
	 * The Region Name defines the name that will print when this region is used in a document.
	 */
	@Override
	public void setRegionName(java.lang.String RegionName)
	{
		set_Value(COLUMNNAME_RegionName, RegionName);
	}
	
	/**
	 * Get Region.
	 * Name of the Region.
	 * The Region Name defines the name that will print when this region is used in a document.
	 */
	@Override
	public java.lang.String getRegionName()
	{
		return (java.lang.String)get_Value(COLUMNNAME_RegionName);
	}
	
	
}
