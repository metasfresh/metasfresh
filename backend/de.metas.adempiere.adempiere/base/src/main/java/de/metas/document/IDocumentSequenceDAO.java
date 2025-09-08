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

}
