package de.metas.contracts;

import org.springframework.stereotype.Component;

import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;


@Component
public class ContractDocumentHandlerProvider implements DocumentHandlerProvider
{

	@Override
	public String getHandledTableName()
	{
		return I_C_Flatrate_Term.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model)
	{
		return new ContractDocumentHandler();
	}
}
