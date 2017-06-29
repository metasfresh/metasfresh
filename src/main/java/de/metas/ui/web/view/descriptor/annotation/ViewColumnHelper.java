package de.metas.ui.web.view.descriptor.annotation;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.adempiere.util.reflect.FieldReference;
import org.reflections.ReflectionUtils;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.Values;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;
import lombok.experimental.UtilityClass;

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

@UtilityClass
public final class ViewColumnHelper
{
	private static final LoadingCache<Class<?>, ClassViewDescriptor> descriptorsByClass = CacheBuilder.newBuilder()
			.weakKeys()
			.build(new CacheLoader<Class<?>, ClassViewDescriptor>()
			{
				@Override
				public ClassViewDescriptor load(final Class<?> dataType) throws Exception
				{
					return createClassViewDescriptor(dataType);
				}
			});

	public static void cacheReset()
	{
		descriptorsByClass.invalidateAll();
		descriptorsByClass.cleanUp();
	}

	private static ClassViewDescriptor getDescriptor(@NonNull final Class<?> dataType)
	{
		try
		{
			return descriptorsByClass.get(dataType);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("dataType", dataType);
		}
	}

	public static List<DocumentLayoutElementDescriptor.Builder> createLayoutElementsForClass(final Class<?> dataType, @NonNull final JSONViewDataType viewType)
	{
		return getDescriptor(dataType)
				.getColumns().stream()
				.filter(column -> column.isDisplayed(viewType))
				.sorted(Comparator.comparing(column -> column.getSeqNo(viewType)))
				.map(column -> createLayoutElement(column))
				.collect(ImmutableList.toImmutableList());
	}

	private static ClassViewDescriptor createClassViewDescriptor(final Class<?> dataType)
	{
		@SuppressWarnings("unchecked")
		final Set<Field> fields = ReflectionUtils.getAllFields(dataType, ReflectionUtils.withAnnotations(ViewColumn.class));

		final ImmutableList<ClassViewColumnDescriptor> columns = fields.stream()
				.map(field -> createClassViewColumnDescriptor(field))
				.collect(ImmutableList.toImmutableList());
		if (columns.isEmpty())
		{
			return ClassViewDescriptor.EMPTY;
		}

		return ClassViewDescriptor.builder()
				.className(dataType.getName())
				.columns(columns)
				.build();

	}

	private static ClassViewColumnDescriptor createClassViewColumnDescriptor(final Field field)
	{
		final ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);
		final String fieldName = !Check.isEmpty(viewColumnAnn.fieldName(), true) ? viewColumnAnn.fieldName().trim() : field.getName();
		final String captionKey = !Check.isEmpty(viewColumnAnn.captionKey()) ? viewColumnAnn.captionKey() : fieldName;

		final ImmutableMap<JSONViewDataType, ClassViewColumnLayoutDescriptor> layoutsByViewType = Stream.of(viewColumnAnn.layouts())
				.map(layoutAnn -> ClassViewColumnLayoutDescriptor.builder()
						.viewType(layoutAnn.when())
						.seqNo(layoutAnn.seqNo())
						.build())
				.collect(GuavaCollectors.toImmutableMapByKey(ClassViewColumnLayoutDescriptor::getViewType));

		return ClassViewColumnDescriptor.builder()
				.fieldName(fieldName)
				.caption(Services.get(IMsgBL.class).translatable(captionKey))
				.widgetType(viewColumnAnn.widgetType())
				.fieldReference(FieldReference.of(field))
				.layoutsByViewType(layoutsByViewType)
				.build();
	}

	private static DocumentLayoutElementDescriptor.Builder createLayoutElement(final ClassViewColumnDescriptor column)
	{
		return DocumentLayoutElementDescriptor.builder()
				.setCaption(column.getCaption())
				.setWidgetType(column.getWidgetType())
				.setGridElement()
				.addField(DocumentLayoutElementFieldDescriptor.builder(column.getFieldName()));
	}

	public static <T extends IViewRow> ImmutableMap<String, Object> extractJsonMap(final T row)
	{
		final Class<? extends IViewRow> rowClass = row.getClass();
		final Map<String, Object> result = new LinkedHashMap<>();
		getDescriptor(rowClass)
				.getColumns()
				.forEach(column -> {
					final Object value = extractFieldValueAsJson(row, column);
					if (value != null)
					{
						result.put(column.getFieldName(), value);
					}
				});

		return ImmutableMap.copyOf(result);
	}

	private static final <T extends IViewRow> Object extractFieldValueAsJson(final T row, final ClassViewColumnDescriptor column)
	{
		final Field field = column.getFieldReference().getField();
		if (!field.isAccessible())
		{
			field.setAccessible(true);
		}
		try
		{
			final Object value = field.get(row);
			return Values.valueToJsonObject(value);
		}
		catch (final Exception e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}
	}

	@Value
	@Builder
	private static final class ClassViewDescriptor
	{
		public static final ClassViewDescriptor EMPTY = builder().build();

		private final String className;
		@Singular
		private final ImmutableList<ClassViewColumnDescriptor> columns;
	}

	@Value
	@Builder
	private static final class ClassViewColumnDescriptor
	{
		@NonNull
		private final String fieldName;

		@NonNull
		private final ITranslatableString caption;
		@NonNull
		private final DocumentFieldWidgetType widgetType;
		@NonNull
		private final FieldReference fieldReference;
		@NonNull
		private final ImmutableMap<JSONViewDataType, ClassViewColumnLayoutDescriptor> layoutsByViewType;

		public boolean isDisplayed(final JSONViewDataType viewType)
		{
			final ClassViewColumnLayoutDescriptor layout = layoutsByViewType.get(viewType);
			return layout != null;
		}

		public int getSeqNo(final JSONViewDataType viewType)
		{
			final ClassViewColumnLayoutDescriptor layout = layoutsByViewType.get(viewType);
			if (layout == null)
			{
				return Integer.MAX_VALUE;
			}

			int seqNo = layout.getSeqNo();
			return seqNo >= 0 ? seqNo : Integer.MAX_VALUE;
		}
	}

	@Value
	@Builder
	private static final class ClassViewColumnLayoutDescriptor
	{
		@NonNull
		private JSONViewDataType viewType;
		private final int seqNo;
	}
}
