// Generated Model - DO NOT CHANGE
package de.metas.ui.web.base.model;

import javax.annotation.Nullable;
import java.sql.ResultSet;
import java.util.Properties;

/** Generated Model for WEBUI_KPI_Field
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_WEBUI_KPI_Field extends org.compiere.model.PO implements I_WEBUI_KPI_Field, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = -1933242286L;

    /** Standard Constructor */
    public X_WEBUI_KPI_Field (final Properties ctx, final int WEBUI_KPI_Field_ID, @Nullable final String trxName)
    {
      super (ctx, WEBUI_KPI_Field_ID, trxName);
    }

    /** Load Constructor */
    public X_WEBUI_KPI_Field (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setColor (final @Nullable java.lang.String Color)
	{
		set_Value (COLUMNNAME_Color, Color);
	}

	@Override
	public java.lang.String getColor() 
	{
		return get_ValueAsString(COLUMNNAME_Color);
	}

	@Override
	public void setES_FieldPath (final @Nullable java.lang.String ES_FieldPath)
	{
		set_Value (COLUMNNAME_ES_FieldPath, ES_FieldPath);
	}

	@Override
	public java.lang.String getES_FieldPath() 
	{
		return get_ValueAsString(COLUMNNAME_ES_FieldPath);
	}

	@Override
	public void setIsGroupBy (final boolean IsGroupBy)
	{
		set_Value (COLUMNNAME_IsGroupBy, IsGroupBy);
	}

	@Override
	public boolean isGroupBy() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsGroupBy);
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
	public void setOffsetName (final @Nullable java.lang.String OffsetName)
	{
		set_Value (COLUMNNAME_OffsetName, OffsetName);
	}

	@Override
	public java.lang.String getOffsetName() 
	{
		return get_ValueAsString(COLUMNNAME_OffsetName);
	}

	@Override
	public void setSQL_Select (final @Nullable java.lang.String SQL_Select)
	{
		set_Value (COLUMNNAME_SQL_Select, SQL_Select);
	}

	@Override
	public java.lang.String getSQL_Select() 
	{
		return get_ValueAsString(COLUMNNAME_SQL_Select);
	}

	@Override
	public void setUOMSymbol (final @Nullable java.lang.String UOMSymbol)
	{
		set_Value (COLUMNNAME_UOMSymbol, UOMSymbol);
	}

	@Override
	public java.lang.String getUOMSymbol() 
	{
		return get_ValueAsString(COLUMNNAME_UOMSymbol);
	}

	@Override
	public void setWEBUI_KPI_Field_ID (final int WEBUI_KPI_Field_ID)
	{
		if (WEBUI_KPI_Field_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_Field_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_WEBUI_KPI_Field_ID, WEBUI_KPI_Field_ID);
	}

	@Override
	public int getWEBUI_KPI_Field_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_WEBUI_KPI_Field_ID);
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