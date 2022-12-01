package de.metas.distribution.ddorder.producer;

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.distribution.ddorder.DDOrderId;
import de.metas.distribution.ddorder.DDOrderLineId;
import de.metas.distribution.ddorder.lowlevel.model.I_DD_OrderLine;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleCreateRequest;
import de.metas.distribution.ddorder.movement.schedule.DDOrderMoveScheduleService;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.LotNumberQuarantine;
import de.metas.product.ProductId;
import de.metas.product.ResourceId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.LocatorId;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

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

/**
 * Creates {@link I_DD_Order}s to move a given set of HUs from their locations to a given destination warehouse.
 * <p>
 * NOTE: this producer is NOT actually moving them, but it's just creating draft DD Orders to move them.
 * <p>
 * Task 08639
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public class HUs2DDOrderProducer
{
	public static HUs2DDOrderProducer newProducer(
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		return new HUs2DDOrderProducer(ddOrderMoveScheduleService);
	}

	// services
	private static final Logger logger = LogManager.getLogger(HUs2DDOrderProducer.class);
	private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUMaterialTrackingBL huMaterialTrackingId = Services.get(IHUMaterialTrackingBL.class);
	private final DDOrderMoveScheduleService ddOrderMoveScheduleService;

	//
	// Parameters
	private LocatorId _locatorToId;
	private Iterator<HUToDistribute> _hus;
	private final Instant date = SystemTime.asInstant();
	private BPartnerLocationId bpartnerLocationId;

	//
	// Status
	private final AtomicBoolean _processed = new AtomicBoolean();
	@Nullable private ResourceId plantId;
	private WarehouseId warehouseInTrasitId;
	private DocTypeId docTypeDO_ID;
	private OrgId orgId;
	private final Map<ArrayKey, DDOrderLineCandidate> ddOrderLineCandidates = new LinkedHashMap<>();

	private HUs2DDOrderProducer(
			@NonNull final DDOrderMoveScheduleService ddOrderMoveScheduleService)
	{
		this.ddOrderMoveScheduleService = ddOrderMoveScheduleService;
	}

	public Optional<I_DD_Order> process()
	{
		markAsProcessed();

		// Make sure we are running out of transaction.
		// NOTE: it won't be a big harm to run in transaction too, but in most of the cases this is not the intention because this could be a long running process
		// NOTE2: we still have cases where we cannot avoid being in transaction, so we commented it out.
		// trxManager.assertThreadInheritedTrxNotExists();

		prepareProcessing();

		final I_DD_Order ddOrderOrNull = huTrxBL
				.createHUContextProcessorExecutor()
				.call(this::processInTrx);

		return Optional.ofNullable(ddOrderOrNull);
	}

	protected I_DD_Order processInTrx(final IHUContext huContext)
	{
		//
		// Iterate all HUs and create DD_OrderLine candidates
		final ILoggable loggable = getLoggable();
		final Iterator<HUToDistribute> hus = getHUs();
		while (hus.hasNext())
		{
			final HUToDistribute huToDistribute = hus.next();

			final I_M_HU hu = huToDistribute.getHu();

			try
			{
				addHU(huContext, huToDistribute);

				loggable.addLog("@M_HU_ID@ " + hu.getValue());
			}
			catch (final Exception e)
			{
				final String errmsg = "@Error@ " + hu.getValue() + ": " + e.getLocalizedMessage();
				logger.error(errmsg, e);
				loggable.addLog(errmsg);
			}
		}

		//
		// Process DD_OrderLine candidates
		return processDDOrderLineCandidates(huContext);
	}

	private I_DD_Order processDDOrderLineCandidates(@NonNull final IHUContext huContext)
	{
		if (ddOrderLineCandidates.isEmpty())
		{
			return null;
		}

		final I_DD_Order ddOrder = createDD_OrderHeader(huContext);

		for (final DDOrderLineCandidate ddOrderLineCandidate : ddOrderLineCandidates.values())
		{
			createDD_OrderLine(huContext, ddOrder, ddOrderLineCandidate);
		}

		//
		// Process the DD order if needed
		docActionBL.processEx(ddOrder, IDocument.ACTION_Complete, IDocument.STATUS_Completed);
		getLoggable().addLog("@Created@ @DD_Order_ID@ " + ddOrder.getDocumentNo());

		return ddOrder;
	}

	/**
	 * Loads common master data which will be used
	 */
	private void prepareProcessing()
	{
		//
		// DD Order organization
		final WarehouseId warehouseToId = getLocatorToId().getWarehouseId();
		orgId = warehouseBL.getWarehouseOrgId(warehouseToId);

		//
		// Plant
		plantId = warehouseBL.getPlantId(warehouseToId).orElse(null);

		//
		// InTransit warehouse
		warehouseInTrasitId = warehouseBL.getInTransitWarehouseId(orgId);

		//
		// DD_Order document type
		docTypeDO_ID = Services.get(IDocTypeDAO.class)
				.getDocTypeIdOrNull(
						DocTypeQuery.builder()
								.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
								.adClientId(Env.getAD_Client_ID())
								.adOrgId(orgId.getRepoId())
								.build());

	}

	private void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "producer not already executed");
	}

	private void markAsProcessed()
	{
		final boolean alreadyProcessed = _processed.getAndSet(true);
		Check.assume(!alreadyProcessed, "producer not already executed");
	}

	public HUs2DDOrderProducer setLocatorToId(@NonNull final LocatorId locatorToId)
	{
		assertNotProcessed();
		this._locatorToId = locatorToId;
		return this;
	}

	public HUs2DDOrderProducer setBpartnerLocationId(@NonNull final BPartnerLocationId bpartnerLocationId)
	{
		assertNotProcessed();
		this.bpartnerLocationId = bpartnerLocationId;
		return this;
	}

	private BPartnerLocationId getBpartnerLocationId()
	{
		return Check.assumeNotNull(this.bpartnerLocationId, "Partner Location not null");
	}

	private LocatorId getLocatorToId()
	{
		return Check.assumeNotNull(_locatorToId, "_locatorToId not null");
	}

	public HUs2DDOrderProducer setHUs(final Iterator<HUToDistribute> hus)
	{
		assertNotProcessed();
		_hus = hus;
		return this;
	}

	private Iterator<HUToDistribute> getHUs()
	{
		return Check.assumeNotNull(_hus, "_hus not null");
	}

	private ILoggable getLoggable()
	{
		return Loggables.getLoggableOrLogger(logger, Level.INFO);
	}

	private void addHU(final IHUContext huContext, final HUToDistribute huToDistribute)
	{
		final I_M_HU hu = huToDistribute.getHu();
		//
		// Validate the HU before creating the DD_Order
		{
			final WarehouseId huWarehouseId = IHandlingUnitsBL.extractWarehouseId(hu);
			final WarehouseId warehouseToId = getLocatorToId().getWarehouseId();
			Check.assume(huWarehouseId.getRepoId() != warehouseToId.getRepoId(), "HU's is not stored in destination warehouse");
		}

		createDDOrderLineCandidates(huContext, huToDistribute);

	}

	private void createDDOrderLineCandidates(final IHUContext huContext, final HUToDistribute huToDistribute)
	{
		//
		// Create DD Order line candidates
		final List<IHUProductStorage> huProductStorages = huContext.getHUStorageFactory()
				.getStorage(huToDistribute.getHu())
				.getProductStorages();

		for (final IHUProductStorage huProductStorage : huProductStorages)
		{
			final DDOrderLineCandidate ddOrderLineCandidateNew = new DDOrderLineCandidate(
					huMaterialTrackingId,
					huContext,
					huProductStorage,
					huToDistribute.getQuarantineLotNo());

			final ArrayKey aggregationKey = ddOrderLineCandidateNew.getAggregationKey();
			final DDOrderLineCandidate ddOrderLineCandidateExisting = ddOrderLineCandidates.get(aggregationKey);
			if (ddOrderLineCandidateExisting != null)
			{
				ddOrderLineCandidateExisting.addDDOrderLineCandidate(ddOrderLineCandidateNew);
			}
			else
			{
				ddOrderLineCandidates.put(aggregationKey, ddOrderLineCandidateNew);
			}
		}
	}

	private I_DD_Order createDD_OrderHeader(final IHUContext huContext)
	{
		final Properties ctx = huContext.getCtx();

		final I_DD_Order ddOrder = InterfaceWrapperHelper.create(ctx, I_DD_Order.class, ITrx.TRXNAME_ThreadInherited);
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setPP_Plant_ID(ResourceId.toRepoId(plantId));
		ddOrder.setC_BPartner_ID(getBpartnerLocationId().getBpartnerId().getRepoId());
		ddOrder.setC_BPartner_Location_ID(getBpartnerLocationId().getRepoId());
		// order.setSalesRep_ID(productPlanningData.getPlanner_ID());

		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(docTypeDO_ID));
		ddOrder.setM_Warehouse_ID(warehouseInTrasitId.getRepoId());
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrder.setDateOrdered(TimeUtil.asTimestamp(date));
		ddOrder.setDatePromised(TimeUtil.asTimestamp(date));
		// order.setM_Shipper_ID(networkLine.getM_Shipper_ID());
		ddOrder.setIsInDispute(false);
		ddOrder.setIsInTransit(false);

		// SO Trx = No
		// IMPORTANT: if set to Yes, the MDDOrderLine will make sure there is sufficient quantity for ASI
		ddOrder.setIsSOTrx(false);

		InterfaceWrapperHelper.save(ddOrder);
		return ddOrder;
	}

	private void createDD_OrderLine(final IHUContext huContext, final I_DD_Order ddOrder, final DDOrderLineCandidate ddOrderLineCandidate)
	{
		final ProductId productId = ddOrderLineCandidate.getProductId();

		//
		// Create DD Order Line
		final I_DD_OrderLine ddOrderline = InterfaceWrapperHelper.newInstance(I_DD_OrderLine.class, ddOrder);
		ddOrderline.setAD_Org_ID(ddOrder.getAD_Org_ID());
		ddOrderline.setDD_Order(ddOrder);
		// Dates (from header)
		ddOrderline.setDateOrdered(ddOrder.getDateOrdered());
		ddOrderline.setDatePromised(ddOrder.getDatePromised());

		//
		// Locator From/To
		final LocatorId pickFromLocatorId = ddOrderLineCandidate.getLocatorFromId();
		final LocatorId dropToLocatorId = getLocatorToId();
		ddOrderline.setM_Locator_ID(pickFromLocatorId.getRepoId());
		ddOrderline.setM_LocatorTo_ID(dropToLocatorId.getRepoId());

		//
		// Product, UOM, Qty
		final Quantity qtyInSourceUOM = ddOrderLineCandidate.getQtyInSourceUOM();
		final Quantity qtyInStockingUOM = ddOrderLineCandidate.getQtyInStockingUOM();
		ddOrderline.setM_Product_ID(productId.getRepoId());
		ddOrderline.setC_UOM_ID(qtyInSourceUOM.getUomId().getRepoId());
		ddOrderline.setQtyEntered(qtyInSourceUOM.toBigDecimal());
		ddOrderline.setQtyOrdered(qtyInStockingUOM.toBigDecimal());
		ddOrderline.setTargetQty(qtyInStockingUOM.toBigDecimal());

		//
		// ASI
		createASI(huContext.getHUAttributeStorageFactory(), ddOrderline, ddOrderLineCandidate);

		//
		// HU related
		// NOTE: we assume we are moving TUs (e.g. Paloxes)
		ddOrderline.setM_HU_PI_Item_Product(ddOrderLineCandidate.getM_HU_PI_Item_Product());
		// ddOrderline.setQtyEnteredTU(QtyEnteredTU); // QtyEnteredTUs is calculated automatically on save

		//
		// Description
		final StringBuilder description = new StringBuilder();

		final String lotNoQuarantineDescription = getDescriptionForLotNoQuarantine(ddOrderLineCandidate.getLotNumberQuarantine());

		description.append(lotNoQuarantineDescription);
		description.append(ddOrderLineCandidate.getDescription());

		ddOrderline.setDescription(description.toString());
		//
		// Other flags
		ddOrderline.setIsInvoiced(false);

		//
		// Save DD Order Line
		InterfaceWrapperHelper.save(ddOrderline);
		final DDOrderLineId ddOrderLineId = DDOrderLineId.ofRepoId(ddOrderline.getDD_OrderLine_ID());
		final DDOrderId ddOrderId = DDOrderId.ofRepoId(ddOrderline.getDD_Order_ID());

		//
		// Create the HU assignment candidate
		ddOrderLineCandidate.getPickFromHUs()
				.stream()
				.map(pickFromHU -> DDOrderMoveScheduleCreateRequest.builder()
						.ddOrderId(ddOrderId)
						.ddOrderLineId(ddOrderLineId)
						.productId(productId)
						//
						// Pick From
						.pickFromLocatorId(pickFromLocatorId)
						.pickFromHUId(pickFromHU.getHuId())
						.qtyToPick(pickFromHU.getQtyToPick())
						//
						// Drop To
						.dropToLocatorId(dropToLocatorId)
						//
						.build())
				.forEach(ddOrderMoveScheduleService::createScheduleToMove);
	}

	private static String getDescriptionForLotNoQuarantine(final LotNumberQuarantine lotNumberQuarantine)
	{
		if (lotNumberQuarantine == null)
		{
			return "";
		}

		final String lotNoQuarantineDescription = lotNumberQuarantine.getDescription();
		if (Check.isBlank(lotNoQuarantineDescription))
		{
			return "";
		}

		return lotNoQuarantineDescription + "; ";
	}

	/**
	 * Creates and sets DD_OrderLine's ASI and ASI To by copying the document relevant attributes from given HU.
	 */
	private void createASI(final IAttributeStorageFactory attributeStorageFactory, final I_DD_OrderLine ddOrderline, final DDOrderLineCandidate ddOrderLineCandidate)
	{
		final I_M_AttributeSetInstance ddOrderline_ASIFrom = attributeSetInstanceBL.getCreateASI(DDOrderLineAttributeSetInstanceAware.ofASIFrom(ddOrderline));
		final IAttributeStorage ddOrderLine_AttributesFrom = attributeStorageFactory.getAttributeStorage(ddOrderline_ASIFrom);

		final I_M_AttributeSetInstance ddOrderline_ASITo = attributeSetInstanceBL.getCreateASI(DDOrderLineAttributeSetInstanceAware.ofASITo(ddOrderline));
		final IAttributeStorage ddOrderLine_AttributesTo = attributeStorageFactory.getAttributeStorage(ddOrderline_ASITo);

		for (final Map.Entry<I_M_Attribute, Object> attribute2value : ddOrderLineCandidate.getAttributes().entrySet())
		{
			final I_M_Attribute attribute = attribute2value.getKey();
			if (!attribute.isAttrDocumentRelevant())
			{
				continue;
			}

			if (!ddOrderLine_AttributesFrom.hasAttribute(attribute))
			{
				continue;
			}

			final Object value = attribute2value.getValue();
			ddOrderLine_AttributesFrom.setValue(attribute, value);
			ddOrderLine_AttributesTo.setValue(attribute, value);
		}
	}

}
