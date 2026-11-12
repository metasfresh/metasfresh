/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.document.archive.interceptor;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.api.impl.DocOutboundLogHeaderPopulator;
import de.metas.document.archive.api.impl.DocOutboundService;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientService;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.IAutoCloseable;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests {@link C_Doc_Outbound_Log#updateFromRecipientId(I_C_Doc_Outbound_Log)} and its skip flag {@link C_Doc_Outbound_Log#DYNATTR_SkipUpdateFromRecipientId}.
 */
class C_Doc_Outbound_LogTest
{
	private static final String CONTACT_EMAIL = "contact@email.test";
	private static final String ADDRESS_SET_BY_CALLER = "caller@email.test";

	private C_Doc_Outbound_Log interceptor;
	private I_C_Doc_Outbound_Log logRecord;

	@BeforeEach
	void init()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);

		interceptor = new C_Doc_Outbound_Log(
				new DocOutBoundRecipientService(bpartnerBL),
				new OrderEmailPropagationSysConfigRepository(Services.get(ISysConfigBL.class)),
				DocOutboundService.newInstanceForUnitTesting(),
				DocOutboundLogHeaderPopulator.newInstanceForUnitTesting());

		// partner's invoice-e-mail setting is empty, so the contact's own setting applies to it
		final I_C_BPartner partner = newInstance(I_C_BPartner.class);
		saveRecord(partner);

		final I_AD_User contact = newInstance(I_AD_User.class);
		contact.setName("contact");
		contact.setC_BPartner_ID(partner.getC_BPartner_ID());
		contact.setEMail(CONTACT_EMAIL);
		contact.setIsInvoiceEmailEnabled("Y");
		saveRecord(contact);

		final I_C_Order order = newInstance(I_C_Order.class);
		order.setC_BPartner_ID(partner.getC_BPartner_ID());
		saveRecord(order);

		logRecord = newInstance(I_C_Doc_Outbound_Log.class);
		final TableRecordReference orderRef = TableRecordReference.of(order);
		logRecord.setAD_Table_ID(orderRef.getAD_Table_ID());
		logRecord.setRecord_ID(orderRef.getRecord_ID());
		logRecord.setC_BPartner_ID(partner.getC_BPartner_ID());
		logRecord.setCurrentEMailRecipient_ID(contact.getAD_User_ID());
		logRecord.setCurrentEMailAddress(ADDRESS_SET_BY_CALLER);
		logRecord.setIsInvoiceEmailEnabled(false);
		saveRecord(logRecord);
	}

	@Test
	void recipientChange_takesAddressAndFlagFromTheContact()
	{
		interceptor.updateFromRecipientId(logRecord);

		assertThat(logRecord.getCurrentEMailAddress()).isEqualTo(CONTACT_EMAIL);
		assertThat(logRecord.isInvoiceEmailEnabled()).isTrue();
	}

	@Test
	void skipFlag_suppressesUpdateFromRecipientId()
	{
		try (final IAutoCloseable ignored = C_Doc_Outbound_Log.DYNATTR_SkipUpdateFromRecipientId.temporarySetValue(logRecord, true))
		{
			interceptor.updateFromRecipientId(logRecord);
		}

		assertThat(logRecord.getCurrentEMailAddress()).isEqualTo(ADDRESS_SET_BY_CALLER);
		assertThat(logRecord.isInvoiceEmailEnabled()).isFalse();
	}
}
