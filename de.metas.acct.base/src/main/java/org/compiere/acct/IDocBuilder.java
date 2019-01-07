package org.compiere.acct;

import java.util.List;

import org.adempiere.acct.api.IDocFactory;

import de.metas.acct.api.AcctSchema;

/**
 * Helper class used to create a new instance of {@link Doc}.
 * 
 * @author tsa
 *
 */
public interface IDocBuilder
{
	/** @return {@link Doc} instance */ 
	Doc<?> build();

	List<AcctSchema> getAcctSchemas();

	IDocBuilder setAcctSchemas(List<AcctSchema> acctSchemas);

	Object getDocumentModel();

	IDocBuilder setDocumentModel(Object documentModel);

	IDocFactory getDocFactory();
}
