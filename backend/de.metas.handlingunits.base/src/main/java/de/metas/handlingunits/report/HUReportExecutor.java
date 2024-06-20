package de.metas.handlingunits.report;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.Language;
import de.metas.notification.INotificationBL;
import de.metas.notification.Recipient;
import de.metas.notification.UserNotificationRequest;
import de.metas.printing.IMassPrintingService;
import de.metas.process.AdProcessId;
import de.metas.process.ProcessExecutionResult;
import de.metas.process.ProcessExecutor;
import de.metas.process.ProcessInfo;
import de.metas.report.DocumentReportService;
import de.metas.report.PrintCopies;
import de.metas.report.server.ReportConstants;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.compiere.util.DB;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * This little class is specialized on executing HU report processes.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class HUReportExecutor
{
	public static HUReportExecutor newInstance(final Properties ctx)
	{
		return new HUReportExecutor(ctx);
	}

	private static final String REPORT_LANG_NONE = "NO-COMMON-LANGUAGE-FOUND";
	private static final String REPORT_AD_PROCESS_ID = "AD_Process_ID";

	private final Properties ctx;
	private int windowNo = Env.WINDOW_None;
	private PrintCopies numberOfCopies = PrintCopies.ONE;
	private AdProcessId adJasperProcessId;
	private Boolean printPreview = null;

	private HUReportExecutor(final Properties ctx)
	{
		this.ctx = ctx;
	}

	/**
	 * Give this service a window number. The default is {@link Env#WINDOW_None}.
	 */
	public HUReportExecutor windowNo(final int windowNo)
	{
		this.windowNo = windowNo;
		return this;
	}

	/**
	 * Specify the number of copies. One means one printout. The default is one.
	 */
	public HUReportExecutor numberOfCopies(final int numberOfCopies)
	{
		return numberOfCopies(PrintCopies.ofInt(numberOfCopies));
	}

	/**
	 * Specify the number of copies. One means one printout. The default is one.
	 */
	public HUReportExecutor numberOfCopies(@NonNull final PrintCopies numberOfCopies)
	{
		Check.assume(numberOfCopies.isGreaterThanZero(), "numberOfCopies > 0");
		this.numberOfCopies = numberOfCopies;
		return this;
	}

	public HUReportExecutor adJasperProcessId(final AdProcessId adJasperProcessId)
	{
		this.adJasperProcessId = adJasperProcessId;
		return this;
	}


	public HUReportExecutor printPreview(final boolean printPreview)
	{
		this.printPreview = printPreview;
		return this;
	}

	/**
	 * Prepares everything and creates a trx-listener to run the report after the current trx is committed (or right now, if there is no currently open trx).
	 *
	 * @param adProcessId the (jasper-)process to be executed
	 * @param husToProcess the HUs to be processed/shown in the report. These HUs' IDs are added to the {@code T_Select} table and can be accessed by the jasper file.
	 */
	public void executeHUReportAfterCommit(final AdProcessId adProcessId, @NonNull final List<HUToReport> husToProcess)
	{
		// check if we actually got any new M_HU_ID
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		final ITrx trx = trxManager.getThreadInheritedTrx(OnTrxMissingPolicy.ReturnTrxNone);
		final HUReportTrxListener huReportTrxListener;
		if (trx != null)
		{
			huReportTrxListener = trx.getProperty("huReportTrxListener", () -> newHUReportTrxListener(adProcessId));
		}
		else
		{
			huReportTrxListener = newHUReportTrxListener(adProcessId);
		}

		final Set<HuId> huIds = extractHUIds(husToProcess);
		if (!huReportTrxListener.addAll(huIds))
		{
			return; // there are no new HU IDs
		}

		// Use BPartner's Language, if all HUs' partners have a common language
		huReportTrxListener.setLanguage(extractReportingLanguageFromHUs(husToProcess));

		if (huReportTrxListener.isListenerWasRegistered())
		{
			return;
		}

		// gh #1121: do this not now, but after the current transaction was committed. Because "right now", the HUs in question are probably not yet ready.
		// The background is that in the handling unit framework we have some "decoupled" DAOs, that collect data in memory and then safe it all at once, right before the commit is made.

		trxManager.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.newEventListener(TrxEventTiming.AFTER_COMMIT)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(huReportTrxListener::afterCommit);

		trxManager.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(huReportTrxListener::afterClose);

		huReportTrxListener.setListenerWasRegistered();
	}

	public HUReportExecutorResult executeNow(final AdProcessId adProcessId, @NonNull final List<HUToReport> husToProcess)
	{
		return executeNow(HUReportRequest.builder()
				.ctx(ctx)
				.adProcessId(adProcessId)
				.windowNo(windowNo)
				.copies(numberOfCopies)
				.adJasperProcessId(adJasperProcessId)
				.printPreview(printPreview)
				.adLanguage(extractReportingLanguageFromHUs(husToProcess))
				.huIdsToProcess(extractHUIds(husToProcess))
				.onErrorThrowException(true)
				.build());
	}

	private HUReportTrxListener newHUReportTrxListener(final AdProcessId adProcessId)
	{
		return new HUReportTrxListener(ctx, adProcessId, windowNo, numberOfCopies, adJasperProcessId);
	}

	private ImmutableSet<HuId> extractHUIds(final Collection<HUToReport> hus)
	{
		return hus.stream()
				.map(HUToReport::getHUId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
	}

	private String extractReportingLanguageFromHUs(final Collection<HUToReport> hus)
	{
		final Set<BPartnerId> huBPartnerIds = hus.stream()
				.map(HUToReport::getBPartnerId)
				.filter(Objects::nonNull)
				.collect(ImmutableSet.toImmutableSet());
		return extractReportingLanguageFromBPartnerIds(huBPartnerIds);
	}

	private String extractReportingLanguageFromBPartnerIds(final Set<BPartnerId> huBPartnerIds)
	{
		if (huBPartnerIds.size() == 1)
		{
			final BPartnerId bpartnerId = huBPartnerIds.iterator().next();
			final Language reportLanguage = Services.get(IBPartnerBL.class).getLanguage(bpartnerId).orElse(null);
			return reportLanguage == null ? REPORT_LANG_NONE : reportLanguage.getAD_Language();
		}
		else
		{
			return REPORT_LANG_NONE;
		}
	}

	private static HUReportExecutorResult executeNow(@NonNull final HUReportRequest request)
	{
		final Properties ctx = request.getCtx();

		final ImmutableSet<HuId> huIdsToProcess = request.getHuIdsToProcess();
		final String adLanguage = request.getAdLanguage();
		final String reportLanguageToUse = Objects.equals(REPORT_LANG_NONE, adLanguage) ? null : adLanguage;

		final ProcessInfo.ProcessInfoBuilder builder = ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(request.getAdProcessId())
				.setWindowNo(request.getWindowNo())
				.setTableName(I_M_HU.Table_Name)
				.setReportLanguage(reportLanguageToUse)
				.addParameter(ReportConstants.REPORT_PARAM_BARCODE_URL, DocumentReportService.getBarcodeServlet(Env.getClientId(ctx), Env.getOrgId(ctx)))
				.addParameter(IMassPrintingService.PARAM_PrintCopies, request.getCopies().toInt());

		if (request.getAdJasperProcessId()!=null)
		{
			builder.addParameter(REPORT_AD_PROCESS_ID, request.getAdJasperProcessId().getRepoId());
		}

		final ProcessExecutor processExecutor =	builder.setPrintPreview(request.getPrintPreview())
				//
				// Execute report in a new transaction
				.buildAndPrepareExecution()
				.onErrorThrowException(request.isOnErrorThrowException())
				.callBefore(processInfo -> DB.createT_Selection(processInfo.getPinstanceId(), HuId.toRepoIds(huIdsToProcess), ITrx.TRXNAME_ThreadInherited))
				.executeSync();

		return HUReportExecutorResult.builder()
				.processInfo(processExecutor.getProcessInfo())
				.processExecutionResult(processExecutor.getResult())
				.build();
	}

	private static final class HUReportTrxListener
	{
		private final Properties ctx;
		private final AdProcessId adProcessId;
		private final int windowNo;
		private final PrintCopies copies;
		private final AdProcessId adJasperProcessId;

		private final Set<HuId> huIdsToProcess = new LinkedHashSet<>(); // using a linked set to preserve the order in which HUs were added

		private String adLanguage;

		/**
		 * It turned out that afterCommit() is called twice and also, on the first time some things were not ready.
		 * Therefore (and because right now there is not time to get to the root of the problem),
		 * we are now doing the job on afterClose(). This flag is set to true on a commit and will tell the afterClose implementation if it shall proceed.
		 *
		 * task https://github.com/metasfresh/metasfresh/issues/1263
		 *
		 */
		private boolean commitWasDone = false;

		private boolean listenerWasRegistered = false;

		private HUReportTrxListener(
				@NonNull final Properties ctx,
				final AdProcessId adProcessId,
				final int windowNo,
				final PrintCopies copies,
		        @Nullable final AdProcessId adJasperProcessId)
		{
			this.ctx = ctx;
			this.adProcessId = adProcessId;
			this.windowNo = windowNo;
			this.copies = copies;
			this.adJasperProcessId = adJasperProcessId;
		}

		public boolean addAll(@NonNull final Collection<HuId> huIds)
		{
			return huIdsToProcess.addAll(huIds);
		}

		public void setLanguage(@NonNull final String language)
		{
			if (adLanguage == null)
			{
				adLanguage = language;
			}
			else if (!Objects.equals(adLanguage, language))
			{
				adLanguage = REPORT_LANG_NONE;
			}
		}

		public boolean isListenerWasRegistered()
		{
			return listenerWasRegistered;
		}

		public void setListenerWasRegistered()
		{
			listenerWasRegistered = true;
		}

		public void afterCommit(final ITrx trx)
		{
			commitWasDone = true;
		}

		public void afterClose(final ITrx trx)
		{
			if (!commitWasDone)
			{
				return;
			}

			if (huIdsToProcess.isEmpty())
			{
				return;
			}

			final HUReportExecutorResult result = executeNow(HUReportRequest.builder()
					.ctx(ctx)
					.adProcessId(adProcessId)
					.windowNo(windowNo)
					.copies(copies)
					.adJasperProcessId(adJasperProcessId)
					.adLanguage(adLanguage)
					.huIdsToProcess(ImmutableSet.copyOf(huIdsToProcess))
					.build());

			final ProcessExecutionResult processExecutionResult = result.getProcessExecutionResult();
			if (processExecutionResult.isError() && !processExecutionResult.isErrorWasReportedToUser())
			{
				final ProcessInfo processInfo = result.getProcessInfo();
				final Recipient recipient = Recipient.userAndRole(processInfo.getUserId(), processInfo.getRoleId());

				final String plainMessage = StringUtils.formatMessage("AD_PInstance_ID={}\n Summary:\n{}", processInfo.getPinstanceId().getRepoId(), processExecutionResult.getSummary());

				final INotificationBL notificationBL = Services.get(INotificationBL.class);
				final UserNotificationRequest userNotificationRequest = UserNotificationRequest.builder()
						.contentPlain(plainMessage)
						.important(true)
						.recipient(recipient)
						.build();

				notificationBL.send(userNotificationRequest);
				processExecutionResult.setErrorWasReportedToUser();
			}
		}
	}

	@lombok.Value
	private static class HUReportRequest
	{
		Properties ctx;
		AdProcessId adProcessId;
		int windowNo;
		PrintCopies copies;
		AdProcessId adJasperProcessId;
		Boolean printPreview;
		String adLanguage;
		boolean onErrorThrowException;
		ImmutableSet<HuId> huIdsToProcess;

		@lombok.Builder
		private HUReportRequest(
				@NonNull final Properties ctx,
				@NonNull final AdProcessId adProcessId,
				final int windowNo,
				@NonNull final PrintCopies copies,
				@Nullable final AdProcessId adJasperProcessId,
				@Nullable final Boolean printPreview,
				@NonNull final String adLanguage,
				final boolean onErrorThrowException,
				final ImmutableSet<HuId> huIdsToProcess)
		{
			Check.assume(copies.toInt() > 0, "copies > 0");
			Check.assumeNotEmpty(adLanguage, "adLanguage is not empty");
			Check.assumeNotEmpty(huIdsToProcess, "huIdsToProcess is not empty");

			this.ctx = ctx;
			this.adProcessId = adProcessId;
			this.windowNo = windowNo > 0 ? windowNo : Env.WINDOW_None;
			this.copies = copies;
			this.adJasperProcessId = adJasperProcessId;
			this.printPreview = printPreview;
			this.adLanguage = adLanguage;
			this.onErrorThrowException = onErrorThrowException;
			this.huIdsToProcess = huIdsToProcess;
		}

	}
}
