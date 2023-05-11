// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for RabbitMQ_Message_Audit
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_RabbitMQ_Message_Audit extends org.compiere.model.PO implements I_RabbitMQ_Message_Audit, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1724339147L;

    /** Standard Constructor */
    public X_RabbitMQ_Message_Audit (final Properties ctx, final int RabbitMQ_Message_Audit_ID, @Nullable final String trxName)
    {
      super (ctx, RabbitMQ_Message_Audit_ID, trxName);
    }

    /** Load Constructor */
    public X_RabbitMQ_Message_Audit (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setContent (final @Nullable java.lang.String Content)
	{
		set_ValueNoCheck (COLUMNNAME_Content, Content);
	}

	@Override
	public java.lang.String getContent() 
	{
		return get_ValueAsString(COLUMNNAME_Content);
	}

	/** 
	 * Direction AD_Reference_ID=541551
	 * Reference name: RabbitMQ_Message_Audit_Direction
	 */
	public static final int DIRECTION_AD_Reference_ID=541551;
	/** In = I */
	public static final String DIRECTION_In = "I";
	/** Out = O */
	public static final String DIRECTION_Out = "O";
	@Override
	public void setDirection (final java.lang.String Direction)
	{
		set_ValueNoCheck (COLUMNNAME_Direction, Direction);
	}

	@Override
	public java.lang.String getDirection() 
	{
		return get_ValueAsString(COLUMNNAME_Direction);
	}

	@Override
	public void setEvent_UUID (final @Nullable java.lang.String Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Event_UUID, Event_UUID);
	}

	@Override
	public java.lang.String getEvent_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Event_UUID);
	}

	@Override
	public void setHost (final @Nullable java.lang.String Host)
	{
		set_ValueNoCheck (COLUMNNAME_Host, Host);
	}

	@Override
	public java.lang.String getHost() 
	{
		return get_ValueAsString(COLUMNNAME_Host);
	}

	@Override
	public void setRabbitMQ_Message_Audit_ID (final int RabbitMQ_Message_Audit_ID)
	{
		if (RabbitMQ_Message_Audit_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_RabbitMQ_Message_Audit_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_RabbitMQ_Message_Audit_ID, RabbitMQ_Message_Audit_ID);
	}

	@Override
	public int getRabbitMQ_Message_Audit_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_RabbitMQ_Message_Audit_ID);
	}

	@Override
	public void setRabbitMQ_QueueName (final @Nullable java.lang.String RabbitMQ_QueueName)
	{
		set_ValueNoCheck (COLUMNNAME_RabbitMQ_QueueName, RabbitMQ_QueueName);
	}

	@Override
	public java.lang.String getRabbitMQ_QueueName() 
	{
		return get_ValueAsString(COLUMNNAME_RabbitMQ_QueueName);
	}

	@Override
	public void setRelated_Event_UUID (final @Nullable java.lang.String Related_Event_UUID)
	{
		set_ValueNoCheck (COLUMNNAME_Related_Event_UUID, Related_Event_UUID);
	}

	@Override
	public java.lang.String getRelated_Event_UUID() 
	{
		return get_ValueAsString(COLUMNNAME_Related_Event_UUID);
	}
}