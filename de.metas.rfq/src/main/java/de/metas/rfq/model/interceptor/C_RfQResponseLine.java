package de.metas.rfq.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.rfq.IRfqDAO;
import de.metas.rfq.exceptions.RfQResponseLineHasReportedPriceException;
import de.metas.rfq.exceptions.RfQResponseLineHasReportedQtysException;
import de.metas.rfq.model.I_C_RfQResponseLine;
import de.metas.rfq.model.I_C_RfQResponseLineQty;
import de.metas.rfq.util.IRfQWorkDatesAware;
import de.metas.rfq.util.RfQWorkDatesUtil;

/*
 * #%L
 * de.metas.business
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

@Interceptor(I_C_RfQResponseLine.class)
public class C_RfQResponseLine
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_RfQResponseLine responseLine)
	{
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(responseLine, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateWorkDates(workDatesAware);

		if (!responseLine.isActive())
		{
			responseLine.setIsSelectedWinner(false);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_RfQResponseLine.COLUMNNAME_IsActive)
	public void deactivateLineQtys(final I_C_RfQResponseLine responseLine)
	{
		if (!responseLine.isActive())
		{
			final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
			for (final I_C_RfQResponseLineQty qty : rfqDAO.retrieveResponseQtys(responseLine))
			{
				if (qty.isActive())
				{
					qty.setIsActive(false);
					InterfaceWrapperHelper.save(qty);
				}
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void preventDeleteIfThereAreReportedQuantities(final I_C_RfQResponseLine rfqResponseLine)
	{
		if(rfqResponseLine.getPrice().signum() != 0)
		{
			throw new RfQResponseLineHasReportedPriceException(rfqResponseLine);
		}
		
		final IRfqDAO rfqDAO = Services.get(IRfqDAO.class);
		if (rfqDAO.hasResponseQtys(rfqResponseLine))
		{
			throw new RfQResponseLineHasReportedQtysException(rfqResponseLine);
		}
	}

}
