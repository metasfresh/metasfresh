package de.metas.manufacturing.job.service.commands;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.IHUStatusBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.attribute.HUAttributeConstants;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.handlingunits.storage.IHUStorage;
import de.metas.i18n.AdMessageKey;
import de.metas.manufacturing.job.model.ReceivingTarget;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewTUTarget;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.product.ProductId;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReceiveGoodsCommand
{
	private static final AdMessageKey MSG_ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED = AdMessageKey.of("de.metas.manufacturing.job.service.commands.ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED");
	private static final AdMessageKey MSG_MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED = AdMessageKey.of("de.metas.manufacturing.job.service.commands.MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED");
	private static final AdMessageKey MSG_NOT_ACTIVE_HU = AdMessageKey.of("de.metas.manufacturing.job.service.commands.NOT_ACTIVE_HU");

	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IUOMConversionBL uomConversionBL;
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;
	private final IHUStatusBL huStatusBL = Services.get(IHUStatusBL.class);

	//
	// Parameters
	@NonNull private final PPOrderId ppOrderId;
	@Nullable private final PPOrderBOMLineId coProductBOMLineId;
	@NonNull private final SelectedReceivingTarget receivingTarget;
	@NonNull private final BigDecimal qtyToReceiveBD;
	@NonNull private final ZonedDateTime date;
	@Nullable private final LocalDate bestBeforeDate;
	@Nullable private final LocalDate productionDate;
	@Nullable private final String lotNo;
	@Nullable private final Quantity catchWeight;
	@Nullable private final String barcode;

	//
	// State
	private I_PP_Order _ppOrder; // lazy
	private I_PP_Order_BOMLine _coProductLine; // lazy
	private final ArrayList<I_M_HU> receivedHUs = new ArrayList<>();

	@Builder
	private ReceiveGoodsCommand(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final ReceiveGoodsRequest request)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.uomConversionBL = uomConversionBL;
		this.ppOrderBL = ppOrderBL;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.ppOrderId = request.getPpOrderId();
		this.coProductBOMLineId = request.getCoProductBOMLineId();
		this.receivingTarget = request.getReceivingTarget();
		this.qtyToReceiveBD = request.getQtyToReceiveBD();
		this.date = request.getDate();
		this.bestBeforeDate = request.getBestBeforeDate();
		this.productionDate = request.getProductionDate();
		this.lotNo = StringUtils.trimBlankToNull(request.getLotNo());
		this.catchWeight = request.getCatchWeight();
		this.barcode = StringUtils.trimBlankToNull(request.getBarcode());
	}

	@Nullable
	public ReceiveGoodsResult execute()
	{
		@Nullable final ReceivingTarget receivingTarget;
		if (this.receivingTarget.getReceiveToNewLU() != null)
		{
			receivingTarget = receiveToNewLU(this.receivingTarget.getReceiveToNewLU());
		}
		else if (this.receivingTarget.getReceiveToQRCode() != null)
		{
			receivingTarget = receiveToQRCode(this.receivingTarget.getReceiveToQRCode());
		}
		else if (this.receivingTarget.getReceiveToNewTU() != null)
		{
			receivingTarget = receiveToNewTU(this.receivingTarget.getReceiveToNewTU());
		}
		else
		{
			throw new AdempiereException("Unhandled target: " + this.receivingTarget);
		}

		saveReceivingTargetForLaterUse(receivingTarget);
		setCatchWeightForReceivedHUs();

		return ReceiveGoodsResult.builder()
				.totalQtyReceived(getTotalQtyReceived())
				.receivingTarget(receivingTarget)
				.build();
	}

	@Nullable
	private ReceivingTarget receiveToQRCode(@NonNull final JsonHUQRCodeTarget qrCodeTarget)
	{
		final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(qrCodeTarget.getHuQRCode().getCode());
		final HuId existingHUId = loadingAndSavingSupportServices.getHuIdByQRCodeIfExists(qrCode).orElse(null);
		if (existingHUId == null)
		{
			final HUQRCodePackingInfo packingInfo = qrCode.getPackingInfo();
			final @NonNull HUQRCodeUnitType huUnitType = packingInfo.getHuUnitType();
			if (HUQRCodeUnitType.TU.equals(huUnitType))
			{
				final HuId tuId = receiveSingleTU(packingInfo.getPackingInstructionsId());

				loadingAndSavingSupportServices.assignQRCodeForReceiptHU(qrCode, tuId);

				if (barcode != null)
				{
					return ReceivingTarget.ofExistingTUId(tuId);
				}
				else
				{
					// NOTE: returning null because this receiving target is not re-usable
					// i.e. receiving again to this HU is not supported
					// (backwards compatible)
					return null;
				}
			}
			else
			{
				throw new AdempiereException("HU Unit Type is not supported: " + huUnitType);
			}
		}
		else
		{
			final I_M_HU existingHU = handlingUnitsBL.getById(existingHUId);
			assertActive(existingHU);

			if (handlingUnitsBL.isLoadingUnit(existingHU))
			{
				return receiveToExistingLU(existingHU, qrCodeTarget.getTuPIItemProductId());
			}
			else if (handlingUnitsBL.isTransportUnit(existingHU))
			{
				return receiveToExistingTU(existingHU);
			}
			else
			{
				throw new AdempiereException(MSG_ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED);
			}
		}
	}

	private ReceivingTarget receiveToNewLU(@NonNull final JsonNewLUTarget newLUTarget)
	{
		final Quantity qtyToReceive = getQtyToReceive();
		final List<I_M_HU> lus = createHUProducer().receiveLUs(qtyToReceive, newLUTarget.getTuPIItemProductId(), newLUTarget.getLuPIItemId());
		collectReceivedHUs(lus);
		if (lus.size() == 1)
		{
			setQRCodeAttribute(lus.get(0));
		}

		//
		// Case: we received 1 item, usually that's receiving by scanning a QR code
		// => keep the same LU
		if (lus.size() == 1 && qtyToReceive.isOne())
		{
			return ReceivingTarget.ofExistingLU(lus.get(0), newLUTarget.getTuPIItemProductId());
		}
		//
		// Else, because we are also respecting the qty TUs per LU, it's better to clean up the target after receiving
		else
		{
			return null;
		}
	}

	private ReceivingTarget receiveToExistingLU(
			@NonNull final I_M_HU existingLU,
			@Nullable final HUPIItemProductId suggestedTUPIItemProductId)
	{
		assertMixingDifferentProductsAllowed(existingLU);

		HUPIItemProductId tuPIItemProductId = suggestedTUPIItemProductId;
		if (tuPIItemProductId == null)
		{
			final ReceivingTarget previousReceivingTarget = getPreviousReceivingTarget();
			tuPIItemProductId = previousReceivingTarget != null ? previousReceivingTarget.getTuPIItemProductId() : null;
		}
		if (tuPIItemProductId == null)
		{
			throw new AdempiereException("No CU-TU association defined");
		}

		final List<I_M_HU> tusOrVhus = receiveTUs(tuPIItemProductId);
		for (final I_M_HU tu : tusOrVhus)
		{
			HUTransformService.newInstance().tuToExistingLU(tu, QtyTU.ONE, existingLU);
		}

		return ReceivingTarget.ofExistingLU(existingLU, tuPIItemProductId);
	}

	private ReceivingTarget receiveToExistingTU(@NonNull final I_M_HU existingTU)
	{
		assertMixingDifferentProductsAllowed(existingTU);

		final I_M_HU vhu = retrieveSingleVHU();

		HUTransformService.newInstance().cusToExistingTU(ImmutableList.of(vhu), existingTU);
		return ReceivingTarget.ofExistingTU(existingTU);
	}

	private void assertMixingDifferentProductsAllowed(@NotNull final I_M_HU targetHU)
	{
		if (isCoProduct())
		{
			final IHUStorage huStorage = handlingUnitsBL.getStorageFactory().getStorage(targetHU);
			if (!huStorage.isEmptyOrSingleProductStorageMatching(getProductId()))
			{
				throw new AdempiereException(MSG_MIXING_DIFFERENT_PRODUCTS_NOT_ALLOWED);
			}
		}
	}

	private void assertActive(@NotNull final I_M_HU targetHU)
	{
		if (!huStatusBL.isStatusActive(targetHU))
		{
			throw new AdempiereException(MSG_NOT_ACTIVE_HU);
		}
	}

	private I_PP_Order getPPOrder()
	{
		I_PP_Order ppOrder = this._ppOrder;
		if (ppOrder == null)
		{
			ppOrder = this._ppOrder = ppOrderBL.getById(ppOrderId);
		}
		return ppOrder;
	}

	@Nullable
	private I_PP_Order_BOMLine getCOProductLine()
	{
		if (coProductBOMLineId != null)
		{
			I_PP_Order_BOMLine coProductLine = this._coProductLine;
			if (coProductLine == null)
			{
				coProductLine = this._coProductLine = ppOrderBOMBL.getOrderBOMLineById(coProductBOMLineId);
			}
			return coProductLine;
		}
		else
		{
			return null;
		}
	}

	private void clearLoadedData()
	{
		this._coProductLine = null;
		this._ppOrder = null;
	}

	private IPPOrderReceiptHUProducer createHUProducer()
	{
		final IPPOrderReceiptHUProducer huProducer;
		final LocatorId locatorId;

		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			locatorId = LocatorId.ofRepoId(coProductLine.getM_Warehouse_ID(), coProductLine.getM_Locator_ID());
			huProducer = ppOrderBL.receivingByOrCoProduct(PPOrderBOMLineId.ofRepoId(coProductLine.getPP_Order_BOMLine_ID()));
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			locatorId = LocatorId.ofRepoId(ppOrder.getM_Warehouse_ID(), ppOrder.getM_Locator_ID());
			huProducer = ppOrderBL.receivingMainProduct(ppOrderId);
		}

		StringUtils.trimBlankToOptional(lotNo).ifPresent(huProducer::lotNumber);
		Optional.ofNullable(bestBeforeDate).ifPresent(huProducer::bestBeforeDate);
		Optional.ofNullable(productionDate).ifPresent(huProducer::productionDate);

		return huProducer
				.movementDate(date)
				.locatorId(locatorId);
	}

	private Quantity getQtyToReceive()
	{
		final UomId uomId;
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			uomId = UomId.ofRepoId(coProductLine.getC_UOM_ID());
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			uomId = UomId.ofRepoId(ppOrder.getC_UOM_ID());
		}

		return Quantitys.of(qtyToReceiveBD, uomId);
	}

	private ReceivingTarget getPreviousReceivingTarget()
	{
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		return coProductLine != null
				? ManufacturingJobLoaderAndSaver.extractReceivingTarget(coProductLine)
				: ManufacturingJobLoaderAndSaver.extractReceivingTarget(getPPOrder());
	}

	private void saveReceivingTargetForLaterUse(@Nullable final ReceivingTarget receivingTarget)
	{
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			ManufacturingJobLoaderAndSaver.updateRecordFromReceivingTarget(coProductLine, receivingTarget);
			ppOrderBOMBL.save(coProductLine);
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			ManufacturingJobLoaderAndSaver.updateRecordFromReceivingTarget(ppOrder, receivingTarget);
			ppOrderBL.save(ppOrder);
		}
	}

	private ReceivingTarget receiveToNewTU(@NonNull final JsonNewTUTarget newTUTarget)
	{
		final HUPIItemProductId tuPIItemProductId = newTUTarget.getTuPIItemProductId();
		final List<I_M_HU> hus = receiveTUs(tuPIItemProductId);

		if (barcode != null && hus.size() == 1)
		{
			return ReceivingTarget.ofExistingTU(hus.get(0));
		}
		else
		{
			return null;
		}
	}

	@NonNull
	private List<I_M_HU> receiveTUs(@NonNull final HUPIItemProductId tuPIItemProductId)
	{
		final List<I_M_HU> hus = createHUProducer().receiveTUs(getQtyToReceive(), tuPIItemProductId);
		collectReceivedHUs(hus);

		if (hus.size() == 1)
		{
			setQRCodeAttribute(hus.get(0));
		}

		return hus;
	}

	@NonNull
	private HuId receiveSingleTU(@NonNull final HuPackingInstructionsId tuPackingInstructionsId)
	{
		final I_M_HU tu = createHUProducer().receiveSingleTU(getQtyToReceive(), tuPackingInstructionsId);
		collectReceivedHU(tu);
		setQRCodeAttribute(tu);

		return HuId.ofRepoId(tu.getM_HU_ID());
	}

	@NonNull
	private I_M_HU retrieveSingleVHU()
	{
		final I_M_HU vhu = createHUProducer().receiveVHU(getQtyToReceive());
		collectReceivedHU(vhu);
		setQRCodeAttribute(vhu);

		return vhu;
	}

	private void collectReceivedHU(@NonNull final I_M_HU hu)
	{
		this.receivedHUs.add(hu);
	}

	private void collectReceivedHUs(@NonNull final List<I_M_HU> hus)
	{
		this.receivedHUs.addAll(hus);
	}

	private void setCatchWeightForReceivedHUs()
	{
		if (catchWeight == null)
		{
			return;
		}

		if (receivedHUs.isEmpty())
		{
			//shouldn't happen
			throw new AdempiereException("No HUs have been received!");
		}

		final PackedHUWeightNetUpdater weightUpdater = new PackedHUWeightNetUpdater(uomConversionBL,
				handlingUnitsBL.createMutableHUContextForProcessing(),
				getProductId(),
				catchWeight);

		weightUpdater.updatePackToHUs(receivedHUs);
	}

	@NonNull
	private ProductId getProductId()
	{
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			return ProductId.ofRepoId(coProductLine.getM_Product_ID());
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			return ProductId.ofRepoId(ppOrder.getM_Product_ID());
		}
	}

	@NonNull
	private Quantity getTotalQtyReceived()
	{
		clearLoadedData();
		final I_PP_Order_BOMLine ppOrderBomLine = getCOProductLine();
		if (ppOrderBomLine != null)
		{
			return loadingAndSavingSupportServices.getQuantities(ppOrderBomLine).getQtyIssuedOrReceived().negate();
		}

		return loadingAndSavingSupportServices.getQuantities(getPPOrder()).getQtyReceived();
	}

	private boolean isCoProduct()
	{
		return getCOProductLine() != null;
	}

	private void setQRCodeAttribute(@NonNull final I_M_HU hu)
	{
		if (barcode == null) {return;}

		final IAttributeStorage huAttributes = handlingUnitsBL.getAttributeStorage(hu);
		if (!huAttributes.hasAttribute(HUAttributeConstants.ATTR_QRCode)) {return;}

		huAttributes.setSaveOnChange(true);
		huAttributes.setValue(HUAttributeConstants.ATTR_QRCode, barcode);
	}
}
