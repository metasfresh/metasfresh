package de.metas.frontend_testing.masterdata.product;

import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.organization.OrgId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.ProductCategoryId;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.CreateUOMConversionRequest;
import de.metas.uom.IUOMConversionDAO;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_M_Product;
import org.compiere.model.I_M_ProductPrice;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class CreateProductCommand
{
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final IUOMConversionDAO uomConversionDAO = Services.get(IUOMConversionDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonCreateProductRequest request;

	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;
	@NonNull private final ProductCategoryId productCategoryId = MasterdataContext.PRODUCT_CATEGORY_STANDARD_ID;
	@Nullable private final Identifier suggestedIdentifier;
	private ProductId productId;
	private UomId productUomId;

	@Builder
	private CreateProductCommand(
			@NonNull final MasterdataContext context,
			@NonNull final JsonCreateProductRequest request,
			@Nullable final String identifier)
	{
		this.context = context;
		this.request = request;
		this.suggestedIdentifier = Identifier.ofNullableString(identifier);
	}

	public JsonCreateProductResponse execute()
	{
		final I_M_Product productRecord = createProduct();
		createUOMConversions();
		createPrices();

		return JsonCreateProductResponse.builder()
				.productCode(productRecord.getValue())
				.build();
	}

	public I_M_Product createProduct()
	{
		final Identifier identifier;
		final String value;
		if (suggestedIdentifier == null)
		{
			identifier = Identifier.unique("P");
			value = identifier.getAsString();
		}
		else
		{
			identifier = suggestedIdentifier;
			value = suggestedIdentifier.toAsUniqueString();
		}

		final I_M_Product productRecord = newInstanceOutOfTrx(I_M_Product.class);
		this.productUomId = Optional.ofNullable(request.getUom()).map(uomDAO::getUomIdByX12DE355).orElse(UomId.EACH);

		productRecord.setAD_Org_ID(orgId.getRepoId());
		productRecord.setValue(value);
		productRecord.setName(value);
		productRecord.setC_UOM_ID(productUomId.getRepoId());
		productRecord.setProductType(ProductType.Item.getCode());
		productRecord.setIsStocked(true);
		productRecord.setM_Product_Category_ID(productCategoryId.getRepoId());
		productRecord.setIsSold(true);
		productRecord.setIsPurchased(true);
		InterfaceWrapperHelper.saveRecord(productRecord);
		this.productId = ProductId.ofRepoId(productRecord.getM_Product_ID());
		context.putIdentifier(identifier, productId);

		return productRecord;
	}

	private void createUOMConversions()
	{
		final List<JsonCreateProductRequest.UOMConversion> uomConversions = request.getUomConversions();
		if (uomConversions == null || uomConversions.isEmpty())
		{
			return;
		}

		uomConversions.forEach(this::createUOMConversion);
	}

	private void createUOMConversion(final JsonCreateProductRequest.UOMConversion uomConversion)
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

	private void createPrices()
	{
		final List<JsonCreateProductRequest.Price> prices = request.getPrices();
		if (prices == null || prices.isEmpty())
		{
			return;
		}

		prices.forEach(this::createPrice);
	}

	private void createPrice(final JsonCreateProductRequest.Price priceRequest)
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
}
