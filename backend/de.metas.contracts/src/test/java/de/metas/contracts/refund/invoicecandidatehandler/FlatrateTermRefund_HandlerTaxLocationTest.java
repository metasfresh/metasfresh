/*
 * #%L
 * de.metas.contracts
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

package de.metas.contracts.refund.invoicecandidatehandler;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.contracts.invoicecandidate.TaxLocationTestFixture;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_RefundConfig;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_RefundConfig;
import de.metas.contracts.model.X_C_Flatrate_Term;
import de.metas.contracts.refund.RefundConfigRepository;
import de.metas.contracts.refund.RefundContractRepository;
import de.metas.invoice.service.InvoiceScheduleRepository;
import de.metas.invoice.vatid.TaxLocation;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.product.ProductId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_InvoiceSchedule;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_InvoiceSchedule;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Where, by whom and when a refund's invoice candidate is taxed, and with which ship-from country (see {@link TaxLocation}).
 */
public class FlatrateTermRefund_HandlerTaxLocationTest
{
	private TaxLocationTestFixture fixture;
	private BPartnerLocationId billLocationId;
	private I_C_InvoiceSchedule invoiceSchedule;
	private ProductId productId;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new RefundContractRepository(new RefundConfigRepository(new InvoiceScheduleRepository())));

		fixture = new TaxLocationTestFixture();
		billLocationId = fixture.createLocation();

		invoiceSchedule = newInstance(I_C_InvoiceSchedule.class);
		invoiceSchedule.setInvoiceFrequency(X_C_InvoiceSchedule.INVOICEFREQUENCY_Monthly);
		invoiceSchedule.setInvoiceDay(28);
		invoiceSchedule.setInvoiceDistance(1);
		save(invoiceSchedule);

		final I_M_Product product = newInstance(I_M_Product.class);
		save(product);
		productId = ProductId.ofRepoId(product.getM_Product_ID());
	}

	@Test
	void billLocation_dateOrderedElseDateToInvoice()
	{
		final I_C_Invoice_Candidate ic = createIC(productId, billLocationId);
		ic.setDateOrdered(Timestamp.valueOf("2026-03-04 00:00:00"));
		ic.setDateToInvoice(Timestamp.valueOf("2026-03-09 00:00:00"));
		save(ic);

		final TaxLocation withDateOrdered = new FlatrateTermRefund_Handler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(withDateOrdered.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(withDateOrdered.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(withDateOrdered.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-04 00:00:00").toInstant());
		assertThat(withDateOrdered.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());

		ic.setDateOrdered(null);
		save(ic);

		final TaxLocation withDateToInvoice = new FlatrateTermRefund_Handler().getTaxLocation(ic).orElseThrow(AssertionError::new);

		assertThat(withDateToInvoice.getBpartnerLocationId()).isEqualTo(billLocationId);
		assertThat(withDateToInvoice.getOrgId()).isEqualTo(fixture.getOrgId());
		assertThat(withDateToInvoice.getTaxDate()).isEqualTo(Timestamp.valueOf("2026-03-09 00:00:00").toInstant());
		assertThat(withDateToInvoice.getFromCountryId()).isEqualTo(fixture.getOrgCountryId());
	}

	/**
	 * The tax code does not run without a refund product, so no location decided a tax.
	 */
	@Test
	void noRefundProduct_isEmpty()
	{
		final I_C_Invoice_Candidate ic = createIC(null, billLocationId);
		ic.setDateOrdered(Timestamp.valueOf("2026-03-04 00:00:00"));
		save(ic);

		assertThat(new FlatrateTermRefund_Handler().getTaxLocation(ic)).isEmpty();
	}

	/**
	 * The tax code does not run without a bill location.
	 */
	@Test
	void noBillLocation_isEmpty()
	{
		final I_C_Invoice_Candidate ic = createIC(productId, billLocationId);
		ic.setDateOrdered(Timestamp.valueOf("2026-03-04 00:00:00"));
		ic.setBill_Location_ID(0);
		save(ic);

		assertThat(new FlatrateTermRefund_Handler().getTaxLocation(ic)).isEmpty();
	}

	/**
	 * The tax code does not run without a date.
	 */
	@Test
	void noDate_isEmpty()
	{
		final I_C_Invoice_Candidate ic = createIC(productId, billLocationId);

		assertThat(new FlatrateTermRefund_Handler().getTaxLocation(ic)).isEmpty();
	}

	private I_C_Invoice_Candidate createIC(final ProductId refundProductId, final BPartnerLocationId billTo)
	{
		final I_C_Flatrate_Conditions conditions = newInstance(I_C_Flatrate_Conditions.class);
		conditions.setType_Conditions(X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refund);
		conditions.setName("refundConditions");
		save(conditions);

		final I_C_Flatrate_RefundConfig config = newInstance(I_C_Flatrate_RefundConfig.class);
		config.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		config.setC_InvoiceSchedule_ID(invoiceSchedule.getC_InvoiceSchedule_ID());
		config.setRefundInvoiceType(X_C_Flatrate_RefundConfig.REFUNDINVOICETYPE_Invoice);
		config.setRefundBase(X_C_Flatrate_RefundConfig.REFUNDBASE_Percentage);
		config.setRefundPercent(BigDecimal.TEN);
		config.setRefundMode(X_C_Flatrate_RefundConfig.REFUNDMODE_Accumulated);
		config.setMinQty(BigDecimal.ZERO);
		config.setM_Product_ID(ProductId.toRepoId(refundProductId));
		save(config);

		final I_C_Flatrate_Term term = newInstance(I_C_Flatrate_Term.class);
		term.setType_Conditions(X_C_Flatrate_Term.TYPE_CONDITIONS_Refund);
		term.setDocStatus(X_C_Flatrate_Term.DOCSTATUS_Completed);
		term.setC_Flatrate_Conditions_ID(conditions.getC_Flatrate_Conditions_ID());
		term.setM_Product_ID(ProductId.toRepoId(refundProductId));
		term.setBill_BPartner_ID(billTo.getBpartnerId().getRepoId());
		term.setStartDate(TimeUtil.asTimestamp(LocalDate.of(2026, 1, 1)));
		term.setEndDate(TimeUtil.asTimestamp(LocalDate.of(2026, 12, 31)));
		save(term);

		final I_C_Invoice_Candidate ic = newInstance(I_C_Invoice_Candidate.class);
		ic.setAD_Org_ID(fixture.getOrgId().getRepoId());
		ic.setRecord_ID(term.getC_Flatrate_Term_ID());
		ic.setBill_BPartner_ID(billTo.getBpartnerId().getRepoId());
		ic.setBill_Location_ID(billTo.getRepoId());
		save(ic);
		return ic;
	}
}
