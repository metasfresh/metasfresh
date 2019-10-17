package de.metas.invoicecandidate.api.impl.aggregationEngine;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.junit4.SpringRunner;

import de.metas.ShutdownListener;
import de.metas.StartupListener;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.C_Invoice_Candidate_Builder;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.user.UserRepository;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2019 metas GmbH
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
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {
		StartupListener.class,
		ShutdownListener.class,
		//
		CurrencyRepository.class,
		MoneyService.class,
		InvoiceCandidateRecordService.class })
@DirtiesContext(classMode = ClassMode.BEFORE_CLASS)
public class TestUpdatedLocationAndUser extends AbstractAggregationEngineTestBase
{
	@Override
	@Before
	public void init()
	{
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
	}

	private C_Invoice_Candidate_Builder prepareInvoiceCandidate()
	{
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(1, 2);

		return createInvoiceCandidate()
				.setBillBPartnerAndLocationId(billBPartnerAndLocationId)
				.setPriceEntered(1)
				.setQtyOrdered(1)
				.setSOTrx(true);
	}

	@Test
	public void test_updateLocationAndUser_True()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int newLocationId = createNewDefaultLocation(partnerId);
		final int newUserId = createNewDefaultUser(partnerId, newLocationId, "User1");

		final AggregationEngine engine = AggregationEngine.builder()
				.defaultDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.updateLocationAndContactForInvoice(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBill_Location_ID()).isEqualTo(newLocationId);
		assertThat(invoice.getBill_User_ID()).isEqualTo(newUserId);
	}

	@Test
	public void test_updateLocationAndUser_False()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		final int oldLocationId = ic1.getBill_Location_ID();
		final int oldUserId = ic1.getBill_User_ID() <= 0 ? -1 : ic1.getBill_User_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int newLocationId = createNewDefaultLocation(partnerId);
		createNewDefaultUser(partnerId, newLocationId, "User1");

		final AggregationEngine engine = AggregationEngine.builder()
				.defaultDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.updateLocationAndContactForInvoice(false)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBill_Location_ID()).isEqualTo(oldLocationId);
		assertThat(invoice.getBill_User_ID()).isEqualTo(oldUserId);
	}



	@Test
	public void test_updateLocationAndUser_True_RespectOverride()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		final int overrideLocationId = createNewDefaultLocation(partnerId);
		final int overrideUserId = createNewDefaultUser(partnerId, overrideLocationId, "User1");

		ic1.setBill_User_ID_Override_ID(overrideUserId);
		ic1.setBill_Location_Override_ID(overrideLocationId);

		save(ic1);

		updateInvalidCandidates();
		refresh(ic1);

		final int newLocationId = createNewDefaultLocation(partnerId);
		createNewDefaultUser(partnerId, newLocationId, "User2");

		final AggregationEngine engine = AggregationEngine.builder()
				.defaultDateInvoiced(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.updateLocationAndContactForInvoice(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBill_Location_ID()).isEqualTo(overrideLocationId);
		assertThat(invoice.getBill_User_ID()).isEqualTo(overrideUserId);
	}


	private int createNewDefaultLocation(int partnerId)
	{
		final I_C_BPartner_Location newBillLocation = newInstance(I_C_BPartner_Location.class);
		newBillLocation.setC_BPartner_ID(partnerId);
		newBillLocation.setIsBillToDefault(true);
		newBillLocation.setIsBillTo(true);
		save(newBillLocation);

		return newBillLocation.getC_BPartner_Location_ID();
	}

	private int createNewDefaultUser(final int partnerId, final int locationId, final String name)
	{
		final I_AD_User newBillUser = newInstance(I_AD_User.class);
		newBillUser.setC_BPartner_ID(partnerId);
		newBillUser.setName(name);
		newBillUser.setIsBillToContact_Default(true);
		newBillUser.setC_BPartner_Location_ID(locationId);
		save(newBillUser);

		return newBillUser.getAD_User_ID();
	}

}
