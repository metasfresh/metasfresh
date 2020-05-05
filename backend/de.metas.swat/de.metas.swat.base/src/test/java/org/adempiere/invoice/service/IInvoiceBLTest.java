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

package org.adempiere.invoice.service;

import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.business.BusinessTestHelper;
import de.metas.invoice.InvoiceId;
import org.adempiere.invoice.service.impl.InvoiceBL;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

class IInvoiceBLTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	void ensureUOMsAreNotNull()
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

		final InvoiceBL invoiceBL = new InvoiceBL();
		invoiceBL.ensureUOMsAreNotNull(InvoiceId.ofRepoId(invoice.getC_Invoice_ID()));
		refresh(line);

		Assertions.assertThat(line.getC_UOM_ID()).isEqualTo(product.getC_UOM_ID());
		Assertions.assertThat(line.getPrice_UOM_ID()).isEqualTo(product.getC_UOM_ID());
	}
}
