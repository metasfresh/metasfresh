package de.metas.banking.impexp;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.banking.impexp.BankStatementImportInvoiceResolver.ReferencedInvoice;
import de.metas.bpartner.BPartnerId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.InvoiceId;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_I_BankStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

class BankStatementImportInvoiceResolverTest
{
	private static final int ORG = 1000000;
	private static final int PARTNER_A = 2000001;
	private static final int PARTNER_B = 2000002;

	private BankStatementImportInvoiceResolver resolver;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		resolver = new BankStatementImportInvoiceResolver();
	}

	@Builder(builderMethodName = "invoice", builderClassName = "InvoiceBuilder")
	private InvoiceId createInvoice(
			@NonNull final String documentNo,
			@Nullable final Integer orgId,
			@Nullable final Integer partnerId,
			@Nullable final DocStatus docStatus,
			@Nullable final Boolean paid,
			@Nullable final Boolean sales)
	{
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setAD_Org_ID(orgId != null ? orgId : ORG);
		invoice.setDocumentNo(documentNo);
		invoice.setC_BPartner_ID(partnerId != null ? partnerId : PARTNER_A);
		invoice.setDocStatus((docStatus != null ? docStatus : DocStatus.Completed).getCode());
		invoice.setIsPaid(paid != null && paid);
		invoice.setIsSOTrx(sales == null || sales);
		invoice.setIsFinancial(true);
		saveRecord(invoice);
		return InvoiceId.ofRepoId(invoice.getC_Invoice_ID());
	}

	@Builder(builderMethodName = "importLine", builderClassName = "ImportLineBuilder")
	private I_I_BankStatement createImportLine(
			@Nullable final InvoiceId invoiceId,
			@Nullable final String invoiceDocumentNo,
			@Nullable final String memo,
			@Nullable final String lineDescription,
			@Nullable final Integer partnerId,
			@Nullable final Integer orgId)
	{
		final I_I_BankStatement record = newInstance(I_I_BankStatement.class);
		record.setAD_Org_ID(orgId != null ? orgId : ORG);
		record.setC_Invoice_ID(invoiceId != null ? invoiceId.getRepoId() : 0);
		record.setInvoiceDocumentNo(invoiceDocumentNo);
		record.setMemo(memo);
		record.setLineDescription(lineDescription);
		record.setC_BPartner_ID(partnerId != null ? partnerId : 0);
		saveRecord(record);
		return record;
	}

	private InvoiceId resolvedInvoiceId(final I_I_BankStatement record)
	{
		return resolver.resolve(record).map(ReferencedInvoice::getInvoiceId).orElse(null);
	}

	@Test
	void byInvoiceDocumentNo_takesThePartnerOfTheInvoice()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").build();

		final ReferencedInvoice result = resolver.resolve(importLine().invoiceDocumentNo("100").build()).orElse(null);

		assertThat(result).isNotNull();
		assertThat(result.getInvoiceId()).isEqualTo(invoiceId);
		assertThat(result.getBpartnerId()).isEqualTo(BPartnerId.ofRepoId(PARTNER_A));
	}

	@Test
	void byMemo()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().memo(" 100 ").build())).isEqualTo(invoiceId);
	}

	@Test
	void byLineDescription()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().lineDescription("100").build())).isEqualTo(invoiceId);
	}

	@Test
	void sameInvoiceInSeveralFields_isLinked()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").memo("100").build())).isEqualTo(invoiceId);
	}

	@Test
	void differentInvoicesInDifferentFields_areAmbiguous_noPrecedenceAmongTheFields()
	{
		invoice().documentNo("100").build();
		invoice().documentNo("200").build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("100").memo("200").build())).isEmpty();
	}

	@Test
	void unknownNumber_noInvoice()
	{
		invoice().documentNo("100").build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("999").build())).isEmpty();
	}

	@Test
	void noReference_noInvoice()
	{
		invoice().documentNo("100").build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo(" ").build())).isEmpty();
	}

	@Test
	void twoUnpaidInvoicesWithTheSameNumber_noInvoice()
	{
		invoice().documentNo("100").build();
		invoice().documentNo("100").partnerId(PARTNER_B).build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("100").build())).isEmpty();
	}

	@Test
	void aPaidInvoiceWithTheSameNumber_doesNotMakeItAmbiguous()
	{
		invoice().documentNo("100").paid(true).build();
		final InvoiceId invoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").build())).isEqualTo(invoiceId);
	}

	@Test
	void invoiceOfAnotherOrg_noInvoice()
	{
		invoice().documentNo("100").orgId(ORG + 1).build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("100").build())).isEmpty();
	}

	@Test
	void documentNoReferencesInvoiceOfAnotherPartnerThanTheImportedOne_noInvoice()
	{
		invoice().documentNo("100").partnerId(PARTNER_B).build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("100").partnerId(PARTNER_A).build())).isEmpty();
	}

	@Test
	void documentNoReferencesInvoiceOfTheImportedPartner_isLinked()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").partnerId(PARTNER_A).build())).isEqualTo(invoiceId);
	}

	@Test
	void invoiceId_isUsedFirst_evenIfTheDocumentNoReferencesAnotherInvoice()
	{
		final InvoiceId byId = invoice().documentNo("100").build();
		invoice().documentNo("200").build();
		assertThat(resolvedInvoiceId(importLine().invoiceId(byId).invoiceDocumentNo("200").build())).isEqualTo(byId);
	}

	@Test
	void invoiceId_ofADraftReversedOrPaidInvoice_noInvoice_andNoFallbackToTheDocumentNo()
	{
		invoice().documentNo("100").build(); // would match by number
		final InvoiceId draft = invoice().documentNo("D").docStatus(DocStatus.Drafted).build();
		final InvoiceId reversed = invoice().documentNo("R").docStatus(DocStatus.Reversed).build();
		final InvoiceId paid = invoice().documentNo("P").paid(true).build();

		assertThat(resolver.resolve(importLine().invoiceId(draft).invoiceDocumentNo("100").build())).isEmpty();
		assertThat(resolver.resolve(importLine().invoiceId(reversed).invoiceDocumentNo("100").build())).isEmpty();
		assertThat(resolver.resolve(importLine().invoiceId(paid).invoiceDocumentNo("100").build())).isEmpty();
	}

	@Test
	void invoiceId_ofAnotherPartnerThanTheImportedOne_noInvoice()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").partnerId(PARTNER_B).build();
		assertThat(resolver.resolve(importLine().invoiceId(invoiceId).partnerId(PARTNER_A).build())).isEmpty();
	}

	@Test
	void invoiceId_ofAnotherOrg_noInvoice()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").orgId(ORG + 1).build();
		assertThat(resolver.resolve(importLine().invoiceId(invoiceId).build())).isEmpty();
	}

	@Test
	void aClosedInvoiceIsLinked()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").docStatus(DocStatus.Closed).build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").build())).isEqualTo(invoiceId);
	}

	@Test
	void aPurchaseInvoice_noInvoice()
	{
		invoice().documentNo("100").sales(false).build();
		assertThat(resolver.resolve(importLine().invoiceDocumentNo("100").build())).isEmpty();
	}

	@Test
	void invoiceId_ofAPurchaseInvoice_noInvoice()
	{
		final InvoiceId invoiceId = invoice().documentNo("100").sales(false).build();
		assertThat(resolver.resolve(importLine().invoiceId(invoiceId).build())).isEmpty();
	}

	@Test
	void aPurchaseInvoiceWithTheSameNumber_doesNotMakeItAmbiguous()
	{
		invoice().documentNo("100").partnerId(PARTNER_B).sales(false).build();
		final InvoiceId salesInvoiceId = invoice().documentNo("100").build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").build())).isEqualTo(salesInvoiceId);
	}

	@Test
	void anInvoiceOfAnotherPartnerThanTheImportedOneWithTheSameNumber_doesNotMakeItAmbiguous()
	{
		invoice().documentNo("100").partnerId(PARTNER_B).build();
		final InvoiceId ownInvoiceId = invoice().documentNo("100").partnerId(PARTNER_A).build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").partnerId(PARTNER_A).build())).isEqualTo(ownInvoiceId);
	}

	@Test
	void anInvoiceOfAnotherPartnerThanTheImportedOneReferencedInTheMemo_doesNotMakeItAmbiguous()
	{
		final InvoiceId ownInvoiceId = invoice().documentNo("100").partnerId(PARTNER_A).build();
		invoice().documentNo("200").partnerId(PARTNER_B).build();
		assertThat(resolvedInvoiceId(importLine().invoiceDocumentNo("100").memo("200").partnerId(PARTNER_A).build())).isEqualTo(ownInvoiceId);
	}
}
