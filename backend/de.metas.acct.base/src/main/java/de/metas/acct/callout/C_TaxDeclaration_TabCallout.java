package de.metas.acct.callout;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
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

		defaultDocType(taxDeclaration);
		setPreliminaryDocumentNo(taxDeclaration);
	}

	private static void defaultDocType(final I_C_TaxDeclaration taxDeclaration)
	{
		if (taxDeclaration.getC_DocType_ID() > 0)
		{
			return;
		}
		final DocTypeQuery query = DocTypeQuery.builder()
				.adClientId(taxDeclaration.getAD_Client_ID())
				.adOrgId(taxDeclaration.getAD_Org_ID())
				.docBaseType(DocBaseType.TaxDeclaration)
				.build();
		taxDeclaration.setC_DocType_ID(Services.get(IDocTypeBL.class).getDocTypeId(query).getRepoId());
	}

	private static void setPreliminaryDocumentNo(final I_C_TaxDeclaration taxDeclaration)
	{
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
