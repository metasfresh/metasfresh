package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

<<<<<<< HEAD
=======
import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.currency.CurrencyRepository;
<<<<<<< HEAD
import io.github.jsonSnapshot.SnapshotMatcher;
=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Currency;
import org.compiere.model.I_C_DocType;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_InvoiceLine;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.compiere.model.X_C_DocType;
import org.compiere.util.TimeUtil;
<<<<<<< HEAD
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
=======
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

import java.math.BigDecimal;
import java.util.Optional;

<<<<<<< HEAD
import static io.github.jsonSnapshot.SnapshotMatcher.validateSnapshots;
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;
=======
import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

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

<<<<<<< HEAD
=======
@ExtendWith(SnapshotExtension.class)
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
class SalesInvoiceFactoryTest
{

	private SalesInvoiceFactory salesInvoiceFactory;

<<<<<<< HEAD
=======
	private Expect expect;

>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());

		salesInvoiceFactory = new SalesInvoiceFactory(new CommissionProductService());
	}

<<<<<<< HEAD
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

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	@Test
	void forRecord()
	{
		SystemTime.setTimeSource(() -> 1583223780929L); // approximately 2020-03-03 09:23CET

		final I_C_DocType docTypeRecord = newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(X_C_DocType.DOCBASETYPE_ARCreditMemo);
		saveRecord(docTypeRecord);

		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");
		final I_C_Currency currencyRecord = BusinessTestHelper.createCurrency("TobiDollar");

		final I_M_Product product = BusinessTestHelper.createProduct("product", uomRecord);
		product.setIsCommissioned(true);
		saveRecord(product);

		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setAD_Org_ID(10);
		invoiceRecord.setC_DocType_ID(docTypeRecord.getC_DocType_ID());
		invoiceRecord.setC_BPartner_SalesRep_ID(20);
		invoiceRecord.setC_BPartner_ID(30);
		invoiceRecord.setC_Currency_ID(currencyRecord.getC_Currency_ID());
		invoiceRecord.setDateInvoiced(TimeUtil.parseTimestamp("2020-03-21"));
		saveRecord(invoiceRecord);

		final I_C_InvoiceLine invoiceLineRecord = newInstance(I_C_InvoiceLine.class);
		invoiceLineRecord.setC_Invoice_ID(invoiceRecord.getC_Invoice_ID());
		invoiceLineRecord.setC_UOM_ID(uomRecord.getC_UOM_ID());
		invoiceLineRecord.setM_Product_ID(product.getM_Product_ID());
		invoiceLineRecord.setPriceActual(TEN);
		invoiceLineRecord.setQtyEntered(new BigDecimal("50"));

		invoiceLineRecord.setLineNetAmt(new BigDecimal("100"));
		invoiceLineRecord.setQtyEntered(new BigDecimal("30"));

		saveRecord(invoiceLineRecord);

		// invoke the method under test
		final Optional<SalesInvoice> result = salesInvoiceFactory.forRecord(invoiceRecord);
		assertThat(result).isPresent();
		assertThat(result.get().getInvoiceLines()).hasSize(1);

		final SalesInvoiceLine salesInvoiceLine = result.get().getInvoiceLines().get(0);
		assertThat(salesInvoiceLine.getInvoicedCommissionPoints().toBigDecimal()).isEqualByComparingTo("-100"); // LineNetAmt * 1 because it's a credit memo

<<<<<<< HEAD
		SnapshotMatcher.expect(result.get()).toMatchSnapshot();
=======
		expect.serializer("orderedJson").toMatchSnapshot(result.get());
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}

}
