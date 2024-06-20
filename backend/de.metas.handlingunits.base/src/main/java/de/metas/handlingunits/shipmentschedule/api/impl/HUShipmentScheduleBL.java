package de.metas.handlingunits.shipmentschedule.api.impl;

import de.metas.adempiere.gui.search.IHUPackingAwareBL;
import de.metas.adempiere.gui.search.impl.ShipmentScheduleHUPackingAware;
import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUCapacityBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUShipperTransportationBL;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.LUTUCUPair;
import de.metas.handlingunits.allocation.ILUTUConfigurationFactory;
import de.metas.handlingunits.allocation.impl.TULoader;
import de.metas.handlingunits.exceptions.HUException;
import de.metas.handlingunits.model.I_C_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_LUTU_Configuration;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU;
import de.metas.i18n.AdMessageKey;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentSchedulePA;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.logging.LogManager;
import de.metas.order.IOrderDAO;
import de.metas.order.OrderAndLineId;
import de.metas.product.ProductId;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.adempiere.util.lang.IContextAware;
import org.adempiere.warehouse.LocatorId;
import org.compiere.model.I_C_UOM;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import static de.metas.common.util.CoalesceUtil.firstGreaterThanZero;
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

public class HUShipmentScheduleBL implements IHUShipmentScheduleBL
{
	private final Logger logger = LogManager.getLogger(getClass());
	private final IShipmentSchedulePA shipmentScheduleDAO = Services.get(IShipmentSchedulePA.class);
	private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	private final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);
	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	private final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);
	private final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
	private final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);
	private final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
	private final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);
	private final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);
	private final IInOutCandidateBL inOutCandidateBL = Services.get(IInOutCandidateBL.class);
	private final IOrderDAO orderDAO = Services.get(IOrderDAO.class);
	private final IHUPIItemProductDAO huPIItemProductDAO = Services.get(IHUPIItemProductDAO.class);
	private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
	private final IHUPackingAwareBL huPackingAwareBL = Services.get(IHUPackingAwareBL.class);
	private final IHUCapacityBL huCapacityBL = Services.get(IHUCapacityBL.class);

	private static final String SYSCONFIG_ShipmentConsolidationPeriod = "de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL.ShipmentConsolidationPeriod";
	private static final String DEFAULT_ShipmentConsolidationPeriod = null;

	public static final AdMessageKey MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P = AdMessageKey.of("WEBUI_Picking_Already_Shipped");

	@Override
	public I_M_ShipmentSchedule getById(@NonNull final ShipmentScheduleId id)
	{
		return shipmentScheduleDAO.getById(id, I_M_ShipmentSchedule.class);
	}

	@Override
	public Map<ShipmentScheduleId, I_M_ShipmentSchedule> getByIds(@NonNull final Set<ShipmentScheduleId> ids)
	{
		return shipmentScheduleDAO.getByIds(ids, I_M_ShipmentSchedule.class);
	}

	@Override
	public BPartnerLocationId getBPartnerLocationId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule);
	}

	@Override
	public LocatorId getDefaultLocatorId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		return shipmentScheduleEffectiveBL.getDefaultLocatorId(shipmentSchedule);
	}

	@Override
	public ShipmentScheduleWithHU addQtyPickedAndUpdateHU(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty,
			@NonNull final I_M_HU tuOrVHU,
			@NonNull final IHUContext huContext,
			final boolean anonymousHuPickedOnTheFly)
	{
		// Retrieve VHU, TU and LU
		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuOrVHU), "{} shall be a TU or a VirtualHU", tuOrVHU);
		final LUTUCUPair husPair = handlingUnitsBL.getTopLevelParentAsLUTUCUPair(tuOrVHU);

		// Create ShipmentSchedule Qty Picked record
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked //
				schedQtyPicked = shipmentScheduleAllocBL.createNewQtyPickedRecord(sched, stockQtyAndCatchQty);

		// mark this as an 'anonymousOnTheFly` pick
		schedQtyPicked.setIsAnonymousHuPickedOnTheFly(anonymousHuPickedOnTheFly);

		// Set HU specific stuff
		final I_M_ShipmentSchedule_QtyPicked schedQtyPickedHU = create(schedQtyPicked, I_M_ShipmentSchedule_QtyPicked.class);
		setHUs(schedQtyPickedHU, husPair);

		ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(schedQtyPickedHU, huContext)
				.updateQtyTUAndQtyLU();
		saveRecord(schedQtyPickedHU);

		//
		// Update LU/TU/VHU
		final I_M_HU topLevelHU = husPair.getTopLevelHU();
		setHUStatusToPicked(topLevelHU);
		setHUPartnerAndLocationFromSched(topLevelHU, sched);
		handlingUnitsDAO.saveHU(topLevelHU);
		huContext.flush();

		return ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(schedQtyPickedHU, huContext);
	}

	private static void setHUs(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord, final LUTUCUPair husPair)
	{
		qtyPickedRecord.setM_LU_HU(husPair.getM_LU_HU());
		qtyPickedRecord.setM_TU_HU(husPair.getM_TU_HU());
		qtyPickedRecord.setVHU(husPair.getVHU());
	}

	private void setHUStatusToPicked(@NonNull final I_M_HU hu)
	{
		// huContext might be needed if the M_HU_Status record has ExChangeGebindeLagerWhenEmpty='Y'
		final IMutableHUContext huContext = huContextFactory.createMutableHUContext();

		huStatusBL.setHUStatus(huContext, hu, X_M_HU.HUSTATUS_Picked);
		// handlingUnitsDAO.saveHU(hu); // don't save it here
	}

	/**
	 * This method updates the given <code>hu</code>'s <code>C_BPartner_ID</code> and <code>C_BPartner_Location_ID</code> from the given <code>sched</code>'s effective values.
	 * <p>
	 * NOTE:
	 * * this method is NOT saving the <code>hu</code>
	 * * these changes will be propagated to the HU's children by a model interceptor.
	 *
	 * @see IShipmentScheduleEffectiveBL
	 * @see de.metas.handlingunits.model.validator.M_HU#updateChildren(I_M_HU)
	 */
	private void setHUPartnerAndLocationFromSched(
			final I_M_HU hu,
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched)
	{
		final BPartnerId schedEffectiveBPartnerId = shipmentScheduleEffectiveBL.getBPartnerId(sched);
		final BPartnerLocationId schedEffectiveBPLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(sched);

		hu.setC_BPartner_ID(schedEffectiveBPartnerId.getRepoId());
		hu.setC_BPartner_Location_ID(BPartnerLocationId.toRepoId(schedEffectiveBPLocationId));
	}

	@Override
	public void updateAllocationLUForTU(final I_M_HU tuHU)
	{
		Check.assume(handlingUnitsBL.isTransportUnitOrVirtual(tuHU), "{} shall be a TU", tuHU);

		final I_M_HU luHU = handlingUnitsBL.getLoadingUnitHU(tuHU);

		final IHUContext huContext = huContextFactory.createMutableHUContext(getContextAware(tuHU));

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
			ShipmentScheduleWithHU
					.ofShipmentScheduleQtyPicked(ssQtyPicked, huContext)
					.updateQtyTUAndQtyLU();

			save(ssQtyPicked);
		}
	}

	@Override
	public void updateAllocationLUAndTUForCU(final I_M_HU cuHU)
	{
		Check.assume(handlingUnitsBL.isPureVirtual(cuHU), "{} shall be a CU", cuHU);

		final I_M_HU tuHU = handlingUnitsBL.getTransportUnitHU(cuHU);
		final I_M_HU luHU = tuHU != null ? handlingUnitsBL.getLoadingUnitHU(tuHU) : null;

		final IHUContext huContext = huContextFactory.createMutableHUContext(getContextAware(cuHU));

		//
		// Iterate all QtyPicked records and update M_LU_HU_ID
		final List<I_M_ShipmentSchedule_QtyPicked> ssQtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForHU(cuHU);
		for (final I_M_ShipmentSchedule_QtyPicked ssQtyPicked : ssQtyPickedList)
		{
			// Don't touch those which were already delivered
			if (shipmentScheduleAllocBL.isDelivered(ssQtyPicked))
			{
				continue;
			}

			// Update LU
			ssQtyPicked.setM_TU_HU(tuHU);
			ssQtyPicked.setM_LU_HU(luHU);
			ShipmentScheduleWithHU.ofShipmentScheduleQtyPicked(ssQtyPicked, huContext).updateQtyTUAndQtyLU();

			save(ssQtyPicked);
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
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedList = huShipmentScheduleDAO.retrieveSchedsQtyPickedForTU(sched.getM_ShipmentSchedule_ID(), tuHU.getM_HU_ID(), trxName);
		if (qtyPickedList.isEmpty())
		{
			return;
		}

		//
		// Check if total quantity assigned is ZERO
		BigDecimal qtySum = ZERO;
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
			save(qtyPicked);
		}

		// also reset the HUs partner and location
		tuHU.setC_BPartner_ID(0);
		tuHU.setC_BPartner_Location_ID(0);
		save(tuHU);
	}

	@Override
	public IInOutProducerFromShipmentScheduleWithHU createInOutProducerFromShipmentSchedule()
	{
		final InOutGenerateResult result = inOutCandidateBL.createEmptyInOutGenerateResult(true); // storeReceipts = true

		return new InOutProducerFromShipmentScheduleWithHU(result);
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link InOutProducerFromShipmentScheduleWithHU#createShipmentHeader(I_M_ShipmentSchedule)}
	 */
	@Nullable
	@SuppressWarnings("JavadocReference")
	@Override
	public I_M_InOut getOpenShipmentOrNull(final @NonNull ShipmentScheduleWithHU candidate, final @NonNull LocalDate movementDate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = create(candidate.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

		final IContextAware contextProvider = getContextAware(shipmentSchedule);
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
			final DocTypeQuery query = DocTypeQuery.builder()
					.docBaseType(X_C_DocType.DOCBASETYPE_MaterialDelivery)
					.adClientId(shipmentSchedule.getAD_Client_ID())
					.adOrgId(shipmentSchedule.getAD_Org_ID())
					.build();
			final DocTypeId docTypeId = docTypeDAO.getDocTypeId(query);
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
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_ID, shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule));
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_C_BPartner_Location_ID, shipmentScheduleEffectiveBL.getBPartnerLocationId(shipmentSchedule));
			final BPartnerContactId adUserID = shipmentScheduleEffectiveBL.getBPartnerContactId(shipmentSchedule);
			if (adUserID != null)
			{
				queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_AD_User_ID, BPartnerContactId.toRepoId(adUserID));
			}
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
			queryBuilder.addEqualsFilter(org.compiere.model.I_M_InOut.COLUMNNAME_M_Warehouse_ID, shipmentScheduleEffectiveBL.getWarehouseId(shipmentSchedule));
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
			logger.warn("More than 1 open shipment found for schedule {}. Returning the first one (out of {}).", shipmentSchedule, shipmentCount);
		}

		return shipments.iterator().next();
	}

	@Override
	@Nullable
	public HUPIItemProductId getEffectivePackingMaterialId(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule huShipmentSchedule = create(shipmentSchedule, I_M_ShipmentSchedule.class);

		final HUPIItemProductId pip = HUPIItemProductId.ofRepoIdOrNull(
				firstGreaterThanZero(
						huShipmentSchedule.getM_HU_PI_Item_Product_Override_ID(),
						huShipmentSchedule.getM_HU_PI_Item_Product_ID()));
		if (pip != null)
		{
			return pip;
		}

		// if is not set in shipment schedule, return the one form order line or null
		return extractOrderLinePackingMaterialIdOrNull(shipmentSchedule);
	}

	@Nullable
	private HUPIItemProductId extractOrderLinePackingMaterialIdOrNull(@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final OrderAndLineId orderLineId = OrderAndLineId.ofRepoIdsOrNull(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
		if (orderLineId == null)
		{
			return null;
		}

		final I_C_OrderLine orderLine = orderDAO.getOrderLineById(orderLineId, I_C_OrderLine.class);
		return HUPIItemProductId.ofRepoIdOrNull(orderLine.getM_HU_PI_Item_Product_ID());
	}

	@Override
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_IgnoringPickedHUs(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final HUPIItemProductId packingMaterialId = getEffectivePackingMaterialId(shipmentSchedule);
		if (packingMaterialId == null)
		{
			return null;
		}

		return huPIItemProductDAO.getRecordById(packingMaterialId);
	}

	@Override
	public I_M_ShipmentSchedule getShipmentScheduleOrNull(@NonNull final I_M_HU hu)
	{
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
				shipmentSchedule = create(ssQtyPicked.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);
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
	public Optional<TULoader> createTULoader(final I_M_ShipmentSchedule schedule)
	{
		final HUPIItemProductId tuPIItemProductId = getEffectivePackingMaterialId(schedule);
		if (tuPIItemProductId == null || tuPIItemProductId.isVirtualHU())
		{
			return Optional.empty();
		}

		final I_M_HU_PI_Item_Product tuPIItemProduct = huPIItemProductDAO.getRecordById(tuPIItemProductId);
		final ProductId cuProductId = ProductId.ofRepoId(schedule.getM_Product_ID());
		final I_C_UOM cuUOM = shipmentScheduleBL.getUomOfProduct(schedule);

		final TULoader tuLoader = TULoader.builder()
				.huContext(handlingUnitsBL.createMutableHUContext())
				.tuPI(tuPIItemProduct.getM_HU_PI_Item().getM_HU_PI_Version().getM_HU_PI())
				.capacity(huCapacityBL.getCapacity(tuPIItemProduct, cuProductId, cuUOM))
				.build();

		return Optional.of(tuLoader);
	}

	@Override
	public I_M_HU_LUTU_Configuration deriveM_HU_LUTU_Configuration(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product_IgnoringPickedHUs(schedule);
		if (tuPIItemProduct == null || tuPIItemProduct.getM_HU_PI_Item_Product_ID() <= 0)
		{
			throw new HUException("@NotFound@ @M_HU_PI_Item_Product_ID@ (" + schedule + ")");
		}

		final UomId cuUOMId = shipmentScheduleBL.getUomIdOfProduct(schedule);
		final ProductId cuProductId = ProductId.ofRepoId(schedule.getM_Product_ID());

		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(schedule);
		final BPartnerLocationId bpartnerLocationId = shipmentScheduleEffectiveBL.getBPartnerLocationId(schedule);
		final LocatorId locatorId = shipmentScheduleEffectiveBL.getDefaultLocatorId(schedule);

		// NOTE: we are not checking if tuPIItemProduct is for an PI of Type Transport Unit (TU)
		// because it can also be a Virtual PI and also because it's enough for us to find out an LU for it

		final I_M_HU_LUTU_Configuration lutuConfiguration = lutuConfigurationFactory.createLUTUConfiguration(
				tuPIItemProduct,
				cuProductId,
				cuUOMId,
				bpartnerId,
				false); // noLUForVirtualTU == false => allow placing the CU (e.g. a packing material product) directly on the LU);
		lutuConfiguration.setC_BPartner_ID(BPartnerId.toRepoId(bpartnerId));
		lutuConfiguration.setC_BPartner_Location_ID(bpartnerLocationId.getRepoId());
		lutuConfiguration.setM_Locator_ID(locatorId.getRepoId());
		lutuConfiguration.setHUStatus(X_M_HU.HUSTATUS_Planning);

		return lutuConfiguration;
	}

	@Override
	public IAggregationKeyBuilder<ShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder()
	{
		return new HUShipmentScheduleHeaderAggregationKeyBuilder();
	}

	public final String getShipmentConsolidationPeriod()
	{
		return sysConfigBL.getValue(SYSCONFIG_ShipmentConsolidationPeriod, DEFAULT_ShipmentConsolidationPeriod);
	}

	@Override
	public boolean isHUAllocation(final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord)
	{
		Check.assumeNotNull(qtyPickedRecord, "qtyPickedRecord not null");
		return qtyPickedRecord.getVHU_ID() > 0;
	}

	@Override
	public void updateEffectiveValues(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule shipmentScheduleToUse = //
				create(shipmentSchedule, I_M_ShipmentSchedule.class);

		// initialize values with the calculated ones
		BigDecimal qtyTU_Effective = shipmentScheduleToUse.getQtyTU_Calculated();
		I_M_HU_PI_Item_Product piItemProduct_Effective = shipmentScheduleToUse.getM_HU_PI_Item_Product_Calculated();

		if (!isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_QtyTU_Override))
		{
			qtyTU_Effective = shipmentScheduleToUse.getQtyTU_Override();
		}

		if (!isNull(shipmentSchedule, I_M_ShipmentSchedule.COLUMNNAME_M_HU_PI_Item_Product_Override_ID))
		{
			piItemProduct_Effective = shipmentScheduleToUse.getM_HU_PI_Item_Product_Override();

			// safe because only applied when M_HU_PI_Item_Product_Effective is not null
			if (X_M_HU_PI_Version.HU_UNITTYPE_VirtualPI.equals(piItemProduct_Effective.getM_HU_PI_Item().getM_HU_PI_Version().getHU_UnitType()))
			{
				qtyTU_Effective = ONE;
				shipmentScheduleToUse.setQtyTU_Override(ONE);
			}
		}

		shipmentScheduleToUse.setQtyOrdered_TU(qtyTU_Effective);
		shipmentScheduleToUse.setM_HU_PI_Item_Product(piItemProduct_Effective);

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

		shipmentScheduleToUse.setPackDescription((Check.isEmpty(description, true) ? "" : description));
	}

	@Override
	public void updateHURelatedValuesFromOrderLine(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule shipmentScheduleToUse = create(shipmentSchedule, I_M_ShipmentSchedule.class);

		final HUPIItemProductId orderLinePackingMaterialId = extractOrderLinePackingMaterialIdOrNull(shipmentSchedule);
		if (shipmentSchedule.getC_OrderLine_ID() <= 0 || !HUPIItemProductId.isRegular(orderLinePackingMaterialId))
		{
			logger.debug("C_OrderLine_ID={}; orderLinePackingMaterialId={} is regular={}; -> unset M_HU_PI_Item_Product_ID and PackDescription",
					shipmentSchedule.getC_OrderLine_ID(), HUPIItemProductId.toRepoId(orderLinePackingMaterialId), HUPIItemProductId.isRegular(orderLinePackingMaterialId));
			shipmentScheduleToUse.setM_HU_PI_Item_Product_ID(-1);
			shipmentScheduleToUse.setPackDescription(null);
			return;

		}
		final I_C_OrderLine orderLine = create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		updatePackingInstructionsFromOrderLine(shipmentScheduleToUse, orderLine);
		updateHUQuantitiesFromOrderLine(shipmentScheduleToUse, orderLine);
		updatePackingRelatedQtys(shipmentScheduleToUse);
	}

	private void updatePackingInstructionsFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		final I_M_HU_PI_Item_Product piItemProduct_Effective = orderLine.getM_HU_PI_Item_Product();

		shipmentSchedule.setM_HU_PI_Item_Product_Calculated(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product(piItemProduct_Effective);

		if (shipmentSchedule.getM_HU_PI_Item_Product_Override_ID() <= 0)
		{
			shipmentSchedule.setPackDescription(orderLine.getPackDescription());
		}
	}

	private void updateHUQuantitiesFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final I_C_OrderLine orderLine)
	{
		final BigDecimal qtyTU_Effective = orderLine.getQtyEnteredTU();

		shipmentSchedule.setQtyTU_Calculated(qtyTU_Effective);
		getEffectivePackingMaterialId(shipmentSchedule);

		shipmentSchedule.setQtyOrdered_TU(qtyTU_Effective);

		final I_M_HU_LUTU_Configuration lutuConfiguration = //
				deriveM_HU_LUTU_Configuration(shipmentSchedule);

		final int qtyOrderedLU = //
				lutuConfigurationFactory.calculateQtyLUForTotalQtyTUs(lutuConfiguration, qtyTU_Effective);
		shipmentSchedule.setQtyOrdered_LU(BigDecimal.valueOf(qtyOrderedLU));
	}

	private void updatePackingRelatedQtys(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final ShipmentScheduleHUPackingAware packingAware = new ShipmentScheduleHUPackingAware(shipmentSchedule);

		final BigDecimal qtyTUCalculated = shipmentSchedule.getQtyTU_Calculated();

		if (!qtyTUCalculated.equals(shipmentSchedule.getQtyOrdered_TU()))
		{
			// Calculate and set QtyEntered(CU) from M_HU_PI_Item_Product and QtyEnteredTU(aka QtyPacks)
			final int qtyTU = packingAware.getQtyTU().intValueExact();
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyTU);
		}

		final int hupipCalculatedID = shipmentSchedule.getM_HU_PI_Item_Product_Calculated_ID();
		final int currentHUPIPID = shipmentSchedule.getM_HU_PI_Item_Product_ID();

		if (hupipCalculatedID != currentHUPIPID)
		{
			final BigDecimal qtyTU = packingAware.getQtyTU();
			huPackingAwareBL.setQtyCUFromQtyTU(packingAware, qtyTU.intValueExact());

			shipmentSchedule.setQtyOrdered_Override(packingAware.getQty());
		}
	}

	@Override
	public void closeShipmentSchedule(final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		shipmentScheduleBL.closeShipmentSchedule(shipmentSchedule);
	}

	@Override
	public void closeShipmentSchedules(final Set<ShipmentScheduleId> shipmentScheduleIds)
	{
		shipmentScheduleBL.closeShipmentSchedules(shipmentScheduleIds);
	}

	@Override
	public void deleteByTopLevelHUAndShipmentScheduleId(@NonNull final HuId topLevelHUId, @NonNull final ShipmentScheduleId shipmentScheduleId)
	{
		final I_M_HU topLevelHU = handlingUnitsBL.getById(topLevelHUId);
		final List<I_M_ShipmentSchedule_QtyPicked> qtyPickedRecords = huShipmentScheduleDAO.retrieveByTopLevelHUAndShipmentScheduleId(topLevelHU, shipmentScheduleId);
		for (final I_M_ShipmentSchedule_QtyPicked qtyPickedRecord : qtyPickedRecords)
		{
			if (qtyPickedRecord.getM_InOutLine_ID() > 0)
			{
				throw new AdempiereException(
						MSG_WEBUI_PICKING_ALREADY_SHIPPED_2P,
						topLevelHUId.getRepoId(),
						qtyPickedRecord.getM_InOutLine().getM_InOut().getDocumentNo());
			}
		}

		shipmentScheduleAllocBL.deleteRecords(qtyPickedRecords);
	}
}
