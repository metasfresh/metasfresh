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

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.ObjectUtils;

import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.model.I_M_ReceiptSchedule_Alloc;

/**
 * Collects {@link I_M_ReceiptSchedule_Alloc}s and produces {@link HUReceiptLineCandidate}s.
 *
 * Each {@link HUReceiptLineCandidate} will become later an Receipt Line.
 *
 * @author tsa
 *
 */
/* package */class HUReceiptLineCandidatesBuilder
{
	// Parameters
	private IHUContext _huContext;
	private final I_M_ReceiptSchedule _receiptSchedule;

	//
	// Cumulated values
	private IQtyAndQuality _qtyAndQuality = null;
	private final List<HUReceiptLineCandidate> receiptLineCandidates = new ArrayList<HUReceiptLineCandidate>();
	private final transient List<HUReceiptLineCandidate> receiptLineCandidatesRO = Collections.unmodifiableList(receiptLineCandidates);
	private boolean stale = true;

	public HUReceiptLineCandidatesBuilder(final I_M_ReceiptSchedule receiptSchedule)
	{
		super();

		Check.assumeNotNull(receiptSchedule, "receiptSchedule not null");
		_receiptSchedule = receiptSchedule;

		stale = true;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public void setHUContext(final IHUContext huContext)
	{
		_huContext = huContext;
	}

	private IHUContext getHUContext()
	{
		Check.assumeNotNull(_huContext, "_huContext not null");
		return _huContext;
	}

	private I_M_ReceiptSchedule getM_ReceiptSchedule()
	{
		return _receiptSchedule;
	}

	/**
	 * Aggregates and adds given {@link I_M_ReceiptSchedule_Alloc} with its {@link I_M_ReceiptSchedule_Alloc#COLUMNNAME_M_TU_HU_ID M_TU_HU}
	 *
	 * @param rsa
	 */
	public void add(final I_M_ReceiptSchedule_Alloc rsa)
	{
		//
		// Create HUReceiptLineCandidate Part
		final IHUContext huContext = getHUContext();
		final I_M_HU tuHU = rsa.getM_TU_HU();
		final HUReceiptLinePartAttributes partAttributes = HUReceiptLinePartAttributes.newInstance(huContext, tuHU);
		final HUReceiptLinePartCandidate receiptLinePartToAdd = new HUReceiptLinePartCandidate(partAttributes);
		receiptLinePartToAdd.add(rsa);

		//
		// Add it
		add(receiptLinePartToAdd);
	}

	/**
	 * Aggregates and adds given {@link HUReceiptLinePartCandidate}.
	 *
	 * @param receiptLinePartToAdd
	 */
	protected void add(final HUReceiptLinePartCandidate receiptLinePartToAdd)
	{
		//
		// Try adding Part to existing Receipt Line Candidate
		for (final HUReceiptLineCandidate receiptLineCandidate : receiptLineCandidates)
		{
			if (receiptLineCandidate.add(receiptLinePartToAdd))
			{
				stale = true; // we need to recompute the amounts
				return;
			}
		}

		//
		// Create a new Receipt Line Candidate and add the Part to it
		final I_M_ReceiptSchedule receiptSchedule = getM_ReceiptSchedule();
		final HUReceiptLineCandidate receiptLineCandidate = new HUReceiptLineCandidate(receiptSchedule);
		if (!receiptLineCandidate.add(receiptLinePartToAdd))
		{
			// shall not happen
			throw new AdempiereException("New candidate " + receiptLineCandidate + " refused to add " + receiptLinePartToAdd);
		}
		receiptLineCandidates.add(receiptLineCandidate); // 06135: don't forget to add the fruits of our falbour :-P
		stale = true; // we need to recompute the amounts
	}

	public List<HUReceiptLineCandidate> getHUReceiptLineCandidates()
	{
		return receiptLineCandidatesRO;
	}

	private final void updateIfStale()
	{
		if (!stale)
		{
			return;
		}

		//
		// Compute qty&quality (qtys, discount percent, quality notices)
		// Collect receipt schedule allocations
		final MutableQtyAndQuality qtyAndQuality = new MutableQtyAndQuality();
		final List<I_M_ReceiptSchedule_Alloc> receiptScheduleAllocs = new ArrayList<I_M_ReceiptSchedule_Alloc>();
		for (final HUReceiptLineCandidate receiptLineCandidate : receiptLineCandidates)
		{
			final IQtyAndQuality lineQtyAndQuality = receiptLineCandidate.getQtyAndQuality();
			qtyAndQuality.add(lineQtyAndQuality);
			receiptScheduleAllocs.addAll(receiptLineCandidate.getReceiptScheduleAllocs());
		}

		//
		// Update cumulated values
		_qtyAndQuality = qtyAndQuality;

		stale = false; // not staled anymore
	}

	public final IQtyAndQuality getQtyAndQuality()
	{
		updateIfStale();
		return _qtyAndQuality;
	}
}
