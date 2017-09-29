package de.metas.contracts.flatrate.modelvalidator;

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
import java.util.List;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import com.google.common.collect.ImmutableList;

import de.metas.contracts.flatrate.api.IFlatrateBL;
import de.metas.contracts.flatrate.api.IFlatrateDAO;
import de.metas.contracts.interceptor.MainValidator;
import de.metas.contracts.model.I_C_Flatrate_Conditions;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.X_C_Flatrate_Conditions;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class FlatrateDataEntryValidator implements ModelValidator
{

	private static final String MSG_DATA_ENTRY_ALREADY_INVOICED_0P = "DataEntry_Already_Invoiced";
	private static final String MSG_DATA_ENTRY_EXISTING_CORRECTION_ENTRY_0P = "DataEntry_Existing_Correction_Entry";
	public static final String MSG_DATA_ENTRY_EXISTING_CLOSING_ENTRY_0P = "DataEntry_Existing_Closing_Entry";

	private int m_AD_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
			m_AD_Client_ID = client.getAD_Client_ID();

		engine.addModelChange(I_C_Flatrate_DataEntry.Table_Name, this);
		engine.addDocValidate(I_C_Flatrate_DataEntry.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE
				|| type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE || type == TYPE_AFTER_DELETE)
		{
			final I_C_Flatrate_DataEntry dataEntry = InterfaceWrapperHelper.create(po, I_C_Flatrate_DataEntry.class);

			final I_C_Flatrate_Term term = dataEntry.getC_Flatrate_Term();

			//
			// check if there is anything to do for this data entry
			if (term.getC_Flatrate_Conditions_ID() < 0)
			{
				return null; // nothing to do
			}
			if (term.getC_UOM_ID() != dataEntry.getC_UOM_ID())
			{
				return null; // nothing to do
			}

			final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
			final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

			if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
			{
				if (po.is_ValueChanged(I_C_Flatrate_DataEntry.COLUMNNAME_ActualQty) ||
						po.is_ValueChanged(I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Reported))
				{

					final I_C_Flatrate_Conditions conditions = dataEntry.getC_Flatrate_Term().getC_Flatrate_Conditions();

					if (!X_C_Flatrate_Conditions.TYPE_CONDITIONS_Refundable.equals(conditions.getType_Conditions()))
					{
						flatrateBL.updateEntry(dataEntry);
					}
				}
			}

			if (type == TYPE_AFTER_NEW)
			{
				final boolean mainEntry = dataEntry.getC_UOM_ID() == term.getC_UOM_ID();

				if (mainEntry && !dataEntry.isSimulation() && isInvoiceCandidatesRelatedType(dataEntry))
				{
					// handle open clearing allocs
					final List<I_C_Invoice_Clearing_Alloc> allocsWithoutDataEntry = flatrateDB.retrieveOpenClearingAllocs(dataEntry);

					for (final I_C_Invoice_Clearing_Alloc ica : allocsWithoutDataEntry)
					{
						ica.setC_Flatrate_DataEntry_ID(dataEntry.getC_Flatrate_DataEntry_ID());
						InterfaceWrapperHelper.save(ica);
					}

					// handle invoice candidates that have no clearing allocs
					flatrateDB.updateCandidates(dataEntry);
				}
			}

			if (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
			{
				if (po.is_ValueChanged(X_C_Flatrate_DataEntry.COLUMNNAME_ActualQty))
				{
					final boolean mainEntry = dataEntry.getC_UOM_ID() == term.getC_UOM_ID();

					if (dataEntry.isSimulation() || mainEntry)
					{
						flatrateDB.updateQtyActualFromDataEntry(dataEntry);
					}
				}
			}

			if (type == TYPE_AFTER_DELETE)
			{
				if (isInvoiceCandidatesRelatedType(dataEntry))
				{
					final List<I_C_Invoice_Clearing_Alloc> allocsOfDataEntry = flatrateDB.retrieveClearingAllocs(dataEntry);
					for (final I_C_Invoice_Clearing_Alloc ica : allocsOfDataEntry)
					{
						ica.setC_Flatrate_DataEntry_ID(0);
						ica.setC_Invoice_Candidate_ID(0);
						InterfaceWrapperHelper.save(ica);
					}
				}
			}
		}
		return null;
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		if (TIMING_BEFORE_VOID == timing || TIMING_BEFORE_CLOSE == timing)
		{
			throw new AdempiereException("@" + MainValidator.MSG_FLATRATE_DOC_ACTION_NOT_SUPPORTED_0P + "@");
		}

		final I_C_Flatrate_DataEntry dataEntry = InterfaceWrapperHelper.create(po, I_C_Flatrate_DataEntry.class);

		if (timing == TIMING_BEFORE_PREPARE)
		{
			if (po.get_Value(I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Reported) == null)
			{
				// make sure that the column has been set
				throw new FillMandatoryException(I_C_Flatrate_DataEntry.COLUMNNAME_Qty_Reported);
			}
		}

		if (dataEntry.isSimulation())
		{
			// nothing else to do
			return null;
		}

		else if (timing == TIMING_BEFORE_COMPLETE)
		{
			final IFlatrateBL flatrateBL = Services.get(IFlatrateBL.class);
			return flatrateBL.beforeCompleteDataEntry(dataEntry);
		}
		else if (timing == TIMING_BEFORE_REACTIVATE)
		{
			return beforeReactivate(dataEntry);
		}
		else if (timing == TIMING_AFTER_REACTIVATE)
		{
			//
			// notes:

			final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

			if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
			{
				final List<I_C_Invoice_Clearing_Alloc> allocLines = flatrateDB.retrieveClearingAllocs(dataEntry);
				for (final I_C_Invoice_Clearing_Alloc alloc : allocLines)
				{
					final I_C_Invoice_Candidate candToClear = alloc.getC_Invoice_Cand_ToClear();
					candToClear.setQtyInvoiced(BigDecimal.ZERO);
					InterfaceWrapperHelper.save(candToClear);

					alloc.setC_Invoice_Candidate_ID(0);
					InterfaceWrapperHelper.save(alloc);
				}
			}
		}
		return null;
	}

	private String beforeReactivate(final I_C_Flatrate_DataEntry dataEntry)
	{
		//
		// Check if we can allow the reactivation
		final String trxName = InterfaceWrapperHelper.getTrxName(dataEntry);

		// We need this assumption, because we are going to delete a C_Invoice_Candidate and that needs to happen in the
		// same trx in which we set dataEntry's C_Invoice_Candidate_ID to 0
		// (otherwise the DB's FK constraints wouldn't allow it).
		Check.assume(trxName != null, dataEntry + " has a trxName!=null");

		final I_C_Invoice_Candidate invoiceCandidate = dataEntry.getC_Invoice_Candidate();
		if (invoiceCandidate != null && invoiceCandidate.getQtyInvoiced().signum() != 0)
		{
			throw new AdempiereException("@" + MSG_DATA_ENTRY_ALREADY_INVOICED_0P + "@");
		}

		final I_C_Invoice_Candidate icCorr = dataEntry.getC_Invoice_Candidate_Corr();
		if (icCorr != null && icCorr.getQtyInvoiced().signum() != 0)
		{
			throw new AdempiereException("@" + MSG_DATA_ENTRY_ALREADY_INVOICED_0P + "@");
		}

		final I_C_Flatrate_Term flatrateTerm = dataEntry.getC_Flatrate_Term();
		final IFlatrateDAO flatrateDB = Services.get(IFlatrateDAO.class);

		//
		// if it's an invoicing data entry, make sure that there is no correction entry yet
		if (X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased.equals(dataEntry.getType()))
		{
			if (flatrateTerm.isClosingWithCorrectionSum())
			{
				final Timestamp dataEntryStartDate = dataEntry.getC_Period().getStartDate();

				// Now find out if there is a correction entry with a period equal or after of the period of 'dataEntry'

				final List<I_C_Flatrate_DataEntry> existingCorrectionEntries =
						flatrateDB.retrieveDataEntries(
								flatrateTerm,
								X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased,
								dataEntry.getC_UOM());
				for (final I_C_Flatrate_DataEntry corrEntry : existingCorrectionEntries)
				{
					final Timestamp corrEntryStartDate = corrEntry.getC_Period().getStartDate();
					if (corrEntryStartDate.equals(dataEntryStartDate) || corrEntryStartDate.after(dataEntryStartDate))
					{
						throw new AdempiereException("@" + MSG_DATA_ENTRY_EXISTING_CORRECTION_ENTRY_0P + "@");
					}
				}
			}
		}

		//
		// If we got this far, then the reactivation is OK
		if (invoiceCandidate != null)
		{
			dataEntry.setC_Invoice_Candidate_ID(0);
			InterfaceWrapperHelper.delete(invoiceCandidate);
		}

		if (icCorr != null)
		{
			dataEntry.setC_Invoice_Candidate_Corr_ID(0);
			InterfaceWrapperHelper.delete(icCorr);
		}

		return null;
	}

	/** @return true if dataEntry's type is invoice candidates related */
	private final boolean isInvoiceCandidatesRelatedType(I_C_Flatrate_DataEntry dataEntry)
	{
		final String dataEntryType = dataEntry.getType();
		return DATAENTRY_TYPES_InvoiceCandidatesRelated.contains(dataEntryType);
	}

	private static final List<String> DATAENTRY_TYPES_InvoiceCandidatesRelated = ImmutableList
			.of(X_C_Flatrate_DataEntry.TYPE_Correction_PeriodBased, X_C_Flatrate_DataEntry.TYPE_Invoicing_PeriodBased);
}
