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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Timestamp;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.IMutable;
import org.adempiere.util.lang.Mutable;
import org.adempiere.util.trxConstraints.api.ITrxConstraints;
import org.adempiere.util.trxConstraints.api.ITrxConstraintsBL;
import org.compiere.model.IQuery;
import org.compiere.model.I_AD_Workflow;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Storage;
import org.compiere.model.I_M_Warehouse;
import org.compiere.model.I_S_Resource;
import org.compiere.util.TimeUtil;
import org.compiere.util.TrxRunnable;
import org.eevolution.LiberoConstants;
import org.eevolution.api.IPPWorkflowDAO;
import org.eevolution.api.IProductBOMDAO;
import org.eevolution.model.I_AD_Note;
import org.eevolution.model.I_PP_MRP;
import org.eevolution.model.I_PP_Product_Planning;
import org.eevolution.model.X_PP_MRP;
import org.eevolution.model.X_PP_Product_Planning;
import org.eevolution.mrp.api.IMRPBL;
import org.eevolution.mrp.api.IMRPContextRunnable;
import org.eevolution.mrp.api.IMRPCreateSupplyRequest;
import org.eevolution.mrp.api.IMRPDAO;
import org.eevolution.mrp.api.IMRPDemand;
import org.eevolution.mrp.api.IMRPDemandAggregation;
import org.eevolution.mrp.api.IMRPDemandAggregationFactory;
import org.eevolution.mrp.api.IMRPDemandAllocationResult;
import org.eevolution.mrp.api.IMRPExecutor;
import org.eevolution.mrp.api.IMRPExecutorJobs;
import org.eevolution.mrp.api.IMRPNoteDAO;
import org.eevolution.mrp.api.IMRPQueryBuilder;
import org.eevolution.mrp.api.IMRPResult;
import org.eevolution.mrp.api.IMutableMRPResult;
import org.eevolution.mrp.spi.IMRPSupplyProducer;
import org.eevolution.mrp.spi.IMRPSupplyProducerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import de.metas.logging.LogManager;
import de.metas.material.planning.IMRPNoteBuilder;
import de.metas.material.planning.IMRPNotesCollector;
import de.metas.material.planning.IMRPSegment;
import de.metas.material.planning.IMaterialPlanningContext;
import de.metas.material.planning.IMutableMRPContext;
import de.metas.material.planning.IProductPlanningDAO;
import de.metas.material.planning.ProductPlanningBL;
import de.metas.material.planning.impl.MRPContextFactory;
import de.metas.material.planning.pporder.LiberoException;
import de.metas.purchasing.api.IBPartnerProductDAO;
import de.metas.quantity.Quantity;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class MRPExecutor implements IMRPExecutor
{
	/**
	 * MRP-020 - {@link I_PP_Product_Planning#isCreatePlan()} is <code>false</code> but we have a net requirements quantity to satisfy.
	 */
	public static final String MRP_ERROR_020_CreatePlanDisabledWhenSupplyNeeded = "MRP-020";
	public static final String MRP_ERROR_050_CancelSupplyNotice = "MRP-050";
	/**
	 * MRP-060 - Supply is Due but it's not released yet (i.e. document is still drafted)
	 */
	public static final String MRP_ERROR_060_SupplyDueButNotReleased = "MRP-060";
	/**
	 * MRP-070 - Release Past Due For Action Notice overdue.
	 *
	 * Indicates that a supply order was not released when it was due, and should be either released or expedited now, or the demand rescheduled for a later date.
	 */
	public static final String MRP_ERROR_070_SupplyPastDueButNotReleased = "MRP-070";
	/**
	 * MRP-100 - Time Fence Conflict.
	 *
	 * Indicates that there is an unsatisfied material requirement inside the planning time fence for this product.<br>
	 * You should either manually schedule and expedite orders to fill this demand or delay fulfillment of the requirement that created the demand.
	 */
	public static final String MRP_ERROR_100_TimeFenceConflict = "MRP-100";
	/**
	 * MRP-110 - Indicates that a schedule supply order receipt is past due.
	 *
	 * i.e. it's a firm supply which was scheduled to be released in the past, but so far it's not received yet.
	 */
	public static final String MRP_ERROR_110_SupplyPastDueButReleased = "MRP-110";
	public static final String MRP_ERROR_120_NoProductPlanning = "MRP-120";
	private static final String MRP_ERROR_140_NegativeQtyOnHand = "MRP-140";
	/**
	 * MRP-150 - Past Due Demand: Indicates that a demand order is past due.
	 */
	public static final String MRP_ERROR_150_DemandPastDue = "MRP-150";
	public static final String MRP_ERROR_160_CannotCreateDocument = "MRP-160";

	//
	// Services
	private static final transient Logger loggerDefault = LogManager.getLogger(IMRPExecutor.class); // NOTE: keep that name because we want to access it from outside, using a standard name
	private final transient IMRPDAO mrpDAO = Services.get(IMRPDAO.class);
	private final transient IMRPNoteDAO mrpNoteDAO = Services.get(IMRPNoteDAO.class);
	private final transient IMRPBL mrpBL = Services.get(IMRPBL.class);
	private final transient IMRPDemandAggregationFactory mrpDemandAggregationFactory = Services.get(IMRPDemandAggregationFactory.class);
	private final transient IMRPSupplyProducerFactory _mrpSupplyProducerFactory = Services.get(IMRPSupplyProducerFactory.class);

	@Autowired
	private transient MRPContextFactory mrpContextFactory;

	// Database/Trx services:
	private final ITrxManager trxManager = Services.get(ITrxManager.class);

	private final IProductPlanningDAO productPlanningDAO = Services.get(IProductPlanningDAO.class);
	private final IProductBOMDAO productBOMDAO = Services.get(IProductBOMDAO.class);
	private final IPPWorkflowDAO ppWorkflowDAO = Services.get(IPPWorkflowDAO.class);
	private final IBPartnerProductDAO bpartnerProductDAO = Services.get(IBPartnerProductDAO.class);

	@Autowired
	private transient ProductPlanningBL productPlanningBL;

	//
	// Common parameters
	/** Current logger (never null) */
	private Logger logger = loggerDefault;
	private boolean _subsequentMRPExecutorCall;

	// MRP Notes collector
	private final IMRPNotesCollector _mrpNotesCollector;

	// Jobs
	private final MRPExecutorJobs jobs_AfterSegmentRun = new MRPExecutorJobs();
	private final MRPExecutorJobs jobs_AfterAllSegmentsRun = new MRPExecutorJobs();

	//
	// MRP Result
	private final IMutableMRPResult mrpResult = new MRPResult();
	private final MRPSegmentsCollector mrpChangedSegments = new MRPSegmentsCollector();
	private boolean mrpChangedSegments_CollectEnabled = true;

	public MRPExecutor()
	{
		_mrpNotesCollector = newMRPNotesCollector();
	}

	/**
	 * Init common settings. This method is called before each MRP run/cleanup.
	 *
	 * @param mrpContext
	 */
	private final void initFromMRPContext(final IMaterialPlanningContext mrpContext)
	{
		this.logger = mrpContext.getLogger();
		this._subsequentMRPExecutorCall = mrpContext.isSubsequentMRPExecutorCall();
	}

	@Override
	public final boolean isSubsequentMRPExecutor()
	{
		return this._subsequentMRPExecutorCall;
	}

	public final IMRPSupplyProducerFactory getMRPSupplyProducerFactory()
	{
		return _mrpSupplyProducerFactory;
	}

	private final IMutableMRPContext createMRPContext(final IMaterialPlanningContext mrpContext)
	{
		final IMutableMRPContext mrpContextNew = mrpContextFactory.createMRPContext(mrpContext);
		return mrpContextNew;
	}

	@Override
	public void cleanup(final IMaterialPlanningContext mrpContext)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");
		initFromMRPContext(mrpContext);

		final IMRPSupplyProducer producers = getMRPSupplyProducerFactory().getAllSupplyProducers();

		final IMutableMRPContext mrpContextForCleanup = createMRPContext(mrpContext);
		if (!isSubsequentMRPExecutor())
		{
			mrpContextForCleanup.setTrxName(ITrx.TRXNAME_None); // we want to delete them immediately
		}

		final boolean deleteNotesAndReservations = mrpContextForCleanup.getEnforced_PP_MRP_Demand_ID() <= 0
				&& mrpContext.isAllowCleanup();
		final boolean deleteDocuments = mrpContext.isAllowCleanup();

		final ITrxConstraintsBL trxConstraintsBL = Services.get(ITrxConstraintsBL.class);
		trxConstraintsBL.saveConstraints();
		try
		{
			// Because we are running out of transaction we need to allow local transactions
			if (trxManager.isNull(mrpContextForCleanup.getTrxName()))
			{
				final ITrxConstraints constraints = trxConstraintsBL.getConstraints();
				constraints.addAllowedTrxNamePrefix(ITrx.TRXNAME_PREFIX_LOCAL);
			}

			// Cleanup Quantity On Hand Reservations
			if (deleteNotesAndReservations)
			{
				getMRPSupplyReservator().cleanupQuantityOnHandReservations(mrpContext);
			}

			//
			// Delete old MRP notes; generated documents
			if (deleteDocuments)
			{
				producers.cleanup(mrpContextForCleanup, this);
			}

			// Delete MRP notes
			if (deleteNotesAndReservations)
			{
				final int notesDeleted = mrpNoteDAO.deleteMRPNotes(mrpContextForCleanup);
				mrpResult.addDeletedNotes(notesDeleted);
			}

			// Mark available all MRP supply & demand records for our planning segment
			markMRPRecordsAvailable(mrpContextForCleanup);
		}
		finally
		{
			trxConstraintsBL.restoreConstraints();
		}
	}

	protected void markMRPRecordsAvailable(final IMaterialPlanningContext mrpContext)
	{
		//
		// Update MRP supplies
		getMRPSupplyReservator().markMRPSuppliesAvailable(mrpContext);

		//
		// Update MRP demands
		final int mrpDemands_UpdateCount = createDemandsMRPQueryBuilder(mrpContext)
				// match all, available or not; we need to get all of them available
				.setMRPAvailable(null)
				// Make available MRP records which are not bounded to a plant (e.g. Sales Orders)
				.setAcceptWithoutPlant(true)
				.updateMRPRecordsAndMarkAvailable();
		logger.debug("Marked demands available for #{} records\n{}", new Object[] { mrpDemands_UpdateCount, mrpContext });
	}

	private Iterator<I_PP_MRP> retrieveMRPDemands(final IMaterialPlanningContext mrpContext, final int lowLevel)
	{
		final IMRPQueryBuilder mrpQueryBuilder = createDemandsMRPQueryBuilder(mrpContext)
				// Product's LLC
				.setLowLevelCode(lowLevel)
				// Only those MRP records which are in our planning horizon
				.setDatePromisedMax(mrpContext.getPlanningHorizon())
				// Retrieve only available MRP demands.
				// NOTE: newly generated MRP demands were already set as available (see onMRPRecordBeforeCreate())
				.setMRPAvailable(true)
				// Also get those MRP records which are not bounded to a particular plant
				.setAcceptWithoutPlant(true)
		//
		;

		final IQueryBuilder<I_PP_MRP> queryBuilder = mrpQueryBuilder.createQueryBuilder();

		//
		// Set order by clause (very important)
		queryBuilder.orderBy()
				.addColumn(I_PP_MRP.COLUMNNAME_M_Product_ID)
				.addColumn(I_PP_MRP.COLUMNNAME_DatePromised)
				.addColumn(I_PP_MRP.COLUMNNAME_C_BPartner_ID) // in case of POQ when aggregating o BPartner level
				.addColumn(I_PP_MRP.COLUMNNAME_PP_MRP_ID);

		// NOTE: we are fetching the whole list first because we don't want to be affected by changes of the columns if the where clause
		final IQuery<I_PP_MRP> query = queryBuilder.create();
		final List<I_PP_MRP> mrpDemands = query
				.list();
		return mrpDemands.iterator();
	}

	private IMRPQueryBuilder createDemandsMRPQueryBuilder(final IMaterialPlanningContext mrpContext)
	{
		final IMRPQueryBuilder mrpQueryBuilder = mrpDAO.createMRPQueryBuilder()
				// Planning Dimension
				.setMRPContext(mrpContext)
				.setM_Product_ID(mrpContext.getM_Product_ID())
				// Only demands (Firm, Not Firm)
				.setTypeMRP(X_PP_MRP.TYPEMRP_Demand)
				// Exclude records which have BPartners/Warehouses/etc excluded
				.setSkipIfMRPExcluded(true)
		//
		;

		//
		// If we were asked to balance a precise Demand, filter by it.
		// NOTE: we are running through MRP Query Builder and not returning it right away, just because we want to enforce all other filters
		final List<I_PP_MRP> mrpDemands = mrpContext.getMRPDemands();
		if (!mrpDemands.isEmpty())
		{
			mrpQueryBuilder.addOnlyPP_MRPs(mrpDemands);
		}

		return mrpQueryBuilder;
	}

	@Override
	public IMRPQueryBuilder createMRPQueryBuilder(final IMaterialPlanningContext mrpContext)
	{
		final IMRPQueryBuilder mrpQueryBuilder = mrpDAO.createMRPQueryBuilder()
				// Planning Dimension
				.setMRPContext(mrpContext)
				.setM_Product_ID(mrpContext.getM_Product_ID());

		if (mrpContext.getEnforced_PP_MRP_Demand_ID() > 0)
		{
			mrpQueryBuilder.setEnforced_PP_MRP_Demand_ID(mrpContext.getEnforced_PP_MRP_Demand_ID());
		}

		return mrpQueryBuilder;
	}

	@Override
	public final void runMRP(final IMaterialPlanningContext mrpContext0)
	{
		//
		// Validate MRP Context (i.e. MRP Planning Segment is fully defined)
		Check.assumeNotNull(mrpContext0, "mrpContext0 not null");
		Check.assume(mrpContext0.getAD_Client_ID() > 0, LiberoException.class, "Invalid AD_Client_ID in {}", mrpContext0);
		Check.assumeNotNull(mrpContext0.getAD_Org(), LiberoException.class, "Invalid Organization in {}", mrpContext0);
		Check.assumeNotNull(mrpContext0.getM_Warehouse(), LiberoException.class, "Invalid Warehouse in {}", mrpContext0);
		Check.assumeNotNull(mrpContext0.getPlant(), LiberoException.class, "Invalid Plant in {}", mrpContext0);

		//
		// Get commons settings from given MRP Context and initialize this MRP Executor
		initFromMRPContext(mrpContext0);

		//
		// Status
		IMutableMRPContext mrpContextPrev = null; // MRP Context for previous PP_MRP demand evalutated
		IMRPDemandAggregation mrpDemandAgg = null; // current MRP demand aggregation

		final IMutableMRPContext mrpContextBase = createMRPContext(mrpContext0);
		final int lowlevelMax = mrpDAO.getMaxLowLevel(mrpContextBase);

		logger.info("Evaluating {}", mrpContextBase);
		logger.info("Low Level Max: {}", lowlevelMax);
		// Calculate MRP for all levels
		for (int level = 0; level <= lowlevelMax; level++)
		{
			logger.info("Balancing Low Level: {}/{}", new Object[] { level, lowlevelMax });

			final Iterator<I_PP_MRP> mrpDemands = retrieveMRPDemands(mrpContextBase, level);
			while (mrpDemands.hasNext())
			{
				//
				// After first level that matched our context (with Product constraint)
				// we shall take out the Product restriction in order to match what was generated on higher low level codes (LLCs)
				if (mrpContextBase.getM_Product() != null)
				{
					mrpContextBase.setM_Product(null);
				}

				//
				// Get current Demand
				final I_PP_MRP mrpDemand = mrpDemands.next();
				if (logger.isInfoEnabled())
				{
					logger.info("Evaluating MRP Demand: {}", mrpDemand);
					logger.info("Are there more MRP Demands after this one: {}", mrpDemands.hasNext());
				}

				//
				// Make sure the MRP demand is available for balancing.
				// If it's not available, skip it.
				// If it's available, later, when we are sure that we can solve it, we will flag it as not available anymore.
				if (!isMRPDemandAvailable(mrpContextBase, mrpDemand))
				{
					continue;
				}

				//
				// Mark the demand as not available anymore, not not consider it again.
				markNotAvailable(mrpDemand);

				//
				// MRP Segment changed
				// => calculate plan for exiting Demand
				// => reset MRP Demand Aggregation
				final boolean mrpSegmentChanged = mrpContextPrev == null || mrpContextPrev.getM_Product_ID() != mrpDemand.getM_Product_ID(); // FIXME: rewrite, i.e. check if segment changed
				if (mrpSegmentChanged)
				{
					// If we have some MRP demands aggregated
					// => calculate plan for them
					if (mrpDemandAgg != null && mrpDemandAgg.hasDemands())
					{
						logger.info("Calculate plan for previous MRP aggregated demand: {}", mrpDemandAgg);
						calculatePlan(mrpContextPrev, mrpDemandAgg.createMRPDemand());

						//
						// Discard MRP Demand aggregation because:
						// * was already balanced by calculatePlan
						// * or it's out of Planning Horizon
						// NOTE: anyways, won't be used anymore so we are reseting it just preventivelly
						mrpDemandAgg = null;
					}
				} // new product

				//
				// Setup MRP Context for this run
				final IMutableMRPContext mrpContextCurrent = createMRPContext(mrpContextBase);
				mrpContextCurrent.setMRPDemands(Collections.singletonList(mrpDemand));
				if (mrpSegmentChanged)
				{
					// Load Product & define Product Data Planning
					final I_M_Product product = mrpDemand.getM_Product();
					setProduct(mrpContextCurrent, product);
					// If No Product Planning found, go to next MRP record
					// NOTE: a notice was already created
					if (mrpContextCurrent.getProductPlanning() == null)
					{
						continue;
					}
				}
				else
				{
					//
					// Product did not changed => re-use the previous product panning data
					Check.assumeNotNull(mrpContextPrev, LiberoException.class, "mrpContextPrev not null");
					mrpContextCurrent.setM_Product(mrpContextPrev.getM_Product());
					mrpContextCurrent.setProductPlanning(mrpContextPrev.getProductPlanning());
					mrpContextCurrent.setQtyProjectOnHand(mrpContextPrev.getQtyProjectOnHand());
				}

				//
				// If No Product Planning found, go to next MRP record
				// NOTE: shall not happen
				final I_PP_Product_Planning productPlanning = mrpContextCurrent.getProductPlanning();
				if (productPlanning == null)
				{
					final LiberoException ex = new LiberoException("No product planning was set to context."
							+ "\n @PP_MRP_ID@: " + mrpDemand
							+ "\n MRP Context: " + mrpContextCurrent);
					logger.warn(ex.getLocalizedMessage() + " [SKIP]", ex);
					continue;
				}

				// MRP-150
				// Past Due Demand
				// Indicates that a demand order is past due.
				final Timestamp mrpDemandDatePromised = mrpDemand.getDatePromised();
				final Date mrpDateRun = mrpContextCurrent.getDate();
				if (mrpDemandDatePromised.compareTo(mrpDateRun) < 0)
				{
					newMRPNote(mrpContextCurrent, MRP_ERROR_150_DemandPastDue)
							.addParameter(I_PP_MRP.COLUMNNAME_DatePromised, mrpDemandDatePromised)
							.setQtyPlan(mrpDemand.getQty())
							.addMRPRecord(mrpDemand)
							.collect();
				}

				mrpContextPrev = mrpContextCurrent; // FIXME: is this the right place to set it?

				//
				// Aggregate current MRP demand and calculate plan if needed
				if (mrpDemandAgg != null)
				{
					if (mrpDemandAgg.canAdd(mrpDemand))
					{
						mrpDemandAgg.addMRPDemand(mrpDemand);
					}
					else
					{
						logger.info("Calculate plan for current MRP demands: " + mrpDemandAgg);
						calculatePlan(mrpContextCurrent, mrpDemandAgg.createMRPDemand());
						mrpDemandAgg = null;
					}
				}
				if (mrpDemandAgg == null)
				{
					mrpDemandAgg = createMRPDemandAggregation(mrpContextCurrent);
					mrpDemandAgg.addMRPDemand(mrpDemand);
				}
			} // end while mrpDemands

			//
			// If we have some MRP demands aggregated after we evaluated all MRP demands
			// => calculate plan for them
			if (mrpDemandAgg != null && mrpDemandAgg.hasDemands())
			{
				logger.info("Calculate plan for previous MRP aggregated demand: {}", mrpDemandAgg);
				calculatePlan(mrpContextPrev, mrpDemandAgg.createMRPDemand());

				//
				// Discard MRP Demand aggregation because:
				// * was already balanced by calculatePlan
				// * or it's out of Planning Horizon
				// NOTE: anyways, won't be used anymore so we are reseting it just preventivelly
				mrpDemandAgg = null;
			}

			//
			// Create Notice if there is not needed supply
			// NOTE: it will also mark further MRP supplies as not available
			if (mrpContextPrev != null)
			{
				getMRPSupplyReservator().cancelRemainingMRPSupplies(mrpContextPrev);
			}

		} // end for each Product Low Level Code

		//
		// Execute after run jobs
		executeAfterRunJobs();
	} // end runMRP

	/**
	 * Execute submited jobs to be executed after {@link #runMRP(IMaterialPlanningContext)}
	 */
	private final void executeAfterRunJobs()
	{
		final boolean mrpChangedSegments_CollectEnabledOld = this.mrpChangedSegments_CollectEnabled;
		try
		{
			// Disabling changed segments collecting because:
			// * most of the time this jobs are about processing some generated documents
			// * there is no point to fire segment changes because there are no actually segment changes
			// * firing segment changes it could introduce loops here
			this.mrpChangedSegments_CollectEnabled = false;

			// Actually execute all jobs, if any
			jobs_AfterSegmentRun.executeAllJobs();
		}
		finally
		{
			// restore
			this.mrpChangedSegments_CollectEnabled = mrpChangedSegments_CollectEnabledOld;
		}

	}

	/**
	 * Checks if given MRP Demand is available for balancing
	 *
	 * @param mrpContext
	 * @param mrpDemand
	 * @return true if given MRP demand is available for balancing
	 */
	private final boolean isMRPDemandAvailable(final IMaterialPlanningContext mrpContext, final I_PP_MRP mrpDemand)
	{
		Check.assume(mrpBL.isDemand(mrpDemand), LiberoException.class, "MRP record shall be a Demand: {}", mrpDemand);
		final String OrderType = mrpDemand.getOrderType();

		//
		// If demand is forecast and promised date less than or equal to today
		// => skip it because it's in the past
		if (X_PP_MRP.ORDERTYPE_Forecast.equals(OrderType))
		{
			final Timestamp DatePromised = mrpDemand.getDatePromised();
			if (DatePromised.compareTo(mrpContext.getDate()) <= 0)
			{
				// Mark it as not available just to make sure we will not evaluate it again.
				markNotAvailable(mrpDemand);
				return false;
			}
		}

		// NOTE: this is redundant because only available demands are retrieved anyways
		if (!mrpDemand.isAvailable())
		{
			return false;
		}

		//
		// Consider this demand available
		return true;
	}

	/**
	 * Setup MRP for given <code>product</code>.
	 *
	 * After executing this method, following variables will be set:
	 * <ul>
	 * <li>MRPContext's product planning data will be initialized
	 * <li>MRPContext's TimeFence will be set
	 * <li>MRPContext's QtyProjectOnHand will be set to QtyOnHand for MRPContext's warehouse, adjusted with SafetyStock </u>
	 *
	 * @param mrpContext
	 * @param product
	 */
	private void setProduct(final IMutableMRPContext mrpContext, final I_M_Product product)
	{
		//
		// Find data product planning demand
		// ... and set it (first thing ever, because other BLs depends on this)
		mrpContext.setM_Product(product);
		final I_PP_Product_Planning productPlanning = findProductPlanning(mrpContext);
		mrpContext.setProductPlanning(productPlanning);

		//
		// MRP-120 No Product Data Planning was found
		if (productPlanning == null)
		{
			newMRPNote(mrpContext, MRP_ERROR_120_NoProductPlanning)
					.setM_Product(product)
					.collect();
			return;
		}

		//
		// FIXME: workaround to disable the POQ because is not working ATM (08202)
		if (LiberoConstants.isMRP_POQ_Disabled()
				&& productPlanning != null
				&& X_PP_Product_Planning.ORDER_POLICY_PeriodOrderQuantity.equals(productPlanning.getOrder_Policy()))
		{
			mrpContext.setProductPlanning(null);
			newMRPNote(mrpContext, MRP_ERROR_120_NoProductPlanning)
					.setM_Product(product)
					.setComment("Ignored because the product planning is about POQ (" + productPlanning + ") and POQ is disabled.")
					.collect();
			return;
		}

		//
		// Set TimeFence
		final int timeFenceDays = productPlanning.getTimeFence().intValueExact();
		if (timeFenceDays > 0)
		{
			final Date mrpDateRun = mrpContext.getDate();
			final Timestamp TimeFence = TimeUtil.addDays(mrpDateRun, timeFenceDays);
			mrpContext.setTimeFence(TimeFence);
		}
		else
		{
			mrpContext.setTimeFence(null);
		}

		//
		// Retrieve initial QtyProjectOnHand = actual QtyOnHand
		BigDecimal qtyOnHand = calculateQtyOnHand(mrpContext);

		//
		// MRP-140
		// Beginning Quantity Less Than Zero
		// Indicates that the quantity on hand is negative.
		if (qtyOnHand.signum() < 0)
		{
			newMRPNote(mrpContext, MRP_ERROR_140_NegativeQtyOnHand)
					.addParameter(I_M_Storage.COLUMNNAME_QtyOnHand, qtyOnHand)
					.collect();
		}

		//
		// MRP-001 QtyOnHand is less then safety stock
		//
		// Example:
		// Quantity Project On hand 100
		// Safety Stock 150
		// 150 > 100 The Quantity Project On hand is now 50
		final BigDecimal qtySafetyStock = productPlanning.getSafetyStock();
		if (qtySafetyStock.signum() > 0
				&& qtySafetyStock.compareTo(qtyOnHand) > 0)
		{
			newMRPNote(mrpContext, "MRP-001")
					.addParameter(I_M_Storage.COLUMNNAME_QtyOnHand, qtyOnHand)
					.addParameter(I_PP_Product_Planning.COLUMNNAME_SafetyStock, qtySafetyStock)
					.collect();
			//
			qtyOnHand = qtyOnHand.subtract(qtySafetyStock);
		}

		logger.info("Warehouse: {}", mrpContext.getM_Warehouse().getName()); // not null
		logger.info("Product: {}", product);
		logger.info("Product Planning: {}", productPlanning);
		logger.info("QtyOnHand (-Safety stock): {}", qtyOnHand);
		logger.info("Safety Stock: {}", qtySafetyStock);
		if (logger.isInfoEnabled())
		{
			logger.info(MRPTracer.dumpMRPRecordsToString("MRP Records when setting current planning product"));
		}

		//
		// Initialize Projected Qty On Hand with "Quantity On Hand"
		// later on it will be adjusted with evaluated MRP supplies and demands
		mrpContext.setQtyProjectOnHand(qtyOnHand);
	}

	private final IMRPDemandAggregation createMRPDemandAggregation(final IMaterialPlanningContext mrpContext)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");

		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();
		Check.assumeNotNull(productPlanning, "productPlanning not null");

		final IMRPDemandAggregation mrpDemandAggegation = mrpDemandAggregationFactory.createMRPDemandAggregator(productPlanning);
		return mrpDemandAggegation;
	}

	private I_PP_Product_Planning findProductPlanning(final IMaterialPlanningContext mrpContext)
	{
		final Properties ctx = mrpContext.getCtx();
		final I_M_Product product = mrpContext.getM_Product();
		final int adOrgId = mrpContext.getAD_Org().getAD_Org_ID();
		final I_M_Warehouse warehouse = mrpContext.getM_Warehouse();
		final I_S_Resource plant = mrpContext.getPlant();

		// Find data product planning demand
		final I_PP_Product_Planning productPlanning = productPlanningDAO.find(
				ctx,
				adOrgId,
				warehouse.getM_Warehouse_ID(), // M_Warehouse_ID
				plant.getS_Resource_ID(), // S_Resource_ID
				product.getM_Product_ID(),
				mrpContext.getM_AttributeSetInstance_ID());
		if (productPlanning == null)
		{
			return null;
		}

		//
		// Create a plain copy of current data planning
		final I_PP_Product_Planning productPlanningActual = productPlanningBL.createPlainProductPlanning(productPlanning);

		//
		// Set actual IsPurchased flag
		final String isPurchasedStr = productPlanningActual.getIsPurchased();
		final boolean isPurchasedActual;
		if (isPurchasedStr == null)
		{
			// Fallback when IsPurchased flag is not set in product data planning: use it from M_Product.IsPurchased
			isPurchasedActual = product.isPurchased();
		}
		else
		{
			isPurchasedActual = X_PP_Product_Planning.ISPURCHASED_Yes.equals(isPurchasedStr);
		}

		//
		// Set actual IsManufactured flag
		final String isManufacturedStr = productPlanningActual.getIsManufactured();
		final boolean isManufacturedActual;
		if (isManufacturedStr == null)
		{
			// Fallback when IsManufactured flag is not set in product data planning: use it from M_Product.IsBOM
			isManufacturedActual = product.isBOM();
			productPlanningActual.setIsManufactured(isManufacturedActual ? X_PP_Product_Planning.ISMANUFACTURED_Yes : X_PP_Product_Planning.ISMANUFACTURED_No);
		}
		else
		{
			isManufacturedActual = X_PP_Product_Planning.ISMANUFACTURED_Yes.equals(isManufacturedStr);
		}

		//
		if (productPlanningActual.getPP_Product_BOM_ID() <= 0 && isManufacturedActual)
		{
			final int productBomId = productBOMDAO.retrieveDefaultBOMId(product);
			productPlanningActual.setPP_Product_BOM_ID(productBomId);
		}
		if (productPlanningActual.getAD_Workflow_ID() <= 0 && isManufacturedActual)
		{
			final I_AD_Workflow adWorkflow = ppWorkflowDAO.retrieveWorkflowForProduct(product);
			productPlanningActual.setAD_Workflow(adWorkflow);
		}
		if (productPlanningActual.getPlanner_ID() <= 0)
		{
			productPlanningActual.setPlanner_ID(mrpContext.getPlanner_User_ID());
		}
		if (productPlanningActual.getM_Warehouse_ID() <= 0)
		{
			productPlanningActual.setM_Warehouse(warehouse);
		}
		if (productPlanningActual.getS_Resource_ID() <= 0)
		{
			productPlanningActual.setS_Resource(plant);
		}
		if (productPlanningActual.getOrder_Policy() == null)
		{
			productPlanningActual.setOrder_Policy(X_PP_Product_Planning.ORDER_POLICY_Lot_For_Lot);
		}

		return productPlanningActual;
	}

	/**
	 * Calculate QtyOnHand
	 *
	 * @param mrpContext
	 * @return QtyOnHand
	 */
	private BigDecimal calculateQtyOnHand(final IMaterialPlanningContext mrpContext)
	{
		final I_M_Warehouse warehouse = mrpContext.getM_Warehouse();
		final I_M_Product product = mrpContext.getM_Product();

		//
		// Get the QtyOnHand from storage
		final BigDecimal qtyOnHand0 = mrpDAO.getQtyOnHand(mrpContext, warehouse, product);

		//
		// Get how much of that QtyOnHand was already reserved for other demands
		final BigDecimal qtyOnHandReserved = mrpDAO.createMRPQueryBuilder()
				.setContextProvider(mrpContext)
				.setSkipIfMRPExcluded(true)
				.setM_Warehouse_ID(warehouse.getM_Warehouse_ID())
				.setM_Product_ID(product.getM_Product_ID())
				.setTypeMRP(X_PP_MRP.TYPEMRP_Supply)
				.setOrderType(X_PP_MRP.ORDERTYPE_QuantityOnHandReservation)
				.calculateQtySUM();

		//
		// Subtract reserved QtyOnHand and return it
		final BigDecimal qtyOnHand = qtyOnHand0.subtract(qtyOnHandReserved);
		return qtyOnHand;
	}

	/**************************************************************************
	 * Calculate Plan this product
	 *
	 * @param mrpContext
	 * @param mrpDemand
	 */
	private void calculatePlan(final IMaterialPlanningContext mrpContext, final IMRPDemand mrpDemand)
	{
		Check.assumeNotNull(mrpContext, "mrpContext not null");

		logger.info("Creating Plan ...");

		//
		// Get Product and planning data
		final I_PP_Product_Planning m_product_planning = mrpContext.getProductPlanning();
		final I_M_Product product = mrpContext.getM_Product();
		//
		// Check Internal Error: product from data planning should be the same with the product given as argument
		if (m_product_planning.getM_Product_ID() != product.getM_Product_ID())
		{
			throw new IllegalStateException("MRP Internal Error:"
					+ " DataPlanningProduct(" + m_product_planning.getM_Product_ID() + ")"
					+ " <> Product(" + product + ")");
		}

		//
		// Get MRP Demand that we will need to balance
		Check.assumeNotNull(mrpDemand, "mrpDemand not null");
		final Date demandDateStartSchedule = mrpDemand.getDateStartSchedule();
		Check.assumeNotNull(demandDateStartSchedule, "DemandDateStartSchedule not null");

		//
		// Initial Projected QtyOnHand (before we do anything)
		final BigDecimal qtyProjectedOnHand0 = mrpContext.getQtyProjectOnHand();

		//
		// Allocate gross required quantity to existing supplies
		final IMRPDemandAllocationResult mrpSupplyAllocationResult = getMRPSupplyReservator().reserveMRPSupplies(mrpContext, mrpDemand);

		//
		// Notify handlers about our allocations
		// ... maybe they want to take some decisions / do some stuff
		getMRPSupplyProducerFactory().notifyQtyOnHandAllocated(mrpContext, this, mrpSupplyAllocationResult.getMRPDemandToSupplyAllocations());

		// NOTE: also Projected QtyOnHand is updated (i.e. Projected QtyOnHand which was reserved was subtracted).
		final BigDecimal qtyNetReqs = mrpSupplyAllocationResult.getQtyNetReqRemaining();

		//
		// Log current status (very useful for audit and debugging)
		final I_M_Warehouse warehouse = mrpContext.getM_Warehouse();
		logger.info("                    Product:" + product.getName() + " (ID=" + product.getM_Product_ID() + ")");
		logger.info("                  Warehouse:" + warehouse.getName() + " (ID=" + warehouse.getM_Warehouse_ID() + ")");
		logger.info(" Demand Date Start Schedule:" + demandDateStartSchedule);
		logger.info("                 MRP Demand:" + mrpDemand);
		logger.info("     Qty Scheduled Receipts:" + mrpSupplyAllocationResult.getQtyScheduledReceipts());
		logger.info("    Projected QOH (initial):" + qtyProjectedOnHand0);
		logger.info("              Projected QOH:" + mrpContext.getQtyProjectOnHand());
		logger.info("   Projected QOH (reserved):" + mrpSupplyAllocationResult.getQtyOnHandReserved());
		logger.info("               Qty Supplied:" + mrpSupplyAllocationResult.getQtySupplied());
		logger.info("                QtyNetReqs:" + qtyNetReqs);
		logger.info("Supply allocation result: {}", mrpSupplyAllocationResult);
		if (logger.isInfoEnabled())
		{
			logger.info(MRPTracer.dumpMRPRecordsToString("MRP records after supply allocations"));
		}

		//
		// Check if we have a net required quantity that we will need to supply
		// If not, everything it's fine.
		if (qtyNetReqs.signum() <= 0)
		{
			logger.info("Result: entire qty is available or scheduled to receipt. No need to create supply documents.");
			return;
		}

		//
		// Apply Order modifier
		// NOTE: we need to apply these modifiers AFTER we reserved QtyOnHand and MRP supplies
		// because these modifiers are about what to do in case we need to order it
		final BigDecimal qtyToOrder = calculateQtyToOrder(mrpContext, qtyNetReqs);
		logger.info("Qty To Order (after ordered qty modifiers):" + qtyToOrder);

		//
		// Adjust Projected QtyOnHand.
		// So far our Projected QtyOnHand it's Initial Projected QtyOnHand, minus Projected QtyOnHand which was reservered.
		// Now we are subtracting the qtyToOrder
		// ... and while creating the actual supply documents we will add how much was supplied on them.
		//
		// At this point, it could be a regular/normal case to have the ProjectQOH negative because we just subtracted the QtyToOrder from it,
		// but it will be adjusted when we will create the supplies (see bellow, in this method).
		{
			final BigDecimal qtyProjectedOnHand = mrpContext.getQtyProjectOnHand();
			final BigDecimal qtyProjectedOnHandNew = qtyProjectedOnHand.subtract(qtyToOrder);
			mrpContext.setQtyProjectOnHand(qtyProjectedOnHandNew);

		}

		//
		// MRP-100 Time Fence Conflict Action Notice
		// Indicates that there is an unsatisfied material requirement inside the planning time fence for this product.
		// You should either manually schedule and expedite orders to fill this demand or delay fulfillment
		// of the requirement that created the demand.
		final Timestamp TimeFence = mrpContext.getTimeFence();
		if (TimeFence != null && demandDateStartSchedule.compareTo(TimeFence) < 0)
		{
			newMRPNote(mrpContext, MRP_ERROR_100_TimeFenceConflict)
					.addParameter(I_PP_Product_Planning.COLUMNNAME_TimeFence, m_product_planning.getTimeFence())
					.addParameter("TimeFence_Max", TimeFence)
					.addParameter("DemandDateStartSchedule", demandDateStartSchedule)
					.addParameter("QtyToOrder", qtyToOrder)
					.collect();
		}

		// MRP-020 Create
		// Indicates that a supply order should be created to satisfy the net required quantity.
		// This message is created if the flag 'Create Plan' is No.
		if (m_product_planning.isCreatePlan() == false)
		{
			newMRPNote(mrpContext, MRP_ERROR_020_CreatePlanDisabledWhenSupplyNeeded)
					.addParameter("QtyToOrder", qtyToOrder)
					.collect();
			//
			logger.info("Result: Create Plan is disabled. Nothing to do");
			return;
		}

		//
		// Calculate how many supply documents we shal create and how much quantity on each of them
		// NOTE: this makes sensse only for Fixed Order Quantity (FOQ) ordering type.
		// For other ordering types we will just create 1 supply order with the whole quantity to order.
		int ordersCount = 1;
		BigDecimal qtyToOrderPerOrder = qtyToOrder;
		final String orderPolicy = m_product_planning.getOrder_Policy();
		if (X_PP_Product_Planning.ORDER_POLICY_FixedOrderQuantity.equals(orderPolicy))
		{
			if (m_product_planning.getOrder_Qty().signum() > 0)
			{
				ordersCount = qtyToOrder.divide(m_product_planning.getOrder_Qty(), 0, RoundingMode.UP).intValueExact();
				qtyToOrderPerOrder = m_product_planning.getOrder_Qty();
			}
		}
		logger.info("Orders#:" + ordersCount);
		logger.info("Qty / Order: " + qtyToOrderPerOrder);

		//
		// Create supply documents
		Check.assume(ordersCount > 0, "ordersCount > 0 but it was {} (internal error)", ordersCount);
		for (int orderNo = 1; orderNo <= ordersCount; orderNo++)
		{
			try
			{

				final List<IMRPRecordAndQty> remainingMRPDemandsToAllocate = mrpSupplyAllocationResult.getRemainingMRPDemandsToAllocate();
				final Quantity qtySupplied = createSupply(mrpContext,
						Quantity.of(qtyToOrderPerOrder, mrpContext.getM_Product().getC_UOM()),
						demandDateStartSchedule,
						remainingMRPDemandsToAllocate);

				//
				// Adjust Projected QtyOnHand: we are adding the qty that it was supplied.
				final BigDecimal qtyProjectedOnHand = mrpContext.getQtyProjectOnHand();
				final BigDecimal qtyProjectedOnHandNew = qtyProjectedOnHand.add(qtySupplied.getQty());
				mrpContext.setQtyProjectOnHand(qtyProjectedOnHandNew);
			}
			catch (final Exception e)
			{
				// MRP-160 - Cannot Create Document
				// Indicates that there was an error durring document creation
				newMRPNote(mrpContext, MRP_ERROR_160_CannotCreateDocument)
						.addParameter("QtyToOrderPerOrder", qtyToOrderPerOrder)
						.addParameter("DemandDateStartSchedule", demandDateStartSchedule)
						.setException(e)
						.collect();
			}
		}
	}

	/**
	 * Adjusts the Net Requirements quantity by applying ordering settings from Product Planning (Order_Min, Order_Pack, Order_Max).
	 *
	 * @param mrpContext
	 * @param qtyNetReq
	 * @return net requirements quantity adjusted
	 */
	private BigDecimal calculateQtyToOrder(
			final IMaterialPlanningContext mrpContext,
			final BigDecimal qtyNetReq)
	{
		final I_PP_Product_Planning productPlanning = mrpContext.getProductPlanning();

		//
		// Qty To Order
		// Initially it's our net required quantity and then we will adjust it.
		BigDecimal qtyToOrder = qtyNetReq;

		//
		// Check Order Min
		final BigDecimal qtyOrderMin = productPlanning.getOrder_Min();
		if (qtyOrderMin.signum() > 0)
		{
			if (qtyOrderMin.compareTo(qtyToOrder) > 0)
			{
				newMRPNote(mrpContext, "MRP-080")
						.addParameter("QtyToOrder", qtyToOrder)
						.addParameter(I_PP_Product_Planning.COLUMNNAME_Order_Min, qtyOrderMin)
						.collect();
			}
			qtyToOrder = qtyToOrder.max(qtyOrderMin);
		}

		//
		// Check Order Pack
		final BigDecimal qtyOrderPerPack = productPlanning.getOrder_Pack();
		if (qtyOrderPerPack.signum() > 0)
		{
			final BigDecimal packs = qtyToOrder.divide(qtyOrderPerPack, 0, RoundingMode.UP);
			qtyToOrder = qtyOrderPerPack.multiply(packs);
		}

		//
		// Check Order Max
		// * Order_Max is set
		// * qty that needs to be supplied is greater than Order_Max
		final BigDecimal qtyOrderMax = productPlanning.getOrder_Max();
		if (qtyOrderMax.signum() > 0 && qtyToOrder.compareTo(qtyOrderMax) > 0)
		{
			newMRPNote(mrpContext, "MRP-090")
					.addParameter("QtyToOrder", qtyToOrder)
					.addParameter(I_PP_Product_Planning.COLUMNNAME_Order_Max, qtyOrderMax)
					.collect();
		}

		return qtyToOrder;
	}

	/**
	 * Create supply document
	 *
	 * @param mrpContext
	 * @param qtyToSupply quantity that needs to be supplied
	 * @param DemandDateStartSchedule
	 * @param mrpDemandsToAllocate
	 * @return how much was actually supplied
	 * @throws LiberoException if there is any error
	 */
	private Quantity createSupply(
			final IMaterialPlanningContext mrpContext,
			final Quantity qtyToSupply,
			final Date DemandDateStartSchedule,
			final List<IMRPRecordAndQty> mrpDemandsToAllocate)
			throws LiberoException
	{
		mrpBL.executeInMRPContext(mrpContext, new IMRPContextRunnable()
		{
			@Override
			public void run(final IMutableMRPContext mrpContextLocal)
			{
				final IMRPSupplyProducer producer = getMRPSupplyProducerFactory().getSupplyProducer(mrpContextLocal);

				logger.info("Supply producer: " + producer);
				logger.info("Qty To Supply: " + qtyToSupply);
				logger.info("DemandDateStartSchedule: " + DemandDateStartSchedule);

				final IMRPCreateSupplyRequest request = new MRPCreateSupplyRequest(mrpContextLocal,
						MRPExecutor.this,
						qtyToSupply,
						DemandDateStartSchedule,
						mrpDemandsToAllocate);
				mrpContextLocal.setMRPDemands(request.getMRPDemandRecords());
				producer.createSupply(request);
			}
		});

		// TODO: we shall change our MRP supply producers and let them tell how much it was supplied.
		final Quantity qtySupplied = qtyToSupply;
		return qtySupplied;
	}

	/**
	 *
	 * @param mrpContext
	 * @param DemandDateStartSchedule
	 * @return qty reserved
	 */

	protected void markNotAvailable(final I_PP_MRP mrp)
	{
		final String trxName = isSubsequentMRPExecutor() ? ITrx.TRXNAME_ThreadInherited : ITrx.TRXNAME_None;
		mrpDAO.markNotAvailable(mrp, trxName);
	}

	@Override
	public final void addGeneratedSupplyDocument(final Object document)
	{
		logger.info("MRP Document generated: {}", document);

		mrpResult.addSupplyDocument(document);
	}

	@Override
	public final void addDeletedDocuments(String tableName, int count)
	{
		mrpResult.addDeletedDocuments(tableName, count);
	}

	protected void collectMRPNote(final IMRPNoteBuilder mrpNoteBuilder)
	{
		// Don't collect duplicate notes
		if (mrpNoteBuilder.isDuplicate())
		{
			return;
		}

		// NOTE: we can do a duplicate check also at this point,
		// but what i am concerned of is there are too many notes collected (in some our tests we got ~9000 notes),
		// and even if we use a list of ArrayKeys could be a huge one.

		final I_AD_Note note = createMRPNote(mrpNoteBuilder);
		mrpResult.addNote(note);
	}

	protected I_AD_Note createMRPNote(final IMRPNoteBuilder mrpNoteBuilder)
	{
		//
		// Get transaction name to use
		// NOTE: we save messages out of transaction
		// ... and we use a transaction just to bypass TrxConstraints
		final String trxName;
		if (isSubsequentMRPExecutor())
		{
			final IMaterialPlanningContext mrpContext = mrpNoteBuilder.getMRPContext();
			trxName = mrpContext != null ? mrpContext.getTrxName() : ITrx.TRXNAME_None;
		}
		else
		{
			trxName = ITrx.TRXNAME_None;
		}

		//
		// Actually create the MRP note and add it to our MRP result
		final IMutable<I_AD_Note> noteToReturn = new Mutable<>();
		trxManager.run(trxName, new TrxRunnable()
		{
			@Override
			public void run(String localTrxName) throws Exception
			{
				final I_AD_Note note = InterfaceWrapperHelper.create(mrpNoteBuilder.createMRPNote(), I_AD_Note.class);
				noteToReturn.setValue(note);
			}
		});

		return noteToReturn.getValue();
	}

	@Override
	public IMRPNoteBuilder newMRPNote(final IMaterialPlanningContext mrpContext, final String mrpErrorCode)
	{
		final IMRPNotesCollector mrpNotesCollector = getMRPNotesCollector();
		return mrpNotesCollector.newMRPNoteBuilder(mrpContext, mrpErrorCode);
	}

	@Override
	public void addChangedMRPSegment(final IMRPSegment mrpSegment)
	{
		logger.info("MRP Segment changed notification: {}", mrpSegment);
		if (!mrpChangedSegments_CollectEnabled)
		{
			logger.info("Skip collecting it because collecting is disabled");
			return;
		}

		mrpChangedSegments.addSegment(mrpSegment);
	}

	@Override
	public List<IMRPSegment> getAndClearChangedMRPSegments()
	{
		return mrpChangedSegments.getAndClearMRPSegments();
	}

	/**
	 * Marks the MRP record (Demand) as available, to be used in current planning.
	 *
	 * NOTE: logic of following methods are are all relying on this:
	 * <ul>
	 * <li>{@link #isMRPDemandAvailable(IMaterialPlanningContext, I_PP_MRP)}
	 * <li>{@link #retrieveMRPDemands(IMaterialPlanningContext, int)}
	 * </ul>
	 */
	@Override
	public void onMRPRecordBeforeCreate(final I_PP_MRP mrp)
	{
		final String typeMRP = mrp.getTypeMRP();
		if (X_PP_MRP.TYPEMRP_Demand.equals(typeMRP))
		{
			mrp.setIsAvailable(true);
		}
		else if (X_PP_MRP.TYPEMRP_Supply.equals(typeMRP))
		{
			mrp.setIsAvailable(false);
		}
		else
		{
			// shall not happen (never ever)
			throw new LiberoException("@NotSupported@ @TypeMRP@: " + typeMRP);
		}
	}

	@Override
	public IMRPResult getMRPResult()
	{
		return mrpResult;
	}

	public MRPSupplyReservator getMRPSupplyReservator()
	{
		final MRPSupplyReservator mrpSupplyReservator = new MRPSupplyReservator();
		mrpSupplyReservator.setMRPExecutor(this);
		return mrpSupplyReservator;
	}

	@Override
	public final IMRPNotesCollector getMRPNotesCollector()
	{
		return _mrpNotesCollector;
	}

	protected IMRPNotesCollector newMRPNotesCollector()
	{
		return new MRPExecutorNotesCollector(this);
	}

	@Override
	public IMRPExecutorJobs getAfterSegmentRunJobs()
	{
		return jobs_AfterSegmentRun;
	}

	@Override
	public IMRPExecutorJobs getAfterAllSegmentsRunJobs()
	{
		return jobs_AfterAllSegmentsRun;
	}

}
