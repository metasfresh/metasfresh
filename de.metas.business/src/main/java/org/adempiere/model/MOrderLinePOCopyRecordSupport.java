package org.adempiere.model;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.util.HashMap;
import java.util.Map;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;
import java.util.function.Predicate;

import org.adempiere.ad.trx.api.ITrx;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_Order_CompensationGroup;
import org.compiere.model.PO;

import de.metas.adempiere.model.I_C_Order;
import de.metas.freighcost.api.IFreightCostBL;
import de.metas.util.Services;
import de.metas.util.collections.CompositePredicate;

public class MOrderLinePOCopyRecordSupport extends GeneralCopyRecordSupport
{
	/**
	 * Skip predicates: if it's evaluated <code>true</code> (i.e. {@link Predicate#test(Object)} returns true) then the order line will NOT copied.
	 */
	private static final CompositePredicate<I_C_OrderLine> skipPredicates = new CompositePredicate<>();
	private static final String DYNATTR_OrderCompensationGroupIdsMap = "OrderCompensationGroupIdsMap";

	@Override
	public void copyRecord(final PO po, final String trxName)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(po, I_C_OrderLine.class);

		//
		// Exclude freight cost products
		final Properties ctx = InterfaceWrapperHelper.getCtx(po);
		if (Services.get(IFreightCostBL.class).isFreightCostProduct(ctx, orderLine.getM_Product_ID(), ITrx.TRXNAME_None))
		{
			return ;
		}

		// Check if we shall skip this record
		if (!isCopyRecord(orderLine))
		{
			return ;
		}

		// delegate to super
		super.copyRecord(po, trxName);
	}

	@Override
	protected void onRecordCopied(final PO to, final PO from)
	{
		final I_C_OrderLine toOrderLine = InterfaceWrapperHelper.create(to, I_C_OrderLine.class);
		final I_C_OrderLine fromOrderLine = InterfaceWrapperHelper.create(from, I_C_OrderLine.class);

		toOrderLine.setC_Order_CompensationGroup_ID(getOrCloneOrderCompensationGroup(fromOrderLine.getC_Order_CompensationGroup_ID()));
	}

	private final int getOrCloneOrderCompensationGroup(final int orderCompensationGroupId)
	{
		if (orderCompensationGroupId <= 0)
		{
			return -1;
		}

		final I_C_Order toOrder = getParentModel(I_C_Order.class);
		Map<Integer, Integer> orderCompensationGroupIdsMap = InterfaceWrapperHelper.getDynAttribute(toOrder, DYNATTR_OrderCompensationGroupIdsMap);
		if (orderCompensationGroupIdsMap == null)
		{
			orderCompensationGroupIdsMap = new HashMap<>();
			InterfaceWrapperHelper.setDynAttribute(toOrder, DYNATTR_OrderCompensationGroupIdsMap, orderCompensationGroupIdsMap);
		}

		final int toOrderId = toOrder.getC_Order_ID();
		final int orderCompensationGroupIdNew = orderCompensationGroupIdsMap.computeIfAbsent(orderCompensationGroupId, k -> cloneOrderCompensationGroup(orderCompensationGroupId, toOrderId));
		return orderCompensationGroupIdNew;
	}

	private static int cloneOrderCompensationGroup(final int orderCompensationGroupId, final int toOrderId)
	{
		final I_C_Order_CompensationGroup orderCompensationGroup = load(orderCompensationGroupId, I_C_Order_CompensationGroup.class);
		final I_C_Order_CompensationGroup orderCompensationGroupNew = newInstance(I_C_Order_CompensationGroup.class);
		InterfaceWrapperHelper.copyValues(orderCompensationGroup, orderCompensationGroupNew);
		orderCompensationGroupNew.setC_Order_ID(toOrderId);
		InterfaceWrapperHelper.save(orderCompensationGroupNew);
		return orderCompensationGroupNew.getC_Order_CompensationGroup_ID();
	}

	/**
	 * Add a skip filter.
	 *
	 * In case given skip filter evaluates the order line as true (i.e. {@link Predicate#test(Object)} returns true) then the order line will NOT copied.
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
		if (skipPredicates.test(orderLine))
		{
			return false;
		}

		//
		// Default: accept it
		return true;
	}
}
