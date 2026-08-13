package de.metas.frontend_testing.masterdata.hu;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.JsonTestId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.I_M_ProductPrice;
import de.metas.logging.LogManager;
import de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes.GenerateHUQRCodesActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.MaterialReceiptActivityHandler;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.pricing.service.ProductPrices;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_PriceList_Version;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreatePackingInstructionsCommand
{
	private static final Logger logger = LogManager.getLogger(CreatePackingInstructionsCommand.class);
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IPriceListDAO priceListDAO = Services.get(IPriceListDAO.class);
	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPackingInstructionsRequest request;
	@NonNull private final Identifier identifier;

	public JsonPackingInstructionsResponse execute()
	{
		renamePreviousEANs();

		//
		// TU or CU
		final PIResult tu;
		final JsonTestId tuPIItemProductTestId;
		if (request.isCu())
		{
			tu = loadPI_CU();
			tuPIItemProductTestId = null;
		}
		else
		{
			tu = createPI(request.getTuNotNull(), HuUnitType.TU);
			final HuPackingInstructionsItemId tuPIItemId = createPIItem_Material(tu);
			tuPIItemProductTestId = createPIItemProduct(tuPIItemId);
		}

		//
		// LU
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

	private PIResult loadPI_CU()
	{
		final I_M_HU_PI piRecord = handlingUnitsBL.getPI(HuPackingInstructionsId.VIRTUAL);
		return PIResult.builder()
				.pi(piRecord)
				.piId(HuPackingInstructionsId.VIRTUAL)
				.piName(piRecord.getName())
				.pivId(HuPackingInstructionsVersionId.VIRTUAL)
				.build();
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

	private JsonTestId createPIItemProduct(@NonNull final HuPackingInstructionsItemId tuPIItemId)
	{
		final Identifier tuIdentifier = request.getTuNotNull();
		final Identifier productIdentifier = request.getProductNotNull();
		final ProductId productId = context.getId(productIdentifier, ProductId.class);
		final UomId uomId = productBL.getStockUOMId(productId);

		final I_M_HU_PI_Item_Product record = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Item_Product.class);

		record.setM_Product_ID(productId.getRepoId());
		record.setM_HU_PI_Item_ID(tuPIItemId.getRepoId());
		final BigDecimal qtyCUsPerTU = request.getQtyCUsPerTU();
		if (qtyCUsPerTU != null)
		{
			record.setIsInfiniteCapacity(false);
			record.setQty(qtyCUsPerTU);
		}
		else
		{
			record.setIsInfiniteCapacity(true);
			record.setQty(BigDecimal.ZERO);
		}
		record.setC_UOM_ID(uomId.getRepoId());
		record.setValidFrom(Timestamp.from(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant()));
		record.setEAN_TU(request.getTu_ean() != null ? request.getTu_ean().getAsString() : null);
		record.setIsDefaultForProduct(request.isDefaultForProduct());
		saveRecord(record);
		final HUPIItemProductId piItemProductId = HUPIItemProductId.ofRepoId(record.getM_HU_PI_Item_Product_ID());

		if (request.isReferencedByProductPrice())
		{
			pointProductPricesAt(productId, record);
		}

		context.putIdentifierIfAbsent(tuIdentifier, piItemProductId);
		context.putIdentifier(Identifier.ofString(tuIdentifier.getAsString() + "_" + productIdentifier.getAsString()), piItemProductId);

		return MaterialReceiptActivityHandler.extractNewTUTargetTestId(record);
	}

	/**
	 * Makes the product's price(s) on the current price list version reference the given CU-TU allocation,
	 * so the packing instruction is one a product price actually points at.
	 * <p>
	 * Links <em>every</em> price row the product has on that price list version. Fixtures create one price
	 * per product, so that is the same thing in practice — but a fixture that creates several (e.g.
	 * attribute-dependent variants) would get them all pointed at this allocation.
	 */
	private void pointProductPricesAt(
			@NonNull final ProductId productId,
			@NonNull final I_M_HU_PI_Item_Product piItemProduct)
	{
		// Fails with "No identifier found for PriceListVersionId" when the request has no bpartners section:
		// the price list version is created as a side effect of creating a bpartner.
		final PriceListVersionId priceListVersionId = context.getIdOfType(PriceListVersionId.class);
		final I_M_PriceList_Version priceListVersion = priceListDAO.getPriceListVersionById(priceListVersionId);

		final List<I_M_ProductPrice> productPrices = ProductPrices.newQuery(priceListVersion)
				.setProductId(productId)
				.list(I_M_ProductPrice.class);

		Check.assumeNotEmpty(productPrices,
				"referencedByProductPrice needs product {} to have a price on price list version {} — give the product a price in the same request",
				productId, priceListVersionId);

		for (final I_M_ProductPrice productPrice : productPrices)
		{
			productPrice.setM_HU_PI_Item_Product(piItemProduct);
			saveRecord(productPrice);
		}
	}

	private void renamePreviousEANs()
	{
		final EAN13 tu_ean = request.getTu_ean();
		if (tu_ean == null)
		{
			return;
		}

		queryBL.createQueryBuilder(I_M_HU_PI_Item_Product.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_HU_PI_Item_Product.COLUMNNAME_EAN_TU, tu_ean.getAsString())
				.create()
				.forEach(record -> {
					final String tuEAN_before = record.getEAN_TU();
					final String tuEAN_after = tuEAN_before + "_old";
					record.setEAN_TU(tuEAN_after);
					InterfaceWrapperHelper.saveRecord(record);
					logger.info("Updated {}: changed EAN from {} to {}", record, tuEAN_before, tuEAN_after);
				});
	}
}
