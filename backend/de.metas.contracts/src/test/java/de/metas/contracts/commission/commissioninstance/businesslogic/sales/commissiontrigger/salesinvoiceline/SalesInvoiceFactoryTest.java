package de.metas.contracts.commission.commissioninstance.businesslogic.sales.commissiontrigger.salesinvoiceline;

import au.com.origin.snapshots.Expect;
import au.com.origin.snapshots.junit5.SnapshotExtension;
import de.metas.business.BusinessTestHelper;
import de.metas.common.util.time.SystemTime;
import de.metas.contracts.commission.commissioninstance.services.CommissionProductService;
import de.metas.currency.CurrencyRepository;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import de.metas.pricing.tax.ProductTaxCategoryRepository;
import de.metas.pricing.tax.ProductTaxCategoryService;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.ad.wrapper.POJONextIdSuppliers;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.math.BigDecimal;
import java.util.Optional;

import static java.math.BigDecimal.TEN;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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

@ExtendWith(SnapshotExtension.class)
class SalesInvoiceFactoryTest
{

	private SalesInvoiceFactory salesInvoiceFactory;

	private Expect expect;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		POJOLookupMap.setNextIdSupplier(POJONextIdSuppliers.newPerTableSequence());

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		SpringContextHolder.registerJUnitBean(new ProductTaxCategoryService(new ProductTaxCategoryRepository()));

		salesInvoiceFactory = new SalesInvoiceFactory(new CommissionProductService());
	}

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

		expect.serializer("orderedJson").toMatchSnapshot(result.get());
	}

}
