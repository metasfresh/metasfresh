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

import de.metas.document.engine.IDocument;
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
		documentBL = new TestDocumentBL();
		documentBL.setDeadlockRetryPolicy(DeadlockRetryPolicy.builder().maxAttempts(3).backoffMillis(0).build());
		documentBL.setRetryModelSnapshotter(TestDocument::snapshot);
	}

	/**
	 * Calls {@link IDocument#processIt(String)} of the given test document directly
	 * (PlainDocumentBL would simulate the processing of a non-DocumentWrapper document, DocumentBL would need a model).
	 */
	static class TestDocumentBL extends AbstractDocumentBL
	{
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

	/**
	 * Shared by all instances of one document: the outcomes of the coming attempts, and the instances the attempts ran on.
	 */
	static class Script
	{
		final Deque<Outcome> outcomes;
		final List<TestDocument> attemptInstances = new ArrayList<>();
		/** the instance state each attempt started with: "prepared/docStatus" */
		final List<String> attemptStartStates = new ArrayList<>();

		Script(final Outcome... outcomes) {this.outcomes = new ArrayDeque<>(Arrays.asList(outcomes));}
	}

	static class TestDocumentBase
	{
		/** stands for a subclass flag like MOrder's m_justPrepared */
		boolean prepared = false;
	}

	static class TestDocument extends TestDocumentBase implements IDocument
	{
		private final Script script;
		String docStatus = IDocument.STATUS_InProgress;
		@Nullable String processMsg = null;

		TestDocument(final Script script) {this.script = script;}

		/** a new instance in the caller's state, except for the "prepared" flag, which a fresh instance does not have */
		@Nullable
		static java.util.function.Supplier<Object> snapshot(final Object model)
		{
			if (!(model instanceof TestDocument))
			{
				return null;
			}
			final TestDocument caller = (TestDocument)model;
			final String docStatus = caller.docStatus;
			return () -> {
				final TestDocument copy = new TestDocument(caller.script);
				copy.docStatus = docStatus;
				return copy;
			};
		}

		@Override
		public boolean processIt(final String docAction)
		{
			script.attemptInstances.add(this);
			script.attemptStartStates.add(prepared + "/" + docStatus);

			// what a real attempt changes in memory before it fails: the "just prepared" flag and the DocStatus
			prepared = true;
			docStatus = IDocument.STATUS_InProgress;
			processMsg = "attempt " + script.attemptInstances.size();

			final Outcome outcome = script.outcomes.removeFirst();
			switch (outcome)
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

	@Test
	void firstAttemptSucceeds_runsOnCallerInstance()
	{
		final Script script = new Script(Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);

		assertThat(documentBL.processIt((Object)caller, IDocument.ACTION_Complete)).isTrue();

		assertThat(script.attemptInstances).containsExactly(caller);
		assertThat(caller.docStatus).isEqualTo(IDocument.STATUS_Completed);
	}

	@Test
	void deadlock_retriesOnFreshInstance_andCallerTakesOverItsState()
	{
		final Script script = new Script(Outcome.DEADLOCK, Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);

		assertThat(documentBL.processIt((Object)caller, IDocument.ACTION_Complete)).isTrue();

		assertThat(script.attemptInstances).hasSize(2);
		final TestDocument retryInstance = script.attemptInstances.get(1);
		assertThat(retryInstance).isNotSameAs(caller);
		assertThat(caller.docStatus).isEqualTo(IDocument.STATUS_Completed);
		assertThat(caller.getProcessMsg()).isEqualTo("attempt 2");
		assertThat(caller.prepared).isTrue(); // superclass field taken over as well
	}

	@Test
	void retryStartsFromCallerStateBeforeTheFirstAttempt()
	{
		final Script script = new Script(Outcome.DEADLOCK, Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);
		caller.docStatus = IDocument.STATUS_Drafted;

		documentBL.processIt((Object)caller, IDocument.ACTION_Complete);

		// the retry does not see the "prepared" flag and the DocStatus that the rolled back first attempt left in memory
		assertThat(script.attemptStartStates).containsExactly("false/DR", "false/DR");
	}

	@Test
	void retryReturnsFalse_callerGetsRetryProcessMsg()
	{
		final Script script = new Script(Outcome.DEADLOCK, Outcome.FAILURE);
		final TestDocument caller = new TestDocument(script);

		assertThat(documentBL.processIt((Object)caller, IDocument.ACTION_Complete)).isFalse();

		assertThat(caller.getProcessMsg()).isEqualTo("attempt 2");
		assertThat(caller.docStatus).isEqualTo(IDocument.STATUS_InProgress);
	}

	@Test
	void allAttemptsDeadlock_throws_andCallerIsNotChangedByTheRetries()
	{
		final Script script = new Script(Outcome.DEADLOCK, Outcome.DEADLOCK, Outcome.DEADLOCK);
		final TestDocument caller = new TestDocument(script);

		assertThatThrownBy(() -> documentBL.processIt((Object)caller, IDocument.ACTION_Complete))
				.hasMessageContaining("deadlock");

		assertThat(script.attemptInstances).hasSize(3);
		assertThat(script.attemptInstances.get(1)).isNotSameAs(caller);
		assertThat(script.attemptInstances.get(2)).isNotSameAs(caller);
		assertThat(caller.getProcessMsg()).isEqualTo("attempt 1");
	}

	@Test
	void otherException_isNotRetried()
	{
		final Script script = new Script(Outcome.OTHER_EXCEPTION, Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);

		assertThatThrownBy(() -> documentBL.processIt((Object)caller, IDocument.ACTION_Complete))
				.hasMessageContaining("some other error");

		assertThat(script.attemptInstances).containsExactly(caller);
	}

	@Test
	void unsupportedModel_isRetriedOnCallerInstance()
	{
		documentBL.setRetryModelSnapshotter(model -> null);
		final Script script = new Script(Outcome.DEADLOCK, Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);

		assertThat(documentBL.processIt((Object)caller, IDocument.ACTION_Complete)).isTrue();

		assertThat(script.attemptInstances).containsExactly(caller, caller);
	}

	@Test
	void transferFailure_doesNotFailTheCommittedAction()
	{
		final Script script = new Script(Outcome.DEADLOCK, Outcome.SUCCESS);
		final TestDocument caller = new TestDocument(script);
		// a retry instance of another class cannot be copied onto the caller
		documentBL.setRetryModelSnapshotter(model -> () -> new TestDocument(script) {});

		assertThat(documentBL.processIt((Object)caller, IDocument.ACTION_Complete)).isTrue();

		assertThat(script.attemptInstances).hasSize(2);
		assertThat(caller.getProcessMsg()).isEqualTo("attempt 1"); // stale, as documented
	}

	@Nested
	class PO_SNAPSHOTTER
	{
		@Test
		void pojoModel_notSupported()
		{
			final I_C_Order order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
			InterfaceWrapperHelper.saveRecord(order);

			assertThat(RetryModelSnapshotter.PO_SNAPSHOTTER.snapshot(order)).isNull();
		}

		@Test
		void nonModel_notSupported()
		{
			assertThat(RetryModelSnapshotter.PO_SNAPSHOTTER.snapshot(new TestDocument(new Script()))).isNull();
		}
	}

	@Nested
	class InstanceStateCopierTest
	{
		class Base
		{
			int baseValue;
			@Nullable String nullableValue = "set";
		}

		class Sub extends Base
		{
			final String identity;
			int subValue;
			transient String instanceBoundCache;

			Sub(final String identity) {this.identity = identity;}
		}

		@Test
		void copiesNonFinalFieldsOfAllClasses_includingNulls_butNotFinalOrTransientOnes()
		{
			final Sub from = new Sub("from");
			from.baseValue = 1;
			from.subValue = 2;
			from.nullableValue = null;
			from.instanceBoundCache = "from's cache";
			final Sub to = new Sub("to");
			to.instanceBoundCache = "to's cache";

			InstanceStateCopier.copyState(from, to);

			assertThat(to.baseValue).isEqualTo(1);
			assertThat(to.subValue).isEqualTo(2);
			assertThat(to.nullableValue).isNull();
			assertThat(to.identity).isEqualTo("to");
			assertThat(to.instanceBoundCache).isEqualTo("to's cache");
		}

		@Test
		void differentClasses_fail()
		{
			assertThatThrownBy(() -> InstanceStateCopier.copyState(new Sub("a"), new Base()))
					.isInstanceOf(AdempiereException.class);
		}
	}
}
