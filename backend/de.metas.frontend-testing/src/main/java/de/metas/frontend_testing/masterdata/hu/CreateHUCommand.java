package de.metas.frontend_testing.masterdata.hu;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.CoalesceUtil;
import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.attribute.weightable.IWeightable;
import de.metas.handlingunits.attribute.weightable.Weightables;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Capacity;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public class CreateHUCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHandlingUnitsDAO handlingUnitsDAO = Services.get(IHandlingUnitsDAO.class);
	@NonNull private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateHURequest request;
	@NonNull private final Identifier identifier;

	private ProductId _productId;
	private WarehouseId _warehouseId;

	@Builder
	private CreateHUCommand(
			@NonNull final InventoryService inventoryService,
			@NonNull final HUQRCodesService huQRCodesService,
			@NonNull final MasterdataContext context,
			@NonNull final JsonCreateHURequest request,
			@Nullable final String identifier)
	{
		this.inventoryService = inventoryService;
		this.huQRCodesService = huQRCodesService;
		this.context = context;
		this.request = request;

		this.identifier = Identifier.ofNullableStringOrUnique(identifier, "HU");
	}

	public JsonCreateHUResponse execute()
	{
		trxManager.assertThreadInheritedTrxNotExists();

		final HuId cuId = createCU();
		final HuId huId = transformCU(cuId);
		addAdditionalProducts(huId);
		final IAttributeStorage huAttributes = updateAttributes(huId);

		context.putIdentifier(identifier, huId);

		final String huQRCodeStr;
		if (request.isGenerateHUQRCode())
		{
			huQRCodeStr = huQRCodesService.getQRCodeByHuId(huId).toGlobalQRCodeString();
		}
		else
		{
			huQRCodeStr = null;
		}

		return JsonCreateHUResponse.builder()
				.huId(String.valueOf(huId.getRepoId()))
				.qrCode(huQRCodeStr)
				.productId(getProductId())
				.warehouseId(getWarehouseId())
				.externalBarcode(huAttributes != null && huAttributes.hasAttribute(AttributeConstants.ATTR_ExternalBarcode) ? huAttributes.getValueAsString(AttributeConstants.ATTR_ExternalBarcode) : null)
				.tus(getIncludedTUs(huId))
				.build();
	}

	/**
	 * When this HU was created as an LU with included TUs (request's packingInstructions has an
	 * {@code luPIItem}), also resolve the included, individually-addressable TUs' own QR codes, so a
	 * caller can scan one of them directly (e.g. to reach its per-TU alternative step) without a
	 * separate lookup endpoint. Additive only — the top-level {@code qrCode} field keeps returning
	 * the LU's own QR code.
	 * <p>
	 * An included row that is itself an <b>aggregate HU</b> (one DB row standing in for several
	 * identical, exactly-capacity-filled TUs — {@code LUTUProducerDestination}/{@code
	 * TUProducerDestination} coalesce same-content TUs this way whenever a TU is loaded to exactly
	 * its rated capacity) is skipped: {@link HUQRCodesService#getQRCodeByHuId} would otherwise try to
	 * generate one QR code per aggregated TU count and throw
	 * ("Expected only one QR code ... but found [...]"), since an aggregate row has no single QR.
	 * There is also nothing useful to scan there individually — same as a loading unit, an aggregate
	 * HU is not a real single-TU write-off source.
	 */
	private ImmutableList<JsonCreateHUResponse.Tu> getIncludedTUs(final HuId huId)
	{
		if (!request.isGenerateHUQRCode() || request.getPackingInstructions() == null)
		{
			return ImmutableList.of();
		}

		final PackingInstructions packingInstructions = context.getObjectNotNull(request.getPackingInstructions());
		if (packingInstructions.getLuPIItem() == null)
		{
			return ImmutableList.of();
		}

		return handlingUnitsDAO.retrieveIncludedHUs(huId)
				.stream()
				.filter(tu -> !handlingUnitsBL.isAggregateHU(tu))
				.map(tu -> {
					final HuId tuId = HuId.ofRepoId(tu.getM_HU_ID());
					return JsonCreateHUResponse.Tu.builder()
							.huId(String.valueOf(tuId.getRepoId()))
							.qrCode(huQRCodesService.getQRCodeByHuId(tuId).toGlobalQRCodeString())
							.build();
				})
				.collect(ImmutableList.toImmutableList());
	}

	private @NonNull HuId createCU()
	{
		final WarehouseId warehouseId = getWarehouseId();
		final ProductId productId = getProductId();
		final I_C_UOM uom = productBL.getStockUOM(productId);

		return trxManager.callInThreadInheritedTrx(
				() -> inventoryService.createInventoryForMissingQty(
						CreateVirtualInventoryWithQtyReq.builder()
								.clientId(ClientId.METASFRESH)
								.orgId(MasterdataContext.ORG_ID)
								.warehouseId(warehouseId)
								.productId(productId)
								.qty(Quantity.of(getTotalQtyCUs(), uom))
								.movementDate(SystemTime.asZonedDateTime())
								.attributeSetInstanceId(AttributeSetInstanceId.NONE)
								.build()
				)
		);
	}

	@NonNull
	private ProductId getProductId()
	{
		ProductId productId = this._productId;
		if (productId == null)
		{
			final Identifier productIdentifier = request.getProduct();
			productId = this._productId = productIdentifier != null
					? context.getId(productIdentifier, ProductId.class)
					: context.getIdOfType(ProductId.class);
		}
		return productId;
	}

	@NonNull
	private WarehouseId getWarehouseId()
	{
		WarehouseId warehouseId = this._warehouseId;
		if (warehouseId == null)
		{
			final Identifier warehouseIdentifier = request.getWarehouse();
			warehouseId = this._warehouseId = warehouseIdentifier != null
					? context.getId(warehouseIdentifier, WarehouseId.class)
					: context.getIdOfType(WarehouseId.class);
		}
		return warehouseId;
	}

	private BigDecimal getTotalQtyCUs()
	{
		if (request.getPackingInstructions() != null)
		{
			final PackingInstructions packingInstructions = context.getObjectNotNull(request.getPackingInstructions());
			if (packingInstructions.isInfiniteCapacity())
			{
				return Check.assumeNotNull(request.getQty(), "qty shall be set when packingInstructions has infinite capacity");
			}
			else
			{
				// An explicit qty together with finite-capacity packingInstructions means: load this
				// total across the LU's TUs, under-filling the last one (it is NOT rejected as it used
				// to be) — this is what lets a masterdata request force a real, individually
				// addressable (non-aggregate) TU into existence for scanning (see getIncludedTUs).
				// Absent qty keeps the old behaviour: exact fill, derived from the packing instructions.
				return CoalesceUtil.coalesce(request.getQty(), packingInstructions.getQtyCUs());
			}
		}
		else
		{
			return Check.assumeNotNull(request.getQty(), "qty shall be set when packingInstructions are not set");
		}
	}

	private HuId transformCU(HuId cuId)
	{
		if (request.getPackingInstructions() != null)
		{
			final PackingInstructions packingInstructions = context.getObjectNotNull(request.getPackingInstructions());
			return transformCU(packingInstructions, cuId);
		}
		else
		{
			return cuId;
		}
	}

	private HuId transformCU(final PackingInstructions packingInstructions, HuId sourceCUId)
	{
		return huTrxBL.process(huContext -> {
			return transformCU0(packingInstructions, sourceCUId, huContext);
		});
	}

	private HuId transformCU0(final PackingInstructions packingInstructions, HuId sourceCUId, final IHUContext huContext)
	{
		final I_M_HU sourceCU = handlingUnitsBL.getById(sourceCUId);
		final IHUProductStorage sourceCUProductStorage = handlingUnitsBL.getSingleHUProductStorage(sourceCU);
		final ProductId productId = sourceCUProductStorage.getProductId();
		final I_C_UOM uom = sourceCUProductStorage.getC_UOM();

		final LUTUProducerDestination producer = new LUTUProducerDestination();

		producer.setHUStatus(sourceCU.getHUStatus());
		producer.setLocatorId(IHandlingUnitsBL.extractLocatorId(sourceCU));
		final BPartnerLocationId bpartnerLocationId = IHandlingUnitsBL.extractBPartnerLocationIdOrNull(sourceCU);
		if (bpartnerLocationId != null)
		{
			producer.setBPartnerAndLocationId(bpartnerLocationId);
		}

		final I_M_HU_PI tuPI = packingInstructions.getTuPI();
		producer.setTUPI(tuPI);
		if (packingInstructions.isInfiniteCapacity())
		{
			producer.addCUPerTU(Capacity.createInfiniteCapacity(productId, uom));
		}
		else
		{
			final BigDecimal qtyCUsPerTU = packingInstructions.getQtyCUsPerTUNotNull();
			producer.addCUPerTU(productId, qtyCUsPerTU, uom);
		}

		final I_M_HU_PI_Item luPIItem = packingInstructions.getLuPIItem();
		if (luPIItem != null)
		{
			final QtyTU qtyTUs = Check.assumeNotNull(packingInstructions.getQtyTUs(), "qtyTUs must be set when luPIItem is set");
			producer.setMaxTUsPerLU(qtyTUs.toInt());
			producer.setLUItemPI(luPIItem);
			producer.setLUPI(luPIItem.getM_HU_PI_Version().getM_HU_PI());
			producer.setCreateTUsForRemainingQty(false);
			producer.setMaxLUs(1);
		}
		else
		{
			producer.setNoLU();
		}

		HULoader.builder()
				.source(HUListAllocationSourceDestination.of(sourceCU))
				.destination(producer)
				.load(AllocationUtils.builder()
						.setHUContext(huContext)
						.setProduct(productId)
						.setQuantity(Quantity.of(getTotalQtyCUs(), uom))
						.setDateAsToday()
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU newLU = producer.getSingleCreatedHU().orElseThrow(() -> new AdempiereException("No LU was created"));
		return HuId.ofRepoId(newLU.getM_HU_ID());
	}

	/**
	 * Stocks each of {@link JsonCreateHURequest#getAdditionalProducts()} onto the already-created {@code targetHuId},
	 * on top of its primary product — i.e. makes the HU carry storage of more than one distinct product.
	 * <p>
	 * Implemented the same way {@link de.metas.handlingunits.allocation.transfer.impl.HUDistributeBuilder} distributes
	 * a VHU's content onto an existing TU: create a fresh virtual CU for the additional product, then
	 * {@link HULoader} it directly onto {@code targetHuId} (an existing HU used as {@code destination}, not a
	 * producer that would create a new one).
	 */
	private void addAdditionalProducts(final HuId targetHuId)
	{
		final List<JsonCreateHURequest.AdditionalProduct> additionalProducts = request.getAdditionalProducts();
		if (additionalProducts == null || additionalProducts.isEmpty())
		{
			return;
		}

		additionalProducts.forEach(additionalProduct -> addAdditionalProduct(targetHuId, additionalProduct));
	}

	private void addAdditionalProduct(final HuId targetHuId, final JsonCreateHURequest.AdditionalProduct additionalProduct)
	{
		final WarehouseId warehouseId = getWarehouseId();
		final ProductId additionalProductId = context.getId(additionalProduct.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(additionalProductId);
		final Quantity qty = Quantity.of(additionalProduct.getQty(), uom);

		final HuId sourceCuId = trxManager.callInThreadInheritedTrx(
				() -> inventoryService.createInventoryForMissingQty(
						CreateVirtualInventoryWithQtyReq.builder()
								.clientId(ClientId.METASFRESH)
								.orgId(MasterdataContext.ORG_ID)
								.warehouseId(warehouseId)
								.productId(additionalProductId)
								.qty(qty)
								.movementDate(SystemTime.asZonedDateTime())
								.attributeSetInstanceId(AttributeSetInstanceId.NONE)
								.build()
				)
		);

		huTrxBL.process(huContext -> {
			final I_M_HU sourceCU = handlingUnitsBL.getById(sourceCuId);
			final I_M_HU targetHu = handlingUnitsBL.getById(targetHuId);

			HULoader.builder()
					.source(HUListAllocationSourceDestination.of(sourceCU))
					.destination(HUListAllocationSourceDestination.of(targetHu))
					.load(AllocationUtils.builder()
							.setHUContext(huContext)
							.setProduct(additionalProductId)
							.setQuantity(qty)
							.setDateAsToday()
							.setForceQtyAllocation(true)
							.create());

			handlingUnitsBL.destroyIfEmptyStorage(huContext, sourceCU);
		});
	}

	private IAttributeStorage updateAttributes(final HuId huId)
	{
		final BigDecimal weightNet = request.getWeightNet();
		final String lotNo = request.getLotNo();
		final LocalDate bestBeforeDate = request.getBestBeforeDate() != null
				? LocalDate.parse(request.getBestBeforeDate())
				: null;
		final String externalBarcode = request.getExternalBarcode();

		if (CoalesceUtil.countNotNulls(weightNet, lotNo, bestBeforeDate, externalBarcode) <= 0)
		{
			return null;
		}

		final IAttributeStorage huAttributes = handlingUnitsBL.getAttributeStorage(huId);
		huAttributes.setSaveOnChange(true);

		if (weightNet != null)
		{
			final IWeightable weightable = Weightables.wrap(huAttributes);
			weightable.setWeightNet(weightNet);
		}

		if (lotNo != null)
		{
			huAttributes.setValue(AttributeConstants.ATTR_LotNumber, lotNo);
		}

		if (bestBeforeDate != null)
		{
			huAttributes.setValue(AttributeConstants.ATTR_BestBeforeDate, bestBeforeDate);
		}

		if (externalBarcode != null)
		{
			huAttributes.setValue(AttributeConstants.ATTR_ExternalBarcode, externalBarcode);
		}

		return huAttributes;
	}

}
