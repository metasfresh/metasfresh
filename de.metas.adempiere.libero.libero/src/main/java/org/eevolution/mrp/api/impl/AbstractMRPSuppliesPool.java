package org.eevolution.mrp.api.impl;

/*
 * #%L
 * de.metas.adempiere.libero.libero
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.ObjectUtils;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDemandToSupplyAllocation;
import org.eevolution.mrp.api.IMRPSuppliesPool;
import org.slf4j.Logger;

import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMaterialPlanningContext;

public abstract class AbstractMRPSuppliesPool implements IMRPSuppliesPool
{
	//
	// Services
	protected final transient IMRPBL mrpBL = Services.get(IMRPBL.class);
	protected final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	protected final transient ITrxManager trxManager = Services.get(ITrxManager.class);
	protected final transient Logger logger;

	private final IMaterialPlanningContext _mrpContext;
	private final MRPExecutor _mrpExecutor;

	private List<IMutableMRPRecordAndQty> _mrpSuppliesAvailable;

	public AbstractMRPSuppliesPool(final IMaterialPlanningContext mrpContext, final MRPExecutor mrpExecutor)
	{
		super();

		Check.assumeNotNull(mrpContext, "mrpContext not null");
		_mrpContext = mrpContext;
		logger = mrpContext.getLogger();
		Check.assumeNotNull(logger, "logger not null"); // shall not happen

		Check.assumeNotNull(mrpExecutor, "mrpExecutor not null");
		_mrpExecutor = mrpExecutor;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/**
	 *
	 * @return MRP context; never <code>null</code>
	 */
	protected final IMaterialPlanningContext getMRPContext()
	{
		return _mrpContext;
	}

	protected final MRPExecutor getMRPExecutor()
	{
		return _mrpExecutor;
	}

	private final IMRPNotesCollector getMRPNotesCollector()
	{
		return getMRPExecutor().getMRPNotesCollector();
	}

	@Override
	public final void setMRPSupplies(final List<IMutableMRPRecordAndQty> mrpSupplies)
	{
		Check.assumeNotNull(mrpSupplies, "mrpSupplies not null");
		Check.assumeNull(_mrpSuppliesAvailable, "MRP Supplies not already configured");
		_mrpSuppliesAvailable = new ArrayList<>(mrpSupplies);
	}

	protected final List<IMutableMRPRecordAndQty> getMRPSuppliesAvailable()
	{
		Check.assumeNotNull(_mrpSuppliesAvailable, "MRP Supplies configured");
		// NOTE: return the inner list (we are rellying on this)
		return _mrpSuppliesAvailable;
	}

	@Override
	public final List<IMRPDemandToSupplyAllocation> allocate(final IMutableMRPRecordAndQty mrpDemand)
	{
		Check.assumeNotNull(mrpDemand, "mrpDemand not null");

		//
		// Created MRP Supply to Demand allocations
		final List<IMRPDemandToSupplyAllocation> allocations = new ArrayList<>();

		//
		// Step: SCORE current MRP supplies
		// (Iterate available supplies and build up a list of MRP supplies together with their scores)
		final List<MRPSupplyAndScore> mrpSuppliesAndScores = new ArrayList<>();
		final List<IMutableMRPRecordAndQty> mrpSuppliesAvailable = getMRPSuppliesAvailable();
		for (final Iterator<IMutableMRPRecordAndQty> mrpSuppliesAvailableIterator = mrpSuppliesAvailable.iterator(); mrpSuppliesAvailableIterator.hasNext();)
		{
			final IMutableMRPRecordAndQty mrpSupply = mrpSuppliesAvailableIterator.next();

			// Skip those MRP supplies which are out of our context
			if (!isValid(mrpSupply))
			{
				// NOTE: we are not marking it as not available because they could be OK for other pools.
				mrpSuppliesAvailableIterator.remove();
				continue;
			}

			//
			// Calculate the matching score
			final MRPMatchingScore matchingScore = matchSupplyToDemand(mrpSupply, mrpDemand);

			// Skip those MRP supplies which are not compatible with our demand
			if (matchingScore.isNotMatched())
			{
				continue;
			}

			//
			// Add it to our list of MRP Supplies and matching scores list
			final MRPSupplyAndScore mrpSupplyAndScore = new MRPSupplyAndScore(mrpSupply, matchingScore);
			mrpSuppliesAndScores.add(mrpSupplyAndScore);
		}

		//
		// Step: Allocate MRP Supplies, ordered by Best Score
		// Iterate the MRP Supply & Matching score list and allocate them in the order of best matched.
		Collections.sort(mrpSuppliesAndScores, MRPSupplyAndScoreByTopScoreComparator.instance);
		for (final MRPSupplyAndScore mrpSupplyAndScore : mrpSuppliesAndScores)
		{
			// If the MRP demand was fully allocated, stop here
			if (mrpDemand.isZeroQty())
			{
				break;
			}

			// Allocate current MRP Supply to Demand
			final IMutableMRPRecordAndQty mrpSupply = mrpSupplyAndScore.getMRPSupply();

			// Mark it as not available, to be sure that it won't be allocated more then once
			markNotAvailable(mrpSupply);

			//
			// Try allocating MRP supply to MRP demand
			final List<IMRPDemandToSupplyAllocation> allocs = allocate(mrpDemand, mrpSupply);
			allocations.addAll(allocs);

			// After allocation, if it has no qty available, remove it from our pool
			if (mrpSupply.isZeroQty())
			{
				mrpSuppliesAvailable.remove(mrpSupply);
			}
		}

		return allocations;
	}

	/**
	 * Create MRP Demand to MRP Supply allocation
	 * 
	 * @param mrpDemand
	 * @param mrpSupply
	 * @return allocation or <code>null</code>
	 */
	protected List<IMRPDemandToSupplyAllocation> allocate(final IMutableMRPRecordAndQty mrpDemand, final IMutableMRPRecordAndQty mrpSupply)
	{
		final BigDecimal mrpDemandQty = mrpDemand.getQty();
		final BigDecimal mrpSupplyQty = mrpSupply.getQty();

		//
		// Calculate how much we can allocate
		final BigDecimal qtyToAllocate = mrpSupplyQty.min(mrpDemandQty);
		if (qtyToAllocate.signum() == 0)
		{
			return Collections.emptyList();
		}

		//
		// Create an MRP&Qty record for our allocation
		final IMRPDemandToSupplyAllocation alloc = new MRPDemandToSupplyAllocation(mrpDemand.getPP_MRP(), mrpSupply.getPP_MRP(), qtyToAllocate);

		//
		// Process allocation
		// => decrease Supply Qty available to allocate
		// => decrease Demand Qty available to allocate
		{
			final BigDecimal qtyAllocated = alloc.getQtyAllocated();
			mrpSupply.subtractQty(qtyAllocated);
			mrpDemand.subtractQty(qtyAllocated);
			// TODO: shall we update the Parent_MRP_ID of the supply?
		}

		return Collections.singletonList(alloc);
	}

	public final void cancelSupplies()
	{
		//
		// Iterate available supplies and cancel them
		final List<IMutableMRPRecordAndQty> mrpSuppliesAvailable = getMRPSuppliesAvailable();
		final Iterator<IMutableMRPRecordAndQty> mrpSuppliesAvailableIterator = mrpSuppliesAvailable.iterator();
		while (mrpSuppliesAvailableIterator.hasNext())
		{
			final IMutableMRPRecordAndQty mrpSupply = mrpSuppliesAvailableIterator.next();

			// Skip those MRP supplies which are out of our context
			if (!isValid(mrpSupply))
			{
				mrpSuppliesAvailableIterator.remove();
				continue;
			}

			// Mark it as not available anymore
			markNotAvailable(mrpSupply);

			//
			// Cancel it
			cancelSupply(mrpSupply);

			//
			// Remove it from our list
			mrpSuppliesAvailableIterator.remove();
		}
	}

	private final void cancelSupply(final IMutableMRPRecordAndQty mrpSupply)
	{
		final I_PP_MRP mrpSupplyRecord = mrpSupply.getPP_MRP();
		final boolean mrpSupplyIsFirm = mrpBL.isReleased(mrpSupplyRecord);

		//
		// MRP-050 Cancel Action Notice
		// Indicate that a scheduled supply order is no longer needed and should be deleted.
		if (mrpSupplyIsFirm
				&& !mrpSupply.isZeroQty() // has Qty to supply
		)
		{
			newSupplyMRPNote(mrpSupplyRecord, MRPExecutor.MRP_ERROR_050_CancelSupplyNotice)
					.addParameter("SupplyDatePromised", mrpSupplyRecord.getDatePromised())
					.collect();
		}

	}

	/**
	 * Checks if given <code>mrpSupply</code> is valid for our pool.
	 *
	 * NOTE: MRP supplies which are not valid will be removed from our pool, BUT they won't be marked as not available.
	 *
	 * @param mrpSupply
	 * @return true if is valid in our context
	 */
	protected final boolean isValid(final IMRPRecordAndQty mrpSupply)
	{
		final I_PP_MRP mrpSupplyRecord = mrpSupply.getPP_MRP();

		// NOTE: we are not checking for IsAvailable, because that flag is set to false by the pool, first time when the pool is considering this MRP supply.
		// So, if we already did, at least, a partial allocation of this supply, this supply will already have IsAvailable=N.

		// TODO: check if mrpSupply is matching our planning segment from mrpContext

		//
		// Exclude Quantity On Hand reservations
		if (mrpBL.isQtyOnHandAnyReservation(mrpSupplyRecord))
		{
			return false;
		}

		return true;
	}

	/**
	 * Checkes if given <code>mrpSupply</code> shall be accepted for <code>mrpDemand</code>.
	 * 
	 * @param mrpSupply
	 * @param mrpDemand
	 * @return matching score
	 */
	protected final MRPMatchingScore matchSupplyToDemand(final IMRPRecordAndQty mrpSupply, final IMRPRecordAndQty mrpDemand)
	{
		final MRPMatchingScore totalScore = new MRPMatchingScore();

		if (totalScore.isMatchedOrNotSet())
		{
			final int score = matchSupplyToDemand_OrderLineSO(mrpSupply, mrpDemand);
			totalScore.addScore(score);
		}

		if (totalScore.isMatchedOrNotSet())
		{
			final int score = matchSupplyToDemand_MRPParent(mrpSupply, mrpDemand);
			totalScore.addScore(score);
		}

		return totalScore;
	}

	/**
	 * Checkes if given <code>mrpSupply</code> shall be accepted for <code>mrpDemand</code>, considering only the supplies parent link ({@link I_PP_MRP#getPP_MRP_Parent_ID()}).
	 *
	 * @param mrpSupply
	 * @param mrpDemand
	 * @return matching score (see {@link MRPMatchingScore})
	 */
	protected int matchSupplyToDemand_MRPParent(final IMRPRecordAndQty mrpSupply, final IMRPRecordAndQty mrpDemand)
	{
		final I_PP_MRP mrpSupplyRecord = mrpSupply.getPP_MRP();
		final I_PP_MRP mrpDemandRecord = mrpDemand.getPP_MRP();

		final Set<Integer> mrpSupply_Forward_MRPDemandIds = getForward_PP_MRP_Demand_IDs(mrpSupplyRecord);
		final boolean mrpSupply_hasForwardDemands = !mrpSupply_Forward_MRPDemandIds.isEmpty();
		// final int mrpSupplyRecord_ParentId = mrpSupplyRecord.getPP_MRP_Parent_ID();
		final int mrpDemandId = mrpDemandRecord.getPP_MRP_ID();

		//
		// Case: MRP supply is pointing to our MRP Demand
		// => perfect match
		if (mrpSupply_Forward_MRPDemandIds.contains(mrpDemandId))
		{
			return MRPMatchingScore.SCORE_FullMatch;
		}
		//
		// Case: MRP supply is pointing to some demands, but not to this one
		// => not matching at all, this MRP supply shall be allocated to that MRP demand
		else if (mrpSupply_hasForwardDemands)
		{
			return MRPMatchingScore.SCORE_NotMatch;
		}
		//
		// Case: MRP supply is not pointing to any demand
		// => could be matched in case there is nothing else
		else
		{
			return MRPMatchingScore.SCORE_PartialMatch_5;
		}
	}

	protected final Set<Integer> getForward_PP_MRP_Demand_IDs(final I_PP_MRP mrpSupply)
	{
		final String dynAttributeName = "Forward_PP_MRP_Demand_IDs";

		Set<Integer> mrpDemandIds = InterfaceWrapperHelper.getDynAttribute(mrpSupply, dynAttributeName);
		if (mrpDemandIds == null)
		{
			final List<Integer> mrpDemandIdsList = mrpDAO.retrieveForwardMRPDemandsForSupplyQuery(mrpSupply)
					.create()
					.listIds();
			mrpDemandIds = Collections.unmodifiableSet(new HashSet<>(mrpDemandIdsList));
			InterfaceWrapperHelper.setDynAttribute(mrpSupply, dynAttributeName, mrpDemandIds);
		}

		return mrpDemandIds;
	}

	protected final Set<Integer> getBackward_PP_MRP_Supply_IDs(final I_PP_MRP mrpDemand)
	{
		final String dynAttributeName = "Backward_PP_MRP_Supply_IDs";

		Set<Integer> mrpSupplyIds = InterfaceWrapperHelper.getDynAttribute(mrpDemand, dynAttributeName);
		if (mrpSupplyIds == null)
		{
			final List<Integer> mrpSupplyIdsList = mrpDAO.retrieveBackwardMRPSuppliesForDemandQuery(mrpDemand)
					.create()
					.listIds();
			mrpSupplyIds = Collections.unmodifiableSet(new HashSet<>(mrpSupplyIdsList));
			InterfaceWrapperHelper.setDynAttribute(mrpDemand, dynAttributeName, mrpSupplyIds);
		}

		return mrpSupplyIds;
	}

	/**
	 * Checkes if given <code>mrpSupply</code> shall be accepted for <code>mrpDemand</code>, considering only the sales order line ({@link I_PP_MRP#getC_OrderLineSO_ID()}).
	 *
	 * @param mrpSupply
	 * @param mrpDemand
	 * @return matching score (see {@link MRPMatchingScore})
	 */
	protected int matchSupplyToDemand_OrderLineSO(final IMRPRecordAndQty mrpSupply, final IMRPRecordAndQty mrpDemand)
	{
		final int supply_orderLineSOId = mrpSupply.getPP_MRP().getC_OrderLineSO_ID();
		final int demand_orderLineSOId = mrpDemand.getPP_MRP().getC_OrderLineSO_ID();

		if (supply_orderLineSOId > 0)
		{
			// Case: supply and demand are for the same particular sales order line
			// => full match
			if (demand_orderLineSOId > 0 && demand_orderLineSOId == supply_orderLineSOId)
			{
				return MRPMatchingScore.SCORE_FullMatch;
			}
			// Case: supply and demand are for a particular sales order line, but not de same
			// => not matching at all, because the supply shall be allocated to a demand for that sales order
			else if (demand_orderLineSOId > 0)
			{
				return MRPMatchingScore.SCORE_NotMatch;
			}
			// Case: supply is for a particular sales order line but demand is not for a particular sales order line
			// => not matching, because the supply shall be allocated to a demand for that sales order
			else
			{
				return MRPMatchingScore.SCORE_NotMatch;
			}
		}
		else
		{
			// Case: supply is NOT for a particular sales order, but the demand is
			// => partial match, i.e. we shall consider it if there is nothing else to pick
			if (demand_orderLineSOId > 0)
			{
				return MRPMatchingScore.SCORE_PartialMatch_5;
			}
			// Case: supply is NOT for a particular sales order line, nor the demand
			// => full match
			else
			{
				return MRPMatchingScore.SCORE_FullMatch;
			}
		}
	}

	protected final void markNotAvailable(final IMRPRecordAndQty mrpSupply)
	{
		final I_PP_MRP mrpSupplyRecord = mrpSupply.getPP_MRP();
		final MRPExecutor mrpExecutor = getMRPExecutor();
		mrpExecutor.markNotAvailable(mrpSupplyRecord);
	}

	protected final IMRPNoteBuilder newSupplyMRPNote(final I_PP_MRP mrpSupply, final String mrpErrorCode)
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		final IMRPNotesCollector mrpNotesCollector = getMRPNotesCollector();

		return mrpNotesCollector.newMRPNoteBuilder(mrpContext, mrpErrorCode)
				.addParameter("PP_MRP_Supply_ID", mrpSupply)
				.addMRPRecord(mrpSupply) // that's the MRP record which causes troubles
		;
	}

	protected final boolean isOrderPolicy_LFL()
	{
		final IMaterialPlanningContext mrpContext = getMRPContext();
		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();
		if (productPlanning == null)
		{
			// if no product planning.... shall not happen, but, consider it LFL
			return true;
		}
		final String orderPolicy = productPlanning.getOrder_Policy();
		if (orderPolicy == null)
		{
			// if no order policy => consider it LFL
			return true;
		}

		return X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot.equals(orderPolicy);
	}

	protected static final class MRPMatchingScore implements Comparable<MRPMatchingScore>
	{
		public static final int SCORE_NotMatch = 0;
		public static final int SCORE_PartialMatch_5 = 5;
		public static final int SCORE_FullMatch = 9;

		private int totalScore = 0;
		private boolean initialized = false;

		public MRPMatchingScore()
		{
			super();
		}

		@Override
		public String toString()
		{
			return String.valueOf(totalScore);
		}

		@Override
		public int compareTo(final MRPMatchingScore other)
		{
			return this.totalScore - other.totalScore;
		}

		@Override
		public int hashCode()
		{
			final int prime = 31;
			int result = 1;
			result = prime * result + totalScore;
			return result;
		}

		@Override
		public boolean equals(final Object otherObj)
		{
			if (this == otherObj)
			{
				return true;
			}
			if (otherObj == null)
			{
				return false;
			}
			if (getClass() != otherObj.getClass())
			{
				return false;
			}
			final MRPMatchingScore other = (MRPMatchingScore)otherObj;
			if (this.totalScore != other.totalScore)
			{
				return false;
			}
			return true;
		}

		/**
		 * Add given score to current total. If given score is {@link #SCORE_NotMatch} then entire total will be set to not match.
		 * 
		 * @param score
		 */
		public void addScore(final int score)
		{
			// If the total score was already set to NotMatched, keep it like that
			if (initialized && totalScore == 0)
			{
				return;
			}

			Check.assume(score >= 0 && score <= 9, "score shall be between 0 and 9 but it was {}", score);
			if (score == SCORE_NotMatch)
			{
				totalScore = 0;
			}
			else
			{
				totalScore = totalScore * 10 + score;
			}
		}

		public MRPMatchingScore max(final MRPMatchingScore other)
		{
			final int cmp = compareTo(other);
			return cmp > 0 ? other : this;
		}

		public boolean isNotMatched()
		{
			return totalScore == 0;
		}

		public boolean isMatchedOrNotSet()
		{
			if (totalScore > 0)
			{
				return true;
			}
			if (!initialized)
			{
				return true;
			}

			return false;
		}
	}

	private final class MRPSupplyAndScore
	{
		private final IMutableMRPRecordAndQty mrpSupply;
		private final MRPMatchingScore score;

		public MRPSupplyAndScore(final IMutableMRPRecordAndQty mrpSupply, final MRPMatchingScore score)
		{
			super();
			Check.assumeNotNull(mrpSupply, "mrpSupply not null");
			this.mrpSupply = mrpSupply;

			Check.assumeNotNull(score, "score not null");
			this.score = score;
		}

		@Override
		public String toString()
		{
			return ObjectUtils.toString(this);
		}

		public IMutableMRPRecordAndQty getMRPSupply()
		{
			return mrpSupply;
		}

		public MRPMatchingScore getScore()
		{
			return score;
		}
	}

	private static final class MRPSupplyAndScoreByTopScoreComparator implements Comparator<MRPSupplyAndScore>
	{
		public static final transient MRPSupplyAndScoreByTopScoreComparator instance = new MRPSupplyAndScoreByTopScoreComparator();

		private MRPSupplyAndScoreByTopScoreComparator()
		{
			super();
		}

		@Override
		public int compare(final MRPSupplyAndScore o1, final MRPSupplyAndScore o2)
		{
			final MRPMatchingScore score1 = o1.getScore();
			final MRPMatchingScore score2 = o2.getScore();
			final int cmp = score1.compareTo(score2);
			return cmp * (-1); // revert the result in order to get the best scores first
		}
	}
}
