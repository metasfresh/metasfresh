package de.metas.frontend_testing.masterdata.hu;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUContext;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.impl.AllocationUtils;
import de.metas.handlingunits.allocation.impl.HUListAllocationSourceDestination;
import de.metas.handlingunits.allocation.impl.HULoader;
import de.metas.handlingunits.allocation.transfer.impl.LUTUProducerDestination;
import de.metas.handlingunits.hutransaction.IHUTrxBL;
import de.metas.handlingunits.inventory.CreateVirtualInventoryWithQtyReq;
import de.metas.handlingunits.inventory.InventoryService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.service.HUQRCodesService;
import de.metas.handlingunits.storage.IHUProductStorage;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetInstanceId;
import org.adempiere.service.ClientId;
import org.adempiere.warehouse.WarehouseId;
import org.compiere.model.I_C_UOM;

import javax.annotation.Nullable;
import java.math.BigDecimal;

public class CreateHUCommand
{
	@NonNull private final ITrxManager trxManager = Services.get(ITrxManager.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IHUTrxBL huTrxBL = Services.get(IHUTrxBL.class);
	@NonNull private final InventoryService inventoryService;
	@NonNull private final HUQRCodesService huQRCodesService;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateHURequest request;
	@NonNull private final Identifier identifier;

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

		context.putIdentifier(identifier, huId);
		final HUQRCode huQRCode = huQRCodesService.getQRCodeByHuId(huId);

		return JsonCreateHUResponse.builder()
				.huId(String.valueOf(huId.getRepoId()))
				.qrCode(huQRCode.toGlobalQRCodeString())
				.build();
	}

	private @NonNull HuId createCU()
	{
		final WarehouseId warehouseId = context.getId(request.getWarehouse(), WarehouseId.class);
		final ProductId productId = context.getId(request.getProduct(), ProductId.class);
		final I_C_UOM uom = productBL.getStockUOM(productId);

		return trxManager.callInThreadInheritedTrx(
				() -> inventoryService.createInventoryForMissingQty(
						CreateVirtualInventoryWithQtyReq.builder()
								.clientId(ClientId.METASFRESH)
								.orgId(MasterdataContext.ORG_ID)
								.warehouseId(warehouseId)
								.productId(productId)
								.qty(Quantity.of(computeQtyCUs(), uom))
								.movementDate(SystemTime.asZonedDateTime())
								.attributeSetInstanceId(AttributeSetInstanceId.NONE)
								.build()
				)
		);
	}

	private BigDecimal computeQtyCUs()
	{
		if (request.getPackingInstructions() != null)
		{
			if (request.getQty() != null)
			{
				throw new AdempiereException("qty shall not be set when packingInstructions are set");
			}
			final PackingInstructions packingInstructions = context.getObjectNotNull(request.getPackingInstructions());
			return packingInstructions.getQtyCUs();
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
		final BigDecimal qtyCUsPerTU = packingInstructions.getQtyCUsPerTU();
		producer.setTUPI(tuPI);
		producer.addCUPerTU(productId, qtyCUsPerTU, uom);

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
						.setQuantity(Quantity.of(packingInstructions.getQtyCUs(), uom))
						.setDateAsToday()
						.setForceQtyAllocation(true)
						.create());

		final I_M_HU newLU = producer.getSingleCreatedHU().orElseThrow(() -> new AdempiereException("No LU was created"));
		return HuId.ofRepoId(newLU.getM_HU_ID());
	}

}
