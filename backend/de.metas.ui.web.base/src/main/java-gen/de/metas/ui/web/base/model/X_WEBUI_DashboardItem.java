// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_DashboardItem
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_DashboardItem extends org.compiere.model.PO implements I_WEBUI_DashboardItem, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1552880742L;

    /** Standard Constructor */
    public X_WEBUI_DashboardItem (final Properties ctx, final int WEBUI_DashboardItem_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_DashboardItem_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_DashboardItem (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setSeqNo (final int SeqNo)
	{
		set_Value (COLUMNNAME_SeqNo, SeqNo);
	}

	@Override
	public int getSeqNo() 
	{
		return get_ValueAsInt(COLUMNNAME_SeqNo);
	}

	@Override
	public void setURL (final @Nullable java.lang.String URL)
	{
		set_Value (COLUMNNAME_URL, URL);
	}

	@Override
	public java.lang.String getURL() 
	{
		return get_ValueAsString(COLUMNNAME_URL);
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_Dashboard getWEBUI_Dashboard()
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_Dashboard_ID, de.metas.ui.web.base.model.I_WEBUI_Dashboard.class);
	}

	@Override
	public void setWEBUI_Dashboard(final de.metas.ui.web.base.model.I_WEBUI_Dashboard WEBUI_Dashboard)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_Dashboard_ID, de.metas.ui.web.base.model.I_WEBUI_Dashboard.class, WEBUI_Dashboard);
	}

	@Override
	public void setWEBUI_Dashboard_ID (final int WEBUI_Dashboard_ID)
	{
		if (WEBUI_Dashboard_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_Dashboard_ID, WEBUI_Dashboard_ID);
	}

	@Override
	public int getWEBUI_Dashboard_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_Dashboard_ID);
	}

	@Override
	public void setWEBUI_DashboardItem_ID (final int WEBUI_DashboardItem_ID)
	{
		if (WEBUI_DashboardItem_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_DashboardItem_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_DashboardItem_ID, WEBUI_DashboardItem_ID);
	}

	@Override
	public int getWEBUI_DashboardItem_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_DashboardItem_ID);
	}

	/** 
	 * WEBUI_DashboardWidgetType AD_Reference_ID=540697
	 * Reference name: WEBUI_DashboardWidgetType
	 */
	public static final int WEBUI_DASHBOARDWIDGETTYPE_AD_Reference_ID=540697;
	/** Target = T */
	public static final String WEBUI_DASHBOARDWIDGETTYPE_Target = "T";
	/** KPI = K */
	public static final String WEBUI_DASHBOARDWIDGETTYPE_KPI = "K";
	@Override
	public void setWEBUI_DashboardWidgetType (final java.lang.String WEBUI_DashboardWidgetType)
	{
		set_Value (COLUMNNAME_WEBUI_DashboardWidgetType, WEBUI_DashboardWidgetType);
	}

	@Override
	public java.lang.String getWEBUI_DashboardWidgetType() 
	{
		return get_ValueAsString(COLUMNNAME_WEBUI_DashboardWidgetType);
	}

	@Override
	public de.metas.ui.web.base.model.I_WEBUI_KPI getWEBUI_KPI()
	{
		return get_ValueAsPO(COLUMNNAME_WEBUI_KPI_ID, de.metas.ui.web.base.model.I_WEBUI_KPI.class);
	}

	@Override
	public void setWEBUI_KPI(final de.metas.ui.web.base.model.I_WEBUI_KPI WEBUI_KPI)
	{
		set_ValueFromPO(COLUMNNAME_WEBUI_KPI_ID, de.metas.ui.web.base.model.I_WEBUI_KPI.class, WEBUI_KPI);
	}

	@Override
	public void setWEBUI_KPI_ID (final int WEBUI_KPI_ID)
	{
		if (WEBUI_KPI_ID < 1) 
			set_Value (COLUMNNAME_WEBUI_KPI_ID, null);
		else 
			set_Value (COLUMNNAME_WEBUI_KPI_ID, WEBUI_KPI_ID);
	}

	@Override
	public int getWEBUI_KPI_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_KPI_ID);
	}
}