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

import com.google.common.collect.ImmutableList;
import de.metas.externalsystem.endpoint.TransportType;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

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

	@Test
	void ofIntentAndTransport_localFileResolvesToTheLocalFilePollingCommands()
	{
		// Asserted on the wire value, not only on the enum constant: this string is one of the three
		// joints the whole enable chain turns on (ExternalSystem_Service.EnableCommand and the camel
		// component's direct route id are the other two), so a rename of the constant alone must not
		// slip through here.
		assertThat(ScriptedImportConversionCommand.ofIntentAndTransport(ScriptedImportConversionIntent.Start, TransportType.LOCAL_FILE).getValue())
				.isEqualTo("enableLocalFilePolling");
		assertThat(ScriptedImportConversionCommand.ofIntentAndTransport(ScriptedImportConversionIntent.Stop, TransportType.LOCAL_FILE).getValue())
				.isEqualTo("disableLocalFilePolling");
	}

	@Test
	void ofIntentAndTransport_eachTransportGetsItsOwnCommand()
	{
		// Two transports sharing a command means one of them is silently running the other one's route.
		// Distinctness (rather than a fixed expected list) also makes a transport added later fail here
		// unless ofIntentAndTransport was extended for it.
		for (final ScriptedImportConversionIntent intent : ScriptedImportConversionIntent.values())
		{
			final ImmutableList<ScriptedImportConversionCommand> commands = Arrays.stream(TransportType.values())
					.map(transportType -> ScriptedImportConversionCommand.ofIntentAndTransport(intent, transportType))
					.collect(ImmutableList.toImmutableList());

			assertThat(commands).as("commands for intent %s", intent).doesNotHaveDuplicates();
		}
	}
}
