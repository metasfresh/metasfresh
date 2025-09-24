package de.metas.document.archive.spi.impl;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipients;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.document.archive.mailrecipient.impl.InvoiceDocOutboundLogMailRecipientProvider;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.order.impl.OrderEmailPropagationSysConfigRepository;
import de.metas.organization.OrgId;
import de.metas.user.UserRepository;
import de.metas.util.Services;
import org.adempiere.service.ClientId;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_AD_SysConfig;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Invoice;
import org.compiere.model.I_C_Order;
import org.jetbrains.annotations.NotNull;
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
import static org.assertj.core.api.Assertions.assertThat;

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
	private OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;

	private I_C_BPartner shipToBPartnerRecord;
	private I_C_BPartner billBPartnerRecord;
	private I_C_Invoice invoiceRecord;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		Services.registerService(IBPartnerBL.class, bpartnerBL);

		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		orderEmailPropagationSysConfigRepository = new OrderEmailPropagationSysConfigRepository(sysConfigBL);

		invoiceDocOutboundLogMailRecipientProvider = new InvoiceDocOutboundLogMailRecipientProvider(
				new DocOutBoundRecipientRepository(bpartnerBL),
				orderEmailPropagationSysConfigRepository,
				bpartnerBL);

		createMasterdata();
	}

	private void createMasterdata()
	{
		shipToBPartnerRecord = createBPartner();
		final I_C_BPartner_Location shipToBPLocationRecord = createBPLocation(shipToBPartnerRecord);

		billBPartnerRecord = createBPartner();
		final I_C_BPartner_Location billBPLocationRecord = createBPLocation(billBPartnerRecord);

		final I_C_Order orderRecord = newInstance(I_C_Order.class);
		orderRecord.setC_BPartner_ID(shipToBPartnerRecord.getC_BPartner_ID());
		orderRecord.setC_BPartner_Location_ID(shipToBPLocationRecord.getC_BPartner_Location_ID());
		orderRecord.setBill_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		orderRecord.setBill_Location_ID(billBPLocationRecord.getC_BPartner_Location_ID());
		saveRecord(orderRecord);

		invoiceRecord = newInstance(I_C_Invoice.class);
		invoiceRecord.setC_Order_ID(orderRecord.getC_Order_ID());
		invoiceRecord.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		invoiceRecord.setC_BPartner_Location_ID(billBPLocationRecord.getC_BPartner_Location_ID());
		saveRecord(invoiceRecord);
	}

	private I_C_BPartner createBPartner()
	{
		I_C_BPartner bpartnerRecord = newInstance(I_C_BPartner.class);
		bpartnerRecord.setAD_Language(AD_Language_en_US);
		saveRecord(bpartnerRecord);
		return bpartnerRecord;
	}

	private @NotNull I_C_BPartner_Location createBPLocation(I_C_BPartner bPartnerRecord)
	{
		final I_C_BPartner_Location billBPLocationRecord = newInstance(I_C_BPartner_Location.class);
		billBPLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		save(billBPLocationRecord);
		return billBPLocationRecord;
	}

	@Test
	public void provideMailRecipient()
	{
		final org.compiere.model.I_AD_User defaultShipContact = newInstance(I_AD_User.class);
		defaultShipContact.setName("defaultShipContact");
		defaultShipContact.setEMail("defaultShipContact.EMail");
		defaultShipContact.setIsShipToContact_Default(true);
		defaultShipContact.setC_BPartner_ID(shipToBPartnerRecord.getC_BPartner_ID());
		saveRecord(defaultShipContact);

		billBPartnerRecord.setIsInvoiceEmailCcToMember(true);
		saveRecord(billBPartnerRecord);

		final org.compiere.model.I_AD_User billContact2 = newInstance(I_AD_User.class);
		billContact2.setName("billContact2");
		billContact2.setEMail(null);
		billContact2.setIsBillToContact_Default(true);
		billContact2.setAD_Language(AD_Language_en_AU);
		billContact2.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		saveRecord(billContact2);

		final org.compiere.model.I_AD_User billContact = newInstance(I_AD_User.class);
		billContact.setName("billContact");
		billContact.setEMail("billContact.EMail");
		billContact.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		billContact.setAD_Language(AD_Language_en_GB);
		billContact.setIsInvoiceEmailEnabled("Y");
		saveRecord(billContact);

		// invoke the method under test
		final Optional<DocOutBoundRecipients> result = invoiceDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRecord.getC_Invoice_ID()))
						.clientId(ClientId.ofRepoId(invoiceRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
						.build());
		assertThat(result).isPresent();
		assertThat(result.get().getTo().getId().getRepoId()).isEqualTo(billContact.getAD_User_ID());
		assertThat(result.get().getTo().getEmailAddress()).isEqualTo("billContact.EMail");
		assertThat(result.get().getTo().getUserLanguage()).isEqualTo(asLanguage(AD_Language_en_GB));
		assertThat(result.get().getTo().getBPartnerLanguage()).isEqualTo(asLanguage(AD_Language_en_US));
		assertThat(result.get().getCc()).isNotNull();

		assertThat(result.get().getCc().getId().getRepoId()).isEqualTo(defaultShipContact.getAD_User_ID());
		assertThat(result.get().getCc().getEmailAddress()).isEqualTo("defaultShipContact.EMail");

	}

	@Test
	public void mailFromDocument_SysConfigNoTable()
	{
		invoiceRecord.setEMail("test@test.test");
		saveRecord(invoiceRecord);
		final org.compiere.model.I_AD_User userRecord2 = newInstance(I_AD_User.class);
		userRecord2.setName("userRecord2");
		userRecord2.setEMail(null);
		userRecord2.setIsBillToContact_Default(true);
		userRecord2.setAD_Language(AD_Language_en_AU);
		userRecord2.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());

		final org.compiere.model.I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("userRecord");
		userRecord.setEMail("userRecord.EMail");
		userRecord.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		userRecord.setAD_Language(AD_Language_en_GB);
		userRecord.setIsInvoiceEmailEnabled("Y");
		saveRecord(userRecord);

		createSysConfigOrderEmailPropagation("N");

		// invoke the method under test
		final Optional<DocOutBoundRecipients> result = invoiceDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRecord.getC_Invoice_ID()))
						.clientId(ClientId.ofRepoId(invoiceRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
						.build());
		assertThat(result).isPresent();
		assertThat(result.get().getTo().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getTo().getEmailAddress()).isEqualTo("userRecord.EMail");
		assertThat(result.get().getTo().getUserLanguage()).isEqualTo(asLanguage(AD_Language_en_GB));
		assertThat(result.get().getTo().getBPartnerLanguage()).isEqualTo(asLanguage(AD_Language_en_US));
	}

	@Test
	public void mailFromUser_SysConfigTable_C_DocOutboundLog()
	{
		invoiceRecord.setEMail("test@test.test");
		saveRecord(invoiceRecord);
		final org.compiere.model.I_AD_User userRecord2 = newInstance(I_AD_User.class);
		userRecord2.setName("userRecord2");
		userRecord2.setEMail(null);
		userRecord2.setIsBillToContact_Default(true);
		userRecord2.setAD_Language(AD_Language_en_AU);
		userRecord2.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());

		final org.compiere.model.I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("userRecord");
		userRecord.setEMail("userRecord.EMail");
		userRecord.setC_BPartner_ID(billBPartnerRecord.getC_BPartner_ID());
		userRecord.setAD_Language(AD_Language_en_GB);
		userRecord.setIsInvoiceEmailEnabled("Y");
		saveRecord(userRecord);

		createSysConfigOrderEmailPropagation(I_C_Doc_Outbound_Log.Table_Name);

		// invoke the method under test
		final Optional<DocOutBoundRecipients> result = invoiceDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_Invoice.Table_Name, invoiceRecord.getC_Invoice_ID()))
						.clientId(ClientId.ofRepoId(invoiceRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID()))
						.build());
		assertThat(result).isPresent();
		assertThat(result.get().getTo().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getTo().getEmailAddress()).isEqualTo("test@test.test");
		assertThat(result.get().getTo().getUserLanguage()).isEqualTo(asLanguage(AD_Language_en_GB));
		assertThat(result.get().getTo().getBPartnerLanguage()).isEqualTo(asLanguage(AD_Language_en_US));
	}

	private I_AD_SysConfig createSysConfigOrderEmailPropagation(final String value)
	{
		final I_AD_SysConfig sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(orderEmailPropagationSysConfigRepository.SYS_CONFIG_C_Order_Email_Propagation);
		sysConfig.setValue(value);
		saveRecord(sysConfig);
		return sysConfig;
	}
}
