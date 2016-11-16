package de.metas.commission.process;

/*
 * #%L
 * de.metas.commission.base
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


import java.io.File;
import java.io.FileWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Order;
import org.compiere.model.MBPartner;
import org.compiere.model.MOrder;
import org.compiere.model.MOrderLine;
import org.compiere.model.MPeriod;
import org.compiere.model.PO;
import org.compiere.model.Query;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.commission.interfaces.IAdvComInstance;
import de.metas.commission.model.I_C_AdvCommissionPayrollLine;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.MCAdvCommissionPayrollLine;
import de.metas.commission.service.ICommissionInstanceDAO;
import de.metas.commission.service.IHierarchyBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.process.SvrProcess;

public class DumpCommissionReviewData extends SvrProcess
{
	private final static String EOL = System.getProperty("line.separator");

	private int orgId;
	private MPeriod period;
	private File outputFile;

	@Override
	protected String doIt() throws Exception
	{
		final DateFormat dateFmt = DateFormat.getDateInstance(DateFormat.MEDIUM, Env.getLanguage(getCtx()).getLocale());

		final IHierarchyBL hierarchyBL = Services.get(IHierarchyBL.class);

		final FileWriter os = new FileWriter(outputFile);

		try
		{
			os.append("Prov-Periode: ");
			os.append(period.toString());
			os.append(DumpCommissionReviewData.EOL);
			os.append(DumpCommissionReviewData.EOL);

			for (final MOrder order : retrieveOrdersFromPeriod())
			{
				os.append(Msg.translate(getCtx(), I_C_Order.COLUMNNAME_C_Order_ID));
				os.append(" ");
				os.append(order.getDocumentNo());
				os.append(" (");
				os.append(dateFmt.format(order.getDateOrdered()));
				os.append(", ");
				os.append(order.getDocStatus());
				os.append("); ");

				os.append(Msg.translate(getCtx(), org.compiere.model.I_C_BPartner.COLUMNNAME_C_BPartner_ID));
				os.append(" ");
				final MBPartner cust = (MBPartner)order.getC_BPartner();
				os.append(cust.getValue());
				os.append(" (");
				os.append(cust.getName());
				os.append("),");
				if (cust.isSalesRep())
				{
					os.append("VP");
				}
				else
				{
					os.append("KD");
				}
				os.append(DumpCommissionReviewData.EOL);

				final I_C_Sponsor custSponsor = Services.get(ISponsorDAO.class).retrieveForBPartner(cust, false);
				if (custSponsor == null)
				{
					os.append(" >> Kunde ist nicht in Hierarchie <<");
					os.append(DumpCommissionReviewData.EOL);
					os.append(DumpCommissionReviewData.EOL);
					continue;
				}
				int count = 0;
				for (final I_C_Sponsor salesRepSponsor : hierarchyBL.findSponsorsInUpline(period.getEndDate(), custSponsor, Integer.MAX_VALUE, Integer.MAX_VALUE, null, null, null))
				{
					boolean hierarchyChangeAfterOrder = false;
					for (final I_C_Sponsor_SalesRep ssr : Services.get(ISponsorDAO.class).retrieveSalesRepSSRs(salesRepSponsor))
					{
						if (ssr.getC_Sponsor_Parent_ID() == 0)
						{
							continue;
						}
						if (ssr.getUpdated().after(order.getDateOrdered()))
						{
							hierarchyChangeAfterOrder = true;
						}
					}
					final String prefix;
					if (salesRepSponsor.getCreated().after(order.getDateOrdered()))
					{
						prefix = "SP";
					}
					else if (hierarchyChangeAfterOrder)
					{
						prefix = "DL";
					}
					else
					{
						prefix = null;
					}
					final String line = DumpTree.mkLineForSponsor(salesRepSponsor, count, period, prefix);
					os.append(line);
					count++;
				}
				os.append(DumpCommissionReviewData.EOL);
			}
		}
		finally
		{
			os.flush();
			os.close();
		}
		return "@Success@";
	}

	@Override
	protected void prepare()
	{
		final Object[] params = DumpTree.readParameters(getCtx(), getParametersAsArray());
		orgId = (Integer)params[0];
		period = (MPeriod)params[1];
		outputFile = (File)params[2];
	}

	private List<MOrder> retrieveOrdersFromPeriod()
	{
		final String wcPayrollLine =
				I_C_AdvCommissionPayrollLine.COLUMNNAME_AD_Org_ID + "=? AND " +
						I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayroll_ID + " IN (" +
						"   select C_AdvCommissionPayroll_ID from C_AdvCommissionPayroll where C_Period_ID=? " +
						")";
		final String orderByPayrollLine = I_C_AdvCommissionPayrollLine.COLUMNNAME_C_AdvCommissionPayrollLine_ID;

		final List<MCAdvCommissionPayrollLine> lines =
				new Query(getCtx(), I_C_AdvCommissionPayrollLine.Table_Name, wcPayrollLine, get_TrxName())
						.setParameters(orgId, period.getC_Period_ID())
						.setOnlyActiveRecords(true)
						.setOrderBy(orderByPayrollLine)
						.list();

		final Set<Integer> orderIds = new HashSet<Integer>();
		final List<MOrder> result = new ArrayList<MOrder>();

		for (final MCAdvCommissionPayrollLine line : lines)
		{
			final PO instanceTrigger = Services.get(ICommissionInstanceDAO.class).retrievePO(
					InterfaceWrapperHelper.create(line.getC_AdvCommissionInstance(),IAdvComInstance.class));
			if (instanceTrigger instanceof MOrderLine)
			{
				final MOrderLine ol = (MOrderLine)instanceTrigger;
				if (orderIds.add(ol.getC_Order_ID()))
				{
					result.add(ol.getParent());
				}
			}
		}

		final String wc = I_C_Order.COLUMNNAME_AD_Org_ID + "=? AND " + I_C_Order.COLUMNNAME_DateOrdered + ">=? AND " + I_C_Order.COLUMNNAME_DateOrdered + "<=?";
		final String orderBy = I_C_Order.COLUMNNAME_DateOrdered;

		final List<MOrder> allOrdersPromPeriod = new Query(getCtx(), I_C_Order.Table_Name, wc, get_TrxName())
				.setParameters(orgId, period.getStartDate(), period.getEndDate())
				.setOnlyActiveRecords(true)
				.setOrderBy(orderBy)
				.list();
		for (final MOrder order : allOrdersPromPeriod)
		{
			if (orderIds.add(order.get_ID()))
			{
				result.add(order);
			}
		}

		Collections.sort(result, new Comparator<MOrder>()
		{
			@Override
			public int compare(final MOrder o1, final MOrder o2)
			{
				return o1.getDocumentNo().compareTo(o2.getDocumentNo());
			}
		});

		return result;
	}
}
