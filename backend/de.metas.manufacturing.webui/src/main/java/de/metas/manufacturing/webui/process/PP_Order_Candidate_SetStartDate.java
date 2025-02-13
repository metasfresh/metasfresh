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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.process.IProcessPrecondition;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.adprocess.ViewBasedProcessTemplate;
import de.metas.ui.web.process.descriptor.ProcessParamLookupValuesProvider;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;
import org.eevolution.productioncandidate.model.PPOrderCandidateId;
import org.eevolution.productioncandidate.service.PPOrderCandidateService;
import org.eevolution.productioncandidate.service.ResourcePlanningPrecision;

import java.sql.Timestamp;
import java.util.stream.IntStream;

public class PP_Order_Candidate_SetStartDate extends ViewBasedProcessTemplate implements IProcessPrecondition
{
	private final PPOrderCandidateService ppOrderCandidateService = SpringContextHolder.instance.getBean(PPOrderCandidateService.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@Param(parameterName = "Date", mandatory = true)
	private Timestamp p_Date;

	private static final String PARAM_Hour = "Hour";
	@Param(parameterName = PARAM_Hour, mandatory = true)
	private String p_Hour;

	private static final String PARAM_Minute = "Minute";
	@Param(parameterName = PARAM_Minute, mandatory = true)
	private String p_Minute;

	private final int rowsLimit = sysConfigBL.getPositiveIntValue("PP_Order_Candidate_SetStartDate.rowsLimit", 2000);

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
		ppOrderCandidateService.setDateStartSchedule(getSelectedPPOrderCandidateIds(), convertParamsToTimestamp());
		return MSG_OK;
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_Hour, numericKey = false, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.list)
	private LookupValuesList hourLookupProvider()
	{
		return LookupValuesList.fromCollection(
				IntStream.range(0, 24)
						.mapToObj(this::toStringLookupValue)
						.collect(ImmutableList.toImmutableList())
		);
	}

	@ProcessParamLookupValuesProvider(parameterName = PARAM_Minute, numericKey = false, lookupSource = DocumentLayoutElementFieldDescriptor.LookupSource.list)
	private LookupValuesList minuteLookupProvider()
	{
		final ResourcePlanningPrecision precision = ppOrderCandidateService.getResourcePlanningPrecision();
		final ImmutableSet<LookupValue.StringLookupValue> minutes = precision.getMinutes()
				.stream()
				.map(this::toStringLookupValue)
				.collect(ImmutableSet.toImmutableSet());

		return LookupValuesList.fromCollection(minutes);
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
	private Timestamp convertParamsToTimestamp()
	{
		return Timestamp.valueOf(TimeUtil.asLocalDate(p_Date)
				.atTime(Integer.parseInt(p_Hour), Integer.parseInt(p_Minute)));
	}

	@NonNull
	private LookupValue.StringLookupValue toStringLookupValue(final int value)
	{
		final String formattedValue = String.format("%02d", value);

		return LookupValue.StringLookupValue.of(formattedValue, formattedValue);
	}
	
	@NonNull
	private static PPOrderCandidateId extractPPOrderCandidateId(final IViewRow row) {return toPPOrderCandidateId(row.getId());}

	@NonNull
	private static PPOrderCandidateId toPPOrderCandidateId(final DocumentId rowId) {return PPOrderCandidateId.ofRepoId(rowId.toInt());}
}
