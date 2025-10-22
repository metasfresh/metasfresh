package org.adempiere.mm.attributes.api;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetAttributeIdsList;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.mm.attributes.MultiAttributeSetAttributeIdsList;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface IAttributeDAO extends ISingletonService
{
	String CACHEKEY_ATTRIBUTE_VALUE = I_M_AttributeValue.Table_Name;

	I_M_AttributeSet getAttributeSetById(AttributeSetId attributeSetId);

	AttributeSetAttributeIdsList getAttributeIdsByAttributeSetId(@NonNull AttributeSetId attributeSetId);

	MultiAttributeSetAttributeIdsList getAttributeIdsByAttributeSetIds(@NonNull Set<AttributeSetId> attributeSetIds);

	I_M_Attribute getAttributeById(int attributeId);

	I_M_Attribute getAttributeById(AttributeId attributeId);

	<T extends I_M_Attribute> T getAttributeById(AttributeId attributeId, Class<T> type);

	List<I_M_Attribute> getAttributesByIds(Collection<AttributeId> attributeIds);

	/**
	 * @return attributes, ordered by M_AttributeUse.SeqNo
	 */
	List<I_M_Attribute> getAttributesByAttributeSetId(AttributeSetId attributeSetId);

	List<I_M_Attribute> getAllAttributes();

	String getAttributeCodeById(AttributeId attributeId);

	@NonNull
	ImmutableList<AttributeCode> getOrderedAttributeCodesByIds(@NonNull final List<AttributeId> orderedAttributeIds);

	/**
	 * Retrieves the "No Attribute Set" (i.e. M_AttributeSet_ID = {@link AttributeSetId#NONE}).
	 */
	I_M_AttributeSet retrieveNoAttributeSet();

	List<AttributeListValue> retrieveAttributeValues(I_M_Attribute attribute);

	List<AttributeListValue> retrieveAttributeValuesByAttributeId(AttributeId attributeId);

	List<AttributeListValue> retrieveAttributeValuesByAttributeSetId(@NonNull AttributeSetId attributeSetId);

	List<AttributeListValue> retrieveAttributeValuesByIds(Collection<AttributeValueId> attributeValueIds);

	/**
	 * Retrieve all attribute values that are defined for SO/PO transactions.
	 *
	 * @param soTrx if NULL, retrieve all attribute values.
	 */
	List<AttributeListValue> retrieveFilteredAttributeValues(I_M_Attribute attribute, SOTrx soTrx);

	/**
	 * Retrieves all attributes in a set that are (or aren't) instance attributes
	 */
	List<I_M_Attribute> retrieveAttributes(AttributeSetId attributeSetId, boolean isInstanceAttribute);

	/**
	 * Check if an attribute belongs to an attribute set (via M_AttributeUse).
	 */
	boolean containsAttribute(AttributeSetId attributeSetId, AttributeId attributeId);

	I_M_Attribute retrieveAttribute(AttributeSetId attributeSetId, AttributeId attributeId);

	AttributeListValue retrieveAttributeValueOrNull(AttributeId attributeId, String value);

	AttributeListValue retrieveAttributeValueOrNull(I_M_Attribute attribute, String value);

	AttributeListValue retrieveAttributeValueOrNull(AttributeId attributeId, AttributeValueId attributeValueId);

	AttributeListValue retrieveAttributeValueOrNull(I_M_Attribute attribute, AttributeValueId attributeValueId);

	AttributeListValue retrieveAttributeValueOrNull(I_M_Attribute attribute, String value, boolean includeInactive);

	/**
	 * Retrieves substitutes (M_AttributeValue.Value) for given value.
	 * Example use case:
	 *
	 * <pre>
	 * We have following attribute values:
	 * * A&B
	 * * A&C
	 * * A
	 * * B
	 * * C
	 *
	 * We want to specify that when "A" or "B" is needed then we can also use "A&B".
	 * For that, in {@link I_M_AttributeValue_Mapping} we insert following records:
	 * (M_AttributeValue_ID, M_AttributeValue_To_ID)
	 * * A&B,  A
	 * * A&B,  B
	 * * A&C,  A
	 * * A&C,  C
	 *
	 * Now, when we call {@link #retrieveAttributeValueSubstitutes(I_M_Attribute, String)} with value="A" we will get a set of {"A&B", "A&C"}.
	 * </pre>
	 *
	 * @return substitutes (M_AttributeValue.Value).
	 */
	Set<String> retrieveAttributeValueSubstitutes(I_M_Attribute attribute, String value);

	AttributeListValue createAttributeValue(AttributeListValueCreateRequest request);

	AttributeListValue changeAttributeValue(AttributeListValueChangeRequest request);

	void deleteAttributeValueByCode(AttributeId attributeId, String value);

	Optional<ITranslatableString> getAttributeDisplayNameByValue(@NonNull AttributeCode attributeCode);

	Optional<ITranslatableString> getAttributeDescriptionByValue(@NonNull String value);

	@NonNull
	AttributeId getAttributeIdByCode(AttributeCode attributeCode);

	AttributeId retrieveAttributeIdByValueOrNull(AttributeCode attributeCode);

	/**
	 * Gets {@link I_M_Attribute} by it's Value (a.k.a. Internal Name)
	 *
	 * @return attribute; never return null
	 */
	<T extends I_M_Attribute> T retrieveAttributeByValue(AttributeCode attributeCode, Class<T> clazz);

	/**
	 * @return attribute; never return null
	 */
	default I_M_Attribute retrieveAttributeByValue(@NonNull final AttributeCode attributeCode)
	{
		return retrieveAttributeByValue(attributeCode, I_M_Attribute.class);
	}

	@NonNull
	Attribute getAttributeByCode(@NonNull final AttributeCode attributeCode);

	/**
	 * @return attribute; never return null
	 */
	default I_M_Attribute retrieveAttributeByValue(@NonNull final String value)
	{
		return retrieveAttributeByValue(AttributeCode.ofString(value), I_M_Attribute.class);
	}

	<T extends I_M_Attribute> T retrieveAttributeByValueOrNull(AttributeCode attributeCode, Class<T> clazz);

	default I_M_Attribute retrieveAttributeByValueOrNull(@NonNull final AttributeCode attributeCode)
	{
		return retrieveAttributeByValueOrNull(attributeCode, I_M_Attribute.class);
	}

	/**
	 * @return true if given attribute is expected to have a huge amount of attribute values
	 */
	boolean isHighVolumeValuesList(I_M_Attribute attribute);

	Optional<ITranslatableString> getAttributeDisplayNameByValue(String value);
}
