package de.metas.document.archive.spi.impl;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.dao.ICompositeQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryBuilderOrderByClause;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.archive.api.IArchiveBL;
import org.adempiere.bpartner.service.IBPartnerBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_DocType;
import org.compiere.model.MClient;
import org.compiere.model.MQuery;
import org.compiere.model.PrintInfo;
import org.compiere.print.MPrintFormat;
import org.compiere.print.ReportEngine;
import org.compiere.util.Language;
import org.slf4j.Logger;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.base.Optional;

import de.metas.document.archive.api.IDocOutboundDAO;
import de.metas.document.archive.async.spi.impl.DocOutboundCCWorkpackageProcessor;
import de.metas.document.archive.model.I_AD_Archive;
import de.metas.document.archive.model.I_C_Doc_Outbound_Config;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.document.engine.IDocActionBL;
import de.metas.logging.LogManager;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class DefaultModelArchiver
{
	public static final DefaultModelArchiver of(final Object record)
	{
		return new DefaultModelArchiver()
				.setRecord(record);
	}

	// services
	private static final Logger logger = LogManager.getLogger(DefaultModelArchiver.class);
	private final transient IArchiveBL archiveBL = Services.get(org.adempiere.archive.api.IArchiveBL.class);
	private final transient IDocOutboundDAO archiveDAO = Services.get(IDocOutboundDAO.class);
	private final transient IBPartnerBL bpartnerBL = Services.get(IBPartnerBL.class);
	private final transient IDocActionBL docActionBL = Services.get(IDocActionBL.class);
	private final transient ICCAbleDocumentFactoryService ccAbleDocumentFactoryService = Services.get(ICCAbleDocumentFactoryService.class);
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	// Constants
	private static final String COLUMNNAME_C_BPartner_ID = "C_BPartner_ID";
	private static final String COLUMNNAME_AD_Client_ID = "AD_Client_ID";

	//
	// Parameters
	private Object _record;
	private Integer _adPrintFormatId;

	//
	// Status & cached values
	private final AtomicBoolean _processed = new AtomicBoolean(false);
	private Optional<I_C_Doc_Outbound_Config> _docOutboundConfig;
	private Optional<Language> _language;
	//
	// Result
	private I_AD_Archive _archive;
	private byte[] _pdfData;

	private DefaultModelArchiver()
	{
		super();
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("record", _record)
				.add("language", _language)
				.add("AD_PrintFormat_ID", _adPrintFormatId)
				.add("docOutboundConfig", _docOutboundConfig)
				.toString();
	}

	public I_AD_Archive archive()
	{
		// Mark as processed
		markProcessed();

		//
		// Create PDF data
		final ReportEngine reportEngine = createReportEngine();
		final byte[] pdfData = reportEngine.createPDFData();
		if (pdfData == null || pdfData.length == 0)
		{
			throw new AdempiereException("Cannot create PDF data for " + this);
		}
		logger.debug("PDF Data: {} bytes", pdfData.length);

		//
		// PrintInfo (needed for archiving)
		final PrintInfo printInfo = reportEngine.getPrintInfo();

		final MPrintFormat printFormat = reportEngine.getPrintFormat();
		if (printFormat != null && printFormat.getJasperProcess_ID() > 0)
		{
			printInfo.setAD_Process_ID(printFormat.getJasperProcess_ID());
		}
		logger.debug("PrintInfo: {}", printInfo);

		//
		// Create AD_Archive and save it
		final I_AD_Archive archive;
		{
			final boolean forceArchive = true; // always force archive (i.e. don't check again if document needs to be archived)
			archive = InterfaceWrapperHelper.create(archiveBL.archive(pdfData, printInfo, forceArchive, ITrx.TRXNAME_ThreadInherited), I_AD_Archive.class);
			// archive.setIsDirectPrint(true);
			archive.setC_Doc_Outbound_Config(getC_Doc_Outbound_Config_OrNull()); // 09417: reference the config and it's settings will decide if a printing queue item shall be created
			InterfaceWrapperHelper.save(archive);
			logger.debug("Archive: {}", archive);
		}

		//
		// Send data to CC Path if available
		final String ccPath = getCCPath();
		if (!Check.isEmpty(ccPath, true)
				&& ccAbleDocumentFactoryService.isSupported(getRecord()))
		{
			createCCFile(archive);
		}

		//
		// Set result & return
		this._archive = archive;
		this._pdfData = pdfData;
		return archive;
	}
	
	public I_AD_Archive getAD_Archive()
	{
		assertProcessed();
		return _archive;
	}
	
	public byte[] getPdfData()
	{
		assertProcessed();
		return _pdfData;
	}

	private final void markProcessed()
	{
		final boolean alreadyProcessed = _processed.getAndSet(true);
		if (alreadyProcessed)
		{
			// already processed
			throw new AdempiereException("Already processed: " + this);
		}
	}

	protected final void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "not already processed: {}", this);
	}
	
	protected final void assertProcessed()
	{
		Check.assume(_processed.get(), "processed: {}", this);
	}

	// NOTE: private is ok since we are setting the record from the factory method
	private DefaultModelArchiver setRecord(final Object record)
	{
		assertNotProcessed();
		_record = record;
		return this;
	}

	protected final Object getRecord()
	{
		Check.assumeNotNull(_record, "record not null");
		return _record;
	}

	private Properties getCtx()
	{
		return InterfaceWrapperHelper.getCtx(getRecord());
	}

	private int getAD_Table_ID()
	{
		return InterfaceWrapperHelper.getModelTableId(getRecord());
	}

	private int getRecord_ID()
	{
		return InterfaceWrapperHelper.getId(getRecord());
	}

	private I_C_Doc_Outbound_Config getC_Doc_Outbound_Config_OrNull()
	{
		if (_docOutboundConfig == null)
		{
			final I_C_Doc_Outbound_Config docOutboundConfig = archiveDAO.retrieveConfig(getCtx(), getAD_Table_ID());
			logger.debug("Using config: {}", docOutboundConfig);
			_docOutboundConfig = Optional.fromNullable(docOutboundConfig);
		}
		return _docOutboundConfig.orNull();
	}

	public DefaultModelArchiver setAD_PrintFormat_ID(final int adPrintFormatId)
	{
		assertNotProcessed();
		this._adPrintFormatId = adPrintFormatId;
		return this;
	}

	private int getAD_PrintFormat_ID()
	{
		if (_adPrintFormatId != null && _adPrintFormatId > 0)
		{
			return _adPrintFormatId;
		}

		final I_C_Doc_Outbound_Config docOutboundConfig = getC_Doc_Outbound_Config_OrNull();
		if (docOutboundConfig == null)
		{
			return -1;
		}
		return docOutboundConfig.getAD_PrintFormat_ID();
	}

	private String getCCPath()
	{
		final I_C_Doc_Outbound_Config docOutboundConfig = getC_Doc_Outbound_Config_OrNull();
		if (docOutboundConfig == null)
		{
			return null;
		}
		return docOutboundConfig.getCCPath();
	}

	private final Language getLanguage()
	{
		if (_language == null)
		{
			final Language language = extractLanguage();
			_language = Optional.fromNullable(language);
		}

		return _language.orNull();
	}

	protected Language extractLanguage()
	{
		final Object record = getRecord();
		// 09527 get the most suitable language from the po's C_BPartner, if it exists.
		Language language = bpartnerBL.getLanguageForModel(record);
		if (language != null)
		{
			logger.debug("Using {} BPartner's language: {}", record, language);
			return language;
		}

		// 04454 and 04430: we need to set the printformat's language;
		// using the client language is what would also be done by ReportEngine.get() if it can't be determined via a reportEngineDocumentType
		// 09527: Exception: When the partner has a language set. In this case, the partner's language must be set
		if (language == null)
		{
			final Properties ctx = InterfaceWrapperHelper.getCtx(record);
			final Integer adClientId = InterfaceWrapperHelper.<Integer> getValue(record, COLUMNNAME_AD_Client_ID).or(-1);
			if (adClientId != null && adClientId >= 0)
			{
				final MClient adClient = MClient.get(ctx, adClientId);
				language = adClient.getLanguage();
				if (language != null)
				{
					logger.debug("Using {}'s language: {}", adClient, language);
					return language;
				}
			}
		}

		logger.debug("No language found");
		return null;
	}

	private ReportEngine createReportEngine()
	{
		final Object record = getRecord();
		final Properties ctx = getCtx();
		final String trxName = ITrx.TRXNAME_ThreadInherited;
		final int tableId = getAD_Table_ID();
		final int recordId = getRecord_ID();

		//
		// Create ReportEngine
		final int reportEngineDocumentType = ReportEngine.getTypeByTableId(tableId);
		ReportEngine reportEngine = null;
		if (reportEngineDocumentType > -1)         // important: 0 is also fine! -1 means "not found"
		{
			// we are dealing with document reporting
			final int pInstanceId = 0; // N/A
			final int printFormatId = getAD_PrintFormat_ID();
			reportEngine = ReportEngine.get(ctx, reportEngineDocumentType, recordId, pInstanceId, printFormatId, trxName);
		}

		if (reportEngine == null)
		{
			final MQuery query = createMQuery(record);
			final PrintInfo printInfo = createPrintInfo();

			final boolean readFromDisk = false; // we can go with the cached version, because there is code making sure that we only get a cached version which has an equal ctx!

			//
			// Print format
			int printFormatId = findBP_PrintFormat_ID(record, printInfo.getC_BPartner_ID());
			if (printFormatId <= 0)
			{
				printFormatId = getAD_PrintFormat_ID();
			}
			final MPrintFormat printFormat = MPrintFormat.get(ctx, printFormatId, readFromDisk);

			final Language language = getLanguage();
			printFormat.setLanguage(language);

			reportEngine = new ReportEngine(ctx, printFormat, query, printInfo, trxName);
		}
		logger.debug("ReportEngine: {} (DocumentType:{})", reportEngine, reportEngineDocumentType);

		return reportEngine;
	}

	@VisibleForTesting
	public PrintInfo createPrintInfo()
	{
		final Object record = getRecord();
		final int adTableId = InterfaceWrapperHelper.getModelTableId(record);
		final int recordId = InterfaceWrapperHelper.getId(record);

		final String documentNo = docActionBL.getDocumentNo(record);

		final PrintInfo printInfo = new PrintInfo(documentNo, adTableId, recordId);
		// printInfo.setAD_Process_ID(process_ID); // no process
		printInfo.setAD_Table_ID(adTableId);

		final Integer bpartnerId = InterfaceWrapperHelper.getValueOrNull(record, COLUMNNAME_C_BPartner_ID);
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

	private void createCCFile(final I_AD_Archive archive)
	{
		DocOutboundCCWorkpackageProcessor.scheduleOnTrxCommit(archive);
	}

	/**
	 * Helper method which creates an {@link MQuery} (KeyColumnName=Record_ID) for given object
	 *
	 * @param po
	 * @return
	 */
	private static MQuery createMQuery(final Object record)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(record);

		final int recordId = InterfaceWrapperHelper.getId(record);
		Check.assume(recordId >= 0, "Record {} has an ID", record);

		final String keyColumnName = InterfaceWrapperHelper.getModelKeyColumnName(record);
		Check.assumeNotNull(keyColumnName, "Table {} has a simple primary key", tableName);

		//
		final MQuery query = MQuery.getEqualQuery(keyColumnName, recordId);
		query.setTableName(tableName);
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

		final Properties ctx = InterfaceWrapperHelper.getCtx(model);
		final IQueryBuilder<I_C_BP_PrintFormat> queryBuilder = queryBL.createQueryBuilder(I_C_BP_PrintFormat.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addOnlyContextClientOrSystem()
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMN_C_BPartner_ID, bpartnerId);

		final IQueryBuilderOrderByClause<I_C_BP_PrintFormat> orderBy = queryBuilder.orderBy();
		final ICompositeQueryFilter<I_C_BP_PrintFormat> docTypeFilter = queryBuilder.addCompositeQueryFilter()
				.setJoinOr();

		// Match by DocType
		{
			final I_C_DocType docType = docActionBL.getDocTypeOrNull(model);
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
