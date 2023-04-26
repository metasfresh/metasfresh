package de.metas.material.planning.event;

import de.metas.bpartner.BPartnerId;
import de.metas.material.cockpit.view.MainDataRecordIdentifier;
import de.metas.material.cockpit.view.mainrecord.MainDataRequestHandler;
import de.metas.material.cockpit.view.mainrecord.UpdateMainDataRequest;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.SupplyRequiredDescriptor;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.ddorder.DDOrderAdvisedEventCreator;
import de.metas.material.planning.ppordercandidate.PPOrderCandidateAdvisedEventCreator;
import de.metas.organization.IOrgDAO;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;

import java.math.BigDecimal;
import java.time.ZoneId;

import static org.adempiere.model.InterfaceWrapperHelper.load;

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
	public MaterialRequest mkRequest(
			@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor,
			@NonNull final IMaterialPlanningContext mrpContext)
	{
		final IUOMDAO uomDAO = Services.get(IUOMDAO.class);

		final BPartnerId descriptorBPartnerId = supplyRequiredDescriptor.getMaterialDescriptor().getCustomerId();

		final int productId = supplyRequiredDescriptor.getMaterialDescriptor().getProductId();
		final I_M_Product product = load(productId, I_M_Product.class);

		final BigDecimal qtyToSupply = supplyRequiredDescriptor.getMaterialDescriptor().getQuantity();

		final I_C_UOM uom = uomDAO.getById(product.getC_UOM_ID());

		return MaterialRequest.builder()
				.qtyToSupply(Quantity.of(qtyToSupply, uom))
				.mrpContext(mrpContext)
				.mrpDemandBPartnerId(BPartnerId.toRepoIdOr(descriptorBPartnerId, -1))
				.mrpDemandOrderLineSOId(supplyRequiredDescriptor.getOrderLineId())
				.mrpDemandShipmentScheduleId(supplyRequiredDescriptor.getShipmentScheduleId())
				.demandDate(supplyRequiredDescriptor.getMaterialDescriptor().getDate())
				.isSimulated(supplyRequiredDescriptor.isSimulated())
				.build();
	}

	public void updateMainData(@NonNull final SupplyRequiredDescriptor supplyRequiredDescriptor)
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
				.qtySupplyRequired(supplyRequiredDescriptor.getMaterialDescriptor().getQuantity())
				.build();

		mainDataRequestHandler.handleDataUpdateRequest(updateMainDataRequest);
	}
}
