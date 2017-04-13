package de.metas.ui.web.process.view;

import java.lang.ref.WeakReference;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.view.ViewAction.AlwaysAllowPrecondition;
import de.metas.ui.web.process.view.ViewAction.Precondition;
import de.metas.ui.web.view.IDocumentViewSelection;
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

/**
 * Descriptor of a method annotated with {@link ViewAction}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
/* package */final class ViewActionDescriptor
{
	public static final ViewActionDescriptor of(final String actionId, final Method viewActionMethod)
	{
		return new ViewActionDescriptor(actionId, viewActionMethod);
	}

	private final String actionId;
	private final WeakReference<Method> viewActionMethodRef;

	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final boolean defaultAction;
	private final Class<? extends Precondition> preconditionClass;
	private final Precondition preconditionSharedInstance;

	private final ViewActionMethodReturnTypeConverter viewActionReturnTypeConverter;
	private final List<ViewActionMethodArgumentExtractor> viewActionParameterExtractors;

	private ViewActionDescriptor(@NonNull final String actionId, @NonNull final Method viewActionMethod)
	{
		this.actionId = actionId;
		viewActionMethodRef = new WeakReference<>(viewActionMethod);
		if (!viewActionMethod.isAccessible())
		{
			viewActionMethod.setAccessible(true);
		}

		final ViewAction viewActionAnn = viewActionMethod.getAnnotation(ViewAction.class);
		caption = Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.caption());
		description = Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.description());
		defaultAction = viewActionAnn.defaultAction();

		//
		// Preconditions
		preconditionClass = viewActionAnn.precondition();
		if (AlwaysAllowPrecondition.class.equals(preconditionClass))
		{
			preconditionSharedInstance = AlwaysAllowPrecondition.instance;
		}
		else
		{
			preconditionSharedInstance = null;
		}

		//
		// View action method's return type
		viewActionReturnTypeConverter = createReturnTypeConverter(viewActionMethod);

		//
		// View action method's parameters
		viewActionParameterExtractors = Stream.of(viewActionMethod.getParameterTypes())
				.map(parameterType -> createArgumentExtractor(viewActionMethod, parameterType))
				.collect(ImmutableList.toImmutableList());
	}

	private static final ViewActionMethodReturnTypeConverter createReturnTypeConverter(final Method method)
	{
		final Class<?> viewActionReturnType = method.getReturnType();
		if (Void.class.equals(viewActionReturnType) || void.class.equals(viewActionReturnType))
		{
			return returnValue -> null;
		}
		else if (ProcessInstanceResult.ResultAction.class.isAssignableFrom(viewActionReturnType))
		{
			return returnType -> (ProcessInstanceResult.ResultAction)returnType;
		}
		else
		{
			throw new IllegalArgumentException("Action method's return type is not supported: " + method);
		}

	}

	private static final ViewActionMethodArgumentExtractor createArgumentExtractor(final Method method, final Class<?> parameterType)
	{
		if (Collection.class.isAssignableFrom(parameterType))
		{
			return selectedDocumentIds -> selectedDocumentIds;
		}
		else
		{
			throw new IllegalArgumentException("Action method's parameter " + parameterType + " is not supported: " + method);
		}

	}

	public String getActionId()
	{
		return actionId;
	}

	public ProcessDescriptor getProcessDescriptor(@NonNull final ProcessId processId)
	{
		final ProcessLayout processLayout = ProcessLayout.builder()
				.setProcessId(processId)
				.setCaption(caption)
				.setDescription(description)
				.build();

		return ProcessDescriptor.builder()
				.setProcessId(processId)
				.setType(ProcessDescriptorType.Process)
				//
				.setLayout(processLayout)
				//
				.build();
	}

	public final WebuiRelatedProcessDescriptor toWebuiRelatedProcessDescriptor(final DocumentViewAsPreconditionsContext viewContext)
	{
		final IDocumentViewSelection view = viewContext.getView();
		final Set<DocumentId> selectedDocumentIds = viewContext.getSelectedDocumentIds();

		return WebuiRelatedProcessDescriptor.builder()
				.processId(ViewProcessInstancesRepository.buildProcessId(view.getViewId(), actionId))
				.processCaption(caption)
				.processDescription(description)
				//
				.defaultQuickAction(defaultAction)
				.quickAction(true)
				//
				.preconditionsResolutionSupplier(() -> checkPreconditions(view, selectedDocumentIds))
				//
				.build();
	}

	private final ProcessPreconditionsResolution checkPreconditions(final IDocumentViewSelection view, final Set<DocumentId> selectedDocumentIds)
	{
		try
		{
			return getPreconditionsInstance().matches(view, selectedDocumentIds);
		}
		catch (InstantiationException | IllegalAccessException ex)
		{
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private final Precondition getPreconditionsInstance() throws InstantiationException, IllegalAccessException
	{
		if (preconditionSharedInstance != null)
		{
			return preconditionSharedInstance;
		}
		return preconditionClass.newInstance();
	}

	public Method getViewActionMethod()
	{
		final Method method = viewActionMethodRef.get();
		if (method == null)
		{
			// usually this happens when the class loader was reset (e.g. JRebel)
			throw new IllegalStateException("Method no longer available for " + this);
		}
		return method;
	}

	public ProcessInstanceResult.ResultAction convertReturnType(final Object returnValue)
	{
		return viewActionReturnTypeConverter.convert(returnValue);
	}

	public Object[] extractMethodArguments(final Set<DocumentId> selectedDocumentIds)
	{
		return viewActionParameterExtractors.stream()
				.map(parameterExtractor -> parameterExtractor.extractArgument(selectedDocumentIds))
				.toArray();
	}

	@FunctionalInterface
	private static interface ViewActionMethodReturnTypeConverter
	{
		ProcessInstanceResult.ResultAction convert(Object returnValue);
	}

	@FunctionalInterface
	private static interface ViewActionMethodArgumentExtractor
	{
		Object extractArgument(final Set<DocumentId> selectedDocumentIds);
	}
}
