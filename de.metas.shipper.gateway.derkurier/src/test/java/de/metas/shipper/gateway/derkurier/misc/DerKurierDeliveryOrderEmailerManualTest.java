package de.metas.shipper.gateway.derkurier.misc;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Random;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_MailBox;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.IAttachmentBL;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_DeliveryOrder;
import de.metas.shipper.gateway.derkurier.model.I_DerKurier_Shipper_Config;

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
	private Random random;

	@Before
	public void init()
	{
		AdempiereTestHelper.get().init();

		random = new Random(System.currentTimeMillis());
	}

	@Test
	@Ignore // remove the ignore to run this test manually
	public void testAttachAndEmail()
	{
		final I_AD_MailBox mailBox = newInstance(I_AD_MailBox.class);

		mailBox.setEMail("derKurier@test.test");
		mailBox.setSMTPHost("localhost");
		mailBox.setSMTPPort(25);
		mailBox.setPassword("test");

		save(mailBox);

		final I_DerKurier_Shipper_Config derKurierShipperConfig = newInstance(I_DerKurier_Shipper_Config.class);

		derKurierShipperConfig.setAD_MailBox_ID(mailBox.getAD_MailBox_ID());

		final I_DerKurier_DeliveryOrder deliveryOrder = newInstance(I_DerKurier_DeliveryOrder.class);
		save(deliveryOrder);

		Services.get(IAttachmentBL.class).addEntry(deliveryOrder, "CSV-Daten", generateBytes(10));

		final AttachmentEntry firstEntry = Services.get(IAttachmentBL.class).getFirstEntry(deliveryOrder);

		DerKurierDeliveryOrderEmailer deliveryOrderEmailAttachment = new DerKurierDeliveryOrderEmailer(new DerKurierShipperConfigRepository());
		deliveryOrderEmailAttachment.sendAttachmentAsEmail(derKurierShipperConfig, firstEntry);

	}

	private final byte[] generateBytes(final int size)
	{
		final byte[] bytes = new byte[size];
		random.nextBytes(bytes);
		return bytes;
	}
}
