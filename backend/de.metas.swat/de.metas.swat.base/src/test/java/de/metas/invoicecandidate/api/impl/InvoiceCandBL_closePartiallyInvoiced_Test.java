package de.metas.invoicecandidate.api.impl;

/*
 * #%L
 * de.metas.swat.base
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

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.document.engine.IDocument;
import de.metas.invoicecandidate.AbstractICTestSupport;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Line_Alloc;
import de.metas.invoicecandidate.model.X_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.util.Properties;  // needed for Env.getCtx()

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for {@link InvoiceCandBL#closePartiallyInvoiced_InvoiceCandidates(de.metas.adempiere.model.I_C_Invoice)}.
 * <p>
 * Verifies the me03#29369 gate: when an invoice is a Partial invoice (IsPartialInvoice='Y'),
 * the method must NOT close any invoice candidates.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class InvoiceCandBL_closePartiallyInvoiced_Test extends AbstractICTestSupport
{
	private static final BigDecimal QTY_ORDERED = new BigDecimal("10");
	private static final BigDecimal QTY_INVOICED_PARTIAL = new BigDecimal("4");

	private InvoiceCandBL invoiceCandBL;

	@BeforeEach
	public void init()
	{
		invoiceCandBL = new InvoiceCandBL();
	}

	/**
	 * Given an invoice with IsPartialInvoice='Y',
	 * the method must return immediately without closing any IC —
	 * even when the sysconfig C_Invoice_Candidate_Close_PartiallyInvoiced='Y'.
	 */
	@Test
	public void closePartiallyInvoiced_InvoiceCandidates_skipsWhenInvoiceIsPartial()
	{
		// given
		enableClosePartiallyInvoicedSysConfig();

		final I_C_Invoice_Candidate ic = buildInvoiceCandidate();

		final PlainContextAware ctx = buildContextAware();
		final I_C_Invoice invoice = createInvoiceWithILA(ctx, ic, QTY_INVOICED_PARTIAL, /*isPartialInvoice=*/true);

		// when
		invoiceCandBL.closePartiallyInvoiced_InvoiceCandidates(invoice);

		// then — IC must NOT be closed
		InterfaceWrapperHelper.refresh(ic);
		assertThat(ic.getProcessed_Override()).as("IC.Processed_Override must stay null/unset for a partial invoice").isNull();
	}

	/**
	 * Given an invoice with IsPartialInvoice='N' (final invoice) and sysconfig enabled,
	 * and a partially-invoiced IC (QtyInvoiced < QtyOrdered),
	 * the method must close the IC.
	 */
	@Test
	public void closePartiallyInvoiced_InvoiceCandidates_closesWhenInvoiceIsFinal()
	{
		// given
		enableClosePartiallyInvoicedSysConfig();

		final I_C_Invoice_Candidate ic = buildInvoiceCandidate();

		final PlainContextAware ctx = buildContextAware();
		final I_C_Invoice invoice = createInvoiceWithILA(ctx, ic, QTY_INVOICED_PARTIAL, /*isPartialInvoice=*/false);

		// Pre-checks: verify setup is correct
		assertThat(invoiceCandBL.isCloseIfPartiallyInvoiced(OrgId.ofRepoId(invoice.getAD_Org_ID())))
				.as("SysConfig C_Invoice_Candidate_Close_PartiallyInvoiced must be enabled")
				.isTrue();
		assertThat(invoice.isPartialInvoice())
				.as("Invoice must NOT be a partial invoice for this test")
				.isFalse();

		// when
		invoiceCandBL.closePartiallyInvoiced_InvoiceCandidates(invoice);

		// then — IC must be closed (Processed_Override='Y')
		// Load a fresh copy from POJO map to avoid any valuesOld/refresh quirks
		final I_C_Invoice_Candidate icFresh = InterfaceWrapperHelper.load(
				ic.getC_Invoice_Candidate_ID(), I_C_Invoice_Candidate.class);
		assertThat(icFresh.getProcessed_Override()).as("IC.Processed_Override must be 'Y' after closing a final invoice with partial qty").isEqualTo("Y");
	}

	// -----------------------------------------------------------------------
	// Helpers
	// -----------------------------------------------------------------------

	private void enableClosePartiallyInvoicedSysConfig()
	{
		final I_AD_SysConfig sysConfig = InterfaceWrapperHelper.newInstance(I_AD_SysConfig.class);
		sysConfig.setName("C_Invoice_Candidate_Close_PartiallyInvoiced");
		sysConfig.setValue("Y");
		// Force AD_Client_ID=0 (SYSTEM scope) so the SysConfig fallback chain
		// (1000000,0) -> (0,0) can find this record regardless of the test client ID.
		// Without this, the POJO wrapper would inherit the test client ID from Env.getAD_Client_ID(ctx),
		// which doesn't match the ClientId.METASFRESH (1000000) used by isCloseIfPartiallyInvoiced.
		InterfaceWrapperHelper.setValue(sysConfig, I_AD_SysConfig.COLUMNNAME_AD_Client_ID, 0);
		InterfaceWrapperHelper.save(sysConfig);
	}

	private I_C_Invoice_Candidate buildInvoiceCandidate()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(
				bPartnerLocation.getC_BPartner_ID(),
				bPartnerLocation.getC_BPartner_Location_ID());

		return createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setSOTrx(true)
				.setQtyOrdered(QTY_ORDERED.intValue())
				.setInvoiceRule(X_C_Invoice_Candidate.INVOICERULE_AfterDelivery) // canCloseBasedOnInvoiceRule requires AfterDelivery (not Immediate)
				.build();
	}

	private PlainContextAware buildContextAware()
	{
		final Properties ctx = Env.getCtx();
		return PlainContextAware.newWithThreadInheritedTrx(ctx);
	}

	/**
	 * Creates an invoice + invoice line + ILA linking invoice line to the IC.
	 * The invoice line carries {@code qtyInvoiced} (< QtyOrdered on the IC) so that
	 * the "partially invoiced" loop condition is satisfied.
	 */
	private I_C_Invoice createInvoiceWithILA(
			final PlainContextAware ctx,
			final I_C_Invoice_Candidate ic,
			final BigDecimal qtyInvoiced,
			final boolean isPartialInvoice)
	{
		final I_C_DocType dt = InterfaceWrapperHelper.newInstance(I_C_DocType.class, ctx);
		dt.setDocBaseType(X_C_DocType.DOCBASETYPE_ARInvoice);
		InterfaceWrapperHelper.save(dt);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class, ctx);
		invoice.setC_DocType_ID(dt.getC_DocType_ID());
		invoice.setDocStatus(IDocument.STATUS_Completed);
		invoice.setIsPartialInvoice(isPartialInvoice);
		InterfaceWrapperHelper.save(invoice);

		final I_C_InvoiceLine invoiceLine = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class, ctx);
		invoiceLine.setC_Invoice(invoice);
		invoiceLine.setM_Product_ID(productId.getRepoId());
		invoiceLine.setQtyInvoiced(qtyInvoiced);
		invoiceLine.setQtyEntered(qtyInvoiced.multiply(TEN));
		invoiceLine.setC_UOM_ID(uomId.getRepoId());
		invoiceLine.setLine(10);
		InterfaceWrapperHelper.save(invoiceLine);

		final I_C_Invoice_Line_Alloc ila = InterfaceWrapperHelper.newInstance(I_C_Invoice_Line_Alloc.class, ctx);
		ila.setC_InvoiceLine(invoiceLine);
		ila.setC_Invoice_Candidate(ic);
		ila.setQtyInvoiced(qtyInvoiced);
		ila.setQtyInvoicedInUOM(qtyInvoiced.multiply(TEN));
		ila.setC_UOM_ID(uomId.getRepoId());
		InterfaceWrapperHelper.save(ila);

		return invoice;
	}
}
