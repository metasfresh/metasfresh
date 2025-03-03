/*
 * #%L
 * de.metas.ui.web.base
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

package de.metas.ui.web.replenish.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.i18n.AdMessageKey;
import de.metas.mforecast.ForecastRequest;
import de.metas.mforecast.impl.ForecastId;
import de.metas.mforecast.impl.ForecastService;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantitys;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Forecast;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.stream.Collectors;

public class WEBUI_M_Replenish_Generate_Forecasts extends ViewBasedProcessTemplate implements IProcessDefaultParametersProvider, IProcessPrecondition
{
	public static final AdMessageKey ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG = AdMessageKey.of("ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG");

	@NonNull private final ForecastService forecastService = SpringContextHolder.instance.getBean(ForecastService.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	private static final String PARAM_DatePromised = I_M_Forecast.COLUMNNAME_DatePromised;
	@Param(parameterName = PARAM_DatePromised, mandatory = true)
	private Timestamp p_DatePromised;

	@Override
	public final ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (getSelectedRowIds().isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean allRowsHaveDemand = streamSelectedRows()
				.map(MaterialNeedsPlannerRow::ofRow)
				.allMatch(MaterialNeedsPlannerRow::isDemandFilled);

		if (!allRowsHaveDemand)
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(ALL_SELECTED_RECORDS_SHALL_HAVE_WAREHOUSE_AND_DEMAND_MSG));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final List<ForecastRequest> forecastRequests = streamSelectedRows()
				.map(MaterialNeedsPlannerRow::ofRow)
				.filter(MaterialNeedsPlannerRow::isDemandFilled)
				.collect(Collectors.groupingBy(MaterialNeedsPlannerRow::getWarehouseId))
				.entrySet()
				.stream()
				.map(entry -> ForecastRequest.builder()
						.name(warehouseBL.getWarehouseName(entry.getKey()) + "_" + p_DatePromised)
						.warehouseId(entry.getKey())
						.datePromised(p_DatePromised)
						.forecastLineRequests(entry.getValue().stream()
								.map(this::toForecastLineRequest)
								.collect(ImmutableList.toImmutableList()))
						.build())
				.collect(ImmutableList.toImmutableList());

		final ImmutableSet<ForecastId> createdForecastIds = forecastService.createForecasts(forecastRequests);

		forecastService.completeForecasts(createdForecastIds);

		return "@Created@: " + createdForecastIds.size();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_DatePromised.equals(parameter.getColumnName()))
		{
			return getDefaultDatePromised();
		}

		return DEFAULT_VALUE_NOTAVAILABLE;
	}

	@NonNull
	private ForecastRequest.ForecastLineRequest toForecastLineRequest(@NonNull final MaterialNeedsPlannerRow row)
	{
		final ProductId productId = row.getProductId();
		final UomId uomId = productBL.getStockUOMId(productId);

		return ForecastRequest.ForecastLineRequest.builder()
				.productId(productId)
				.quantity(Quantitys.of(row.getLevelMin(), uomId))
				.build();
	}

	@NonNull
	private Timestamp getDefaultDatePromised()
	{
		final OrgId orgId = getOrgId() != null ? getOrgId() : Env.getOrgId();
		final ZoneId timeZone = orgDAO.getTimeZone(orgId);

		return TimeUtil.asTimestamp(LocalDate.now()
				.with(TemporalAdjusters.next(DayOfWeek.MONDAY))
				.atStartOfDay(timeZone)
				.toInstant());
	}
}
