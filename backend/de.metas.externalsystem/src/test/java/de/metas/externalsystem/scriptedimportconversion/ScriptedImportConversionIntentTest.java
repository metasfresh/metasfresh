/*
 * #%L
 * de.metas.externalsystem
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

package de.metas.externalsystem.scriptedimportconversion;

import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ScriptedImportConversionIntentTest
{
	@Test
	void ofCode_matchesByCode()
	{
		assertThat(ScriptedImportConversionIntent.ofCode("start")).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(ScriptedImportConversionIntent.ofCode("stop")).isEqualTo(ScriptedImportConversionIntent.Stop);
	}

	@Test
	void ofCode_unknown_throwsClearError()
	{
		assertThatThrownBy(() -> ScriptedImportConversionIntent.ofCode("enableRestAPI"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("enableRestAPI");
	}

	@Test
	void ofCodeOrNull_matchesByCode()
	{
		assertThat(ScriptedImportConversionIntent.ofCodeOrNull("start")).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(ScriptedImportConversionIntent.ofCodeOrNull("stop")).isEqualTo(ScriptedImportConversionIntent.Stop);
	}

	@Test
	void ofCodeOrNull_unknownOrNull_returnsNull()
	{
		assertThat(ScriptedImportConversionIntent.ofCodeOrNull("enableRestAPI")).isNull();
		assertThat(ScriptedImportConversionIntent.ofCodeOrNull(null)).isNull();
	}
}
