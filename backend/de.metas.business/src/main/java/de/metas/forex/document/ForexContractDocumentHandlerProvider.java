package de.metas.forex.document;

import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import de.metas.forex.ForexContractService;
import lombok.NonNull;
import org.compiere.model.I_C_ForeignExchangeContract;
import org.springframework.stereotype.Component;

@Component
public class ForexContractDocumentHandlerProvider implements DocumentHandlerProvider
{
	private final ForexContractService forexContractService;

	public ForexContractDocumentHandlerProvider(@NonNull final ForexContractService forexContractService) {this.forexContractService = forexContractService;}

	@Override
	public String getHandledTableName()
	{
		return I_C_ForeignExchangeContract.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model)
	{
		return new ForexContractDocumentHandler(forexContractService);
	}
}
