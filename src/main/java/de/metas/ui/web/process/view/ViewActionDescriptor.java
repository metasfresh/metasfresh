package de.metas.ui.web.process.view;

import java.lang.reflect.Method;

import org.adempiere.exceptions.AdempiereException;

import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.view.ViewAction.Precondition;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.PanelLayoutType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.model.Document;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
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
@Builder
public final class ViewActionDescriptor
{
	private final @NonNull String actionId;
	private final @NonNull Method viewActionMethod; // hard reference, because else it would be too sensible on things like cache reset

	private final @NonNull ITranslatableString caption;
	private final @NonNull ITranslatableString description;
	private final boolean defaultAction;
	private final Class<? extends Precondition> preconditionClass;
	private final Precondition preconditionSharedInstance;

	private final @NonNull PanelLayoutType layoutType;

	@Singular
	private final ImmutableList<ViewActionParamDescriptor> viewActionParamDescriptors;
	private final @NonNull ViewActionMethodReturnTypeConverter viewActionReturnTypeConverter;

	public String getActionId()
	{
		return actionId;
	}

	public DocumentEntityDescriptor createParametersEntityDescriptor(final ProcessId processId)
	{
		final DocumentEntityDescriptor.Builder parametersDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Process, processId.toDocumentId())
				.disableDefaultTableCallouts();

		viewActionParamDescriptors.stream()
				.filter(ViewActionParamDescriptor::isUserParameter)
				.map(ViewActionParamDescriptor::createParameterFieldDescriptor)
				.forEach(parametersDescriptor::addField);

		if (parametersDescriptor.getFieldsCount() == 0)
		{
			return null;
		}

		return parametersDescriptor.build();
	}

	public ProcessDescriptor getProcessDescriptor(@NonNull final ProcessId processId)
	{
		final DocumentEntityDescriptor parametersDescriptor = createParametersEntityDescriptor(processId);

		final ProcessLayout processLayout = ProcessLayout.builder()
				.setProcessId(processId)
				.setLayoutType(layoutType)
				.setCaption(caption)
				.setDescription(description)
				.addElements(parametersDescriptor)
				.build();

		return ProcessDescriptor.builder()
				.setProcessId(processId)
				.setType(ProcessDescriptorType.Process)
				//
				.setLayout(processLayout)
				//
				.build();
	}

	public final WebuiRelatedProcessDescriptor toWebuiRelatedProcessDescriptor(final ViewAsPreconditionsContext viewContext)
	{
		final IView view = viewContext.getView();
		final DocumentIdsSelection selectedDocumentIds = viewContext.getSelectedRowIds();

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

	private final ProcessPreconditionsResolution checkPreconditions(final IView view, final DocumentIdsSelection selectedDocumentIds)
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
		return viewActionMethod;
	}

	public ProcessInstanceResult.ResultAction convertReturnType(final Object returnValue)
	{
		return viewActionReturnTypeConverter.convert(returnValue);
	}

	public Object[] extractMethodArguments(final IView view, final Document processParameters, final DocumentIdsSelection selectedDocumentIds)
	{
		return viewActionParamDescriptors.stream()
				.map(paramDesc -> paramDesc.extractArgument(view, processParameters, selectedDocumentIds))
				.toArray();
	}

	@FunctionalInterface
	public static interface ViewActionMethodReturnTypeConverter
	{
		ProcessInstanceResult.ResultAction convert(Object returnValue);
	}

	@FunctionalInterface
	public static interface ViewActionMethodArgumentExtractor
	{
		public Object extractArgument(IView view, Document processParameters, DocumentIdsSelection selectedDocumentIds);
	}
}
