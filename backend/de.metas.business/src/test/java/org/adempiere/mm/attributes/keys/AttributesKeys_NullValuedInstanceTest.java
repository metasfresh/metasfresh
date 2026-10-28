/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2026 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import de.metas.material.event.commons.AttributesKey;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.AttributesTestHelper;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies that a null-valued {@code ProjectValue} {@code M_AttributeInstance} row is invisible to
 * {@link AttributesKeys#createAttributesKeyFromASIStorageAttributes(AttributeSetInstanceId)} — the
 * attributes key of an ASI carrying such a row is byte-identical to that of the same ASI without the
 * row at all. This underpins the safety of removing such rows via a bulk cleanup: doing so must be a
 * no-op for any record co-referencing the cleaned ASI, and this test confirms it at the key level.
 */
@ExtendWith(AdempiereTestWatcher.class)
class AttributesKeys_NullValuedInstanceTest
{
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private AttributesTestHelper attributesTestHelper;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();
	}

	private I_M_Attribute createStorageRelevantAttribute(final String value)
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute(value, X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		attribute.setIsStorageRelevant(true);
		saveRecord(attribute);
		return attribute;
	}

	private I_M_AttributeSetInstance createASI()
	{
		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asi);
		return asi;
	}

	@Test
	void nullValuedProjectValueRow_isInvisibleToAttributesKey()
	{
		// Given: one real, storage-relevant attribute and the storage-relevant ProjectValue attribute
		final I_M_Attribute sizeAttribute = createStorageRelevantAttribute("Artikelgroesse");
		final I_M_Attribute projectValueAttribute = createStorageRelevantAttribute(AttributeConstants.ATTR_Project.getCode());

		// an ASI carrying the real attribute's value plus a null-valued ProjectValue instance row
		final I_M_AttributeSetInstance asiWithNullRow = createASI();
		final AttributeSetInstanceId asiWithNullRowId = AttributeSetInstanceId.ofRepoId(asiWithNullRow.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.setAttributeInstanceValue(asiWithNullRowId, AttributeId.ofRepoId(sizeAttribute.getM_Attribute_ID()), "21");
		attributeSetInstanceBL.setAttributeInstanceValue(asiWithNullRowId, AttributeId.ofRepoId(projectValueAttribute.getM_Attribute_ID()), null);

		// the same ASI, but with no ProjectValue row at all
		final I_M_AttributeSetInstance asiWithoutRow = createASI();
		final AttributeSetInstanceId asiWithoutRowId = AttributeSetInstanceId.ofRepoId(asiWithoutRow.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.setAttributeInstanceValue(asiWithoutRowId, AttributeId.ofRepoId(sizeAttribute.getM_Attribute_ID()), "21");

		// When
		final Optional<AttributesKey> keyWithNullRow = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiWithNullRowId);
		final Optional<AttributesKey> keyWithoutRow = AttributesKeys.createAttributesKeyFromASIStorageAttributes(asiWithoutRowId);

		// Then: the null-valued row is invisible to the key
		assertThat(keyWithNullRow).isPresent();
		assertThat(keyWithNullRow).isEqualTo(keyWithoutRow);
	}
}
