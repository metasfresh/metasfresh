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
import de.metas.document.engine.impl.DocumentProcessingAttemptsTest.Outcome;
import de.metas.document.engine.impl.DocumentProcessingAttemptsTest.TestDocument;
import de.metas.document.engine.impl.DocumentProcessingAttemptsTest.TestDocumentBL;
import de.metas.util.Services;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * {@link AbstractDocumentBL#processEx(Object, String, String)} called without a transaction: the engine runs the document action in its own transaction.
 * The document header has to be saved in that transaction, so that whatever runs when it commits (e.g. async work that the action enqueued)
 * sees the header with its new DocStatus.
 */
class AbstractDocumentBL_processEx_Test
{
	private TestDocumentBL documentBL;
	private I_C_Order order;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();
		documentBL = new TestDocumentBL(model -> null);

		order = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		order.setDocStatus(IDocument.STATUS_Drafted);
		InterfaceWrapperHelper.saveRecord(order);
	}

	/**
	 * Works on {@link #order} like a real document: the action changes the order in memory only, and {@code processEx} saves it.
	 * After a successful attempt, it records the order's persisted DocStatus at the moment the engine's transaction is committed.
	 */
	private class OrderDocument extends TestDocument
	{
		final List<String> persistedDocStatusOnCommit = new ArrayList<>();

		OrderDocument(final Outcome... outcomes) {super(outcomes);}

		@Override
		public Object getDocumentModel() {return order;}

		@Override
		public boolean processIt(final String docAction)
		{
			final boolean processed = super.processIt(docAction);
			order.setDocStatus(getDocStatus());

			Services.get(ITrxManager.class)
					.getCurrentTrxListenerManagerOrAutoCommit()
					.runAfterCommit(() -> persistedDocStatusOnCommit.add(retrievePersistedDocStatus()));
			return processed;
		}

		private String retrievePersistedDocStatus()
		{
			return InterfaceWrapperHelper.loadOutOfTrx(order.getC_Order_ID(), I_C_Order.class).getDocStatus();
		}
	}

	@Test
	void noTrx_headerIsSavedBeforeTheEngineTrxCommits()
	{
		final OrderDocument document = new OrderDocument(Outcome.SUCCESS);

		documentBL.processEx(document, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		assertThat(document.persistedDocStatusOnCommit).containsExactly(IDocument.STATUS_Completed);
	}

	@Test
	void noTrx_deadlockRetried_headerIsSavedWithTheSuccessfulAttempt()
	{
		final OrderDocument document = new OrderDocument(Outcome.DEADLOCK, Outcome.SUCCESS);

		documentBL.processEx(document, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		// the deadlocked attempt was rolled back, so only the successful attempt's commit is observed
		assertThat(document.persistedDocStatusOnCommit).containsExactly(IDocument.STATUS_Completed);
		assertThat(document.attemptStartStates).hasSize(2);
	}

	@Test
	void callerTrx_headerIsSavedInTheCallersTrx()
	{
		final OrderDocument document = new OrderDocument(Outcome.SUCCESS);

		Services.get(ITrxManager.class).runInThreadInheritedTrx(
				() -> documentBL.processEx(document, IDocument.ACTION_Complete, IDocument.STATUS_Completed));

		assertThat(document.persistedDocStatusOnCommit).containsExactly(IDocument.STATUS_Completed);
	}
}
