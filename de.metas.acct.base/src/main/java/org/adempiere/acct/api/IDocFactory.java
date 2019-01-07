package org.adempiere.acct.api;

import java.util.List;
import java.util.Set;

import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.acct.Doc;
import org.compiere.acct.PostingExecutionException;

import de.metas.acct.api.AcctSchema;
import de.metas.util.ISingletonService;

/**
 * Accountable document factory. Use this interface to create the right {@link Doc} instance for your accountable document.
 * 
 * @author tsa
 *
 */
public interface IDocFactory extends ISingletonService
{
	/**
	 * @return Document or <code>null</code> if there is no such accountable document for given AD_Table_ID/Record_ID
	 * @throws PostingExecutionException if the document could not be created
	 */
	Doc<?> getOrNull(List<AcctSchema> acctSchemas, TableRecordReference documentRef);

	/**
	 * @return a list of all accountable documents (meta data), registered on system
	 */
	List<IDocMetaInfo> getDocMetaInfoList();

	Set<Integer> getDocTableIds();
}
