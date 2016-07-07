package de.metas.flatrate.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.util.List;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import de.metas.flatrate.api.IFlatrateDAO;
import de.metas.flatrate.model.I_C_Flatrate_DataEntry;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_Invoice_Clearing_Alloc;
import de.metas.flatrate.model.X_C_Flatrate_DataEntry;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.exceptions.InconsistentUpdateExeption;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;

public class C_Invoice_Candidate implements ModelValidator
{
	public static final String MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P = "DataEntry_Error_AlreadyCompleted";

	public static final String MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P = "DataEntry_Error_AlreadyCompleted_Text";

	private int m_AD_Client_ID = -1;

	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

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

		engine.addModelChange(I_C_Invoice_Candidate.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type) throws Exception
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		
		final I_C_Invoice_Candidate invoiceCand = InterfaceWrapperHelper.create(po, I_C_Invoice_Candidate.class);

		if (type == TYPE_BEFORE_NEW || type == TYPE_BEFORE_CHANGE)
		{
			final I_C_Flatrate_Term term = flatrateDAO.retrieveNonSimTermOrNull(invoiceCand);
			invoiceCand.setIsToClear(term != null);
			
			// FRESH-378
			// Mark the candidate as Processed_Override = Y because in case it has IsToClear on true. This kind of candidate is not relevant for the invoice
			// Could happen when the partner has a subscription for the kind of product in the invoice candidate
			if(term != null)
			{
				if (invoiceCandBL.isCloseIfIsToClear())
				{
					invoiceCand.setProcessed_Override("Y");
				}
			}

		}
		if (type == TYPE_BEFORE_CHANGE)
		{
			final I_C_Flatrate_Term term = flatrateDAO.retrieveNonSimTermOrNull(invoiceCand);
			final boolean newIsToClear = term != null;
			if (invoiceCand.isToClear() != newIsToClear)
			{
				invoiceCand.setIsToClear(newIsToClear);
				Services.get(IInvoiceCandDAO.class).invalidateCand(invoiceCand);
			}
			
			// FRESH-378
			// Make sure the invoice candidate is closed (if required) if the invoice candidate is set to be cleared
			if(newIsToClear)
			{
				if (invoiceCandBL.isCloseIfIsToClear())
				{
					invoiceCand.setProcessed_Override("Y");
				}
			}

			if (newIsToClear && po.is_ValueChanged(I_C_Invoice_Candidate.COLUMNNAME_DateOrdered))
			{
				Check.assume(false, "Column I_C_Invoice_Cand." + I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + " is not updatable");
				// If the column was updatable, we would have to
				// *check if new and old term are the same
				// *check if ICAs need update, creation or deletion and do it;
				// *check which dataEntries' ActualQty needs update and make sure that they are not yet completed
				// *check if isToClear needs update;
			}
		}
		if (type == TYPE_AFTER_NEW)
		{
			if (invoiceCand.isToClear())
			{
				// create an invoice candidate allocation record for 'invoiceCand'

				final I_C_Flatrate_Term term = flatrateDAO.retrieveNonSimTermOrNull(invoiceCand);
				Check.assume(term != null, "There is a term for " + invoiceCand);

				final I_C_Invoice_Clearing_Alloc ica = InterfaceWrapperHelper.create(po.getCtx(), I_C_Invoice_Clearing_Alloc.class, po.get_TrxName());
				ica.setAD_Org_ID(invoiceCand.getAD_Org_ID());
				Check.assume(ica.getAD_Client_ID() == invoiceCand.getAD_Client_ID(), "po.getCtx() contains the correct AD_Client_ID");

				ica.setC_Invoice_Cand_ToClear(invoiceCand);
				ica.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());

				// task 07371: the code to retrieve and link the C_FlatrateDataEntry for 'invoiceCand' and 'term' was moved to a dedicated MV 'C_Invoice_Clearing_Alloc'.

				InterfaceWrapperHelper.save(ica);
			}
		}

		if (type == TYPE_AFTER_NEW || type == TYPE_AFTER_CHANGE)
		{
			final boolean qtyChanged = po.is_ValueChanged(I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice)
					|| po.is_ValueChanged(I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);

			final boolean isToClearChanged = po.is_ValueChanged(I_C_Invoice_Candidate.COLUMNNAME_IsToClear);

			if ((qtyChanged || isToClearChanged) && invoiceCand.isToClear())
			{
				// update data entries
				for (final I_C_Invoice_Clearing_Alloc ica : flatrateDAO.retrieveClearingAllocs(invoiceCand))
				{
					if (ica.getC_Flatrate_DataEntry_ID() == 0)
					{
						continue;
					}

					final I_C_Flatrate_DataEntry dataEntry = ica.getC_Flatrate_DataEntry();
					if (dataEntry.getC_Flatrate_Term().isClosingWithActualSum())
					{
						if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(dataEntry.getDocStatus()))
						{
							throw new InconsistentUpdateExeption(
									MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P,
									MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P,
									new Object[] { invoiceCand.getC_Invoice_Candidate_ID(), dataEntry.getC_Period().getName() },
									dataEntry.getC_Flatrate_Term().getAD_User_InCharge());
						}
						updateActualQty(dataEntry, po, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, isToClearChanged);
						updateActualQty(dataEntry, po, I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced, isToClearChanged);

						// note: this will also trigger updates of the other values of 'dataEntry' as well as of
						// possible aux entries
						InterfaceWrapperHelper.save(dataEntry);
					}
				}
			}
		}

		if (type == TYPE_BEFORE_DELETE)
		{
			// cleanup
			for (final I_C_Invoice_Clearing_Alloc ica : flatrateDAO.retrieveClearingAllocs(invoiceCand))
			{
				if (ica.getC_Flatrate_DataEntry_ID() > 0)
				{
					final I_C_Flatrate_DataEntry dataEntry = ica.getC_Flatrate_DataEntry();
					if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(dataEntry.getDocStatus()))
					{
						throw new InconsistentUpdateExeption(
								MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P,
								MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P,
								new Object[] { invoiceCand.getC_Invoice_Candidate_ID(), dataEntry.getC_Period().getName() },
								dataEntry.getC_Flatrate_Term().getAD_User_InCharge());
					}
				}
				InterfaceWrapperHelper.delete(ica);
			}

			if (invoiceCand.isToClear())
			{
				final List<I_C_Invoice_Clearing_Alloc> icas = flatrateDAO.retrieveClearingAllocs(invoiceCand);
				Check.assume(icas.size() <= 1, invoiceCand + " has max 1 ica");

				if (!icas.isEmpty() && icas.get(0).getC_Flatrate_DataEntry_ID() > 0)
				{
					final I_C_Flatrate_DataEntry dataEntry = icas.get(0).getC_Flatrate_DataEntry();
					final BigDecimal diff = invoiceCand.getQtyToInvoice().add(invoiceCand.getQtyInvoiced());
					dataEntry.setActualQty(dataEntry.getActualQty().subtract(diff));
					InterfaceWrapperHelper.save(dataEntry);
				}
			}
		}

		return null;
	}

	/**
	 * 
	 * @param dataEntry
	 * @param po
	 * @param colName
	 * @param isToClearClanged if <code>true</code>, then we assume that IsToClean has been change to 'Y'. In that case, we don't add the difference between old and new value, but just the new value
	 */
	private void updateActualQty(
			final I_C_Flatrate_DataEntry dataEntry,
			final PO po,
			final String colName,
			final boolean isToClearClanged)
	{
		BigDecimal oldValue = (BigDecimal)po.get_ValueOld(colName);
		if (isToClearClanged || oldValue == null)
		{
			oldValue = BigDecimal.ZERO;
		}

		BigDecimal currentValue = (BigDecimal)po.get_Value(colName);
		if (currentValue == null)
		{
			currentValue = BigDecimal.ZERO;
		}

		final BigDecimal diff = currentValue.subtract(oldValue);

		dataEntry.setActualQty(dataEntry.getActualQty().add(diff));
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		// nothing to do
		return null;
	}

}
