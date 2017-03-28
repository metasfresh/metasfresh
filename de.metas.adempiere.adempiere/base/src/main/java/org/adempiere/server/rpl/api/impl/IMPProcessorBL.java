package org.adempiere.server.rpl.api.impl;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Iterator;
import java.util.Properties;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.server.rpl.IImportProcessor;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorDAO;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.server.rpl.interfaces.I_IMP_Processor;
import org.adempiere.service.IAttachmentBL;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Services;
import org.adempiere.util.StringUtils;
import org.compiere.model.AdempiereProcessor;
import org.compiere.model.I_AD_Attachment;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.I_IMP_ProcessorLog;
import org.compiere.model.I_IMP_ProcessorParameter;
import org.compiere.model.I_IMP_Processor_Type;
import org.compiere.model.MColumn;
import org.compiere.model.MRefTable;
import org.compiere.model.MTable;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DisplayType;
import org.compiere.util.TrxRunnable;
import org.w3c.dom.Document;

public class IMPProcessorBL implements IIMPProcessorBL
{
	private static final Logger log = LogManager.getLogger(IMPProcessorBL.class);

	private Class<? extends IImportHelper> importHelperClass = ImportHelper.class;

	private static final String XMLATTACHMENT_NAME = "message.xml";

	private static final String MSG_AD_Reference_Override_ID_Not_Handled = "AD_Reference_Override_ID_NotHandled";

	@Override
	public I_IMP_ProcessorLog createLog(final org.compiere.model.I_IMP_Processor impProcessor,
			final String summary, final String text, final String reference, final Throwable error)
	{
		if (error != null)
		{
			// we only want to log the stacktrace of the orginal cause, so we iterate to it
			// note that we use the counter as a primitive protection against infinite loops
			Throwable originalCause = error;
			int counter = 0;
			while (originalCause.getCause() != null && originalCause.getCause() != originalCause)
			{
				originalCause = originalCause.getCause();
				Check.assume(counter < 1000, "There is no loop in originalCause.getCause() with originalCause=" + originalCause);
				counter++;
			}
			log.error(error.getLocalizedMessage(), originalCause); // log the full error message, but only the original exception
		}
		else
		{
			log.trace(summary);
		}

		if (error == null && InterfaceWrapperHelper.create(impProcessor, I_IMP_Processor.class).isLogOnlyImportErrors())
		{
			log.trace("There is no error and we only log errors; nothing to do");
			return null;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(impProcessor); // TODO: we need to use getCtx(impProcessor, useClientOrgFromModel=true)
		final I_IMP_ProcessorLog pLog = InterfaceWrapperHelper.create(ctx, I_IMP_ProcessorLog.class, ITrx.TRXNAME_None);
		pLog.setAD_Org_ID(impProcessor.getAD_Org_ID());

		pLog.setIMP_Processor_ID(impProcessor.getIMP_Processor_ID());
		pLog.setSummary(summary);
		pLog.setTextMsg(StringUtils.trunc(text, 2000));
		// pLog.setReference("topicName = " + topicName );
		pLog.setReference(reference);
		pLog.setIsError(error != null);
		InterfaceWrapperHelper.save(pLog);

		if (!Check.isEmpty(text, true))
		{
			final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
			final I_AD_Attachment attachment = attachmentBL.getAttachment(pLog);
			attachmentBL.addEntry(attachment, XMLATTACHMENT_NAME, text.getBytes());
		}

		return pLog;
	}

	@Override
	public String getXmlMessage(final I_IMP_ProcessorLog pLog)
	{
		Check.assumeNotNull(pLog, "pLog not null");

		final IAttachmentBL attachmentBL = Services.get(IAttachmentBL.class);
		final I_AD_Attachment attachment = attachmentBL.getAttachment(pLog);

		final byte[] data = attachmentBL.getEntryAsBytes(attachment, XMLATTACHMENT_NAME);
		if (data == null || data.length == 0)
		{
			return null;
		}
		final String xml = new String(data);
		if (Check.isEmpty(xml, true))
		{
			return null;
		}
		return xml;
	}

	@Override
	public org.w3c.dom.Document getXmlDocument(final I_IMP_ProcessorLog plog)
	{
		final String xmlString = getXmlMessage(plog);
		if (Check.isEmpty(xmlString))
		{
			return null;
		}

		try
		{
			final Document xmlDocument = XMLHelper.createDocumentFromString(xmlString);
			return xmlDocument;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Invalid XML Document for " + plog, e);
		}
	}

	@Override
	public void markResolved(final I_IMP_ProcessorLog plog)
	{
		plog.setIsError(false);
		InterfaceWrapperHelper.save(plog);
	}

	@Override
	public void resubmit(final Iterator<I_IMP_ProcessorLog> logs, final boolean failfast, final ILoggable loggable)
	{
		final int[] countSuccess = { 0 };
		final int[] countFail = { 0 };

		while (logs.hasNext())
		{
			final I_IMP_ProcessorLog plog = logs.next();

			final Document document = getXmlDocument(plog);
			if (document == null)
			{
				continue;
			}

			final Properties ctx = InterfaceWrapperHelper.getCtx(plog);
			final IImportHelper impHelper = createImportHelper(ctx);

			final StringBuilder result = new StringBuilder();
			Services.get(ITrxManager.class).run(ITrx.TRXNAME_None, new TrxRunnable()
			{
				@Override
				public void run(final String localTrxName) throws Exception
				{
					try
					{
						impHelper.importXMLDocument(result, document, localTrxName);
						countSuccess[0]++;

						log.info("" + plog + " - Result: " + result);

						InterfaceWrapperHelper.refresh(plog, localTrxName); // change plog's trxname to 'localTrxName'
						markResolved(plog);
					}
					catch (final ReplicationException e)
					{
						InterfaceWrapperHelper.refresh(plog, localTrxName); // change plog's trxname to 'localTrxName'
						refreshLogException(plog, e);

						if (failfast)
						{
							throw e;
						}
						countFail[0]++;
					}
				}
			});
		}
		loggable.addLog("countSuccess=" + countSuccess[0] + "; countFail=" + countFail[0]);
	}

	private void refreshLogException(final I_IMP_ProcessorLog plog, final Throwable t)
	{
		// set XML message back just to be sure
		plog.setTextMsg(StringUtils.trunc(getXmlMessage(plog), 2000));

		// set error flag & exception message
		plog.setIsError(true);
		plog.setSummary("Error: " + t.getLocalizedMessage());
		// leave the reference intact
		// plog.setReference(null);

		InterfaceWrapperHelper.save(plog);
	}

	@Override
	public IImportProcessor getIImportProcessor(final org.compiere.model.I_IMP_Processor impProcessor)
	{
		final I_IMP_Processor_Type type = impProcessor.getIMP_Processor_Type(); // TODO: caching
		final String classname = type.getJavaClass();
		try
		{
			return (IImportProcessor)getClass().getClassLoader().loadClass(classname).newInstance();
		}
		catch (final Exception e)
		{
			throw new AdempiereException(e);
		}
	}

	@Override
	public I_IMP_ProcessorParameter createParameter(final org.compiere.model.I_IMP_Processor impProcessor,
			final String key, final String name, final String desc, final String help, final String value)
	{
		I_IMP_ProcessorParameter para = Services.get(IIMPProcessorDAO.class).retrieveParameter(impProcessor, key);
		if (para == null)
		{
			para = InterfaceWrapperHelper.newInstance(I_IMP_ProcessorParameter.class, impProcessor);
			para.setIMP_Processor_ID(impProcessor.getIMP_Processor_ID());
			para.setValue(key);
		}
		para.setIsActive(true);
		para.setName(name);
		para.setDescription(desc);
		para.setHelp(help);
		para.setParameterValue(value);
		InterfaceWrapperHelper.save(para);
		return para;
	}

	@Override
	public I_IMP_ProcessorParameter createParameter(final org.compiere.model.I_IMP_Processor impProcessor, final String key, final String value)
	{
		final String name = null;
		final String desc = null;
		final String help = null;
		return createParameter(impProcessor, key, name, desc, help, value);
	}

	@Override
	public AdempiereProcessor asAdempiereProcessor(final org.compiere.model.I_IMP_Processor impProcessor)
	{
		if (impProcessor == null)
		{
			return null;
		}

		return new IMPProcessorAdempiereProcessorAdapter(impProcessor);
	}

	@Override
	public org.compiere.model.I_IMP_Processor getIMP_Processor(final AdempiereProcessor adempiereProcessor)
	{
		Check.assumeNotNull(adempiereProcessor, "adempiereProcessor not null");
		if (adempiereProcessor instanceof IMPProcessorAdempiereProcessorAdapter)
		{
			return ((IMPProcessorAdempiereProcessorAdapter)adempiereProcessor).getIMP_Procesor();
		}
		else
		{
			throw new IllegalArgumentException("Cannot get " + org.compiere.model.I_IMP_Processor.class + " from " + adempiereProcessor);
		}
	}

	@Override
	public IImportHelper createImportHelper(final Properties initialCtx)
	{
		try
		{
			final IImportHelper importHelper = importHelperClass.newInstance();
			importHelper.setInitialCtx(initialCtx);
			return importHelper;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot instantiate " + importHelperClass, e);
		}
	}

	@Override
	public void setImportHelperClass(final Class<? extends IImportHelper> importHelperClass)
	{
		Check.assumeNotNull(importHelperClass, "importHelperClass not null");
		this.importHelperClass = importHelperClass;
	}

	@Override
	public int getAD_Reference_ID(final I_AD_Column column, final I_EXP_FormatLine formatLine)
	{
		Check.assumeNotNull(column, "Param 'column' is not null");
		Check.assumeNotNull(formatLine, "Param 'formatLine' is not null");

		final int displayType = column.getAD_Reference_ID();
		final int displayTypeOverride = formatLine.getAD_Reference_Override_ID();

		//
		// Override not set
		if (displayTypeOverride <= 0)
		{
			return displayType;
		}
		//
		// Override to Date
		else if (displayTypeOverride == DisplayType.Date)
		{
			if (displayType == DisplayType.Date
					|| displayType == DisplayType.DateTime)
			{
				return displayTypeOverride;
			}
			else
			{
				throw new ReplicationException(MSG_AD_Reference_Override_ID_Not_Handled);
			}
		}
		//
		// Override to ID
		else if (displayTypeOverride == DisplayType.ID)
		{
			if (DisplayType.isID(displayType))
			{
				return displayTypeOverride;
			}

			throw new ReplicationException(MSG_AD_Reference_Override_ID_Not_Handled);
		}
		//
		// Fail: Override is not compatible with original type
		else
		{
			throw new ReplicationException(MSG_AD_Reference_Override_ID_Not_Handled);
		}
	}
	
	
	@Override
	public ITableAndColumn getTargetTableAndColumn(I_EXP_FormatLine line)
	{
		Check.errorUnless(
				X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(line.getType()) || X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()),
				"Given {} has wrong type {}", line.getType()); 
		
		// start 03203 case handling for different replication linking
		// almost copy-paste from ExportHelper
		
		//embeddedFormatLine
		final Properties ctx = InterfaceWrapperHelper.getCtx(line);
		
		final I_AD_Column column = line.getAD_Column();
		
		final int displayType = getAD_Reference_ID(column, line);
		final int adReferenceValueId = column.getAD_Reference_Value_ID();
		final String embeddedTableName;
		final String embeddedKeyColumnName;

		if (displayType == DisplayType.Table
				|| displayType == DisplayType.Search && adReferenceValueId > 0)
		{
			final int referenceId = adReferenceValueId;

			final MRefTable refTable = MRefTable.get(ctx, referenceId);
			final MColumn embeddedKeyColumn = MColumn.get(ctx, refTable.getAD_Key());

			final MTable embeddedTable = MTable.get(ctx, refTable.getAD_Table_ID());
			embeddedTableName = embeddedTable.getTableName();
			embeddedKeyColumnName = embeddedKeyColumn.getColumnName();
		}
		else if (displayType == DisplayType.TableDir
				|| displayType == DisplayType.Search && adReferenceValueId <= 0)
		{
		
			final MTable embeddedTable = MTable.get(ctx,  line.getEXP_EmbeddedFormat().getAD_Table_ID());
			final String[] embeddedKeyColumns = embeddedTable.getKeyColumns();
			if (embeddedKeyColumns == null || embeddedKeyColumns.length != 1)
			{
				throw new ReplicationException("Embedded table shall have one and only one primary key")
						.setParameter("AD_Table_ID", embeddedTable);
			}
			embeddedTableName = embeddedTable.getTableName();
			embeddedKeyColumnName = embeddedKeyColumns[0];
		}
		else if (DisplayType.isLookup(displayType, true))
		{
			embeddedTableName = DisplayType.getTableName(displayType);
			Check.assumeNotNull(embeddedTableName, "TableName found for DisplayType={}", displayType);
			final MTable embeddedTable = MTable.get(ctx, embeddedTableName);
			final String[] embeddedKeyColumns = embeddedTable.getKeyColumns();
			if (embeddedKeyColumns == null || embeddedKeyColumns.length != 1)
			{
				throw new ReplicationException("Embedded table shall have one and only one primary key")
						.setParameter("AD_Table_ID", embeddedTable);
			}
			embeddedKeyColumnName = embeddedKeyColumns[0];
		}
		else
		{
			throw new IllegalStateException("Column's reference type not supported: " + column + " , DisplayType=" + displayType);
		}
		
		return new ITableAndColumn()
		{
			@Override
			public String getTableName()
			{
				return embeddedTableName;
			}
			
			@Override
			public String getColumnName()
			{
				return embeddedKeyColumnName;
			}
		};
	}
}
