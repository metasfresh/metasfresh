/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.handlingunits.inout.impl;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.inout.InOutLineId;
import de.metas.order.OrderId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.mm.attributes.api.CreateAttributeInstanceReq;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Value
@Builder
public class CustomerReturnLineCandidate
{
	@NonNull
	OrgId orgId;

	@NonNull
	ProductId productId;

	@NonNull
	Quantity returnedQty;

	@NonNull
	BPartnerLocationId bPartnerLocationId;

	@NonNull
	ReturnedGoodsWarehouseType returnedGoodsWarehouseType;

	@Nullable
	LocalDate movementDate;

	@Nullable
	ZonedDateTime dateReceived;

	@Nullable
	List<CreateAttributeInstanceReq> createAttributeInstanceReqs;

	@Nullable
	String externalResourceURL;

	@Nullable
	InOutLineId originalShipmentInOutLineId;

	@Nullable
	OrderId orderId;

	@Nullable
	String externalId;

	@Nullable
	HUPIItemProductId hupiItemProductId;

	@Nullable
	BigDecimal returnedTUQty;
}
