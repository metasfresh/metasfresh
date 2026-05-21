package de.metas.inoutcandidate.modelvalidator;

import java.util.LinkedHashSet;
import java.util.Set;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintRepository;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.inoutcandidate.invalidation.IShipmentScheduleInvalidateBL;
import de.metas.inoutcandidate.invalidation.segments.IShipmentScheduleSegment;
import de.metas.inoutcandidate.invalidation.segments.ImmutableShipmentScheduleSegment;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;

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
@Component
public class M_Shipment_Constraint
{
	private final ShipmentConstraintRepository shipmentConstraintRepository;
	private final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL;

	public M_Shipment_Constraint(
			@NonNull final ShipmentConstraintRepository shipmentConstraintRepository,
			@NonNull final IShipmentScheduleInvalidateBL shipmentScheduleInvalidateBL)
	{
		this.shipmentConstraintRepository = shipmentConstraintRepository;
		this.shipmentScheduleInvalidateBL = shipmentScheduleInvalidateBL;
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void invalidateShipmentSchedules(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<IShipmentScheduleSegment> affectedStorageSegments = extractAffectedStorageSegments(constraint, changeType);
		if (affectedStorageSegments.isEmpty())
		{
			return;
		}

		shipmentScheduleInvalidateBL.flagSegmentForRecompute(affectedStorageSegments);
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE, ModelValidator.TYPE_AFTER_DELETE })
	public void updateReceiptScheduleDeliveryStop(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<BPartnerId> affectedBPartnerIds = extractAffectedBPartnerIds(constraint, changeType);
		if (affectedBPartnerIds.isEmpty())
		{
			return;
		}

		for (final BPartnerId billBPartnerId : affectedBPartnerIds)
		{
			// MUST use the trx-aware, uncached lookup here — we run inside the constraint-save
			// transaction and need to see the just-saved row. The cached repository method
			// (getDeliveryStopConstraintIdFor) is out-of-trx and would miss in-flight changes
			// (gh#28631 TC-4).
			final boolean isDeliveryStop = shipmentConstraintRepository.hasActiveDeliveryStopFor(billBPartnerId);
			shipmentConstraintRepository.updateReceiptScheduleDeliveryStop(billBPartnerId, isDeliveryStop);
		}
	}

	private static Set<BPartnerId> extractAffectedBPartnerIds(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
	{
		final Set<BPartnerId> bpartnerIds = new LinkedHashSet<>();
		if (changeType.isNewOrChange() && constraint.getBill_BPartner_ID() > 0)
		{
			bpartnerIds.add(BPartnerId.ofRepoId(constraint.getBill_BPartner_ID()));
		}
		if (changeType.isChangeOrDelete())
		{
			final I_M_Shipment_Constraint constraintOld = InterfaceWrapperHelper.createOld(constraint, I_M_Shipment_Constraint.class);
			if (constraintOld.getBill_BPartner_ID() > 0)
			{
				bpartnerIds.add(BPartnerId.ofRepoId(constraintOld.getBill_BPartner_ID()));
			}
		}
		return bpartnerIds;
	}

	private static Set<IShipmentScheduleSegment> extractAffectedStorageSegments(final I_M_Shipment_Constraint constraint, final ModelChangeType changeType)
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
