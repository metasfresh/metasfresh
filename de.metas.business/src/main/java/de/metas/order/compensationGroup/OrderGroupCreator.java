package de.metas.order.compensationGroup;

import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_C_OrderLine;

import com.google.common.collect.ImmutableList;

import lombok.NonNull;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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

public class OrderGroupCreator
{
	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);

	private List<I_C_OrderLine> _groupOrderLines;
	private int _compensationProductId = -1;

	public static OrderGroupCreator newInstance()
	{
		return new OrderGroupCreator();
	}

	private OrderGroupCreator()
	{
	}

	public OrderGroupCreator groupOrderLines(@NonNull final List<I_C_OrderLine> groupOrderLines)
	{
		_groupOrderLines = groupOrderLines.stream()
				.peek(OrderGroupCompensationUtils::assertNotCompensationLine)
				.peek(OrderGroupCompensationUtils::assertNotInGroup)
				.collect(ImmutableList.toImmutableList());
		return this;
	}

	private List<I_C_OrderLine> getGroupOrderLines()
	{
		Check.assumeNotEmpty(_groupOrderLines, "groupOrderLines is not empty");
		return _groupOrderLines;
	}

	public OrderGroupCreator compensationProductId(final int compensationProductId)
	{
		_compensationProductId = compensationProductId;
		return this;
	}

	private int getCompensationProductId()
	{
		Check.assume(_compensationProductId > 0, "compensationProductId > 0");
		return _compensationProductId;
	}

	public void createGroup()
	{
		final List<I_C_OrderLine> orderLines = getGroupOrderLines();
		orderLines.forEach(OrderGroupCompensationUtils::assertNotInGroup);

		final int orderId = orderLines.stream()
				.map(I_C_OrderLine::getC_Order_ID)
				.distinct()
				.collect(GuavaCollectors.singleElementOrThrow(() -> new AdempiereException("All order lines shall be from same order")));

		final int lastGroup = retrieveLastGroup(orderId);
		final int group = lastGroup + 1;

		orderLines.forEach(ol -> {
			ol.setGroupNo(group);
			save(ol);
		});

		OrderGroupCompensationLineProducer.newInstance()
				.groupOrderLines(orderLines)
				.compensationProductId(getCompensationProductId())
				.createCompensationLine();
	}

	private int retrieveLastGroup(final int orderId)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class)
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_C_Order_ID, orderId)
				.create()
				.maxInt(I_C_OrderLine.COLUMNNAME_GroupNo);
	}
}
