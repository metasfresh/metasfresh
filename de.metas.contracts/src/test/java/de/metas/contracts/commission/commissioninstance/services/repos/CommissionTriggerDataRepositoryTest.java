package de.metas.contracts.commission.commissioninstance.services.repos;

import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_UOM;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.business.BusinessTestHelper;
import de.metas.contracts.commission.commissioninstance.businesslogic.sales.CommissionTriggerData;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.InvoiceCandidateId;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.util.time.SystemTime;
import io.github.jsonSnapshot.SnapshotMatcher;

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

class CommissionTriggerDataRepositoryTest
{

	private CommissionTriggerDataRepository commissionTriggerDataRepository;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		commissionTriggerDataRepository = new CommissionTriggerDataRepository(new InvoiceCandidateRecordHelper(new MoneyService(new CurrencyRepository())));
	}

	@BeforeAll
	static void beforeAll()
	{
		SnapshotMatcher.start(
				AdempiereTestHelper.SNAPSHOT_CONFIG,
				AdempiereTestHelper.createSnapshotJsonFunction());
	}

	@AfterAll
	static void afterAll()
	{
		validateSnapshots();
	}

	@Test
	void testGetForInvoiceCandidateId()
	{
		SystemTime.setTimeSource(() -> 1583223780929L); // approximately 2020-03-03 09:23CET

		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");
		final I_C_Currency currencyRecord = BusinessTestHelper.createCurrency("TobiDollar");

		final I_C_Invoice_Candidate icRecord = newInstance(I_C_Invoice_Candidate.class);
		icRecord.setAD_Org_ID(10);
		icRecord.setC_Currency_ID(currencyRecord.getC_Currency_ID());
		icRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		icRecord.setM_Product_ID(30);
		icRecord.setPriceActual(TEN);

		icRecord.setQtyEntered(new BigDecimal("50"));

		icRecord.setNetAmtToInvoice(new BigDecimal("300"));
		icRecord.setQtyToInvoiceInUOM(new BigDecimal("30"));

		icRecord.setNetAmtInvoiced(new BigDecimal("100"));
		icRecord.setQtyInvoicedInUOM(new BigDecimal("10"));
		saveRecord(icRecord);

		// invoke the method under test
		final CommissionTriggerData result = commissionTriggerDataRepository.getForInvoiceCandidateId(
				InvoiceCandidateId.ofRepoId(icRecord.getC_Invoice_Candidate_ID()),
				false/* candidateDeleted */);

		assertThat(result.getForecastedPoints().toBigDecimal()).isEqualByComparingTo("100"); // (Entered - ToInvoiceInUOM - InvoicedInUOM) * PriceActual
		assertThat(result.getInvoiceablePoints().toBigDecimal()).isEqualByComparingTo("300"); // toInvoiceInUOM * priceActual
		assertThat(result.getInvoicedPoints().toBigDecimal()).isEqualByComparingTo("100"); // invoicedInUOM * priceActual

		SnapshotMatcher.expect(result).toMatchSnapshot();
	}

}
