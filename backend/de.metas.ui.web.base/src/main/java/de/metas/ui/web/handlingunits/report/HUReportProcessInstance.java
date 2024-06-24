package de.metas.ui.web.handlingunits.report;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.handlingunits.report.HUReportExecutor;
import de.metas.handlingunits.report.HUReportExecutorResult;
import de.metas.handlingunits.report.HUReportService;
import de.metas.handlingunits.report.HUToReport;
import de.metas.process.AdProcessId;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstanceParameter;
import de.metas.ui.web.process.ProcessExecutionContext;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.adprocess.ADProcessPostProcessRequest;
import de.metas.ui.web.process.adprocess.ADProcessPostProcessService;
import de.metas.ui.web.process.adprocess.DocumentFieldAsProcessInstanceParameter;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.view.ViewRowIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.Document.CopyMode;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
import de.metas.ui.web.window.model.NullDocumentChangesCollector;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.IAutoCloseable;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.ReadLock;
import java.util.concurrent.locks.ReentrantReadWriteLock.WriteLock;

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

final class HUReportProcessInstance implements IProcessInstanceController
{
	public static final String PARAM_Copies = "Copies";
	public static final String PARAM_AD_Process_ID = "AD_Process_ID";
	public static final String PARAM_IsPrintPreview = "IsPrintPreview";

	private final DocumentId instanceId;
	private final ViewRowIdsSelection viewRowIdsSelection;
	private final AdProcessId reportAdProcessId;
	private final Document parameters;
	@Getter
	private final boolean startProcessDirectly;

	private ProcessInstanceResult lastExecutionResult;

	private final ReentrantReadWriteLock readwriteLock;

	@Builder
	private HUReportProcessInstance(
			@NonNull final DocumentId instanceId,
			@NonNull final ViewRowIdsSelection viewRowIdsSelection,
			@NonNull final AdProcessId reportAdProcessId,
			@NonNull final Document parameters)
	{
		this.instanceId = instanceId;
		this.viewRowIdsSelection = viewRowIdsSelection;
		this.reportAdProcessId = reportAdProcessId;
		this.parameters = parameters;
		this.startProcessDirectly = parameters.getFieldNames().isEmpty();

		lastExecutionResult = null;

		readwriteLock = new ReentrantReadWriteLock();
	}

	private HUReportProcessInstance(
			final HUReportProcessInstance from,
			final CopyMode copyMode,
			final IDocumentChangesCollector changesCollector)
	{
		instanceId = from.instanceId;
		viewRowIdsSelection = from.viewRowIdsSelection;
		reportAdProcessId = from.reportAdProcessId;
		parameters = from.parameters.copy(copyMode, changesCollector);
		startProcessDirectly = from.startProcessDirectly;

		lastExecutionResult = from.lastExecutionResult;

		readwriteLock = from.readwriteLock; // always share
	}

	public HUReportProcessInstance copyReadonly()
	{
		return new HUReportProcessInstance(this, CopyMode.CheckInReadonly, NullDocumentChangesCollector.instance);
	}

	public HUReportProcessInstance copyReadWrite(final IDocumentChangesCollector changesCollector)
	{
		return new HUReportProcessInstance(this, CopyMode.CheckOutWritable, changesCollector);
	}

	/* package */ IAutoCloseable lockForReading()
	{
		final ReadLock readLock = readwriteLock.readLock();
		readLock.lock();
		return readLock::unlock;
	}

	/* package */ IAutoCloseable lockForWriting()
	{
		final WriteLock writeLock = readwriteLock.writeLock();
		writeLock.lock();
		return writeLock::unlock;
	}

	@Override
	public DocumentId getInstanceId()
	{
		return instanceId;
	}

	@Override
	public synchronized ProcessInstanceResult startProcess(@NonNull final ProcessExecutionContext context)
	{
		final int numberOfCopies = getCopies();
		if (numberOfCopies <= 0)
		{
			throw new AdempiereException("@" + PARAM_Copies + "@ > 0");
		}

		final IViewsRepository viewsRepo = context.getViewsRepo();
		final DocumentCollection documentsCollection = context.getDocumentsCollection();

		final ViewId viewId = viewRowIdsSelection.getViewId();
		final HUReportAwareView view = HUReportAwareViews.cast(viewsRepo.getView(viewId));
		final HUReportExecutorResult reportExecutorResult = HUReportExecutor.newInstance(context.getCtx())
				.numberOfCopies(numberOfCopies)
				.adJasperProcessId(getJasperProcess_ID())
				.printPreview(isPrintPreview())
				.executeNow(reportAdProcessId, extractHUsToReport(view));

		final ADProcessPostProcessService postProcessService = ADProcessPostProcessService.builder()
				.viewsRepo(viewsRepo)
				.documentsCollection(documentsCollection)
				.build();
		final ProcessInstanceResult result = postProcessService.postProcess(ADProcessPostProcessRequest.builder()
				.viewId(viewId)
				.processInfo(reportExecutorResult.getProcessInfo())
				.processExecutionResult(reportExecutorResult.getProcessExecutionResult())
				.instanceIdOverride(instanceId)
				.build());

		return lastExecutionResult = result;
	}

	private List<HUToReport> extractHUsToReport(final HUReportAwareView view)
	{
		final Set<HUToReport> husToCheck = view.streamByIds(viewRowIdsSelection.getRowIds())
				.map(HUReportAwareViewRowAsHUToReport::ofOrNull)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());

		return HUReportService.get().getHUsToProcess(husToCheck);
	}

	@Override
	public synchronized ProcessInstanceResult getExecutionResult()
	{
		return lastExecutionResult;
	}

	@Override
	public Collection<IProcessInstanceParameter> getParameters()
	{
		return parameters.getFieldViews()
				.stream()
				.map(DocumentFieldAsProcessInstanceParameter::of)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parameters.getFieldLookupValues(parameterName);
	}

	@Override
	public LookupValuesPage getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parameters.getFieldLookupValuesForQuery(parameterName, query);
	}

	@Override
	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		parameters.processValueChanges(events, reason);
	}

	public void setCopies(final int copies)
	{
		boolean ignoreReadonlyFlag = false;
		parameters.processValueChange(PARAM_Copies, copies, ReasonSupplier.NONE, ignoreReadonlyFlag);
	}

	public int getCopies()
	{
		return parameters.getFieldView(PARAM_Copies).getValueAsInt(0);
	}

	public AdProcessId getJasperProcess_ID()
	{
		final IDocumentFieldView field = parameters.getFieldViewOrNull(PARAM_AD_Process_ID);
		if (field != null)
		{
			final int processId = field.getValueAsInt(0);
			if (processId > 0)
			{
				return AdProcessId.ofRepoId(processId);
			}
		}
		return null;
	}

	public boolean isPrintPreview()
	{
		final IDocumentFieldView field = parameters.getFieldViewOrNull(PARAM_IsPrintPreview);
		if (field != null)
		{
			return field.getValueAsBoolean();
		}
		return true;
	}
}
