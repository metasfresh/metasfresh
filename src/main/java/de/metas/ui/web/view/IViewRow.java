package de.metas.ui.web.view;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.adempiere.util.NumberUtils;

import com.google.common.collect.ImmutableMap;

import de.metas.i18n.ITranslatableString;
import de.metas.ui.web.exceptions.EntityNotFoundException;
import de.metas.ui.web.window.datatypes.DocumentId;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.NonNull;

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
	IViewRowType getType();
	boolean isProcessed();
	// @formatter:on

	/**
	 * Gets row's document path.
	 * Usually the row's document path consist of view's WindowId and row's ID.
	 * But there are views which mix data from multiple windows/tables and in that case this method will use row's actual WindowId instead of view's WindowId.
	 *
	 * @return row's document path
	 */
	DocumentPath getDocumentPath();

	//
	// Fields
	Map<String, Object> getFieldNameAndJsonValues();

	default int getFieldJsonValueAsInt(@NonNull final String fieldName, final int defaultValueIfNotFound)
	{
		final Object jsonValueObj = getFieldNameAndJsonValues().get(fieldName);
		if (jsonValueObj == null)
		{
			return defaultValueIfNotFound;
		}
		else if (jsonValueObj instanceof Number)
		{
			return ((Number)jsonValueObj).intValue();
		}
		else if (jsonValueObj instanceof JSONLookupValue)
		{
			return ((JSONLookupValue)jsonValueObj).getKeyAsInt();
		}
		else
		{
			return Integer.parseInt(jsonValueObj.toString());
		}
	}

	default BigDecimal getFieldJsonValueAsBigDecimal(@NonNull final String fieldName, final BigDecimal defaultValueIfNotFoundOrError)
	{
		final Object jsonValueObj = getFieldNameAndJsonValues().get(fieldName);
		return NumberUtils.asBigDecimal(jsonValueObj, defaultValueIfNotFoundOrError);
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
	List<? extends IViewRow> getIncludedRows();

	//
	// Attributes
	// @formatter:off
	default boolean hasAttributes() { return false; }
	default IViewRowAttributes getAttributes() { throw new EntityNotFoundException("Row does not support attributes"); };
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
	default ITranslatableString getSingleColumnCaption() { return ITranslatableString.empty(); }
	// @formatter:on


	/** @return a stream of given row and all it's included rows recursively */
	public default Stream<IViewRow> streamRecursive()
	{
		return this.getIncludedRows()
				.stream()
				.map(includedRow -> includedRow.streamRecursive())
				.reduce(Stream.of(this), Stream::concat);
	}
}
