/*
 * #%L
 * de.metas.cucumber
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

package de.metas.cucumber.stepdefs.costing;

import com.google.common.collect.ImmutableList;
import de.metas.costing.CostElementId;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.StepDefUtil;
import de.metas.cucumber.stepdefs.acctschema.C_AcctSchema_StepDefData;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_AcctSchema;
import org.compiere.util.DB;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Drives {@code de_metas_acct.product_costs_recreate_all_from_date} — the batched, resumable cost-recompute
 * driver that parks every document it wants reposted in a staging table and releases the whole table into
 * {@code de_metas_acct.accounting_docs_to_repost} in document-date order — and asserts what a run left behind.
 * <p>
 * Three things about this step def are deliberate and load-bearing:
 * <ul>
 * <li><b>The driver is CALLed on a dedicated auto-commit connection.</b> It is a PROCEDURE that COMMITs after
 * every batch, and PostgreSQL rejects transaction control inside an explicit transaction block. metasfresh's
 * {@code DB.executeUpdate*} always runs its statement inside a JDBC transaction (it commits afterwards
 * itself), so it cannot be used here.</li>
 * <li><b>Everything a run leaves behind is snapshotted immediately after the CALL returns</b>, in the same
 * step, and every later assertion reads that snapshot. The accounting server's poller drains
 * {@code accounting_docs_to_repost} on its own schedule, so reading the queue in a later step would race the
 * drain.</li>
 * <li><b>Assertions name the documents they expect, never a total row count.</b> The driver deliberately has
 * no product parameter — it recomputes EVERY product of the accounting schema — so any other document in the
 * recompute's date range takes part in the run. Counting rows would make the assertions depend on what the
 * rest of the test database happens to contain.</li>
 * </ul>
 */
@RequiredArgsConstructor
public class ProductCostsRecreateAll_StepDef
{
	private static final Logger logger = LoggerFactory.getLogger(ProductCostsRecreateAll_StepDef.class);

	private static final String TABLE_Queue = "\"de_metas_acct\".accounting_docs_to_repost";
	private static final String TABLE_Staging = "\"de_metas_acct\".accounting_docs_to_repost_staging";
	private static final String TABLE_Progress = "\"de_metas_acct\".product_costs_recreate_progress";

	/** Trigger + trigger function this step def installs to make a run die mid-flight. Test-only. */
	private static final String ABORT_TRIGGER_FUNCTION = "public.cucumber_abort_cost_recompute_staging";
	private static final String ABORT_TRIGGER_NAME = "cucumber_abort_cost_recompute_staging_tg";

	@NonNull private final C_AcctSchema_StepDefData acctSchemaTable;
	@NonNull private final M_CostElement_StepDefData costElementTable;
	@NonNull private final IdentifiersResolver identifiersResolver;

	/** What the most recent driver run left behind. */
	@Nullable private RunOutcome lastRunOutcome;
	/** What the run before that left behind; lets a resume be compared against the run it continues. */
	@Nullable private RunOutcome previousRunOutcome;

	/**
	 * Runs the cost-recompute driver {@code de_metas_acct.product_costs_recreate_all_from_date} and expects it
	 * to succeed, i.e. to work through every batch and release the staging table into the repost queue.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>C_AcctSchema_ID</b> — (required, identifier-ref) accounting schema to recompute<br>
	 *   <b>M_CostElement_ID</b> — (required, identifier-ref or costing-method code/name, e.g. <code>AveragePO</code>) the cost element the run recomputes<br>
	 *   <b>StartDateAcct</b> — (required) first accounting date the recompute covers (inclusive)<br>
	 *   <b>ProductsPerCommit</b> — (required) products per commit-batch<br>
	 *   <b>Resume</b> — (optional, default <code>N</code>) <code>Y</code> to skip the batches an earlier run already finished<br>
	 * @cucumber.depends StepDefData: C_AcctSchema_StepDefData, M_CostElement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the cost recompute driver runs:
	 *   | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume |
	 *   | acctSchema      | AveragePO        | 2027-01-01    | 1                 | Y      |
	 * </pre>
	 */
	@When("^the cost recompute driver runs:$")
	public void run(@NonNull final DataTable dataTable)
	{
		rememberOutcome(invokeDriver(DataTableRows.of(dataTable).singleRow(), null, false /*runMustFail*/));
	}

	/**
	 * Runs the cost-recompute driver as a resume and asserts it is REFUSED because the recorded batches were
	 * not produced by this call's run — here because that run used a different products-per-commit.
	 * <p>
	 * A batch number is not a stable name for a set of products: the driver recomputes it on every call from
	 * the caller's own {@code p_ProductsPerCommit}. So a resume with a different products-per-commit reads a
	 * batch number recorded DONE under the old grouping as "already done" for a DIFFERENT set of products —
	 * which are then never rewound, never staged, and the run reports success. Refusing is the only safe
	 * answer, and the refusal must name BOTH products-per-commit values: that is what tells the operator
	 * which parameter to correct in order to actually continue the crashed run.
	 * <p>
	 * The run's outcome is still snapshotted and remembered, so the steps after this one can assert that the
	 * refused run changed nothing.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as <code>the cost recompute driver runs:</code>, plus<br>
	 *   <b>RecordedProductsPerCommit</b> — (required) products-per-commit of the run that recorded the DONE batches; the refusal must name it<br>
	 * @cucumber.depends StepDefData: C_AcctSchema_StepDefData, M_CostElement_StepDefData
	 * @cucumber.example
	 * <pre>
	 * When the cost recompute driver run is refused because the recorded run used a different products-per-commit:
	 *   | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume | RecordedProductsPerCommit |
	 *   | acctSchema      | AveragePO        | 2027-06-01    | 2                 | Y      | 1                         |
	 * </pre>
	 */
	@When("^the cost recompute driver run is refused because the recorded run used a different products-per-commit:$")
	public void runIsRefused_recordedRunUsedADifferentProductsPerCommit(@NonNull final DataTable dataTable)
	{
		final DataTableRow row = DataTableRows.of(dataTable).singleRow();
		final int requestedProductsPerCommit = row.getAsInt("ProductsPerCommit");
		final int recordedProductsPerCommit = row.getAsInt("RecordedProductsPerCommit");

		rememberOutcome(invokeDriver(row, null, true /*runMustFail*/));

		assertThat(getLastRunOutcome().getFailure())
				.as("resuming with ProductsPerCommit=%s must be refused, because the recorded batches were produced with ProductsPerCommit=%s",
						requestedProductsPerCommit, recordedProductsPerCommit)
				.isNotNull()
				// hasStackTraceContaining, not hasMessageContaining: the driver is CALLed through a plain JDBC
				// statement, so the refusal raised by the procedure is the CAUSE of the exception this step
				// sees, and the top-level message is only "Failed executing: CALL ...".
				.hasStackTraceContaining("Cannot resume")
				.hasStackTraceContaining("ProductsPerCommit=" + recordedProductsPerCommit)
				.hasStackTraceContaining("ProductsPerCommit=" + requestedProductsPerCommit);
	}

	/**
	 * Runs the cost-recompute driver and asserts it dies while staging the given document, leaving the batches
	 * before it committed — the local, deterministic stand-in for the production abort this driver exists for
	 * (a multi-hour run losing its backend connection after several batches had already committed).
	 * <p>
	 * The abort is injected by a test-only {@code BEFORE INSERT} trigger on the staging table that raises for
	 * exactly that one document; the trigger is dropped again before the step returns, whatever happens. The
	 * document is named rather than a batch number, so the injection does not depend on how many other
	 * products the run happens to cover.
	 * <p>
	 * The step also releases every session-level advisory lock on its connection before handing it back to the
	 * pool: a real operator runs the driver in its own {@code psql} session, so a crashed run's advisory lock
	 * dies with that session — a pooled JDBC connection outlives the failure, so the lock has to be released
	 * explicitly or the resume run could not acquire it.
	 * <p>
	 * The failure's message is asserted to be the INJECTED one, not merely "the run failed": the driver
	 * recomputes every product of the accounting schema, so it can also die for a reason that has nothing to
	 * do with this scenario (a foreign document in the recompute's date range whose period has no calendar,
	 * say). Accepting any failure would let such a run pass this step and then fail several steps later on an
	 * assertion that looks unrelated, hiding the real cause.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns same as <code>the cost recompute driver runs:</code>
	 * @cucumber.depends StepDefData: C_AcctSchema_StepDefData, M_CostElement_StepDefData; the document
	 *   identifier must be resolvable by <code>IdentifiersResolver</code>
	 * @cucumber.example
	 * <pre>
	 * When the cost recompute driver run dies while staging document invProduct3:
	 *   | C_AcctSchema_ID | M_CostElement_ID | StartDateAcct | ProductsPerCommit | Resume |
	 *   | acctSchema      | AveragePO        | 2027-01-01    | 1                 | N      |
	 * </pre>
	 */
	@When("^the cost recompute driver run dies while staging document ([^ :]+):$")
	public void runDiesWhileStagingDocument(@NonNull final String documentIdentifier, @NonNull final DataTable dataTable)
	{
		final TableRecordReference dieOnDocument = identifiersResolver.getTableRecordReference(
				StepDefDataIdentifier.ofString(documentIdentifier));
		rememberOutcome(invokeDriver(DataTableRows.of(dataTable).singleRow(), dieOnDocument, true /*runMustFail*/));
	}

	/**
	 * Asserts, per named document, whether the most recent driver run left it parked in the staging table and
	 * whether it released it to the repost queue. Both come from the snapshot taken right after the run, so the
	 * accounting server's poller cannot race this assertion.
	 * <p>
	 * The rows marked <code>IsReleased=Y</code> must appear in the repost queue in the order they are listed
	 * here — that is the order the documents will be reposted in, and therefore the order their costs are
	 * computed in. Each of them must appear exactly ONCE, and no document may be staged twice: a resume that
	 * restarted from the first batch instead of continuing would stage and release an already-done batch's
	 * document a second time. For the same reason the recorded batch numbers must be free of duplicates.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Record_ID</b> — (required, identifier-ref) the document<br>
	 *   <b>IsStaged</b> — (required) whether it must be parked in the staging table<br>
	 *   <b>IsReleased</b> — (required) whether it must be in the repost queue; the <code>Y</code> rows are checked in the listed order<br>
	 * @cucumber.depends the identifier must be resolvable by <code>IdentifiersResolver</code>
	 * @cucumber.example
	 * <pre>
	 * Then the cost recompute run left these documents:
	 *   | Record_ID   | IsStaged | IsReleased |
	 *   | invProduct1 | Y        | N          |
	 *   | invProduct2 | Y        | N          |
	 *   | invProduct3 | N        | N          |
	 * </pre>
	 */
	@Then("^the cost recompute run left these documents:$")
	public void runLeftTheseDocuments(@NonNull final DataTable dataTable)
	{
		final RunOutcome outcome = getLastRunOutcome();
		final ImmutableList.Builder<TableRecordReference> expectedReleasedInOrder = ImmutableList.builder();

		for (final DataTableRow row : DataTableRows.of(dataTable).toList())
		{
			final TableRecordReference documentRef = identifiersResolver.getTableRecordReference(row.getAsIdentifier("Record_ID"));

			if (row.getAsBoolean("IsStaged"))
			{
				assertThat(outcome.getStagedDocuments()).as("staged documents").contains(documentRef);
			}
			else
			{
				assertThat(outcome.getStagedDocuments()).as("staged documents").doesNotContain(documentRef);
			}

			if (row.getAsBoolean("IsReleased"))
			{
				assertThat(outcome.getQueuedDocuments())
						.as("documents released to the repost queue")
						.containsOnlyOnce(documentRef);
				expectedReleasedInOrder.add(documentRef);
			}
			else
			{
				assertThat(outcome.getQueuedDocuments())
						.as("documents released to the repost queue")
						.doesNotContain(documentRef);
			}
		}

		// guarded: AssertJ's containsSubsequence rejects an empty expectation against a non-empty actual, and
		// "this run released none of these documents" is a legitimate expectation - it is what the per-document
		// doesNotContain assertions above already covered.
		final ImmutableList<TableRecordReference> releasedInOrder = expectedReleasedInOrder.build();
		if (!releasedInOrder.isEmpty())
		{
			assertThat(outcome.getQueuedDocuments())
					.as("documents released to the repost queue, ordered by SeqNo")
					.containsSubsequence(releasedInOrder.toArray(new TableRecordReference[0]));
		}

		assertThat(outcome.getStagedDocuments()).as("staged documents").doesNotHaveDuplicates();
		assertThat(outcome.getDoneBatchNos()).as("commit-batches recorded DONE").doesNotHaveDuplicates();
	}

	/**
	 * Asserts the staging table is empty, i.e. the run released everything it had parked. Safe to state
	 * absolutely: the release step empties the whole staging table in the same transaction that fills the queue.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the cost recompute staging table is empty
	 * </pre>
	 */
	@Then("^the cost recompute staging table is empty$")
	public void stagingTableIsEmpty()
	{
		assertThat(getLastRunOutcome().getStagedDocuments()).as("staged documents").isEmpty();
	}

	/**
	 * Asserts the most recent run finished exactly the given number of commit-batches MORE than the run before
	 * it — the direct statement of "it resumed" as opposed to "it started over": a run that restarted from the
	 * first batch would re-do, and re-record, every batch the crashed run had already finished.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And the cost recompute run finished 1 more batch than the run before it
	 * </pre>
	 */
	@Then("^the cost recompute run finished (\\d+) more batch(?:es)? than the run before it$")
	public void runFinishedNMoreBatches(final int expectedAdditionalBatches)
	{
		final int previousDoneBatches = previousRunOutcome != null ? previousRunOutcome.getDoneBatchNos().size() : 0;
		assertThat(getLastRunOutcome().getDoneBatchNos().size() - previousDoneBatches)
				.as("commit-batches finished on top of the %s the previous run had finished", previousDoneBatches)
				.isEqualTo(expectedAdditionalBatches);
	}

	/**
	 * Waits until the accounting server's repost poller has taken every released document off the queue.
	 * A drained queue is the signal that the released documents are on their way to being reposted.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And after not more than 120s, the repost queue is drained
	 * </pre>
	 */
	@Then("^after not more than (\\d+)s, the repost queue is drained$")
	public void repostQueueIsDrained(final int maxWaitSeconds) throws InterruptedException
	{
		StepDefUtil.tryAndWait(
				maxWaitSeconds,
				1000,
				() -> countQueueRows() == 0,
				() -> logger.info("Repost queue still holds {} row(s)", countQueueRows()));
	}

	private void rememberOutcome(@NonNull final RunOutcome outcome)
	{
		previousRunOutcome = lastRunOutcome;
		lastRunOutcome = outcome;
	}

	private RunOutcome getLastRunOutcome()
	{
		if (lastRunOutcome == null)
		{
			throw new AdempiereException("No cost-recompute driver run was performed yet in this scenario");
		}
		return lastRunOutcome;
	}

	/**
	 * @param dieOnDocument when set, an abort is injected while this document is staged
	 * @param runMustFail   {@code true} when the failure IS the expected outcome (an injected abort, or a
	 *                      refusal the calling step asserts on). Only a run that is expected to SUCCEED
	 *                      rethrows its failure here — otherwise the assertion the caller wants to make
	 *                      about the failure could never be reached.
	 */
	private RunOutcome invokeDriver(
			@NonNull final DataTableRow row,
			@Nullable final TableRecordReference dieOnDocument,
			final boolean runMustFail)
	{
		final String callSql = buildCallSql(row);
		logger.info("Invoking cost-recompute driver: {}", callSql);

		Exception failure = null;
		final RunOutcome outcome;

		final Connection connection = DB.createConnection(true /*autoCommit*/, Connection.TRANSACTION_READ_COMMITTED);
		try
		{
			if (dieOnDocument != null)
			{
				installStagingAbortTrigger(connection, dieOnDocument);
			}
			try
			{
				execute(connection, callSql);
			}
			catch (final Exception ex)
			{
				// logged, not only remembered: when the run was expected to succeed the exception is rethrown
				// below, but when the failure IS the expected outcome (an injected abort, or a refusal) its
				// message is the only clue whether the run died for that reason or for an unrelated one.
				logger.warn("Cost-recompute driver run failed", ex);
				failure = ex;
			}
			finally
			{
				if (dieOnDocument != null)
				{
					removeStagingAbortTrigger(connection);
				}
				// see the step-def Javadoc: emulate the session teardown a real operator's psql session performs
				execute(connection, "SELECT pg_advisory_unlock_all()");
			}

			outcome = takeSnapshot(connection, failure);
		}
		finally
		{
			DB.close(connection);
		}

		if (dieOnDocument != null)
		{
			assertThat(failure)
					.as("cost-recompute driver run must die while staging %s", dieOnDocument)
					.isNotNull()
					// hasStackTraceContaining, not hasMessageContaining: the driver is CALLed through a plain
					// JDBC statement, so the PostgreSQL error is the CAUSE of the exception this step sees and
					// the top-level message is only "Failed executing: CALL ...".
					.hasStackTraceContaining(stagingAbortMessage(dieOnDocument));
		}
		else if (!runMustFail && failure != null)
		{
			throw AdempiereException.wrapIfNeeded(failure);
		}

		return outcome;
	}

	private String buildCallSql(@NonNull final DataTableRow row)
	{
		final int acctSchemaId = row.getAsIdentifier(I_C_AcctSchema.COLUMNNAME_C_AcctSchema_ID)
				.lookupNotNullIn(acctSchemaTable)
				.getC_AcctSchema_ID();
		final CostElementId costElementId = costElementTable.getSingleId(row.getAsString("M_CostElement_ID"));
		final LocalDate startDateAcct = row.getAsLocalDate("StartDateAcct");
		final int productsPerCommit = row.getAsInt("ProductsPerCommit");
		final String resume = row.getAsOptionalString("Resume").orElse("N");

		return "CALL \"de_metas_acct\".product_costs_recreate_all_from_date("
				+ "p_C_AcctSchema_ID:=" + acctSchemaId
				+ ", p_M_CostElement_ID:=" + costElementId.getRepoId()
				+ ", p_StartDateAcct:='" + startDateAcct + "'::date"
				+ ", p_ProductsPerCommit:=" + productsPerCommit
				+ ", p_Resume:='" + resume + "')";
	}

	/** The message the injected abort raises with — the proof a failed run failed for the injected reason. */
	private static String stagingAbortMessage(@NonNull final TableRecordReference dieOnDocument)
	{
		return "cucumber: cost-recompute run died while staging "
				+ dieOnDocument.getTableName() + "/" + dieOnDocument.getRecord_ID();
	}

	private void installStagingAbortTrigger(@NonNull final Connection connection, @NonNull final TableRecordReference dieOnDocument)
	{
		execute(connection, "CREATE OR REPLACE FUNCTION " + ABORT_TRIGGER_FUNCTION + "() RETURNS trigger LANGUAGE plpgsql AS $fn$"
				+ " BEGIN"
				+ "   IF NEW.tablename = " + DB.TO_STRING(dieOnDocument.getTableName())
				+ "      AND NEW.record_id = " + dieOnDocument.getRecord_ID() + " THEN"
				+ "     RAISE EXCEPTION 'cucumber: cost-recompute run died while staging %/%', NEW.tablename, NEW.record_id;"
				+ "   END IF;"
				+ "   RETURN NEW;"
				+ " END $fn$");
		execute(connection, "DROP TRIGGER IF EXISTS " + ABORT_TRIGGER_NAME + " ON " + TABLE_Staging);
		execute(connection, "CREATE TRIGGER " + ABORT_TRIGGER_NAME
				+ " BEFORE INSERT ON " + TABLE_Staging
				+ " FOR EACH ROW EXECUTE PROCEDURE " + ABORT_TRIGGER_FUNCTION + "()");
	}

	private void removeStagingAbortTrigger(@NonNull final Connection connection)
	{
		execute(connection, "DROP TRIGGER IF EXISTS " + ABORT_TRIGGER_NAME + " ON " + TABLE_Staging);
		execute(connection, "DROP FUNCTION IF EXISTS " + ABORT_TRIGGER_FUNCTION + "()");
	}

	private RunOutcome takeSnapshot(@NonNull final Connection connection, @Nullable final Exception failure)
	{
		final ImmutableList.Builder<Integer> doneBatchNos = ImmutableList.builder();
		final ArrayList<TableRecordReference> stagedDocuments = new ArrayList<>();
		final ArrayList<TableRecordReference> queuedDocuments = new ArrayList<>();

		try (final Statement statement = connection.createStatement())
		{
			try (final ResultSet rs = statement.executeQuery(
					"SELECT batch_no FROM " + TABLE_Progress + " WHERE status='DONE' ORDER BY batch_no"))
			{
				while (rs.next())
				{
					doneBatchNos.add(rs.getInt("batch_no"));
				}
			}
			try (final ResultSet rs = statement.executeQuery(
					"SELECT tablename, record_id FROM " + TABLE_Staging + " ORDER BY dateacct, tablename_prio, record_id"))
			{
				while (rs.next())
				{
					stagedDocuments.add(TableRecordReference.of(rs.getString("tablename"), rs.getInt("record_id")));
				}
			}
			try (final ResultSet rs = statement.executeQuery(
					"SELECT tablename, record_id FROM " + TABLE_Queue + " ORDER BY seqno"))
			{
				while (rs.next())
				{
					queuedDocuments.add(TableRecordReference.of(rs.getString("tablename"), rs.getInt("record_id")));
				}
			}
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed snapshotting the cost-recompute run outcome", ex);
		}

		return new RunOutcome(
				doneBatchNos.build(),
				ImmutableList.copyOf(stagedDocuments),
				ImmutableList.copyOf(queuedDocuments),
				failure);
	}

	private static int countQueueRows()
	{
		return DB.getSQLValueEx(null, "SELECT COUNT(1) FROM " + TABLE_Queue);
	}

	private static void execute(@NonNull final Connection connection, @NonNull final String sql)
	{
		try (final Statement statement = connection.createStatement())
		{
			statement.execute(sql);
		}
		catch (final SQLException ex)
		{
			throw new AdempiereException("Failed executing: " + sql, ex);
		}
	}

	@Value
	private static class RunOutcome
	{
		@NonNull ImmutableList<Integer> doneBatchNos;
		/** Documents still parked in the staging table, in release order. */
		@NonNull ImmutableList<TableRecordReference> stagedDocuments;
		/** Documents in the repost queue, ordered by SeqNo, i.e. in the order they will be reposted. */
		@NonNull ImmutableList<TableRecordReference> queuedDocuments;
		/** What the run failed with, or {@code null} when it succeeded. */
		@Nullable Exception failure;
	}
}
