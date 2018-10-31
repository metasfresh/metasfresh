/**
 *
 */
package de.metas.document.sequence;

import de.metas.document.DocumentNoBuilderException;
import de.metas.document.DocumentSequenceInfo;
import de.metas.document.sequence.impl.IPreliminaryDocumentNoBuilder;

public interface IDocumentNoBuilder
{
	public static final String NO_DOCUMENTNO = null;

	/** Sequence name prefix for Table Document Nos (also used for Value) */
	public static final String PREFIX_DOCSEQ = "DocumentNo_";

	/**
	 * Builds the DocumentNo string.
	 *
	 * @return DocumentNo string or {@link #NO_DOCUMENTNO}
	 * @throws DocumentNoBuilderException in case building fails
	 */
	String build() throws DocumentNoBuilderException;

	IDocumentNoBuilder setAD_Client_ID(int adClientId);

	IDocumentNoBuilder setDocumentSequenceInfo(DocumentSequenceInfo documentSeqInfo);

	IDocumentNoBuilder setDocumentSequenceInfoBySequenceId(int AD_Sequence_ID);

	IDocumentNoBuilder setFailOnError(boolean failOnError);

	/**
	 * Sets the document/model for which we are building the DocumentNo.
	 * The builder can use is to get {@code AD_Client_ID}, {@code AD_Org_ID}, {@code DocumentDate} and maybe more, in future.
	 *
	 * @param documentModel document/model or null
	 */
	IDocumentNoBuilder setDocumentModel(Object documentModel);

	/**
	 * Explicitly set which is the sequence number to be used.
	 * If set, then the builder won't fetch and increment current sequence number but it will just use this one.
	 */
	IDocumentNoBuilder setSequenceNo(String sequenceNo);

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
