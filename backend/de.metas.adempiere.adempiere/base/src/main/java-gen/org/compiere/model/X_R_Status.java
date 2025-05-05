// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for R_Status
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_R_Status extends org.compiere.model.PO implements I_R_Status, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -356716214L;

    /** Standard Constructor */
    public X_R_Status (final Properties ctx, final int R_Status_ID, @Nullable final String trxName)
    {
      super (ctx, R_Status_ID, trxName);
    }

    /** Load Constructor */
    public X_R_Status (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCalendarColor (final @Nullable java.lang.String CalendarColor)
	{
		set_Value (COLUMNNAME_CalendarColor, CalendarColor);
	}

	@Override
	public java.lang.String getCalendarColor() 
	{
		return get_ValueAsString(COLUMNNAME_CalendarColor);
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
	public void setHelp (final @Nullable java.lang.String Help)
	{
		set_Value (COLUMNNAME_Help, Help);
	}

	@Override
	public java.lang.String getHelp() 
	{
		return get_ValueAsString(COLUMNNAME_Help);
	}

	@Override
	public void setIsClosed (final boolean IsClosed)
	{
		set_Value (COLUMNNAME_IsClosed, IsClosed);
	}

	@Override
	public boolean isClosed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsClosed);
	}

	@Override
	public void setIsDefault (final boolean IsDefault)
	{
		set_Value (COLUMNNAME_IsDefault, IsDefault);
	}

	@Override
	public boolean isDefault() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDefault);
	}

	@Override
	public void setIsFinalClose (final boolean IsFinalClose)
	{
		set_Value (COLUMNNAME_IsFinalClose, IsFinalClose);
	}

	@Override
	public boolean isFinalClose() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFinalClose);
	}

	@Override
	public void setIsOpen (final boolean IsOpen)
	{
		set_Value (COLUMNNAME_IsOpen, IsOpen);
	}

	@Override
	public boolean isOpen() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsOpen);
	}

	@Override
	public void setIsWebCanUpdate (final boolean IsWebCanUpdate)
	{
		set_Value (COLUMNNAME_IsWebCanUpdate, IsWebCanUpdate);
	}

	@Override
	public boolean isWebCanUpdate() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsWebCanUpdate);
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
	public org.compiere.model.I_R_Status getNext_Status()
	{
		return get_ValueAsPO(COLUMNNAME_Next_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setNext_Status(final org.compiere.model.I_R_Status Next_Status)
	{
		set_ValueFromPO(COLUMNNAME_Next_Status_ID, org.compiere.model.I_R_Status.class, Next_Status);
	}

	@Override
	public void setNext_Status_ID (final int Next_Status_ID)
	{
		if (Next_Status_ID < 1) 
			set_Value (COLUMNNAME_Next_Status_ID, null);
		else 
			set_Value (COLUMNNAME_Next_Status_ID, Next_Status_ID);
	}

	@Override
	public int getNext_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Next_Status_ID);
	}

	@Override
	public void setR_Status_ID (final int R_Status_ID)
	{
		if (R_Status_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_R_Status_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_R_Status_ID, R_Status_ID);
	}

	@Override
	public int getR_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_Status_ID);
	}

	@Override
	public org.compiere.model.I_R_StatusCategory getR_StatusCategory()
	{
		return get_ValueAsPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class);
	}

	@Override
	public void setR_StatusCategory(final org.compiere.model.I_R_StatusCategory R_StatusCategory)
	{
		set_ValueFromPO(COLUMNNAME_R_StatusCategory_ID, org.compiere.model.I_R_StatusCategory.class, R_StatusCategory);
	}

	@Override
	public void setR_StatusCategory_ID (final int R_StatusCategory_ID)
	{
		if (R_StatusCategory_ID < 1) 
			set_Value (COLUMNNAME_R_StatusCategory_ID, null);
		else 
			set_Value (COLUMNNAME_R_StatusCategory_ID, R_StatusCategory_ID);
	}

	@Override
	public int getR_StatusCategory_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_R_StatusCategory_ID);
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
	public void setTimeoutDays (final int TimeoutDays)
	{
		set_Value (COLUMNNAME_TimeoutDays, TimeoutDays);
	}

	@Override
	public int getTimeoutDays() 
	{
		return get_ValueAsInt(COLUMNNAME_TimeoutDays);
	}

	@Override
	public org.compiere.model.I_R_Status getUpdate_Status()
	{
		return get_ValueAsPO(COLUMNNAME_Update_Status_ID, org.compiere.model.I_R_Status.class);
	}

	@Override
	public void setUpdate_Status(final org.compiere.model.I_R_Status Update_Status)
	{
		set_ValueFromPO(COLUMNNAME_Update_Status_ID, org.compiere.model.I_R_Status.class, Update_Status);
	}

	@Override
	public void setUpdate_Status_ID (final int Update_Status_ID)
	{
		if (Update_Status_ID < 1) 
			set_Value (COLUMNNAME_Update_Status_ID, null);
		else 
			set_Value (COLUMNNAME_Update_Status_ID, Update_Status_ID);
	}

	@Override
	public int getUpdate_Status_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Update_Status_ID);
	}

	@Override
	public void setValue (final java.lang.String Value)
	{
		set_Value (COLUMNNAME_Value, Value);
	}

	@Override
	public java.lang.String getValue() 
	{
		return get_ValueAsString(COLUMNNAME_Value);
	}
}