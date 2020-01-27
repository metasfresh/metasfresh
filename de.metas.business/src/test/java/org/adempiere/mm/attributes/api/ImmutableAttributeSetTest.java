package org.adempiere.mm.attributes.api;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.ZoneId;

import org.adempiere.mm.attributes.AttributeListValue;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.impl.AttributesTestHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ImmutableAttributeSetTest
{
	private AttributesTestHelper attributesTestHelper;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		attributesTestHelper = new AttributesTestHelper();

	}

	@Test
	public void ofAttributesetInstanceId()
	{

		final I_M_Attribute attrStringWithValue = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue = attributesTestHelper.createM_AttributeValue(attrStringWithValue, "testValue1");

		final I_M_Attribute attributeStringNull = attributesTestHelper.createM_Attribute("AttrStringNullValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringNullValue = attributesTestHelper.createM_AttributeValue(attributeStringNull, null);

		final I_M_AttributeSetInstance asi = newInstance(I_M_AttributeSetInstance.class);
		save(asi);

		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final AttributeSetInstanceId asiId = AttributeSetInstanceId.ofRepoId(asi.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeStringValue);
		attributeSetInstanceBL.getCreateAttributeInstance(asiId, attributeStringNullValue);

		final ImmutableAttributeSet attributeSet = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asiId);

		assertThat(attributeSet.getAttributes()).contains(attrStringWithValue);
		assertThat(attributeSet.getAttributes()).contains(attributeStringNull);

		assertThat(attributeSet.getValue(attrStringWithValue)).isEqualTo(attributeStringValue.getValue());
		assertThat(attributeSet.getValue(attributeStringNull)).isNull();

	}

	@Test
	public void equalsTrue()
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_Attribute attrStringWithValue1 = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue1 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue1");

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);

		final AttributeSetInstanceId asi1Id = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.getCreateAttributeInstance(asi1Id, attributeStringValue1);

		final AttributeListValue attributeStringValue2 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue1");

		final I_M_AttributeSetInstance asi2 = newInstance(I_M_AttributeSetInstance.class);
		save(asi2);

		final AttributeSetInstanceId asi2Id = AttributeSetInstanceId.ofRepoId(asi2.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.getCreateAttributeInstance(asi2Id, attributeStringValue2);

		final ImmutableAttributeSet attributeSet1 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi1Id);

		final ImmutableAttributeSet attributeSet2 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi2Id);

		assertThat(attributeSet1).isEqualTo(attributeSet2);
	}

	@Test
	public void equalsFalse_DifferentValue()
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_Attribute attrStringWithValue1 = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue1 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue1");

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);

		final AttributeSetInstanceId asi1Id = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.getCreateAttributeInstance(asi1Id, attributeStringValue1);

		final AttributeListValue attributeStringValue2 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue2");

		final I_M_AttributeSetInstance asi2 = newInstance(I_M_AttributeSetInstance.class);
		save(asi2);

		final AttributeSetInstanceId asi2Id = AttributeSetInstanceId.ofRepoId(asi2.getM_AttributeSetInstance_ID());
		attributeSetInstanceBL.getCreateAttributeInstance(asi2Id, attributeStringValue2);

		final ImmutableAttributeSet attributeSet1 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi1Id);
		final ImmutableAttributeSet attributeSet2 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi2Id);

		assertThat(attributeSet1).isNotEqualTo(attributeSet2);
	}

	@Test
	public void equalsFalse_DifferentAttribute()
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_Attribute attrStringWithValue1 = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue1 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue1");

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);
		final AttributeSetInstanceId asi1Id = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asi1Id, attributeStringValue1);

		final I_M_Attribute attrStringWithValue2 = attributesTestHelper.createM_Attribute("AttrStringWithValue2", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue2 = attributesTestHelper.createM_AttributeValue(attrStringWithValue2, "testValue1");

		final I_M_AttributeSetInstance asi2 = newInstance(I_M_AttributeSetInstance.class);
		save(asi2);
		final AttributeSetInstanceId asi2Id = AttributeSetInstanceId.ofRepoId(asi2.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asi2Id, attributeStringValue2);

		final ImmutableAttributeSet attributeSet1 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi1Id);

		final ImmutableAttributeSet attributeSet2 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi2Id);

		assertThat(attributeSet1).isNotEqualTo(attributeSet2);
	}

	@Test
	public void equalsFalse_DifferentObject()
	{
		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.EMPTY;
		final Object otherObject = new Object();
		assertThat(attributeSet).isNotEqualTo(otherObject);
	}

	@Test
	public void equalsTrue_DifferentOrder()
	{
		final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);

		final I_M_Attribute attrStringWithValue1 = attributesTestHelper.createM_Attribute("AttrStringWithValue", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue1 = attributesTestHelper.createM_AttributeValue(attrStringWithValue1, "testValue1");

		final I_M_Attribute attrStringWithValue2 = attributesTestHelper.createM_Attribute("AttrStringWithValue2", X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40, true);
		final AttributeListValue attributeStringValue2 = attributesTestHelper.createM_AttributeValue(attrStringWithValue2, "testValue2");

		final I_M_AttributeSetInstance asi1 = newInstance(I_M_AttributeSetInstance.class);
		save(asi1);
		final AttributeSetInstanceId asi1Id = AttributeSetInstanceId.ofRepoId(asi1.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asi1Id, attributeStringValue1);
		attributeSetInstanceBL.getCreateAttributeInstance(asi1Id, attributeStringValue2);

		final I_M_AttributeSetInstance asi2 = newInstance(I_M_AttributeSetInstance.class);
		save(asi2);
		final AttributeSetInstanceId asi2Id = AttributeSetInstanceId.ofRepoId(asi2.getM_AttributeSetInstance_ID());

		attributeSetInstanceBL.getCreateAttributeInstance(asi2Id, attributeStringValue2);
		attributeSetInstanceBL.getCreateAttributeInstance(asi2Id, attributeStringValue1);

		final ImmutableAttributeSet attributeSet1 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi1Id);

		final ImmutableAttributeSet attributeSet2 = Services.get(IAttributeDAO.class).getImmutableAttributeSetById(asi2Id);

		assertThat(attributeSet1).isEqualTo(attributeSet2);
	}

	@Test
	public void testValueAsDate()
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute("date", X_M_Attribute.ATTRIBUTEVALUETYPE_Date, true);

		assertValueAsLocalDate(attribute, null, null);
		assertValueAsLocalDate(attribute, "", null);
		assertValueAsLocalDate(attribute, "    ", null);

		final LocalDate localDate = LocalDate.of(2019, Month.FEBRUARY, 3);
		assertValueAsLocalDate(attribute, localDate, localDate);
		assertValueAsLocalDate(attribute, localDate.toString(), localDate);
		assertValueAsLocalDate(attribute, localDate.atTime(LocalTime.of(13, 14)), localDate);
		assertValueAsLocalDate(attribute, localDate.atTime(LocalTime.of(13, 14)).atZone(ZoneId.systemDefault()), localDate);
		// assertValueAsLocalDate(attribute, localDate.atTime(LocalTime.of(13, 14)).atZone(ZoneId.systemDefault()).toString(), localDate); // not supported; not sure if is needed
		assertValueAsLocalDate(attribute, localDate.atTime(LocalTime.of(13, 14)).atZone(ZoneId.systemDefault()).format(Env.DATE_FORMAT), localDate);

		assertValueAsLocalDate(attribute, TimeUtil.asDate(localDate), localDate);
		assertValueAsLocalDate(attribute, TimeUtil.asDate(localDate).getTime(), localDate);

		assertValueAsLocalDate(attribute, TimeUtil.asTimestamp(localDate), localDate);
		assertValueAsLocalDate(attribute, TimeUtil.asTimestamp(localDate).toString(), localDate); // JDBC format
	}

	private void assertValueAsLocalDate(final I_M_Attribute attribute, Object inputValue, LocalDate expectedValue)
	{
		final String attributeKey = attribute.getValue();
		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.builder()
				.attributeValue(attribute, inputValue)
				.build();

		assertThat(attributeSet.getValueAsLocalDate(attributeKey)).isEqualTo(expectedValue);
	}

	@Test
	public void testValueAsBigDecimal()
	{
		final I_M_Attribute attribute = attributesTestHelper.createM_Attribute("number", X_M_Attribute.ATTRIBUTEVALUETYPE_Number, true);

		assertValueAsBigDecimal(attribute, null, null);
		assertValueAsBigDecimal(attribute, "", null);
		assertValueAsBigDecimal(attribute, 123, new BigDecimal("123"));
		assertValueAsBigDecimal(attribute, new BigDecimal("123.45"), new BigDecimal("123.45"));
	}

	private void assertValueAsBigDecimal(final I_M_Attribute attribute, Object inputValue, BigDecimal expectedValue)
	{
		final String attributeKey = attribute.getValue();
		final ImmutableAttributeSet attributeSet = ImmutableAttributeSet.builder()
				.attributeValue(attribute, inputValue)
				.build();

		assertThat(attributeSet.getValueAsBigDecimal(attributeKey)).isEqualTo(expectedValue);
	}

}
