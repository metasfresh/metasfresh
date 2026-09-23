package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableSet;
import de.metas.banking.BankAccountId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPBankAccountDAO;
import de.metas.common.util.time.SystemTime;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.document.DocBaseType;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.money.CurrencyId;
import de.metas.organization.OrgId;
import de.metas.pos.POSTerminalId;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.product.IProductBL;
import de.metas.product.ProductId;
import de.metas.security.IUserRolePermissionsDAO;
import de.metas.security.RoleId;
import de.metas.security.requests.CreateMobileApplicationAccessRequest;
import de.metas.tax.api.ITaxBL;
import de.metas.tax.api.TaxCategoryId;
import de.metas.uom.UomId;
import de.metas.util.Services;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.warehouse.WarehouseId;
import org.adempiere.warehouse.api.CreateWarehouseRequest;
import org.adempiere.warehouse.api.IWarehouseBL;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_Mobile_Application;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

/**
 * Creates a POS terminal ({@code C_POS}) for frontend/mobile testing: a dedicated cashbook, a fresh
 * sales pricing setup ({@code M_PricingSystem} + {@code M_PriceList} + {@code M_PriceList_Version} with
 * {@code M_ProductPrice} for the requested products), a walk-in customer, a ship-from warehouse and the
 * sales-order document type. Also grants the {@code pos} mobile application to the {@link RoleId#WEBUI}
 * role, the role every {@code login} user of this masterdata API gets
 * ({@link de.metas.frontend_testing.masterdata.user.LoginUserCommand}).
 * <p>
 * Does NOT touch {@code de.metas.pos.base} main code; it only reads from it ({@link POSTerminalId}).
 */
@Builder
public class CreatePOSTerminalCommand
{
	private static final String POS_MOBILE_APPLICATION_VALUE = "pos";

	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);
	@NonNull private final IProductBL productBL = Services.get(IProductBL.class);
	@NonNull private final ITaxBL taxBL = Services.get(ITaxBL.class);
	@NonNull private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);
	@NonNull private final IBPBankAccountDAO bpBankAccountDAO = Services.get(IBPBankAccountDAO.class);
	@NonNull private final IUserRolePermissionsDAO userRolePermissionsDAO = Services.get(IUserRolePermissionsDAO.class);
	@NonNull private final IWarehouseBL warehouseBL = Services.get(IWarehouseBL.class);

	@NonNull private final CurrencyRepository currencyRepository;

	@NonNull private final MasterdataContext context;
	@NonNull private final JsonPOSTerminalRequest request;
	@NonNull private final Identifier identifier;
	@NonNull private final OrgId orgId = MasterdataContext.ORG_ID;

	public JsonPOSTerminalResponse execute()
	{
		assertOnlyCashPaymentMethod();

		final CurrencyId currencyId = currencyRepository.getCurrencyIdByCurrencyCode(CurrencyCode.ofThreeLetterCode(request.getPriceListCurrency()));

		final BankAccountId bankAccountId = createCashbookBankAccount(currencyId);

		final I_M_PriceList_Version priceListVersion = createPOSPriceList(currencyId);
		final PriceListId priceListId = PriceListId.ofRepoId(priceListVersion.getM_PriceList_ID());
		final PriceListVersionId priceListVersionId = PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());
		createProductPrices(priceListVersionId, request.getProducts() != null ? request.getProducts() : ImmutableSet.of());

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
		posRecord.setM_PriceList_ID(priceListId.getRepoId());
		posRecord.setM_Warehouse_ID(warehouseId.getRepoId());
		InterfaceWrapperHelper.saveRecord(posRecord);

		final POSTerminalId posTerminalId = POSTerminalId.ofRepoId(posRecord.getC_POS_ID());
		context.putIdentifier(identifier, posTerminalId);

		return JsonPOSTerminalResponse.builder()
				.id(posTerminalId)
				.walkInBPartnerId(walkInBPartnerId)
				.bankAccountId(bankAccountId)
				.cashJournalOpen(false)
				.build();
	}

	/**
	 * Only {@code CASH} is provisioned so far (no SumUp/card payment-processor config wiring in this
	 * command) — fail loud rather than silently ignore an unsupported {@code paymentMethods} entry.
	 */
	private void assertOnlyCashPaymentMethod()
	{
		final List<String> paymentMethods = request.getPaymentMethods();
		final boolean onlyCash = paymentMethods.stream().allMatch("CASH"::equalsIgnoreCase);
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

	/**
	 * Creates a fresh, POS-dedicated {@code M_PricingSystem} + {@code M_PriceList} + {@code M_PriceList_Version}
	 * (mirrors {@link CreateBPartnerCommand#createPricingSystem()}, but never reuses/registers a shared one in
	 * the {@link MasterdataContext} — each POS terminal gets its own).
	 */
	private I_M_PriceList_Version createPOSPriceList(@NonNull final CurrencyId currencyId)
	{
		final String value = identifier.toUniqueString();

		final I_M_PricingSystem pricingSystem = InterfaceWrapperHelper.newInstance(I_M_PricingSystem.class);
		pricingSystem.setValue(value);
		pricingSystem.setName(value);
		pricingSystem.setAD_Org_ID(orgId.getRepoId());
		InterfaceWrapperHelper.saveRecord(pricingSystem);

		final I_M_PriceList priceList = InterfaceWrapperHelper.newInstance(I_M_PriceList.class);
		priceList.setM_PricingSystem_ID(pricingSystem.getM_PricingSystem_ID());
		priceList.setAD_Org_ID(orgId.getRepoId());
		priceList.setC_Currency_ID(currencyId.getRepoId());
		priceList.setName(value);
		priceList.setIsTaxIncluded(request.isTaxIncluded());
		priceList.setPricePrecision(2);
		priceList.setIsActive(true);
		priceList.setIsSOPriceList(true);
		priceList.setC_Country_ID(MasterdataContext.COUNTRY_ID.getRepoId());
		InterfaceWrapperHelper.saveRecord(priceList);

		final I_M_PriceList_Version plv = InterfaceWrapperHelper.newInstance(I_M_PriceList_Version.class);
		plv.setM_PriceList_ID(priceList.getM_PriceList_ID());
		plv.setAD_Org_ID(priceList.getAD_Org_ID());
		plv.setValidFrom(Timestamp.from(MasterdataContext.DEFAULT_ValidFrom.atStartOfDay(SystemTime.zoneId()).toInstant()));
		InterfaceWrapperHelper.saveRecord(plv);

		return plv;
	}

	private void createProductPrices(@NonNull final PriceListVersionId priceListVersionId, @NonNull final Set<Identifier> productIdentifiers)
	{
		if (productIdentifiers.isEmpty())
		{
			return;
		}

		final ImmutableSet<ProductId> productIds = context.getIds(productIdentifiers, ProductId.class);
		productIds.forEach(productId -> createProductPrice(priceListVersionId, productId));
	}

	/**
	 * Reuses the {@code M_ProductPrice} creation pattern of
	 * {@link de.metas.frontend_testing.masterdata.product.CreateProductCommand#createPrice}. This is infra
	 * scaffolding (task satisfies no test case on its own): the {@code PriceStd} is best-effort copied from
	 * the product's own already-created price (if any, e.g. via {@code JsonCreateProductRequest#getPrice()});
	 * a product with no existing price gets {@code 0} here.
	 */
	private void createProductPrice(@NonNull final PriceListVersionId priceListVersionId, @NonNull final ProductId productId)
	{
		final UomId uomId = productBL.getStockUOMId(productId);
		final BigDecimal priceStd = resolveExistingPriceStdOrZero(productId);

		final I_M_ProductPrice productPrice = InterfaceWrapperHelper.newInstance(I_M_ProductPrice.class);
		productPrice.setIsActive(true);
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setC_UOM_ID(uomId.getRepoId());
		productPrice.setPriceStd(priceStd);
		productPrice.setC_TaxCategory_ID(getTaxCategoryId().getRepoId());
		productPrice.setInvoicableQtyBasedOn(InvoicableQtyBasedOn.NominalWeight.getCode());
		InterfaceWrapperHelper.saveRecord(productPrice);
	}

	private BigDecimal resolveExistingPriceStdOrZero(@NonNull final ProductId productId)
	{
		return queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.create()
				.stream()
				.map(I_M_ProductPrice::getPriceStd)
				.findFirst()
				.orElse(BigDecimal.ZERO);
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
				.request(JsonCreateBPartnerRequest.builder().build()) // defaults: isCustomer=true, isSoPriceList=true
				.identifier(identifier.toUniqueString() + "_walkIn")
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

	/**
	 * Grants the {@code pos} mobile application to {@link RoleId#WEBUI} — the role every masterdata-API
	 * {@code login} user gets ({@link de.metas.frontend_testing.masterdata.user.LoginUserCommand}) — the same
	 * {@link IUserRolePermissionsDAO#createMobileApplicationAccess} call the
	 * {@code de.metas.mobile.application.interceptor.Mobile_Application} model interceptor uses when a new
	 * {@code Mobile_Application} record is created. Idempotent (no-op if already granted).
	 */
	private void grantPOSMobileApplicationAccess()
	{
		final I_Mobile_Application mobileApp = queryBL.createQueryBuilder(I_Mobile_Application.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_Mobile_Application.COLUMNNAME_Value, POS_MOBILE_APPLICATION_VALUE)
				.create()
				.firstOnlyOptional(I_Mobile_Application.class)
				.orElseThrow(() -> new AdempiereException("No Mobile_Application found with Value=`" + POS_MOBILE_APPLICATION_VALUE + "`"));

		final MobileApplicationRepoId posApplicationId = MobileApplicationRepoId.ofRepoId(mobileApp.getMobile_Application_ID());

		userRolePermissionsDAO.createMobileApplicationAccess(CreateMobileApplicationAccessRequest.builder()
				.roleId(RoleId.WEBUI)
				.clientId(MasterdataContext.CLIENT_ID)
				.orgId(orgId)
				.applicationId(posApplicationId)
				.build());
	}
}
