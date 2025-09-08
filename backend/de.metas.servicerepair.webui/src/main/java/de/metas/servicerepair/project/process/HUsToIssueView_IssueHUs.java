/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.servicerepair.project.process;

import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.HuId;
import de.metas.process.IProcessDefaultParameter;
import de.metas.process.IProcessDefaultParametersProvider;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.servicerepair.project.hu_to_issue.HUsToIssueViewContext;
import de.metas.servicerepair.project.hu_to_issue.HUsToIssueViewFactory;
import de.metas.servicerepair.project.model.ServiceRepairProjectTask;
import de.metas.servicerepair.project.model.ServiceRepairProjectTaskId;
import de.metas.servicerepair.project.service.ServiceRepairProjectService;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.uom.UomId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.compiere.SpringContextHolder;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.HashMap;

public class HUsToIssueView_IssueHUs
		extends HUEditorProcessTemplate
		implements IProcessDefaultParametersProvider
{
	private final ServiceRepairProjectService projectService = SpringContextHolder.instance.getBean(ServiceRepairProjectService.class);

	private static final String PARAM_Qty = "Qty";
	@Param(parameterName = PARAM_Qty, mandatory = true)
	private BigDecimal qty;

	private static final String PARAM_C_UOM_ID = "C_UOM_ID";
	@Param(parameterName = PARAM_C_UOM_ID, mandatory = true)
	private UomId uomId;

	private final HashMap<ServiceRepairProjectTaskId, ServiceRepairProjectTask> tasksCache = new HashMap<>();

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return super.checkPreconditionsApplicable();
	}

	@Nullable
	@Override
	public Object getParameterDefaultValue(final IProcessDefaultParameter parameter)
	{
		if (PARAM_Qty.equals(parameter.getColumnName()))
		{
			return getTask().getQtyToReserve().toZeroIfNegative().toBigDecimal();
		}
		if (PARAM_C_UOM_ID.equals(parameter.getColumnName()))
		{
			return getTask().getQtyToReserve().getUomId();
		}
		else
		{
			return IProcessDefaultParametersProvider.DEFAULT_VALUE_NOTAVAILABLE;
		}
	}

	@Override
	protected String doIt()
	{
		final Quantity qtyToReserve = getQtyToReserveParam();
		final ImmutableSet<HuId> huIds = getSelectedTopLevelHUIds();
		final ServiceRepairProjectTaskId taskId = getTaskId();

		projectService.reserveSparePartsFromHUs(taskId, qtyToReserve, huIds);
		tasksCache.clear();

		return MSG_OK;
	}

	private Quantity getQtyToReserveParam()
	{
		final Quantity qtyToReserve = Quantitys.of(qty, uomId);
		if (qtyToReserve.signum() <= 0)
		{
			throw new FillMandatoryException(PARAM_Qty);
		}
		return qtyToReserve;
	}

	private ServiceRepairProjectTask getTask()
	{
		return tasksCache.computeIfAbsent(getTaskId(), projectService::getTaskById);
	}

	private ServiceRepairProjectTaskId getTaskId()
	{
		final HUsToIssueViewContext husToIssueViewContext = getHusToIssueViewContext();
		return husToIssueViewContext.getTaskId();
	}

	private ImmutableSet<HuId> getSelectedTopLevelHUIds()
	{
		return streamSelectedHUIds(HUEditorRowFilter.Select.ONLY_TOPLEVEL).collect(ImmutableSet.toImmutableSet());
	}

	private HUsToIssueViewContext getHusToIssueViewContext()
	{
		return getView()
				.getParameter(HUsToIssueViewFactory.PARAM_HUsToIssueViewContext, HUsToIssueViewContext.class)
				.orElseThrow(() -> new AdempiereException("No view context"));
	}
}
