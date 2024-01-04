package de.metas.ui.web.picking.husToPick.process;

import com.google.common.annotations.VisibleForTesting;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.PlainWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.impl.HUQtyService;
import de.metas.handlingunits.weighting.WeightHUCommand;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.handlingunits.HUEditorRowAttributesHelper;
import de.metas.ui.web.process.adprocess.device_providers.DeviceDescriptorsList;
import de.metas.ui.web.process.descriptor.ProcessParamDevicesProvider;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.SpringContextHolder;

import java.math.BigDecimal;
import java.util.Optional;

/*
 * #%L
 * metasfresh-webui-api
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

public class WEBUI_HUsToPick_Weight extends HUsToPickViewBasedProcess implements IProcessPrecondition, IProcessParametersCallout, IProcessDefaultParametersProvider
{
	private final HUQtyService huQtyService = SpringContextHolder.instance.getBean(HUQtyService.class);

	private static final String PARAM_WeightGrossInitial = "WeightGrossInitial";
	@Param(parameterName = PARAM_WeightGrossInitial)
	private BigDecimal weightGrossInitial;

	private static final String PARAM_WeightGross = "WeightGross";
	@Param(parameterName = PARAM_WeightGross)
	private BigDecimal weightGross;

	private static final String PARAM_WeightTare = "WeightTare";
	@Param(parameterName = PARAM_WeightTare)
	private BigDecimal weightTare;

	private static final String PARAM_WeightTareAdjust = "WeightTareAdjust";
	@Param(parameterName = PARAM_WeightTareAdjust)
	private BigDecimal weightTareAdjust;

	private static final String PARAM_WeightNet = "WeightNet";
	@Param(parameterName = PARAM_WeightNet)
	private BigDecimal weightNet;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		else if (selectedRowIds.isMoreThanOneDocumentId())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final HUEditorRow huRow = getSingleSelectedRow();
		if (!huRow.isTopLevel())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a top level HU");
		}
		if (!huRow.isHUStatusActive())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not an active HU");
		}

		final IWeightable weightable = getHUAttributesAsWeightable().orElse(null);
		if (weightable == null
				|| !weightable.isWeightable())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a weightable HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private HuId getSelectedHUId()
	{
		return HuId.ofRepoId(getRecord_ID());
	}

	private WarehouseId getHUWarehouseId()
	{
		final LocatorId locatorId = getSingleSelectedRow().getLocatorId();
		return locatorId != null ? locatorId.getWarehouseId() : null;
	}

	private Optional<IWeightable> getHUAttributesAsWeightable()
	{
		return getSingleSelectedRow()
				.getAttributes()
				.toWeightable();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final IWeightable weightable = getHUAttributesAsWeightable().get();
		final String parameterName = parameter.getColumnName();

		if (PARAM_WeightGrossInitial.equals(parameterName)
				|| PARAM_WeightGross.equals(parameterName))
		{
			return weightable.hasWeightGross()
					? weightable.getWeightGross()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		else if (PARAM_WeightTare.equals(parameterName))
		{
			return weightable.hasWeightTare()
					? weightable.getWeightTare()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		else if (PARAM_WeightTareAdjust.equals(parameterName))
		{
			return weightable.hasWeightTareAdjust()
					? weightable.getWeightTareAdjust()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		else if (PARAM_WeightNet.equals(parameterName))
		{
			return weightable.hasWeightNet()
					? weightable.getWeightNet()
					: IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (PARAM_WeightGross.equals(parameterName))
		{
			updateWeightNetParameter();
		}
		else if (PARAM_WeightTare.equals(parameterName))
		{
			updateWeightNetParameter();
		}
		else if (PARAM_WeightTareAdjust.equals(parameterName))
		{
			updateWeightNetParameter();
		}
	}

	private void updateWeightNetParameter()
	{
		final PlainWeightable weightable = getParametersAsWeightable();
		Weightables.updateWeightNet(weightable);
		weightNet = weightable.getWeightNet();
	}

	@VisibleForTesting
	PlainWeightable getParametersAsWeightable()
	{
		final IWeightable huAttributes = getHUAttributesAsWeightable().get();

		return PlainWeightable.builder()
				.uom(huAttributes.getWeightNetUOM())
				.weightGross(weightGross)
				.weightNet(weightNet)
				.weightTare(weightTare)
				.weightTareAdjust(weightTareAdjust)
				.build();
	}

	@ProcessParamDevicesProvider(parameterName = PARAM_WeightGross)
	public DeviceDescriptorsList getWeightGrossDevices()
	{
		final IWeightable weightable = getHUAttributesAsWeightable().get();
		final AttributeCode weightGrossAttribute = weightable.getWeightGrossAttribute();

		final WarehouseId huWarehouseId = getHUWarehouseId();

		return HUEditorRowAttributesHelper.getDeviceDescriptors(weightGrossAttribute, huWarehouseId);
	}

	@Override
	protected String doIt()
	{
		final HuId huId = getSelectedHUId();
		final PlainWeightable targetWeight = getParametersAsWeightable();

		WeightHUCommand.builder()
				.huQtyService(huQtyService)
				//
				.huId(huId)
				.targetWeight(targetWeight)
				.build()
				//
				.execute();

		return MSG_OK;
	}
}
