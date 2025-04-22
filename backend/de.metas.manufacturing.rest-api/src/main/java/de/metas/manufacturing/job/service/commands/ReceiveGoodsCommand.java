package de.metas.manufacturing.job.service.commands;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHUPIItemProductBL;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.allocation.transfer.LUTUResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.picking.candidate.commands.PackedHUWeightNetUpdater;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
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
import de.metas.util.Check;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.warehouse.LocatorId;
import org.eevolution.api.PPOrderBOMLineId;
import org.eevolution.api.PPOrderId;
import org.eevolution.model.I_PP_Order;
import org.eevolution.model.I_PP_Order_BOMLine;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class ReceiveGoodsCommand
{
	private static final AdMessageKey ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED = AdMessageKey.of("de.metas.manufacturing.job.service.commands.ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED");

	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IUOMConversionBL uomConversionBL;
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final IHUPIItemProductBL huPIItemProductBL;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	//
	// Parameters
	final @NonNull PPOrderId ppOrderId;
	final @Nullable PPOrderBOMLineId coProductBOMLineId;
	final @NonNull SelectedReceivingTarget receivingTarget;
	final @NonNull BigDecimal qtyToReceiveBD;
	final @NonNull ZonedDateTime date;
	final @Nullable LocalDate bestBeforeDate;
	final @Nullable String lotNo;
	final @Nullable Quantity catchWeight;

	//
	// State
	private I_PP_Order _ppOrder; // lazy
	private I_PP_Order_BOMLine _coProductLine;
	private final List<I_M_HU> receivedHUs = new ArrayList<>();

	@Builder
	private ReceiveGoodsCommand(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IUOMConversionBL uomConversionBL,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final IHUPIItemProductBL huPIItemProductBL,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final ReceiveGoodsRequest request)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.uomConversionBL = uomConversionBL;
		this.ppOrderBL = ppOrderBL;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.huPIItemProductBL = huPIItemProductBL;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.ppOrderId = request.getPpOrderId();
		this.coProductBOMLineId = request.getCoProductBOMLineId();
		this.receivingTarget = request.getReceivingTarget();
		this.qtyToReceiveBD = request.getQtyToReceiveBD();
		this.date = request.getDate();
		this.bestBeforeDate = request.getBestBeforeDate();
		this.lotNo = request.getLotNo();
		this.catchWeight = request.getCatchWeight();
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
			receivingTarget = receiveByQRCode(this.receivingTarget.getReceiveToQRCode());
		}
		else if (this.receivingTarget.getReceiveToNewTU() != null)
		{
			receiveToNewTU(this.receivingTarget.getReceiveToNewTU());
			receivingTarget = null;
		}
		else
		{
			throw new AdempiereException("Unhandled target: " + this.receivingTarget);
		}

		saveReceivingHUForLaterUse(receivingTarget);
		setCatchWeightForReceivedHUs();

		return ReceiveGoodsResult.builder()
				.totalQtyReceived(getTotalQtyReceived())
				.receivingTarget(receivingTarget)
				.build();
	}

	@Nullable
	private ReceivingTarget receiveByQRCode(@NonNull final JsonHUQRCodeTarget qrCodeTarget)
	{
		final HUQRCode qrCode = HUQRCode.fromGlobalQRCodeJsonString(qrCodeTarget.getHuQRCode().getCode());
		final HuId existingHUId = loadingAndSavingSupportServices.getHuIdByQRCodeIfExists(qrCode).orElse(null);
		if (existingHUId == null)
		{
			final HUQRCodePackingInfo packingInfo = qrCode.getPackingInfo();
			final @NonNull HUQRCodeUnitType huUnitType = packingInfo.getHuUnitType();
			if (HUQRCodeUnitType.TU.equals(huUnitType))
			{
				final I_M_HU tu = createHUProducer().receiveSingleTU(getQtyToReceive(null),
																	 packingInfo.getPackingInstructionsId());
				collectReceivedHU(tu);
				final HuId tuId = HuId.ofRepoId(tu.getM_HU_ID());

				loadingAndSavingSupportServices.assignQRCodeForReceiptHU(qrCode, tuId);

				// NOTE: returning null because this receiving target is not re-usable
				// i.e. receiving again to this HU is not supported
				//return ReceivingTarget.builder().tuId(tuId).build();
				return null;
			}
			else
			{
				throw new AdempiereException("HU Unit Type is not supported: " + huUnitType);
			}
		}
		else
		{
			final I_M_HU existingHU = handlingUnitsBL.getById(existingHUId);
			if (handlingUnitsBL.isLoadingUnit(existingHU))
			{
				return receiveToExistingLU(existingHU, qrCodeTarget.getTuPIItemProductId());
			}
			else
			{
				throw new AdempiereException(ONLY_RECEIVE_TO_EXISTING_LU_IS_SUPPORTED);
			}
		}
	}

	private ReceivingTarget receiveToNewLU(@NonNull final JsonNewLUTarget newLUTarget)
	{
		final I_M_HU_PI_Item newLUPIItem = handlingUnitsBL.getPackingInstructionItemById(newLUTarget.getLuPIItemId());

		final HUPIItemProductId tuPIItemProductId = newLUTarget.getTuPIItemProductId();
		final List<I_M_HU> tusOrVhus = receiveTUs(tuPIItemProductId);
		final HuId luId = addTUsToLU(tusOrVhus, null, newLUPIItem);

		return ReceivingTarget.builder()
				.luId(luId)
				.tuPIItemProductId(tuPIItemProductId)
				.build();
	}

	private ReceivingTarget receiveToExistingLU(
			@NonNull final I_M_HU existingLU,
			@Nullable final HUPIItemProductId suggestedTUPIItemProductId)
	{
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
		final HuId luId = addTUsToLU(tusOrVhus, existingLU, null);
		return ReceivingTarget.builder()
				.luId(luId)
				.tuPIItemProductId(tuPIItemProductId)
				.build();
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

		if (Check.isNotBlank(lotNo))
		{
			huProducer.lotNumber(lotNo);
		}

		if (bestBeforeDate != null)
		{
			huProducer.bestBeforeDate(bestBeforeDate);
		}

		return huProducer
				.movementDate(date)
				.locatorId(locatorId);
	}

	private Quantity getQtyToReceive(@Nullable final HUPIItemProductId tuPIItemProductId)
	{
		if (catchWeight != null && tuPIItemProductId != null)
		{
			return huPIItemProductBL.getById(tuPIItemProductId).getQtyCUsPerTU();
		}

		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			final UomId uomId = UomId.ofRepoId(coProductLine.getC_UOM_ID());
			return Quantitys.of(qtyToReceiveBD, uomId);
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			final UomId uomId = UomId.ofRepoId(ppOrder.getC_UOM_ID());
			return Quantitys.of(qtyToReceiveBD, uomId);
		}
	}

	private ReceivingTarget getPreviousReceivingTarget()
	{
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		return coProductLine != null
				? ManufacturingJobLoaderAndSaver.extractReceivingTarget(coProductLine)
				: ManufacturingJobLoaderAndSaver.extractReceivingTarget(getPPOrder());
	}

	private void saveReceivingHUForLaterUse(@Nullable final ReceivingTarget receivingTarget)
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

	private HuId addTUsToLU(
			@NonNull final List<I_M_HU> tusOrVhus,
			@Nullable final I_M_HU existingLU,
			@Nullable final I_M_HU_PI_Item newLUPIItem)
	{
		I_M_HU lu = existingLU;

		for (final I_M_HU tu : tusOrVhus)
		{
			if (lu == null)
			{
				final LUTUResult createdLUs = HUTransformService.newInstance()
						.tuToNewLUs(
								tu,
								QtyTU.ONE,
								Objects.requireNonNull(newLUPIItem),
								false);
				lu = createdLUs.getSingleLURecord();
			}
			else
			{
				HUTransformService.newInstance().tuToExistingLU(tu, QtyTU.ONE, lu);
			}
		}

		if (lu == null)
		{
			// shall not happen
			throw new AdempiereException("No LU was created");
		}

		return HuId.ofRepoId(lu.getM_HU_ID());
	}

	private void receiveToNewTU(@NonNull final JsonNewTUTarget newTUTarget)
	{
		receiveTUs(newTUTarget.getTuPIItemProductId());
	}

	@NonNull
	private List<I_M_HU> receiveTUs(@NonNull final HUPIItemProductId tuPIItemProductId)
	{
		final List<I_M_HU> hus = createHUProducer().receiveTUs(getQtyToReceive(tuPIItemProductId), tuPIItemProductId);
		collectReceivedHUs(hus);
		return hus;
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
}
