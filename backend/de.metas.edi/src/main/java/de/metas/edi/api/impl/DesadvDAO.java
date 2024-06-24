/*
 * #%L
 * de.metas.edi
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.edi.api.impl;

import com.google.common.collect.ImmutableList;
import de.metas.edi.api.EDIDesadvId;
import de.metas.edi.api.EDIDesadvLineId;
import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.IQuery;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

public class DesadvDAO implements IDesadvDAO
{

	/**
	 * System configuration to tell the minimum sum percentage (QtyDeliveredInUOM/QtyEntered) that is accepted for a desadv entry
	 */
	private static final String SYS_CONFIG_DefaultMinimumPercentage = "de.metas.esb.edi.DefaultMinimumPercentage";
	private static final String SYS_CONFIG_DefaultMinimumPercentage_DEFAULT = "50";

	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@Override
	public I_EDI_Desadv retrieveMatchingDesadvOrNull(@NonNull final String poReference, @NonNull final IContextAware ctxAware)
	{
		Check.assumeNotEmpty(poReference, "Param 'poReference' is not emtpy; ctxAware={}", ctxAware);

		return queryBL.createQueryBuilder(I_EDI_Desadv.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv.COLUMN_POReference, poReference)
				.addEqualsFilter(I_EDI_Desadv.COLUMN_Processed, false)
				.addEqualsFilter(I_EDI_Desadv.COLUMN_Processing, false)
				.create()
				.firstOnly(I_EDI_Desadv.class);
	}

	@Override
	public I_EDI_Desadv retrieveById(final @NonNull EDIDesadvId ediDesadvId)
	{
		return InterfaceWrapperHelper.load(ediDesadvId, I_EDI_Desadv.class);
	}

	@Override
	public I_EDI_DesadvLine retrieveMatchingDesadvLinevOrNull(@NonNull final I_EDI_Desadv desadv, final int line)
	{
		return queryBL.createQueryBuilder(I_EDI_DesadvLine.class, desadv)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_Line, line)
				.create()
				.firstOnly(I_EDI_DesadvLine.class);
	}

	@Override
	public List<I_EDI_DesadvLine> retrieveAllDesadvLines(final I_EDI_Desadv desadv)
	{
		return createAllDesadvLinesQuery(desadv)
				.list(I_EDI_DesadvLine.class);
	}

	@Override
	public List<I_EDI_DesadvLine> retrieveLinesByIds(@NonNull final Collection<Integer> desadvLineIds)
	{
		if (desadvLineIds.isEmpty())
		{
			return ImmutableList.of();
		}

		final IQueryBL queryBL = Services.get(IQueryBL.class);
		return queryBL.createQueryBuilder(I_EDI_DesadvLine.class)
				.addInArrayFilter(I_EDI_DesadvLine.COLUMN_EDI_DesadvLine_ID, desadvLineIds)
				.create()
				.list();

	}

	@NonNull
	public I_EDI_DesadvLine retrieveLineById(@NonNull final EDIDesadvLineId ediDesadvLineId)
	{
		return InterfaceWrapperHelper.load(ediDesadvLineId, I_EDI_DesadvLine.class);
	}

	@Override
	public List<I_M_InOut> retrieveAllInOuts(final I_EDI_Desadv desadv)
	{
		return createAllInOutsQuery(desadv)
				.list(I_M_InOut.class);
	}

	@Override
	public boolean hasInOuts(final I_EDI_Desadv desadv)
	{
		return createAllInOutsQuery(desadv)
				.anyMatch();
	}

	private IQuery<I_M_InOut> createAllInOutsQuery(final I_EDI_Desadv desadv)
	{
		return queryBL.createQueryBuilder(I_M_InOut.class, desadv)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOut.COLUMNNAME_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.create();
	}

	@Override
	public List<I_M_InOutLine> retrieveAllInOutLines(final I_EDI_DesadvLine desadvLine)
	{
		return createAllInOutLinesQuery(desadvLine)
				.list(I_M_InOutLine.class);
	}

	@Override
	public boolean hasInOutLines(final I_EDI_DesadvLine desadvLine)
	{
		return createAllInOutLinesQuery(desadvLine)
				.anyMatch();
	}

	@Override
	public boolean hasOrderLines(final I_EDI_DesadvLine desadvLine)
	{
		return createAllOrderLinesQuery(desadvLine)
				.create()
				.anyMatch();
	}

	@Override
	public List<I_C_OrderLine> retrieveAllOrderLines(final I_EDI_DesadvLine desadvLine)
	{
		return createAllOrderLinesQuery(desadvLine)
				.create()
				.list(I_C_OrderLine.class);
	}

	@Override
	public boolean hasDesadvLines(final I_EDI_Desadv desadv)
	{
		return createAllDesadvLinesQuery(desadv)
				.anyMatch();
	}

	@Override
	public boolean hasOrders(final I_EDI_Desadv desadv)
	{
		return createAllOrdersQuery(desadv)
				.anyMatch();
	}

	@Override
	public List<I_C_Order> retrieveAllOrders(final I_EDI_Desadv desadv)
	{
		return createAllOrdersQuery(desadv)
				.list(I_C_Order.class);
	}

	private IQuery<I_M_InOutLine> createAllInOutLinesQuery(final I_EDI_DesadvLine desadvLine)
	{
		return queryBL.createQueryBuilder(I_M_InOutLine.class, desadvLine)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_EDI_DesadvLine_ID, desadvLine.getEDI_DesadvLine_ID())
				.create();
	}

	private IQuery<I_EDI_DesadvLine> createAllDesadvLinesQuery(final I_EDI_Desadv desadv)
	{
		return queryBL.createQueryBuilder(I_EDI_DesadvLine.class, desadv)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.create();
	}

	private IQuery<I_C_Order> createAllOrdersQuery(final I_EDI_Desadv desadv)
	{
		return queryBL.createQueryBuilder(I_C_Order.class, desadv)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.create();
	}

	private IQueryBuilder<I_C_OrderLine> createAllOrderLinesQuery(final I_EDI_DesadvLine desadvLine)
	{
		return queryBL.createQueryBuilder(I_C_OrderLine.class, desadvLine)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_EDI_DesadvLine_ID, desadvLine.getEDI_DesadvLine_ID());
	}

	@Override
	public de.metas.handlingunits.model.I_M_ShipmentSchedule retrieveM_ShipmentScheduleOrNull(@NonNull final I_EDI_DesadvLine desadvLine)
	{
		final IQueryBuilder<I_C_OrderLine> orderLinesQuery = createAllOrderLinesQuery(desadvLine);

		final IQueryBuilder<I_M_ShipmentSchedule> queryBuilder = orderLinesQuery
				.andCollectChildren(I_M_ShipmentSchedule.COLUMN_C_OrderLine_ID, I_M_ShipmentSchedule.class);

		queryBuilder.orderBy()
				.addColumn(I_M_ShipmentSchedule.COLUMN_M_ShipmentSchedule_ID);

		return queryBuilder.create().first(de.metas.handlingunits.model.I_M_ShipmentSchedule.class);
	}

	@Override
	public BigDecimal retrieveMinimumSumPercentage()
	{
		final String minimumPercentageAccepted_Value = Services.get(ISysConfigBL.class).getValue(
				SYS_CONFIG_DefaultMinimumPercentage, SYS_CONFIG_DefaultMinimumPercentage_DEFAULT);
		try
		{
			return new BigDecimal(minimumPercentageAccepted_Value);
		}
		catch (final NumberFormatException e)
		{
			Check.errorIf(true, "AD_SysConfig {} = {} can't be parsed as a number", SYS_CONFIG_DefaultMinimumPercentage, minimumPercentageAccepted_Value);
			return null; // shall not be reached
		}
	}

	@Override
	public void save(@NonNull final I_EDI_Desadv ediDesadv)
	{
		InterfaceWrapperHelper.save(ediDesadv);
	}
}
