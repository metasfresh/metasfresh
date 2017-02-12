/**********************************************************************
 * This file is part of Adempiere ERP Bazaar                          *
 * http://www.adempiere.org                                           *
 *                                                                    *
 * Copyright (C) Trifon Trifonov.                                     *
 * Copyright (C) Contributors                                         *
 *                                                                    *
 * This program is free software; you can redistribute it and/or      *
 * modify it under the terms of the GNU General Public License        *
 * as published by the Free Software Foundation; either version 2     *
 * of the License, or (at your option) any later version.             *
 *                                                                    *
 * This program is distributed in the hope that it will be useful,    *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of     *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the       *
 * GNU General Public License for more details.                       *
 *                                                                    *
 * You should have received a copy of the GNU General Public License  *
 * along with this program; if not, write to the Free Software        *
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,         *
 * MA 02110-1301, USA.                                                *
 *                                                                    *
 * Contributors:                                                      *
 *  - Trifon Trifonov (trifonnt@users.sourceforge.net)                *
 *                                                                    *
 * Sponsors:                                                          *
 *  - E-evolution (http://www.e-evolution.com/)                       *
 *********************************************************************/
package org.adempiere.server.rpl.api.impl;

import static org.adempiere.server.rpl.api.impl.ReplicationHelper.setReplicationCtx;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;
import java.util.regex.Pattern;

import javax.xml.bind.DatatypeConverter;
import javax.xml.xpath.XPathExpressionException;

import org.adempiere.ad.persistence.TableModelLoader;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.process.rpl.RPL_Constants;
import org.adempiere.process.rpl.XMLHelper;
import org.adempiere.process.rpl.exp.ExportHelper;
import org.adempiere.process.rpl.model.I_EXP_ReplicationTrxLine;
import org.adempiere.process.rpl.model.X_EXP_ReplicationTrxLine;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerBL;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerCtx;
import org.adempiere.process.rpl.requesthandler.api.IReplRequestHandlerResult;
import org.adempiere.process.rpl.requesthandler.model.I_IMP_RequestHandler;
import org.adempiere.process.rpl.requesthandler.spi.IReplRequestHandler;
import org.adempiere.server.rpl.api.IIMPProcessorBL;
import org.adempiere.server.rpl.api.IIMPProcessorBL.ITableAndColumn;
import org.adempiere.server.rpl.api.IImportHelper;
import org.adempiere.server.rpl.exceptions.DuplicateLookupObjectException;
import org.adempiere.server.rpl.exceptions.ReplicationException;
import org.adempiere.server.rpl.interfaces.I_EXP_Format;
import org.adempiere.server.rpl.trx.api.IReplicationTrxBL;
import org.adempiere.server.rpl.trx.api.IReplicationTrxDAO;
import org.adempiere.server.rpl.trx.api.impl.POReplicationTrxLineDraft;
import org.adempiere.server.rpl.trx.spi.IReplicationIssueAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Attribute_Value;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_Column;
import org.compiere.model.I_AD_Element;
import org.compiere.model.I_AD_ReplicationDocument;
import org.compiere.model.I_AD_ReplicationTable;
import org.compiere.model.I_AD_Session;
import org.compiere.model.I_AD_Table;
import org.compiere.model.I_EXP_FormatLine;
import org.compiere.model.MColumn;
import org.compiere.model.MEXPFormat;
import org.compiere.model.MRefTable;
import org.compiere.model.MReplicationStrategy;
import org.compiere.model.MSession;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.model.X_AD_Reference;
import org.compiere.model.X_AD_ReplicationDocument;
import org.compiere.model.X_AD_ReplicationTable;
import org.compiere.model.X_EXP_FormatLine;
import org.compiere.process.DocAction;
import org.compiere.util.DisplayType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.google.common.base.Optional;

import de.metas.adempiere.service.IColumnBL;
import de.metas.logging.LogManager;
import de.metas.monitoring.api.IMeter;
import de.metas.monitoring.api.IMonitoringBL;

/**
 * Default XML importer
 *
 * @author Trifon N. Trifonov
 * @author Antonio Cañaveral, e-Evolution <li>[ 2195016 ] Implementation delete records messages <li>http://sourceforge.net/tracker/index.php?func=detail&aid=2195016&group_id=176962&atid=879332
 * @author victor.perez@e-evolution.com, e-Evolution <li>[ 2195090 ] Stabilization of replication <li>https://sourceforge.net/tracker/?func=detail&atid=879332&aid=2936561&group_id=176962 <li>BF
 *         [2947622] The replication ID (Primary Key) is not working <li>https://sourceforge.net/tracker/?func=detail&aid=2947622&group_id=176962&atid=879332
 *
 */
public class ImportHelper implements IImportHelper
{
	private static final String MSG_XMLClientValueMandatory = "XMLClientValueMandatory";
	private static final String MSG_XMLVersionAttributeMandatory = "XMLVersionAttributeMandatory";
	private static final String MSG_XMLClientNotFound = "XMLClientNotFound";
	private static final String MSG_EXPFormatNotFound = "EXPFormatNotFound";
	private static final String MSG_EXPReplicationTypeNonValidType = "EXPReplicationTypeNonValidType";
	private static final String MSG_PONotFound = "PONotFound";
	public static final String MSG_EDIMultiColumnNotSupported = "EDIMultiColumnNotSupported";
	private static final String MSG_EXPFormatNoLines = "EXPFormatNoLines";
	public static final String MSG_EXPFormatLineNonValidType = "EXPFormatLineNonValidType";
	private static final String MSG_EXPFormatLineNoUniqueColumns = "EXPFormatLineNoUniqueColumns";
	private static final String MSG_EXPValueFormatNotResolved = "EXPFormatNotResolved";
	private static final String MSG_EXPObjectFormatNotResolved = "EXPObjectFormatNotResolved";
	private static final String MSG_EXPFormatLineDuplicatedObject = "FormatLineDuplicatedObject";
	private static final String MSG_EXPFormatLineDuplicatedDefaultObject = "FormatLineDuplicatedDefaultObject";
	private static final String MSG_EXPFormatLineNoDefaultFallbackObject = "FormatLineNoDefaultFallbackObject";

	private static final String MSG_CantImport = "CantImport";
	private static final String MSG_CantExport = "CantExport";
	private static final String MSG_CantProcessDoc = "CantProcessDoc";
	private static final String MSG_CantGetUniqueFormatLine = "CantGetUniqueFormatLine";
	private static final String MSG_CantGetRefferenceNode = "CantGetRefferenceNode";
	private static final String MSG_CantGetRecordID = "CantGetRecordID";
	private static final String MSG_CantGetNodeList = "CantGetNodeList";
	private static final String MSG_ParseException = "ParseException";

	/**
	 * Currently not used, but might be again in future.
	 */
	@SuppressWarnings("unused")
	private static final String MSG_InternalError = "InternalError";

	private static final String MSG_CantSetColumnValue = "CantSetColumnValue";
	private static final String MSG_InvalidArguments = "InvalidArguments";

	/** Instance Logger */
	private final Logger log = LogManager.getLogger(ImportHelper.class);

	/** Static Logger */
	private static Logger s_log = LogManager.getLogger(ImportHelper.class);

	/** Set change PO */
	boolean isChanged = false;

	// private int adClientId = -1;

	/** Initial Context */
	private Properties _initialCtx = null;
	/** Context */
	private Properties ctx = null;

	private final IIMPProcessorBL importProcessorBL;

	public ImportHelper()
	{
		super();

		importProcessorBL = Services.get(IIMPProcessorBL.class);
	}

	@Override
	public void setInitialCtx(final Properties initialCtx)
	{
		if (ctx != null)
		{
			throw new IllegalStateException("Cannot set initialCtx when context was already created");
		}

		Check.assume(initialCtx != null, "initialCtx is not null");
		_initialCtx = initialCtx;
	}

	@Override
	public Document importXMLDocument(
			final StringBuilder result,
			final Document documentToBeImported,
			final String trxName)
	{
		final Document[] retValue = new Document[] { null };
		Services.get(ITrxManager.class).run(trxName, new TrxRunnable()
		{
			@Override
			public void run(final String localTrxName) throws Exception
			{
				retValue[0] = importXMLDocument0(result, documentToBeImported, localTrxName);
			}
		});

		return retValue[0];
	}

	private Document importXMLDocument0(
			final StringBuilder result,
			final Document documentToBeImported,
			final String trxName)
	{

		final Element rootElement = documentToBeImported.getDocumentElement();

		ctx = createContext(rootElement);

		// Find which Export format to Load...
		final String version = rootElement.getAttribute(RPL_Constants.XML_ATTR_Version);
		log.info("Version = {}", version);
		if (Check.isEmpty(version, true))
		{
			throw new ReplicationException(MSG_XMLVersionAttributeMandatory);
		}

		// /Getting Attributes.
		final int ReplicationMode = getAttributeAsInt(rootElement, RPL_Constants.XML_ATTR_REPLICATION_MODE);
		final String ReplicationType = rootElement.getAttribute(RPL_Constants.XML_ATTR_REPLICATION_TYPE);
		final int ReplicationEvent = getAttributeAsInt(rootElement, RPL_Constants.XML_ATTR_REPLICATION_EVENT);

		String EXP_Format_Value = null;
		EXP_Format_Value = rootElement.getNodeName();
		log.info("EXP_Format_Value = {}", EXP_Format_Value);

		final int adClientId = Env.getAD_Client_ID(ctx);
		MEXPFormat expFormatPO = null;
		try
		{
			// Note: calling the method with trxName==NONE because we want to get the cached version
			expFormatPO = MEXPFormat.getFormatByValueAD_Client_IDAndVersion(ctx, EXP_Format_Value, adClientId, version, ITrx.TRXNAME_None);
		}
		catch (final AdempiereException e)
		{
			throw new ReplicationException(MSG_EXPValueFormatNotResolved, e)
					.addParameter(I_AD_Client.COLUMNNAME_AD_Client_ID, adClientId)
					.addParameter(I_AD_Client.COLUMNNAME_Value, EXP_Format_Value)
					.addParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_Version, version);
		}

		if (expFormatPO == null || expFormatPO.getEXP_Format_ID() <= 0)
		{
			throw new ReplicationException(MSG_EXPFormatNotFound)
					.addParameter(I_AD_Client.COLUMNNAME_AD_Client_ID, adClientId)
					.addParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_Version, version);
		}
		log.info("expFormat = " + expFormatPO.toString());

		final IMeter meter = Services.get(IMonitoringBL.class).createOrGet("org.adempiere.replication", ImportHelper.class.getSimpleName() + "_" + expFormatPO.getName());

		isChanged = false;

		//
		// Get ReplicationTrxName from the root element,
		// OR generate random UUID if ReplicationTrxName is empty
		final String ReplicationTrxName;
		{
			final String attrReplicationTrxName = rootElement.getAttribute(RPL_Constants.XML_ATTR_REPLICATION_TrxName);
			if (!Check.isEmpty(attrReplicationTrxName))
			{
				ReplicationTrxName = attrReplicationTrxName;
			}
			else
			{
				ReplicationTrxName = UUID.randomUUID().toString();
			}
		}

		final PO po;
		try
		{
			po = importElement(result, rootElement, expFormatPO, ReplicationType, "",
					null, // masterPO
					null, // masterExpFormat
					null, // masterFormaLine
					ReplicationTrxName, trxName);
			meter.plusOne();

			// task 03132
			final I_EXP_Format format = InterfaceWrapperHelper.create(expFormatPO, I_EXP_Format.class);
			if (format.getIMP_RequestHandler_ID() > 0)
			{
				return handleRequestResonse(rootElement, format, po);
			}
		}
		catch (final Exception e)
		{
			throw new ReplicationException(MSG_CantImport, e)
					.addParameter(I_AD_Element.COLUMNNAME_AD_Element_ID, rootElement);
		}

		Check.assumeNotNull(po, "po not null");
		if (!po.is_Changed() && !isChanged)
		{
			log.info("Object not changed = " + po.toString());
			return null;
		}

		//
		// Decide actions on PO, depending on REPLICATION configuration
		if (MReplicationStrategy.REPLICATION_TABLE == ReplicationMode)
		{
			handleTableReplication(ReplicationType, ReplicationEvent, adClientId, po);
		}
		else if (MReplicationStrategy.REPLICATION_DOCUMENT == ReplicationMode
				&& X_AD_ReplicationDocument.REPLICATIONTYPE_Merge.equals(ReplicationType)
				&& po instanceof DocAction)
		{
			handleDocumentReplication(po);
		}

		result.append("Save Successful; ");
		return null;
	}

	private void handleTableReplication(final String ReplicationType, final int ReplicationEvent, final int adClientId, final PO po)
	{
		//
		// Here must invoke other method else we get cycle...
		if (ModelValidator.TYPE_BEFORE_DELETE == ReplicationEvent
				|| ModelValidator.TYPE_BEFORE_DELETE_REPLICATION == ReplicationEvent)
		{
			po.deleteEx(true);
		}
		else
		{
			if (X_AD_ReplicationTable.REPLICATIONTYPE_Broadcast.equals(ReplicationType))
			{
				final ExportHelper expHelper = new ExportHelper(ctx, adClientId);
				try
				{
					expHelper.exportRecord(po,
							MReplicationStrategy.REPLICATION_TABLE,
							X_AD_ReplicationTable.REPLICATIONTYPE_Merge,
							ModelValidator.TYPE_AFTER_CHANGE);
					po.saveExReplica(true);
				}
				catch (final Exception e)
				{
					throw new ReplicationException(MSG_CantExport, e)
							.addParameter(I_AD_ReplicationTable.COLUMNNAME_ReplicationType, X_AD_ReplicationTable.REPLICATIONTYPE_Broadcast)
							.addParameter("PO", po);
				}

			}
			else if (X_AD_ReplicationTable.REPLICATIONTYPE_Merge.equals(ReplicationType)
					|| X_AD_ReplicationTable.REPLICATIONTYPE_Reference.equals(ReplicationType))
			{

				try
				{
					po.saveExReplica(true);
				}
				catch (final AdempiereException e)
				{
					throw new ReplicationException(MSG_CantExport, e)
							.addParameter(I_AD_ReplicationTable.COLUMNNAME_ReplicationType, X_AD_ReplicationTable.REPLICATIONTYPE_Merge)
							.addParameter("PO", po);
				}
			}
			/*
			 * else if (X_AD_ReplicationTable.REPLICATIONTYPE_Reference.equals(ReplicationType)) { //Do nothing?? }
			 */
			else if (X_AD_ReplicationTable.REPLICATIONTYPE_Local.equals(ReplicationType))
			{
				// Do nothing??
			}
			else
			{
				// Replication Type is not one of the possible values...ERROR
				throw new ReplicationException(MSG_EXPReplicationTypeNonValidType)
						.addParameter(I_AD_ReplicationTable.COLUMNNAME_ReplicationType, ReplicationType);
			}
		}
	}

	private void handleDocumentReplication(final PO po)
	{
		Env.setContext(po.getCtx(), "#AD_Client_ID", po.getAD_Client_ID());
		final DocAction document = (DocAction)po;
		try
		{
			if (!document.processIt(document.getDocAction()))
			{
				log.info("PO.toString() = can not " + po.get_Value("DocAction"));
			}
		}
		catch (final Exception e)
		{
			throw new ReplicationException(MSG_CantProcessDoc, e)
					.addParameter(I_AD_ReplicationDocument.COLUMNNAME_C_DocType_ID, document);
		}

		InterfaceWrapperHelper.save(po);
	}

	/**
	 * check if 'expFormatPO' has a handler. if so, call it with 'po' and the format
	 */
	private Document handleRequestResonse(
			final Element rootElement,
			final I_EXP_Format format,
			final PO requestPO)
	{
		//
		// Make sure we have a valid AD_Session_ID (i.e. guard agaist security shits)
		// FIXME: we need to make an exception for AD_User_Login
		// if (!rootElement.hasAttribute(RPL_Constants.XML_ATTR_AD_SESSION_ID))
		// {
		// s_log.debug("Skip because XML attribute '{}' is missing", RPL_Constants.XML_ATTR_AD_SESSION_ID);
		// return null;
		// }

		final I_IMP_RequestHandler requestHandlerPO = format.getIMP_RequestHandler();
		if (requestHandlerPO == null)
		{
			s_log.debug("Skipping because format '{}' does not have a request handler set", format);
			return null;
		}
		if (!requestHandlerPO.isActive())
		{
			s_log.debug("Skipping because request handler '{}' is not active", requestHandlerPO);
			return null;
		}

		final IReplRequestHandlerBL requestHandlerBL = Services.get(IReplRequestHandlerBL.class);

		final IReplRequestHandler requestHandlerInstance = requestHandlerBL.getRequestHandlerInstance(requestHandlerPO);
		final IReplRequestHandlerCtx requestHandlerCtx = requestHandlerBL.createCtx(ctx, format, requestHandlerPO);
		final IReplRequestHandlerResult requestHandlerResult = requestHandlerInstance.handleRequest(requestPO, requestHandlerCtx);

		if (requestHandlerResult.getFormatToUse() == null)
		{
			// the implementation didn't select a format to use for export
			if (requestHandlerPO.getEXP_Format_ID() > 0)
			{
				// if the record has a format set, then use that
				final I_EXP_Format handlerSpecifiedFormat = InterfaceWrapperHelper.create(requestHandlerPO.getEXP_Format(), I_EXP_Format.class);
				requestHandlerResult.setFormatToUse(handlerSpecifiedFormat);
			}
			else
			{
				// else, use the incoming format
				requestHandlerResult.setFormatToUse(format);
			}
		}
		return requestHandlerBL.processRequestHandlerResult(requestHandlerResult);
	}

	/**
	 * @param ctx
	 * @param result
	 * @param rootElement
	 * @param expFormat
	 * @param ReplicationType
	 * @param masterPO
	 * @param ReplicationTrxName
	 * @param trxName
	 * @return
	 */
	private PO importElement(
			final StringBuilder result,
			final Element rootElement,
			final MEXPFormat expFormat,
			final String ReplicationType,
			final String parentXPath,
			final PO masterPO,
			final I_EXP_Format masterExpFormat,
			final I_EXP_FormatLine masterExpFormatLine,
			final String ReplicationTrxName,
			final String trxName)
	{
		boolean isLookupTableReplicationTrxSupported;
		PO po = null;
		I_EXP_ReplicationTrxLine replicationTrxLine = null;
		try
		{
			//
			// Getting the Object for the replicate
			final POReplicationTrxLineDraft draft = getObjectFromFormat(result,
					expFormat,
					rootElement, rootElement.getNodeName(),
					ReplicationType,
					parentXPath,
					masterPO,
					masterExpFormat,
					masterExpFormatLine,
					ReplicationTrxName,
					trxName);

			isLookupTableReplicationTrxSupported = isLookupTableReplicationTrxSupported(draft.isDoLookup(), masterExpFormat);
			po = draft.getPODraft();
			replicationTrxLine = draft.getTrxLineDraftOrNull();
		}
		catch (final DuplicateLookupObjectException dloe)
		{
			//
			// If replication transactions are not supported, then this import should fail
			isLookupTableReplicationTrxSupported = isLookupTableReplicationTrxSupported(dloe.isDoLookup(), masterExpFormat);
			if (!isLookupTableReplicationTrxSupported)
			{
				throw dloe;
			}

			//
			// Those POs might belong to a lookup table, i.e. might not not necessarily be POs of the table that our current po references
			final List<PO> lookedUpPOs = dloe.getLookedUpPOs();
			po = getSingleDefaultPO(lookedUpPOs, masterExpFormatLine);
			replicationTrxLine = dloe.getTrxLineDraftOrNull();
		}
		catch (final Exception e)
		{
			throw new ReplicationException(MSG_EXPObjectFormatNotResolved, e);
		}

		if (po == null)
		{
			throw new ReplicationException(MSG_PONotFound);
		}

		if (X_AD_ReplicationTable.REPLICATIONTYPE_Reference.equals(ReplicationType))
		{
			//
			// If this is just for push and exists, we do nothing
			if (po.get_ID() == 0)
			{
				return null;
			}
		}

		log.info("PO.toString() = " + po.toString());

		if (po.get_KeyColumns().length < 1)
		{
			throw new ReplicationException(MSG_EDIMultiColumnNotSupported)
					.addParameter(I_AD_Table.COLUMNNAME_TableName, po.get_TableName());
		}

		// Specify the line order
		// *embedded sub formats last; note that the postgres ordering for boolean values is false, true, null
		// *mandatory line before non-mandatory lines
		// *whatever is specified in the 'Position' column
		final String orderBy =
				I_EXP_FormatLine.COLUMNNAME_Type + "='" + X_EXP_FormatLine.TYPE_EmbeddedEXPFormat + "'," +
						I_EXP_FormatLine.COLUMNNAME_IsMandatory + " DESC , " + // mandatory fields first
						I_EXP_FormatLine.COLUMNNAME_Position;

		final Collection<I_EXP_FormatLine> formatLines = expFormat.getFormatLinesOrderedBy(orderBy);
		if (formatLines == null || formatLines.size() < 1)
		{
			throw new ReplicationException(MSG_EXPFormatNoLines);
		}

		// Iterate all Export Format Lines (even this marked as part of unique index)
		// and set value of column
		for (final I_EXP_FormatLine formatLine : formatLines)
		{
			try
			{
				log.info("=================== Beginning of Format Line ===============================");
				log.info("formatLine: [" + formatLine.toString() + "]");

				//
				// Get the value from xml
				final Object value = getValueFromFormat(formatLine, po, rootElement, result, parentXPath, ReplicationType, ReplicationTrxName);

				//
				// Set the value on the PO
				setReplicaValues(value, formatLine, po, result);
			}
			catch (final Exception e)
			{
				throw new ReplicationException(MSG_EXPValueFormatNotResolved, e)
						.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, formatLine);
			}
		}

		// if we imported a record that has an AD_Session_ID column, then set its value to the session-id which we are importing with (if any).
		if (po.getPOInfo().hasColumnName(I_AD_Session.COLUMNNAME_AD_Session_ID))
		{
			final int adSessionId = Env.getContextAsInt(ctx, Env.CTXNAME_AD_Session_ID);
			if (adSessionId > 0)
			{
				po.set_ValueOfColumn(I_AD_Session.COLUMNNAME_AD_Session_ID, adSessionId);
			}
		}
		po.saveExReplica(true);

		//
		// If we're handling this in a replication transaction
		if (replicationTrxLine != null)
		{
			replicationTrxLine.setRecord_ID(InterfaceWrapperHelper.getId(po));
			replicationTrxLine.setAD_Table_ID(po.get_Table_ID());

			//
			// Create replication transaction line
			InterfaceWrapperHelper.save(replicationTrxLine);

			//
			// Set it's value on the PO if supported
			if (isLookupTableReplicationTrxSupported)
			{
				// task 08770 set the value to the imported PO's physical column
				// note: see the javadoc of isLookupTableReplicationTrxSupported() for why we still need to check if the columns actually exist, despite isLookupTableReplicationTrxSupported==true.
				final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);
				if (adTableDAO.hasColumnName(po.get_TableName(), IReplicationIssueAware.COLUMNNAME_EXP_ReplicationTrx_ID))
				{
					InterfaceWrapperHelper.setValue(po, IReplicationIssueAware.COLUMNNAME_EXP_ReplicationTrx_ID, replicationTrxLine.getEXP_ReplicationTrx_ID());
				}
				if (adTableDAO.hasColumnName(po.get_TableName(), IReplicationIssueAware.COLUMNNAME_IsImportedWithIssues))
				{
					final boolean importedWithIssues = !X_EXP_ReplicationTrxLine.REPLICATIONTRXSTATUS_Vollstaendig.equals(replicationTrxLine.getReplicationTrxStatus());
					InterfaceWrapperHelper.setValue(po, IReplicationIssueAware.COLUMNNAME_IsImportedWithIssues, importedWithIssues);
				}
				InterfaceWrapperHelper.save(po);
			}
		}
		return po;
	}

	/**
	 * Get the value from format
	 *
	 * @param line
	 * @param po
	 * @param rootElement
	 * @param result
	 * @param ReplicationType
	 * @param trxName
	 * @param ReplicationTrxName
	 * @return Object with the Value
	 */
	private Object getValueFromFormat(
			final I_EXP_FormatLine line,
			final PO po,
			final Element rootElement,
			final StringBuilder result,
			final String parentXPath,
			final String ReplicationType,
			final String ReplicationTrxName)
	{
		Object value = null;

		// Find Record_ID by ???Value??? In fact by Columns set as Part Of Unique Index in Export Format!
		final String valueXPath = appendToXPath(parentXPath, line);

		if (X_EXP_FormatLine.TYPE_XMLElement.equals(line.getType()))
		{
			// XML Element
			try
			{
				// we must not mix up a missing node (i.e. null) with an empty string result (i.e. "")
				final Element referencedNode = XMLHelper.getElement(valueXPath, rootElement);
				if (referencedNode == null)
				{
					value = null;
				}
				else
				{
					value = getXMLValue(valueXPath, rootElement, line);
				}
			}
			catch (final XPathExpressionException e)
			{
				throw new ReplicationException(MSG_CantGetUniqueFormatLine, e)
						.addParameter(X_EXP_FormatLine.TYPE_XMLElement, line.getValue());
			}
			log.info("value=[" + value + "]");

		}
		else if (X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(line.getType()))
		{
			// Referenced Export Format
			// get from cache
			final MEXPFormat referencedExpFormat = MEXPFormat.get(ctx, line.getEXP_EmbeddedFormat_ID(), ITrx.TRXNAME_None);
			log.info("referencedExpFormat = " + referencedExpFormat);

			// int refRecord_ID = 0;

			log.debug("Seach for XML Element = " + valueXPath);
			final Element referencedNode;
			try
			{
				referencedNode = XMLHelper.getElement(valueXPath, rootElement);
			}
			catch (final XPathExpressionException e)
			{
				throw new ReplicationException(MSG_CantGetRefferenceNode, e)
						.addParameter(X_EXP_FormatLine.TYPE_XMLElement, valueXPath);
			}

			log.info("referencedNode = " + referencedNode);
			if (referencedNode != null && referencedNode.hasChildNodes())
			{
				try
				{
					// 03469: we don't just try to look up the ID, but also create the PO on demand, if required
					final PO referencedPO = importElement(result, rootElement, referencedExpFormat, ReplicationType, valueXPath,
							null, // masterPO
							InterfaceWrapperHelper.create(line.getEXP_Format(), I_EXP_Format.class), // masterExpFormat
							line, // masterFormatLine
							ReplicationTrxName, po.get_TrxName());

					final ITableAndColumn targetTableAndColumn = Services.get(IIMPProcessorBL.class).getTargetTableAndColumn(line);

					final String embeddedKeyColumnName = targetTableAndColumn.getColumnName();
					value = referencedPO.get_Value(embeddedKeyColumnName);
					// end 03203 case handling for different replication linking
				}
				catch (final Exception e)
				{
					throw new ReplicationException(MSG_CantGetRecordID, e)
							.addParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_EXP_Format_ID, referencedExpFormat)
							.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, line.getValue());
				}
				// log.info("refRecord_ID = " + refRecord_ID);
				// value = new Integer(refRecord_ID);
			}
			else
			{
				log.info("NULL VALUE FOR " + valueXPath);
				value = null;
			}
			log.info("value=[" + value + "]");
		}
		else if (X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()))
		{
			if (po.is_Changed())
			{
				isChanged = true;
				po.saveExReplica(true);
			}

			// Embedded Export Format It is used for Parent-Son records like Order&OrderLine
			// get from cache
			final MEXPFormat referencedExpFormat = MEXPFormat.get(ctx, line.getEXP_EmbeddedFormat_ID(), ITrx.TRXNAME_None);
			log.info("embeddedExpFormat = " + referencedExpFormat);

			NodeList nodeList;
			try
			{
				nodeList = XMLHelper.getNodeList(line.getValue(), rootElement);
			}
			catch (final XPathExpressionException e)
			{
				throw new ReplicationException(MSG_CantGetNodeList, e)
						.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, line.getValue());
			}
			for (int j = 0; j < nodeList.getLength(); j++)
			{
				final Element referencedElement = (Element)nodeList.item(j);
				log.info("EmbeddedEXPFormat - referencedElement.getNodeName = " + referencedElement.getNodeName());

				PO embeddedPo = null;
				// Import embedded PO
				log.debug("=== BEGIN RECURSION CALL ===");
				try
				{
					// note that we call the method with parentXPath="", because we also call it with 'referencedElement', i.e. the current child node
					embeddedPo = importElement(result, referencedElement, referencedExpFormat, ReplicationType, "",
							po, // masterPO
							InterfaceWrapperHelper.create(line.getEXP_Format(), I_EXP_Format.class), // masterExpFormat
							line, // masterExpFormatLine
							ReplicationTrxName, po.get_TrxName());
				}
				catch (final Exception e)
				{
					throw new ReplicationException(MSG_CantImport, e)
							.addParameter(I_AD_Element.COLUMNNAME_AD_Element_ID, referencedElement)
							.addParameter(I_AD_Element.COLUMNNAME_PO_Name, po);
				}
				log.debug("embeddedPo = " + embeddedPo);
				embeddedPo.saveExReplica(true);
				result.append(" Embedded Save Successful ; ");
			}
		}
		else if (X_EXP_FormatLine.TYPE_XMLAttribute.equals(line.getType()))
		{
			// XML Attribute
			try
			{
				value = getXMLValue("@" + line.getValue(), rootElement, line);
			}
			catch (final XPathExpressionException e)
			{
				throw new ReplicationException(MSG_CantGetUniqueFormatLine, e)
						.addParameter(X_EXP_FormatLine.TYPE_XMLElement, line.getValue());
			}
			log.info("value=[" + value + "]");
		}
		else
		{
			// Export Format Line is not one of the possible values...ERROR
			throw new ReplicationException(MSG_EXPFormatLineNonValidType);
		}

		return value;
	}

	/**
	 *
	 * @param value
	 * @param line
	 * @param po
	 * @param result
	 */
	private void setReplicaValues(Object value, final I_EXP_FormatLine line, final PO po, final StringBuilder result)
	{
		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		final MColumn column = MColumn.get(ctx, line.getAD_Column_ID());
		log.info("column=[" + column + "]");

		if (adTableDAO.isVirtualColumn(column))
		{
			log.info("Skip importing value '{}' ({}) for {} because it's a virtual column", new Object[] { value, line, po });
			return;
		}

		if (value == null)
		{
			return; // nothing to set
		}

		if (!X_EXP_FormatLine.TYPE_EmbeddedEXPFormat.equals(line.getType()))
		{
			final int adReferenceId = importProcessorBL.getAD_Reference_ID(column, line);
			final String columnName = column.getColumnName();

			// the class to assume when we set our value
			final Class<?> clazz;

			// Handle Posted
			if (columnName.equalsIgnoreCase("Posted")
					|| columnName.equalsIgnoreCase("Processed")
					|| columnName.equalsIgnoreCase("Processing"))
			{
				clazz = Boolean.class;
			}
			else if (Services.get(IColumnBL.class).isRecordColumnName(columnName))
			{
				clazz = Integer.class;
			}
			else
			{
				// the default
				clazz = DisplayType.getClass(adReferenceId, true);
			}

			// 02775
			// Note: at this place there was a check for column names AD_Language and EntityType.
			// That code didn't work and has been removed

			log.info("clazz = " + clazz.getName());

			if (DisplayType.isDate(adReferenceId))
			{
				// Handle Date and Time
				value = handleDateTime(value, column, line);
			}

			log.info("formatLinesType = " + line.getType());

			if (DisplayType.DateTime == adReferenceId
					|| DisplayType.Date == adReferenceId)
			{
				//
				final boolean ok = po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), value);
				log.info("Set value of column [{}]=[{}] (ok={})", columnName, value, ok);
			}
			else if (DisplayType.Integer == adReferenceId
					|| DisplayType.isID(adReferenceId)

					// 03534: note that a Button can be 'anything' in the case of C_BPartner.AD_OrgBP_ID, it is a table reference to AD_Org
					|| DisplayType.Button == adReferenceId && column.getAD_Reference_Value_ID() > 0
					&& X_AD_Reference.VALIDATIONTYPE_TableValidation.equals(column.getAD_Reference_Value().getValidationType()))
			{
				// 02775
				// Note: Even with displayType being ID, we still can't assume an integer in all cases. E.g. AD_Languange
				boolean stringValueAlreadySet = false;
				if (column.getAD_Reference_Value_ID() <= 0)
				{
					// Without an explicit reference value, we assume that the FK is an interger
					stringValueAlreadySet = false;
				}

				final int displayType = adReferenceId;
				if ((displayType == DisplayType.Table
						|| displayType == DisplayType.Search
						|| displayType == DisplayType.Button
						) && column.getAD_Reference_Value_ID() > 0)
				{
					final MRefTable refTable = MRefTable.get(ctx, column.getAD_Reference_Value_ID());

					final MColumn referencedCol = MColumn.get(ctx, refTable.getAD_Key());
					if (DisplayType.isText(referencedCol.getAD_Reference_ID()))
					{
						final boolean ok = po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), value);
						log.info("Set string value of column [{}]=[{}] (ok={})", columnName, value, ok);
						stringValueAlreadySet = true;
					}
				}

				if (!stringValueAlreadySet)
				{
					if (!Check.isEmpty(value.toString()))
					{
						// Note: Prior to 02775 we had a NumberFormatException to 'AD_Language' columns at this place
						final int intValue = Integer.parseInt(value.toString());
						value = new Integer(intValue);
					}
					else
					{
						value = null;
					}

					log.info("About to set int value of column [" + columnName + "]=[" + value + "]");
					po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), value);
					log.info("Set int value of column [" + columnName + "]=[" + value + "]");
				}

			}
			else if (DisplayType.isNumeric(adReferenceId)
					&& adReferenceId != DisplayType.Integer)
			{
				//
				if (!Check.isEmpty(value.toString()))
				{
					value = new BigDecimal(value.toString());
				}
				else
				{
					value = null;
				}
				// value = new Double( doubleValue );

				log.info("About to set BigDecimal value of column [" + columnName + "]=[" + value + "]");

				po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), value);

				log.info("Set BigDecimal value of column [" + columnName + "]=[" + value + "]");
			}
			else if (DisplayType.YesNo == adReferenceId)
			{
				if (clazz == Boolean.class)
				{
					final String v = handleYesNo(value);
					po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), v);
				}
			}
			else
			{
				try
				{
					log.info("About to set value of column [" + columnName + "]=[" + value + "]");

					if (clazz == Boolean.class)
					{
						final String v = handleYesNo(value);
						po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), v);
					}
					else
					{
						po.set_ValueOfAD_Column_ID(line.getAD_Column_ID(), clazz.cast(value));
					}

					log.info("Set value of column [" + columnName + "]=[" + value + "]");
				}
				catch (final ClassCastException ex)
				{
					throw new ReplicationException(MSG_CantSetColumnValue, ex)
							.addParameter(I_AD_Column.COLUMNNAME_AD_Column_ID, line.getAD_Column_ID());
				}

			}
			result.append(columnName).append("=").append(value).append("; ");
		}// end if TYPE_EmbeddedEXPFormat
	}

	private static int getADClientIdByValue(final Properties ctx, final String value, final String trxName)
	{
		s_log.debug("AD_Client_Value = {}", value);

		final String whereClause = I_AD_Client.COLUMNNAME_Value + "= ? ";
		final int adClientId = new Query(ctx, I_AD_Client.Table_Name, whereClause, trxName)
				.setOnlyActiveRecords(true)
				.setParameters(value)
				.firstIdOnly();

		s_log.debug("AD_Client_ID = {}" + adClientId);
		return adClientId;
	}

	/**
	 * This Method gets the PO record associated with the created ReplicationTrxLine from the exportFormat
	 *
	 * @param result
	 * @param expFormat
	 * @param rootElement
	 * @param rootNodeName
	 * @param ReplicationType
	 * @param parentXPath
	 * @param masterPO
	 * @param ReplicationTrxName
	 * @param trxName
	 * @return
	 * @throws ReplicationException
	 */
	private POReplicationTrxLineDraft getObjectFromFormat(
			final StringBuilder result,
			final MEXPFormat expFormat,
			final Element rootElement,
			final String rootNodeName,
			final String ReplicationType,
			final String parentXPath,
			final PO masterPO,
			final I_EXP_Format masterExpFormat,
			final I_EXP_FormatLine masterExpFormatLine,
			final String ReplicationTrxName,
			final String trxName) throws ReplicationException
	{
		if (expFormat == null || rootElement == null || rootNodeName == null)
		{
			throw new IllegalArgumentException("expFormat, rootNode and RootnodeName can't be null!");
		}

		final IADTableDAO adTableDAO = Services.get(IADTableDAO.class);

		log.info("expFormat = " + expFormat);
		log.info("rootNode.getNodeName() = " + rootElement.getNodeName());
		log.info("rootNodeName = " + rootNodeName);

		if (rootElement.getParentNode() != null)
		{
			log.info("rootNode.ParentName = " + rootElement.getParentNode().getNodeName());
		}

		// get the import mode to see if the format specifies any assumptions
		final I_EXP_Format expFormatExt = InterfaceWrapperHelper.create(expFormat, I_EXP_Format.class);
		final String importMode = expFormatExt.getRplImportMode();

		final boolean doLookup = Check.isEmpty(importMode) || I_EXP_Format.RplImportMode_RecordExists.equals(importMode);

		// Get list with all Unique columns!
		final List<I_EXP_FormatLine> uniqueFormatLines = new ArrayList<I_EXP_FormatLine>();
		if (doLookup)
		{
			uniqueFormatLines.addAll(expFormat.getUniqueColumns());
			if (uniqueFormatLines.isEmpty()
					&& masterPO == null) // note that with a masterPO, a lookup can be done, even without unique lines
			{
				throw new ReplicationException(MSG_EXPFormatLineNoUniqueColumns);
			}
		}

		int replication_id = 0;
		final Object[] cols = new Object[uniqueFormatLines.size()];
		final List<Object> params = new ArrayList<Object>();
		final StringBuilder whereClause = new StringBuilder();
		int col = 0;
		String formatLines = "";

		for (final I_EXP_FormatLine uniqueFormatLine : uniqueFormatLines)
		{
			boolean nodeExists = true;
			final MColumn column = MColumn.get(ctx, uniqueFormatLine.getAD_Column_ID());
			log.info("column = [" + column + "]");
			final String columnName = column.getColumnName();
			String columnSQL;
			if (adTableDAO.isVirtualColumn(column))
			{
				columnSQL = "( " + column.getColumnSQL() + " )";
			}
			else
			{
				columnSQL = columnName;
			}

			formatLines = formatLines + "|" + columnName;

			if (X_EXP_FormatLine.TYPE_XMLElement.equals(uniqueFormatLine.getType()))
			{
				// XML Element
				final String xPath = appendToXPath(parentXPath, uniqueFormatLine);
				try
				{
					cols[col] = getXMLValue(xPath, rootElement, uniqueFormatLine);
				}
				catch (final XPathExpressionException e)
				{
					throw new ReplicationException(MSG_CantGetUniqueFormatLine, e)
							.addParameter(X_EXP_FormatLine.TYPE_XMLElement, xPath);
				}
				if (cols[col] == null || Check.isEmpty(cols[col].toString()))
				{
					// 04124: guarding against NPE, as there might be an "empty" tag
					// 03259: the (lookup) value doesn't exist in the incoming XML. This is equivalent to a null value
					nodeExists = false;
				}
				log.info("values[" + col + "]=" + String.valueOf(cols[col]));

			}
			else if (X_EXP_FormatLine.TYPE_ReferencedEXPFormat.equals(uniqueFormatLine.getType()))
			{
				// Referenced Export Format
				log.info("referencedExpFormat.EXP_EmbeddedFormat_ID = " + uniqueFormatLine.getEXP_EmbeddedFormat_ID());
				// get from cache
				final MEXPFormat referencedExpFormat = MEXPFormat.get(ctx, uniqueFormatLine.getEXP_EmbeddedFormat_ID(), ITrx.TRXNAME_None);
				log.info("referencedExpFormat = " + referencedExpFormat);

				// Find Record_ID by ???Value??? In fact by Columns set as Part Of Unique Index in Export Format!
				// metas-ts: currently this is consistent with the way be create our XSD file. There, we also use line.getValue for the XSD name
				final Element referencedNode = (Element)rootElement.getElementsByTagName(uniqueFormatLine.getValue()).item(0);
				log.info("referencedNode = " + referencedNode);

				final int record_ID;
				if (referencedNode == null)
				{
					// 03259 the referenced node doesn't exist in the incoming XML. This is equivalent to a null value
					nodeExists = false;
					record_ID = -1;
				}
				else
				{
					final String xPath = appendToXPath(parentXPath, uniqueFormatLine);
					try
					{
						// 03469: we don't just try to look up the ID, but also create the PO on demand, if required
						final PO referencedPO = importElement(result, rootElement, referencedExpFormat, ReplicationType, xPath,
								null, // masterPO
								InterfaceWrapperHelper.create(expFormat, I_EXP_Format.class), // masterExportFormant
								uniqueFormatLine, // masterExpFormatLine
								ReplicationTrxName, trxName);
						record_ID = referencedPO.get_ID();
					}
					catch (final Exception e)
					{
						throw new ReplicationException(MSG_CantGetRecordID, e)
								.addParameter(org.compiere.model.I_EXP_Format.COLUMNNAME_EXP_Format_ID, referencedExpFormat)
								.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, uniqueFormatLine.getValue());
					}
				}
				log.info("record_ID = " + record_ID);

				cols[col] = record_ID;
			}
			else
			{
				// Export Format Line is not one of two possible values...ERROR
				throw new ReplicationException(MSG_EXPFormatLineNonValidType)
						.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, uniqueFormatLine.getValue());
			}

			// metas start: rc: task 03749
			// Initialized variable
			// metas end : rc
			Object paramSQL = null;
			if (cols[col] == null || !nodeExists)
			{
				paramSQL = cols[col]; // 04124: don't try to parse an empty or null column value
			}
			else
			{
				final int adReferenceId = importProcessorBL.getAD_Reference_ID(column, uniqueFormatLine);

				if (DisplayType.isDate(adReferenceId))
				{
					final Timestamp value = handleDateTime(cols[col], column, uniqueFormatLine);
					paramSQL = value;
				}
				else if (adReferenceId == DisplayType.String)
				{
					paramSQL = cols[col];
				}
				else if (DisplayType.isID(adReferenceId)
						|| DisplayType.Integer == adReferenceId)
				{
					Object value = cols[col];
					if (!Check.isEmpty(value.toString()))
					{
						// double doubleValue = Double.parseDouble(value.toString());
						value = new Integer(value.toString());
						if (DisplayType.ID == adReferenceId)
						{
							replication_id = (Integer)value;
						}
					}
					else
					{
						value = null;
					}
					paramSQL = value;
				}
				else if (DisplayType.isNumeric(adReferenceId))
				{
					columnSQL = "ROUND(" + columnSQL + ", 2)";
					paramSQL = new BigDecimal((String)cols[col]).setScale(2, BigDecimal.ROUND_HALF_UP);
				}
				// metas: cg: task 03749 : start
				else if (DisplayType.isLOB(adReferenceId))
				{
					final Object value = cols[col];
					String decoded;
					// metas: rc: start
					try
					{
						decoded = new String(Util.decodeBase64(value.toString().getBytes()));
						paramSQL = decoded.getBytes();
					}
					catch (final Exception e)
					{
						throw new ReplicationException("Decoding failed at line " + uniqueFormatLine.getName()
								+ ", at column number " + col, e)
								.addParameter(I_EXP_FormatLine.COLUMNNAME_EXP_FormatLine_ID, uniqueFormatLine)
								.addParameter(I_EXP_FormatLine.COLUMNNAME_AD_Column_ID, column);
					}
					// metas:rc: end
				}
				// metas: cg: end
				else
				{
					paramSQL = cols[col]; // assuming that the column value needs so special threadment
				}
			}

			if (col > 0)
			{
				whereClause.append(" AND ");
			}
			if (paramSQL == null || !nodeExists)
			{
				whereClause.append(columnSQL).append(" IS NULL ");
			}
			else
			{
				whereClause.append(columnSQL).append(" = ? ");
				params.add(paramSQL);
			}
			col++;
		}

		final String linkMasterColumnName;
		final String linkColumnName;
		final int linkId;

		if (masterPO != null)
		{
			linkMasterColumnName = masterPO.get_KeyColumns()[0];
			linkColumnName = linkMasterColumnName; // TODO: check which is the link column
			linkId = masterPO.get_ID();
			whereClause.append(" AND ").append(linkColumnName).append("= ? ");
			params.add(linkId);
		}
		else
		{
			linkMasterColumnName = null;
			linkColumnName = null;
			linkId = -1;
		}

		final I_AD_Table lookupTable = expFormat.getAD_Table();
		final String lookupTableName = lookupTable.getTableName();

		final List<PO> lookupValues = new ArrayList<PO>();
		if (doLookup)
		{
			final List<PO> list = new Query(ctx, lookupTableName, whereClause.toString(), trxName)
					.setOnlyActiveRecords(true)
					.setParameters(params)
					.list();
			lookupValues.addAll(list);
		}

		//
		// Create EXP_ReplicationTrx (will be used for our PO)
		// Note that this is where all POs go when they get born, so it's safe to inject the trxLine here.
		final IReplicationTrxBL replicationTrxBL = Services.get(IReplicationTrxBL.class);

		//
		// See JavaDoc
		final boolean isLookupTableAcceptedInReplicationTrxHandling = isLookupTableAcceptedInReplicationTrxHandling(doLookup, lookupTableName);

		final I_EXP_ReplicationTrxLine replicationTrxLine;
		if (isLookupTableAcceptedInReplicationTrxHandling)
		{
			replicationTrxLine = replicationTrxBL.createAndMatchVoidReplicationTrxLine(ctx, lookupTableName, ReplicationTrxName, trxName);
		}
		else
		{
			replicationTrxLine = null;
		}

		if (isLookupTableAcceptedInReplicationTrxHandling)
		{
			if (!expFormatExt.isAlwaysFlagWithIssue())
			{
				//
				// Assume that the replicationTrxLine shall be completed
				replicationTrxLine.setReplicationTrxStatus(X_EXP_ReplicationTrxLine.REPLICATIONTRXSTATUS_Vollstaendig);
			}
			else
			{
				//
				// Assume that the replicationTrxLine shall be flagged with issue and not completed
				replicationTrxLine.setReplicationTrxStatus(X_EXP_ReplicationTrxLine.REPLICATIONTRXSTATUS_NichtVollstaendigImportiert);
			}
		}

		final PO po;
		if (lookupValues.size() > 1) // The Return Object must be always one
		{
			//
			// Do a lookup to find out if the DB contains a data record for the given XML data
			final String lookupExMsg = MSG_EXPFormatLineDuplicatedObject + " : " + expFormat.getName() + "(" + formatLines + ")";
			final ReplicationException lookupEx = new DuplicateLookupObjectException(lookupExMsg, lookupValues, replicationTrxLine, doLookup)
					.addParameter("WhereClause", whereClause.toString())
					.addParameter("Parameters", params)
					.addParameter("Value", lookupValues)
					.addParameter("MasterPO", masterPO != null ? masterPO : "NULL");

			// further up, this exception might be caught and resolved by getSingleDefaultPO()
			throw lookupEx;
		}
		else if (lookupValues.isEmpty()) // Means that is a new record
		{
			if (I_EXP_Format.RplImportMode_RecordExists.equals(importMode))
			{
				//
				// TODO not yet handled in transaction
				//
				// the format definition wrongly assumed that there is already a record for the given XML data
				throw new ReplicationException(MSG_PONotFound)
						.addParameter(I_EXP_Format.COLUMNNAME_RplImportMode, importMode)
						.addParameter("WhereClause", whereClause.toString())
						.addParameter("Parameters", params)
						.addParameter("MasterPO", masterPO != null ? masterPO : "NULL");
			}
			else
			{
				// there is no existing record for the given XML data, but the format definition didn't assume
				// otherwise, so it's OK
				final String tableName = Services.get(IADTableDAO.class).retrieveTableName(expFormat.getAD_Table_ID());
				po = TableModelLoader.instance.newPO(ctx, tableName, trxName);
				po.set_ValueNoCheck("AD_Client_ID", Env.getAD_Client_ID(ctx)); // metas
				// NOTE: using context's AD_Org_ID, else IReplRequestHandler could fail to run because the object won't have write permissions
				po.set_ValueNoCheck("AD_Org_ID", Env.getAD_Org_ID(ctx)); // metas

				if (replication_id > 0)
				{
					po.set_CustomColumn(po.get_KeyColumns()[0], replication_id);
				}

				if (linkColumnName != null && linkId > 0)
				{
					po.set_CustomColumn(linkColumnName, linkId);
				}
			}
		}
		else
		{
			po = lookupValues.get(0);
		}

		//
		// Set PO results to ReplicationTrxLine & save (current trx already selected)
		final POReplicationTrxLineDraft draftResult;
		if (isLookupTableAcceptedInReplicationTrxHandling)
		{
			draftResult = new POReplicationTrxLineDraft(po, replicationTrxLine, doLookup);
		}
		else
		{
			draftResult = new POReplicationTrxLineDraft(po);
		}

		return draftResult;
	}

	/**
	 * @param doLookup
	 * @param lookupTableName
	 * @return true if the table is not excluded in general from replication trx manangement, AND if this is an INSERT and not a LOOKUP.
	 *         <p>
	 *         Note: we disallow this on lookups, because we don't need that case yet and aren't yet 100% usre about the implications of allowing for also for lookups.
	 */
	private boolean isLookupTableAcceptedInReplicationTrxHandling(final boolean doLookup, final String lookupTableName)
	{
		final boolean isTableIgnored = Services.get(IReplicationTrxBL.class).isTableIgnored(lookupTableName);
		return !isTableIgnored && !doLookup;
	}

	/**
	 * Currently always returns <code>true</code>. The original intention was to return <code>true</code> only if the PO can be linked to the ReplicationTrx. The problem there is that we often use
	 * <i>nested</i> lookup-EXP-Formats that are based on a view and therefore can't be linked to the replication-trx. Example: we look up the C_BPartner_Location for a C_OLCand using a view-based
	 * lookup format. And inside that view-based lookup format, we use another view-based lookup format to look up the BPL's C_BPArtner.
	 *
	 * @param doLookup
	 * @param lookupTableName
	 * @return <code>true</code>
	 */
	private boolean isLookupTableReplicationTrxSupported(final boolean doLookup, final I_EXP_Format exportFormat)
	{
		return true; // todo: we need a checkbox in the exp-format that say "yes", even if there is no such EXP_ReplicationTrx_ID col
		// if (exportFormat == null)
		// {
		// return false;
		// }
		// final String lookupTableName = exportFormat.getAD_Table().getTableName();
		//
		// final boolean isRplTrxSupported = Services.get(IADTableDAO.class).hasColumnName(lookupTableName, I_EXP_ReplicationTrx.COLUMNNAME_EXP_ReplicationTrx_ID);
		// // return isRplTrxSupported && !doLookup;
		// return isRplTrxSupported;
	}

	/**
	 * @param availableValues
	 * @return single default value
	 *
	 * @throws ReplicationException if none or more default values were found
	 */
	private PO getSingleDefaultPO(final List<PO> availableValues, final I_EXP_FormatLine line) throws ReplicationException
	{
		// TODO: get the table!!
		// MRefTable.get(ctx, targetCcol.getAD_Reference_ID()).
		final ITableAndColumn targetTableAndColumn = Services.get(IIMPProcessorBL.class).getTargetTableAndColumn(line);

		PO defaultValue = null;
		for (final PO lookupValue : availableValues)
		{
			if(!lookupValue.isActive())
			{
				continue; // shouldn't be the case, but just to make sure..
			}
			final int id = lookupValue.get_ID();

			final String tableName = targetTableAndColumn.getTableName();
			final PO targetValue = TableModelLoader.instance.getPO(ctx, tableName, id, ITrx.TRXNAME_None);

			final Optional<Boolean> isReplicationLookupDefault = InterfaceWrapperHelper.getValue(targetValue, IReplicationTrxDAO.COLUMNNAME_IsReplicationLookupDefault);
			if (isReplicationLookupDefault.isPresent() && isReplicationLookupDefault.get())
			{
				if (defaultValue != null)
				{
					throw new DuplicateLookupObjectException(MSG_EXPFormatLineDuplicatedDefaultObject);
				}
				defaultValue = lookupValue;
			}
		}

		//
		// ONLY get a single item or null
		if (defaultValue == null)
		{
			throw new ReplicationException(MSG_EXPFormatLineNoDefaultFallbackObject);
		}
		return defaultValue;
	}

	/**
	 * This method attempts to parse the given value into a <code>Timestamp</code>.
	 * <p>
	 * If the given <code>formatLine</code> has a <code>DateFormat</code>, then we use that format to parse the given <code>value</code> using {@link SimpleDateFormat}. Otherwise, we use
	 * {@link DatatypeConverter#parseDateTime(String)} to do the parsing.
	 *
	 * Note that this method assumes that
	 * <ul>
	 * <li>none of the given parameters is <code>null</code></li>
	 * <li>the given column is a "Date" column, i.e. <code>DisplayType.isDate()</code> will return <code>true</code></li>
	 * </ul>
	 *
	 * @param value
	 * @param column
	 * @param formatLine
	 * @return
	 */
	private Timestamp handleDateTime(final Object value,
			final I_AD_Column column,
			final I_EXP_FormatLine formatLine)
	{
		if (value == null)
		{
			return null;
		}

		final String valueString = value.toString();
		if (Check.isEmpty(valueString, true))
		{
			return null;
		}

		final int displayType = importProcessorBL.getAD_Reference_ID(column, formatLine);
		Check.assume(DisplayType.isDate(displayType), column + " is a date dolumn");

		Timestamp result;
		final String dateFormat = formatLine.getDateFormat();
		final SimpleDateFormat df;
		if (!Check.isEmpty(dateFormat))
		{
			df = new SimpleDateFormat(dateFormat);
			try
			{
				final Date date = df.parse(valueString);
				result = new Timestamp(date.getTime());
			}
			catch (final ParseException e)
			{
				throw new ReplicationException(MSG_ParseException, e)
						.addParameter(I_AD_Attribute_Value.COLUMNNAME_V_String, value)
						.addParameter(I_EXP_FormatLine.COLUMNNAME_DateFormat, dateFormat);
			}
			log.info("Parsed value = " + result.toString() + " (Format:" + df.toPattern() + ")");
		}
		else
		{
			try
			{
				final Calendar calendar = javax.xml.bind.DatatypeConverter.parseDateTime(valueString);
				result = new Timestamp(calendar.getTimeInMillis());
			}
			catch (final IllegalArgumentException e)
			{
				throw new ReplicationException(MSG_ParseException, e)
						.addParameter(I_AD_Attribute_Value.COLUMNNAME_V_String, value);
			}
		}

		//
		// If our time is Date only, make sure we get rid of Time part.
		if (displayType == DisplayType.Date)
		{
			// assuming result is not null
			result = TimeUtil.trunc(result, TimeUtil.TRUNC_DAY);
		}

		return result;
	}

	private static final Pattern REGEXP_YesNo = Pattern.compile("[YN]");

	/**
	 *
	 * @param value
	 * @return "Y" or "N"
	 */
	protected String handleYesNo(final Object value)
	{
		if (value == null)
		{
			return "N";
		}

		final String v;
		final String strValue = value.toString();

		if (REGEXP_YesNo.matcher(strValue.toUpperCase()).matches())
		{
			v = strValue.toUpperCase();
		}
		else
		{
			v = strValue.equals("true") ? "Y" : "N";
		}
		return v;
	}

	private int getAttributeAsInt(final Element element, final String attributeName)
	{
		final String valueStr = element.getAttribute(attributeName);
		try
		{
			return new Integer(valueStr);
		}
		catch (final NumberFormatException e)
		{
			throw new ReplicationException(MSG_InvalidArguments, e)
					.addParameter("Attribute", attributeName)
					.addParameter("AttributeValue", valueStr);
		}
	}

	private String appendToXPath(final String xPath, final org.compiere.model.I_EXP_FormatLine line)
	{
		// metas-ts: currently this is consistent with the way be create our XSD file. There, we also use line.getValue for the XSD name
		return appendToXPath(xPath, line.getValue());
	}

	private String appendToXPath(final String xPath, final String appendix)
	{
		if (Check.isEmpty(xPath, true))
		{
			return appendix;
		}
		return xPath + "/" + appendix;
	}

	private final Properties createContext(final Element rootElement)
	{
		Check.assumeNotNull(_initialCtx, "initialCtx not null");
		final Properties ctx = Env.deriveCtx(_initialCtx);

		if (rootElement.hasAttribute(RPL_Constants.XML_ATTR_AD_Client_Value))
		{
			final String AD_Client_Value = rootElement.getAttribute(RPL_Constants.XML_ATTR_AD_Client_Value);
			updateContextFromADClientValue(ctx, AD_Client_Value);
		}

		if (rootElement.hasAttribute(RPL_Constants.XML_ATTR_AD_SESSION_ID))
		{
			final int adSessionId = getAttributeAsInt(rootElement, RPL_Constants.XML_ATTR_AD_SESSION_ID);
			updateContextFromSession(ctx, adSessionId);
		}
		else
		{
			// task 08569: is there is no incoming session, then *do not* fall back to the one from '_initialCtx', but make it explicit that there is no session.
			// this XML needs to have a request handler that deals with the situation (e.g. by doing a login)
			setReplicationCtx(ctx, Env.CTXNAME_AD_Session_ID, Env.CTXVALUE_AD_SESSION_ID_NONE, false);
		}

		//
		// Validate context
		if (!Env.containsKey(ctx, Env.CTXNAME_AD_Client_ID))
		{
			throw new ReplicationException(ReplicationHelper.MSG_XMLInvalidContext)
					.addParameter(Env.CTXNAME_AD_Client_ID, null);
		}

		return ctx;
	}

	private static void updateContextFromADClientValue(final Properties ctx, final String AD_Client_Value)
	{
		if (Check.isEmpty(AD_Client_Value, true))
		{
			throw new ReplicationException(MSG_XMLClientValueMandatory);
		}

		final int adClientId;
		try
		{
			adClientId = getADClientIdByValue(ctx, AD_Client_Value, ITrx.TRXNAME_None);
		}
		catch (final Exception e)
		{
			throw new ReplicationException(MSG_XMLClientNotFound, e)
					.addParameter(I_AD_Client.COLUMNNAME_Value, AD_Client_Value);
		}
		if (adClientId < 0)
		{
			throw new ReplicationException(MSG_XMLClientNotFound)
					.addParameter(I_AD_Client.COLUMNNAME_Value, AD_Client_Value);
		}

		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, adClientId, false);
	}

	private static void updateContextFromSession(final Properties ctx, final int adSessionId)
	{
		if (adSessionId <= 0)
		{
			s_log.debug("Skip because there is not AD_Session_ID");
			return;
		}
		final MSession session = MSession.get(ctx, adSessionId);
		if (session == null)
		{
			s_log.info("Skip because no session found for ID: {}", adSessionId);
			return;
		}
		if (session.isProcessed())
		{
			s_log.info("Skip because session is already processed: {}", session);
			return;
		}

		// Update context from session
		session.updateContext(true); // force=true
		setReplicationCtx(ctx, Env.CTXNAME_AD_Client_ID, session.getAD_Client_ID(), false);
		setReplicationCtx(ctx, Env.CTXNAME_AD_Session_ID, adSessionId, false);
		setReplicationCtx(ctx, Env.CTXNAME_AD_Role_ID, session.getAD_Role_ID(), true);
		setReplicationCtx(ctx, Env.CTXNAME_Date, session.getLoginDate(), true);
		setReplicationCtx(ctx, Env.CTXNAME_AD_Org_ID, session.getAD_Org_ID(), true);
		setReplicationCtx(ctx, Env.CTXNAME_AD_User_ID, session.getAD_User_ID(), true);
	}

	private Object getXMLValue(final String xPathExpression, final Node rootNode, final I_EXP_FormatLine formatLine) throws XPathExpressionException
	{
		final String value = XMLHelper.getString(xPathExpression, rootNode);
		if (!Check.isEmpty(value))
		{
			return value;
		}

		// Default value

		String defaultValue = null;

		final String defaultValueExpr = formatLine.getDefaultValue();
		if (!Check.isEmpty(defaultValueExpr, true))
		{
			defaultValue = Env.parseContext(ctx, 0, defaultValueExpr, false, true); // onlyWindow=false, ignoreUnparsable=true
		}

		return defaultValue;
	}
}
