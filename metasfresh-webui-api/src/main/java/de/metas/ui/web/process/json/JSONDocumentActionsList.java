package de.metas.ui.web.process.json;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.metas.ui.web.process.descriptor.ProcessDescriptor;
import de.metas.ui.web.window.datatypes.json.JSONFilteringOptions;

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
	public static final Collector<ProcessDescriptor, ?, JSONDocumentActionsList> collect(final JSONFilteringOptions jsonOpts)
	{
		final Supplier<List<ProcessDescriptor>> supplier = ArrayList::new;
		final BiConsumer<List<ProcessDescriptor>, ProcessDescriptor> accumulator = List::add;
		final BinaryOperator<List<ProcessDescriptor>> combiner = (l, r) -> {
			l.addAll(r);
			return l;
		};
		final Function<List<ProcessDescriptor>, JSONDocumentActionsList> finisher = processDescriptors -> new JSONDocumentActionsList(processDescriptors, jsonOpts);
		return Collector.of(supplier, accumulator, combiner, finisher);
	}

	@JsonProperty("actions")
	private final List<JSONDocumentAction> actions;

	private JSONDocumentActionsList(final List<ProcessDescriptor> processDescriptors, final JSONFilteringOptions jsonOpts)
	{
		super();
		actions = JSONDocumentAction.ofList(processDescriptors, jsonOpts);
	}

	public List<JSONDocumentAction> getActions()
	{
		return actions;
	}
}
