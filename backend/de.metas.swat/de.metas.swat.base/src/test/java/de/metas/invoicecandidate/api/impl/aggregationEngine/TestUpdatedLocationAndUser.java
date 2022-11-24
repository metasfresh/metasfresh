package de.metas.invoicecandidate.api.impl.aggregationEngine;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.business.BusinessTestHelper;
import de.metas.currency.CurrencyRepository;
import de.metas.invoicecandidate.C_Invoice_Candidate_Builder;
import de.metas.invoicecandidate.api.IInvoiceHeader;
import de.metas.invoicecandidate.api.impl.AggregationEngine;
import de.metas.invoicecandidate.internalbusinesslogic.InvoiceCandidateRecordService;
import de.metas.invoicecandidate.model.I_C_Invoice_Candidate;
import de.metas.money.MoneyService;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.refresh;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.*;

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
public class TestUpdatedLocationAndUser extends AbstractAggregationEngineTestBase
{
	@Override
	@Before
	public void init()
	{
		Services.registerService(IBPartnerBL.class, new BPartnerBL(new UserRepository()));
		SpringContextHolder.registerJUnitBean(new InvoiceCandidateRecordService());
		SpringContextHolder.registerJUnitBean(new MoneyService(new CurrencyRepository()));

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		SpringContextHolder.registerJUnitBean(new OrderEmailPropagationSysConfigRepository(sysConfigBL));
	}

	private C_Invoice_Candidate_Builder prepareInvoiceCandidate()
	{
		final I_C_BPartner bPartner = BusinessTestHelper.createBPartner("test-bp");
		final I_C_BPartner_Location bPartnerLocation = BusinessTestHelper.createBPartnerLocation(bPartner);
		final BPartnerLocationId billBPartnerAndLocationId = BPartnerLocationId.ofRepoId(bPartnerLocation.getC_BPartner_ID(), bPartnerLocation.getC_BPartner_Location_ID());

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

		final int newLocationId = createNewDefaultLocation(partnerId, true);
		final int newUserId = createNewDefaultUser(partnerId, newLocationId, "User1", true);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(newLocationId);
		assertThat(invoice.getBillTo().getContactId().getRepoId()).isEqualTo(newUserId);
	}

	@Test
	public void test_updateLocationAndUser_True_InactiveLocation()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int oldLocationId = ic1.getBill_Location_ID();

		final int newLocationId = createNewDefaultLocation(partnerId, false);
		final int newUserId = createNewDefaultUser(partnerId, newLocationId, "User1", true);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(oldLocationId);
		assertThat(invoice.getBillTo().getContactId().getRepoId()).isEqualTo(newUserId);
	}

	@Test
	public void test_updateLocationAndUser_True_InactiveUser()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int oldLocationId = ic1.getBill_Location_ID();
		final int oldUserId = ic1.getBill_User_ID() <= 0 ? -1 : ic1.getBill_User_ID();

		final int newLocationId = createNewDefaultLocation(partnerId, false);
		createNewDefaultUser(partnerId, newLocationId, "User1", false);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(oldLocationId);
		assertThat(BPartnerContactId.toRepoId(invoice.getBillTo().getContactId())).isEqualTo(oldUserId);
	}

	@Test
	public void test_updateLocationAndUser_True_BothInactive()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int oldLocationId = ic1.getBill_Location_ID();

		final int oldUserId = ic1.getBill_User_ID() <= 0 ? -1 : ic1.getBill_User_ID();

		final int newLocationId = createNewDefaultLocation(partnerId, false);
		createNewDefaultUser(partnerId, newLocationId, "User1", false);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(oldLocationId);
		assertThat(BPartnerContactId.toRepoId(invoice.getBillTo().getContactId())).isEqualTo(oldUserId);
	}

	@Test
	public void test_updateLocationAndUser_True_UserWithNoLocation()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		updateInvalidCandidates();
		refresh(ic1);

		final int newLocationId = createNewDefaultLocation(partnerId, true);
		final int newUserId = createNewDefaultUser(partnerId, -1, "User1", true);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(newLocationId);
		assertThat(invoice.getBillTo().getContactId().getRepoId()).isEqualTo(newUserId);
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

		final int newLocationId = createNewDefaultLocation(partnerId, true);
		createNewDefaultUser(partnerId, newLocationId, "User1", true);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(false)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(oldLocationId);
		assertThat(BPartnerContactId.toRepoId(invoice.getBillTo().getContactId())).isEqualTo(oldUserId);
	}

	@Test
	public void test_updateLocationAndUser_True_RespectOverride()
	{
		final I_C_Invoice_Candidate ic1 = prepareInvoiceCandidate().build();
		int partnerId = ic1.getBill_BPartner_ID();

		final int overrideLocationId = createNewDefaultLocation(partnerId, true);
		final int overrideUserId = createNewDefaultUser(partnerId, overrideLocationId, "User1", true);

		ic1.setBill_User_ID_Override_ID(overrideUserId);
		ic1.setBill_Location_Override_ID(overrideLocationId);

		save(ic1);

		updateInvalidCandidates();
		refresh(ic1);

		final int newLocationId = createNewDefaultLocation(partnerId, true);
		createNewDefaultUser(partnerId, newLocationId, "User2", true);

		final AggregationEngine engine = AggregationEngine.builder()
				.dateInvoicedParam(LocalDate.of(2019, Month.SEPTEMBER, 1))
				.bpartnerBL(new BPartnerBL(new UserRepository()))
				.useDefaultBillLocationAndContactIfNotOverride(true)
				.build();

		engine.addInvoiceCandidate(ic1);

		final List<IInvoiceHeader> invoices = engine.aggregate();
		assertThat(invoices).hasSize(1);

		final IInvoiceHeader invoice = invoices.get(0);
		assertThat(invoice.getBillTo().getBpartnerLocationId().getRepoId()).isEqualTo(overrideLocationId);
		assertThat(invoice.getBillTo().getContactId().getRepoId()).isEqualTo(overrideUserId);
	}

	private int createNewDefaultLocation(int partnerId, boolean isActive)
	{
		final I_C_BPartner_Location newBillLocation = newInstance(I_C_BPartner_Location.class);
		newBillLocation.setC_BPartner_ID(partnerId);
		newBillLocation.setIsBillToDefault(true);
		newBillLocation.setIsBillTo(true);
		newBillLocation.setIsActive(isActive);
		save(newBillLocation);

		return newBillLocation.getC_BPartner_Location_ID();
	}

	private int createNewDefaultUser(final int partnerId, final int locationId, final String name, boolean isActive)
	{
		final I_AD_User newBillUser = newInstance(I_AD_User.class);
		newBillUser.setC_BPartner_ID(partnerId);
		newBillUser.setName(name);
		newBillUser.setIsBillToContact_Default(true);
		newBillUser.setC_BPartner_Location_ID(locationId);
		newBillUser.setIsActive(isActive);
		save(newBillUser);

		return newBillUser.getAD_User_ID();
	}

}
