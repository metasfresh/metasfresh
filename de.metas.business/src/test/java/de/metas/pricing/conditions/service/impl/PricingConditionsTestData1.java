package de.metas.pricing.conditions.service.impl;

import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Services;
import org.assertj.core.api.AbstractIntegerAssert;
import org.compiere.model.I_M_DiscountSchema;
import org.compiere.model.I_M_DiscountSchemaBreak;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.junit.Ignore;

import de.metas.pricing.conditions.PricingConditions;
import de.metas.pricing.conditions.PricingConditionsBreak;
import de.metas.pricing.conditions.PricingConditionsBreakQuery;
import de.metas.pricing.conditions.service.IPricingConditionsRepository;
import de.metas.pricing.conditions.service.impl.PricingConditionsRepository;

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

@Ignore
public class PricingConditionsTestData1
{
	public static PricingConditionsTestData1 newInstance()
	{
		return new PricingConditionsTestData1();
	}

	private final PricingConditionsRepository pricingConditionsRepo;

	private final I_M_Product product;

	public final I_M_DiscountSchema schema;

	public final List<I_M_DiscountSchemaBreak> breaks;
	public final I_M_DiscountSchemaBreak break10;
	public final I_M_DiscountSchemaBreak break20;

	private PricingConditionsTestData1()
	{
		pricingConditionsRepo = (PricingConditionsRepository)Services.get(IPricingConditionsRepository.class);

		final I_M_Product_Category productCategory = PricingConditionsTestUtils.createM_ProductCategory("Category1");
		product = PricingConditionsTestUtils.createM_Product("Product1", productCategory);

		schema = PricingConditionsTestUtils.createSchema();
		breaks = new ArrayList<>();

		break10 = PricingConditionsTestUtils.createBreak(schema, 10);
		break10.setM_Product_ID(product.getM_Product_ID());
		break10.setBreakValue(BigDecimal.valueOf(10));
		break10.setBreakDiscount(BigDecimal.valueOf(10));
		save(break10);
		breaks.add(break10);

		break20 = PricingConditionsTestUtils.createBreak(schema, 20);
		break20.setM_Product_ID(product.getM_Product_ID());
		break20.setBreakValue(BigDecimal.valueOf(20));
		break20.setBreakDiscount(BigDecimal.valueOf(20));
		save(break20);
		breaks.add(break20);
	}

	public PricingConditions getPricingConditions()
	{
		return pricingConditionsRepo.retrievePricingConditionsById(schema.getM_DiscountSchema_ID());
	}

	public PricingConditionsBreakQuery createQueryForQty(final int qty)
	{
		return PricingConditionsTestUtils.createQueryForQty(product, qty);
	}

	public AbstractIntegerAssert<?> assertSchemaBreakIdForQty(final int qty)
	{
		final PricingConditions pricingConditions = getPricingConditions();
		final PricingConditionsBreak actualSchemaBreak = pricingConditions.pickApplyingBreak(createQueryForQty(qty));

		assertThat(actualSchemaBreak).isNotNull();
		return assertThat(actualSchemaBreak.getDiscountSchemaBreakId());
	}
}
