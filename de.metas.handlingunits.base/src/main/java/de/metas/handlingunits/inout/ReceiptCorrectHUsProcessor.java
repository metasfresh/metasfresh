package de.metas.handlingunits.inout;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.adempiere.util.Check;
import org.adempiere.util.GuavaCollectors;
import org.adempiere.util.Services;
import org.compiere.model.I_M_InOut;
import org.compiere.process.DocAction;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Ordering;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.TreeMultimap;

import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUAware;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.handlingunits.util.HUByIdComparator;
import de.metas.inout.event.InOutProcessedEventBus;
import de.metas.inoutcandidate.api.IReceiptScheduleDAO;

/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2017 metas GmbH
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

/**
 * Helper class used to reverse the receipts for a given set of HUs.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class ReceiptCorrectHUsProcessor
{
	public static final Builder builder()
	{
		return new Builder();
	}

	// services
	private final transient IReceiptScheduleDAO receiptScheduleDAO = Services.get(IReceiptScheduleDAO.class);
	private final transient IHUInOutDAO huInOutDAO = Services.get(IHUInOutDAO.class);

	//
	// Pre-built indexes
	private final List<I_M_HU> hus;
	private final Map<Integer, I_M_InOut> huId2inout;
	private final Multimap<Integer, I_M_HU> inoutId2hus;

	private ReceiptCorrectHUsProcessor(final Builder builder)
	{
		super();

		final SetMultimap<Integer, I_M_HU> inoutId2hus = TreeMultimap.create(Ordering.natural(), HUByIdComparator.instance);
		final Map<Integer, I_M_InOut> huId2inout = new HashMap<>();
		final Map<Integer, I_M_HU> husById = new HashMap<>();
		final Set<Integer> seenHUIds = new HashSet<>(); // used not add an HU more than once

		//
		// Retrieve and create HUs
		final I_M_ReceiptSchedule receiptSchedule = builder.getM_ReceiptSchedule();
		final List<I_M_InOut> receipts = receiptScheduleDAO.retrieveCompletedReceipts(receiptSchedule);
		for (final I_M_InOut receipt : receipts)
		{
			final int inoutId = receipt.getM_InOut_ID();
			inoutId2hus.putAll(inoutId, huInOutDAO.retrieveHandlingUnits(receipt));

			for (final I_M_HU hu : inoutId2hus.get(inoutId))
			{
				final int huId = hu.getM_HU_ID();
				if (!seenHUIds.add(huId))
				{
					continue;
				}

				// TODO: check if is a top level HU

				husById.put(huId, hu);
				huId2inout.put(huId, receipt);
			}
		}

		if (husById.isEmpty())
		{
			throw new TerminalException("@NotFound@ @M_HU_ID@");
		}

		hus = ImmutableList.copyOf(husById.values());
		this.huId2inout = ImmutableMap.copyOf(huId2inout);
		this.inoutId2hus = ImmutableMultimap.copyOf(inoutId2hus);
	}

	/**
	 * Reverse given receipts.
	 *
	 * @param receiptsToReverse
	 */
	public void reverseReceipts(final List<I_M_InOut> receiptsToReverse)
	{
		if (receiptsToReverse.isEmpty())
		{
			// TODO: fix exception type
			throw new TerminalException("@NotFound@ @M_InOut_ID@");
		}

		//
		// Reverse those receipts
		final IDocActionBL docActionBL = Services.get(IDocActionBL.class);
		docActionBL.processDocumentsList(receiptsToReverse, DocAction.ACTION_Reverse_Correct, DocAction.STATUS_Reversed);

		// Notify the user that the receipt was reversed
		InOutProcessedEventBus.newInstance()
				.queueEventsUntilCurrentTrxCommit()
				.notify(receiptsToReverse);
	}

	/**
	 * Reverse all receipts that contain any of the given HUs.
	 *
	 * @param hus HUs
	 */
	public void reverseReceiptsForHUs(final Collection<I_M_HU> hus)
	{
		final List<I_M_InOut> receiptsToReverse = getReceiptsToReverse(IHUAware.transformHUCollection(hus));
		reverseReceipts(receiptsToReverse);
	}

	/**
	 * @return all possible HUs which can be considered for reversing.
	 */
	public List<I_M_HU> getAvailableHUsToReverse()
	{
		return hus;
	}

	/**
	 * @param hu
	 * @return receipt which contains the given HU or <code>null</code> if no receipt is matching.
	 */
	public I_M_InOut getReceiptOrNull(final I_M_HU hu)
	{
		final int huId = hu.getM_HU_ID();
		final I_M_InOut receipt = huId2inout.get(huId);
		return receipt;
	}

	/**
	 * @param hu
	 * @return receipt which contains the given HU or <code>null</code> if no receipt is matching.
	 */
	public I_M_InOut getReceiptOrNull(final IHUAware huAware)
	{
		final I_M_HU hu = huAware.getM_HU();
		return getReceiptOrNull(hu);
	}

	/**
	 * Get the receipts to be reversed based on given HUs.
	 *
	 * @param huAwareList
	 * @return receipts
	 */
	public List<I_M_InOut> getReceiptsToReverse(final Collection<? extends IHUAware> huAwareList)
	{
		if (huAwareList == null || huAwareList.isEmpty())
		{
			return ImmutableList.of();
		}

		return huAwareList.stream()
				.map(this::getReceiptOrNull)
				.filter(receipt -> receipt != null) // it could be that user selected not a top level HU.... skip it for now
				.collect(GuavaCollectors.toImmutableList());
	}

	/**
	 * Get all HUs which are assigned to given receipts.
	 *
	 * @param receipts
	 * @return HUs
	 */
	public Set<I_M_HU> getHUsForReceipts(final Collection<? extends I_M_InOut> receipts)
	{
		final Set<I_M_HU> hus = new TreeSet<>(HUByIdComparator.instance);

		for (final I_M_InOut receipt : receipts)
		{
			final int inoutId = receipt.getM_InOut_ID();
			final Collection<I_M_HU> husForReceipt = inoutId2hus.get(inoutId);
			if (Check.isEmpty(husForReceipt))
			{
				continue;
			}

			hus.addAll(husForReceipt);
		}

		return hus;
	}

	public static final class Builder
	{
		private I_M_ReceiptSchedule receiptSchedule;

		private Builder()
		{
			super();
		}

		public ReceiptCorrectHUsProcessor build()
		{
			return new ReceiptCorrectHUsProcessor(this);
		}

		public Builder setM_ReceiptSchedule(final I_M_ReceiptSchedule receiptSchedule)
		{
			this.receiptSchedule = receiptSchedule;
			return this;
		}

		private I_M_ReceiptSchedule getM_ReceiptSchedule()
		{
			Check.assumeNotNull(receiptSchedule, "Parameter receiptSchedule is not null");
			return receiptSchedule;
		}

	}

}
