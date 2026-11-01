package de.metas.invoicecandidate.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.invoicecandidate.api.IInvoiceCandRecomputeTagger;
import de.metas.invoicecandidate.api.InvoiceCandRecomputeTag;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate_Recompute;
import de.metas.lock.api.ILock;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.swat.base
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

/**
 * A recompute run of one client must neither tag, count nor delete the queued recompute rows of another client,
 * but must still sweep orphan rows (no candidate) and rows of its own inactive candidates.
 * The run is set up like {@code UpdateInvalidInvoiceCandidatesWorkpackageProcessor}: not locked, not yet tagged.
 */
public class InvoiceCandDAO_ClientScopedRecompute_Test
{
	private static final int CLIENT_A = 1000000;
	private static final int CLIENT_B = 1000001;
	private static final int ORPHAN_INVOICE_CANDIDATE_ID = 999999;

	private InvoiceCandDAO invoiceCandDAO;
	private Properties ctxA;

	private I_C_Invoice_Candidate icA1;
	private I_C_Invoice_Candidate icA2;
	private I_C_Invoice_Candidate icAInactive;
	private I_C_Invoice_Candidate icB1;
	private I_C_Invoice_Candidate icB2;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		invoiceCandDAO = new InvoiceCandDAO();

		ctxA = Env.copyCtx(Env.getCtx());
		Env.setContext(ctxA, Env.CTXNAME_AD_Client_ID, CLIENT_A);
		final Properties ctxB = Env.copyCtx(Env.getCtx());
		Env.setContext(ctxB, Env.CTXNAME_AD_Client_ID, CLIENT_B);

		icA1 = createInvoiceCandidate(ctxA, true);
		icB1 = createInvoiceCandidate(ctxB, true);
		icA2 = createInvoiceCandidate(ctxA, true);
		icB2 = createInvoiceCandidate(ctxB, true);
		icAInactive = createInvoiceCandidate(ctxA, false);

		// the recompute table has no client column; all rows are queued from client A's context
		enqueue(icA1.getC_Invoice_Candidate_ID());
		enqueue(icB1.getC_Invoice_Candidate_ID());
		enqueue(icA2.getC_Invoice_Candidate_ID());
		enqueue(icB2.getC_Invoice_Candidate_ID());
		enqueue(icAInactive.getC_Invoice_Candidate_ID());
		enqueue(ORPHAN_INVOICE_CANDIDATE_ID);
	}

	private static I_C_Invoice_Candidate createInvoiceCandidate(final Properties ctx, final boolean active)
	{
		final I_C_Invoice_Candidate ic = InterfaceWrapperHelper.create(ctx, I_C_Invoice_Candidate.class, ITrx.TRXNAME_None);
		ic.setIsActive(active);
		InterfaceWrapperHelper.saveRecord(ic);
		assertThat(ic.getAD_Client_ID()).isEqualTo(Env.getAD_Client_ID(ctx));
		return ic;
	}

	private void enqueue(final int invoiceCandidateId)
	{
		final I_C_Invoice_Candidate_Recompute recompute = InterfaceWrapperHelper.create(ctxA, I_C_Invoice_Candidate_Recompute.class, ITrx.TRXNAME_None);
		recompute.setC_Invoice_Candidate_ID(invoiceCandidateId);
		InterfaceWrapperHelper.saveRecord(recompute);
	}

	private IInvoiceCandRecomputeTagger newRunOfClientA()
	{
		return invoiceCandDAO.tagToRecompute()
				.setContext(ctxA, ITrx.TRXNAME_None)
				.setLockedBy(ILock.NULL)
				.setTaggedWithNoTag();
	}

	private static List<I_C_Invoice_Candidate_Recompute> retrieveAllQueued()
	{
		return Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Invoice_Candidate_Recompute.class)
				.orderBy(I_C_Invoice_Candidate_Recompute.COLUMNNAME_C_Invoice_Candidate_ID)
				.create()
				.list();
	}

	private static List<Integer> queuedCandidateIds(final List<I_C_Invoice_Candidate_Recompute> rows)
	{
		return rows.stream().map(I_C_Invoice_Candidate_Recompute::getC_Invoice_Candidate_ID).collect(ImmutableList.toImmutableList());
	}

	@Test
	public void runOfClientA_tagsAndRecomputesOnlyItsOwnRows_plusOrphanAndOwnInactive()
	{
		final IInvoiceCandRecomputeTagger tagger = newRunOfClientA();
		final InvoiceCandRecomputeTag tag = tagger.tag();

		// (1) tagged: client A's rows, A's inactive candidate's row and the orphan row; never client B's
		final List<Integer> taggedIds = retrieveAllQueued().stream()
				.filter(row -> row.getAD_PInstance_ID() == tag.getPinstanceId().getRepoId())
				.map(I_C_Invoice_Candidate_Recompute::getC_Invoice_Candidate_ID)
				.collect(ImmutableList.toImmutableList());
		assertThat(taggedIds)
				.as("(1)+(4)+(5) a run of client A tags A's rows, the orphan row and the row of A's inactive candidate")
				.containsExactlyInAnyOrder(
						icA1.getC_Invoice_Candidate_ID(),
						icA2.getC_Invoice_Candidate_ID(),
						icAInactive.getC_Invoice_Candidate_ID(),
						ORPHAN_INVOICE_CANDIDATE_ID);

		// (1) recomputed: only client A's active candidates
		final Iterator<I_C_Invoice_Candidate> recomputed = tagger.retrieveInvoiceCandidates();
		final ImmutableList.Builder<Integer> recomputedIds = ImmutableList.builder();
		recomputed.forEachRemaining(ic -> recomputedIds.add(ic.getC_Invoice_Candidate_ID()));
		assertThat(recomputedIds.build())
				.as("(1) a run of client A recomputes only A's active candidates")
				.containsExactlyInAnyOrder(icA1.getC_Invoice_Candidate_ID(), icA2.getC_Invoice_Candidate_ID());
	}

	@Test
	public void runOfClientA_leavesClientBRowsQueued_andSweepsOrphanAndOwnInactive()
	{
		final IInvoiceCandRecomputeTagger tagger = newRunOfClientA();
		tagger.tag();
		tagger.deleteAllTaggedAndInvalidateCache();

		final List<I_C_Invoice_Candidate_Recompute> remaining = retrieveAllQueued();

		// (2) client B's rows are still queued and untagged
		assertThat(queuedCandidateIds(remaining))
				.as("(2)+(4)+(5) after client A's run only client B's rows remain queued; the orphan row and A's inactive candidate's row are swept")
				.containsExactly(icB1.getC_Invoice_Candidate_ID(), icB2.getC_Invoice_Candidate_ID());
		assertThat(remaining)
				.as("(2) client B's remaining rows are untagged")
				.allSatisfy(row -> assertThat(row.getAD_PInstance_ID()).isLessThanOrEqualTo(0));
	}

	@Test
	public void runOfClientA_countToBeTagged_excludesClientBRows()
	{
		// (3) A's 2 active rows + A's inactive candidate's row + the orphan row; not B's 2 rows
		assertThat(newRunOfClientA().countToBeTagged())
				.as("(3)+(4)+(5) the remaining count of client A's run excludes client B's rows")
				.isEqualTo(4);
	}

	@Test
	public void orphanRow_isTaggedAndDeleted()
	{
		final IInvoiceCandRecomputeTagger tagger = newRunOfClientA();
		tagger.tag();
		tagger.deleteAllTaggedAndInvalidateCache();

		assertThat(queuedCandidateIds(retrieveAllQueued()))
				.as("(4) the orphan row (no candidate) is tagged and deleted")
				.doesNotContain(ORPHAN_INVOICE_CANDIDATE_ID);
	}

	@Test
	public void rowOfInactiveOwnCandidate_isSwept()
	{
		final IInvoiceCandRecomputeTagger tagger = newRunOfClientA();
		tagger.tag();
		tagger.deleteAllTaggedAndInvalidateCache();

		assertThat(queuedCandidateIds(retrieveAllQueued()))
				.as("(5) the row of client A's inactive candidate is still swept")
				.doesNotContain(icAInactive.getC_Invoice_Candidate_ID());
	}
}
