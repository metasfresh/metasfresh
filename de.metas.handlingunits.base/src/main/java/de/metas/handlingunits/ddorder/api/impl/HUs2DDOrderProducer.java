package de.metas.handlingunits.ddorder.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.Check;
import org.adempiere.util.ILoggable;
import org.adempiere.util.Loggables;
import org.adempiere.util.Services;
import org.adempiere.util.time.SystemTime;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_S_Resource;
import org.compiere.model.X_C_DocType;
import org.compiere.util.ArrayKeyBuilder;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.compiere.util.Util.ArrayKey;
import org.eevolution.model.I_DD_Order;
import org.eevolution.model.X_DD_Order;
import org.slf4j.Logger;

import com.google.common.collect.ImmutableMap;

import ch.qos.logback.classic.Level;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.product.model.I_M_Product_LotNumber_Lock;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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
 *
 * NOTE: this producer is NOT actually moving them, but it's just creating draft DD Orders to move them.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 * @task 08639
 */
public class HUs2DDOrderProducer
{
	public static final HUs2DDOrderProducer newProducer()
	{
		return new HUs2DDOrderProducer();
	}

	// services
	private static final transient Logger logger = LogManager.getLogger(HUs2DDOrderProducer.class);
	private final transient IMsgBL msgBL = Services.get(IMsgBL.class);
	private final transient IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	private final transient IWarehouseDAO warehouseDAO = Services.get(IWarehouseDAO.class);
	private final transient IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUMaterialTrackingBL huMaterialTrackingId = Services.get(IHUMaterialTrackingBL.class);

	//
	// Parameters
	private Properties _ctx;
	private I_M_Warehouse _warehouseTo;
	private I_M_Locator _locatorTo;
	private Iterator<HUToDistribute> _hus;
	private final Timestamp date = SystemTime.asDayTimestamp();
	private int _bpartnerId;
	private int _bpartnerLocationId;

	//
	// Status
	private final AtomicBoolean _processed = new AtomicBoolean();
	private I_S_Resource plant;
	private org.compiere.model.I_M_Warehouse warehouseInTrasit;
	private int docTypeDO_ID;
	private I_AD_Org org;
	private final Map<ArrayKey, DDOrderLineCandidate> ddOrderLineCandidates = new LinkedHashMap<>();

	private HUs2DDOrderProducer()
	{
		super();
	}

	public void process()
	{
		markAsProcessed();

		// Make sure we are running out of transaction.
		// NOTE: it won't be a big harm to run in transaction too, but in most of the cases this is not the intention because this could be a long running process
		// NOTE2: we still have cases where we cannot avoid being in transaction, so we commented it out. 
		// trxManager.assertThreadInheritedTrxNotExists();

		prepareProcessing();

		final Properties ctx = getCtx();
		huTrxBL.createHUContextProcessorExecutor(PlainContextAware.newWithThreadInheritedTrx(ctx))
				.run(this::processInTrx);
	}

	protected void processInTrx(IHUContext huContext)
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
		processDDOrderLineCandidates(huContext);
	}

	private final void processDDOrderLineCandidates(final IHUContext huContext)
	{
		if (ddOrderLineCandidates.isEmpty())
		{
			return;
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
	}

	/** Loads common master data which will be used */
	private void prepareProcessing()
	{
		//
		// DD Order organization
		final I_M_Warehouse warehouseTo = getM_Warehouse_To();
		org = warehouseTo.getAD_Org();
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);

		//
		// Plant
		plant = warehouseTo.getPP_Plant();

		//
		// InTransit warehouse
		warehouseInTrasit = warehouseDAO.retrieveWarehouseInTransitForOrg(ctx, org.getAD_Org_ID());
		Check.assumeNotNull(warehouseInTrasit, "Warehouse in Trasit shall exist for {}", org);

		//
		// DD_Order document type
		docTypeDO_ID = Services.get(IDocTypeDAO.class).getDocTypeIdOrNull(
				DocTypeQuery.builder()
						.docBaseType(X_C_DocType.DOCBASETYPE_DistributionOrder)
						.adClientId(Env.getAD_Client_ID(ctx))
						.adOrgId(org.getAD_Org_ID())
						.build());

	}

	private final void assertNotProcessed()
	{
		Check.assume(!_processed.get(), "producer not already executed");
	}

	private final void markAsProcessed()
	{
		final boolean alreadyProcessed = _processed.getAndSet(true);
		Check.assume(!alreadyProcessed, "producer not already executed");
	}

	public final HUs2DDOrderProducer setContext(final Properties ctx)
	{
		this._ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	public final HUs2DDOrderProducer setM_Warehouse_To(final org.compiere.model.I_M_Warehouse warehouseTo)
	{
		assertNotProcessed();

		Check.assumeNotNull(warehouseTo, "warehouseTo not null");
		_warehouseTo = InterfaceWrapperHelper.create(warehouseTo, I_M_Warehouse.class);
		_locatorTo = warehouseBL.getDefaultLocator(_warehouseTo);
		return this;
	}

	public HUs2DDOrderProducer setBpartnerId(final int bpartnerId)
	{
		assertNotProcessed();
		_bpartnerId = bpartnerId;
		return this;
	}

	public HUs2DDOrderProducer setBpartnerLocationId(final int bpartnerLocationId)
	{
		assertNotProcessed();
		_bpartnerLocationId = bpartnerLocationId;
		return this;
	}

	private final int getBpartnerId()
	{
		Check.assumeGreaterThanZero(_bpartnerId, "_bpartnerId");
		Check.assumeNotNull(_bpartnerId, "Partner rnot null");
		return _bpartnerId;
	}

	private final int getBpartnerLocationId()
	{
		Check.assumeGreaterThanZero(_bpartnerLocationId, "_bpLocationId");
		return _bpartnerLocationId;
	}

	private final I_M_Warehouse getM_Warehouse_To()
	{
		Check.assumeNotNull(_warehouseTo, "_warehouseTo not null");
		return _warehouseTo;
	}

	private final I_M_Locator getM_Locator_To()
	{
		Check.assumeNotNull(_locatorTo, "_locatorTo not null");
		return _locatorTo;
	}

	public HUs2DDOrderProducer setHUs(final Iterator<HUToDistribute> hus)
	{
		assertNotProcessed();
		_hus = hus;
		return this;
	}

	private final Iterator<HUToDistribute> getHUs()
	{
		Check.assumeNotNull(_hus, "_hus not null");
		return _hus;
	}

	private final ILoggable getLoggable()
	{
		return Loggables.getLoggableOrLogger(logger, Level.INFO);
	}

	private void addHU(final IHUContext huContext, final HUToDistribute huToDistribute)
	{
		final I_M_HU hu = huToDistribute.getHu();
		//
		// Validate the HU before creating the DD_Order
		{
			final I_M_Locator huLocator = hu.getM_Locator();
			Check.assumeNotNull(huLocator, "HU has a locator set");

			final I_M_Warehouse warehouseTo = getM_Warehouse_To();
			Check.assume(huLocator.getM_Warehouse_ID() != warehouseTo.getM_Warehouse_ID(), "HU's is not stored in destination warehouse");
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
			final DDOrderLineCandidate ddOrderLineCandidateNew = new DDOrderLineCandidate(huContext, huProductStorage, huToDistribute);
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

	private final I_DD_Order createDD_OrderHeader(final IHUContext huContext)
	{
		final Properties ctx = huContext.getCtx();

		final I_DD_Order ddOrder = InterfaceWrapperHelper.create(ctx, I_DD_Order.class, ITrx.TRXNAME_ThreadInherited);
		ddOrder.setAD_Org(org);
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setPP_Plant(plant);
		ddOrder.setC_BPartner_ID(getBpartnerId());
		ddOrder.setC_BPartner_Location_ID(getBpartnerLocationId());
		// order.setSalesRep_ID(productPlanningData.getPlanner_ID());

		ddOrder.setC_DocType_ID(docTypeDO_ID);
		ddOrder.setM_Warehouse(warehouseInTrasit);
		ddOrder.setDocStatus(X_DD_Order.DOCSTATUS_Drafted);
		ddOrder.setDocAction(X_DD_Order.DOCACTION_Complete);
		ddOrder.setDateOrdered(date);
		ddOrder.setDatePromised(date);
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
		final I_M_Product product = ddOrderLineCandidate.getM_Product();

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
		// final I_M_HU hu = huProductStorage.getM_HU();
		final I_M_Locator locatorFrom = ddOrderLineCandidate.getM_Locator_From();
		final I_M_Locator locatorTo = getM_Locator_To();
		ddOrderline.setM_Locator(locatorFrom);
		ddOrderline.setM_LocatorTo(locatorTo);

		//
		// Product, UOM, Qty
		// NOTE: we assume qtyToMove is in "mrpContext.getC_UOM()" which shall be the Product's stocking UOM
		final BigDecimal qty = ddOrderLineCandidate.getQtyInSourceUOM();
		final I_C_UOM qtyUOM = ddOrderLineCandidate.getC_UOM();
		final BigDecimal qtyInStockingUOM = ddOrderLineCandidate.getQtyInStockingUOM();
		ddOrderline.setM_Product(product);
		ddOrderline.setC_UOM(qtyUOM);
		ddOrderline.setQtyEntered(qty);
		ddOrderline.setQtyOrdered(qtyInStockingUOM);
		ddOrderline.setTargetQty(qtyInStockingUOM);

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

		final I_M_Product_LotNumber_Lock lotNumberLock = ddOrderLineCandidate.getLotNumberLock();

		final String lotNoLockDescription = getDescriptionForLotNoLock(lotNumberLock);

		description.append(lotNoLockDescription);
		description.append(ddOrderLineCandidate.getDescription());

		ddOrderline.setDescription(description.toString());
		//
		// Other flags
		ddOrderline.setIsInvoiced(false);

		//
		// Save DD Order Line
		InterfaceWrapperHelper.save(ddOrderline);

		//
		// Create the HU assignment candidate
		huDDOrderDAO.addToHUsScheduledToMove(ddOrderline, ddOrderLineCandidate.getM_HUs());
	}

	private String getDescriptionForLotNoLock(final I_M_Product_LotNumber_Lock lotNumberLock)
	{
		if (lotNumberLock == null)
		{
			return "";
		}

		final String lotNoLockDescription = lotNumberLock.getDescription();

		if (Check.isEmpty(lotNoLockDescription))
		{
			return "";
		}

		return lotNoLockDescription + "; ";
	}

	/**
	 * Creates and sets DD_OrderLine's ASI and ASI To by copying the document relevant attributes from given HU.
	 *
	 * @param attributeStorageFactory
	 * @param ddOrderline
	 * @param ddOrderLineCandidate
	 */
	private void createASI(final IAttributeStorageFactory attributeStorageFactory, final I_DD_OrderLine ddOrderline, final DDOrderLineCandidate ddOrderLineCandidate)
	{
		final I_M_AttributeSetInstance ddOrderline_ASIFrom = attributeSetInstanceBL.getCreateASI(DDOrderLineAttributeSetInstanceAware.ofASIFrom(ddOrderline));
		final IAttributeStorage ddOrderLine_AttributesFrom = attributeStorageFactory.getAttributeStorage(ddOrderline_ASIFrom);

		final I_M_AttributeSetInstance ddOrderline_ASITo = attributeSetInstanceBL.getCreateASI(DDOrderLineAttributeSetInstanceAware.ofASITo(ddOrderline));
		final IAttributeStorage ddOrderLine_AttributesTo = attributeStorageFactory.getAttributeStorage(ddOrderline_ASITo);

		for (final Map.Entry<org.compiere.model.I_M_Attribute, Object> attribute2value : ddOrderLineCandidate.getAttributes().entrySet())
		{
			final I_M_Attribute attribute = InterfaceWrapperHelper.create(attribute2value.getKey(), I_M_Attribute.class);
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

	private final class DDOrderLineCandidate
	{
		private I_M_Locator locatorFrom;
		private I_M_Product product;
		private I_C_UOM uom;
		private ArrayKey aggregationKey;
		private final List<I_M_HU> hus = new ArrayList<>();

		private BigDecimal qtyInSourceUOM = BigDecimal.ZERO;
		private BigDecimal qtyInStockingUOM = BigDecimal.ZERO;

		private I_M_HU_PI_Item_Product piItemProduct;
		private Map<org.compiere.model.I_M_Attribute, Object> attributes = ImmutableMap.of();

		private I_M_Product_LotNumber_Lock lotNoLock;

		public DDOrderLineCandidate(final IHUContext huContext, final IHUProductStorage huProductStorage, final HUToDistribute huToDistribute)
		{
			super();

			final ArrayKeyBuilder aggregationKeyBuilder = Util.mkKey();

			//
			// Locator from
			final I_M_HU hu = huProductStorage.getM_HU();
			this.locatorFrom = hu.getM_Locator();
			aggregationKeyBuilder.appendId(locatorFrom.getM_Locator_ID());

			//
			// Product & UOM
			this.product = huProductStorage.getM_Product();
			this.uom = huProductStorage.getC_UOM();
			aggregationKeyBuilder.appendId(product.getM_Product_ID());
			aggregationKeyBuilder.appendId(uom.getC_UOM_ID());

			//
			// PI Item Product
			this.piItemProduct = hu.getM_HU_PI_Item_Product();
			aggregationKeyBuilder.appendId(piItemProduct == null ? -1 : piItemProduct.getM_HU_PI_Item_Product_ID());

			//
			// Fetch relevant attributes
			final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
			final IQualityInspectionSchedulable qualityInspectionSchedulable = huMaterialTrackingId.asQualityInspectionSchedulable(huContext, huAttributeStorage).orNull();
			if (qualityInspectionSchedulable != null)
			{
				this.attributes = qualityInspectionSchedulable.getAttributesAsMap();
			}
			for (Map.Entry<org.compiere.model.I_M_Attribute, Object> attribute2value : attributes.entrySet())
			{
				aggregationKeyBuilder.append(attribute2value.getKey().getValue(), attribute2value.getValue());
			}

			this.lotNoLock = huToDistribute.getLockLotNo();

			aggregationKeyBuilder.append(lotNoLock == null ? -1 : lotNoLock.getM_Product_LotNumber_Lock_ID());

			this.aggregationKey = aggregationKeyBuilder.build();

			//
			// Add this HUProductStoarge
			addHUProductStorage(huProductStorage);
		}

		public final ArrayKey getAggregationKey()
		{
			Check.assumeNotNull(aggregationKey, "aggregationKey not null");
			return aggregationKey;
		}

		public void addDDOrderLineCandidate(final DDOrderLineCandidate candidateToAdd)
		{
			Check.assume(Objects.equals(this.aggregationKey, candidateToAdd.getAggregationKey()), "Same aggregation key\n.Expected: {} \nBut it was: {}", this.aggregationKey, candidateToAdd.getAggregationKey());

			this.hus.addAll(candidateToAdd.getM_HUs());

			final BigDecimal huQtyInSourceUOM = candidateToAdd.getQtyInSourceUOM();
			this.qtyInSourceUOM = this.qtyInSourceUOM.add(huQtyInSourceUOM);

			final BigDecimal huQtyInStockingUOM = candidateToAdd.getQtyInStockingUOM();
			this.qtyInStockingUOM = this.qtyInStockingUOM.add(huQtyInStockingUOM);
		}

		private void addHUProductStorage(final IHUProductStorage huProductStorage)
		{
			final I_M_HU hu = huProductStorage.getM_HU();
			this.hus.add(hu);

			final BigDecimal huQtyInSourceUOM = huProductStorage.getQty();
			this.qtyInSourceUOM = this.qtyInSourceUOM.add(huQtyInSourceUOM);

			final BigDecimal huQtyInStockingUOM = huProductStorage.getQtyInStockingUOM();
			this.qtyInStockingUOM = this.qtyInStockingUOM.add(huQtyInStockingUOM);
		}

		public final I_M_Locator getM_Locator_From()
		{
			return locatorFrom;
		}

		public final I_M_Product getM_Product()
		{
			return product;
		}

		public final I_C_UOM getC_UOM()
		{
			return uom;
		}

		public BigDecimal getQtyInSourceUOM()
		{
			return qtyInSourceUOM;
		}

		public BigDecimal getQtyInStockingUOM()
		{
			return qtyInStockingUOM;
		}

		public I_M_HU_PI_Item_Product getM_HU_PI_Item_Product()
		{
			return piItemProduct;
		}

		public List<I_M_HU> getM_HUs()
		{
			return hus;
		}

		public String getDescription()
		{
			final StringBuilder description = new StringBuilder();
			for (final I_M_HU hu : hus)
			{
				final String huValue = hu.getValue();
				if (description.length() > 0)
				{
					description.append(", ");
				}
				description.append(huValue);
			}

			description.insert(0, msgBL.translate(Env.getCtx(), "M_HU_ID") + ": ");

			return description.toString();
		}

		public I_M_Product_LotNumber_Lock getLotNumberLock()
		{
			return lotNoLock;
		}

		public Map<org.compiere.model.I_M_Attribute, Object> getAttributes()
		{
			return attributes;
		}
	}

	@Value
	public static final class HUToDistribute
	{
		public static HUToDistribute ofHU(final I_M_HU hu)
		{
			return builder().hu(hu).build();
		}
		
		I_M_HU hu;
		I_M_Product_LotNumber_Lock lockLotNo;
		int bpartnerId;
		int bpartnerLocationId;

		@Builder
		private HUToDistribute(
				@NonNull final I_M_HU hu,
				I_M_Product_LotNumber_Lock lockLotNo,
				int bpartnerId,
				int bpartnerLocationId)
		{
			this.hu = hu;
			this.lockLotNo = lockLotNo;
			this.bpartnerId = bpartnerId;
			this.bpartnerLocationId = bpartnerLocationId;
		}
	}
}
