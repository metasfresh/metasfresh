package de.metas.edi.model.validator;

/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IEDIDocumentBL;
import de.metas.edi.api.ValidationState;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_EDI_Document;
import de.metas.edi.model.I_M_InOut;
import de.metas.handlingunits.inout.IHUInOutBL;

@Validator(I_M_InOut.class)
public class M_InOut
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InOut.COLUMNNAME_C_BPartner_ID)
	public void updateEdiStatus(final I_M_InOut document)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(document))
		{
			// no EDI for customer return (for the time being)
			return;
		}

		// make sure the inout is initialized with the ediEnabled flag from order, if the order is set
		setEdiEnabledFromOrder(document);

		final IEDIDocumentBL ediDocumentBL = Services.get(IEDIDocumentBL.class);
		if (!ediDocumentBL.updateEdiEnabled(document))
		{
			return;
		}

		final List<Exception> feedback = ediDocumentBL.isValidInOut(document);

		final String EDIStatus = document.getEDI_ExportStatus();
		final ValidationState validationState = ediDocumentBL.updateInvalid(document, EDIStatus, feedback, false); // saveLocally=false

		if (ValidationState.INVALID == validationState)
		{
			// document.setIsEdiEnabled(false); // DON'T set this to false, because then the "revalidate" button is also not available (displaylogic)
			// IsEdiEnabled means "enabled in general", not "valid document and can be send right now"
			document.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Invalid);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }, ifColumnsChanged = I_M_InOut.COLUMNNAME_C_Order_ID)
	public void updateEdiEnabled(final I_M_InOut inout)
	{
		setEdiEnabledFromOrder(inout);
	}

	private void setEdiEnabledFromOrder(final I_M_InOut inout)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(inout))
		{
			// no EDI for customer return (for the time being)
			return;
		}

		final I_C_Order order = InterfaceWrapperHelper.create(inout.getC_Order(), de.metas.edi.model.I_C_Order.class);
		if (order == null || order.getC_Order_ID() <= 0)
		{
			// nothing to do
			return;
		}

		final boolean isEdiEnabled = order.isEdiEnabled();

		inout.setIsEdiEnabled(isEdiEnabled);

		// Also update the EDI Export Status to "DontSend"

		if (!isEdiEnabled)
		{
			inout.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_DontSend);
		}

	}

// @formatter:off
// Commenting this out for now because we don't have real validation code for DESADVs we send it to the ESB which does the evaluation.
//	/**
//	 * If the given inOut is associated with the a DESADV and if it is invalid, then also change the DESADV's status accordingly.
//	 * 
//	 * @param inOut
//	 */
//	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
//			, ifColumnsChanged = I_M_InOut.COLUMNNAME_EDI_ExportStatus)
//	public void updateDesadvStatus(final I_M_InOut inOut)
//	{
//		if (inOut.getEDI_Desadv_ID() > 0 && I_EDI_Document.EDI_EXPORTSTATUS_Invalid.equals(inOut.getEDI_ExportStatus()))
//		{
//			final I_EDI_Desadv desadv = inOut.getEDI_Desadv();
//			desadv.setEDI_ExportStatus(I_EDI_Document.EDI_EXPORTSTATUS_Invalid);
//			InterfaceWrapperHelper.save(desadv);
//		}
//	}
// @formatter:on

	/**
	 * Checks if the given inOut can be reverted, voided etc. The goal is to prevent such changes for InOuts that were already sent via EDI.
	 * 
	 * @param inOut
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void assertReActivationAllowed(final I_M_InOut inOut)
	{
		if (Services.get(IHUInOutBL.class).isCustomerReturn(inOut))
		{
			// no EDI for customer return (for the time being)
			return;
		}

		final String inOutEDIStatus = inOut.getEDI_ExportStatus();
		if (I_EDI_Document.EDI_EXPORTSTATUS_Enqueued.equals(inOutEDIStatus)
				|| I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted.equals(inOutEDIStatus)
				|| I_EDI_Document.EDI_EXPORTSTATUS_Sent.equals(inOutEDIStatus))
		{
			throw new AdempiereException("@NotAllowed@ (@EDIStatus@: " + inOutEDIStatus + ")");
		}

		if (inOut.getEDI_Desadv_ID() <= 0)
		{
			return;
		}
		final String desadvEDIStatus = inOut.getEDI_Desadv().getEDI_ExportStatus();
		if (I_EDI_Document.EDI_EXPORTSTATUS_Enqueued.equals(desadvEDIStatus)
				|| I_EDI_Document.EDI_EXPORTSTATUS_SendingStarted.equals(desadvEDIStatus)
				|| I_EDI_Document.EDI_EXPORTSTATUS_Sent.equals(desadvEDIStatus))
		{
			throw new AdempiereException("@NotAllowed@ (@EDI_Desadv_ID@ @EDIStatus@: " + desadvEDIStatus + ")");
		}
	}

	/**
	 * If the given <code>inOut</code> is OK to be send as EDI, then we add it to a {@link de.metas.esb.edi.model.I_EDI_Desadv}.
	 * <p>
	 * Note that if the EDI-status changes to something else later on, the inOut shall remain assigned. Its not this MV's problem.
	 *
	 * @param inOut
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
	 * {@link #assertReActivationAllowed(I_M_InOut)}. Note that this method will also be fired if the inout's <code>C_Order</code> is reactivated.
	 * 
	 * @param inOut
	 * 
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
