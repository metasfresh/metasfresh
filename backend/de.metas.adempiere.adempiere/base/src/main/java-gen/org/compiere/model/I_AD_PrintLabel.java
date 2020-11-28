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

/** Generated Interface for AD_PrintLabel
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_PrintLabel 
{

    /** TableName=AD_PrintLabel */
    public static final String Table_Name = "AD_PrintLabel";

    /** AD_Table_ID=570 */
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

    /** Column name AD_LabelPrinter_ID */
    public static final String COLUMNNAME_AD_LabelPrinter_ID = "AD_LabelPrinter_ID";

	/** Set Label printer.
	  * Label Printer Definition
	  */
	public void setAD_LabelPrinter_ID (int AD_LabelPrinter_ID);

	/** Get Label printer.
	  * Label Printer Definition
	  */
	public int getAD_LabelPrinter_ID();

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

    /** Column name AD_PrintLabel_ID */
    public static final String COLUMNNAME_AD_PrintLabel_ID = "AD_PrintLabel_ID";

	/** Set Print Label.
	  * Label Format to print
	  */
	public void setAD_PrintLabel_ID (int AD_PrintLabel_ID);

	/** Get Print Label.
	  * Label Format to print
	  */
	public int getAD_PrintLabel_ID();

    /** Column name AD_Table_ID */
    public static final String COLUMNNAME_AD_Table_ID = "AD_Table_ID";

	/** Set Table.
	  * Database Table information
	  */
	public void setAD_Table_ID (int AD_Table_ID);

	/** Get Table.
	  * Database Table information
	  */
	public int getAD_Table_ID();

	public I_AD_Table getAD_Table() throws RuntimeException;

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

    /** Column name IsLandscape */
    public static final String COLUMNNAME_IsLandscape = "IsLandscape";

	/** Set Landscape.
	  * Landscape orientation
	  */
	public void setIsLandscape (boolean IsLandscape);

	/** Get Landscape.
	  * Landscape orientation
	  */
	public boolean isLandscape();

    /** Column name LabelHeight */
    public static final String COLUMNNAME_LabelHeight = "LabelHeight";

	/** Set Label Height.
	  * Height of the label
	  */
	public void setLabelHeight (int LabelHeight);

	/** Get Label Height.
	  * Height of the label
	  */
	public int getLabelHeight();

    /** Column name LabelWidth */
    public static final String COLUMNNAME_LabelWidth = "LabelWidth";

	/** Set Label Width.
	  * Width of the Label
	  */
	public void setLabelWidth (int LabelWidth);

	/** Get Label Width.
	  * Width of the Label
	  */
	public int getLabelWidth();

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

    /** Column name PrinterName */
    public static final String COLUMNNAME_PrinterName = "PrinterName";

	/** Set Printer Name.
	  * Name of the Printer
	  */
	public void setPrinterName (String PrinterName);

	/** Get Printer Name.
	  * Name of the Printer
	  */
	public String getPrinterName();

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
