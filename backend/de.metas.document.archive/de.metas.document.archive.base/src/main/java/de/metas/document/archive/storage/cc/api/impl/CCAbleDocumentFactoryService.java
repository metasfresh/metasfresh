package de.metas.document.archive.storage.cc.api.impl;

import de.metas.document.archive.storage.cc.api.ICCAbleDocument;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactory;
import de.metas.document.archive.storage.cc.api.ICCAbleDocumentFactoryService;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.HashMap;
import java.util.Map;

public class CCAbleDocumentFactoryService implements ICCAbleDocumentFactoryService
{
	private final Map<String, ICCAbleDocumentFactory> factories = new HashMap<>();

	public CCAbleDocumentFactoryService()
	{
		registerCCAbleDocumentFactory(org.compiere.model.I_C_Order.Table_Name, new OrderCCAbleDocumentFactory());
	}

	@Override
	public void registerCCAbleDocumentFactory(
			@NonNull final String tableName,
			@NonNull final ICCAbleDocumentFactory factory)
	{
		Check.assumeNotEmpty(tableName, "tableName not empty");
		factories.put(tableName, factory);
	}

	@Override
	public ICCAbleDocument createCCAbleDocument(@NonNull final Object model)
	{
		final String tableName = InterfaceWrapperHelper.getModelTableName(model);
		final ICCAbleDocumentFactory factory = getFactoryByTableNameOrNull(tableName);
		if (factory == null)
		{
			throw new AdempiereException("No factory found for " + model);
		}
		return factory.createCCAbleDocumentAdapter(model);
	}

	private ICCAbleDocumentFactory getFactoryByTableNameOrNull(@NonNull final String tableName)
	{
		return factories.get(tableName);
	}

	@Override
	public boolean isTableNameSupported(@NonNull final String tableName)
	{
		final ICCAbleDocumentFactory factory = getFactoryByTableNameOrNull(tableName);
		return factory != null;
	}
}
