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
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MPeriod;
import org.compiere.model.Query;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.model.X_C_AdvCommissionFact;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;

public class DumpTree extends JavaProcess
{
	private final static String EOL = System.getProperty("line.separator");

	final static Logger logger = LogManager.getLogger(DumpTree.class);

	private int orgId;
	private MPeriod period;
	private File outputFile;

	// select those ssrs whose parent sponsors don't have a parent sponsor of their own
	final static String WHERE_ROOT = //
			"C_Sponsor_SalesRep_ID in (" //
					+ " SELECT ssr.C_Sponsor_SalesRep_ID " //
					+ " FROM C_Sponsor_SalesRep ssr " //
					+ "   LEFT JOIN C_Sponsor ps ON ps.C_Sponsor_ID=ssr.C_Sponsor_Parent_ID " //
					+ " WHERE true "
					+ "   AND NOT EXISTS ( " //
					+ "     SELECT * " //
					+ "     FROM C_Sponsor_SalesRep ssr2 " //
					+ "     WHERE ssr2.C_Sponsor_ID=ps.C_Sponsor_ID AND ssr2.C_Sponsor_Parent_ID>0" //
					+ "   ) "
					+ "   AND C_Sponsor_Parent_ID>0"
					+ "   AND ssr.AD_Org_ID in (0,?)"
					+ "   AND ssr.ValidFrom <=? "
					+ "   AND ssr.ValidTo >=? "
					+ " ) ";

	@Override
	protected String doIt() throws Exception
	{
		final FileWriter os = new FileWriter(outputFile);

		os.append("Prov-Periode: ");
		os.append(period.toString());

		os.append(DumpTree.EOL);

		try
		{
			// find those SSRs that have the sponsor parent id set to 0
			final List<I_C_Sponsor_SalesRep> roots =
					new Query(getCtx(), I_C_Sponsor_SalesRep.Table_Name, DumpTree.WHERE_ROOT, get_TrxName())
							.setParameters(orgId, period.getEndDate(), period.getEndDate())
							.setOnlyActiveRecords(true)
							.list(I_C_Sponsor_SalesRep.class);

			final int depth = 0;

			for (final I_C_Sponsor_SalesRep root : roots)
			{
				writeInfo(root, depth, os);

				recurse(
						Services.get(ISponsorDAO.class).retrieveChildren(root, period.getEndDate()),
						os,
						depth + 1);
			}
		}
		finally
		{
			os.flush();
			os.close();
		}

		return "@Success@";
	}

	private void writeInfo(final I_C_Sponsor_SalesRep ssr, final int depth, final FileWriter os) throws IOException
	{
		final I_C_Sponsor sponsor = ssr.getC_Sponsor();

		//
		// get the data to write
		final String line = mkLineForSponsor(sponsor, depth, period, null);
		os.write(line);
	}

	public static String mkLineForSponsor(final I_C_Sponsor sponsor, final int depth, final MPeriod period, final String linePrefix)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(sponsor);
		final String trxName = InterfaceWrapperHelper.getTrxName(sponsor);

		final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final I_C_BPartner bPartner;
		final Timestamp date = period.getEndDate();

		final I_C_BPartner salesRep = sponsorBL.retrieveSalesRepAt(ctx, date, sponsor, false, trxName); // throwEx=false

		if (salesRep == null)
		{
			bPartner = Services.get(ISponsorDAO.class).getCustomer(sponsor, date);
		}
		else
		{
			bPartner = salesRep;
		}

		if (bPartner == null || bPartner.getC_BPartner_ID() <= 0)
		{
			// nothing to do
			return "";
		}

		I_C_AdvCommissionSalaryGroup actualSG = null;
		I_C_AdvCommissionSalaryGroup forecastSG = null;

		final Map<String, BigDecimal> apv = new HashMap<String, BigDecimal>();
		final Map<String, BigDecimal> adv = new HashMap<String, BigDecimal>(2);

		if (salesRep != null)
		{
			actualSG = retrieveSG(ctx, sponsor, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant, date, trxName);
			forecastSG = retrieveSG(ctx, sponsor, X_C_AdvComSalesRepFact.STATUS_Prognose, date, trxName);

			apv.put(X_C_AdvCommissionFact.STATUS_Prognostiziert,
					srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_APV, period, X_C_AdvComSalesRepFact.STATUS_Prognose));

			apv.put(X_C_AdvCommissionFact.STATUS_ZuBerechnen,
					srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_APV, period, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant));

			adv.put(X_C_AdvComSalesRepFact.STATUS_Prov_Relevant,
					srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_6EDL, period, X_C_AdvComSalesRepFact.STATUS_Prov_Relevant));

			adv.put(X_C_AdvComSalesRepFact.STATUS_Prognose,
					srfBL.retrieveSum(sponsor, X_C_AdvComSalesRepFact.NAME_6EDL, period, X_C_AdvComSalesRepFact.STATUS_Prognose));
		}

		//
		// write the data
		final StringBuilder sb = new StringBuilder();

		if (Check.isEmpty(linePrefix))
		{
			sb.append("   ");
		}
		else
		{
			if (linePrefix.length() > 3)
			{
				sb.append(linePrefix.substring(0, 3));
			}
			else
			{
				sb.append(linePrefix);
				for (int i = linePrefix.length(); i < 3; i++)
				{
					sb.append(" ");
				}
			}
		}

		sb.append(depth);
		for (int i = 0; i < depth; i++)
		{
			sb.append("  ");
		}

		sb.append("|_");
		sb.append(bPartner.getValue());
		sb.append(" ");
		sb.append(bPartner.getName());

		sb.append("; VP=");
		sb.append(bPartner.isSalesRep() ? "Y" : "N");

		if (bPartner.isSalesRep())
		{
			sb.append("; Rang=");

			appendSGIfNotNull(actualSG, sb);

			sb.append("; APV=");
			appendPointsComFactIfNotNull(apv, sb);

			sb.append("; 6EDL=");
			appendPointsSalesRepFactIfNotNull(adv, sb);

			if (forecastSG != null)
			{
				sb.append("(");
				sb.append(forecastSG.getValue());
				sb.append(")");
			}
		}
		sb.append(DumpTree.EOL);

		return sb.toString();
	}

	private static void appendPointsComFactIfNotNull(final Map<String, BigDecimal> points, final StringBuilder sb)
	{

		if (points == null || points.isEmpty())
		{
			sb.append("<LEER>");
		}
		else
		{
			appendNumber(points.get(X_C_AdvCommissionFact.STATUS_ZuBerechnen), sb);
			sb.append(" (");
			appendNumber(points.get(X_C_AdvCommissionFact.STATUS_Prognostiziert), sb);
			sb.append(")");
		}
	}

	private static void appendPointsSalesRepFactIfNotNull(final Map<String, BigDecimal> points, final StringBuilder sb)
	{
		if (points == null || points.isEmpty())
		{
			sb.append("<LEER>");
		}
		else
		{
			appendNumber(points.get(X_C_AdvComSalesRepFact.STATUS_Prov_Relevant), sb);
			sb.append(" (");
			appendNumber(points.get(X_C_AdvComSalesRepFact.STATUS_Prognose), sb);
			sb.append(")");
		}
	}

	private static void appendNumber(final BigDecimal number, final StringBuilder sb)
	{
		sb.append(number.setScale(2, BigDecimal.ROUND_HALF_UP));
	}

	private static void appendSGIfNotNull(final I_C_AdvCommissionSalaryGroup actualSG, final StringBuilder sb)
	{
		if (actualSG == null)
		{
			sb.append("<LEER>");
		}
		else
		{
			sb.append(actualSG.getValue());
		}
	}

	private static I_C_AdvCommissionSalaryGroup retrieveSG(
			final Properties ctx,
			final I_C_Sponsor sponsor,
			final String status,
			final Timestamp date,
			final String trxName)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

		final I_C_Sponsor_SalesRep ssr = sponsorBL.retrieveContractSSR(ctx, sponsor, date, trxName);
		if (ssr == null || ssr.getC_AdvComSystem_ID() <= 0)
		{
			return null;
		}

		final I_C_AdvCommissionSalaryGroup actualSG =
				sponsorBL.retrieveRank(ctx, sponsor, ssr.getC_AdvComSystem(), date, status, trxName);

		return actualSG;
	}

	private void recurse(final Collection<I_C_Sponsor_SalesRep> ssrs, final FileWriter os, final int depth) throws IOException
	{
		for (final I_C_Sponsor_SalesRep ssr : ssrs)
		{
			writeInfo(ssr, depth, os);
			recurse(
					Services.get(ISponsorDAO.class).retrieveChildren(ssr, period.getEndDate()),
					os,
					depth + 1);
		}
	}

	@Override
	protected void prepare()
	{
		final Object[] params = readParameters(getCtx(), getParametersAsArray());
		orgId = (Integer)params[0];
		period = (MPeriod)params[1];
		outputFile = (File)params[2];
	}

	static Object[] readParameters(final Properties ctx, final ProcessInfoParameter[] para)
	{
		int orgId = 0;
		MPeriod period = null;
		File outputFile = null;

		for (int i = 0; i < para.length; i++)
		{
			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null)
			{
				;
			}
			else if (name.equals("AD_Org_ID"))
			{
				orgId = ((BigDecimal)para[i].getParameter()).intValue();
			}
			else if (name.equals("C_Period_ID"))
			{
				final int periodId = ((BigDecimal)para[i].getParameter()).intValue();
				period = MPeriod.get(ctx, periodId);
			}
			else if (name.equals("FileName"))
			{
				final String fileName = (String)para[i].getParameter();
				outputFile = new File(fileName);
			}
		}
		return new Object[] { orgId, period, outputFile };
	}
}
