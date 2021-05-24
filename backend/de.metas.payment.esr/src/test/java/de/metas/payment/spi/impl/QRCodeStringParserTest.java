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

import org.junit.jupiter.api.Test;

class QRCodeStringParserTest
{
	@Test
	public void test()
	{
		final String qrCode = ""
				/* line 00 */ + "SPC" + "\n"
				/* line 01 */ + "0200" + "\n"
				/* line 02 */ + "1" + "\n"
				/* line 03 */ + "CH8989144256754461336" + "\n"
				/* line 04 */ + "K" + "\n"
				/* line 05 */ + "intensive care AG" + "\n"
				/* line 06 */ + "intensive care street 22" + "\n"
				/* line 07 */ + "6215 City" + "\n"
				/* line 08 */ + "" + "\n"
				/* line 09 */ + "" + "\n"
				/* line 10 */ + "CH" + "\n"
				/* line 11 */ + "" + "\n"
				/* line 12 */ + "" + "\n"
				/* line 13 */ + "" + "\n"
				/* line 14 */ + "" + "\n"
				/* line 15 */ + "" + "\n"
				/* line 16 */ + "" + "\n"
				/* line 17 */ + "" + "\n"
				/* line 18 */ + "1571.04" + "\n"
				/* line 19 */ + "CHF" + "\n"
				/* line 20 */ + "K" + "\n"
				/* line 21 */ + "Customer" + "\n"
				/* line 22 */ + "Customer Street 2550" + "\n"
				/* line 23 */ + "4002 City" + "\n"
				/* line 24 */ + "" + "\n"
				/* line 25 */ + "" + "\n"
				/* line 26 */ + "CH" + "\n"
				/* line 27 */ + "QRR" + "\n"
				/* line 28 */ + "048290000002156574010493652" + "\n"
				/* line 29 */ + "" + "\n"
				/* line 30 */ + "EPD" + "\n"
				/* line 31 */ + "" + "\n"
				/* line 32 */ + "" + "\n"
				/* line 33 */ + "";

		new QRCodeStringParser().parse(qrCode);
	}
}