/*
 * #%L
 * de.metas.business
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

package de.metas.product;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static de.metas.util.Services.get;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the central product life-cycle enforcement predicate: {@link IProductBL#isAllowed(ProductId, ProductLifeCycleAction)}
 * and {@link IProductBL#assertAllowed(ProductId, ProductLifeCycleAction)}.
 */
class ProductBL_assertAllowed_Test
{
	private IProductBL productBL;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		productBL = get(IProductBL.class);
	}

	private ProductId createProduct(final String productLifeCycleStatus)
	{
		final I_M_Product p = InterfaceWrapperHelper.newInstance(I_M_Product.class);
		p.setValue("P");
		p.setName("Test Product");
		p.setProductLifeCycleStatus(productLifeCycleStatus);
		InterfaceWrapperHelper.saveRecord(p);
		return ProductId.ofRepoId(p.getM_Product_ID());
	}

	@Test
	void nullStatus_isFullyPermissive()
	{
		final ProductId productId = createProduct(null);
		for (final ProductLifeCycleAction action : ProductLifeCycleAction.values())
		{
			assertThat(productBL.isAllowed(productId, action)).as("action=%s", action).isTrue();
			assertThatCode(() -> productBL.assertAllowed(productId, action)).as("action=%s", action).doesNotThrowAnyException();
		}
	}

	@Test
	void ok_isFullyPermissive()
	{
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);
		for (final ProductLifeCycleAction action : ProductLifeCycleAction.values())
		{
			assertThat(productBL.isAllowed(productId, action)).as("action=%s", action).isTrue();
			assertThatCode(() -> productBL.assertAllowed(productId, action)).as("action=%s", action).doesNotThrowAnyException();
		}
	}

	@Test
	void phaseOut_blocksPurchaseOnly()
	{
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut);

		assertThat(productBL.isAllowed(productId, ProductLifeCycleAction.PURCHASE)).isFalse();
		assertThatThrownBy(() -> productBL.assertAllowed(productId, ProductLifeCycleAction.PURCHASE))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());

		for (final ProductLifeCycleAction action : new ProductLifeCycleAction[] {
				ProductLifeCycleAction.SELL, ProductLifeCycleAction.PICK, ProductLifeCycleAction.MANUFACTURE, ProductLifeCycleAction.SHIP })
		{
			assertThat(productBL.isAllowed(productId, action)).as("action=%s", action).isTrue();
			assertThatCode(() -> productBL.assertAllowed(productId, action)).as("action=%s", action).doesNotThrowAnyException();
		}
	}

	@Test
	void blocked_blocksAllActions()
	{
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);

		for (final ProductLifeCycleAction action : ProductLifeCycleAction.values())
		{
			assertThat(productBL.isAllowed(productId, action)).as("action=%s", action).isFalse();
			assertThatThrownBy(() -> productBL.assertAllowed(productId, action))
					.as("action=%s", action)
					.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());
		}
	}

	@Test
	void doNotDeliver_blocksShipOnly()
	{
		final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_DeliveryStop);

		assertThat(productBL.isAllowed(productId, ProductLifeCycleAction.SHIP)).isFalse();
		assertThatThrownBy(() -> productBL.assertAllowed(productId, ProductLifeCycleAction.SHIP))
				.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());

		for (final ProductLifeCycleAction action : new ProductLifeCycleAction[] {
				ProductLifeCycleAction.PURCHASE, ProductLifeCycleAction.SELL, ProductLifeCycleAction.PICK, ProductLifeCycleAction.MANUFACTURE })
		{
			assertThat(productBL.isAllowed(productId, action)).as("action=%s", action).isTrue();
			assertThatCode(() -> productBL.assertAllowed(productId, action)).as("action=%s", action).doesNotThrowAnyException();
		}
	}
}
