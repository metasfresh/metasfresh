package de.metas.ui.web.process.view;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.util.CCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import de.metas.process.IProcessPreconditionsContext;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstancesRepository;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.view.DocumentViewsRepository;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@Component
public class ViewProcessInstancesRepository implements IProcessInstancesRepository
{
	@Autowired
	private DocumentViewsRepository viewsRepository;

	private static final String PROCESS_HANDLER_TYPE = "View";

	private final CCache<String, ViewActionsDescriptor> viewActionsDescriptorByViewClassname = CCache.newCache("viewActionsDescriptorByViewClassname", 50, 0);

	private final CCache<String, ViewActionInstancesList> viewActionInstancesByViewId = CCache.newLRUCache("viewActionInstancesByViewId", 100, 60);

	@Override
	public String getProcessHandlerType()
	{
		return PROCESS_HANDLER_TYPE;
	}

	private final ViewActionsDescriptor getViewActionsDescriptor(@NonNull final IDocumentViewSelection view)
	{
		final Class<? extends IDocumentViewSelection> viewClass = view.getClass();
		return viewActionsDescriptorByViewClassname.getOrLoad(viewClass.getName(), () -> ViewActionsDescriptor.of(viewClass));
	}

	private final ViewActionDescriptor getViewActionDescriptor(final ProcessId processId)
	{
		final IPair<String, String> viewIdAndActionId = extractViewIdAndActionId(processId);
		final String viewId = viewIdAndActionId.getLeft();
		final String actionId = viewIdAndActionId.getRight();
		final IDocumentViewSelection view = viewsRepository.getView(viewId);

		return getViewActionsDescriptor(view)
				.getAction(actionId);
	}

	@Override
	public ProcessDescriptor getProcessDescriptor(final ProcessId processId)
	{
		return getViewActionDescriptor(processId).getProcessDescriptor(processId);
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final IProcessPreconditionsContext preconditionsContext)
	{
		final DocumentViewAsPreconditionsContext viewContext = DocumentViewAsPreconditionsContext.castOrNull(preconditionsContext);
		if (viewContext == null)
		{
			return Stream.empty();
		}

		final IDocumentViewSelection view = viewContext.getView();
		return getViewActionsDescriptor(view)
				.streamDocumentRelatedProcesses(viewContext);
	}

	static final ProcessId buildProcessId(final ViewId viewId, final String viewActionId)
	{
		return ProcessId.of(PROCESS_HANDLER_TYPE, viewId.getViewId() + "_" + viewActionId);
	}

	private static final IPair<String, String> extractViewIdAndActionId(final ProcessId processId)
	{
		final String processIdStr = processId.getProcessId();
		final int idx = processIdStr.indexOf("_");
		if (idx <= 0)
		{
			throw new IllegalArgumentException("Invalid view action ID: " + processId);
		}
		final String viewId = processIdStr.substring(0, idx);
		final String actionId = processIdStr.substring(idx + 1);

		return ImmutablePair.of(viewId, actionId);
	}

	@Override
	public IProcessInstanceController createNewProcessInstance(final ProcessId processId, final JSONCreateProcessInstanceRequest request)
	{
		final IPair<String, String> viewIdAndActionId = extractViewIdAndActionId(processId);
		final String viewId = viewIdAndActionId.getLeft();
		final String actionId = viewIdAndActionId.getRight();
		final IDocumentViewSelection view = viewsRepository.getView(viewId);
		final ViewActionDescriptor viewActionDescriptor = getViewActionsDescriptor(view).getAction(actionId);

		final ViewActionInstancesList viewActionInstancesList = viewActionInstancesByViewId.getOrLoad(viewId, () -> new ViewActionInstancesList(viewId));
		final DocumentId pinstanceId = viewActionInstancesList.nextPInstanceId();
		final ViewActionInstance viewActionInstance = ViewActionInstance.of(pinstanceId, view, viewActionDescriptor, request);
		viewActionInstancesList.add(viewActionInstance);
		return viewActionInstance;
	}

	private ViewActionInstance getActionInstance(final DocumentId pinstanceId)
	{
		final String viewId = ViewActionInstancesList.extractViewId(pinstanceId);
		final ViewActionInstancesList viewActionInstancesList = viewActionInstancesByViewId.get(viewId);
		if (viewActionInstancesList == null)
		{
			throw new EntityNotFoundException("No view action instance found for " + pinstanceId);
		}

		return viewActionInstancesList.getByInstanceId(pinstanceId);
	}

	@Override
	public <R> R forProcessInstanceReadonly(final DocumentId pinstanceId, final Function<IProcessInstanceController, R> processor)
	{
		final ViewActionInstance actionInstance = getActionInstance(pinstanceId);
		return processor.apply(actionInstance);
	}

	@Override
	public <R> R forProcessInstanceWritable(final DocumentId pinstanceId, final Function<IProcessInstanceController, R> processor)
	{
		final ViewActionInstance actionInstance = getActionInstance(pinstanceId);

		// Make sure the process was not already executed.
		// If it was executed we are not allowed to change it.
		actionInstance.assertNotExecuted();

		return processor.apply(actionInstance);
	}
	
	@Override
	public void cacheReset()
	{
		viewActionsDescriptorByViewClassname.clear();
		viewActionInstancesByViewId.clear();
	}

	@ToString
	private static final class ViewActionInstancesList
	{
		private final String viewId;
		private final AtomicInteger nextIdSupplier = new AtomicInteger(1);
		private final Map<DocumentId, ViewActionInstance> instances = new ConcurrentHashMap<>();

		public ViewActionInstancesList(@NonNull final String viewId)
		{
			this.viewId = viewId;
		}

		public ViewActionInstance getByInstanceId(final DocumentId pinstanceId)
		{
			final ViewActionInstance actionInstance = instances.get(pinstanceId);
			if (actionInstance == null)
			{
				throw new EntityNotFoundException("No view action instance found for " + pinstanceId);
			}
			return actionInstance;
		}

		public DocumentId nextPInstanceId()
		{
			final int nextId = nextIdSupplier.incrementAndGet();
			return DocumentId.ofString(viewId + "_" + nextId);
		}

		public static final String extractViewId(@NonNull final DocumentId pinstanceId)
		{
			final String pinstanceIdStr = pinstanceId.toJson();
			final int idx = pinstanceIdStr.indexOf("_");
			final String viewId = pinstanceIdStr.substring(0, idx);
			return viewId;
		}

		public void add(final ViewActionInstance viewActionInstance)
		{
			instances.put(viewActionInstance.getInstanceId(), viewActionInstance);
		}
	}
}
