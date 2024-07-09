package org.adempiere.mm.attributes.api.impl;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.adempiere.util.cache.annotations.CacheSkipIfNotNull;
import de.metas.cache.CCache;
import de.metas.cache.annotation.CacheCtx;
import de.metas.i18n.IModelTranslationMap;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.Check;
import de.metas.util.OptionalBoolean;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.ToString;
import lombok.Value;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.impl.ValidationRuleQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.validationRule.AdValRuleId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeListValueTrxRestriction;
import org.adempiere.mm.attributes.AttributeSetAttribute;
import org.adempiere.mm.attributes.AttributeSetAttributeIdsList;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.api.AttributeListValueChangeRequest;
import org.adempiere.mm.attributes.api.AttributeListValueCreateRequest;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.comparator.FixedOrderByKeyComparator;
import org.adempiere.util.proxy.Cached;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeUse;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.model.X_M_Attribute;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.loadByRepoIdAwaresOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class AttributeDAO implements IAttributeDAO
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private final CCache<Integer, AttributesMap> attributesMapCache = CCache.<Integer, AttributesMap>builder()
			.tableName(I_M_Attribute.Table_Name)
			.build();

	private final CCache<AttributeSetId, AttributeSetAttributeIdsList> attributeSetAttributeIdsListsCache = CCache.<AttributeSetId, AttributeSetAttributeIdsList>builder()
			.tableName(I_M_AttributeUse.Table_Name)
			.build();

	@Override
	public void save(@NonNull final I_M_AttributeSetInstance asi)
	{
		saveRecord(asi);
	}

	@Override
	public void save(@NonNull final I_M_AttributeInstance ai)
	{
		saveRecord(ai);
	}

	@Override
	public I_M_AttributeSet getAttributeSetById(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone())
		{
			return retrieveNoAttributeSet();
		}
		else
		{
			return loadOutOfTrx(attributeSetId, I_M_AttributeSet.class);
		}
	}

	private AttributeSetAttributeIdsList getAttributeIdsByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone())
		{
			return AttributeSetAttributeIdsList.EMPTY;
		}

		return attributeSetAttributeIdsListsCache.getOrLoad(attributeSetId, this::retrieveAttributeIdsByAttributeSetId);
	}

	private AttributeSetAttributeIdsList retrieveAttributeIdsByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone())
		{
			return AttributeSetAttributeIdsList.EMPTY;
		}

		return queryBL
				.createQueryBuilderOutOfTrx(I_M_AttributeUse.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_AttributeUse.COLUMN_M_AttributeSet_ID, attributeSetId)
				.orderBy(I_M_AttributeUse.COLUMN_SeqNo)
				.orderBy(I_M_AttributeUse.COLUMN_M_AttributeUse_ID)
				.create()
				.list()
				.stream()
				.map(AttributeDAO::toAttributeSetAttribute)
				.collect(AttributeSetAttributeIdsList.collect());
	}

	private static AttributeSetAttribute toAttributeSetAttribute(final I_M_AttributeUse record)
	{
		return AttributeSetAttribute.builder()
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.seqNo(record.getSeqNo())
				.mandatoryOnReceipt(OptionalBoolean.ofNullableString(record.getMandatoryOnReceipt()))
				.mandatoryOnPicking(OptionalBoolean.ofNullableString(record.getMandatoryOnPicking()))
				.mandatoryOnShipment(OptionalBoolean.ofNullableString(record.getMandatoryOnShipment()))
				.mandatoryOnManufacturing(OptionalBoolean.ofNullableString(record.getMandatoryOnManufacturing()))
				.build();
	}

	@Override
	public I_M_Attribute getAttributeById(final int attributeId)
	{
		// assume table level caching is enabled
		return InterfaceWrapperHelper.loadOutOfTrx(attributeId, I_M_Attribute.class);
	}

	@Override
	public I_M_Attribute getAttributeById(@NonNull final AttributeId attributeId)
	{
		return getAttributeById(attributeId, I_M_Attribute.class);
	}

	@Override
	public <T extends I_M_Attribute> T getAttributeById(@NonNull final AttributeId attributeId, @NonNull final Class<T> type)
	{
		// assume table level caching is enabled
		return InterfaceWrapperHelper.loadOutOfTrx(attributeId, type);
	}

	@Override
	public List<I_M_Attribute> getAttributesByIds(final Collection<AttributeId> attributeIds)
	{
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}

		return loadByRepoIdAwaresOutOfTrx(attributeIds, I_M_Attribute.class);
	}

	@Override
	public List<I_M_Attribute> getAttributesByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		final List<AttributeId> attributeIds = getAttributeIdsByAttributeSetId(attributeSetId).getAttributeIdsInOrder();
		if (attributeIds.isEmpty())
		{
			return ImmutableList.of();
		}
		final List<I_M_Attribute> attributes = getAttributesByIds(attributeIds);

		// preserve the M_AttributeUse order!
		final FixedOrderByKeyComparator<I_M_Attribute, AttributeId> //
				order = FixedOrderByKeyComparator.notMatchedAtTheEnd(
				attributeIds,
				a -> AttributeId.ofRepoId(a.getM_Attribute_ID()));

		return attributes.stream()
				.filter(I_M_Attribute::isActive)
				.sorted(order)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public Set<AttributeId> getAttributeIdsByAttributeSetInstanceId(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMN_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.create()
				.listDistinct(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, Integer.class)
				.stream()
				.map(AttributeId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}

	@Override
	public String getAttributeCodeById(final AttributeId attributeId)
	{
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return attribute.getValue();
	}

	@Override
	public <T extends I_M_Attribute> T retrieveAttributeByValue(@NonNull final AttributeCode attributeCode, @NonNull final Class<T> clazz)
	{
		final AttributeId attributeId = getAttributesMap().getAttributeIdByCode(attributeCode);
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return InterfaceWrapperHelper.create(attribute, clazz);
	}

	@Override
	@Nullable
	public <T extends I_M_Attribute> T retrieveAttributeByValueOrNull(@NonNull final AttributeCode attributeCode, @NonNull final Class<T> clazz)
	{
		final AttributeId attributeId = getAttributesMap().getAttributeIdByCodeOrNull(attributeCode);
		if (attributeId == null)
		{
			return null;
		}
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return InterfaceWrapperHelper.create(attribute, clazz);
	}

	@Override
	public Optional<ITranslatableString> getAttributeDisplayNameByValue(@NonNull final String value)
	{
		return getAttributeDisplayNameByValue(AttributeCode.ofString(value));
	}

	@Override
	public Optional<ITranslatableString> getAttributeDisplayNameByValue(@NonNull final AttributeCode attributeCode)
	{
		final Attribute attribute = getAttributesMap().getAttributeByCodeOrNull(attributeCode);
		return attribute != null
				? Optional.of(attribute.getDisplayName())
				: Optional.empty();
	}

	@Override
	public Optional<ITranslatableString> getAttributeDescriptionByValue(@NonNull final String value)
	{
		final AttributeCode attributeCode = AttributeCode.ofString(value);
		final Attribute attribute = getAttributesMap().getAttributeByCodeOrNull(attributeCode);
		return attribute != null
				? Optional.ofNullable(attribute.getDescription())
				: Optional.empty();
	}

	@Override
	@NonNull
	public AttributeId getAttributeIdByCode(final AttributeCode attributeCode)
	{
		return getAttributesMap().getAttributeIdByCode(attributeCode);
	}

	@Override
	@Nullable
	public AttributeId retrieveAttributeIdByValueOrNull(final AttributeCode attributeCode)
	{
		return getAttributesMap().getAttributeIdByCodeOrNull(attributeCode);
	}

	private AttributesMap getAttributesMap()
	{
		return attributesMapCache.getOrLoad(0, this::retrieveAttributesMap);
	}

	private AttributesMap retrieveAttributesMap()
	{
		final ImmutableList<Attribute> attributes = queryBL.createQueryBuilderOutOfTrx(I_M_Attribute.class)
				.addOnlyActiveRecordsFilter()
				.create()
				.stream()
				.map(AttributeDAO::toAttribute)
				.collect(ImmutableList.toImmutableList());

		return new AttributesMap(attributes);
	}

	private static Attribute toAttribute(final I_M_Attribute record)
	{
		final IModelTranslationMap modelTranslationMap = InterfaceWrapperHelper.getModelTranslationMap(record);
		final ITranslatableString displayName = modelTranslationMap
				.getColumnTrl(I_M_Attribute.COLUMNNAME_Name, record.getName());

		final ITranslatableString description = modelTranslationMap
				.getColumnTrl(I_M_Attribute.COLUMNNAME_Description, record.getDescription());

		return Attribute.builder()
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.attributeCode(AttributeCode.ofString(record.getValue()))
				.displayName(displayName)
				.description(description)
				.build();
	}

	@Override
	public List<I_M_Attribute> getAllAttributes()
	{
		return queryBL
				.createQueryBuilderOutOfTrx(I_M_Attribute.class)
				.addOnlyActiveRecordsFilter()
				// .addOnlyContextClient()
				.orderBy(I_M_Attribute.COLUMNNAME_Name)
				.orderBy(I_M_Attribute.COLUMNNAME_M_Attribute_ID)
				.create()
				.list();
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValues(final I_M_Attribute attribute)
	{
		return retrieveAttributeValuesMap(attribute, false/* includeInactive */)
				.toList();
	}

	@Override
	public List<AttributeListValue> retrieveAttributeValuesByAttributeId(@NonNull final AttributeId attributeId)
	{
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return retrieveAttributeValues(attribute);
	}

	public List<AttributeListValue> retrieveAttributeValuesByAttributeSetId(@NonNull final AttributeSetId attributeSetId)
	{
		if (attributeSetId.isNone())
		{
			return ImmutableList.of();
		}
		
		final List<I_M_Attribute> attributeRecords = getAttributesByAttributeSetId(attributeSetId);
		final ImmutableList.Builder<AttributeListValue> result = ImmutableList.builder();

		for (final I_M_Attribute attributeRecord : attributeRecords)
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
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return retrieveAttributeValueOrNull(attribute, value);
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final I_M_Attribute attribute, final String value)
	{
		final boolean includeInactive = false;
		return retrieveAttributeValueOrNull(attribute, value, includeInactive);
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(@NonNull final I_M_Attribute attribute, final String value, final boolean includeInactive)
	{
		//
		// In case we are dealing with a high-volume attribute values set, we can not fetch all of them,
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
		final I_M_Attribute attribute = getAttributeById(attributeId);
		return retrieveAttributeValueOrNull(attribute, attributeValueId);
	}

	@Override
	@Nullable
	public AttributeListValue retrieveAttributeValueOrNull(
			@NonNull final I_M_Attribute attribute,
			@NonNull final AttributeValueId attributeValueId)
	{
		//
		// In case we are dealing with a high-volume attribute values set, we can not fetch all of them,
		// but better to go directly and query.
		if (isHighVolumeValuesList(attribute))
		{
			final I_M_AttributeValue attributeListValueRecord = queryBL
					.createQueryBuilder(I_M_AttributeValue.class, attribute)
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_Attribute_ID, attribute.getM_Attribute_ID())
					.addEqualsFilter(I_M_AttributeValue.COLUMN_M_AttributeValue_ID, attributeValueId)
					.create()
					.firstOnly(I_M_AttributeValue.class);
			return attributeListValueRecord != null
					? toAttributeListValue(attributeListValueRecord)
					: null;
		}
		else
		{
			return retrieveAttributeValuesMap(attribute, false/* includeInactive */)
					.getByIdOrNull(attributeValueId);
		}
	}

	@Override
	public boolean isHighVolumeValuesList(@NonNull final I_M_Attribute attribute)
	{
		if (!X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attribute.getAttributeValueType()))
		{
			return false;
		}

		return attribute.isHighVolume();
	}

	@Override
	public I_M_AttributeSetInstance getAttributeSetInstanceById(@NonNull final AttributeSetInstanceId attributeSetInstanceId)
	{
		return load(attributeSetInstanceId, I_M_AttributeSetInstance.class);
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstances(final AttributeSetInstanceId attributeSetInstanceId)
	{
		if (attributeSetInstanceId.isNone())
		{
			return ImmutableList.of();
		}

		final I_M_AttributeSetInstance asi = getAttributeSetInstanceById(attributeSetInstanceId);
		return retrieveAttributeInstances(asi);
	}

	@Override
	public List<I_M_AttributeInstance> retrieveAttributeInstances(final I_M_AttributeSetInstance attributeSetInstance)
	{
		if (attributeSetInstance == null)
		{
			return ImmutableList.of();
		}

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSetInstance_ID());
		if (asiId.isNone())
		{
			return ImmutableList.of();
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(attributeSetInstance);
		final String trxName = InterfaceWrapperHelper.getTrxName(attributeSetInstance);
		final ImmutableList<I_M_AttributeInstance> attributeInstances = retrieveAttributeInstances(ctx, asiId, trxName);
		if (attributeInstances.isEmpty())
		{
			return ImmutableList.of();
		}

		//
		// Ordering by M_AttributeUse.SeqNo
		final AttributeSetId attributeSetId = AttributeSetId.ofRepoIdOrNone(attributeSetInstance.getM_AttributeSet_ID());
		final Comparator<I_M_AttributeInstance> order = createAttributeInstanceOrderComparator(attributeSetId);

		return attributeInstances
				.stream()
				.sorted(order)
				.collect(ImmutableList.toImmutableList());
	}

	private Comparator<I_M_AttributeInstance> createAttributeInstanceOrderComparator(final AttributeSetId attributeSetId)
	{
		final List<AttributeId> attributeIds = getAttributeIdsByAttributeSetId(attributeSetId).getAttributeIdsInOrder();
		if (attributeIds.isEmpty())
		{
			return Comparator.comparing(I_M_AttributeInstance::getM_Attribute_ID);
		}

		return FixedOrderByKeyComparator.notMatchedAtTheEnd(
				attributeIds,
				ai -> AttributeId.ofRepoId(ai.getM_Attribute_ID()));
	}

	private ImmutableList<I_M_AttributeInstance> retrieveAttributeInstances(
			final Properties ctx,
			@NonNull final AttributeSetInstanceId asiId,
			final String trxName)
	{
		if (asiId.getRepoId() <= 0)
		{
			return ImmutableList.of();
		}

		return queryBL
				.createQueryBuilder(I_M_AttributeInstance.class, ctx, trxName)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, asiId)
				.orderBy(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID) // at least to have a predictable order
				.create()
				.listImmutable(I_M_AttributeInstance.class);
	}

	@Override
	@Nullable
	public I_M_AttributeInstance retrieveAttributeInstance(
			@Nullable final AttributeSetInstanceId attributeSetInstanceId,
			@NonNull final AttributeId attributeId)
	{
		if (attributeSetInstanceId == null || attributeSetInstanceId.isNone())
		{
			return null;
		}

		return queryBL.createQueryBuilder(I_M_AttributeInstance.class)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, attributeSetInstanceId)
				.addEqualsFilter(I_M_AttributeInstance.COLUMNNAME_M_Attribute_ID, attributeId)
				.create()
				.firstOnly(I_M_AttributeInstance.class);
	}

	@Override
	public List<AttributeListValue> retrieveFilteredAttributeValues(final I_M_Attribute attribute, final SOTrx soTrx)
	{
		return retrieveAttributeValuesMap(attribute, false/* includeInactive */)
				.getMatchingSOTrx(soTrx);
	}

	private AttributeListValueMap retrieveAttributeValuesMap(@NonNull final I_M_Attribute attribute, final boolean includeInactive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(attribute);

		final AttributeId attributeId = AttributeId.ofRepoId(attribute.getM_Attribute_ID());

		//
		// 07708: Apply AD_Val_Rule when filtering attributes for current context
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

		return retrieveAttributeValuesMap(ctx, attributeId, includeInactive, validationRuleQueryFilter);
	}

	@Cached(cacheName = I_M_AttributeValue.Table_Name
			+ "#by#" + I_M_AttributeValue.COLUMNNAME_M_Attribute_ID
			+ "#" + I_M_AttributeValue.COLUMNNAME_Value)
	AttributeListValueMap retrieveAttributeValuesMap(
			@CacheCtx final Properties ctx,
			@NonNull final AttributeId attributeId,
			final boolean includeInactive,
			// NOTE: we are caching this method only if we don't have a filter.
			// If we have a filter:
			// * that's mutable so it will screw up up our case
			// * in most of the cases, when we have an validation rule filter we are dealing with a huge amount of data which needs to be filtered (see Karotten ID example from)
			@CacheSkipIfNotNull final ValidationRuleQueryFilter<I_M_AttributeValue> validationRuleQueryFilter)
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
				.orderBy(I_M_AttributeValue.COLUMNNAME_Value) // order attributes by value, so we can have names like breakfast, lunch, dinner in their "temporal" order
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
	public List<I_M_Attribute> retrieveAttributes(final AttributeSetId attributeSetId, final boolean isInstanceAttribute)
	{
		return getAttributesByAttributeSetId(attributeSetId)
				.stream()
				.filter(attribute -> attribute.isInstanceAttribute() == isInstanceAttribute)
				.collect(ImmutableList.toImmutableList());
	}

	@Override
	public boolean containsAttribute(@NonNull final AttributeSetId attributeSetId, @NonNull final AttributeId attributeId)
	{
		return getAttributeIdsByAttributeSetId(attributeSetId).contains(attributeId);
	}

	@Override
	@Nullable
	public I_M_Attribute retrieveAttribute(final AttributeSetId attributeSetId, final AttributeId attributeId)
	{
		if (!containsAttribute(attributeSetId, attributeId))
		{
			return null;
		}

		return getAttributeById(attributeId);
	}

	@Override
	public Optional<AttributeSetAttribute> getAttributeSetAttributeId(final AttributeSetId attributeSetId, final AttributeId attributeId)
	{
		return getAttributeIdsByAttributeSetId(attributeSetId).getByAttributeId(attributeId);
	}

	@Override
	public I_M_AttributeInstance createNewAttributeInstance(
			final Properties ctx,
			final I_M_AttributeSetInstance asi,
			@NonNull final AttributeId attributeId,
			final String trxName)
	{
		final I_M_AttributeInstance ai = InterfaceWrapperHelper.create(ctx, I_M_AttributeInstance.class, trxName);
		ai.setAD_Org_ID(asi.getAD_Org_ID());
		ai.setM_AttributeSetInstance(asi);
		ai.setM_Attribute_ID(attributeId.getRepoId());

		return ai;
	}

	@Override
	@Cached(cacheName = I_M_AttributeSet.Table_Name + "#ID=0")
	public I_M_AttributeSet retrieveNoAttributeSet()
	{
		return queryBL
				.createQueryBuilder(I_M_AttributeSet.class)
				.addEqualsFilter(I_M_AttributeSet.COLUMNNAME_M_AttributeSet_ID, AttributeSetId.NONE)
				.create()
				.firstOnlyNotNull(I_M_AttributeSet.class);
	}

	@Override
	@Cached(cacheName = I_M_AttributeSetInstance.Table_Name + "#ID=0")
	public I_M_AttributeSetInstance retrieveNoAttributeSetInstance()
	{
		return queryBL
				.createQueryBuilder(I_M_AttributeSetInstance.class)
				.addEqualsFilter(I_M_AttributeSetInstance.COLUMNNAME_M_AttributeSetInstance_ID, AttributeSetInstanceId.NONE)
				.create()
				.firstOnlyNotNull(I_M_AttributeSetInstance.class);
	}

	@Override
	public ImmutableAttributeSet getImmutableAttributeSetById(@NonNull final AttributeSetInstanceId asiId)
	{
		if (asiId.isNone())
		{
			return ImmutableAttributeSet.EMPTY;
		}

		final List<I_M_AttributeInstance> instances = retrieveAttributeInstances(asiId);
		return createImmutableAttributeSet(instances);
	}

	private ImmutableAttributeSet createImmutableAttributeSet(final Collection<I_M_AttributeInstance> instances)
	{
		final ImmutableAttributeSet.Builder builder = ImmutableAttributeSet.builder();
		for (final I_M_AttributeInstance instance : instances)
		{
			final AttributeId attributeId = AttributeId.ofRepoId(instance.getM_Attribute_ID());
			final Object value = extractAttributeInstanceValue(instance);
			final AttributeValueId attributeValueId = AttributeValueId.ofRepoIdOrNull(instance.getM_AttributeValue_ID());
			builder.attributeValue(attributeId, value, attributeValueId);
		}
		return builder.build();
	}

	private Object extractAttributeInstanceValue(final I_M_AttributeInstance instance)
	{
		final I_M_Attribute attribute = getAttributeById(instance.getM_Attribute_ID());
		final String attributeValueType = attribute.getAttributeValueType();
		if (X_M_Attribute.ATTRIBUTEVALUETYPE_Date.equals(attributeValueType))
		{
			return instance.getValueDate();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_List.equals(attributeValueType))
		{
			return instance.getValue();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_Number.equals(attributeValueType))
		{
			return instance.getValueNumber();
		}
		else if (X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40.equals(attributeValueType))
		{
			return instance.getValue();
		}
		else
		{
			throw new AdempiereException("Unsupported attributeValueType=" + attributeValueType)
					.setParameter("instance", instance)
					.setParameter("attribute", attribute)
					.appendParametersToMessage();
		}
	}

	@Override
	public Map<AttributeSetInstanceId, ImmutableAttributeSet> getAttributesForASIs(
			@NonNull final Set<AttributeSetInstanceId> asiIds)
	{
		Check.assumeNotEmpty(asiIds, "asiIds is not empty");

		final Map<AttributeSetInstanceId, List<I_M_AttributeInstance>> //
				instancesByAsiId = queryBL
				.createQueryBuilder(I_M_AttributeInstance.class)
				.addInArrayFilter(I_M_AttributeInstance.COLUMNNAME_M_AttributeSetInstance_ID, asiIds)
				.create()
				.list()
				.stream()
				.collect(Collectors.groupingBy(ai -> AttributeSetInstanceId.ofRepoId(ai.getM_AttributeSetInstance_ID())));

		final ImmutableMap.Builder<AttributeSetInstanceId, ImmutableAttributeSet> result = ImmutableMap.builder();
		instancesByAsiId.forEach((asiId, instances) -> result.put(asiId, createImmutableAttributeSet(instances)));

		return result.build();

	}

	@Override
	public AttributeSetInstanceId copyASI(@NonNull final AttributeSetInstanceId asiSourceId)
	{
		final I_M_AttributeSetInstance asiSourceRecord = getAttributeSetInstanceById(asiSourceId);
		final I_M_AttributeSetInstance asiTargetRecord = copy(asiSourceRecord);

		return AttributeSetInstanceId.ofRepoId(asiTargetRecord.getM_AttributeSetInstance_ID());
	}

	@Override
	public boolean nullSafeASIEquals(
			@Nullable final AttributeSetInstanceId firstASIId,
			@Nullable final AttributeSetInstanceId secondASIId)
	{
		if (firstASIId == null && secondASIId == null)
		{
			return true;
		}

		if ((firstASIId == null && secondASIId != null)
				|| (secondASIId == null && firstASIId != null))
		{
			return false;
		}

		final ImmutableAttributeSet firstAttributeSet = getImmutableAttributeSetById(firstASIId);
		final ImmutableAttributeSet secondAttributeSet = getImmutableAttributeSetById(secondASIId);

		return firstAttributeSet.equals(secondAttributeSet);
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

		public List<AttributeListValue> getMatchingSOTrx(@Nullable final SOTrx soTrx)
		{
			return map.values()
					.stream()
					.filter(av -> av.isMatchingSOTrx(soTrx))
					.collect(ImmutableList.toImmutableList());
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

	@Value
	@Builder
	private static class Attribute
	{
		@NonNull
		AttributeId attributeId;

		@NonNull
		AttributeCode attributeCode;

		@NonNull
		ITranslatableString displayName;

		@Nullable
		ITranslatableString description;
	}

	@ToString
	private static class AttributesMap
	{
		private final ImmutableMap<AttributeId, Attribute> attributesById;
		private final ImmutableMap<AttributeCode, Attribute> attributesByCode;

		AttributesMap(@NonNull final List<Attribute> attributes)
		{
			this.attributesById = Maps.uniqueIndex(attributes, Attribute::getAttributeId);
			this.attributesByCode = Maps.uniqueIndex(attributes, Attribute::getAttributeCode);
		}

		public Attribute getAttributeByCodeOrNull(final AttributeCode attributeCode)
		{
			return attributesByCode.get(attributeCode);
		}

		@Nullable
		public AttributeId getAttributeIdByCodeOrNull(@NonNull final AttributeCode attributeCode)
		{
			final Attribute attribute = getAttributeByCodeOrNull(attributeCode);
			return attribute != null ? attribute.getAttributeId() : null;
		}

		@Nullable
		public AttributeId getAttributeIdByCodeOrNull(@NonNull final String attributeCode)
		{
			return getAttributeIdByCodeOrNull(AttributeCode.ofString(attributeCode));
		}

		@NonNull
		public AttributeId getAttributeIdByCode(@NonNull final AttributeCode attributeCode)
		{
			final AttributeId attributeId = getAttributeIdByCodeOrNull(attributeCode);
			if (attributeId == null)
			{
				throw new AdempiereException("No active attribute found for `" + attributeCode + "`");
			}
			return attributeId;
		}

		public AttributeId getAttributeIdByCode(@NonNull final String attributeCode)
		{
			return getAttributeIdByCode(AttributeCode.ofString(attributeCode));
		}
	}
}
