package de.metas.invoice.service.impl;

import de.metas.acct.api.DocumentUnpostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.invoice.service.IInvoiceBL;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import javax.annotation.Nullable;
import java.sql.Timestamp;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

/**
 * {@link IInvoiceBL#unpostIfPosted(I_C_Invoice)} un-posts a posted invoice through {@link IPostingService#unpost(DocumentUnpostRequest)},
 * using the base type of the invoice's effective document type ({@code C_DocType_ID}, else {@code C_DocTypeTarget_ID}) for the period check.
 */
public class InvoiceBLUnpostTest
{
	private final Timestamp dateAcct = Timestamp.valueOf("2024-01-15 00:00:00");

	private IPostingService postingService;
	private IInvoiceBL invoiceBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		postingService = mock(IPostingService.class);
		Services.registerService(IPostingService.class, postingService); // before the invoice BL, which looks it up eagerly
		invoiceBL = Services.get(IInvoiceBL.class);
	}

	private DocTypeId createDocType(final DocBaseType docBaseType)
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType.getCode());
		InterfaceWrapperHelper.save(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	private I_C_Invoice createInvoice(final boolean posted, @Nullable final DocTypeId docTypeId, @Nullable final DocTypeId docTypeTargetId)
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setPosted(posted);
		invoice.setDocStatus(DocStatus.Completed.getCode());
		invoice.setDateAcct(dateAcct);
		invoice.setC_DocType_ID(docTypeId != null ? docTypeId.getRepoId() : 0);
		invoice.setC_DocTypeTarget_ID(docTypeTargetId != null ? docTypeTargetId.getRepoId() : 0);
		InterfaceWrapperHelper.save(invoice);
		return invoice;
	}

	private DocumentUnpostRequest captureUnpostRequest()
	{
		final ArgumentCaptor<DocumentUnpostRequest> captor = ArgumentCaptor.forClass(DocumentUnpostRequest.class);
		verify(postingService).unpost(captor.capture());
		return captor.getValue();
	}

	@Test
	void postedInvoice_isUnposted_withItsDocTypeAndDateAcct()
	{
		final DocTypeId docTypeId = createDocType(DocBaseType.PurchaseInvoice);
		final DocTypeId otherTargetDocTypeId = createDocType(DocBaseType.SalesInvoice);
		final I_C_Invoice invoice = createInvoice(true, docTypeId, otherTargetDocTypeId);

		invoiceBL.unpostIfPosted(invoice);

		final DocumentUnpostRequest request = captureUnpostRequest();
		assertThat(request.getDocument()).isSameAs(invoice);
		assertThat(request.getDocBaseType()).isEqualTo(DocBaseType.PurchaseInvoice);
		assertThat(request.getDateAcct()).isEqualTo(TimeUtil.asInstant(dateAcct));
	}

	@Test
	void postedInvoiceWithoutDocType_isUnposted_withItsTargetDocType()
	{
		final DocTypeId docTypeTargetId = createDocType(DocBaseType.SalesInvoice);
		final I_C_Invoice invoice = createInvoice(true, null, docTypeTargetId);

		invoiceBL.unpostIfPosted(invoice);

		assertThat(captureUnpostRequest().getDocBaseType()).isEqualTo(DocBaseType.SalesInvoice);
	}

	@Test
	void postedInvoiceWithoutAnyDocType_failsNamingTheInvoice()
	{
		final I_C_Invoice invoice = createInvoice(true, null, null);

		assertThatThrownBy(() -> invoiceBL.unpostIfPosted(invoice))
				.hasMessageContaining("C_Invoice_ID=" + invoice.getC_Invoice_ID());

		verify(postingService, never()).unpost(any());
	}

	@Test
	void notPostedInvoice_isNotUnposted()
	{
		final I_C_Invoice invoice = createInvoice(false, createDocType(DocBaseType.PurchaseInvoice), null);

		invoiceBL.unpostIfPosted(invoice);

		verify(postingService, never()).unpost(any());
	}
}
