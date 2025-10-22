package org.adempiere.mm.attributes.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.adempiere.util.cache.annotations.CacheSkipIfNotNull;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.javaclasses.JavaClassId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.GuavaCollectors;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import de.metas.util.lang.SeqNo;
import lombok.NonNull;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.expression.api.IStringExpression;
import org.adempiere.ad.expression.api.impl.StringExpressionCompiler;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeListValueTrxRestriction;
import org.adempiere.mm.attributes.AttributeSetAttribute;
import org.adempiere.mm.attributes.AttributeSetDescriptor;
import org.adempiere.mm.attributes.AttributeSetDescriptorsCollection;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetMandatoryType;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.mm.attributes.api.Attribute;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.AttributeValuesOrderByType;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.model.X_M_Attribute;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AttributeDAO implements IAttributeDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, AttributesMap> attributesMapCache = CCache.<Integer, AttributesMap>builder()
			.tableName(I_M_Attribute.Table_Name)
			.build();

	private final CCache<AttributeSetId, AttributeSetDescriptor> attributeSetAttributeIdsListsCache = CCache.<AttributeSetId, AttributeSetDescriptor>builder()
			.tableName(I_M_AttributeSet.Table_Name)
			.additionalTableNameToResetFor(I_M_AttributeUse.Table_Name)
			.additionalTableNameToResetFor(I_M_Attribute.Table_Name)
			.build();

	@Override
	public Attribute getAttributeById(final AttributeId id)
	{
		return getAttributesMap().getById(id);
	}

	@Override
	public Collection<Attribute> getAttributesByIds(final Collection<AttributeId> attributeIds)
	{
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return getAttributesMap().getByIds(attributeIds);
	}

	@Override
	public Collection<Attribute> getAttributesByCodes(final Set<AttributeCode> attributeCodes)
	{
		if (attributeCodes.isEmpty())
		{
			return ImmutableList.of();
		}

		return getAttributesMap().getByCodes(attributeCodes);
	}

	@Override
	public AttributeSetDescriptor getAttributeSetDescriptorById(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone()) {return AttributeSetDescriptor.NONE;}
		return attributeSetAttributeIdsListsCache.getOrLoad(attributeSetId, this::retrieveAttributeIdsByAttributeSetId);
	}

	@Override
	public AttributeSetDescriptorsCollection getAttributeSetDescriptorsByIds(@NonNull final Set<AttributeSetId> attributeSetIds)
	{
		if (attributeSetIds.isEmpty())
		{
			return AttributeSetDescriptorsCollection.EMPTY;
		}

		return AttributeSetDescriptorsCollection.of(attributeSetAttributeIdsListsCache.getAllOrLoad(attributeSetIds, this::retrieveAttributeIdsByAttributeSetIds));
	}

	@NonNull
	private AttributeSetDescriptor retrieveAttributeIdsByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		return retrieveAttributeIdsByAttributeSetIds(ImmutableSet.of(attributeSetId)).get(attributeSetId);
	}

	@NonNull
	private Map<AttributeSetId, AttributeSetDescriptor> retrieveAttributeIdsByAttributeSetIds(@NonNull final Set<AttributeSetId> attributeSetIds)
	{
		if (attributeSetIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		final AttributesMap attributesMap = getAttributesMap();

		final ImmutableMap<AttributeSetId, I_M_AttributeSet> recordsById = queryBL.createQueryBuilderOutOfTrx(I_M_AttributeSet.class)
				.addInArrayFilter(I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID, attributeSetIds)
				.orderBy(I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID)
				.stream()
				.collect(GuavaCollectors.toImmutableMapByKey(record -> AttributeSetId.ofRepoId(record.getM_AttributeSet_ID())));

		final ImmutableListMultimap<AttributeSetId, I_M_AttributeUse> attributeAssignmentRecordsByAttributeSetId = queryBL.createQueryBuilderOutOfTrx(I_M_AttributeUse.class)
				.addOnlyActiveRecordsFilter()
				.addInArrayFilter(I_M_AttributeUse.COLUMNNAME_M_AttributeSet_ID, attributeSetIds)
				.create()
				.stream()
				.filter(record -> attributesMap.isActiveAttribute(AttributeId.ofRepoId(record.getM_Attribute_ID())))
				.collect(ImmutableListMultimap.toImmutableListMultimap(
						record -> AttributeSetId.ofRepoId(record.getM_AttributeSet_ID()),
						record -> record));

		return attributeSetIds.stream()
				.map(attributeSetId -> {
					if (attributeSetId.isNone()) {return AttributeSetDescriptor.NONE;}

					final I_M_AttributeSet record = recordsById.get(attributeSetId);
					if (record == null) {return null;}

					return fromRecord(record, attributeAssignmentRecordsByAttributeSetId, attributesMap);
				})
				.filter(Objects::nonNull)
				.collect(GuavaCollectors.toHashMapByKey(AttributeSetDescriptor::getAttributeSetId));
	}

	private static AttributeSetDescriptor fromRecord(
			@NonNull final I_M_AttributeSet record,
			@NonNull final ImmutableListMultimap<AttributeSetId, I_M_AttributeUse> attributeAssignmentRecordsByAttributeSetId,
			@NonNull final AttributesMap attributesMap)
	{
		final AttributeSetId attributeSetId = AttributeSetId.ofRepoId(record.getM_AttributeSet_ID());

		return AttributeSetDescriptor.builder()
				.attributeSetId(attributeSetId)
				.name(record.getName())
				.description(record.getDescription())
				.isInstanceAttribute(record.isInstanceAttribute())
				.mandatoryType(AttributeSetMandatoryType.ofCode(record.getMandatoryType()))
				.attributes(attributeAssignmentRecordsByAttributeSetId.get(attributeSetId)
						.stream()
						.map(attributeAssignmentRecord -> fromRecord(attributeAssignmentRecord, attributesMap))
						.filter(Objects::nonNull)
						.collect(ImmutableList.toImmutableList()))
				.build();
	}

	@Nullable
	private static AttributeSetAttribute fromRecord(@NonNull final I_M_AttributeUse record, @NonNull final AttributesMap attributesMap)
	{
		final AttributeId attributeId = AttributeId.ofRepoId(record.getM_Attribute_ID());
		final Attribute attribute = attributesMap.getByIdOrNull(attributeId);
		if (attribute == null || !attribute.isActive()) {return null;}

		return AttributeSetAttribute.builder()
				.attribute(attribute)
				.seqNo(SeqNo.ofInt(record.getSeqNo()))
				.mandatoryOnReceipt(OptionalBoolean.ofNullableString(record.getMandatoryOnReceipt()))
				.mandatoryOnPicking(OptionalBoolean.ofNullableString(record.getMandatoryOnPicking()))
				.mandatoryOnShipment(OptionalBoolean.ofNullableString(record.getMandatoryOnShipment()))
				.build();
	}

	@Override
	public I_M_Attribute getAttributeRecordById(final int attributeId)
	{
		// assume table level caching is enabled
		return InterfaceWrapperHelper.loadOutOfTrx(attributeId, I_M_Attribute.class);
	}

	@Override
	public I_M_Attribute getAttributeRecordById(@NonNull final AttributeId attributeId)
	{
		return getAttributeRecordById(attributeId, I_M_Attribute.class);
	}

	@Override
	public <T extends I_M_Attribute> T getAttributeRecordById(@NonNull final AttributeId attributeId, @NonNull final Class<T> type)
	{
		// assume table level caching is enabled
		return InterfaceWrapperHelper.loadOutOfTrx(attributeId, type);
	}

	@Override
	public List<I_M_Attribute> getAttributeRecordsByIds(final Collection<AttributeId> attributeIds)
	{
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return loadByRepoIdAwaresOutOfTrx(attributeIds, I_M_Attribute.class);
	}

	@Override
	public List<Attribute> getAttributesByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		final List<AttributeId> attributeIds = getAttributeSetDescriptorById(attributeSetId).getAttributeIdsInOrder();
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final Set<Attribute> attributes = getAttributesMap().getByIds(attributeIds);

		// preserve the M_AttributeUse order!
		final FixedOrderByKeyComparator<Attribute, AttributeId> order = FixedOrderByKeyComparator.notMatchedAtTheEnd(attributeIds, Attribute::getAttributeId);

		return attributes.stream()
				.filter(Attribute::isActive)
				.sorted(order)
				.collect(ImmutableList.toImmutableList());
	}

	private List<I_M_Attribute> getAttributeRecordsByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		final List<AttributeId> attributeIds = getAttributeSetDescriptorById(attributeSetId).getAttributeIdsInOrder();
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final List<I_M_Attribute> attributes = getAttributeRecordsByIds(attributeIds);

		// preserve the M_AttributeUse order!
		final FixedOrderByKeyComparator<I_M_Attribute, AttributeId> order = FixedOrderByKeyComparator.notMatchedAtTheEnd(attributeIds, attribute -> AttributeId.ofRepoId(attribute.getM_Attribute_ID()));

		return attributes.stream()
				.filter(I_M_Attribute::isActive)
				.sorted(order)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public String getAttributeCodeById(final AttributeId attributeId)
	{
		final I_M_Attribute attribute = getAttributeRecordById(attributeId);
		return attribute.getValue();
	}

	@Override
	@NonNull
	public ImmutableList<AttributeCode> getOrderedAttributeCodesByIds(@NonNull final List<AttributeId> orderedAttributeIds)
	{
		return getAttributesMap().getOrderedAttributeCodesByIds(orderedAttributeIds);
	}

	@Override
	public <T extends I_M_Attribute> T retrieveAttributeByValue(@NonNull final AttributeCode attributeCode, @NonNull final Class<T> clazz)
	{
		final AttributeId attributeId = getAttributesMap().getIdByCode(attributeCode);
		final I_M_Attribute attribute = getAttributeRecordById(attributeId);
		return InterfaceWrapperHelper.create(attribute, clazz);
	}

	@Override
	public @NotNull Attribute getAttributeByCode(@NonNull final AttributeCode attributeCode)
	{
		return getAttributesMap().getByCode(attributeCode);
	}

	@Override
	@Nullable
	public <T extends I_M_Attribute> T retrieveActiveAttributeByValueOrNull(@NonNull final AttributeCode attributeCode, @NonNull final Class<T> clazz)
	{
		final Attribute attribute = getAttributesMap().getByCodeOrNull(attributeCode);
		if (attribute == null || !attribute.isActive())
		{
			return null;
		}

		return InterfaceWrapperHelper.create(getAttributeRecordById(attribute.getAttributeId()), clazz);
	}

	@Override
	public Optional<ITranslatableString> getAttributeDisplayNameByValue(@NonNull final String value)
	{
		return getAttributeDisplayNameByValue(AttributeCode.ofString(value));
	}

	@Override
	public Optional<ITranslatableString> getAttributeDisplayNameByValue(@NonNull final AttributeCode attributeCode)
	{
		final Attribute attribute = getAttributesMap().getByCodeOrNull(attributeCode);
		return attribute != null
				? Optional.of(attribute.getDisplayName())
				: Optional.empty();
	}

	@Override
	public Optional<ITranslatableString> getAttributeDescriptionByValue(@NonNull final String value)
	{
		final AttributeCode attributeCode = AttributeCode.ofString(value);
		final Attribute attribute = getAttributesMap().getByCodeOrNull(attributeCode);
		return attribute != null
				? Optional.ofNullable(attribute.getDescription())
				: Optional.empty();
	}

	@Override
	@NonNull
	public AttributeId getAttributeIdByCode(final AttributeCode attributeCode)
	{
		return getAttributesMap().getIdByCode(attributeCode);
	}

	@Override
	@Nullable
	public AttributeId retrieveActiveAttributeIdByValueOrNull(final AttributeCode attributeCode)
	{
		final Attribute attribute = getAttributesMap().getByCodeOrNull(attributeCode);
		return attribute != null && attribute.isActive() ? attribute.getAttributeId() : null;
	}

	private AttributesMap getAttributesMap()
	{
		return attributesMapCache.getOrLoad(0, this::retrieveAttributesMap);
	}

	private AttributesMap retrieveAttributesMap()
	{
		final ImmutableList<Attribute> attributes = queryBL.createQueryBuilderOutOfTrx(I_M_Attribute.class)
				//.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(AttributeDAO::fromRecord)
				.collect(ImmutableList.toImmutableList());

		return new AttributesMap(attributes);
	}

	public static Attribute fromRecord(final I_M_Attribute record)
	{
		final IModelTranslationMap trls = InterfaceWrapperHelper.getModelTranslationMap(record);

		return Attribute.builder()
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.attributeCode(AttributeCode.ofString(record.getValue()))
				.valueType(AttributeValueType.ofCode(record.getAttributeValueType()))
				.displayName(trls.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, record.getName()))
				.description(trls.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, record.getDescription()))
				.descriptionPattern(extractDescriptionPattern(record))
				//
				.defaultValueSQL(extractDefaultValueSQL(record))
				//
				.isActive(record.isActive())
				.isInstanceAttribute(record.isInstanceAttribute())
				.isMandatory(record.isMandatory())
				.isReadOnlyValues(record.isReadOnlyValues())
				.isPricingRelevant(record.isPricingRelevant())
				.isStorageRelevant(record.isStorageRelevant())
				//
				.uomId(UomId.ofRepoIdOrNull(record.getC_UOM_ID()))
				//
				.isHighVolume(record.isHighVolume())
				.listValuesProviderJavaClassId(extractListValuesProviderJavaClassId(record))
				.listValRuleId(AdValRuleId.ofRepoIdOrNull(record.getAD_Val_Rule_ID()))
				.listOrderBy(AttributeValuesOrderByType.ofNullableCode(record.getAttributeValuesOrderBy()))
				.build();
	}

	@Nullable
	private static IStringExpression extractDefaultValueSQL(final I_M_Attribute record)
	{
		final String defaultValueSQL = StringUtils.trimBlankToNull(record.getDefaultValueSQL());
		if (defaultValueSQL == null)
		{
			return null;
		}

		try
		{
			return IStringExpression.compile(defaultValueSQL);
		}
		catch (final Exception ex)
		{
			throw new AdempiereException("Invalid attribute's DefaultValueSQL", ex)
					.setParameter("attribute", record)
					.setParameter("DefaultValueSQL", record.getDefaultValueSQL());
		}
	}

	@Nullable
	public static JavaClassId extractListValuesProviderJavaClassId(final I_M_Attribute record)
	{
		return JavaClassId.ofRepoIdOrNull(record.getAD_JavaClass_ID());
	}

	private static IStringExpression extractDescriptionPattern(final I_M_Attribute record)
	{
		final String descriptionPatternStr = record.getDescriptionPattern();
		return Check.isBlank(descriptionPatternStr)
				? null
				: StringExpressionCompiler.instance.compileOrDefault(descriptionPatternStr, null);
	}

	@Override
	public List<Attribute> getAllAttributes()
	{
		return getAttributesMap().streamActive()
				.sorted(Attribute.ORDER_BY_DisplayName)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValues(final I_M_Attribute attribute)
	{
		return retrieveAttributeValuesMap(attribute, false/* includeInactive */)
				.toList();
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValues(final Attribute attribute)
	{
		final I_M_Attribute attributeRecord = getAttributeRecordById(attribute.getAttributeId());
		return retrieveAttributeValues(attributeRecord);
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValuesByAttributeId(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attributeRecord = getAttributeRecordById(attributeId);
		return retrieveAttributeValues(attributeRecord);
	}

	public List<AttributeListValue> retrieveAttributeValuesByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone())
		{
			return ImmutableList.of();
		}

		final ImmutableList.Builder<AttributeListValue> result = ImmutableList.builder();

		for (final I_M_Attribute attributeRecord : getAttributeRecordsByAttributeSetId(attributeSetId))
		{
			final List<AttributeListValue> listValues = retrieveAttributeValues(attributeRecord);
			result.addAll(listValues);
		}
		return result.build();
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValuesByIds(@NonNull final Collection<AttributeValueId> attributeValueIds)
	{
		if (attributeValueIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_M_AttributeValue.class)
				.addInArrayFilter(I_M_AttributeValue.COLUMN_M_AttributeValue_ID, attributeValueIds)
				.orderBy(I_M_AttributeValue.COLUMN_M_AttributeValue_ID)
				.create()
				.stream()
				.map(AttributeDAO::toAttributeListValue)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final AttributeId attributeId, final String value)
	{
		final I_M_Attribute attributeRecord = getAttributeRecordById(attributeId);
		return retrieveAttributeValueOrNull(attributeRecord, value);
	}

	@Nullable
	@Override
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final Attribute attribute, final String value)
	{
		final I_M_Attribute attributeRecord = getAttributeRecordById(attribute.getAttributeId());
		return retrieveAttributeValueOrNull(attributeRecord, value);
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final I_M_Attribute attribute, final String value)
	{
		final boolean includeInactive = false;
		return retrieveAttributeValueOrNull(attribute, value, includeInactive);
	}

	@Nullable
	@Override
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final Attribute attribute, final String value, final boolean includeInactive)
	{
		final I_M_Attribute attributeRecord = getAttributeRecordById(attribute.getAttributeId());
		return retrieveAttributeValueOrNull(attributeRecord, value, includeInactive);
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final I_M_Attribute attribute, final String value, final boolean includeInactive)
	{
		//
		// In case we are dealing with a high-volume attribute values set, we cannot fetch all of them,
		// but better to go directly and query.
		if (isHighVolumeValuesList(attribute))
		{
			final I_M_AttributeValue attributeListValueRecord = queryBL
					.createQueryBuilder(I_M_AttributeValue.class, attribute)
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_Attribute_ID, attribute.getM_Attribute_ID())
					.addEqualsFilter(I_M_AttributeValue.COLUMN_Value, value)
					.create()
					.firstOnly(I_M_AttributeValue.class);

			return attributeListValueRecord != null
					? toAttributeListValue(attributeListValueRecord)
					: null;
		}
		else
		{
			final AttributeListValueMap map = retrieveAttributeValuesMap(attribute, includeInactive);
			return map.getByValueOrNull(value);
		}
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(
			@NonNull final AttributeId attributeId,
			@NonNull final AttributeValueId attributeValueId)
	{
		final Attribute attribute = getAttributeById(attributeId);
		return retrieveAttributeValueOrNull(attribute, attributeValueId);
	}

	@Nullable
	@Override
	public AttributeListValue retrieveAttributeValueOrNull(
			@NonNull final Attribute attribute,
			@NonNull final AttributeValueId attributeValueId)
	{
		//
		// In case we are dealing with a high-volume attribute values set, we cannot fetch all of them,
		// but better to go directly and query.
		if (attribute.isHighVolumeValuesList())
		{
			final I_M_AttributeValue attributeListValueRecord = queryBL
					.createQueryBuilder(I_M_AttributeValue.class, attribute)
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_Attribute_ID, attribute.getAttributeId())
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_AttributeValue_ID, attributeValueId)
					.create()
					.firstOnly(I_M_AttributeValue.class);
			return attributeListValueRecord != null
					? toAttributeListValue(attributeListValueRecord)
					: null;
		}
		else
		{
			final I_M_Attribute attributeRecord = getAttributeRecordById(attribute.getAttributeId());
			return retrieveAttributeValuesMap(attributeRecord, false/* includeInactive */)
					.getByIdOrNull(attributeValueId);
		}
	}

	private boolean isHighVolumeValuesList(@NonNull final I_M_Attribute attribute)
	{
		if (!X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			return false;
		}

		return attribute.isHighVolume();
	}

	private AttributeListValueMap retrieveAttributeValuesMap(@NonNull final I_M_Attribute attribute, final boolean includeInactive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);

		//
		// 07708: Apply AD_Val_Rule when filtering attributes for the current context
		final ValidationRuleQueryFilter<I_M_AttributeValue> validationRuleQueryFilter;
		final AdValRuleId adValRuleId = AdValRuleId.ofRepoIdOrNull(attribute.getAD_Val_Rule_ID());
		if (adValRuleId != null)
		{
			validationRuleQueryFilter = new ValidationRuleQueryFilter<>(attribute, adValRuleId);
		}
		else
		{
			validationRuleQueryFilter = null;
		}

		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());
		final String orderByColumnName = Optional.ofNullable(attribute.getAttributeValuesOrderBy())
				.map(AttributeValuesOrderByType::ofCode)
				.map(Enum::name)
				.orElse(I_M_AttributeValue.COLUMNNAME_Value);  // order attributes by value, so we can have names like breakfast, lunch, dinner in their "temporal" order

		return retrieveAttributeValuesMap(ctx, attributeId, includeInactive, validationRuleQueryFilter, orderByColumnName);
	}

	@Cached(cacheName = I_M_AttributeValue.Table_Name
			+ "#by#" + I_M_AttributeValue.COLUMNNAME_M_Attribute_ID)
	AttributeListValueMap retrieveAttributeValuesMap(
			@CacheCtx final Properties ctx,
			@NonNull final AttributeId attributeId,
			final boolean includeInactive,
			// NOTE: we are caching this method only if we don't have a filter.
			// If we have a filter:
			// * that's mutable, so it will screw up our case
			// * in most of the cases, when we have a validation rule filter, we are dealing with a huge amount of data which needs to be filtered (see Karotten ID example from)
			@CacheSkipIfNotNull final ValidationRuleQueryFilter<I_M_AttributeValue> validationRuleQueryFilter,
			@NonNull final String orderByColumnName)
	{
		final IQueryBuilder<I_M_AttributeValue> queryBuilder = queryBL.createQueryBuilder(I_M_AttributeValue.class, ctx, ITrx.TRXNAME_None);

		final ICompositeQueryFilter<I_M_AttributeValue> filters = queryBuilder.getCompositeFilter();
		if (!includeInactive)
		{
			filters.addOnlyActiveRecordsFilter();
		}
		filters.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_M_Attribute_ID, attributeId);

		if (validationRuleQueryFilter != null)
		{
			queryBuilder.filter(validationRuleQueryFilter);
		}

		final List<AttributeListValue> list = queryBuilder
				.orderBy(orderByColumnName)
				.create()
				.stream()
				.map(AttributeDAO::toAttributeListValue)
				.collect(ImmutableList.toImmutableList());

		return AttributeListValueMap.ofList(list);
	}

	@VisibleForTesting
	public static AttributeListValue toAttributeListValue(final I_M_AttributeValue record)
	{
		return AttributeListValue.builder()
				.id(AttributeValueId.ofRepoId(record.getM_AttributeValue_ID()))
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.value(record.getValue())
				.name(record.getName())
				.description(record.getDescription())
				.availableForTrx(AttributeListValueTrxRestriction.ofCode(record.getAvailableTrx()))
				.active(record.isActive())
				.nullFieldValue(record.isNullFieldValue())
				.build();
	}

	@Override
	public Set<String> retrieveAttributeValueSubstitutes(final I_M_Attribute attribute, final String value)
	{
		final AttributeListValue attributeValue = retrieveAttributeValueOrNull(attribute, value);
		if (attributeValue == null)
		{
			return ImmutableSet.of();
		}

		final AttributeValueId attributeValueId = attributeValue.getId();
		return retrieveAttributeValueSubstitutes(attributeValueId);
	}

	@Cached(cacheName = I_M_AttributeValue_Mapping.Table_Name + "#by#" + I_M_AttributeValue_Mapping.COLUMNNAME_M_AttributeValue_To_ID + "#StringSet")
	Set<String> retrieveAttributeValueSubstitutes(final AttributeValueId attributeValueId)
	{
		final List<AttributeValueId> attributeValueSubstitutesIds = queryBL.createQueryBuilderOutOfTrx(I_M_AttributeValue_Mapping.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_AttributeValue_Mapping.COLUMNNAME_M_AttributeValue_To_ID, attributeValueId)
				.create()
				.listDistinct(I_M_AttributeValue_Mapping.COLUMNNAME_M_AttributeValue_ID, Integer.class)
				.stream()
				.map(AttributeValueId::ofRepoId)
				.collect(ImmutableList.toImmutableList());

		final Set<String> substitutes = new HashSet<>();
		for (final AttributeListValue attributeValueSubstitute : retrieveAttributeValuesByIds(attributeValueSubstitutesIds))
		{
			final String substituteValue = attributeValueSubstitute.getValue();
			substitutes.add(substituteValue);
		}

		return Collections.unmodifiableSet(substitutes);
	}

	@Override
	public AttributeListValue createAttributeValue(@NonNull final AttributeListValueCreateRequest request)
	{
		final I_M_AttributeValue record = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_AttributeValue.class);
		record.setIsActive(request.isActive());
		record.setM_Attribute_ID(request.getAttributeId().getRepoId());
		record.setValue(request.getValue());
		record.setName(request.getName());
		record.setDescription(request.getDescription());
		record.setAvailableTrx(request.getAvailableForTrx().getCode());
		saveRecord(record);

		return toAttributeListValue(record);
	}

	@Override
	@SuppressWarnings("OptionalAssignedToNull")
	public AttributeListValue changeAttributeValue(@NonNull final AttributeListValueChangeRequest request)
	{
		final I_M_AttributeValue record = loadOutOfTrx(request.getId(), I_M_AttributeValue.class);
		if (request.getActive() != null)
		{
			record.setIsActive(request.getActive());
		}
		if (request.getValue() != null)
		{
			record.setValue(request.getValue().orElse(null));
		}
		if (request.getName() != null)
		{
			record.setName(request.getName());
		}
		if (request.getDescription() != null)
		{
			record.setDescription(request.getDescription().orElse(null));
		}
		if (request.getAvailableForTrx() != null)
		{
			record.setAvailableTrx(request.getAvailableForTrx().getCode());
		}

		saveRecord(record);
		return toAttributeListValue(record);
	}

	@Override
	public void deleteAttributeValueByCode(@NonNull final AttributeId attributeId, @Nullable final String value)
	{
		queryBL.createQueryBuilder(I_M_AttributeValue.class)
				.addEqualsFilter(I_M_AttributeValue.COLUMN_M_Attribute_ID, attributeId)
				.addEqualsFilter(I_M_AttributeValue.COLUMNNAME_Value, value)
				.create()
				.delete();
	}

	@Override
	public List<Attribute> retrieveAttributes(final AttributeSetId attributeSetId, final boolean isInstanceAttribute)
	{
		return getAttributesByAttributeSetId(attributeSetId)
				.stream()
				.filter(attribute -> attribute.isInstanceAttribute() == isInstanceAttribute)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public boolean containsAttribute(@NonNull final AttributeSetId attributeSetId, @NonNull final AttributeId attributeId)
	{
		return getAttributeSetDescriptorById(attributeSetId).contains(attributeId);
	}

	@Override
	@Nullable
	public I_M_Attribute retrieveAttribute(final AttributeSetId attributeSetId, final AttributeId attributeId)
	{
		if (!containsAttribute(attributeSetId, attributeId))
		{
			return null;
		}

		return getAttributeRecordById(attributeId);
	}

	private static final class AttributeListValueMap
	{
		public static AttributeListValueMap ofList(final List<AttributeListValue> list)
		{
			if (list.isEmpty())
			{
				return EMPTY;
			}

			return new AttributeListValueMap(list);
		}

		private static final AttributeListValueMap EMPTY = new AttributeListValueMap();

		private final ImmutableMap<String, AttributeListValue> map;

		private AttributeListValueMap()
		{
			map = ImmutableMap.of();
		}

		private AttributeListValueMap(@NonNull final List<AttributeListValue> list)
		{
			map = Maps.uniqueIndex(list, AttributeListValue::getValue);
		}

		public List<AttributeListValue> toList()
		{
			return ImmutableList.copyOf(map.values());
		}

		@Nullable
		public AttributeListValue getByIdOrNull(@NonNull final AttributeValueId id)
		{
			return map.values()
					.stream()
					.filter(av -> AttributeValueId.equals(av.getId(), id))
					.findFirst()
					.orElse(null);
		}

		public AttributeListValue getByValueOrNull(final String value)
		{
			return map.get(value);
		}
	}
}
