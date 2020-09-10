package de.metas.edi.model.validator;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_M_InOut;
import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.logging.TableRecordMDC;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InOut.COLUMNNAME_C_BPartner_ID)
	public void updateEdiStatus(final I_M_InOut document)
	{
		try (final MDCCloseable inOutMDC = TableRecordMDC.putTableRecordReference(document))
		{
			if (Services.get(IHUInOutBL.class).isCustomerReturn(document))
			{
				return; // no EDI for customer return (for the time being)
			}

			// make sure the inout is initialized with the ediEnabled flag from order, if the order is set
			setEdiEnabledFromOrder(document);

			final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
			ediDocumentBL.updateEdiEnabled(document);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InOut.COLUMNNAME_C_Order_ID)
	public void updateEdiEnabled(final I_M_InOut inout)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(inout))
		{
			return; // no EDI for customer return (for the time being)
		}
		setEdiEnabledFromOrder(inout);
	}

	private void setEdiEnabledFromOrder(@NonNull final I_M_InOut inout)
	{
		if (inout.getC_Order_ID() <= 0)
		{
			return; // nothing to do
		}

		final I_C_Order order = InterfaceWrapperHelper.create(inout.getC_Order(), de.metas.edi.model.I_C_Order.class);
		if (order == null || order.getC_Order_ID() <= 0)
		{
			// nothing to do
			return;
		}

		// order.isEdiEnabled might be for DESADV or INVOIC; so we also need to check the bpartner's flag
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(order.getC_BPartner(), I_C_BPartner.class);
		if (!bpartner.isEdiDesadvRecipient())
		{
			inout.setIsEdiEnabled(false);
			return;
		}

		final boolean isEdiEnabled = order.isEdiEnabled();
		inout.setIsEdiEnabled(isEdiEnabled);
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

		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
		if (!ediDocumentBL.updateEdiEnabled(inOut))
		{
			return;
		}

		if (inOut.getEDI_Desadv_ID() <= 0
				&& !Check.isEmpty(inOut.getPOReference(), true)) // task 08619: only try if we have a POReference and thus can succeed
		{
			Services.get(IDesadvBL.class).addToDesadvCreateForInOutIfNotExist(inOut);
		}
	}

	/**
	 * Calls {@link IDesadvBL#removeInOutFromDesadv(I_M_InOut)} to detach the given inout from it's desadv (if any) when it is reversed, reactivated etc. Also see
	 * {@link de.metas.handlingunits.model.validator.M_InOut#assertReActivationAllowed(org.compiere.model.I_M_InOut)}. Note that this method will also be fired if the inout's <code>C_Order</code> is reactivated.
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void removeFromDesadv(final I_M_InOut inOut)
	{
		if (inOut.getEDI_Desadv_ID() > 0)
		{
			Services.get(IDesadvBL.class).removeInOutFromDesadv(inOut);
		}
	}
}
