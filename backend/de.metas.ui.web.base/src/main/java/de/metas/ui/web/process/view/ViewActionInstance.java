package de.metas.ui.web.process.view;

import com.google.common.collect.ImmutableList;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.process.IProcessInstanceController;
import de.metas.ui.web.process.IProcessInstanceParameter;
import de.metas.ui.web.process.ProcessExecutionContext;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ProcessInstanceResult.CreateAndOpenIncludedViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.OpenIncludedViewAction;
import de.metas.ui.web.process.ProcessInstanceResult.ResultAction;
import de.metas.ui.web.process.adprocess.DocumentFieldAsProcessInstanceParameter;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.LookupValuesPage;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import de.metas.ui.web.window.model.IDocumentChangesCollector.ReasonSupplier;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;

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
	private static final String VERSION_DEFAULT = "0";

	private final ProcessId processId;
	private final DocumentId pinstanceId;
	private final WeakReference<IView> viewRef;
	private final ViewActionDescriptor viewActionDescriptor;
	private final DocumentIdsSelection selectedDocumentIds;

	private ProcessInstanceResult result;

	@Nullable
	private final Document parametersDocument;
	
	@Getter
	private final boolean startProcessDirectly;

	@Builder
	private ViewActionInstance(
			@NonNull final DocumentId pinstanceId,
			@NonNull final IView view,
			@NonNull final ViewActionDescriptor viewActionDescriptor,
			@NonNull final DocumentIdsSelection selectedDocumentIds)
	{
		processId = ViewProcessInstancesRepository.buildProcessId(view.getViewId(), viewActionDescriptor.getActionId());
		this.pinstanceId = pinstanceId;

		viewRef = new WeakReference<>(view);
		this.viewActionDescriptor = viewActionDescriptor;

		this.selectedDocumentIds = selectedDocumentIds;

		final DocumentEntityDescriptor parametersDescriptor = viewActionDescriptor.createParametersEntityDescriptor(processId);
		if (parametersDescriptor != null)
		{
			parametersDocument = Document.builder(parametersDescriptor)
					.initializeAsNewDocument(pinstanceId, VERSION_DEFAULT);
			startProcessDirectly = parametersDocument.getFieldNames().isEmpty();
		}
		else
		{
			parametersDocument = null;
			startProcessDirectly = true;
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

	public ProcessId getProcessId()
	{
		return processId;
	}

	@Override
	public DocumentId getInstanceId()
	{
		return pinstanceId;
	}

	@Override
	public Collection<IProcessInstanceParameter> getParameters()
	{
		if (parametersDocument == null)
		{
			return ImmutableList.of();
		}
		return parametersDocument.getFieldViews()
				.stream()
				.map(DocumentFieldAsProcessInstanceParameter::of)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public LookupValuesList getParameterLookupValues(final String parameterName)
	{
		return parametersDocument.getFieldLookupValues(parameterName);
	}

	@Override
	public LookupValuesPage getParameterLookupValuesForQuery(final String parameterName, final String query)
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
	public ProcessInstanceResult startProcess(@NonNull final ProcessExecutionContext context)
	{
		assertNotExecuted();

		//
		// Validate parameters, if any
		if (parametersDocument != null)
		{
			parametersDocument.checkAndGetValidStatus().throwIfInvalid();
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
			final ResultAction resultActionProcessed = processResultAction(resultAction, context.getViewsRepo());

			final ProcessInstanceResult result = ProcessInstanceResult.builder(pinstanceId)
					.action(resultActionProcessed)
					.build();

			this.result = result;
			return result;
		}
		catch (final Throwable ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private ResultAction processResultAction(final ResultAction resultAction, final IViewsRepository viewRepos)
	{
		if (resultAction == null)
		{
			return null;
		}

		if (resultAction instanceof CreateAndOpenIncludedViewAction)
		{
			final IView view = viewRepos.createView(((CreateAndOpenIncludedViewAction)resultAction).getCreateViewRequest());
			return OpenIncludedViewAction.builder().viewId(view.getViewId()).build();
		}

		return resultAction;
	}

	/* package */ void assertNotExecuted()
	{
		Check.assumeNull(result, "view action instance not already executed");
	}
}
