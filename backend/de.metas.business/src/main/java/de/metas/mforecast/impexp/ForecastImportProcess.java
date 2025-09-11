/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.mforecast.impexp;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.AccountDimension;
import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.ImportRecordsSelection;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.marketing.base.model.CampaignId;
import de.metas.material.event.forecast.Forecast;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.IForecastDAO;
import de.metas.mforecast.impl.ForecastDAO;
import de.metas.mforecast.impl.ForecastId;
import de.metas.pricing.PriceListId;
import de.metas.product.ProductId;
import de.metas.product.acct.api.ActivityId;
import de.metas.project.ProjectId;
import de.metas.quantity.Quantity;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Forecast;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.MAccount;
import org.compiere.model.X_I_Forecast;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.Objects;
import java.util.Properties;

public class ForecastImportProcess extends SimpleImportProcessTemplate<I_I_Forecast>
{
	final private IForecastDAO forecastDAO = Services.get(IForecastDAO.class);
	final private IUOMDAO uomDAO = Services.get(IUOMDAO.class);

	@Override
	protected ImportRecordResult importRecord(
			final @NonNull IMutable<Object> stateHolder,
			final @NonNull I_I_Forecast importRecord,
			final boolean isInsertOnly) throws Exception
	{
		ForecastImportContext context = (ForecastImportContext) stateHolder.getValue();
		if (context == null)
		{
			context = new ForecastImportContext();
			stateHolder.setValue(context);
		}

		boolean wasInsert = false;

		// Normalize name
		String impName = importRecord.getName();
		if (impName == null)
		{
			impName = "";
		}

		final Timestamp datePromised = importRecord.getDatePromised();
		final String name = impName;
		final WarehouseId warehouseId = WarehouseId.ofRepoId(importRecord.getM_Warehouse_ID());
		final BPartnerId bPartnerId = BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID());
		final PriceListId priceListId = PriceListId.ofRepoIdOrNull(importRecord.getM_PriceList_ID());
		final I_C_UOM uomRecord = uomDAO.getById(UomId.ofRepoIdOrNull(importRecord.getC_UOM_ID()));

		// Check if we need a new Forecast
		if (context.forecastId == null
				|| !context.name.equals(name)
				|| context.warehouseId.compareTo(warehouseId)!=0
				|| (context.priceListId!=null && context.priceListId.getRepoId() != importRecord.getM_PriceList_ID())
				|| context.bPartnerId.getRepoId() != importRecord.getC_BPartner_ID()
				|| !Objects.equals(context.externalId,importRecord.getExternalId())
				|| !TimeUtil.equals (context.datePromised ,  datePromised, TimeUtil.TRUNC_DAY) )
		{
			final UomId uomId = UomId.ofRepoIdOrNull(importRecord.getC_UOM_ID());

			// Build ForecastRequest with one line
			final ForecastRequest.ForecastLineRequest lineRequest = ForecastRequest.ForecastLineRequest.builder()
					.productId(ProductId.ofRepoId(importRecord.getM_Product_ID()))
					.quantity(Quantity.of(importRecord.getQty(), uomDAO.getById(uomId)))
					.activityId(ActivityId.ofRepoIdOrNull(importRecord.getC_Activity_ID()))
					.campaignId(CampaignId.ofRepoIdOrNull(importRecord.getC_Campaign_ID()))
					.projectId(ProjectId.ofRepoIdOrNull(importRecord.getC_Project_ID()))
					.quantityCalculated(Quantity.ofNullable(importRecord.getQtyCalculated(),uomRecord))
					.build();

			final ForecastRequest forecastRequest = ForecastRequest.builder()
					.warehouseId(warehouseId)
					.bpartnerId(bPartnerId)
					.priceListId(priceListId)
					.exernalId(importRecord.getExternalId())
					.datePromised(datePromised.toInstant())
					.name(name)
					.forecastLineRequests(ImmutableList.of(lineRequest))
					.build();

			// Persist forecast

			// Update context
			context.forecastId = forecastDAO.createForecast(forecastRequest);
			context.name = name;
			context.warehouseId = warehouseId;
			context.bPartnerId = bPartnerId;
			context.priceListId = priceListId;
			context.externalId = importRecord.getExternalId();
			context.datePromised = datePromised;

			wasInsert = true;
		}
		else
		{
			// Add line to the current forecast
			final ForecastRequest.ForecastLineRequest lineRequest = ForecastRequest.ForecastLineRequest.builder()
					.productId(ProductId.ofRepoId(importRecord.getM_Product_ID()))
					.quantity(Quantity.of(importRecord.getQty(),uomRecord))
					.activityId(ActivityId.ofRepoIdOrNull(importRecord.getC_Activity_ID()))
					.campaignId(CampaignId.ofRepoIdOrNull(importRecord.getC_Campaign_ID()))
					.projectId(ProjectId.ofRepoIdOrNull(importRecord.getC_Project_ID()))
					.quantityCalculated(Quantity.ofNullable(importRecord.getQtyCalculated(),uomRecord))
					.build();

			final I_M_Forecast forecastRecord = forecastDAO.getById(context.forecastId);
			forecastDAO.createForecastLine(forecastRecord, lineRequest);

		}

		importRecord.setM_Forecast_ID(context.forecastId.getRepoId());
		InterfaceWrapperHelper.save(importRecord, ITrx.TRXNAME_ThreadInherited);

		return wasInsert ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}


	@Override
	protected String getTargetTableName()
	{
		return I_M_Forecast.Table_Name;
	}

	@Override
	protected void updateAndValidateImportRecordsImpl()
	{
		final ImportRecordsSelection selection = getImportRecordsSelection();

		MForecastImportTableSqlUpdater.builder()
				.selection(selection)
				.tableName(getImportTableName())
				.build()
				.updateIForecast();
	}

	@Override
	protected String getImportOrderBySql()
	{
		return I_I_Forecast.COLUMNNAME_Name
				+ ',' + I_I_Forecast.COLUMNNAME_DatePromised
				+ ',' + I_I_Forecast.COLUMNNAME_BPValue
				+ ',' + I_I_Forecast.COLUMNNAME_WarehouseValue
				+ ',' + I_I_Forecast.COLUMNNAME_PriceList
				+ ',' + I_I_Forecast.COLUMNNAME_ProductValue;
	}

	@Override
	public Class<I_I_Forecast> getImportModelClass()
	{
		return I_I_Forecast.class;
	}

	@Override
	public String getImportTableName()
	{
		return I_I_Forecast.Table_Name;
	}

	@Override
	public I_I_Forecast retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Forecast(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}


	public static final class ForecastImportContext
	{
		ForecastId forecastId = null;
		String name = "";
		@Nullable BPartnerId bPartnerId = null;
		WarehouseId warehouseId = null;
		@Nullable PriceListId priceListId = null;
		@Nullable String externalId = "";
		Timestamp datePromised = null;
	}

}
