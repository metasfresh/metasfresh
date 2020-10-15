/** Generated Model - DO NOT CHANGE */
package de.metas.inoutcandidate.model;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for M_ShipmentSchedule_QtyPicked
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("javadoc")
public class X_M_ShipmentSchedule_QtyPicked extends org.compiere.model.PO implements I_M_ShipmentSchedule_QtyPicked, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -37740715L;

    /** Standard Constructor */
    public X_M_ShipmentSchedule_QtyPicked (Properties ctx, int M_ShipmentSchedule_QtyPicked_ID, String trxName)
    {
      super (ctx, M_ShipmentSchedule_QtyPicked_ID, trxName);
    }

    /** Load Constructor */
    public X_M_ShipmentSchedule_QtyPicked (Properties ctx, ResultSet rs, String trxName)
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
	public void setCatch_UOM_ID (int Catch_UOM_ID)
	{
		if (Catch_UOM_ID < 1) 
			set_Value (COLUMNNAME_Catch_UOM_ID, null);
		else 
			set_Value (COLUMNNAME_Catch_UOM_ID, Integer.valueOf(Catch_UOM_ID));
	}

	@Override
	public int getCatch_UOM_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Catch_UOM_ID);
	}

	@Override
	public void setDescription (java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return (java.lang.String)get_Value(COLUMNNAME_Description);
	}

	@Override
	public void setIsAnonymousHuPickedOnTheFly (boolean IsAnonymousHuPickedOnTheFly)
	{
		set_Value (COLUMNNAME_IsAnonymousHuPickedOnTheFly, Boolean.valueOf(IsAnonymousHuPickedOnTheFly));
	}

	@Override
	public boolean isAnonymousHuPickedOnTheFly() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAnonymousHuPickedOnTheFly);
	}

	@Override
	public org.compiere.model.I_M_InOutLine getM_InOutLine()
	{
		return get_ValueAsPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class);
	}

	@Override
	public void setM_InOutLine(org.compiere.model.I_M_InOutLine M_InOutLine)
	{
		set_ValueFromPO(COLUMNNAME_M_InOutLine_ID, org.compiere.model.I_M_InOutLine.class, M_InOutLine);
	}

	@Override
	public void setM_InOutLine_ID (int M_InOutLine_ID)
	{
		if (M_InOutLine_ID < 1) 
			set_Value (COLUMNNAME_M_InOutLine_ID, null);
		else 
			set_Value (COLUMNNAME_M_InOutLine_ID, Integer.valueOf(M_InOutLine_ID));
	}

	@Override
	public int getM_InOutLine_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_InOutLine_ID);
	}

	@Override
	public de.metas.inoutcandidate.model.I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return get_ValueAsPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public void setM_ShipmentSchedule(de.metas.inoutcandidate.model.I_M_ShipmentSchedule M_ShipmentSchedule)
	{
		set_ValueFromPO(COLUMNNAME_M_ShipmentSchedule_ID, de.metas.inoutcandidate.model.I_M_ShipmentSchedule.class, M_ShipmentSchedule);
	}

	@Override
	public void setM_ShipmentSchedule_ID (int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, Integer.valueOf(M_ShipmentSchedule_ID));
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setM_ShipmentSchedule_QtyPicked_ID (int M_ShipmentSchedule_QtyPicked_ID)
	{
		if (M_ShipmentSchedule_QtyPicked_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID, Integer.valueOf(M_ShipmentSchedule_QtyPicked_ID));
	}

	@Override
	public int getM_ShipmentSchedule_QtyPicked_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_QtyPicked_ID);
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
	public void setQtyDeliveredCatch (java.math.BigDecimal QtyDeliveredCatch)
	{
		set_Value (COLUMNNAME_QtyDeliveredCatch, QtyDeliveredCatch);
	}

	@Override
	public java.math.BigDecimal getQtyDeliveredCatch() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyDeliveredCatch);
		return bd != null ? bd : BigDecimal.ZERO;
	}

	@Override
	public void setQtyPicked (java.math.BigDecimal QtyPicked)
	{
		set_Value (COLUMNNAME_QtyPicked, QtyPicked);
	}

	@Override
	public java.math.BigDecimal getQtyPicked() 
	{
		BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_QtyPicked);
		return bd != null ? bd : BigDecimal.ZERO;
	}
}