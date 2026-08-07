package de.metas.document;

import de.metas.document.sequence.DocSequenceId;
import de.metas.util.ISingletonService;
import lombok.NonNull;
import org.compiere.model.I_C_DocType;

import javax.annotation.Nullable;
import java.util.Date;

/**
 * DAO methods for retrieving DocumentNo sequence informations.
 *
 * @author tsa
 *
 */
public interface IDocumentSequenceDAO extends ISingletonService
{
	DocumentSequenceInfo retriveDocumentSequenceInfo(@NonNull String sequenceName, int adClientId, int adOrgId);

	DocumentSequenceInfo retriveDocumentSequenceInfo(DocSequenceId sequenceId);

	@Nullable
	@Deprecated
	default DocumentSequenceInfo retriveDocumentSequenceInfo(final int adSequenceRepoId)
	{
		final DocSequenceId adSequenceId = DocSequenceId.ofRepoIdOrNull(adSequenceRepoId);
		return adSequenceId != null ? retriveDocumentSequenceInfo(adSequenceId) : null;
	}

	String retrieveDocumentNoByYear(int AD_Sequence_ID, Date date);

	String retrieveDocumentNoByYearAndMonth(int AD_Sequence_ID, Date date);

	String retrieveDocumentNoByYearMonthAndDay(final int AD_Sequence_ID, java.util.Date date);

	/** @return document type sequence map */
	DocTypeSequenceMap retrieveDocTypeSequenceMap(I_C_DocType docType);

	String retrieveDocumentNo(int AD_Sequence_ID);

	String retrieveDocumentNoSys(int AD_Sequence_ID);

	/**
	 * Atomically ensures the sequence's {@code CurrentNext} is at least {@code value + 1}; never decreases it.
	 * <p>
	 * Executes a single {@code UPDATE AD_Sequence SET CurrentNext = GREATEST(CurrentNext, ?) WHERE AD_Sequence_ID = ?}.
	 * Because it is one statement, PostgreSQL's row-level write lock serializes it against concurrent draws/advances of
	 * the same sequence — the same atomic-statement guarantee {@link de.metas.document.sequence.impl.DocumentNoBuilder}
	 * relies on to draw numbers race-free. Call it after assigning an explicit number to a record so that a later
	 * draw ({@code IDocumentNoBuilder}) cannot re-issue that same number.
	 *
	 * @param sequenceId the sequence to advance
	 * @param value      advance {@code CurrentNext} to at least {@code value + 1}
	 */
	void advanceCurrentNextPast(@NonNull DocSequenceId sequenceId, int value);

}
