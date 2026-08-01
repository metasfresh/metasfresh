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

import de.metas.ad_reference.ADRefListItemCreateRequest;
import de.metas.ad_reference.ADReferenceService;
import de.metas.ad_reference.ReferenceId;
import de.metas.i18n.TranslatableStrings;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
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
	private ADReferenceService adReferenceService;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		// assertAllowed resolves the blocked status' human-readable name via ADReferenceService; register a
		// mocked one (auto-creates ref-lists on demand) so the guard's error path works in the POJO env.
		adReferenceService = ADReferenceService.newMocked();
		SpringContextHolder.registerJUnitBean(adReferenceService);
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

	@Nested
	class Blocked
	{
		@Test
		void blocksAllActions()
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
		void error_resolvesHumanReadableStatusName()
		{
			// The action-blocked error must surface the human-readable, locale-resolved status name
			// ("Gesperrt"), not the raw code "G". Register the ref-list item so the lookup resolves to a
			// real name instead of falling back to the raw code — the item-found branch the other tests
			// never exercise (the auto-created mock ref-list has no items).
			adReferenceService.saveRefList(ADRefListItemCreateRequest.builder()
					.referenceId(ReferenceId.ofRepoId(X_M_Product.PRODUCTLIFECYCLESTATUS_AD_Reference_ID))
					.value(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked)
					.name(TranslatableStrings.constant("Gesperrt"))
					.build());

			// The exception hands this ITranslatableString to AdempiereException as a message param, so the
			// blocked message renders "Gesperrt" once the AD_Message template is loaded (verified end-to-end
			// against the real stack, not here — the POJO harness has no AD_Message so {0}/{1} don't
			// interpolate; this asserts the resolution the guard relies on).
			assertThat(adReferenceService
					.retrieveListNameTranslatableString(X_M_Product.PRODUCTLIFECYCLESTATUS_AD_Reference_ID, X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked)
					.translate("de_DE"))
					.as("resolved status name")
					.isEqualTo("Gesperrt");

			final ProductId productId = createProduct(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);
			assertThatThrownBy(() -> productBL.assertAllowed(productId, ProductLifeCycleAction.PICK))
					.isInstanceOfSatisfying(AdempiereException.class, ex -> assertThat(ex.isUserValidationError()).isTrue());
		}
	}
}
