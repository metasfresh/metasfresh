package de.metas.acct.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.acct.doc.AcctDocRegistry;
import de.metas.acct.posting.DocumentPostingUserNotificationService;
import de.metas.acct.posting.log.DocumentPostingLogService;
import de.metas.user.UserId;
import de.metas.util.Services;
import org.adempiere.ad.table.api.IReferencedRecordDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

	@BeforeEach
	void init()
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
			final IReferencedRecordDAO failingRecordDAO = mock(IReferencedRecordDAO.class);
			when(failingRecordDAO.exists(any())).thenThrow(new AdempiereException("database is not available"));
			Services.registerService(IReferencedRecordDAO.class, failingRecordDAO);
			postingService = new PostingService();

			final TableRecordReference documentRef = TableRecordReference.of(I_C_Invoice.Table_Name, 9649545);
			when(acctDocRegistry.get(any(), eq(documentRef)))
					.thenThrow(new AdempiereException("account determination failed"));

			postNow(documentRef);

			verify(documentPostingLogService).logPostingError(any(), any());
			verify(userNotificationService).notifyPostingError(eq(NOTIFY_USER_ID), eq(documentRef), any(Exception.class));
		}
	}
}
