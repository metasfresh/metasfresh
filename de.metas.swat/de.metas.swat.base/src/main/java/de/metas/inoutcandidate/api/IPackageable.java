package de.metas.inoutcandidate.api;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.order.OrderId;
import de.metas.order.OrderLineId;
import de.metas.product.ProductId;

/**
 * Packaging source; something that will be later packed by picking terminal
 *
 * @author tsa
 *
 */
public interface IPackageable
{
	BPartnerId getBpartnerId();

	String getProductName();

	ProductId getProductId();

	AttributeSetInstanceId getAsiId();

	String getFreightCostRule();

	LocalDateTime getDeliveryDate();

	String getDocumentNo();

	boolean isDisplayed();

	String getShipperName();

	String getDeliveryVia();

	String getWarehouseName();

	String getBpartnerLocationName();

	String getBpartnerName();

	String getBpartnerValue();

	BigDecimal getQtyToDeliver();

	ShipmentScheduleId getShipmentScheduleId();

	BPartnerLocationId getBpartnerLocationId();

	String getBpartnerAddress();

	WarehouseId getWarehouseId();

	OrderId getOrderId();

	OrderLineId getOrderLineIdOrNull();

	int getShipperId();

	String getDocSubType();

	LocalDateTime getPreparationDate();
}
