package de.metas.pricing.conditions.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.junit.Before;
import org.junit.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.pricing.conditions.PricingConditionsBreakMatchCriteria;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;

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

public class PricingConditionsRepositoryTest
{
	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void test_toPricingConditionsBreakMatchCriteria()
	{
		final I_M_DiscountSchemaBreak schemaBreakRecord = newInstance(I_M_DiscountSchemaBreak.class);
		schemaBreakRecord.setBreakValue(new BigDecimal("10"));
		schemaBreakRecord.setM_Product_ID(10);
		schemaBreakRecord.setM_Product_Category_ID(20);
		schemaBreakRecord.setManufacturer_ID(30);
		schemaBreakRecord.setM_AttributeValue_ID(40);
		save(schemaBreakRecord);

		final PricingConditionsBreakMatchCriteria matchingCriteria = PricingConditionsRepository.toPricingConditionsBreakMatchCriteria(schemaBreakRecord);

		assertEquals(schemaBreakRecord, matchingCriteria);
	}

	@Test
	public void test_updateSchemaBreakRecordFromRecordFromMatchCriteria()
	{
		final PricingConditionsBreakMatchCriteria matchingCriteria = PricingConditionsBreakMatchCriteria.builder()
				.breakValue(new BigDecimal("10"))
				.productId(ProductId.ofRepoId(10))
				.productCategoryId(ProductCategoryId.ofRepoId(20))
				.productManufacturerId(BPartnerId.ofRepoId(30))
				.attributeValueId(40)
				.build();

		final I_M_DiscountSchemaBreak schemaBreakRecord = newInstance(I_M_DiscountSchemaBreak.class);
		PricingConditionsRepository.updateSchemaBreakRecordFromRecordFromMatchCriteria(schemaBreakRecord, matchingCriteria);

		assertEquals(schemaBreakRecord, matchingCriteria);
	}

	private static void assertEquals(final I_M_DiscountSchemaBreak schemaBreakRecord, final PricingConditionsBreakMatchCriteria matchingCriteria)
	{
		assertThat(matchingCriteria.getBreakValue()).isEqualByComparingTo(schemaBreakRecord.getBreakValue());
		assertThat(matchingCriteria.getProductId()).isEqualTo(ProductId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_ID()));
		assertThat(matchingCriteria.getProductCategoryId()).isEqualTo(ProductCategoryId.ofRepoIdOrNull(schemaBreakRecord.getM_Product_Category_ID()));
		assertThat(matchingCriteria.getProductManufacturerId()).isEqualTo(BPartnerId.ofRepoIdOrNull(schemaBreakRecord.getManufacturer_ID()));
		assertThat(matchingCriteria.getAttributeValueId()).isEqualTo(schemaBreakRecord.getM_AttributeValue_ID());
	}
}
