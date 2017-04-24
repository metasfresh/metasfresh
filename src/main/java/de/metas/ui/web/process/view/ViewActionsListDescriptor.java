package de.metas.ui.web.process.view;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.lang.MutableInt;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import de.metas.logging.LogManager;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.DocumentViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.view.IDocumentViewSelection;
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
 * All {@link ViewActionDescriptor}s for a view class.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ToString
/* package */final class ViewActionsListDescriptor
{
	public static final ViewActionsListDescriptor of(final Class<? extends IDocumentViewSelection> viewClass)
	{
		return new ViewActionsListDescriptor(viewClass);
	}

	private static final Logger logger = LogManager.getLogger(ViewActionsListDescriptor.class);

	private final Map<String, ViewActionDescriptor> viewActions;

	private ViewActionsListDescriptor(final Class<? extends IDocumentViewSelection> viewClass)
	{
		final ActionIdGenerator actionIdGenerator = new ActionIdGenerator();

		@SuppressWarnings("unchecked")

		final Set<Method> viewActionMethods = ReflectionUtils.getAllMethods(viewClass, ReflectionUtils.withAnnotation(ViewAction.class));
		viewActions = viewActionMethods.stream()
				.map(viewActionMethod -> {
					try
					{
						return ViewActionDescriptor.of(actionIdGenerator.getActionId(viewActionMethod), viewActionMethod);
					}
					catch (final Throwable ex)
					{
						logger.warn("Failed creating view action descriptor for {}. Ignored", viewActionMethod, ex);
						return null;
					}
				})
				.filter(actionDescriptor -> actionDescriptor != null)
				.collect(GuavaCollectors.toImmutableMapByKey(ViewActionDescriptor::getActionId));
	}

	public ViewActionDescriptor getAction(final String actionId)
	{
		final ViewActionDescriptor action = viewActions.get(actionId);
		if (action == null)
		{
			throw new EntityNotFoundException("No view action found for id: " + actionId);
		}
		return action;
	}

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final DocumentViewAsPreconditionsContext viewContext)
	{
		return viewActions.values().stream()
				.map(viewActionDescriptor -> viewActionDescriptor.toWebuiRelatedProcessDescriptor(viewContext));
	}

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
