package de.metas.invoice.service.impl;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableList;

import de.metas.adempiere.model.I_C_Invoice;
import de.metas.adempiere.model.I_C_InvoiceLine;
import de.metas.currency.CurrencyRepository;

public class InvoiceBLRenumberTests
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(new CurrencyRepository());
	}

	/**
	 * Creates five invoice lines, with partially same line numbers. Flags three of the with {@link InvoiceBL#setHasFixedLineNumber(I_C_InvoiceLine, boolean)} and then renumber them by calling
	 * {@link InvoiceBL#renumberLines(java.util.List, int)}.<br>
	 * Verify that they are renumbered such that the invoice lines with fixed number retain that number and that only the reset actually get renumbered.
	 */
	@Test
	public void test()
	{
		final InvoiceBL invoiceBL = new InvoiceBL();

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		InterfaceWrapperHelper.save(invoice);

		final I_C_InvoiceLine il1 = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		il1.setC_Invoice(invoice);
		il1.setLine(10);
		InterfaceWrapperHelper.save(il1);

		final I_C_InvoiceLine il2 = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		il2.setC_Invoice(invoice);
		il2.setLine(20);
		InterfaceWrapperHelper.save(il2);

		final I_C_InvoiceLine il3 = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		il3.setC_Invoice(invoice);
		il3.setLine(10);
		InterfaceWrapperHelper.save(il3);
		invoiceBL.setHasFixedLineNumber(il3, true);

		final I_C_InvoiceLine il4 = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		il4.setC_Invoice(invoice);
		il4.setLine(20);
		InterfaceWrapperHelper.save(il4);
		invoiceBL.setHasFixedLineNumber(il4, true);

		final I_C_InvoiceLine il5 = InterfaceWrapperHelper.newInstance(I_C_InvoiceLine.class);
		il5.setC_Invoice(invoice);
		il5.setLine(30);
		InterfaceWrapperHelper.save(il5);
		invoiceBL.setHasFixedLineNumber(il5, true);

		final ImmutableList<I_C_InvoiceLine> lines = ImmutableList.<I_C_InvoiceLine> of(il1, il2, il3, il4, il5);

		invoiceBL.renumberLines(lines, 10);

		assertThat(il1.getLine(), is(40));
		assertThat(il2.getLine(), is(50));
		assertThat(il3.getLine(), is(10));
		assertThat(il4.getLine(), is(20));
		assertThat(il5.getLine(), is(30));
	}

}
