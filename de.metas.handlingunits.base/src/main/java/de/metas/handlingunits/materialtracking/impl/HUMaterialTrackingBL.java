package de.metas.handlingunits.materialtracking.impl;

import java.util.Optional;

import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_M_Attribute;

import de.metas.handlingunits.attribute.IHUAttributesBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.util.Check;
import de.metas.util.Services;

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

public class HUMaterialTrackingBL implements IHUMaterialTrackingBL
{
	/**
	 * @param ctx
	 * @return {@link I_M_Attribute} for {@link #ATTRIBUTENAME_IsQualityInspection}
	 */
	I_M_Attribute getIsQualityInspectionAttribute()
	{
		final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);

		final I_M_Attribute attribute = attributeDAO.retrieveAttributeByValue(ATTRIBUTENAME_IsQualityInspection);
		Check.assumeNotNull(attribute, "attribute shall exist for {}", ATTRIBUTENAME_IsQualityInspection);

		return attribute;
	}

	@Override
	public Optional<IQualityInspectionSchedulable> asQualityInspectionSchedulable(final IContextAware context, final IAttributeStorage attributeStorage)
	{
		return AttributeStorageQualityInspectionSchedulable.of(this, context, attributeStorage);
	}

	@Override
	public void updateHUAttributeRecursive(final I_M_HU hu,
			final I_M_Material_Tracking materialTracking,
			final String onlyHUStatus)
	{
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);
		final IHUAttributesBL huAttributesBL = Services.get(IHUAttributesBL.class);

		final I_M_Attribute materialTrackingAttribute = materialTrackingAttributeBL.getMaterialTrackingAttribute();
		final Object attributeValue = materialTracking == null ? null : materialTracking.getM_Material_Tracking_ID();

		huAttributesBL.updateHUAttributeRecursive(hu,
				materialTrackingAttribute,
				attributeValue,
				onlyHUStatus);
	}
}
