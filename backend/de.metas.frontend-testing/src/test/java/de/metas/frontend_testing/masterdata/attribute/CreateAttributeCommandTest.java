package de.metas.frontend_testing.masterdata.attribute;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2025 metas GmbH
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

public class CreateAttributeCommandTest
{
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		context = new MasterdataContext();
	}

	private I_M_Attribute execute(final JsonCreateAttributeRequest request, final String identifier)
	{
		CreateAttributeCommand.builder()
				.context(context)
				.request(request)
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();

		final AttributeId attributeId = context.getId(Identifier.ofString(identifier), AttributeId.class);
		return InterfaceWrapperHelper.load(attributeId, I_M_Attribute.class);
	}

	@Test
	public void newAttribute_withoutIsInstanceAttribute_defaultsToTrue()
	{
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("ATTR_DEFAULT").build(),
				"attrDefault");

		assertThat(record.isInstanceAttribute()).isTrue();
	}

	@Test
	public void newAttribute_withIsInstanceAttributeFalse_isHonoured()
	{
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("ATTR_FALSE").isInstanceAttribute(false).build(),
				"attrFalse");

		assertThat(record.isInstanceAttribute()).isFalse();
	}

	@Test
	public void newAttribute_withIsInstanceAttributeTrue_isHonoured()
	{
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("ATTR_TRUE").isInstanceAttribute(true).build(),
				"attrTrue");

		assertThat(record.isInstanceAttribute()).isTrue();
	}

	@Test
	public void newAttribute_withoutName_defaultsToValue()
	{
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("ATTR_NO_NAME").build(),
				"attrNoName");

		assertThat(record.getName()).isEqualTo("ATTR_NO_NAME");
	}

	@Test
	public void upsertExistingAttribute_withoutName_preservesExistingName()
	{
		// given an existing attribute with a proper (translated) Name, e.g. a seeded standard attribute
		// re-linked by Value (like HU_BestBeforeDate whose seeded Name is "Mindesthaltbarkeit")
		execute(
				JsonCreateAttributeRequest.builder().value("HU_BestBeforeDate").name("Mindesthaltbarkeit").build(),
				"bbd1");

		// when upserting the same attribute (by Value) with the name omitted
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("HU_BestBeforeDate").build(),
				"bbd2");

		// then the existing Name must be preserved, NOT clobbered back to the technical Value - otherwise the
		// mobile receive dialog shows the raw code "HU_BestBeforeDate" instead of the human label.
		assertThat(record.getName()).isEqualTo("Mindesthaltbarkeit");
	}

	@Test
	public void upsertExistingAttribute_withoutIsInstanceAttribute_doesNotDowngrade()
	{
		// given an existing attribute explicitly created with IsInstanceAttribute=false
		execute(
				JsonCreateAttributeRequest.builder().value("ATTR_UPSERT").isInstanceAttribute(false).build(),
				"attrUpsert1");

		// when upserting the same attribute (by Value) with the flag omitted
		final I_M_Attribute record = execute(
				JsonCreateAttributeRequest.builder().value("ATTR_UPSERT").build(),
				"attrUpsert2");

		// then the existing value must be preserved, NOT silently defaulted back to true
		assertThat(record.isInstanceAttribute()).isFalse();
	}
}
