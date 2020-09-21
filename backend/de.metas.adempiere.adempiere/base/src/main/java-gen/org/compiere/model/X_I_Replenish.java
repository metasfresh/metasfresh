/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for I_Replenish
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_I_Replenish extends org.compiere.model.PO implements I_I_Replenish, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1634785315L;

    /** Standard Constructor */
    public X_I_Replenish (Properties ctx, int I_Replenish_ID, String trxName)
    {
      super (ctx, I_Replenish_ID, trxName);
    }

    /** Load Constructor */
    public X_I_Replenish (Properties ctx, ResultSet rs, String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Issue getAD_Issue()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class);
	}

	@Override
	public void setAD_Issue(org.compiere.model.I_AD_Issue AD_Issue)
	{
		set_ValueFromPO(COLUMNNAME_AD_Issue_ID, org.compiere.model.I_AD_Issue.class, AD_Issue);
	}

	@Override
	public void setAD_Issue_ID (int AD_Issue_ID)
	{
		if (AD_Issue_ID < 1) 
			set_Value (COLUMNNAME_AD_Issue_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Issue_ID, Integer.valueOf(AD_Issue_ID));
	}

	@Override
	public int getAD_Issue_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Issue_ID);
	}

	@Override
	public org.compiere.model.I_C_Calendar getC_Calendar()
	{
		return get_ValueAsPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class);
	}

	@Override
	public void setC_Calendar(org.compiere.model.I_C_Calendar C_Calendar)
	{
		set_ValueFromPO(COLUMNNAME_C_Calendar_ID, org.compiere.model.I_C_Calendar.class, C_Calendar);
	}

	@Override
	public void setC_Calendar_ID (int C_Calendar_ID)
	{
		if (C_Calendar_ID < 1) 
			set_Value (COLUMNNAME_C_Calendar_ID, null);
		else 
			set_Value (COLUMNNAME_C_Calendar_ID, Integer.valueOf(C_Calendar_ID));
	}

	@Override
	public int getC_Calendar_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Calendar_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport getC_DataImport()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class);
	}

	@Override
	public void setC_DataImport(org.compiere.model.I_C_DataImport C_DataImport)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_ID, org.compiere.model.I_C_DataImport.class, C_DataImport);
	}

	@Override
	public void setC_DataImport_ID (int C_DataImport_ID)
	{
		if (C_DataImport_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_ID, Integer.valueOf(C_DataImport_ID));
	}

	@Override
	public int getC_DataImport_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_ID);
	}

	@Override
	public org.compiere.model.I_C_DataImport_Run getC_DataImport_Run()
	{
		return get_ValueAsPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class);
	}

	@Override
	public void setC_DataImport_Run(org.compiere.model.I_C_DataImport_Run C_DataImport_Run)
	{
		set_ValueFromPO(COLUMNNAME_C_DataImport_Run_ID, org.compiere.model.I_C_DataImport_Run.class, C_DataImport_Run);
	}

	@Override
	public void setC_DataImport_Run_ID (int C_DataImport_Run_ID)
	{
		if (C_DataImport_Run_ID < 1) 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, null);
		else 
			set_Value (COLUMNNAME_C_DataImport_Run_ID, Integer.valueOf(C_DataImport_Run_ID));
	}

	@Override
	public int getC_DataImport_Run_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_DataImport_Run_ID);
	}

	@Override
	public org.compiere.model.I_C_Period getC_Period()
	{
		return get_ValueAsPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class);
	}

	@Override
	public void setC_Period(org.compiere.model.I_C_Period C_Period)
	{
		set_ValueFromPO(COLUMNNAME_C_Period_ID, org.compiere.model.I_C_Period.class, C_Period);
	}

	@Override
	public void setC_Period_ID (int C_Period_ID)
	{
		if (C_Period_ID < 1) 
			set_Value (COLUMNNAME_C_Period_ID, null);
		else 
			set_Value (COLUMNNAME_C_Period_ID, Integer.valueOf(C_Period_ID));
	}

	@Override
	public int getC_Period_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Period_ID);
	}

	@Override
	public void setDateGeneral (java.lang.String DateGeneral)
	{
		set_Value (COLUMNNAME_DateGeneral, DateGeneral);
	}

	@Override
	public java.lang.String getDateGeneral() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_DateGeneral);
	}

	@Override
	public void setI_ErrorMsg (java.lang.String I_ErrorMsg)
	{
		set_Value (COLUMNNAME_I_ErrorMsg, I_ErrorMsg);
	}

	@Override
	public java.lang.String getI_ErrorMsg() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_ErrorMsg);
	}

	/** 
	 * I_IsImported AD_Reference_ID=540745
	 * Reference name: I_IsImported
	 */
	public static final int I_ISIMPORTED_AD_Reference_ID=540745;
	/** NotImported = N */
	public static final String I_ISIMPORTED_NotImported = "N";
	/** Imported = Y */
	public static final String I_ISIMPORTED_Imported = "Y";
	/** ImportFailed = E */
	public static final String I_ISIMPORTED_ImportFailed = "E";
	@Override
	public void setI_IsImported (java.lang.String I_IsImported)
	{

		set_Value (COLUMNNAME_I_IsImported, I_IsImported);
	}

	@Override
	public java.lang.String getI_IsImported() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_IsImported);
	}

	@Override
	public void setI_LineContent (java.lang.String I_LineContent)
	{
		set_Value (COLUMNNAME_I_LineContent, I_LineContent);
	}

	@Override
	public java.lang.String getI_LineContent() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_I_LineContent);
	}

	@Override
	public void setI_LineNo (int I_LineNo)
	{
		set_Value (COLUMNNAME_I_LineNo, Integer.valueOf(I_LineNo));
	}

	@Override
	public int getI_LineNo() 
	{
		return get_ValueAsInt(COLUMNNAME_I_LineNo);
	}

	@Override
	public void setI_Replenish_ID (int I_Replenish_ID)
	{
		if (I_Replenish_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_I_Replenish_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_I_Replenish_ID, Integer.valueOf(I_Replenish_ID));
	}

	@Override
	public int getI_Replenish_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_I_Replenish_ID);
	}

	@Override
	public void setLevel_Max (java.math.BigDecimal Level_Max)
	{
		set_Value (COLUMNNAME_Level_Max, Level_Max);
	}

	@Override
	public java.math.BigDecimal getLevel_Max() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Level_Max);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLevel_Min (java.math.BigDecimal Level_Min)
	{
		set_Value (COLUMNNAME_Level_Min, Level_Min);
	}

	@Override
	public java.math.BigDecimal getLevel_Min() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Level_Min);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setLocatorValue (java.lang.String LocatorValue)
	{
		set_Value (COLUMNNAME_LocatorValue, LocatorValue);
	}

	@Override
	public java.lang.String getLocatorValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_LocatorValue);
	}

	@Override
	public void setM_Locator_ID (int M_Locator_ID)
	{
		if (M_Locator_ID < 1) 
			set_Value (COLUMNNAME_M_Locator_ID, null);
		else 
			set_Value (COLUMNNAME_M_Locator_ID, Integer.valueOf(M_Locator_ID));
	}

	@Override
	public int getM_Locator_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Locator_ID);
	}

	@Override
	public void setM_Product_ID (int M_Product_ID)
	{
		if (M_Product_ID < 1) 
			set_Value (COLUMNNAME_M_Product_ID, null);
		else 
			set_Value (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public org.compiere.model.I_M_Replenish getM_Replenish()
	{
		return get_ValueAsPO(COLUMNNAME_M_Replenish_ID, org.compiere.model.I_M_Replenish.class);
	}

	@Override
	public void setM_Replenish(org.compiere.model.I_M_Replenish M_Replenish)
	{
		set_ValueFromPO(COLUMNNAME_M_Replenish_ID, org.compiere.model.I_M_Replenish.class, M_Replenish);
	}

	@Override
	public void setM_Replenish_ID (int M_Replenish_ID)
	{
		if (M_Replenish_ID < 1) 
			set_Value (COLUMNNAME_M_Replenish_ID, null);
		else 
			set_Value (COLUMNNAME_M_Replenish_ID, Integer.valueOf(M_Replenish_ID));
	}

	@Override
	public int getM_Replenish_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Replenish_ID);
	}

	@Override
	public void setM_Warehouse_ID (int M_Warehouse_ID)
	{
		if (M_Warehouse_ID < 1) 
			set_Value (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_Value (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
	}

	@Override
	public void setOrgValue (java.lang.String OrgValue)
	{
		set_Value (COLUMNNAME_OrgValue, OrgValue);
	}

	@Override
	public java.lang.String getOrgValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_OrgValue);
	}

	@Override
	public void setProcessed (boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Boolean.valueOf(Processed));
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setProcessing (boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Boolean.valueOf(Processing));
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setProductValue (java.lang.String ProductValue)
	{
		set_Value (COLUMNNAME_ProductValue, ProductValue);
	}

	@Override
	public java.lang.String getProductValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ProductValue);
	}

	/** 
	 * ReplenishType AD_Reference_ID=164
	 * Reference name: M_Replenish Type
	 */
	public static final int REPLENISHTYPE_AD_Reference_ID=164;
	/** Maximalbestand beibehalten = 2 */
	public static final String REPLENISHTYPE_MaximalbestandBeibehalten = "2";
	/** Manuell = 0 */
	public static final String REPLENISHTYPE_Manuell = "0";
	/** Bei Unterschreitung Minimalbestand = 1 */
	public static final String REPLENISHTYPE_BeiUnterschreitungMinimalbestand = "1";
	/** Custom = 9 */
	public static final String REPLENISHTYPE_Custom = "9";
	/** Zuk?nftigen Bestand sichern = 7 */
	public static final String REPLENISHTYPE_ZukNftigenBestandSichern = "7";
	@Override
	public void setReplenishType (java.lang.String ReplenishType)
	{

		set_Value (COLUMNNAME_ReplenishType, ReplenishType);
	}

	@Override
	public java.lang.String getReplenishType() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_ReplenishType);
	}

	@Override
	public void setTimeToMarket (int TimeToMarket)
	{
		set_Value (COLUMNNAME_TimeToMarket, Integer.valueOf(TimeToMarket));
	}

	@Override
	public int getTimeToMarket() 
	{
		return get_ValueAsInt(COLUMNNAME_TimeToMarket);
	}

	@Override
	public void setWarehouseValue (java.lang.String WarehouseValue)
	{
		set_Value (COLUMNNAME_WarehouseValue, WarehouseValue);
	}

	@Override
	public java.lang.String getWarehouseValue() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_WarehouseValue);
	}
}