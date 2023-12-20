package de.metas.dunning.document.archive;

import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipient;
import de.metas.document.archive.mailrecipient.DocOutBoundRecipientRepository;
import de.metas.document.archive.mailrecipient.DocOutboundLogMailRecipientRequest;
import de.metas.document.archive.model.I_C_Doc_Outbound_Log;
import de.metas.dunning.invoice.DunningService;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.getModelTableId;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

/*
 * #%L
 * de.metas.dunning
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

public class DunningDocOutboundLogMailRecipientProviderTest
{
	private DunningDocOutboundLogMailRecipientProvider dunningDocOutboundLogMailRecipientProvider;
	private I_C_BPartner bPartnerRecord;
	private I_C_BPartner_Location bPartnerLocationRecord;
	private OrderEmailPropagationSysConfigRepository orderEmailPropagationSysConfigRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		bPartnerRecord = newInstance(I_C_BPartner.class);
		saveRecord(bPartnerRecord);

		bPartnerLocationRecord = newInstance(I_C_BPartner_Location.class);
		bPartnerLocationRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		save(bPartnerLocationRecord);

		final UserRepository userRepository = new UserRepository();
		final BPartnerBL bpartnerBL = new BPartnerBL(userRepository);


		final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
		orderEmailPropagationSysConfigRepository = new OrderEmailPropagationSysConfigRepository(sysConfigBL);

		dunningDocOutboundLogMailRecipientProvider = new DunningDocOutboundLogMailRecipientProvider(
				new DocOutBoundRecipientRepository(bpartnerBL),
				orderEmailPropagationSysConfigRepository,
				bpartnerBL,
				new DunningService());

		Services.registerService(IBPartnerBL.class, bpartnerBL);
	}

	@Test
	public void provideMailRecipient_dunned_invoices_with_common_email_user()
	{
		final org.compiere.model.I_AD_User userRecord = createUserRecord("userRecord.EMail");

		final I_C_Invoice invoiceRecord1 = createInvoiceRecord(userRecord, null);
		final I_C_Invoice invoiceRecord2 = createInvoiceRecord(userRecord, null);

		final I_C_Dunning_Candidate candidateRecord1 = createCandidateRecord(invoiceRecord1);
		final I_C_Dunning_Candidate candidateRecord2 = createCandidateRecord(invoiceRecord2);

		final I_C_DunningDoc dunningDocRecord = createDunningDocRecord();

		final I_C_DunningDoc_Line docLineRecord1 = createDocLineRecord(dunningDocRecord);
		final I_C_DunningDoc_Line docLineRecord2 = createDocLineRecord(dunningDocRecord);

		createDocLineSourceRecord(candidateRecord1, docLineRecord1);
		createDocLineSourceRecord(candidateRecord2, docLineRecord2);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = dunningDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocRecord.getC_DunningDoc_ID()))
						.clientId(ClientId.ofRepoId(dunningDocRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(dunningDocRecord.getAD_Org_ID()))
						.build());

		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("userRecord.EMail");
	}

	@Test
	public void provideMailRecipient_dunned_invoices_with_common_email_SysConfigNoTable()
	{
		final org.compiere.model.I_AD_User userRecord = createUserRecord("userRecord.EMail");

		final I_C_Invoice invoiceRecord1 = createInvoiceRecord(userRecord, "test@test.test");
		final I_C_Invoice invoiceRecord2 = createInvoiceRecord(userRecord, "test@test.test");

		final I_C_Dunning_Candidate candidateRecord1 = createCandidateRecord(invoiceRecord1);
		final I_C_Dunning_Candidate candidateRecord2 = createCandidateRecord(invoiceRecord2);

		final I_C_DunningDoc dunningDocRecord = createDunningDocRecord();

		final I_C_DunningDoc_Line docLineRecord1 = createDocLineRecord(dunningDocRecord);
		final I_C_DunningDoc_Line docLineRecord2 = createDocLineRecord(dunningDocRecord);

		createDocLineSourceRecord(candidateRecord1, docLineRecord1);
		createDocLineSourceRecord(candidateRecord2, docLineRecord2);

		createSysConfigOrderEmailPropagation("N");

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = dunningDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocRecord.getC_DunningDoc_ID()))
						.clientId(ClientId.ofRepoId(dunningDocRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(dunningDocRecord.getAD_Org_ID()))
						.build());

		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("userRecord.EMail");
	}


	@Test
	public void provideMailRecipient_dunned_invoices_with_common_email_SysConfigTable_C_DocOutboundLog()
	{
		final org.compiere.model.I_AD_User userRecord = createUserRecord("userRecord.EMail");

		final I_C_Invoice invoiceRecord1 = createInvoiceRecord(userRecord, "test@test.test");
		final I_C_Invoice invoiceRecord2 = createInvoiceRecord(userRecord, "test@test.test");

		final I_C_Dunning_Candidate candidateRecord1 = createCandidateRecord(invoiceRecord1);
		final I_C_Dunning_Candidate candidateRecord2 = createCandidateRecord(invoiceRecord2);

		final I_C_DunningDoc dunningDocRecord = createDunningDocRecord();

		final I_C_DunningDoc_Line docLineRecord1 = createDocLineRecord(dunningDocRecord);
		final I_C_DunningDoc_Line docLineRecord2 = createDocLineRecord(dunningDocRecord);

		createDocLineSourceRecord(candidateRecord1, docLineRecord1);
		createDocLineSourceRecord(candidateRecord2, docLineRecord2);

		createSysConfigOrderEmailPropagation(I_C_Doc_Outbound_Log.Table_Name);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = dunningDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocRecord.getC_DunningDoc_ID()))
						.clientId(ClientId.ofRepoId(dunningDocRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(dunningDocRecord.getAD_Org_ID()))
						.build());

		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(userRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("test@test.test");
	}

	@Test
	public void provideMailRecipient_dunned_invoices_without_common_emailuser()
	{
		final org.compiere.model.I_AD_User invoiceUserRecord = createUserRecord("userRecord.EMail");

		final org.compiere.model.I_AD_User bPartnerUserRecord = newInstance(I_AD_User.class);
		bPartnerUserRecord.setName("bPartnerUserRecord");
		bPartnerUserRecord.setEMail("bPartnerUserRecord.EMail");
		bPartnerUserRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		saveRecord(bPartnerUserRecord);

		final I_C_Invoice invoiceRecord1 = createInvoiceRecord(invoiceUserRecord, null);
		final I_C_Invoice invoiceRecord2 = createInvoiceRecord(null, null);

		final I_C_Dunning_Candidate candidateRecord1 = createCandidateRecord(invoiceRecord1);
		final I_C_Dunning_Candidate candidateRecord2 = createCandidateRecord(invoiceRecord2);

		final I_C_DunningDoc dunningDocRecord = createDunningDocRecord();

		final I_C_DunningDoc_Line docLineRecord1 = createDocLineRecord(dunningDocRecord);
		final I_C_DunningDoc_Line docLineRecord2 = createDocLineRecord(dunningDocRecord);

		createDocLineSourceRecord(candidateRecord1, docLineRecord1);
		createDocLineSourceRecord(candidateRecord2, docLineRecord2);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = dunningDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocRecord.getC_DunningDoc_ID()))
						.clientId(ClientId.ofRepoId(dunningDocRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(dunningDocRecord.getAD_Org_ID()))
						.build());

		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(bPartnerUserRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("bPartnerUserRecord.EMail");
	}



	@Test
	public void provideMailRecipient_dunned_invoices_without_common_email()
	{
		final org.compiere.model.I_AD_User invoiceUserRecord = createUserRecord("userRecord.EMail");

		final org.compiere.model.I_AD_User bPartnerUserRecord = newInstance(I_AD_User.class);
		bPartnerUserRecord.setName("bPartnerUserRecord");
		bPartnerUserRecord.setEMail("bPartnerUserRecord.EMail");
		bPartnerUserRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		saveRecord(bPartnerUserRecord);

		final I_C_Invoice invoiceRecord1 = createInvoiceRecord(invoiceUserRecord, "test@test1.test");
		final I_C_Invoice invoiceRecord2 = createInvoiceRecord(bPartnerUserRecord, "test@test2.test");

		final I_C_Dunning_Candidate candidateRecord1 = createCandidateRecord(invoiceRecord1);
		final I_C_Dunning_Candidate candidateRecord2 = createCandidateRecord(invoiceRecord2);

		final I_C_DunningDoc dunningDocRecord = createDunningDocRecord();

		final I_C_DunningDoc_Line docLineRecord1 = createDocLineRecord(dunningDocRecord);
		final I_C_DunningDoc_Line docLineRecord2 = createDocLineRecord(dunningDocRecord);

		createDocLineSourceRecord(candidateRecord1, docLineRecord1);
		createDocLineSourceRecord(candidateRecord2, docLineRecord2);

		// invoke the method under test
		final Optional<DocOutBoundRecipient> result = dunningDocOutboundLogMailRecipientProvider.provideMailRecipient(
				DocOutboundLogMailRecipientRequest.builder()
						.recordRef(TableRecordReference.of(I_C_DunningDoc.Table_Name, dunningDocRecord.getC_DunningDoc_ID()))
						.clientId(ClientId.ofRepoId(dunningDocRecord.getAD_Client_ID()))
						.orgId(OrgId.ofRepoId(dunningDocRecord.getAD_Org_ID()))
						.build());

		assertThat(result).isPresent();
		assertThat(result.get().getId().getRepoId()).isEqualTo(bPartnerUserRecord.getAD_User_ID());
		assertThat(result.get().getEmailAddress()).isEqualTo("bPartnerUserRecord.EMail");
	}

	@SuppressWarnings("SameParameterValue")
	private org.compiere.model.I_AD_User createUserRecord(final String eMail)
	{
		final org.compiere.model.I_AD_User userRecord = newInstance(I_AD_User.class);
		userRecord.setName("userRecord");
		userRecord.setEMail(eMail);
		userRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		saveRecord(userRecord);
		return userRecord;
	}

	private I_C_Invoice createInvoiceRecord(@Nullable final org.compiere.model.I_AD_User userRecord, @Nullable final String invoiceEmail)
	{
		final I_C_Invoice invoiceRecord2 = newInstance(I_C_Invoice.class);
		invoiceRecord2.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		invoiceRecord2.setEMail(invoiceEmail);
		if (userRecord != null)
		{
			invoiceRecord2.setAD_User_ID(userRecord.getAD_User_ID());
		}
		saveRecord(invoiceRecord2);
		return invoiceRecord2;
	}

	private I_C_Dunning_Candidate createCandidateRecord(final I_C_Invoice invoiceRecord)
	{
		final I_C_Dunning_Candidate candidateRecord1 = newInstance(I_C_Dunning_Candidate.class);
		candidateRecord1.setRecord_ID(invoiceRecord.getC_Invoice_ID());
		candidateRecord1.setAD_Table_ID(getModelTableId(invoiceRecord));
		saveRecord(candidateRecord1);
		return candidateRecord1;
	}

	private I_C_DunningDoc createDunningDocRecord()
	{
		final I_C_DunningDoc dunningDocRecord = newInstance(I_C_DunningDoc.class);
		dunningDocRecord.setC_BPartner_ID(bPartnerRecord.getC_BPartner_ID());
		dunningDocRecord.setC_BPartner_Location_ID(bPartnerLocationRecord.getC_BPartner_Location_ID());
		saveRecord(dunningDocRecord);
		return dunningDocRecord;
	}

	private I_C_DunningDoc_Line createDocLineRecord(final I_C_DunningDoc docRecord)
	{
		final I_C_DunningDoc_Line docLineRecord2 = newInstance(I_C_DunningDoc_Line.class);
		docLineRecord2.setC_DunningDoc(docRecord);
		saveRecord(docLineRecord2);
		return docLineRecord2;
	}

	private void createDocLineSourceRecord(final I_C_Dunning_Candidate candidateRecord, final I_C_DunningDoc_Line docLineRecord)
	{
		final I_C_DunningDoc_Line_Source docLineSourceRecord2 = newInstance(I_C_DunningDoc_Line_Source.class);
		docLineSourceRecord2.setC_Dunning_Candidate(candidateRecord);
		docLineSourceRecord2.setC_DunningDoc_Line(docLineRecord);
		saveRecord(docLineSourceRecord2);
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
