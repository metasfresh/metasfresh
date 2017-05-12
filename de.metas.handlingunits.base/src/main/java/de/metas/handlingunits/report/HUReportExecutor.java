package de.metas.handlingunits.report;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.ad.trx.spi.TrxListenerAdapter;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Table_Process;
import org.compiere.report.IJasperService;
import org.compiere.util.DB;
import org.compiere.util.Env;

import de.metas.handlingunits.model.I_M_HU;
import de.metas.i18n.Language;
import de.metas.process.ProcessInfo;

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
	/**
	 * AD_SysConfig for "BarcodeServlet".
	 */
	private static final String SYSCONFIG_BarcodeServlet = "de.metas.adempiere.report.barcode.BarcodeServlet";
	private static final String PARA_BarcodeURL = "barcodeURL";

	private final Properties ctx;

	private int windowNo = Env.WINDOW_None;

	private int copies = 1;

	private HUReportExecutor(final Properties ctx)
	{
		this.ctx = ctx;
	};

	public static HUReportExecutor get(final Properties ctx)
	{
		return new HUReportExecutor(ctx);
	}

	/**
	 * Give this service a window number. The default is {@link Env#WINDOW_None}.
	 *
	 * @param windowNo
	 * @return
	 */
	public HUReportExecutor withWindowNo(final int windowNo)
	{
		this.windowNo = windowNo;
		return this;
	}

	/**
	 * Specify the number of copies. One means one printout. The default is one.
	 *
	 * @param copies
	 * @return
	 */
	public HUReportExecutor withNumberOfCopies(final int copies)
	{
		this.copies = copies;
		return this;
	}

	/**
	 * Prepares everything and creates a trx-listener to run the report after the current trx is committed (or right now, if there is no currently open trx).
	 * 
	 * @param adProcessId the (jasper-)process to be executed
	 * @param husToProcess the HUs to be processed/shown in the report. These HUs' IDs are added to the {@code T_Select} table and can be accessed by the jasper file.
	 * @param printCopies number of copies. "1" means one printout
	 */
	public void executeHUReportAfterCommit(final int adProcessId, final List<I_M_HU> husToProcess)
	{
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

		//
		// Use BPartner's Language as reporting language if our HUs have an unique BPartner
		final Language reportLanguage;
		if (huBPartnerIds.size() == 1)
		{
			final int bpartnerId = huBPartnerIds.iterator().next();
			reportLanguage = Services.get(IBPartnerBL.class).getLanguage(ctx, bpartnerId);
		}
		else
		{
			reportLanguage = null; // N/A
		}

		final String barcodeServlet = Services.get(ISysConfigBL.class).getValue(SYSCONFIG_BarcodeServlet,
				null,  // defaultValue,
				Env.getAD_Client_ID(ctx),
				Env.getAD_Org_ID(ctx));

		// gh #1121: do this not now, but after the current transaction was committed. Because "right now", the Hus in question are probably not yet ready.
		// The background is that in the handling unit framework we have some "decoupled" DAOs, that collect data in memory and then safe it all at once, right before the commit is made.
		final ITrxManager trxManager = Services.get(ITrxManager.class);
		trxManager
				.getTrxListenerManagerOrAutoCommit(ITrx.TRXNAME_ThreadInherited)
				.registerListener(
						new TrxListenerAdapter()
						{
							/**
							 * It turned out that afterCommit() is called twice and also, on the first time some things were not ready.
							 * Therefore (and because right now there is not time to get to the root of the problem),
							 * we are now doing the job on afterClose(). This flag is set to true on a commit and will tell the afterClose implementation if it shall proceed.
							 * 
							 * @task https://github.com/metasfresh/metasfresh/issues/1263
							 * 
							 */
							boolean commitWasDone = false;

							@Override
							public void afterCommit(final ITrx trx)
							{
								commitWasDone = true;
							}

							@Override
							public void afterClose(final ITrx trx)
							{
								if (!commitWasDone)
								{
									return;
								}
								ProcessInfo.builder()
										.setCtx(ctx)
										.setAD_Process_ID(adProcessId)
										.setWindowNo(windowNo)
										.setTableName(I_M_HU.Table_Name)
										.setReportLanguage(reportLanguage)
										.addParameter(PARA_BarcodeURL, barcodeServlet)
										.addParameter(IJasperService.PARAM_PrintCopies, BigDecimal.valueOf(copies))
										//
										// Execute report in a new transaction
										.buildAndPrepareExecution()
										.callBefore(processInfo -> DB.createT_Selection(processInfo.getAD_PInstance_ID(), huIds, ITrx.TRXNAME_ThreadInherited))
										.executeSync();
							}
						});
	}
}
