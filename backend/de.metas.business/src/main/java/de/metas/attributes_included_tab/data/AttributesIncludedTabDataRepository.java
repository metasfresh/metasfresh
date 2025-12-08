package de.metas.attributes_included_tab.data;

import com.google.common.collect.ImmutableMap;
import de.metas.util.GuavaCollectors;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.AttributeValueType;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_Record_Attribute;
import org.compiere.util.TimeUtil;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.HashMap;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@Repository
public class AttributesIncludedTabDataRepository
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	public AttributesIncludedTabData getByKey(@NonNull final AttributesIncludedTabDataKey key)
	{
		final ImmutableMap<AttributeId, AttributesIncludedTabDataField> fields = streamRecords(key)
				.map(AttributesIncludedTabDataRepository::fromRecord)
				.collect(ImmutableMap.toImmutableMap(AttributesIncludedTabDataField::getAttributeId, field -> field));

		return AttributesIncludedTabData.builder()
				.key(key)
				.fields(fields)
				.build();
	}

	private Stream<I_Record_Attribute> streamRecords(@NonNull final AttributesIncludedTabDataKey key)
	{
		return queryBL.createQueryBuilder(I_Record_Attribute.class)
				.addEqualsFilter(I_Record_Attribute.COLUMNNAME_AD_Table_ID, key.getRecordRef().getAD_Table_ID())
				.addEqualsFilter(I_Record_Attribute.COLUMNNAME_Record_ID, key.getRecordRef().getRecord_ID())
				.addEqualsFilter(I_Record_Attribute.COLUMNNAME_M_AttributeSet_IncludedTab_ID, key.getTabId())
				.orderBy(I_Record_Attribute.COLUMNNAME_Record_Attribute_ID) // just to have a predictable order
				.create()
				.stream();
	}

	private static AttributesIncludedTabDataField fromRecord(final I_Record_Attribute record)
	{
		return AttributesIncludedTabDataField.builder()
				.attributeId(AttributeId.ofRepoId(record.getM_Attribute_ID()))
				.valueType(AttributeValueType.ofCode(record.getAttributeValueType()))
				.valueString(record.getValueString())
				.valueNumber(InterfaceWrapperHelper.isNull(record, I_Record_Attribute.COLUMNNAME_ValueNumber) ? null : record.getValueNumber())
				.valueDate(TimeUtil.asLocalDate(record.getValueDate()))
				.valueItemId(AttributeValueId.ofRepoIdOrNull(record.getM_AttributeValue_ID()))
				.build();
	}

	private static void updateRecord(
			@NonNull final I_Record_Attribute record,
			@NonNull final AttributesIncludedTabDataField field,
			@NonNull final AttributesIncludedTabDataKey key)
	{
		record.setAD_Table_ID(key.getRecordRef().getAD_Table_ID());
		record.setRecord_ID(key.getRecordRef().getRecord_ID());
		record.setM_AttributeSet_IncludedTab_ID(key.getTabId().getRepoId());
		record.setM_Attribute_ID(field.getAttributeId().getRepoId());
		record.setAttributeValueType(field.getValueType().getCode());

		record.setValueString(field.getValueString());
		record.setValueNumber(field.getValueNumber());
		record.setValueDate(TimeUtil.asTimestamp(field.getValueDate()));
		record.setM_AttributeValue_ID(AttributeValueId.toRepoId(field.getValueItemId()));
	}

	public void save(@NonNull final AttributesIncludedTabData data)
	{
		final HashMap<AttributeId, I_Record_Attribute> recordsByAttributeId = streamRecords(data.getKey())
				.collect(GuavaCollectors.toHashMapByKey(record -> AttributeId.ofRepoId(record.getM_Attribute_ID())));

		data.getFields()
				.stream()
				.filter(field -> !field.isNullValues())
				.forEach(field -> {
					I_Record_Attribute record = recordsByAttributeId.remove(field.getAttributeId());
					if (record == null)
					{
						record = InterfaceWrapperHelper.newInstance(I_Record_Attribute.class);
					}

					updateRecord(record, field, data.getKey());

					InterfaceWrapperHelper.save(record);
				});

		InterfaceWrapperHelper.deleteAll(recordsByAttributeId.values());
	}

	public AttributesIncludedTabData updateByKey(
			@NonNull final AttributesIncludedTabDataKey key,
			@NonNull final Collection<AttributeId> attributeIds,
			@NonNull final BiFunction<AttributeId, AttributesIncludedTabDataField, AttributesIncludedTabDataField> mapper)
	{
		final HashMap<AttributeId, I_Record_Attribute> recordsByAttributeId = streamRecords(key)
				.collect(GuavaCollectors.toHashMapByKey(record -> AttributeId.ofRepoId(record.getM_Attribute_ID())));

		final HashMap<AttributeId, AttributesIncludedTabDataField> fields = new HashMap<>();

		for (final AttributeId attributeId : attributeIds)
		{
			I_Record_Attribute record = recordsByAttributeId.remove(attributeId);
			final AttributesIncludedTabDataField fieldBeforeChange = record != null ? fromRecord(record) : null;
			final AttributesIncludedTabDataField field = mapper.apply(attributeId, fieldBeforeChange);

			if (field == null || field.isNullValues())
			{
				if (record != null)
				{
					InterfaceWrapperHelper.delete(record);
				}
			}
			else if (!AttributesIncludedTabDataField.equals(field, fieldBeforeChange))
			{
				if (record == null)
				{
					record = InterfaceWrapperHelper.newInstance(I_Record_Attribute.class);
				}
				updateRecord(record, field, key);
				InterfaceWrapperHelper.save(record);
			}

			if (field != null)
			{
				fields.put(attributeId, field);
			}
		}

		InterfaceWrapperHelper.deleteAll(recordsByAttributeId.values());

		return AttributesIncludedTabData.builder()
				.key(key)
				.fields(fields)
				.build();
	}
}
