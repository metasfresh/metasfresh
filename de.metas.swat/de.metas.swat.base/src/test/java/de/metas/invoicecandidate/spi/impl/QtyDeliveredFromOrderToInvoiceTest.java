package de.metas.invoicecandidate.spi.impl;

import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

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

import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.invoicecandidate.spi.InvoiceCandidateGenerateRequest;
import de.metas.money.MoneyService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = { StartupListener.class, ShutdownListener.class, MoneyService.class, CurrencyRepository.class, InvoiceCandidateRecordService.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class QtyDeliveredFromOrderToInvoiceTest extends AbstractDeliveryTest
{
	@Test
	public void testQtyDelivered()
	{
		final List<I_C_Invoice_Candidate> invoiceCandidates = olHandler
				.createCandidatesFor(InvoiceCandidateGenerateRequest.of(olHandler, orderLine))
				.getC_Invoice_Candidates();
		Assert.assertTrue("There is 1 invoice candidate", invoiceCandidates.size() == 1);
		final I_C_Invoice_Candidate candidate = invoiceCandidates.get(0);
		saveRecord(candidate);
		olHandler.setDeliveredData(candidate);

		Assert.assertEquals("Invalid qty delivered", orderLine.getQtyDelivered(), candidate.getQtyDelivered());
	}
}
