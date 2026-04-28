package de.metas.edi.model.validator;

import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIExportStatus;
import de.metas.edi.api.impl.EDIDocumentBL;
import de.metas.edi.api.impl.DesadvBL;
import de.metas.edi.model.I_M_InOut;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.util.Services;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_M_InOut.class)
@Component
@RequiredArgsConstructor
public class M_InOut
{
	@NonNull private final EDIDocumentBL ediDocumentBL;
	@NonNull private final DesadvBL desadvBL;

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_M_InOut.COLUMNNAME_C_BPartner_ID, I_M_InOut.COLUMNNAME_C_Order_ID, I_M_InOut.COLUMNNAME_POReference })
	public void updateEdiStatus(final I_M_InOut inOut)
	{
		ediDocumentBL.updateEdiExportStatus(inOut);
	}

	/**
	 * Validates the InOut and links it to a DESADV if eligible.
	 * <p>
	 * When {@link DesadvBL#isOneDesadvPerShipment(de.metas.esb.edi.model.I_EDI_Desadv)} is true and the InOut
	 * is linked to a DESADV that is currently Sent or DontSend, the DESADV is reopened to Pending
	 * via {@link DesadvBL#reopenDesadvIfNeeded(EDIDesadvId)}.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void addToDesadv(final I_M_InOut inOut)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(inOut))
		{
			return;
		}

		if (!ediDocumentBL.updateEdiExportStatus(inOut))
		{
			return;
		}

		final EDIDesadvId existingDesadvId = EDIDesadvId.ofRepoIdOrNull(inOut.getEDI_Desadv_ID());
		if (existingDesadvId == null)
		{
			desadvBL.addToDesadvCreateForInOutIfNotExist(inOut);
		}
		else
		{
			desadvBL.reopenDesadvIfNeeded(existingDesadvId);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void removeFromDesadv(final I_M_InOut inOut)
	{
		if (EDIDesadvId.ofRepoIdOrNull(inOut.getEDI_Desadv_ID()) != null)
		{
			desadvBL.removeInOutFromDesadv(inOut);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void updateEdiExportStatusOnReverse(final I_M_InOut inOut)
	{
		inOut.setEDI_ExportStatus(EDIExportStatus.DontSend.getCode());
	}

	/**
	 * Triggers DESADV status recomputation when an InOut's EDI_ExportStatus changes.
	 * <p>
	 * When {@link DesadvBL#isOneDesadvPerShipment(de.metas.esb.edi.model.I_EDI_Desadv)} is true,
	 * the DESADV status is derived from all linked InOut statuses
	 * via {@link DesadvBL#recomputeDesadvStatusFromInOuts(EDIDesadvId)}.
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE,
			ifColumnsChanged = I_M_InOut.COLUMNNAME_EDI_ExportStatus)
	public void recomputeDesadvStatusOnInOutStatusChange(final I_M_InOut inOut)
	{
		final EDIDesadvId desadvId = EDIDesadvId.ofRepoIdOrNull(inOut.getEDI_Desadv_ID());
		if (desadvId == null)
		{
			return;
		}

		desadvBL.recomputeDesadvStatusFromInOuts(desadvId);
	}
}
