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
import org.adempiere.ad.table.ReferencedRecordRepository;
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

import java.util.function.Supplier;

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

		// By default, treat the document's table as an accounting table (e.g. C_Invoice);
		// individual tests override this for non-accounting tables.
		when(acctDocRegistry.isAccountingTable(any())).thenReturn(true);

		SpringContextHolder.registerJUnitBean(AcctDocRegistry.class, acctDocRegistry);
		SpringContextHolder.registerJUnitBean(DocumentPostingLogService.class, documentPostingLogService);
		SpringContextHolder.registerJUnitBean(DocumentPostingUserNotificationService.class, userNotificationService);
		SpringContextHolder.registerJUnitBean(ReferencedRecordRepository.class, referencedRecordRepositorySupplier.get());

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
	@DisplayName("document's table is not an accounting table")
	class NonAccountingDocument
	{
		/**
		 * A document whose table has no accounting {@code Doc} provider (e.g. PP_Product_BOM, M_Forecast) can never be
		 * posted. Previously every completeIt() still scheduled a posting, and AcctDocRegistry.get(..) threw
		 * "No accountable document found", which PostingService swallowed into AD_Issue (~120 such entries per cucumber run).
		 * The posting must be skipped before reaching the registry, without logging an error or notifying anyone.
		 */
		@Test
		@DisplayName("skips posting entirely: AcctDocRegistry.get is never called and no posting error is logged")
		void skipsPostingWithoutError()
		{
			final String nonAccountingTableName = "PP_Product_BOM";
			when(acctDocRegistry.isAccountingTable(nonAccountingTableName)).thenReturn(false);
			final TableRecordReference nonAccountingRef = TableRecordReference.of(nonAccountingTableName, 9649545);

			postNow(nonAccountingRef);

			verify(acctDocRegistry, never()).get(any(), any());
			verify(documentPostingLogService, never()).logPostingError(any(), any());
			verify(userNotificationService, never()).notifyPostingError(any(UserId.class), any(), any(Exception.class));
		}
	}
}
