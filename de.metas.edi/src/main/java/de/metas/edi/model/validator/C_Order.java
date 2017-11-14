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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.edi.api.IDesadvBL;
import de.metas.edi.api.IEDIInputDataSourceBL;
import de.metas.edi.model.I_C_BPartner;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_EDI_Document;

@Validator(I_C_Order.class)
public class C_Order
{

	public static final C_Order INSTANCE = new C_Order();

	private C_Order()
	{
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void assertReActivationAllowed(final I_C_Order order)
	{
		if (order.getEDI_Desadv_ID() <= 0)
		{
			return;
		}
		final String desadvEDIStatus = order.getEDI_Desadv().getEDI_ExportStatus();
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
	public void addToDesadv(final I_C_Order order)
	{
		if (!order.isSOTrx())
		{
			return;
		}
		if (Check.isEmpty(order.getPOReference()))
		{
			return;
		}
		final I_C_BPartner bpartner = InterfaceWrapperHelper.create(order.getC_BPartner(), I_C_BPartner.class);
		if (!bpartner.isEdiRecipient())
		{
			return;
		}

		if (!order.isEdiEnabled())
		{
			// do not add to desadv if not edi enabled
			return;
		}

		Services.get(IDesadvBL.class).addToDesadvCreateForOrderIfNotExist(order);
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSEACCRUAL,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID })
	public void removeFromDesadv(final I_C_Order order)
	{
		if (order.getEDI_Desadv_ID() > 0)
		{
			Services.get(IDesadvBL.class).removeOrderFromDesadv(order);
		}
	}

	/**
	 * @param order
	 * @task http://dewiki908/mediawiki/index.php/08926_EDI-Ausschalten_f%C3%BCr_bestimmte_Belege_%28109751792947%29
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_AD_InputDataSource_ID }
			)
	public void updateEdiEnabled(final I_C_Order order)
	{
		final int orderInputDataSourceId = order.getAD_InputDataSource_ID();

		if (orderInputDataSourceId <= 0)
		{
			// nothing to ro
			return;
		}

		final boolean isEdiEnabled = Services.get(IEDIInputDataSourceBL.class).isEDIInputDataSource(orderInputDataSourceId);
		if (isEdiEnabled)
		{
			order.setIsEdiEnabled(true);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = { I_C_Order.COLUMNNAME_C_BPartner_ID })
	public void onPartnerChange(final I_C_Order order)
	{

		final I_C_BPartner partner = InterfaceWrapperHelper.create(order.getC_BPartner(), de.metas.edi.model.I_C_BPartner.class);
		if (partner == null)
		{
			// nothing to do
			return;
		}

		final boolean isEdiRecipient = partner.isEdiRecipient();

		// in case the partner was changed and the new one is not an edi recipient, the order will not be edi enabled
		// If the new bp is edi recipient, we leave it to the user to set the flag or not

		if (!isEdiRecipient)
		{
			order.setIsEdiEnabled(false);
		}
	}


}
