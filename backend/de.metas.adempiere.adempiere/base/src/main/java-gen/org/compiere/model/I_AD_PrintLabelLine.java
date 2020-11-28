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

/** Generated Interface for AD_PrintLabelLine
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_PrintLabelLine 
{

    /** TableName=AD_PrintLabelLine */
    public static final String Table_Name = "AD_PrintLabelLine";

    /** AD_Table_ID=569 */
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

    /** Column name AD_Column_ID */
    public static final String COLUMNNAME_AD_Column_ID = "AD_Column_ID";

	/** Set Column.
	  * Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID);

	/** Get Column.
	  * Column in the table
	  */
	public int getAD_Column_ID();

	public I_AD_Column getAD_Column() throws RuntimeException;

    /** Column name AD_LabelPrinterFunction_ID */
    public static final String COLUMNNAME_AD_LabelPrinterFunction_ID = "AD_LabelPrinterFunction_ID";

	/** Set Label printer Function.
	  * Function of Label Printer
	  */
	public void setAD_LabelPrinterFunction_ID (int AD_LabelPrinterFunction_ID);

	/** Get Label printer Function.
	  * Function of Label Printer
	  */
	public int getAD_LabelPrinterFunction_ID();

	public I_AD_LabelPrinterFunction getAD_LabelPrinterFunction() throws RuntimeException;

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

	public I_AD_PrintLabel getAD_PrintLabel() throws RuntimeException;

    /** Column name AD_PrintLabelLine_ID */
    public static final String COLUMNNAME_AD_PrintLabelLine_ID = "AD_PrintLabelLine_ID";

	/** Set Print Label Line.
	  * Print Label Line Format
	  */
	public void setAD_PrintLabelLine_ID (int AD_PrintLabelLine_ID);

	/** Get Print Label Line.
	  * Print Label Line Format
	  */
	public int getAD_PrintLabelLine_ID();

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

    /** Column name LabelFormatType */
    public static final String COLUMNNAME_LabelFormatType = "LabelFormatType";

	/** Set Label Format Type.
	  * Label Format Type
	  */
	public void setLabelFormatType (String LabelFormatType);

	/** Get Label Format Type.
	  * Label Format Type
	  */
	public String getLabelFormatType();

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

    /** Column name PrintName */
    public static final String COLUMNNAME_PrintName = "PrintName";

	/** Set Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName);

	/** Get Print Text.
	  * The label text to be printed on a document or correspondence.
	  */
	public String getPrintName();

    /** Column name SeqNo */
    public static final String COLUMNNAME_SeqNo = "SeqNo";

	/** Set Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public void setSeqNo (int SeqNo);

	/** Get Sequence.
	  * Method of ordering records;
 lowest number comes first
	  */
	public int getSeqNo();

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

    /** Column name XPosition */
    public static final String COLUMNNAME_XPosition = "XPosition";

	/** Set X Position.
	  * Absolute X (horizontal) position in 1/72 of an inch
	  */
	public void setXPosition (int XPosition);

	/** Get X Position.
	  * Absolute X (horizontal) position in 1/72 of an inch
	  */
	public int getXPosition();

    /** Column name YPosition */
    public static final String COLUMNNAME_YPosition = "YPosition";

	/** Set Y Position.
	  * Absolute Y (vertical) position in 1/72 of an inch
	  */
	public void setYPosition (int YPosition);

	/** Get Y Position.
	  * Absolute Y (vertical) position in 1/72 of an inch
	  */
	public int getYPosition();
}
