// Generated Model - DO NOT CHANGE
package de.metas.contracts.model;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for C_SubscriptionProgress
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_C_SubscriptionProgress extends org.compiere.model.PO implements I_C_SubscriptionProgress, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1330587435L;

    /** Standard Constructor */
    public X_C_SubscriptionProgress (final Properties ctx, final int C_SubscriptionProgress_ID, @Nullable final String trxName)
    {
      super (ctx, C_SubscriptionProgress_ID, trxName);
    }

    /** Load Constructor */
    public X_C_SubscriptionProgress (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public de.metas.contracts.model.I_C_Flatrate_Term getC_Flatrate_Term()
	{
		return get_ValueAsPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class);
	}

	@Override
	public void setC_Flatrate_Term(final de.metas.contracts.model.I_C_Flatrate_Term C_Flatrate_Term)
	{
		set_ValueFromPO(COLUMNNAME_C_Flatrate_Term_ID, de.metas.contracts.model.I_C_Flatrate_Term.class, C_Flatrate_Term);
	}

	@Override
	public void setC_Flatrate_Term_ID (final int C_Flatrate_Term_ID)
	{
		if (C_Flatrate_Term_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_Flatrate_Term_ID, C_Flatrate_Term_ID);
	}

	@Override
	public int getC_Flatrate_Term_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Flatrate_Term_ID);
	}

	/** 
	 * ContractStatus AD_Reference_ID=540000
	 * Reference name: SubscriptionStatus
	 */
	public static final int CONTRACTSTATUS_AD_Reference_ID=540000;
	/** Running = Ru */
	public static final String CONTRACTSTATUS_Running = "Ru";
	/** DeliveryPause = Pa */
	public static final String CONTRACTSTATUS_DeliveryPause = "Pa";
	/** Quit = Qu */
	public static final String CONTRACTSTATUS_Quit = "Qu";
	/** Info = In */
	public static final String CONTRACTSTATUS_Info = "In";
	/** Waiting = Wa */
	public static final String CONTRACTSTATUS_Waiting = "Wa";
	/** EndingContract = Ec */
	public static final String CONTRACTSTATUS_EndingContract = "Ec";
	/** Voided = Vo */
	public static final String CONTRACTSTATUS_Voided = "Vo";
	@Override
	public void setContractStatus (final @Nullable java.lang.String ContractStatus)
	{
		set_Value (COLUMNNAME_ContractStatus, ContractStatus);
	}

	@Override
	public java.lang.String getContractStatus() 
	{
		return get_ValueAsString(COLUMNNAME_ContractStatus);
	}

	@Override
	public void setC_SubscriptionProgress_ID (final int C_SubscriptionProgress_ID)
	{
		if (C_SubscriptionProgress_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_C_SubscriptionProgress_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_C_SubscriptionProgress_ID, C_SubscriptionProgress_ID);
	}

	@Override
	public int getC_SubscriptionProgress_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_SubscriptionProgress_ID);
	}

	@Override
	public void setDropShip_BPartner_ID (final int DropShip_BPartner_ID)
	{
		if (DropShip_BPartner_ID < 1) 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_BPartner_ID, DropShip_BPartner_ID);
	}

	@Override
	public int getDropShip_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_BPartner_ID);
	}

	@Override
	public void setDropShip_Location_ID (final int DropShip_Location_ID)
	{
		if (DropShip_Location_ID < 1) 
			set_Value (COLUMNNAME_DropShip_Location_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_Location_ID, DropShip_Location_ID);
	}

	@Override
	public int getDropShip_Location_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_Location_ID);
	}

	@Override
	public void setDropShip_User_ID (final int DropShip_User_ID)
	{
		if (DropShip_User_ID < 1) 
			set_Value (COLUMNNAME_DropShip_User_ID, null);
		else 
			set_Value (COLUMNNAME_DropShip_User_ID, DropShip_User_ID);
	}

	@Override
	public int getDropShip_User_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DropShip_User_ID);
	}

	@Override
	public void setEventDate (final @Nullable java.sql.Timestamp EventDate)
	{
		set_Value (COLUMNNAME_EventDate, EventDate);
	}

	@Override
	public java.sql.Timestamp getEventDate() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_EventDate);
	}

	/** 
	 * EventType AD_Reference_ID=540013
	 * Reference name: C_SubscriptionProgress EventType
	 */
	public static final int EVENTTYPE_AD_Reference_ID=540013;
	/** Delivery = DE */
	public static final String EVENTTYPE_Delivery = "DE";
	/** BeginOfPause = PB */
	public static final String EVENTTYPE_BeginOfPause = "PB";
	/** EndOfPause = PE */
	public static final String EVENTTYPE_EndOfPause = "PE";
	/** Quantity = QT */
	public static final String EVENTTYPE_Quantity = "QT";
	/** Price = P */
	public static final String EVENTTYPE_Price = "P";
	@Override
	public void setEventType (final java.lang.String EventType)
	{
		set_ValueNoCheck (COLUMNNAME_EventType, EventType);
	}

	@Override
	public java.lang.String getEventType() 
	{
		return get_ValueAsString(COLUMNNAME_EventType);
	}

	@Override
	public void setM_ShipmentSchedule_ID (final int M_ShipmentSchedule_ID)
	{
		if (M_ShipmentSchedule_ID < 1) 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, null);
		else 
			set_Value (COLUMNNAME_M_ShipmentSchedule_ID, M_ShipmentSchedule_ID);
	}

	@Override
	public int getM_ShipmentSchedule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ShipmentSchedule_ID);
	}

	@Override
	public void setProcessed (final boolean Processed)
	{
		set_Value (COLUMNNAME_Processed, Processed);
	}

	@Override
	public boolean isProcessed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processed);
	}

	@Override
	public void setQty (final @Nullable BigDecimal Qty)
	{
		set_Value (COLUMNNAME_Qty, Qty);
	}

	@Override
	public BigDecimal getQty() 
	{
		final BigDecimal bd = get_ValueAsBigDecimal(COLUMNNAME_Qty);
		return bd != null ? bd : BigDecimal.ZERO;
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

	/** 
	 * Status AD_Reference_ID=540002
	 * Reference name: C_SubscriptionProgress Status
	 */
	public static final int STATUS_AD_Reference_ID=540002;
	/** Planned = P */
	public static final String STATUS_Planned = "P";
	/** Open = O */
	public static final String STATUS_Open = "O";
	/** Delivered = D */
	public static final String STATUS_Delivered = "D";
	/** InPicking = C */
	public static final String STATUS_InPicking = "C";
	/** Done = E */
	public static final String STATUS_Done = "E";
	/** Delayed = H */
	public static final String STATUS_Delayed = "H";
	@Override
	public void setStatus (final java.lang.String Status)
	{
		set_Value (COLUMNNAME_Status, Status);
	}

	@Override
	public java.lang.String getStatus() 
	{
		return get_ValueAsString(COLUMNNAME_Status);
	}
}