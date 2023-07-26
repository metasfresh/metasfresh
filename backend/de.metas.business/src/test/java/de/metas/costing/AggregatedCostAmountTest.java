package de.metas.costing;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Set;
import java.util.stream.IntStream;

import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import de.metas.acct.api.AcctSchemaId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;

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

public class AggregatedCostAmountTest
{
	private final ClientId clientId = ClientId.ofRepoId(1);
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);

	@Test
	public void test()
	{
		final AggregatedCostAmount amt = AggregatedCostAmount.builder()
				.costSegment(createCostSegment())
				.amount(createCostElement(1, CostingMethod.AverageInvoice), CostAmount.of(1000, currencyId))
				.amount(createCostElement(2, CostingMethod.AveragePO), CostAmount.of(100, currencyId))
				.amount(createCostElement(3, CostingMethod.AveragePO), CostAmount.of(10, currencyId))
				.amount(createCostElement(4, CostingMethod.AveragePO), CostAmount.of(1, currencyId))
				.build();

		assertThat(amt.getTotalAmount(CostingMethod.AveragePO, null).get())
				.isEqualTo(CostAmount.of(111, currencyId));

		assertThat(amt.getTotalAmount(CostingMethod.AveragePO, ImmutableSet.of()).get())
				.isEqualTo(CostAmount.of(111, currencyId));

		assertThat(amt.getTotalAmount(CostingMethod.AveragePO, costElementIds(2, 4)).get())
				.isEqualTo(CostAmount.of(101, currencyId));
	}

	private CostSegment createCostSegment()
	{
		return CostSegment.builder()
				.acctSchemaId(AcctSchemaId.ofRepoId(1))
				.costTypeId(CostTypeId.ofRepoId(1))
				.costingLevel(CostingLevel.Client)
				.clientId(clientId)
				.orgId(OrgId.ANY)
				.productId(ProductId.ofRepoId(2))
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.build();
	}

	private CostElement createCostElement(final int id, final CostingMethod costingMethod)
	{
		return CostElement.builder()
				.id(CostElementId.ofRepoId(id))
				.name("cost element " + id)
				.costElementType(CostElementType.Material)
				.costingMethod(costingMethod)
				.clientId(clientId)
				.build();
	}

	private static Set<CostElementId> costElementIds(final int... ids)
	{
		return IntStream.of(ids)
				.mapToObj(CostElementId::ofRepoId)
				.collect(ImmutableSet.toImmutableSet());
	}
}
