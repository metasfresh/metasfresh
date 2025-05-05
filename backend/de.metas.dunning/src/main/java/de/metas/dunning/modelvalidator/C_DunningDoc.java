package de.metas.dunning.modelvalidator;

import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.location.IDocumentLocationBL;
import de.metas.dunning.api.impl.DunningDocDocumentLocationAdapterFactory;
import de.metas.dunning.export.async.C_DunningDoc_CreateExportData;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.util.Services;
import de.metas.workflow.api.IWorkflowBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.SpringContextHolder;
import org.compiere.model.ModelValidator;

@Validator(I_C_DunningDoc.class)
public class C_DunningDoc
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_NEW)
	public void setDocTypeId(final I_C_DunningDoc dunningDoc)
	{
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.adClientId(dunningDoc.getAD_Client_ID())
				.adOrgId(dunningDoc.getAD_Org_ID())
				.docBaseType(DocBaseType.DunningDoc)
				.build();

		final DocTypeId docTypeId = docTypeDAO.getDocTypeId(query);
		dunningDoc.setC_DocType_ID(docTypeId.getRepoId());
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, //
			ifColumnsChanged = I_C_DunningDoc.COLUMNNAME_Processed)
	public void createDocResponsible(final I_C_DunningDoc dunningDoc)
	{
		if (dunningDoc.isProcessed())
		{
			Services.get(IWorkflowBL.class).createDocResponsible(dunningDoc, dunningDoc.getAD_Org_ID());
		}
	}

	/**
	 * Updates <code>C_DunningDoc.BPartnerAddress</code> when the <code>C_BPartner_Location_ID</code> column is changed.
	 * Task 07359
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_DunningDoc.COLUMNNAME_C_BPartner_Location_ID)
	public void updateAddressField(final I_C_DunningDoc dunningDoc)
	{
		final IDocumentLocationBL documentLocationBL = SpringContextHolder.instance.getBean(IDocumentLocationBL.class);
		documentLocationBL.updateRenderedAddressAndCapturedLocation(DunningDocDocumentLocationAdapterFactory.locationAdapter(dunningDoc));
	}

	@DocValidate(timings = ModelValidator.TIMING_AFTER_COMPLETE)
	public void scheduleDataExport(final I_C_DunningDoc dunningDoc)
	{
		C_DunningDoc_CreateExportData.scheduleOnTrxCommit(dunningDoc);
	}
}
