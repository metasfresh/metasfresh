/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.rest_api.utils;

import de.metas.business.BusinessTestHelper;
import de.metas.common.rest_api.JsonAttributeSetInstance;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.mm.attributes.api.ImmutableAttributeSet;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeInstance;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_M_Attribute;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class RestApiUtilsTest
{

	final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	private OrgId orgId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		orgId = BusinessTestHelper.createOrgWithTimeZone();
	}

	@Test
	void extractJsonAttributeSetInstance_empty()
	{
		final I_M_AttributeSetInstance asiRecord = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asiRecord);

		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(asiRecord.getM_AttributeSetInstance_ID()));

		assertThat(attributeSet.isEmpty()).isTrue();
	}

	@Test
	void extractJsonAttributeSetInstance()
	{
		// given
		final I_M_AttributeSetInstance asiRecord = newInstance(I_M_AttributeSetInstance.class);
		saveRecord(asiRecord);

		final I_M_Attribute attrRecord1 = newInstance(I_M_Attribute.class);
		attrRecord1.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_StringMax40);
		attrRecord1.setValue("valueStr");
		attrRecord1.setName("nameStr");
		saveRecord(attrRecord1);

		final I_M_AttributeInstance aiRecord1 = newInstance(I_M_AttributeInstance.class);
		aiRecord1.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		aiRecord1.setM_Attribute_ID(attrRecord1.getM_Attribute_ID());
		aiRecord1.setValue("value1");
		saveRecord(aiRecord1);

		final I_M_Attribute attrRecord2 = newInstance(I_M_Attribute.class);
		attrRecord2.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Date);
		attrRecord2.setValue("valueDate");
		attrRecord2.setName("nameDate");
		saveRecord(attrRecord2);

		final I_M_AttributeInstance aiRecord2 = newInstance(I_M_AttributeInstance.class);
		aiRecord2.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		aiRecord2.setM_Attribute_ID(attrRecord2.getM_Attribute_ID());
		aiRecord2.setValueDate(Timestamp.valueOf("2020-07-20 09:48:12"));
		saveRecord(aiRecord2);

		final I_M_Attribute attrRecord3 = newInstance(I_M_Attribute.class);
		attrRecord3.setAttributeValueType(X_M_Attribute.ATTRIBUTEVALUETYPE_Number);
		attrRecord3.setValue("valueNumber");
		attrRecord3.setName("nameNumber");
		saveRecord(attrRecord3);

		final I_M_AttributeInstance aiRecord3 = newInstance(I_M_AttributeInstance.class);
		aiRecord3.setM_AttributeSetInstance_ID(asiRecord.getM_AttributeSetInstance_ID());
		aiRecord3.setM_Attribute_ID(attrRecord3.getM_Attribute_ID());
		aiRecord3.setValueNumber(BigDecimal.TEN);
		saveRecord(aiRecord3);

		final ImmutableAttributeSet attributeSet = attributeDAO.getImmutableAttributeSetById(AttributeSetInstanceId.ofRepoId(asiRecord.getM_AttributeSetInstance_ID()));

		// when
		final JsonAttributeSetInstance result = RestApiUtils.extractJsonAttributeSetInstance(attributeSet, orgId);

		// then
		assertThat(result.getAttributeInstances()).hasSize(3);
		assertThat(result.getCode2Instance().get("valueStr").getValueStr()).isEqualTo("value1");
		assertThat(result.getCode2Instance().get("valueDate").getValueDate()).isEqualTo(LocalDate.of(2020, 7, 20)); // it's date; not dateTime
		assertThat(result.getCode2Instance().get("valueNumber").getValueNumber()).isEqualTo(BigDecimal.TEN);
	}
}