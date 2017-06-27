package de.metas.ui.web.view.descriptor.annotation;

import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import org.adempiere.exceptions.AdempiereException;
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
	private static final LoadingCache<Class<?>, ClassViewLayout> layoutsByClass = CacheBuilder.newBuilder()
			.weakKeys()
			.build(new CacheLoader<Class<?>, ClassViewLayout>()
			{
				@Override
				public ClassViewLayout load(final Class<?> dataType) throws Exception
				{
					return createClassViewLayout(dataType);
				}
			});

	public static void cacheReset()
	{
		layoutsByClass.invalidateAll();
		layoutsByClass.cleanUp();
	}

	private static ClassViewLayout getClassViewLayout(@NonNull final Class<?> dataType)
	{
		try
		{
			return layoutsByClass.get(dataType);
		}
		catch (final ExecutionException e)
		{
			throw AdempiereException.wrapIfNeeded(e).setParameter("dataType", dataType);
		}
	}

	public static List<DocumentLayoutElementDescriptor.Builder> getLayoutElementsForClass(final Class<?> dataType)
	{
		return getClassViewLayout(dataType)
				.getColumns().stream()
				.map(column -> createLayoutElement(column))
				.collect(ImmutableList.toImmutableList());
	}

	private static ClassViewLayout createClassViewLayout(final Class<?> dataType)
	{
		@SuppressWarnings("unchecked")
		final Set<Field> fields = ReflectionUtils.getAllFields(dataType, ReflectionUtils.withAnnotations(ViewColumn.class));

		final ImmutableList<ClassViewColumnLayout> columns = fields.stream()
				.sorted(Comparator.comparing(field -> extractSeqNo(field)))
				.map(field -> createClassViewColumnLayout(field))
				.collect(ImmutableList.toImmutableList());
		if (columns.isEmpty())
		{
			return ClassViewLayout.EMPTY;
		}

		return ClassViewLayout.builder()
				.className(dataType.getName())
				.columns(columns)
				.build();

	}
	
	private static final int extractSeqNo(final Field field)
	{
		ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);
		if(viewColumnAnn == null)
		{
			return Integer.MAX_VALUE;
		}
		int seqNo = viewColumnAnn.seqNo();
		return seqNo >= 0 ? seqNo : Integer.MAX_VALUE;
	}

	private static ClassViewColumnLayout createClassViewColumnLayout(final Field field)
	{
		final ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);
		final String fieldName = field.getName();
		final String captionKey = Check.isEmpty(viewColumnAnn.captionKey()) ? viewColumnAnn.captionKey() : fieldName;

		return ClassViewColumnLayout.builder()
				.fieldName(fieldName)
				.caption(Services.get(IMsgBL.class).translatable(captionKey))
				.widgetType(viewColumnAnn.widgetType())
				.fieldReference(FieldReference.of(field))
				.build();
	}

	private static DocumentLayoutElementDescriptor.Builder createLayoutElement(final ClassViewColumnLayout column)
	{
		return DocumentLayoutElementDescriptor.builder()
				.setWidgetType(column.getWidgetType())
				.setGridElement()
				.addField(DocumentLayoutElementFieldDescriptor.builder(column.getFieldName()));
	}

	public static <T extends IViewRow> ImmutableMap<String, Object> extractJsonMap(final T row)
	{
		final Class<? extends IViewRow> rowClass = row.getClass();
		final Map<String, Object> result = new LinkedHashMap<>();
		getClassViewLayout(rowClass)
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

	private static final <T extends IViewRow> Object extractFieldValueAsJson(final T row, final ClassViewColumnLayout column)
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
	private static final class ClassViewLayout
	{
		public static final ClassViewLayout EMPTY = builder().build();

		private final String className;
		@Singular
		private final ImmutableList<ClassViewColumnLayout> columns;
	}

	@Value
	@Builder
	private static final class ClassViewColumnLayout
	{
		@NonNull
		private final String fieldName;
		@NonNull
		private final ITranslatableString caption;
		@NonNull
		private final DocumentFieldWidgetType widgetType;
		@NonNull
		private final FieldReference fieldReference;
	}
}
