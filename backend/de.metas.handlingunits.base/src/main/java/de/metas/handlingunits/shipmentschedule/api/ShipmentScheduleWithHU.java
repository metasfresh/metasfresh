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

import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.jgoodies.common.base.Objects;
import de.metas.bpartner.BPartnerId;
import de.metas.common.util.CoalesceUtil;
import de.metas.document.dimension.Dimension;
import de.metas.document.engine.DocStatus;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
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
import de.metas.inout.InOutAndLineId;
import de.metas.inout.ShipmentScheduleId;
import de.metas.inoutcandidate.api.impl.ShipmentScheduleEffectiveBL;
import de.metas.inoutcandidate.model.I_M_ShipmentSchedule;
import de.metas.inoutcandidate.spi.ShipmentScheduleHandler;
import de.metas.inoutcandidate.split.ShipmentScheduleSplit;
import de.metas.inoutcandidate.split.ShipmentScheduleSplitId;
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
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.IContextAware;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;
import org.compiere.model.Null;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.TreeSet;

import static java.math.BigDecimal.ONE;
import static java.math.BigDecimal.ZERO;

/**
 * Note that contrary to the class name, this might as well be a shipment schedule *without* HU.
 */
@SuppressWarnings("OptionalUsedAsFieldOrParameterType")
public class ShipmentScheduleWithHU
{
	private static final Logger logger = LogManager.getLogger(ShipmentScheduleWithHU.class);

	@NonNull private final ShipmentScheduleWithHUSupportingServices services;
	@NonNull private final IHUContext huContext;

	@NonNull private final I_M_ShipmentSchedule shipmentSchedule;
	@Nullable private final ShipmentScheduleSplit split;
	@Getter @Nullable private I_M_ShipmentSchedule_QtyPicked shipmentScheduleQtyPicked;

	@NonNull private final Quantity pickedQty;
	@NonNull @Getter private final Optional<Quantity> catchQty;

	@Nullable private final I_M_HU vhu;
	@Nullable private final I_M_HU tuHU;
	@Nullable private final I_M_HU luHU;

	@Getter(lazy = true)
	private final Object attributesAggregationKey = computeAttributesAggregationKey();

	@Getter(lazy = true)
	private final List<IAttributeValue> attributeValues = computeAttributeValues();

	@Nullable private I_M_InOutLine shipmentLine = null;

	@Getter private final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse;

	/**
	 * Tells us if the candidate is supposed to receive a manual packing material (in the case when we have a quick shipment, with no picking).
	 * <p>
	 * <b>IMPORTANT:</b> is ignored if there is already and existing ShipmentLineBuilder and this ShipmentScheduleWithHU can be added to it.
	 */
	@Getter private final boolean adviseManualPackingMaterial;

	ShipmentScheduleWithHU(
			@NonNull ShipmentScheduleWithHUSupportingServices services,
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule_QtyPicked allocRecord,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		this.services = services;
		this.huContext = huContext;

		this.shipmentScheduleQtyPicked = allocRecord;
		this.shipmentSchedule = InterfaceWrapperHelper.create(allocRecord.getM_ShipmentSchedule(), I_M_ShipmentSchedule.class);
		this.split = null;

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
	ShipmentScheduleWithHU(
			@NonNull ShipmentScheduleWithHUSupportingServices services, 
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final StockQtyAndUOMQty stockQtyAndCatchQty,
			@NonNull final M_ShipmentSchedule_QuantityTypeToUse qtyTypeToUse)
	{
		this.services = services;
		this.huContext = huContext;

		this.shipmentScheduleQtyPicked = null; // no allocation, will be created on fly when needed
		this.shipmentSchedule = shipmentSchedule;
		this.split = null;

		this.pickedQty = stockQtyAndCatchQty.getStockQty();
		this.catchQty = stockQtyAndCatchQty.getUOMQtyOpt();

		vhu = null; // no VHU
		tuHU = null; // no TU
		luHU = null; // no LU

		this.adviseManualPackingMaterial = true;
		this.qtyTypeToUse = qtyTypeToUse;
	}

	ShipmentScheduleWithHU(
			@NonNull final ShipmentScheduleWithHUSupportingServices services,
			@NonNull final IHUContext huContext,
			@NonNull final I_M_ShipmentSchedule shipmentSchedule,
			@NonNull final ShipmentScheduleSplit split)
	{
		this.services = services;
		this.huContext = huContext;
		this.shipmentSchedule = shipmentSchedule;
		this.split = split;

		this.pickedQty = split.getQtyToDeliver();
		this.catchQty = Optional.empty();

		this.vhu = null; // no VHU
		this.tuHU = null; // no TU
		this.luHU = null; // no LU

		this.adviseManualPackingMaterial = true;
		this.qtyTypeToUse = M_ShipmentSchedule_QuantityTypeToUse.TYPE_SPLIT_SHIPMENT;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.omitNullValues()
				.add("shipmentSchedule", shipmentSchedule)
				.add("split", split)
				.add("qtyPicked", getQtyPicked())
				.add("vhu", vhu)
				.add("tuHU", tuHU)
				.add("luHU", luHU)
				.add("attributesAggregationKey", attributesAggregationKey == null ? "<NOT BUILT>" : attributesAggregationKey)
				.add("shipmentLine", shipmentLine)
				.add("qtyTypeToUse", qtyTypeToUse)
				.toString();
	}

	public IHUContext getHUContext()
	{
		return huContext;
	}

	public ProductId getProductId()
	{
		return ProductId.ofRepoId(shipmentSchedule.getM_Product_ID());
	}

	private AttributeSetInstanceId getM_AttributeSetInstance_ID()
	{
		return AttributeSetInstanceId.ofRepoIdOrNone(shipmentSchedule.getM_AttributeSetInstance_ID());
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

		final AttributeSetInstanceId asiId = getM_AttributeSetInstance_ID();
		if (asiId.isRegular())
		{
			/// add all values from the ASI
			final I_M_AttributeSetInstance attributeSetInstance = services.getASI(asiId);
			final IAttributeStorage asiAttributeStorage = ASIAttributeStorage.createNew(attributeStorageFactory, attributeSetInstance);
			allAttributeValues.addAll(asiAttributeStorage.getAttributeValues());

			// additionally add whatever the attributeStorageFactory's storage implementation has to offer.
			final IAttributeStorage huAsiAttributeStorage = attributeStorageFactory.getAttributeStorage(attributeSetInstance);
			allAttributeValues.addAll(huAsiAttributeStorage.getAttributeValues());
		}

		final ShipmentScheduleHandler handler = services.getShipmentScheduleHandler(shipmentSchedule);

		return allAttributeValues.stream()
				.filter(IAttributeValue::isUseInASI)
				.filter(attributeValue -> !Objects.equals(attributeValue.getValue(), attributeValue.getEmptyValue())) // when comparing different shipmentScheduleWithHU instances, we want no attributes to be equal to attributes with null values
				.filter(attributeValue -> handler.attributeShallBePartOfShipmentLine(shipmentSchedule, attributeValue.getM_Attribute()))
				.collect(ImmutableList.toImmutableList());
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

	public Quantity getQtyPicked() {return pickedQty;}

	public I_C_UOM getUOM()
	{
		return getQtyPicked().getUOM();
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
		final IContextAware context = InterfaceWrapperHelper.getContextAware(inout);

		//
		// Create/Update ShipmentSchedule to Shipment Line allocation
		createUpdateShipmentLineAlloc(inout);

		//
		// Update all M_Packages that point to our top level HU (i.e. LU/TU/VHU if any)
		// and set M_InOut to them.
		final I_M_HU topLevelHU = getTopLevelHU();
		if (topLevelHU != null)
		{
			services.assignShipmentToPackages(topLevelHU, inout, context.getTrxName());
		}
		return this;
	}

	private void createUpdateShipmentLineAlloc(@NonNull final I_M_InOut inout)
	{
		// At this point we assume setM_InOutLine() method was called by API
		Check.assumeNotNull(shipmentLine, "shipmentLine not null");

		// If there is no shipment schedule allocation, create one now
		// This happens if you called the factory method that doesn't have a shipmentScheduleAlloc parameter
		if (shipmentScheduleQtyPicked == null)
		{
			final StockQtyAndUOMQty stockQtyAndCatchQty = StockQtyAndUOMQty.builder()
					.productId(ProductId.ofRepoId(shipmentSchedule.getM_Product_ID()))
					.stockQty(getQtyPicked())
					.uomQty(catchQty.orElse(null))
					.build();

			shipmentScheduleQtyPicked = services.createNewQtyPickedRecord(shipmentSchedule, stockQtyAndCatchQty);

			// lu, tu and vhu are null, so no need to set them

			if (DocStatus.ofCode(inout.getDocStatus()).isCompletedOrClosed())
			{
				// take that bit we have from the iol. might be useful to have a least the number of TUs
				final de.metas.handlingunits.model.I_M_InOutLine iol = InterfaceWrapperHelper.create(getM_InOutLine(), de.metas.handlingunits.model.I_M_InOutLine.class);
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
		services.save(shipmentScheduleQtyPicked);

		if (split != null)
		{
			split.markAsProcessed(InOutAndLineId.ofRepoId(shipmentLine.getM_InOut_ID(), shipmentLine.getM_InOutLine_ID()));
			services.save(split);
		}

		services.resetCatchQtyOverride(shipmentSchedule);
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
		return tuHU != null ? services.extractTuQty(tuHU) : BigDecimal.ZERO;
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

		final ImmutableList<I_M_HU_Item> huMaterialItems = services.getMaterialHUItems(tuOrVhu);
		if (huMaterialItems.isEmpty())
		{
			return retrievePiipForReferencedRecord();
		}

		Check.assume(huMaterialItems.size() == 1, "Each hu has just one M_HU_Item with type={}; hu={}; huMaterialItems={}", X_M_HU_Item.ITEMTYPE_Material, tuOrVhu, huMaterialItems);
		final I_M_HU_Item huMaterialItem = huMaterialItems.get(0);

		final BPartnerId bpartnerId = services.getBPartnerId(shipmentSchedule);
		final ZonedDateTime preparationDate = services.getPreparationDate(shipmentSchedule);

		final I_M_HU_PI_Item huPIItem = services.getPIItem(huMaterialItem);
		if (huPIItem == null)
		{
			return services.getVirtualPIMaterialItemProduct();
		}

		final I_M_HU_PI_Item_Product matchingPiip = services.retrievePIMaterialItemProduct(
				huPIItem,
				bpartnerId,
				getProductId(),
				preparationDate);
		if (matchingPiip != null)
		{
			return matchingPiip;
		}

		// could not find a packing instruction; return "No Packing Item"
		return services.getVirtualPIMaterialItemProduct();
	}

	private I_M_HU_PI_Item_Product retrievePiipForReferencedRecord()
	{

		final I_M_HU_PI_Item_Product piipIgnoringPickedHUs = services.getM_HU_PI_Item_Product_IgnoringPickedHUs(shipmentSchedule);
		if (piipIgnoringPickedHUs != null)
		{
			return piipIgnoringPickedHUs;
		}

		return services.getVirtualPIMaterialItemProduct();
	}

	@Nullable
	public ShipperId getShipperId()
	{
		return ShipperId.ofRepoIdOrNull(shipmentSchedule.getM_Shipper_ID());
	}

	public Dimension getDimension()
	{
		Dimension dimension = Dimension.EMPTY;
		if (split != null)
		{
			dimension = dimension.fallbackTo(split.getDimension());
		}

		dimension = dimension.fallbackTo(services.getDimension(this.shipmentSchedule));

		return dimension;
	}

	public ShipmentScheduleSplitId getSplitId() {return split != null ? split.getIdNotNull() : null;}

	public Optional<LocalDate> getDeliveryDate()
	{
		if (split != null)
		{
			return Optional.of(split.getDeliveryDate());
		}
		else
		{
			return ShipmentScheduleEffectiveBL.getDeliveryDateAsLocalDate(shipmentSchedule);
		}
	}
}
