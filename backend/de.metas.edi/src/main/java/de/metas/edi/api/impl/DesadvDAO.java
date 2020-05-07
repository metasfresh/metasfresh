package de.metas.edi.api.impl;

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

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.IContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.IQuery;

import de.metas.edi.api.IDesadvDAO;
import de.metas.edi.model.I_C_Order;
import de.metas.edi.model.I_C_OrderLine;
import de.metas.edi.model.I_M_InOut;
import de.metas.edi.model.I_M_InOutLine;
import de.metas.esb.edi.model.I_EDI_Desadv;
import de.metas.esb.edi.model.I_EDI_DesadvLine;
import de.metas.esb.edi.model.I_EDI_DesadvLine_SSCC;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;

public class DesadvDAO implements IDesadvDAO
{

	/**
	 * System configuration to tell the minimum sum percentage (QtyDeliveredInUOM/QtyEntered) that is accepted for a desadv entry
	 */
	private static final String SYS_CONFIG_DefaultMinimumPercentage = "de.metas.esb.edi.DefaultMinimumPercentage";
	private static final String SYS_CONFIG_DefaultMinimumPercentage_DEFAULT = "50";

	@Override
	public I_EDI_Desadv retrieveMatchingDesadvOrNull(final String poReference, final IContextAware ctxAware)
	{
		Check.assumeNotNull(ctxAware, "Param 'ctxAware' is not null");
		Check.assumeNotEmpty(poReference, "Param 'poReference' is not emtpy");

		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_Desadv.class, ctxAware)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_Desadv.COLUMN_POReference, poReference)
				.addEqualsFilter(I_EDI_Desadv.COLUMN_Processed, false)
				.addEqualsFilter(I_EDI_Desadv.COLUMN_Processing, false)
				.create()
				.firstOnly(I_EDI_Desadv.class);
	}

	@Override
	public I_EDI_DesadvLine retrieveMatchingDesadvLinevOrNull(final I_EDI_Desadv desadv, final int line)
	{
		Check.assumeNotNull(desadv, "Param 'desadv'");

		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_DesadvLine.class, desadv)
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
	public List<I_M_InOut> retrieveAllInOuts(final I_EDI_Desadv desadv)
	{
		return createAllInOutsQuery(desadv)
				.list(I_M_InOut.class);
	}

	@Override
	public boolean hasInOuts(I_EDI_Desadv desadv)
	{
		return createAllInOutsQuery(desadv)
				.match();
	}

	private IQuery<I_M_InOut> createAllInOutsQuery(final I_EDI_Desadv desadv)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InOut.class, desadv)
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
	public boolean hasInOutLines(I_EDI_DesadvLine desadvLine)
	{
		return createAllInOutLinesQuery(desadvLine)
				.match();
	}

	@Override
	public boolean hasOrderLines(I_EDI_DesadvLine desadvLine)
	{
		return createAllOrderLinesQuery(desadvLine)
				.create()
				.match();
	}

	@Override
	public List<I_C_OrderLine> retrieveAllOrderLines(I_EDI_DesadvLine desadvLine)
	{
		return createAllOrderLinesQuery(desadvLine)
				.create()
				.list(I_C_OrderLine.class);
	}

	@Override
	public boolean hasDesadvLines(I_EDI_Desadv desadv)
	{
		return createAllDesadvLinesQuery(desadv)
				.match();
	}

	@Override
	public boolean hasOrders(I_EDI_Desadv desadv)
	{
		return createAllOrdersQuery(desadv)
				.match();
	}

	@Override
	public List<I_C_Order> retrieveAllOrders(I_EDI_Desadv desadv)
	{
		return createAllOrdersQuery(desadv)
				.list(I_C_Order.class);
	}

	private IQuery<I_M_InOutLine> createAllInOutLinesQuery(final I_EDI_DesadvLine desadvLine)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_M_InOutLine.class, desadvLine)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_InOutLine.COLUMNNAME_EDI_DesadvLine_ID, desadvLine.getEDI_DesadvLine_ID())
				.create();
	}

	private IQuery<I_EDI_DesadvLine> createAllDesadvLinesQuery(final I_EDI_Desadv desadv)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_EDI_DesadvLine.class, desadv)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_DesadvLine.COLUMN_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.create();
	}

	private IQuery<I_C_Order> createAllOrdersQuery(I_EDI_Desadv desadv)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_Order.class, desadv)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Order.COLUMNNAME_EDI_Desadv_ID, desadv.getEDI_Desadv_ID())
				.create();
	}

	private IQueryBuilder<I_C_OrderLine> createAllOrderLinesQuery(final I_EDI_DesadvLine desadvLine)
	{
		return Services.get(IQueryBL.class).createQueryBuilder(I_C_OrderLine.class, desadvLine)
				// .addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_OrderLine.COLUMNNAME_EDI_DesadvLine_ID, desadvLine.getEDI_DesadvLine_ID());
	}

	@Override
	public List<I_EDI_DesadvLine_SSCC> retrieveDesadvLineSSCCs(final I_EDI_DesadvLine desadvLine)
	{
		return createDesadvLineSSCCsQuery(desadvLine)
				.create()
				.list();
	}

	@Override
	public int retrieveDesadvLineSSCCsCount(final I_EDI_DesadvLine desadvLine)
	{
		return createDesadvLineSSCCsQuery(desadvLine)
				.create()
				.count();
	}

	private IQueryBuilder<I_EDI_DesadvLine_SSCC> createDesadvLineSSCCsQuery(final I_EDI_DesadvLine desadvLine)
	{
		final IQueryBuilder<I_EDI_DesadvLine_SSCC> queryBuilder = Services.get(IQueryBL.class).createQueryBuilder(I_EDI_DesadvLine_SSCC.class, desadvLine)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_EDI_DesadvLine_SSCC.COLUMNNAME_EDI_DesadvLine_ID, desadvLine.getEDI_DesadvLine_ID());
		queryBuilder.orderBy()
				.addColumn(I_EDI_DesadvLine_SSCC.COLUMN_EDI_DesadvLine_SSCC_ID);

		return queryBuilder;
	}

	@Override
	public de.metas.handlingunits.model.I_M_ShipmentSchedule retrieveM_ShipmentScheduleOrNull(final I_EDI_DesadvLine desadvLine)
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
		catch (NumberFormatException e)
		{
			Check.errorIf(true, "AD_SysConfig {} = {} can't be parsed as a number", SYS_CONFIG_DefaultMinimumPercentage, minimumPercentageAccepted_Value);
			return null; // shall not be reached
		}
	}
}
