package de.metas.ui.web.picking.husToPick.process;

import java.math.BigDecimal;
import java.util.Optional;

import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorRow;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;

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
	private static final String PARAM_WeightGross_Initial = "WeightGross_Initial";
	@Param(parameterName = PARAM_WeightGross_Initial)
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

		final IWeightable weightable = getWeightable().orElse(null);
		if (weightable == null
				|| !weightable.isWeightable())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a weightable HU");
		}

		return ProcessPreconditionsResolution.accept();
	}

	private Optional<IWeightable> getWeightable()
	{
		return getSingleSelectedRow()
				.getAttributes()
				.toWeightable();
	}

	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		final IWeightable weightable = getWeightable().get();
		final String parameterName = parameter.getColumnName();

		if (PARAM_WeightGross_Initial.equals(parameterName)
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
			throw new UnsupportedOperationException("calculate weight net on gross changed");
		}
		else if (PARAM_WeightTareAdjust.equals(parameterName))
		{
			throw new UnsupportedOperationException("calculate weight net on tare adjust changed");
		}
	}

	@Override
	protected String doIt()
	{
		// TODO:
		// * set weight tara adjust
		// * compute and set weight net
		// * create an internal use inventory to adjust QtyCUs !
		
//		weightGross;
//		weightTareAdjust;
//		weightNet;


		throw new UnsupportedOperationException();
	}
}
