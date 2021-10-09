/*
 * #%L
 * de.metas.swat.base
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

package de.metas.invoice.service;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.invoice.InvoiceId;
import de.metas.util.Services;

class IInvoiceBLTest
{
	private IInvoiceBL invoiceBL;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
		invoiceBL = Services.get(IInvoiceBL.class);
	}

	@Test
	public void ensureUOMsAreNotNull()
	{
		final I_C_UOM uomRecord = BusinessTestHelper.createUOM("uom");
		//
		final I_M_Product product = BusinessTestHelper.createProduct("product", uomRecord);
		saveRecord(product);
		//
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		saveRecord(invoice);
		//
		final I_C_InvoiceLine line = newInstance(I_C_InvoiceLine.class);
		line.setC_Invoice_ID(invoice.getC_Invoice_ID());
		line.setM_Product_ID(product.getM_Product_ID());
		line.setC_UOM_ID(-1);
		saveRecord(line);

		Assertions.assertThat(line.getC_UOM_ID()).isLessThanOrEqualTo(0);
		Assertions.assertThat(line.getPrice_UOM_ID()).isLessThanOrEqualTo(0);

		invoiceBL.ensureUOMsAreNotNull(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		refresh(line);

		Assertions.assertThat(line.getC_UOM_ID()).isEqualTo(product.getC_UOM_ID());
		Assertions.assertThat(line.getPrice_UOM_ID()).isEqualTo(product.getC_UOM_ID());
	}
}
