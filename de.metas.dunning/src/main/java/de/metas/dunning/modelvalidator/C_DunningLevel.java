package de.metas.dunning.modelvalidator;

/*
 * #%L
 * de.metas.dunning
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.ModelValidator;

import de.metas.dunning.interfaces.I_C_DunningLevel;

@Validator(I_C_DunningLevel.class)
public class C_DunningLevel
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void validate(final I_C_DunningLevel level)
	{
		if (level.isShowNotDue())
		{
			throw new AdempiereException("@NotSupported@ @" + I_C_DunningLevel.COLUMNNAME_IsShowNotDue + "@=@Y@");
		}
	}
}
