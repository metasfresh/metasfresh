/*
 * #%L
 * de.metas.fresh.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.fresh.partnerreporttext.api;

import de.metas.bpartner.BPartnerId;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_Report_Text;
import de.metas.organization.ClientAndOrgId;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Repository;

@Repository
public class BPartnerReportTextRepository
{

	public @Nullable BPartnerReportText getById(@NonNull final BPartnerReportTextId bPartnerReportTextId)
	{
		final I_C_BPartner_Report_Text record = InterfaceWrapperHelper.load(bPartnerReportTextId, I_C_BPartner_Report_Text.class);
		if (record == null)
		{
			throw new AdempiereException("No C_BPartner_Report_Text found for " + bPartnerReportTextId);
		}

		return fromRecord(record);
	}

	public static BPartnerReportText fromRecord(@NonNull final I_C_BPartner_Report_Text record)
	{
		return BPartnerReportText.builder()
				.id(BPartnerReportTextId.ofRepoId(record.getC_BPartner_Report_Text_ID()))
				.lastModified(record.getUpdated().toInstant())
				.bPartnerID(BPartnerId.ofRepoId(record.getC_BPartner_ID()))
				.additionalText(record.getAdditionalText())
				.clientAndOrgId(ClientAndOrgId.ofClientAndOrg(record.getAD_Client_ID(), record.getAD_Org_ID()))
				.build();
	}
}
