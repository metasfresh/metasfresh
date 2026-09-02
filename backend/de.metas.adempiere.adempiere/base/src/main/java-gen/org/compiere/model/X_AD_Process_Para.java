// Generated Model - DO NOT CHANGE
package org.compiere.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for AD_Process_Para
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_AD_Process_Para extends org.compiere.model.PO implements I_AD_Process_Para, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1722723924L;

    /** Standard Constructor */
    public X_AD_Process_Para (final Properties ctx, final int AD_Process_Para_ID, @Nullable final String trxName)
    {
      super (ctx, AD_Process_Para_ID, trxName);
    }

    /** Load Constructor */
    public X_AD_Process_Para (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public org.compiere.model.I_AD_Element getAD_Element()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class);
	}

	@Override
	public void setAD_Element(final org.compiere.model.I_AD_Element AD_Element)
	{
		set_ValueFromPO(COLUMNNAME_AD_Element_ID, org.compiere.model.I_AD_Element.class, AD_Element);
	}

	@Override
	public void setAD_Element_ID (final int AD_Element_ID)
	{
		if (AD_Element_ID < 1) 
			set_Value (COLUMNNAME_AD_Element_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Element_ID, AD_Element_ID);
	}

	@Override
	public int getAD_Element_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Element_ID);
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
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_ID, AD_Process_ID);
	}

	@Override
	public int getAD_Process_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_ID);
	}

	@Override
	public void setAD_Process_Para_ID (final int AD_Process_Para_ID)
	{
		if (AD_Process_Para_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_AD_Process_Para_ID, AD_Process_Para_ID);
	}

	@Override
	public int getAD_Process_Para_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Process_Para_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference(final org.compiere.model.I_AD_Reference AD_Reference)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_ID, org.compiere.model.I_AD_Reference.class, AD_Reference);
	}

	@Override
	public void setAD_Reference_ID (final int AD_Reference_ID)
	{
		if (AD_Reference_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_ID, AD_Reference_ID);
	}

	@Override
	public int getAD_Reference_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_ID);
	}

	@Override
	public org.compiere.model.I_AD_Reference getAD_Reference_Value()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class);
	}

	@Override
	public void setAD_Reference_Value(final org.compiere.model.I_AD_Reference AD_Reference_Value)
	{
		set_ValueFromPO(COLUMNNAME_AD_Reference_Value_ID, org.compiere.model.I_AD_Reference.class, AD_Reference_Value);
	}

	@Override
	public void setAD_Reference_Value_ID (final int AD_Reference_Value_ID)
	{
		if (AD_Reference_Value_ID < 1) 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Reference_Value_ID, AD_Reference_Value_ID);
	}

	@Override
	public int getAD_Reference_Value_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Reference_Value_ID);
	}

	@Override
	public org.compiere.model.I_AD_Val_Rule getAD_Val_Rule()
	{
		return get_ValueAsPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class);
	}

	@Override
	public void setAD_Val_Rule(final org.compiere.model.I_AD_Val_Rule AD_Val_Rule)
	{
		set_ValueFromPO(COLUMNNAME_AD_Val_Rule_ID, org.compiere.model.I_AD_Val_Rule.class, AD_Val_Rule);
	}

	@Override
	public void setAD_Val_Rule_ID (final int AD_Val_Rule_ID)
	{
		if (AD_Val_Rule_ID < 1) 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, null);
		else 
			set_Value (COLUMNNAME_AD_Val_Rule_ID, AD_Val_Rule_ID);
	}

	@Override
	public int getAD_Val_Rule_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_AD_Val_Rule_ID);
	}

	/** 
	 * BarcodeScannerType AD_Reference_ID=541029
	 * Reference name: BarcodeScannerType
	 */
	public static final int BARCODESCANNERTYPE_AD_Reference_ID=541029;
	/** QRCode = qrCode */
	public static final String BARCODESCANNERTYPE_QRCode = "qrCode";
	/** Barcode = barcode */
	public static final String BARCODESCANNERTYPE_Barcode = "barcode";
	/** Datamatrix = datamatrix */
	public static final String BARCODESCANNERTYPE_Datamatrix = "datamatrix";
	@Override
	public void setBarcodeScannerType (final @Nullable java.lang.String BarcodeScannerType)
	{
		set_Value (COLUMNNAME_BarcodeScannerType, BarcodeScannerType);
	}

	@Override
	public java.lang.String getBarcodeScannerType() 
	{
		return get_ValueAsString(COLUMNNAME_BarcodeScannerType);
	}

	@Override
	public void setColumnName (final java.lang.String ColumnName)
	{
		set_Value (COLUMNNAME_ColumnName, ColumnName);
	}

	@Override
	public java.lang.String getColumnName() 
	{
		return get_ValueAsString(COLUMNNAME_ColumnName);
	}

	@Override
	public void setDefaultValue (final @Nullable java.lang.String DefaultValue)
	{
		set_Value (COLUMNNAME_DefaultValue, DefaultValue);
	}

	@Override
	public java.lang.String getDefaultValue() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultValue);
	}

	@Override
	public void setDefaultValue2 (final @Nullable java.lang.String DefaultValue2)
	{
		set_Value (COLUMNNAME_DefaultValue2, DefaultValue2);
	}

	@Override
	public java.lang.String getDefaultValue2() 
	{
		return get_ValueAsString(COLUMNNAME_DefaultValue2);
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
	public void setDisplayLogic (final @Nullable java.lang.String DisplayLogic)
	{
		set_Value (COLUMNNAME_DisplayLogic, DisplayLogic);
	}

	@Override
	public java.lang.String getDisplayLogic() 
	{
		return get_ValueAsString(COLUMNNAME_DisplayLogic);
	}

	/** 
	 * EntityType AD_Reference_ID=389
	 * Reference name: _EntityTypeNew
	 */
	public static final int ENTITYTYPE_AD_Reference_ID=389;
	@Override
	public void setEntityType (final java.lang.String EntityType)
	{
		set_Value (COLUMNNAME_EntityType, EntityType);
	}

	@Override
	public java.lang.String getEntityType() 
	{
		return get_ValueAsString(COLUMNNAME_EntityType);
	}

	@Override
	public void setFieldLength (final int FieldLength)
	{
		set_Value (COLUMNNAME_FieldLength, FieldLength);
	}

	@Override
	public int getFieldLength() 
	{
		return get_ValueAsInt(COLUMNNAME_FieldLength);
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
	public void setIsAutocomplete (final boolean IsAutocomplete)
	{
		set_Value (COLUMNNAME_IsAutocomplete, IsAutocomplete);
	}

	@Override
	public boolean isAutocomplete() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsAutocomplete);
	}

	@Override
	public void setIsCentrallyMaintained (final boolean IsCentrallyMaintained)
	{
		set_Value (COLUMNNAME_IsCentrallyMaintained, IsCentrallyMaintained);
	}

	@Override
	public boolean isCentrallyMaintained() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsCentrallyMaintained);
	}

	@Override
	public void setIsEncrypted (final boolean IsEncrypted)
	{
		set_Value (COLUMNNAME_IsEncrypted, IsEncrypted);
	}

	@Override
	public boolean isEncrypted() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsEncrypted);
	}

	@Override
	public void setIsMandatory (final boolean IsMandatory)
	{
		set_Value (COLUMNNAME_IsMandatory, IsMandatory);
	}

	@Override
	public boolean isMandatory() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsMandatory);
	}

	@Override
	public void setIsRange (final boolean IsRange)
	{
		set_Value (COLUMNNAME_IsRange, IsRange);
	}

	@Override
	public boolean isRange() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsRange);
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
	public void setReadOnlyLogic (final @Nullable java.lang.String ReadOnlyLogic)
	{
		set_Value (COLUMNNAME_ReadOnlyLogic, ReadOnlyLogic);
	}

	@Override
	public java.lang.String getReadOnlyLogic() 
	{
		return get_ValueAsString(COLUMNNAME_ReadOnlyLogic);
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
	public void setShowInactiveValues (final boolean ShowInactiveValues)
	{
		set_Value (COLUMNNAME_ShowInactiveValues, ShowInactiveValues);
	}

	@Override
	public boolean isShowInactiveValues() 
	{
		return get_ValueAsBoolean(COLUMNNAME_ShowInactiveValues);
	}

	@Override
	public void setValueMax (final @Nullable java.lang.String ValueMax)
	{
		set_Value (COLUMNNAME_ValueMax, ValueMax);
	}

	@Override
	public java.lang.String getValueMax() 
	{
		return get_ValueAsString(COLUMNNAME_ValueMax);
	}

	@Override
	public void setValueMin (final @Nullable java.lang.String ValueMin)
	{
		set_Value (COLUMNNAME_ValueMin, ValueMin);
	}

	@Override
	public java.lang.String getValueMin() 
	{
		return get_ValueAsString(COLUMNNAME_ValueMin);
	}

	@Override
	public void setVFormat (final @Nullable java.lang.String VFormat)
	{
		set_Value (COLUMNNAME_VFormat, VFormat);
	}

	@Override
	public java.lang.String getVFormat() 
	{
		return get_ValueAsString(COLUMNNAME_VFormat);
	}
}