package de.metas.ui.web.view.descriptor.annotation;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.ui.web.view.IViewRow;
import de.metas.ui.web.view.ViewRowFieldNameAndJsonValues;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.TranslationSource;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout;
import de.metas.ui.web.view.descriptor.annotation.ViewColumn.ViewColumnLayout.Displayed;
import de.metas.ui.web.view.json.JSONViewDataType;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.MediaType;
import de.metas.ui.web.window.datatypes.json.JSONNullValue;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.descriptor.ViewEditorRenderMode;
import de.metas.ui.web.window.descriptor.WidgetSize;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.ReferenceListAwareEnum;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;
import lombok.ToString;
import lombok.Value;
import lombok.experimental.UtilityClass;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.reflect.FieldReference;
import org.compiere.Adempiere;
import org.compiere.util.Env;
import org.reflections.ReflectionUtils;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.lang.reflect.Field;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
	private static final Logger logger = LogManager.getLogger(ViewColumnHelper.class);

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

	public static List<DocumentLayoutElementDescriptor.Builder> createLayoutElementsForClass(
			@NonNull final Class<?> dataType,
			@NonNull final JSONViewDataType viewType)
	{
		return getDescriptor(dataType)
				.streamColumns()
				.filter(column -> column.isDisplayed(viewType))
				.sorted(Comparator.comparing(column -> column.getSeqNo(viewType)))
				.map(column -> createLayoutElement(column))
				.collect(ImmutableList.toImmutableList());
	}

	@Value
	@Builder
	public static class ClassViewColumnOverrides
	{
		public static final ClassViewColumnOverrides ofFieldName(final String fieldName)
		{
			return builder(fieldName).build();
		}

		public static final ClassViewColumnOverridesBuilder builder(final String fieldName)
		{
			return new ClassViewColumnOverridesBuilder().fieldName(fieldName);
		}

		@NonNull
		private final String fieldName;
		private final WidgetSize widgetSize;
		@Singular
		private final ImmutableSet<MediaType> restrictToMediaTypes;
	}

	public static List<DocumentLayoutElementDescriptor.Builder> createLayoutElementsForClassAndFieldNames(
			@NonNull final Class<?> dataType,
			@NonNull final JSONViewDataType viewDataType,
			@NonNull final ClassViewColumnOverrides... columns)
	{
		Check.assumeNotEmpty(columns, "columnOverrides is not empty");

		final ClassViewDescriptor descriptor = getDescriptor(dataType);
		return Stream.of(columns)
				.map(columnOverride -> {
					final ClassViewColumnDescriptor columnDescriptor = descriptor.getColumnByName(columnOverride.getFieldName());
					return createClassViewColumnDescriptorEffective(columnDescriptor, columnOverride);
				})
				.map(ViewColumnHelper::createLayoutElement)
				.collect(ImmutableList.toImmutableList());
	}

	private static ClassViewColumnDescriptor createClassViewColumnDescriptorEffective(@NonNull final ClassViewColumnDescriptor column, @NonNull final ClassViewColumnOverrides overrides)
	{
		final ClassViewColumnDescriptor.ClassViewColumnDescriptorBuilder columnBuilder = column.toBuilder();

		if (overrides.getWidgetSize() != null)
		{
			columnBuilder.widgetSize(overrides.getWidgetSize());
		}
		if (overrides.getRestrictToMediaTypes() != null)
		{
			columnBuilder.restrictToMediaTypes(overrides.getRestrictToMediaTypes());
		}

		return columnBuilder.build();
	}

	private static ClassViewDescriptor createClassViewDescriptor(@NonNull final Class<?> dataType)
	{
		@SuppressWarnings("unchecked")
		final Set<Field> fields = ReflectionUtils.getAllFields(dataType, ReflectionUtils.withAnnotation(ViewColumn.class));

		final ImmutableList<ClassViewColumnDescriptor> columns = fields.stream()
				.map(field -> createClassViewColumnDescriptor(field))
				.collect(ImmutableList.toImmutableList());
		if (columns.isEmpty())
		{
			return ClassViewDescriptor.EMPTY;
		}

		return ClassViewDescriptor.builder()
				.columns(columns)
				.build();

	}

	private static ClassViewColumnDescriptor createClassViewColumnDescriptor(@NonNull final Field field)
	{

		final String fieldName = extractFieldName(field);

		final ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);

		final ImmutableMap<JSONViewDataType, ClassViewColumnLayoutDescriptor> layoutsByViewType = createViewColumnLayoutDescriptors(viewColumnAnn, fieldName);

		return ClassViewColumnDescriptor.builder()
				.fieldName(fieldName)
				.caption(extractCaption(field))
				.widgetType(viewColumnAnn.widgetType())
				.listReferenceId(viewColumnAnn.listReferenceId())
				.editorRenderMode(viewColumnAnn.editor())
				.allowSorting(viewColumnAnn.sorting())
				.fieldReference(FieldReference.of(field))
				.layoutsByViewType(layoutsByViewType)
				.widgetSize(viewColumnAnn.widgetSize())
				.restrictToMediaTypes(ImmutableSet.copyOf(viewColumnAnn.restrictToMediaTypes()))
				.build();
	}

	private static ITranslatableString extractCaption(@NonNull final Field field)
	{
		final ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);

		final String captionKey = !Check.isEmpty(viewColumnAnn.captionKey())
				? viewColumnAnn.captionKey()
				: extractFieldName(field);

		final TranslationSource captionTranslationSource = viewColumnAnn.captionTranslationSource();
		if (captionTranslationSource == TranslationSource.DEFAULT)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			return msgBL.translatable(captionKey);
		}
		else if (captionTranslationSource == TranslationSource.ATTRIBUTE_NAME)
		{
			final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
			return attributesRepo.getAttributeDisplayNameByValue(captionKey)
					.orElseGet(() -> TranslatableStrings.anyLanguage(captionKey));
		}
		else
		{
			logger.warn("Unknown TranslationSource={} for {}. Returning the captionKey={}", captionTranslationSource, field, captionKey);
			return TranslatableStrings.anyLanguage(captionKey);
		}
	}

	private static String extractFieldName(@NonNull final Field field)
	{
		final ViewColumn viewColumnAnn = field.getAnnotation(ViewColumn.class);
		final String fieldName = !Check.isEmpty(viewColumnAnn.fieldName(), true) ? viewColumnAnn.fieldName().trim() : field.getName();
		return fieldName;
	}

	private static ImmutableMap<JSONViewDataType, ClassViewColumnLayoutDescriptor> createViewColumnLayoutDescriptors(
			@NonNull final ViewColumn viewColumnAnn,
			@NonNull final String fieldName)
	{
		final int defaultSeqNo = viewColumnAnn.seqNo();

		if (viewColumnAnn.layouts().length > 0)
		{
			return Stream.of(viewColumnAnn.layouts())
					.map(layoutAnn -> ClassViewColumnLayoutDescriptor
							.builder()
							.viewType(layoutAnn.when())
							.displayed(extractDisplayedValue(layoutAnn, fieldName))
							.seqNo(layoutAnn.seqNo() >= 0 ? layoutAnn.seqNo() : defaultSeqNo)
							.build())
					.collect(GuavaCollectors.toImmutableMapByKey(ClassViewColumnLayoutDescriptor::getViewType));
		}
		else if (defaultSeqNo >= 0)
		{
			return Stream.of(JSONViewDataType.values())
					.map(viewType -> ClassViewColumnLayoutDescriptor.builder()
							.viewType(viewType)
							.displayed(viewColumnAnn.displayed())
							.seqNo(defaultSeqNo)
							.build())
					.collect(GuavaCollectors.toImmutableMapByKey(ClassViewColumnLayoutDescriptor::getViewType));
		}
		else
		{
			return ImmutableMap.of();
		}
	}

	private static boolean extractDisplayedValue(
			@NonNull final ViewColumnLayout viewColumnLayout,
			@NonNull final String fieldName)
	{
		if (viewColumnLayout.displayed() == Displayed.FALSE)
		{
			return false;
		}
		else if (viewColumnLayout.displayed() == Displayed.SYSCONFIG)
		{
			final String displayedSysConfigPrefix = viewColumnLayout.displayedSysConfigPrefix();
			if (Check.isEmpty(displayedSysConfigPrefix, true))
			{
				return viewColumnLayout.defaultDisplaySysConfig();
			}
			final String sysConfigKey = StringUtils.appendIfNotEndingWith(displayedSysConfigPrefix, ".") + fieldName + ".IsDisplayed";

			return Services.get(ISysConfigBL.class)
					.getBooleanValue(
							sysConfigKey,
							viewColumnLayout.defaultDisplaySysConfig(),
							Env.getAD_Client_ID(),
							Env.getAD_Org_ID(Env.getCtx()));
		}
		else if (viewColumnLayout.displayed() == Displayed.TRUE)
		{
			return true;
		}
		Check.fail("ViewColumnLayout.displayed value={}; viewColumnLayout={}", viewColumnLayout.displayed(), viewColumnLayout);
		return false;
	}

	private static DocumentLayoutElementDescriptor.Builder createLayoutElement(final ClassViewColumnDescriptor column)
	{
		return DocumentLayoutElementDescriptor.builder()
				.setGridElement()
				.setCaption(column.getCaption())
				.setWidgetType(column.getWidgetType())
				.setWidgetSize(column.getWidgetSize())
				.setViewEditorRenderMode(column.getEditorRenderMode())
				.setViewAllowSorting(column.isAllowSorting())
				.restrictToMediaTypes(column.getRestrictToMediaTypes())
				.addField(DocumentLayoutElementFieldDescriptor.builder(column.getFieldName()));
	}

	public static <T extends IViewRow> ImmutableSet<String> extractFieldNames(@NonNull final T row)
	{
		final Class<? extends IViewRow> rowClass = row.getClass();
		return extractFieldNames(rowClass);
	}

	public static <T extends IViewRow> ImmutableSet<String> extractFieldNames(@NonNull final Class<T> rowType)
	{
		return getDescriptor(rowType).getFieldNames();
	}

	/**
	 * This helper method is intended to support individual implementations of {@link IViewRow#getFieldNameAndJsonValues()}.
	 * <b>
	 * Note that the individual fields maybe be {@code instanceof} {@link Supplier}.
	 * Useful because if a field is configured not to be shown at all, then the respective supplier won't be invoked.
	 */
	public static <T extends IViewRow> ViewRowFieldNameAndJsonValues extractJsonMap(@NonNull final T row)
	{
		final Class<? extends IViewRow> rowClass = row.getClass();
		final ImmutableMap<String, Object> map = getDescriptor(rowClass)
				.streamColumns()
				.map(column -> extractFieldNameAndValueAsJsonObject(row, column))
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toImmutableMap());

		return ViewRowFieldNameAndJsonValues.ofMap(map);
	}

	private static <T extends IViewRow> Map.Entry<String, Object> extractFieldNameAndValueAsJsonObject(
			@NonNull final T row,
			@NonNull final ClassViewColumnDescriptor column)
	{
		final Object value = extractFieldValueAsJsonObject(row, column);
		if (JSONNullValue.isNull(value))
		{
			return null;
		}

		return GuavaCollectors.entry(column.getFieldName(), value);
	}

	private static <T extends IViewRow> Object extractFieldValueAsJsonObject(
			@NonNull final T row,
			@NonNull final ClassViewColumnDescriptor column)
	{
		try
		{
			final Field field = column.getField();
			if (!field.isAccessible())
			{
				field.setAccessible(true);
			}

			final Object value = field.get(row);
			if (value instanceof Supplier<?>)
			{
				final Supplier<?> supplier = (Supplier<?>)value;
				return normalizeAndResolveValue(supplier.get(), column);
			}
			else
			{
				return normalizeAndResolveValue(value, column);
			}
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("column", column)
					.setParameter("row", row);
		}
	}

	private static Object normalizeAndResolveValue(
			@Nullable final Object valueParam,
			@NonNull final ClassViewColumnDescriptor column)
	{
		Object result = valueParam;

		if (column.getWidgetType().isLookup())
		{
			if (column.getListReferenceId() > 0)
			{
				result = resolveListValueByCode(column.getListReferenceId(), result);
			}
		}

		if (result == null)
		{
			return JSONNullValue.instance;
		}

		return result;
	}

	private static LookupValue resolveListValueByCode(final int listReferenceId, final Object code)
	{
		if (code == null)
		{
			return null;
		}

		if (Adempiere.isUnitTestMode())
		{
			if (code instanceof ReferenceListAwareEnum)
			{
				return StringLookupValue.of(((ReferenceListAwareEnum)code).getCode(), code.toString());
			}
			else
			{
				return StringLookupValue.unknown(code.toString());
			}
		}

		final LookupValue lookupValue = LookupDataSourceFactory.instance.listByAD_Reference_Value_ID(listReferenceId).findById(code);
		if (lookupValue == null)
		{
			return StringLookupValue.unknown(code.toString());
		}
		else
		{
			return lookupValue;
		}
	}

	public static <T extends IViewRow> ImmutableMap<String, DocumentFieldWidgetType> getWidgetTypesByFieldName(@NonNull final Class<T> rowClass)
	{
		return getDescriptor(rowClass).getWidgetTypesByFieldName();
	}

	@ToString
	@EqualsAndHashCode
	private static final class ClassViewDescriptor
	{
		public static final ClassViewDescriptor EMPTY = builder().build();

		private final ImmutableMap<String, ClassViewColumnDescriptor> columnsByName;
		@Getter
		private final ImmutableMap<String, DocumentFieldWidgetType> widgetTypesByFieldName;

		@Builder
		private ClassViewDescriptor(@Singular final ImmutableList<ClassViewColumnDescriptor> columns)
		{
			columnsByName = Maps.uniqueIndex(columns, ClassViewColumnDescriptor::getFieldName);
			widgetTypesByFieldName = columns.stream()
					.collect(ImmutableMap.toImmutableMap(ClassViewColumnDescriptor::getFieldName, ClassViewColumnDescriptor::getWidgetType));
		}

		public ImmutableSet<String> getFieldNames()
		{
			return columnsByName.keySet();
		}

		public Stream<ClassViewColumnDescriptor> streamColumns()
		{
			return columnsByName.values().stream();
		}

		public ClassViewColumnDescriptor getColumnByName(@NonNull final String fieldName)
		{
			final ClassViewColumnDescriptor column = columnsByName.get(fieldName);
			if (column == null)
			{
				throw new AdempiereException("No column found for " + fieldName + " in " + this);
			}
			return column;
		}
	}

	@Value
	@Builder(toBuilder = true)
	private static final class ClassViewColumnDescriptor
	{
		@NonNull
		private final String fieldName;
		@NonNull
		@Getter(AccessLevel.NONE)
		private final FieldReference fieldReference;

		@NonNull
		private final ITranslatableString caption;
		@NonNull
		private final DocumentFieldWidgetType widgetType;

		private final int listReferenceId;

		@Nullable
		private final WidgetSize widgetSize;
		@NonNull
		private final ViewEditorRenderMode editorRenderMode;
		private final boolean allowSorting;
		@NonNull
		private final ImmutableMap<JSONViewDataType, ClassViewColumnLayoutDescriptor> layoutsByViewType;
		@NonNull
		private final ImmutableSet<MediaType> restrictToMediaTypes;

		public boolean isDisplayed(final JSONViewDataType viewType)
		{
			final ClassViewColumnLayoutDescriptor layout = layoutsByViewType.get(viewType);
			return layout != null && layout.isDisplayed();
		}

		public int getSeqNo(final JSONViewDataType viewType)
		{
			final ClassViewColumnLayoutDescriptor layout = layoutsByViewType.get(viewType);
			if (layout == null)
			{
				return Integer.MAX_VALUE;
			}

			final int seqNo = layout.getSeqNo();
			return seqNo >= 0 ? seqNo : Integer.MAX_VALUE;
		}

		public Field getField()
		{
			return fieldReference.getField();
		}
	}

	@Value
	@Builder
	private static final class ClassViewColumnLayoutDescriptor
	{
		@NonNull
		private JSONViewDataType viewType;
		private final boolean displayed;
		private final int seqNo;
	}
}
