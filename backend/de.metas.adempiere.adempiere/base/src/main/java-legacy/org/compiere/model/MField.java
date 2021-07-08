package org.compiere.model;

import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;

/**
 * Field Model
 * 
 * @author Jorg Janke
 */
public class MField extends X_AD_Field
{
	public MField(final Properties ctx, final int AD_Field_ID, final String trxName)
	{
		super(ctx, AD_Field_ID, trxName);
		if (is_new())
		{
			setEntityType(ENTITYTYPE_UserMaintained);	// U
			setIsDisplayed(true);	// Y
			setIsEncrypted(false);
			setIsFieldOnly(false);
			setIsHeading(false);
			setIsReadOnly(false);
			setIsSameLine(false);
		}
	}

	public MField(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}

	public MField(final I_AD_Tab parent)
	{
		this(InterfaceWrapperHelper.getCtx(parent), 0, InterfaceWrapperHelper.getTrxName(parent));
		setClientOrgFromModel(parent);
		setAD_Tab(parent);
	}

	public void setColumn(final I_AD_Column column)
	{
		setAD_Column_ID(column.getAD_Column_ID());
		setName(column.getName());
		setDescription(column.getDescription());
		setHelp(column.getHelp());
		setDisplayLength(column.getFieldLength());
		setEntityType(column.getEntityType());
	}

}
