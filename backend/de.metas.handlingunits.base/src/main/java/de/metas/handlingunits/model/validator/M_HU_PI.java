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
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.List;

@Validator(I_M_HU_PI.class)
public class M_HU_PI
{
	private transient static final Logger logger = LogManager.getLogger(M_HU_PI.class);

	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteMHUPI(final I_M_HU_PI pi)
	{
		final List<I_M_HU_PI_Version> piVersions = handlingUnitsDAO.retrieveAllPIVersions(pi);
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
		try (final MDC.MDCCloseable ignored = TableRecordMDC.putTableRecordReference(newDefault))
		{
			if (!newDefault.isDefaultForPicking())
			{
				return;
			}

			final I_M_HU_PI previousDefault = handlingUnitsDAO.retrievePIDefaultForPicking();
			if (previousDefault != null)
			{
				logger.debug("M_HU_PI={} is now IsDefaultForPicking; -> Change previousDefault M_HU_PI={} to IsDefaultForPicking='N'",
						newDefault.getM_HU_PI_ID(), previousDefault.getM_HU_PI_ID());
				previousDefault.setIsDefaultForPicking(false);
				handlingUnitsDAO.save(previousDefault);
			}
		}
	}
}
