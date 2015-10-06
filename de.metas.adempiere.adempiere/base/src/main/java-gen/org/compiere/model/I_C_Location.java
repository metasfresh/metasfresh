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
 * Generated Model Interface for C_Location.
 * Entity Types: [D]
 * @author Adempiere (generated)
 * @version
 */
public interface I_C_Location
{
	/**
	 * TableName = C_Location
	 */
	public static final String Table_Name = "C_Location";
	
	/**
	 * AD_Table_ID(162
	 */
	@Deprecated
	public static final int Table_ID = org.compiere.model.MTable.getTable_ID(Table_Name);
	
	@Deprecated
	static final org.compiere.util.KeyNamePair Model = new org.compiere.util.KeyNamePair(Table_ID, Table_Name);
	
	/**
	 * AccessLevel = 7 - System - Client - Org
	 */
	@Deprecated
	static final java.math.BigDecimal accessLevel = java.math.BigDecimal.valueOf(7);
	
	
	
	
	public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";
	
	/**
	 * Get Mandant.
	 * Client/Tenant for this installation..
	 * A Client is a company or a legal entity. You cannot share data between Clients. Tenant is a synonym for Client.
	 */
	public int getAD_Client_ID();
	
	
	
	public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";
	
	/**
	 * Set Organisation.
	 * Organizational entity within client.
	 * An organization is a unit of your client or legal entity - examples are store, department. You can share data between organizations.
	 */
	public void setAD_Org_ID(int AD_Org_ID);
	/**
	 * Get Organisation.
	 * Organizational entity within client.
	 * An organization is a unit of your client or legal entity - examples are store, department. You can share data between organizations.
	 */
	public int getAD_Org_ID();
	
	
	
	public static final String COLUMNNAME_Address1 = "Address1";
	
	/**
	 * Set Adresszeile 1.
	 * Address line 1 for this location.
	 * The Address 1 identifies the address for an entity's location
	 */
	public void setAddress1(java.lang.String Address1);
	/**
	 * Get Adresszeile 1.
	 * Address line 1 for this location.
	 * The Address 1 identifies the address for an entity's location
	 */
	public java.lang.String getAddress1();
	
	
	
	public static final String COLUMNNAME_Address2 = "Address2";
	
	/**
	 * Set Adresszeile 2.
	 * Address line 2 for this location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public void setAddress2(java.lang.String Address2);
	/**
	 * Get Adresszeile 2.
	 * Address line 2 for this location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public java.lang.String getAddress2();
	
	
	
	public static final String COLUMNNAME_Address3 = "Address3";
	
	/**
	 * Set Adresszeile 3.
	 * Address Line 3 for the location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public void setAddress3(java.lang.String Address3);
	/**
	 * Get Adresszeile 3.
	 * Address Line 3 for the location.
	 * The Address 2 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public java.lang.String getAddress3();
	
	
	
	public static final String COLUMNNAME_Address4 = "Address4";
	
	/**
	 * Set Adresszeile 4.
	 * Address Line 4 for the location.
	 * The Address 4 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public void setAddress4(java.lang.String Address4);
	/**
	 * Get Adresszeile 4.
	 * Address Line 4 for the location.
	 * The Address 4 provides additional address information for an entity.  It can be used for building location, apartment number or similar information.
	 */
	public java.lang.String getAddress4();
	
	
	
	public static final String COLUMNNAME_C_City_ID = "C_City_ID";
	
	/**
	 * Set Ort.
	 * City.
	 * City in a country
	 */
	public void setC_City_ID(int C_City_ID);
	/**
	 * Get Ort.
	 * City.
	 * City in a country
	 */
	public int getC_City_ID();
	/**
	 * Get model  Ort.
	 * City.
	 * City in a country
	 */
	public org.compiere.model.I_C_City getC_City() throws java.lang.RuntimeException;
	
	
	
	public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";
	
	/**
	 * Set Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	public void setC_Country_ID(int C_Country_ID);
	/**
	 * Get Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	public int getC_Country_ID();
	/**
	 * Get model  Land.
	 * Country .
	 * The Country defines a Country.  Each Country must be defined before it can be used in any document.
	 */
	public org.compiere.model.I_C_Country getC_Country() throws java.lang.RuntimeException;
	
	
	
	public static final String COLUMNNAME_C_Location_ID = "C_Location_ID";
	
	/**
	 * Set Anschrift.
	 * Location or Address.
	 * The Location / Address field defines the location of an entity.
	 */
	public void setC_Location_ID(int C_Location_ID);
	/**
	 * Get Anschrift.
	 * Location or Address.
	 * The Location / Address field defines the location of an entity.
	 */
	public int getC_Location_ID();
	
	
	
	public static final String COLUMNNAME_C_Region_ID = "C_Region_ID";
	
	/**
	 * Set Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	public void setC_Region_ID(int C_Region_ID);
	/**
	 * Get Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	public int getC_Region_ID();
	/**
	 * Get model  Region.
	 * Identifies a geographical Region.
	 * The Region identifies a unique Region for this Country.
	 */
	public org.compiere.model.I_C_Region getC_Region() throws java.lang.RuntimeException;
	
	
	
	public static final String COLUMNNAME_CareOf = "CareOf";
	
	/**
	 * Set C/O.
	 * In care of
	 */
	public void setCareOf(java.lang.String CareOf);
	/**
	 * Get C/O.
	 * In care of
	 */
	public java.lang.String getCareOf();
	
	
	
	public static final String COLUMNNAME_City = "City";
	
	/**
	 * Set Ort.
	 * Identifies a City.
	 * The City identifies a unique City for this Country or Region.
	 */
	public void setCity(java.lang.String City);
	/**
	 * Get Ort.
	 * Identifies a City.
	 * The City identifies a unique City for this Country or Region.
	 */
	public java.lang.String getCity();
	
	
	
	public static final String COLUMNNAME_Created = "Created";
	
	/**
	 * Get Erstellt.
	 * Date this record was created.
	 * The Created field indicates the date that this record was created.
	 */
	public java.sql.Timestamp getCreated();
	
	
	
	public static final String COLUMNNAME_CreatedBy = "CreatedBy";
	
	/**
	 * Get Erstellt durch.
	 * User who created this records.
	 * The Created By field indicates the user who created this record.
	 */
	public int getCreatedBy();
	
	
	
	public static final String COLUMNNAME_IsActive = "IsActive";
	
	/**
	 * Set Aktiv.
	 * The record is active in the system.
	 * There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
	 * There are two reasons for de-activating and not deleting records:
	 * (1) The system requires the record for audit purposes.
	 * (2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.
	 */
	public void setIsActive(boolean IsActive);
	/**
	 * Get Aktiv.
	 * The record is active in the system.
	 * There are two methods of making records unavailable in the system: One is to delete the record, the other is to de-activate the record. A de-activated record is not available for selection, but available for reports.
	 * There are two reasons for de-activating and not deleting records:
	 * (1) The system requires the record for audit purposes.
	 * (2) The record is referenced by other records. E.g., you cannot delete a Business Partner, if there are invoices for this partner record existing. You de-activate the Business Partner and prevent that this record is used for future entries.
	 */
	public boolean isActive();
	
	
	
	public static final String COLUMNNAME_IsPostalValidated = "IsPostalValidated";
	
	/**
	 * Set PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde..
	 * Postleitzahlen können automatisch gegen vorhandene DPD-Routendaten verifiziert oder manuell durch den Benutzer angelegt und damit ebenfalls verifiziert werden.
	 */
	public void setIsPostalValidated(boolean IsPostalValidated);
	/**
	 * Get PLZ verifiziert.
	 * Sagt aus, ob Postleitzahl der Adresse verifiziert wurde..
	 * Postleitzahlen können automatisch gegen vorhandene DPD-Routendaten verifiziert oder manuell durch den Benutzer angelegt und damit ebenfalls verifiziert werden.
	 */
	public boolean isPostalValidated();
	
	
	
	public static final String COLUMNNAME_POBox = "POBox";
	
	/**
	 * Set PO Box
	 */
	public void setPOBox(java.lang.String POBox);
	/**
	 * Get PO Box
	 */
	public java.lang.String getPOBox();
	
	
	
	public static final String COLUMNNAME_Postal = "Postal";
	
	/**
	 * Set PLZ.
	 * Postal code.
	 * The Postal Code or ZIP identifies the postal code for this entity's address.
	 */
	public void setPostal(java.lang.String Postal);
	/**
	 * Get PLZ.
	 * Postal code.
	 * The Postal Code or ZIP identifies the postal code for this entity's address.
	 */
	public java.lang.String getPostal();
	
	
	
	public static final String COLUMNNAME_Postal_Add = "Postal_Add";
	
	/**
	 * Set -.
	 * Additional ZIP or Postal code.
	 * The Additional ZIP or Postal Code identifies, if appropriate, any additional Postal Code information.
	 */
	public void setPostal_Add(java.lang.String Postal_Add);
	/**
	 * Get -.
	 * Additional ZIP or Postal code.
	 * The Additional ZIP or Postal Code identifies, if appropriate, any additional Postal Code information.
	 */
	public java.lang.String getPostal_Add();
	
	
	
	public static final String COLUMNNAME_RegionName = "RegionName";
	
	/**
	 * Set Region.
	 * Name of the Region.
	 * The Region Name defines the name that will print when this region is used in a document.
	 */
	public void setRegionName(java.lang.String RegionName);
	/**
	 * Get Region.
	 * Name of the Region.
	 * The Region Name defines the name that will print when this region is used in a document.
	 */
	public java.lang.String getRegionName();
	
	
	
	public static final String COLUMNNAME_Updated = "Updated";
	
	/**
	 * Get Aktualisiert.
	 * Date this record was updated.
	 * The Updated field indicates the date that this record was updated.
	 */
	public java.sql.Timestamp getUpdated();
	
	
	
	public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";
	
	/**
	 * Get Aktualisiert durch.
	 * User who updated this records.
	 * The Updated By field indicates the user who updated this record.
	 */
	public int getUpdatedBy();
	
}
