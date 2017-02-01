/**
 *
 */
package de.metas.document.documentNo;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.documentNo.impl.IPreliminaryDocumentNoBuilder;

/**
 * @author cg
 *
 */
public interface IDocumentNoBuilder
{
	public static final String NO_DOCUMENTNO = null;

	/**
	 * Builds the DocumentNo string.
	 *
	 * @return DocumentNo string or {@link #NO_DOCUMENTNO}
	 * @throws DocumentNoBuilderException in case building fails
	 */
	String build() throws DocumentNoBuilderException;

	IDocumentNoBuilder setAD_Client_ID(int adClientId);

	IDocumentNoBuilder setDocumentSequenceInfo(DocumentSequenceInfo documentSeqInfo);

	IDocumentNoBuilder setDocumentSequenceInfoByTableName(String tableName, int adClientId, int adOrgId);

	IDocumentNoBuilder setDocumentSequenceByDocTypeId(int C_DocType_ID, boolean useDefiniteSequence);

	IDocumentNoBuilder setFailOnError(boolean failOnError);

	/**
	 * Sets the document/model for which we are building the DocumentNo.
	 * 
	 * @param documentModel document/model or null
	 */
	IDocumentNoBuilder setDocumentModel(Object documentModel);

	/**
	 * Explicitly set which is the sequence no to be used. In this case, the builder won't fetch and increment current sequence number but it will just use this one.
	 *
	 * @param sequenceNo
	 */
	IDocumentNoBuilder setSequenceNo(int sequenceNo);

	/**
	 * Does nothing!
	 * We are keeping this method here just to keep the old logic (in case we want to turn on trxName usage).
	 * 
	 * @param trxName
	 * @return
	 */
	IDocumentNoBuilder setTrxName(String trxName);

	/**
	 * Advises the builder to use a preliminary DocumentNo.
	 * 
	 * If enabled, the builder:
	 * <ul>
	 * <li>will just fetch the current next DocumentNo without incrementing it
	 * <li>will return a DocumentNo which is wrapped with preliminary markers (see {@link IPreliminaryDocumentNoBuilder#withPreliminaryMarkers(String)}).
	 * </ul>
	 * 
	 * @param usePreliminaryDocumentNo
	 */
	IDocumentNoBuilder setUsePreliminaryDocumentNo(boolean usePreliminaryDocumentNo);
}
