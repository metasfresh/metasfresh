package de.metas.ui.web.process.view;

import de.metas.cache.CCache;
import de.metas.common.util.pair.IPair;
import de.metas.common.util.pair.ImmutablePair;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.CreateProcessInstanceRequest;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstancesRepository;
import de.metas.ui.web.process.ProcessHandlerType;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.WebuiPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.view.ViewId;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.model.IDocumentChangesCollector;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Stream;

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
@RequiredArgsConstructor
public class ViewProcessInstancesRepository implements IProcessInstancesRepository
{
	private final IViewsRepository viewsRepository;

	private static final ProcessHandlerType PROCESS_HANDLER_TYPE = ProcessHandlerType.ofCode("View");

	// private final CCache<String, ViewActionDescriptorsList> viewActionsDescriptorByViewClassname = CCache.newCache("viewActionsDescriptorByViewClassname", 50, 0);

	private final CCache<String, ViewActionInstancesList> viewActionInstancesByViewId = CCache.newLRUCache("viewActionInstancesByViewId", 100, 60);

	@Override
	public ProcessHandlerType getProcessHandlerType()
	{
		return PROCESS_HANDLER_TYPE;
	}

	private ViewActionDescriptorsList getViewActionDescriptors(@NonNull final IView view)
	{
		final ViewActionDescriptorsList viewClassActions = ViewActionDescriptorsFactory.instance.getFromClass(view.getClass());

		final ViewActionDescriptorsList viewActions = view.getActions();

		return viewClassActions.mergeWith(viewActions);
	}

	private ViewActionDescriptor getViewActionDescriptor(final ProcessId processId)
	{
		final IPair<String, String> viewIdAndActionId = extractViewIdAndActionId(processId);
		final String viewId = viewIdAndActionId.getLeft();
		final String actionId = viewIdAndActionId.getRight();
		final IView view = viewsRepository.getView(viewId);

		return getViewActionDescriptors(view)
				.getAction(actionId);
	}

	@Override
	public ProcessDescriptor getProcessDescriptor(final ProcessId processId)
	{
		return getViewActionDescriptor(processId).getProcessDescriptor(processId);
	}

	@Override
	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final WebuiPreconditionsContext preconditionsContext)
	{
		final ViewAsPreconditionsContext viewContext = ViewAsPreconditionsContext.castOrNull(preconditionsContext);
		if (viewContext == null)
		{
			return Stream.empty();
		}

		final IView view = viewContext.getView();
		return getViewActionDescriptors(view)
				.streamDocumentRelatedProcesses(viewContext);
	}

	static ProcessId buildProcessId(final ViewId viewId, final String viewActionId)
	{
		return ProcessId.of(PROCESS_HANDLER_TYPE, viewId.getViewId() + "_" + viewActionId);
	}

	private static IPair<String, String> extractViewIdAndActionId(final ProcessId processId)
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
	public IProcessInstanceController createNewProcessInstance(final CreateProcessInstanceRequest request)
	{
		//
		// Get the view and and the viewActionDescriptor
		final IPair<String, String> viewIdAndActionId = extractViewIdAndActionId(request.getProcessId());
		final String viewId = viewIdAndActionId.getLeft();
		final String actionId = viewIdAndActionId.getRight();
		final IView view = viewsRepository.getView(viewId);
		final ViewActionDescriptor viewActionDescriptor = getViewActionDescriptors(view).getAction(actionId);

		//
		// Create the view action instance
		// and add it to our internal list of current view action instances
		final ViewActionInstancesList viewActionInstancesList = viewActionInstancesByViewId.getOrLoad(viewId, () -> new ViewActionInstancesList(viewId));
		final DocumentId pinstanceId = viewActionInstancesList.nextPInstanceId();
		final ViewActionInstance viewActionInstance = ViewActionInstance.builder()
				.pinstanceId(pinstanceId)
				.view(view)
				.viewActionDescriptor(viewActionDescriptor)
				.selectedDocumentIds(request.getViewRowIdsSelection().getRowIds())
				.build();
		request.assertProcessIdEquals(viewActionInstance.getProcessId());
		viewActionInstancesList.add(viewActionInstance);

		//
		// Return the newly created instance
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
	public <R> R forProcessInstanceWritable(final DocumentId pinstanceId, final IDocumentChangesCollector changesCollector, final Function<IProcessInstanceController, R> processor)
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
		viewActionInstancesByViewId.reset();
	}

	@ToString
	private static final class ViewActionInstancesList
	{
		private final String viewId;
		private final AtomicInteger nextIdSupplier = new AtomicInteger(1);
		private final ConcurrentHashMap<DocumentId, ViewActionInstance> instances = new ConcurrentHashMap<>();

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

		private DocumentId nextPInstanceId()
		{
			final int nextId = nextIdSupplier.incrementAndGet();
			return DocumentId.ofString(viewId + "_" + nextId);
		}

		public static String extractViewId(@NonNull final DocumentId pinstanceId)
		{
			final String pinstanceIdStr = pinstanceId.toJson();
			final int idx = pinstanceIdStr.indexOf("_");
			return pinstanceIdStr.substring(0, idx);
		}

		public void add(final ViewActionInstance viewActionInstance)
		{
			instances.put(viewActionInstance.getInstanceId(), viewActionInstance);
		}
	}
}
