/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationAndCaptureId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.location.DocumentLocation;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.order.InvoiceRule;
import de.metas.organization.OrgId;
import de.metas.pricing.PricingSystemId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.user.UserId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;
import java.time.Instant;

@Value
@Builder
public class FlatrateTerm
{
	@NonNull FlatrateTermId id;

	@NonNull OrgId orgId;

	@NonNull BPartnerLocationAndCaptureId  billPartnerLocationAndCaptureId;

	/**
	 * May be {@code null} for somesorts of contracts
	 */
	@Nullable BPartnerLocationAndCaptureId  dropshipPartnerLocationAndCaptureId;

	@Nullable
	ProductId productId;

	@Nullable
	UserId userInChargeId;

	boolean isSimulation;

	@NonNull 
	ConditionsId flatrateConditionsId;

	/**
	 * Null, unless the underlying C_Flatrate_conditions have a pricingsystem set.
	 */
	@Nullable
	PricingSystemId pricingSystemId;

	@Nullable
	Instant startDate;

	@Nullable
	Instant masterStartDate;

	@Nullable
	Instant endDate;
	@Nullable
	Instant masterEndDate;

	@Nullable
	FlatrateTermStatus status;

	@Nullable
	Quantity plannedQtyPerUnit;

	@Nullable
	DeliveryRule deliveryRule;

	@Nullable
	DeliveryViaRule deliveryViaRule;

	@NonNull
	InvoiceRule invoiceRule;

	@NonNull
	DocumentLocation billLocation;

	public BPartnerId getShipOrBillPartnerId()
	{
		// if the billto and shipto partners differ, then the shipto-partner is generally the one with the departments to which stuff is shipped
		return CoalesceUtil.coalesceSuppliersNotNull(
				() -> getDropshipPartnerLocationAndCaptureId() != null ? getDropshipPartnerLocationAndCaptureId().getBpartnerId() : null,
				() -> getBillLocation().getBpartnerId());
	}

}
