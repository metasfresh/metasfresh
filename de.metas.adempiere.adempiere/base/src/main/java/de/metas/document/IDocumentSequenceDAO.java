package de.metas.document;

import java.util.Date;

import org.compiere.model.I_C_DocType;

import de.metas.util.ISingletonService;

/**
 * DAO methods for retrieving DocumentNo sequence informations.
 *
 * @author tsa
 *
 */
public interface IDocumentSequenceDAO extends ISingletonService
{
	DocumentSequenceInfo retriveDocumentSequenceInfo(String sequenceName, int adClientId, int adOrgId);

	DocumentSequenceInfo retriveDocumentSequenceInfo(int adSequenceId);

	String retrieveDocumentNoByYear(int AD_Sequence_ID, Date date);

	/** @return document type sequence map */
	DocTypeSequenceMap retrieveDocTypeSequenceMap(I_C_DocType docType);

	String retrieveDocumentNo(int AD_Sequence_ID);

	String retrieveDocumentNoSys(int AD_Sequence_ID);

}
