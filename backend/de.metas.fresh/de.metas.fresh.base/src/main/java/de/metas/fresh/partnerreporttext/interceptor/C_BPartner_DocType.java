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

package de.metas.fresh.partnerreporttext.interceptor;

import de.metas.bpartner.BPartnerId;
import de.metas.fresh.partnerreporttext.api.BPartnerReportText;
import de.metas.fresh.partnerreporttext.api.BPartnerReportTextId;
import de.metas.fresh.partnerreporttext.api.BPartnerReportTextRepository;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_DocType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner_DocType.class)
@Component
public class C_BPartner_DocType
{
	private final BPartnerReportTextRepository bPartnerReportTextRepository;

	public C_BPartner_DocType(final BPartnerReportTextRepository bPartnerReportTextRepository)
	{
		this.bPartnerReportTextRepository = bPartnerReportTextRepository;
	}

	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW })
	public void setBusinessPartner(final I_C_BPartner_DocType bPartnerDocType)
	{
		final BPartnerReportTextId partnerReportTextId = BPartnerReportTextId.ofRepoId(bPartnerDocType.getC_BPartner_Report_Text_ID());
		final BPartnerReportText partnerReportText = bPartnerReportTextRepository.getById(partnerReportTextId);

		if (partnerReportText != null)
		{
			final BPartnerId bPartnerID = partnerReportText.getBPartnerID();
			bPartnerDocType.setC_BPartner_ID(bPartnerID.getRepoId());
		}

	}
}
