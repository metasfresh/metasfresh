package de.metas.acct.tax.document;

import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.engine.DocumentHandler;
import de.metas.document.engine.DocumentHandlerProvider;
import lombok.NonNull;
import org.compiere.model.I_C_TaxDeclaration;
import org.springframework.stereotype.Component;

@Component
public class TaxDeclarationDocumentHandlerProvider implements DocumentHandlerProvider
{
	@NonNull private final TaxDeclarationRepository repo;

	public TaxDeclarationDocumentHandlerProvider(@NonNull final TaxDeclarationRepository repo)
	{
		this.repo = repo;
	}

	@Override
	public String getHandledTableName()
	{
		return I_C_TaxDeclaration.Table_Name;
	}

	@Override
	public DocumentHandler provideForDocument(final Object model_NOTUSED)
	{
		return new TaxDeclarationDocumentHandler(repo);
	}
}
