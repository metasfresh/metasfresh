package de.metas.ordercandidate.modelvalidator;

import de.metas.interfaces.I_C_OrderLine;
import de.metas.order.IOrderLineBL;
import de.metas.ordercandidate.api.IOLCandDAO;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.List;

/*
 * #%L
 * de.metas.swat.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
@Interceptor(I_C_OrderLine.class)
@Component
public class C_OrderLine
{
	private final IOrderLineBL orderLineBL = Services.get(IOrderLineBL.class);

	/**
	 * Method is fired before an order line is deleted. It deletes all {@link I_C_Order_Line_Alloc} records referencing the order line and sets <code>Processed='N'</code> for all {@link I_C_OLCand}s
	 * that were originally aggregated into the order line.
	 */
	// 03472
	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteOlas(final I_C_OrderLine ol)
	{
		final IOLCandDAO olCandDAO = Services.get(IOLCandDAO.class);
		final List<I_C_Order_Line_Alloc> olasToDelete = olCandDAO.retrieveAllOlas(ol);

		for (final I_C_Order_Line_Alloc ola : olasToDelete)
		{
			final I_C_OLCand olCand = ola.getC_OLCand();
			olCand.setProcessed(false);
			InterfaceWrapperHelper.save(olCand);

			InterfaceWrapperHelper.delete(ola);
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE,
			ifColumnsChanged = de.metas.interfaces.I_C_OrderLine.COLUMNNAME_IsManualDiscount)
	public void onManualDiscountChange(final I_C_OrderLine orderLine)
	{
		if (!orderLine.isManualDiscount())
		{
			orderLineBL.updatePrices(orderLine);
		}
	}
}
