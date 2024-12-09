// Generated Model - DO NOT CHANGE
package de.metas.picking.model;

<<<<<<< HEAD
import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for M_Picking_Config
 *  @author metasfresh (generated) 
=======
import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_Picking_Config
 *  @author metasfresh (generated)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
 */
@SuppressWarnings("unused")
public class X_M_Picking_Config extends org.compiere.model.PO implements I_M_Picking_Config, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 462743628L;

    /** Standard Constructor */
    public X_M_Picking_Config (final Properties ctx, final int M_Picking_Config_ID, @Nullable final String trxName)
    {
      super (ctx, M_Picking_Config_ID, trxName);
    }

    /** Load Constructor */
    public X_M_Picking_Config (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setIsAllowOverdelivery (final boolean IsAllowOverdelivery)
	{
		set_Value (COLUMNNAME_IsAllowOverdelivery, IsAllowOverdelivery);
	}

	@Override
<<<<<<< HEAD
	public boolean isAllowOverdelivery() 
=======
	public boolean isAllowOverdelivery()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAllowOverdelivery);
	}

	@Override
	public void setIsAutoProcess (final boolean IsAutoProcess)
	{
		set_Value (COLUMNNAME_IsAutoProcess, IsAutoProcess);
	}

	@Override
<<<<<<< HEAD
	public boolean isAutoProcess() 
=======
	public boolean isAutoProcess()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutoProcess);
	}

	@Override
<<<<<<< HEAD
=======
	public void setIsForbidAggCUsForDifferentOrders (final boolean IsForbidAggCUsForDifferentOrders)
	{
		set_Value (COLUMNNAME_IsForbidAggCUsForDifferentOrders, IsForbidAggCUsForDifferentOrders);
	}

	@Override
	public boolean isForbidAggCUsForDifferentOrders()
	{
		return get_ValueAsBoolean(COLUMNNAME_IsForbidAggCUsForDifferentOrders);
	}

	@Override
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	public void setM_Picking_Config_ID (final int M_Picking_Config_ID)
	{
		if (M_Picking_Config_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_Picking_Config_ID, M_Picking_Config_ID);
	}

	@Override
<<<<<<< HEAD
	public int getM_Picking_Config_ID() 
=======
	public int getM_Picking_Config_ID()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsInt(COLUMNNAME_M_Picking_Config_ID);
	}

	/** 
	 * WEBUI_PickingTerminal_ViewProfile AD_Reference_ID=540772
	 * Reference name: WEBUI_PickingTerminal_ViewProfile
	 */
	public static final int WEBUI_PICKINGTERMINAL_VIEWPROFILE_AD_Reference_ID=540772;
	/** groupByProduct = groupByProduct */
	public static final String WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByProduct = "groupByProduct";
	/** Group by Order = groupByOrder */
	public static final String WEBUI_PICKINGTERMINAL_VIEWPROFILE_GroupByOrder = "groupByOrder";
	@Override
	public void setWEBUI_PickingTerminal_ViewProfile (final java.lang.String WEBUI_PickingTerminal_ViewProfile)
	{
		set_Value (COLUMNNAME_WEBUI_PickingTerminal_ViewProfile, WEBUI_PickingTerminal_ViewProfile);
	}

	@Override
<<<<<<< HEAD
	public java.lang.String getWEBUI_PickingTerminal_ViewProfile() 
=======
	public java.lang.String getWEBUI_PickingTerminal_ViewProfile()
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	{
		return get_ValueAsString(COLUMNNAME_WEBUI_PickingTerminal_ViewProfile);
	}
}