package de.metas.ui.web.handlingunits.report;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.util.Services;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.handlingunits.HUEditorView;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

class HUReportProcessInstance implements IProcessInstanceController
{
	private final DocumentId instanceId;
	private final ViewRowIdsSelection viewRowIdsSelection;
	private final int reportADProcessId;

	private ProcessInstanceResult lastExecutionResult;

	@Builder
	private HUReportProcessInstance(
			@NonNull final DocumentId instanceId,
			@NonNull final ViewRowIdsSelection viewRowIdsSelection,
			final int reportADProcessId)
	{
		Check.assume(reportADProcessId > 0, "reportADProcessId > 0");
		this.instanceId = instanceId;
		this.viewRowIdsSelection = viewRowIdsSelection;
		this.reportADProcessId = reportADProcessId;
	}

	@Override
	public DocumentId getInstanceId()
	{
		return instanceId;
	}

	@Override
	public synchronized ProcessInstanceResult startProcess(final IViewsRepository viewsRepo, final DocumentCollection documentsCollection)
	{
		try
		{
			final HUEditorView view = HUEditorView.cast(viewsRepo.getView(viewRowIdsSelection.getViewId()));
			HUReportExecutor.newInstance(Env.getCtx())
					// .numberOfCopies(1)
					.executeNow(reportADProcessId, extractHUsToReport(view));

			return lastExecutionResult = ProcessInstanceResult.ok(instanceId);
		}
		catch (final Exception ex)
		{
			return lastExecutionResult = ProcessInstanceResult.error(instanceId, ex);
		}
	}

	private List<I_M_HU> extractHUsToReport(final HUEditorView view)
	{
		final Set<Integer> huIds = view.streamByIds(viewRowIdsSelection.getRowIds())
				.map(row -> row.getM_HU_ID())
				.collect(ImmutableSet.toImmutableSet());

		return Services.get(IHandlingUnitsDAO.class).retrieveByIds(huIds);
	}

	@Override
	public synchronized ProcessInstanceResult getExecutionResult()
	{
		return lastExecutionResult;
	}

	@Override
	public Collection<IDocumentFieldView> getParameters()
	{
		return ImmutableList.of();
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public LookupValuesList getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		throw new UnsupportedOperationException();
	}

}
