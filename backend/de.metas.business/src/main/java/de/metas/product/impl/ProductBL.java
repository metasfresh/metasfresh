package de.metas.product.impl;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import de.metas.acct.api.AcctSchema;
import de.metas.acct.api.IAcctSchemaDAO;
import de.metas.costing.CostingLevel;
import de.metas.costing.IProductCostingBL;
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
import de.metas.uom.IUOMConversionBL;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UOMConversionContext;
import de.metas.uom.UOMPrecision;
import de.metas.uom.UOMType;
import de.metas.uom.UomId;
import de.metas.uom.X12DE355;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.mm.attributes.AttributeSetId;
import org.adempiere.mm.attributes.api.IAttributeDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_AttributeSet;
import org.compiere.model.I_M_AttributeSetInstance;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_Product_Category;
import org.compiere.model.MAttributeSet;
import org.compiere.util.Env;
import org.compiere.util.TimeUtil;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.ZoneId;
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

	/**
	 * @return UOM used for Product's Weight; never return null
	 */
	public I_C_UOM getWeightUOM(final I_M_Product product)
	{
		// FIXME: we hardcoded the UOM for M_Product.Weight to Kilogram
		return uomsRepo.getByX12DE355(X12DE355.KILOGRAM);
	}

	@Override
	public BigDecimal getWeight(
			@NonNull final I_M_Product product,
			@NonNull final I_C_UOM uomTo)
	{
		final BigDecimal weightPerStockingUOM = product.getWeight();
		if (weightPerStockingUOM.signum() == 0)
		{
			return BigDecimal.ZERO;
		}

		final I_C_UOM stockingUom = getStockUOM(product);

		//
		// Calculate the rate to convert from stocking UOM to "uomTo"
		final UOMConversionContext uomConversionCtx = UOMConversionContext.of(product.getM_Product_ID());
		final IUOMConversionBL uomConversionBL = Services.get(IUOMConversionBL.class); // don't extract it to field because IUOMConversionBL already has IProductBL as a field
		final BigDecimal stocking2uomToRate = uomConversionBL.convertQty(uomConversionCtx, BigDecimal.ONE, stockingUom, uomTo);

		//
		// Calculate the Weight for one "uomTo"
		final int weightPerUomToPrecision = getWeightUOM(product).getStdPrecision();
		final BigDecimal weightPerUomTo = weightPerStockingUOM
				.multiply(stocking2uomToRate)
				.setScale(weightPerUomToPrecision, RoundingMode.HALF_UP);

		return weightPerUomTo;
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
		if (product == null)
		{
			return "<" + productId + ">";
		}
		return product.getName();
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
}
