// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for WEBUI_KPI
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_KPI extends org.compiere.model.PO implements I_WEBUI_KPI, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 929068186L;

    /** Standard Constructor */
    public X_WEBUI_KPI (final Properties ctx, final int WEBUI_KPI_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_KPI_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_KPI (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
    {
      super (ctx, rs, trxName);
    }


	/** Load Meta Data */
	@Override
	protected org.compiere.model.POInfo initPO(final Properties ctx)
	{
		return org.compiere.model.POInfo.getPOInfo(Table_Name);
	}

	@Override
	public org.compiere.model.I_AD_Window getAD_Window()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class);
	}

	@Override
	public void setAD_Window(final org.compiere.model.I_AD_Window AD_Window)
	{
		set_ValueFromPO(COLUMNNAME_AD_Window_ID, org.compiere.model.I_AD_Window.class, AD_Window);
	}

	@Override
	public void setAD_Window_ID (final int AD_Window_ID)
	{
		if (AD_Window_ID < 1) 
			set_Value (COLUMNNAME_AD_Window_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Window_ID, AD_Window_ID);
	}

	@Override
	public int getAD_Window_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Window_ID);
	}

	/** 
	 * ChartType AD_Reference_ID=540701
	 * Reference name: WEBUI_ChartType
	 */
	public static final int CHARTTYPE_AD_Reference_ID=540701;
	/** BarChart = BC */
	public static final String CHARTTYPE_BarChart = "BC";
	/** PieChart = PC */
	public static final String CHARTTYPE_PieChart = "PC";
	/** AreaChart = AC */
	public static final String CHARTTYPE_AreaChart = "AC";
	/** Metric = M */
	public static final String CHARTTYPE_Metric = "M";
	/** URLs = urls */
	public static final String CHARTTYPE_URLs = "urls";
	@Override
	public void setChartType (final java.lang.String ChartType)
	{
		set_Value (COLUMNNAME_ChartType, ChartType);
	}

	@Override
	public java.lang.String getChartType() 
	{
		return get_ValueAsString(COLUMNNAME_ChartType);
	}

	@Override
	public void setCompareOffset (final @Nullable java.lang.String CompareOffset)
	{
		set_Value (COLUMNNAME_CompareOffset, CompareOffset);
	}

	@Override
	public java.lang.String getCompareOffset() 
	{
		return get_ValueAsString(COLUMNNAME_CompareOffset);
	}

	@Override
	public void setDescription (final @Nullable java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	@Override
	public void setES_Index (final @Nullable java.lang.String ES_Index)
	{
		set_Value (COLUMNNAME_ES_Index, ES_Index);
	}

	@Override
	public java.lang.String getES_Index() 
	{
		return get_ValueAsString(COLUMNNAME_ES_Index);
	}

	@Override
	public void setES_Query (final @Nullable java.lang.String ES_Query)
	{
		set_Value (COLUMNNAME_ES_Query, ES_Query);
	}

	@Override
	public java.lang.String getES_Query() 
	{
		return get_ValueAsString(COLUMNNAME_ES_Query);
	}

	@Override
	public void setES_TimeRange (final @Nullable java.lang.String ES_TimeRange)
	{
		set_Value (COLUMNNAME_ES_TimeRange, ES_TimeRange);
	}

	@Override
	public java.lang.String getES_TimeRange() 
	{
		return get_ValueAsString(COLUMNNAME_ES_TimeRange);
	}

	@Override
	public void setES_TimeRange_End (final @Nullable java.lang.String ES_TimeRange_End)
	{
		set_Value (COLUMNNAME_ES_TimeRange_End, ES_TimeRange_End);
	}

	@Override
	public java.lang.String getES_TimeRange_End() 
	{
		return get_ValueAsString(COLUMNNAME_ES_TimeRange_End);
	}

	@Override
	public void setIsApplySecuritySettings (final boolean IsApplySecuritySettings)
	{
		set_Value (COLUMNNAME_IsApplySecuritySettings, IsApplySecuritySettings);
	}

	@Override
	public boolean isApplySecuritySettings() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsApplySecuritySettings);
	}

	@Override
	public void setIsGenerateComparation (final boolean IsGenerateComparation)
	{
		set_Value (COLUMNNAME_IsGenerateComparation, IsGenerateComparation);
	}

	@Override
	public boolean isGenerateComparation() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGenerateComparation);
	}

	@Override
	public void setKPI_AllowedStaledTimeInSec (final int KPI_AllowedStaledTimeInSec)
	{
		set_Value (COLUMNNAME_KPI_AllowedStaledTimeInSec, KPI_AllowedStaledTimeInSec);
	}

	@Override
	public int getKPI_AllowedStaledTimeInSec() 
	{
		return get_ValueAsInt(COLUMNNAME_KPI_AllowedStaledTimeInSec);
	}

	/** 
	 * KPI_DataSource_Type AD_Reference_ID=541339
	 * Reference name: KPI_DataSource_Type
	 */
	public static final int KPI_DATASOURCE_TYPE_AD_Reference_ID=541339;
	/** Elasticsearch = E */
	public static final String KPI_DATASOURCE_TYPE_Elasticsearch = "E";
	/** SQL = S */
	public static final String KPI_DATASOURCE_TYPE_SQL = "S";
	@Override
	public void setKPI_DataSource_Type (final java.lang.String KPI_DataSource_Type)
	{
		set_Value (COLUMNNAME_KPI_DataSource_Type, KPI_DataSource_Type);
	}

	@Override
	public java.lang.String getKPI_DataSource_Type() 
	{
		return get_ValueAsString(COLUMNNAME_KPI_DataSource_Type);
	}

	@Override
	public void setName (final java.lang.String Name)
	{
		set_Value (COLUMNNAME_Name, Name);
	}

	@Override
	public java.lang.String getName() 
	{
		return get_ValueAsString(COLUMNNAME_Name);
	}

	@Override
	public void setSource_Table_ID (final int Source_Table_ID)
	{
		if (Source_Table_ID < 1) 
			set_Value (COLUMNNAME_Source_Table_ID, null);
		else 
			set_Value (COLUMNNAME_Source_Table_ID, Source_Table_ID);
	}

	@Override
	public int getSource_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Source_Table_ID);
	}

	@Override
	public void setSQL_Details_WhereClause (final @Nullable java.lang.String SQL_Details_WhereClause)
	{
		set_Value (COLUMNNAME_SQL_Details_WhereClause, SQL_Details_WhereClause);
	}

	@Override
	public java.lang.String getSQL_Details_WhereClause() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_Details_WhereClause);
	}

	@Override
	public void setSQL_From (final @Nullable java.lang.String SQL_From)
	{
		set_Value (COLUMNNAME_SQL_From, SQL_From);
	}

	@Override
	public java.lang.String getSQL_From() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_From);
	}

	@Override
	public void setSQL_GroupAndOrderBy (final @Nullable java.lang.String SQL_GroupAndOrderBy)
	{
		set_Value (COLUMNNAME_SQL_GroupAndOrderBy, SQL_GroupAndOrderBy);
	}

	@Override
	public java.lang.String getSQL_GroupAndOrderBy() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_GroupAndOrderBy);
	}

	@Override
	public void setSQL_WhereClause (final @Nullable java.lang.String SQL_WhereClause)
	{
		set_Value (COLUMNNAME_SQL_WhereClause, SQL_WhereClause);
	}

	@Override
	public java.lang.String getSQL_WhereClause() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_WhereClause);
	}

	@Override
	public void setWEBUI_KPI_ID (final int WEBUI_KPI_ID)
	{
		if (WEBUI_KPI_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_ID, WEBUI_KPI_ID);
	}

	@Override
	public int getWEBUI_KPI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_KPI_ID);
	}
}