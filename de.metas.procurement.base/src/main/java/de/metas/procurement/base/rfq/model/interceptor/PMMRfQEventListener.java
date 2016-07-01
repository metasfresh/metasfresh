package de.metas.procurement.base.rfq.model.interceptor;

import java.util.ArrayList;
import java.util.List;

import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;

import de.metas.procurement.base.IPMM_RfQ_BL;
import de.metas.procurement.base.rfq.model.I_C_RfQ;
import de.metas.rfq.event.RfQEventListenerAdapter;
import de.metas.rfq.model.I_C_RfQResponse;

/*
 * #%L
 * de.metas.procurement.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

public final class PMMRfQEventListener extends RfQEventListenerAdapter
{
	public static final transient PMMRfQEventListener instance = new PMMRfQEventListener();

	private PMMRfQEventListener()
	{
		super();
	}

	@Override
	public void onBeforeComplete(final de.metas.rfq.model.I_C_RfQ rfq)
	{
		if (!Services.get(IPMM_RfQ_BL.class).isProcurement(rfq))
		{
			return;
		}

		final I_C_RfQ pmmRfq = InterfaceWrapperHelper.create(rfq, I_C_RfQ.class);
		validatePMM_RfQ(pmmRfq);
	}

	private void validatePMM_RfQ(final I_C_RfQ pmmRfq)
	{
		//
		// Make sure mandatory fields are filled
		final List<String> notFilledMandatoryColumns = new ArrayList<>();
		if (pmmRfq.getC_Flatrate_Conditions_ID() <= 0)
		{
			notFilledMandatoryColumns.add(I_C_RfQ.COLUMNNAME_C_Flatrate_Conditions_ID);
		}
		if (pmmRfq.getDateWorkStart() == null)
		{
			notFilledMandatoryColumns.add(I_C_RfQ.COLUMNNAME_DateWorkStart);
		}
		if (pmmRfq.getDateWorkComplete() == null)
		{
			notFilledMandatoryColumns.add(I_C_RfQ.COLUMNNAME_DateWorkComplete);
		}
		if (pmmRfq.getDateResponse() == null)
		{
			notFilledMandatoryColumns.add(I_C_RfQ.COLUMNNAME_DateResponse);
		}
		//
		if(!notFilledMandatoryColumns.isEmpty())
		{
			throw new FillMandatoryException(false, notFilledMandatoryColumns);
		}
	}
	
	@Override
	public void onAfterClose(final I_C_RfQResponse rfqResponse)
	{
		Services.get(IPMM_RfQ_BL.class).createDraftContractsForSelectedWinners(rfqResponse);
	}
}
