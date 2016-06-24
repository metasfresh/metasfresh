package de.metas.rfq.model.interceptor;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.ModelValidator;

import de.metas.rfq.IRfqDAO;
import de.metas.rfq.model.I_C_RfQResponse;
import de.metas.rfq.model.I_C_RfQResponseLine;
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

@Interceptor(I_C_RfQResponse.class)
public class C_RfQResponse
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void beforeSave(final I_C_RfQResponse rfqResponse)
	{
		final IRfQWorkDatesAware workDatesAware = InterfaceWrapperHelper.create(rfqResponse, IRfQWorkDatesAware.class);
		RfQWorkDatesUtil.updateWorkDates(workDatesAware);
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void deleteLines(final I_C_RfQResponse rfqResponse)
	{
		for (final I_C_RfQResponseLine rfqResponseLine : Services.get(IRfqDAO.class).retrieveResponseLines(rfqResponse))
		{
			rfqResponseLine.setProcessed(false);
			InterfaceWrapperHelper.delete(rfqResponseLine);
		}
	}

}
