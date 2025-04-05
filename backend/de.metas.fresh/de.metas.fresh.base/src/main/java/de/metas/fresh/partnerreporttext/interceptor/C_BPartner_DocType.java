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

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.document.IDocTypeDAO;
import de.metas.fresh.partnerreporttext.api.IBPartnerDocTypeDAO;
import de.metas.fresh.partnerreporttext.api.IBPartnerReportTextDAO;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_DocType;
import de.metas.fresh.partnerreporttext.model.I_C_BPartner_Report_Text;
import de.metas.i18n.AdMessageKey;
import de.metas.util.Services;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_DocType;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

@Interceptor(I_C_BPartner_DocType.class)
@Component
public class C_BPartner_DocType
{
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final IBPartnerReportTextDAO partnerReportTextDAO = Services.get(IBPartnerReportTextDAO.class);
	private final IBPartnerDocTypeDAO partnerDocTypeDAO = Services.get(IBPartnerDocTypeDAO.class);
	private final static AdMessageKey MSG_C_BPartner_DocType_Unique_Record = AdMessageKey.of("C_BPartner_DocType_Unique");

	@ModelChange(timings = { ModelValidator.TYPE_AFTER_NEW })
	public void setBusinessPartner(final I_C_BPartner_DocType bPartnerDocType)
	{
		final I_C_BPartner_Report_Text partnerReportText = partnerReportTextDAO.getById(bPartnerDocType.getC_BPartner_Report_Text_ID());
		if (partnerReportText != null)
		{
			if (!partnerDocTypeDAO.isPartnerDocTypeUnique(partnerReportText, bPartnerDocType.getC_DocType_ID()))
			{
				final I_C_DocType docType = Services.get(IDocTypeDAO.class).getById(bPartnerDocType.getC_DocType_ID());
				final I_C_BPartner partner = Services.get(IBPartnerDAO.class).getById(partnerReportText.getC_BPartner_ID());
				throw new AdempiereException(MSG_C_BPartner_DocType_Unique_Record)
						.markAsUserValidationError()
						.appendParametersToMessage()
						.setParameter("DocType", docType.getDocBaseType())
						.setParameter("BPartner", partner.getValue())
						.setParameter("ReportText", partnerReportText.getValue());
			}

			trxManager.runAfterCommit(() -> {
				partnerDocTypeDAO.setBPartner(bPartnerDocType, partnerReportText.getC_BPartner_ID());
				partnerDocTypeDAO.save(bPartnerDocType);
			});
		}
	}

}
