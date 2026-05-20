package de.metas.acct.callout;

import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_TabCallout extends TabCalloutAdapter
{
	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_TaxDeclaration taxDeclaration = calloutRecord.getModel(I_C_TaxDeclaration.class);

		final String documentNo = Services.get(IDocumentNoBuilderFactory.class)
				.forTableName(I_C_TaxDeclaration.Table_Name, taxDeclaration.getAD_Client_ID(), taxDeclaration.getAD_Org_ID())
				.setDocumentModel(taxDeclaration)
				.setFailOnError(false)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (documentNo == IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			return;
		}

		taxDeclaration.setDocumentNo(documentNo);
	}
}
