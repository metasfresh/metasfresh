/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.document.sequence.DocSequenceId;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

class CopyAttributesFromBOMLineGeneratorTest
{
	private static final AttributeCode ATTR_Attribute1 = AttributeCode.ofString("Attribute1");
	private static final AttributeCode ATTR_Attribute2 = AttributeCode.ofString("Attribute2");

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		mkAttribute(ATTR_Attribute1);
		mkAttribute(ATTR_Attribute2);
	}

	private void mkAttribute(final AttributeCode attributeCode)
	{
		final I_M_Attribute po = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		po.setName(attributeCode.getCode());
		po.setValue(attributeCode.getCode());
		InterfaceWrapperHelper.saveRecord(po);
	}

	@Test
	void test()
	{
		final CopyAttributesFromBOMLineGenerator generator = new CopyAttributesFromBOMLineGenerator();

		final ImmutableAttributeSet result = generator.generate(ComponentGeneratorContext.builder()
				.existingAttributes(ImmutableAttributeSet.builder().attributeValue(ATTR_Attribute1, "value1").build())
				.bomLineAttributes(ImmutableAttributeSet.builder().attributeValue(ATTR_Attribute2, "value2").build())
																		.parameters(ComponentGeneratorParams.builder()
																							.sequenceId(DocSequenceId.ofRepoId(123456))
																							.build())
																		.clientId(ClientId.METASFRESH)
				.build());

		assertThat(result)
				.isEqualTo(ImmutableAttributeSet.builder()
						.attributeValue(ATTR_Attribute2, "value2")
						.build());
	}
}