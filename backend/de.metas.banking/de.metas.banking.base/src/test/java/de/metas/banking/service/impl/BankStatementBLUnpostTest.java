package de.metas.banking.service.impl;

import de.metas.acct.api.DocumentPostMultiRequest;
import de.metas.acct.api.DocumentPostRequest;
import de.metas.acct.api.DocumentUnpostRequest;
import de.metas.acct.api.IPostingService;
import de.metas.banking.api.BankAccountService;
import de.metas.document.DocBaseType;
import de.metas.document.engine.IDocument;
import de.metas.currency.CurrencyRepository;
import de.metas.money.MoneyService;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BankStatement;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link BankStatementBL#unpost(I_C_BankStatement)} un-posts a posted statement through {@link IPostingService#unpost(DocumentUnpostRequest)}
 * (which re-queues it only when it is completed or closed, see {@code PostingServiceTest}), and queues a not yet posted statement for posting.
 */
public class BankStatementBLUnpostTest
{
	private final Timestamp statementDate = Timestamp.valueOf("2024-01-15 00:00:00");

	private RecordingPostingService postingService;
	private BankStatementBL bankStatementBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		postingService = new RecordingPostingService();
		Services.registerService(IPostingService.class, postingService);
		bankStatementBL = new BankStatementBL(BankAccountService.newInstanceForUnitTesting(), new MoneyService(new CurrencyRepository()));
	}

	private I_C_BankStatement createBankStatement(final String docStatus, final boolean posted)
	{
		final I_C_BankStatement bankStatement = InterfaceWrapperHelper.newInstance(I_C_BankStatement.class);
		bankStatement.setDocStatus(docStatus);
		bankStatement.setPosted(posted);
		bankStatement.setStatementDate(statementDate);
		InterfaceWrapperHelper.save(bankStatement);
		InterfaceWrapperHelper.setValue(bankStatement, I_C_BankStatement.COLUMNNAME_UpdatedBy, 100);
		return bankStatement;
	}

	@Test
	void postedStatement_isUnpostedThroughThePostingService_withItsStatementDate()
	{
		final I_C_BankStatement bankStatement = createBankStatement(IDocument.STATUS_Completed, true);

		bankStatementBL.unpost(bankStatement);

		assertThat(postingService.unposts).hasSize(1);
		final DocumentUnpostRequest request = postingService.unposts.get(0);
		assertThat(request.getDocument()).isSameAs(bankStatement);
		assertThat(request.getDateAcct()).isEqualTo(statementDate);
		assertThat(request.getDocBaseType()).isEqualTo(DocBaseType.BankStatement);
		assertThat(postingService.schedules).isEmpty();
	}

	@Test
	void notPostedStatement_isQueuedForPosting_withoutUnposting()
	{
		final I_C_BankStatement bankStatement = createBankStatement(IDocument.STATUS_Completed, false);

		bankStatementBL.unpost(bankStatement);

		assertThat(postingService.schedules).hasSize(1);
		assertThat(postingService.schedules.get(0).getRecord()).isEqualTo(TableRecordReference.of(bankStatement));
		assertThat(postingService.unposts).isEmpty();
	}

	private static class RecordingPostingService implements IPostingService
	{
		private final List<DocumentUnpostRequest> unposts = new ArrayList<>();
		private final List<DocumentPostRequest> schedules = new ArrayList<>();

		@Override
		public boolean isEnabled() {return true;}

		@Override
		public void schedule(final DocumentPostRequest request) {schedules.add(request);}

		@Override
		public void schedule(final DocumentPostMultiRequest requests) {requests.toSet().forEach(schedules::add);}

		@Override
		public void postAfterCommit(final DocumentPostMultiRequest requests) {throw new UnsupportedOperationException();}

		@Override
		public void postNowAndThrow(final DocumentPostRequest request) {throw new UnsupportedOperationException();}

		@Override
		public void unpost(final DocumentUnpostRequest request) {unposts.add(request);}
	}
}
