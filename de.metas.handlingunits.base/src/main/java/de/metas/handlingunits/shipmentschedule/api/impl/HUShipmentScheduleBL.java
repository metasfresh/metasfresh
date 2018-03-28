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
import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.isNull;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryBuilder;
import org.adempiere.ad.dao.IQueryOrderBy;
import org.adempiere.ad.dao.IQueryOrderBy.Direction;
import org.adempiere.ad.dao.IQueryOrderBy.Nulls;
import org.adempiere.ad.dao.impl.DateTruncQueryFilterModifier;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.IContextAware;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.agg.key.IAggregationKeyBuilder;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.model.X_M_InOut;
import org.slf4j.Logger;

import de.metas.document.IDocTypeDAO;
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
import de.metas.handlingunits.model.I_M_ShipmentSchedule;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU;
import de.metas.handlingunits.model.X_M_HU_PI_Version;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleBL;
import de.metas.handlingunits.shipmentschedule.api.IHUShipmentScheduleDAO;
import de.metas.handlingunits.shipmentschedule.api.IInOutProducerFromShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.api.ShipmentScheduleWithHU;
import de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU;
import de.metas.inout.model.I_M_InOut;
import de.metas.inoutcandidate.api.IInOutCandidateBL;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.InOutGenerateResult;
import de.metas.inoutcandidate.api.impl.HUShipmentScheduleHeaderAggregationKeyBuilder;
import de.metas.logging.LogManager;
import de.metas.quantity.Quantity;
import de.metas.shipping.model.I_M_ShipperTransportation;
import lombok.NonNull;

public class HUShipmentScheduleBL implements IHUShipmentScheduleBL
{
	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final String SYSCONFIG_ShipmentConsolidationPeriod = "de.metas.handlingunits.shipmentschedule.api.impl.HUShipmentScheduleBL.ShipmentConsolidationPeriod";
	private static final String DEFAULT_ShipmentConsolidationPeriod = null;

	@Override
	public I_M_ShipmentSchedule_QtyPicked addQtyPicked(
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
			final Quantity qtyPickedDiff,
			final I_M_HU tuOrVHU)
	{
		// Services
		final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);

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

		// Create ShipmentSchedule Qty Picked record
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule_QtyPicked schedQtyPicked = //
				shipmentScheduleAllocBL.addQtyPicked(sched, qtyPickedDiff);

		// Set HU specific stuff
		final I_M_ShipmentSchedule_QtyPicked schedQtyPickedHU = //
				create(schedQtyPicked, I_M_ShipmentSchedule_QtyPicked.class);

		schedQtyPickedHU.setVHU(vhu);
		schedQtyPickedHU.setM_LU_HU(luHU);
		schedQtyPickedHU.setM_TU_HU(tuHU);

		ShipmentScheduleWithHU
				.ofShipmentScheduleQtyPicked(schedQtyPickedHU)
				.updateQtyTUAndQtyLU();

		save(schedQtyPickedHU);

		updateHuFromSchedQtyPicked(schedQtyPickedHU);

		return schedQtyPickedHU;
	}

	private void updateHuFromSchedQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked schedQtyPickedHU)
	{
		final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
		final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);

		final IHUContextFactory huContextFactory = Services.get(IHUContextFactory.class);

		final I_M_HU tuHU = schedQtyPickedHU.getM_TU_HU();
		final I_M_HU luHU = schedQtyPickedHU.getM_LU_HU();

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
		final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched = //
				schedQtyPickedHU.getM_ShipmentSchedule();
		setHUPartnerAndLocationFromSched(sched, tuHU);

		handlingUnitsDAO.saveHU(tuHU);
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
	private void setHUPartnerAndLocationFromSched(
			final de.metas.inoutcandidate.model.I_M_ShipmentSchedule sched,
			final I_M_HU hu)
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
			ShipmentScheduleWithHU
					.ofShipmentScheduleQtyPicked(ssQtyPicked)
					.updateQtyTUAndQtyLU();

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
		final IHUShipmentScheduleDAO huShipmentScheduleDAO = Services.get(IHUShipmentScheduleDAO.class);
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
		final InOutGenerateResult result = Services.get(IInOutCandidateBL.class)
				.createEmptyInOutGenerateResult(true); // storeReceipts = true

		return new InOutProducerFromShipmentScheduleWithHU(result);
	}

	/**
	 * NOTE: KEEP IN SYNC WITH {@link de.metas.handlingunits.shipmentschedule.spi.impl.InOutProducerFromShipmentScheduleWithHU#createShipmentHeader(I_M_ShipmentSchedule)}
	 *
	 */
	@Override
	public I_M_InOut getOpenShipmentOrNull(final ShipmentScheduleWithHU candidate, final Date movementDate)
	{
		final I_M_ShipmentSchedule shipmentSchedule = create(candidate.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

		//
		// Services
		final IQueryBL queryBL = Services.get(IQueryBL.class);
		final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final IHUShipperTransportationBL huShipperTransportationBL = Services.get(IHUShipperTransportationBL.class);

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
	public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product_IgnoringPickedHUs(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_M_ShipmentSchedule shipmentScheduleHU = create(shipmentSchedule, I_M_ShipmentSchedule.class);
		if (shipmentScheduleHU.getM_HU_PI_Item_Product_ID() > 0)
		{
			return shipmentScheduleHU.getM_HU_PI_Item_Product();
		}

		// Check order line's M_HU_PI_Item_Product_ID
		if (shipmentScheduleHU.getC_OrderLine_ID() > 0)
		{
			final I_C_OrderLine orderLine = create(shipmentScheduleHU.getC_OrderLine(), I_C_OrderLine.class);
			final I_M_HU_PI_Item_Product piItemProduct = orderLine.getM_HU_PI_Item_Product();
			if (piItemProduct != null && piItemProduct.getM_HU_PI_Item_Product_ID() > 0)
			{
				return piItemProduct;
			}
		}

		return null;
	}

	@Override
	public I_M_ShipmentSchedule getShipmentScheduleOrNull(@NonNull final I_M_HU hu)
	{
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
	public I_M_HU_LUTU_Configuration deriveM_HU_LUTU_Configuration(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule schedule)
	{
		final I_M_HU_PI_Item_Product tuPIItemProduct = getM_HU_PI_Item_Product_IgnoringPickedHUs(schedule);
		if (tuPIItemProduct == null || tuPIItemProduct.getM_HU_PI_Item_Product_ID() <= 0)
		{
			throw new HUException("@NotFound@ @M_HU_PI_Item_Product_ID@ (" + schedule + ")");
		}

		// Services
		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveValuesBL = Services.get(IShipmentScheduleEffectiveBL.class);
		final ILUTUConfigurationFactory lutuConfigurationFactory = Services.get(ILUTUConfigurationFactory.class);

		final I_C_UOM cuUOM = shipmentScheduleBL.getUomOfProduct(schedule);
		final I_M_Product cuProduct = schedule.getM_Product();

		final I_C_BPartner bpartner = shipmentScheduleEffectiveValuesBL.getBPartner(schedule);
		final int bpartnerLocationId = shipmentScheduleEffectiveValuesBL.getC_BP_Location_ID(schedule);
		final I_M_Locator locator = shipmentScheduleEffectiveValuesBL.getDefaultLocator(schedule);

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
	public IAggregationKeyBuilder<ShipmentScheduleWithHU> mkHUShipmentScheduleHeaderAggregationKeyBuilder()
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

		final StringBuilder packDescription = new StringBuilder();
		packDescription.append(Check.isEmpty(description, true) ? "" : description);
		shipmentScheduleToUse.setPackDescription(packDescription.toString());
	}

	@Override
	public void updateHURelatedValuesFromOrderLine(
			@NonNull final de.metas.inoutcandidate.model.I_M_ShipmentSchedule shipmentSchedule)
	{
		if (shipmentSchedule.getC_OrderLine_ID() <= 0)
		{
			return;
		}

		final I_M_ShipmentSchedule shipmentScheduleToUse = create(shipmentSchedule, I_M_ShipmentSchedule.class);

		updatePackingInstructionsFromOrderLine(shipmentScheduleToUse);
		updateTuQuantitiesFromOrderLine(shipmentScheduleToUse);
	}

	private void updatePackingInstructionsFromOrderLine(
			@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_OrderLine orderLine = create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);

		final I_M_HU_PI_Item_Product hupip = orderLine.getM_HU_PI_Item_Product();
		final I_M_HU_PI_Item_Product piItemProduct_Effective = hupip;

		shipmentSchedule.setM_HU_PI_Item_Product_Calculated(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product_Override(piItemProduct_Effective);
		shipmentSchedule.setM_HU_PI_Item_Product(piItemProduct_Effective);
	}

	private void updateTuQuantitiesFromOrderLine(@NonNull final I_M_ShipmentSchedule shipmentSchedule)
	{
		final I_C_OrderLine orderLine = create(shipmentSchedule.getC_OrderLine(), I_C_OrderLine.class);
		final BigDecimal qtyTU_Effective = orderLine.getQtyEnteredTU();

		shipmentSchedule.setQtyTU_Calculated(qtyTU_Effective);
		shipmentSchedule.setQtyOrdered_TU(qtyTU_Effective);
	}
}
