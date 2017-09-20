package de.metas.handlingunits.shipmentschedule.api.impl;

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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.Set;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutor;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorExecutorService;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ImmutablePair;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.slf4j.Logger;

import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocActionBL;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_InOutLine;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.IShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.util.HUDeliveryQuantitiesHelper;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocDAO;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.logging.LogManager;
import de.metas.shipping.model.I_M_ShipperTransportation;

public class HUShipmentScheduleBL implements IHUShipmentScheduleBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final String SYSCONFIG_ShipmentConsolidationPeriod = "de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL.ShipmentConsolidationPeriod";
	private static final String DEFAULT_ShipmentConsolidationPeriod = null;

	@Override
	public I_M_ShipmentSchedule_QtyPicked addQtyPicked(
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
			final BigDecimal qtyPickedDiff,
			final I_C_UOM uom,
			final I_M_HU tuOrVHU)
	{
		// Services
		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		//
		// Retrieve VHU, TU and LU
		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuOrVHU), "{} shall be a TU or a VirtualHU", tuOrVHU);
		final I_M_HU vhu;
		final I_M_HU tuHU;
		if (handlingUnitsBL.isVirtual(tuOrVHU))
		{
			vhu = tuOrVHU;
			tuHU = handlingUnitsBL.getTransportUnitHU(tuOrVHU);
			Check.assumeNotNull(tuHU, "TU shall exist for virtual HU: {}", vhu);
		}
		else
		{
			vhu = null;
			tuHU = tuOrVHU;
		}
		final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);

		//
		// Create ShipmentSchedule Qty Picked record
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked schedQtyPicked = shipmentScheduleAllocBL.addQtyPicked(sched, qtyPickedDiff, uom);

		//
		// Set HU specific stuff
		final I_M_ShipmentSchedule_QtyPicked schedQtyPickedHU = InterfaceWrapperHelper.create(schedQtyPicked, I_M_ShipmentSchedule_QtyPicked.class);
		schedQtyPickedHU.setM_LU_HU(luHU);
		schedQtyPickedHU.setM_TU_HU(tuHU);
		schedQtyPickedHU.setVHU(vhu);
		InterfaceWrapperHelper.save(schedQtyPickedHU);

		// Change HU status to Picked
		// huContext might be needed if the M_HU_Status record has ExChangeGebindeLagerWhenEmpty='Y'
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();

		// if the TU is contained by an U, it is sufficient to update the HU_Status of the LU, because all its children will be updated afterwards.
		if (luHU != null)
		{
			handlingUnitsBL.setHUStatus(huContext, luHU, X_M_HU.HUSTATUS_Picked);
			handlingUnitsDAO.saveHU(luHU);
		}
		// Fallback: set the TU's status as picked if it doesn't have a parent.
		else
		{
			handlingUnitsBL.setHUStatus(huContext, tuHU, X_M_HU.HUSTATUS_Picked);
		}

		// update HU from sched
		setHUPartnerAndLocationFromSched(sched, tuHU);

		
		
		handlingUnitsDAO.saveHU(tuHU);

		return schedQtyPickedHU;
	}

	/**
	 * This method updates the given <code>hu</code>'s <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> from the given <code>sched</code>'s effective values.
	 *
	 * Note that these changes are propagated to the HU's children by a model validator.
	 *
	 * @param sched
	 * @param hu
	 * @see IShipmentScheduleEffectiveBL
	 * @see de.metas.handlingunits.model.validator.M_HU#updateChildren(I_M_HU)
	 */
	private void setHUPartnerAndLocationFromSched(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched, final I_M_HU hu)
	{
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final int schedEffectiveBPartner_ID = shipmentScheduleEffectiveBL.getC_BPartner_ID(sched);
		final int schedEffectiveBP_Location_ID = shipmentScheduleEffectiveBL.getC_BP_Location_ID(sched);

		hu.setC_BPartner_ID(schedEffectiveBPartner_ID);
		hu.setC_BPartner_Location_ID(schedEffectiveBP_Location_ID);
	}

	@Override
	public void updateAllocationLUForTU(final I_M_HU tuHU)
	{
		// Services
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuHU), "{} shall be a TU", tuHU);

		final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);

		//
		// Iterate all QtyPicked records and update M_LU_HU_ID
		final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(tuHU);
		for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
		{
			// Don't touch those which were already delivered
			if (shipmentScheduleAllocBL.isDelivered(ssQtyPicked))
			{
				continue;
			}

			// Update LU
			ssQtyPicked.setM_LU_HU(luHU);
			InterfaceWrapperHelper.save(ssQtyPicked);
		}
	}

	@Override
	public void unallocateTU(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
			final I_M_HU tuHU,
			final String trxName)
	{
		//
		// Retrieve QtyPicked records.
		// If there are not QtyPicked records for our TU then do nothing.
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(sched, tuHU, trxName);
		if (qtyPickedList.isEmpty())
		{
			return;
		}

		//
		// Check if total quantity assigned is ZERO
		BigDecimal qtySum = BigDecimal.ZERO;
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (!qtyPicked.isActive())
			{
				continue;
			}

			final BigDecimal qty = qtyPicked.getQtyPicked();
			qtySum = qtySum.add(qty);
		}
		if (qtySum.signum() != 0)
		{
			throw new AdempiereException("Cannot unassign " + tuHU + " from " + sched + " because currently assigned quantity is " + qtySum + " instead of ZERO.");
		}

		//
		// Inactivate all qty picked records
		for (final I_M_ShipmentSchedule_QtyPicked qtyPicked : qtyPickedList)
		{
			if (!qtyPicked.isActive())
			{
				continue;
			}

			qtyPicked.setIsActive(false);
			InterfaceWrapperHelper.save(qtyPicked);
		}

		// also reset the HUs partner and location
		tuHU.setC_BPartner_ID(0);
		tuHU.setC_BPartner_Location_ID(0);
		InterfaceWrapperHelper.save(tuHU);
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU createInOutProducerFromShipmentSchedule()
	{
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class)
				.createInOutGenerateResult(true); // storeReceipts = true

		return new InOutProducerFromShipmentScheduleWithHU(result);
	}

	@Override
	public void generateInOuts(final Properties ctx,
			final IInOutProducerFromShipmentScheduleWithHU producer,
			final Iterator<IShipmentScheduleWithHU> receiptSchedules)
	{
		final ITrxItemProcessorExecutorService executorService = Services.get(ITrxItemProcessorExecutorService.class);
		final ITrxItemProcessorContext processorCtx = executorService.createProcessorContext(ctx, ITrx.TRX_None);
		final ITrxItemProcessorExecutor<IShipmentScheduleWithHU, InOutGenerateResult> executor = executorService.createExecutor(processorCtx, producer);

		executor.execute(receiptSchedules);
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU#createShipmentHeader(I_M_ShipmentSchedule)}
	 *
	 * @param candidate
	 * @return shipment which is still open for the shipment schedule (first) and it's shipper transportation
	 */
	@Override
	public I_M_InOut getOpenShipmentScheduleOrNull(final IShipmentScheduleWithHU candidate, final Date movementDate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = InterfaceWrapperHelper.create(candidate.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);

		final IContextAware contextProvider = InterfaceWrapperHelper.getContextAware(shipmentSchedule);
		final IQueryBuilder<I_M_InOut> queryBuilder = queryBL.createQueryBuilder(I_M_InOut.class, contextProvider)
				.addOnlyActiveRecordsFilter();

		//
		// AD_Org_ID
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_AD_Org_ID, shipmentSchedule.getAD_Org_ID());
		}

		//
		// Document Type
		{
			final int docTypeId = docTypeDAO.getDocTypeId(contextProvider.getCtx(),
					X_C_DocType.DOCBASETYPE_MaterialDelivery,
					shipmentSchedule.getAD_Client_ID(),
					shipmentSchedule.getAD_Org_ID(),
					ITrx.TRXNAME_NoneNotNull);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_DocType_ID, docTypeId);

			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_MovementType, X_M_InOut.MOVEMENTTYPE_CustomerShipment);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_IsSOTrx, true);
		}

		//
		// Document action/status
		// i.e. shipment shall be Drafted
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_DocAction, X_M_InOut.DOCACTION_Complete);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_DocStatus, X_M_InOut.DOCSTATUS_Drafted);
		}

		//
		// BPartner, Location & Contact
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_ID, shipmentScheduleEffectiveValuesBL.getC_BPartner_ID(shipmentSchedule));
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_Location_ID, shipmentScheduleEffectiveValuesBL.getBPartnerLocation(shipmentSchedule)
					.getC_BPartner_Location_ID());
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_AD_User_ID, shipmentScheduleEffectiveValuesBL.getAD_User_ID(shipmentSchedule));
		}

		//
		// Document Dates:
		// * make sure our shipments are in the consolidation period (08225)
		final String shipmentConsolidationPeriod = getShipmentConsolidationPeriod();
		if (!Check.isEmpty(shipmentConsolidationPeriod, true)
				&& !"-".equals(shipmentConsolidationPeriod))
		{
			Check.assumeNotNull(movementDate, "movementDate not null");
			final DateTruncQueryFilterModifier dateTruncModifier = DateTruncQueryFilterModifier.forTruncString(shipmentConsolidationPeriod);
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_MovementDate, movementDate, dateTruncModifier);
		}

		//
		// Warehouse
		{
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_M_Warehouse_ID, shipmentScheduleEffectiveValuesBL.getWarehouseId(shipmentSchedule));
		}

		//
		// Match Shipper Transportation
		{
			//
			// Add the candidate's HUs so that they're checked to have a common shipper transportation (though only one of them should have it)
			final List<I_M_HU> hus = new ArrayList<>();
			hus.add(candidate.getVHU());
			hus.add(candidate.getM_TU_HU());
			hus.add(candidate.getM_LU_HU());
			final I_M_ShipperTransportation shipperTransportation = huShipperTransportationBL.getCommonM_ShipperTransportationOrNull(hus);
			if (shipperTransportation != null)
			{
				queryBuilder.addEqualsFilter(I_M_InOut.COLUMNNAME_M_ShipperTransportation, shipperTransportation.getM_ShipperTransportation_ID());
			}
		}

		final IQueryOrderBy orderBy = queryBL.createQueryOrderByBuilder(I_M_InOut.class)
				.addColumn(org.compiere.model.I_M_InOut.COLUMNNAME_M_InOut_ID, Direction.Descending, Nulls.Last)
				.createQueryOrderBy();

		//
		// NO order filter // C_Order reference

		//
		// Fetch matching shipments
		final List<I_M_InOut> shipments = queryBuilder
				.create()
				.setOrderBy(orderBy)
				.list(I_M_InOut.class);
		if (shipments.isEmpty())
		{
			return null;
		}

		final int shipmentCount = shipments.size();
		if (shipmentCount > 1)
		{
			final Exception ex = new AdempiereException("More than 1 open shipment found for schedule {}. Returning the first one (out of {}).",
					new Object[] { shipmentSchedule, shipmentCount });
			logger.warn(ex.getLocalizedMessage());
		}

		return shipments.iterator().next();
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		Check.assumeNotNull(shipmentSchedule, "shipmentSchedule not null");
		final I_M_ShipmentSchedule shipmentScheduleHU = InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class);

		//
		// Check shipment schedule's M_HU_PI_Item_Product_Override_ID
		if (shipmentScheduleHU.getM_HU_PI_Item_Product_Override_ID() > 0)
		{
			return shipmentScheduleHU.getM_HU_PI_Item_Product_Override();
		}

		//
		// Check shipment schedule's M_HU_PI_Item_Product_ID
		if (shipmentScheduleHU.getM_HU_PI_Item_Product_ID() > 0)
		{
			return shipmentScheduleHU.getM_HU_PI_Item_Product();
		}

		//
		// Check order line's M_HU_PI_Item_Product_ID
		if (shipmentScheduleHU.getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(shipmentScheduleHU.getC_OrderLine(), I_C_OrderLine.class);
			final I_M_HU_PI_Item_Product piItemProduct = orderLine.getM_HU_PI_Item_Product();
			if (piItemProduct != null && piItemProduct.getM_HU_PI_Item_Product_ID() > 0)
			{
				return piItemProduct;
			}
		}

		return null;
	}

	@Override
	public void updateHUDeliveryQuantities(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final IShipmentScheduleAllocDAO shipmentScheduleAllocDAO = Services.get(IShipmentScheduleAllocDAO.class);

		final List<I_M_ShipmentSchedule_QtyPicked> allocations = shipmentScheduleAllocDAO.retrieveAllQtyPickedRecords(shipmentSchedule, I_M_ShipmentSchedule_QtyPicked.class);

		IPair<BigDecimal, BigDecimal> deliveredQtyLUAndTU = calculateQtyDeliveredFromQtyPickedRecords(allocations);
		if (deliveredQtyLUAndTU == null)
		{
			deliveredQtyLUAndTU = calculateQtyDeliveredFromInOutLines(allocations);
		}
		if (deliveredQtyLUAndTU == null)
		{
			deliveredQtyLUAndTU = ImmutablePair.of(BigDecimal.ZERO, BigDecimal.ZERO);
		}
		BigDecimal qtyDelivered_LU = deliveredQtyLUAndTU.getLeft();
		BigDecimal qtyDelivered_TU = deliveredQtyLUAndTU.getRight();

		final I_M_ShipmentSchedule huShipmentSchedule = InterfaceWrapperHelper.create(shipmentSchedule, I_M_ShipmentSchedule.class);
		huShipmentSchedule.setQtyDelivered_TU(qtyDelivered_TU);
		huShipmentSchedule.setQtyDelivered_LU(qtyDelivered_LU);
		HUDeliveryQuantitiesHelper.updateQtyToDeliver(huShipmentSchedule);

		// NOTE: please don't save it, respect the method contract
		// InterfaceWrapperHelper.save(huShipmentSchedule);
	}

	/** @return QtyDeliveredLU / QtyDeliveredTU pair */
	private IPair<BigDecimal, BigDecimal> calculateQtyDeliveredFromQtyPickedRecords(final Collection<I_M_ShipmentSchedule_QtyPicked> allocations)
	{
		if (allocations.isEmpty())
		{
			return null;
		}

		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

		final Set<Integer> seenLUIds = new HashSet<>();
		final Set<Integer> seenTUIds = new HashSet<>();
		int qtyDelivered_TU = 0;

		// task 08298: if the shipment was directly created from the M_Shipment_Schedule without picking and HUs, then this variable will remain "true" after the following for-loop.
		boolean allocationsHaveNoLUTU = true;

		//
		// Iterate allocations and build up the counters
		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocations)
		{
			// skip inactive allocations
			if (!alloc.isActive())
			{
				continue;
			}

			// skip not-delivered allocations
			if (!shipmentScheduleAllocBL.isDelivered(alloc))
			{
				continue;
			}

			//
			// count delivered LUs
			final int huLUId = alloc.getM_LU_HU_ID();
			if (huLUId > 0)
			{
				seenLUIds.add(huLUId);
				allocationsHaveNoLUTU = false; // task 08298: at least one alloc has a LU-ID set so we assume that the LUTU-information is valid
			}

			//
			// count delivered TUs
			final int huTUId = alloc.getM_TU_HU_ID();
			if (huTUId > 0 && seenTUIds.add(huTUId))
			{
				// gh #1244: the following code can be a performance penalty and the assumption below might not be 100% correct.
				// TODO in gh#1271: we might consider introducing I_M_ShipmentSchedule_QtyPicked.QtyDelivered_TU/QtyDelivered_LU fields
				final I_M_HU huTU = alloc.getM_TU_HU();
				if (huTU != null)
				{
					// gh #1244: also cover the case of aggregate HUs.
					final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
					final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
					if (handlingUnitsBL.isAggregateHU(huTU))
					{
						// we assume the whole aggregated TU was assigned to this shipment schedule, so that's why we consider the whole number of TUs (to be done in gh#1271)
						qtyDelivered_TU += handlingUnitsDAO.retrieveParentItem(huTU).getQty().intValueExact();
					}
					else
					{
						qtyDelivered_TU += 1;
					}
					allocationsHaveNoLUTU = false; // task 08298: at least one alloc has a TU-ID set so we assume that the LUTU-information is valid
				}
			}
		}

		if (allocationsHaveNoLUTU)
		{
			return null;
		}

		final BigDecimal qtyDelivered_LU = BigDecimal.valueOf(seenLUIds.size());
		return ImmutablePair.of(qtyDelivered_LU, BigDecimal.valueOf(qtyDelivered_TU));
	}

	/** @return QtyDeliveredLU / QtyDeliveredTU pair */
	private IPair<BigDecimal, BigDecimal> calculateQtyDeliveredFromInOutLines(final Collection<I_M_ShipmentSchedule_QtyPicked> allocations)
	{
		// set of the linked inouts that have status Complete. It will be needed to determine the number of LUs
		final Set<Integer> seenIOIds = new HashSet<>();

		// task 08298: fallback and use the actual lines' TU-Qty
		BigDecimal iolTuQtySum = BigDecimal.ZERO;

		// not sure if each line is always allocated just once..using this set to make sure we don't count a line twice
		final Set<Integer> seenIolIds = new HashSet<>();

		for (final I_M_ShipmentSchedule_QtyPicked alloc : allocations)
		{
			if (alloc.isActive() // is active, ...
					&& alloc.getM_InOutLine_ID() > 0 // ... references an iol ...
					&& seenIolIds.add(alloc.getM_InOutLine_ID()) // ... and wasn't counted yet
			)
			{
				final I_M_InOutLine iol = InterfaceWrapperHelper.create(alloc.getM_InOutLine(), I_M_InOutLine.class);

				// task 08959
				// Only sum up the qtys from inout lines that belong to completed inouts
				final org.compiere.model.I_M_InOut io = iol.getM_InOut();

				if (Services.get(IDocActionBL.class).isDocumentCompleted(io))
				{
					seenIOIds.add(io.getM_InOut_ID());
					iolTuQtySum = iolTuQtySum.add(iol.getQtyEnteredTU());
				}
			}
		}

		final BigDecimal qtyDelivered_TU = iolTuQtySum;

		// task 08959
		// Assume there is one LU / Complete InOut
		// TODO: When the logic of updating shipment schedules is modified, an elegant solution must be provided that is also consistent with the LU values in the inoutlines
		final BigDecimal qtyDelivered_LU = BigDecimal.valueOf(seenIOIds.size());
		// task 08959: I will leave this here for traceability :
		// final BigDecimal currentQtyToDeliverLU = huShipmentSchedule.getQtyToDeliver_LU();

		// NOTE: because we don't have handlers for the shipment schedules update other than the swat one, we cannot properly update the HU delivered qtys on shipment complete,
		// which would be similar with the CU qty
		// To make sure we are not updating the QtyDeliveredLU too early, make sure to set it only when the QtyDeliveredTU is also set
		// Because there is no LU qty in the M_ShipmentSchedule_QtyPicked andd neither in the M_InOutLine, we have to make sure this value is set even if there are no allocations
		// TODO: Fix this with an elegant solution after we have a HU handler for updating Shipment schedules or after we merge shipment schedules with receipt schedules

		// if (huShipmentSchedule.getQtyDelivered_TU().signum() > 0)
		// {
		// qtyDelivered_LU = qtyDelivered_LU.add(currentQtyToDeliverLU);
		// }

		return ImmutablePair.of(qtyDelivered_LU, qtyDelivered_TU);
	}

	@Override
	public I_M_ShipmentSchedule getShipmentScheduleOrNull(final I_M_HU hu)
	{
		Check.assumeNotNull(hu, "hu not null");

		// Services
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

		//
		// Retrieve QtyPicked records for given HU
		final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(hu);
		if (ssQtyPickedList.isEmpty())
		{
			return null;
		}

		//
		// Iterate QtyPicked records and try to find Shipment Schedule (it shall be only one)
		int shipmentScheduleId = -1;
		I_M_ShipmentSchedule shipmentSchedule = null;
		for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
		{
			// skip inactive lines
			if (!ssQtyPicked.isActive())
			{
				continue;
			}

			// skip already delivered lines
			if (shipmentScheduleAllocBL.isDelivered(ssQtyPicked))
			{
				continue;
			}

			//
			// Case: first QtyPicked line
			if (shipmentScheduleId <= 0)
			{
				shipmentSchedule = InterfaceWrapperHelper.create(ssQtyPicked.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);
				shipmentScheduleId = shipmentSchedule.getM_ShipmentSchedule_ID();
			}
			//
			// Case: not first QtyPicked line, but the line is pointing to same Shipment Schedule => do nothing, skip, it's ok
			else if (shipmentScheduleId == ssQtyPicked.getM_ShipmentSchedule_ID())
			{
				// do nothing
			}
			//
			// Case: given HU is assigned to more than on Shipment Schedule line
			else
			{
				// TODO: i think this is a common case when in Kommissionierung Terminal more than one shipment schedule lines are aggregated
				// I think we shall just pick the first one.... not sure, because of the Qtys
				// see de.metas.customer.picking.service.impl.PackingService.addProductQtyToHU(Properties, I_M_HU, Map<I_M_ShipmentSchedule, BigDecimal>)
				final AdempiereException ex = new AdempiereException("More than one " + de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked.Table_Name + " record was found for: " + hu
						+ ". Return null.");
				logger.warn(ex.getLocalizedMessage(), ex);

				return null;
			}
		}

		return shipmentSchedule;
	}

	@Override
	public I_M_HU_LUTU_Configuration getM_HU_LUTU_Configuration(final I_M_ShipmentSchedule schedule)
	{
		Check.assumeNotNull(schedule, "schedule not null");

		// Services
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

		final I_C_UOM cuUOM = shipmentScheduleBL.getC_UOM(schedule);
		final I_M_Product cuProduct = schedule.getM_Product();

		final I_C_BPartner bpartner = shipmentScheduleEffectiveValuesBL.getBPartner(schedule);
		final int bpartnerLocationId = shipmentScheduleEffectiveValuesBL.getC_BP_Location_ID(schedule);
		final I_M_Locator locator = shipmentScheduleEffectiveValuesBL.getDefaultLocator(schedule);

		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product(schedule);
		if (tuPIItemProduct == null || tuPIItemProduct.getM_HU_PI_Item_Product_ID() <= 0)
		{
			throw new HUException("@NotFound@ @M_HU_PI_Item_Product_ID@ (" + schedule + ")");
		}

		// NOTE: we are not checking if tuPIItemProduct is for an PI of Type Transport Unit (TU)
		// because it can also be a Virtual PI and also because it's enough for us to find out an LU for it

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProduct,
				cuUOM,
				bpartner,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);
		lutuConfiguration.setC_BPartner(bpartner);
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId);
		lutuConfiguration.setM_Locator(locator);
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);

		return lutuConfiguration;
	}

	@Override
	public IAggregationKeyBuilder<IShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder()
	{
		return new HUShipmentScheduleHeaderAggregationKeyBuilder();
	}

	public final String getShipmentConsolidationPeriod()
	{
		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		final String shipmentConsolidationPeriod = sysConfigBL.getValue(SYSCONFIG_ShipmentConsolidationPeriod, DEFAULT_ShipmentConsolidationPeriod);
		return shipmentConsolidationPeriod;
	}

	@Override
	public boolean isHUAllocation(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord)
	{
		Check.assumeNotNull(qtyPickedRecord, "qtyPickedRecord not null");
		if (qtyPickedRecord.getVHU_ID() > 0)
		{
			return true;
		}

		return false;
	}

	@Override
	public void updateEffectiveValues(final I_M_ShipmentSchedule shipmentSchedule)
	{
		// initialize values with the calculated ones

		BigDecimal qtyTU_Effective = shipmentSchedule.getQtyTU_Calculated();
		I_M_HU_PI_Item_Product piItemProduct_Effective = shipmentSchedule.getM_HU_PI_Item_Product_Calculated();

		if (!InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override))
		{
			qtyTU_Effective = shipmentSchedule.getQtyTU_Override();
		}

		if (!InterfaceWrapperHelper.isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID))
		{
			piItemProduct_Effective = shipmentSchedule.getM_HU_PI_Item_Product_Override();

			// safe because only applied when M_HU_PI_Item_Product_Effective is not null
			if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(piItemProduct_Effective.getM_HU_PI_Item().getM_HU_PI_Version().getHU_UnitType()))
			{
				qtyTU_Effective = BigDecimal.ONE;
				shipmentSchedule.setQtyTU_Override(BigDecimal.ONE);
			}
		}

		shipmentSchedule.setQtyOrdered_TU(qtyTU_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product(piItemProduct_Effective);

		shipmentSchedule.setQtyToDeliver_TU(qtyTU_Effective.subtract(shipmentSchedule.getQtyDelivered_TU()));

		// set pack description

		final String description;

		if (piItemProduct_Effective == null)
		{
			description = "";
		}
		else
		{
			description = piItemProduct_Effective.getDescription();
		}

		final StringBuilder packDescription = new StringBuilder();
		packDescription.append(Check.isEmpty(description, true) ? "" : description);
		shipmentSchedule.setPackDescription(packDescription.toString());

	}

	@Override
	public void createEffectiveValues(final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_OrderLine orderLine = InterfaceWrapperHelper.create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		final I_M_HU_PI_Item_Product hupip = orderLine.getM_HU_PI_Item_Product();
		final BigDecimal qtyTU = orderLine.getQtyEnteredTU();

		final BigDecimal qtyTU_Effective = qtyTU;
		final I_M_HU_PI_Item_Product piItemProduct_Effective = hupip;

		// M_HU_PI_Item_product
		shipmentSchedule.setM_HU_PI_Item_Product_Calculated(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product_Override(piItemProduct_Effective);

		// TU
		shipmentSchedule.setQtyTU_Calculated(qtyTU_Effective);
		shipmentSchedule.setQtyOrdered_TU(qtyTU_Effective);
		shipmentSchedule.setQtyTU_Override(qtyTU_Effective);
	}
}
