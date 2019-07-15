package de.metas.ui.web.view;

import javax.annotation.Nullable;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import de.metas.ui.web.view.descriptor.annotation.ViewColumnHelper;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2019 metas GmbH
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

public final class ViewRowFieldNameAndJsonValuesHolder<RowType extends IViewRow>
{
	public static <RowType extends IViewRow> ViewRowFieldNameAndJsonValuesHolder<RowType> newInstance(@NonNull final Class<RowType> rowType)
	{
		return new ViewRowFieldNameAndJsonValuesHolder<>(rowType);
	}

	public static <RowType extends IViewRow> ViewRowFieldNameAndJsonValuesHolderBuilder<RowType> builder(@NonNull final Class<RowType> rowType)
	{
		return new ViewRowFieldNameAndJsonValuesHolderBuilder<RowType>().rowType(rowType);
	}

	private final Class<RowType> rowType;
	@Getter
	private final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName;
	@Getter
	private final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName;

	private transient ImmutableSet<String> fieldNames; // lazy
	private transient ViewRowFieldNameAndJsonValues values; // lazy

	@Builder(builderMethodName = "_builder")
	private ViewRowFieldNameAndJsonValuesHolder(
			@NonNull final Class<RowType> rowType,
			@Nullable final ImmutableMap<String, ViewEditorRenderMode> viewEditorRenderModeByFieldName,
			@Nullable final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName)
	{
		this.rowType = rowType;
		this.viewEditorRenderModeByFieldName = viewEditorRenderModeByFieldName != null
				? viewEditorRenderModeByFieldName
				: ImmutableMap.of();

		this.widgetTypesByFieldName = widgetTypesByFieldName != null
				? widgetTypesByFieldName
				: ImmutableMap.of();
	}

	private ViewRowFieldNameAndJsonValuesHolder(final Class<RowType> rowType)
	{
		this.rowType = rowType;
		this.viewEditorRenderModeByFieldName = ImmutableMap.of();
		this.widgetTypesByFieldName = ImmutableMap.of();
	}

	private ViewRowFieldNameAndJsonValuesHolder(final ViewRowFieldNameAndJsonValuesHolder<RowType> from)
	{
		this.rowType = from.rowType;
		this.viewEditorRenderModeByFieldName = from.viewEditorRenderModeByFieldName;
		this.widgetTypesByFieldName = from.widgetTypesByFieldName;

		this.fieldNames = from.fieldNames;
		this.values = from.values;
	}

	public ImmutableSet<String> getFieldNames()
	{
		ImmutableSet<String> fieldNames = this.fieldNames;
		if (fieldNames == null)
		{
			fieldNames = this.fieldNames = ImmutableSet.<String> builder()
					.addAll(ViewColumnHelper.extractFieldNames(rowType))
					.addAll(getViewEditorRenderModeByFieldName().keySet())
					.addAll(getWidgetTypesByFieldName().keySet())
					.build();
		}
		return fieldNames;
	}

	public ViewRowFieldNameAndJsonValues get(@NonNull final RowType row)
	{
		ViewRowFieldNameAndJsonValues values = this.values;
		if (values == null)
		{
			values = this.values = ViewColumnHelper.extractJsonMap(row);
		}
		return values;
	}

	public void clearValues()
	{
		this.values = null;
	}

	public ViewRowFieldNameAndJsonValuesHolder<RowType> copy()
	{
		return new ViewRowFieldNameAndJsonValuesHolder<>(this);
	}
}
