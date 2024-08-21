/*
 * #%L
 * de.metas.manufacturing.rest-api
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

package de.metas.manufacturing.rest_api.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableList;
import de.metas.common.manufacturing.v2.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.v2.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrder;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrderBOMLine;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.v2.JsonResponseManufacturingOrdersReport;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.manufacturing.rest_api.ExportSequenceNumberProvider;
import de.metas.manufacturing.rest_api.ManufacturingOrderExportAuditRepository;
import de.metas.manufacturing.rest_api.ManufacturingOrderReportAuditRepository;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.material.planning.pporder.IPPOrderBOMDAO;
import de.metas.organization.IOrgDAO;
import de.metas.product.ProductRepository;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.eevolution.api.IPPOrderDAO;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
class ManufacturingOrderAPIService
{
	private final ManufacturingOrderExportAuditRepository orderExportAuditRepo;
	private final ManufacturingOrderReportAuditRepository orderReportAuditRepo;
	private final ProductRepository productRepo;
	private final ObjectMapper jsonObjectMapper;
	private final HUReservationService huReservationService;
	private final ExportSequenceNumberProvider exportSequenceNumberProvider;

	private final IPPOrderDAO ppOrderDAO = Services.get(IPPOrderDAO.class);
	private final IPPOrderBOMBL ppOrderBOMBL = Services.get(IPPOrderBOMBL.class);
	private final IPPOrderBOMDAO ppOrderBOMDAO = Services.get(IPPOrderBOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Builder
	public ManufacturingOrderAPIService(
			@NonNull final ManufacturingOrderExportAuditRepository orderExportAuditRepo,
			@NonNull final ManufacturingOrderReportAuditRepository orderReportAuditRepo,
			@NonNull final ProductRepository productRepo,
			@NonNull final ObjectMapper jsonObjectMapper,
			@NonNull final HUReservationService huReservationService,
			@NonNull final ExportSequenceNumberProvider exportSequenceNumberProvider)
	{
		this.orderExportAuditRepo = orderExportAuditRepo;
		this.orderReportAuditRepo = orderReportAuditRepo;
		this.productRepo = productRepo;
		this.jsonObjectMapper = jsonObjectMapper;
		this.huReservationService = huReservationService;
		this.exportSequenceNumberProvider = exportSequenceNumberProvider;
	}

	public JsonResponseManufacturingOrdersBulk exportOrders(
			@NonNull final Instant canBeExportedFrom,
			@NonNull final QueryLimit limit,
			@NonNull final String adLanguage)
	{
		final ManufacturingOrdersExportCommand command = ManufacturingOrdersExportCommand.builder()
				.orderAuditRepo(orderExportAuditRepo)
				.productRepo(productRepo)
				.exportSequenceNumberProvider(exportSequenceNumberProvider)
				//
				.canBeExportedFrom(canBeExportedFrom)
				.limit(limit)
				.adLanguage(adLanguage)
				//
				.build();

		return command.execute();
	}

	public void setExportStatus(@NonNull final JsonRequestSetOrdersExportStatusBulk request)
	{
		final ManufacturingOrdersSetExportStatusCommand command = ManufacturingOrdersSetExportStatusCommand.builder()
				.orderAuditRepo(orderExportAuditRepo)
				.jsonObjectMapper(jsonObjectMapper)
				//
				.request(request)
				//
				.build();

		command.execute();
	}

	public JsonResponseManufacturingOrdersReport report(@NonNull final JsonRequestManufacturingOrdersReport request)
	{
		final ManufacturingOrderReportProcessCommand command = ManufacturingOrderReportProcessCommand.builder()
				.huReservationService(huReservationService)
				.auditRepository(orderReportAuditRepo)
				.jsonObjectMapper(jsonObjectMapper)
				//
				.request(request)
				//
				.build();

		return command.execute();
	}

	@NonNull
	public JsonResponseManufacturingOrder retrievePPOrder(@NonNull final PPOrderId ppOrderId)
	{
		final I_PP_Order ppOrder = ppOrderDAO.getById(ppOrderId);

		final ImmutableList<JsonResponseManufacturingOrderBOMLine> bomLinesResponse = retrieveBomLines(ppOrderId);

		final MapToJsonResponseManufacturingOrderRequest request = MapToJsonResponseManufacturingOrderRequest
				.builder()
				.components(bomLinesResponse)
				.order(ppOrder)
				.ppOrderBOMBL(ppOrderBOMBL)
				.orgDAO(orgDAO)
				.productRepository(productRepo)
				.build();

		return JsonConverter.toJson(request);
	}

	@NonNull
	public ImmutableList<JsonResponseManufacturingOrderBOMLine> retrieveBomLines(@NonNull final PPOrderId ppOrderId)
	{
		return ppOrderBOMDAO.retrieveOrderBOMLines(ppOrderId)
				.stream()
				.map(bomLine -> JsonConverter.toJson(bomLine, ppOrderBOMBL, productRepo))
				.collect(ImmutableList.toImmutableList());
	}
}