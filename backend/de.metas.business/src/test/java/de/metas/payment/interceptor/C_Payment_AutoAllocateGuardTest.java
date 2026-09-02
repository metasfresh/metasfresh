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

package de.metas.payment.interceptor;

import de.metas.invoice.InvoiceId;
import de.metas.invoice.proforma.ProformaOrderAllocService;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Payment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * RED TDD test for the not-yet-existing {@code C_Payment_AutoAllocateGuard} interceptor (AC #15).
 * <p>
 * Phase 7 / Task 41 of the iter-3 split-payment plan
 * (https://github.com/metasfresh/me03/issues/29369).
 * <p>
 * The guard interceptor must reject flipping {@code IsAutoAllocateAvailableAmt} from {@code N} to
 * {@code Y} on a proforma-prepayment payment (any {@code C_Payment} whose
 * {@code Proforma_Invoice_ID} is set). On regular payments the flip is allowed; keeping the flag
 * at {@code N} on a proforma-prepayment payment is also allowed (it is the AC-mandated default).
 * <p>
 * Until {@code C_Payment_AutoAllocateGuard} (Task 42) is implemented and registered with
 * {@link org.adempiere.ad.wrapper.POJOLookupMap#addModelValidator}, no save-time enforcement
 * exists, so {@link #rejectFlipToY_onProformaPrepayment()} fails RED (no exception thrown).
 * Tests 2 + 3 pass coincidentally — when the interceptor lands, all three must remain green.
 * <p>
 * Fixture: in-memory POJO models via {@link AdempiereTestHelper}, mirroring
 * {@code C_Invoice_IsPartialInvoiceTest} (Phase 2). No Spring context, no DB.
 */
class C_Payment_AutoAllocateGuardTest
{
	/** Stand-in proforma {@code C_Invoice_ID}. Any positive int marks the payment as a proforma prepayment. */
	private static final int PROFORMA_INVOICE_ID = 1234567;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// Register the interceptor under test. {@link C_Payment_AutoAllocateGuard} is a Spring
		// {@code @Component} in production; in this in-memory POJO test we wire it manually via
		// {@link POJOLookupMap#addModelValidator(Object)} so {@link InterfaceWrapperHelper#save}
		// fires its {@code @ModelChange} hook. Mirrors the pattern used in
		// {@code PP_Product_PlanningTest} (de.metas.manufacturing).
		//
		// The {@link ProformaOrderAllocService} dependency is mocked: the only test that needs
		// it to return {@code true} is {@link #rejectFlipToY_onProformaPrepayment()}, and any
		// {@link InvoiceId} the guard might pass through (the stand-in {@code PROFORMA_INVOICE_ID})
		// must trigger the rejection branch. Returning {@code true} unconditionally is safe
		// because the regular-payment test never reaches the {@code hasAllocations} call (its
		// {@code Proforma_Invoice_ID} stays at 0, so the guard short-circuits earlier) and the
		// keep-N test never throws either (the guard returns on the {@code !isAutoAllocateAvailableAmt}
		// check before reaching the service).
		final ProformaOrderAllocService proformaOrderAllocService = Mockito.mock(ProformaOrderAllocService.class);
		Mockito.when(proformaOrderAllocService.hasAllocations(Mockito.any(InvoiceId.class))).thenReturn(true);

		POJOLookupMap.get().addModelValidator(new C_Payment_AutoAllocateGuard(proformaOrderAllocService));
	}

	// -------------------------------------------------------------------------
	// AC #15 — flipping IsAutoAllocateAvailableAmt to Y is rejected on proforma prepayments
	// -------------------------------------------------------------------------

	/**
	 * Saving a proforma-prepayment payment with {@code IsAutoAllocateAvailableAmt='Y'} must fail
	 * with an {@link AdempiereException} whose translated message references the column name.
	 * <p>
	 * RED until Task 42: with no interceptor registered, the save succeeds silently and
	 * {@code assertThatThrownBy} reports "no exception thrown".
	 */
	@Test
	void rejectFlipToY_onProformaPrepayment()
	{
		final I_C_Payment prepay = newProformaPrepaymentPayment();
		prepay.setIsAutoAllocateAvailableAmt(true);

		assertThatThrownBy(() -> InterfaceWrapperHelper.save(prepay))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("IsAutoAllocateAvailableAmt");
	}

	/**
	 * A non-proforma payment may freely flip {@code IsAutoAllocateAvailableAmt} to {@code Y};
	 * the guard must not interfere with the iter-2 / pre-existing auto-allocation flow on regular
	 * AR/AP payments.
	 */
	@Test
	void allowFlipToY_onRegularPayment()
	{
		final I_C_Payment regular = newRegularPayment();
		regular.setIsAutoAllocateAvailableAmt(true);

		assertThatCode(() -> InterfaceWrapperHelper.save(regular))
				.doesNotThrowAnyException();
		assertThat(regular.isAutoAllocateAvailableAmt()).isTrue();
	}

	/**
	 * Keeping the flag at {@code N} on a proforma-prepayment payment is the AC-mandated default
	 * and must save without complaint — the guard fires only on flip-to-Y.
	 */
	@Test
	void allowKeepN_onProformaPrepayment()
	{
		final I_C_Payment prepay = newProformaPrepaymentPayment();
		prepay.setIsAutoAllocateAvailableAmt(false);  // explicit, no-op

		assertThatCode(() -> InterfaceWrapperHelper.save(prepay))
				.doesNotThrowAnyException();
		assertThat(prepay.isAutoAllocateAvailableAmt()).isFalse();
	}

	// -------------------------------------------------------------------------
	// Fixture helpers
	// -------------------------------------------------------------------------

	/**
	 * Build a proforma-prepayment payment: identified by {@code Proforma_Invoice_ID > 0} (the
	 * canonical "is this a proforma prepayment" check used by {@code C_Payment_LCStep} and the
	 * iter-2 proforma flow). The flag starts at {@code N} (default boolean).
	 */
	private I_C_Payment newProformaPrepaymentPayment()
	{
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setProforma_Invoice_ID(PROFORMA_INVOICE_ID);
		payment.setIsAutoAllocateAvailableAmt(false);
		InterfaceWrapperHelper.saveRecord(payment);
		return payment;
	}

	/**
	 * Build a regular (non-proforma) payment — {@code Proforma_Invoice_ID} stays at 0 / unset,
	 * so the guard's "is proforma prepayment?" predicate returns false.
	 */
	private I_C_Payment newRegularPayment()
	{
		final I_C_Payment payment = InterfaceWrapperHelper.newInstance(I_C_Payment.class);
		payment.setIsAutoAllocateAvailableAmt(false);
		InterfaceWrapperHelper.saveRecord(payment);
		return payment;
	}
}
