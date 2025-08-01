package org.compiere.model;

import de.metas.util.Check;
import org.compiere.util.DisplayType;

import java.io.Serial;
import java.sql.ResultSet;
import java.util.Properties;

/**
 * Window Tab Field Customization
 * @author Teo Sarca, teo.sarca@gmail.com
 * 			<li>BF [ 2726889 ] Finish User Window (AD_UserDef*) functionality
 */
public class MUserDefField extends X_AD_UserDef_Field
{
	/**
	 * 
	 */
	@Serial
	private static final long serialVersionUID = -5464451156146805763L;

	public MUserDefField(Properties ctx, int AD_UserDef_Field_ID, String trxName)
	{
		super(ctx, AD_UserDef_Field_ID, trxName);
	}

	public MUserDefField(Properties ctx, ResultSet rs, String trxName)
	{
		super(ctx, rs, trxName);
	}

	public void apply(final GridFieldVO vo)
	{
		final GridFieldLayoutConstraints layoutConstraints = vo.getLayoutConstraints();
		
		final String name = getName();
		if (!Check.isEmpty(name) && name.length() > 1)
			vo.setHeader(name);
		if (!Check.isEmpty(getDescription()))
			vo.setDescription(getDescription());
		if (!Check.isEmpty(getHelp()))
			vo.setHelp(getHelp());
		//
		vo.setIsDisplayed(this.isDisplayed());
		if (this.getIsReadOnly() != null)
		{
			vo.setIsReadOnly(DisplayType.toBoolean(getIsReadOnly()));
		}
		if (this.getIsSameLine() != null)
		{
			layoutConstraints.setSameLine("Y".equals(this.getIsSameLine()));
		}
		if (this.getIsUpdateable() != null)
		{
			vo.setIsUpdateable(DisplayType.toBoolean(getIsUpdateable()));
		}
		if (this.getIsMandatory() != null)
			vo.setMandatory("Y".equals(this.getIsMandatory()));
		if (this.getDisplayLength() > 0)
		{
			layoutConstraints.setDisplayLength(this.getDisplayLength());
		}
		if (!Check.isEmpty(this.getDisplayLogic(), true))
		{
			vo.setDisplayLogic(this.getDisplayLogic());
		}
		if (!Check.isEmpty(this.getDefaultValue(), true))
			vo.DefaultValue = this.getDefaultValue();
		if (this.getSortNo() > 0)
			vo.SortNo = this.getSortNo();
	}
}
