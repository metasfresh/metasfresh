package de.metas.inoutcandidate.modelvalidator;

import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.storage.IStorageSegment;
import de.metas.storage.impl.ImmutableStorageSegment;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Interceptor(I_M_Shipment_Constraint.class)
public class M_Shipment_Constraint
{
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedules(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<IStorageSegment> affectedStorageSegments = extractAffectedStorageSegments(constraint, changeType);
		Services.get(IShipmentSchedulePA.class).invalidate(affectedStorageSegments);
	}

	private static final Set<IStorageSegment> extractAffectedStorageSegments(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<IStorageSegment> storageSegments = new LinkedHashSet<>();
		if (changeType.isNewOrChange())
		{
			if (constraint.isActive())
			{
				storageSegments.add(createStorageSegment(constraint));
			}
		}
		if (changeType.isChangeOrDelete())
		{
			final I_M_Shipment_Constraint constraintOld = InterfaceWrapperHelper.createOld(constraint, I_M_Shipment_Constraint.class);
			if (constraintOld.isActive())
			{
				storageSegments.add(createStorageSegment(constraintOld));
			}
		}

		return storageSegments;
	}

	private static IStorageSegment createStorageSegment(final I_M_Shipment_Constraint constraint)
	{
		return ImmutableStorageSegment.builder()
				.anyC_BPartner_ID()
				.Bill_BPartner_ID(constraint.getBill_BPartner_ID())
				.anyM_Product_ID()
				.anyM_Locator_ID()
				.build();
	}
}
