/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.payment.spi.impl;

import de.metas.banking.payment.PaymentString;
import org.adempiere.test.AdempiereTestHelper;
import org.apache.commons.io.IOUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;

import static org.assertj.core.api.Assertions.*;

class QRCodeStringParserTest
{
	private static final String expectedIBAN = "CH1589144626811245431";
	private static final BigDecimal expectedAMT = new BigDecimal("100.80");

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
	}

	@Test
	public void testWithSampleFile() throws IOException
	{
		final InputStream inputStream = getClass().getResourceAsStream("/de/metas/payment/spi/impl/QRR_PurchaseInvoice.txt");
		assertThat(inputStream).isNotNull();

		final String qrCode = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());

		final PaymentString paymentString = new QRCodeStringParser().parse(qrCode);

		assertThat(paymentString.getAmount()).isEqualTo(expectedAMT);

		assertThat(paymentString.getIBAN()).isEqualTo(expectedIBAN);
	}
}
