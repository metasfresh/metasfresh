/*
 * #%L
 * de.metas.edi
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

package de.metas.edi.api.impl;

import de.metas.edi.process.export.json.M_InOut_EPCIS_Export_JSON;
import de.metas.externalsystem.scriptedexportconversion.ExternalSystemScriptedExportConversionConfig;
import de.metas.logging.LogManager;
import de.metas.process.AdProcessId;
import de.metas.process.IADProcessDAO;
import de.metas.util.Services;
import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.Nullable;
import java.util.Objects;

/**
 * Single source of truth for "is this scripted-export config the EPCIS outbound export?" — i.e. does
 * its outbound-data process resolve to {@link M_InOut_EPCIS_Export_JSON}. Shared by every consumer
 * (the success listener that writes the transmission ledger, and the reverse guard) so the gating
 * cannot silently drift between them.
 */
@Component
public class EpcisExportConfigMatcher
{
	private static final Logger logger = LogManager.getLogger(EpcisExportConfigMatcher.class);

	// IADProcessDAO is an ISingletonService — must be obtained via Services.get, not a constructor param,
	// else this @Component fails to wire at context boot with NoSuchBeanDefinitionException.
	private final IADProcessDAO adProcessDAO = Services.get(IADProcessDAO.class);

	public boolean isEpcisExportConfig(@NonNull final ExternalSystemScriptedExportConversionConfig config)
	{
		final AdProcessId epcisExportProcessId = getProcessIdOrNull();
		// Fail SAFE, not fail-open: a null EPCIS export process means EPCIS export is not (uniquely)
		// registered, so NO config can be the EPCIS export — never match a config just because its
		// own outbound process happens to be null. The null case is logged below for visibility.
		return epcisExportProcessId != null
				&& Objects.equals(config.getOutboundDataProcessId(), epcisExportProcessId);
	}

	@Nullable
	private AdProcessId getProcessIdOrNull()
	{
		final AdProcessId processId = adProcessDAO.retrieveProcessIdByClassIfUnique(M_InOut_EPCIS_Export_JSON.class);
		if (processId == null)
		{
			// Not-unique (0 or >1 AD_Process rows for the class): a misconfiguration. Log it — silently
			// treating every EPCIS check as "no match" would defeat both the ledger write and the
			// reverse guard without a trace.
			logger.error("No unique AD_Process for {} — EPCIS export gating will match no config."
					+ " Check the AD_Process registration.", M_InOut_EPCIS_Export_JSON.class.getName());
		}
		return processId;
	}
}
