package org.adempiere.mm.attributes.api.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.IAttributesBL;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.DisplayType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class AttributeSetDescriptionBuilderCommandTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	private void createEachUOM()
	{
		prepareUOM()
				.uomId(UomId.EACH)
				.uomSymbol("Ea")
				.precision(0)
				.build();
	}

	@Builder(builderMethodName = "prepareUOM", builderClassName = "UOMBuilder")
	private static UomId createUOM(
			UomId uomId,
			@NonNull String uomSymbol,
			int precision)
	{
		final I_C_UOM uom = newInstance(I_C_UOM.class);
		if (uomId != null)
		{
			uom.setC_UOM_ID(uomId.getRepoId());
		}
		uom.setName(uomSymbol);
		uom.setUOMSymbol(uomSymbol);
		uom.setStdPrecision(precision);
		saveRecord(uom);
		return UomId.ofRepoId(uom.getC_UOM_ID());
	}

	@Builder(builderMethodName = "prepareAttribute", builderClassName = "AttributeBuilder")
	private static AttributeId createAttribute(
			@NonNull final String value,
			final String name,
			@NonNull final String attributeValueType,
			final UomId uomId,
			final String descriptionPattern)
	{
		final I_M_Attribute attribute = newInstance(I_M_Attribute.class);
		attribute.setValue(value);
		attribute.setName(name != null ? name : value);
		attribute.setAttributeValueType(attributeValueType);
		attribute.setC_UOM_ID(UomId.toRepoId(uomId));
		attribute.setDescriptionPattern(descriptionPattern);
		saveRecord(attribute);
		return AttributeId.ofRepoId(attribute.getM_Attribute_ID());
	}

	@Nested
	public static class AttributesBL_getNumberDisplayType_assumptions
	{
		@BeforeEach
		public void init()
		{
			AdempiereTestHelper.get().init();
		}

		private void createNumericAttributeAndAssumeDisplayType(final UomId uomId, final int expectedDispayType)
		{
			final AttributeId attributeId = prepareAttribute()
					.value("test")
					.attributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number)
					.uomId(uomId)
					.build();
			I_M_Attribute attribute = Services.get(IAttributeDAO.class).getAttributeById(attributeId);

			final IAttributesBL attributesBL = Services.get(IAttributesBL.class);
			assertThat(attributesBL.getNumberDisplayType(attribute))
					.as("expected display type when uomId=" + uomId)
					.isEqualTo(expectedDispayType);
		}

		@Test
		public void noUOM()
		{
			final UomId uomId = null;
			createNumericAttributeAndAssumeDisplayType(uomId, DisplayType.Number);
		}

		@Test
		public void Each()
		{
			createNumericAttributeAndAssumeDisplayType(UomId.EACH, DisplayType.Integer);
		}

		@Test
		public void someOtherUOM_with_ZeroPrecision()
		{
			final UomId uomId = prepareUOM()
					.uomSymbol("BlaBla")
					.precision(0)
					.build();

			createNumericAttributeAndAssumeDisplayType(uomId, DisplayType.Number);
		}

		@Test
		public void someOtherUOM_with_NonZeroPrecision()
		{
			final UomId uomId = prepareUOM()
					.uomSymbol("BlaBla")
					.precision(2)
					.build();

			createNumericAttributeAndAssumeDisplayType(uomId, DisplayType.Number);
		}
	}

	@Test
	public void numericAttribute_without_DescriptionPattern()
	{
		final AttributeId attributeId = prepareAttribute()
				.value("MonthsUntilExpiry")
				.attributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number)
				.uomId(UomId.EACH) // to advice the builder to render the attribute as Integer instead of Numeric
				.build();

		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.builder()
				.attributeValue(attributeId, 7)
				.build();

		String result = new AttributeSetDescriptionBuilderCommand(attributeSet, "en_US")
				.execute()
				.getDefaultValue();
		assertThat(result).isEqualTo("7");
	}

	@Test
	public void numericAttribute_with_DescriptionPattern_Label_Value()
	{
		final AttributeId attributeId = prepareAttribute()
				.value("MonthsUntilExpiry")
				.name("Exp")
				.attributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number)
				.uomId(UomId.EACH) // to advice the builder to render the attribute as Integer instead of Numeric
				.descriptionPattern("@Label@: @Value@")
				.build();

		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.builder()
				.attributeValue(attributeId, 7)
				.build();

		String result = new AttributeSetDescriptionBuilderCommand(attributeSet, "en_US")
				.execute()
				.getDefaultValue();
		assertThat(result).isEqualTo("Exp: 7");
	}

	@Test
	public void numericAttribute_with_DescriptionPattern_Label_Value_UOM()
	{
		createEachUOM(); // needed to fetch the UOM symbol

		final AttributeId attributeId = prepareAttribute()
				.value("MonthsUntilExpiry")
				.name("Exp")
				.attributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number)
				.uomId(UomId.EACH) // to advice the builder to render the attribute as Integer instead of Numeric
				.descriptionPattern("@Label@: @Value@ @UOM@")
				.build();

		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.builder()
				.attributeValue(attributeId, 7)
				.build();

		String result = new AttributeSetDescriptionBuilderCommand(attributeSet, "en_US")
				.execute()
				.getDefaultValue();
		assertThat(result).isEqualTo("Exp: 7 Ea");
	}

}
