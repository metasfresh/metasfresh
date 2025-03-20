package de.metas.frontend_testing.masterdata.hu;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.JsonTestId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes.GenerateHUQRCodesActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.MaterialReceiptActivityHandler;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.model.InterfaceWrapperHelper;

import java.math.BigDecimal;
import java.sql.Timestamp;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreatePackingInstructionsCommand
{
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPackingInstructionsRequest request;
	@NonNull private final Identifier identifier;

	public JsonPackingInstructionsResponse execute()
	{
		final PIResult tu = createPI(request.getTu(), HuUnitType.TU);
		final HuPackingInstructionsItemId tuPIItemId = createPIItem_Material(tu);
		final JsonTestId tuPIItemProductTestId = createPIItemProduct(request.getTu(), tuPIItemId);

		final PIResult lu;
		final I_M_HU_PI_Item luPIItem;
		final JsonTestId luPIItemTestId;
		if (request.getLu() != null)
		{
			lu = createPI(request.getLu(), HuUnitType.LU);
			luPIItem = createPIItem_IncludedHU(lu, tu, request.getQtyTUsPerLU());
			luPIItemTestId = MaterialReceiptActivityHandler.extractNewLUTargetTestId(luPIItem);
		}
		else
		{
			lu = null;
			luPIItem = null;
			luPIItemTestId = null;
		}

		context.putObject(identifier, PackingInstructions.builder()
				.tuPI(tu.getPi())
				.qtyCUsPerTU(request.getQtyCUsPerTU())
				.luPIItem(luPIItem)
				.qtyTUs(luPIItem != null ? QtyTU.ofInt(request.getQtyTUsPerLU()) : null)
				.build());

		return JsonPackingInstructionsResponse.builder()
				.tuName(tu.getPiName())
				.tuPIItemProductTestId(tuPIItemProductTestId)
				.tuPITestId(GenerateHUQRCodesActivityHandler.toPITestId(tu.getPiId()))
				//
				.luName(lu != null ? lu.getPiName() : null)
				.luPIItemTestId(luPIItemTestId)
				.luPITestId(lu != null ? GenerateHUQRCodesActivityHandler.toPITestId(lu.getPiId()) : null)
				//
				.build();
	}

	@Value
	@Builder
	static class PIResult
	{
		@NonNull I_M_HU_PI pi;
		@NonNull HuPackingInstructionsId piId;
		@NonNull String piName;
		@NonNull HuPackingInstructionsVersionId pivId;
	}

	private PIResult createPI(
			@NonNull final Identifier identifier,
			@NonNull final HuUnitType huUnitType)
	{
		final PIResult existingPI = context.<PIResult>getObject(identifier).orElse(null);
		if (existingPI != null)
		{
			return existingPI;
		}

		final String piName = identifier.toUniqueString();

		final I_M_HU_PI piRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI.class);
		piRecord.setName(piName);
		piRecord.setIsActive(true);
		saveRecord(piRecord);
		final HuPackingInstructionsId piId = HuPackingInstructionsId.ofRepoId(piRecord.getM_HU_PI_ID());
		context.putIdentifier(identifier, piId);

		//
		//
		//
		final I_M_HU_PI_Version pivRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class);
		pivRecord.setM_HU_PI_ID(piId.getRepoId());
		pivRecord.setName(piName);
		pivRecord.setHU_UnitType(huUnitType.getCode());
		pivRecord.setIsCurrent(true);
		pivRecord.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(pivRecord);
		final HuPackingInstructionsVersionId pivId = HuPackingInstructionsVersionId.ofRepoId(pivRecord.getM_HU_PI_Version_ID());

		final PIResult newPI = PIResult.builder()
				.pi(piRecord)
				.piId(piId)
				.piName(piName)
				.pivId(pivId)
				.build();
		context.putObject(identifier, newPI);

		return newPI;
	}

	private I_M_HU_PI_Item createPIItem_IncludedHU(final PIResult lu, final PIResult tu, final int qtyTUsPerLU)
	{
		final I_M_HU_PI_Item luPIItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		luPIItemRecord.setM_HU_PI_Version_ID(lu.getPivId().getRepoId());
		luPIItemRecord.setItemType(HUItemType.HandlingUnit.getCode());
		luPIItemRecord.setQty(BigDecimal.valueOf(qtyTUsPerLU));
		luPIItemRecord.setIncluded_HU_PI_ID(tu.getPiId().getRepoId());
		saveRecord(luPIItemRecord);
		return luPIItemRecord;
	}

	private HuPackingInstructionsItemId createPIItem_Material(final PIResult tu)
	{
		final I_M_HU_PI_Item huPiItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		huPiItemRecord.setM_HU_PI_Version_ID(tu.getPivId().getRepoId());
		huPiItemRecord.setItemType(HUItemType.Material.getCode());
		saveRecord(huPiItemRecord);
		return HuPackingInstructionsItemId.ofRepoId(huPiItemRecord.getM_HU_PI_Item_ID());
	}

	private JsonTestId createPIItemProduct(Identifier tuIdentifier, HuPackingInstructionsItemId tuPIItemId)
	{
		final ProductId productId = context.getId(request.getProduct(), ProductId.class);
		final UomId uomId = productBL.getStockUOMId(productId);

		final I_M_HU_PI_Item_Product record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);

		record.setM_Product_ID(productId.getRepoId());
		record.setM_HU_PI_Item_ID(tuPIItemId.getRepoId());
		record.setQty(request.getQtyCUsPerTU());
		record.setC_UOM_ID(uomId.getRepoId());
		record.setValidFrom(Timestamp.from(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant()));
		saveRecord(record);
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoId(record.getM_HU_PI_Item_Product_ID());
		context.putIdentifier(tuIdentifier, piItemProductId);

		return MaterialReceiptActivityHandler.extractNewTUTargetTestId(record);
	}
}
