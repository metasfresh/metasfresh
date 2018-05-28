package de.metas.inout.invoicecandidate;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;

import javax.annotation.Nullable;

import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Condition;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_PaymentTerm;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_M_InOut;
import org.junit.Before;
import org.junit.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.document.engine.IDocument;
import de.metas.inout.model.I_M_InOut;
import de.metas.interfaces.I_C_BPartner;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_M_InOutLine;

/*
 * #%L
 * de.metas.swat.base
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

public class M_InOutLine_HandlerTest
{
	private static final BigDecimal TWO = new BigDecimal(2);
	private static final BigDecimal FOUR = new BigDecimal(4);
	private static final BigDecimal FIVE = new BigDecimal(5);
	private static final BigDecimal SIX = new BigDecimal(6);
	private static final BigDecimal EIGHT = new BigDecimal(8);
	private static final BigDecimal NINE = new BigDecimal(9);

	private I_M_InOutLine packagingInOutLine;
	private I_M_InOut inout;
	private I_C_PaymentTerm paymentTermA;
	private I_C_PaymentTerm paymentTermB;
	private M_InOutLine_Handler inOutLineHandlerUnderTest;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();
		BusinessTestHelper.createDefaultBusinessRecords();

		final I_C_BPartner bPartner = newInstance(I_C_BPartner.class);
		save(bPartner);

		final I_C_BPartner_Location bPartnerLocation = newInstance(I_C_BPartner_Location.class);
		bPartnerLocation.setIsBillTo(true);
		bPartnerLocation.setC_BPartner(bPartner);
		save(bPartnerLocation);

		inout = newInstance(I_M_InOut.class);
		inout.setIsSOTrx(true);
		inout.setDocStatus(IDocument.STATUS_Completed); // otherwise the code won't consider the inoutLines' quantities
		inout.setC_BPartner(bPartner);
		save(inout);

		final I_M_Product packagingProduct = newInstance(I_M_Product.class);
		save(packagingProduct);

		packagingInOutLine = newInstance(I_M_InOutLine.class);
		packagingInOutLine.setM_InOut(inout);
		packagingInOutLine.setM_Product(packagingProduct);
		packagingInOutLine.setIsPackagingMaterial(true);
		packagingInOutLine.setMovementQty(TEN);
		save(packagingInOutLine);

		paymentTermA = newInstance(I_C_PaymentTerm.class);
		paymentTermA.setName("paymentTermA");
		save(paymentTermA);
		POJOWrapper.setInstanceName(paymentTermA, "paymentTermA");

		paymentTermB = newInstance(I_C_PaymentTerm.class);
		paymentTermB.setName("paymentTermB");
		save(paymentTermB);
		POJOWrapper.setInstanceName(paymentTermB, "paymentTermB");

		inOutLineHandlerUnderTest = new M_InOutLine_Handler();
	}

	@Test
	public void extractPaymentTermIdViaOrderLine_from_materialInOutLine_with_paymentTerm()
	{
		final I_M_InOutLine materialInOutLine = createMaterialInOutLine(paymentTermA);

		final int paymentTermId = M_InOutLine_Handler.extractPaymentTermIdViaOrderLine(materialInOutLine);

		assertThat(paymentTermId).isEqualTo(paymentTermA.getC_PaymentTerm_ID());
	}

	@Test
	public void extractPaymentTermIdViaOrderLine_from_materialInOutLine_without_paymentTerm()
	{
		final I_M_InOutLine materialInOutLine = createMaterialInOutLine(null);

		final int paymentTermId = M_InOutLine_Handler.extractPaymentTermIdViaOrderLine(materialInOutLine);

		assertThat(paymentTermId).isLessThanOrEqualTo(0);
	}

	@Test
	public void extractPaymentTermId_from_packagingInOutLine_with_one_materialInOutLine()
	{
		createMaterialInOutLine(paymentTermA);

		final int paymentTermId = M_InOutLine_Handler.extractPaymentTermId(packagingInOutLine);

		assertThat(paymentTermId).isEqualTo(paymentTermA.getC_PaymentTerm_ID());
	}

	/**
	 * Create two material inOut lines that reference {@link #packagingInOutLine}.
	 * The two have different payment terms.
	 * Expectation: the paymentTerm of "first" of the two material lines should be taken
	 */
	@Test
	public void extractPaymentTermId_from_packagingInOutLine_with_two_materialInOutLines()
	{
		createMaterialInOutLine(paymentTermB);
		createMaterialInOutLine(paymentTermA);

		final int paymentTermId = M_InOutLine_Handler.extractPaymentTermId(packagingInOutLine);
		assertThat(paymentTermId).isEqualTo(paymentTermB.getC_PaymentTerm_ID());
	}

	@Test
	public void extractPaymentTermId_from_packagingInOutLine_with_two_materialInOutLines_butno_paymentTerm()
	{
		createMaterialInOutLine(null);
		createMaterialInOutLine(null);

		createUnrelatedMaterialInOutLine(paymentTermA, TEN);

		final int paymentTermId = M_InOutLine_Handler.extractPaymentTermId(packagingInOutLine);
		assertThat(paymentTermId).isEqualTo(paymentTermA.getC_PaymentTerm_ID());
	}

	@Test
	public void createCandidatesForInOutLine_one_materialInOutLine_with_PaymentTerm()
	{
		createMaterialInOutLine(paymentTermA, TEN.add(FIVE));

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);

	}

	@Test
	public void createCandidatesForInOutLine_one_materialInOutLine_with_PaymentTerm_and_smallqtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermA, ONE);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);
	}

	@Test
	public void createCandidatesForInOutLine_one_packagingInOutLine()
	{
		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLines_one_without_paymentterm()
	{
		createMaterialInOutLine(null, FIVE);
		createMaterialInOutLine(paymentTermB, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermB));
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLines_both_without_paymentterm()
	{
		createMaterialInOutLine(null, FIVE);
		createMaterialInOutLine(null, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic).has(invoiceCandidateWithTerm(null));
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLines_both_without_paymentterm_smallQtyEnteredTU()
	{
		createMaterialInOutLine(null, ONE);
		createMaterialInOutLine(null, ONE);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic).has(invoiceCandidateWithTerm(null));
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);

		// make sure that on later updates, the qty remains the same
		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN);
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms()
	{
		createMaterialInOutLine(paymentTermA, FIVE);
		createMaterialInOutLine(paymentTermB, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_assertInvariants(result);
	}

	private void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);
		assertThat(result).allSatisfy(ic -> assertThat(ic.isPackagingMaterial()).isTrue());

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).isEqualByComparingTo(FIVE); // value taken from the first material-inoutLine
				});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).isEqualByComparingTo(FIVE); // five and not six, because packagingInOutLine only has movementQty=10
				});
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_1st_materialIOL_has_largeQtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermA, TEN.add(ONE));
		createMaterialInOutLine(paymentTermB, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);

		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_1st_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_1st_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);
	}

	private void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_1st_materialIOL_has_largeQtyEnteredTU_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(1); // only one, because the movementQty of 10 are already exceeded by the QtyEnteredTU=11 of the first material-iol
		assertThat(result).allSatisfy(ic -> assertThat(ic.isPackagingMaterial()).isTrue());

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher
				});
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_both_have_smallQtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermA, ONE);
		createMaterialInOutLine(paymentTermB, FOUR);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_both_have_smallQtyEnteredTU_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_both_have_smallQtyEnteredTU_assertInvariants(result);
	}

	private void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_both_have_smallQtyEnteredTU_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);
		assertThat(result).allSatisfy(ic -> assertThat(ic.isPackagingMaterial()).isTrue());

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic ->
					{
						assertThat(ic.isPackagingMaterial()).isTrue();
						assertThat(ic.getQtyDelivered()).isEqualByComparingTo(ONE);
					});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic ->
					{
						assertThat(ic.isPackagingMaterial()).isTrue();
						assertThat(ic.getQtyDelivered()).isEqualByComparingTo(NINE); // we need to account for packagingInOutLine's full qty of TEN
					});
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_2nd_materialIOL_has_largeQtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermB, SIX);
		createMaterialInOutLine(paymentTermA, TEN.add(ONE));

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_2nd_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_2nd_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);
	}

	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_2nd_materialIOL_has_largeQtyEnteredTU_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermB").isEqualByComparingTo(SIX); // the full qty of the first material inoutline
				});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermA").isEqualByComparingTo(FOUR);
				});
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_customerReturn()
	{
		inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns); // we'll expect the ICs' quantities to be negated
		save(inout);

		createMaterialInOutLine(paymentTermA, FIVE);
		createMaterialInOutLine(paymentTermB, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_customerReturn_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_customerReturn_assertInvariants(result);
	}

	private void createCandidatesForInOutLine_two_materialInOutLine_different_payment_terms_customerReturn_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);
		assertThat(result).allSatisfy(ic -> assertThat(ic.isPackagingMaterial()).isTrue());

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermA").isEqualByComparingTo(FIVE.negate()); // value taken from the first material-inoutLine
				});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermB").isEqualByComparingTo(FIVE.negate()); // five and not six, because packagingInOutLine only has movementQty=10
				});
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_same_payment_term()
	{
		createMaterialInOutLine(paymentTermA, FIVE);
		createMaterialInOutLine(paymentTermA, TEN);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest
				.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);

		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher
	}


	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_same_payment_term_customer_return()
	{
		inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns); // we'll expect the ICs' quantities to be negated
		save(inout);

		createMaterialInOutLine(paymentTermA, FIVE);
		createMaterialInOutLine(paymentTermA, TEN);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest
				.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate()); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);

		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate()); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_same_payment_term_smallQtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermA, ONE);
		createMaterialInOutLine(paymentTermA, ONE);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest
				.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);

		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher
	}

	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_same_payment_term_customer_return_smallQtyEnteredTU()
	{
		inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns); // we'll expect the ICs' quantities to be negated
		save(inout);

		createMaterialInOutLine(paymentTermA, ONE);
		createMaterialInOutLine(paymentTermA, ONE);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest
				.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(paymentTermA));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate());

		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);

		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate());
	}


	@Test
	public void createCandidatesForInOutLine_two_materialInOutLine_both_without_payment_term_customer_return()
	{
		inout.setMovementType(X_M_InOut.MOVEMENTTYPE_CustomerReturns); // we'll expect the ICs' quantities to be negated
		save(inout);

		createMaterialInOutLine(null, FIVE);
		createMaterialInOutLine(null, TEN);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest
				.createCandidatesForInOutLine(packagingInOutLine);

		assertThat(result).hasSize(1);
		final I_C_Invoice_Candidate ic = result.get(0);
		assertThat(ic).has(invoiceCandidateWithTerm(null));
		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate()); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher

		inOutLineHandlerUnderTest.setOrderedData(ic);
		inOutLineHandlerUnderTest.setDeliveredData(ic);

		assertThat(ic.isPackagingMaterial()).isTrue();
		assertThat(ic.getQtyDelivered()).isEqualByComparingTo(TEN.negate()); // packagingInOutLine only has movementQty=10 so the IC's value can't be higher
	}

	@Test
	public void createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_has_largeQtyEnteredTU()
	{
		createMaterialInOutLine(paymentTermB, TWO);
		createMaterialInOutLine(paymentTermB, FOUR);
		createMaterialInOutLine(paymentTermA, TEN.add(ONE));

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);
		createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_has_largeQtyEnteredTU_assertInvariants(result);
	}

	public void createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_has_largeQtyEnteredTU_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermB").isEqualByComparingTo(SIX); // the full qty of the first two material inoutlines
				});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermA").isEqualByComparingTo(FOUR);
				});
	}

	@Test
	public void createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_exceeds_movementQty()
	{
		createMaterialInOutLine(paymentTermB, TWO);
		createMaterialInOutLine(paymentTermA, FOUR);
		createMaterialInOutLine(paymentTermA, SIX);

		final List<I_C_Invoice_Candidate> result = inOutLineHandlerUnderTest.createCandidatesForInOutLine(packagingInOutLine);
		createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_exceeds_movementQty_assertInvariants(result);

		// make sure that on later updates, the qty remains the same
		result.forEach(ic ->
			{
				inOutLineHandlerUnderTest.setOrderedData(ic);
				inOutLineHandlerUnderTest.setDeliveredData(ic);
			});
		createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_exceeds_movementQty_assertInvariants(result);
	}

	public void createCandidatesForInOutLine_three_materialInOutLine_different_payment_terms_3rd_materialIOL_exceeds_movementQty_assertInvariants(final List<I_C_Invoice_Candidate> result)
	{
		assertThat(result).hasSize(2);

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermB))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermB").isEqualByComparingTo(TWO);
				});

		assertThat(result)
				.filteredOn(invoiceCandidateWithTerm(paymentTermA))
				.hasSize(1)
				.allSatisfy(ic -> {
					assertThat(ic.isPackagingMaterial()).isTrue();
					assertThat(ic.getQtyDelivered()).as("ic with paymentTermA").isEqualByComparingTo(EIGHT);
				});
	}

	private I_M_InOutLine createMaterialInOutLine(@Nullable final I_C_PaymentTerm paymentTerm)
	{
		return createMaterialInOutLine(
				paymentTerm,
				TEN// qtyEnteredTU
		);
	}

	private I_M_InOutLine createMaterialInOutLine(
			@Nullable final I_C_PaymentTerm paymentTerm,
			@Nullable final BigDecimal qtyEnteredTU)
	{
		final I_M_InOutLine unrelatedMaterialInOutLine = createUnrelatedMaterialInOutLine(
				paymentTerm,
				qtyEnteredTU);
		unrelatedMaterialInOutLine.setM_PackingMaterial_InOutLine(packagingInOutLine);
		save(unrelatedMaterialInOutLine);

		return unrelatedMaterialInOutLine;
	}

	private I_M_InOutLine createUnrelatedMaterialInOutLine(
			@Nullable final I_C_PaymentTerm paymentTerm,
			@Nullable final BigDecimal qtyEnteredTU)
	{
		final I_M_Product materialProduct = newInstance(I_M_Product.class);
		save(materialProduct);

		final I_C_Order order = newInstance(I_C_Order.class);
		save(order);

		final I_C_OrderLine orderLine = newInstance(I_C_OrderLine.class);
		orderLine.setC_Order(order);
		orderLine.setC_PaymentTerm_Override(paymentTerm);
		save(orderLine);

		final I_M_InOutLine materialInOutLine = newInstance(I_M_InOutLine.class);
		materialInOutLine.setIsActive(true);
		materialInOutLine.setM_InOut(inout);
		materialInOutLine.setC_OrderLine(orderLine);
		materialInOutLine.setM_Product(materialProduct);
		materialInOutLine.setQtyEnteredTU(qtyEnteredTU);
		materialInOutLine.setIsPackagingMaterial(false);
		save(materialInOutLine);

		return materialInOutLine;
	}

	private Condition<I_C_Invoice_Candidate> invoiceCandidateWithTerm(
			@Nullable final I_C_PaymentTerm term)
	{
		final String description = term == null
				? "C_PaymentTerm_ID=0"
				: "C_PaymentTerm_ID=" + term.getC_PaymentTerm_ID() + " (" + term.getName() + ")";

		return new Condition<I_C_Invoice_Candidate>(description)
		{
			@Override
			public boolean matches(I_C_Invoice_Candidate value)
			{
				if (term == null)
				{
					return value.getC_PaymentTerm_ID() <= 0;
				}
				return value.getC_PaymentTerm_ID() == term.getC_PaymentTerm_ID();
			}
		};
	}

}
