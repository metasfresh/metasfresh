package de.metas.einvoice;

import de.metas.invoice.InvoiceId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.X_C_BPartner;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class EInvoiceConfigServiceTest
{
	private EInvoiceConfigService service;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		service = new EInvoiceConfigService();
	}

	@Test
	public void eInvoiceFormat_optionalOfCode_knownCode_returnsPresent()
	{
		assertThat(EInvoiceFormat.optionalOfCode(X_C_BPartner.EINVOICETYPE_ZUGFeRD)).isPresent().contains(EInvoiceFormat.ZUGFeRD);
		assertThat(EInvoiceFormat.optionalOfCode(X_C_BPartner.EINVOICETYPE_XRechnung)).isPresent().contains(EInvoiceFormat.XRECHNUNG);
		assertThat(EInvoiceFormat.optionalOfCode(X_C_BPartner.EINVOICETYPE_PEPPOL)).isPresent().contains(EInvoiceFormat.PEPPOL);
	}

	@Test
	public void eInvoiceFormat_optionalOfCode_nullCode_returnsEmpty()
	{
		assertThat(EInvoiceFormat.optionalOfCode(null)).isEmpty();
	}

	@Test
	public void givenEInvoiceRecipient_whenResolveForInvoice_thenReturnConfig()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setIsEInvoiceRecipeint(true);
		bpartner.setEInvoiceType(X_C_BPartner.EINVOICETYPE_ZUGFeRD);
		bpartner.setEInvoice_BuyerReference("04011000-12345-67");
		saveRecord(bpartner);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(invoice);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final Optional<EInvoiceRecipientConfig> result = service.resolveForInvoice(invoiceId);

		assertThat(result).isPresent();
		assertThat(result.get().getFormat()).isEqualTo(EInvoiceFormat.ZUGFeRD);
		assertThat(result.get().getBuyerReference()).isEqualTo("04011000-12345-67");
	}

	@Test
	public void givenNonEInvoiceRecipient_whenResolveForInvoice_thenReturnEmpty()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setIsEInvoiceRecipeint(false);
		saveRecord(bpartner);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(invoice);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final Optional<EInvoiceRecipientConfig> result = service.resolveForInvoice(invoiceId);

		assertThat(result).isEmpty();
	}

	@Test
	public void givenEInvoiceRecipientWithoutBuyerReference_whenResolveForInvoice_thenReturnConfigWithNullBuyerReference()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setIsEInvoiceRecipeint(true);
		bpartner.setEInvoiceType(X_C_BPartner.EINVOICETYPE_ZUGFeRD);
		// EInvoice_BuyerReference not set — ZUGFeRD does not require a Leitweg-ID
		saveRecord(bpartner);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(invoice);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final Optional<EInvoiceRecipientConfig> result = service.resolveForInvoice(invoiceId);

		assertThat(result).isPresent();
		assertThat(result.get().getFormat()).isEqualTo(EInvoiceFormat.ZUGFeRD);
		assertThat(result.get().getBuyerReference()).isNull();
	}

	@Test
	public void givenEInvoiceRecipientWithNoType_whenResolveForInvoice_thenReturnEmpty()
	{
		final I_C_BPartner bpartner = newInstance(I_C_BPartner.class);
		bpartner.setIsEInvoiceRecipeint(true);
		// EInvoiceType not set — remains null
		saveRecord(bpartner);

		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		saveRecord(invoice);

		final InvoiceId invoiceId = InvoiceId.ofRepoId(invoice.getC_Invoice_ID());

		final Optional<EInvoiceRecipientConfig> result = service.resolveForInvoice(invoiceId);

		assertThat(result).isEmpty();
	}
}
