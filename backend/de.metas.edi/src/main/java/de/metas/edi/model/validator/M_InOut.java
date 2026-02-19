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
	 * If the given <code>inOut</code> is OK to be send as EDI, then we add it to a {@link de.metas.esb.edi.model.I_EDI_Desadv}.
	 * <p>
	 * Note that if the EDI-status changes to something else later on, the inOut shall remain assigned. Its not this MV's problem.
	 */
	@DocValidate(timings = ModelValidator.TIMING_BEFORE_COMPLETE)
	public void addToDesadv(final I_M_InOut inOut)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(inOut))
		{
			// no EDI for customer return (for the time being)
			return;
		}

		if (!ediDocumentBL.updateEdiExportStatus(inOut))
		{
			return;
		}

		if (EDIDesadvId.ofRepoIdOrNull(inOut.getEDI_Desadv_ID()) == null)
		{
			desadvBL.addToDesadvCreateForInOutIfNotExist(inOut);
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
}
