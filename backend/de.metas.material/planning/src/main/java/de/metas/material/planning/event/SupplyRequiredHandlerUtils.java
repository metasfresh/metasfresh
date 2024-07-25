package de.metas.material.planning.event;

import de.metas.bpartner.BPartnerId;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.organization.IOrgDAO;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
import java.time.ZoneId;

/*
 * #%L
 * metasfresh-material-planning
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

@UtilityClass
public class SupplyRequiredHandlerUtils
{
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	private final MainDataRequestHandler mainDataRequestHandler = new MainDataRequestHandler();

	@NonNull
	public static MaterialRequest mkRequest(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final MaterialPlanningContext context)
	{

		return MaterialRequest.builder()
				.qtyToSupply(getQuantity(supplyRequiredDescriptor))
				.context(context)
				.mrpDemandBPartnerId(BPartnerId.toRepoIdOr(supplyRequiredDescriptor.getCustomerId(), -1))
				.mrpDemandOrderLineSOId(supplyRequiredDescriptor.getOrderLineId())
				.mrpDemandShipmentScheduleId(supplyRequiredDescriptor.getShipmentScheduleId())
				.demandDate(supplyRequiredDescriptor.getDemandDate())
				.isSimulated(supplyRequiredDescriptor.isSimulated())
				.build();
	}

	private static @NonNull Quantity getQuantity(final @NonNull SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		final IProductBL productBL = Services.get(IProductBL.class);

		final ProductId productId = ProductId.ofRepoId(supplyRequiredDescriptor.getProductId());
		final BigDecimal qtyToSupplyBD = supplyRequiredDescriptor.getQtyToSupplyBD();
		final I_C_UOM uom = productBL.getStockUOM(productId);
		return Quantity.of(qtyToSupplyBD, uom);
	}

	public void updateMainData(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
	{
		updateMainDataWithQty(supplyRequiredDescriptor, supplyRequiredDescriptor.getMaterialDescriptor().getQuantity());
	}

	public void updateMainDataWithQty(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor, final BigDecimal qty)
	{
		if (supplyRequiredDescriptor.isSimulated())
		{
			return;
		}

		final ZoneId orgTimezone = orgDAO.getTimeZone(supplyRequiredDescriptor.getEventDescriptor().getOrgId());

		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier
				.createForMaterial(supplyRequiredDescriptor.getMaterialDescriptor(), orgTimezone);

		final UpdateMainDataRequest updateMainDataRequest = UpdateMainDataRequest.builder()
				.identifier(mainDataRecordIdentifier)
				.qtySupplyRequired(qty)
				.build();

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}
}
