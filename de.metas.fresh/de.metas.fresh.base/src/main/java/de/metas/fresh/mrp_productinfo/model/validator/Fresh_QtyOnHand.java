package de.metas.fresh.mrp_productinfo.model.validator;

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

import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;

import de.metas.fresh.freshQtyOnHand.api.IFreshQtyOnHandDAO;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.fresh.mrp_productinfo.async.spi.impl.UpdateMRPProductInfoTableWorkPackageProcessor;
import de.metas.util.Services;

@Interceptor(I_Fresh_QtyOnHand.class)
public class Fresh_QtyOnHand
{
	public static final Fresh_QtyOnHand INSTANCE = new Fresh_QtyOnHand();

	private Fresh_QtyOnHand()
	{
	}

	@ModelChange(timings = {
			ModelValidator.TYPE_BEFORE_CHANGE,
			ModelValidator.TYPE_BEFORE_DELETE },
			ifColumnsChanged = I_Fresh_QtyOnHand.COLUMNNAME_Processed)
	public void enqueueFreshQtyOnHandLines(final I_Fresh_QtyOnHand qtyOnHand)
	{
		final List<I_Fresh_QtyOnHand_Line> lines = Services.get(IFreshQtyOnHandDAO.class).retrieveLines(qtyOnHand);
		for(final I_Fresh_QtyOnHand_Line line:lines)
		{
			UpdateMRPProductInfoTableWorkPackageProcessor.schedule(line);
		}
	}
}
