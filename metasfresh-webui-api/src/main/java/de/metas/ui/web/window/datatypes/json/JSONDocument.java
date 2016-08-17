package de.metas.ui.web.window.datatypes.json;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import de.metas.ui.web.window.WindowConstants;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.descriptor.sql.SqlDocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.model.Document;
import io.swagger.annotations.ApiModel;

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

/**
 * JSON format:
 * <code>
 * [ { field:someFieldName }, {...} ]
 * </code>
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@ApiModel("document")
@SuppressWarnings("serial")
public final class JSONDocument extends ArrayList<JSONDocumentField>
{
	public static final JSONDocument ofDocument(final Document document)
	{
		final JSONDocument jsonDocument = new JSONDocument();
		final JSONDocumentField jsonIdField = jsonDocument.setIdField(document.getDocumentId());

		//
		// Set debugging info
		if (WindowConstants.isProtocolDebugging())
		{
			jsonIdField.putDebugProperty("tablename", SqlDocumentEntityDataBindingDescriptor.getTableName(document));
		}

		// All other fields
		document.getFieldViews()
				.stream()
				.map(JSONDocumentField::ofDocumentField)
				.collect(jsonDocument.fieldsCollector());

		return jsonDocument;
	}

	/**
	 * @param documents
	 * @return list of {@link JSONDocument}s
	 */
	public static List<JSONDocument> ofDocumentsList(final Collection<Document> documents)
	{
		return documents.stream()
				.map(JSONDocument::ofDocument)
				.collect(Collectors.toList());
	}

	public JSONDocument()
	{
		super();
	}

	public JSONDocument(final List<JSONDocumentField> fields)
	{
		super();
		addAll(fields);
	}

	@Override
	public String toString()
	{
		return super.toString();
	}

	public JSONDocumentField setIdField(final int id)
	{
		final String idStr = DocumentId.of(id).toJson();
		final String reason = null; // N/A
		final JSONDocumentField jsonField = JSONDocumentField.ofId(idStr, reason);
		add(0, jsonField);
		return jsonField;
	}

	public Collector<JSONDocumentField, ?, Void> fieldsCollector()
	{
		final Supplier<List<JSONDocumentField>> supplier = ArrayList::new;
		final BiConsumer<List<JSONDocumentField>, JSONDocumentField> accumulator = List::add;
		final BinaryOperator<List<JSONDocumentField>> combiner = (acc1, acc2) -> {
			acc1.addAll(acc2);
			return acc1;
		};
		final Function<List<JSONDocumentField>, Void> finisher = (acc) -> {
			JSONDocument.this.addAll(acc);
			return null;
		};
		return Collector.<JSONDocumentField, List<JSONDocumentField>, Void> of(supplier, accumulator, combiner, finisher);
	}
}
