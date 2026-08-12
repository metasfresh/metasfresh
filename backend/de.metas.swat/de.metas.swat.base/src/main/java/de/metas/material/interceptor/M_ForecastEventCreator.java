package de.metas.material.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.material.event.ModelProductDescriptorExtractor;
import de.metas.material.event.commons.EventDescriptor;
import de.metas.material.event.commons.MaterialDescriptor;
import de.metas.material.event.commons.ProductDescriptor;
import de.metas.material.event.forecast.Forecast;
import de.metas.material.event.forecast.ForecastCreatedEvent;
import de.metas.material.event.forecast.ForecastDeletedEvent;
import de.metas.material.event.forecast.ForecastLine;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.IProductPlanningDAO.ProductPlanningQuery;
import de.metas.material.planning.ProductPlanning;
import de.metas.mforecast.IForecastDAO;
import de.metas.mforecast.impl.ForecastId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.DocTimingType;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.util.TimeUtil;

import java.util.List;
import java.util.Optional;

/*
 * #%L
 * de.metas.swat.base
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

public class M_ForecastEventCreator
{
	private final IForecastDAO forecastsRepo = Services.get(IForecastDAO.class);
	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);

	private final ModelProductDescriptorExtractor productDescriptorFactory;

	public M_ForecastEventCreator(@NonNull final ModelProductDescriptorExtractor productDescriptorFactory)
	{
		this.productDescriptorFactory = productDescriptorFactory;
	}

	public Optional<ForecastCreatedEvent> createEventWithLinesAndTiming(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final DocTimingType timing)
	{
		return buildForecast(forecastRecord, timing)
				.map(forecast -> ForecastCreatedEvent
						.builder()
						.forecast(forecast)
						.eventDescriptor(EventDescriptor.ofClientAndOrg(forecastRecord.getAD_Client_ID(), forecastRecord.getAD_Org_ID()))
						.build());
	}

	public Optional<ForecastDeletedEvent> createDeletedEvent(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final DocTimingType timing)
	{
		return buildForecast(forecastRecord, timing)
				.map(forecast -> ForecastDeletedEvent
						.builder()
						.forecast(forecast)
						.eventDescriptor(EventDescriptor.ofClientAndOrg(forecastRecord.getAD_Client_ID(), forecastRecord.getAD_Org_ID()))
						.build());
	}

	@NonNull
	private Optional<Forecast> buildForecast(
			@NonNull final I_M_Forecast forecastRecord,
			@NonNull final DocTimingType timing)
	{
		final List<I_M_ForecastLine> forecastLineRecords = forecastsRepo
				.retrieveLinesByForecastId(ForecastId.ofRepoId(forecastRecord.getM_Forecast_ID()));

		if (forecastLineRecords.isEmpty())
		{
			return Optional.empty();
		}

		return Optional.of(Forecast.builder()
								   .forecastId(forecastRecord.getM_Forecast_ID())
								   .docStatus(timing.getDocStatus())
								   .forecastLines(forecastLineRecords.stream()
														  .map(line -> createForecastLine(line, forecastRecord))
														  .collect(ImmutableList.toImmutableList()))
								   .build());
	}

	private ForecastLine createForecastLine(
			@NonNull final I_M_ForecastLine forecastLine,
			@NonNull final I_M_Forecast forecast)
	{
		final ProductDescriptor productDescriptor = createProductDescriptorWithPlanningFallback(forecastLine);

		final BPartnerId customerId = BPartnerId.ofRepoIdOrNull(CoalesceUtil.firstGreaterThanZero(forecastLine.getC_BPartner_ID(), forecast.getC_BPartner_ID()));

		final MaterialDescriptor materialDescriptor = MaterialDescriptor.builder()
				.date(TimeUtil.asInstant(forecastLine.getDatePromised()))
				.productDescriptor(productDescriptor)
				.customerId(customerId)
				.warehouseId(WarehouseId.ofRepoId(forecastLine.getM_Warehouse_ID()))
				.quantity(forecastLine.getQty())
				.build();

		return ForecastLine.builder()
				.forecastLineId(forecastLine.getM_ForecastLine_ID())
				.materialDescriptor(materialDescriptor)
				.build();
	}

	private ProductDescriptor createProductDescriptorWithPlanningFallback(@NonNull final I_M_ForecastLine forecastLine)
	{
		final AttributeSetInstanceId forecastAsiId = AttributeSetInstanceId
				.ofRepoIdOrNone(forecastLine.getM_AttributeSetInstance_ID());

		if (!forecastAsiId.isNone())
		{
			// forecast line has explicit ASI — use it directly, no enrichment needed
			return productDescriptorFactory.createProductDescriptor(forecastLine);
		}

		// forecast line has no ASI — look up PP_Product_Planning for a fallback ASI
		final AttributeSetInstanceId planningAsiId = resolvePlanningAsiId(forecastLine, forecastAsiId);
		if (!planningAsiId.isNone())
		{
			return productDescriptorFactory.createProductDescriptor(
					forecastLine.getM_Product_ID(), planningAsiId);
		}

		// no ASI anywhere — default NONE behavior
		return productDescriptorFactory.createProductDescriptor(forecastLine);
	}

	private AttributeSetInstanceId resolvePlanningAsiId(
			@NonNull final I_M_ForecastLine forecastLine,
			@NonNull final AttributeSetInstanceId forecastAsiId)
	{
		final ProductPlanningQuery query = ProductPlanningQuery.builder()
				.productId(ProductId.ofRepoId(forecastLine.getM_Product_ID()))
				.warehouseId(WarehouseId.ofRepoIdOrNull(forecastLine.getM_Warehouse_ID()))
				.orgId(OrgId.ofRepoId(forecastLine.getAD_Org_ID()))
				.attributeSetInstanceId(forecastAsiId)
				.build();

		return productPlanningDAO.find(query)
				.map(ProductPlanning::getAttributeSetInstanceId)
				.filter(asiId -> !asiId.isNone())
				.orElse(AttributeSetInstanceId.NONE);
	}
}
