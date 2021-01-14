package de.metas.ui.web.process.view;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.adempiere.util.lang.MutableInt;
import org.compiere.Adempiere;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import de.metas.cache.CCache;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.ui.web.process.ProcessInstanceResult;
import de.metas.ui.web.process.view.ViewAction.AlwaysAllowPrecondition;
import de.metas.ui.web.process.view.ViewActionDescriptor.ViewActionDescriptorBuilder;
import de.metas.ui.web.process.view.ViewActionDescriptor.ViewActionMethodArgumentExtractor;
import de.metas.ui.web.process.view.ViewActionDescriptor.ViewActionMethodReturnTypeConverter;
import de.metas.ui.web.view.IView;
import de.metas.ui.web.window.datatypes.DocumentIdsSelection;
import de.metas.util.Services;

import lombok.NonNull;

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
 * Factory method used extract {@link ViewActionDescriptorsList} from different sources.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ViewActionDescriptorsFactory
{
	public static final transient ViewActionDescriptorsFactory instance = new ViewActionDescriptorsFactory();

	private static final transient Logger logger = LogManager.getLogger(ViewActionDescriptorsFactory.class);

	private final CCache<String, ViewActionDescriptorsList> viewActionsDescriptorByViewClassname = CCache.newCache("viewActionsDescriptorByViewClassname", 50, 0);

	private ViewActionDescriptorsFactory()
	{
	}

	/**
	 * Gets the view action descriptors from given class.
	 *
	 * @param clazz an utility class or a view class
	 * @return action descriptors
	 */
	public ViewActionDescriptorsList getFromClass(@NonNull final Class<?> clazz)
	{
		return viewActionsDescriptorByViewClassname.getOrLoad(clazz.getName(), () -> createFromClass(clazz));
	}

	private static final ViewActionDescriptorsList createFromClass(@NonNull final Class<?> clazz)
	{
		final ActionIdGenerator actionIdGenerator = new ActionIdGenerator();

		@SuppressWarnings("unchecked")
		final Set<Method> viewActionMethods = ReflectionUtils.getAllMethods(clazz, ReflectionUtils.withAnnotation(ViewAction.class));
		final List<ViewActionDescriptor> viewActions = viewActionMethods.stream()
				.map(viewActionMethod -> {
					try
					{
						return createViewActionDescriptor(actionIdGenerator.getActionId(viewActionMethod), viewActionMethod);
					}
					catch (final Throwable ex)
					{
						logger.warn("Failed creating view action descriptor for {}. Ignored", viewActionMethod, ex);
						return null;
					}
				})
				.filter(actionDescriptor -> actionDescriptor != null)
				.collect(Collectors.toList());

		return ViewActionDescriptorsList.of(viewActions);
	}

	private static final ViewActionDescriptor createViewActionDescriptor(@NonNull final String actionId, @NonNull final Method viewActionMethod)
	{
		if (!viewActionMethod.isAccessible())
		{
			viewActionMethod.setAccessible(true);
		}

		final ViewActionDescriptorBuilder actionBuilder = ViewActionDescriptor.builder()
				.actionId(actionId)
				.viewActionMethod(viewActionMethod);

		final ViewAction viewActionAnn = viewActionMethod.getAnnotation(ViewAction.class);
		actionBuilder
				.caption(Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.caption()))
				.description(Services.get(IMsgBL.class).getTranslatableMsgText(viewActionAnn.description()))
				.defaultAction(viewActionAnn.defaultAction())
				.layoutType(viewActionAnn.layoutType());

		//
		// Preconditions
		final Class<? extends ViewAction.Precondition> preconditionClass = viewActionAnn.precondition();
		ViewAction.Precondition preconditionSharedInstance;
		if (AlwaysAllowPrecondition.class.equals(preconditionClass))
		{
			preconditionSharedInstance = AlwaysAllowPrecondition.instance;
		}
		else
		{
			preconditionSharedInstance = null;
		}
		actionBuilder
				.preconditionClass(preconditionClass)
				.preconditionSharedInstance(preconditionSharedInstance);

		//
		// View action method's return type
		actionBuilder.viewActionReturnTypeConverter(createReturnTypeConverter(viewActionMethod));

		//
		// View action method's parameters
		{
			final Class<?>[] methodParameterTypes = viewActionMethod.getParameterTypes();
			final Annotation[][] methodParameterAnnotations = viewActionMethod.getParameterAnnotations();
			for (int parameterIndex = 0; parameterIndex < methodParameterTypes.length; parameterIndex++)
			{
				final String parameterName = String.valueOf(parameterIndex);
				final Class<?> methodParameterType = methodParameterTypes[parameterIndex];
				final ViewActionParam methodParameterAnnotation = Stream.of(methodParameterAnnotations[parameterIndex])
						.filter(ann -> ann instanceof ViewActionParam)
						.map(ann -> (ViewActionParam)ann)
						.findFirst().orElse(null);

				final ViewActionParamDescriptor paramDescriptor = ViewActionParamDescriptor.builder()
						.parameterName(parameterName)
						.parameterValueClass(methodParameterType)
						.parameterAnnotation(methodParameterAnnotation)
						.methodArgumentExtractor(createViewActionMethodArgumentExtractor(parameterName, methodParameterType, methodParameterAnnotation))
						.build();
				actionBuilder.viewActionParamDescriptor(paramDescriptor);
			}
		}

		return actionBuilder.build();
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

	private static final ViewActionMethodArgumentExtractor createViewActionMethodArgumentExtractor(final String parameterName, final Class<?> parameterType, final ViewActionParam annotation)
	{
		if (annotation != null)
		{
			return (view, processParameters, selectedDocumentIds) -> processParameters.getFieldView(parameterName).getValueAs(parameterType);
		}
		//
		// selectedDocumentIds internal parameter
		else if (DocumentIdsSelection.class.isAssignableFrom(parameterType))
		{
			return (view, processParameters, selectedDocumentIds) -> selectedDocumentIds;
		}
		//
		// View parameter
		else if (IView.class.isAssignableFrom(parameterType))
		{
			return (view, processParameters, selectedDocumentIds) -> view;
		}

		//
		// Primitive type => not supported
		else if (parameterType.isPrimitive())
		{
			throw new IllegalArgumentException("Action method's primitive parameter " + parameterType + " is not supported for parameterName: " + parameterName);
		}
		//
		// Try getting the bean from spring context
		else
		{
			return (view, processParameters, selectedDocumentIds) -> Adempiere.getBean(parameterType);
		}
	}

	/** Helper class used to generate unique actionIds based on annotated method name */
	private static final class ActionIdGenerator
	{
		private final Map<String, MutableInt> methodName2counter = new HashMap<>();

		public String getActionId(final Method actionMethod)
		{
			final String methodName = actionMethod.getName();
			final MutableInt counter = methodName2counter.computeIfAbsent(methodName, k -> new MutableInt(0));
			final int methodNameSuffix = counter.incrementAndGet();

			if (methodNameSuffix == 1)
			{
				return methodName;
			}
			else if (methodNameSuffix > 1)
			{
				return methodName + methodNameSuffix;
			}
			else
			{
				// shall NEVER happen
				throw new IllegalStateException("internal error: methodNameSuffix <= 0");
			}
		}
	}
}
