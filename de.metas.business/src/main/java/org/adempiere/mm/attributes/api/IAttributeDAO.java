package org.adempiere.mm.attributes.api;

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
import java.util.Properties;
import java.util.Set;

import org.adempiere.util.ISingletonService;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_AttributeValue;
import org.compiere.model.I_M_AttributeValue_Mapping;
import org.compiere.util.Env;

/**
 * Material Attributes DAO
 *
 * @author tsa
 *
 */
public interface IAttributeDAO extends ISingletonService
{
	/**
	 * Retrieves the "No Attribute Set" (i.e. M_AttributeSet_ID = {@link AttributeConstants#M_AttributeSet_ID_None}).
	 */
	I_M_AttributeSet retrieveNoAttributeSet();

	/**
	 * Retrieves the "No Attribute Set Instance" (i.e. M_AttributeSetInstance_ID = {@link AttributeConstants#M_AttributeSetInstance_ID_None}).
	 */
	I_M_AttributeSetInstance retrieveNoAttributeSetInstance();

	I_M_Attribute retrieveAttributeById(Properties ctx, int attributeId);

	List<I_M_AttributeValue> retrieveAttributeValues(I_M_Attribute attribute);

	/**
	 * Retrieves all attribute instances associated with an attribute instance set.
	 *
	 * @param attributeSetInstance may be {@code null}, in which case an empty list is returned.
	 * @return a list of the given {@code attributeSetInstance}'s attribute instances, ordered by {@code M_Attribute_ID}. Never {@code null}
	 */
	List<I_M_AttributeInstance> retrieveAttributeInstances(I_M_AttributeSetInstance attributeSetInstance);

	/**
	 * Same as {@link #retrieveAttributeInstance(I_M_AttributeSetInstance, int, String)} but <code>attributeSetInstance</code>'s trxName will be used.
	 *
	 * @param attributeSetInstance
	 * @param attributeId M_Attribute_ID
	 * @return attribute instance or null
	 */
	I_M_AttributeInstance retrieveAttributeInstance(I_M_AttributeSetInstance attributeSetInstance, int attributeId);

	/**
	 * Retrieve all attribute values that are defined for SO/PO transactions.
	 *
	 * @param attribute
	 * @param isSOTrx if NULL, retrieve all attribute values.
	 * @return
	 */
	List<I_M_AttributeValue> retrieveFilteredAttributeValues(I_M_Attribute attribute, Boolean isSOTrx);

	/**
	 * Retrieves all attributes in a set that are (or aren't) instance attributes
	 *
	 * @param attributeSet
	 * @param isInstanceAttribute
	 * @return
	 */
	List<I_M_Attribute> retrieveAttributes(I_M_AttributeSet attributeSet, boolean isInstanceAttribute);

	/**
	 * Check if an attribute belongs to an attribute set (via M_AttributeUse).
	 *
	 * @param attributeSetId
	 * @param attributeId
	 * @param ctxProvider
	 * @return
	 */
	boolean containsAttribute(int attributeSetId, int attributeId, Object ctxProvider);

	I_M_Attribute retrieveAttribute(final I_M_AttributeSet as, final int attributeId);

	I_M_AttributeValue retrieveAttributeValueOrNull(I_M_Attribute attribute, String value);

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
	 * @param attribute
	 * @param value
	 * @return substitutes (M_AttributeValue.Value).
	 */
	Set<String> retrieveAttributeValueSubstitutes(I_M_Attribute attribute, String value);

	/**
	 * Gets {@link I_M_Attribute} by it's Value (a.k.a. Internal Name)
	 *
	 * @param ctx
	 * @param value
	 * @param clazz
	 * @return attribute; never return null
	 */
	<T extends I_M_Attribute> T retrieveAttributeByValue(Properties ctx, String value, Class<T> clazz);

	/**
	 * @return attribute; never return null
	 */
	default I_M_Attribute retrieveAttributeByValue(final String value)
	{
		return retrieveAttributeByValue(Env.getCtx(), value, I_M_Attribute.class);
	}


	/**
	 * Creates a new {@link I_M_AttributeInstance}.
	 *
	 * NOTE: it is not saving it
	 *
	 * @param ctx
	 * @param asi
	 * @param attributeId
	 * @param trxName
	 * @return
	 */
	I_M_AttributeInstance createNewAttributeInstance(Properties ctx, final I_M_AttributeSetInstance asi, final int attributeId, final String trxName);

	/**
	 * Creates a new {@link I_M_AttributeSetInstance} (including it's {@link I_M_AttributeInstance}s) by copying given <code>asi</code>
	 *
	 * @param fromASI
	 * @return asi copy
	 */
	default I_M_AttributeSetInstance copy(I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI).copy();
	}


	default I_M_AttributeSetInstance copy(I_M_AttributeSetInstance fromASI, int overrideM_AttributeSet_ID)
	{
		return ASICopy.newInstance(fromASI)
				.overrideM_AttributeSet_ID(overrideM_AttributeSet_ID)
				.copy();
	}

	default ASICopy prepareCopy(final I_M_AttributeSetInstance fromASI)
	{
		return ASICopy.newInstance(fromASI);
	}

	/**
	 * @param attribute
	 * @return true if given attribute is expected to have a huge amount of {@link I_M_AttributeValue}s.
	 */
	boolean isHighVolumeValuesList(I_M_Attribute attribute);
}
