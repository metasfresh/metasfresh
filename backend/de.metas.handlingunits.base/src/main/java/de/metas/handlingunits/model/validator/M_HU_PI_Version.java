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

import lombok.NonNull;

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.attribute.IHUPIAttributesDAO;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.i18n.AdMessageKey;
import de.metas.product.PackageDimensionCalcMethod;
import de.metas.util.Services;

@Validator(I_M_HU_PI_Version.class)
public class M_HU_PI_Version
{
	private static final AdMessageKey MSG_CALC_METHOD_ONLY_ON_TU = AdMessageKey.of("M_HU_PI_Version_CalcMethodOnlyOnTU");

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = { I_M_HU_PI_Version.COLUMNNAME_PackageDimensionCalcMethod, I_M_HU_PI_Version.COLUMNNAME_HU_UnitType })
	public void rejectCalcMethodOnNonTUVersions(@NonNull final I_M_HU_PI_Version piVersion)
	{
		final PackageDimensionCalcMethod calcMethod = PackageDimensionCalcMethod.ofNullableCode(piVersion.getPackageDimensionCalcMethod());
		if (calcMethod == null)
		{
			return;
		}
		final HuUnitType huUnitType = HuUnitType.ofNullableCode(piVersion.getHU_UnitType());
		if (huUnitType == null || !huUnitType.isTU())
		{
			throw new AdempiereException(MSG_CALC_METHOD_ONLY_ON_TU)
					.setParameter("HU_UnitType", huUnitType)
					.setParameter("PackageDimensionCalcMethod", calcMethod)
					.setParameter("M_HU_PI_Version_ID", piVersion.getM_HU_PI_Version_ID());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteMHUPIVersion(@NonNull final I_M_HU_PI_Version piVersion)
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
