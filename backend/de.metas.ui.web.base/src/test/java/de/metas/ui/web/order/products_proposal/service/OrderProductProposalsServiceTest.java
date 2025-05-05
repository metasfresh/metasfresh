/*
 * #%L
 * de.metas.ui.web.base
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.ui.web.order.products_proposal.service;

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyCode;
import de.metas.currency.CurrencyRepository;
import de.metas.currency.impl.PlainCurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.engine.DocStatus;
import de.metas.money.CurrencyId;
import de.metas.money.MoneyService;
import de.metas.organization.ClientAndOrgId;
import de.metas.organization.OrgId;
import de.metas.pricing.PriceListId;
import de.metas.pricing.PriceListVersionId;
import de.metas.pricing.productprice.ProductPriceRepository;
import de.metas.pricing.service.impl.PricingTestHelper;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.product.ProductId;
import de.metas.uom.UomId;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Order;
import org.compiere.model.I_C_OrderLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_PriceList;
import org.compiere.model.I_M_PriceList_Version;
import org.compiere.model.I_M_PricingSystem;
import org.compiere.model.I_M_ProductPrice;
import org.compiere.model.X_C_DocType;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

public class OrderProductProposalsServiceTest
{
	private static final Timestamp EXPECTED_TIMESTAMP = Timestamp.valueOf("2022-02-01 10:10:10.0");
	private static final Timestamp PREVIOUS_TIMESTAMP = Timestamp.valueOf("2022-01-01 10:10:10.0");

	private BPartnerId bPartnerId;
	private ProductId productId;
	private ClientId clientId;
	private OrgId orgId;
	private PriceListId priceListId;
	private PriceListVersionId priceListVersionId;
	private DocTypeId docTypeId;
	private CurrencyId currencyId;
	private UomId uomId;

	private OrderProductProposalsService orderProductProposalsService;
	private PricingTestHelper pricingTestHelper;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final CurrencyRepository currencyRepository = new CurrencyRepository();
		final ProductTaxCategoryRepository productTaxCategoryRepository = new ProductTaxCategoryRepository();
		final ProductTaxCategoryService productTaxCategoryService= new ProductTaxCategoryService(productTaxCategoryRepository);
		final ProductPriceRepository productPriceRepository = new ProductPriceRepository(productTaxCategoryService);
		orderProductProposalsService = new OrderProductProposalsService(currencyRepository, new MoneyService(currencyRepository), productPriceRepository);

		pricingTestHelper = new PricingTestHelper();

		prepareContext();
	}

	@Test
	public void getLastCompleteQuotation()
	{
		final Optional<Order> lastQuotation = orderProductProposalsService.getLastQuotation(ClientAndOrgId.ofClientAndOrg(clientId, orgId), bPartnerId, productId);

		assertThat(lastQuotation).isPresent();
		assertThat(lastQuotation.get().getDateOrdered().toLocalDateTime()).isEqualTo(EXPECTED_TIMESTAMP.toLocalDateTime());
	}

	@Test
	public void getOrdersByQuery()
	{
		final ClientAndOrgId clientAndOrgId = ClientAndOrgId.ofClientAndOrg(clientId, orgId);
		final List<Order> orders = orderProductProposalsService.getOrdersByQuery(clientAndOrgId, bPartnerId, ImmutableSet.of(productId));

		assertThat(orders).isNotNull();
		assertThat(orders.size()).isEqualTo(2);
		assertThat(orders.get(0).getDateOrdered().toLocalDateTime()).isEqualTo(EXPECTED_TIMESTAMP.toLocalDateTime());
		assertThat(orders.get(1).getDateOrdered().toLocalDateTime()).isEqualTo(PREVIOUS_TIMESTAMP.toLocalDateTime());
	}

	private void createProductPrice(
			@NonNull final PriceListVersionId priceListVersionId,
			@NonNull final ProductId productId,
			@NonNull final UomId uomId)
	{
		final I_M_ProductPrice productPrice = newInstance(I_M_ProductPrice.class);
		productPrice.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		productPrice.setM_Product_ID(productId.getRepoId());
		productPrice.setC_UOM_ID(uomId.getRepoId());
		saveRecord(productPrice);
	}

	private void prepareContext()
	{
		final I_C_UOM uom = BusinessTestHelper.createUomEach();
		uomId = UomId.ofRepoId(uom.getC_UOM_ID());

		orgId = OrgId.ANY;
		clientId = Env.getClientId();
		currencyId = PlainCurrencyDAO.createCurrencyId(CurrencyCode.USD);

		final I_C_Country country = pricingTestHelper.createCountry("DE", currencyId.getRepoId());
		final I_M_PricingSystem pricingSystem = pricingTestHelper.createPricingSystem();

		final I_M_PriceList priceList = pricingTestHelper.createPriceList(pricingSystem, country);
		priceListId = PriceListId.ofRepoId(priceList.getM_PriceList_ID());

		final I_M_PriceList_Version priceListVersion = pricingTestHelper.createPriceListVersion(priceList);
		priceListVersionId = PriceListVersionId.ofRepoId(priceListVersion.getM_PriceList_Version_ID());

		docTypeId = createDocType();

		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test");
		bPartnerId = BPartnerId.ofRepoId(bPartner.getC_BPartner_ID());

		productId = BusinessTestHelper.createProductId("productTest_1", uom);

		createProductPrice(priceListVersionId, productId, uomId);
		createQuotation(PREVIOUS_TIMESTAMP);
		createQuotation(EXPECTED_TIMESTAMP);
	}

	@NonNull
	private DocTypeId createDocType()
	{
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(X_C_DocType.DOCBASETYPE_SalesOrder);
		docTypeRecord.setDocSubType(X_C_DocType.DOCSUBTYPE_Proposal);
		docTypeRecord.setAD_Org_ID(orgId.getRepoId());
		saveRecord(docTypeRecord);

		return DocTypeId.ofRepoId(docTypeRecord.getC_DocType_ID());
	}

	private void createQuotation(@NonNull final Timestamp timestamp)
	{
		final I_C_Order orderRecord = InterfaceWrapperHelper.newInstance(I_C_Order.class);
		orderRecord.setDateOrdered(timestamp);
		orderRecord.setC_BPartner_ID(bPartnerId.getRepoId());
		orderRecord.setC_DocTypeTarget_ID(docTypeId.getRepoId());
		orderRecord.setDocStatus(DocStatus.Completed.getCode());
		orderRecord.setAD_Org_ID(orgId.getRepoId());
		orderRecord.setM_PriceList_ID(priceListId.getRepoId());
		orderRecord.setDatePromised(timestamp);

		saveRecord(orderRecord);

		final I_C_OrderLine orderLineRecord = InterfaceWrapperHelper.newInstance(I_C_OrderLine.class);
		orderLineRecord.setM_Product_ID(productId.getRepoId());
		orderLineRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		orderLineRecord.setC_UOM_ID(uomId.getRepoId());
		orderLineRecord.setM_PriceList_Version_ID(priceListVersionId.getRepoId());
		orderLineRecord.setC_Currency_ID(currencyId.getRepoId());

		saveRecord(orderLineRecord);
	}
}