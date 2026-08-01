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

package de.metas.purchasecandidate.interceptor;

import de.metas.ad_reference.ADReferenceService;
import de.metas.purchasecandidate.PurchaseCandidateRepository;
import de.metas.purchasecandidate.model.I_C_PurchaseCandidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_Product;
import org.compiere.model.ModelValidator;
import org.compiere.model.X_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Tests the product life-cycle guard
 * {@link C_PurchaseCandidate#assertProductAllowedForPurchase(I_C_PurchaseCandidate)}.
 * <p>
 * This guard is the single architectural enforcement point for the product's PURCHASE life-cycle
 * status on purchase-candidate creation. It replaces the former guard in
 * {@code PurchaseCandidateRepository.createOrUpdateRecord} (which lived in a {@code @Repository},
 * violating architecture.md §8) and, being a {@code C_PurchaseCandidate} interceptor, it covers
 * ALL creation paths that {@code newInstance + save} such a record: the material-event
 * {@code PurchaseCandidateRequestedHandler}, the REST {@code CreatePurchaseCandidatesService}, and
 * the manual Sales-Order&rarr;Purchase WebUI {@code PurchaseRowsSaver}.
 * <p>
 * The guard method is exercised directly (rather than through a full {@code save}) so it is isolated
 * from the interceptor's unrelated {@code AFTER_NEW} profit-update hook. Its {@code TYPE_BEFORE_NEW}-only
 * timing — which guarantees an update of an already-open candidate is never retroactively blocked
 * (AC6) — is asserted separately against the {@code @ModelChange} annotation.
 */
class C_PurchaseCandidateTest
{
	private C_PurchaseCandidate interceptor;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(ADReferenceService.newMocked());
		interceptor = new C_PurchaseCandidate(Mockito.mock(PurchaseCandidateRepository.class));
	}

	private I_C_PurchaseCandidate candidateForProductStatus(final String lifeCycleStatus)
	{
		final I_M_Product product = newInstance(I_M_Product.class);
		product.setValue("P-" + lifeCycleStatus);
		product.setName("Test Product");
		product.setProductLifeCycleStatus(lifeCycleStatus);
		saveRecord(product);

		final I_C_PurchaseCandidate candidate = newInstance(I_C_PurchaseCandidate.class);
		candidate.setM_Product_ID(product.getM_Product_ID());
		return candidate;
	}

	@Test
	void blockedProduct_isRejectedForPurchase()
	{
		// "G" (Gesperrt) blocks every action, incl. PURCHASE
		final I_C_PurchaseCandidate candidate = candidateForProductStatus(X_M_Product.PRODUCTLIFECYCLESTATUS_Blocked);
		assertThatThrownBy(() -> interceptor.assertProductAllowedForPurchase(candidate))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("M_Product_BBSStatus_ActionBlocked");
	}

	@Test
	void auslaufProduct_isRejectedForPurchase()
	{
		// "A" (Auslauf) blocks PURCHASE specifically
		final I_C_PurchaseCandidate candidate = candidateForProductStatus(X_M_Product.PRODUCTLIFECYCLESTATUS_PhaseOut);
		assertThatThrownBy(() -> interceptor.assertProductAllowedForPurchase(candidate))
				.isInstanceOf(AdempiereException.class);
	}

	@Test
	void okProduct_isAllowedForPurchase()
	{
		final I_C_PurchaseCandidate candidate = candidateForProductStatus(X_M_Product.PRODUCTLIFECYCLESTATUS_OK);
		assertThatCode(() -> interceptor.assertProductAllowedForPurchase(candidate)).doesNotThrowAnyException();
	}

	@Test
	void nullStatusProduct_isAllowedForPurchase()
	{
		final I_C_PurchaseCandidate candidate = candidateForProductStatus(null);
		assertThatCode(() -> interceptor.assertProductAllowedForPurchase(candidate)).doesNotThrowAnyException();
	}

	/**
	 * Retroactive-safety (AC6): the guard must be {@code TYPE_BEFORE_NEW}-only, so updating an
	 * already-existing candidate whose product was blocked <i>after</i> creation never fires it.
	 */
	@Test
	void guard_isBeforeNewOnly() throws NoSuchMethodException
	{
		final ModelChange annotation = C_PurchaseCandidate.class
				.getMethod("assertProductAllowedForPurchase", I_C_PurchaseCandidate.class)
				.getAnnotation(ModelChange.class);

		assertThat(annotation).isNotNull();
		assertThat(annotation.timings()).containsExactly(ModelValidator.TYPE_BEFORE_NEW);
	}
}
