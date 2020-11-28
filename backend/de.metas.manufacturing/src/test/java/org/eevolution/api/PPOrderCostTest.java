/*
 * #%L
 * de.metas.adempiere.libero.libero
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

package org.eevolution.api;

import de.metas.acct.api.AcctSchemaId;
import de.metas.business.BusinessTestHelper;
import de.metas.costing.CostAmount;
import de.metas.costing.CostElementId;
import de.metas.costing.CostPrice;
import de.metas.costing.CostSegmentAndElement;
import de.metas.costing.CostTypeId;
import de.metas.costing.CostingLevel;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.QuantityUOMConverters;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PPOrderCostTest
{
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	private final AcctSchemaId acctSchemaId = AcctSchemaId.ofRepoId(1);
	private final CostTypeId costTypeId = CostTypeId.ofRepoId(1);
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(1);
	private final CostElementId costElementId = CostElementId.ofRepoId(1);

	private I_C_UOM uomEach;
	private UomId uomEachId;

	private I_C_UOM uomKg;
	private UomId uomKgId;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		uomEach = BusinessTestHelper.createUomEach();
		uomEachId = UomId.ofRepoId(uomEach.getC_UOM_ID());

		uomKg = BusinessTestHelper.createUomKg();
		uomKgId = UomId.ofRepoId(uomKg.getC_UOM_ID());
	}

	private CostSegmentAndElement costSegmentAndElement(final ProductId productId)
	{
		return CostSegmentAndElement.builder()
				.costingLevel(CostingLevel.Organization)
				.acctSchemaId(acctSchemaId)
				.costTypeId(costTypeId)
				.clientId(clientId)
				.orgId(orgId)
				.productId(productId)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.costElementId(costElementId)
				.build();
	}

	@Test
	public void checkSameUomIsEnforced()
	{
		assertThatThrownBy(() -> PPOrderCost.builder()
				.trxType(PPOrderCostTrxType.MaterialIssue)
				.costSegmentAndElement(costSegmentAndElement(ProductId.ofRepoId(1)))
				.price(CostPrice.zero(currencyId, uomKgId))
				.accumulatedQty(Quantity.zero(uomEach))
				.build())
				.isInstanceOf(AdempiereException.class)
				.hasMessageStartingWith("UOM not matching");
	}

	@Nested
	public class addingAccumulatedAmountAndQty
	{
		@Test
		public void sameUOM()
		{
			final PPOrderCost ppOrderCost = PPOrderCost.builder()
					.trxType(PPOrderCostTrxType.MaterialIssue)
					.costSegmentAndElement(costSegmentAndElement(ProductId.ofRepoId(1)))
					.price(CostPrice.zero(currencyId, uomKgId))
					.accumulatedQty(Quantity.zero(uomKg))
					.build();

			final PPOrderCost ppOrderCost2 = ppOrderCost.addingAccumulatedAmountAndQty(
					CostAmount.of(100, currencyId),
					Quantity.of(10, uomKg),
					QuantityUOMConverters.noConversion());

			assertThat(ppOrderCost2.getAccumulatedAmount()).isEqualTo(CostAmount.of(100, currencyId));
			assertThat(ppOrderCost2.getAccumulatedQty()).isEqualTo(Quantity.of(10, uomKg));
		}

		@Test
		public void differentUOM()
		{
			final ProductId productId = BusinessTestHelper.createProductId("product", uomEach);

			final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class);
			uomConversionBL.createUOMConversion(CreateUOMConversionRequest.builder()
					.productId(productId)
					.fromUomId(uomEachId)
					.toUomId(uomKgId)
					.fromToMultiplier(new BigDecimal("3"))
					.build());

			final PPOrderCost ppOrderCost = PPOrderCost.builder()
					.trxType(PPOrderCostTrxType.MaterialIssue)
					.costSegmentAndElement(costSegmentAndElement(productId))
					.price(CostPrice.zero(currencyId, uomKgId))
					.accumulatedQty(Quantity.zero(uomKg))
					.build();

			final PPOrderCost ppOrderCost2 = ppOrderCost.addingAccumulatedAmountAndQty(
					CostAmount.of(100, currencyId),
					Quantity.of(1, uomEach),
					uomConversionBL);

			assertThat(ppOrderCost2.getAccumulatedAmount()).isEqualTo(CostAmount.of(100, currencyId));
			assertThat(ppOrderCost2.getAccumulatedQty()).isEqualTo(Quantity.of(3, uomKg));
		}
	}
}