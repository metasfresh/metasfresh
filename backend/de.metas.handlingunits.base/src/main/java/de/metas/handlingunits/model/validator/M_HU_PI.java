package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import java.util.List;

@Validator(I_M_HU_PI.class)
public class M_HU_PI
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteMHUPI(final I_M_HU_PI pi)
	{
		final List<I_M_HU_PI_Version> piVersions = Services.get(IHandlingUnitsDAO.class).retrieveAllPIVersions(pi);
		for (final I_M_HU_PI_Version version : piVersions)
		{
			InterfaceWrapperHelper.delete(version);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_M_HU_PI.COLUMNNAME_IsDefaultForPicking
	)
	public void ensureOnlyOneDefaultForPicking(@NonNull final I_M_HU_PI newDefault)
	{
		if (!newDefault.isDefaultForPicking())
		{
			return;
		}

		final I_M_HU_PI previousDefault = Services.get(IHandlingUnitsDAO.class).retrievePIDefaultForPicking();
		if (previousDefault != null)
		{
			previousDefault.setIsDefaultForPicking(false);
			InterfaceWrapperHelper.save(previousDefault);
		}
	}
}
