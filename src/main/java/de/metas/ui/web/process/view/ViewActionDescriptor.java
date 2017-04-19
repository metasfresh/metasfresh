package de.metas.ui.web.process.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;

import de.metas.i18n.ITranslatableString;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.process.descriptor.ProcessDescriptor.ProcessDescriptorType;
import de.metas.ui.web.process.descriptor.ProcessLayout;
import de.metas.ui.web.process.descriptor.ProcessLayout.ProcessLayoutType;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.view.ViewAction.AlwaysAllowPrecondition;
import de.metas.ui.web.process.view.ViewAction.Precondition;
import de.metas.ui.web.view.IDocumentViewSelection;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.model.Document;
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
	private final Method viewActionMethod;

	private final ITranslatableString caption;
	private final ITranslatableString description;
	private final boolean defaultAction;
	private final Class<? extends Precondition> preconditionClass;
	private final Precondition preconditionSharedInstance;
	
	private final ProcessLayoutType layoutType;

	private final List<ViewActionParamDescriptor> viewActionParamDescriptors;
	private final ViewActionMethodReturnTypeConverter viewActionReturnTypeConverter;

	private ViewActionDescriptor(@NonNull final String actionId, @NonNull final Method viewActionMethod)
	{
		this.actionId = actionId;
		this.viewActionMethod = viewActionMethod; // hard reference, because else it would be too sensible on things like cache reset
		if (!viewActionMethod.isAccessible())
		{
			viewActionMethod.setAccessible(true);
		}

		final ViewAction viewActionAnn = viewActionMethod.getAnnotation(ViewAction.class);
		caption = Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.caption());
		description = Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.description());
		defaultAction = viewActionAnn.defaultAction();
		layoutType = viewActionAnn.layoutType();

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
		{
			final ImmutableList.Builder<ViewActionParamDescriptor> viewActionParamDescriptors = ImmutableList.builder();
			final Class<?>[] methodParameterTypes = viewActionMethod.getParameterTypes();
			final Annotation[][] methodParameterAnnotations = viewActionMethod.getParameterAnnotations();
			for (int parameterIndex = 0; parameterIndex < methodParameterTypes.length; parameterIndex++)
			{
				final Class<?> methodParameterType = methodParameterTypes[parameterIndex];
				final ViewActionParam methodParameterAnnotation = Stream.of(methodParameterAnnotations[parameterIndex])
						.filter(ann -> ann instanceof ViewActionParam)
						.map(ann -> (ViewActionParam)ann)
						.findFirst().orElse(null);

				final ViewActionParamDescriptor paramDescriptor = ViewActionParamDescriptor.of(viewActionMethod, parameterIndex, methodParameterType, methodParameterAnnotation);
				viewActionParamDescriptors.add(paramDescriptor);
			}

			this.viewActionParamDescriptors = viewActionParamDescriptors.build();
		}
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

	public String getActionId()
	{
		return actionId;
	}

	public DocumentEntityDescriptor createParametersEntityDescriptor(final ProcessId processId)
	{
		final DocumentEntityDescriptor.Builder parametersDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Process, processId.toDocumentId())
				// .setDataBinding(ProcessParametersDataBindingDescriptorBuilder.instance)
				.disableDefaultTableCallouts();

		viewActionParamDescriptors.stream()
				.filter(ViewActionParamDescriptor::isUserParameter)
				.map(ViewActionParamDescriptor::getParameterFieldDescriptor)
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
		return viewActionMethod;
	}

	public ProcessInstanceResult.ResultAction convertReturnType(final Object returnValue)
	{
		return viewActionReturnTypeConverter.convert(returnValue);
	}

	public Object[] extractMethodArguments(final Document processParameters, final Set<DocumentId> selectedDocumentIds)
	{
		return viewActionParamDescriptors.stream()
				.map(paramDesc -> paramDesc.extractArgument(processParameters, selectedDocumentIds))
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
		public Object extractArgument(Document processParameters, Set<DocumentId> selectedDocumentIds);
	}

	private static final class ViewActionParamDescriptor
	{
		public static ViewActionParamDescriptor of(final Method method, final int parameterIndex, final Class<?> parameterType, final ViewActionParam annotation)
		{
			return new ViewActionParamDescriptor(method, parameterIndex, parameterType, annotation);
		}

		private final String parameterName;
		private final Class<?> parameterValueClass;
		private final ViewActionParam parameterAnnotation;

		private final ViewActionMethodArgumentExtractor methodArgumentExtractor;

		private ViewActionParamDescriptor(final Method method, final int parameterIndex, final Class<?> parameterType, final ViewActionParam annotation)
		{
			parameterName = String.valueOf(parameterIndex);
			parameterValueClass = parameterType;
			parameterAnnotation = annotation;

			// Process parameter
			if (annotation != null)
			{
				methodArgumentExtractor = (processParameters, selectedDocumentIds) -> processParameters.getFieldView(parameterName).getValueAs(parameterValueClass);
			}
			// selectedDocumentIds internal parameter
			else if (Collection.class.isAssignableFrom(parameterType))
			{
				methodArgumentExtractor = (processParameters, selectedDocumentIds) -> selectedDocumentIds;
			}
			else
			{
				throw new IllegalArgumentException("Action method's parameter " + parameterType + " is not supported: " + method);
			}
		}

		public boolean isUserParameter()
		{
			return parameterAnnotation != null;
		}

		public DocumentFieldDescriptor.Builder getParameterFieldDescriptor()
		{
			Preconditions.checkState(isUserParameter(), "parameter is internal");

			return DocumentFieldDescriptor.builder(parameterName)
					.setCaption(parameterAnnotation.caption())
					// .setDescription(attribute.getDescription())
					//
					.setValueClass(parameterValueClass)
					.setWidgetType(parameterAnnotation.widgetType())
					// .setLookupDescriptorProvider(lookupDescriptor)
					//
					// .setDefaultValueExpression(defaultValueExpr)
					.setReadonlyLogic(false)
					.setDisplayLogic(true)
					.setMandatoryLogic(parameterAnnotation.mandatory())
					//
					.addCharacteristic(Characteristic.PublicField)
			//
			// .setDataBinding(new ASIAttributeFieldBinding(attributeId, fieldName, attribute.isMandatory(), readMethod, writeMethod))
			;

		}

		public Object extractArgument(final Document processParameters, final Set<DocumentId> selectedDocumentIds)
		{
			return methodArgumentExtractor.extractArgument(processParameters, selectedDocumentIds);
		}

	}
}
