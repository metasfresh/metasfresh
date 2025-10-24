/*
 * #%L
 * de.metas.shipper.gateway.commons
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

package de.metas.shipper.gateway.commons.process;

import com.google.common.collect.ImmutableSet;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.CarrierGoodsTypeId;
import de.metas.inoutcandidate.CarrierProductId;
import de.metas.inoutcandidate.CarrierServiceId;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.shipping.ShipperId;
import de.metas.util.collections.CollectionUtils;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReferenceSet;
import org.compiere.model.I_M_ShipmentSchedule_Carrier_Service;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class M_ShipmentSchedule_Advise_Manual extends JavaProcess implements IProcessPrecondition, IProcessDefaultParametersProvider
{
	@NonNull private final CarrierAdviseProcessHelper helper = CarrierAdviseProcessHelper.newInstance();

	private static final String PARAM_Shipper = I_M_ShipmentSchedule.COLUMNNAME_M_Shipper_ID;
	@Param(parameterName = PARAM_Shipper, mandatory = true)
	private ShipperId p_Shipper;

	private static final String PARAM_CarrierProduct = I_M_ShipmentSchedule.COLUMNNAME_Carrier_Product_ID;
	@Param(parameterName = PARAM_CarrierProduct, mandatory = true)
	private CarrierProductId p_CarrierProduct;

	private static final String PARAM_GoodsType = I_M_ShipmentSchedule.COLUMNNAME_Carrier_Goods_Type_ID;
	@Param(parameterName = PARAM_GoodsType, mandatory = true)
	private CarrierGoodsTypeId p_GoodsType;

	private static final String PARAM_CarrierProductService = I_M_ShipmentSchedule_Carrier_Service.COLUMNNAME_Carrier_Service_ID;
	@Param(parameterName = PARAM_CarrierProductService)
	private CarrierServiceId p_CarrierProductService;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(final @NonNull IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		final ImmutableSet<ShipmentScheduleId> shipmentScheduleIds = TableRecordReferenceSet.of(context.getSelectedIncludedRecords())
				.streamIds(I_M_ShipmentSchedule.Table_Name, ShipmentScheduleId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
		if (helper.isSingleShipper(shipmentScheduleIds))
		{
			return ProcessPreconditionsResolution.reject("Only one shipper is allowed"); //TODO adMsg
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		return "";
	}

	@Override
	public @Nullable Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (Objects.equals(PARAM_Shipper, parameter.getColumnName()))
		{
			return CollectionUtils.singleElement(helper.getShipperIds(getSelectedIncludedRecordIds(I_M_ShipmentSchedule.class, ShipmentScheduleId::ofRepoId)));
		}
		else
		{
			return DEFAULT_VALUE_NOTAVAILABLE;
		}
	}
}
