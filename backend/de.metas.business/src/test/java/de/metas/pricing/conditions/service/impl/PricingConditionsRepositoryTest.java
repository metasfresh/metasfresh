package de.metas.pricing.conditions.service.impl;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.mm.attributes.AttributeValueId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.X_M_DiscountSchemaBreak;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.bpartner.BPartnerId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.payment.paymentterm.PaymentTermService;
import de.metas.pricing.conditions.PriceSpecification;
import de.metas.pricing.conditions.PriceSpecificationType;
import de.metas.pricing.conditions.PricingConditionsBreak;
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
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new PaymentTermService());
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
				.attributeValueId(AttributeValueId.ofRepoId(40))
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
		assertThat(matchingCriteria.getAttributeValueId()).isEqualTo(AttributeValueId.ofRepoIdOrNull(schemaBreakRecord.getM_AttributeValue_ID()));
	}

	/** Tests with a schemaBreakRecord that has a fixed base price. */
	@Test
	public void toPricingConditionsBreak_PriceOverride()
	{
		final I_M_DiscountSchemaBreak schemaBreakRecord = createDiscountSchemaBreakRecord();
		saveRecord(schemaBreakRecord);

		// invoke the method under test
		final PricingConditionsBreak result = PricingConditionsRepository.toPricingConditionsBreak(schemaBreakRecord);

		final PriceSpecification priceSpecification = result.getPriceSpecification();
		assertThat(priceSpecification.getType()).isEqualTo(PriceSpecificationType.FIXED_PRICE);
		assertThat(priceSpecification.getFixedPrice()).isEqualTo(Money.of(TEN, CurrencyId.ofRepoId(10)));
	}

	/** Tests with a schemaBreakRecord that has *no* base price (neither "fixed" nor "pricing-system") */
	@Test
	public void toPricingConditionsBreak_no_PriceOverride()
	{
		final I_M_DiscountSchemaBreak schemaBreakRecord = createDiscountSchemaBreakRecord();
		schemaBreakRecord.setPriceBase(null);
		saveRecord(schemaBreakRecord);

		// invoke the method under test
		final PricingConditionsBreak result = PricingConditionsRepository.toPricingConditionsBreak(schemaBreakRecord);

		assertThat(result.getPriceSpecification().getType()).isEqualTo(PriceSpecificationType.NONE);
	}

	private I_M_DiscountSchemaBreak createDiscountSchemaBreakRecord()
	{
		final I_M_DiscountSchemaBreak schemaBreakRecord = newInstance(I_M_DiscountSchemaBreak.class);
		schemaBreakRecord.setM_DiscountSchema_ID(20);

		schemaBreakRecord.setPriceBase(X_M_DiscountSchemaBreak.PRICEBASE_Fixed);
		schemaBreakRecord.setC_Currency_ID(10);
		schemaBreakRecord.setPriceStdFixed(TEN);
		return schemaBreakRecord;
	}
}
