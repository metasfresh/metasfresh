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

import de.metas.bpartner.BPartnerId;
import de.metas.impexp.processing.SimpleImportProcessTemplate;
import de.metas.marketing.base.model.CampaignId;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.ForecastRequest.ForecastLineRequest;
import de.metas.mforecast.ForecastRequest.ForecastRequestBuilder;
import de.metas.mforecast.IForecastDAO;
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
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IMutable;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_I_Forecast;
import org.compiere.model.I_M_Forecast;
import org.compiere.model.X_I_Forecast;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
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
		final ForecastImportContext context = (ForecastImportContext)stateHolder.computeIfNull(ForecastImportContext::new);
		final ForecastHeaderKey forecastHeaderKey = ForecastHeaderKey.of(importRecord);
		boolean wasInsert = false;

		//
		// New Forecast document
		if (context.forecastId == null
				|| !Objects.equals(context.forecastHeaderKey, forecastHeaderKey))
		{
			final ForecastRequest forecastRequest = prepareForecastRequest(forecastHeaderKey)
					.forecastLineRequest(toForecastLineRequest(importRecord))
					.build();

			// Update context
			context.forecastId = forecastDAO.createForecast(forecastRequest);
			context.forecastHeaderKey = forecastHeaderKey;

			wasInsert = true;
		}
		//
		// Add a line to the current forecast document
		else
		{
			forecastDAO.addForecastLine(context.forecastId, toForecastLineRequest(importRecord));
		}

		importRecord.setM_Forecast_ID(context.forecastId.getRepoId());
		InterfaceWrapperHelper.save(importRecord, ITrx.TRXNAME_ThreadInherited);

		return wasInsert ? ImportRecordResult.Inserted : ImportRecordResult.Updated;
	}

	private ForecastRequestBuilder prepareForecastRequest(@NonNull final ForecastHeaderKey key)
	{
		return ForecastRequest.builder()
				.name(key.getName())
				.warehouseId(key.getWarehouseId())
				.bpartnerId(key.getBpartnerId())
				.priceListId(key.getPriceListId())
				.externalId(key.getExternalId())
				.datePromised(TimeUtil.asInstant(key.getDatePromisedDay()));
	}

	private ForecastLineRequest toForecastLineRequest(final @NotNull I_I_Forecast importRecord)
	{
		final I_C_UOM uom = uomDAO.getById(UomId.ofRepoId(importRecord.getC_UOM_ID()));

		return ForecastLineRequest.builder()
				.productId(ProductId.ofRepoId(importRecord.getM_Product_ID()))
				.quantity(Quantity.of(importRecord.getQty(), uom))
				.activityId(ActivityId.ofRepoIdOrNull(importRecord.getC_Activity_ID()))
				.campaignId(CampaignId.ofRepoIdOrNull(importRecord.getC_Campaign_ID()))
				.projectId(ProjectId.ofRepoIdOrNull(importRecord.getC_Project_ID()))
				.quantityCalculated(Quantity.ofNullable(importRecord.getQtyCalculated(), uom))
				.build();
	}

	@Override
	protected String getTargetTableName() {return I_M_Forecast.Table_Name;}

	@Override
	protected void updateAndValidateImportRecordsImpl()
	{
		MForecastImportTableSqlUpdater.builder()
				.selection(getImportRecordsSelection())
				.tableName(getImportTableName())
				.build()
				.updateIForecast();
	}

	@Override
	protected String getImportOrderBySql() {return ForecastHeaderKey.IMPORT_ORDER_BY;}

	@Override
	public Class<I_I_Forecast> getImportModelClass() {return I_I_Forecast.class;}

	@Override
	public String getImportTableName() {return I_I_Forecast.Table_Name;}

	@Override
	public I_I_Forecast retrieveImportRecord(final Properties ctx, final ResultSet rs) throws SQLException
	{
		return new X_I_Forecast(ctx, rs, ITrx.TRXNAME_ThreadInherited);
	}

	//
	//

	@Value
	@Builder
	private static class ForecastHeaderKey
	{
		public static final String IMPORT_ORDER_BY = I_I_Forecast.COLUMNNAME_Name
				+ ',' + I_I_Forecast.COLUMNNAME_DatePromised
				+ ',' + I_I_Forecast.COLUMNNAME_BPValue
				+ ',' + I_I_Forecast.COLUMNNAME_WarehouseValue
				+ ',' + I_I_Forecast.COLUMNNAME_PriceList
				+ ',' + I_I_Forecast.COLUMNNAME_ProductValue;

		@NonNull String name;
		@NonNull WarehouseId warehouseId;
		@Nullable PriceListId priceListId;
		@Nullable BPartnerId bpartnerId;
		@Nullable String externalId;
		@NonNull LocalDate datePromisedDay;

		public static ForecastHeaderKey of(final I_I_Forecast importRecord)
		{
			return ForecastHeaderKey.builder()
					.name(StringUtils.trimBlankToOptional(importRecord.getName()).orElseThrow(() -> new FillMandatoryException("Name")))
					.warehouseId(WarehouseId.ofRepoId(importRecord.getM_Warehouse_ID()))
					.priceListId(PriceListId.ofRepoIdOrNull(importRecord.getM_PriceList_ID()))
					.bpartnerId(BPartnerId.ofRepoIdOrNull(importRecord.getC_BPartner_ID()))
					.externalId(StringUtils.trimBlankToNull(importRecord.getExternalId()))
					.datePromisedDay(importRecord.getDatePromised().toLocalDateTime().toLocalDate())
					.build();
		}
	}

	//
	//
	//
	//
	//

	public static final class ForecastImportContext
	{
		@Nullable ForecastId forecastId;
		@Nullable ForecastImportProcess.ForecastHeaderKey forecastHeaderKey;
	}

}
