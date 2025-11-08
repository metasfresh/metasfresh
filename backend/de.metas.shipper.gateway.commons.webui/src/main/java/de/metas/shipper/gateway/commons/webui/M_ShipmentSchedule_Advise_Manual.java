/*
 * #%L
 * de.metas.shipper.gateway.commons.webui
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

package de.metas.shipper.gateway.commons.webui;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.ShipmentScheduleQuery;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipper.gateway.commons.process.CarrierAdviseProcessService;
import de.metas.shipper.gateway.commons.process.CarrierAdviseUpdateRequest;
import de.metas.shipping.ShipperId;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_ShipmentSchedule_Carrier_Service;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.stream.Stream;

public class M_ShipmentSchedule_Advise_Manual extends ViewBasedProcessTemplate implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@NonNull private final CarrierAdviseProcessService helper = SpringContextHolder.instance.getBean(CarrierAdviseProcessService.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PARAM_IsIncludeCarrierAdviseManual = "IsIncludeCarrierAdviseManual";
	@Param(parameterName = PARAM_IsIncludeCarrierAdviseManual, mandatory = true)
	private boolean p_IsIncludeCarrierAdviseManual;

	private static final String PARAM_Shipper = I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID;
	@Param(parameterName = PARAM_Shipper, mandatory = true)
	private ShipperId p_ShipperId;

	private static final String PARAM_CarrierProduct = I_M_ShipmentSchedule.COLUMNNAME_Carrier_Product_ID;
	@Param(parameterName = PARAM_CarrierProduct, mandatory = true)
	private CarrierProductId p_CarrierProduct;

	private static final String PARAM_GoodsType = I_M_ShipmentSchedule.COLUMNNAME_Carrier_Goods_Type_ID;
	@Param(parameterName = PARAM_GoodsType, mandatory = true)
	private CarrierGoodsTypeId p_GoodsType;

	private static final String PARAM_CarrierProductService = I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_Carrier_Service_ID;
	@Param(parameterName = PARAM_CarrierProductService)
	private CarrierServiceId p_CarrierProductService;

	private static final String PARAM_CarrierProductService2 = I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_Carrier_Service_ID + "2";
	@Param(parameterName = PARAM_CarrierProductService2)
	private CarrierServiceId p_CarrierProductService2;

	private static final String PARAM_CarrierProductService3 = I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_Carrier_Service_ID + "3";
	@Param(parameterName = PARAM_CarrierProductService3)
	private CarrierServiceId p_CarrierProductService3;

	private final QueryLimit rowsLimit = QueryLimit.ofInt(sysConfigBL.getPositiveIntValue("M_ShipmentSchedule_Advise_Manual.rowsLimit", 1000));

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = getSelectedShipmentScheduleIds();
		if (shipmentScheduleIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (!helper.isSingleShipper(shipmentScheduleIds))
		{
			return ProcessPreconditionsResolution.reject(CarrierAdviseProcessService.ONLY_EXACTLY_ONE_SHIPPER_ALLOWED);
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	public @Nullable Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_Shipper, parameter.getColumnName()))
		{
			return CollectionUtils.singleElement(helper.getShipperIds(getSelectedShipmentScheduleIds()));
		}
		else if (Objects.equals(PARAM_IsIncludeCarrierAdviseManual, parameter.getColumnName()))
		{
			return false;
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		helper.updateEligibleShipmentSchedules(
				CarrierAdviseUpdateRequest.builder()
						.query(ShipmentScheduleQuery.builder()
								.shipperId(p_ShipperId)
								.shipmentScheduleIds(getSelectedShipmentScheduleIds())
								.build())
						.isIncludeCarrierAdviseManual(p_IsIncludeCarrierAdviseManual)
						.carrierProductId(p_CarrierProduct)
						.carrierGoodsTypeId(p_GoodsType)
						.carrierServiceIds(getCarrierServiceIds())
						.build()
		);

		return JavaProcess.MSG_OK;
	}

	private ImmutableSet<ShipmentScheduleId> getSelectedShipmentScheduleIds()
	{
		return getSelectedIds(ShipmentScheduleId::ofRepoId, rowsLimit);
	}

	private ImmutableSet<CarrierServiceId> getCarrierServiceIds()
	{
		return Stream.of(p_CarrierProductService, p_CarrierProductService2, p_CarrierProductService3)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}
}
