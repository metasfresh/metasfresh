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

/** Generated Model for AD_PrintFormatItem
 *  @author Adempiere (generated) 
 *  @version Release 3.5.4a - $Id$ */
public class X_AD_PrintFormatItem extends PO implements I_AD_PrintFormatItem, I_Persistent 
{

	/**
	 *
	 */
	private static final long serialVersionUID = 20090915L;

    /** Standard Constructor */
    public X_AD_PrintFormatItem (Properties ctx, int AD_PrintFormatItem_ID, String trxName)
    {
      super (ctx, AD_PrintFormatItem_ID, trxName);
      /** if (AD_PrintFormatItem_ID == 0)
        {
			setAD_Column_ID (0);
			setAD_PrintFormatChild_ID (0);
			setAD_PrintFormat_ID (0);
			setAD_PrintFormatItem_ID (0);
			setFieldAlignmentType (null);
// D
			setImageIsAttached (false);
			setIsAveraged (false);
			setIsCentrallyMaintained (false);
			setIsCounted (false);
			setIsDeviationCalc (false);
			setIsFilledRectangle (false);
// N
			setIsFixedWidth (false);
			setIsGroupBy (false);
			setIsHeightOneLine (true);
// Y
			setIsImageField (false);
			setIsMaxCalc (false);
			setIsMinCalc (false);
			setIsNextLine (true);
// Y
			setIsNextPage (false);
			setIsOrderBy (false);
			setIsPageBreak (false);
			setIsPrinted (true);
// Y
			setIsRelativePosition (true);
// Y
			setIsRunningTotal (false);
			setIsSetNLPosition (false);
			setIsSummarized (false);
			setIsSuppressNull (false);
			setIsSuppressRepeats (false);
// N
			setIsVarianceCalc (false);
			setLineAlignmentType (null);
// X
			setMaxHeight (0);
			setMaxWidth (0);
			setName (null);
			setPrintAreaType (null);
// C
			setPrintFormatType (null);
// F
			setSeqNo (0);
// @SQL=SELECT NVL(MAX(SeqNo),0)+10 AS DefaultValue FROM AD_PrintFormatItem WHERE AD_PrintFormat_ID=@AD_PrintFormat_ID@
			setSortNo (0);
			setXPosition (0);
			setXSpace (0);
			setYPosition (0);
			setYSpace (0);
        } */
    }

    /** Load Constructor */
    public X_AD_PrintFormatItem (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }

    /** AccessLevel
      * @return 7 - System - Client - Org 
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
      StringBuffer sb = new StringBuffer ("X_AD_PrintFormatItem[")
        .append(get_ID()).append("]");
      return sb.toString();
    }

	public I_AD_Column getAD_Column() throws RuntimeException
    {
		return (I_AD_Column)MTable.get(getCtx(), I_AD_Column.Table_Name)
			.getPO(getAD_Column_ID(), get_TrxName());	}

	/** Set Column.
		@param AD_Column_ID 
		Column in the table
	  */
	public void setAD_Column_ID (int AD_Column_ID)
	{
		if (AD_Column_ID < 1) 
			set_Value (COLUMNNAME_AD_Column_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Column_ID, Integer.valueOf(AD_Column_ID));
	}

	/** Get Column.
		@return Column in the table
	  */
	public int getAD_Column_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_Column_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintColor getAD_PrintColor() throws RuntimeException
    {
		return (I_AD_PrintColor)MTable.get(getCtx(), I_AD_PrintColor.Table_Name)
			.getPO(getAD_PrintColor_ID(), get_TrxName());	}

	/** Set Print Color.
		@param AD_PrintColor_ID 
		Color used for printing and display
	  */
	public void setAD_PrintColor_ID (int AD_PrintColor_ID)
	{
		if (AD_PrintColor_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintColor_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintColor_ID, Integer.valueOf(AD_PrintColor_ID));
	}

	/** Get Print Color.
		@return Color used for printing and display
	  */
	public int getAD_PrintColor_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintColor_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFont getAD_PrintFont() throws RuntimeException
    {
		return (I_AD_PrintFont)MTable.get(getCtx(), I_AD_PrintFont.Table_Name)
			.getPO(getAD_PrintFont_ID(), get_TrxName());	}

	/** Set Print Font.
		@param AD_PrintFont_ID 
		Maintain Print Font
	  */
	public void setAD_PrintFont_ID (int AD_PrintFont_ID)
	{
		if (AD_PrintFont_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFont_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFont_ID, Integer.valueOf(AD_PrintFont_ID));
	}

	/** Get Print Font.
		@return Maintain Print Font
	  */
	public int getAD_PrintFont_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFont_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormat getAD_PrintFormatChild() throws RuntimeException
    {
		return (I_AD_PrintFormat)MTable.get(getCtx(), I_AD_PrintFormat.Table_Name)
			.getPO(getAD_PrintFormatChild_ID(), get_TrxName());	}

	/** Set Included Print Format.
		@param AD_PrintFormatChild_ID 
		Print format that is included here.
	  */
	public void setAD_PrintFormatChild_ID (int AD_PrintFormatChild_ID)
	{
		if (AD_PrintFormatChild_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintFormatChild_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintFormatChild_ID, Integer.valueOf(AD_PrintFormatChild_ID));
	}

	/** Get Included Print Format.
		@return Print format that is included here.
	  */
	public int getAD_PrintFormatChild_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormatChild_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintFormat getAD_PrintFormat() throws RuntimeException
    {
		return (I_AD_PrintFormat)MTable.get(getCtx(), I_AD_PrintFormat.Table_Name)
			.getPO(getAD_PrintFormat_ID(), get_TrxName());	}

	/** Set Print Format.
		@param AD_PrintFormat_ID 
		Data Print Format
	  */
	public void setAD_PrintFormat_ID (int AD_PrintFormat_ID)
	{
		if (AD_PrintFormat_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormat_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormat_ID, Integer.valueOf(AD_PrintFormat_ID));
	}

	/** Get Print Format.
		@return Data Print Format
	  */
	public int getAD_PrintFormat_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormat_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Print Format Item.
		@param AD_PrintFormatItem_ID 
		Item/Column in the Print format
	  */
	public void setAD_PrintFormatItem_ID (int AD_PrintFormatItem_ID)
	{
		if (AD_PrintFormatItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormatItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_PrintFormatItem_ID, Integer.valueOf(AD_PrintFormatItem_ID));
	}

	/** Get Print Format Item.
		@return Item/Column in the Print format
	  */
	public int getAD_PrintFormatItem_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintFormatItem_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	public I_AD_PrintGraph getAD_PrintGraph() throws RuntimeException
    {
		return (I_AD_PrintGraph)MTable.get(getCtx(), I_AD_PrintGraph.Table_Name)
			.getPO(getAD_PrintGraph_ID(), get_TrxName());	}

	/** Set Graph.
		@param AD_PrintGraph_ID 
		Graph included in Reports
	  */
	public void setAD_PrintGraph_ID (int AD_PrintGraph_ID)
	{
		if (AD_PrintGraph_ID < 1) 
			set_Value (COLUMNNAME_AD_PrintGraph_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PrintGraph_ID, Integer.valueOf(AD_PrintGraph_ID));
	}

	/** Get Graph.
		@return Graph included in Reports
	  */
	public int getAD_PrintGraph_ID () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_AD_PrintGraph_ID);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Arc Diameter.
		@param ArcDiameter 
		Arc Diameter for rounded Rectangles
	  */
	public void setArcDiameter (int ArcDiameter)
	{
		set_Value (COLUMNNAME_ArcDiameter, Integer.valueOf(ArcDiameter));
	}

	/** Get Arc Diameter.
		@return Arc Diameter for rounded Rectangles
	  */
	public int getArcDiameter () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_ArcDiameter);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** BarcodeType AD_Reference_ID=377 */
	public static final int BARCODETYPE_AD_Reference_ID=377;
	/** Codabar 2 of 7 linear = 2o9 */
	public static final String BARCODETYPE_Codabar2Of7Linear = "2o9";
	/** Code 39  3 of 9 linear w/o Checksum = 3o9 */
	public static final String BARCODETYPE_Code393Of9LinearWOChecksum = "3o9";
	/** Codeabar linear = COD */
	public static final String BARCODETYPE_CodeabarLinear = "COD";
	/** Code 128 dynamically switching = C28 */
	public static final String BARCODETYPE_Code128DynamicallySwitching = "C28";
	/** Code 128 A character set = 28A */
	public static final String BARCODETYPE_Code128ACharacterSet = "28A";
	/** Code 128 B character set = 28B */
	public static final String BARCODETYPE_Code128BCharacterSet = "28B";
	/** Code 128 C character set = 28C */
	public static final String BARCODETYPE_Code128CCharacterSet = "28C";
	/** Code 39 linear with Checksum = C39 */
	public static final String BARCODETYPE_Code39LinearWithChecksum = "C39";
	/** EAN 128 = E28 */
	public static final String BARCODETYPE_EAN128 = "E28";
	/** Global Trade Item No GTIN UCC/EAN 128 = GTN */
	public static final String BARCODETYPE_GlobalTradeItemNoGTINUCCEAN128 = "GTN";
	/** Codabar Monarch linear = MON */
	public static final String BARCODETYPE_CodabarMonarchLinear = "MON";
	/** Codabar NW-7 linear = NW7 */
	public static final String BARCODETYPE_CodabarNW_7Linear = "NW7";
	/** PDF417 two dimensional = 417 */
	public static final String BARCODETYPE_PDF417TwoDimensional = "417";
	/** SCC-14 shipping code UCC/EAN 128 = C14 */
	public static final String BARCODETYPE_SCC_14ShippingCodeUCCEAN128 = "C14";
	/** Shipment ID number UCC/EAN 128 = SID */
	public static final String BARCODETYPE_ShipmentIDNumberUCCEAN128 = "SID";
	/** UCC 128 = U28 */
	public static final String BARCODETYPE_UCC128 = "U28";
	/** Code 39 USD3 with Checksum = US3 */
	public static final String BARCODETYPE_Code39USD3WithChecksum = "US3";
	/** Codabar USD-4 linear = US4 */
	public static final String BARCODETYPE_CodabarUSD_4Linear = "US4";
	/** US Postal Service UCC/EAN 128 = USP */
	public static final String BARCODETYPE_USPostalServiceUCCEAN128 = "USP";
	/** SSCC-18 number UCC/EAN 128 = C18 */
	public static final String BARCODETYPE_SSCC_18NumberUCCEAN128 = "C18";
	/** Code 39 USD3 w/o Checksum = us3 */
	public static final String BARCODETYPE_Code39USD3WOChecksum = "us3";
	/** Code 39  3 of 9 linear with Checksum = 3O9 */
	public static final String BARCODETYPE_Code393Of9LinearWithChecksum = "3O9";
	/** Code 39 linear w/o Checksum = c39 */
	public static final String BARCODETYPE_Code39LinearWOChecksum = "c39";
	/** EAN 13 = E13 */
	public static final String BARCODETYPE_EAN13 = "E13";
	/** Set Barcode Type.
		@param BarcodeType 
		Type of barcode
	  */
	public void setBarcodeType (String BarcodeType)
	{

		set_Value (COLUMNNAME_BarcodeType, BarcodeType);
	}

	/** Get Barcode Type.
		@return Type of barcode
	  */
	public String getBarcodeType () 
	{
		return (String)get_Value(COLUMNNAME_BarcodeType);
	}

	/** Set Below Column.
		@param BelowColumn 
		Print this column below the column index entered
	  */
	public void setBelowColumn (int BelowColumn)
	{
		set_Value (COLUMNNAME_BelowColumn, Integer.valueOf(BelowColumn));
	}

	/** Get Below Column.
		@return Print this column below the column index entered
	  */
	public int getBelowColumn () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_BelowColumn);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** FieldAlignmentType AD_Reference_ID=253 */
	public static final int FIELDALIGNMENTTYPE_AD_Reference_ID=253;
	/** Default = D */
	public static final String FIELDALIGNMENTTYPE_Default = "D";
	/** Leading (left) = L */
	public static final String FIELDALIGNMENTTYPE_LeadingLeft = "L";
	/** Trailing (right) = T */
	public static final String FIELDALIGNMENTTYPE_TrailingRight = "T";
	/** Block = B */
	public static final String FIELDALIGNMENTTYPE_Block = "B";
	/** Center = C */
	public static final String FIELDALIGNMENTTYPE_Center = "C";
	/** Set Field Alignment.
		@param FieldAlignmentType 
		Field Text Alignment
	  */
	public void setFieldAlignmentType (String FieldAlignmentType)
	{

		set_Value (COLUMNNAME_FieldAlignmentType, FieldAlignmentType);
	}

	/** Get Field Alignment.
		@return Field Text Alignment
	  */
	public String getFieldAlignmentType () 
	{
		return (String)get_Value(COLUMNNAME_FieldAlignmentType);
	}

	/** Set Format Pattern.
		@param FormatPattern 
		The pattern used to format a number or date.
	  */
	public void setFormatPattern (String FormatPattern)
	{
		set_Value (COLUMNNAME_FormatPattern, FormatPattern);
	}

	/** Get Format Pattern.
		@return The pattern used to format a number or date.
	  */
	public String getFormatPattern () 
	{
		return (String)get_Value(COLUMNNAME_FormatPattern);
	}

	/** Set Image attached.
		@param ImageIsAttached 
		The image to be printed is attached to the record
	  */
	public void setImageIsAttached (boolean ImageIsAttached)
	{
		set_Value (COLUMNNAME_ImageIsAttached, Boolean.valueOf(ImageIsAttached));
	}

	/** Get Image attached.
		@return The image to be printed is attached to the record
	  */
	public boolean isImageIsAttached () 
	{
		Object oo = get_Value(COLUMNNAME_ImageIsAttached);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Image URL.
		@param ImageURL 
		URL of  image
	  */
	public void setImageURL (String ImageURL)
	{
		set_Value (COLUMNNAME_ImageURL, ImageURL);
	}

	/** Get Image URL.
		@return URL of  image
	  */
	public String getImageURL () 
	{
		return (String)get_Value(COLUMNNAME_ImageURL);
	}

	/** Set Calculate Mean (?).
		@param IsAveraged 
		Calculate Average of numeric content or length
	  */
	public void setIsAveraged (boolean IsAveraged)
	{
		set_Value (COLUMNNAME_IsAveraged, Boolean.valueOf(IsAveraged));
	}

	/** Get Calculate Mean (?).
		@return Calculate Average of numeric content or length
	  */
	public boolean isAveraged () 
	{
		Object oo = get_Value(COLUMNNAME_IsAveraged);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Centrally maintained.
		@param IsCentrallyMaintained 
		Information maintained in System Element table
	  */
	public void setIsCentrallyMaintained (boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, Boolean.valueOf(IsCentrallyMaintained));
	}

	/** Get Centrally maintained.
		@return Information maintained in System Element table
	  */
	public boolean isCentrallyMaintained () 
	{
		Object oo = get_Value(COLUMNNAME_IsCentrallyMaintained);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Count (?).
		@param IsCounted 
		Count number of not empty elements
	  */
	public void setIsCounted (boolean IsCounted)
	{
		set_Value (COLUMNNAME_IsCounted, Boolean.valueOf(IsCounted));
	}

	/** Get Calculate Count (?).
		@return Count number of not empty elements
	  */
	public boolean isCounted () 
	{
		Object oo = get_Value(COLUMNNAME_IsCounted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Deviation (?).
		@param IsDeviationCalc 
		Calculate Standard Deviation
	  */
	public void setIsDeviationCalc (boolean IsDeviationCalc)
	{
		set_Value (COLUMNNAME_IsDeviationCalc, Boolean.valueOf(IsDeviationCalc));
	}

	/** Get Calculate Deviation (?).
		@return Calculate Standard Deviation
	  */
	public boolean isDeviationCalc () 
	{
		Object oo = get_Value(COLUMNNAME_IsDeviationCalc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fill Shape.
		@param IsFilledRectangle 
		Fill the shape with the color selected
	  */
	public void setIsFilledRectangle (boolean IsFilledRectangle)
	{
		set_Value (COLUMNNAME_IsFilledRectangle, Boolean.valueOf(IsFilledRectangle));
	}

	/** Get Fill Shape.
		@return Fill the shape with the color selected
	  */
	public boolean isFilledRectangle () 
	{
		Object oo = get_Value(COLUMNNAME_IsFilledRectangle);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Fixed Width.
		@param IsFixedWidth 
		Column has a fixed width
	  */
	public void setIsFixedWidth (boolean IsFixedWidth)
	{
		set_Value (COLUMNNAME_IsFixedWidth, Boolean.valueOf(IsFixedWidth));
	}

	/** Get Fixed Width.
		@return Column has a fixed width
	  */
	public boolean isFixedWidth () 
	{
		Object oo = get_Value(COLUMNNAME_IsFixedWidth);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Group by.
		@param IsGroupBy 
		After a group change, totals, etc. are printed
	  */
	public void setIsGroupBy (boolean IsGroupBy)
	{
		set_Value (COLUMNNAME_IsGroupBy, Boolean.valueOf(IsGroupBy));
	}

	/** Get Group by.
		@return After a group change, totals, etc. are printed
	  */
	public boolean isGroupBy () 
	{
		Object oo = get_Value(COLUMNNAME_IsGroupBy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set One Line Only.
		@param IsHeightOneLine 
		If selected, only one line is printed
	  */
	public void setIsHeightOneLine (boolean IsHeightOneLine)
	{
		set_Value (COLUMNNAME_IsHeightOneLine, Boolean.valueOf(IsHeightOneLine));
	}

	/** Get One Line Only.
		@return If selected, only one line is printed
	  */
	public boolean isHeightOneLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsHeightOneLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Image Field.
		@param IsImageField 
		The image is retrieved from the data column
	  */
	public void setIsImageField (boolean IsImageField)
	{
		set_Value (COLUMNNAME_IsImageField, Boolean.valueOf(IsImageField));
	}

	/** Get Image Field.
		@return The image is retrieved from the data column
	  */
	public boolean isImageField () 
	{
		Object oo = get_Value(COLUMNNAME_IsImageField);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Maximim (?).
		@param IsMaxCalc 
		Calculate the maximim amount
	  */
	public void setIsMaxCalc (boolean IsMaxCalc)
	{
		set_Value (COLUMNNAME_IsMaxCalc, Boolean.valueOf(IsMaxCalc));
	}

	/** Get Calculate Maximim (?).
		@return Calculate the maximim amount
	  */
	public boolean isMaxCalc () 
	{
		Object oo = get_Value(COLUMNNAME_IsMaxCalc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Minimum (?).
		@param IsMinCalc 
		Calculate the minimum amount
	  */
	public void setIsMinCalc (boolean IsMinCalc)
	{
		set_Value (COLUMNNAME_IsMinCalc, Boolean.valueOf(IsMinCalc));
	}

	/** Get Calculate Minimum (?).
		@return Calculate the minimum amount
	  */
	public boolean isMinCalc () 
	{
		Object oo = get_Value(COLUMNNAME_IsMinCalc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Next Line.
		@param IsNextLine 
		Print item on next line
	  */
	public void setIsNextLine (boolean IsNextLine)
	{
		set_Value (COLUMNNAME_IsNextLine, Boolean.valueOf(IsNextLine));
	}

	/** Get Next Line.
		@return Print item on next line
	  */
	public boolean isNextLine () 
	{
		Object oo = get_Value(COLUMNNAME_IsNextLine);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Next Page.
		@param IsNextPage 
		The column is printed on the next page
	  */
	public void setIsNextPage (boolean IsNextPage)
	{
		set_Value (COLUMNNAME_IsNextPage, Boolean.valueOf(IsNextPage));
	}

	/** Get Next Page.
		@return The column is printed on the next page
	  */
	public boolean isNextPage () 
	{
		Object oo = get_Value(COLUMNNAME_IsNextPage);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Order by.
		@param IsOrderBy 
		Include in sort order
	  */
	public void setIsOrderBy (boolean IsOrderBy)
	{
		set_Value (COLUMNNAME_IsOrderBy, Boolean.valueOf(IsOrderBy));
	}

	/** Get Order by.
		@return Include in sort order
	  */
	public boolean isOrderBy () 
	{
		Object oo = get_Value(COLUMNNAME_IsOrderBy);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Page break.
		@param IsPageBreak 
		Start with new page
	  */
	public void setIsPageBreak (boolean IsPageBreak)
	{
		set_Value (COLUMNNAME_IsPageBreak, Boolean.valueOf(IsPageBreak));
	}

	/** Get Page break.
		@return Start with new page
	  */
	public boolean isPageBreak () 
	{
		Object oo = get_Value(COLUMNNAME_IsPageBreak);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Printed.
		@param IsPrinted 
		Indicates if this document / line is printed
	  */
	public void setIsPrinted (boolean IsPrinted)
	{
		set_Value (COLUMNNAME_IsPrinted, Boolean.valueOf(IsPrinted));
	}

	/** Get Printed.
		@return Indicates if this document / line is printed
	  */
	public boolean isPrinted () 
	{
		Object oo = get_Value(COLUMNNAME_IsPrinted);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Relative Position.
		@param IsRelativePosition 
		The item is relative positioned (not absolute)
	  */
	public void setIsRelativePosition (boolean IsRelativePosition)
	{
		set_Value (COLUMNNAME_IsRelativePosition, Boolean.valueOf(IsRelativePosition));
	}

	/** Get Relative Position.
		@return The item is relative positioned (not absolute)
	  */
	public boolean isRelativePosition () 
	{
		Object oo = get_Value(COLUMNNAME_IsRelativePosition);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Running Total.
		@param IsRunningTotal 
		Create a running total (sum)
	  */
	public void setIsRunningTotal (boolean IsRunningTotal)
	{
		set_Value (COLUMNNAME_IsRunningTotal, Boolean.valueOf(IsRunningTotal));
	}

	/** Get Running Total.
		@return Create a running total (sum)
	  */
	public boolean isRunningTotal () 
	{
		Object oo = get_Value(COLUMNNAME_IsRunningTotal);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Set NL Position.
		@param IsSetNLPosition 
		Set New Line Position
	  */
	public void setIsSetNLPosition (boolean IsSetNLPosition)
	{
		set_Value (COLUMNNAME_IsSetNLPosition, Boolean.valueOf(IsSetNLPosition));
	}

	/** Get Set NL Position.
		@return Set New Line Position
	  */
	public boolean isSetNLPosition () 
	{
		Object oo = get_Value(COLUMNNAME_IsSetNLPosition);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Sum (?).
		@param IsSummarized 
		Calculate the Sum of numeric content or length
	  */
	public void setIsSummarized (boolean IsSummarized)
	{
		set_Value (COLUMNNAME_IsSummarized, Boolean.valueOf(IsSummarized));
	}

	/** Get Calculate Sum (?).
		@return Calculate the Sum of numeric content or length
	  */
	public boolean isSummarized () 
	{
		Object oo = get_Value(COLUMNNAME_IsSummarized);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Suppress Null.
		@param IsSuppressNull 
		Suppress columns or elements with NULL value
	  */
	public void setIsSuppressNull (boolean IsSuppressNull)
	{
		set_Value (COLUMNNAME_IsSuppressNull, Boolean.valueOf(IsSuppressNull));
	}

	/** Get Suppress Null.
		@return Suppress columns or elements with NULL value
	  */
	public boolean isSuppressNull () 
	{
		Object oo = get_Value(COLUMNNAME_IsSuppressNull);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Suppress Repeats.
		@param IsSuppressRepeats 
		Suppress repeated elements in column.
	  */
	public void setIsSuppressRepeats (boolean IsSuppressRepeats)
	{
		set_Value (COLUMNNAME_IsSuppressRepeats, Boolean.valueOf(IsSuppressRepeats));
	}

	/** Get Suppress Repeats.
		@return Suppress repeated elements in column.
	  */
	public boolean isSuppressRepeats () 
	{
		Object oo = get_Value(COLUMNNAME_IsSuppressRepeats);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** Set Calculate Variance (?�).
		@param IsVarianceCalc 
		Calculate Variance
	  */
	public void setIsVarianceCalc (boolean IsVarianceCalc)
	{
		set_Value (COLUMNNAME_IsVarianceCalc, Boolean.valueOf(IsVarianceCalc));
	}

	/** Get Calculate Variance (?�).
		@return Calculate Variance
	  */
	public boolean isVarianceCalc () 
	{
		Object oo = get_Value(COLUMNNAME_IsVarianceCalc);
		if (oo != null) 
		{
			 if (oo instanceof Boolean) 
				 return ((Boolean)oo).booleanValue(); 
			return "Y".equals(oo);
		}
		return false;
	}

	/** LineAlignmentType AD_Reference_ID=254 */
	public static final int LINEALIGNMENTTYPE_AD_Reference_ID=254;
	/** Leading (left) = L */
	public static final String LINEALIGNMENTTYPE_LeadingLeft = "L";
	/** Center = C */
	public static final String LINEALIGNMENTTYPE_Center = "C";
	/** Trailing (right) = T */
	public static final String LINEALIGNMENTTYPE_TrailingRight = "T";
	/** None = X */
	public static final String LINEALIGNMENTTYPE_None = "X";
	/** Set Line Alignment.
		@param LineAlignmentType 
		Line Alignment
	  */
	public void setLineAlignmentType (String LineAlignmentType)
	{

		set_Value (COLUMNNAME_LineAlignmentType, LineAlignmentType);
	}

	/** Get Line Alignment.
		@return Line Alignment
	  */
	public String getLineAlignmentType () 
	{
		return (String)get_Value(COLUMNNAME_LineAlignmentType);
	}

	/** Set Line Width.
		@param LineWidth 
		Width of the lines
	  */
	public void setLineWidth (int LineWidth)
	{
		set_Value (COLUMNNAME_LineWidth, Integer.valueOf(LineWidth));
	}

	/** Get Line Width.
		@return Width of the lines
	  */
	public int getLineWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_LineWidth);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max Height.
		@param MaxHeight 
		Maximum Height in 1/72 if an inch - 0 = no restriction
	  */
	public void setMaxHeight (int MaxHeight)
	{
		set_Value (COLUMNNAME_MaxHeight, Integer.valueOf(MaxHeight));
	}

	/** Get Max Height.
		@return Maximum Height in 1/72 if an inch - 0 = no restriction
	  */
	public int getMaxHeight () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxHeight);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Max Width.
		@param MaxWidth 
		Maximum Width in 1/72 if an inch - 0 = no restriction
	  */
	public void setMaxWidth (int MaxWidth)
	{
		set_Value (COLUMNNAME_MaxWidth, Integer.valueOf(MaxWidth));
	}

	/** Get Max Width.
		@return Maximum Width in 1/72 if an inch - 0 = no restriction
	  */
	public int getMaxWidth () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_MaxWidth);
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

	/** PrintAreaType AD_Reference_ID=256 */
	public static final int PRINTAREATYPE_AD_Reference_ID=256;
	/** Content = C */
	public static final String PRINTAREATYPE_Content = "C";
	/** Header = H */
	public static final String PRINTAREATYPE_Header = "H";
	/** Footer = F */
	public static final String PRINTAREATYPE_Footer = "F";
	/** Set Area.
		@param PrintAreaType 
		Print Area
	  */
	public void setPrintAreaType (String PrintAreaType)
	{

		set_Value (COLUMNNAME_PrintAreaType, PrintAreaType);
	}

	/** Get Area.
		@return Print Area
	  */
	public String getPrintAreaType () 
	{
		return (String)get_Value(COLUMNNAME_PrintAreaType);
	}

	/** PrintFormatType AD_Reference_ID=255 */
	public static final int PRINTFORMATTYPE_AD_Reference_ID=255;
	/** Field = F */
	public static final String PRINTFORMATTYPE_Field = "F";
	/** Text = T */
	public static final String PRINTFORMATTYPE_Text = "T";
	/** Print Format = P */
	public static final String PRINTFORMATTYPE_PrintFormat = "P";
	/** Image = I */
	public static final String PRINTFORMATTYPE_Image = "I";
	/** Rectangle = R */
	public static final String PRINTFORMATTYPE_Rectangle = "R";
	/** Line = L */
	public static final String PRINTFORMATTYPE_Line = "L";
	/** Set Format Type.
		@param PrintFormatType 
		Print Format Type
	  */
	public void setPrintFormatType (String PrintFormatType)
	{

		set_Value (COLUMNNAME_PrintFormatType, PrintFormatType);
	}

	/** Get Format Type.
		@return Print Format Type
	  */
	public String getPrintFormatType () 
	{
		return (String)get_Value(COLUMNNAME_PrintFormatType);
	}

	/** Set Print Text.
		@param PrintName 
		The label text to be printed on a document or correspondence.
	  */
	public void setPrintName (String PrintName)
	{
		set_Value (COLUMNNAME_PrintName, PrintName);
	}

	/** Get Print Text.
		@return The label text to be printed on a document or correspondence.
	  */
	public String getPrintName () 
	{
		return (String)get_Value(COLUMNNAME_PrintName);
	}

	/** Set Print Label Suffix.
		@param PrintNameSuffix 
		The label text to be printed on a document or correspondence after the field
	  */
	public void setPrintNameSuffix (String PrintNameSuffix)
	{
		set_Value (COLUMNNAME_PrintNameSuffix, PrintNameSuffix);
	}

	/** Get Print Label Suffix.
		@return The label text to be printed on a document or correspondence after the field
	  */
	public String getPrintNameSuffix () 
	{
		return (String)get_Value(COLUMNNAME_PrintNameSuffix);
	}

	/** Set Running Total Lines.
		@param RunningTotalLines 
		Create Running Total Lines (page break) every x lines
	  */
	public void setRunningTotalLines (int RunningTotalLines)
	{
		set_Value (COLUMNNAME_RunningTotalLines, Integer.valueOf(RunningTotalLines));
	}

	/** Get Running Total Lines.
		@return Create Running Total Lines (page break) every x lines
	  */
	public int getRunningTotalLines () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_RunningTotalLines);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Sequence.
		@param SeqNo 
		Method of ordering records; lowest number comes first
	  */
	public void setSeqNo (int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, Integer.valueOf(SeqNo));
	}

	/** Get Sequence.
		@return Method of ordering records; lowest number comes first
	  */
	public int getSeqNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SeqNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** ShapeType AD_Reference_ID=333 */
	public static final int SHAPETYPE_AD_Reference_ID=333;
	/** 3D Rectangle = 3 */
	public static final String SHAPETYPE_3DRectangle = "3";
	/** Oval = O */
	public static final String SHAPETYPE_Oval = "O";
	/** Round Rectangle = R */
	public static final String SHAPETYPE_RoundRectangle = "R";
	/** Normal Rectangle = N */
	public static final String SHAPETYPE_NormalRectangle = "N";
	/** Set Shape Type.
		@param ShapeType 
		Type of the shape to be painted
	  */
	public void setShapeType (String ShapeType)
	{

		set_Value (COLUMNNAME_ShapeType, ShapeType);
	}

	/** Get Shape Type.
		@return Type of the shape to be painted
	  */
	public String getShapeType () 
	{
		return (String)get_Value(COLUMNNAME_ShapeType);
	}

	/** Set Record Sort No.
		@param SortNo 
		Determines in what order the records are displayed
	  */
	public void setSortNo (int SortNo)
	{
		set_Value (COLUMNNAME_SortNo, Integer.valueOf(SortNo));
	}

	/** Get Record Sort No.
		@return Determines in what order the records are displayed
	  */
	public int getSortNo () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_SortNo);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set X Position.
		@param XPosition 
		Absolute X (horizontal) position in 1/72 of an inch
	  */
	public void setXPosition (int XPosition)
	{
		set_Value (COLUMNNAME_XPosition, Integer.valueOf(XPosition));
	}

	/** Get X Position.
		@return Absolute X (horizontal) position in 1/72 of an inch
	  */
	public int getXPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XPosition);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set X Space.
		@param XSpace 
		Relative X (horizontal) space in 1/72 of an inch
	  */
	public void setXSpace (int XSpace)
	{
		set_Value (COLUMNNAME_XSpace, Integer.valueOf(XSpace));
	}

	/** Get X Space.
		@return Relative X (horizontal) space in 1/72 of an inch
	  */
	public int getXSpace () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_XSpace);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Y Position.
		@param YPosition 
		Absolute Y (vertical) position in 1/72 of an inch
	  */
	public void setYPosition (int YPosition)
	{
		set_Value (COLUMNNAME_YPosition, Integer.valueOf(YPosition));
	}

	/** Get Y Position.
		@return Absolute Y (vertical) position in 1/72 of an inch
	  */
	public int getYPosition () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YPosition);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}

	/** Set Y Space.
		@param YSpace 
		Relative Y (vertical) space in 1/72 of an inch
	  */
	public void setYSpace (int YSpace)
	{
		set_Value (COLUMNNAME_YSpace, Integer.valueOf(YSpace));
	}

	/** Get Y Space.
		@return Relative Y (vertical) space in 1/72 of an inch
	  */
	public int getYSpace () 
	{
		Integer ii = (Integer)get_Value(COLUMNNAME_YSpace);
		if (ii == null)
			 return 0;
		return ii.intValue();
	}
}