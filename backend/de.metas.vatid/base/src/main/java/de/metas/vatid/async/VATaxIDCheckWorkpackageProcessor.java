package de.metas.vatid.async;

import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.WorkpackageProcessorAdapter;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.logging.LogManager;
import de.metas.tax.api.VATIdentifier;
import de.metas.util.Services;
import de.metas.vatid.VATaxIDCheckRequest;
import de.metas.vatid.VATaxIDCheckService;
import lombok.NonNull;
import org.adempiere.ad.session.AdSessionId;
import org.compiere.SpringContextHolder;
import org.slf4j.Logger;

import javax.annotation.Nullable;

/**
 * Runs one save-triggered VAT-ID check asynchronously, off the thread that did the save.
 *
 * <p><b>Why a work package and not the saving thread.</b> The online check talks to a third party. Its
 * everyday latency is a fraction of a second, but a VIES that is merely slow rather than down would be
 * felt directly by the user saving a Business Partner — and on a bulk import it would occupy one thread
 * per record for as long as the service takes to answer. A work package moves that wait to the async
 * processor, which brings retry, restart-survival and monitor visibility with it; a bare thread would
 * have none of the three and could still exhaust the pool.
 *
 * <p><b>The process path deliberately does NOT come through here.</b> {@code C_BPartner_VATaxID_Check}
 * already runs in its own async context with its own per-target transaction isolation and its own run
 * report, and its caller is waiting for a summary rather than for a save to return. Sending it through a
 * queue would hide its progress and split its reporting for no gain.
 *
 * <p><b>What carries {@code RequestSent} between enqueue and answer is the evidence row, not the record.</b>
 * {@code VATaxIDCheckRepository#writeRequestSent} appends a {@code VATaxID_CheckLog} row at that status in
 * its own committed transaction, before the online call, so the attempt survives a rollback of the check
 * that follows it. The parent {@code C_BPartner} / {@code C_BPartner_Location} is not touched until the
 * verdict lands: it keeps whatever status it had — typically {@code NotChecked} — for as long as the check
 * is queued or in flight, and a queued check is therefore visible in the log, not on the record.
 */
public class VATaxIDCheckWorkpackageProcessor extends WorkpackageProcessorAdapter
{
	private static final Logger logger = LogManager.getLogger(VATaxIDCheckWorkpackageProcessor.class);

	// Field, not a lookup inside processWorkPackage: docs/coding-rules/service-injection.md requires
	// service lookups to be class fields. SpringContextHolder rather than injection is right here — the
	// class is instantiated by the async framework, not by Spring.
	@NonNull private final VATaxIDCheckService checkService = SpringContextHolder.instance.getBean(VATaxIDCheckService.class);

	private static final String PARAM_C_BPartner_ID = "C_BPartner_ID";
	private static final String PARAM_C_BPartner_Location_ID = "C_BPartner_Location_ID";
	private static final String PARAM_VATaxID = "VATaxID";
	private static final String PARAM_AD_Session_ID = "AD_Session_ID";

	/**
	 * Enqueues the check for after the current save commits. Bound to the thread-inherited transaction, so
	 * a save that is rolled back never leaves a queued check behind.
	 */
	public static void enqueueOnTrxCommit(
			@NonNull final BPartnerId bpartnerId,
			@Nullable final BPartnerLocationId bpartnerLocationId,
			@NonNull final VATIdentifier vataxID,
			@NonNull final AdSessionId adSessionId)
	{
		Services.get(IWorkPackageQueueFactory.class)
				.getQueueForEnqueuing(VATaxIDCheckWorkpackageProcessor.class)
				.newWorkPackage()
				.bindToThreadInheritedTrx()
				.parameters()
				.setParameter(PARAM_C_BPartner_ID, bpartnerId.getRepoId())
				.setParameter(PARAM_C_BPartner_Location_ID, BPartnerLocationId.toRepoId(bpartnerLocationId))
				.setParameter(PARAM_VATaxID, vataxID.getAsString())
				.setParameter(PARAM_AD_Session_ID, adSessionId.getRepoId())
				.end()
				.buildAndEnqueue();
	}

	/**
	 * {@code false} because the check must not run inside the queue's own transaction: the evidence row is
	 * written in its own committed transaction precisely so it survives a rollback of the check that
	 * follows it, and the check itself opens what it needs.
	 */
	@Override
	public boolean isRunInTransaction()
	{
		return false;
	}

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workPackage, final String localTrxName_NOTUSED)
	{
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(getParameters().getParameterAsInt(PARAM_C_BPartner_ID, -1));
		final BPartnerLocationId bpartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(
				bpartnerId, getParameters().getParameterAsInt(PARAM_C_BPartner_Location_ID, -1));
		final VATIdentifier vataxID = VATIdentifier.ofNullable(getParameters().getParameterAsString(PARAM_VATaxID));
		final AdSessionId adSessionId = AdSessionId.ofRepoIdOrNull(
				getParameters().getParameterAsInt(PARAM_AD_Session_ID, -1));

		if (vataxID == null)
		{
			// The value was cleared between enqueue and processing. Nothing to check, and not an error.
			return Result.SUCCESS;
		}

		try
		{
			checkService.check(VATaxIDCheckRequest.builder()
					.bpartnerId(bpartnerId)
					.bpartnerLocationId(bpartnerLocationId)
					.vataxID(vataxID)
					.adSessionId(adSessionId)
					.build());
		}
		catch (final Exception ex)
		{
			// Logged rather than rethrown, for the same reason the synchronous trigger swallowed it: a
			// third party's answer must not turn into a failed unit of work that retries forever. The
			// attempt is already durably recorded in VATaxID_CheckLog, and the nightly run re-checks.
			//
			// Deliberately NOT special-cased for VATaxIDCheckRequestRejectedException, unlike
			// VATaxIDMassCheckService, which aborts its whole loop on it. The distinction there buys
			// something this path cannot use: one work package is one record, so there is no remaining
			// selection to spare and nothing to abort. Rethrowing would only convert a misconfiguration
			// into a growing pile of errored, retrying work packages -- one per save -- which is exactly
			// the failure mode this catch exists to prevent, and it would do so while the config is broken,
			// i.e. when saves are most likely to keep coming. The misconfiguration still surfaces: with the
			// full exception (and its error code) here, and unmissably in the next run's abort message.
			logger.warn("VAT-ID check failed for {} - leaving the stored status as it was", vataxID.getAsString(), ex);
		}

		return Result.SUCCESS;
	}
}
