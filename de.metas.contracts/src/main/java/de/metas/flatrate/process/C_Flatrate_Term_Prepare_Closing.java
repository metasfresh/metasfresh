package de.metas.flatrate.process;

/*
 * #%L
 * de.metas.contracts
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.model.Query;
import org.compiere.util.Msg;
import org.compiere.util.TimeUtil;

import de.metas.flatrate.api.IFlatrateBL;
import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.SvrProcess;

public class C_Flatrate_Term_Prepare_Closing extends SvrProcess
{

	private static final String MSG_PREPARE_CLOSING_MISSING_DATA_ENTRIES_0P = "PrepareClosing_MissingDataEntries";
	private static final String MSG_PREPARE_CLOSING_CORRECTION_ENTRY_CREATED_2P = "PrepareClosing_CorrectionEntry_Created";
	private static final String MSG_PREPARE_CLOSING_CORRECTION_ENTRY_EXISTS_2P = "PrepareClosing_CorrectionEntry_Exists";

	private I_C_Period p_periodTo;

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		final String trxName = get_TrxName();

		final I_C_Flatrate_Term flatrateTerm =
				InterfaceWrapperHelper.create(ctx, getRecord_ID(), I_C_Flatrate_Term.class, trxName);

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final Timestamp dateFrom;
		final List<I_C_Flatrate_DataEntry> existingCorrEntries =
				flatrateDB.retrieveDataEntries(flatrateTerm, X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, flatrateTerm.getC_UOM());
		if (existingCorrEntries.isEmpty())
		{
			dateFrom = flatrateTerm.getStartDate();
		}
		else
		{
			// note: assuming that existingCorrEntries are ordered by their periods
			final Timestamp endDate =
					existingCorrEntries.get(existingCorrEntries.size() - 1).getC_Period().getEndDate();
			dateFrom = TimeUtil.addDays(endDate, 1);
		}
		final Map<Integer, I_C_Flatrate_DataEntry> uomId2NewCorrectionEntries = new HashMap<Integer, I_C_Flatrate_DataEntry>();

		Check.assume(flatrateTerm.isClosingWithCorrectionSum(), flatrateTerm + " has IsClosingWithCorrectionSum='Y");

		// create one correction entry per UOM and fill it with the qtys that were invoiced
		// (i.e. the entries' Qty_Reported values). It's up to the user to add the final correction values and
		// complete those entries.
		final List<I_C_UOM> uoms = new Query(ctx, I_C_UOM.Table_Name, I_C_UOM.COLUMNNAME_UOMType + "=?", trxName)
				.setParameters(flatrateTerm.getUOMType())
				.setOnlyActiveRecords(true)
				.setOrderBy(I_C_UOM.COLUMNNAME_C_UOM_ID)
				.list(I_C_UOM.class);

		boolean allEntriesCompleted = true;

		for (final I_C_UOM uom : uoms)
		{
			// We sum up qtyReported from the invoicing data entries lying within 'p_period'.
			// At the same time we make sure that all those entries have been completed.
			// The result is used as the correction entry's qtyPlanned value.
			final QtyReportedAndQtyActual qtyReportedAndQtyActual = retreiveQtyReportedOrNull(flatrateTerm, dateFrom, p_periodTo.getEndDate(), uom);
			if (qtyReportedAndQtyActual == null)
			{
				allEntriesCompleted = false;
			}

			if (allEntriesCompleted)
			{
				final I_C_Flatrate_DataEntry existingCorrectionentry =
						flatrateDB.retrieveDataEntryOrNull(flatrateTerm, p_periodTo, X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, uom);

				if (existingCorrectionentry != null)
				{
					final String msg =
							Msg.getMsg(getCtx(),
									MSG_PREPARE_CLOSING_CORRECTION_ENTRY_EXISTS_2P,
									new Object[] { uom.getName(), p_periodTo.getName() });
					addLog(msg);
				}
				else
				{
					final I_C_Flatrate_DataEntry newCorrectionEntry = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_DataEntry.class, trxName);
					newCorrectionEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());
					Check.assume(newCorrectionEntry.getAD_Client_ID() == flatrateTerm.getAD_Client_ID(), "ctx contains the correct AD_Client_ID");
					
					newCorrectionEntry.setType(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);

					newCorrectionEntry.setC_UOM_ID(uom.getC_UOM_ID());
					newCorrectionEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());
					newCorrectionEntry.setC_Period_ID(p_periodTo.getC_Period_ID());

					newCorrectionEntry.setQty_Planned(qtyReportedAndQtyActual.qtyReported);
					if (flatrateTerm.getC_Flatrate_Conditions().isCorrectionAmtAtClosing())
					{
						newCorrectionEntry.setActualQty(qtyReportedAndQtyActual.qtyActual);
					}

					uomId2NewCorrectionEntries.put(uom.getC_UOM_ID(), newCorrectionEntry);
				}
			}
		}

		if (allEntriesCompleted)
		{
			for (final I_C_Flatrate_DataEntry newCorrectionEntry : uomId2NewCorrectionEntries.values())
			{
				InterfaceWrapperHelper.save(newCorrectionEntry);

				final String msg = Msg.getMsg(getCtx(), MSG_PREPARE_CLOSING_CORRECTION_ENTRY_CREATED_2P,
						new Object[] {
								newCorrectionEntry.getC_UOM().getName(),
								p_periodTo.getName() });
				addLog(msg);
			}
		}
		else
		{
			return "@Error@ @" + MSG_PREPARE_CLOSING_MISSING_DATA_ENTRIES_0P + "@";
		}

		return "@Success@";
	}

	private QtyReportedAndQtyActual retreiveQtyReportedOrNull(
			final I_C_Flatrate_Term flatrateTerm,
			final Timestamp startDate, final Timestamp endDate,
			final I_C_UOM uom)
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final List<String> errors = new ArrayList<String>();
		final List<I_C_Flatrate_DataEntry> invoicingEntries =
				flatrateBL.retrieveAndCheckInvoicingEntries(flatrateTerm, startDate, endDate, uom, errors);
		if (invoicingEntries == null)
		{
			for (final String error : errors)
			{
				addLog(error);
			}
			return null;
		}

		BigDecimal qtyReported = BigDecimal.ZERO;
		BigDecimal qtyActual = BigDecimal.ZERO;

		for (final I_C_Flatrate_DataEntry invoicingEntry : invoicingEntries)
		{
			Check.assume(X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(invoicingEntry.getDocStatus()),
					invoicingEntry + " should have DocStatus='CO', but has " + invoicingEntry.getDocStatus());

			qtyReported = qtyReported.add(invoicingEntry.getQty_Reported());
			qtyActual = qtyActual.add(invoicingEntry.getActualQty());
		}
		return new QtyReportedAndQtyActual(qtyReported, qtyActual);

	}

	private static class QtyReportedAndQtyActual
	{
		final BigDecimal qtyReported;
		final BigDecimal qtyActual;

		public QtyReportedAndQtyActual(BigDecimal qtyReported, BigDecimal qtyActual)
		{
			this.qtyReported = qtyReported;
			this.qtyActual = qtyActual;
		}
	}

	@Override
	protected void prepare()
	{
		for (final ProcessInfoParameter para : getParametersAsArray())
		{
			final String name = para.getParameterName();
			if (para.getParameter() == null)
				;

			else if (name.equals(I_C_Period.COLUMNNAME_C_Period_ID))
			{
				final int periodId = para.getParameterAsInt();
				p_periodTo = InterfaceWrapperHelper.create(getCtx(), periodId, I_C_Period.class, get_TrxName());
			}
		}
	}

}
