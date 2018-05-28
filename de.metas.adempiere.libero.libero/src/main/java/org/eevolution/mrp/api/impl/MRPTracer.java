package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_S_Resource;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_MRP_Alloc;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDAO;

import de.metas.material.planning.IMRPSegment;

/**
 * Helper class to help developer tracing shits while debugging.
 *
 * Calls to this method, if not in unit testings, shall never ever be allowed in code on commit.
 *
 * @author tsa
 *
 */
public final class MRPTracer
{
	private MRPTracer()
	{
		super();
	}

	public static void dumpMRPRecords()
	{
		dumpMRPRecords("MRP records");
	}

	public static void dumpMRPRecords(final String message)
	{
		System.out.println(dumpMRPRecordsToString(message));
	}

	public static String dumpMRPRecordsToString(final String message)
	{
		// services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IMRPDAO mrpDAO = Services.get(IMRPDAO.class);

		final IContextAware contextProvider = new PlainContextAware(Env.getCtx());

		// String writer
		final ByteArrayOutputStream baos = new ByteArrayOutputStream();
		final PrintStream out = new PrintStream(baos);

		final IQueryBuilder<I_PP_MRP> queryBuilder = queryBL
				.createQueryBuilder(I_PP_MRP.class, contextProvider);

		queryBuilder.orderBy()
				.addColumn(I_PP_MRP.COLUMNNAME_M_Product_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_AD_Org_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_S_Resource_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_M_Warehouse_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_PP_MRP_ID);

		out.println("");
		out.println("=========[ " + message + " ]===========================================================================");
		IMRPSegment mrpSegmentPrevious = null;
		BigDecimal qtyDemand = BigDecimal.ZERO;
		BigDecimal qtySupply = BigDecimal.ZERO;
		int mrpCountPerSegment = 0;
		final List<I_PP_MRP> mrpsAll = queryBuilder.create().list();
		for (final I_PP_MRP mrp : mrpsAll)
		{
			final IMRPSegment mrpSegment = Services.get(IMRPBL.class).createMRPSegment(mrp);

			// Check if segment changed
			if (mrpSegmentPrevious != null && !mrpSegmentPrevious.equals(mrpSegment))
			{
				final BigDecimal qtyOnHand = mrpDAO.getQtyOnHand(contextProvider, mrpSegmentPrevious.getM_Warehouse(), mrpSegmentPrevious.getM_Product());
				final BigDecimal qtyBalance = qtyDemand.subtract(qtySupply);
				out.println("=> Totals: QtyDemand=" + qtyDemand + ", QtySupply=" + qtySupply + ", Balance=" + qtyBalance + ", QtyOnHand=" + qtyOnHand);

				// reset
				qtyDemand = BigDecimal.ZERO;
				qtySupply = BigDecimal.ZERO;
				mrpCountPerSegment = 0;
			}

			// Update Segment Aggregated values:
			final String typeMRP = mrp.getTypeMRP();
			final BigDecimal qty = mrp.getQty();
			if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
			{
				qtyDemand = qtyDemand.add(qty);
			}
			else if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
			{
				qtySupply = qtySupply.add(qty);
			}

			final String mrpStr = toString(mrp);
			out.println(mrpStr);

			mrpSegmentPrevious = mrpSegment;
			mrpCountPerSegment++;
		}

		// Totals for last segment
		if (mrpCountPerSegment > 0)
		{
			final BigDecimal qtyOnHand = mrpDAO.getQtyOnHand(contextProvider, mrpSegmentPrevious.getM_Warehouse(), mrpSegmentPrevious.getM_Product());
			final BigDecimal qtyBalance = qtyDemand.subtract(qtySupply);
			out.println("=> Totals: QtyDemand=" + qtyDemand + ", QtySupply=" + qtySupply + ", Balance=" + qtyBalance + ", QtyOnHand=" + qtyOnHand);

			// reset
			qtyDemand = BigDecimal.ZERO;
			qtySupply = BigDecimal.ZERO;
			mrpCountPerSegment = 0;
		}

		out.println("======================================================================================================");

		return baos.toString();
	}

	public static final String toString(final I_PP_MRP mrp)
	{
		final StringBuilder sb = new StringBuilder();

		final String typeMRP = mrp.getTypeMRP();
		final String qtySignStr = X_PP_MRP.TYPEMRP_Demand.equals(typeMRP) ? "(-)" : "(+)";

		sb.append("ID=").append(mrp.getPP_MRP_ID());
		sb.append(", Product=").append(mrp.getM_Product().getValue());
		sb.append(", Warehouse=").append(mrp.getM_Warehouse().getName());
		final I_S_Resource plant = mrp.getS_Resource();
		sb.append(", Plant=").append(plant == null ? null : plant.getName());
		sb.append(", TypeMRP=").append(typeMRP);
		sb.append(", OrderType=").append(mrp.getOrderType());
		sb.append(", DocStatus=").append(mrp.getDocStatus());
		sb.append(", Qty=").append(qtySignStr).append(mrp.getQty()).append("/").append(mrp.getQtyRequiered());
		sb.append(", IsAvailable=").append(mrp.isAvailable());

		if (mrp.getPP_MRP_Parent_ID() > 0)
		{
			sb.append(", Parent_MRP_ID=").append(mrp.getPP_MRP_Parent_ID());
		}

		if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			final List<Integer> mrpSupplyIds = Services.get(IQueryBL.class).createQueryBuilder(I_PP_MRP_Alloc.class, mrp)
					.addEqualsFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID, mrp.getPP_MRP_ID())
					.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID)
					.create()
					.listIds();
			sb.append(", Backward_MRP_Supplies=").append(mrpSupplyIds);
		}
		else if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			final List<Integer> mrpDemandIds = Services.get(IQueryBL.class).createQueryBuilder(I_PP_MRP_Alloc.class, mrp)
					.addEqualsFilter(I_PP_MRP_Alloc.COLUMN_PP_MRP_Supply_ID, mrp.getPP_MRP_ID())
					.andCollect(I_PP_MRP_Alloc.COLUMN_PP_MRP_Demand_ID)
					.create()
					.listIds();
			sb.append(", Forward_MRP_Demands=").append(mrpDemandIds);
		}

		//
		// Document
		if (mrp.getDD_Order_ID() > 0)
		{
			sb.append(", DD_Order_ID=").append(mrp.getDD_Order_ID());
		}
		if (mrp.getPP_Order_ID() > 0)
		{
			sb.append(", PP_Order_ID=").append(mrp.getPP_Order_ID());
		}
		if (mrp.getC_Order_ID() > 0)
		{
			sb.append(", C_Order_ID=").append(mrp.getC_Order_ID());
		}

		if (mrp.getC_BPartner_ID() > 0)
		{
			sb.append(", BP=").append(mrp.getC_BPartner().getName());
		}

		if (!Check.isEmpty(mrp.getDescription(), true))
		{
			sb.append(", Description=").append(mrp.getDescription());
		}

		sb.append(", ScheduledDates=").append(mrp.getDateStartSchedule()).append("-").append(mrp.getDateFinishSchedule());

		if (mrp.getDD_Order_ID() > 0)
		{
			final boolean mrpAllowCleanup = mrp.getDD_Order().isMRP_AllowCleanup();
			sb.append(", MRP_AllowCleanup=").append(mrpAllowCleanup);
		}

		return sb.toString();
	}

	public static final String toString(final Collection<I_PP_MRP> mrps)
	{
		if (mrps == null || mrps.isEmpty())
		{
			return "(no MRP records)";
		}

		final StringBuilder sb = new StringBuilder();
		for (final I_PP_MRP mrp : mrps)
		{
			final String mrpStr = toString(mrp);
			if (sb.length() > 0)
			{
				sb.append("\n");
			}
			sb.append(mrpStr);
		}

		return sb.toString();
	}

}
