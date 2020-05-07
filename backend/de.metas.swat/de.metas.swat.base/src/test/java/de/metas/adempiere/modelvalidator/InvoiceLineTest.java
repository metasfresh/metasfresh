package de.metas.adempiere.modelvalidator;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import static org.junit.Assert.assertNull;

import java.util.Collections;

import org.adempiere.invoice.service.IInvoiceDAO;
import org.adempiere.util.Services;
import org.compiere.model.MInvoiceLine;
import org.compiere.model.ModelValidator;
import org.junit.Test;
import org.junit.runner.RunWith;

import de.metas.adempiere.test.POTest;
import mockit.Expectations;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

// don't know why we need this in this particular class (jmockit.jar is before junit.jar in the classpath), but we do
@RunWith(JMockit.class)
public class InvoiceLineTest {

	@Mocked
	MInvoiceLine ilPO;

	@Mocked
	MInvoiceLine referringIlPO;

	@Mocked
	IInvoiceDAO invoicePA;
	
	@Mocked
	Services services;
	
	/**
	 * When an invoiceline is deleted, the model validator checks for other ils
	 * referring to this one. If there are such il's there reference is nulled
	 */
	@Test
	public void deleteInvoiceLine() {

		POTest.recordGenericExpectations(ilPO, 10);
		
		new Expectations() {{
				Services.get(IInvoiceDAO.class);
				result = invoicePA;

				invoicePA.retrieveReferringLines(POTest.CTX, 10, POTest.TRX_NAME);
				result = Collections.singletonList(referringIlPO);
		}};

		String result = new InvoiceLine().modelChange(ilPO,
				ModelValidator.TYPE_BEFORE_DELETE);
		assertNull(result);

		new Verifications() 
		{{
				referringIlPO.setRef_InvoiceLine_ID(0);
				referringIlPO.saveEx();
		}};
	}
}
