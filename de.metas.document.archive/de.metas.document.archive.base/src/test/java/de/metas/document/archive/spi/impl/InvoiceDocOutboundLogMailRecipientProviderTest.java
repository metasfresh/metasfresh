package de.metas.document.archive.spi.impl;

import static de.metas.i18n.Language.AD_Language_en_AU;
import static de.metas.i18n.Language.AD_Language_en_GB;
import static de.metas.i18n.Language.AD_Language_en_US;
import static de.metas.i18n.Language.asLanguage;
import static org.adempiere.model.InterfaceWrapperHelper.getTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.junit.Before;
import org.junit.Test;

import de.metas.adempiere.model.I_AD_User;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.impl.InvoiceDocOutboundLogMailRecipientProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.user.UserRepository;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.document.archive.base
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class InvoiceDocOutboundLogMailRecipientProviderTest
{
	private I_C_Doc_Outbound_Log docOutboundLogRecord;
	private InvoiceDocOutboundLogMailRecipientProvider invoiceDocOutboundLogMailRecipientProvider;
	private I_C_BPartner bPartnerRecord;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		bPartnerRecord = newInstance(I_C_BPartner.class);
		bPartnerRecord.setAD_Language(AD_Language_en_US);
		saveRecord(bPartnerRecord);

		final I_C_BPartner_Location bPartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		save(bPartnerLocationRecord);

		final I_C_Invoice invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setC_BPartner(bPartnerRecord);
		invoiceRecord.setC_BPartner_Location(bPartnerLocationRecord);
		saveRecord(invoiceRecord);

		docOutboundLogRecord = newInstance(I_C_Doc_Outbound_Log.class);
		docOutboundLogRecord.setAD_Table_ID(getTableId(I_C_Invoice.class));
		docOutboundLogRecord.setRecord_ID(invoiceRecord.getC_Invoice_ID());
		saveRecord(docOutboundLogRecord);

		final UserRepository userRepository = new UserRepository();

		final BPartnerBL bPartnerBL = new BPartnerBL(userRepository);
		Services.registerService(IBPartnerBL.class, bPartnerBL);

		invoiceDocOutboundLogMailRecipientProvider = new InvoiceDocOutboundLogMailRecipientProvider(
				new DocOutBoundRecipientRepository(),
				new BPartnerBL(userRepository));
	}

	@Test
	public void provideMailRecipient()
	{
		final I_AD_User userRecord2 = newInstance(I_AD_User.class);
		userRecord2.setName("userRecord2");
		userRecord2.setEMail(null);
		userRecord2.setIsBillToContact_Default(true);
		userRecord2.setAD_Language(AD_Language_en_AU);
		userRecord2.setC_BPartner(bPartnerRecord);

		final I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("userRecord");
		userRecord.setEMail("userRecord.EMail");
		userRecord.setC_BPartner(bPartnerRecord);
		userRecord.setAD_Language(AD_Language_en_GB);
		saveRecord(userRecord);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = invoiceDocOutboundLogMailRecipientProvider.provideMailRecipient(docOutboundLogRecord);
		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("userRecord.EMail");
		assertThat(result.get().getUserLanguage()).isEqualTo(asLanguage(AD_Language_en_GB));
		assertThat(result.get().getBPartnerLanguage()).isEqualTo(asLanguage(AD_Language_en_US));
	}
}
