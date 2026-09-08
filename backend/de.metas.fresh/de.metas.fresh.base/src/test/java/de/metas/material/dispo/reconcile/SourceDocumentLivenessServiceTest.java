package de.metas.material.dispo.reconcile;

/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.document.engine.DocStatus;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.material.dispo.commons.candidate.Candidate;
import de.metas.material.dispo.commons.candidate.CandidateBusinessCase;
import de.metas.material.dispo.commons.candidate.CandidateType;
import de.metas.material.dispo.commons.candidate.businesscase.BusinessCaseDetail;
import de.metas.material.dispo.commons.candidate.businesscase.DemandDetail;
import de.metas.material.dispo.commons.candidate.businesscase.Flag;
import de.metas.material.dispo.commons.candidate.businesscase.ProductionDetail;
import de.metas.material.dispo.commons.reconcile.SourceDocumentStatus;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.pporder.PPOrderRef;
import de.metas.organization.ClientAndOrgId;
import de.metas.product.ResourceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.util.Env;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;

public class SourceDocumentLivenessServiceTest
{
	private static final Instant DATE_OF_CANDIDATE = Instant.parse("2026-09-08T00:00:00Z");
	private static final int PRODUCT_ID = 1000001;
	private static final WarehouseId WAREHOUSE_ID = WarehouseId.ofRepoId(1000002);

	private SourceDocumentLivenessService livenessService;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		livenessService = SourceDocumentLivenessService.newInstanceForUnitTesting();
	}

	@Test
	public void shipmentDemandWithOpenShipmentSchedule_isStillOpen()
	{
		final int shipmentScheduleId = createShipmentSchedule(false /* processed */, true /* active */);
		final Candidate candidate = demandCandidate(
				CandidateBusinessCase.SHIPMENT,
				DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, -1, -1, BigDecimal.TEN));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.STILL_OPEN);
	}

	@Test
	public void shipmentDemandWithProcessedShipmentSchedule_isClosed()
	{
		final int shipmentScheduleId = createShipmentSchedule(true /* processed */, true /* active */);
		final Candidate candidate = demandCandidate(
				CandidateBusinessCase.SHIPMENT,
				DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, -1, -1, BigDecimal.TEN));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.CLOSED);
	}

	@Test
	public void productionDemandWithClosedPPOrder_isClosed()
	{
		final int ppOrderId = createPPOrder(DocStatus.Closed);
		final Candidate candidate = demandCandidate(CandidateBusinessCase.PRODUCTION, productionDetail(ppOrderId));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.CLOSED);
	}

	@Test
	public void productionDemandWithCompletedPPOrder_isStillOpen()
	{
		final int ppOrderId = createPPOrder(DocStatus.Completed);
		final Candidate candidate = demandCandidate(CandidateBusinessCase.PRODUCTION, productionDetail(ppOrderId));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.STILL_OPEN);
	}

	@Test
	public void forecastDemandWithOpenForecast_isStillOpen()
	{
		final int forecastLineId = createForecastLine(DocStatus.Completed);
		final Candidate candidate = demandCandidate(
				CandidateBusinessCase.FORECAST,
				DemandDetail.forForecastLineId(forecastLineId, -1 /* forecastId */, BigDecimal.TEN));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.STILL_OPEN);
	}

	@Test
	public void forecastDemandWithClosedForecast_isClosed()
	{
		final int forecastLineId = createForecastLine(DocStatus.Closed);
		final Candidate candidate = demandCandidate(
				CandidateBusinessCase.FORECAST,
				DemandDetail.forForecastLineId(forecastLineId, -1 /* forecastId */, BigDecimal.TEN));

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.CLOSED);
	}

	@Test
	public void candidateWithoutAnyDetail_hasNoSourceDocument()
	{
		final Candidate candidate = demandCandidate(CandidateBusinessCase.SHIPMENT, null);

		assertThat(livenessService.getStatus(candidate)).isEqualTo(SourceDocumentStatus.NO_SOURCE_DOCUMENT);
	}

	@Test
	public void openShipmentScheduleDatedBeforeTheLivenessCutoff_isClosed()
	{
		final int shipmentScheduleId = createShipmentSchedule(false /* processed */, true /* active */);
		final Candidate candidate = demandCandidate(
				CandidateBusinessCase.SHIPMENT,
				DemandDetail.forShipmentScheduleIdAndOrderLineId(shipmentScheduleId, -1, -1, BigDecimal.TEN));
		final Instant livenessCutoff = DATE_OF_CANDIDATE.plusSeconds(1);

		assertThat(livenessService.getStatus(candidate, livenessCutoff)).isEqualTo(SourceDocumentStatus.CLOSED);
		assertThat(livenessService.getStatus(candidate, DATE_OF_CANDIDATE)).isEqualTo(SourceDocumentStatus.STILL_OPEN);
	}

	private int createShipmentSchedule(final boolean processed, final boolean active)
	{
		final I_M_ShipmentSchedule record = InterfaceWrapperHelper.newInstance(I_M_ShipmentSchedule.class);
		record.setProcessed(processed);
		record.setIsActive(active);
		InterfaceWrapperHelper.save(record);
		return record.getM_ShipmentSchedule_ID();
	}

	/**
	 * Note that the created line's {@code M_Forecast_ID} is the ONLY way to reach the forecast document
	 * here: the tests deliberately leave {@code DemandDetail.forecastId} unset, so the service has to walk
	 * {@code M_ForecastLine.M_Forecast_ID} rather than short-cutting via the detail.
	 */
	private int createForecastLine(final DocStatus docStatus)
	{
		final I_M_Forecast forecast = InterfaceWrapperHelper.newInstance(I_M_Forecast.class);
		forecast.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.save(forecast);

		final I_M_ForecastLine forecastLine = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class);
		forecastLine.setM_Forecast_ID(forecast.getM_Forecast_ID());
		InterfaceWrapperHelper.save(forecastLine);

		return forecastLine.getM_ForecastLine_ID();
	}

	private int createPPOrder(final DocStatus docStatus)
	{
		final I_PP_Order record = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		record.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.save(record);
		return record.getPP_Order_ID();
	}

	private static ProductionDetail productionDetail(final int ppOrderId)
	{
		return ProductionDetail.builder()
				.plantId(ResourceId.ofRepoId(1))
				.productBomLineId(1)
				.description("test")
				.ppOrderRef(PPOrderRef.ofPPOrderId(ppOrderId))
				.ppOrderDocStatus(DocStatus.Completed)
				.advised(Flag.FALSE)
				.pickDirectlyIfFeasible(Flag.FALSE)
				.qty(BigDecimal.TEN)
				.build();
	}

	private static Candidate demandCandidate(
			final CandidateBusinessCase businessCase,
			@Nullable final BusinessCaseDetail businessCaseDetail)
	{
		return Candidate.builder()
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(Env.getAD_Client_ID(Env.getCtx()), 1000000))
				.type(CandidateType.DEMAND)
				.businessCase(businessCase)
				.businessCaseDetail(businessCaseDetail)
				.materialDescriptor(MaterialDescriptor.builder()
						.date(DATE_OF_CANDIDATE)
						.productDescriptor(ProductDescriptor.completeForProductIdAndEmptyAttribute(PRODUCT_ID))
						.warehouseId(WAREHOUSE_ID)
						.quantity(BigDecimal.TEN)
						.build())
				.build();
	}
}
