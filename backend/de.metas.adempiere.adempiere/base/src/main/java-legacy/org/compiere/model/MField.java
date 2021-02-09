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
			// setObscureType(OBSCURETYPE_ObscureDigitsButLast4);
		}
	}	// MField

	/**
	 * Load Constructor
	 * 
	 * @param ctx context
	 * @param rs result set
	 * @param trxName transaction
	 */
	public MField(final Properties ctx, final ResultSet rs, final String trxName)
	{
		super(ctx, rs, trxName);
	}	// MField

	/**
	 * Parent Constructor
	 * 
	 * @param parent parent
	 */
	public MField(final I_AD_Tab parent)
	{
		this(InterfaceWrapperHelper.getCtx(parent), 0, InterfaceWrapperHelper.getTrxName(parent));
		setClientOrgFromModel(parent);
		setAD_Tab(parent);
	}	// MField

	/**
	 * Copy Constructor
	 * 
	 * @param parent parent
	 * @param from copy from
	 */
	public MField(final MTab parent, final MField from)
	{
		this(parent.getCtx(), 0, parent.get_TrxName());
		copyValues(from, this);
		setClientOrg(parent);
		setAD_Tab_ID(parent.getAD_Tab_ID());
		setEntityType(parent.getEntityType());
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
