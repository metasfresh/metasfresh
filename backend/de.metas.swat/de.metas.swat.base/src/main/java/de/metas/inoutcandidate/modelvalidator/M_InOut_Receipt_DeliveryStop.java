package de.metas.inoutcandidate.modelvalidator;

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

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.AdMessageKey;
import de.metas.inoutcandidate.ShipmentConstraintId;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintService;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;
import de.metas.inout.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Layer 4 (catch-all) of the delivery-stop receipt guard.
 *
 * <p>Fires {@code BEFORE_PREPARE} on every {@code M_InOut} that is a receipt
 * ({@code IsSOTrx='N'}). If the vendor has an active delivery stop, the receipt
 * is rejected with a clear, BPartner-named error — regardless of how the
 * receipt was created (WebUI, mobile UI, API, background process).
 *
 * <p>Mirrors the structure of {@link C_Order#assertNotDeliveryStopped} for the
 * purchase-order side. The throw site is intentionally kept identical.
 *
 * <p>gh#28631
 */
@Interceptor(I_M_InOut.class)
@Component
public class M_InOut_Receipt_DeliveryStop
{
	private static final AdMessageKey MSG_CannotReceive_DeliveryStop_Single = AdMessageKey.of("CannotReceive_DeliveryStop_Single");

	// ShipmentConstraintService is a Spring @Service; this interceptor is registered
	// as a Spring bean (see SwatValidator / ReceiptScheduleValidator), so injection
	// via SpringContextHolder is the correct pattern used throughout this package.
	private final ShipmentConstraintService shipmentConstraintService = SpringContextHolder.instance.getBean(ShipmentConstraintService.class);

	@DocValidate(timings = ModelValidator.TIMING_BEFORE_PREPARE)
	public void assertVendorNotDeliveryStopped(@NonNull final I_M_InOut inout)
	{
		// Only receipts (purchase side) — skip shipments
		if (inout.isSOTrx())
		{
			return;
		}

		// Check the vendor BPartner for a delivery stop
		final BPartnerId vendorId = BPartnerId.ofRepoIdOrNull(inout.getC_BPartner_ID());
		if (vendorId == null)
		{
			return;
		}

		final Optional<ShipmentConstraintId> constraintId = shipmentConstraintService.getDeliveryStopConstraintIdFor(vendorId);
		if (constraintId.isPresent())
		{
			throw new AdempiereException(MSG_CannotReceive_DeliveryStop_Single, vendorId.getRepoId(), constraintId.get().getRepoId())
					.markAsUserValidationError();
		}
	}
}
