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
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.util.Services;

@Interceptor(I_EDI_DesadvLine.class)
@Component
public class EDI_DesadvLine
{
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDesadvLineDelete(final I_EDI_DesadvLine desadvLine)
	{
		final List<I_M_InOutLine> allInOutLines = Services.get(IDesadvDAO.class).retrieveAllInOutLines(desadvLine);
		for (final I_M_InOutLine inOutLine : allInOutLines)
		{
			inOutLine.setEDI_DesadvLine_ID(0);
			InterfaceWrapperHelper.save(inOutLine);
		}

		final List<I_C_OrderLine> allOrderLines = Services.get(IDesadvDAO.class).retrieveAllOrderLines(desadvLine);
		for (final I_C_OrderLine orderLine : allOrderLines)
		{
			orderLine.setEDI_DesadvLine_ID(0);
			InterfaceWrapperHelper.save(orderLine);
		}
	}
}
