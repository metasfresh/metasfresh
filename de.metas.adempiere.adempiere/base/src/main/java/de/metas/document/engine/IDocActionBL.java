package de.metas.document.engine;

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

import java.sql.Timestamp;
import java.util.Collection;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.ISingletonService;
import org.compiere.model.I_C_DocType;
import org.compiere.process.DocAction;

/**
 * Note the the implementation of this
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IDocActionBL extends ISingletonService
{

	/**
	 *
	 * @param document
	 * @param docAction
	 * @return true if document was processed
	 * @throws IllegalArgumentException if 'document' is not instance of {@link DocAction}.
	 */
	boolean processIt(Object document, String docAction);

	/**
	 * This method makes a direct call to the legacy DocumentEngine. It will check if the given <code>processAction</code> is permissible, and if not, execute the given document's current
	 * <code>DocAction</code> instead.
	 *
	 * @param document
	 * @param processAction
	 * @return
	 */
	boolean processIt(DocAction document, String processAction);

	/**
	 * Process document. If there is any error it throws exception. If success the document is saved.
	 *
	 * @param document
	 * @param docAction
	 * @param expectedDocStatus (optional) If specified (not null), after processing it is checked that document shall have expected DocStatus
	 * @throws IllegalArgumentException if document is not a valid {@link DocAction}
	 * @throws AdempiereException if processing fails or document does not have expected DocStatus
	 */
	void processEx(Object document, String docAction, String expectedDocStatus);

	/**
	 * Check if a document is completed.
	 *
	 * A documented is considered complete when it is COmpleted or CLosed.
	 *
	 * Please note that a reversed document is not considered to be complete.
	 *
	 * @param document
	 * @return true if document is completed
	 * @throws IllegalArgumentException if document is not a valid {@link DocAction}.
	 */
	boolean isStatusCompletedOrClosed(Object document);

	boolean isDocumentTable(String tableName);

	/**
	 * Convert given <code>document</code> to {@link DocAction} interface
	 *
	 * @param document
	 * @return document as {@link DocAction}
	 * @throws IllegalArgumentException if document is null or it cannot be converted to {@link DocAction}
	 */
	DocAction getDocAction(Object document);

	/**
	 * Convert given <code>document</code> to {@link DocAction} interface. If the document cannot be converted to {@link DocAction} null is returned.
	 *
	 * @param document
	 * @return document as {@link DocAction} or null
	 */
	DocAction getDocActionOrNull(Object document);

	/**
	 * Retrieve C_DocType_ID for given record. C_DocType_ID and C_DocTypeTarget_ID columns will be checked.
	 *
	 * Please note, is not necessary that the given table to be a true document.
	 *
	 * @param ctx
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @return
	 */
	int getC_DocType_ID(Properties ctx, int AD_Table_ID, int Record_ID);

	I_C_DocType getDocTypeOrNull(Object model);

	/**
	 * Retrieve document status for given record.
	 *
	 * @param ctx
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @return
	 */
	String getDocStatusOrNull(Properties ctx, int AD_Table_ID, int Record_ID);

	/**
	 * Retrieve DocumentNo for given record. If no value was found, the model will be loaded and {@link #getDocumentNo(Object)} will be used.
	 *
	 * @param ctx
	 * @param AD_Table_ID
	 * @param Record_ID
	 * @return document no
	 */
	String getDocumentNo(Properties ctx, final int adTableId, final int recordId);

	/**
	 * Retrieve DocumentNo for given model. Steps to fetch the DocumentNo are (in this order):
	 * <ul>
	 * <li>if <code>model</code> is a {@link DocAction} instance, then {@link DocAction#getDocumentNo()} will be used
	 * <li>if <code>model</code> has DocumentNo column and is not null, that column will be used
	 * <li>if <code>model</code> has Value column and is not null, that column will be used
	 * <li>if <code>model</code> has Name column and is not null, that column will be used
	 * <li>if none are present, record's ID converted to String will be used.
	 * </ul>
	 *
	 * NOTE: this algorithm was implemented due to requirements from http://dewiki908/mediawiki/index.php/03918_Massendruck_f%C3%BCr_Mahnungen_%282013021410000132%29#IT2_-_G01_-_Mass_Printing.
	 *
	 * @param model
	 * @return document no
	 */
	String getDocumentNo(Object model);

	boolean isStatusCompletedOrClosedOrReversed(String docStatus);

	boolean isStatusCompletedOrClosedOrReversed(Object document);

	boolean isStatusDraftedOrInProgress(Object document);

	boolean isStatusCompleted(Object document);

	/**
	 * @param document
	 * @return true if the doc has status 'CL', false otherwise
	 */
	boolean isStatusClosed(Object document);

	/**
	 * Returns <code>true</code> if the given <code>document</code>'s status is one of the given <code>docStatusesToCheckFor</code>.
	 *
	 * @param document
	 * @param docStatusesToCheckFor
	 * @return
	 */
	boolean isStatusOneOf(Object document, String... docStatusesToCheckFor);

	boolean isStatusOneOf(String docStatus, String... docStatusesToCheckFor);

	/**
	 * @param document
	 * @return true if given document is Reversed or Voided
	 */
	boolean isStatusReversedOrVoided(Object document);

	/**
	 * Process a list of documents. The documents will be processed in the same transaction.
	 *
	 * @param documentsToProcess
	 * @param docAction
	 * @param expectedDocStatus
	 */
	<T> void processDocumentsList(Collection<T> documentsToProcess, String docAction, String expectedDocStatus);

	/**
	 * Get the Document Date based on the given table and record.
	 * In case the table is of an yet unsupported table type, the document date will be left null.
	 *
	 * @param ctx
	 * @param adTableID
	 * @param recordId
	 * @return
	 */
	Timestamp getDocumentDate(final Properties ctx, final int adTableID, final int recordId);

	/**
	 * Gets document summary
	 *
	 * @param model
	 * @return document summary or toString() in case the model is not a document.
	 */
	String getSummary(Object model);

}
