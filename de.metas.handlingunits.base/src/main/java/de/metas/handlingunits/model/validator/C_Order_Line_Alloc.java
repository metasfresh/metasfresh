package de.metas.handlingunits.model.validator;

/*
 * #%L
 * de.metas.handlingunits.base
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


import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.handlingunits.model.I_C_OLCand;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.ordercandidate.model.I_C_Order_Line_Alloc;
import de.metas.util.Check;

@Validator(I_C_Order_Line_Alloc.class)
public class C_Order_Line_Alloc
{
	@ModelChange(
			timings = {
					ModelValidator.TYPE_AFTER_NEW,
					ModelValidator.TYPE_AFTER_CHANGE
			})
	public void onNewOrderLineAlloc(final I_C_Order_Line_Alloc ola)
	{

		final org.compiere.model.I_C_OrderLine orderLine = ola.getC_OrderLine();
		final de.metas.ordercandidate.model.I_C_OLCand olcand = ola.getC_OLCand();

		Check.assumeNotNull(orderLine, "orderline is null");
		Check.assumeNotNull(olcand, "olcand is null");

		final I_C_OrderLine ol = InterfaceWrapperHelper.create(orderLine, de.metas.handlingunits.model.I_C_OrderLine.class);
		final I_C_OLCand oc = InterfaceWrapperHelper.create(olcand, de.metas.handlingunits.model.I_C_OLCand.class);

		if (oc.isManualQtyItemCapacity())
		{
			ol.setQtyItemCapacity(oc.getQtyItemCapacity());
		}

		final Integer valueOverrideOrValue = InterfaceWrapperHelper.getValueOverrideOrValue(oc, I_C_OLCand.COLUMNNAME_M_HU_PI_Item_Product_ID);
		final int piipID = valueOverrideOrValue == null ? 0 : valueOverrideOrValue;
		ol.setM_HU_PI_Item_Product_ID(piipID);

		ol.setIsManualQtyItemCapacity(oc.isManualQtyItemCapacity());

		InterfaceWrapperHelper.save(ol);
		InterfaceWrapperHelper.save(oc);
	}
}
