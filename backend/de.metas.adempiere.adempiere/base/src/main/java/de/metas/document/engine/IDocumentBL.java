package de.metas.document.engine;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_DocType;

import de.metas.util.ISingletonService;

public interface IDocumentBL extends ISingletonService
{
	String COLUMNNAME_C_DocType_ID = "C_DocType_ID";

	/**
	 *
	 * @param document
	 * @param docAction
	 * @return true if document was processed
	 * @throws IllegalArgumentException if 'document' is not instance of {@link IDocument}.
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
	boolean processIt(IDocument document, String processAction);

	default boolean processIt(final Object documentObj)
	{
		final IDocument document = getDocument(documentObj);
		return processIt(document, document.getDocAction());
	}

	/**
	 * Process document. If there is any error it throws exception. If success the document is saved.
	 *
	 * @param document
	 * @param docAction
	 * @param expectedDocStatus (optional) If specified (not null), after processing it is checked that document shall have expected DocStatus
	 * @throws IllegalArgumentException if document is not a valid {@link IDocument}
	 * @throws AdempiereException if processing fails or document does not have expected DocStatus
	 */
	void processEx(Object document, String docAction, String expectedDocStatus);

	default void processEx(final Object document, final String docAction)
	{
		final String expectedDocStatus = null;
		processEx(document, docAction, expectedDocStatus);
	}

	/**
	 * Check if a document is completed via it's {@code DocStatus} value.
	 *
	 * A documented is considered complete when it is COmpleted or CLosed.
	 *
	 * Please note that a reversed document is not considered to be complete.
	 *
	 * @param document
	 * @return true if document is completed
	 * @throws IllegalArgumentException if document is not a valid {@link IDocument}.
	 * @deprecated Please directly use {@link DocStatus}'s methods
	 */
	@Deprecated
	boolean isDocumentCompletedOrClosed(Object document);

	boolean isDocumentTable(String tableName);

	/**
	 * Convert given <code>document</code> to {@link IDocument} interface
	 *
	 * @param document
	 * @return document as {@link IDocument}
	 * @throws IllegalArgumentException if document is null or it cannot be converted to {@link IDocument}
	 */
	IDocument getDocument(Object document);

	/**
	 * Convert given <code>document</code> to {@link IDocument} interface. If the document cannot be converted to {@link IDocument} null is returned.
	 *
	 * @param document
	 * @return document as {@link IDocument} or null
	 */
	IDocument getDocumentOrNull(Object document);

	/**
	 * Retrieve C_DocType_ID for given record. C_DocType_ID and C_DocTypeTarget_ID columns will be checked.
	 *
	 * Please note, is not necessary that the given table to be a true document.
	 */
	int getC_DocType_ID(Properties ctx, int AD_Table_ID, int Record_ID);

	I_C_DocType getDocTypeOrNull(Object model);

	/**
	 * Retrieve document status for given record.
	 */
	DocStatus getDocStatusOrNull(Object documentObj);

	/**
	 * Retrieve DocumentNo for given record. If no value was found, the model will be loaded and {@link #getDocumentNo(Object)} will be used.
	 *
	 * @param ctx
	 * @return document no
	 */
	String getDocumentNo(Properties ctx, final int adTableId, final int recordId);

	/**
	 * Retrieve DocumentNo for given model. Steps to fetch the DocumentNo are (in this order):
	 * <ul>
	 * <li>if <code>model</code> is a {@link IDocument} instance, then {@link IDocument#getDocumentNo()} will be used
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

	/**
	 * @deprecated Please directly use {@link DocStatus}'s methods
	 */
	@Deprecated
	boolean issDocumentDraftedOrInProgress(Object document);

	/**
	 * @deprecated Please directly use {@link DocStatus}'s methods
	 */
	@Deprecated
	boolean isDocumentCompleted(Object document);

	/**
	 * @deprecated Please directly use {@link DocStatus}'s methods
	 */
	@Deprecated
	boolean isDocumentClosed(Object document);

	/**
	 * @deprecated Please directly use {@link DocStatus}'s methods
	 */
	@Deprecated
	boolean isDocumentReversedOrVoided(Object document);

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
	 */
	LocalDate getDocumentDate(final Properties ctx, final int adTableID, final int recordId);

	/**
	 * Gets document summary
	 *
	 * @param model
	 * @return document summary or toString() in case the model is not a document.
	 */
	String getSummary(Object model);

	/**
	 * Return {@code true} if the given {@code model} has are {@code Reversal_ID} and its own ID is bigger than its reversal partner's ID.
	 * In other words, returns {@code true}, if the given {@code model} is the reversal and not the reversed document.
	 *
	 * @param model
	 * @return
	 */
	boolean isReversalDocument(Object model);

	/**
	 * Retrieve a map with DocAction Ref_List(135) values.
	 *
	 * @return
	 */
	Map<String, IDocActionItem> retrieveDocActionItemsIndexedByValue();

	interface IDocActionItem
	{
		String getValue();

		String getDescription();
	}
}
