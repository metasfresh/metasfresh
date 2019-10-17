package de.metas.contracts.interceptor;

import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

/*
 * #%L
 * de.metas.contracts
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

import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.modelvalidator.annotations.Validator;
import org.compiere.model.ModelValidator;

import de.metas.adempiere.model.I_M_Product;
import de.metas.contracts.IFlatrateBL;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.util.Services;

@Validator(I_M_InOutLine.class)
public class M_InOutLine
{
	private final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE })
	public void updateFLatrateDataEntryQtyAdd(final I_M_InOutLine doc)
	{
		if (doc.getM_Product_ID() <= 0)
		{
			return; // nothing to do yet
		}

		flatrateBL.updateFlatrateDataEntryQty(
				loadOutOfTrx(doc.getM_Product_ID(),I_M_Product.class),
				doc.getMovementQty(),
				doc, // inOutLine
				false); // subtract = false
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_CHANGE, ModelValidator.TYPE_BEFORE_DELETE })
	public void updateFLatrateDataEntryQtySubstract(final I_M_InOutLine doc)
	{
		if (doc.getM_Product_ID() <= 0)
		{
			return; // nothing to do/avoid NPE
		}
		flatrateBL.updateFlatrateDataEntryQty(
				loadOutOfTrx(doc.getM_Product_ID(),I_M_Product.class),
				doc.getMovementQty(),
				doc, // inOutLine
				true); // subtract = true
	}
}
