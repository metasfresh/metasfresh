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
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Language;
import de.metas.inoutcandidate.ShipmentConstraintId;
import de.metas.organization.OrgId;
import lombok.NonNull;
import org.springframework.stereotype.Service;

import javax.annotation.Nullable;
import java.util.Optional;

/**
 * Business operations on {@link ShipmentConstraint} — composes
 * {@link ShipmentConstraintRepository} into the high-level actions the
 * delivery / order stop feature needs.
 * <p>
 * Replaces {@code IShipmentConstraintsBL}. All ints crossing the boundary are
 * typed as {@code RepoIdAware} (BPartnerId, OrgId, ShipmentConstraintId).
 */
@Service
public class ShipmentConstraintService
{
	private final ShipmentConstraintRepository repository;

	public ShipmentConstraintService(@NonNull final ShipmentConstraintRepository repository)
	{
		this.repository = repository;
	}

	/**
	 * Create a fresh constraint row from a source document (e.g. a dunning doc).
	 * Returns the persisted id.
	 */
	public ShipmentConstraintId createConstraint(@NonNull final ShipmentConstraintCreateCommand command)
	{
		final ShipmentConstraint constraint = ShipmentConstraint.builder()
				.orgId(command.getOrgId())
				.billBPartnerId(command.getBillBPartnerId())
				.active(true)
				.deliveryStop(command.isDeliveryStop())
				.manual(false)
				.deliveryStopReason(resolveReason(command.getReason()))
				.sourceDocRef(command.getSourceDocRef())
				.build();
		return repository.save(constraint).getId();
	}

	/**
	 * Creates or re-activates the manual delivery-stop constraint mirroring the BPartner's flag.
	 * - If a manual constraint row exists: update reason and (re)activate it.
	 * - Otherwise create a new manual constraint row.
	 */
	public void createOrUpdateManualDeliveryStop(
			@NonNull final BPartnerId billBPartnerId,
			@NonNull final OrgId orgId,
			@Nullable final String reason)
	{
		final Optional<ShipmentConstraint> existing = repository.getManualForBPartner(billBPartnerId);

		if (existing.isPresent())
		{
			final ShipmentConstraint updated = existing.get().toBuilder()
					.active(true)
					.deliveryStopReason(reason)
					.build();
			repository.save(updated);
		}
		else
		{
			final ShipmentConstraint fresh = ShipmentConstraint.builder()
					.orgId(orgId)
					.billBPartnerId(billBPartnerId)
					.active(true)
					.deliveryStop(true)
					.manual(true)
					.deliveryStopReason(reason)
					.build();
			repository.save(fresh);
		}
	}

	/**
	 * Deactivates the manual delivery-stop constraint for the given BPartner (no-op if not present
	 * or already inactive).
	 */
	public void deactivateManualDeliveryStop(@NonNull final BPartnerId billBPartnerId)
	{
		repository.getManualForBPartner(billBPartnerId)
				.filter(ShipmentConstraint::isActive)
				.ifPresent(c -> repository.save(c.toBuilder().active(false).build()));
	}

	/**
	 * Whether at least one active delivery-stop constraint exists for the given Bill-BPartner
	 * (committed state, cached). Use this for order-completion checks.
	 */
	public Optional<ShipmentConstraintId> getDeliveryStopConstraintIdFor(@NonNull final BPartnerId billBPartnerId)
	{
		return repository.getDeliveryStopConstraintIdFor(billBPartnerId);
	}

	@Nullable
	private static String resolveReason(@Nullable final ITranslatableString reason)
	{
		if (reason == null)
		{
			return null;
		}
		// Persist the German base-language rendering — the Shipment-Restrictions window
		// is German and DeliveryStopReason is a plain VARCHAR.
		return reason.translate(Language.getBaseAD_Language());
	}
}
