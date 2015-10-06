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

/** Generated Interface for AD_PrintFormatItem
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a
 */
public interface I_AD_PrintFormatItem 
{

    /** TableName=AD_PrintFormatItem */
    public static final String Table_Name = "AD_PrintFormatItem";

    /** AD_Table_ID=489 */
    public static final int Table_ID = MTable.getTable_ID(Table_Name);

    KeyNamePair Model = new KeyNamePair(Table_ID, Table_Name);

    /** AccessLevel = 7 - System - Client - Org 
     */
    BigDecimal accessLevel = BigDecimal.valueOf(7);

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

    /** Column name AD_PrintColor_ID */
    public static final String COLUMNNAME_AD_PrintColor_ID = "AD_PrintColor_ID";

	/** Set Print Color.
	  * Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID);

	/** Get Print Color.
	  * Color used for printing and display
	  */
	public int getAD_PrintColor_ID();

	public I_AD_PrintColor getAD_PrintColor() throws RuntimeException;

    /** Column name AD_PrintFont_ID */
    public static final String COLUMNNAME_AD_PrintFont_ID = "AD_PrintFont_ID";

	/** Set Print Font.
	  * Maintain Print Font
	  */
	public void setAD_PrintFont_ID (int AD_PrintFont_ID);

	/** Get Print Font.
	  * Maintain Print Font
	  */
	public int getAD_PrintFont_ID();

	public I_AD_PrintFont getAD_PrintFont() throws RuntimeException;

    /** Column name AD_PrintFormatChild_ID */
    public static final String COLUMNNAME_AD_PrintFormatChild_ID = "AD_PrintFormatChild_ID";

	/** Set Included Print Format.
	  * Print format that is included here.
	  */
	public void setAD_PrintFormatChild_ID (int AD_PrintFormatChild_ID);

	/** Get Included Print Format.
	  * Print format that is included here.
	  */
	public int getAD_PrintFormatChild_ID();

	public I_AD_PrintFormat getAD_PrintFormatChild() throws RuntimeException;

    /** Column name AD_PrintFormat_ID */
    public static final String COLUMNNAME_AD_PrintFormat_ID = "AD_PrintFormat_ID";

	/** Set Print Format.
	  * Data Print Format
	  */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID);

	/** Get Print Format.
	  * Data Print Format
	  */
	public int getAD_PrintFormat_ID();

	public I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException;

    /** Column name AD_PrintFormatItem_ID */
    public static final String COLUMNNAME_AD_PrintFormatItem_ID = "AD_PrintFormatItem_ID";

	/** Set Print Format Item.
	  * Item/Column in the Print format
	  */
	public void setAD_PrintFormatItem_ID (int AD_PrintFormatItem_ID);

	/** Get Print Format Item.
	  * Item/Column in the Print format
	  */
	public int getAD_PrintFormatItem_ID();

    /** Column name AD_PrintGraph_ID */
    public static final String COLUMNNAME_AD_PrintGraph_ID = "AD_PrintGraph_ID";

	/** Set Graph.
	  * Graph included in Reports
	  */
	public void setAD_PrintGraph_ID (int AD_PrintGraph_ID);

	/** Get Graph.
	  * Graph included in Reports
	  */
	public int getAD_PrintGraph_ID();

	public I_AD_PrintGraph getAD_PrintGraph() throws RuntimeException;

    /** Column name ArcDiameter */
    public static final String COLUMNNAME_ArcDiameter = "ArcDiameter";

	/** Set Arc Diameter.
	  * Arc Diameter for rounded Rectangles
	  */
	public void setArcDiameter (int ArcDiameter);

	/** Get Arc Diameter.
	  * Arc Diameter for rounded Rectangles
	  */
	public int getArcDiameter();

    /** Column name BarcodeType */
    public static final String COLUMNNAME_BarcodeType = "BarcodeType";

	/** Set Barcode Type.
	  * Type of barcode
	  */
	public void setBarcodeType (String BarcodeType);

	/** Get Barcode Type.
	  * Type of barcode
	  */
	public String getBarcodeType();

    /** Column name BelowColumn */
    public static final String COLUMNNAME_BelowColumn = "BelowColumn";

	/** Set Below Column.
	  * Print this column below the column index entered
	  */
	public void setBelowColumn (int BelowColumn);

	/** Get Below Column.
	  * Print this column below the column index entered
	  */
	public int getBelowColumn();

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

    /** Column name FieldAlignmentType */
    public static final String COLUMNNAME_FieldAlignmentType = "FieldAlignmentType";

	/** Set Field Alignment.
	  * Field Text Alignment
	  */
	public void setFieldAlignmentType (String FieldAlignmentType);

	/** Get Field Alignment.
	  * Field Text Alignment
	  */
	public String getFieldAlignmentType();

    /** Column name FormatPattern */
    public static final String COLUMNNAME_FormatPattern = "FormatPattern";

	/** Set Format Pattern.
	  * The pattern used to format a number or date.
	  */
	public void setFormatPattern (String FormatPattern);

	/** Get Format Pattern.
	  * The pattern used to format a number or date.
	  */
	public String getFormatPattern();

    /** Column name ImageIsAttached */
    public static final String COLUMNNAME_ImageIsAttached = "ImageIsAttached";

	/** Set Image attached.
	  * The image to be printed is attached to the record
	  */
	public void setImageIsAttached (boolean ImageIsAttached);

	/** Get Image attached.
	  * The image to be printed is attached to the record
	  */
	public boolean isImageIsAttached();

    /** Column name ImageURL */
    public static final String COLUMNNAME_ImageURL = "ImageURL";

	/** Set Image URL.
	  * URL of  image
	  */
	public void setImageURL (String ImageURL);

	/** Get Image URL.
	  * URL of  image
	  */
	public String getImageURL();

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

    /** Column name IsAveraged */
    public static final String COLUMNNAME_IsAveraged = "IsAveraged";

	/** Set Calculate Mean (?).
	  * Calculate Average of numeric content or length
	  */
	public void setIsAveraged (boolean IsAveraged);

	/** Get Calculate Mean (?).
	  * Calculate Average of numeric content or length
	  */
	public boolean isAveraged();

    /** Column name IsCentrallyMaintained */
    public static final String COLUMNNAME_IsCentrallyMaintained = "IsCentrallyMaintained";

	/** Set Centrally maintained.
	  * Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained);

	/** Get Centrally maintained.
	  * Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained();

    /** Column name IsCounted */
    public static final String COLUMNNAME_IsCounted = "IsCounted";

	/** Set Calculate Count (?).
	  * Count number of not empty elements
	  */
	public void setIsCounted (boolean IsCounted);

	/** Get Calculate Count (?).
	  * Count number of not empty elements
	  */
	public boolean isCounted();

    /** Column name IsDeviationCalc */
    public static final String COLUMNNAME_IsDeviationCalc = "IsDeviationCalc";

	/** Set Calculate Deviation (?).
	  * Calculate Standard Deviation
	  */
	public void setIsDeviationCalc (boolean IsDeviationCalc);

	/** Get Calculate Deviation (?).
	  * Calculate Standard Deviation
	  */
	public boolean isDeviationCalc();

    /** Column name IsFilledRectangle */
    public static final String COLUMNNAME_IsFilledRectangle = "IsFilledRectangle";

	/** Set Fill Shape.
	  * Fill the shape with the color selected
	  */
	public void setIsFilledRectangle (boolean IsFilledRectangle);

	/** Get Fill Shape.
	  * Fill the shape with the color selected
	  */
	public boolean isFilledRectangle();

    /** Column name IsFixedWidth */
    public static final String COLUMNNAME_IsFixedWidth = "IsFixedWidth";

	/** Set Fixed Width.
	  * Column has a fixed width
	  */
	public void setIsFixedWidth (boolean IsFixedWidth);

	/** Get Fixed Width.
	  * Column has a fixed width
	  */
	public boolean isFixedWidth();

    /** Column name IsGroupBy */
    public static final String COLUMNNAME_IsGroupBy = "IsGroupBy";

	/** Set Group by.
	  * After a group change, totals, etc. are printed
	  */
	public void setIsGroupBy (boolean IsGroupBy);

	/** Get Group by.
	  * After a group change, totals, etc. are printed
	  */
	public boolean isGroupBy();

    /** Column name IsHeightOneLine */
    public static final String COLUMNNAME_IsHeightOneLine = "IsHeightOneLine";

	/** Set One Line Only.
	  * If selected, only one line is printed
	  */
	public void setIsHeightOneLine (boolean IsHeightOneLine);

	/** Get One Line Only.
	  * If selected, only one line is printed
	  */
	public boolean isHeightOneLine();

    /** Column name IsImageField */
    public static final String COLUMNNAME_IsImageField = "IsImageField";

	/** Set Image Field.
	  * The image is retrieved from the data column
	  */
	public void setIsImageField (boolean IsImageField);

	/** Get Image Field.
	  * The image is retrieved from the data column
	  */
	public boolean isImageField();

    /** Column name IsMaxCalc */
    public static final String COLUMNNAME_IsMaxCalc = "IsMaxCalc";

	/** Set Calculate Maximim (?).
	  * Calculate the maximim amount
	  */
	public void setIsMaxCalc (boolean IsMaxCalc);

	/** Get Calculate Maximim (?).
	  * Calculate the maximim amount
	  */
	public boolean isMaxCalc();

    /** Column name IsMinCalc */
    public static final String COLUMNNAME_IsMinCalc = "IsMinCalc";

	/** Set Calculate Minimum (?).
	  * Calculate the minimum amount
	  */
	public void setIsMinCalc (boolean IsMinCalc);

	/** Get Calculate Minimum (?).
	  * Calculate the minimum amount
	  */
	public boolean isMinCalc();

    /** Column name IsNextLine */
    public static final String COLUMNNAME_IsNextLine = "IsNextLine";

	/** Set Next Line.
	  * Print item on next line
	  */
	public void setIsNextLine (boolean IsNextLine);

	/** Get Next Line.
	  * Print item on next line
	  */
	public boolean isNextLine();

    /** Column name IsNextPage */
    public static final String COLUMNNAME_IsNextPage = "IsNextPage";

	/** Set Next Page.
	  * The column is printed on the next page
	  */
	public void setIsNextPage (boolean IsNextPage);

	/** Get Next Page.
	  * The column is printed on the next page
	  */
	public boolean isNextPage();

    /** Column name IsOrderBy */
    public static final String COLUMNNAME_IsOrderBy = "IsOrderBy";

	/** Set Order by.
	  * Include in sort order
	  */
	public void setIsOrderBy (boolean IsOrderBy);

	/** Get Order by.
	  * Include in sort order
	  */
	public boolean isOrderBy();

    /** Column name IsPageBreak */
    public static final String COLUMNNAME_IsPageBreak = "IsPageBreak";

	/** Set Page break.
	  * Start with new page
	  */
	public void setIsPageBreak (boolean IsPageBreak);

	/** Get Page break.
	  * Start with new page
	  */
	public boolean isPageBreak();

    /** Column name IsPrinted */
    public static final String COLUMNNAME_IsPrinted = "IsPrinted";

	/** Set Printed.
	  * Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted);

	/** Get Printed.
	  * Indicates if this document / line is printed
	  */
	public boolean isPrinted();

    /** Column name IsRelativePosition */
    public static final String COLUMNNAME_IsRelativePosition = "IsRelativePosition";

	/** Set Relative Position.
	  * The item is relative positioned (not absolute)
	  */
	public void setIsRelativePosition (boolean IsRelativePosition);

	/** Get Relative Position.
	  * The item is relative positioned (not absolute)
	  */
	public boolean isRelativePosition();

    /** Column name IsRunningTotal */
    public static final String COLUMNNAME_IsRunningTotal = "IsRunningTotal";

	/** Set Running Total.
	  * Create a running total (sum)
	  */
	public void setIsRunningTotal (boolean IsRunningTotal);

	/** Get Running Total.
	  * Create a running total (sum)
	  */
	public boolean isRunningTotal();

    /** Column name IsSetNLPosition */
    public static final String COLUMNNAME_IsSetNLPosition = "IsSetNLPosition";

	/** Set Set NL Position.
	  * Set New Line Position
	  */
	public void setIsSetNLPosition (boolean IsSetNLPosition);

	/** Get Set NL Position.
	  * Set New Line Position
	  */
	public boolean isSetNLPosition();

    /** Column name IsSummarized */
    public static final String COLUMNNAME_IsSummarized = "IsSummarized";

	/** Set Calculate Sum (?).
	  * Calculate the Sum of numeric content or length
	  */
	public void setIsSummarized (boolean IsSummarized);

	/** Get Calculate Sum (?).
	  * Calculate the Sum of numeric content or length
	  */
	public boolean isSummarized();

    /** Column name IsSuppressNull */
    public static final String COLUMNNAME_IsSuppressNull = "IsSuppressNull";

	/** Set Suppress Null.
	  * Suppress columns or elements with NULL value
	  */
	public void setIsSuppressNull (boolean IsSuppressNull);

	/** Get Suppress Null.
	  * Suppress columns or elements with NULL value
	  */
	public boolean isSuppressNull();

    /** Column name IsSuppressRepeats */
    public static final String COLUMNNAME_IsSuppressRepeats = "IsSuppressRepeats";

	/** Set Suppress Repeats.
	  * Suppress repeated elements in column.
	  */
	public void setIsSuppressRepeats (boolean IsSuppressRepeats);

	/** Get Suppress Repeats.
	  * Suppress repeated elements in column.
	  */
	public boolean isSuppressRepeats();

    /** Column name IsVarianceCalc */
    public static final String COLUMNNAME_IsVarianceCalc = "IsVarianceCalc";

	/** Set Calculate Variance (?�).
	  * Calculate Variance
	  */
	public void setIsVarianceCalc (boolean IsVarianceCalc);

	/** Get Calculate Variance (?�).
	  * Calculate Variance
	  */
	public boolean isVarianceCalc();

    /** Column name LineAlignmentType */
    public static final String COLUMNNAME_LineAlignmentType = "LineAlignmentType";

	/** Set Line Alignment.
	  * Line Alignment
	  */
	public void setLineAlignmentType (String LineAlignmentType);

	/** Get Line Alignment.
	  * Line Alignment
	  */
	public String getLineAlignmentType();

    /** Column name LineWidth */
    public static final String COLUMNNAME_LineWidth = "LineWidth";

	/** Set Line Width.
	  * Width of the lines
	  */
	public void setLineWidth (int LineWidth);

	/** Get Line Width.
	  * Width of the lines
	  */
	public int getLineWidth();

    /** Column name MaxHeight */
    public static final String COLUMNNAME_MaxHeight = "MaxHeight";

	/** Set Max Height.
	  * Maximum Height in 1/72 if an inch - 0 = no restriction
	  */
	public void setMaxHeight (int MaxHeight);

	/** Get Max Height.
	  * Maximum Height in 1/72 if an inch - 0 = no restriction
	  */
	public int getMaxHeight();

    /** Column name MaxWidth */
    public static final String COLUMNNAME_MaxWidth = "MaxWidth";

	/** Set Max Width.
	  * Maximum Width in 1/72 if an inch - 0 = no restriction
	  */
	public void setMaxWidth (int MaxWidth);

	/** Get Max Width.
	  * Maximum Width in 1/72 if an inch - 0 = no restriction
	  */
	public int getMaxWidth();

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

    /** Column name PrintAreaType */
    public static final String COLUMNNAME_PrintAreaType = "PrintAreaType";

	/** Set Area.
	  * Print Area
	  */
	public void setPrintAreaType (String PrintAreaType);

	/** Get Area.
	  * Print Area
	  */
	public String getPrintAreaType();

    /** Column name PrintFormatType */
    public static final String COLUMNNAME_PrintFormatType = "PrintFormatType";

	/** Set Format Type.
	  * Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType);

	/** Get Format Type.
	  * Print Format Type
	  */
	public String getPrintFormatType();

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

    /** Column name PrintNameSuffix */
    public static final String COLUMNNAME_PrintNameSuffix = "PrintNameSuffix";

	/** Set Print Label Suffix.
	  * The label text to be printed on a document or correspondence after the field
	  */
	public void setPrintNameSuffix (String PrintNameSuffix);

	/** Get Print Label Suffix.
	  * The label text to be printed on a document or correspondence after the field
	  */
	public String getPrintNameSuffix();

    /** Column name RunningTotalLines */
    public static final String COLUMNNAME_RunningTotalLines = "RunningTotalLines";

	/** Set Running Total Lines.
	  * Create Running Total Lines (page break) every x lines
	  */
	public void setRunningTotalLines (int RunningTotalLines);

	/** Get Running Total Lines.
	  * Create Running Total Lines (page break) every x lines
	  */
	public int getRunningTotalLines();

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

    /** Column name ShapeType */
    public static final String COLUMNNAME_ShapeType = "ShapeType";

	/** Set Shape Type.
	  * Type of the shape to be painted
	  */
	public void setShapeType (String ShapeType);

	/** Get Shape Type.
	  * Type of the shape to be painted
	  */
	public String getShapeType();

    /** Column name SortNo */
    public static final String COLUMNNAME_SortNo = "SortNo";

	/** Set Record Sort No.
	  * Determines in what order the records are displayed
	  */
	public void setSortNo (int SortNo);

	/** Get Record Sort No.
	  * Determines in what order the records are displayed
	  */
	public int getSortNo();

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

    /** Column name XSpace */
    public static final String COLUMNNAME_XSpace = "XSpace";

	/** Set X Space.
	  * Relative X (horizontal) space in 1/72 of an inch
	  */
	public void setXSpace (int XSpace);

	/** Get X Space.
	  * Relative X (horizontal) space in 1/72 of an inch
	  */
	public int getXSpace();

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

    /** Column name YSpace */
    public static final String COLUMNNAME_YSpace = "YSpace";

	/** Set Y Space.
	  * Relative Y (vertical) space in 1/72 of an inch
	  */
	public void setYSpace (int YSpace);

	/** Get Y Space.
	  * Relative Y (vertical) space in 1/72 of an inch
	  */
	public int getYSpace();
}
