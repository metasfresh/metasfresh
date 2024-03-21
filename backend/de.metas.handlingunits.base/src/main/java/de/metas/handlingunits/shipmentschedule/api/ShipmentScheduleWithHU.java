package de.metas.handlingunits.shipmentschedule.api;

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

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jgoodies.common.base.Objects;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHUContextFactory;
import de.metas.handlingunits.IHUPIItemProductDAO;
import de.metas.handlingunits.IHUPackageBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.IMutableHUContext;
import de.metas.handlingunits.attribute.IAttributeValue;
import de.metas.handlingunits.attribute.storage.ASIAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.storage.IAttributeStorageFactory;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_ShipmentSchedule_QtyPicked;
import de.metas.handlingunits.model.X_M_HU_Item;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.IShipmentScheduleAllocBL;
import de.metas.inoutcandidate.api.IShipmentScheduleBL;
import de.metas.inoutcandidate.api.IShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.api.IShipmentScheduleHandlerBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.logging.LogManager;
import de.metas.order.OrderAndLineId;
import de.metas.order.OrderId;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.StockQtyAndUOMQty;
import de.metas.quantity.StockQtyAndUOMQtys;
import de.metas.shipping.ShipperId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.Null;
import org.compiere.util.Env;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;
import static org.adempiere.model.InterfaceWrapperHelper.create;
import static org.adempiere.model.InterfaceWrapperHelper.getContextAware;
import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/** Note that contrary to the class name, this might as well be a shipment schedule *without* HU. See {@link #ofShipmentScheduleWithoutHu(IHUContext, I_M_ShipmentSchedule, StockQtyAndUOMQty, M_ShipmentSchedule_QuantityTypeToUse)}. */
public class ShipmentScheduleWithHU
{
	public static ShipmentScheduleWithHU ofShipmentScheduleQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@Nullable final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		final IMutableHUContext huContext = Services.get(IHUContextFactory.class).createMutableHUContext();
		return ofShipmentScheduleQtyPickedWithHuContext(shipmentScheduleQtyPicked, huContext, qtyTypeToUse);
	}

	public static ShipmentScheduleWithHU ofShipmentScheduleQtyPicked(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final IHUContext huContext)
	{
		return ofShipmentScheduleQtyPickedWithHuContext(
				shipmentScheduleQtyPicked,
				huContext,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER/* just because that's how it was an we don't have great test coverage */);
	}

	public static ShipmentScheduleWithHU ofShipmentScheduleQtyPickedWithHuContext(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final IHUContext huContext,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		return new ShipmentScheduleWithHU(
				huContext,
				shipmentScheduleQtyPicked,
				qtyTypeToUse);
	}

	public static ShipmentScheduleWithHU ofShipmentScheduleQtyPickedWithHuContext(
			@NonNull final I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked,
			@NonNull final IHUContext huContext)
	{
		return new ShipmentScheduleWithHU(
				huContext,
				shipmentScheduleQtyPicked,
				M_ShipmentSchedule_QuantityTypeToUse.TYPE_QTY_TO_DELIVER/* just because that's how it was and we don't have great test coverage */);
	}

	/**
	 * Create an "empty" instance with no HUs inside. Used if a shipment without HU allocation has to be created.
	 */
	public static ShipmentScheduleWithHU ofShipmentScheduleWithoutHu(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		return new ShipmentScheduleWithHU(huContext, shipmentSchedule, stockQtyAndCatchQty, qtyTypeToUse);
	}

	private static final Logger logger = LogManager.getLogger(ShipmentScheduleWithHU.class);

	private final IHUContext huContext;
	private final I_M_ShipmentSchedule shipmentSchedule;
	private final Quantity pickedQty;

	@Getter
	private final Optional<Quantity> catchQty;
	private final I_M_HU vhu;
	private final I_M_HU tuHU;
	private final I_M_HU luHU;

	@Getter(lazy = true)
	private final Object attributesAggregationKey = computeAttributesAggregationKey();

	@Getter(lazy = true)
	private final List<IAttributeValue> attributeValues = computeAttributeValues();

	private I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked;

	private I_M_InOutLine shipmentLine = null;

	@Getter
	private final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse;

	/**
	 * Tells us if the candidate is supposed to receive a manual packing material (in the case when we have a quick shipment, with no picking).
	 * <p>
	 * <b>IMPORTANT:</b> is ignored if there is already and existing ShipmentLineBuilder and this ShipmentScheduleWithHU can be added to it.
	 */
	@Getter
	private final boolean adviseManualPackingMaterial;

	private ShipmentScheduleWithHU(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule_QtyPicked allocRecord,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		this.huContext = huContext;

		this.shipmentScheduleQtyPicked = allocRecord;
		this.shipmentSchedule = create(allocRecord.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);

		final ProductId productId = ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
		final UomId catchUomIdOrNull = UomId.ofRepoIdOrNull(allocRecord.getCatch_UOM_ID());

		final StockQtyAndUOMQty stockQtyAndCatchQty = StockQtyAndUOMQtys.create(
				allocRecord.getQtyPicked(), productId,
				allocRecord.getQtyDeliveredCatch(), catchUomIdOrNull);

		this.pickedQty = stockQtyAndCatchQty.getStockQty();
		this.catchQty = stockQtyAndCatchQty.getUOMQtyOpt();

		this.vhu = allocRecord.getVHU_ID() > 0 ? allocRecord.getVHU() : null;
		this.tuHU = allocRecord.getM_TU_HU_ID() > 0 ? allocRecord.getM_TU_HU() : null;
		this.luHU = allocRecord.getM_LU_HU_ID() > 0 ? allocRecord.getM_LU_HU() : null;

		this.adviseManualPackingMaterial = false;
		this.qtyTypeToUse = qtyTypeToUse;
	}

	/**
	 * Creates a HU-"empty" instance that just references the given shipment schedule.
	 */
	private ShipmentScheduleWithHU(
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		this.huContext = huContext;

		this.shipmentScheduleQtyPicked = null; // no allocation, will be created on fly when needed
		this.shipmentSchedule = shipmentSchedule;

		this.pickedQty = stockQtyAndCatchQty.getStockQty();
		this.catchQty = stockQtyAndCatchQty.getUOMQtyOpt();

		vhu = null; // no VHU
		tuHU = null; // no TU
		luHU = null; // no LU

		this.adviseManualPackingMaterial = true;
		this.qtyTypeToUse = qtyTypeToUse;
	}

	@Override
	public String toString()
	{
		return "ShipmentScheduleWithHU ["
				+ "\n    shipmentSchedule=" + shipmentSchedule
				+ "\n    qtyPicked=" + pickedQty
				+ "\n    vhu=" + vhu
				+ "\n    tuHU=" + tuHU
				+ "\n    luHU=" + luHU
				+ "\n    attributesAggregationKey=" + (attributesAggregationKey == null ? "<NOT BUILT>" : attributesAggregationKey)
				+ "\n    shipmentScheduleAlloc=" + shipmentScheduleQtyPicked
				+ "\n    shipmentLine=" + shipmentLine
				+ "\n	 qtyTypeToUse=" + qtyTypeToUse
				+ "\n]";
	}

	public IHUContext getHUContext()
	{
		return huContext;
	}

	public ProductId getProductId()
	{
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	public int getM_AttributeSetInstance_ID()
	{
		final int asiId = shipmentSchedule.getM_AttributeSetInstance_ID();
		return asiId <= 0 ? AttributeConstants.M_AttributeSetInstance_ID_None : asiId;
	}

	private Object computeAttributesAggregationKey()
	{
		logger.trace("Creating AttributesAggregationKey");

		final ImmutableMap.Builder<AttributeCode, Object> keyBuilder = ImmutableMap.builder();

		for (final IAttributeValue attributeValue : getAttributeValues())
		{
			final AttributeCode name = attributeValue.getAttributeCode();
			final Object value = attributeValue.getValue();
			keyBuilder.put(name, Null.box(value));
		}
		final ImmutableMap<AttributeCode, Object> attributesAggregationKey = keyBuilder.build();

		logger.trace("AttributesAggregationKey created: {}", attributesAggregationKey);
		return attributesAggregationKey;
	}

	private List<IAttributeValue> computeAttributeValues()
	{
		final TreeSet<IAttributeValue> allAttributeValues = //
				new TreeSet<>(Comparator.comparing(av -> av.getM_Attribute().getM_Attribute_ID()));

		final IAttributeStorageFactory attributeStorageFactory = huContext.getHUAttributeStorageFactory();
		final I_M_HU hu = getTopLevelHU();
		if (hu != null)
		{
			final IAttributeStorage huAttributeStorage = attributeStorageFactory.getAttributeStorage(hu);
			allAttributeValues.addAll(huAttributeStorage.getAttributeValues());
		}

		if (getM_AttributeSetInstance_ID() > 0)
		{
			/// add all values from the ASI
			final I_M_AttributeSetInstance attributeSetInstance = load(getM_AttributeSetInstance_ID(), I_M_AttributeSetInstance.class);
			final IAttributeStorage asiAttributeStorage = ASIAttributeStorage.createNew(attributeStorageFactory, attributeSetInstance);
			allAttributeValues.addAll(asiAttributeStorage.getAttributeValues());

			// additionally add whatever the attributeStorageFactory's storage implementation has to offer.
			final IAttributeStorage huAsiAttributeStorage = attributeStorageFactory.getAttributeStorage(attributeSetInstance);
			allAttributeValues.addAll(huAsiAttributeStorage.getAttributeValues());
		}

		final ShipmentScheduleHandler handler = Services.get(IShipmentScheduleHandlerBL.class).getHandlerFor(shipmentSchedule);

		final ImmutableList<IAttributeValue> result = allAttributeValues.stream()
				.filter(IAttributeValue::isUseInASI)
				.filter(attributeValue -> !Objects.equals(attributeValue.getValue(), attributeValue.getEmptyValue())) // when comparing different shipmentScheduleWithHU instances, we want no attributes to be equal to attributes with null values
				.filter(attributeValue -> handler.attributeShallBePartOfShipmentLine(shipmentSchedule, attributeValue.getM_Attribute()))
				.collect(ImmutableList.toImmutableList());
		return result;
	}

	@Nullable
	public OrderId getOrderId()
	{
		return OrderId.ofRepoIdOrNull(shipmentSchedule.getC_Order_ID());
	}

	@Nullable
	public OrderAndLineId getOrderLineId()
	{
		return OrderAndLineId.ofRepoIdsOrNull(shipmentSchedule.getC_Order_ID(), shipmentSchedule.getC_OrderLine_ID());
	}

	public ShipmentScheduleId getShipmentScheduleId()
	{
		return ShipmentScheduleId.ofRepoId(shipmentSchedule.getM_ShipmentSchedule_ID());
	}

	public I_M_ShipmentSchedule getM_ShipmentSchedule()
	{
		return shipmentSchedule;
	}

	public Quantity getQtyPicked()
	{
		return pickedQty;
	}

	public I_C_UOM getUOM()
	{
		return pickedQty.getUOM();
	}

	public I_M_HU getVHU()
	{
		return vhu;
	}

	public I_M_HU getM_TU_HU()
	{
		return tuHU;
	}

	public I_M_HU getM_LU_HU()
	{
		return luHU;
	}

	/**
	 * Gets the underlying LU or TU or VHU, depending on which is the first not null
	 *
	 * @return LU/TU/VHU
	 */
	private I_M_HU getTopLevelHU()
	{
		return CoalesceUtil.coalesce(luHU, tuHU, vhu);
	}

	/**
	 * Called by API when a shipment line that includes this candidate was just created.
	 */
	public ShipmentScheduleWithHU setM_InOutLine(final I_M_InOutLine shipmentLine)
	{
		logger.trace("Setting shipmentLine={} to {}", shipmentLine, this);
		this.shipmentLine = shipmentLine;
		return this;
	}

	public I_M_InOutLine getM_InOutLine()
	{
		return shipmentLine;
	}

	/**
	 * Called by API when a shipment that includes this candidate was generated and processed.
	 */
	public ShipmentScheduleWithHU setM_InOut(@NonNull final I_M_InOut inout)
	{
		final IContextAware context = getContextAware(inout);

		//
		// Create/Update ShipmentSchedule to Shipment Line allocation
		createUpdateShipmentLineAlloc(inout);

		//
		// Update all M_Packages that point to our top level HU (i.e. LU/TU/VHU if any)
		// and set M_InOut to them.
		final I_M_HU topLevelHU = getTopLevelHU();
		if (topLevelHU != null)
		{
			final IHUPackageBL huPackageBL = Services.get(IHUPackageBL.class);
			huPackageBL.assignShipmentToPackages(topLevelHU, inout, context.getTrxName());
		}
		return this;
	}

	public I_M_ShipmentSchedule_QtyPicked getShipmentScheduleQtyPicked()
	{
		return shipmentScheduleQtyPicked;
	}

	private void createUpdateShipmentLineAlloc(@NonNull final I_M_InOut inout)
	{
		// At this point we assume setM_InOutLine() method was called by API
		Check.assumeNotNull(shipmentLine, "shipmentLine not null");

		// If there is no shipment schedule allocation, create one now
		// This happens if you called the factory method that doesn't have a shipmentScheduleAlloc parameter
		if (shipmentScheduleQtyPicked == null)
		{
			final IShipmentScheduleAllocBL shipmentScheduleAllocBL = Services.get(IShipmentScheduleAllocBL.class);

			final StockQtyAndUOMQty stockQtyAndCatchQty = StockQtyAndUOMQty.builder()
					.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
					.stockQty(pickedQty)
					.uomQty(catchQty.orElse(null))
					.build();

			shipmentScheduleQtyPicked = create(
					shipmentScheduleAllocBL.createNewQtyPickedRecord(shipmentSchedule, stockQtyAndCatchQty),
					I_M_ShipmentSchedule_QtyPicked.class);

			// lu, tu and vhu are null, so no need to set them

			if (DocStatus.ofCode(inout.getDocStatus()).isCompletedOrClosed())
			{
				// take that bit we have from the iol. might be useful to have a least the number of TUs
				final de.metas.handlingunits.model.I_M_InOutLine iol = create(getM_InOutLine(), de.metas.handlingunits.model.I_M_InOutLine.class);
				final BigDecimal qtyEnteredTU = iol.getQtyEnteredTU();
				shipmentScheduleQtyPicked.setQtyTU(qtyEnteredTU);
				shipmentScheduleQtyPicked.setQtyLU(qtyEnteredTU.signum() > 0 ? ONE : ZERO);
			}
		}
		else
		{
			updateQtyTUAndQtyLU();
		}

		// Set shipment line link and processed flag
		shipmentScheduleQtyPicked.setM_InOutLine(shipmentLine);
		shipmentScheduleQtyPicked.setProcessed(inout.isProcessed());

		// Save allocation
		saveRecord(shipmentScheduleQtyPicked);

		final IShipmentScheduleBL shipmentScheduleBL = Services.get(IShipmentScheduleBL.class);
		shipmentScheduleBL.resetCatchQtyOverride(shipmentSchedule);
	}

	public void updateQtyTUAndQtyLU()
	{
		if (shipmentScheduleQtyPicked == null)
		{
			return;
		}

		final BigDecimal qtyLU = luHU == null ? ZERO : ONE;
		shipmentScheduleQtyPicked.setQtyLU(qtyLU);

		final BigDecimal qtyTU = extractTuQty();
		shipmentScheduleQtyPicked.setQtyTU(qtyTU);
	}

	private BigDecimal extractTuQty()
	{
		final BigDecimal qtyTU;
		if (tuHU == null)
		{
			qtyTU = ZERO;
		}
		else if (Services.get(IHandlingUnitsBL.class).isAggregateHU(tuHU))
		{
			Services.get(IHandlingUnitsDAO.class).retrieveParentItem(tuHU);
			qtyTU = tuHU.getM_HU_Item_Parent().getQty();
		}
		else
		{
			qtyTU = ONE;
		}
		return qtyTU;
	}

	/**
	 * @return never returns {@code null}. If none is found, it returns the "virtual" packing instruction (i.e. "No Packing Item").
	 */
	public I_M_HU_PI_Item_Product retrieveM_HU_PI_Item_Product()
	{
		if (getQtyTypeToUse().isOnlyUseToDeliver()
				&& (shipmentScheduleQtyPicked == null || shipmentScheduleQtyPicked.isAnonymousHuPickedOnTheFly()))
		{
			return retrievePiipForReferencedRecord();
		}

		final I_M_HU tuOrVhu = CoalesceUtil.coalesce(getM_TU_HU(), getVHU());
		if (tuOrVhu == null)
		{
			return retrievePiipForReferencedRecord();
		}

		if (tuOrVhu.getM_HU_PI_Item_Product_ID() > 0)
		{
			return IHandlingUnitsBL.extractPIItemProductOrNull(tuOrVhu);
		}

		final ImmutableList<I_M_HU_Item> huMaterialItems = Services.get(IHandlingUnitsDAO.class).retrieveItems(tuOrVhu).stream()
				.filter(item -> X_M_HU_Item.ITEMTYPE_Material.equals(item.getItemType()))
				.collect(ImmutableList.toImmutableList());
		if (huMaterialItems.isEmpty())
		{
			return retrievePiipForReferencedRecord();
		}

		Check.assume(huMaterialItems.size() == 1, "Each hu has just one M_HU_Item with type={}; hu={}; huMaterialItems={}", X_M_HU_Item.ITEMTYPE_Material, tuOrVhu, huMaterialItems);
		final I_M_HU_Item huMaterialItem = huMaterialItems.get(0);

		final IShipmentScheduleEffectiveBL shipmentScheduleEffectiveBL = Services.get(IShipmentScheduleEffectiveBL.class);

		final BPartnerId bpartnerId = shipmentScheduleEffectiveBL.getBPartnerId(shipmentSchedule);
		final ZonedDateTime preparationDate = shipmentScheduleEffectiveBL.getPreparationDate(shipmentSchedule);

		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);

		final I_M_HU_PI_Item huPIItem = Services.get(IHandlingUnitsBL.class).getPIItem(huMaterialItem);
		if (huPIItem == null)
		{
			return hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx());
		}

		final I_M_HU_PI_Item_Product matchingPiip = hupiItemProductDAO.retrievePIMaterialItemProduct(
				huPIItem,
				bpartnerId,
				getProductId(),
				preparationDate);
		if (matchingPiip != null)
		{
			return matchingPiip;
		}

		// could not find a packing instruction; return "No Packing Item"
		return hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx());
	}

	private I_M_HU_PI_Item_Product retrievePiipForReferencedRecord()
	{
		final IHUShipmentScheduleBL huShipmentScheduleBL = Services.get(IHUShipmentScheduleBL.class);

		final I_M_HU_PI_Item_Product piipIgnoringPickedHUs = huShipmentScheduleBL.getM_HU_PI_Item_Product_IgnoringPickedHUs(shipmentSchedule);
		if (piipIgnoringPickedHUs != null)
		{
			return piipIgnoringPickedHUs;
		}

		final IHUPIItemProductDAO hupiItemProductDAO = Services.get(IHUPIItemProductDAO.class);
		return hupiItemProductDAO.retrieveVirtualPIMaterialItemProduct(Env.getCtx());
	}

	@Nullable
	public ShipperId getShipperId()
	{
		return ShipperId.ofRepoIdOrNull(shipmentSchedule.getM_Shipper_ID());
	}
}
