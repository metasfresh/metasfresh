package de.metas.inout.model.validator;

/*
 * #%L
 * de.metas.swat.base
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


import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.document.service.IDocumentLocationBL;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.I_M_MatchInv;
import org.compiere.model.ModelValidator;

import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.api.IInOutMovementBL;
import de.metas.inout.api.IMaterialBalanceDetailBL;
import de.metas.inout.api.IMaterialBalanceDetailDAO;
import de.metas.inout.event.InOutGeneratedEventBus;
import de.metas.inout.model.I_M_InOut;

@Validator(I_M_InOut.class)
public class M_InOut
{
	@Init
	public void onInit()
	{
		// Setup event bus topics on which swing client notification listener shall subscribe
		Services.get(IEventBusFactory.class).addAvailableUserNotificationsTopic(InOutGeneratedEventBus.EVENTBUS_TOPIC);
	}
	
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = {
					I_M_InOut.COLUMNNAME_C_BPartner_ID
					, I_M_InOut.COLUMNNAME_C_BPartner_Location_ID
					, I_M_InOut.COLUMNNAME_AD_User_ID })
	public void updateBPartnerAddress(final I_M_InOut doc)
	{
		Services.get(IDocumentLocationBL.class).setBPartnerAddress(doc);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE }
			, ifColumnsChanged = {
					I_M_InOut.COLUMNNAME_DropShip_BPartner_ID
					, I_M_InOut.COLUMNNAME_DropShip_Location_ID
					, I_M_InOut.COLUMNNAME_DropShip_User_ID })
	public void updateDeliveryToAddress(final I_M_InOut doc)
	{
		Services.get(IDocumentLocationBL.class).setDeliveryToAddress(doc);
	}

	/**
	 * Generate movements from receipt (if needed).
	 *
	 * This is the counter-part of {@link #reverseMovements(I_M_InOut)}.
	 *
	 * @param inout
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void generateMovement(final I_M_InOut inout)
	{
		// We are generating movements only for receipts
		if (inout.isSOTrx())
		{
			return;
		}

		// Don't generate movements for a reversal document
		if (Services.get(IInOutBL.class).isReversal(inout))
		{
			return;
		}

		// Actually generate the movements
		final IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);
		inoutMovementBL.generateMovementFromReceipt(inout);
	}

	/**
	 * Reverse linked movements.
	 *
	 * This is the counter-part of {@link #generateMovement(I_M_InOut)}.
	 *
	 * @param inout
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT
			, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL
			, ModelValidator.TIMING_BEFORE_VOID
			, ModelValidator.TIMING_BEFORE_REACTIVATE
	})
	public void reverseMovements(final I_M_InOut inout)
	{
		final IInOutMovementBL inoutMovementBL = Services.get(IInOutMovementBL.class);
		inoutMovementBL.reverseMovements(inout);
	}

	/**
	 * Reverse {@link I_M_MatchInv} assignments.
	 *
	 * @param inout
	 */
	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REVERSECORRECT
			, ModelValidator.TIMING_BEFORE_REVERSEACCRUAL
			, ModelValidator.TIMING_BEFORE_VOID
			, ModelValidator.TIMING_BEFORE_REACTIVATE
	})
	public void removeMatchInvAssignments(final I_M_InOut inout)
	{
		//
		// Services
		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final List<I_M_InOutLine> iols = inOutDAO.retrieveAllLines(inout);
		for (final I_M_InOutLine iol : iols)
		{
			inOutBL.deleteMatchInvsForInOutLine(iol); // task 08531
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_COMPLETE })
	public void addInoutToBalance(final I_M_InOut inout)
	{

		final boolean isReversal = Services.get(IInOutBL.class).isReversal(inout);

		// do nothing in case of reversal
		if (!isReversal)
		{
			final IMaterialBalanceDetailBL materialBalanceDetailBL = Services.get(IMaterialBalanceDetailBL.class);

			materialBalanceDetailBL.addInOutToBalance(inout);

		}
	}

	@DocValidate(timings = {
			ModelValidator.TIMING_BEFORE_REACTIVATE,
			ModelValidator.TIMING_BEFORE_REVERSECORRECT,
			ModelValidator.TIMING_BEFORE_VOID,
			ModelValidator.TIMING_AFTER_REVERSEACCRUAL,
			ModelValidator.TIMING_AFTER_CLOSE
	})
	public void removeInoutFromBalance(final I_M_InOut inout)
	{
		final IMaterialBalanceDetailDAO materialBalanceDetailDAO = Services.get(IMaterialBalanceDetailDAO.class);

		materialBalanceDetailDAO.removeInOutFromBalance(inout);
	}

}
