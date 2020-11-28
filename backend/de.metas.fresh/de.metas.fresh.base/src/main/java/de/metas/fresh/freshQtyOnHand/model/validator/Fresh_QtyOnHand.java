package de.metas.fresh.freshQtyOnHand.model.validator;

/*
 * #%L
 * de.metas.fresh.base
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


import java.sql.Timestamp;
import java.util.List;

import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.ModelValidator;

import de.metas.fresh.freshQtyOnHand.api.IFreshQtyOnHandDAO;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.util.Services;

@Interceptor(I_Fresh_QtyOnHand.class)
public class Fresh_QtyOnHand
{
	/**
	 * Update {@link I_Fresh_QtyOnHand_Line}s when their parent was changed.
	 * 
	 * It updates:
	 * <ul>
	 * <li> {@link I_Fresh_QtyOnHand_Line#COLUMN_DateDoc}
	 * </ul>
	 * 
	 * NOTE: we need to run this method on AFTER change because we also have {@link Fresh_QtyOnHand_Line} interceptor which will be triggered and to be consistent, the {@link I_Fresh_QtyOnHand} shall be
	 * up2date.
	 * 
	 * @param qtyOnHandHeader
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE
			, ifColumnsChanged = { I_Fresh_QtyOnHand.COLUMNNAME_DateDoc })
	public void updateLines(final I_Fresh_QtyOnHand qtyOnHandHeader)
	{
		final Timestamp dateDoc = qtyOnHandHeader.getDateDoc();

		final List<I_Fresh_QtyOnHand_Line> lines = Services.get(IFreshQtyOnHandDAO.class).retrieveLines(qtyOnHandHeader);
		for (final I_Fresh_QtyOnHand_Line line : lines)
		{
			line.setDateDoc(dateDoc);
			InterfaceWrapperHelper.save(line);
		}
	}
}
