package de.metas.dunning.process;

import java.util.List;

import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.archive.api.IArchiveDAO;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_Archive;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.Env;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableList;

import ch.qos.logback.classic.Level;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.dunning.DunningDocId;
import de.metas.dunning.invoice.DunningService;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfo;
import de.metas.report.ExecuteReportStrategy;
import de.metas.report.ExecuteReportStrategyUtil;
import de.metas.report.ExecuteReportStrategyUtil.PdfDataProvider;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class C_DunningDoc_JasperWithInvoicePDFsStrategy implements ExecuteReportStrategy
{
	private static final Logger logger = LogManager.getLogger(C_DunningDoc_JasperWithInvoicePDFsStrategy.class);

	private final transient IArchiveBL archiveBL = Services.get(IArchiveBL.class);
	private final transient IArchiveDAO archiveDAO = Services.get(IArchiveDAO.class);
	private final transient int dunningDocJasperProcessId;

	public C_DunningDoc_JasperWithInvoicePDFsStrategy(final int dunningDocJasperProcessId)
	{
		this.dunningDocJasperProcessId = dunningDocJasperProcessId;
	}

	@Override
	public ExecuteReportResult executeReport(
			@NonNull final ProcessInfo processInfo,
			@NonNull final OutputType outputType)
	{
		final DunningDocId dunningDocId = DunningDocId.ofRepoId(processInfo.getRecord_ID());

		final byte[] dunningDocData = ExecuteReportStrategyUtil.executeJasperProcess(dunningDocJasperProcessId, processInfo, outputType);

		final boolean isPDF = OutputType.PDF.equals(outputType);
		if (!isPDF)
		{
			Loggables.withLogger(logger, Level.WARN).addLog("Concatenating additional PDF-Data is not supported with outputType={}; returning only the jasper data itself.", outputType);
			return new ExecuteReportResult(outputType, dunningDocData);
		}

		final DunningService dunningService = SpringContextHolder.instance.getBean(DunningService.class);
		final List<I_C_Invoice> dunnedInvoices = dunningService.retrieveDunnedInvoices(dunningDocId);

		final List<PdfDataProvider> additionalDataItemsToAttach = retrieveAdditionalDataItems(dunnedInvoices);
		final byte[] data = ExecuteReportStrategyUtil.concatenatePDF(dunningDocData, additionalDataItemsToAttach);

		return new ExecuteReportResult(outputType, data);
	}

	private List<PdfDataProvider> retrieveAdditionalDataItems(@NonNull final List<I_C_Invoice> dunnedInvoices)
	{
		ImmutableList.Builder<PdfDataProvider> result = ImmutableList.builder();
		for (final I_C_Invoice invoice : dunnedInvoices)
		{
			final List<I_AD_Archive> invoiceArchives = archiveDAO.retrieveLastArchives(Env.getCtx(), TableRecordReference.of(invoice), 1);
			if (invoiceArchives.isEmpty())
			{
				continue;
			}

			final byte[] data = archiveBL.getBinaryData(invoiceArchives.get(0));
			result.add(PdfDataProvider.forData(data));
		}
		return result.build();
	}
}
