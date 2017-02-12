package de.metas.banking.payment.modelvalidator;

/*
 * #%L
 * de.metas.banking.base
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


import java.util.Collection;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_C_AllocationHdr;
import org.compiere.model.I_C_AllocationLine;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.compiere.model.ModelValidator;
import org.slf4j.Logger;

import de.metas.banking.payment.IPaySelectionBL;
import de.metas.banking.payment.IPaySelectionUpdater;
import de.metas.logging.LogManager;

@Interceptor(I_C_AllocationHdr.class)
public class C_AllocationHdr
{
	public static final transient C_AllocationHdr instance = new C_AllocationHdr();
	private final transient Logger logger = LogManager.getLogger(getClass());

	private C_AllocationHdr()
	{
		super();
	}

	/**
	 * After {@link I_C_AllocationHdr} was completed/reversed/voided/reactivated,
	 * update all {@link I_C_PaySelectionLine}s which were not already processed and which are about the invoices from this allocation.
	 * 
	 * @param allocationHdr
	 * @task 08972
	 */
	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE
			, ModelValidator.TIMING_AFTER_REVERSECORRECT
			, ModelValidator.TIMING_AFTER_REVERSEACCRUAL
			, ModelValidator.TIMING_AFTER_VOID
			, ModelValidator.TIMING_AFTER_REACTIVATE })
	public void updateDraftedPaySelectionLines(final I_C_AllocationHdr allocationHdr)
	{
		final IContextAware context = InterfaceWrapperHelper.getContextAware(allocationHdr);

		//
		// Retrieve all C_PaySelectionLines which are about invoices from our allocation and which are not already processed.
		// The C_PaySelectionLines will be groupped by C_PaySelection_ID.
		//@formatter:off
		final Collection<List<I_C_PaySelectionLine>> paySelectionLinesGroups = Services.get(IQueryBL.class)
				//
				// Get all C_AllocationLines
				.createQueryBuilder(I_C_AllocationLine.class, context)
				.addEqualsFilter(I_C_AllocationLine.COLUMN_C_AllocationHdr_ID, allocationHdr.getC_AllocationHdr_ID())
				//
				// Collect all invoices from allocation lines
				.andCollect(I_C_AllocationLine.COLUMN_C_Invoice_ID)
				//
				// Collect all C_PaySelectionLines which are about those invoices
				.andCollectChildren(I_C_PaySelectionLine.COLUMN_C_Invoice_ID, I_C_PaySelectionLine.class)
				// Only those which are not processed
				.addEqualsFilter(I_C_PaySelectionLine.COLUMN_Processed, false)
				// Only those which are active 
				.addOnlyActiveRecordsFilter()
				//
				// Retrieve C_PaySelectionLines and group them by C_PaySelection_ID
				.create()
				.listAndSplit(I_C_PaySelectionLine.class, I_C_PaySelectionLine.COLUMN_C_PaySelection_ID.asValueFunction());
		//@formatter:on

		//
		// Update each C_PaySelectionLines group
		for (final Collection<I_C_PaySelectionLine> paySelectionLines : paySelectionLinesGroups)
		{
			updatePaySelectionLines(context, paySelectionLines);
		}
	}

	/**
	 * Update all given pay selection lines.
	 * 
	 * NOTE: pay selection lines shall ALL be part of the same {@link I_C_PaySelection}.
	 * 
	 * @param context
	 * @param paySelectionLines
	 */
	private final void updatePaySelectionLines(final IContextAware context, final Collection<I_C_PaySelectionLine> paySelectionLines)
	{
		// shall not happen
		if (paySelectionLines.isEmpty())
		{
			return;
		}

		// Make sure the C_PaySelection was not already processed.
		// Shall not happen.
		final I_C_PaySelection paySelection = paySelectionLines.iterator().next().getC_PaySelection();
		if (paySelection.isProcessed())
		{
			logger.info("Skip updating lines because pay selection was already processed: {}", paySelection);
			return;
		}
		if (paySelection.isProcessing())
		{
			logger.info("Skip updating lines because pay selection was locked: {}", paySelection);
		}

		//
		// Update all pay selection lines
		final IPaySelectionBL paySelectionBL = Services.get(IPaySelectionBL.class);
		final IPaySelectionUpdater paySelectionUpdater = paySelectionBL.newPaySelectionUpdater();
		paySelectionUpdater
				.setContext(context)
				.setC_PaySelection(paySelection)
				.addPaySelectionLinesToUpdate(paySelectionLines)
				.update();

		logger.info("Updated {}", paySelectionUpdater.getSummary());
	}
}
