package de.metas.product.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.IBPartnerProductDAO;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
import de.metas.gs1.GS1ProductCodes;
import de.metas.gs1.GS1ProductCodesCollection;
import de.metas.gs1.GS1ProductCodesCollection.GS1ProductCodesCollectionBuilder;
import de.metas.gs1.GTIN;
import de.metas.gs1.ean13.EAN13;
import de.metas.gs1.ean13.EAN13ProductCode;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import de.metas.logging.LogManager;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.product.IProductBL;
import de.metas.product.IProductDAO;
import de.metas.product.IProductDAO.ProductQuery;
import de.metas.product.IssuingToleranceSpec;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.quantity.Quantity;
import de.metas.quantity.Quantitys;
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Optionals;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.dao.QueryLimit;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MAttributeSet;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;

import static de.metas.util.Check.assumeNotNull;
import static org.adempiere.model.InterfaceWrapperHelper.loadOutOfTrx;

public final class ProductBL implements IProductBL
{
	private static final Logger logger = LogManager.getLogger(ProductBL.class);

	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IProductDAO productsRepo = Services.get(IProductDAO.class);
	private final IUOMDAO uomsRepo = Services.get(IUOMDAO.class);
	private final IClientDAO clientDAO = Services.get(IClientDAO.class);
	private final IAttributeDAO attributesRepo = Services.get(IAttributeDAO.class);
	private final IAcctSchemaDAO acctSchemasRepo = Services.get(IAcctSchemaDAO.class);
	private final IProductCostingBL productCostingBL = Services.get(IProductCostingBL.class);
	private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	private final IBPartnerProductDAO partnerProductDAO = Services.get(IBPartnerProductDAO.class);

	@Override
	public I_M_Product getById(@NonNull final ProductId productId)
	{
		return productsRepo.getById(productId);
	}

	@Override
	public I_M_Product getByIdInTrx(@NonNull final ProductId productId)
	{
		return productsRepo.getByIdInTrx(productId);
	}

	@Override
	public ProductId getProductIdByValue(
			@NonNull final OrgId orgId,
			@NonNull final String productValue)
	{
		final ProductQuery query = ProductQuery.builder()
				.orgId(orgId)
				.value(productValue).build();
		return productsRepo.retrieveProductIdBy(query);
	}

	@Override
	public UOMPrecision getUOMPrecision(final I_M_Product product)
	{
		final UomId uomId = UomId.ofRepoId(product.getC_UOM_ID());
		return uomsRepo.getStandardPrecision(uomId);
	}

	@Override
	public UOMPrecision getUOMPrecision(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return getUOMPrecision(product);
	}

	@Override
	public String getMMPolicy(final I_M_Product product)
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoId(product.getM_Product_Category_ID());
		final I_M_Product_Category pc = productsRepo.getProductCategoryById(productCategoryId);
		String policy = pc.getMMPolicy();
		if (policy == null || policy.isEmpty())
		{
			policy = clientDAO.retriveClient(Env.getCtx()).getMMPolicy();
		}
		return policy;
	}

	@Override
	public String getMMPolicy(final int productId)
	{
		Check.assume(productId > 0, "productId > 0");
		final I_M_Product product = loadOutOfTrx(productId, I_M_Product.class);
		return getMMPolicy(product);
	}

	@Override
	public I_C_UOM getStockUOM(@NonNull final I_M_Product product)
	{
		return uomsRepo.getById(product.getC_UOM_ID());
	}

	@Override
	public I_C_UOM getStockUOM(final int productId)
	{
		// we don't know if the product of productId was already committed, so we can't load it out-of-trx
		final I_M_Product product = InterfaceWrapperHelper.load(productId, I_M_Product.class);
		Check.assumeNotNull(product, "Unable to load M_Product record for M_Product_ID={}", productId);

		return Check.assumeNotNull(getStockUOM(product), "The uom for productId={} may not be null", productId);
	}

	@NotNull
	private I_C_UOM getNetWeightUOM()
	{
		// FIXME: we hardcoded the UOM for M_Product.Weight to Kilogram
		return uomsRepo.getByX12DE355(X12DE355.KILOGRAM);
	}

	@Override
	public Optional<Quantity> computeGrossWeight(@NonNull final ProductId productId, @NonNull final Quantity qty)
	{
		final Quantity unitWeight = getGrossWeight(productId, qty.getUOM()).orElse(null);
		if (unitWeight == null)
		{
			return Optional.empty();
		}

		final Quantity totalWeight = unitWeight.multiply(qty.toBigDecimal());
		return Optional.of(totalWeight);
	}

	@Override
	public Optional<Quantity> getGrossWeight(final ProductId productId, final I_C_UOM targetProductUOM)
	{
		final I_M_Product product = getById(productId);
		return getGrossWeight(product, targetProductUOM);
	}

	@Override
	public Optional<Quantity> getGrossWeight(final I_M_Product product, final I_C_UOM targetProductUOM)
	{
		return getGrossWeight(product)
				.map(weightForOneStockingUOM -> convertWeightFromStockingUOMToTargetUOM(weightForOneStockingUOM, product, targetProductUOM));
	}

	@Override
	public Optional<Quantity> getGrossWeight(final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return getGrossWeight(product);
	}

	private Optional<Quantity> getGrossWeight(final I_M_Product product)
	{
		final UomId weightUomId = UomId.ofRepoIdOrNull(product.getGrossWeight_UOM_ID());
		if (weightUomId == null || InterfaceWrapperHelper.isNull(product, I_M_Product.COLUMNNAME_GrossWeight))
		{
			return getNetWeight(product);
		}

		final BigDecimal weightBD = product.getGrossWeight();
		if (weightBD.signum() <= 0)
		{
			return getNetWeight(product);
		}

		return Optional.of(Quantitys.of(weightBD, weightUomId));
	}

	@Override
	public Optional<Quantity> getNetWeight(@NonNull final I_M_Product product)
	{
		final BigDecimal weightPerStockingUOM = product.getWeight();
		if (weightPerStockingUOM.signum() <= 0)
		{
			return Optional.empty();
		}

		return Optional.of(Quantity.of(weightPerStockingUOM, getNetWeightUOM()));
	}

	@Override
	public Optional<Quantity> getNetWeight(
			@NonNull final I_M_Product product,
			@NonNull final I_C_UOM targetProductUOM)
	{
		return getNetWeight(product)
				.map(weightPerOneStockingUOM -> convertWeightFromStockingUOMToTargetUOM(weightPerOneStockingUOM, product, targetProductUOM));
	}

	private Quantity convertWeightFromStockingUOMToTargetUOM(
			@NonNull final Quantity weightPerOneStockingUOM,
			@NonNull final I_M_Product product,
			@NonNull final I_C_UOM targetProductUOM)
	{
		final I_C_UOM stockingUom = getStockUOM(product);

		//
		// Calculate the rate to convert from stocking UOM to "uomTo"
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(product.getM_Product_ID());
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class); // don't extract it to field because IUOMConversionBL already has IProductBL as a field
		final BigDecimal stocking2uomToRate = uomConversionBL.convertQty(uomConversionCtx, BigDecimal.ONE, stockingUom, targetProductUOM);

		//
		// Calculate the Weight for one "uomTo"
		final UOMPrecision weightPerUomToPrecision = UOMPrecision.ofInt(getNetWeightUOM().getStdPrecision());

		return weightPerOneStockingUOM
				.multiply(stocking2uomToRate)
				.setScale(weightPerUomToPrecision);
	}

	@Override
	public boolean isService(final I_M_Product product)
	{
		// i.e. PRODUCTTYPE_Service, PRODUCTTYPE_Resource, PRODUCTTYPE_Online
		return ProductType.ofCode(product.getProductType()).isService();
	}

	@Override
	public boolean isStocked(@NonNull final I_M_Product product)
	{
		if (!product.isStocked())
		{
			logger.debug("isStocked - M_Product_ID={} has isStocked=false; -> return false", product.getM_Product_ID());
			return false;
		}

		final ProductType productType = ProductType.ofCode(product.getProductType());
		final boolean result = productType.isItem();

		logger.debug("isStocked - M_Product_ID={} is has isStocked=true and type={}; -> return {}", product.getM_Product_ID(), productType, result);
		return result;
	}

	@Override
	public boolean isStocked(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			logger.debug("isStocked - productId=null; -> return false");
			return false;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = getById(productId);
		return isStocked(product);
	}

	@Override
	public boolean isItemType(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			logger.debug("isItemType - productId=null; -> return false");
			return false;
		}

		// NOTE: we rely on table cache config
		final I_M_Product product = getById(productId);
		return isItemType(product);
	}

	private boolean isItemType(@NonNull final I_M_Product product)
	{
		final ProductType productType = ProductType.ofCode(product.getProductType());
		final boolean isItemProduct = productType.isItem();

		logger.debug("isItemProduct - M_Product_ID={} has type={}; -> return {}",
				product.getM_Product_ID(),
				productType,
				isItemProduct);
		return isItemProduct;
	}

	@Override
	public boolean isDiverse(@NonNull final ProductId productId)
	{
		return productsRepo
				.getById(productId, de.metas.adempiere.model.I_M_Product.class)
				.isDiverse();
	}

	@Override
	public AttributeSetId getAttributeSetId(final I_M_Product product)
	{
		final ProductCategoryId productCategoryId = ProductCategoryId.ofRepoIdOrNull(product.getM_Product_Category_ID());
		if (productCategoryId == null) // guard against NPE which might happen in unit tests
		{
			return AttributeSetId.NONE;
		}

		final I_M_Product_Category productCategoryRecord = productsRepo.getProductCategoryById(productCategoryId);
		final int attributeSetId = productCategoryRecord.getM_AttributeSet_ID();
		return attributeSetId > 0 ? AttributeSetId.ofRepoId(attributeSetId) : AttributeSetId.NONE;
	}

	@Override
	public AttributeSetId getAttributeSetId(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return getAttributeSetId(product);
	}

	@Override
	@Nullable
	public I_M_AttributeSet getAttributeSetOrNull(@NonNull final ProductId productId)
	{
		final AttributeSetId attributeSetId = getAttributeSetId(productId);
		if (attributeSetId.isNone())
		{
			return null;
		}

		return attributesRepo.getAttributeSetById(attributeSetId);
	}

	@Nullable
	@Override
	public I_M_AttributeSetInstance getCreateASI(
			final Properties ctx,
			final int M_AttributeSetInstance_ID,
			final int M_Product_ID)
	{
		// Load Instance if not 0
		if (M_AttributeSetInstance_ID > 0)
		{
			logger.debug("From M_AttributeSetInstance_ID={}", M_AttributeSetInstance_ID);
			return InterfaceWrapperHelper.create(ctx, M_AttributeSetInstance_ID, I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
		}

		// Get new from Product
		logger.debug("From M_Product_ID={}", M_Product_ID);
		if (M_Product_ID <= 0)
		{
			return null;
		}

		final AttributeSetId attributeSetId = getAttributeSetId(ProductId.ofRepoId(M_Product_ID));
		final I_M_AttributeSetInstance asi = InterfaceWrapperHelper.create(ctx, I_M_AttributeSetInstance.class, ITrx.TRXNAME_None);
		asi.setM_AttributeSet_ID(attributeSetId.getRepoId());
		return asi;
	}    // get

	@Override
	public boolean isTradingProduct(final I_M_Product product)
	{
		Check.assumeNotNull(product, "product not null");
		return product.isPurchased()
				&& product.isSold();
	}

	@Override
	public boolean isASIMandatory(
			@NonNull final I_M_Product product,
			final boolean isSOTrx)
	{

		final ClientId adClientId = ClientId.ofRepoId(product.getAD_Client_ID());
		final OrgId adOrgId = OrgId.ofRepoId(product.getAD_Org_ID());

		//
		// If CostingLevel is BatchLot ASI is always mandatory - check all client acct schemas
		for (final AcctSchema as : acctSchemasRepo.getAllByClient(adClientId))
		{
			if (as.isDisallowPostingForOrg(adOrgId))
			{
				continue;
			}

			final CostingLevel costingLevel = productCostingBL.getCostingLevel(product, as);
			if (CostingLevel.BatchLot == costingLevel)
			{
				return true;
			}
		}

		//
		// Check Attribute Set settings
		final AttributeSetId attributeSetId = getAttributeSetId(product);
		if (!attributeSetId.isNone())
		{
			final MAttributeSet mas = MAttributeSet.get(attributeSetId);
			if (mas == null || !mas.isInstanceAttribute())
			{
				return false;
			}
			// Outgoing transaction
			else if (isSOTrx)
			{
				return mas.isMandatory();
			}
			// Incoming transaction
			else
			{
				// isSOTrx == false
				return mas.isMandatoryAlways();
			}
		}
		//
		// Default not mandatory
		return false;
	}

	@Override
	public boolean isASIMandatory(
			@NonNull final ProductId productId,
			final boolean isSOTrx)
	{
		final I_M_Product product = getById(productId);
		return isASIMandatory(product, isSOTrx);
	}

	@Override
	public boolean isInstanceAttribute(@NonNull final ProductId productId)
	{
		final I_M_AttributeSet mas = getAttributeSetOrNull(productId);
		return mas != null && mas.isInstanceAttribute();
	}

	@Override
	public boolean isProductInCategory(
			final ProductId productId,
			final ProductCategoryId expectedProductCategoryId)
	{
		if (productId == null || expectedProductCategoryId == null)
		{
			return false;
		}

		final ProductCategoryId productCategoryId = productsRepo.retrieveProductCategoryByProductId(productId);
		return Objects.equals(productCategoryId, expectedProductCategoryId);
	}

	@Override
	public String getProductValueAndName(@Nullable final ProductId productId)
	{
		if (productId == null)
		{
			return "-";
		}

		try
		{
			final I_M_Product product = getById(productId);
			if (product == null)
			{
				return "<" + productId + ">";
			}
			return product.getValue() + "_" + product.getName();
		}
		catch (final Exception ex)
		{
			logger.warn("No product found for {}. Returning `<{}>`.", productId, productId, ex);
			return "<" + productId + ">";
		}
	}

	@Override
	public String getProductValue(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getValue();
	}

	@Override
	public GS1ProductCodesCollection getGS1ProductCodesCollection(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return getGS1ProductCodesCollection(product);
	}

	@Override
	public GS1ProductCodesCollection getGS1ProductCodesCollection(@NonNull final I_M_Product product)
	{
		final GS1ProductCodesCollectionBuilder result = GS1ProductCodesCollection.builder()
				.productValue(product.getValue())
				.defaultCodes(extractGS1ProductCodes(product));

		final ProductId productId = ProductId.ofRepoId(product.getM_Product_ID());
		for (final I_C_BPartner_Product bpartnerProductRecord : partnerProductDAO.retrieveForProductIds(ImmutableSet.of(productId)))
		{
			final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartnerProductRecord.getC_BPartner_ID());
			result.codes(bpartnerId, extractGS1ProductCodes(bpartnerProductRecord));
		}

		return result.build();
	}

	@NonNull
	private static GS1ProductCodes extractGS1ProductCodes(@NonNull final I_M_Product product)
	{
		return GS1ProductCodes.builder()
				.gtin(GTIN.ofNullableString(product.getGTIN()))
				.ean13ProductCode(EAN13ProductCode.ofNullableString(product.getEAN13_ProductCode()))
				.build();
	}

	@NonNull
	private static GS1ProductCodes extractGS1ProductCodes(@NonNull final I_C_BPartner_Product bpartnerProduct)
	{
		final String ean = StringUtils.trimBlankToNull(bpartnerProduct.getEAN_CU());

		return GS1ProductCodes.builder()
				.gtin(GTIN.ofNullableString(bpartnerProduct.getGTIN()))
				.ean13(ean != null ? EAN13.ofString(ean).orElse(null) : null)
				.ean13ProductCode(EAN13ProductCode.ofNullableString(bpartnerProduct.getEAN13_ProductCode()))
				.build();
	}

	@Override
	public Optional<GTIN> getGTIN(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return GTIN.optionalOfNullableString(product.getGTIN());
	}

	@Override
	public ImmutableMap<ProductId, String> getProductValues(@NonNull final Set<ProductId> productIds)
	{
		if (productIds.isEmpty())
		{
			return ImmutableMap.of();
		}

		return productsRepo.getByIds(productIds)
				.stream()
				.collect(ImmutableMap.toImmutableMap(
						product -> ProductId.ofRepoId(product.getM_Product_ID()),
						I_M_Product::getValue));
	}

	@Override
	public String getProductName(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		return buildProductName(product, productId);
	}

	private static String buildProductName(@Nullable final I_M_Product product, @NonNull final ProductId productId)
	{
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getName();
	}

	@Override
	public Map<ProductId, String> getProductNames(@NonNull final Set<ProductId> productIds)
	{
		final List<I_M_Product> products = getByIds(productIds);
		if (products.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ImmutableMap<ProductId, I_M_Product> productsById = Maps.uniqueIndex(products, product -> ProductId.ofRepoId(product.getM_Product_ID()));

		final HashMap<ProductId, String> result = new HashMap<>();
		for (final ProductId productId : productIds)
		{
			final I_M_Product product = productsById.get(productId);
			result.put(productId, buildProductName(product, productId));
		}

		return result;
	}

	@Override
	public Optional<UomId> getCatchUOMId(@NonNull final ProductId productId)
	{
		final ImmutableSet<UomId> catchUomIds = uomConversionDAO.getProductConversions(productId).getCatchUomIds();
		return uomsRepo.getByIds(catchUomIds)
				.stream()
				.filter(I_C_UOM::isActive)
				.filter(uom -> UOMType.ofNullableCodeOrOther(uom.getUOMType()).isWeight())
				.map(uom -> UomId.ofRepoId(uom.getC_UOM_ID()))
				.sorted()
				.findFirst();
	}

	@Override
	public ProductType getProductType(@NonNull final ProductId productId)
	{
		final I_M_Product product = assumeNotNull(getById(productId), "M_Product record with M_Product_ID={} needs to exist", productId.getRepoId());
		return ProductType.ofCode(product.getProductType());
	}

	@Override
	public ProductCategoryId getDefaultProductCategoryId()
	{
		return productsRepo.getDefaultProductCategoryId();
	}

	@Override
	public ITranslatableString getProductNameTrl(@NonNull final ProductId productId)
	{
		final I_M_Product product = getById(productId);
		if (product == null)
		{
			return TranslatableStrings.anyLanguage("<" + productId + ">");
		}

		return InterfaceWrapperHelper.getModelTranslationMap(product)
				.getColumnTrl(I_M_Product.COLUMNNAME_Name, product.getName());
	}

	@Override
	public ProductId retrieveMappedProductIdOrNull(final ProductId productId, final OrgId orgId)
	{
		return productsRepo.retrieveMappedProductIdOrNull(productId, orgId);
	}

	@Override
	public boolean isHaddexProduct(final ProductId productId)
	{
		final org.compiere.model.I_M_Product product = getById(productId);

		return product.isHaddexCheck();
	}

	@Nullable
	@Override
	public I_M_AttributeSet getProductMasterDataSchemaOrNull(@NonNull final ProductId productId)
	{
		final AttributeSetId attributeSetId = getMasterDataSchemaAttributeSetId(productId);
		if (attributeSetId.isNone())
		{
			return null;
		}

		return attributesRepo.getAttributeSetById(attributeSetId);
	}

	@NonNull
	@Override
	public AttributeSetId getMasterDataSchemaAttributeSetId(@NonNull final ProductId productId)
	{
		final I_M_Product product = productsRepo.getById(productId);

		final int attributeSetRepoId = product.getM_AttributeSet_ID();

		return AttributeSetId.ofRepoIdOrNone(attributeSetRepoId);
	}

	@Override
	public ImmutableList<String> retrieveSupplierApprovalNorms(@NonNull final ProductId productId)
	{
		final I_M_Product product = productsRepo.getById(productId);

		if (!product.isRequiresSupplierApproval())
		{
			return ImmutableList.of();
		}

		return productsRepo.retrieveSupplierApprovalNorms(productId);
	}

	@Override
	public boolean isDiscontinuedAt(
			@NonNull final I_M_Product productRecord,
			@NonNull final LocalDate targetDate)
	{
		if (!productRecord.isDiscontinued())
		{
			return false;
		}

		final ZoneId zoneId = orgDAO.getTimeZone(OrgId.ofRepoId(productRecord.getAD_Org_ID()));

		return productRecord.getDiscontinuedFrom() == null
				|| TimeUtil.asLocalDate(productRecord.getDiscontinuedFrom(), zoneId).compareTo(targetDate) <= 0;
	}

	@Override
	public Optional<IssuingToleranceSpec> getIssuingToleranceSpec(@NonNull final ProductId productId)
	{
		return productsRepo.getIssuingToleranceSpec(productId);
	}

	@Override
	@NonNull
	public ImmutableList<I_M_Product> getByIdsInTrx(@NonNull final Set<ProductId> productIds)
	{
		return productsRepo.getByIdsInTrx(productIds);
	}

	@Override
	public Optional<ProductId> getProductIdByBarcode(@NonNull final String barcode, @NonNull final ClientId clientId)
	{
		return productsRepo.getProductIdByBarcode(barcode, clientId);
	}

	@Override
	public Optional<ProductId> getProductIdByGTIN(@NonNull final GTIN gtin)
	{
		return getProductIdByGTIN(gtin, null, ClientId.METASFRESH);
	}

	@Override
	public Optional<ProductId> getProductIdByGTIN(@NonNull final GTIN gtin, @Nullable final BPartnerId bpartnerId, @NonNull final ClientId clientId)
	{
		final EAN13 ean13 = gtin.toEAN13().orElse(null);

		//noinspection OptionalAssignedToNull
		return Optionals.firstPresentOfSuppliers(
				() -> gtin.isFixed() ? getProductIdByGTINStrictly(gtin, bpartnerId, clientId) : null,
				() -> ean13 != null && ean13.isVariable() ? getProductIdByEAN13ProductCode(ean13.getProductNo(), bpartnerId, clientId) : null,
				() -> ean13 != null && ean13.isVariableWeight() ? productsRepo.getProductIdByValueStartsWith(ean13.getProductNo().getAsString(), clientId) : null
		);
	}

	private Optional<ProductId> getProductIdByGTINStrictly(@NonNull final GTIN gtin, @Nullable final BPartnerId bpartnerId, @NonNull final ClientId clientId)
	{
		if (bpartnerId != null)
		{
			final ImmutableSet<ProductId> productIds = partnerProductDAO.retrieveByGTIN(gtin, bpartnerId)
					.stream()
					.map(partnerProduct -> ProductId.ofRepoId(partnerProduct.getM_Product_ID()))
					.collect(ImmutableSet.toImmutableSet());
			if (productIds.size() == 1)
			{
				return Optional.of(productIds.iterator().next());
			}
		}

		return productsRepo.getProductIdByGTINStrictly(gtin, clientId);
	}

	@Override
	public Optional<ProductId> getProductIdByGTINStrictly(@NonNull final GTIN gtin, @NonNull final ClientId clientId)
	{
		return productsRepo.getProductIdByGTINStrictly(gtin, clientId);
	}

	@Override
	public ProductId getProductIdByGTINStrictlyNotNull(@NonNull final GTIN gtin, @NonNull final ClientId clientId)
	{
		return getProductIdByGTINStrictly(gtin, clientId)
				.orElseThrow(() -> new AdempiereException("@NotFound@ @M_Product_ID@: @GTIN@ " + gtin));
	}

	@Override
	public Optional<ProductId> getProductIdByEAN13(@NonNull final EAN13 ean13) {return getProductIdByEAN13(ean13, null, ClientId.METASFRESH);}

	@Override
	public Optional<ProductId> getProductIdByEAN13(@NonNull final EAN13 ean13, @Nullable final BPartnerId bpartnerId, @NonNull final ClientId clientId)
	{
		return getProductIdByGTIN(ean13.toGTIN(), bpartnerId, clientId);
	}

	private Optional<ProductId> getProductIdByEAN13ProductCode(
			@NonNull final EAN13ProductCode ean13ProductCode,
			@Nullable final BPartnerId bpartnerId,
			@NonNull final ClientId clientId)
	{
		if (bpartnerId != null)
		{
			final ImmutableSet<ProductId> productIds = partnerProductDAO.retrieveByEAN13ProductCode(ean13ProductCode, bpartnerId)
					.stream()
					.map(partnerProduct -> ProductId.ofRepoId(partnerProduct.getM_Product_ID()))
					.collect(ImmutableSet.toImmutableSet());
			if (productIds.size() == 1)
			{
				return Optional.of(productIds.iterator().next());
			}
		}

		return productsRepo.getProductIdByEAN13ProductCode(ean13ProductCode, clientId);
	}

	@Override
	public boolean isValidEAN13Product(@NonNull final EAN13 ean13, @NonNull final ProductId expectedProductId)
	{
		return isValidEAN13Product(ean13, expectedProductId);
	}

	@Override
	public boolean isValidEAN13Product(@NonNull final EAN13 ean13, @NonNull final ProductId expectedProductId, @Nullable final BPartnerId bpartnerId)
	{
		return getGS1ProductCodesCollection(expectedProductId).isValidProductNo(ean13, bpartnerId);
	}

	@Override
	public Set<ProductId> getProductIdsMatchingQueryString(
			@NonNull final String queryString,
			@NonNull final ClientId clientId,
			@NonNull final QueryLimit limit)
	{
		return productsRepo.getProductIdsMatchingQueryString(queryString, clientId, limit);
	}

	@Override
	@NonNull
	public List<I_M_Product> getByIds(@NonNull final Set<ProductId> productIds)
	{
		return productsRepo.getByIds(productIds);
	}

	@Override
	public boolean isExistingValue(@NonNull final String value, @NonNull final ClientId clientId)
	{
		return productsRepo.isExistingValue(value, clientId);
	}

	@Override
	public void setProductCodeFieldsFromGTIN(@NonNull final I_M_Product record, @Nullable final GTIN gtin)
	{
		record.setGTIN(gtin != null ? gtin.getAsString() : null);
		record.setUPC(gtin != null ? gtin.getAsString() : null);

		if (gtin != null)
		{
			record.setEAN13_ProductCode(null);
		}
	}

	@Override
	public void setProductCodeFieldsFromEAN13ProductCode(@NonNull final I_M_Product record, @Nullable final EAN13ProductCode ean13ProductCode)
	{
		record.setEAN13_ProductCode(ean13ProductCode != null ? ean13ProductCode.getAsString() : null);
		if (ean13ProductCode != null)
		{
			record.setGTIN(null);
			record.setUPC(null);
		}
	}

}
