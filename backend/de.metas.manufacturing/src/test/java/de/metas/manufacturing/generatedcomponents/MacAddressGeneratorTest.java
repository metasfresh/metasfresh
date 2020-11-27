/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.manufacturing.generatedcomponents;

import de.metas.document.sequence.IDocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoBuilderFactory;
import de.metas.document.sequence.impl.DocumentNoParts;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

class MacAddressGeneratorTest
{
	private MacAddressGenerator generator;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
		SpringContextHolder.registerJUnitBean(IDocumentNoBuilderFactory.class, new DocumentNoBuilderFactory(Optional.empty()));

		generator = new MacAddressGenerator();
	}

	@Test
	void prefixHasColon()
	{
		final DocumentNoParts documentNoParts = DocumentNoParts.builder()
				.prefix("BB:")
				.suffix("")
				.sequenceNumber("1")
				.build();

		assertThat(generator.generateNextMacAddress0(documentNoParts)).isEqualTo(MacAddress.of("BB:00:00:00:00:01"));
	}

	@Test
	void prefixNoColon()
	{
		final DocumentNoParts documentNoParts = DocumentNoParts.builder()
				.prefix("BB:A")
				.suffix("")
				.sequenceNumber("1234")
				.build();

		assertThat(generator.generateNextMacAddress0(documentNoParts)).isEqualTo(MacAddress.of("BB:A0:00:00:04:D2"));
	}

	@Test
	void dashGroupDelimiter()
	{
		final DocumentNoParts documentNoParts = DocumentNoParts.builder()
				.prefix("BB-A")
				.suffix("")
				.sequenceNumber("1234")
				.build();

		assertThat(generator.generateNextMacAddress0(documentNoParts)).isEqualTo(MacAddress.of("BB-A0-00-00-04-D2"));
	}

	@Test
	void noPrefix()
	{
		final DocumentNoParts documentNoParts = DocumentNoParts.builder()
				.prefix("")
				.suffix("")
				.sequenceNumber("1234321")
				.build();

		assertThat(generator.generateNextMacAddress0(documentNoParts)).isEqualTo(MacAddress.of("00:00:00:12:D5:91"));
	}
}
