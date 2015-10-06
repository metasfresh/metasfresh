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

import java.math.BigDecimal;
import java.sql.Timestamp;
import org.compiere.util.KeyNamePair;

/** Generated Interface for C_Country
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_C_Country 
{

    /** TableName=C_Country */
    public static final String Table_Name = "C_Country";

    /** AD_Table_ID=170 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 6 - System - Client 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(6);

    /** Load Meta Data */

    /** Column name AD_Client_ID */
    public static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	/** Get Client.
	  * Client/Tenant for this installation.
	  */
	public int getAD_Client_ID();

    /** Column name AD_Language */
    public static final String COLUMNNAME_AD_Language = "AD_Language";

	/** Set Language.
	  * Language for this entity
	  */
	public void setAD_Language (String AD_Language);

	/** Get Language.
	  * Language for this entity
	  */
	public String getAD_Language();

    /** Column name AD_Org_ID */
    public static final String COLUMNNAME_AD_Org_ID = "AD_Org_ID";

	/** Set Organization.
	  * Organizational entity within client
	  */
	public void setAD_Org_ID (int AD_Org_ID);

	/** Get Organization.
	  * Organizational entity within client
	  */
	public int getAD_Org_ID();

    /** Column name AllowCitiesOutOfList */
    public static final String COLUMNNAME_AllowCitiesOutOfList = "AllowCitiesOutOfList";

	/** Set AllowCitiesOutOfList.
	  * A flag which tells if a country accept or not new cities
	  */
	public void setAllowCitiesOutOfList (boolean AllowCitiesOutOfList);

	/** Get AllowCitiesOutOfList.
	  * A flag which tells if a country accept or not new cities
	  */
	public boolean isAllowCitiesOutOfList();

    /** Column name CaptureSequence */
    public static final String COLUMNNAME_CaptureSequence = "CaptureSequence";

	/** Set CaptureSequence	  */
	public void setCaptureSequence (String CaptureSequence);

	/** Get CaptureSequence	  */
	public String getCaptureSequence();

    /** Column name C_Country_ID */
    public static final String COLUMNNAME_C_Country_ID = "C_Country_ID";

	/** Set Country.
	  * Country 
	  */
	public void setC_Country_ID (int C_Country_ID);

	/** Get Country.
	  * Country 
	  */
	public int getC_Country_ID();

    /** Column name C_Currency_ID */
    public static final String COLUMNNAME_C_Currency_ID = "C_Currency_ID";

	/** Set Currency.
	  * The Currency for this record
	  */
	public void setC_Currency_ID (int C_Currency_ID);

	/** Get Currency.
	  * The Currency for this record
	  */
	public int getC_Currency_ID();

	public I_C_Currency getC_Currency() throws RuntimeException;

    /** Column name CountryCode */
    public static final String COLUMNNAME_CountryCode = "CountryCode";

	/** Set ISO Country Code.
	  * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public void setCountryCode (String CountryCode);

	/** Get ISO Country Code.
	  * Upper-case two-letter alphanumeric ISO Country code according to ISO 3166-1 - http://www.chemie.fu-berlin.de/diverse/doc/ISO_3166.html
	  */
	public String getCountryCode();

    /** Column name Created */
    public static final String COLUMNNAME_Created = "Created";

	/** Get Created.
	  * Date this record was created
	  */
	public Timestamp getCreated();

    /** Column name CreatedBy */
    public static final String COLUMNNAME_CreatedBy = "CreatedBy";

	/** Get Created By.
	  * User who created this records
	  */
	public int getCreatedBy();

    /** Column name Description */
    public static final String COLUMNNAME_Description = "Description";

	/** Set Description.
	  * Optional short description of the record
	  */
	public void setDescription (String Description);

	/** Get Description.
	  * Optional short description of the record
	  */
	public String getDescription();

    /** Column name DisplaySequence */
    public static final String COLUMNNAME_DisplaySequence = "DisplaySequence";

	/** Set Address Print Format.
	  * Format for printing this Address
	  */
	public void setDisplaySequence (String DisplaySequence);

	/** Get Address Print Format.
	  * Format for printing this Address
	  */
	public String getDisplaySequence();

    /** Column name DisplaySequenceLocal */
    public static final String COLUMNNAME_DisplaySequenceLocal = "DisplaySequenceLocal";

	/** Set Local Address Format.
	  * Format for printing this Address locally
	  */
	public void setDisplaySequenceLocal (String DisplaySequenceLocal);

	/** Get Local Address Format.
	  * Format for printing this Address locally
	  */
	public String getDisplaySequenceLocal();

    /** Column name ExpressionBankAccountNo */
    public static final String COLUMNNAME_ExpressionBankAccountNo = "ExpressionBankAccountNo";

	/** Set Bank Account No Format.
	  * Format of the Bank Account
	  */
	public void setExpressionBankAccountNo (String ExpressionBankAccountNo);

	/** Get Bank Account No Format.
	  * Format of the Bank Account
	  */
	public String getExpressionBankAccountNo();

    /** Column name ExpressionBankRoutingNo */
    public static final String COLUMNNAME_ExpressionBankRoutingNo = "ExpressionBankRoutingNo";

	/** Set Bank Routing No Format.
	  * Format of the Bank Routing Number
	  */
	public void setExpressionBankRoutingNo (String ExpressionBankRoutingNo);

	/** Get Bank Routing No Format.
	  * Format of the Bank Routing Number
	  */
	public String getExpressionBankRoutingNo();

    /** Column name ExpressionPhone */
    public static final String COLUMNNAME_ExpressionPhone = "ExpressionPhone";

	/** Set Phone Format.
	  * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPhone (String ExpressionPhone);

	/** Get Phone Format.
	  * Format of the phone;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPhone();

    /** Column name ExpressionPostal */
    public static final String COLUMNNAME_ExpressionPostal = "ExpressionPostal";

	/** Set Postal Code Format.
	  * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPostal (String ExpressionPostal);

	/** Get Postal Code Format.
	  * Format of the postal code;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPostal();

    /** Column name ExpressionPostal_Add */
    public static final String COLUMNNAME_ExpressionPostal_Add = "ExpressionPostal_Add";

	/** Set Additional Postal Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public void setExpressionPostal_Add (String ExpressionPostal_Add);

	/** Get Additional Postal Format.
	  * Format of the value;
 Can contain fixed format elements, Variables: "_lLoOaAcCa09"
	  */
	public String getExpressionPostal_Add();

    /** Column name HasPostal_Add */
    public static final String COLUMNNAME_HasPostal_Add = "HasPostal_Add";

	/** Set Additional Postal code.
	  * Has Additional Postal Code
	  */
	public void setHasPostal_Add (boolean HasPostal_Add);

	/** Get Additional Postal code.
	  * Has Additional Postal Code
	  */
	public boolean isHasPostal_Add();

    /** Column name HasRegion */
    public static final String COLUMNNAME_HasRegion = "HasRegion";

	/** Set Country has Region.
	  * Country contains Regions
	  */
	public void setHasRegion (boolean HasRegion);

	/** Get Country has Region.
	  * Country contains Regions
	  */
	public boolean isHasRegion();

    /** Column name IsActive */
    public static final String COLUMNNAME_IsActive = "IsActive";

	/** Set Active.
	  * The record is active in the system
	  */
	public void setIsActive (boolean IsActive);

	/** Get Active.
	  * The record is active in the system
	  */
	public boolean isActive();

    /** Column name IsAddressLinesLocalReverse */
    public static final String COLUMNNAME_IsAddressLinesLocalReverse = "IsAddressLinesLocalReverse";

	/** Set Reverse Local Address Lines.
	  * Print Local Address in reverse Order
	  */
	public void setIsAddressLinesLocalReverse (boolean IsAddressLinesLocalReverse);

	/** Get Reverse Local Address Lines.
	  * Print Local Address in reverse Order
	  */
	public boolean isAddressLinesLocalReverse();

    /** Column name IsAddressLinesReverse */
    public static final String COLUMNNAME_IsAddressLinesReverse = "IsAddressLinesReverse";

	/** Set Reverse Address Lines.
	  * Print Address in reverse Order
	  */
	public void setIsAddressLinesReverse (boolean IsAddressLinesReverse);

	/** Get Reverse Address Lines.
	  * Print Address in reverse Order
	  */
	public boolean isAddressLinesReverse();

    /** Column name IsPostcodeLookup */
    public static final String COLUMNNAME_IsPostcodeLookup = "IsPostcodeLookup";

	/** Set IsPostcodeLookup.
	  * Does this country have a post code web service
	  */
	public void setIsPostcodeLookup (boolean IsPostcodeLookup);

	/** Get IsPostcodeLookup.
	  * Does this country have a post code web service
	  */
	public boolean isPostcodeLookup();

    /** Column name LookupClassName */
    public static final String COLUMNNAME_LookupClassName = "LookupClassName";

	/** Set LookupClassName.
	  * The class name of the postcode lookup plugin
	  */
	public void setLookupClassName (String LookupClassName);

	/** Get LookupClassName.
	  * The class name of the postcode lookup plugin
	  */
	public String getLookupClassName();

    /** Column name LookupClientID */
    public static final String COLUMNNAME_LookupClientID = "LookupClientID";

	/** Set LookupClientID.
	  * The ClientID or Login submitted to the Lookup URL
	  */
	public void setLookupClientID (String LookupClientID);

	/** Get LookupClientID.
	  * The ClientID or Login submitted to the Lookup URL
	  */
	public String getLookupClientID();

    /** Column name LookupPassword */
    public static final String COLUMNNAME_LookupPassword = "LookupPassword";

	/** Set LookupPassword.
	  * The password submitted to the Lookup URL
	  */
	public void setLookupPassword (String LookupPassword);

	/** Get LookupPassword.
	  * The password submitted to the Lookup URL
	  */
	public String getLookupPassword();

    /** Column name LookupUrl */
    public static final String COLUMNNAME_LookupUrl = "LookupUrl";

	/** Set LookupUrl.
	  * The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	public void setLookupUrl (String LookupUrl);

	/** Get LookupUrl.
	  * The URL of the web service that the plugin connects to in order to retrieve postcode data
	  */
	public String getLookupUrl();

    /** Column name MediaSize */
    public static final String COLUMNNAME_MediaSize = "MediaSize";

	/** Set Media Size.
	  * Java Media Size
	  */
	public void setMediaSize (String MediaSize);

	/** Get Media Size.
	  * Java Media Size
	  */
	public String getMediaSize();

    /** Column name Name */
    public static final String COLUMNNAME_Name = "Name";

	/** Set Name.
	  * Alphanumeric identifier of the entity
	  */
	public void setName (String Name);

	/** Get Name.
	  * Alphanumeric identifier of the entity
	  */
	public String getName();

    /** Column name RegionName */
    public static final String COLUMNNAME_RegionName = "RegionName";

	/** Set Region.
	  * Name of the Region
	  */
	public void setRegionName (String RegionName);

	/** Get Region.
	  * Name of the Region
	  */
	public String getRegionName();

    /** Column name Updated */
    public static final String COLUMNNAME_Updated = "Updated";

	/** Get Updated.
	  * Date this record was updated
	  */
	public Timestamp getUpdated();

    /** Column name UpdatedBy */
    public static final String COLUMNNAME_UpdatedBy = "UpdatedBy";

	/** Get Updated By.
	  * User who updated this records
	  */
	public int getUpdatedBy();
}
