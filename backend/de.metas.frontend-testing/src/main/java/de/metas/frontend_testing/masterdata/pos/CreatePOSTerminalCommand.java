package de.metas.frontend_testing.masterdata.pos;

import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPBankAccountDAO;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.PricingSetupHelper;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pos.POSPaymentMethod;
import de.metas.pos.POSTerminalId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.productprice.CreateProductPriceRequest;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateWarehouseRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_POS;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Creates a POS terminal ({@code C_POS}) for frontend/mobile testing: a dedicated cashbook, a fresh
 * sales pricing setup ({@code M_PricingSystem} + {@code M_PriceList} + {@code M_PriceList_Version} with
 * {@code M_ProductPrice} for the requested products), a walk-in customer, a ship-from warehouse and the
 * sales-order document type. Also grants the {@code pos} mobile application to {@link RoleId#WEBUI}.
 */
@Builder
public class CreatePOSTerminalCommand
{
	private static final String POS_MOBILE_APPLICATION_VALUE = "pos";

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final ProductPriceRepository productPriceRepository;
	@NonNull private final MobileApplicationInfoRepository mobileApplicationInfoRepository;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPOSTerminalRequest request;
	@NonNull private final Identifier identifier;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;

	public JsonPOSTerminalResponse execute()
	{
		assertOnlyCashPaymentMethod();

		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getPriceListCurrency()));

		final BankAccountId bankAccountId = createCashbookBankAccount(currencyId);

		final PricingSetupHelper.PricingSetupResult pricingSetup = PricingSetupHelper.createPricingSystemAndPriceList(
				orgId,
				identifier.toUniqueString(),
				currencyId,
				MasterdataContext.COUNTRY_ID,
				request.isTaxIncluded(),
				true); // isSoPriceList
		createProductPrices(pricingSetup.getPriceListVersionId(), request.getProducts());

		final BPartnerId walkInBPartnerId = resolveOrCreateWalkInBPartner();
		final WarehouseId warehouseId = createShipFromWarehouse();
		final DocTypeId salesOrderDocTypeId = getSalesOrderDocTypeId();

		grantPOSMobileApplicationAccess();

		final I_C_POS posRecord = InterfaceWrapperHelper.newInstance(I_C_POS.class);
		posRecord.setAD_Org_ID(orgId.getRepoId());
		posRecord.setName(identifier.toUniqueString());
		posRecord.setIsActive(true);
		posRecord.setIsModifyPrice(false);
		posRecord.setCashLastBalance(BigDecimal.ZERO);
		posRecord.setC_BPartnerCashTrx_ID(walkInBPartnerId.getRepoId());
		posRecord.setC_BP_BankAccount_ID(bankAccountId.getRepoId());
		posRecord.setC_DocTypeOrder_ID(salesOrderDocTypeId.getRepoId());
		posRecord.setM_PriceList_ID(pricingSetup.getPriceListId().getRepoId());
		posRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		InterfaceWrapperHelper.saveRecord(posRecord);

		final POSTerminalId posTerminalId = POSTerminalId.ofRepoId(posRecord.getC_POS_ID());
		context.putIdentifier(identifier, posTerminalId);

		return JsonPOSTerminalResponse.builder()
				.id(posTerminalId)
				.walkInBPartnerId(walkInBPartnerId)
				.bankAccountId(bankAccountId)
				.isCashJournalOpen(false)
				.build();
	}

	/**
	 * Only {@code CASH} is provisioned so far (no SumUp/card payment-processor config wiring in this
	 * command) — fail loud rather than silently ignore an unsupported {@code paymentMethods} entry.
	 */
	private void assertOnlyCashPaymentMethod()
	{
		final List<POSPaymentMethod> paymentMethods = request.getPaymentMethods();
		final boolean onlyCash = paymentMethods.stream().allMatch(POSPaymentMethod::isCash);
		if (!onlyCash)
		{
			throw new AdempiereException("CreatePOSTerminalCommand currently supports only the CASH payment method; got: " + paymentMethods);
		}
	}

	private BankAccountId createCashbookBankAccount(@NonNull final CurrencyId currencyId)
	{
		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.newInstance(I_C_BP_BankAccount.class);
		bankAccount.setC_BPartner_ID(MasterdataContext.METASFRESH_ORG_BPARTNER_ID.getRepoId());
		bankAccount.setAD_Org_ID(orgId.getRepoId());
		bankAccount.setC_Currency_ID(currencyId.getRepoId());
		bankAccount.setIsActive(true);
		bpBankAccountDAO.save(bankAccount);
		return BankAccountId.ofRepoId(bankAccount.getC_BP_BankAccount_ID());
	}

	private void createProductPrices(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final Map<String, JsonPOSTerminalRequest.ProductPrice> products)
	{
		products.forEach((productIdentifierStr, priceSpec) -> createProductPrice(priceListVersionId, Identifier.ofString(productIdentifierStr), priceSpec));
	}

	private void createProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final Identifier productIdentifier,
			@NonNull final JsonPOSTerminalRequest.ProductPrice priceSpec)
	{
		final ProductId productId = context.getId(productIdentifier, ProductId.class);
		final UomId uomId = priceSpec.getUom() != null
				? uomDAO.getUomIdByX12DE355(priceSpec.getUom())
				: productBL.getStockUOMId(productId);
		final InvoicableQtyBasedOn invoicableQtyBasedOn = priceSpec.getInvoicableQtyBasedOn() != null
				? priceSpec.getInvoicableQtyBasedOn()
				: InvoicableQtyBasedOn.NominalWeight;

		productPriceRepository.createProductPrice(CreateProductPriceRequest.builder()
				.orgId(orgId)
				.productId(productId)
				.priceListVersionId(priceListVersionId)
				.priceStd(priceSpec.getPrice())
				.uomId(uomId)
				.taxCategoryId(getTaxCategoryId())
				.invoicableQtyBasedOn(invoicableQtyBasedOn)
				.build());
	}

	private TaxCategoryId getTaxCategoryId()
	{
		final String taxCategoryInternalName = MasterdataContext.DEFAULT_TaxCategory_InternalName;
		return taxBL.getTaxCategoryIdByInternalName(taxCategoryInternalName)
				.orElseThrow(() -> new AdempiereException("Missing C_TaxCategory for internalName `" + taxCategoryInternalName + "`"));
	}

	private BPartnerId resolveOrCreateWalkInBPartner()
	{
		final Identifier walkInCustomer = request.getWalkInCustomer();
		if (walkInCustomer != null)
		{
			return context.getId(walkInCustomer, BPartnerId.class);
		}

		final JsonCreateBPartnerResponse response = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(JsonCreateBPartnerRequest.builder().build())
				.identifier(identifier.getAsString() + "_walkIn")
				.build()
				.execute();
		return response.getId();
	}

	private WarehouseId createShipFromWarehouse()
	{
		final String value = identifier.toUniqueString();

		return warehouseBL.createWarehouse(CreateWarehouseRequest.builder()
				.orgId(orgId)
				.value(value)
				.name(value)
				.partnerLocationId(MasterdataContext.METASFRESH_ORG_BPARTNER_LOCATION_ID)
				.active(true)
				.build())
				.getId();
	}

	private DocTypeId getSalesOrderDocTypeId()
	{
		return docTypeDAO.getDocTypeId(DocTypeQuery.builder()
				.docBaseType(DocBaseType.SalesOrder)
				.clientAndOrgId(MasterdataContext.CLIENT_ID, orgId)
				.build());
	}

	/** Grants the {@code pos} mobile application to {@link RoleId#WEBUI}, the masterdata-API login users' role. */
	private void grantPOSMobileApplicationAccess()
	{
		final MobileApplicationRepoId posApplicationId = mobileApplicationInfoRepository
				.getById(MobileApplicationId.ofString(POS_MOBILE_APPLICATION_VALUE))
				.getRepoId();

		userRolePermissionsDAO.createMobileApplicationAccess(CreateMobileApplicationAccessRequest.builder()
				.roleId(RoleId.WEBUI)
				.clientId(MasterdataContext.CLIENT_ID)
				.orgId(orgId)
				.applicationId(posApplicationId)
				.build());
	}
}
