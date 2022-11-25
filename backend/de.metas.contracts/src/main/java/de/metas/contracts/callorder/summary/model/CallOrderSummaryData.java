/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.contracts.callorder.summary.model;

import de.metas.contracts.FlatrateTermId;
import de.metas.lang.SOTrx;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.mm.attributes.AttributeSetInstanceId;

import javax.annotation.Nullable;
import java.math.BigDecimal;

@Value
@Builder(toBuilder = true)
public class CallOrderSummaryData
{
	@NonNull
	OrderId orderId;

	@NonNull
	OrderLineId orderLineId;

	@NonNull
	FlatrateTermId flatrateTermId;

	@NonNull
	ProductId productId;

	@NonNull
	UomId uomId;

	@NonNull
	BigDecimal qtyEntered;

	@With
	@NonNull
	@Builder.Default
	BigDecimal qtyDelivered = BigDecimal.ZERO;

	@With
	@NonNull
	@Builder.Default
	BigDecimal qtyInvoiced = BigDecimal.ZERO;

	@Nullable
	String poReference;

	@Nullable
	AttributeSetInstanceId attributeSetInstanceId;

	boolean isActive;

	@NonNull
	SOTrx soTrx;
}
