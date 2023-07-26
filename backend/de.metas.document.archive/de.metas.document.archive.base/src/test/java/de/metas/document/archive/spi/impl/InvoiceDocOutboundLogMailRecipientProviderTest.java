package de.metas.document.archive.spi.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.document.archive.mailrecipient.impl.InvoiceDocOutboundLogMailRecipientProvider;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static de.metas.i18n.Language.AD_Language_en_AU;
import static de.metas.i18n.Language.AD_Language_en_GB;
import static de.metas.i18n.Language.AD_Language_en_US;
import static de.metas.i18n.Language.asLanguage;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.*;

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
	private InvoiceDocOutboundLogMailRecipientProvider invoiceDocOutboundLogMailRecipientProvider;
	private I_C_BPartner bPartnerRecord;
	private I_C_Invoice invoiceRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bPartnerRecord = newInstance(I_C_BPartner.class);
		bPartnerRecord.setAD_Language(AD_Language_en_US);
		saveRecord(bPartnerRecord);

		final I_C_BPartner_Location bPartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		save(bPartnerLocationRecord);

		invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		invoiceRecord.setC_BPartner_Location_ID(bPartnerLocationRecord.getC_BPartner_Location_ID());
		saveRecord(invoiceRecord);

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);

		invoiceDocOutboundLogMailRecipientProvider = new InvoiceDocOutboundLogMailRecipientProvider(
				new DocOutBoundRecipientRepository(bpartnerBL),
				bpartnerBL);
	}

	@Test
	public void provideMailRecipient()
	{
		final org.compiere.model.I_AD_User userRecord2 = newInstance(I_AD_User.class);
		userRecord2.setName("userRecord2");
		userRecord2.setEMail(null);
		userRecord2.setIsBillToContact_Default(true);
		userRecord2.setAD_Language(AD_Language_en_AU);
		userRecord2.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());

		final org.compiere.model.I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("userRecord");
		userRecord.setEMail("userRecord.EMail");
		userRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		userRecord.setAD_Language(AD_Language_en_GB);
		saveRecord(userRecord);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = invoiceDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRecord.getC_Invoice_ID()))
						.clientId(ClientId.ofRepoId(invoiceRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
						.build());
		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("userRecord.EMail");
		assertThat(result.get().getUserLanguage()).isEqualTo(asLanguage(AD_Language_en_GB));
		assertThat(result.get().getBPartnerLanguage()).isEqualTo(asLanguage(AD_Language_en_US));
	}
}
