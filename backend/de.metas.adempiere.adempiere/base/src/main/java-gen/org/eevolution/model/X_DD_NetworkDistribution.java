// Generated Model - DO NOT CHANGE
package org.eevolution.model;

import java.sql.ResultSet;
import java.util.Properties;
import javax.annotation.Nullable;

/** Generated Model for DD_NetworkDistribution
 *  @author metasfresh (generated) 
 */
@SuppressWarnings("unused")
public class X_DD_NetworkDistribution extends org.compiere.model.PO implements I_DD_NetworkDistribution, org.compiere.model.I_Persistent 
{

	private static final long serialVersionUID = 1133403930L;

    /** Standard Constructor */
    public X_DD_NetworkDistribution (final Properties ctx, final int DD_NetworkDistribution_ID, @Nullable final String trxName)
    {
      super (ctx, DD_NetworkDistribution_ID, trxName);
    }

    /** Load Constructor */
    public X_DD_NetworkDistribution (final Properties ctx, final ResultSet rs, @Nullable final String trxName)
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
	public void setCopyFrom (final @Nullable java.lang.String CopyFrom)
	{
		set_Value (COLUMNNAME_CopyFrom, CopyFrom);
	}

	@Override
	public java.lang.String getCopyFrom() 
	{
		return get_ValueAsString(COLUMNNAME_CopyFrom);
	}

	@Override
	public void setDD_NetworkDistribution_ID (final int DD_NetworkDistribution_ID)
	{
		if (DD_NetworkDistribution_ID < 1) 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistribution_ID, null);
		else 
			set_ValueNoCheck (COLUMNNAME_DD_NetworkDistribution_ID, DD_NetworkDistribution_ID);
	}

	@Override
	public int getDD_NetworkDistribution_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_DD_NetworkDistribution_ID);
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
	public void setDocumentNo (final @Nullable java.lang.String DocumentNo)
	{
		set_Value (COLUMNNAME_DocumentNo, DocumentNo);
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
	public void setIsHUDestroyed (final boolean IsHUDestroyed)
	{
		set_Value (COLUMNNAME_IsHUDestroyed, IsHUDestroyed);
	}

	@Override
	public boolean isHUDestroyed() 
	{
		return get_ValueAsBoolean(COLUMNNAME_IsHUDestroyed);
	}

	@Override
	public org.compiere.model.I_M_ChangeNotice getM_ChangeNotice()
	{
		return get_ValueAsPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class);
	}

	@Override
	public void setM_ChangeNotice(final org.compiere.model.I_M_ChangeNotice M_ChangeNotice)
	{
		set_ValueFromPO(COLUMNNAME_M_ChangeNotice_ID, org.compiere.model.I_M_ChangeNotice.class, M_ChangeNotice);
	}

	@Override
	public void setM_ChangeNotice_ID (final int M_ChangeNotice_ID)
	{
		if (M_ChangeNotice_ID < 1) 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, null);
		else 
			set_Value (COLUMNNAME_M_ChangeNotice_ID, M_ChangeNotice_ID);
	}

	@Override
	public int getM_ChangeNotice_ID() 
	{
		return get_ValueAsInt(COLUMNNAME_M_ChangeNotice_ID);
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
	public void setProcessing (final boolean Processing)
	{
		set_Value (COLUMNNAME_Processing, Processing);
	}

	@Override
	public boolean isProcessing() 
	{
		return get_ValueAsBoolean(COLUMNNAME_Processing);
	}

	@Override
	public void setRevision (final @Nullable java.lang.String Revision)
	{
		set_Value (COLUMNNAME_Revision, Revision);
	}

	@Override
	public java.lang.String getRevision() 
	{
		return get_ValueAsString(COLUMNNAME_Revision);
	}

	@Override
	public void setValidFrom (final @Nullable java.sql.Timestamp ValidFrom)
	{
		set_Value (COLUMNNAME_ValidFrom, ValidFrom);
	}

	@Override
	public java.sql.Timestamp getValidFrom() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidFrom);
	}

	@Override
	public void setValidTo (final @Nullable java.sql.Timestamp ValidTo)
	{
		set_Value (COLUMNNAME_ValidTo, ValidTo);
	}

	@Override
	public java.sql.Timestamp getValidTo() 
	{
		return get_ValueAsTimestamp(COLUMNNAME_ValidTo);
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