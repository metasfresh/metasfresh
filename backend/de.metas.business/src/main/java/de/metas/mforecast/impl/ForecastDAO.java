package de.metas.mforecast.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.marketing.base.model.CampaignId;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.ForecastRequest.ForecastLineRequest;
import de.metas.mforecast.IForecastDAO;
import de.metas.pricing.PriceListId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.impl.ActiveRecordQueryFilter;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.I_M_ForecastLine;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class ForecastDAO implements IForecastDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	@NonNull
	public Stream<I_M_Forecast> streamRecordsByIds(@NonNull final ImmutableSet<ForecastId> ids)
	{
		if (ids.isEmpty())
		{
			return Stream.empty();
		}

		return queryBL.createQueryBuilder(I_M_Forecast.class)
				.addInArrayFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, ids)
				.create()
				.stream(I_M_Forecast.class);
	}

	@Override
	@NonNull
	public List<I_M_ForecastLine> retrieveLinesByForecastId(@NonNull final ForecastId forecastId)
	{
		return queryBL.createQueryBuilder(I_M_ForecastLine.class)
				.addEqualsFilter(I_M_ForecastLine.COLUMNNAME_M_Forecast_ID, forecastId)
				.filter(ActiveRecordQueryFilter.getInstance(I_M_ForecastLine.class))
				.orderBy(I_M_ForecastLine.COLUMNNAME_M_ForecastLine_ID)
				.create()
				.list();
	}

	@Override
	public I_M_ForecastLine getForecastLineById(final int forecastLineRecordId)
	{
		return InterfaceWrapperHelper.load(forecastLineRecordId, I_M_ForecastLine.class);
	}

	@Override
	@NonNull
	public ForecastId createForecast(@NonNull final ForecastRequest request)
	{
		final I_M_Forecast forecastRecord = InterfaceWrapperHelper.newInstance(I_M_Forecast.class);

		forecastRecord.setM_Warehouse_ID(request.getWarehouseId().getRepoId());
		forecastRecord.setDatePromised(TimeUtil.asTimestamp(request.getDatePromised()));
		forecastRecord.setName(StringUtils.trimBlankToNull(request.getName()));
		forecastRecord.setC_BPartner_ID(BPartnerId.toRepoId(request.getBpartnerId()));
		forecastRecord.setM_PriceList_ID(PriceListId.getRepoId(request.getPriceListId()));
		forecastRecord.setExternalId(StringUtils.trimBlankToNull(request.getExternalId()));

		saveRecord(forecastRecord);

		request.getForecastLineRequests()
				.forEach(lineRequest -> addForecastLine(forecastRecord, lineRequest));

		return ForecastId.ofRepoId(forecastRecord.getM_Forecast_ID());
	}

	@Override
	public void addForecastLine(
			@NonNull final ForecastId forecastId,
			@NonNull final ForecastLineRequest request)
	{
		final I_M_Forecast forecastRecord = getById(forecastId);
		addForecastLine(forecastRecord, request);
	}

	private static void addForecastLine(final I_M_Forecast forecastRecord, final @NotNull ForecastLineRequest request)
	{
		final I_M_ForecastLine forecastLineRecord = InterfaceWrapperHelper.newInstance(I_M_ForecastLine.class);

		forecastLineRecord.setM_Forecast_ID(forecastRecord.getM_Forecast_ID());
		forecastLineRecord.setM_Warehouse_ID(forecastRecord.getM_Warehouse_ID());
		forecastLineRecord.setQty(request.getQuantity().toBigDecimal());
		forecastLineRecord.setC_UOM_ID(request.getQuantity().getUomId().getRepoId());
		forecastLineRecord.setM_Product_ID(request.getProductId().getRepoId());
		forecastLineRecord.setC_Activity_ID(ActivityId.toRepoId(request.getActivityId()));
		forecastLineRecord.setC_Campaign_ID(CampaignId.toRepoId(request.getCampaignId()));
		forecastLineRecord.setC_Project_ID(ProjectId.toRepoId(request.getProjectId()));
		forecastLineRecord.setQtyCalculated(Quantity.toBigDecimal(request.getQuantityCalculated()));

		saveRecord(forecastLineRecord);
	}

	@Override
	public I_M_Forecast getById(@NonNull final ForecastId forecastId)
	{
		final I_M_Forecast forecast = InterfaceWrapperHelper.load(forecastId.getRepoId(), I_M_Forecast.class);
		if (forecast == null)
		{
			throw new AdempiereException("@NotFound@: " + forecastId);
		}
		return forecast;
	}

	@Override
	public void save(@NonNull final I_M_Forecast forecastRecord)
	{
		saveRecord(forecastRecord);
	}
}
