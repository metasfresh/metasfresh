package de.metas.acct.interceptor;

import de.metas.acct.tax.TaxDeclarationId;
import de.metas.acct.tax.TaxDeclarationRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeBL;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_C_TaxDeclaration;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Component
@Interceptor(I_C_TaxDeclaration.class)
@RequiredArgsConstructor
public class C_TaxDeclaration
{
	@NonNull private final TaxDeclarationRepository taxDeclarationRepository;
	@NonNull private final IDocTypeBL docTypeBL;

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void defaultDocType(final I_C_TaxDeclaration taxDeclaration)
	{
		if (taxDeclaration.getC_DocType_ID() > 0)
		{
			return;
		}
		taxDeclaration.setC_DocType_ID(docTypeBL.getDocTypeId(DocTypeQuery.builder()
				.adClientId(taxDeclaration.getAD_Client_ID())
				.adOrgId(taxDeclaration.getAD_Org_ID())
				.docBaseType(DocBaseType.TaxDeclaration)
				.build()).getRepoId());
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteTaxDeclarationLinesAndAccts(final I_C_TaxDeclaration taxDeclaration)
	{
		taxDeclarationRepository.deleteChildRows(TaxDeclarationId.ofRepoId(taxDeclaration.getC_TaxDeclaration_ID()));
	}
}
