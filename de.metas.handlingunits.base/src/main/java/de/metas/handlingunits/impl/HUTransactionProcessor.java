package de.metas.handlingunits.impl;

/*
 * #%L
 * de.metas.handlingunits.base
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IReference;
import org.adempiere.util.lang.LazyInitializer;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.HUConstants;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUTransaction;
import de.metas.handlingunits.IHUTransactionAttribute;
import de.metas.handlingunits.IHUTransactionProcessor;
import de.metas.handlingunits.IHUTrxBL;
import de.metas.handlingunits.IHUTrxListener;
import de.metas.handlingunits.allocation.IAllocationResult;
import de.metas.handlingunits.attribute.IHUTransactionAttributeProcessor;
import de.metas.handlingunits.attribute.impl.HUTransactionAttributeProcessor;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_Trx_Hdr;
import de.metas.handlingunits.model.I_M_HU_Trx_Line;
import de.metas.handlingunits.storage.IHUItemStorage;
import de.metas.handlingunits.storage.IHUStorageFactory;

public class HUTransactionProcessor implements IHUTransactionProcessor
{
	//
	// Services
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);

	private final IHUContext _huContext;

	public HUTransactionProcessor(final IHUContext huContext)
	{
		super();

		Check.assumeNotNull(huContext, "huContext not null");
		// TODO: shall we assume that trxName is not null???
		_huContext = huContext;
	}

	/**
	 * @return HU Context; never return null
	 */
	private final IHUContext getHUContext()
	{
		return _huContext;
	}

	private final IHUTrxListener getTrxListeners()
	{
		return _huContext.getTrxListeners();
	}

	private LazyInitializer<I_M_HU_Trx_Hdr> createTrxHeaderReference()
	{
		return new LazyInitializer<I_M_HU_Trx_Hdr>()
		{

			@Override
			protected I_M_HU_Trx_Hdr initialize()
			{
				final I_M_HU_Trx_Hdr trxHdr = InterfaceWrapperHelper.newInstance(I_M_HU_Trx_Hdr.class, getHUContext());
				saveTrxHdr(trxHdr);

				return trxHdr;
			}
		};
	}

	private final I_M_HU_Trx_Line createTrxLine(final I_M_HU_Trx_Hdr trxHdr, final IHUTransaction trxLineCandidate)
	{
		Check.assumeNotNull(trxLineCandidate.getCounterpart(),
				"TrxLine shall have a parent set: {}", trxLineCandidate);
		Check.assume(trxLineCandidate != trxLineCandidate.getCounterpart(),
				"TrxLine shall not self reference: {}", trxLineCandidate);

		final I_M_HU_Trx_Line trxLine = InterfaceWrapperHelper.newInstance(I_M_HU_Trx_Line.class, trxHdr);
		if (trxLineCandidate.isSkipProcessing())
		{
			trxLine.setProcessed(true);
		}

		final I_M_HU_Item huItem = trxLineCandidate.getM_HU_Item();
		final I_M_HU hu = huItem == null ? null : huItem.getM_HU();

		trxLine.setAD_Org_ID(trxHdr.getAD_Org_ID());
		trxLine.setM_HU_Trx_Hdr(trxHdr);
		trxLine.setDateTrx(TimeUtil.asTimestamp(trxLineCandidate.getDate()));
		trxLine.setM_Product(trxLineCandidate.getProduct());
		trxLine.setQty(trxLineCandidate.getQuantity().getQty());
		trxLine.setC_UOM(trxLineCandidate.getQuantity().getUOM());
		trxLine.setM_HU(hu);
		trxLine.setM_HU_Item(huItem);

		final I_M_HU_Item vhuItem = trxLineCandidate.getVHU_Item();
		trxLine.setVHU_Item(vhuItem);

		//
		// 07827: Track HU movement locator and status
		trxLine.setM_Locator(trxLineCandidate.getM_Locator());
		trxLine.setHUStatus(trxLineCandidate.getHUStatus());

		huTrxBL.setReferencedObject(trxLine, trxLineCandidate.getReferencedModel());

		saveTrxLine(trxLine);
		return trxLine;
	}

	/**
	 * Process transaction lines.
	 *
	 * NOTE: this method is not marking the transaction header as processed. Please use {@link #markProcessed(LazyInitializer)} to do so.
	 *
	 * @param trxHdrRef
	 * @param trxLines
	 */
	private final void processTrx(final IReference<I_M_HU_Trx_Hdr> trxHdrRef, final List<I_M_HU_Trx_Line> trxLines)
	{
		Check.assumeNotNull(trxHdrRef, "trxHdrRef not null");
		Check.assumeNotNull(trxLines, "trxLines not null");

		//
		// If no lines, there nothing to do
		if (trxLines.isEmpty())
		{
			return;
		}

		//
		// Validate Transactions count
		if (trxLines.size() < 2)
		{
			throw new AdempiereException("There shall be at least 2 transaction lines in order to have a valid transaction."
					+ "\nLines count: " + trxLines.size()
					+ "\nLines: " + trxLines);
		}

		//
		// Validate Transaction Total Qty
		// NOTE: this checking cannot be performed anymore because:
		// 1. trxLines could have different UOMs and a proper checking involves UOM conversions
		// 2. it's not accurate because involves roundings.
		//
		// BigDecimal trxQty = BigDecimal.ZERO;
		// for (final I_M_HU_Trx_Line trxLine : trxLines)
		// {
		// final BigDecimal qty = trxLine.getQty();
		// trxQty = trxQty.add(qty);
		// }
		// if (trxQty.signum() != 0)
		// {
		// // Checksum qty shall be zero
		// // In other words, what was transferred from one side shall arrive to the other side
		// throw new AdempiereException("TrxQty not zero on " + trxHdrRef + "."
		// + "\nTrxQty: " + trxQty
		// + "\nTrx lines: " + trxLines);
		// }

		//
		// Process each line
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			processTrxLine(trxLine);
		}

		getTrxListeners().afterTrxProcessed(trxHdrRef, trxLines);
	}

	private final void processTrxLine(final I_M_HU_Trx_Line trxLine)
	{
		// Skip if already processed
		if (trxLine.isProcessed())
		{
			return;
		}

		final I_M_HU_Item vhuItem = trxLine.getVHU_Item();

		final IHUContext huContext = getHUContext();

		//
		// If this transaction affects an HU Item Storage, then update the storage
		// TODO: refactor and move it to a listener with before process event
		if (vhuItem != null)
		{
			final IHUStorageFactory storageFactory = huContext.getHUStorageFactory();
			final IHUItemStorage itemStorage = storageFactory.getStorage(vhuItem);
			itemStorage.addQty(trxLine.getM_Product(), trxLine.getQty(), trxLine.getC_UOM());
		}

		trxLine.setProcessed(true);
		saveTrxLine(trxLine);

		getTrxListeners().trxLineProcessed(huContext, trxLine);
	}

	private final void saveTrxHdr(final I_M_HU_Trx_Hdr trxHdr)
	{
		InterfaceWrapperHelper.save(trxHdr);
	}

	private final void saveTrxLine(final I_M_HU_Trx_Line trxLine)
	{
		if (!HUConstants.DEBUG_07277_saveHUTrxLine)
		{
			return; // FIXME: debugging
		}

		InterfaceWrapperHelper.save(trxLine);
	}

	@Override
	public void createTrx(final IAllocationResult result)
	{
		//
		// Create Transaction Header Reference
		final LazyInitializer<I_M_HU_Trx_Hdr> trxHdrRef = createTrxHeaderReference();

		//
		// Create and process transaction lines
		final List<IHUTransaction> trxCandidates = result.getTransactions();
		createAndProcessTrx(trxHdrRef, trxCandidates);

		//
		// Create an processes header attribute transactions
		final List<IHUTransactionAttribute> attributeTrxs = result.getAttributeTransactions();
		final IHUTransactionAttributeProcessor attributeTrxProcessor = createHUTransactionAttributeProcessor(trxHdrRef);
		attributeTrxProcessor.createAndProcess(attributeTrxs);

		//
		// Mark the transaction header as processed because everything was processed
		markProcessed(trxHdrRef);
	}

	private void createAndProcessTrx(final IReference<I_M_HU_Trx_Hdr> trxHdrRef, final List<IHUTransaction> trxCandidates)
	{
		final List<I_M_HU_Trx_Line> trxLines = create(trxHdrRef, trxCandidates);
		processTrx(trxHdrRef, trxLines);
	}

	private List<I_M_HU_Trx_Line> create(final IReference<I_M_HU_Trx_Hdr> trxHdrRef, final List<IHUTransaction> trxCandidates)
	{
		if (trxCandidates.isEmpty())
		{
			return Collections.emptyList();
		}

		//
		// Create Transaction Line for each transaction line candidate
		// Also build some indexes to be able to set counterparts after
		final List<I_M_HU_Trx_Line> trxLines = new ArrayList<I_M_HU_Trx_Line>();
		final Map<IHUTransaction, I_M_HU_Trx_Line> trxCandidate2trxLine = new IdentityHashMap<IHUTransaction, I_M_HU_Trx_Line>();

		for (final IHUTransaction trxCandidate : trxCandidates)
		{
			final I_M_HU_Trx_Hdr trxHdr = trxHdrRef.getValue();
			final I_M_HU_Trx_Line trxLine = createTrxLine(trxHdr, trxCandidate);
			trxCandidate2trxLine.put(trxCandidate, trxLine);
			trxLines.add(trxLine);
		}

		//
		// Set Counterpart Transaction Link
		for (final IHUTransaction trxCandidate : trxCandidates)
		{
			final IHUTransaction counterpartTrxCandidate = trxCandidate.getCounterpart();
			if (counterpartTrxCandidate == null)
			{
				throw new AdempiereException("No counterpart transaction was found for " + trxCandidate);
				// continue;
			}

			final I_M_HU_Trx_Line counterpartTrxLine = trxCandidate2trxLine.get(counterpartTrxCandidate);
			if (counterpartTrxLine == null)
			{
				throw new AdempiereException("No counterpart transaction was found for " + trxCandidate);
			}

			final I_M_HU_Trx_Line trxLine = trxCandidate2trxLine.get(trxCandidate);
			Check.assumeNotNull(trxLine, "trxLine shall exist for {}", trxCandidate);

			trxLine.setParent_HU_Trx_Line(counterpartTrxLine);

			saveTrxLine(trxLine);
		}

		return trxLines;
	}

	@Override
	public void reverseTrxLines(final List<I_M_HU_Trx_Line> trxLines)
	{
		Check.assumeNotNull(trxLines, "trxLines not null");

		if (trxLines.isEmpty())
		{
			// nothing to do
			return;
		}

		//
		// Build ID to M_HU_Trx_Line map
		final Map<Integer, I_M_HU_Trx_Line> id2trxLine = new HashMap<Integer, I_M_HU_Trx_Line>(trxLines.size());
		for (final I_M_HU_Trx_Line trxLine : trxLines)
		{
			final int trxLineId = trxLine.getM_HU_Trx_Line_ID();
			id2trxLine.put(trxLineId, trxLine);
		}

		//
		// Create Reversal Trx Header
		final LazyInitializer<I_M_HU_Trx_Hdr> reversalTrxHdrRef = createTrxHeaderReference();

		//
		// Iterate trxLines and it's counterpart until we reversed everything
		final List<I_M_HU_Trx_Line> reversalTrxLines = new ArrayList<I_M_HU_Trx_Line>(trxLines.size());
		while (!id2trxLine.isEmpty())
		{
			final int trxLineId = id2trxLine.keySet().iterator().next();
			final I_M_HU_Trx_Line trxLine = id2trxLine.remove(trxLineId);
			Check.assumeNotNull(trxLine, "Trx line for ID={} shall exist in {}", trxLineId, id2trxLine);

			final int counterpartTrxLineId = trxLine.getParent_HU_Trx_Line_ID();
			final I_M_HU_Trx_Line counterpartTrxLine = id2trxLine.remove(counterpartTrxLineId);
			Check.assumeNotNull(counterpartTrxLine,
					"Counterpart trx line with ID={} for trx line {} not found in {}",
					counterpartTrxLineId, trxLine, id2trxLine);

			final I_M_HU_Trx_Line reversalTrxLine = createTrxLineReversal(reversalTrxHdrRef, trxLine);
			final I_M_HU_Trx_Line reversalCounterpartTrxLine = createTrxLineReversal(reversalTrxHdrRef, counterpartTrxLine);

			// Link reversals
			linkTrxLines(reversalTrxLine, reversalCounterpartTrxLine);

			// Add to reversals list to be processed
			reversalTrxLines.add(reversalTrxLine);
			reversalTrxLines.add(reversalCounterpartTrxLine);
		}

		//
		// Process reversal
		processTrx(reversalTrxHdrRef, reversalTrxLines);

		markProcessed(reversalTrxHdrRef);
	}

	private final void markProcessed(final LazyInitializer<I_M_HU_Trx_Hdr> trxHdrRef)
	{
		Check.assumeNotNull(trxHdrRef, "trxHdrRef not null");

		if (!trxHdrRef.isInitialized())
		{
			// transaction header was not created because it was not needed
			// nothing to do
			return;
		}

		final I_M_HU_Trx_Hdr trxHdr = trxHdrRef.getValue();
		Check.assumeNotNull(trxHdr, "trxHdr not null");

		trxHdr.setProcessed(true);
		saveTrxHdr(trxHdr);
	}

	/**
	 * Create a reversal transaction line for given <code>trxLine</code>.
	 *
	 * NOTE: Parent_HU_Trx_Line shall be set.
	 *
	 * @param reversalTrxHdr reversal transaction header
	 * @param trxLine original transaction line
	 * @return reversal transaction line
	 */
	private final I_M_HU_Trx_Line createTrxLineReversal(final IReference<I_M_HU_Trx_Hdr> reversalTrxHdrRef, final I_M_HU_Trx_Line trxLine)
	{
		Check.assumeNotNull(reversalTrxHdrRef, "reversalTrxHdrRef not null");
		Check.assumeNotNull(trxLine, "trxLine not null");

		if (trxLine.getReversalLine_ID() > 0)
		{
			throw new AdempiereException("Cannot reverse " + trxLine + " because it was already reversed by " + trxLine.getReversalLine());
		}

		final I_M_HU_Trx_Hdr reversalTrxHdr = reversalTrxHdrRef.getValue();
		Check.assumeNotNull(reversalTrxHdr, "reversalTrxHdr not null");

		final I_M_HU_Trx_Line reversalTrxLine = InterfaceWrapperHelper.newInstance(I_M_HU_Trx_Line.class, reversalTrxHdr);
		InterfaceWrapperHelper.copyValues(trxLine, reversalTrxLine, true); // honorIsCalculated=true
		reversalTrxLine.setM_HU_Trx_Hdr(reversalTrxHdr);
		reversalTrxLine.setAD_Org_ID(reversalTrxHdr.getAD_Org_ID());
		reversalTrxLine.setIsActive(true);
		reversalTrxLine.setParent_HU_Trx_Line(null); // to be set
		reversalTrxLine.setProcessed(false);
		reversalTrxLine.setQty(trxLine.getQty().negate());
		reversalTrxLine.setReversalLine(trxLine);

		saveTrxLine(reversalTrxLine);

		//
		// Link back to original transaction
		final String trxName = InterfaceWrapperHelper.getTrxName(reversalTrxHdr);
		trxLine.setReversalLine(reversalTrxLine);
		InterfaceWrapperHelper.save(trxLine, trxName);

		//
		// Reverse attribute transactions
		final IHUTransactionAttributeProcessor attributeTrxProcessor = createHUTransactionAttributeProcessor(reversalTrxHdrRef);
		attributeTrxProcessor.reverseTrxAttributes(reversalTrxLine, trxLine);

		return reversalTrxLine;
	}

	/**
	 * Link counterpart transactions (i.e. sets Parent_HU_Trx_Line_ID to point each other).
	 *
	 * NOTE: after linking trx lines will be saved in this method.
	 *
	 * @param trxLine
	 * @param counterpartTrxLine
	 */
	private final void linkTrxLines(final I_M_HU_Trx_Line trxLine, final I_M_HU_Trx_Line counterpartTrxLine)
	{
		Check.assumeNotNull(trxLine, "trxLine not null");
		Check.assumeNotNull(counterpartTrxLine, "conterpartTrxLine not null");

		if (trxLine.getM_HU_Trx_Hdr_ID() != counterpartTrxLine.getM_HU_Trx_Hdr_ID())
		{
			throw new AdempiereException("Transactions lines shall have same header: " + trxLine + ", " + counterpartTrxLine);
		}

		// Link counterpartTrxLine -> trxLine
		if (trxLine.getM_HU_Trx_Line_ID() <= 0)
		{
			// make sure trxLine is saved so we have an ID to link with
			saveTrxLine(trxLine);
		}
		counterpartTrxLine.setParent_HU_Trx_Line(trxLine);
		saveTrxLine(counterpartTrxLine);

		// Link trxLine -> counterpartTrxLine
		trxLine.setParent_HU_Trx_Line(counterpartTrxLine);
		saveTrxLine(trxLine);
	}

	private IHUTransactionAttributeProcessor createHUTransactionAttributeProcessor(final IReference<I_M_HU_Trx_Hdr> trxHdrRef)
	{
		final IHUTransactionAttributeProcessor trxAttributeProcessor = new HUTransactionAttributeProcessor(getHUContext());
		trxAttributeProcessor.setM_HU_Trx_Hdr(trxHdrRef);

		return trxAttributeProcessor;
	}
}
