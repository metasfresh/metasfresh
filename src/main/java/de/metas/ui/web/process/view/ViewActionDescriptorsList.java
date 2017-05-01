package de.metas.ui.web.process.view;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;

import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.process.ViewAsPreconditionsContext;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
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
public final class ViewActionDescriptorsList
{
	public static final ViewActionDescriptorsList of(final List<ViewActionDescriptor> actions)
	{
		if (actions.isEmpty())
		{
			return ViewActionDescriptorsList.EMPTY;
		}

		final ImmutableMap<String, ViewActionDescriptor> viewActionsByActionId = Maps.uniqueIndex(actions, ViewActionDescriptor::getActionId);
		return new ViewActionDescriptorsList(viewActionsByActionId);
	}

	public static final ViewActionDescriptorsList EMPTY = new ViewActionDescriptorsList(ImmutableMap.of());

	private final ImmutableMap<String, ViewActionDescriptor> viewActionsByActionId;

	private ViewActionDescriptorsList(final ImmutableMap<String, ViewActionDescriptor> viewActionsByActionId)
	{
		this.viewActionsByActionId = viewActionsByActionId;
	}

	public ViewActionDescriptorsList mergeWith(ViewActionDescriptorsList actionsToAdd)
	{
		if (actionsToAdd == null || actionsToAdd.viewActionsByActionId.isEmpty())
		{
			return this;
		}

		if (this.viewActionsByActionId.isEmpty())
		{
			return actionsToAdd;
		}

		final Map<String, ViewActionDescriptor> newViewActionsByActionId = new HashMap<>(this.viewActionsByActionId);
		newViewActionsByActionId.putAll(actionsToAdd.viewActionsByActionId);
		return new ViewActionDescriptorsList(ImmutableMap.copyOf(newViewActionsByActionId));
	}

	public ViewActionDescriptor getAction(final String actionId)
	{
		final ViewActionDescriptor action = viewActionsByActionId.get(actionId);
		if (action == null)
		{
			throw new EntityNotFoundException("No view action found for id: " + actionId);
		}
		return action;
	}

	public Stream<WebuiRelatedProcessDescriptor> streamDocumentRelatedProcesses(final ViewAsPreconditionsContext viewContext)
	{
		return viewActionsByActionId.values().stream()
				.map(viewActionDescriptor -> viewActionDescriptor.toWebuiRelatedProcessDescriptor(viewContext));
	}

}
