package de.metas.acct.callout;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import de.metas.document.sequence.IDocumentNoBuilder;
import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.api.ICalloutRecord;
import org.adempiere.ad.ui.spi.TabCalloutAdapter;
import org.compiere.model.I_C_TaxDeclaration;

public class C_TaxDeclaration_TabCallout extends TabCalloutAdapter
{
	@NonNull private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	@NonNull private final IDocumentNoBuilderFactory documentNoBuilderFactory = Services.get(IDocumentNoBuilderFactory.class);

	@Override
	public void onNew(final ICalloutRecord calloutRecord)
	{
		final I_C_TaxDeclaration taxDeclaration = calloutRecord.getModel(I_C_TaxDeclaration.class);

		if (taxDeclaration.getC_DocType_ID() <= 0)
		{
			taxDeclaration.setC_DocType_ID(docTypeBL.getDocTypeId(DocTypeQuery.builder()
					.adClientId(taxDeclaration.getAD_Client_ID())
					.adOrgId(taxDeclaration.getAD_Org_ID())
					.docBaseType(DocBaseType.TaxDeclaration)
					.build()).getRepoId());
		}

		final String documentNo = documentNoBuilderFactory
				.forTableName(I_C_TaxDeclaration.Table_Name, taxDeclaration.getAD_Client_ID(), taxDeclaration.getAD_Org_ID())
				.setDocumentModel(taxDeclaration)
				.setFailOnError(false)
				.setUsePreliminaryDocumentNo(true)
				.build();

		if (documentNo != IDocumentNoBuilder.NO_DOCUMENTNO)
		{
			taxDeclaration.setDocumentNo(documentNo);
		}
	}
}
