package de.metas.edi.esb;

/*
 * #%L
 * de.metas.edi.esb
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

import com.ctc.wstx.stax.WstxInputFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ESBCamelApplication
{

	/**
	 * Main method to start this application.
	 */
	public static void main(final String[] args)
	{
		// get http://camel.465427.n5.nabble.com/Seeing-Info-message-all-over-log-file-related-to-Woodstox-td5749044.html
		System.setProperty("javax.xml.stream.XMLInputFactory", WstxInputFactory.class.getName());

		SpringApplication.run(ESBCamelApplication.class, args);
	}

}

