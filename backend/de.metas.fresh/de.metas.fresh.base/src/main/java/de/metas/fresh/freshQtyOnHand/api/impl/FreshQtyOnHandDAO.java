package de.metas.fresh.freshQtyOnHand.api.impl;

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

import de.metas.fresh.freshQtyOnHand.FreshQtyOnHandId;
import de.metas.fresh.freshQtyOnHand.api.IFreshQtyOnHandDAO;
import de.metas.fresh.model.I_Fresh_QtyOnHand;
import de.metas.fresh.model.I_Fresh_QtyOnHand_Line;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import java.util.List;

public class FreshQtyOnHandDAO implements IFreshQtyOnHandDAO
{
	@Override
	public List<I_Fresh_QtyOnHand_Line> retrieveLines(final I_Fresh_QtyOnHand qtyOnHandHeader)
	{
		final List<I_Fresh_QtyOnHand_Line> result = Services.get(IQueryBL.class)
				.createQueryBuilder(I_Fresh_QtyOnHand_Line.class, qtyOnHandHeader)
				.addEqualsFilter(I_Fresh_QtyOnHand_Line.COLUMN_Fresh_QtyOnHand_ID, qtyOnHandHeader.getFresh_QtyOnHand_ID())
				.create()
				.list();

		// Optimization: set parent
		for (final I_Fresh_QtyOnHand_Line line : result)
		{
			line.setFresh_QtyOnHand(qtyOnHandHeader);
		}

		return result;
	}

	@NonNull
	public I_Fresh_QtyOnHand getById(@NonNull final FreshQtyOnHandId freshQtyOnHandId)
	{
		final I_Fresh_QtyOnHand freshQtyOnHandRecord = InterfaceWrapperHelper.load(freshQtyOnHandId, I_Fresh_QtyOnHand.class);
		if (freshQtyOnHandRecord == null)
		{
			throw new AdempiereException("@NotFound@: " + freshQtyOnHandId);
		}

		return freshQtyOnHandRecord;
	}
}
