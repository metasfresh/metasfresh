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

package de.metas.contracts.commission.salesrep;

import de.metas.contracts.commission.salesrep.interceptor.C_Invoice;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.IModelInterceptorRegistry;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

public class InvoiceTest
{
	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		Services.get(IModelInterceptorRegistry.class).addModelInterceptor(new C_Invoice(new DocumentSalesRepDescriptorFactory(), new DocumentSalesRepDescriptorService()));
	}

	@Test
	public void testSalesRepSameIdAsBPartner()
	{
		final I_C_BPartner partner = createPartner();
		final I_C_Invoice invoice = newInstance(I_C_Invoice.class);
		invoice.setC_BPartner_ID(partner.getC_BPartner_ID());
		invoice.setC_BPartner_SalesRep_ID(partner.getC_BPartner_ID());
		Assertions.assertNotEquals(0, invoice.getC_BPartner_SalesRep_ID());
		save(invoice);

		Assertions.assertEquals(0, invoice.getC_BPartner_SalesRep_ID());

	}

	private I_C_BPartner createPartner()
	{
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		partner.setName("dummy1");

		save(partner);

		return partner;
	}
}
