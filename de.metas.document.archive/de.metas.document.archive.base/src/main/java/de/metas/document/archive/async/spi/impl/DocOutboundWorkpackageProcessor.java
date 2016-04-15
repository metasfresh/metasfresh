package de.metas.document.archive.async.spi.impl;

/*
 * #%L
 * de.metas.document.archive.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderOrderByClause;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.archive.api.IArchiveEventManager;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MClient;
import org.compiere.model.MQuery;
import org.compiere.model.PO;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.util.Language;

import de.metas.async.api.IQueueDAO;
import de.metas.async.api.IWorkPackageQueue;
import de.metas.async.model.I_C_Queue_Block;
import de.metas.async.model.I_C_Queue_WorkPackage;
import de.metas.async.processor.IWorkPackageQueueFactory;
import de.metas.async.spi.IWorkpackageProcessor;
import de.metas.document.archive.api.IArchiveDAO;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.document.engine.IDocActionBL;

/**
 * Process work packages from queue and:
 * <ul>
 * <li>archive the document
 * <li>record log (see {@link de.metas.document.archive.spi.impl.DocOutboundArchiveEventListener#onPdfUpdate(org.compiere.model.I_AD_Archive, org.compiere.model.I_AD_User)})
 * </ul>
 * 
 * @author tsa
 * 
 */
public class DocOutboundWorkpackageProcessor implements IWorkpackageProcessor
{
	private static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";

	private static final transient Logger logger = LogManager.getLogger(DocOutboundWorkpackageProcessor.class);

	@Override
	public Result processWorkPackage(final I_C_Queue_WorkPackage workpackage, final String localTrxName)
	{
		final IQueueDAO queueDAO = Services.get(IQueueDAO.class);

		final List<PO> list = queueDAO.retrieveItems(workpackage, PO.class, localTrxName);
		for (final PO po : list)
		{
			generateOutboundDocument(po);
		}
		return Result.SUCCESS;
	}

	private void generateOutboundDocument(final PO po)
	{
		final I_AD_Archive archive = archive(po);
		if (archive == null)
		{
			return;
		}

		Services.get(IArchiveEventManager.class).firePdfUpdate(archive, null); // user=null
	}

	// using protected modifier for JUnit testing
	protected PrintInfo createPrintInfo(final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);

		final int adTableId = Services.get(IADTableDAO.class).retrieveTableId(tableName);
		final int recordId = InterfaceWrapperHelper.getId(model);

		final String documentNo = Services.get(IDocActionBL.class).getDocumentNo(model);

		final PrintInfo printInfo = new PrintInfo(documentNo, adTableId, recordId);
		// printInfo.setAD_Process_ID(process_ID); // no process
		printInfo.setAD_Table_ID(adTableId);

		final Integer bpartnerId = InterfaceWrapperHelper.getValueOrNull(model, DocOutboundWorkpackageProcessor.COLUMNNAME_C_BPartner_ID);
		if (bpartnerId != null && bpartnerId > 0)
		{
			printInfo.setC_BPartner_ID(bpartnerId);
		}

		printInfo.setCopies(1); // TODO: C_BPartner.DocumentCopies + C_DocType.DocumentCopies
		printInfo.setDocumentCopy(false); // TODO: check if document was already printed (e.g. check IsPrinted flag)
		printInfo.setHelp(null);
		printInfo.setWithDialog(false);
		// printInfo.setName(name);
		// printInfo.setDescription(description);
		// printInfo.setPrinterName(printerName);
		// printInfo.setRecord_ID(record_ID);

		return printInfo;
	}

	// protected because it will be overwritten in testing
	protected I_AD_Archive archive(final PO po)
	{
		final Properties ctx = po.getCtx();
		final String trxName = po.get_TrxName();
		final int tableId = po.get_Table_ID();
		final int recordId = po.get_ID();

		//
		// Get Config
		final I_C_Doc_Outbound_Config config = Services.get(IArchiveDAO.class).retrieveConfig(ctx, tableId);
		if (config == null)
		{
			throw new AdempiereException("@NotFound@ @C_Doc_Outbound_Config@ (@TableName@:" + po.get_TableName() + ")");
		}
		logger.debug("Config: {}", config);

		//
		// Create ReportEngine
		final int reportEngineDocumentType = ReportEngine.getTypeByTableId(tableId);
		ReportEngine reportEngine = null;
		if (reportEngineDocumentType > -1)  // important: 0 is also fine! -1 means "not found"
		{
			// we are dealing with document reporting
			final int printFormatId = config.getAD_PrintFormat_ID();
			reportEngine = ReportEngine.get(ctx, reportEngineDocumentType, recordId, printFormatId, trxName);
		}

		// 09527 get the most suitable language from the po's C_BPartner, if it exists.
		Language language = Services.get(IBPartnerBL.class).getLanguageForModel(po);

		if (reportEngine == null)
		{
			final MQuery query = createMQuery(po);
			final PrintInfo printInfo = createPrintInfo(po);

			final boolean readFromDisk = false; // we can go with the cached version, because there is code making sure that we only get a cached version which has an equal ctx!

			//
			// Print format
			int printFormatId = findBP_PrintFormat_ID(po, printInfo.getC_BPartner_ID());
			if(printFormatId <= 0)
			{
				printFormatId = config.getAD_PrintFormat_ID();
			}
			final MPrintFormat printFormat = MPrintFormat.get(ctx, printFormatId, readFromDisk);
			
			// 04454 and 04430: we need to set the printformat's language;
			// using the client language is what would also be done by ReportEngine.get() if it can't be determined via a reportEngineDocumentType
			// 09527: Exception: When the partner has a language set. In this case, the partner's language must be set
			if (language == null)
			{
				language = MClient.get(ctx, po.getAD_Client_ID()).getLanguage();
			}
			printFormat.setLanguage(language);

			reportEngine = new ReportEngine(ctx, printFormat, query, printInfo, trxName);
		}
		logger.debug("ReportEngine: {} (DocumentType:{})", new Object[] { reportEngine, reportEngineDocumentType });

		//
		// Create PDF data
		final byte[] data = reportEngine.createPDFData();
		if (data == null || data.length == 0)
		{
			throw new AdempiereException("Cannot create PDF data for " + po);
		}
		logger.debug("PDF Data: {} bytes", data.length);

		//
		// PrintInfo (needed for archiving)
		final PrintInfo printInfo = reportEngine.getPrintInfo();
		logger.debug("PrintInfo: {}", printInfo);

		//
		// Create AD_Archive and save it
		final boolean forceArchive = true; // always force archive (i.e. don't check again if document needs to be archived)
		final I_AD_Archive archive = InterfaceWrapperHelper.create(
				Services.get(org.adempiere.archive.api.IArchiveBL.class).archive(data, printInfo, forceArchive, trxName),
				I_AD_Archive.class);
		// archive.setIsDirectPrint(true);
		archive.setC_Doc_Outbound_Config(config); // 09417: reference the config and it's settings will decide if a printing queue item shall be created
		InterfaceWrapperHelper.save(archive);
		logger.debug("Archive: {}", archive);

		//
		// Send data to CC Path if available
		final String ccPath = config.getCCPath();
		if (!Check.isEmpty(ccPath)
				&& Services.get(ICCAbleDocumentFactoryService.class).isSupported(po))
		{
			createCCFile(archive);
		}

		return archive;
	}

	private void createCCFile(final I_AD_Archive archive)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(archive);
		final String trxName = InterfaceWrapperHelper.getTrxName(archive);

		final IWorkPackageQueue queue = Services.get(IWorkPackageQueueFactory.class).getQueueForEnqueuing(ctx, DocOutboundCCWorkpackageProcessor.class);

		final I_C_Queue_Block block = queue.enqueueBlock(ctx);
		final I_C_Queue_WorkPackage workpackage = queue.enqueueWorkPackage(block, IWorkPackageQueue.PRIORITY_AUTO);
		queue.enqueueElement(workpackage, archive);

		queue.markReadyForProcessingAfterTrxCommit(workpackage, trxName);
	}

	/**
	 * Helper method which creates an {@link MQuery} (KeyColumnName=Record_ID) for given object
	 * 
	 * @param po
	 * @return
	 */
	private static MQuery createMQuery(final PO po)
	{
		final int recordId = po.get_ID();
		Check.assume(recordId >= 0, "Object {} has an ID", po);

		final String keyColumnName = po.getPOInfo().getKeyColumnName();
		Check.assumeNotNull(keyColumnName, "Object {} has a simple primary key", po);

		final MQuery query = MQuery.getEqualQuery(keyColumnName, recordId);
		query.setTableName(po.get_TableName());
		query.setRecordCount(1);

		return query;
	}

	private int findBP_PrintFormat_ID(final Object model, final int bpartnerId)
	{
		if (bpartnerId <= 0)
		{
			return -1;
		}
		if (model == null)
		{
			// shall not happen
			return -1;
		}

		final IQueryBuilder<I_C_BP_PrintFormat> queryBuilder = Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_BP_PrintFormat.class, model)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMN_C_BPartner_ID, bpartnerId);

		final IQueryBuilderOrderByClause<I_C_BP_PrintFormat> orderBy = queryBuilder.orderBy();
		final ICompositeQueryFilter<I_C_BP_PrintFormat> docTypeFilter = queryBuilder.addCompositeQueryFilter()
				.setJoinOr();

		// Match by DocType
		{
			final I_C_DocType docType = Services.get(IDocActionBL.class).getDocTypeOrNull(model);
			if (docType != null)
			{
				docTypeFilter.addEqualsFilter(I_C_BP_PrintFormat.COLUMN_C_DocType_ID, docType.getC_DocType_ID());

				orderBy.addColumn(I_C_BP_PrintFormat.COLUMN_C_DocType_ID, Direction.Ascending, Nulls.Last);
			}
		}

		// Match by AD_Table_ID
		{
			final int adTableId = InterfaceWrapperHelper.getModelTableId(model);

			docTypeFilter.addEqualsFilter(I_C_BP_PrintFormat.COLUMN_AD_Table_ID, adTableId);
			orderBy.addColumn(I_C_BP_PrintFormat.COLUMN_AD_Table_ID, Direction.Ascending, Nulls.Last);
		}

		final int printFormatId = queryBuilder.andCollect(I_C_BP_PrintFormat.COLUMN_AD_PrintFormat_ID)
				.create()
				.firstId();
		return printFormatId;
	}

}
