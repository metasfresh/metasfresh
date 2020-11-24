/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_AttributeConfig
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule_AttributeConfig extends org.compiere.model.PO implements I_M_ShipmentSchedule_AttributeConfig, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -2100756852L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_AttributeConfig (Properties ctx, int M_ShipmentSchedule_AttributeConfig_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_AttributeConfig_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_AttributeConfig (Properties ctx, ResultSet rs, String trxName)
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
	public void setM_Attribute_ID (int M_Attribute_ID)
	{
		if (M_Attribute_ID < 1) 
			set_Value (COLUMNNAME_M_Attribute_ID, null);
		else 
			set_Value (COLUMNNAME_M_Attribute_ID, Integer.valueOf(M_Attribute_ID));
	}

	@Override
	public int getM_Attribute_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_Attribute_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_IolCandHandler getM_IolCandHandler()
	{
		return get_ValueAsPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class);
	}

	@Override
	public void setM_IolCandHandler(de.metas.inoutcandidate.model.I_M_IolCandHandler M_IolCandHandler)
	{
		set_ValueFromPO(COLUMNNAME_M_IolCandHandler_ID, de.metas.inoutcandidate.model.I_M_IolCandHandler.class, M_IolCandHandler);
	}

	@Override
	public void setM_IolCandHandler_ID (int M_IolCandHandler_ID)
	{
		if (M_IolCandHandler_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_IolCandHandler_ID, Integer.valueOf(M_IolCandHandler_ID));
	}

	@Override
	public int getM_IolCandHandler_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_IolCandHandler_ID);
	}

	@Override
	public void setM_ShipmentSchedule_AttributeConfig_ID (int M_ShipmentSchedule_AttributeConfig_ID)
	{
		if (M_ShipmentSchedule_AttributeConfig_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID, Integer.valueOf(M_ShipmentSchedule_AttributeConfig_ID));
	}

	@Override
	public int getM_ShipmentSchedule_AttributeConfig_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_AttributeConfig_ID);
	}

	@Override
	public void setOnlyIfInReferencedASI (boolean OnlyIfInReferencedASI)
	{
		set_Value (COLUMNNAME_OnlyIfInReferencedASI, Boolean.valueOf(OnlyIfInReferencedASI));
	}

	@Override
	public boolean isOnlyIfInReferencedASI() 
	{
		return get_ValueAsBoolean(COLUMNNAME_OnlyIfInReferencedASI);
	}
}