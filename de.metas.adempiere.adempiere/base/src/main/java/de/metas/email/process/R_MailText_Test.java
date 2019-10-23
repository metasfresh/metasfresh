package de.metas.email.process;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_R_MailText;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.email.MailService;
import de.metas.email.templates.MailTemplateId;
import de.metas.email.templates.MailTextBuilder;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.user.UserId;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2016 metas GmbH
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

/**
 * Test the {@link I_R_MailText}.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class R_MailText_Test extends JavaProcess
{
	// services
	private final transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);
	private final transient IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
	private final transient IUserDAO usersRepo = Services.get(IUserDAO.class);
	private final transient MailService mailService = Adempiere.getBean(MailService.class);

	@Param(parameterName = "C_BPartner_ID")
	private int p_C_BPartner_ID;
	@Param(parameterName = "AD_User_ID")
	private int p_AD_User_ID = -1;
	@Param(parameterName = "AD_Table_ID")
	private int p_AD_Table_ID = -1;
	@Param(parameterName = "Record_ID")
	private int p_Record_ID = -1;

	@Override
	protected String doIt()
	{
		final MailTemplateId mailTemplateId = MailTemplateId.ofRepoId(getRecord_ID());
		final MailTextBuilder mailTextBuilder = mailService.newMailTextBuilder(mailTemplateId);

		Object record = null;
		if (p_AD_Table_ID > 0 && p_Record_ID >= 0)
		{
			final String tableName = tableDAO.retrieveTableName(p_AD_Table_ID);
			record = InterfaceWrapperHelper.create(getCtx(), tableName, p_Record_ID, Object.class, getTrxName());
			if (record != null)
			{
				mailTextBuilder.recordAndUpdateBPartnerAndContact(record);
			}
		}

		I_C_BPartner bpartner = null;
		if (p_C_BPartner_ID > 0)
		{
			bpartner = bpartnersRepo.getById(p_C_BPartner_ID);
			mailTextBuilder.bpartner(bpartner);
		}

		I_AD_User contact = null;
		if (p_AD_User_ID >= 0)
		{
			contact = usersRepo.getById(UserId.ofRepoId(p_AD_User_ID));
			mailTextBuilder.bpartnerContact(contact);
		}

		//
		// Display configuration
		addLog("Using @R_MailText_ID@: {}", mailTemplateId);
		addLog("Using @C_BPartner_ID@: {}", bpartner);
		addLog("Using @AD_User_ID@: {}", contact);
		addLog("Using @Record_ID@: {}", record);
		addLog("Using @AD_Language@: {}", mailTextBuilder.getAdLanguage());

		//
		// Display results
		addLog("@Result@ ---------------------------------");
		addLog("@MailHeader@: {}", mailTextBuilder.getMailHeader());
		addLog("@MailText@ (full): {}", mailTextBuilder.getFullMailText());

		return MSG_OK;
	}

}
