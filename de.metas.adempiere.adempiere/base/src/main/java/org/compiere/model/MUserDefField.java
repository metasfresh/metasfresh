package org.compiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.sql.ResultSet;
import java.util.Properties;

import org.adempiere.util.Check;
import org.compiere.util.DisplayType;

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
