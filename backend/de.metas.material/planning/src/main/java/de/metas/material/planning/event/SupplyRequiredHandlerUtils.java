package de.metas.material.planning.event;

import de.metas.bpartner.BPartnerId;
<<<<<<< HEAD
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.MaterialPlanningContext;
=======
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.MaterialPlanningContext;
import de.metas.organization.IOrgDAO;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_UOM;

import java.math.BigDecimal;
<<<<<<< HEAD
=======
import java.time.ZoneId;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
=======
	private final MainDataRequestHandler mainDataRequestHandler = new MainDataRequestHandler();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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
<<<<<<< HEAD
=======

	public void updateQtySupplyRequired(
			@NonNull final MaterialDescriptor materialDescriptor,
			@NonNull final EventDescriptor eventDescriptor,
			@NonNull final BigDecimal qtySupplyRequiredDelta)
	{
		if (qtySupplyRequiredDelta.signum() == 0)
		{
			return;
		}

		final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
		final ZoneId orgTimezone = orgDAO.getTimeZone(eventDescriptor.getOrgId());
		final MainDataRecordIdentifier mainDataRecordIdentifier = MainDataRecordIdentifier.createForMaterial(materialDescriptor, orgTimezone);

		mainDataRequestHandler.handleDataUpdateRequest(
				UpdateMainDataRequest.builder()
						.identifier(mainDataRecordIdentifier)
						.qtySupplyRequired(qtySupplyRequiredDelta)
						.build()
		);
	}

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
}
