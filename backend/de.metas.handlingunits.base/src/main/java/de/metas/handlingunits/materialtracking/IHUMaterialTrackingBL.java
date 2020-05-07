package de.metas.handlingunits.materialtracking;

import org.adempiere.model.IContextAware;
import org.adempiere.util.ISingletonService;

import com.google.common.base.Optional;

import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.materialtracking.model.I_M_Material_Tracking;

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

public interface IHUMaterialTrackingBL extends ISingletonService
{
	/** Scheduled to Quality Inspection attribute name (i.e. M_Attribute.Value) */
	String ATTRIBUTENAME_IsQualityInspection = "IsQualityInspection";
	String ATTRIBUTEVALUE_IsQualityInspection_Yes = "Y";
	String ATTRIBUTEVALUE_IsQualityInspection_No = "N";

	String ATTRIBUTENAME_QualityInspectionCycle = "QualityInspectionCycle";

	/**
	 * Wraps given {@link IAttributeStorage} to {@link IQualityInspectionSchedulable} if supported.
	 * 
	 * @param context
	 * @param attributeStorage
	 * @return optional {@link IQualityInspectionSchedulable}.
	 */
	Optional<IQualityInspectionSchedulable> asQualityInspectionSchedulable(IContextAware context, IAttributeStorage attributeStorage);

	/**
	 * Iterates the HU-tree of the given HU and sets <code>M_HU_MAterial_Tracking_ID</code> attribute to the given value.
	 * 
	 * @param hu
	 * @param materialTracking
	 * @param onlyHUStatus may be <code>null</code> or empty. Otherwise, only HUs with the given status are updated. However, all HUs are iterated.
	 */
	void updateHUAttributeRecursive(I_M_HU hu,
			I_M_Material_Tracking materialTracking,
			String onlyHUStatus);
}
