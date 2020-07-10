package org.adempiere.mm.attributes.api;

import java.util.Collection;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributeValueId;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;

import de.metas.i18n.ITranslatableString;
import de.metas.lang.SOTrx;
import de.metas.util.ISingletonService;

public interface IAttributeDAO extends ISingletonService
{
	String CACHEKEY_ATTRIBUTE_VALUE = I_M_AttributeValue.Table_Name;

	void save(I_M_AttributeSetInstance asi);

	void save(I_M_AttributeInstance ai);

	I_M_AttributeSet getAttributeSetById(AttributeSetId attributeSetId);

	I_M_Attribute getAttributeById(int attributeId);

	I_M_Attribute getAttributeById(AttributeId attributeId);

	<T extends I_M_Attribute> T getAttributeById(AttributeId attributeId, Class<T> type);

	List<I_M_Attribute> getAttributesByIds(Collection<AttributeId> attributeIds);

	/** @return attributeIds ordered by M_AttributeUse.SeqNo */
	List<AttributeId> getAttributeIdsByAttributeSetId(AttributeSetId attributeSetId);

	Set<AttributeId> getAttributeIdsByAttributeSetInstanceId(AttributeSetInstanceId attributeSetInstanceId);

	/** @return attributes, ordered by M_AttributeUse.SeqNo */
	List<I_M_Attribute> getAttributesByAttributeSetId(AttributeSetId attributeSetId);

	List<I_M_Attribute> getAllAttributes();

	String getAttributeCodeById(AttributeId attributeId);

	/**
	 * Retrieves the "No Attribute Set" (i.e. M_AttributeSet_ID = {@link AttributeConstants#M_AttributeSet_ID_None}).
	 */
	I_M_AttributeSet retrieveNoAttributeSet();

	/**
	 * Retrieves the "No Attribute Set Instance" (i.e. M_AttributeSetInstance_ID = {@link AttributeConstants#M_AttributeSetInstance_ID_None}).
	 */
	I_M_AttributeSetInstance retrieveNoAttributeSetInstance();

	List<AttributeListValue> retrieveAttributeValues(I_M_Attribute attribute);

	List<AttributeListValue> retrieveAttributeValuesByAttributeId(AttributeId attributeId);

	List<AttributeListValue> retrieveAttributeValuesByIds(Collection<AttributeValueId> attributeValueIds);

	List<I_M_AttributeInstance> retrieveAttributeInstances(AttributeSetInstanceId attributeSetInstanceId);

	/**
	 * Retrieves all attribute instances associated with an attribute instance set.
	 *
	 * @param attributeSetInstance may be {@code null}, in which case an empty list is returned.
	 * @return a list of the given {@code attributeSetInstance}'s attribute instances, ordered by M_AttributeUse.SeqNo
	 */
	List<I_M_AttributeInstance> retrieveAttributeInstances(I_M_AttributeSetInstance attributeSetInstance);

	/**
	 * @param attributeSetInstanceId may be {@code null} or "none". In that case, always {@code null} is returned.
	 *
	 * @return the attribute instance with the given {@code attributeSetInstanceId} and {@code attributeId}, or {@code null}.
	 */
	I_M_AttributeInstance retrieveAttributeInstance(AttributeSetInstanceId attributeSetInstanceId, AttributeId attributeId);

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
	 *
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

	boolean deleteAttributeValueByCode(AttributeId attributeId, String value);

	AttributeId retrieveAttributeIdByValue(AttributeCode attributeCode);

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
	default I_M_Attribute retrieveAttributeByValue(final AttributeCode attributeCode)
	{
		return retrieveAttributeByValue(attributeCode, I_M_Attribute.class);
	}

	/**
	 * @return attribute; never return null
	 */
	default I_M_Attribute retrieveAttributeByValue(final String value)
	{
		return retrieveAttributeByValue(AttributeCode.ofString(value), I_M_Attribute.class);
	}

	/**
	 * Creates a new {@link I_M_AttributeInstance}.
	 *
	 * NOTE: it is not saving it
	 */
	I_M_AttributeInstance createNewAttributeInstance(Properties ctx, final I_M_AttributeSetInstance asi, final AttributeId attributeId, final String trxName);

	/**
	 * Creates a new {@link I_M_AttributeSetInstance} (including it's {@link I_M_AttributeInstance}s) by copying given <code>asi</code>
	 *
	 * @return asi copy
	 */
	default I_M_AttributeSetInstance copy(I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI).copy();
	}

	default ASICopy prepareCopy(final I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI);
	}

	/**
	 * @return true if given attribute is expected to have a huge amount of attribute values
	 */
	boolean isHighVolumeValuesList(I_M_Attribute attribute);

	ImmutableAttributeSet getImmutableAttributeSetById(AttributeSetInstanceId asiId);

	Map<AttributeSetInstanceId, ImmutableAttributeSet> getAttributesForASIs(Set<AttributeSetInstanceId> asiIds, Set<AttributeId> attributeIds);

	Optional<ITranslatableString> getAttributeDisplayNameByValue(String value);

	boolean areAttributeSetsEqual(AttributeSetInstanceId firstASIId, AttributeSetInstanceId secondASIId);

	I_M_AttributeSetInstance getAttributeSetInstanceById(AttributeSetInstanceId attributeSetInstanceId);

	AttributeSetInstanceId copyASI(AttributeSetInstanceId asiSourceId);
}
