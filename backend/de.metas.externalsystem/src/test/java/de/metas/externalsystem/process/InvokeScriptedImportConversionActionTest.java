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

package de.metas.externalsystem.process;

import de.metas.externalsystem.scriptedimportconversion.ScriptedImportConversionIntent;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class InvokeScriptedImportConversionActionTest
{
	@Test
	void resolveIntent_startStopIntentCodes_areAccepted()
	{
		// the manual "call" process (AD_Process 585512, External_Request = AD_Reference 541998) passes the intent code
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("start")).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("stop")).isEqualTo(ScriptedImportConversionIntent.Stop);
	}

	@Test
	void resolveIntent_concreteEnableCommands_mapToStart()
	{
		// the generic external-system infra (startup reconciler) + ExternalSystem_Service EnableCommand pass the
		// CONCRETE command code as {externalRequest}; the enable variants must resolve to the Start intent
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("enableRestAPI")).isEqualTo(ScriptedImportConversionIntent.Start);
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("enableSftpPolling")).isEqualTo(ScriptedImportConversionIntent.Start);
	}

	@Test
	void resolveIntent_concreteDisableCommands_mapToStop()
	{
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("disableRestAPI")).isEqualTo(ScriptedImportConversionIntent.Stop);
		assertThat(InvokeScriptedImportConversionAction.resolveIntent("disableSftpPolling")).isEqualTo(ScriptedImportConversionIntent.Stop);
	}

	@Test
	void resolveIntent_unknownCode_throwsClearError()
	{
		assertThatThrownBy(() -> InvokeScriptedImportConversionAction.resolveIntent("bogus"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("bogus");
	}
}
