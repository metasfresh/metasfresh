package de.metas.manufacturing.job.service.commands;

import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.IHandlingUnitsDAO;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.allocation.transfer.HUTransformService;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.pporder.api.IHUPPOrderBL;
import de.metas.handlingunits.pporder.api.IPPOrderReceiptHUProducer;
import de.metas.handlingunits.qrcodes.model.json.JsonRenderedHUQRCode;
import de.metas.manufacturing.job.model.CurrentReceivingHU;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaver;
import de.metas.manufacturing.job.service.ManufacturingJobLoaderAndSaverSupportingServices;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.json.JsonAggregateToLU;
import de.metas.material.planning.pporder.IPPOrderBOMBL;
import de.metas.quantity.Quantitys;
import de.metas.uom.UomId;
import de.metas.util.Check;
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
	private final IHandlingUnitsDAO handlingUnitsDAO;
	private final IHUPPOrderBL ppOrderBL;
	private final IPPOrderBOMBL ppOrderBOMBL;
	private final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices;

	final @NonNull PPOrderId ppOrderId;
	final @Nullable PPOrderBOMLineId coProductBOMLineId;
	final @NonNull JsonAggregateToLU aggregateToLU;
	final @NonNull BigDecimal qtyToReceiveBD;
	final @NonNull ZonedDateTime date;

	@Builder
	private ReceiveGoodsCommand(
			@NonNull final IHandlingUnitsDAO handlingUnitsDAO,
			@NonNull final IHUPPOrderBL ppOrderBL,
			@NonNull final IPPOrderBOMBL ppOrderBOMBL,
			@NonNull final ManufacturingJobLoaderAndSaverSupportingServices loadingAndSavingSupportServices,
			//
			@NonNull final PPOrderId ppOrderId,
			@Nullable final PPOrderBOMLineId coProductBOMLineId,
			@NonNull final JsonAggregateToLU aggregateToLU,
			@NonNull final BigDecimal qtyToReceiveBD,
			@NonNull final ZonedDateTime date)
	{
		this.handlingUnitsDAO = handlingUnitsDAO;
		this.ppOrderBL = ppOrderBL;
		this.ppOrderBOMBL = ppOrderBOMBL;
		this.loadingAndSavingSupportServices = loadingAndSavingSupportServices;

		this.ppOrderId = ppOrderId;
		this.coProductBOMLineId = coProductBOMLineId;
		this.aggregateToLU = aggregateToLU;
		this.qtyToReceiveBD = qtyToReceiveBD;
		this.date = date;
	}

	public CurrentReceivingHU execute()
	{
		final I_PP_Order ppOrder = ppOrderBL.getById(ppOrderId);

		final I_PP_Order_BOMLine coProductLine;
		final LocatorId locatorId;
		final UomId uomId;
		final IPPOrderReceiptHUProducer huProducer;
		if (coProductBOMLineId != null)
		{
			coProductLine = ppOrderBOMBL.getOrderBOMLineById(coProductBOMLineId);
			locatorId = LocatorId.ofRepoId(coProductLine.getM_Warehouse_ID(), coProductLine.getM_Locator_ID());
			uomId = UomId.ofRepoId(coProductLine.getC_UOM_ID());
			huProducer = ppOrderBL.receivingByOrCoProduct(coProductBOMLineId);
		}
		else
		{
			coProductLine = null;
			locatorId = LocatorId.ofRepoId(ppOrder.getM_Warehouse_ID(), ppOrder.getM_Locator_ID());
			uomId = UomId.ofRepoId(ppOrder.getC_UOM_ID());
			huProducer = ppOrderBL.receivingMainProduct(ppOrderId);
		}

		final HUPIItemProductId tuPIItemProductId = aggregateToLU.getTUPIItemProductId()
				.orElseGet(() -> HUPIItemProductId.ofRepoIdOrNone(ppOrder.getCurrent_Receiving_TU_PI_Item_Product_ID()));

		final List<I_M_HU> tusOrVhus = huProducer
				.movementDate(date)
				.locatorId(locatorId)
				.receiveTUs(
						Quantitys.create(qtyToReceiveBD, uomId),
						tuPIItemProductId);

		final HuId luId = aggregateTUsToLU(tusOrVhus);

		final CurrentReceivingHU currentReceivingHU = CurrentReceivingHU.builder()
				.tuPIItemProductId(tuPIItemProductId)
				.aggregateToLUId(luId)
				.build();

		//
		// Remember current receiving LU.
		// We will need it later too.
		if (coProductLine != null)
		{
			ManufacturingJobLoaderAndSaver.updateRecordFromCurrentReceivingHU(coProductLine, currentReceivingHU);
			ppOrderBOMBL.save(coProductLine);
		}
		else
		{
			ManufacturingJobLoaderAndSaver.updateRecordFromCurrentReceivingHU(ppOrder, currentReceivingHU);
			ppOrderBL.save(ppOrder);
		}

		return currentReceivingHU;
	}

	private HuId aggregateTUsToLU(final @NonNull List<I_M_HU> tusOrVhus)
	{
		Check.assumeNotEmpty(tusOrVhus, "at least one TU shall be received from manufacturing order");

		I_M_HU lu = null;
		I_M_HU_PI_Item luPIItem = null;
		if (aggregateToLU.getExistingLU() != null)
		{
			final JsonRenderedHUQRCode qrCode = aggregateToLU.getExistingLU().getHuQRCode();
			final HuId luId = loadingAndSavingSupportServices.getHuIdByQRCode(qrCode);
			lu = handlingUnitsDAO.getById(luId);
		}
		else
		{
			if (aggregateToLU.getNewLU() == null)
			{
				throw new AdempiereException("LU packing materials spec needs to be provided when no actual LU is specified.");
			}
			luPIItem = handlingUnitsDAO.getPackingInstructionItemById(aggregateToLU.getNewLU().getLuPIItemId());
		}

		for (final I_M_HU tu : tusOrVhus)
		{
			if (lu == null)
			{
				final List<I_M_HU> createdLUs = HUTransformService.newInstance()
						.tuToNewLUs(
								tu,
								QtyTU.ONE.toBigDecimal(),
								Objects.requireNonNull(luPIItem),
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
