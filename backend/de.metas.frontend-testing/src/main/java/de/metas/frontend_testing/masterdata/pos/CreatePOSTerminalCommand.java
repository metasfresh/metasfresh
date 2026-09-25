package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableMap;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPBankAccountDAO;
import de.metas.common.util.CoalesceUtil;
import de.metas.costing.ChargeId;
import de.metas.costing.ChargeTypeId;
import de.metas.costing.impl.ChargeRepository;
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
import de.metas.pos.POSTerminalCreateRequest;
import de.metas.pos.POSTerminalId;
import de.metas.pos.POSTerminalRepository;
import de.metas.pos.withdrawal.POSCashWithdrawalService;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.pricelist.PriceListVersionRepository;
import de.metas.pricing.productprice.CreateProductPriceRequest;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.ITaxDAO;
import de.metas.tax.api.SOPOType;
import de.metas.tax.api.TaxCategoryId;
import de.metas.tax.api.TypeOfDestCountry;
import de.metas.uom.IUOMDAO;
import de.metas.uom.UomId;
import de.metas.util.Services;
import de.metas.util.lang.Percent;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateWarehouseRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BP_BankAccount;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.ZoneOffset;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Creates a POS terminal ({@code C_POS}) for frontend/mobile testing: a dedicated cashbook, a fresh
 * sales pricing setup ({@code M_PricingSystem} + {@code M_PriceList} + {@code M_PriceList_Version} with
 * {@code M_ProductPrice} for the requested products), a walk-in customer, a ship-from warehouse and the
 * sales-order document type. Also grants the {@code pos} mobile application to {@link RoleId#WEBUI} and, when requested,
 * creates the cash withdrawal categories (see {@link JsonPOSTerminalRequest#getCashWithdrawalCategories()}).
 */
@Builder
public class CreatePOSTerminalCommand
{
	private static final String POS_MOBILE_APPLICATION_VALUE = "pos";
	static final String POS_LINE_LEVEL_TAX_CATEGORY_INTERNAL_NAME = "POS_LineLevelTest";
	static final BigDecimal DEFAULT_LINE_LEVEL_TAX_RATE_PERCENT = BigDecimal.valueOf(19);
	static final String NO_CHARGE_TYPE_ID = "-1";

	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final IUOMDAO uomDAO = Services.get(IUOMDAO.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final ITaxDAO taxDAO = Services.get(ITaxDAO.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);
	@NonNull private final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);

	@NonNull private final CurrencyRepository currencyRepository;
	@NonNull private final ProductPriceRepository productPriceRepository;
	@NonNull private final MobileApplicationInfoRepository mobileApplicationInfoRepository;
	@NonNull private final POSTerminalRepository posTerminalRepository;
	@NonNull private final PriceListVersionRepository priceListVersionRepository;
	@NonNull private final ChargeRepository chargeRepository;

	@NonNull private final MasterdataContext context;
	/**
	 * Receives the previous value of each sysconfig this command changes (first writer wins), so the caller can restore it.
	 */
	@NonNull private final Map<String, String> previousSysconfigsCollector;
	@NonNull private final JsonPOSTerminalRequest request;
	@NonNull private final Identifier identifier;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;

	public JsonPOSTerminalResponse execute()
	{
		assertOnlyCashPaymentMethod();

		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(request.getPriceListCurrency());

		final BankAccountId bankAccountId = createCashbookBankAccount(currencyId);

		final PricingSetupHelper.PricingSetupResult pricingSetup = PricingSetupHelper.createPricingSystemAndPriceList(
				priceListVersionRepository,
				PricingSetupHelper.PricingSetupRequest.builder()
						.orgId(orgId)
						.value(identifier.toUniqueString())
						.currencyId(currencyId)
						.countryId(MasterdataContext.COUNTRY_ID)
						.isTaxIncluded(request.isTaxIncluded())
						.isSoPriceList(true)
						.build());
		createProductPrices(pricingSetup.getPriceListVersionId(), request.getProducts());

		final BPartnerId walkInBPartnerId = resolveOrCreateWalkInBPartner();
		final WarehouseId warehouseId = createShipFromWarehouse();
		final DocTypeId salesOrderDocTypeId = getSalesOrderDocTypeId();

		grantPOSMobileApplicationAccess();

		final String name = identifier.toUniqueString();
		final POSTerminalId posTerminalId = posTerminalRepository.createPOSTerminal(POSTerminalCreateRequest.builder()
				.orgId(orgId)
				.name(name)
				.walkInCustomerId(walkInBPartnerId)
				.cashbookId(bankAccountId)
				.salesOrderDocTypeId(salesOrderDocTypeId)
				.priceListId(pricingSetup.getPriceListId())
				.shipFromWarehouseId(warehouseId)
				.build());
		context.putIdentifier(identifier, posTerminalId);

		final ImmutableMap<String, JsonPOSTerminalResponse.CashWithdrawalCategory> cashWithdrawalCategories = createCashWithdrawalCategories();

		return JsonPOSTerminalResponse.builder()
				.id(posTerminalId)
				.name(name)
				.walkInBPartnerId(walkInBPartnerId)
				.bankAccountId(bankAccountId)
				.isCashJournalOpen(false)
				.cashWithdrawalCategories(cashWithdrawalCategories)
				.build();
	}

	/**
	 * The cash withdrawal categories are configured by one global sysconfig, so a second terminal's categories would silently replace the first one's.
	 */
	public static void assertAtMostOneWithCashWithdrawalCategories(@NonNull final Collection<JsonPOSTerminalRequest> requests)
	{
		final long count = requests.stream()
				.filter(request -> !request.getCashWithdrawalCategories().isEmpty())
				.count();
		if (count > 1)
		{
			throw new AdempiereException("At most one POS terminal per request may declare cashWithdrawalCategories, but got " + count);
		}
	}

	/**
	 * Creates one charge per requested label under a fresh charge type and offers that charge type's charges as the
	 * terminals' cash withdrawal categories. Charge names are unique per client, so each name gets the fresh charge
	 * type's id as a short per-run suffix (e.g. {@code "Porto 1000003"}).
	 */
	private ImmutableMap<String, JsonPOSTerminalResponse.CashWithdrawalCategory> createCashWithdrawalCategories()
	{
		final List<String> labels = request.getCashWithdrawalCategories();
		if (labels.isEmpty())
		{
			return ImmutableMap.of();
		}

		final ChargeTypeId chargeTypeId = chargeRepository.createChargeType(identifier.toUniqueString() + "_cashWithdrawal", orgId);

		final ImmutableMap.Builder<String, JsonPOSTerminalResponse.CashWithdrawalCategory> result = ImmutableMap.builder();
		for (final String label : labels)
		{
			final String name = label + " " + chargeTypeId.getRepoId();
			final ChargeId chargeId = chargeRepository.createCharge(name, chargeTypeId, orgId);
			result.put(label, JsonPOSTerminalResponse.CashWithdrawalCategory.builder()
					.chargeId(chargeId)
					.name(name)
					.build());
		}

		setSystemSysconfig(POSCashWithdrawalService.SYSCONFIG_ChargeTypeId, String.valueOf(chargeTypeId.getRepoId()));

		return result.build();
	}

	/**
	 * Sets the sysconfig on system level ({@code AD_Client_ID=0, AD_Org_ID=0}), the level the cash withdrawal sysconfig is configured on.
	 * An absent previous value is reported as {@value #NO_CHARGE_TYPE_ID}, which {@link POSCashWithdrawalService} reads as
	 * "no categories", so restoring the reported value switches the categories off again.
	 */
	private void setSystemSysconfig(@NonNull final String name, @NonNull final String value)
	{
		final String previousValue = sysConfigBL.getValue(name);
		previousSysconfigsCollector.putIfAbsent(name, previousValue != null ? previousValue : NO_CHARGE_TYPE_ID);

		sysConfigBL.setValue(name, value, ClientId.SYSTEM, OrgId.ANY);
	}

	/**
	 * Only {@code CASH} is provisioned so far (no SumUp/card payment-processor config wiring in this
	 * command) — fail loud rather than silently ignore an unsupported {@code paymentMethods} entry.
	 */
	private void assertOnlyCashPaymentMethod()
	{
		final List<POSPaymentMethod> paymentMethods = request.getPaymentMethods();
		final boolean isOnlyCash = paymentMethods.stream().allMatch(POSPaymentMethod::isCash);
		if (!isOnlyCash)
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
		final UomId uomId = Optional.ofNullable(priceSpec.getUom())
				.map(uomDAO::getUomIdByX12DE355)
				.orElseGet(() -> productBL.getStockUOMId(productId));
		final InvoicableQtyBasedOn invoicableQtyBasedOn = CoalesceUtil.coalesceNotNull(priceSpec.getInvoicableQtyBasedOn(), InvoicableQtyBasedOn.NominalWeight);
		final TaxCategoryId taxCategoryId = getTaxCategoryId(priceSpec.getTaxRatePercent());

		productPriceRepository.createProductPrice(CreateProductPriceRequest.builder()
				.orgId(orgId)
				.productId(productId)
				.priceListVersionId(priceListVersionId)
				.priceStd(priceSpec.getPrice())
				.uomId(uomId)
				.taxCategoryId(taxCategoryId)
				.invoicableQtyBasedOn(invoicableQtyBasedOn)
				.build());
	}

	/**
	 * POS requires the product's applicable tax to be line-level (see
	 * {@code POSOrderUpdateFromRemoteCommand#findTax}), but every tax under the seeded
	 * {@link MasterdataContext#DEFAULT_TaxCategory_InternalName} category is document-level for this
	 * org/country. Get-or-create a dedicated line-level tax category for the requested rate (created once
	 * per rate, reused across runs) instead of using the seeded default. {@code taxRatePercentRequested} is
	 * {@link JsonPOSTerminalRequest.ProductPrice#getTaxRatePercent()}; {@code null} keeps the pre-existing
	 * default rate ({@link #DEFAULT_LINE_LEVEL_TAX_RATE_PERCENT}).
	 */
	private TaxCategoryId getTaxCategoryId(@Nullable final BigDecimal taxRatePercentRequested)
	{
		final BigDecimal taxRatePercent = CoalesceUtil.coalesceNotNull(taxRatePercentRequested, DEFAULT_LINE_LEVEL_TAX_RATE_PERCENT);
		final String internalName = taxRatePercent.compareTo(DEFAULT_LINE_LEVEL_TAX_RATE_PERCENT) == 0
				? POS_LINE_LEVEL_TAX_CATEGORY_INTERNAL_NAME
				: POS_LINE_LEVEL_TAX_CATEGORY_INTERNAL_NAME + "_" + taxRatePercent.stripTrailingZeros().toPlainString();

		return taxBL.getTaxCategoryIdByInternalName(internalName)
				.orElseGet(() -> createLineLevelTaxCategory(internalName, taxRatePercent));
	}

	private TaxCategoryId createLineLevelTaxCategory(@NonNull final String internalName, @NonNull final BigDecimal taxRatePercent)
	{
		final TaxCategoryId taxCategoryId = taxDAO.createTaxCategory(ITaxDAO.CreateTaxCategoryRequest.builder()
				.internalName(internalName)
				.name("POS testing (line level, " + taxRatePercent + "%)")
				.build());

		taxDAO.createTax(ITaxDAO.CreateTaxRequest.builder()
				.taxCategoryId(taxCategoryId)
				.name("POS testing " + taxRatePercent + "% (line level)")
				.rate(Percent.of(taxRatePercent))
				.documentLevel(false)
				.validFrom(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(ZoneOffset.UTC).toInstant())
				.countryId(MasterdataContext.COUNTRY_ID)
				.typeOfDestCountry(TypeOfDestCountry.DOMESTIC)
				.sopoType(SOPOType.BOTH)
				.build());

		return taxCategoryId;
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
				.priceListVersionRepository(priceListVersionRepository)
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
