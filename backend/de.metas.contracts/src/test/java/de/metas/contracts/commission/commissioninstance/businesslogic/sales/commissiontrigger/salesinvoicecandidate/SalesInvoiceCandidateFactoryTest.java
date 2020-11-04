package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoicecandidate;

import de.metas.bpartner.BPartnerId;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.commission.commissioninstance.businesslogic.CommissionPoints;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.organization.OrgId;
import de.metas.product.ProductId;
import de.metas.util.lang.Percent;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.util.TimeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.contracts
 * %%
 * Copyright (C) 2020 metas GmbH
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

class SalesInvoiceCandidateFactoryTest
{
	private SalesInvoiceCandidateFactory salesInvoiceCandidateFactory;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		final MoneyService moneyService = new MoneyService(new CurrencyRepository());
		salesInvoiceCandidateFactory = new SalesInvoiceCandidateFactory(moneyService, new CommissionProductService());
	}

	@Test
	void forRecord()
	{
		final ZonedDateTime fixedTime = LocalDate.parse("2020-03-03")
				.atTime(LocalTime.parse("09:23:00"))
				.atZone(ZoneId.of("CET"));

		SystemTime.setFixedTimeSource(fixedTime);

		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");
		final I_C_Currency currencyRecord = BusinessTestHelper.createCurrency("TobiDollar");

		final I_M_Product product = BusinessTestHelper.createProduct("product", uomRecord);
		product.setIsCommissioned(true);
		saveRecord(product);

		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setAD_Org_ID(10);
		icRecord.setC_Currency_ID(currencyRecord.getC_Currency_ID());
		icRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		icRecord.setM_Product_ID(product.getM_Product_ID());
		icRecord.setC_BPartner_SalesRep_ID(20);
		icRecord.setBill_BPartner_ID(30);
		icRecord.setPriceActual(TEN);
		icRecord.setDateOrdered(TimeUtil.parseTimestamp("2020-03-21"));

		icRecord.setQtyEntered(new BigDecimal("50"));

		icRecord.setNetAmtToInvoice(new BigDecimal("300"));
		icRecord.setQtyToInvoiceInUOM(new BigDecimal("30"));

		icRecord.setNetAmtInvoiced(new BigDecimal("100"));
		icRecord.setQtyInvoicedInUOM(new BigDecimal("10"));
		saveRecord(icRecord);

		// invoke the method under test
		final Optional<SalesInvoiceCandidate> result = salesInvoiceCandidateFactory.forRecord(icRecord);

		assertThat(result)
				.get()
				.isEqualTo(SalesInvoiceCandidate.builder()
						.id(InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()))
						.orgId(OrgId.ofRepoId(10))
						.salesRepBPartnerId(BPartnerId.ofRepoId(20))
						.customerBPartnerId(BPartnerId.ofRepoId(30))
						.productId(ProductId.ofRepoId(product.getM_Product_ID()))
						.commissionDate(LocalDate.parse("2020-03-21"))
						.updated(fixedTime.toInstant())
						.forecastCommissionPoints(CommissionPoints.of("100")) // (Entered - ToInvoiceInUOM - InvoicedInUOM) * PriceActual
						.commissionPointsToInvoice(CommissionPoints.of("300")) // toInvoiceInUOM * priceActual
						.invoicedCommissionPoints(CommissionPoints.of("100")) // invoicedInUOM * priceActual
						.tradedCommissionPercent(Percent.ZERO)
						.build());
	}
}
