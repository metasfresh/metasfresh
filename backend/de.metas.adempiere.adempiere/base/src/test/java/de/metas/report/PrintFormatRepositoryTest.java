/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.report;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_PrintFormat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class PrintFormatRepositoryTest
{
	private PrintFormatRepository printFormatRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();
		printFormatRepository = new PrintFormatRepository();
	}

	private PrintFormatId createPrintFormat(final String name, final boolean isActive)
	{
		final I_AD_PrintFormat record = newInstance(I_AD_PrintFormat.class);
		record.setName(name);
		record.setIsActive(isActive);
		saveRecord(record);
		return PrintFormatId.ofRepoId(record.getAD_PrintFormat_ID());
	}

	@Nested
	class getIdByName
	{
		@Test
		void returnsTheActivePrintFormatWithThatName()
		{
			createPrintFormat("Other", true);
			final PrintFormatId expectedId = createPrintFormat("Wanted", true);

			assertThat(printFormatRepository.getIdByName("Wanted")).isEqualTo(expectedId);
		}

		@Test
		void ignoresInactivePrintFormats()
		{
			createPrintFormat("Wanted", false);
			final PrintFormatId expectedId = createPrintFormat("Wanted", true);

			assertThat(printFormatRepository.getIdByName("Wanted")).isEqualTo(expectedId);
		}

		@Test
		void failsWhenNoActivePrintFormatHasThatName()
		{
			createPrintFormat("Wanted", false);

			assertThatThrownBy(() -> printFormatRepository.getIdByName("Wanted"))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Wanted");
		}

		@Test
		void failsWhenSeveralActivePrintFormatsHaveThatName()
		{
			createPrintFormat("Wanted", true);
			createPrintFormat("Wanted", true);

			assertThatThrownBy(() -> printFormatRepository.getIdByName("Wanted"))
					.isInstanceOf(AdempiereException.class)
					.hasMessageContaining("Wanted");
		}
	}
}
