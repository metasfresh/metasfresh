package de.metas.frontend_testing.masterdata.hu;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
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

	public JsonPackingInstructionsResponse execute()
	{
		final PIResult tu = createPI(request.getTu(), HuUnitType.TU);
		final HuPackingInstructionsItemId tuPIItemId = createPIItem_Material(tu);
		createPIItemProduct(request.getTu(), tuPIItemId);

		final PIResult lu;
		if (request.getLu() != null)
		{
			lu = createPI(request.getLu(), HuUnitType.LU);
			createPIItem_IncludedHU(lu, tu, request.getQtyTUsPerLU());
		}
		else
		{
			lu = null;
		}

		return JsonPackingInstructionsResponse.builder()
				.tuName(tu.getPiName())
				.luName(lu != null ? lu.getPiName() : null)
				.build();
	}

	@Value
	@Builder
	private static class PIResult
	{
		@NonNull HuPackingInstructionsId piId;
		@NonNull String piName;
		@NonNull HuPackingInstructionsVersionId pivId;
	}

	private PIResult createPI(
			@NonNull final Identifier identifier,
			@NonNull final HuUnitType huUnitType)
	{
		final String piName = identifier.toUniqueString();

		final I_M_HU_PI huPiRecord = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI.class);
		huPiRecord.setName(piName);
		huPiRecord.setIsActive(true);
		saveRecord(huPiRecord);
		final HuPackingInstructionsId piId = HuPackingInstructionsId.ofRepoId(huPiRecord.getM_HU_PI_ID());
		context.putIdentifier(identifier, piId);

		//
		//
		//
		final I_M_HU_PI_Version piVersion = InterfaceWrapperHelper.newInstanceOutOfTrx(I_M_HU_PI_Version.class);
		piVersion.setM_HU_PI_ID(piId.getRepoId());
		piVersion.setName(piName);
		piVersion.setHU_UnitType(huUnitType.getCode());
		piVersion.setIsCurrent(true);
		piVersion.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(piVersion);
		final HuPackingInstructionsVersionId pivId = HuPackingInstructionsVersionId.ofRepoId(piVersion.getM_HU_PI_Version_ID());

		return PIResult.builder()
				.piId(piId)
				.piName(piName)
				.pivId(pivId)
				.build();
	}

	private void createPIItem_IncludedHU(final PIResult lu, final PIResult tu, final int qtyTUsPerLU)
	{
		final I_M_HU_PI_Item huPiItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		huPiItemRecord.setM_HU_PI_Version_ID(lu.getPivId().getRepoId());
		huPiItemRecord.setItemType(HUItemType.HandlingUnit.getCode());
		huPiItemRecord.setQty(BigDecimal.valueOf(qtyTUsPerLU));
		huPiItemRecord.setIncluded_HU_PI_ID(tu.getPiId().getRepoId());
		saveRecord(huPiItemRecord);
	}

	private HuPackingInstructionsItemId createPIItem_Material(final PIResult tu)
	{
		final I_M_HU_PI_Item huPiItemRecord = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item.class);
		huPiItemRecord.setM_HU_PI_Version_ID(tu.getPivId().getRepoId());
		huPiItemRecord.setItemType(HUItemType.Material.getCode());
		saveRecord(huPiItemRecord);
		return HuPackingInstructionsItemId.ofRepoId(huPiItemRecord.getM_HU_PI_Item_ID());
	}

	private void createPIItemProduct(Identifier tuIdentifier, HuPackingInstructionsItemId tuPIItemId)
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
	}
}
