package de.metas.contracts.flatrate.process;

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

import de.metas.contracts.IFlatrateBL;
import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.organization.IOrgDAO;
import de.metas.organization.LocalDateAndOrgId;
import de.metas.organization.OrgId;
import de.metas.process.JavaProcess;
import de.metas.process.ProcessInfoParameter;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Period;
import org.compiere.model.I_C_UOM;
import org.compiere.util.TimeUtil;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class C_Flatrate_Term_Prepare_Closing extends JavaProcess
{

	private static final String MSG_PREPARE_CLOSING_MISSING_DATA_ENTRIES_0P = "PrepareClosing_MissingDataEntries";
	private static final String MSG_PREPARE_CLOSING_CORRECTION_ENTRY_CREATED_2P = "PrepareClosing_CorrectionEntry_Created";
	private static final String MSG_PREPARE_CLOSING_CORRECTION_ENTRY_EXISTS_2P = "PrepareClosing_CorrectionEntry_Exists";

	private I_C_Period p_periodTo;

	private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Override
	protected String doIt() throws Exception
	{
		final Properties ctx = getCtx();
		final String trxName = get_TrxName();

		final I_C_Flatrate_Term flatrateTerm =
				InterfaceWrapperHelper.create(ctx, getRecord_ID(), I_C_Flatrate_Term.class, trxName);

		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		final LocalDateAndOrgId startDate;
		final List<I_C_Flatrate_DataEntry> existingCorrEntries =
				flatrateDB.retrieveDataEntries(flatrateTerm, X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, UomId.ofRepoIdOrNull(flatrateTerm.getC_UOM_ID()));
		if (existingCorrEntries.isEmpty())
		{
			startDate = LocalDateAndOrgId.ofTimestamp(flatrateTerm.getStartDate(), OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()), orgDAO::getTimeZone);
		}
		else
		{
			// note: assuming that existingCorrEntries are ordered by their periods
			final Timestamp endDate =
					existingCorrEntries.get(existingCorrEntries.size() - 1).getC_Period().getEndDate();
			startDate = LocalDateAndOrgId.ofTimestamp(TimeUtil.addDays(endDate, 1), OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()), orgDAO::getTimeZone);
		}
		final Map<Integer, I_C_Flatrate_DataEntry> uomId2NewCorrectionEntries = new HashMap<>();

		Check.assume(flatrateTerm.isClosingWithCorrectionSum(), flatrateTerm + " has IsClosingWithCorrectionSum='Y");

		// create one correction entry per UOM and fill it with the qtys that were invoiced
		// (i.e. the entries' Qty_Reported values). It's up to the user to add the final correction values and
		// complete those entries.
		final I_C_UOM uomRecord = uomDAO.getById(flatrateTerm.getC_UOM_ID());

		// We sum up qtyReported from the invoicing data entries lying within 'p_period'.
		// At the same time we make sure that all those entries have been completed.
		// The result is used as the correction entry's qtyPlanned value.
		final LocalDateAndOrgId endDate = LocalDateAndOrgId.ofTimestamp(p_periodTo.getEndDate(), OrgId.ofRepoId(flatrateTerm.getAD_Org_ID()), orgDAO::getTimeZone);
			final QtyReportedAndQtyActual qtyReportedAndQtyActual = retreiveQtyReportedOrNull(flatrateTerm, startDate, endDate, uomRecord);

		final boolean allEntriesCompleted = qtyReportedAndQtyActual != null;

		if (allEntriesCompleted)
		{
			final I_C_Flatrate_DataEntry existingCorrectionentry =
					flatrateDB.retrieveDataEntryOrNull(flatrateTerm, p_periodTo, X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, UomId.ofRepoId(uomRecord.getC_UOM_ID()));

			if (existingCorrectionentry != null)
			{
				final String msg =
						Msg.getMsg(getCtx(),
								   MSG_PREPARE_CLOSING_CORRECTION_ENTRY_EXISTS_2P,
								   new Object[] { uomRecord.getName(), p_periodTo.getName() });
					addLog(msg);
				}
				else
				{
					final I_C_Flatrate_DataEntry newCorrectionEntry = InterfaceWrapperHelper.create(ctx, I_C_Flatrate_DataEntry.class, trxName);
					newCorrectionEntry.setAD_Org_ID(flatrateTerm.getAD_Org_ID());
					Check.assume(newCorrectionEntry.getAD_Client_ID() == flatrateTerm.getAD_Client_ID(), "ctx contains the correct AD_Client_ID");

					newCorrectionEntry.setType(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased);

				newCorrectionEntry.setC_UOM_ID(uomRecord.getC_UOM_ID());
				newCorrectionEntry.setC_Flatrate_Term_ID(flatrateTerm.getC_Flatrate_Term_ID());
				newCorrectionEntry.setC_Period_ID(p_periodTo.getC_Period_ID());

				newCorrectionEntry.setQty_Planned(qtyReportedAndQtyActual.qtyReported);
				if (flatrateTerm.getC_Flatrate_Conditions().isCorrectionAmtAtClosing())
				{
					newCorrectionEntry.setActualQty(qtyReportedAndQtyActual.qtyActual);
				}

				uomId2NewCorrectionEntries.put(uomRecord.getC_UOM_ID(), newCorrectionEntry);
			}
		}
		
		if (allEntriesCompleted)
		{
			for (final I_C_Flatrate_DataEntry newCorrectionEntry : uomId2NewCorrectionEntries.values())
			{
				InterfaceWrapperHelper.save(newCorrectionEntry);
				final ITranslatableString uomName = uomDAO.getName(UomId.ofRepoId(newCorrectionEntry.getC_UOM_ID()));
				final String msg = Msg.getMsg(getCtx(), MSG_PREPARE_CLOSING_CORRECTION_ENTRY_CREATED_2P,
						new Object[] {
								uomName,
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
			final LocalDateAndOrgId startDate, final LocalDateAndOrgId endDate,
			final I_C_UOM uom)
	{
		final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);

		final List<String> errors = new ArrayList<>();
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
			{
				;
			}
			else if (name.equals(I_C_Period.COLUMNNAME_C_Period_ID))
			{
				final int periodId = para.getParameterAsInt();
				p_periodTo = InterfaceWrapperHelper.create(getCtx(), periodId, I_C_Period.class, get_TrxName());
			}
		}
	}

}
