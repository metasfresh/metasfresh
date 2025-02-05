/*
 * #%L
 * de.metas.manufacturing.webui
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

package de.metas.manufacturing.webui.process;

import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessParametersCallout;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.eevolution.model.I_PP_Order;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.eevolution.productioncandidate.service.ResourcePlanningPrecision;

import java.sql.Timestamp;

public class PP_Order_Candidate_SetStartDate extends ViewBasedProcessTemplate implements IProcessParametersCallout, IProcessPrecondition
{
	private final PPOrderCandidateService ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	private static final String PARAM_DateStart = I_PP_Order.COLUMNNAME_DateStart;
	@Param(parameterName = PARAM_DateStart, mandatory = true)
	private Timestamp p_DateStart;

	private final int rowsLimit = sysConfigBL.getPositiveIntValue("PP_Order_Candidate_SetStartDate.rowsLimit", 1000);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isEmpty())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection().toInternal();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		ppOrderCandidateService.setDateStartSchedule(getSelectedPPOrderCandidateIds(), p_DateStart);
		return MSG_OK;
	}
	
	@Override
	public void onParameterChanged(final String parameterName)
	{
		if (!PARAM_DateStart.equals(parameterName))
		{
			return;
		}

		final ResourcePlanningPrecision precision = ppOrderCandidateService.getResourcePlanningPrecision();
		p_DateStart = precision.roundDown(p_DateStart);
	}

	private ImmutableSet<PPOrderCandidateId> getSelectedPPOrderCandidateIds()
	{
		final DocumentIdsSelection selectedRowIds = getSelectedRowIds();
		if (selectedRowIds.isAll())
		{
			return getView().streamByIds(selectedRowIds)
					.limit(rowsLimit)
					.map(PP_Order_Candidate_SetStartDate::extractPPOrderCandidateId)
					.collect(ImmutableSet.toImmutableSet());
		}
		else
		{
			return selectedRowIds.stream()
					.map(PP_Order_Candidate_SetStartDate::toPPOrderCandidateId)
					.collect(ImmutableSet.toImmutableSet());
		}
	}

	@NonNull
	private static PPOrderCandidateId extractPPOrderCandidateId(final IViewRow row) {return toPPOrderCandidateId(row.getId());}

	@NonNull
	private static PPOrderCandidateId toPPOrderCandidateId(final DocumentId rowId) {return PPOrderCandidateId.ofRepoId(rowId.toInt());}
}
