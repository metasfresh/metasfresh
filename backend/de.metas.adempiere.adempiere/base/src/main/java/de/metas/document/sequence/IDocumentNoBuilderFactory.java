/**
 *
 */
package de.metas.document.sequence;

import de.metas.document.sequence.impl.IPreliminaryDocumentNoBuilder;
import de.metas.util.ISingletonService;

public interface IDocumentNoBuilderFactory extends ISingletonService
{
	IPreliminaryDocumentNoBuilder createPreliminaryDocumentNoBuilder();

	/**
	 * Convenience method to create and prepare the builder for a given tableName.<br>
	 * (There doesn't have to be a "real" AD_Table).
	 *
	 * @param tableName the method will look for a sequence whose name is this table name, prepended with {@link IDocumentNoBuilder#PREFIX_DOCSEQ}.
	 */
	IDocumentNoBuilder forTableName(String tableName, int AD_Client_ID, int AD_Org_ID);

	/**
	 * Convenient method to create and prepare the builder for a given DocType.
	 *
	 * @param useDefiniteSequence if {@code true}, then the doc type's {@code DefiniteSequence_ID} is used.
	 */
	IDocumentNoBuilder forDocType(int C_DocType_ID, boolean useDefiniteSequence);

	IDocumentNoBuilder forSequenceId(DocSequenceId sequenceId);

	/**
	 * Create a builder that shall set the given modelRecord's {@code Value} column.
	 */
	IDocumentNoBuilder createValueBuilderFor(Object modelRecord);

	/**
	 * Creates a plain builder that does not contain any info yet.
	 * The user needs to invoke {@link IDocumentNoBuilder#setDocumentSequenceInfo(de.metas.document.DocumentSequenceInfo)}.
	 */
	IDocumentNoBuilder createDocumentNoBuilder();
}
