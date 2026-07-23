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

class ScriptedImportConversionCommandTest
{
	@Test
	void ofCode_matchesByWireValue()
	{
		assertThat(ScriptedImportConversionCommand.ofCode("enableRestAPI")).isEqualTo(ScriptedImportConversionCommand.EnableRestAPI);
		assertThat(ScriptedImportConversionCommand.ofCode("disableRestAPI")).isEqualTo(ScriptedImportConversionCommand.DisableRestAPI);
		assertThat(ScriptedImportConversionCommand.ofCode("enableSftpPolling")).isEqualTo(ScriptedImportConversionCommand.EnableSftpPolling);
		assertThat(ScriptedImportConversionCommand.ofCode("disableSftpPolling")).isEqualTo(ScriptedImportConversionCommand.DisableSftpPolling);
	}

	@Test
	void ofCode_unknown_throwsClearError()
	{
		assertThatThrownBy(() -> ScriptedImportConversionCommand.ofCode("start"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("start");
	}

	@Test
	void ofCodeOrNull_matchesByWireValue()
	{
		assertThat(ScriptedImportConversionCommand.ofCodeOrNull("enableRestAPI")).isEqualTo(ScriptedImportConversionCommand.EnableRestAPI);
		assertThat(ScriptedImportConversionCommand.ofCodeOrNull("disableSftpPolling")).isEqualTo(ScriptedImportConversionCommand.DisableSftpPolling);
	}

	@Test
	void ofCodeOrNull_unknownOrNull_returnsNull()
	{
		assertThat(ScriptedImportConversionCommand.ofCodeOrNull("start")).isNull();
		assertThat(ScriptedImportConversionCommand.ofCodeOrNull(null)).isNull();
	}

	@Test
	void getIntent_enableMapsToStart_disableMapsToStop()
	{
		assertThat(ScriptedImportConversionCommand.EnableRestAPI.getIntent()).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(ScriptedImportConversionCommand.EnableSftpPolling.getIntent()).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(ScriptedImportConversionCommand.DisableRestAPI.getIntent()).isEqualTo(ScriptedImportConversionIntent.Stop);
		assertThat(ScriptedImportConversionCommand.DisableSftpPolling.getIntent()).isEqualTo(ScriptedImportConversionIntent.Stop);
	}
}
