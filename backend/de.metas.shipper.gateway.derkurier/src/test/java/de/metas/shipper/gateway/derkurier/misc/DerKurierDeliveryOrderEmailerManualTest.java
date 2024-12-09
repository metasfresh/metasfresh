package de.metas.shipper.gateway.derkurier.misc;

<<<<<<< HEAD
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.fail;

import java.io.UnsupportedEncodingException;

import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

=======
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.email.EMailAddress;
import de.metas.email.MailService;
import de.metas.email.mailboxes.Mailbox;
<<<<<<< HEAD
import de.metas.email.mailboxes.MailboxRepository;
import de.metas.email.templates.MailTemplateRepository;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
=======
import de.metas.email.mailboxes.MailboxType;
import de.metas.email.mailboxes.SMTPConfig;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_SysConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.nio.charset.StandardCharsets;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

/**
 * This test makes a real invocation on the a mailserver. It's usually {@link Ignore}d.
 */
public class DerKurierDeliveryOrderEmailerManualTest
{

	private AttachmentEntryService attachmentEntryService;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		final I_AD_SysConfig subjectConfig = newInstance(I_AD_SysConfig.class);
		subjectConfig.setName(DerKurierDeliveryOrderEmailer.SYSCONFIG_DerKurier_DeliveryOrder_EmailSubject);
		subjectConfig.setValue("DerKurier_DeliveryOrder_EmailSubject");
		save(subjectConfig);

		final I_AD_SysConfig msgConfig = newInstance(I_AD_SysConfig.class);
		msgConfig.setName(DerKurierDeliveryOrderEmailer.SYSCONFIG_DerKurier_DeliveryOrder_EmailMessage);
		msgConfig.setValue("DerKurier_DeliveryOrder_EmailMessage");
		save(msgConfig);

		attachmentEntryService = AttachmentEntryService.createInstanceForUnitTesting();
	}

	@Test
	@Ignore // remove the ignore to run this test manually
	public void testAttachAndEmail()
	{
		final Mailbox mailbox = Mailbox.builder()
				.email(EMailAddress.ofString("we@derKurier.test"))
<<<<<<< HEAD
				.smtpHost("localhost")
				.smtpPort(25)
				.password("test")
=======
				.type(MailboxType.SMTP)
				.smtpConfig(SMTPConfig.builder()
						.smtpHost("localhost")
						.smtpPort(25)
						.build())
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				.build();

		final I_DerKurier_DeliveryOrder deliveryOrder = newInstance(I_DerKurier_DeliveryOrder.class);
		save(deliveryOrder);

		final AttachmentEntry firstEntry = attachmentEntryService.createNewAttachment(deliveryOrder, "deliveryOrder.csv", generateBytes());

		final DerKurierShipperConfigRepository derKurierShipperConfigRepository = new DerKurierShipperConfigRepository();
<<<<<<< HEAD
		final MailService mailService = new MailService(new MailboxRepository(), new MailTemplateRepository());
=======
		final MailService mailService = MailService.newInstanceForUnitTesting();
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
		final DerKurierDeliveryOrderEmailer derKurierDeliveryOrderEmailer = new DerKurierDeliveryOrderEmailer(
				derKurierShipperConfigRepository,
				attachmentEntryService,
				mailService);

		derKurierDeliveryOrderEmailer.sendAttachmentAsEmail(
<<<<<<< HEAD
				mailbox, 
				EMailAddress.ofString("orderProcessing@derKurier.test"), 
=======
				mailbox,
				EMailAddress.ofString("orderProcessing@derKurier.test"),
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
				firstEntry);

		// now check in your mail server if the mail is OK..
	}

<<<<<<< HEAD
	private final byte[] generateBytes()
	{
		try
		{
			return new String("Test-Attachment-Text-As-Bytes").getBytes("UTF-8");
		}
		catch (final UnsupportedEncodingException e)
		{
			fail("Unable to generate byte for our attachment", e);
			return null;
		}
=======
	private byte[] generateBytes()
	{
		return "Test-Attachment-Text-As-Bytes".getBytes(StandardCharsets.UTF_8);
>>>>>>> 3091b8e938a (externalSystems-Leich+Mehl can invoke a customizable postgREST reports (#19521))
	}
}
