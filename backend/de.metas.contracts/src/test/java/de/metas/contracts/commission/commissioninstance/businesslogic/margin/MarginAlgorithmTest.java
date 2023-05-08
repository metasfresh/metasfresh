/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.contracts.commission.commissioninstance.businesslogic.margin;

import au.com.origin.snapshots.Expect;

import au.com.origin.snapshots.junit5.SnapshotExtension;
import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.contracts.FlatrateTermId;
import de.metas.contracts.commission.Beneficiary;
import de.metas.contracts.commission.Customer;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.businesslogic.CreateCommissionSharesRequest;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.Hierarchy;
import de.metas.contracts.commission.commissioninstance.businesslogic.hierarchy.HierarchyLevel;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionShare;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTrigger;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerData;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.CommissionTriggerType;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate.SalesInvoiceCandidateDocumentId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginLineId;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginRepository;
import de.metas.contracts.pricing.trade_margin.CustomerTradeMarginService;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.money.CurrencyId;
import de.metas.money.Money;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.product.ProductPrice;
import de.metas.quantity.Quantity;
import de.metas.uom.UomId;
import de.metas.util.lang.Percent;
import lombok.Builder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.*;

@ExtendWith(SnapshotExtension.class)
public class MarginAlgorithmTest
{
	private final OrgId orgId = OrgId.ofRepoId(200);
	private final ProductId productId = ProductId.ofRepoId(1);
	private final Beneficiary salesRep = Beneficiary.of(BPartnerId.ofRepoId(1));
	private final FlatrateTermId contractId = FlatrateTermId.ofRepoId(1);
	private final InvoiceCandidateId invoiceCandidateId = InvoiceCandidateId.ofRepoId(1);
	private final CurrencyId currencyId = CurrencyId.ofRepoId(1);
	private final CustomerTradeMarginId marginId = CustomerTradeMarginId.ofRepoId(1);
	private final CustomerTradeMarginLineId marginLineId = CustomerTradeMarginLineId.ofRepoId(marginId, 1);

	private I_C_UOM uomRecord;

	CustomerTradeMarginService customerTradeMarginServiceSpy;
	MarginAlgorithm marginAlgorithm;

	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		final MoneyService moneyService = new MoneyService(new CurrencyRepository());

		customerTradeMarginServiceSpy = Mockito.spy(new CustomerTradeMarginService(moneyService, new CustomerTradeMarginRepository()));
		SpringContextHolder.registerJUnitBean(customerTradeMarginServiceSpy);

		marginAlgorithm = new MarginAlgorithm(customerTradeMarginServiceSpy);

		uomRecord = BusinessTestHelper.createUOM("uom");

		final ProductPrice mockProductPrice = ProductPrice.builder()
				.money(Money.of(TEN, currencyId))
				.productId(productId)
				.uomId(UomId.ofRepoId(uomRecord.getC_UOM_ID())).build();

		Mockito.doReturn(Optional.of(mockProductPrice))
				.when(customerTradeMarginServiceSpy)
				.calculateSalesRepNetUnitPrice(Mockito.notNull());
	}

	@Test
	void givenValidRequest_whenCreateCommissionShares_thenReturnOneShare()
	{
		// given
		final CreateCommissionSharesRequest request = commissionSharesRequestBuilder().build();

		// when
		final ImmutableList<CommissionShare> shares = marginAlgorithm.createCommissionShares(request);

		// then
		assertThat(shares).hasSize(1);

		expect.serializer("orderedJson").toMatchSnapshot(shares.get(0));
	}

	@Builder(builderMethodName = "commissionSharesRequestBuilder")
	private CreateCommissionSharesRequest buildRequest()
	{
		final Hierarchy emptyHierarchy = Hierarchy.builder().build();

		final Integer pointsPrecision = 2;
		final MarginConfig marginConfig = MarginConfig.builder()
				.commissionProductId(productId)
				.pointsPrecision(pointsPrecision)
				.marginContract(MarginContract.builder()
										.id(contractId)
										.contractOwnerBPartnerId(salesRep.getBPartnerId())
										.build())
				.customerTradeMarginLineId(marginLineId)
				.tradedPercent(Percent.of(TEN))
				.build();

		//trigger
		final CommissionTriggerData triggerData = CommissionTriggerData.builder()
				.orgId(orgId)
				.triggerType(CommissionTriggerType.InvoiceCandidate)
				.timestamp(Instant.parse("2021-09-01T00:00:00Z"))
				.triggerDocumentDate(LocalDate.of(2020, 9, 9))
				.triggerDocumentId(new SalesInvoiceCandidateDocumentId(invoiceCandidateId))
				.forecastedBasePoints(CommissionPoints.of(BigDecimal.valueOf(150)))
				.invoiceableBasePoints(CommissionPoints.of(BigDecimal.ZERO))
				.invoicedBasePoints(CommissionPoints.of(BigDecimal.ZERO))
				.productId(productId)
				.totalQtyInvolved(Quantity.of(TEN, uomRecord))
				.documentCurrencyId(currencyId)
				.build();

		final CommissionTrigger commissionTrigger = CommissionTrigger.builder()
				.customer(Customer.of(BPartnerId.ofRepoId(10)))
				.salesRepId(salesRep.getBPartnerId())
				.orgBPartnerId(salesRep.getBPartnerId())
				.commissionTriggerData(triggerData)
				.build();

		//request
		return CreateCommissionSharesRequest.builder()
				.hierarchy(emptyHierarchy)
				.startingHierarchyLevel(HierarchyLevel.ZERO)
				.config(marginConfig)
				.trigger(commissionTrigger)
				.build();
	}
}
