package de.metas.ui.web.view;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.view.ViewRow.DefaultRowType;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.NonNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public interface IViewRow
{
	// Document info
	// @formatter:off
	DocumentId getId();
	boolean isProcessed();
	// @formatter:on

	default IViewRowType getType()
	{
		return DefaultRowType.Row;
	}

	/**
	 * Gets row's document path.
	 * Usually the row's document path consist of view's WindowId and row's ID.
	 * But there are views which mix data from multiple windows/tables and in that case this method will use row's actual WindowId instead of view's WindowId.
	 *
	 * @return row's document path
	 */
	@Nullable
	DocumentPath getDocumentPath();

	//
	// Fields
	Set<String> getFieldNames();

	/**
	 * @return a map with an entry for each of this row's fields.<br>
	 * Where the row has <code>null</code> values, the respective entry's value is {@link #NULL_JSON_VALUE}.
	 */
	ViewRowFieldNameAndJsonValues getFieldNameAndJsonValues();

	default BigDecimal getFieldValueAsBigDecimal(@NonNull final String fieldName, final BigDecimal defaultValueIfNotFoundOrError)
	{
		return getFieldNameAndJsonValues().getAsBigDecimal(fieldName, defaultValueIfNotFoundOrError);
	}

	default int getFieldValueAsInt(@NonNull final String fieldName, final int defaultValueIfNotFoundOrError)
	{
		return getFieldNameAndJsonValues().getAsInt(fieldName, defaultValueIfNotFoundOrError);
	}

	default boolean getFieldValueAsBoolean(@NonNull final String fieldName, final boolean defaultValueIfNotFoundOrError)
	{
		return getFieldNameAndJsonValues().getAsBoolean(fieldName, defaultValueIfNotFoundOrError);
	}

	default Object getFieldValueAsJsonObject(@NonNull final String fieldName, final JSONOptions jsonOpts)
	{
		return getFieldNameAndJsonValues().getAsJsonObject(fieldName, jsonOpts);
	}

	default Comparable<?> getFieldValueAsComparable(@NonNull final String fieldName, final JSONOptions jsonOpts)
	{
		return getFieldNameAndJsonValues().getAsComparable(fieldName, jsonOpts);
	}

	default Map<String, DocumentFieldWidgetType> getWidgetTypesByFieldName()
	{
		return ImmutableMap.of();
	}

	default Map<String, ViewEditorRenderMode> getViewEditorRenderModeByFieldName()
	{
		return ImmutableMap.of();
	}

	//
	// Included documents (children)
	// @formatter:off
	default Collection<? extends IViewRow> getIncludedRows() { return ImmutableList.of(); }
	// @formatter:on

	//
	// Attributes
	// @formatter:off
	default boolean hasAttributes() { return false; }
	default IViewRowAttributes getAttributes() { throw new EntityNotFoundException("Row does not support attributes"); }
	// @formatter:on

	//
	// IncludedView
	// @formatter:off
	default ViewId getIncludedViewId() { return null; }
	// @formatter:on

	//
	// Single column row
	// @formatter:off
	/** @return true if frontend shall display one single column */
	default boolean isSingleColumn() { return false; }
	/** @return text to be displayed if {@link #isSingleColumn()} */
	default ITranslatableString getSingleColumnCaption() { return TranslatableStrings.empty(); }
	// @formatter:on

	/**
	 * @return a stream of given row and all it's included rows recursively
	 */
	default Stream<IViewRow> streamRecursive()
	{
		return this.getIncludedRows()
				.stream()
				.map(includedRow -> includedRow.streamRecursive())
				.reduce(Stream.of(this), Stream::concat);
	}
}
