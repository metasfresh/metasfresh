package de.metas.frontend_testing.masterdata.pos;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.currency.CurrencyRepository;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.bpartner.CreateBPartnerCommand;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerRequest;
import de.metas.frontend_testing.masterdata.bpartner.JsonCreateBPartnerResponse;
import de.metas.frontend_testing.masterdata.product.CreateProductCommand;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductRequest;
import de.metas.frontend_testing.masterdata.product.JsonCreateProductResponse;
import de.metas.mobile.application.MobileApplicationId;
import de.metas.mobile.application.MobileApplicationRepoId;
import de.metas.mobile.application.repository.MobileApplicationInfoRepository;
import de.metas.pos.POSPaymentMethod;
import de.metas.pricing.InvoicableQtyBasedOn;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.ProductId;
import de.metas.product.ProductRepository;
import de.metas.security.RoleId;
import de.metas.uom.X12DE355;
import de.metas.util.Services;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_POS;
import org.compiere.model.I_C_TaxCategory;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.I_Mobile_Application;
import org.compiere.model.I_Mobile_Application_Access;
import org.compiere.model.X_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
 * %%
 * Copyright (C) 2026 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

public class CreatePOSTerminalCommandTest
{
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	private CurrencyRepository currencyRepository;
	private ProductRepository productRepository;
	private ProductPriceRepository productPriceRepository;
	private MobileApplicationInfoRepository mobileApplicationInfoRepository;
	private MasterdataContext context;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		currencyRepository = new CurrencyRepository();
		productRepository = new ProductRepository();
		productPriceRepository = new ProductPriceRepository(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));
		mobileApplicationInfoRepository = new MobileApplicationInfoRepository();
		context = new MasterdataContext();

		createSalesOrderDocType();
		createNormalTaxCategory();
		createPOSMobileApplication();
		createEachUom();
	}

	/** {@link de.metas.uom.UomId#EACH} is a fixed constant (repoId=100) that production code loads by id; seed it. */
	private static void createEachUom()
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstanceOutOfTrx(I_C_UOM.class);
		uom.setC_UOM_ID(de.metas.uom.UomId.EACH.getRepoId());
		uom.setName("Each");
		uom.setX12DE355("EA");
		uom.setStdPrecision(0);
		uom.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(uom);
	}

	private static void createSalesOrderDocType()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		InterfaceWrapperHelper.setValue(docType, I_C_DocType.COLUMNNAME_AD_Client_ID, MasterdataContext.CLIENT_ID.getRepoId());
		docType.setAD_Org_ID(0);
		docType.setName("SalesOrder");
		docType.setDocBaseType(X_C_DocType.DOCBASETYPE_SalesOrder);
		docType.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(docType);
	}

	private static void createNormalTaxCategory()
	{
		final I_C_TaxCategory taxCategory = InterfaceWrapperHelper.newInstance(I_C_TaxCategory.class);
		taxCategory.setName("Normal");
		taxCategory.setInternalName(MasterdataContext.DEFAULT_TaxCategory_InternalName);
		taxCategory.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(taxCategory);
	}

	private static void createPOSMobileApplication()
	{
		final I_Mobile_Application mobileApp = InterfaceWrapperHelper.newInstance(I_Mobile_Application.class);
		mobileApp.setValue("pos");
		mobileApp.setName("POS");
		mobileApp.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(mobileApp);
	}

	private static void createUom(final String x12de355Code, final int stdPrecision)
	{
		final I_C_UOM uom = InterfaceWrapperHelper.newInstance(I_C_UOM.class);
		uom.setName(x12de355Code);
		uom.setX12DE355(x12de355Code);
		uom.setStdPrecision(stdPrecision);
		uom.setIsActive(true);
		InterfaceWrapperHelper.saveRecord(uom);
	}

	private CreatePOSTerminalCommand.CreatePOSTerminalCommandBuilder commandBuilder()
	{
		return CreatePOSTerminalCommand.builder()
				.currencyRepository(currencyRepository)
				.productPriceRepository(productPriceRepository)
				.mobileApplicationInfoRepository(mobileApplicationInfoRepository)
				.context(context);
	}

	private ProductId createProduct(final String identifier)
	{
		final JsonCreateProductResponse response = CreateProductCommand.builder()
				.productRepository(productRepository)
				.context(context)
				.request(JsonCreateProductRequest.builder().value(identifier).build())
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();
		return response.getId();
	}

	private I_M_ProductPrice getProductPrice(final int priceListId, final ProductId productId)
	{
		final I_M_PriceList_Version plv = queryBL.createQueryBuilder(I_M_PriceList_Version.class)
				.addEqualsFilter(I_M_PriceList_Version.COLUMNNAME_M_PriceList_ID, priceListId)
				.create()
				.firstOnlyNotNull(I_M_PriceList_Version.class);

		return queryBL.createQueryBuilder(I_M_ProductPrice.class)
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_PriceList_Version_ID, plv.getM_PriceList_Version_ID())
				.addEqualsFilter(I_M_ProductPrice.COLUMNNAME_M_Product_ID, productId)
				.create()
				.firstOnlyNotNull(I_M_ProductPrice.class);
	}

	@Test
	public void execute_withMinimalRequest_shouldCreateTerminal()
	{
		// given
		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T1"))
				.build()
				.execute();

		// then
		assertThat(response).isNotNull();
		assertThat(response.getId()).isNotNull();
		assertThat(response.getWalkInBPartnerId()).isNotNull();
		assertThat(response.getBankAccountId()).isNotNull();
		assertThat(response.isCashJournalOpen()).isFalse();

		final I_C_POS posRecord = InterfaceWrapperHelper.load(response.getId(), I_C_POS.class);
		assertThat(posRecord).isNotNull();
		assertThat(posRecord.isActive()).isTrue();
		assertThat(posRecord.getCashLastBalance()).isEqualByComparingTo(BigDecimal.ZERO);
		assertThat(posRecord.getC_BP_BankAccount_ID()).isEqualTo(response.getBankAccountId().getRepoId());
		assertThat(posRecord.getC_BPartnerCashTrx_ID()).isEqualTo(response.getWalkInBPartnerId().getRepoId());
		assertThat(posRecord.getM_Warehouse_ID()).isGreaterThan(0);
		assertThat(posRecord.getC_DocTypeOrder_ID()).isGreaterThan(0);

		final I_C_BP_BankAccount bankAccount = InterfaceWrapperHelper.load(response.getBankAccountId(), I_C_BP_BankAccount.class);
		assertThat(bankAccount.getC_BPartner_ID()).isEqualTo(MasterdataContext.METASFRESH_ORG_BPARTNER_ID.getRepoId());

		final I_M_PriceList priceList = InterfaceWrapperHelper.load(posRecord.getM_PriceList_ID(), I_M_PriceList.class);
		assertThat(priceList.isTaxIncluded()).isFalse();
	}

	@Test
	public void execute_withTaxIncludedTrue_shouldSetPriceListTaxIncluded()
	{
		// given
		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(true)
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_TAXINC"))
				.build()
				.execute();

		// then
		final I_C_POS posRecord = InterfaceWrapperHelper.load(response.getId(), I_C_POS.class);
		final I_M_PriceList priceList = InterfaceWrapperHelper.load(posRecord.getM_PriceList_ID(), I_M_PriceList.class);
		assertThat(priceList.isTaxIncluded()).isTrue();
	}

	@Test
	public void execute_withProductPrice_shouldCreateExactPriceOnProductStockUom()
	{
		// given
		final ProductId productId = createProduct("P1");

		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.products(ImmutableMap.of("P1", JsonPOSTerminalRequest.ProductPrice.builder()
						.price(new BigDecimal("9.90"))
						.build()))
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_PRICE"))
				.build()
				.execute();

		// then
		final I_C_POS posRecord = InterfaceWrapperHelper.load(response.getId(), I_C_POS.class);
		final I_M_ProductPrice productPrice = getProductPrice(posRecord.getM_PriceList_ID(), productId);

		assertThat(productPrice.getPriceStd()).isEqualByComparingTo(new BigDecimal("9.90"));
		assertThat(productPrice.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.NominalWeight.getCode());
	}

	@Test
	public void execute_withCatchWeightProductPrice_shouldPricePerKg()
	{
		// given
		createUom("KGM", 3);
		final ProductId productId = createProduct("P_SCALE");

		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.products(ImmutableMap.of("P_SCALE", JsonPOSTerminalRequest.ProductPrice.builder()
						.price(new BigDecimal("15.50"))
						.uom(X12DE355.ofCode("KGM"))
						.invoicableQtyBasedOn(InvoicableQtyBasedOn.CatchWeight)
						.build()))
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_SCALE"))
				.build()
				.execute();

		// then
		final I_C_POS posRecord = InterfaceWrapperHelper.load(response.getId(), I_C_POS.class);
		final I_M_ProductPrice productPrice = getProductPrice(posRecord.getM_PriceList_ID(), productId);

		assertThat(productPrice.getPriceStd()).isEqualByComparingTo(new BigDecimal("15.50"));
		assertThat(productPrice.getInvoicableQtyBasedOn()).isEqualTo(InvoicableQtyBasedOn.CatchWeight.getCode());

		final I_C_UOM uom = InterfaceWrapperHelper.load(productPrice.getC_UOM_ID(), I_C_UOM.class);
		assertThat(uom.getX12DE355()).isEqualTo("KGM");
	}

	@Test
	public void execute_withGivenWalkInCustomer_shouldReuseIt()
	{
		// given
		final JsonCreateBPartnerResponse bpartnerResponse = CreateBPartnerCommand.builder()
				.currencyRepository(currencyRepository)
				.context(context)
				.request(JsonCreateBPartnerRequest.builder().isCustomer(true).build())
				.identifier("existingWalkIn")
				.build()
				.execute();

		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.walkInCustomer(Identifier.ofString("existingWalkIn"))
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_WALKIN"))
				.build()
				.execute();

		// then
		assertThat(response.getWalkInBPartnerId()).isEqualTo(bpartnerResponse.getId());
	}

	@Test
	public void execute_withoutWalkInCustomer_shouldCreateOne()
	{
		// given
		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.build();

		// when
		final JsonPOSTerminalResponse response = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_AUTOWALKIN"))
				.build()
				.execute();

		// then
		final BPartnerId walkInBPartnerId = response.getWalkInBPartnerId();
		assertThat(walkInBPartnerId).isNotNull();
		assertThat(InterfaceWrapperHelper.load(walkInBPartnerId, I_C_BPartner.class)).isNotNull();
	}

	@Test
	public void execute_withNonCashPaymentMethod_shouldThrow()
	{
		// given
		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.paymentMethods(ImmutableList.of(POSPaymentMethod.CARD))
				.build();

		final CreatePOSTerminalCommand command = commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_CARD"))
				.build();

		// when / then
		assertThatThrownBy(command::execute).isInstanceOf(AdempiereException.class);
	}

	@Test
	public void execute_shouldGrantPOSMobileApplicationToWebUIRole()
	{
		// given
		final JsonPOSTerminalRequest request = JsonPOSTerminalRequest.builder()
				.priceListCurrency("EUR")
				.isTaxIncluded(false)
				.build();

		// when
		commandBuilder()
				.request(request)
				.identifier(Identifier.ofString("T_GRANT"))
				.build()
				.execute();

		// then
		final MobileApplicationRepoId posAppRepoId = mobileApplicationInfoRepository.getById(MobileApplicationId.ofString("pos")).getRepoId();

		final boolean granted = queryBL.createQueryBuilder(I_Mobile_Application_Access.class)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_AD_Role_ID, RoleId.WEBUI)
				.addEqualsFilter(I_Mobile_Application_Access.COLUMNNAME_Mobile_Application_ID, posAppRepoId)
				.create()
				.anyMatch();

		assertThat(granted).isTrue();
	}
}
