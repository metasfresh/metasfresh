/*
 * #%L
 * de.metas.business
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


package de.metas.document.engine.impl;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentTableFields;
import de.metas.document.engine.DocumentWrapper;
import de.metas.document.engine.IDocument;
import de.metas.document.exceptions.DocumentProcessingException;
import org.adempiere.ad.trx.api.DeadlockRetryPolicy;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.io.File;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Deque;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class DocumentProcessingAttemptsTest
{
	private TestDocumentBL documentBL;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		documentBL = new TestDocumentBL(TestDocument::snapshot);
	}

	/**
	 * Calls {@link IDocument#processIt(String)} of the given test document directly
	 * (PlainDocumentBL would simulate the processing of a non-DocumentWrapper document, DocumentBL would need a model).
	 */
	static class TestDocumentBL extends AbstractDocumentBL
	{
		TestDocumentBL(final RetryStateSnapshotter snapshotter)
		{
			super(DeadlockRetryPolicy.builder().maxAttempts(3).backoffMillis(0).build(), snapshotter);
		}

		@Override
		protected boolean processIt0(final IDocument doc, final String action) {return doc.processIt(action);}

		@Override
		protected String retrieveString(final int adTableId, final int recordId, final String columnName) {throw new UnsupportedOperationException();}

		@Override
		protected Object retrieveModelOrNull(final Properties ctx, final int adTableId, final int recordId) {throw new UnsupportedOperationException();}

		@Override
		protected IDocument getLegacyDocumentOrNull(final Object documentObj, final boolean throwEx) {throw new UnsupportedOperationException();}

		@Override
		public boolean isDocumentTable(final String tableName) {throw new UnsupportedOperationException();}

		@Override
		public int getC_DocType_ID(final Properties ctx, final int AD_Table_ID, final int Record_ID) {throw new UnsupportedOperationException();}

		@Override
		public LocalDate getDocumentDate(final Properties ctx, final int adTableID, final int recordId) {throw new UnsupportedOperationException();}
	}

	enum Outcome
	{SUCCESS, FAILURE, DEADLOCK, OTHER_EXCEPTION}

	static class TestLine
	{
		final TestDocument parent;

		TestLine(final TestDocument parent) {this.parent = parent;}
	}

	/**
	 * A document whose "column" is {@link #docStatus} (restored from the snapshot), and whose per-action state is {@link #prepared},
	 * {@link #processMsg} and the cached {@link #lines} (dropped by {@link #resetEngineStateForRetry()}).
	 */
	static class TestDocument implements IDocument
	{
		private final Deque<Outcome> outcomes;
		final List<String> attemptStartStates = new ArrayList<>();
		final List<List<TestLine>> attemptLines = new ArrayList<>();
		int resetCount = 0;
		boolean snapshotFails = false;
		boolean restoreFails = false;

		String docStatus = IDocument.STATUS_Drafted;
		boolean prepared = false;
		@Nullable String processMsg = null;
		@Nullable private List<TestLine> lines = null;

		TestDocument(final Outcome... outcomes) {this.outcomes = new ArrayDeque<>(Arrays.asList(outcomes));}

		@Nullable
		static Runnable snapshot(final Object model)
		{
			final TestDocument document = (TestDocument)model;
			if (document.snapshotFails)
			{
				throw new IllegalStateException("snapshot failed");
			}
			final String docStatus = document.docStatus;
			return () -> {
				if (document.restoreFails)
				{
					throw new IllegalStateException("restore failed");
				}
				document.docStatus = docStatus;
			};
		}

		List<TestLine> getLines()
		{
			if (lines == null)
			{
				lines = Arrays.asList(new TestLine(this), new TestLine(this));
			}
			return lines;
		}

		@Override
		public void resetEngineStateForRetry()
		{
			resetCount++;
			prepared = false;
			processMsg = null;
			lines = null;
		}

		@Override
		public boolean processIt(final String docAction)
		{
			attemptStartStates.add(prepared + "/" + docStatus + "/" + processMsg);
			attemptLines.add(getLines());

			// what a real attempt changes in memory before it fails
			prepared = true;
			docStatus = IDocument.STATUS_InProgress;
			processMsg = "attempt " + attemptStartStates.size();

			switch (outcomes.removeFirst())
			{
				case SUCCESS:
					docStatus = IDocument.STATUS_Completed;
					return true;
				case FAILURE:
					return false;
				case DEADLOCK:
					throw new AdempiereException("deadlock", new SQLException("deadlock detected", "40P01"));
				default:
					throw new AdempiereException("some other error");
			}
		}

		@Nullable @Override public String getProcessMsg() {return processMsg;}

		@Override public String getDocStatus() {return docStatus;}

		@Override public void setDocStatus(final String newStatus) {docStatus = newStatus;}

		@Override public String getDocumentInfo() {return "TestDocument";}

		@Override public String getDocumentNo() {return "1";}

		@Override public boolean unlockIt() {throw new UnsupportedOperationException();}

		@Override public boolean invalidateIt() {throw new UnsupportedOperationException();}

		@Override public String prepareIt() {throw new UnsupportedOperationException();}

		@Override public boolean approveIt() {throw new UnsupportedOperationException();}

		@Override public boolean rejectIt() {throw new UnsupportedOperationException();}

		@Override public String completeIt() {throw new UnsupportedOperationException();}

		@Override public boolean voidIt() {throw new UnsupportedOperationException();}

		@Override public boolean closeIt() {throw new UnsupportedOperationException();}

		@Override public boolean reverseCorrectIt() {throw new UnsupportedOperationException();}

		@Override public boolean reverseAccrualIt() {throw new UnsupportedOperationException();}

		@Override public boolean reActivateIt() {throw new UnsupportedOperationException();}

		@Override public File createPDF() {throw new UnsupportedOperationException();}

		@Override public String getSummary() {return "TestDocument";}

		@Override public int getDoc_User_ID() {return -1;}

		@Override public int getC_Currency_ID() {return -1;}

		@Override public BigDecimal getApprovalAmt() {return BigDecimal.ZERO;}

		@Override public int getAD_Client_ID() {return 1;}

		@Override public int getAD_Org_ID() {return 1;}

		@Override public boolean isActive() {return true;}

		@Override public String getDocAction() {return IDocument.ACTION_Complete;}

		@Override public LocalDate getDocumentDate() {return LocalDate.now();}

		@Override public Properties getCtx() {return Env.getCtx();}

		@Override public int get_ID() {return 1;}

		@Override public int get_Table_ID() {return 1;}

		@Override public boolean save() {return true;}

		@Nullable @Override public String get_TrxName() {return ITrx.TRXNAME_None;}

		@Override public void set_TrxName(final String trxName) {}
	}

	private boolean process(final TestDocument document)
	{
		return documentBL.processIt((Object)document, IDocument.ACTION_Complete);
	}

	@Test
	void firstAttemptSucceeds_noReset()
	{
		final TestDocument document = new TestDocument(Outcome.SUCCESS);

		assertThat(process(document)).isTrue();

		assertThat(document.attemptStartStates).containsExactly("false/DR/null");
		assertThat(document.resetCount).isZero();
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Completed);
	}

	@Test
	void deadlock_retriesOnSameInstance_restoredAndReset()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.SUCCESS);

		assertThat(process(document)).isTrue();

		// the retry starts like the first attempt: DocStatus restored from the snapshot, flag and message reset by the hook
		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/DR/null");
		assertThat(document.resetCount).isEqualTo(1);
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Completed);
		assertThat(document.getProcessMsg()).isEqualTo("attempt 2");
	}

	@Test
	void deadlock_cachedLinesAreRebuiltForTheRetry_andReferenceTheDocument()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.SUCCESS);

		process(document);

		final List<TestLine> firstAttemptLines = document.attemptLines.get(0);
		final List<TestLine> retryLines = document.attemptLines.get(1);
		assertThat(retryLines).isNotSameAs(firstAttemptLines);
		assertThat(retryLines).allSatisfy(line -> assertThat(line.parent).isSameAs(document));
		assertThat(document.getLines()).isSameAs(retryLines);
	}

	@Test
	void retryReturnsFalse_givesRetryProcessMsg()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.FAILURE);

		assertThat(process(document)).isFalse();

		assertThat(document.getProcessMsg()).isEqualTo("attempt 2");
		// the returning attempt was committed, so its in-memory state is kept
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_InProgress);
	}

	@Test
	void allAttemptsDeadlock_rethrows()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.DEADLOCK, Outcome.DEADLOCK);

		assertThatThrownBy(() -> process(document)).hasMessageContaining("deadlock");

		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/DR/null", "false/DR/null");
		assertThat(document.resetCount).isEqualTo(2);
		// the last attempt was rolled back, so the caller is back to its state before the action, except for the process message
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Drafted);
		assertThat(document.getProcessMsg()).isEqualTo("attempt 3");
	}

	@Test
	void otherException_isNotRetried()
	{
		final TestDocument document = new TestDocument(Outcome.OTHER_EXCEPTION, Outcome.SUCCESS);

		assertThatThrownBy(() -> process(document)).hasMessageContaining("some other error");

		assertThat(document.attemptStartStates).hasSize(1);
		assertThat(document.resetCount).isZero();
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Drafted);
	}

	/**
	 * With {@code throwExIfNotSuccess}, an attempt that returns false throws a {@link DocumentProcessingException}, so its transaction is rolled back
	 * and the caller is restored like on any other failure.
	 */
	@Test
	void processEx_retryReturnsFalse_throwsAndRestoresTheCaller()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.FAILURE);

		assertThatThrownBy(() -> documentBL.processEx(document, IDocument.ACTION_Complete, IDocument.STATUS_Completed))
				.isInstanceOf(DocumentProcessingException.class);

		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/DR/null");
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Drafted);
		assertThat(document.getProcessMsg()).isEqualTo("attempt 2");
	}

	@Test
	void failingRestore_retryContinuesWithoutRestore()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.SUCCESS);
		document.restoreFails = true;

		assertThat(process(document)).isTrue();

		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/IP/null");
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Completed);
	}

	@Test
	void failingRestore_doesNotHideTheDeadlock()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.DEADLOCK, Outcome.DEADLOCK);
		document.restoreFails = true;

		assertThatThrownBy(() -> process(document)).hasMessageContaining("deadlock");
	}

	@Test
	void failingSnapshot_degradesToRetryWithoutRestore()
	{
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.SUCCESS);
		document.snapshotFails = true;

		assertThat(process(document)).isTrue();

		// DocStatus not restored, but the action's state was still reset
		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/IP/null");
		assertThat(document.docStatus).isEqualTo(IDocument.STATUS_Completed);
	}

	@Test
	void unsupportedModel_retryWithoutRestore()
	{
		documentBL = new TestDocumentBL(model -> null);
		final TestDocument document = new TestDocument(Outcome.DEADLOCK, Outcome.SUCCESS);

		assertThat(process(document)).isTrue();

		assertThat(document.attemptStartStates).containsExactly("false/DR/null", "false/IP/null");
		assertThat(document.resetCount).isEqualTo(1);
	}

	@Nested
	class PO_SNAPSHOTTER
	{
		@Test
		void pojoModel_notSupported()
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			InterfaceWrapperHelper.saveRecord(order);

			assertThat(RetryStateSnapshotter.PO_SNAPSHOTTER.snapshot(order)).isNull();
		}

		@Test
		void nonModel_notSupported()
		{
			assertThat(RetryStateSnapshotter.PO_SNAPSHOTTER.snapshot(new TestDocument())).isNull();
		}
	}

	/**
	 * The "just prepared" contract of DocumentEngine.processIt0 for completing: prepare once, then complete without preparing again,
	 * and the flag is consumed by the completion, so that completing the same instance again prepares again.
	 */
	@Nested
	class DocumentWrapperJustPrepared
	{
		private int prepareCount = 0;
		private String completeStatus = IDocument.STATUS_Completed;

		private IDocument newDocument()
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			order.setDocStatus(IDocument.STATUS_Drafted);
			order.setDocAction(IDocument.ACTION_Complete);
			InterfaceWrapperHelper.saveRecord(order);

			return DocumentWrapper.wrapModelUsingHandler(order, new DocumentHandler()
			{
				@Override public String getSummary(final DocumentTableFields docFields) {return "summary";}

				@Override public String getDocumentInfo(final DocumentTableFields docFields) {return "info";}

				@Override public int getDoc_User_ID(final DocumentTableFields docFields) {return -1;}

				@Override public LocalDate getDocumentDate(final DocumentTableFields docFields) {return LocalDate.now();}

				@Override
				public String prepareIt(final DocumentTableFields docFields)
				{
					prepareCount++;
					return IDocument.STATUS_InProgress;
				}

				@Override public String completeIt(final DocumentTableFields docFields) {return completeStatus;}
			});
		}

		/**
		 * What DocumentEngine does when completing a drafted document: prepareIt(), then completeIt().
		 */
		private String prepareAndComplete(final IDocument document)
		{
			assertThat(document.prepareIt()).isEqualTo(IDocument.STATUS_InProgress);
			return document.completeIt();
		}

		@Test
		void prepareThenComplete_preparesOnce()
		{
			final IDocument document = newDocument();

			assertThat(prepareAndComplete(document)).isEqualTo(IDocument.STATUS_Completed);

			assertThat(prepareCount).isEqualTo(1);
		}

		@Test
		void completingTheSameInstanceAgain_preparesAgain()
		{
			final IDocument document = newDocument();
			prepareAndComplete(document);

			document.completeIt();

			assertThat(prepareCount).isEqualTo(2);
		}

		@Test
		void completionReturnedInProgress_nextCompletionOfTheSameInstancePreparesAgain()
		{
			completeStatus = IDocument.STATUS_InProgress; // e.g. waiting for a payment or a confirmation
			final IDocument document = newDocument();
			assertThat(prepareAndComplete(document)).isEqualTo(IDocument.STATUS_InProgress);

			completeStatus = IDocument.STATUS_Completed;
			assertThat(document.completeIt()).isEqualTo(IDocument.STATUS_Completed);

			assertThat(prepareCount).isEqualTo(2);
		}

		@Test
		void resetHook_dropsJustPreparedAndProcessMsg()
		{
			final IDocument document = newDocument();
			document.prepareIt();

			document.resetEngineStateForRetry();
			document.completeIt();

			assertThat(prepareCount).isEqualTo(2);
			assertThat(document.getProcessMsg()).isNull();
		}
	}
}
