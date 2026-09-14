package de.metas.purchasecandidate.material.event;

import de.metas.material.planning.MaterialPlanningContext;
import de.metas.material.planning.ProductPlanning;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.product.ProductLifeCycleAction;
import de.metas.product.ResourceId;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.warehouse.WarehouseId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * #%L
 * de.metas.purchasecandidate.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

public class PurchaseOrderDemandMatcherTest
{
	private static final ProductId PRODUCT_ID = ProductId.ofRepoId(202);
	private static final OrgId ORG_ID = OrgId.ofRepoId(1);

	private IProductBL productBL;
	private PurchaseOrderDemandMatcher matcher;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		productBL = mock(IProductBL.class);
		Services.registerService(IProductBL.class, productBL);

		// must be constructed AFTER the mock is registered (productBL is a Services.get(...) field)
		matcher = new PurchaseOrderDemandMatcher();
	}

	@Test
	public void matches_productBlockedForPurchase_returnsFalse()
	{
		when(productBL.isAllowed(PRODUCT_ID, ProductLifeCycleAction.PURCHASE)).thenReturn(false);

		// even a purchased product must be rejected when the life-cycle status blocks PURCHASE
		final MaterialPlanningContext context = buildContext(buildProductPlanning(/* isPurchased */ true));

		assertThat(matcher.matches(context)).isFalse();
	}

	@Test
	public void matches_allowedAndPurchased_returnsTrue()
	{
		when(productBL.isAllowed(PRODUCT_ID, ProductLifeCycleAction.PURCHASE)).thenReturn(true);

		final MaterialPlanningContext context = buildContext(buildProductPlanning(/* isPurchased */ true));

		assertThat(matcher.matches(context)).isTrue();
	}

	private static ProductPlanning buildProductPlanning(final boolean isPurchased)
	{
		return ProductPlanning.builder()
				.orgId(ORG_ID)
				.productId(PRODUCT_ID)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.isPurchased(isPurchased)
				.build();
	}

	private static MaterialPlanningContext buildContext(final ProductPlanning productPlanning)
	{
		return MaterialPlanningContext.builder()
				.productId(PRODUCT_ID)
				.attributeSetInstanceId(AttributeSetInstanceId.NONE)
				.warehouseId(WarehouseId.ofRepoId(40))
				.productPlanning(productPlanning)
				.plantId(ResourceId.ofRepoId(50))
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(5, ORG_ID.getRepoId()))
				.build();
	}
}
