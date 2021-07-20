// Generated Model - DO NOT CHANGE
package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for AD_Archive
 *  @author metasfresh (generated) 
 */
public class X_AD_Archive extends org.compiere.model.PO implements I_AD_Archive, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1859229196L;

    /** Standard Constructor */
    public X_AD_Archive (final Properties ctx, final int AD_Archive_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Archive_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Archive (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setAD_Archive_ID (final int AD_Archive_ID)
	{
		if (AD_Archive_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Archive_ID, AD_Archive_ID);
	}

	@Override
	public int getAD_Archive_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Archive_ID);
	}

	@Override
	public void setAD_Language (final java.lang.String AD_Language)
	{
		set_Value (COLUMNNAME_AD_Language, AD_Language);
	}

	@Override
	public java.lang.String getAD_Language() 
	{
		return get_ValueAsString(COLUMNNAME_AD_Language);
	}

	@Override
	public org.compiere.model.I_AD_PInstance getAD_PInstance()
	{
		return get_ValueAsPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class);
	}

	@Override
	public void setAD_PInstance(final org.compiere.model.I_AD_PInstance AD_PInstance)
	{
		set_ValueFromPO(COLUMNNAME_AD_PInstance_ID, org.compiere.model.I_AD_PInstance.class, AD_PInstance);
	}

	@Override
	public void setAD_PInstance_ID (final int AD_PInstance_ID)
	{
		if (AD_PInstance_ID < 1) 
			set_Value (COLUMNNAME_AD_PInstance_ID, null);
		else 
			set_Value (COLUMNNAME_AD_PInstance_ID, AD_PInstance_ID);
	}

	@Override
	public int getAD_PInstance_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_PInstance_ID);
	}

	@Override
	public org.compiere.model.I_AD_Process getAD_Process()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class);
	}

	@Override
	public void setAD_Process(final org.compiere.model.I_AD_Process AD_Process)
	{
		set_ValueFromPO(COLUMNNAME_AD_Process_ID, org.compiere.model.I_AD_Process.class, AD_Process);
	}

	@Override
	public void setAD_Process_ID (final int AD_Process_ID)
	{
		if (AD_Process_ID < 1) 
			set_Value (COLUMNNAME_AD_Process_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public void setAD_Table_ID (final int AD_Table_ID)
	{
		if (AD_Table_ID < 1) 
			set_Value (COLUMNNAME_AD_Table_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Table_ID, AD_Table_ID);
	}

	@Override
	public int getAD_Table_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Table_ID);
	}

	@Override
	public void setBinaryData (final byte[] BinaryData)
	{
		set_Value (COLUMNNAME_BinaryData, BinaryData);
	}

	@Override
	public byte[] getBinaryData() 
	{
		return (byte[])get_Value(COLUMNNAME_BinaryData);
	}

	@Override
	public void setC_BPartner_ID (final int C_BPartner_ID)
	{
		if (C_BPartner_ID < 1) 
			set_Value (COLUMNNAME_C_BPartner_ID, null);
		else 
			set_Value (COLUMNNAME_C_BPartner_ID, C_BPartner_ID);
	}

	@Override
	public int getC_BPartner_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_BPartner_ID);
	}

	@Override
	public void setC_Doc_Outbound_Config_ID (final int C_Doc_Outbound_Config_ID)
	{
		if (C_Doc_Outbound_Config_ID < 1) 
			set_Value (COLUMNNAME_C_Doc_Outbound_Config_ID, null);
		else 
			set_Value (COLUMNNAME_C_Doc_Outbound_Config_ID, C_Doc_Outbound_Config_ID);
	}

	@Override
	public int getC_Doc_Outbound_Config_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_C_Doc_Outbound_Config_ID);
	}

	@Override
	public void setDescription (final java.lang.String Description)
	{
		set_Value (COLUMNNAME_Description, Description);
	}

	@Override
	public java.lang.String getDescription() 
	{
		return get_ValueAsString(COLUMNNAME_Description);
	}

	/** 
	 * DocumentFlavor AD_Reference_ID=541225
	 * Reference name: C_DocType_PrintOptions_DocumentFlavor
	 */
	public static final int DOCUMENTFLAVOR_AD_Reference_ID=541225;
	/** EMail = E */
	public static final String DOCUMENTFLAVOR_EMail = "E";
	/** Print = P */
	public static final String DOCUMENTFLAVOR_Print = "P";
	@Override
	public void setDocumentFlavor (final java.lang.String DocumentFlavor)
	{
		set_Value (COLUMNNAME_DocumentFlavor, DocumentFlavor);
	}

	@Override
	public java.lang.String getDocumentFlavor() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentFlavor);
	}

	@Override
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_ValueNoCheck (COLUMNNAME_DocumentNo, DocumentNo);
	}

	@Override
	public java.lang.String getDocumentNo() 
	{
		return get_ValueAsString(COLUMNNAME_DocumentNo);
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
	public void setIsDirectEnqueue (final boolean IsDirectEnqueue)
	{
		set_Value (COLUMNNAME_IsDirectEnqueue, IsDirectEnqueue);
	}

	@Override
	public boolean isDirectEnqueue() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDirectEnqueue);
	}

	@Override
	public void setIsDirectProcessQueueItem (final boolean IsDirectProcessQueueItem)
	{
		set_Value (COLUMNNAME_IsDirectProcessQueueItem, IsDirectProcessQueueItem);
	}

	@Override
	public boolean isDirectProcessQueueItem() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsDirectProcessQueueItem);
	}

	@Override
	public void setIsFileSystem (final boolean IsFileSystem)
	{
		set_Value (COLUMNNAME_IsFileSystem, IsFileSystem);
	}

	@Override
	public boolean isFileSystem() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsFileSystem);
	}

	@Override
	public void setIsReport (final boolean IsReport)
	{
		set_Value (COLUMNNAME_IsReport, IsReport);
	}

	@Override
	public boolean isReport() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsReport);
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
	public void setRecord_ID (final int Record_ID)
	{
		if (Record_ID < 0) 
			set_Value (COLUMNNAME_Record_ID, null);
		else 
			set_Value (COLUMNNAME_Record_ID, Record_ID);
	}

	@Override
	public int getRecord_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_Record_ID);
	}
}