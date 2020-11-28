package de.metas.handlingunits.ddorder.api.impl;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;

import de.metas.common.util.time.SystemTime;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.api.IAttributeSetInstanceBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.IWarehouseDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Attribute;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Locator;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Warehouse;
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
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.ddorder.api.IHUDDOrderDAO;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.materialtracking.IHUMaterialTrackingBL;
import de.metas.handlingunits.materialtracking.IQualityInspectionSchedulable;
import de.metas.handlingunits.model.I_DD_OrderLine;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
// import de.metas.handlingunits.model.I_M_Warehouse;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.i18n.IMsgBL;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.product.IProductDAO;
import de.metas.product.LotNumberQuarantine;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.ILoggable;
import de.metas.util.Loggables;
import de.metas.util.Services;
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
	private final transient IDocumentBL docActionBL = Services.get(IDocumentBL.class);
	private final transient IHUDDOrderDAO huDDOrderDAO = Services.get(IHUDDOrderDAO.class);
	private final transient IAttributeSetInstanceBL attributeSetInstanceBL = Services.get(IAttributeSetInstanceBL.class);
	private final IHUMaterialTrackingBL huMaterialTrackingId = Services.get(IHUMaterialTrackingBL.class);

	//
	// Parameters
	private Properties _ctx;
	// private I_M_Warehouse _warehouseTo;
	private I_M_Locator _locatorTo;
	private Iterator<HUToDistribute> _hus;
	private final Timestamp date = SystemTime.asDayTimestamp();
	private int _bpartnerId;
	private int _bpartnerLocationId;

	//
	// Status
	private final AtomicBoolean _processed = new AtomicBoolean();
	private I_S_Resource plant;
	private WarehouseId warehouseInTrasitId;
	private DocTypeId docTypeDO_ID;
	private OrgId orgId;
	private final Map<ArrayKey, DDOrderLineCandidate> ddOrderLineCandidates = new LinkedHashMap<>();

	private HUs2DDOrderProducer()
	{
	}

	public Optional<I_DD_Order> process()
	{
		markAsProcessed();

		// Make sure we are running out of transaction.
		// NOTE: it won't be a big harm to run in transaction too, but in most of the cases this is not the intention because this could be a long running process
		// NOTE2: we still have cases where we cannot avoid being in transaction, so we commented it out.
		// trxManager.assertThreadInheritedTrxNotExists();

		prepareProcessing();

		final PlainContextAware ctx = PlainContextAware.newWithThreadInheritedTrx(getCtx());

		final I_DD_Order ddOrderOrNull = huTrxBL
				.createHUContextProcessorExecutor(ctx)
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

	private final I_DD_Order processDDOrderLineCandidates(@NonNull final IHUContext huContext)
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

	/** Loads common master data which will be used */
	private void prepareProcessing()
	{
		//
		// DD Order organization
		final I_M_Warehouse warehouseTo = getM_Warehouse_To();
		orgId = OrgId.ofRepoId(warehouseTo.getAD_Org_ID());

		//
		// Plant
		plant = warehouseTo.getPP_Plant();

		//
		// InTransit warehouse
		warehouseInTrasitId = warehouseDAO.getInTransitWarehouseId(orgId);

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
		_ctx = ctx;
		return this;
	}

	private final Properties getCtx()
	{
		Check.assumeNotNull(_ctx, "_ctx not null");
		return _ctx;
	}

	public HUs2DDOrderProducer setM_Locator_To(@NonNull final I_M_Locator locatorTo)
	{
		assertNotProcessed();

		_locatorTo = locatorTo;
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
		return Check.assumeNotNull(_locatorTo, "_warehouseTo not null").getM_Warehouse();
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
			final WarehouseId huWarehouseId = IHandlingUnitsBL.extractWarehouseId(hu);
			final I_M_Warehouse warehouseTo = getM_Warehouse_To();
			Check.assume(huWarehouseId.getRepoId() != warehouseTo.getM_Warehouse_ID(), "HU's is not stored in destination warehouse");
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
		ddOrder.setAD_Org_ID(orgId.getRepoId());
		ddOrder.setMRP_Generated(true);
		ddOrder.setMRP_AllowCleanup(true);
		ddOrder.setPP_Plant(plant);
		ddOrder.setC_BPartner_ID(getBpartnerId());
		ddOrder.setC_BPartner_Location_ID(getBpartnerLocationId());
		// order.setSalesRep_ID(productPlanningData.getPlanner_ID());

		ddOrder.setC_DocType_ID(DocTypeId.toRepoId(docTypeDO_ID));
		ddOrder.setM_Warehouse_ID(warehouseInTrasitId.getRepoId());
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
		final Quantity qtyInStockingUOM = ddOrderLineCandidate.getQtyInStockingUOM();
		ddOrderline.setM_Product(product);
		ddOrderline.setC_UOM(qtyUOM);
		ddOrderline.setQtyEntered(qty);
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

		final LotNumberQuarantine lotNumberQuarantine = ddOrderLineCandidate.getLotNumberQuarantine();
		final String lotNoQuarantineDescription = getDescriptionForLotNoQuarantine(lotNumberQuarantine);

		description.append(lotNoQuarantineDescription);
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

	private static String getDescriptionForLotNoQuarantine(final LotNumberQuarantine lotNumberQuarantine)
	{
		if (lotNumberQuarantine == null)
		{
			return "";
		}

		final String lotNoQuarantineDescription = lotNumberQuarantine.getDescription();
		if (Check.isEmpty(lotNoQuarantineDescription))
		{
			return "";
		}

		return lotNoQuarantineDescription + "; ";
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
		private final I_M_Locator locatorFrom;
		private final I_M_Product product;
		private final I_C_UOM uom;
		private final ArrayKey aggregationKey;
		private final List<I_M_HU> hus = new ArrayList<>();

		private BigDecimal qtyInSourceUOM = BigDecimal.ZERO;
		private Quantity _qtyInStockingUOM = null;

		private final I_M_HU_PI_Item_Product piItemProduct;
		private Map<org.compiere.model.I_M_Attribute, Object> attributes = ImmutableMap.of();

		private LotNumberQuarantine lotNoQuarantine;

		public DDOrderLineCandidate(final IHUContext huContext, final IHUProductStorage huProductStorage, final HUToDistribute huToDistribute)
		{
			final ArrayKeyBuilder aggregationKeyBuilder = Util.mkKey();

			//
			// Locator from
			final I_M_HU hu = huProductStorage.getM_HU();
			locatorFrom = IHandlingUnitsBL.extractLocator(hu);
			aggregationKeyBuilder.appendId(locatorFrom.getM_Locator_ID());

			//
			// Product & UOM
			product = Services.get(IProductDAO.class).getById(huProductStorage.getProductId());
			uom = huProductStorage.getC_UOM();
			aggregationKeyBuilder.appendId(product.getM_Product_ID());
			aggregationKeyBuilder.appendId(uom.getC_UOM_ID());

			//
			// PI Item Product
			piItemProduct = IHandlingUnitsBL.extractPIItemProductOrNull(hu);
			aggregationKeyBuilder.appendId(piItemProduct == null ? -1 : piItemProduct.getM_HU_PI_Item_Product_ID());

			//
			// Fetch relevant attributes
			final IAttributeStorage huAttributeStorage = huContext.getHUAttributeStorageFactory().getAttributeStorage(hu);
			final IQualityInspectionSchedulable qualityInspectionSchedulable = huMaterialTrackingId.asQualityInspectionSchedulable(huContext, huAttributeStorage).orElse(null);
			if (qualityInspectionSchedulable != null)
			{
				attributes = qualityInspectionSchedulable.getAttributesAsMap();
			}
			for (final Map.Entry<I_M_Attribute, Object> attribute2value : attributes.entrySet())
			{
				aggregationKeyBuilder.append(attribute2value.getKey().getValue(), attribute2value.getValue());
			}

			this.lotNoQuarantine = huToDistribute.getQuarantineLotNo();

			aggregationKeyBuilder.append(lotNoQuarantine == null ? -1 : lotNoQuarantine.getId());

			aggregationKey = aggregationKeyBuilder.build();

			//
			// Add this HUProductStoarge
			addHUProductStorage(huProductStorage);
		}

		public ArrayKey getAggregationKey()
		{
			Check.assumeNotNull(aggregationKey, "aggregationKey not null");
			return aggregationKey;
		}

		public void addDDOrderLineCandidate(final DDOrderLineCandidate candidateToAdd)
		{
			Check.assume(Objects.equals(aggregationKey, candidateToAdd.getAggregationKey()), "Same aggregation key\n.Expected: {} \nBut it was: {}", aggregationKey, candidateToAdd.getAggregationKey());

			hus.addAll(candidateToAdd.getM_HUs());

			final BigDecimal huQtyInSourceUOM = candidateToAdd.getQtyInSourceUOM();
			qtyInSourceUOM = qtyInSourceUOM.add(huQtyInSourceUOM);

			addQtyInStockingUOM(candidateToAdd.getQtyInStockingUOM());
		}

		private void addHUProductStorage(final IHUProductStorage huProductStorage)
		{
			final I_M_HU hu = huProductStorage.getM_HU();
			hus.add(hu);

			final BigDecimal huQtyInSourceUOM = huProductStorage.getQty().toBigDecimal();
			qtyInSourceUOM = qtyInSourceUOM.add(huQtyInSourceUOM);

			addQtyInStockingUOM(huProductStorage.getQtyInStockingUOM());
		}

		public I_M_Locator getM_Locator_From()
		{
			return locatorFrom;
		}

		public I_M_Product getM_Product()
		{
			return product;
		}

		public I_C_UOM getC_UOM()
		{
			return uom;
		}

		public BigDecimal getQtyInSourceUOM()
		{
			return qtyInSourceUOM;
		}

		public Quantity getQtyInStockingUOM()
		{
			Check.assumeNotNull(_qtyInStockingUOM, "qtyInStockingUOM shall be initialized");
			return _qtyInStockingUOM;
		}

		private void addQtyInStockingUOM(@NonNull final Quantity qtyToAdd)
		{
			this._qtyInStockingUOM = Quantity.addNullables(_qtyInStockingUOM, qtyToAdd);
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

		public LotNumberQuarantine getLotNumberQuarantine()
		{
			return lotNoQuarantine;
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
		LotNumberQuarantine quarantineLotNo;
		int bpartnerId;
		int bpartnerLocationId;

		@Builder
		private HUToDistribute(
				@NonNull final I_M_HU hu,
				LotNumberQuarantine quarantineLotNo,
				int bpartnerId,
				int bpartnerLocationId)
		{
			this.hu = hu;
			this.quarantineLotNo = quarantineLotNo;
			this.bpartnerId = bpartnerId;
			this.bpartnerLocationId = bpartnerLocationId;
		}
	}
}
