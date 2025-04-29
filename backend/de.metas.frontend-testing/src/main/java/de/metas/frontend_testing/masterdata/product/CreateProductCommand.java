package de.metas.frontend_testing.masterdata.product;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner_product.BPartnerProductQuery;
import de.metas.bpartner_product.CreateBPartnerProductRequest;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.ean13.EAN13;
import de.metas.ean13.EAN13ProductCode;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.logging.LogManager;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.IProductBL;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.product.ProductType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.InSetPredicate;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.util.TimeUtil;
import org.eevolution.api.BOMComponentType;
import org.eevolution.api.BOMType;
import org.eevolution.api.BOMUse;
import org.eevolution.api.ProductBOMId;
import org.eevolution.api.ProductBOMVersionsId;
import org.eevolution.model.I_PP_Product_BOM;
import org.eevolution.model.I_PP_Product_BOMLine;
import org.eevolution.model.I_PP_Product_BOMVersions;
import org.eevolution.model.X_PP_Product_BOM;
import org.slf4j.Logger;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

@Builder
public class CreateProductCommand
{
	@NonNull private static final Logger logger = LogManager.getLogger(CreateProductCommand.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	@NonNull private final ProductRepository productRepository; // for C_BPartner_Product

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateProductRequest request;

	@NonNull private final Identifier identifier;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;
	@NonNull private final ProductCategoryId productCategoryId = MasterdataContext.PRODUCT_CATEGORY_STANDARD_ID;

	public JsonCreateProductResponse execute()
	{
		final I_M_Product productRecord = createProduct();
		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		final UomId productUomId = UomId.ofRepoId(productRecord.getC_UOM_ID());

		createUOMConversions(productId);
		createPrices(productId, productUomId);
		createBPartnerProducts(productId);
		createBOM(productRecord);

		return JsonCreateProductResponse.builder()
				.id(ProductId.ofRepoId(productRecord.getM_Product_ID()))
				.productCode(productRecord.getValue())
				.ean13ProductCode(EAN13ProductCode.ofNullableString(productRecord.getEAN13_ProductCode()))
				.build();
	}

	public I_M_Product createProduct()
	{
		final String valuePrefix = StringUtils.trimBlankToNull(request.getValuePrefix());
		final String value = valuePrefix != null ? Identifier.ofString(valuePrefix).toUniqueString() : identifier.toUniqueString();

		final I_M_Product productRecord = newInstanceOutOfTrx(I_M_Product.class);
		final UomId productUomId = Optional.ofNullable(request.getUom()).map(uomDAO::getUomIdByX12DE355).orElse(UomId.EACH);

		productRecord.setAD_Org_ID(orgId.getRepoId());
		productRecord.setValue(value);
		productRecord.setName(value);
		productRecord.setGTIN(request.getGtin());
		productRecord.setEAN13_ProductCode(request.getEan13ProductCode());
		productRecord.setC_UOM_ID(productUomId.getRepoId());
		productRecord.setProductType(ProductType.Item.getCode());
		productRecord.setIsStocked(true);
		productRecord.setM_Product_Category_ID(productCategoryId.getRepoId());
		productRecord.setIsSold(true);
		productRecord.setIsPurchased(true);
		InterfaceWrapperHelper.saveRecord(productRecord);

		final ProductId productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		context.putIdentifier(identifier, productId);

		return productRecord;
	}

	private void createUOMConversions(@NonNull final ProductId productId)
	{
		final List<JsonCreateProductRequest.UOMConversion> uomConversions = request.getUomConversions();
		if (uomConversions == null || uomConversions.isEmpty())
		{
			return;
		}

		uomConversions.forEach(uomConversion -> createUOMConversion(uomConversion, productId));
	}

	private void createUOMConversion(final JsonCreateProductRequest.UOMConversion uomConversion, final ProductId productId)
	{
		final UomId fromUomId = uomDAO.getUomIdByX12DE355(uomConversion.getFrom());
		final UomId toUomId = uomDAO.getUomIdByX12DE355(uomConversion.getTo());
		final BigDecimal fromToMultiplier = uomConversion.getMultiplyRate();
		final boolean isCatchUomForProduct = uomConversion.isCatchUOMForProduct();

		final CreateUOMConversionRequest uomConversionRequest = CreateUOMConversionRequest.builder()
				.productId(productId)
				.fromUomId(fromUomId)
				.toUomId(toUomId)
				.fromToMultiplier(fromToMultiplier)
				.catchUOMForProduct(isCatchUomForProduct)
				.build();

		uomConversionDAO.createUOMConversion(uomConversionRequest);
	}

	private void createPrices(@NonNull final ProductId productId, @NonNull final UomId productUomId)
	{
		final List<JsonCreateProductRequest.Price> prices = extractEffectivePricesRequest(request);
		prices.forEach(price -> createPrice(price, productId, productUomId));
	}

	@NonNull
	private static List<JsonCreateProductRequest.Price> extractEffectivePricesRequest(@NonNull final JsonCreateProductRequest request)
	{
		final List<JsonCreateProductRequest.Price> prices = request.getPrices();
		if (prices != null)
		{
			return prices;
		}

		if (request.getPrice() != null)
		{
			return ImmutableList.of(
					JsonCreateProductRequest.Price.builder()
							.price(request.getPrice())
							.build()
			);
		}

		return ImmutableList.of();
	}

	private void createPrice(final JsonCreateProductRequest.Price priceRequest, @NonNull final ProductId productId, @NonNull final UomId productUomId)
	{
		final BigDecimal priceStd = priceRequest.getPrice();
		final UomId uomId = Optional.ofNullable(priceRequest.getUom()).map(uomDAO::getUomIdByX12DE355).orElse(productUomId);
		final PriceListVersionId priceListVersionId = context.getIdOfType(PriceListVersionId.class);
		final InvoicableQtyBasedOn invoicableQtyBasedOn = priceRequest.getInvoicableQtyBasedOn() != null ? priceRequest.getInvoicableQtyBasedOn() : InvoicableQtyBasedOn.NominalWeight;

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class);
		productPrice.setIsActive(true);
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setC_UOM_ID(uomId.getRepoId());
		productPrice.setPriceStd(priceStd);
		productPrice.setC_TaxCategory_ID(getTaxCategoryId().getRepoId());
		productPrice.setInvoicableQtyBasedOn(invoicableQtyBasedOn.getCode());
		saveRecord(productPrice);
	}

	private TaxCategoryId getTaxCategoryId()
	{
		final String taxCategoryInternalName = MasterdataContext.DEFAULT_TaxCategory_InternalName;
		return taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName)
				.orElseThrow(() -> new AdempiereException("Missing C_TaxCategory for internalName `" + taxCategoryInternalName + "`"));
	}

	private void createBPartnerProducts(@NonNull final ProductId productId)
	{
		final List<JsonCreateProductRequest.BPartner> bpartners = request.getBpartners();
		if (bpartners == null || bpartners.isEmpty())
		{
			return;
		}

		bpartners.forEach(bpartner -> createBPartnerProduct(bpartner, productId));
	}

	private void createBPartnerProduct(@NonNull final JsonCreateProductRequest.BPartner bpartner, @NonNull final ProductId productId)
	{
		final BPartnerId bpartnerId = context.getId(bpartner.getBpartner(), BPartnerId.class);
		final EAN13 cuEAN = StringUtils.trimBlankToOptional(bpartner.getCu_ean())
				.map(string -> EAN13.fromString(string).orElseThrow())
				.orElse(null);

		//
		// Make sure there are no previous C_BPartner_Products with the same EAN13 because that will fail our tests
		if (cuEAN != null)
		{
			productRepository.updateBPartnerProductsByQuery(
					BPartnerProductQuery.builder().cuEANs(InSetPredicate.only(cuEAN)).build(),
					bpartnerProduct -> {
						logger.info("Updating CU EAN of bpartner product {} to null", bpartnerProduct);
						return bpartnerProduct.toBuilder().cuEAN(null).build();
					}
			);
		}

		productRepository.createBPartnerProduct(CreateBPartnerProductRequest.builder()
				.productId(productId)
				.bPartnerId(bpartnerId)
				.usedForCustomer(true)
				.cuEAN(cuEAN != null ? cuEAN.getAsString() : null)
				.build());
	}

	private void createBOM(@NonNull final I_M_Product productRecord)
	{
		final JsonCreateProductRequest.BOM bom = request.getBom();
		if (bom == null)
		{
			return;
		}

		final List<JsonCreateProductRequest.BOMLine> bomLines = bom.getLines();
		if (bomLines.isEmpty())
		{
			throw new AdempiereException("BOM must have at least one line");
		}

		final I_PP_Product_BOM bomRecord = createPP_Product_BOM(productRecord);
		bomLines.forEach(bomLine -> createPP_Product_BOMLine(bomLine, bomRecord));
		bomRecord.setDocAction(IDocument.ACTION_Complete);
		documentBL.processEx(bomRecord, IDocument.ACTION_Complete, IDocument.STATUS_Completed);

		productRecord.setIsBOM(true);
		productRecord.setIsVerified(true);
		saveRecord(productRecord);
	}

	private I_PP_Product_BOM createPP_Product_BOM(@NonNull final I_M_Product productRecord)
	{
		final ProductBOMVersionsId bomVersionsId = createPP_Product_BOMVersions(productRecord);

		final I_PP_Product_BOM bomRecord = newInstance(I_PP_Product_BOM.class);

		bomRecord.setM_Product_ID(productRecord.getM_Product_ID());
		bomRecord.setC_UOM_ID(productRecord.getC_UOM_ID());
		bomRecord.setValue(productRecord.getValue());
		bomRecord.setName(productRecord.getName());
		bomRecord.setBOMType(BOMType.CurrentActive.getCode());
		bomRecord.setBOMUse(BOMUse.Manufacturing.getCode());
		bomRecord.setValidFrom(TimeUtil.asTimestamp(MasterdataContext.DEFAULT_ValidFrom));
		bomRecord.setPP_Product_BOMVersions_ID(bomVersionsId.getRepoId());
		bomRecord.setC_DocType_ID(getBOMDocTypeId().getRepoId());
		bomRecord.setDateDoc(TimeUtil.asTimestamp(Instant.now()));
		bomRecord.setDocStatus(X_PP_Product_BOM.DOCSTATUS_Drafted);
		saveRecord(bomRecord);
		return bomRecord;
	}

	private ProductBOMVersionsId createPP_Product_BOMVersions(@NonNull final I_M_Product productRecord)
	{
		final I_PP_Product_BOMVersions record = newInstance(I_PP_Product_BOMVersions.class);
		record.setM_Product_ID(productRecord.getM_Product_ID());
		record.setName(productRecord.getName());

		saveRecord(record);
		final ProductBOMVersionsId bomVersionsId = ProductBOMVersionsId.ofRepoId(record.getPP_Product_BOMVersions_ID());
		context.putIdentifier(identifier, bomVersionsId);

		return bomVersionsId;
	}

	private DocTypeId getBOMDocTypeId()
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.BillOfMaterialVersion)
				.clientAndOrgId(MasterdataContext.CLIENT_ID, orgId)
				.build());
	}

	private void createPP_Product_BOMLine(final JsonCreateProductRequest.BOMLine line, final I_PP_Product_BOM bomRecord)
	{
		final ProductBOMId bomId = ProductBOMId.ofRepoId(bomRecord.getPP_Product_BOM_ID());
		final ProductId lineProductId = context.getId(line.getProduct(), ProductId.class);

		final I_PP_Product_BOMLine lineRecord = newInstance(I_PP_Product_BOMLine.class);
		lineRecord.setPP_Product_BOM_ID(bomId.getRepoId());
		lineRecord.setM_Product_ID(lineProductId.getRepoId());

		final UomId uomId = line.getUom() != null ? uomDAO.getUomIdByX12DE355(line.getUom()) : productBL.getStockUOMId(lineProductId);
		lineRecord.setC_UOM_ID(uomId.getRepoId());

		lineRecord.setComponentType(BOMComponentType.Component.getCode());
		lineRecord.setValidFrom(bomRecord.getValidFrom());

		if (line.isPercentage())
		{
			lineRecord.setIsQtyPercentage(true);
			lineRecord.setQtyBatch(line.getQty());
		}
		else
		{
			lineRecord.setIsQtyPercentage(false);
			lineRecord.setQtyBOM(line.getQty());
		}

		saveRecord(lineRecord);
	}
}
