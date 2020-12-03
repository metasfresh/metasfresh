/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
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

package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config;

import de.metas.bpartner.BPGroupId;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.bpartner.service.IBPGroupDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import de.metas.vertical.healthcare.forum_datenaustausch_ch.commons.model.I_HC_Forum_Datenaustausch_Config;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import lombok.NonNull;
import org.compiere.model.I_C_BP_Group;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import javax.annotation.Nullable;

@Repository
@Profile(ForumDatenaustauschChConstants.PROFILE)
public class ImportConfigRepository
{
	private final IBPGroupDAO groupDAO = Services.get(IBPGroupDAO.class);

	public ImportConfig getForQueryOrNull(@NonNull final BPartnerQuery query)
	{
		final I_HC_Forum_Datenaustausch_Config configRecord = ConfigRepositoryUtil.retrieveRecordForQueryOrNull(query);
		return ofRecordOrNull(configRecord);
	}

	private ImportConfig ofRecordOrNull(@Nullable final I_HC_Forum_Datenaustausch_Config configRecord)
	{
		if (configRecord == null)
		{
			return null;
		}

		final ImportConfig.ImportConfigBuilder builder = ImportConfig.builder();

		final BPGroupId patientBPGroupId = BPGroupId.ofRepoIdOrNull(configRecord.getImportedPartientBP_Group_ID());
		if (patientBPGroupId != null)
		{
			final I_C_BP_Group patientBPartnerGroup = Check.assumeNotNull(
					groupDAO.getById(patientBPGroupId),
					"Unable to load BP_Group referenced by HC_Forum_Datenaustausch_Config.ImportedPartientBP_Group_ID={}. HC_Forum_Datenaustausch_Config={}",
					patientBPGroupId.getRepoId(), configRecord);
			builder.partientBPartnerGroupName(patientBPartnerGroup.getName());
		}

		final BPGroupId municipalityBPGroupId = BPGroupId.ofRepoIdOrNull(configRecord.getImportedMunicipalityBP_Group_ID());
		if (municipalityBPGroupId != null)
		{
			final I_C_BP_Group municipalityBPartnerGroup = Check.assumeNotNull(
					groupDAO.getById(municipalityBPGroupId),
					"Unable to load BP_Group referenced by HC_Forum_Datenaustausch_Config.ImportedMunicipalityBP_Group_ID={}. HC_Forum_Datenaustausch_Config={}",
					municipalityBPGroupId.getRepoId(), configRecord);
			builder.municipalityBPartnerGroupName(municipalityBPartnerGroup.getName());
		}

		if (Check.isNotBlank(configRecord.getImportedBPartnerLanguage()))
		{
			builder.languageCode(configRecord.getImportedBPartnerLanguage());
		}

		return builder.build();
	}
}
