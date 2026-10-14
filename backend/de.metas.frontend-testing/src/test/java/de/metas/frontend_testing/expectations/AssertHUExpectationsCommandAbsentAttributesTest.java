package de.metas.frontend_testing.expectations;

import com.google.common.collect.ImmutableList;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

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

/**
 * Unit test for the additive {@code attributesAbsent} check in
 * {@link AssertHUExpectationsCommand#assertAttributesAbsent(java.util.List, ImmutableAttributeSet)}.
 * <p>
 * This is the regression guard for the mixed-size receive AC: a container HU (TU / LU) must stay
 * size-neutral, so asserting a size code is ABSENT must FAIL when the code is actually present.
 */
public class AssertHUExpectationsCommandAbsentAttributesTest
{
	private static final String SIZE_CODE = "TestSizeCm";
	private static final String OTHER_CODE = "OtherAttr";

	private I_M_Attribute sizeAttribute;
	private I_M_Attribute otherAttribute;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		sizeAttribute = createAttribute(SIZE_CODE);
		otherAttribute = createAttribute(OTHER_CODE);
	}

	private static I_M_Attribute createAttribute(final String value)
	{
		final I_M_Attribute attribute = InterfaceWrapperHelper.newInstance(I_M_Attribute.class);
		attribute.setValue(value);
		attribute.setName(value);
		attribute.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_List);
		InterfaceWrapperHelper.saveRecord(attribute);
		return attribute;
	}

	@Test
	public void absentAsserted_attributePresent_fails()
	{
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.builder()
				.attributeValue(sizeAttribute, "15")
				.build();

		assertThatThrownBy(() -> AssertHUExpectationsCommand.assertAttributesAbsent(ImmutableList.of(SIZE_CODE), attributes))
				.hasMessageContaining("ABSENT")
				.hasMessageContaining(SIZE_CODE)
				.hasMessageContaining("15");
	}

	@Test
	public void absentAsserted_attributeAbsent_passes()
	{
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.builder().build();

		assertThatCode(() -> AssertHUExpectationsCommand.assertAttributesAbsent(ImmutableList.of(SIZE_CODE), attributes))
				.doesNotThrowAnyException();
	}

	@Test
	public void absentAsserted_onlyOtherAttributePresent_passes()
	{
		// A container that carries a DIFFERENT attribute (but NOT the size) is still size-neutral: the check
		// is specific to the listed code, it does not fire on the mere presence of some attribute.
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.builder()
				.attributeValue(otherAttribute, "whatever")
				.build();

		assertThatCode(() -> AssertHUExpectationsCommand.assertAttributesAbsent(ImmutableList.of(SIZE_CODE), attributes))
				.doesNotThrowAnyException();
	}

	@Test
	public void emptyList_isNoOp()
	{
		final ImmutableAttributeSet attributes = ImmutableAttributeSet.builder()
				.attributeValue(sizeAttribute, "15")
				.build();

		assertThatCode(() -> AssertHUExpectationsCommand.assertAttributesAbsent(ImmutableList.of(), attributes))
				.doesNotThrowAnyException();
	}
}
