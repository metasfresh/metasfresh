package de.metas.shipper.gateway.derkurier.misc;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.fail;

import java.io.UnsupportedEncodingException;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_SysConfig;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.email.Mailbox;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;

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
	}

	@Test
	@Ignore // remove the ignore to run this test manually
	public void testAttachAndEmail()
	{
		final Mailbox mailbox = Mailbox.builder()
				.email("we@derKurier.test")
				.smtpHost("localhost")
				.smtpPort(25)
				.password("test")
				.build();

		final I_DerKurier_DeliveryOrder deliveryOrder = newInstance(I_DerKurier_DeliveryOrder.class);
		save(deliveryOrder);

		Services.get(IAttachmentBL.class).addEntry(deliveryOrder, "deliveryOrder.csv", generateBytes());
		final AttachmentEntry firstEntry = Services.get(IAttachmentBL.class).getFirstEntry(deliveryOrder);

		final DerKurierDeliveryOrderEmailer derKurierDeliveryOrderEmailer = new DerKurierDeliveryOrderEmailer(new DerKurierShipperConfigRepository());
		derKurierDeliveryOrderEmailer.sendAttachmentAsEmail(mailbox, "orderProcessing@derKurier.test", firstEntry);

		// now check in your mail server if the mail is OK..
	}

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
	}
}
