package de.metas.handlingunits.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
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

	/**
	 * AD_SysConfig for "BarcodeServlet".
	 */
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
		final ITrxManager trxManager = Services.get(ITrxManager.class);

		//
		// Collect HU's C_BPartner_IDs and M_HU_IDs
		final Set<Integer> huBPartnerIds = new HashSet<>();
		final List<Integer> huIds = new ArrayList<>();
		for (final I_M_HU hu : husToProcess)
		{
			final int huId = hu.getM_HU_ID();
			huIds.add(huId);

			// Collect HU's BPartner ID ... we will need that to advice the report to use HU's BPartner Language Locale
			final int bpartnerId = hu.getC_BPartner_ID();
			if (bpartnerId > 0)
			{
				huBPartnerIds.add(bpartnerId);
			}
		}

		// check if we actually got any new M_HU_ID
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

		if (!huReportTrxListener.addAll(huIds))
		{
			return; // there are no new HU IDs
		}

		// Use BPartner's Language, if all HUs' partners have a common language
		huReportTrxListener.setLanguage(extractReportingLanguage(huBPartnerIds));

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

	private HUReportTrxListener newHUReportTrxListener(final int adProcessId)
	{
		return new HUReportTrxListener(ctx, adProcessId, windowNo, numberOfCopies);
	}
	
	private String extractReportingLanguage(final Set<Integer> huBPartnerIds)
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

	private static final class HUReportTrxListener
	{
		private final Properties listenerCtx;
		private final int listenerAdProcessId;
		private final int listenerWindowNo;
		private final int listenerCopies;

		private final Set<Integer> husToProcess = new LinkedHashSet<>(); // using a linked set to preserve the order in which HUs were added

		private String language;

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
			this.listenerCtx = ctx;
			this.listenerAdProcessId = adProcessId;
			this.listenerWindowNo = windowNo;
			this.listenerCopies = copies;
		}

		public boolean addAll(@NonNull final List<Integer> huIds)
		{
			return husToProcess.addAll(huIds);
		}

		public void setLanguage(@NonNull final String language)
		{
			if (this.language == null)
			{
				this.language = language;
			}
			else if (!Objects.equals(this.language, language))
			{
				this.language = REPORT_LANG_NONE;
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

			if (husToProcess.isEmpty())
			{
				return;
			}

			final String reportLanguageToUse = Objects.equals(REPORT_LANG_NONE, language) ? null : language;

			final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
			final String barcodeServlet = sysConfigBL.getValue(SYSCONFIG_BarcodeServlet,
					null,  // defaultValue,
					Env.getAD_Client_ID(listenerCtx),
					Env.getAD_Org_ID(listenerCtx));

			ProcessInfo.builder()
					.setCtx(listenerCtx)
					.setAD_Process_ID(listenerAdProcessId)
					.setWindowNo(listenerWindowNo)
					.setTableName(I_M_HU.Table_Name)
					.setReportLanguage(reportLanguageToUse)
					.addParameter(PARA_BarcodeURL, barcodeServlet)
					.addParameter(IJasperService.PARAM_PrintCopies, BigDecimal.valueOf(listenerCopies))
					//
					// Execute report in a new transaction
					.buildAndPrepareExecution()
					.callBefore(processInfo -> DB.createT_Selection(processInfo.getAD_PInstance_ID(), husToProcess, ITrx.TRXNAME_ThreadInherited))
					.executeSync();
		}
	}
}
