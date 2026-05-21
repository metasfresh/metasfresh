/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.inoutcandidate.shipmentconstraint;

import de.metas.bpartner.BPartnerId;
import de.metas.inoutcandidate.ShipmentConstraintId;
import de.metas.inoutcandidate.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Persistence boundary for {@link ShipmentConstraint} ({@code M_Shipment_Constraint}).
 * <p>
 * Replaces the legacy {@code IShipmentConstraintsDAO} + record-style methods that
 * lived in {@code IShipmentConstraintsBL}. All public methods take {@code RepoIdAware}
 * parameters; raw ints stay inside the repository.
 */
@Repository
public class ShipmentConstraintRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Looks up the manual ({@code IsManual=Y}) constraint for the given Bill-BPartner,
	 * regardless of {@code IsActive} — re-toggling the BPartner's flag must re-activate
	 * the existing row instead of creating a parallel duplicate.
	 */
	public Optional<ShipmentConstraint> getManualForBPartner(@NonNull final BPartnerId billBPartnerId)
	{
		final I_M_Shipment_Constraint record = queryBL.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, billBPartnerId.getRepoId())
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, true)
				.create()
				.firstOnly(I_M_Shipment_Constraint.class);
		return Optional.ofNullable(record).map(ShipmentConstraintRepository::toDomain);
	}

	/**
	 * Trx-aware (thread-inherited) and uncached: must see constraint rows just saved in
	 * the current transaction. Use this from inside model interceptors that need to react
	 * to in-flight changes; use {@link #getDeliveryStopConstraintIdFor(BPartnerId)} for
	 * out-of-trx committed-state checks.
	 */
	public boolean hasActiveDeliveryStopFor(@NonNull final BPartnerId billBPartnerId)
	{
		return queryBL.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, billBPartnerId.getRepoId())
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsDeliveryStop, true)
				.create()
				.anyMatch();
	}

	/**
	 * Returns the id of the first active delivery-stop constraint for the given Bill-BPartner.
	 * <p>
	 * Out-of-transaction — suitable for order-completion checks against committed state.
	 * Uses {@link PlainContextAware#newOutOfTrx()} so it reads committed rows; use
	 * {@link #hasActiveDeliveryStopFor(BPartnerId)} from inside a model interceptor that
	 * needs to see the in-flight constraint row.
	 * <p>
	 * Not annotated with {@code @Cached}: the lookup is a single indexed query
	 * ({@code M_Shipment_Constraint(Bill_BPartner_ID)} index from the gh#28631 migration)
	 * and the host class is a {@code @Repository}, not an {@code IService} — and
	 * {@code @Cached} requires the latter.
	 */
	public Optional<ShipmentConstraintId> getDeliveryStopConstraintIdFor(@NonNull final BPartnerId billBPartnerId)
	{
		final int repoId = queryBL.createQueryBuilder(I_M_Shipment_Constraint.class, PlainContextAware.newOutOfTrx())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, billBPartnerId.getRepoId())
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMN_IsDeliveryStop, true)
				.orderBy()
				.addColumn(I_M_Shipment_Constraint.COLUMN_M_Shipment_Constraint_ID)
				.endOrderBy()
				.create()
				.firstId();
		return Optional.ofNullable(ShipmentConstraintId.ofRepoIdOrNull(repoId));
	}

	/**
	 * Persists the constraint. Returns a copy with {@link ShipmentConstraint#getId()} populated.
	 */
	public ShipmentConstraint save(@NonNull final ShipmentConstraint constraint)
	{
		final I_M_Shipment_Constraint record = constraint.getId() != null
				? InterfaceWrapperHelper.load(constraint.getId().getRepoId(), I_M_Shipment_Constraint.class)
				: InterfaceWrapperHelper.newInstance(I_M_Shipment_Constraint.class);

		record.setAD_Org_ID(constraint.getOrgId().getRepoId());
		record.setBill_BPartner_ID(constraint.getBillBPartnerId().getRepoId());
		record.setIsActive(constraint.isActive());
		record.setIsDeliveryStop(constraint.isDeliveryStop());
		record.setIsManual(constraint.isManual());
		record.setDeliveryStopReason(constraint.getDeliveryStopReason());

		final SourceDocRef sourceDocRef = constraint.getSourceDocRef();
		if (sourceDocRef != null)
		{
			record.setSourceDoc_Table_ID(sourceDocRef.getAD_Table_ID());
			record.setSourceDoc_Record_ID(sourceDocRef.getRecord_ID());
		}

		InterfaceWrapperHelper.save(record);
		return constraint.withId(ShipmentConstraintId.ofRepoId(record.getM_Shipment_Constraint_ID()));
	}

	/**
	 * Bulk-updates {@code M_ReceiptSchedule.IsDeliveryStop} for all unprocessed receipt
	 * schedules of the given BPartner whose flag currently differs from {@code isDeliveryStop}.
	 *
	 * @return number of receipt schedules updated
	 */
	public int updateReceiptScheduleDeliveryStop(@NonNull final BPartnerId billBPartnerId, final boolean isDeliveryStop)
	{
		return queryBL.createQueryBuilder(I_M_ReceiptSchedule.class)
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_C_BPartner_ID, billBPartnerId.getRepoId())
				.addEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_Processed, false)
				.addNotEqualsFilter(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
				.create()
				.updateDirectly()
				.addSetColumnValue(I_M_ReceiptSchedule.COLUMNNAME_IsDeliveryStop, isDeliveryStop)
				.execute();
	}

	private static ShipmentConstraint toDomain(@NonNull final I_M_Shipment_Constraint record)
	{
		final ShipmentConstraint.ShipmentConstraintBuilder builder = ShipmentConstraint.builder()
				.id(ShipmentConstraintId.ofRepoId(record.getM_Shipment_Constraint_ID()))
				.orgId(OrgId.ofRepoId(record.getAD_Org_ID()))
				.billBPartnerId(BPartnerId.ofRepoId(record.getBill_BPartner_ID()))
				.active(record.isActive())
				.deliveryStop(record.isDeliveryStop())
				.manual(record.isManual())
				.deliveryStopReason(record.getDeliveryStopReason());

		final int sourceTableId = record.getSourceDoc_Table_ID();
		final int sourceRecordId = record.getSourceDoc_Record_ID();
		if (sourceTableId > 0 && sourceRecordId > 0)
		{
			builder.sourceDocRef(SourceDocRef.of(TableRecordReference.of(sourceTableId, sourceRecordId)));
		}

		return builder.build();
	}
}
