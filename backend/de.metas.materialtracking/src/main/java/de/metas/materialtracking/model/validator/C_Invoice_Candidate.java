package de.metas.materialtracking.model.validator;

import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.table.api.AdTableId;
import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.cache.model.impl.TableRecordCacheLocal;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.invoicecandidate.api.IInvoiceCandidateListeners;
import de.metas.materialtracking.IMaterialTrackingBL;
import de.metas.materialtracking.MTLinkRequest;
import de.metas.materialtracking.model.IMaterialTrackingAware;
import de.metas.materialtracking.model.I_C_Invoice_Candidate;
import de.metas.materialtracking.model.I_M_Material_Tracking;
import de.metas.materialtracking.qualityBasedInvoicing.IQualityInspectionHandlerDAO;
import de.metas.materialtracking.qualityBasedInvoicing.ic.spi.impl.MaterialTrackingInvoiceCandidateListener;
import de.metas.util.Services;

@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	@Init
	public void onInit()
	{
		final IInvoiceCandidateListeners invoiceCandidateListeners = Services.get(IInvoiceCandidateListeners.class);
		invoiceCandidateListeners.addListener(MaterialTrackingInvoiceCandidateListener.instance);
	}

	/**
	 * Checks if the given <code>ic</code> references a record that is already tracked. If that is the case, the ic's
	 * {@link I_C_Invoice_Candidate#COLUMNNAME_M_Material_Tracking_ID M_Material_Tracking_ID} is set to the referenced object's tracking ID.
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void referenceMaterialTracking(final I_C_Invoice_Candidate ic)
	{
		final Object referencedObject = getReferencedObjectOrNull(ic);
		Services.get(IQualityInspectionHandlerDAO.class).updateICFromMaterialTracking(ic, referencedObject);
	}

	private Object getReferencedObjectOrNull(final I_C_Invoice_Candidate ic)
	{
		final Object referencedObject;

		if (ic.getC_OrderLine_ID() > 0)
		{
			referencedObject = ic.getC_OrderLine();
		}
		else
		{
			final AdTableId adTableId = AdTableId.ofRepoIdOrNull(ic.getAD_Table_ID());
			final String tableName = adTableId != null
					? Services.get(IADTableDAO.class).retrieveTableName(adTableId)
					: null;
					
			if (tableName != null && I_M_InOutLine.Table_Name.equals(tableName))
			{
				referencedObject = TableRecordCacheLocal.getReferencedValue(ic, I_M_InOutLine.class);
			}
			else
			{
				referencedObject = null;
			}
		}
		return referencedObject;
	}

	/**
	 * Checks if the given <code>ic</code> has {@link I_C_Invoice_Candidate#COLUMNNAME_M_Material_Tracking_ID M_Material_Tracking_ID} <code>>0</code>. If that is the
	 * case, it is also linked to the tracking using {@link IMaterialTrackingBL#linkModelToMaterialTracking(Object, I_M_Material_Tracking)}.
	 *
	 * @param ic
	 */
	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void linkToMaterialTracking(final I_C_Invoice_Candidate ic)
	{
		final boolean createLink = true;
		updateLinkToTrackingIfNotNull(ic, createLink);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void unlinkFromMaterialTracking(final I_C_Invoice_Candidate ic)
	{
		final boolean createLink = false; // i.e. remove the link
		updateLinkToTrackingIfNotNull(ic, createLink);
	}

	private void updateLinkToTrackingIfNotNull(final I_C_Invoice_Candidate ic, final boolean createLink)
	{
		final IMaterialTrackingAware materialTrackingAware = InterfaceWrapperHelper.asColumnReferenceAwareOrNull(ic, IMaterialTrackingAware.class);
		if (materialTrackingAware.getM_Material_Tracking_ID() <= 0)
		{
			return;
		}

		final IMaterialTrackingBL materialTrackingBL = Services.get(IMaterialTrackingBL.class);
		if (createLink)
		{
			materialTrackingBL.linkModelToMaterialTracking(MTLinkRequest.builder()
					.model(ic)
					.materialTrackingRecord(ic.getM_Material_Tracking())
					.build());
		}
		else
		{
			materialTrackingBL.unlinkModelFromMaterialTrackings(ic);
		}
	}
}
