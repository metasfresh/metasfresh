package de.metas.inoutcandidate.spi.impl;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;
import org.compiere.model.I_C_UOM;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.inout.model.I_M_QualityNote;

/**
 * Collects {@link HUReceiptLinePartCandidate}s and behaves like a candidate for receipt line (i.e. {@link I_M_InOutLine}).
 *
 * @author tsa
 *
 */
/* package */class HUReceiptLineCandidate
{
	//
	// Params
	private final I_M_ReceiptSchedule _receiptSchedule;

	//
	// Aggregation Matchers
	private int _subProducerBPartnerId = -1;
	private Object _asiAggregationKey = null;

	private final List<HUReceiptLinePartCandidate> receiptLinePartCandidates = new ArrayList<HUReceiptLinePartCandidate>();

	/**
	 * This variable will keep the QualityNote of the current Receipt Line Candidate
	 */
	private I_M_QualityNote _qualityNote = null;

	//
	// Aggregated values
	private boolean _stale = true;
	private IQtyAndQuality _qtyAndQuality = null;
	private List<I_M_ReceiptSchedule_Alloc> _receiptScheduleAllocs = null;

	public HUReceiptLineCandidate(final I_M_ReceiptSchedule receiptSchedule)
	{
		super();

		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
		_receiptSchedule = receiptSchedule;

		_stale = true;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public boolean add(final HUReceiptLinePartCandidate receiptLinePartToAdd)
	{
		Check.assumeNotNull(receiptLinePartToAdd, "receiptLinePartToAdd not null");

		//
		// Check if we can add this Part to somewhere to this candidate
		if (!canAdd(receiptLinePartToAdd))
		{
			return false;
		}

		//
		// Add given Part to existing Part if possible
		for (final HUReceiptLinePartCandidate receiptLinePart : receiptLinePartCandidates)
		{
			if (receiptLinePart.add(receiptLinePartToAdd))
			{
				_stale = true; // flag this candidate as stale; we need to recompute values
				return true;
			}
		}

		//
		// Add as a new Part
		if (isNew())
		{
			// We are dealing with a new candidate.
			// => initialize it and add this part as it's first part
			init(receiptLinePartToAdd);
		}
		receiptLinePartCandidates.add(receiptLinePartToAdd);
		_stale = true; // flag this candidate as stale; we need to recompute values

		return true;
	}

	/**
	 * Checks if we can add given part to our candidate.
	 *
	 * A part can be added if this candidate is new or if it was compatible fields.
	 *
	 * @param receiptLinePartToAdd
	 * @return true if part can be added
	 */
	private final boolean canAdd(final HUReceiptLinePartCandidate receiptLinePartToAdd)
	{
		// If this is a new candidate, we can accept everything
		if (isNew())
		{
			return true;
		}

		// NOTE: we assume we are dealing with same receipt schedule

		// It shall have the same SubProducer
		if (_subProducerBPartnerId != receiptLinePartToAdd.getSubProducer_BPartner_ID())
		{
			return false;
		}

		// It shall have the same ASI Aggregation Key
		final Object receiptLinePartToAdd_ASIAggregationKey = receiptLinePartToAdd.getAttributeStorageAggregationKey();
		if (!Check.equals(_asiAggregationKey, receiptLinePartToAdd_ASIAggregationKey))
		{
			return false;
		}

		return true;
	}

	/**
	 * @return true if this candidate is new and it was not initialized yet
	 */
	private final boolean isNew()
	{
		return receiptLinePartCandidates.isEmpty();
	}

	/**
	 * Initialize this candidate by setting the values from given part.
	 *
	 * NOTE: this method is NOT collecting the given part.
	 *
	 * @param receiptLinePart
	 */
	private final void init(final HUReceiptLinePartCandidate receiptLinePart)
	{
		Check.assume(isNew(), "this candidate shall be new in order to be able to initialize it: {}", this);

		_subProducerBPartnerId = receiptLinePart.getSubProducer_BPartner_ID();
		_asiAggregationKey = receiptLinePart.getAttributeStorageAggregationKey();
	}

	private final void updateIfStale()
	{
		if (!_stale)
		{
			return;
		}

		//
		// Iterate all Parts and
		// * Compute qty and QtyWithIssues
		// * Collect quality notices
		// * Collect receipt schedule allocations
		final MutableQtyAndQuality qtyAndQuality = new MutableQtyAndQuality();
		final List<I_M_ReceiptSchedule_Alloc> receiptScheduleAllocs = new ArrayList<I_M_ReceiptSchedule_Alloc>();
		for (final HUReceiptLinePartCandidate receiptLinePart : receiptLinePartCandidates)
		{
			// In case there are several qualityNotes, only the first one shall be remembered
			if(_qualityNote == null)
			{
				_qualityNote = receiptLinePart.getQualityNote();
			}
			
			final IQtyAndQuality partQtyAndQuality = receiptLinePart.getQtyAndQuality();
			if (partQtyAndQuality.isZero())
			{
				// skip receipt line parts where Qty is ZERO
				// NOTE: we will also skip receipt schedule allocs but that shall be fine
				// because if Qty is ZERO it means it was an allocation and deallocation
				// so we don't need the packing materials from them
				continue;
			}

			qtyAndQuality.add(partQtyAndQuality);
			receiptScheduleAllocs.addAll(receiptLinePart.getReceiptScheduleAllocs());
		}

		//
		// Update candidate's cumulated values
		_qtyAndQuality = qtyAndQuality;
		_receiptScheduleAllocs = Collections.unmodifiableList(receiptScheduleAllocs);

		_stale = false; // not staled anymore
	}

	/** @return receipt schedule; never return null */
	public I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return _receiptSchedule;
	}

	public int getSubProducer_BPartner_ID()
	{
		// updateIfStale(); // no need
		return _subProducerBPartnerId;
	}

	public List<I_M_ReceiptSchedule_Alloc> getReceiptScheduleAllocs()
	{
		updateIfStale();
		return _receiptScheduleAllocs;
	}

	public I_C_UOM getC_UOM()
	{
		return getM_ReceiptSchedule().getC_UOM();
	}

	public IQtyAndQuality getQtyAndQuality()
	{
		updateIfStale();
		return _qtyAndQuality;
	}
	
	public I_M_QualityNote get_qualityNote()
	{
		return _qualityNote;
	}
}
