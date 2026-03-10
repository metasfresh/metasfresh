package de.metas.inoutcandidate.modelvalidator;

import java.util.LinkedHashSet;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.inoutcandidate.api.IShipmentConstraintsBL;
import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.util.Services;

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
		final Set<IShipmentScheduleSegment> affectedStorageSegments = extractAffectedStorageSegments(constraint, changeType);
		if (affectedStorageSegments.isEmpty())
		{
			return;
		}

		final IShipmentScheduleInvalidateBL invalidSchedulesInvalidator = Services.get(IShipmentScheduleInvalidateBL.class);
		invalidSchedulesInvalidator.flagSegmentForRecompute(affectedStorageSegments);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateReceiptScheduleDeliveryStop(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<Integer> affectedBPartnerIds = extractAffectedBPartnerIds(constraint, changeType);
		if (affectedBPartnerIds.isEmpty())
		{
			return;
		}

		final IShipmentConstraintsBL shipmentConstraintsBL = Services.get(IShipmentConstraintsBL.class);

		for (final int bpartnerId : affectedBPartnerIds)
		{
			final boolean isDeliveryStop = shipmentConstraintsBL.getDeliveryStopShipmentConstraintId(bpartnerId) > 0;

			Services.get(IQueryBL.class)
					.createQueryBuilder(I_M_ReceiptSchedule.class)
					.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID, bpartnerId)
					.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
					.addNotEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
					.create()
					.updateDirectly()
					.addSetColumnValue(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
					.execute();
		}
	}

	private static Set<Integer> extractAffectedBPartnerIds(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<Integer> bpartnerIds = new LinkedHashSet<>();
		if (changeType.isNewOrChange() && constraint.getBill_BPartner_ID() > 0)
		{
			bpartnerIds.add(constraint.getBill_BPartner_ID());
		}
		if (changeType.isChangeOrDelete())
		{
			final I_M_Shipment_Constraint constraintOld = InterfaceWrapperHelper.createOld(constraint, I_M_Shipment_Constraint.class);
			if (constraintOld.getBill_BPartner_ID() > 0)
			{
				bpartnerIds.add(constraintOld.getBill_BPartner_ID());
			}
		}
		return bpartnerIds;
	}

	private static final Set<IShipmentScheduleSegment> extractAffectedStorageSegments(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<IShipmentScheduleSegment> storageSegments = new LinkedHashSet<>();
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

	private static IShipmentScheduleSegment createStorageSegment(final I_M_Shipment_Constraint constraint)
	{
		return ImmutableShipmentScheduleSegment.builder()
				.anyBPartner()
				.billBPartnerId(constraint.getBill_BPartner_ID())
				.anyProduct()
				.anyLocator()
				.build();
	}
}
