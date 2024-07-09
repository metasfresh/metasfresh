/**********************************************************************
 * This file is part of Adempiere ERP Bazaar *
 * http://www.adempiere.org *
 * *
 * Copyright (C) Trifon Trifonov. *
 * Copyright (C) Contributors *
 * *
 * This program is free software; you can redistribute it and/or *
 * modify it under the terms of the GNU General Public License *
 * as published by the Free Software Foundation; either version 2 *
 * of the License, or (at your option) any later version. *
 * *
 * This program is distributed in the hope that it will be useful, *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the *
 * GNU General Public License for more details. *
 * *
 * You should have received a copy of the GNU General Public License *
 * along with this program; if not, write to the Free Software *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, *
 * MA 02110-1301, USA. *
 * *
 * Contributors: *
 * - Trifon Trifonov (trifonnt@users.sourceforge.net) *
 * - Antonio Cañaveral, e-Evolution *
 * *
 * Sponsors: *
 * - E-evolution (http://www.e-evolution.com/) *
 *********************************************************************/
package org.adempiere.process.rpl.exp;

import com.google.common.annotations.VisibleForTesting;
import de.metas.adempiere.service.IAppDictionaryBL;
import de.metas.attachments.AttachmentEntryService;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.security.permissions.Access;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.ExportProcessor2Wrapper;
import org.adempiere.process.rpl.IExportProcessor;
import org.adempiere.process.rpl.IExportProcessor2;
import org.adempiere.process.rpl.RPL_Constants;
import org.adempiere.process.rpl.api.IReplicationAccessContext;
import org.adempiere.process.rpl.api.impl.ReplicationAccessContext;
import org.adempiere.server.rpl.api.impl.ImportHelper;
import org.adempiere.server.rpl.exceptions.ExportProcessorException;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.SpringContextHolder;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_ReplicationStrategy;
import org.compiere.model.I_AD_ReplicationTable;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.MClient;
import org.compiere.model.MColumn;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MEXPProcessor;
import org.compiere.model.MRefTable;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.MTable;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.util.DB;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Text;

import javax.annotation.Nullable;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

/**
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution
 * <ul>
 * <li>[ 2195016 ] Implementation delete records messages
 * <li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195016&group_id=176962&atid=879332
 * </ul>
 * @author victor.perez@e-evolution.com, e-Evolution
 * <ul>
 * <li>[ 2195090 ] Stabilization of replication
 * <li>https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962
 * <li>BF [2947622] The replication ID (Primary Key) is not working
 * <li>https://sourceforge.net/tracker/?func=detail&aid=2947622&group_id=176962&atid=879332
 * </ul>
 */
public class ExportHelper
{
	public static final String MSG_EXPFieldMandatory = "EXPFieldMandatory";
	public static final String MSG_EXPColumnMandatory = "EXPColumnMandatory";
	public static final String MSG_EXPFormatNotFound = "EXPFormatNotFound";
	public static final String MSG_EXPFormatLineError = "EXPFormatLineError";

	/**
	 * Logger
	 */
	private static Logger log = LogManager.getLogger(ExportHelper.class);

	/**
	 * XML Document
	 */
	private Document outDocument = null;

	/** Custom Date Format */
	// private SimpleDateFormat m_customDateFormat = null;

	/**
	 * Client
	 */
	private int m_AD_Client_ID = -1;

	/**
	 * Replication Strategy
	 */
	private I_AD_ReplicationStrategy m_rplStrategy = null;

	public ExportHelper(final MClient client, final MReplicationStrategy rplStrategy)
	{
		m_AD_Client_ID = client.getAD_Client_ID();
		m_rplStrategy = rplStrategy;
	}

	public ExportHelper(final Properties ctx, @NonNull final ClientId AD_Client_ID)
	{
		m_AD_Client_ID = AD_Client_ID.getRepoId();
		m_rplStrategy = Services.get(IClientDAO.class)
				.getById(Env.getClientId(ctx))
				.getAD_ReplicationStrategy();
	}

	/**
	 * Process - Generate Export Format
	 */
	public String exportRecord(final PO po, final Integer ReplicationMode, final String ReplicationType, final Integer ReplicationEvent) throws ReplicationException
	{
		// metas: tsa: refactored
		final MEXPFormat exportFormat = null;
		return exportRecord(po, exportFormat, ReplicationMode, ReplicationType, ReplicationEvent);
	}

	public void exportRecord(
			final PO po,
			final MEXPFormat exportFormat,
			final Integer replicationMode,
			final String replicationType,
			final Integer replicationEvent,
			@Nullable final CreateAttachmentRequest attachResultRequest)
	{
		exportRecord(po, exportFormat, replicationMode, replicationType, replicationEvent);

		if (attachResultRequest != null)
		{
			createAttachment(attachResultRequest);
		}
	}

	public String exportRecord(final PO po, final MEXPFormat exportFormat, final Integer ReplicationMode, final String ReplicationType, final Integer ReplicationEvent) throws ReplicationException
	{
		outDocument = createExportDOM(po, exportFormat, ReplicationMode, ReplicationType, ReplicationEvent);

		final MEXPProcessor mExportProcessor = MEXPProcessor.get(po.getCtx(), m_rplStrategy.getEXP_Processor_ID(), po.get_TrxName());
		log.debug("ExportProcessor = " + mExportProcessor);

		final IExportProcessor exportProcessor = mExportProcessor.getIExportProcessor();
		final IExportProcessor2 exportProcessor2;
		if (exportProcessor instanceof IExportProcessor2)
		{

			exportProcessor2 = (IExportProcessor2)exportProcessor;
		}
		else
		{
			exportProcessor2 = new ExportProcessor2Wrapper(exportProcessor);
		}
		exportProcessor2.process(mExportProcessor, outDocument, po);

		return outDocument == null ? null : outDocument.toString();
	}

	/**
	 * Method creates the XML DOM tree, but doesn't call any export processor.
	 * <p>
	 * Note that this method doesn't alter any of this classe's member variables.
	 */
	// t.schoeneberg@metas.de, 03132
	// extracted the DOM creating code from 'exportRecord()'
	public Document createExportDOM(
			final PO po,
			MEXPFormat exportFormat,
			final Integer ReplicationMode,
			String ReplicationType,
			final Integer ReplicationEvent)
	{
		// metas: tsa: begin
		if (exportFormat == null)
		{
			exportFormat = getEXP_Format(po);
		}
		// metas: tsa: end
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(po.getCtx(), m_AD_Client_ID);
		log.debug("Client = " + client.toString());

		log.debug("po.getAD_Org_ID() = " + po.getAD_Org_ID());

		log.debug("po.get_TrxName() = " + po.get_TrxName());
		if (po.get_TrxName() == null || po.get_TrxName().equals(""))
		{
			po.set_TrxName("exportRecord");
		}

		log.debug("Table = " + po.get_TableName());

		if (po.get_KeyColumns().length < 1)
		{
			throw new ReplicationException(ImportHelper.MSG_EDIMultiColumnNotSupported)
					.setParameter(I_AD_Table.COLUMNNAME_TableName, po.get_TableName());
		}

		// metas: tsa: validate if this PO respects Export Format's WhereClause
		if (!Check.isEmpty(exportFormat.getWhereClause()))
		{
			final String whereClause = "(" + po.get_WhereClause(true) + ") AND (" + exportFormat.getWhereClause() + ")";
			final boolean match = new Query(po.getCtx(), po.get_TableName(), whereClause, po.get_TrxName())
					.anyMatch();
			if (!match)
			{
				log.info("Object " + po + " does not match export format where clause (" + whereClause + ")");
				return null;
			}
		}

		// metas: tsa: begin: if replicationType is not specified, get it now
		if (Check.isEmpty(ReplicationType))
		{
			final I_AD_ReplicationTable replicationTable = MReplicationStrategy.getReplicationTable(po.getCtx(), m_rplStrategy.getAD_ReplicationStrategy_ID(), po.get_Table_ID());
			if (replicationTable != null)
			{
				ReplicationType = replicationTable.getReplicationType();
			}
			else
			{
				ReplicationType = X_AD_ReplicationTable.REPLICATIONTYPE_Merge;
				log.warn("No replication table settings found for " + po + ". Using default: " + ReplicationType);
			}
		}
		// metas: tsa: end

		final Document outDocument = createNewDocument();

		final HashMap<String, Integer> variableMap = new HashMap<>();
		final Element rootElement = generateRootElement(exportFormat, outDocument, ReplicationMode, ReplicationType, ReplicationEvent, client);

		final IReplicationAccessContext racCtx = getDefaultIReplicationAccessContext();
		generateExportFormat(outDocument, rootElement, exportFormat, po, variableMap, racCtx);

		return outDocument;
	}

	/**
	 * Export a limited number of POs for the given format and where clause.
	 */
	public Document exportRecord(final MEXPFormat exportFormat,
			final String where,
			final Integer ReplicationMode,
			final String ReplicationType,
			final Integer ReplicationEvent,
			final IReplicationAccessContext racCtx)
			throws Exception
	{
		final I_AD_Client client = Services.get(IClientDAO.class).retriveClient(exportFormat.getCtx(), m_AD_Client_ID);
		final String tableName = Services.get(IADTableDAO.class).retrieveTableName(exportFormat.getAD_Table_ID());

		// metas: begin: build where clause
		final StringBuffer whereClause = new StringBuffer("1=1");
		if (!Check.isEmpty(exportFormat.getWhereClause(), true))
		{
			whereClause.append(" AND (").append(exportFormat.getWhereClause()).append(")");
		}
		if (!Check.isEmpty(where))
		{
			whereClause.append(" AND (").append(where).append(")");
		}
		// metas: end

		final Collection<PO> records = new Query(exportFormat.getCtx(), tableName, whereClause.toString(), exportFormat.get_TrxName())
				.setOnlyActiveRecords(true)
				.setRequiredAccess(racCtx.isApplyAccessFilter() ? Access.READ : null)
				.setLimit(racCtx.getLimit())
				.list(PO.class);

		for (final PO po : records)
		{
			log.debug("Client = " + client.toString());
			log.trace("po.getAD_Org_ID() = " + po.getAD_Org_ID());
			log.trace("po.get_TrxName() = " + po.get_TrxName());
			if (po.get_TrxName() == null || po.get_TrxName().equals(""))
			{
				po.set_TrxName("exportRecord");
			}

			if (po.get_KeyColumns().length < 1)
			{
				throw new Exception(Services.get(IMsgBL.class).getMsg(po.getCtx(), "ExportNoneColumnKeyNotSupported")); // TODO: Create Mesagge.
			}

			outDocument = createNewDocument();

			final HashMap<String, Integer> variableMap = new HashMap<>();
			final Element rootElement = generateRootElement(exportFormat, outDocument, ReplicationMode, ReplicationType, ReplicationEvent, client);

			generateExportFormat(outDocument, rootElement, exportFormat, po, variableMap, racCtx);
		} // finish record read
		return outDocument;
	}

	private Element generateRootElement(final org.compiere.model.I_EXP_Format exportFormat, final Document outDocument, final Integer ReplicationMode, final String ReplicationType,
			final Integer ReplicationEvent, final I_AD_Client client)
	{
		final Element rootElement = outDocument.createElement(exportFormat.getValue());
		if (exportFormat.getDescription() != null && !"".equals(exportFormat.getDescription()))
		{
			rootElement.appendChild(outDocument.createComment(exportFormat.getDescription()));
		}
		rootElement.setAttribute(RPL_Constants.XML_ATTR_AD_Client_Value, client.getValue());
		rootElement.setAttribute(RPL_Constants.XML_ATTR_Version, exportFormat.getVersion());
		rootElement.setAttribute(RPL_Constants.XML_ATTR_REPLICATION_MODE, ReplicationMode.toString());
		rootElement.setAttribute(RPL_Constants.XML_ATTR_REPLICATION_TYPE, ReplicationType);
		rootElement.setAttribute(RPL_Constants.XML_ATTR_REPLICATION_EVENT, ReplicationEvent.toString());
		if (exportFormat.getAD_Sequence_ID() > 0)
		{
			final String sequenceName = exportFormat.getAD_Sequence().getName();
			final int nextID = DB.getNextID(m_AD_Client_ID, sequenceName, null);
			if (nextID <= 0)
			{
				throw new AdempiereException("Could not retrieve nextID for sequence " + sequenceName);
			}
			rootElement.setAttribute(RPL_Constants.XML_ATTR_SEQUENCE_NO, Integer.toString(nextID));
		}
		rootElement.setAttribute(RPL_Constants.XML_ATTR_REPLICATION_TrxName, null);
		outDocument.appendChild(rootElement);

		return rootElement;
	}

	/*
	 * Trifon Generate Export Format process; RESULT = <C_Invoice> <DocumentNo>101</DocumentNo> </C_Invoice>
	 */
	private void generateExportFormat(final Document outDocument, final Element rootElement, final MEXPFormat exportFormat, final PO masterPO, final HashMap<String, Integer> variableMap,
			final IReplicationAccessContext racCtx)
	{
		final List<I_EXP_FormatLine> formatLines = exportFormat.getFormatLines();

		for (final I_EXP_FormatLine formatLine : formatLines)
		{
			log.debug("Format Line Seach key: {}", formatLine.getValue());

			try
			{
				generateExportFormatLine(outDocument, rootElement, exportFormat, formatLine, masterPO, variableMap, racCtx);
			}
			catch (final Exception e)
			{
				throw new ExportProcessorException(MSG_EXPFormatLineError, e)
						.setParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_EXP_Format_ID, exportFormat)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
						.setParameter("masterPO", masterPO);
			}
		}
	}

	private void generateExportFormatLine(
			final Document outDocument,
			final Element rootElement,
			final MEXPFormat exportFormat,
			final I_EXP_FormatLine formatLine,
			final PO masterPO,
			final HashMap<String, Integer> variableMap,
			final IReplicationAccessContext racCtx)
	{
		final String formatLineType = formatLine.getType();
		if (X_EXP_FormatLine.TYPE_XMLElement.equals(formatLineType))
		{
			// process single XML Attribute
			final MColumn column = retrieveColumn(formatLine);
			final Object value = masterPO.get_Value(column.getColumnName());

			final String valueString;
			try
			{
				valueString = encodeValue(value, formatLine, column);
			}
			catch (final Exception e)
			{
				throw new ReplicationException("Encoding failed at line " + formatLine.getName(), e)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_AD_Column_ID, column);
			}

			final Element newElement = outDocument.createElement(formatLine.getValue());

			if (valueString != null)
			{
				final Text newText = outDocument.createTextNode(valueString);
				newElement.appendChild(newText);
				rootElement.appendChild(newElement);
			}
			// Empty field - if format line is Mandatory
			else if (formatLine.isMandatory())
			{
				final Text newText = outDocument.createTextNode("");
				newElement.appendChild(newText);
				rootElement.appendChild(newElement);
			}
		}
		else if (X_EXP_FormatLine.TYPE_XMLAttribute.equals(formatLineType))
		{
			// process single XML Attribute
			final MColumn column = retrieveColumn(formatLine);
			final Object value = masterPO.get_Value(column.getColumnName());
			final String valueString;
			try
			{
				valueString = encodeValue(value, formatLine, column); // attributes=null
			}
			catch (final Exception e)
			{
				throw new ReplicationException("Encoding failed at line " + formatLine.getName(), e)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_AD_Column_ID, column);
			}
			if (valueString == null && formatLine.isMandatory())
			{
				throw new ExportProcessorException(MSG_EXPFieldMandatory)
						.setParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_EXP_Format_ID, exportFormat)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_AD_Column_ID, formatLine.getAD_Column_ID())
						.setParameter("masterPO", masterPO);
			}

			if (valueString != null)
			{
				rootElement.setAttribute(formatLine.getValue(), valueString);
			}
		}
		else if (X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(formatLineType))
		{
			// process Embedded Export Format

			final int embeddedFormat_ID = formatLine.getEXP_EmbeddedFormat_ID();
			// get from cache
			final MEXPFormat embeddedFormat = MEXPFormat.get(masterPO.getCtx(), embeddedFormat_ID, masterPO.get_TrxName());

			final MTable tableEmbedded = MTable.get(masterPO.getCtx(), embeddedFormat.getAD_Table_ID());
			log.debug("Table Embedded = " + tableEmbedded);

			final String linkColumnName = getLinkColumnName(masterPO, tableEmbedded); // metas
			final Object linkId = masterPO.get_Value(linkColumnName); // metas
			final StringBuffer whereClause = new StringBuffer(linkColumnName + "=?"); // metas: use linkColumnName

			if (embeddedFormat.getWhereClause() != null && !"".equals(embeddedFormat.getWhereClause()))
			{
				whereClause.append(" AND ").append(embeddedFormat.getWhereClause());
			}

			final Query query = new Query(masterPO.getCtx(), tableEmbedded.getTableName(), whereClause.toString(), masterPO.get_TrxName());

			final boolean hasIsActiveColumn = Services.get(IADTableDAO.class).hasColumnName(tableEmbedded.getTableName(), "IsActive");
			if (hasIsActiveColumn)
			{
				// not exporting inactive records, if the current format's table allow us to check (sometimes not the case for simple views);
				// hypothetically we might want to export them too, but that case didn't yet occur and i don't really see it. However, the other way round (i.e. *not* exporting inactive records) is
				// all over.
				query.setOnlyActiveRecords(true);
			}
			final List<PO> instances = query
					.setRequiredAccess(racCtx.isApplyAccessFilter() ? Access.READ : null)
					.setParameters(linkId)
					.setLimit(racCtx.getLimit())
					.list(PO.class);

			for (final PO instance : instances)
			{
				final Element embeddedElement = outDocument.createElement(formatLine.getValue());
				if (formatLine.getDescription() != null && !"".equals(formatLine.getDescription()))
				{
					embeddedElement.appendChild(outDocument.createComment(formatLine.getDescription()));
				}

				generateExportFormat(outDocument, embeddedElement, embeddedFormat, instance, variableMap, racCtx);
				rootElement.appendChild(embeddedElement);
			}
		}
		else if (X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(formatLineType))
		{
			// process Referenced Export Format

			final int embeddedFormat_ID = formatLine.getEXP_EmbeddedFormat_ID();
			if (embeddedFormat_ID <= 0)
			{
				throw new ReplicationException(MSG_EXPFormatNotFound)
						.setParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_EXP_Format_ID, exportFormat)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_Type, formatLineType)
						.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_EmbeddedFormat_ID, embeddedFormat_ID) // not found
						;
			}
			// get from cache
			final MEXPFormat embeddedFormat = MEXPFormat.get(masterPO.getCtx(), embeddedFormat_ID, masterPO.get_TrxName());

			final MColumn column = retrieveColumn(formatLine);

			final int displayType = getDisplayType(column, formatLine);
			final MTable embeddedTable;
			final String embeddedTableName;
			final String embeddedKeyColumnName;
			if (displayType == DisplayType.Table
					|| displayType == DisplayType.Search && column.getAD_Reference_Value_ID() > 0)
			{
				final int referenceId = column.getAD_Reference_Value_ID();
				if (referenceId <= 0)
				{
					// Check.assume(referenceId > 0, "AD_Reference_Value_ID > 0 for column {} (table {})", column, column.getAD_Table().getTableName());
					final String columnName = column.getColumnName();
					final String tableName = Services.get(IADTableDAO.class).retrieveTableName(column.getAD_Table_ID());
					throw new AdempiereException("AD_Reference_Value_ID > 0 for column " + tableName + "." + columnName);
				}

				final MRefTable refTable = MRefTable.get(masterPO.getCtx(), referenceId);
				final MColumn embeddedKeyColumn = MColumn.get(masterPO.getCtx(), refTable.getAD_Key());

				embeddedTable = MTable.get(masterPO.getCtx(), refTable.getAD_Table_ID());
				embeddedTableName = embeddedTable.getTableName();
				embeddedKeyColumnName = embeddedKeyColumn.getColumnName();
			}
			else if (displayType == DisplayType.TableDir
					|| displayType == DisplayType.Search && column.getAD_Reference_Value_ID() <= 0)
			{
				embeddedTable = MTable.get(masterPO.getCtx(), embeddedFormat.getAD_Table_ID());
				final String[] embeddedKeyColumns = embeddedTable.getKeyColumns();
				if (embeddedKeyColumns == null || embeddedKeyColumns.length != 1)
				{
					throw new ReplicationException("Embedded table shall have one and only one primary key")
							.setParameter("AD_Table_ID", embeddedTable);
				}
				embeddedTableName = embeddedTable.getTableName();
				embeddedKeyColumnName = embeddedKeyColumns[0];
			}
			else if (DisplayType.isLookup(displayType, true)) // includeHardcodedLookups=true
			{
				embeddedTableName = DisplayType.getTableName(displayType);
				Check.assumeNotNull(embeddedTableName, "TableName found for DisplayType={}", displayType);

				embeddedTable = MTable.get(masterPO.getCtx(), embeddedTableName);
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

			log.debug("Embedded: Table={}, KeyColumName={}", new Object[] { embeddedTableName, embeddedKeyColumnName });

			final StringBuilder whereClause = new StringBuilder().append(embeddedKeyColumnName).append("=?");
			if (!Check.isEmpty(embeddedFormat.getWhereClause()))
			{
				whereClause.append(" AND ").append(embeddedFormat.getWhereClause());
			}

			final Object value = masterPO.get_Value(column.getColumnName());
			if (value == null)
			{
				return;
			}

			final Query query = new Query(masterPO.getCtx(), embeddedTableName, whereClause.toString(), masterPO.get_TrxName());

			final boolean hasIsActiveColumn = Services.get(IADTableDAO.class).hasColumnName(embeddedTableName, "IsActive");
			if (hasIsActiveColumn)
			{
				// not exporting inactive records, if the current format's table allow us to check (sometimes not the case for simple views);
				// hypothetically we might want to export them too, but that case didn't yet occur and i don't really see it. However, the other way round (i.e. *not* exporting inactive records) is
				// all over.
				query.setOnlyActiveRecords(true);
			}

			final List<PO> instances = query
					.setRequiredAccess(racCtx.isApplyAccessFilter() ? Access.READ : null)
					.setParameters(value)
					.list(PO.class);

			for (final PO instance : instances)
			{
				final Element embeddedElement = outDocument.createElement(formatLine.getValue());
				if (formatLine.getDescription() != null && !"".equals(formatLine.getDescription()))
				{
					embeddedElement.appendChild(outDocument.createComment(formatLine.getDescription()));
				}

				generateExportFormat(outDocument, embeddedElement, embeddedFormat, instance, variableMap, racCtx);
				rootElement.appendChild(embeddedElement);
			}
		}
		else
		{
			throw new ReplicationException(ImportHelper.MSG_EXPFormatLineNonValidType)
					.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine.getValue());
		}
	}

	/**
	 * Utility method which is responsible to create new XML Document
	 *
	 * @return Document
	 * @throws ParserConfigurationException
	 */
	Document createNewDocument()
	{
		Document result = null;
		final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder documentBuilder;
		try
		{
			documentBuilder = documentBuilderFactory.newDocumentBuilder();
		}
		catch (final ParserConfigurationException e)
		{
			// internal error, shall not happen
			throw new AdempiereException(e);
		}

		result = documentBuilder.newDocument();
		return result;
	}

	// metas: begin
	private String getLinkColumnName(final PO masterPO, final MTable tableEmbedded)
	{
		final String[] keyColumns = masterPO.get_KeyColumns();
		if (keyColumns == null || keyColumns.length == 0)
		{
			throw new AdempiereException("No key columns found for " + masterPO);
		}
		if (keyColumns.length == 1)
		{
			return keyColumns[0];
		}

		final Properties ctx = masterPO.getCtx();
		final String masterTableName = masterPO.get_TableName();
		final IAppDictionaryBL appDictBL = Services.get(IAppDictionaryBL.class);
		for (final String keyColumn : keyColumns)
		{
			final MTable table = appDictBL.getReferencedTable(ctx, masterTableName, keyColumn);
			if (table != null && table.getAD_Table_ID() == tableEmbedded.getAD_Table_ID())
			{
				return keyColumn;
			}
		}

		throw new AdempiereException("No relevant link column found on " + masterPO + " for " + tableEmbedded);
	}

	public I_AD_ReplicationStrategy getAD_ReplicationStrategy()
	{
		return m_rplStrategy;
	}

	public MEXPFormat getEXP_Format(final PO po)
	{
		// String version = "3.2.0";
		final String version = null;
		MEXPFormat exportFormat = null;

		final I_AD_ReplicationStrategy rplStrategy = getAD_ReplicationStrategy();
		final I_AD_ReplicationTable replTable = MReplicationStrategy.getReplicationTable(po.getCtx(), rplStrategy.getAD_ReplicationStrategy_ID(), po.get_Table_ID());
		if (replTable != null && replTable.getEXP_Format_ID() > 0)
		{
			exportFormat = MEXPFormat.get(po.getCtx(), replTable.getEXP_Format_ID(), po.get_TrxName());
			log.debug("ExportFormat(replication table): ", exportFormat);
			return exportFormat;
		}

		if (exportFormat == null)
		{
			exportFormat = MEXPFormat.getFormatByAD_Client_IDAD_Table_IDAndVersion(po.getCtx(), m_AD_Client_ID, po.get_Table_ID(), version, po.get_TrxName());
			log.debug("ExportFormat(client): ", exportFormat);
		}

		// Fall back to System Client
		if (exportFormat == null || exportFormat.getEXP_Format_ID() <= 0)
		{
			final int adClientId = 0; // System
			exportFormat = MEXPFormat.getFormatByAD_Client_IDAD_Table_IDAndVersion(po.getCtx(), adClientId, po.get_Table_ID(), version, po.get_TrxName());
			log.debug("ExportFormat(system): ", exportFormat);
		}

		if (exportFormat == null || exportFormat.getEXP_Format_ID() <= 0)
		{
			throw new ExportProcessorException(MSG_EXPFormatNotFound)
					.setParameter(I_AD_Table.COLUMNNAME_TableName, po.get_TableName())
					.setParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_AD_Client_ID, m_AD_Client_ID)
					.setParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_Version, version)
					.setParameter(I_AD_ReplicationTable.Table_Name, replTable);
		}

		return exportFormat;
	}

	/**
	 * Encode given value using column and formatLine settings
	 *
	 * @return String encoded value
	 */
	private static String encodeValue(final Object value,
			@NonNull final I_EXP_FormatLine formatLine,
			@NonNull final I_AD_Column column)
	{
		final int displayType = column.getAD_Reference_ID();

		final String valueString;
		if (value == null)
		{
			valueString = null;
		}
		else if (DisplayType.isLOB(displayType))
		{
			final byte[] data;
			if (value instanceof String)
			{
				data = ((String)value).getBytes(StandardCharsets.UTF_8);
			}
			else
			{
				data = (byte[])value;
			}

			valueString = Util.encodeBase64(data);
		}
		else if (DisplayType.isDate(displayType))
		{
			valueString = encodeDate((Timestamp)value, formatLine, displayType);
		}
		else if (DisplayType.isYesNo(displayType))
		{
			valueString = StringUtils.ofBoolean((Boolean)value, "N");
		}
		else
		{
			final String str = value.toString();
			valueString = str.isEmpty() ? null : str;
		}

		log.debug("Encoded column '{}' from '{}' to '{}'", new Object[] { column.getColumnName(), value, valueString });
		return valueString;
	}

	@NonNull
	@VisibleForTesting
	static String encodeDate(
			final Timestamp date,
			@NonNull final I_EXP_FormatLine formatLine,
			final int displayType)
	{
		final ZoneId timeZoneId = Services.get(IOrgDAO.class).getTimeZone(Env.getOrgId());
		final SimpleDateFormat df = getXsdFormatForDateType(displayType);
		df.setTimeZone(TimeZone.getTimeZone(timeZoneId));

		final String result = df.format(date);
		log.debug("date={}; DatePattern={}; ZoneId={}; -> result={}", date, df.toPattern(), timeZoneId, result);

		return result;
	}

	private static SimpleDateFormat getXsdFormatForDateType(final int displayType)
	{
		final String format;
		if (displayType == DisplayType.Date)
		{
			format = "yyyy-MM-ddXXX";
		}
		else if (displayType == DisplayType.Time)
		{
			format = "HH:mm:ssXXX";
		}
		else if (displayType == DisplayType.DateTime)
		{
			format = "yyyy-MM-dd'T'HH:mm:ssXXX";
		}
		else
		{
			throw new AdempiereException("Unexpected displayType=" + displayType + "; expected types are Date, Time and DateTime");
		}

		return new SimpleDateFormat(format);
	}

	private MColumn retrieveColumn(final I_EXP_FormatLine formatLine)
	{
		final int adColumnId = formatLine.getAD_Column_ID();
		if (adColumnId <= 0)
		{
			throw new ExportProcessorException(MSG_EXPColumnMandatory)
					.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine);
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(formatLine);
		final MColumn column = MColumn.get(ctx, adColumnId);
		if (column == null)
		{
			throw new ExportProcessorException(MSG_EXPColumnMandatory)
					.setParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine)
					.setParameter(I_EXP_FormatLine.COLUMNNAME_AD_Column_ID, adColumnId);
		}

		return column;
	}

	// NOTE: commented @Cached out because is no longer applied anyways (not a service)
	// @Cached
	public IReplicationAccessContext getDefaultIReplicationAccessContext()
	{
		final int limit = IQuery.NO_LIMIT;
		final boolean isApplyAccessFilter = false;
		return new ReplicationAccessContext(limit, isApplyAccessFilter); // TODO hardcoded
	}
	// metas: end

	private static int getDisplayType(@NonNull final MColumn column, @NonNull final I_EXP_FormatLine formatLine)
	{
		if (formatLine.getAD_Reference_Override_ID() > 0)
		{
			return formatLine.getAD_Reference_Override_ID();
		}

		return column.getAD_Reference_ID();
	}

	private void createAttachment(@NonNull final CreateAttachmentRequest request)
	{
		try
		{
			final String documentAsString = writeDocumentToString(outDocument);

			final byte[] data = documentAsString.getBytes();
			final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);
			attachmentEntryService.createNewAttachment(request.getTarget(), request.getAttachmentName(), data);
		}
		catch (final Exception exception)
		{
			throw AdempiereException.wrapIfNeeded(exception);
		}
	}

	private static String writeDocumentToString(@NonNull final Document document) throws TransformerException
	{
		final TransformerFactory tranFactory = TransformerFactory.newInstance();
		final Transformer aTransformer = tranFactory.newTransformer();
		aTransformer.setOutputProperty(OutputKeys.INDENT, "yes");
		final Source src = new DOMSource(document);
		final Writer writer = new StringWriter();
		final Result dest2 = new StreamResult(writer);
		aTransformer.transform(src, dest2);

		return writer.toString();
	}
}
