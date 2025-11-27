/*
 * #%L
 * de.metas.picking.rest-api
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

package de.metas.handlingunits.picking.job.model;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.handlingunits.picking.config.mobileui.PickingJobAggregationType;
import de.metas.i18n.ITranslatableString;
import de.metas.order.OrderId;
import de.metas.organization.InstantAndOrgId;
import de.metas.picking.api.ShipmentScheduleAndJobScheduleIdSet;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.OptionalBoolean;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import lombok.With;
import org.adempiere.warehouse.WarehouseTypeId;

import javax.annotation.Nullable;
import java.util.Set;

@Value
@Builder
public class PickingJobCandidate
{
	@NonNull PickingJobAggregationType aggregationType;
	@Nullable InstantAndOrgId preparationDate;
	@Nullable String salesOrderDocumentNo;
	@Nullable OrderId salesOrderId;
	@Nullable String customerName;
	@Nullable BPartnerLocationId deliveryBPLocationId;
	@Nullable WarehouseTypeId warehouseTypeId;
	boolean partiallyPickedBefore;
	@Nullable BPartnerLocationId handoverLocationId;
	@NonNull @With PickingJobCandidateProducts products;
	@Nullable ShipmentScheduleAndJobScheduleIdSet scheduleIds;

	public Set<ProductId> getProductIds() {return products.getProductIds();}

	public OptionalBoolean hasQtyAvailableToPick()
	{
		return products.hasQtyAvailableToPick();
	}

	@Nullable
	public ProductId getProductId()
	{
		return products.getSingleProductIdOrNull();
	}

	public ITranslatableString getProductName()
	{
		return products.getSingleProductNameOrNull();
	}

	@Nullable
	public Quantity getQtyToDeliver()
	{
		return products.getSingleQtyToDeliverOrNull();
	}

	@Nullable
	public Quantity getQtyAvailableToPick()
	{
		return products.getSingleQtyAvailableToPickOrNull();
	}
}
