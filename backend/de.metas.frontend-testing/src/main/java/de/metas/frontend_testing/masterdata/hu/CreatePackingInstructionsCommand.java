package de.metas.frontend_testing.masterdata.hu;

import de.metas.common.util.time.SystemTime;
import de.metas.frontend_testing.JsonTestId;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.gs1.ean13.EAN13;
import de.metas.handlingunits.grai.GRAI;
import de.metas.handlingunits.grai.HUPIGraiRepository;
import de.metas.handlingunits.HUItemType;
import de.metas.handlingunits.HUPIItemProductId;
import de.metas.handlingunits.HuPackingInstructionsId;
import de.metas.handlingunits.HuPackingInstructionsItemId;
import de.metas.handlingunits.HuPackingInstructionsVersionId;
import de.metas.handlingunits.HuUnitType;
import de.metas.handlingunits.IHandlingUnitsBL;
import de.metas.handlingunits.QtyTU;
import de.metas.handlingunits.model.I_M_HU_PI;
import de.metas.handlingunits.model.I_M_HU_PI_Attribute;
import de.metas.handlingunits.model.I_M_HU_PI_GRAI;
import de.metas.handlingunits.model.I_M_HU_PI_Item;
import de.metas.handlingunits.model.I_M_HU_PI_Item_Product;
import de.metas.handlingunits.model.I_M_HU_PI_Version;
import de.metas.handlingunits.model.X_M_HU_PI_Attribute;
import de.metas.javaclasses.JavaClassId;
import de.metas.logging.LogManager;
import de.metas.manufacturing.workflows_api.activity_handlers.generateHUQRCodes.GenerateHUQRCodesActivityHandler;
import de.metas.manufacturing.workflows_api.activity_handlers.receive.MaterialReceiptActivityHandler;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import de.metas.util.Check;
import de.metas.util.Services;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.mm.attributes.api.AttributeConstants;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.slf4j.Logger;

import javax.annotation.Nullable;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreatePackingInstructionsCommand
{
	private static final Logger logger = LogManager.getLogger(CreatePackingInstructionsCommand.class);

	/** Same {@code HU_TansferStrategy_JavaClass_ID} the cucumber {@code M_HU_PI_Attribute_StepDef} uses. */
	private static final JavaClassId COPY_TRANSFER_STRATEGY_ID = JavaClassId.ofRepoId(540027);

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IHandlingUnitsBL handlingUnitsBL = Services.get(IHandlingUnitsBL.class);
	@NonNull private final IAttributeDAO attributeDAO = Services.get(IAttributeDAO.class);
	@NonNull private final HUPIGraiRepository huPIGraiRepository = new HUPIGraiRepository();
	@NonNull private final ProductPricePackingInstructionRepository productPricePackingInstructionRepository = new ProductPricePackingInstructionRepository();
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
			assignCustomAttributes(tu);
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

		//
		// GRAI mapping
		final GRAI grai = request.isGraiMapping() ? createGRAIMapping(tu) : null;

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
				.grai(grai)
				//
				.build();
	}

	/**
	 * Generates a canonical GRAI whose (companyPrefix, assetType) does not collide with any existing
	 * {@code M_HU_PI_GRAI} row, then inserts an {@code M_HU_PI_GRAI} row linking it to the given TU packing instruction.
	 * <p>
	 * {@code M_HU_PI_GRAI} carries a global unique index on (companyPrefix, assetType). When the test pins BOTH via
	 * overrides (e.g. the Migros {@code 7613204.00307} pair), a prior run's row for that exact pair would make
	 * {@link #generateUniqueGRAI} unable to ever find a free slot — masterdata creation then fails with
	 * "Failed to generate a unique GRAI after 100 attempts". So for a pinned pair we first delete any stale mapping
	 * for it, making the frontend-testing masterdata re-runnable on a persistent (non-fresh) DB. The random (non-pinned)
	 * case is unaffected — a fresh random pair never collides.
	 *
	 * @return the generated GRAI (canonical {@code companyPrefix.assetType.serial} format).
	 */
	private GRAI createGRAIMapping(@NonNull final PIResult tu)
	{
		final String companyPrefixOverride = request.getGraiCompanyPrefix();
		final String assetTypeOverride = request.getGraiAssetType();
		if (companyPrefixOverride != null && assetTypeOverride != null)
		{
			final int deleted = huPIGraiRepository.deleteMapping(companyPrefixOverride, assetTypeOverride);
			if (deleted > 0)
			{
				logger.info("Removed {} stale M_HU_PI_GRAI mapping(s) for pinned GRAI {}.{} (re-runnable masterdata)",
						deleted, companyPrefixOverride, assetTypeOverride);
			}
		}

		final GRAI grai = generateUniqueGRAI(companyPrefixOverride, assetTypeOverride);

		huPIGraiRepository.createMapping(tu.getPiId(), grai);

		logger.info("Created M_HU_PI_GRAI mapping {} -> M_HU_PI_ID={}", grai.toCanonicalString(), tu.getPiId().getRepoId());

		assignGraiAttribute(tu);

		return grai;
	}

	/**
	 * Declares the {@code GRAI} HU-attribute slot on the given TU packing-instruction version, so that HUs
	 * materialised from this PI carry a writable GRAI slot where the scanned GRAI can be stored at pick time.
	 * Without this slot, completion fails with {@code GRAI_COUNT_MISMATCH}.
	 * <p>
	 * Idempotent: if the slot is already present on the PI version, nothing is added.
	 */
	private void assignGraiAttribute(@NonNull final PIResult tu)
	{
		final AttributeId graiAttributeId = attributeDAO.getAttributeIdByCode(AttributeConstants.ATTR_GRAI);

		final HuPackingInstructionsVersionId pivId = tu.getPivId();
		final boolean alreadyPresent = queryBL.createQueryBuilder(I_M_HU_PI_Attribute.class)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, pivId)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, graiAttributeId)
				.create()
				.anyMatch();
		if (alreadyPresent)
		{
			logger.info("GRAI HU-attribute slot already present on M_HU_PI_Version_ID={}", pivId);
			return;
		}

		final I_M_HU_PI_Attribute piAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Attribute.class);

		// Copy ALL columns from the template GRAI M_HU_PI_Attribute so every NOT-NULL column is populated (see findTemplateGraiAttribute).
		final I_M_HU_PI_Attribute templateGraiAttribute = findTemplateGraiAttribute(graiAttributeId);
		if (templateGraiAttribute == null)
		{
			throw new AdempiereException("No GRAI M_HU_PI_Attribute found on the TEMPLATE PI version"
					+ "; expected it to be created by migration 5795460_add_GRAI_attr_to_PI_template.sql (M_Attribute_ID=" + graiAttributeId + ")");
		}
		InterfaceWrapperHelper.copy()
				.setFrom(templateGraiAttribute)
				.setTo(piAttribute)
				.copy();

		// Always (re)set the discriminating columns - these must point at the NEW TU PI version, not the template.
		piAttribute.setM_HU_PI_Version_ID(pivId.getRepoId());
		piAttribute.setM_Attribute_ID(graiAttributeId.getRepoId());
		piAttribute.setIsActive(true);
		saveRecord(piAttribute);

		logger.info("Declared GRAI HU-attribute slot (M_Attribute_ID={}) on M_HU_PI_Version_ID={}", graiAttributeId, pivId);
	}

	/**
	 * Looks up the GRAI {@code M_HU_PI_Attribute} row declared on the current version of the TEMPLATE packing
	 * instruction ({@link HuPackingInstructionsId#TEMPLATE}). This is the row created by the migration script
	 * {@code 5795460_add_GRAI_attr_to_PI_template.sql} and is used as the template to copy onto new TU PI versions.
	 *
	 * @return the template GRAI attribute row, or {@code null} if not present.
	 */
	@Nullable
	private I_M_HU_PI_Attribute findTemplateGraiAttribute(@NonNull final AttributeId graiAttributeId)
	{
		final HuPackingInstructionsVersionId templatePivId = handlingUnitsBL.retrievePICurrentVersionId(HuPackingInstructionsId.TEMPLATE);
		return queryBL.createQueryBuilder(I_M_HU_PI_Attribute.class)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, templatePivId)
				.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, graiAttributeId)
				.create()
				.firstOnlyOrNull(I_M_HU_PI_Attribute.class);
	}

	/**
	 * Declares a writable {@code M_HU_PI_Attribute} slot on the given TU packing-instruction version for every
	 * attribute code in {@link JsonPackingInstructionsRequest#getAttributes()}, so HUs materialised from this PI
	 * carry that attribute in their own storage - see {@link JsonPackingInstructionsRequest#getAttributes()}'s
	 * Javadoc for why this is needed (the apply-side {@code hasAttribute} guard reads the HU's OWN PI version,
	 * not the product's {@code M_AttributeSet}). Mirrors the cucumber step {@code M_HU_PI_Attribute_StepDef}.
	 * <p>
	 * Idempotent per attribute: an already-present slot is left untouched.
	 */
	private void assignCustomAttributes(@NonNull final PIResult tu)
	{
		final List<AttributeCode> attributeCodes = request.getAttributes();
		if (attributeCodes == null || attributeCodes.isEmpty())
		{
			return;
		}

		final HuPackingInstructionsVersionId pivId = tu.getPivId();
		for (final AttributeCode attributeCode : attributeCodes)
		{
			final AttributeId attributeId = attributeDAO.getAttributeIdByCode(attributeCode);

			final boolean alreadyPresent = queryBL.createQueryBuilder(I_M_HU_PI_Attribute.class)
					.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_HU_PI_Version_ID, pivId)
					.addEqualsFilter(I_M_HU_PI_Attribute.COLUMNNAME_M_Attribute_ID, attributeId)
					.create()
					.anyMatch();
			if (alreadyPresent)
			{
				logger.info("HU-attribute slot for {} already present on M_HU_PI_Version_ID={}", attributeCode, pivId);
				continue;
			}

			final I_M_HU_PI_Attribute piAttribute = InterfaceWrapperHelper.newInstance(I_M_HU_PI_Attribute.class);
			piAttribute.setM_HU_PI_Version_ID(pivId.getRepoId());
			piAttribute.setM_Attribute_ID(attributeId.getRepoId());
			piAttribute.setHU_TansferStrategy_JavaClass_ID(COPY_TRANSFER_STRATEGY_ID.getRepoId());
			piAttribute.setIsActive(true);
			piAttribute.setIsDisplayed(true);
			piAttribute.setIsOnlyIfInProductAttributeSet(false);
			piAttribute.setPropagationType(X_M_HU_PI_Attribute.PROPAGATIONTYPE_NoPropagation);
			piAttribute.setUseInASI(true);
			saveRecord(piAttribute);

			logger.info("Declared HU-attribute slot ({}) on M_HU_PI_Version_ID={}", attributeCode, pivId);
		}
	}

	/**
	 * Generates a canonical GRAI ({@code companyPrefix.assetType.serial}) whose (companyPrefix, assetType) pair
	 * does not already exist in {@code M_HU_PI_GRAI} (the global unique index is on those two columns).
	 * <p>
	 * When {@code companyPrefixOverride}/{@code assetTypeOverride} are given (e.g. the Migros returnable-asset
	 * pair), they are used instead of a random pair — e.g. to build a scannable Migros GRAI in a test whose
	 * {@code (companyPrefix, assetType)} must be known ahead of time. The collision-avoidance loop still applies:
	 * if that fixed pair already has a mapping, generation fails after 100 attempts, same as the random case.
	 */
	private GRAI generateUniqueGRAI(@Nullable final String companyPrefixOverride, @Nullable final String assetTypeOverride)
	{
		final ThreadLocalRandom random = ThreadLocalRandom.current();
		for (int attempt = 0; attempt < 100; attempt++)
		{
			// 7-digit company prefix + 5-digit asset type + numeric serial → also valid as a GS1 AI 8003 barcode (12-digit base).
			final String companyPrefix = companyPrefixOverride != null ? companyPrefixOverride : String.format("%07d", random.nextInt(0, 10_000_000));
			final String assetType = assetTypeOverride != null ? assetTypeOverride : String.format("%05d", random.nextInt(0, 100_000));
			final String serial = String.format("%010d", random.nextLong(0, 10_000_000_000L));
			final GRAI grai = GRAI.ofCanonicalString(companyPrefix + "." + assetType + "." + serial);

			final boolean exists = queryBL.createQueryBuilder(I_M_HU_PI_GRAI.class)
					.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_CompanyPrefix, companyPrefix)
					.addEqualsFilter(I_M_HU_PI_GRAI.COLUMNNAME_GRAI_AssetType, assetType)
					.create()
					.anyMatch();
			if (!exists)
			{
				return grai;
			}
		}

		throw new AdempiereException("Failed to generate a unique GRAI after 100 attempts");
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

		final int updatedCount = productPricePackingInstructionRepository
				.pointProductPricesAt(priceListVersionId, productId, piItemProduct);

		Check.assume(updatedCount > 0,
				"referencedByProductPrice needs product {} to have a price on price list version {} — give the product a price in the same request",
				productId, priceListVersionId);
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
