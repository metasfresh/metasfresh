package de.metas.esb.printing.api;

/*
 * #%L
 * de.metas.printing.esb.api
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import com.fasterxml.jackson.databind.ObjectMapper;

import de.metas.printing.esb.api.PrinterHWList;
import org.junit.jupiter.api.Test;

import java.io.InputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PrinterHWTest
{
	private final ObjectMapper mapper = new ObjectMapper(); // can reuse, share globally

	/**
	 * Test that JSON is correctly loaded
	 */
	@Test
	public void testJSON() throws Exception // NOPMD by User on 5/10/13 2:55 PM
	{
		final InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("add_printer_hw.json");

		final PrinterHWList printer = mapper.readValue(is, PrinterHWList.class);
		// System.out.println("printer=" + printer);

		assertNotNull(printer);
	}
}
