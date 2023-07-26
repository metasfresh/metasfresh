package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.X_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import lombok.NonNull;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_commons
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Repository
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class ExportConfigRepository
{
	private static final ImmutableMap<String, XmlVersion> //
	INTERNALNAME_TO_VERSION = ImmutableMap.of(X_HC_Forum_Datenaustausch_Config.EXPORTEDXMLVERSION_V440, XmlVersion.v440);

	private static final ImmutableMap<String, XmlMode> //
	INTERNALNAME_TO_MODE = ImmutableMap.of(
			X_HC_Forum_Datenaustausch_Config.EXPORTEDXMLMODE_Production, XmlMode.PRODUCTION,
			X_HC_Forum_Datenaustausch_Config.EXPORTEDXMLMODE_Test, XmlMode.TEST);

	public ExportConfig getForQueryOrNull(@NonNull final BPartnerQuery query)
	{
		final I_HC_Forum_Datenaustausch_Config configRecord = ConfigRepositoryUtil.retrieveRecordForQueryOrNull(query);
		return ofRecordOrNull(configRecord);
	}

	private ExportConfig ofRecordOrNull(@Nullable final I_HC_Forum_Datenaustausch_Config queryRecord)
	{
		if (queryRecord == null)
		{
			return null;
		}
		return ExportConfig
				.builder()
				.xmlVersion(INTERNALNAME_TO_VERSION.get(queryRecord.getExportedXmlVersion()))
				.mode(INTERNALNAME_TO_MODE.get(queryRecord.getExportedXmlMode()))
				.fromEAN(queryRecord.getFrom_EAN())
				.viaEAN(queryRecord.getVia_EAN())
				.build();
	}
}
