package de.metas.edi.process;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.Arrays;
import java.util.Iterator;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.FailTrxItemExceptionHandler;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;
import org.compiere.process.DocAction;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.process.ProcessInfo;
import de.metas.process.JavaProcess;

/**
 * Aggregates edi-enabled inOuts into desadv records.
 *
 * @author ts
 *
 */
public class EDI_Desadv_Aggregate_M_InOuts extends JavaProcess
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	protected final void prepare()
	{
		// nothing
	}

	@Override
	protected final String doIt() throws Exception
	{
		final ProcessInfo pi = getProcessInfo();

		final IQueryFilter<I_M_InOut> processQueryFilter = pi.getQueryFilter();

		// subquery to select only inOuts with EDI-partners
		final IQuery<I_C_BPartner> ediRecipient = queryBL
				.createQueryBuilder(I_C_BPartner.class, getCtx(), getTrxName())
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_BPartner.COLUMNNAME_IsEdiRecipient, true).create();

		final Iterator<I_M_InOut> inOuts = queryBL
				.createQueryBuilder(I_M_InOut.class, getCtx(), getTrxName())

		// the default filters
				.addOnlyActiveRecordsFilter()

		.addEqualsFilter(I_M_InOut.COLUMNNAME_EDI_Desadv_ID, null) // not yet assigned

		// not yet sent
				.addNotInArrayFilter(I_EDI_Document.COLUMNNAME_EDI_ExportStatus,
						Arrays.asList(I_EDI_Document.EDI_EXPORTSTATUS_Sent, I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted))

		.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_IsSOTrx, true)

		.addInArrayFilter(org.compiere.model.I_M_InOut.COLUMNNAME_DocStatus,
				DocAction.STATUS_Completed, DocAction.STATUS_Closed)

		.addNotEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_POReference, null)

		// task 08926: make sure the inout has EdiEnabled

		.addEqualsFilter(I_M_InOut.COLUMNNAME_IsEdiEnabled, true)

		.addInSubQueryFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_ID, org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID, ediRecipient)

		// the specific process filter (if any)
				.filter(processQueryFilter)

		.create()
				.iterate(I_M_InOut.class);

		final ITrxItemProcessor<I_M_InOut, Void> processor = mkProcessor();

		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);

		final ITrxItemProcessorExecutor<I_M_InOut, Void> executor = executorService.<I_M_InOut, Void> createExecutor()
				.setContext(getCtx(), ITrx.TRXNAME_None)
				.setExceptionHandler(FailTrxItemExceptionHandler.instance)
				.setProcessor(processor)
				.build();

		executor.execute(inOuts);

		return "OK";
	}

	private ITrxItemProcessor<I_M_InOut, Void> mkProcessor()
	{
		return new ITrxItemProcessor<I_M_InOut, Void>()
		{
			private final IDesadvBL desadvBL = Services.get(IDesadvBL.class);

			private ITrxItemProcessorContext processorCtx;

			@Override
			public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
			{
				this.processorCtx = processorCtx;
			}

			@Override
			public void process(final I_M_InOut item) throws Exception
			{
				// Also add invalid ones. The user can sort it out in the desadv window
				// final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
				// final List<Exception> feedback = ediDocumentBL.isValidInOut(item);
				// if (!feedback.isEmpty())
				// {
				// final String errorMessage = ediDocumentBL.buildFeedback(feedback);
				// throw new AdempiereException(errorMessage);
				// }

				final String trxNameBackup = InterfaceWrapperHelper.getTrxName(item);
				try
				{
					EDI_Desadv_Aggregate_M_InOuts.this.addLog("@Added@: @M_InOut_ID@ " + item.getDocumentNo());
					InterfaceWrapperHelper.setTrxName(item, processorCtx.getTrxName());
					final I_EDI_Desadv desadv = desadvBL.addToDesadvCreateForInOutIfNotExist(item);
					if (desadv == null)
					{
						EDI_Desadv_Aggregate_M_InOuts.this.addLog("Could not create desadv for M_InOut=" + item);
					}
					else
					{
						InterfaceWrapperHelper.save(item);
					}
				}
				finally
				{
					InterfaceWrapperHelper.setTrxName(item, trxNameBackup);
				}
			}

			@Override
			public Void getResult()
			{
				return null;
			}
		};
	}
}
