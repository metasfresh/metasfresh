/*
 * #%L
 * metasfresh-material-dispo-service
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

package de.metas.material.dispo.service.simulation;

import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.logging.LogManager;
import de.metas.material.event.MaterialEventObserver;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.PostMaterialEventService;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.OrderLineDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.simulation.DeactivateAllSimulatedCandidatesEvent;
import de.metas.material.event.simulation.SimulatedDemandCreatedEvent;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderLineId;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class ProductionSimulationService
{
	private static final Logger log = LogManager.getLogger(ProductionSimulationService.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);

	private final ModelProductDescriptorExtractor productDescriptorFactory;
	private final PostMaterialEventService postMaterialEventService;
	private final MaterialEventObserver materialEventObserver;

	public ProductionSimulationService(
			@NonNull final ModelProductDescriptorExtractor productDescriptorFactory,
			@NonNull final MaterialEventObserver materialEventObserver,
			@NonNull final PostMaterialEventService postMaterialEventService)
	{
		this.productDescriptorFactory = productDescriptorFactory;
		this.materialEventObserver = materialEventObserver;
		this.postMaterialEventService = postMaterialEventService;
	}

	public void processSimulatedDemand(
			@NonNull final OrderLineId orderLineId,
			@NonNull final I_C_Order salesOrder,
			@NonNull final OrderLineDescriptor orderLineDescriptor)
	{
		final String traceId = UUID.randomUUID().toString();

		materialEventObserver.observe(traceId);

		final MaterialDescriptor materialDescriptor = createMaterialDescriptor(salesOrder, orderLineId);

		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(salesOrder.getAD_Client_ID(), salesOrder.getAD_Org_ID());

		final SimulatedDemandCreatedEvent simulatedDemandCreatedEvent = SimulatedDemandCreatedEvent.builder()
				.eventDescriptor(EventDescriptor.ofClientOrgAndTraceId(clientAndOrgId, traceId))
				.documentLineDescriptor(orderLineDescriptor)
				.materialDescriptor(materialDescriptor)
				.build();

		postMaterialEventService.postEventNow(simulatedDemandCreatedEvent);

		try
		{
			materialEventObserver.awaitProcessing(traceId);
		}
		catch (final Exception exception)
		{
			log.error("Error encountered while awaiting processing for traceId:" + traceId, exception);

			postMaterialEventService.postEventNow(DeactivateAllSimulatedCandidatesEvent.builder()
														  .eventDescriptor(EventDescriptor.ofClientAndOrg(Env.getClientId(), Env.getOrgId()))
														  .build());
		}
	}

	@NonNull
	private MaterialDescriptor createMaterialDescriptor(
			@NonNull final I_C_Order salesOrder,
			@NonNull final OrderLineId orderLineId)
	{
		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId);

		final ZoneId timeZone = orgDAO.getTimeZone(OrgId.ofRepoId(salesOrder.getAD_Org_ID()));

		final ZonedDateTime preparationDate = CoalesceUtil.coalesceSuppliersNotNull(
				() -> TimeUtil.asZonedDateTime(salesOrder.getPreparationDate(), timeZone),
				() -> TimeUtil.asZonedDateTime(salesOrder.getDatePromised(), timeZone));

		final ProductDescriptor productDescriptor = productDescriptorFactory.createProductDescriptor(orderLine);

		return MaterialDescriptor.builder()
				.date(preparationDate.toInstant())
				.productDescriptor(productDescriptor)
				.warehouseId(WarehouseId.ofRepoIdOrNull(orderLine.getM_Warehouse_ID()))
				.customerId(BPartnerId.ofRepoId(orderLine.getC_BPartner_ID()))
				.quantity(orderLine.getQtyOrdered())
				.build();
	}
}
