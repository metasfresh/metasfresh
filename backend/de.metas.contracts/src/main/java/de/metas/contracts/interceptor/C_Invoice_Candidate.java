package de.metas.contracts.interceptor;

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

import de.metas.contracts.IFlatrateDAO;
import de.metas.contracts.model.I_C_Flatrate_DataEntry;
import de.metas.contracts.model.I_C_Flatrate_Term;
import de.metas.contracts.model.I_C_Invoice_Clearing_Alloc;
import de.metas.contracts.model.X_C_Flatrate_DataEntry;
import de.metas.i18n.AdMessageKey;
import de.metas.invoicecandidate.api.IInvoiceCandBL;
import de.metas.invoicecandidate.api.IInvoiceCandDAO;
import de.metas.invoicecandidate.exceptions.InconsistentUpdateException;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;

import java.math.BigDecimal;
import java.util.List;

@Interceptor(I_C_Invoice_Candidate.class)
public class C_Invoice_Candidate
{
	private static final AdMessageKey MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P = AdMessageKey.of("DataEntry_Error_AlreadyCompleted");
	private static final AdMessageKey MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P = AdMessageKey.of("DataEntry_Error_AlreadyCompleted_Text");

	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
	private final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void closeDirectly(final I_C_Invoice_Candidate invoiceCand)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		if (!invoiceCandBL.isCloseIfIsToClear())
		{
			return; // nothing to do
		}

		final I_C_Flatrate_Term term = flatrateDAO.retrieveNonSimTermOrNull(invoiceCand);

		// FRESH-378
		// Mark the candidate as Processed_Override = Y because in case it has IsToClear on true. This kind of candidate is not relevant for the invoice
		// Could happen when the partner has a subscription for the kind of product in the invoice candidate
		if (term != null)
		{
			invoiceCand.setProcessed_Override("Y");
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE })
	public void updateIsToClear(final I_C_Invoice_Candidate invoiceCand)
	{
		final IInvoiceCandBL invoiceCandBL = Services.get(IInvoiceCandBL.class);
		if (invoiceCandBL.extractProcessedOverride(invoiceCand).isTrue())
		{
			return; // #183 FRESH-511: nothing to check or update
		}

		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);
		final IInvoiceCandDAO invoiceCandDAO = Services.get(IInvoiceCandDAO.class);

		final I_C_Flatrate_Term term = flatrateDAO.retrieveNonSimTermOrNull(invoiceCand);
		final boolean newIsToClear = term != null;
		if (invoiceCand.isToClear() != newIsToClear)
		{
			invoiceCand.setIsToClear(newIsToClear);
			invoiceCandDAO.invalidateCand(invoiceCand);
		}

		final boolean dateOrdereChanged = InterfaceWrapperHelper.isValueChanged(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_DateOrdered) && !InterfaceWrapperHelper.isNew(invoiceCand);
		if (newIsToClear && dateOrdereChanged)
		{
			Check.assume(false, "Column I_C_Invoice_Cand." + I_C_Invoice_Candidate.COLUMNNAME_DateOrdered + " is not updatable; I_C_Invoice_Cand={}", invoiceCand);
			// If the column was updatable, we would have to
			// *check if new and old term are the same
			// *check if ICAs need update, creation or deletion and do it;
			// *check which dataEntries' ActualQty needs update and make sure that they are not yet completed
			// *check if isToClear needs update;
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_Invoice_Candidate.COLUMNNAME_Processed)
	public void processRelatedCandidates(@NonNull final I_C_Invoice_Candidate invoiceCand)
	{
		if (invoiceCand.isToClear())
		{
			return; // nothing to do
		}
		if (!invoiceCand.isProcessed())
		{
			return; // nothing to do
		}

		final TableRecordReference tableRecordReference = TableRecordReference.ofReferenced(invoiceCand);
		if (I_C_Flatrate_DataEntry.Table_Name.equals(tableRecordReference.getTableName()))
		{
			final I_C_Flatrate_DataEntry entryRecord = tableRecordReference.getModel(I_C_Flatrate_DataEntry.class);
			closeCandsToClear(flatrateDAO.retrieveClearingAllocs(entryRecord));
		}
		else if (I_C_Flatrate_Term.Table_Name.equals(tableRecordReference.getTableName()))
		{
			final I_C_Flatrate_Term termRecord = tableRecordReference.getModel(I_C_Flatrate_Term.class);
			closeCandsToClear(flatrateDAO.retrieveClearingAllocs(termRecord));
		}
	}

	private void closeCandsToClear(@NonNull final List<I_C_Invoice_Clearing_Alloc> clearingAllocs)
	{
		for (final I_C_Invoice_Clearing_Alloc clearingAlloc : clearingAllocs)
		{
			final I_C_Invoice_Candidate invoiceCandToClear = clearingAlloc.getC_Invoice_Cand_ToClear();
			invoiceCandBL.closeInvoiceCandidate(invoiceCandToClear);
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void createIca(final I_C_Invoice_Candidate invoiceCand)
	{
		if (!invoiceCand.isToClear())
		{
			return; // nothing to do
		}

		// create an invoice candidate allocation record for 'invoiceCand'
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		final I_C_Flatrate_Term term = Check.assumeNotNull(flatrateDAO.retrieveNonSimTermOrNull(invoiceCand), "There is a term for " + invoiceCand);

		final I_C_Invoice_Clearing_Alloc ica = InterfaceWrapperHelper.newInstance(I_C_Invoice_Clearing_Alloc.class, invoiceCand);

		ica.setAD_Org_ID(invoiceCand.getAD_Org_ID());
		Check.assume(ica.getAD_Client_ID() == invoiceCand.getAD_Client_ID(), "po.getCtx() contains the correct AD_Client_ID");

		ica.setC_Invoice_Cand_ToClear(invoiceCand);
		ica.setC_Flatrate_Term_ID(term.getC_Flatrate_Term_ID());

		// task 07371: the code to retrieve and link the C_FlatrateDataEntry for 'invoiceCand' and 'term' was moved to a dedicated MV 'C_Invoice_Clearing_Alloc'.

		InterfaceWrapperHelper.save(ica);
	}

	/**
	 * Deletes {@link I_C_Invoice_Clearing_Alloc}s and udpates {@link I_C_Flatrate_DataEntry}s.
	 */
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_DELETE })
	public void cleanup(final I_C_Invoice_Candidate invoiceCand)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		// cleanup
		for (final I_C_Invoice_Clearing_Alloc ica : flatrateDAO.retrieveAllClearingAllocs(invoiceCand))
		{
			if (ica.isActive() && ica.getC_Flatrate_DataEntry_ID() > 0)
			{
				final I_C_Flatrate_DataEntry dataEntry = ica.getC_Flatrate_DataEntry();
				if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(dataEntry.getDocStatus()))
				{
					throw new InconsistentUpdateException(
							MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P,
							MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P,
							new Object[] { invoiceCand.getC_Invoice_Candidate_ID(), dataEntry.getC_Period().getName() },
							userDAO.getById(UserId.ofRepoId(dataEntry.getC_Flatrate_Term().getAD_User_InCharge_ID())));
				}
			}
			InterfaceWrapperHelper.delete(ica);

			if (invoiceCand.isToClear())
			{
				final List<I_C_Invoice_Clearing_Alloc> icas = flatrateDAO.retrieveClearingAllocs(invoiceCand);
				Check.assume(icas.size() <= 1, invoiceCand + " has max 1 C_Invoice_Clearing_Alloc");

				if (!icas.isEmpty() && icas.get(0).getC_Flatrate_DataEntry_ID() > 0)
				{
					final I_C_Flatrate_DataEntry dataEntry = icas.get(0).getC_Flatrate_DataEntry();
					final BigDecimal diff = invoiceCand.getQtyToInvoice().add(invoiceCand.getQtyInvoiced());
					dataEntry.setActualQty(dataEntry.getActualQty().subtract(diff));
					InterfaceWrapperHelper.save(dataEntry);
				}
			}
		}
	}

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW, ModelValidator.TYPE_AFTER_CHANGE }, ifColumnsChanged = { I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced, I_C_Invoice_Candidate.COLUMNNAME_IsToClear })
	public void updateDataEntriesOnNewAndchange(final I_C_Invoice_Candidate invoiceCand)
	{
		final IFlatrateDAO flatrateDAO = Services.get(IFlatrateDAO.class);

		final boolean qtyChanged = InterfaceWrapperHelper.isValueChanged(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice)
				|| InterfaceWrapperHelper.isValueChanged(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced);

		final boolean isToClearChanged = InterfaceWrapperHelper.isValueChanged(invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_IsToClear);

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
				if (dataEntry.getC_Flatrate_Term().isClosingWithActualSum()) // TODO: don't use a virtual column!
				{
					if (X_C_Flatrate_DataEntry.DOCSTATUS_Completed.equals(dataEntry.getDocStatus()))
					{
						throw new InconsistentUpdateException(
								MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_0P,
								MSG_DATA_ENTRY_ERROR_ALREADY_COMPLETED_TEXT_2P,
								new Object[] { invoiceCand.getC_Invoice_Candidate_ID(), dataEntry.getC_Period().getName() },
								userDAO.getById(UserId.ofRepoId(dataEntry.getC_Flatrate_Term().getAD_User_InCharge_ID())));
					}
					updateActualQty(dataEntry, invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyToInvoice, isToClearChanged);
					updateActualQty(dataEntry, invoiceCand, I_C_Invoice_Candidate.COLUMNNAME_QtyInvoiced, isToClearChanged);

					// note: this will also trigger updates of the other values of 'dataEntry' as well as of
					// possible aux entries
					InterfaceWrapperHelper.save(dataEntry);
				}
			}
		}
	}

	/**
	 * @param isToClearChanged if <code>true</code>, then we assume that IsToClean has been change to 'Y'. In that case, we don't add the difference between old and new value, but just the new value
	 */
	private void updateActualQty(
			final I_C_Flatrate_DataEntry dataEntry,
			final I_C_Invoice_Candidate invoiceCand,
			final String colName,
			final boolean isToClearChanged)
	{
		final PO po = InterfaceWrapperHelper.getPO(invoiceCand);

		BigDecimal oldValue = (BigDecimal)po.get_ValueOld(colName);
		if (isToClearChanged || oldValue == null)
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

}
