package de.metas.handlingunits.materialtracking.impl;

import java.util.Map;

import org.adempiere.model.IContextAware;
import org.adempiere.util.Services;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.DisplayType;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.materialtracking.IMaterialTrackingAttributeBL;

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

/**
 * {@link IQualityInspectionSchedulable} which wraps an {@link IAttributeStorage}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
class AttributeStorageQualityInspectionSchedulable implements IQualityInspectionSchedulable
{
	public static final Optional<IQualityInspectionSchedulable> of(final HUMaterialTrackingBL huMaterialTrackingBL, final IContextAware context, final IAttributeStorage attributeStorage)
	{
		final IMaterialTrackingAttributeBL materialTrackingAttributeBL = Services.get(IMaterialTrackingAttributeBL.class);
		if (!materialTrackingAttributeBL.isMaterialTrackingSet(context, attributeStorage))
		{
			return Optional.absent();
		}

		final I_M_Attribute attrIsQualityInspection = huMaterialTrackingBL.getIsQualityInspectionAttribute(context.getCtx());
		if (!attributeStorage.hasAttribute(attrIsQualityInspection))
		{
			return Optional.absent();
		}

		final I_M_Attribute attrMaterialTrackingId = materialTrackingAttributeBL.getMaterialTrackingAttribute(context.getCtx());
		final IQualityInspectionSchedulable qualityInspectionAware = new AttributeStorageQualityInspectionSchedulable(attributeStorage, attrMaterialTrackingId, attrIsQualityInspection);
		return Optional.of(qualityInspectionAware);
	}

	private final IAttributeStorage attributeStorage;
	private final I_M_Attribute attrIsQualityInspection;
	private final I_M_Attribute attrMaterialTrackingId;

	private AttributeStorageQualityInspectionSchedulable(final IAttributeStorage attributeStorage, final I_M_Attribute attrMaterialTrackingId, I_M_Attribute attrIsQualityInspection)
	{
		super();
		this.attributeStorage = attributeStorage;
		this.attrMaterialTrackingId = attrMaterialTrackingId;
		this.attrIsQualityInspection = attrIsQualityInspection;
	}

	@Override
	public boolean isQualityInspection()
	{
		final boolean qualityInspectionFlag = DisplayType.toBoolean(attributeStorage.getValue(attrIsQualityInspection));
		return qualityInspectionFlag;
	}

	@Override
	public void setQualityInspection(final boolean qualityInspectionFlag)
	{
		attributeStorage.setValue(attrIsQualityInspection, qualityInspectionFlag ? IHUMaterialTrackingBL.ATTRIBUTEVALUE_IsQualityInspection_Yes
				: IHUMaterialTrackingBL.ATTRIBUTEVALUE_IsQualityInspection_No);
	}

	@Override
	public Map<I_M_Attribute, Object> getAttributesAsMap()
	{
		return ImmutableMap.<I_M_Attribute, Object> builder()
				.put(attrMaterialTrackingId, attributeStorage.getValue(attrMaterialTrackingId))
				.put(attrIsQualityInspection, attributeStorage.getValue(attrIsQualityInspection))
				.build();
	}
}
