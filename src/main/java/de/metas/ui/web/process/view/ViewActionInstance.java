package de.metas.ui.web.process.view;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import de.metas.ui.web.window.model.IDocumentFieldView;
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

@ToString
/* package */final class ViewActionInstance implements IProcessInstanceController
{
	public static final ViewActionInstance of(final DocumentId instanceId, final IDocumentViewSelection view, final ViewActionDescriptor viewActionDescriptor, final JSONCreateProcessInstanceRequest request)
	{
		return new ViewActionInstance(instanceId, view, viewActionDescriptor, request);
	}

	private final ProcessId processId;
	private final DocumentId instanceId;
	private final WeakReference<IDocumentViewSelection> viewRef;
	private final ViewActionDescriptor viewActionDescriptor;
	private final Set<DocumentId> selectedDocumentIds;

	private ProcessInstanceResult result;

	private ViewActionInstance(@NonNull final DocumentId instanceId, @NonNull final IDocumentViewSelection view, @NonNull final ViewActionDescriptor viewActionDescriptor, final JSONCreateProcessInstanceRequest request)
	{
		processId = ViewProcessInstancesRepository.buildProcessId(view.getViewId(), viewActionDescriptor.getActionId());
		this.instanceId = instanceId;

		viewRef = new WeakReference<>(view);
		this.viewActionDescriptor = viewActionDescriptor;

		selectedDocumentIds = ImmutableSet.copyOf(request.getViewDocumentIds());
	}

	private IDocumentViewSelection getView()
	{
		final IDocumentViewSelection view = viewRef.get();
		if (view == null)
		{
			throw new AdempiereException("view is no longer available for " + this);
		}
		return view;
	}

	@Override
	public void destroy()
	{
		// nothing
	}

	@Override
	public DocumentId getInstanceId()
	{
		return instanceId;
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

	@Override
	public ProcessInstanceResult getExecutionResult()
	{
		Check.assumeNotNull(result, "action was already executed");
		return result;
	}

	@Override
	public ProcessInstanceResult startProcess()
	{
		final IDocumentViewSelection view = getView();
		final Method viewActionMethod = viewActionDescriptor.getViewActionMethod();
		final Object[] viewActionParams = viewActionDescriptor.extractMethodArguments(selectedDocumentIds);

		try
		{
			final Object resultActionObj = viewActionMethod.invoke(view, viewActionParams);
			final ResultAction resultAction = viewActionDescriptor.convertReturnType(resultActionObj);

			final ProcessInstanceResult result = ProcessInstanceResult.builder(instanceId)
					.setAction(resultAction)
					.build();
			this.result = result;
			return result;
		}
		catch (final Throwable ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	/* package */ void assertNotExecuted()
	{
		Check.assumeNull(result, "view action instance not already executed");
	}

}
