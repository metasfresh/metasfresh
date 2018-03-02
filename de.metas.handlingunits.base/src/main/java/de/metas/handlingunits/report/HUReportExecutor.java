package de.metas.handlingunits.report;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxListenerManager.TrxEventTiming;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.api.OnTrxMissingPolicy;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.report.IJasperService;
import org.compiere.util.DB;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableSet;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.Language;
import de.metas.process.ProcessInfo;
import lombok.NonNull;

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
 * This little class is specialized on executing jasper report processes
 * that are assigned to the {@link I_M_HU} table by {@link I_AD_Table_Process} records.
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

	/** AD_SysConfig for "BarcodeServlet" */
	private static final String SYSCONFIG_BarcodeServlet = "de.metas.adempiere.report.barcode.BarcodeServlet";
	private static final String PARA_BarcodeURL = "barcodeURL";

	private static final String REPORT_LANG_NONE = "NO-COMMON-LANGUAGE-FOUND";

	private final Properties ctx;
	private int windowNo = Env.WINDOW_None;
	private int numberOfCopies = 1;

	private HUReportExecutor(final Properties ctx)
	{
		this.ctx = ctx;
	};

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
		Check.assume(numberOfCopies > 0, "numberOfCopies > 0");
		this.numberOfCopies = numberOfCopies;
		return this;
	}

	/**
	 * Prepares everything and creates a trx-listener to run the report after the current trx is committed (or right now, if there is no currently open trx).
	 *
	 * @param adProcessId the (jasper-)process to be executed
	 * @param husToProcess the HUs to be processed/shown in the report. These HUs' IDs are added to the {@code T_Select} table and can be accessed by the jasper file.
	 */
	public void executeHUReportAfterCommit(final int adProcessId, @NonNull final List<I_M_HU> husToProcess)
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

		final Set<Integer> huIds = extractHUIds(husToProcess);
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
				.registerHandlingMethod(innerTrx -> huReportTrxListener.afterCommit(innerTrx));

		trxManager.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.newEventListener(TrxEventTiming.AFTER_CLOSE)
				.invokeMethodJustOnce(false) // invoke the handling method on *every* commit, because that's how it was and I can't check now if it's really needed
				.registerHandlingMethod(innerTrx -> huReportTrxListener.afterClose(innerTrx));

		huReportTrxListener.setListenerWasRegistered();
	}

	public void executeNow(final int adProcessId, @NonNull final List<I_M_HU> husToProcess)
	{
		executeNow(HUReportRequest.builder()
				.ctx(ctx)
				.adProcessId(adProcessId)
				.windowNo(windowNo)
				.copies(numberOfCopies)
				.adLanguage(extractReportingLanguageFromHUs(husToProcess))
				.huIdsToProcess(extractHUIds(husToProcess))
				.onErrorThrowException(true)
				.build());
	}

	private HUReportTrxListener newHUReportTrxListener(final int adProcessId)
	{
		return new HUReportTrxListener(ctx, adProcessId, windowNo, numberOfCopies);
	}

	private ImmutableSet<Integer> extractHUIds(final Collection<I_M_HU> hus)
	{
		return hus.stream().map(I_M_HU::getM_HU_ID).filter(huId -> huId > 0).collect(ImmutableSet.toImmutableSet());
	}

	private String extractReportingLanguageFromHUs(final Collection<I_M_HU> hus)
	{
		final Set<Integer> huBPartnerIds = hus.stream()
				.map(I_M_HU::getC_BPartner_ID)
				.filter(bpartnerId -> bpartnerId > 0)
				.collect(ImmutableSet.toImmutableSet());
		return extractReportingLanguageFromBPartnerIds(huBPartnerIds);
	}

	private String extractReportingLanguageFromBPartnerIds(final Set<Integer> huBPartnerIds)
	{
		if (huBPartnerIds.size() == 1)
		{
			final int bpartnerId = huBPartnerIds.iterator().next();
			final Language reportLanguage = Services.get(IBPartnerBL.class).getLanguage(ctx, bpartnerId);
			return reportLanguage == null ? REPORT_LANG_NONE : reportLanguage.getAD_Language();
		}
		else
		{
			return REPORT_LANG_NONE;
		}
	}

	private static void executeNow(final HUReportRequest request)
	{
		final Properties ctx = request.getCtx();

		final ImmutableSet<Integer> huIdsToProcess = request.getHuIdsToProcess();
		if (huIdsToProcess.isEmpty())
		{
			return;
		}

		final String adLanguage = request.getAdLanguage();
		final String reportLanguageToUse = Objects.equals(REPORT_LANG_NONE, adLanguage) ? null : adLanguage;

		ProcessInfo.builder()
				.setCtx(ctx)
				.setAD_Process_ID(request.getAdProcessId())
				.setWindowNo(request.getWindowNo())
				.setTableName(I_M_HU.Table_Name)
				.setReportLanguage(reportLanguageToUse)
				.addParameter(PARA_BarcodeURL, getBarcodeServlet(ctx))
				.addParameter(IJasperService.PARAM_PrintCopies, request.getCopies())
				//
				// Execute report in a new transaction
				.buildAndPrepareExecution()
				.onErrorThrowException(request.isOnErrorThrowException())
				.callBefore(processInfo -> DB.createT_Selection(processInfo.getAD_PInstance_ID(), huIdsToProcess, ITrx.TRXNAME_ThreadInherited))
				.executeSync();
	}

	private static String getBarcodeServlet(final Properties ctx)
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final String barcodeServlet = sysConfigBL.getValue(SYSCONFIG_BarcodeServlet,
				null,  // defaultValue,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));
		return barcodeServlet;
	}

	private static final class HUReportTrxListener
	{
		private final Properties ctx;
		private final int adProcessId;
		private final int windowNo;
		private final int copies;

		private final Set<Integer> huIdsToProcess = new LinkedHashSet<>(); // using a linked set to preserve the order in which HUs were added

		private String adLanguage;

		/**
		 * It turned out that afterCommit() is called twice and also, on the first time some things were not ready.
		 * Therefore (and because right now there is not time to get to the root of the problem),
		 * we are now doing the job on afterClose(). This flag is set to true on a commit and will tell the afterClose implementation if it shall proceed.
		 *
		 * @task https://github.com/metasfresh/metasfresh/issues/1263
		 *
		 */
		private boolean commitWasDone = false;

		private boolean listenerWasRegistered = false;

		private HUReportTrxListener(@NonNull final Properties ctx,
				final int adProcessId,
				final int windowNo,
				final int copies)
		{
			this.ctx = ctx;
			this.adProcessId = adProcessId;
			this.windowNo = windowNo;
			this.copies = copies;
		}

		public boolean addAll(@NonNull final Collection<Integer> huIds)
		{
			return huIdsToProcess.addAll(huIds);
		}

		public void setLanguage(@NonNull final String language)
		{
			if (this.adLanguage == null)
			{
				this.adLanguage = language;
			}
			else if (!Objects.equals(this.adLanguage, language))
			{
				this.adLanguage = REPORT_LANG_NONE;
			}
		}

		public boolean isListenerWasRegistered()
		{
			return listenerWasRegistered;
		}

		public void setListenerWasRegistered()
		{
			this.listenerWasRegistered = true;
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

			executeNow(HUReportRequest.builder()
					.ctx(ctx)
					.adProcessId(adProcessId)
					.windowNo(windowNo)
					.copies(copies)
					.adLanguage(adLanguage)
					.huIdsToProcess(ImmutableSet.copyOf(huIdsToProcess))
					.build());
		}
	}

	@lombok.Value
	@lombok.Builder
	private static class HUReportRequest
	{
		Properties ctx;
		int adProcessId;
		int windowNo;
		int copies;
		String adLanguage;
		boolean onErrorThrowException;
		ImmutableSet<Integer> huIdsToProcess;
	}
}
