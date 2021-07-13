package de.metas.document.archive.storage.cc.api;

import de.metas.util.ISingletonService;

public interface ICCAbleDocumentFactoryService extends ISingletonService
{
	void registerCCAbleDocumentFactory(String tableName, ICCAbleDocumentFactory factory);

	ICCAbleDocument createCCAbleDocument(Object model);
	
	boolean isTableNameSupported(String tableName);
}
