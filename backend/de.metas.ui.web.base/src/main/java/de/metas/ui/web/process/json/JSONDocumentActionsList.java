package de.metas.ui.web.process.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.base.MoreObjects;

import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.util.GuavaCollectors;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class JSONDocumentActionsList
{
	public static final Collector<WebuiRelatedProcessDescriptor, ?, JSONDocumentActionsList> collect(final JSONOptions jsonOpts)
	{
		final Supplier<List<WebuiRelatedProcessDescriptor>> supplier = ArrayList::new;
		final BiConsumer<List<WebuiRelatedProcessDescriptor>, WebuiRelatedProcessDescriptor> accumulator = List::add;
		final BinaryOperator<List<WebuiRelatedProcessDescriptor>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<List<WebuiRelatedProcessDescriptor>, JSONDocumentActionsList> finisher = processDescriptors -> new JSONDocumentActionsList(processDescriptors, jsonOpts);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}
	
	public static final JSONDocumentActionsList ofList(final List<WebuiRelatedProcessDescriptor> processDescriptors, final JSONOptions jsonOpts)
	{
		return new JSONDocumentActionsList(processDescriptors, jsonOpts);
	}

	@JsonProperty("actions")
	private final List<JSONDocumentAction> actions;

	private JSONDocumentActionsList(final List<WebuiRelatedProcessDescriptor> processDescriptors, final JSONOptions jsonOpts)
	{
		super();
		actions = processDescriptors.stream()
				.map(processDescriptor -> new JSONDocumentAction(processDescriptor, jsonOpts))
				.sorted(JSONDocumentAction.ORDERBY_QuickActionFirst_Caption)
				.collect(GuavaCollectors.toImmutableList());
	}
	
	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("actions", actions.isEmpty() ? null : actions)
				.toString();
	}

	public List<JSONDocumentAction> getActions()
	{
		return actions;
	}
}
