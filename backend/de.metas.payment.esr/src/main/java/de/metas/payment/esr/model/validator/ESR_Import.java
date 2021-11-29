package de.metas.payment.esr.model.validator;

/*
 * #%L
 * de.metas.payment.esr
 * %%
 * Copyright (C) 2015 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import de.metas.banking.api.IBPBankAccountDAO;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.service.IBPartnerOrgBL;
import de.metas.bpartner.service.OrgHasNoBPartnerLinkException;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.organization.IOrgDAO;
import de.metas.organization.OrgId;
import de.metas.payment.esr.api.IESRImportDAO;
import de.metas.payment.esr.model.I_ESR_Import;
import de.metas.util.Services;
import org.adempiere.ad.callout.annotations.Callout;
import org.adempiere.ad.callout.annotations.CalloutMethod;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@Interceptor(I_ESR_Import.class)
@Callout(I_ESR_Import.class)
public class ESR_Import
{
	private final IBPartnerOrgBL bPartnerOrgBL = Services.get(IBPartnerOrgBL.class);
	private final IBPBankAccountDAO bankAccountDAO = Services.get(IBPBankAccountDAO.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);

	private final AdMessageKey ERR_ESR_Import_DefaultAccountNotFoundForOrg = AdMessageKey.of("ESR_Import_DefaultAccountNotFoundForOrg");

	public ESR_Import()
	{
		final IProgramaticCalloutProvider programaticCalloutProvider = Services.get(IProgramaticCalloutProvider.class);
		programaticCalloutProvider.registerAnnotatedCallout(this);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void onDeleteESRImport(final I_ESR_Import esrImport)
	{
		final IESRImportDAO esrImportPA = Services.get(IESRImportDAO.class);

		esrImportPA.deleteLines(esrImport);
	}

	@ModelChange(
			timings = { ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = I_ESR_Import.COLUMNNAME_AD_Org_ID)
	@CalloutMethod(columnNames = I_ESR_Import.COLUMNNAME_AD_Org_ID)
	public void updateBankAccountForOrganization(final I_ESR_Import esrImport)
	{
		final OrgId orgId = OrgId.ofRepoId(esrImport.getAD_Org_ID());
		final Optional<BPartnerId> orgBPartnerIdOptional = bPartnerOrgBL.retrieveLinkedBPartnerId(orgId);
		if (!orgBPartnerIdOptional.isPresent())
		{
			throw new OrgHasNoBPartnerLinkException(orgId);
		}

		final BPartnerId orgBpartnerId = orgBPartnerIdOptional.get();
		final Optional<I_C_BP_BankAccount> orgBankAccount =
				bankAccountDAO.retrieveDefaultBankAccountInTrx(orgBpartnerId);

		if (!orgBankAccount.isPresent())
		{

			final I_AD_Org org = orgDAO.getById(orgId);

			throw new AdempiereException(msgBL.getTranslatableMsgText(ERR_ESR_Import_DefaultAccountNotFoundForOrg, org.getName()));
		}

		esrImport.setC_BP_BankAccount_ID(orgBankAccount.get().getC_BP_BankAccount_ID());
	}
}
