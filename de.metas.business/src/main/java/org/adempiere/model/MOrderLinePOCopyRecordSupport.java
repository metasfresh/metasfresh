package org.adempiere.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.util.Services;
import org.adempiere.util.collections.CompositePredicate;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.PO;

import de.metas.freighcost.api.IFreightCostBL;

/**
 * metas version for copy an order line
 *
 * @author Cristina Ghita
 */
public class MOrderLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	/**
	 * Skip predicates: if it's evaluated <code>true</code> (i.e. {@link Predicate#evaluate(Object)} returns true) then the order line will NOT copied.
	 */
	private static final CompositePredicate<I_C_OrderLine> skipPredicates = new CompositePredicate<>();

	@Override
	public void copyRecord(final PO po, final String trxName)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		//
		// Exclude freight cost products
		final Properties ctx = InterfaceWrapperHelper.getCtx(po);
		if (Services.get(IFreightCostBL.class).isFreightCostProduct(ctx, orderLine.getM_Product_ID(), ITrx.TRXNAME_None))
		{
			return;
		}

		// Check if we shall skip this record
		if (!isCopyRecord(orderLine))
		{
			return;
		}

		// delegate to super
		super.copyRecord(po, trxName);
	}

	/**
	 * Add a skip filter.
	 *
	 * In case given skip filter evaluates the order line as true (i.e. {@link Predicate#evaluate(Object)} returns true) then the order line will NOT copied.
	 *
	 * @param skipPredicate
	 */
	public static final void addSkipPredicate(final Predicate<I_C_OrderLine> skipPredicate)
	{
		skipPredicates.addPredicate(skipPredicate);
	}

	/**
	 *
	 * @param orderLine
	 * @return true if the record shall be copied
	 */
	private final boolean isCopyRecord(final I_C_OrderLine orderLine)
	{
		// If there was no predicate registered
		// => accept this record
		if (skipPredicates.isEmpty())
		{
			return true;
		}

		// If skip predicates are advicing us to skip this record
		// => skip it
		if (skipPredicates.evaluate(orderLine))
		{
			return false;
		}

		//
		// Default: accept it
		return true;
	}
}
