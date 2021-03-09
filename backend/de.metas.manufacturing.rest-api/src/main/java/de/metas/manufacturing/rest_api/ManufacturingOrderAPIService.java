package de.metas.manufacturing.rest_api;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.common.manufacturing.JsonRequestManufacturingOrdersReport;
import de.metas.common.manufacturing.JsonRequestSetOrdersExportStatusBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersBulk;
import de.metas.common.manufacturing.JsonResponseManufacturingOrdersReport;
import de.metas.handlingunits.reservation.HUReservationService;
import de.metas.product.ProductRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.springframework.stereotype.Service;

import java.time.Instant;

/*
 * #%L
 * de.metas.manufacturing.rest-api
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

@Service
class ManufacturingOrderAPIService
{
	private final ManufacturingOrderExportAuditRepository orderExportAuditRepo;
	private final ManufacturingOrderReportAuditRepository orderReportAuditRepo;
	private final ProductRepository productRepo;
	private final ObjectMapper jsonObjectMapper;
	private final HUReservationService huReservationService;
	private final ExportSequenceNumberProvider exportSequenceNumberProvider;

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

}
