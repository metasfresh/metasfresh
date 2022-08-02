package de.metas.manufacturing.job.service.commands;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.qrcodes.model.HUQRCode;
import de.metas.handlingunits.qrcodes.model.HUQRCodePackingInfo;
import de.metas.handlingunits.qrcodes.model.HUQRCodeUnitType;
import de.metas.manufacturing.job.model.ReceivingTarget;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonHUQRCodeTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonNewLUTarget;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonReceivingTarget;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.collections.CollectionUtils;
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
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

public class ReceiveGoodsCommand
{
	//
	// Services
	private final IHandlingUnitsBL handlingUnitsBL;
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	//
	// Parameters
	final @NonNull PPOrderId ppOrderId;
	final @Nullable PPOrderBOMLineId coProductBOMLineId;
	final @NonNull JsonReceivingTarget receivingTarget;
	final @NonNull BigDecimal qtyToReceiveBD;
	final @NonNull ZonedDateTime date;

	//
	// State
	private I_PP_Order _ppOrder; // lazy
	private I_PP_Order_BOMLine _coProductLine;

	@Builder
	private ReceiveGoodsCommand(
			@NonNull final IHandlingUnitsBL handlingUnitsBL,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final PPOrderId ppOrderId,
			@Nullable final PPOrderBOMLineId coProductBOMLineId,
			@NonNull final JsonReceivingTarget receivingTarget,
			@NonNull final BigDecimal qtyToReceiveBD,
			@NonNull final ZonedDateTime date)
	{
		this.handlingUnitsBL = handlingUnitsBL;
		this.ppOrderBL = ppOrderBL;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.ppOrderId = ppOrderId;
		this.coProductBOMLineId = coProductBOMLineId;
		this.receivingTarget = receivingTarget;
		this.qtyToReceiveBD = qtyToReceiveBD;
		this.date = date;
	}

	@Nullable
	public ReceivingTarget execute()
	{
		@Nullable ReceivingTarget receivingTarget;
		if (this.receivingTarget.getNewLU() != null)
		{
			receivingTarget = receiveToNewLU(this.receivingTarget.getNewLU());
		}
		else if (this.receivingTarget.getExistingLU() != null)
		{
			receivingTarget = receiveByQRCode(this.receivingTarget.getExistingLU());
		}
		else
		{
			throw new AdempiereException("Unhandled target: " + this.receivingTarget);
		}

		saveReceivingHUForLaterUse(receivingTarget);

		return receivingTarget;
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
				final I_M_HU tu = createHUProducer().receiveSingleTU(getQtyToReceive(), packingInfo.getPackingInstructionsId());
				final HuId tuId = HuId.ofRepoId(tu.getM_HU_ID());

				loadingAndSavingSupportServices.assignQRCode(qrCode, tuId);

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
				throw new AdempiereException("Receiving to existing HUs which are not LU is not supported");
			}
		}
	}

	private ReceivingTarget receiveToNewLU(@NonNull final JsonNewLUTarget newLUTarget)
	{
		final I_M_HU_PI_Item newLUPIItem = handlingUnitsBL.getPackingInstructionItemById(newLUTarget.getLuPIItemId());

		final HUPIItemProductId tuPIItemProductId = newLUTarget.getTuPIItemProductId();
		final List<I_M_HU> tusOrVhus = createHUProducer().receiveTUs(getQtyToReceive(), tuPIItemProductId);
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

		final List<I_M_HU> tusOrVhus = createHUProducer().receiveTUs(getQtyToReceive(), tuPIItemProductId);
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

	private IPPOrderReceiptHUProducer createHUProducer()
	{
		final IPPOrderReceiptHUProducer huProducer;
		final LocatorId locatorId;

		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			locatorId = LocatorId.ofRepoId(coProductLine.getM_Warehouse_ID(), coProductLine.getM_Locator_ID());
			huProducer = ppOrderBL.receivingByOrCoProduct(coProductBOMLineId);
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			locatorId = LocatorId.ofRepoId(ppOrder.getM_Warehouse_ID(), ppOrder.getM_Locator_ID());
			huProducer = ppOrderBL.receivingMainProduct(ppOrderId);
		}

		return huProducer
				.movementDate(date)
				.locatorId(locatorId);

	}

	private Quantity getQtyToReceive()
	{
		final I_PP_Order_BOMLine coProductLine = getCOProductLine();
		if (coProductLine != null)
		{
			final UomId uomId = UomId.ofRepoId(coProductLine.getC_UOM_ID());
			return Quantitys.create(qtyToReceiveBD, uomId);
		}
		else
		{
			final I_PP_Order ppOrder = getPPOrder();
			final UomId uomId = UomId.ofRepoId(ppOrder.getC_UOM_ID());
			return Quantitys.create(qtyToReceiveBD, uomId);
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
				final List<I_M_HU> createdLUs = HUTransformService.newInstance()
						.tuToNewLUs(
								tu,
								QtyTU.ONE.toBigDecimal(),
								Objects.requireNonNull(newLUPIItem),
								true);
				lu = CollectionUtils.singleElement(createdLUs);
			}
			else
			{
				HUTransformService.newInstance().tuToExistingLU(tu, QtyTU.ONE.toBigDecimal(), lu);
			}
		}

		if (lu == null)
		{
			// shall not happen
			throw new AdempiereException("No LU was created");
		}

		return HuId.ofRepoId(lu.getM_HU_ID());
	}

}
