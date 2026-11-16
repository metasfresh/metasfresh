package de.metas.acct.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.DocumentUnpostRequest;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.api.IFactAcctDAO;
import de.metas.calendar.IPeriodBL;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.acct.posting.DocumentPostingBusService;
import java.sql.Timestamp;
import org.adempiere.exceptions.AdempiereException;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.posting.DocumentPostingUserNotificationService;
import de.metas.acct.posting.log.DocumentPostingLogService;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.table.ReferencedRecordRepository;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostingServiceTest
{
	private static final ClientId CLIENT_ID = ClientId.ofRepoId(1000000);
	private static final UserId NOTIFY_USER_ID = UserId.ofRepoId(1234);

	private AcctDocRegistry acctDocRegistry;
	private DocumentPostingLogService documentPostingLogService;
	private DocumentPostingUserNotificationService userNotificationService;

	private PostingService postingService;
	private IFactAcctDAO factAcctDAO;
	private IPeriodBL periodBL;
	private DocumentPostingBusService postingBusService;

	@BeforeEach
	void init()
	{
		init(ReferencedRecordRepository::newInstanceForUnitTesting);
	}

	/** The supplier is invoked only after the test helper enabled the unit-test mode and cleared the previously registered beans. */
	private void init(final Supplier<ReferencedRecordRepository> referencedRecordRepositorySupplier)
	{
		AdempiereTestHelper.get().init();

		final IAcctSchemaDAO acctSchemaDAO = mock(IAcctSchemaDAO.class);
		when(acctSchemaDAO.getAllByClient(any())).thenReturn(ImmutableList.of());
		Services.registerService(IAcctSchemaDAO.class, acctSchemaDAO);

		acctDocRegistry = mock(AcctDocRegistry.class);
		documentPostingLogService = mock(DocumentPostingLogService.class);
		userNotificationService = mock(DocumentPostingUserNotificationService.class);

		SpringContextHolder.registerJUnitBean(AcctDocRegistry.class, acctDocRegistry);
		SpringContextHolder.registerJUnitBean(DocumentPostingLogService.class, documentPostingLogService);
		SpringContextHolder.registerJUnitBean(DocumentPostingUserNotificationService.class, userNotificationService);
		SpringContextHolder.registerJUnitBean(ReferencedRecordRepository.class, referencedRecordRepositorySupplier.get());

		factAcctDAO = mock(IFactAcctDAO.class);
		Services.registerService(IFactAcctDAO.class, factAcctDAO);
		periodBL = mock(IPeriodBL.class);
		Services.registerService(IPeriodBL.class, periodBL);
		postingBusService = mock(DocumentPostingBusService.class);
		SpringContextHolder.registerJUnitBean(DocumentPostingBusService.class, postingBusService);

		postingService = new PostingService();
	}

	private void postNow(final TableRecordReference documentRef)
	{
		postingService.postAfterCommit(DocumentPostMultiRequest.of(DocumentPostRequest.builder()
				.record(documentRef)
				.clientId(CLIENT_ID)
				.onErrorNotifyUserId(NOTIFY_USER_ID)
				.build()));
	}

	private TableRecordReference createInvoiceReference()
	{
		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.save(invoice);
		return TableRecordReference.of(invoice);
	}

	@Nested
	@DisplayName("document was deleted after the posting was scheduled")
	class DocumentDeleted
	{
		@Test
		void neitherLogsAPostingErrorNorNotifiesTheUser()
		{
			final TableRecordReference deletedDocumentRef = TableRecordReference.of(I_C_Invoice.Table_Name, 9649545);
			when(acctDocRegistry.get(any(), eq(deletedDocumentRef)))
					.thenThrow(new AdempiereException("No document found for " + deletedDocumentRef));

			postNow(deletedDocumentRef);

			verify(documentPostingLogService, never()).logPostingError(any(), any());
			verify(userNotificationService, never()).notifyPostingError(any(UserId.class), any(), any(Exception.class));
		}
	}

	@Nested
	@DisplayName("document still exists but cannot be posted")
	class DocumentExists
	{
		@Test
		void logsThePostingErrorAndNotifiesTheUser()
		{
			final TableRecordReference existingDocumentRef = createInvoiceReference();
			when(acctDocRegistry.get(any(), eq(existingDocumentRef)))
					.thenThrow(new AdempiereException("account determination failed"));

			postNow(existingDocumentRef);

			verify(documentPostingLogService).logPostingError(any(), any());
			verify(userNotificationService).notifyPostingError(eq(NOTIFY_USER_ID), eq(existingDocumentRef), any(Exception.class));
		}

		@Test
		@DisplayName("the posting error is still reported when we cannot check whether the document exists")
		void logsThePostingErrorEvenIfTheExistenceCheckFails()
		{
			final ReferencedRecordRepository failingRecordRepository = mock(ReferencedRecordRepository.class);
			when(failingRecordRepository.exists(any())).thenThrow(new AdempiereException("database is not available"));
			init(() -> failingRecordRepository);

			final TableRecordReference documentRef = TableRecordReference.of(I_C_Invoice.Table_Name, 9649545);
			when(acctDocRegistry.get(any(), eq(documentRef)))
					.thenThrow(new AdempiereException("account determination failed"));

			postNow(documentRef);

			verify(documentPostingLogService).logPostingError(any(), any());
			verify(userNotificationService).notifyPostingError(eq(NOTIFY_USER_ID), eq(documentRef), any(Exception.class));
		}
	}

	@Nested
	@DisplayName("postNowAndThrow: synchronous posting that surfaces the error to the caller")
	class PostNowAndThrow
	{
		@Test
		void postsUnderTheClientsOwnContext_andRestoresTheCallersContext()
		{
			final TableRecordReference documentRef = createInvoiceReference();
			final List<ClientId> clientsSeenWhilePosting = new ArrayList<>();
			final AdempiereException postingError = new AdempiereException("stop here: Doc.post is final, so the success path cannot be stubbed");
			when(acctDocRegistry.get(any(), eq(documentRef))).thenAnswer(invocation -> {
				clientsSeenWhilePosting.add(Env.getClientId());
				throw postingError;
			});
			final ClientId callersClientId = ClientId.ofRepoId(4711);
			Env.setClientId(Env.getCtx(), callersClientId);

			assertThatThrownBy(() -> postingService.postNowAndThrow(DocumentPostRequest.builder().record(documentRef).clientId(CLIENT_ID).build()))
					.isSameAs(postingError);

			assertThat(clientsSeenWhilePosting).containsExactly(CLIENT_ID);
			assertThat(Env.getClientId()).isEqualTo(callersClientId);
		}

		@Test
		void postingError_isThrown_notLoggedNorNotified()
		{
			final TableRecordReference documentRef = createInvoiceReference();
			final AdempiereException postingError = new AdempiereException("account determination failed");
			when(acctDocRegistry.get(any(), eq(documentRef))).thenThrow(postingError);

			assertThatThrownBy(() -> postingService.postNowAndThrow(DocumentPostRequest.builder()
					.record(documentRef)
					.clientId(CLIENT_ID)
					.onErrorNotifyUserId(NOTIFY_USER_ID)
					.build()))
					.isSameAs(postingError);

			verify(documentPostingLogService, never()).logPostingOK(any());
			verify(documentPostingLogService, never()).logPostingError(any(), any());
			verify(userNotificationService, never()).notifyPostingError(any(UserId.class), any(), any(Exception.class));
		}
	}

	@Nested
	@DisplayName("unpost")
	class Unpost
	{
		private final Timestamp dateAcct = Timestamp.valueOf("2024-01-15 00:00:00");

		private I_C_Invoice createInvoice(final String docStatus, final boolean posted)
		{
			final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
			invoice.setDocStatus(docStatus);
			invoice.setPosted(posted);
			invoice.setDateAcct(dateAcct);
			InterfaceWrapperHelper.save(invoice);
			return invoice;
		}

		private DocumentUnpostRequest requestFor(final I_C_Invoice invoice)
		{
			return DocumentUnpostRequest.builder()
					.document(invoice)
					.dateAcct(dateAcct)
					.docBaseType(DocBaseType.PurchaseInvoice)
					.build();
		}

		@Test
		void completedDocument_factsDeleted_postedReset_andPostingScheduled()
		{
			final I_C_Invoice invoice = createInvoice(IDocument.STATUS_Completed, true);

			postingService.unpost(requestFor(invoice));

			verify(periodBL).testPeriodOpen(any(), eq(dateAcct), eq(DocBaseType.PurchaseInvoice), eq(invoice.getAD_Org_ID()));
			verify(factAcctDAO).deleteForDocumentModel(invoice);
			InterfaceWrapperHelper.refresh(invoice);
			assertThat(invoice.isPosted()).isFalse();
			final org.mockito.ArgumentCaptor<DocumentPostMultiRequest> captor = org.mockito.ArgumentCaptor.forClass(DocumentPostMultiRequest.class);
			verify(postingBusService).sendAfterCommit(captor.capture());
			assertThat(captor.getValue().toSet()).hasSize(1);
			final DocumentPostRequest posting = captor.getValue().toSet().iterator().next();
			assertThat(posting.getRecord()).isEqualTo(TableRecordReference.of(invoice));
			assertThat(posting.getClientId()).isEqualTo(ClientId.ofRepoId(invoice.getAD_Client_ID()));
		}

		@Test
		void draftedDocument_isUnposted_butNotScheduled()
		{
			final I_C_Invoice invoice = createInvoice(IDocument.STATUS_Drafted, false);

			postingService.unpost(requestFor(invoice));

			verify(factAcctDAO).deleteForDocumentModel(invoice);
			verify(postingBusService, never()).sendAfterCommit(any());
		}

		@Test
		void closedPeriod_nothingIsChanged()
		{
			final I_C_Invoice invoice = createInvoice(IDocument.STATUS_Completed, true);
			org.mockito.Mockito.doThrow(new AdempiereException("period closed")).when(periodBL).testPeriodOpen(any(), any(), any(), org.mockito.ArgumentMatchers.anyInt());

			assertThatThrownBy(() -> postingService.unpost(requestFor(invoice))).hasMessageContaining("period closed");

			verify(factAcctDAO, never()).deleteForDocumentModel(any());
			InterfaceWrapperHelper.refresh(invoice);
			assertThat(invoice.isPosted()).isTrue();
			verify(postingBusService, never()).sendAfterCommit(any());
		}
	}
}
