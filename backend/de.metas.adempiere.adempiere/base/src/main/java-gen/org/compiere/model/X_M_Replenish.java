/** Generated Model - DO NOT CHANGE */
package org.compiere.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Replenish
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_Replenish extends org.compiere.model.PO implements I_M_Replenish, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1201937262L;

    /** Standard Constructor */
    public X_M_Replenish (Properties ctx, int M_Replenish_ID, String trxName)
    {
      super (ctx, M_Replenish_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Replenish (Properties ctx, ResultSet rs, String trxName)
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
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Product_ID, Integer.valueOf(M_Product_ID));
	}

	@Override
	public int getM_Product_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Product_ID);
	}

	@Override
	public void setM_Replenish_ID (int M_Replenish_ID)
	{
		if (M_Replenish_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Replenish_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Replenish_ID, Integer.valueOf(M_Replenish_ID));
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
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Warehouse_ID, Integer.valueOf(M_Warehouse_ID));
	}

	@Override
	public int getM_Warehouse_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Warehouse_ID);
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
}