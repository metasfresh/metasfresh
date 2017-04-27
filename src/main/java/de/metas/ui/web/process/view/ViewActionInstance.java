package de.metas.ui.web.process.view;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.process.json.JSONCreateProcessInstanceRequest;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.DocumentValidStatus;
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
	public static final ViewActionInstance of(final DocumentId instanceId, final IView view, final ViewActionDescriptor viewActionDescriptor, final JSONCreateProcessInstanceRequest request)
	{
		return new ViewActionInstance(instanceId, view, viewActionDescriptor, request);
	}

	private static final String VERSION_DEFAULT = "0";

	private final ProcessId processId;
	private final DocumentId instanceId;
	private final WeakReference<IView> viewRef;
	private final ViewActionDescriptor viewActionDescriptor;
	private final Set<DocumentId> selectedDocumentIds;

	private ProcessInstanceResult result;

	@Nullable
	private final Document parametersDocument;

	private ViewActionInstance(@NonNull final DocumentId instanceId, @NonNull final IView view, @NonNull final ViewActionDescriptor viewActionDescriptor, final JSONCreateProcessInstanceRequest request)
	{
		processId = ViewProcessInstancesRepository.buildProcessId(view.getViewId(), viewActionDescriptor.getActionId());
		this.instanceId = instanceId;

		viewRef = new WeakReference<>(view);
		this.viewActionDescriptor = viewActionDescriptor;

		selectedDocumentIds = ImmutableSet.copyOf(request.getViewDocumentIds());

		final DocumentEntityDescriptor parametersDescriptor = viewActionDescriptor.createParametersEntityDescriptor(processId);
		if (parametersDescriptor != null)
		{
			parametersDocument = Document.builder(parametersDescriptor).initializeAsNewDocument(instanceId, VERSION_DEFAULT);
		}
		else
		{
			parametersDocument = null;
		}
	}

	private IView getView()
	{
		final IView view = viewRef.get();
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
		if(parametersDocument == null)
		{
			return ImmutableList.of();
		}
		return parametersDocument.getFieldViews();
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parametersDocument.getFieldLookupValues(parameterName);
	}

	@Override
	public LookupValuesList getParameterLookupValuesForQuery(final String parameterName, final String query)
	{
		return parametersDocument.getFieldLookupValuesForQuery(parameterName, query);
	}

	@Override
	public void processParameterValueChanges(final List<JSONDocumentChangedEvent> events, final ReasonSupplier reason)
	{
		parametersDocument.processValueChanges(events, reason);
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
		assertNotExecuted();

		//
		// Validate parameters, if any
		if (parametersDocument != null)
		{
			final DocumentValidStatus validStatus = parametersDocument.checkAndGetValidStatus();
			if (!validStatus.isValid())
			{
				throw new AdempiereException(validStatus.getReason());
			}
		}

		//
		// Execute view action's method
		final IView view = getView();
		final Method viewActionMethod = viewActionDescriptor.getViewActionMethod();
		final Object[] viewActionParams = viewActionDescriptor.extractMethodArguments(view, parametersDocument, selectedDocumentIds);
		try
		{
			final Object targetObject = Modifier.isStatic(viewActionMethod.getModifiers()) ? null : view;
			final Object resultActionObj = viewActionMethod.invoke(targetObject, viewActionParams);
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
