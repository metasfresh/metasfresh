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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.util.Services;

@Validator(I_M_HU_PI_Version.class)
public class M_HU_PI_Version
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteMHUPIVersion(final I_M_HU_PI_Version piVersion)
	{
		//
		// Delete PI Items
		final List<I_M_HU_PI_Item> piItems = Services.get(IHandlingUnitsDAO.class).retrieveAllPIItems(piVersion);
		for (final I_M_HU_PI_Item item : piItems)
		{
			InterfaceWrapperHelper.delete(item);
		}

		//
		// Delete PI Attributes
		final HuPackingInstructionsVersionId piVersionId = HuPackingInstructionsVersionId.ofRepoId(piVersion.getM_HU_PI_Version_ID());
		Services.get(IHUPIAttributesDAO.class).deleteByVersionId(piVersionId);
	}
}
