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
import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.DeliveryRule;
import de.metas.order.DeliveryViaRule;
import de.metas.organization.OrgId;
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
	@NonNull FlatrateTermId flatrateTermId;

	@NonNull OrgId orgId;

	@NonNull BPartnerId billPartnerID;

	@NonNull
	private BPartnerLocationId billLocationId;

	@Nullable
	private BPartnerId shipToBPartnerId;

	@Nullable
	private BPartnerLocationId shipToLocationId;

	@Nullable
	private ProductId productId;

	@Nullable
	private UserId userInChargeId;

	boolean isSimulation;

	@NonNull ConditionsId flatrateConditionsId;

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

}
