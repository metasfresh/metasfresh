package de.metas.email.process;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.model.I_R_MailText;

import de.metas.email.IMailBL;
import de.metas.email.IMailTextBuilder;
import de.metas.process.Param;
import de.metas.process.SvrProcess;

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
public class R_MailText_Test extends SvrProcess
{
	// services
	private final transient IMailBL mailBL = Services.get(IMailBL.class);
	private final transient IADTableDAO tableDAO = Services.get(IADTableDAO.class);

	@Param(parameterName = "C_BPartner_ID")
	private int p_C_BPartner_ID;
	@Param(parameterName = "AD_User_ID")
	private int p_AD_User_ID = -1;
	@Param(parameterName = "AD_Table_ID")
	private int p_AD_Table_ID = -1;
	@Param(parameterName = "Record_ID")
	private int p_Record_ID = -1;

	@Override
	protected String doIt() throws Exception
	{
		final I_R_MailText mailText = getRecord(I_R_MailText.class);

		final IMailTextBuilder mailTextBuilder = mailBL.newMailTextBuilder(mailText);

		if (p_AD_Table_ID > 0 && p_Record_ID >= 0)
		{
			final String tableName = tableDAO.retrieveTableName(p_AD_Table_ID);
			final Object record = InterfaceWrapperHelper.create(getCtx(), tableName, p_Record_ID, Object.class, getTrxName());
			if (record != null)
			{
				final boolean analyse = true;
				mailTextBuilder.setRecord(record, analyse);
			}
		}

		if (p_C_BPartner_ID > 0)
		{
			mailTextBuilder.setC_BPartner(p_C_BPartner_ID);
		}
		if (p_AD_User_ID >= 0)
		{
			mailTextBuilder.setAD_User(p_AD_User_ID);
		}

		//
		// Display configuration
		addLog("Using @C_BPartner_ID@: {}", mailTextBuilder.getC_BPartner());
		addLog("Using @AD_User_ID@: {}", mailTextBuilder.getAD_User());
		addLog("Using @Record_ID@: {}", mailTextBuilder.getRecord());
		addLog("Using @AD_Language@: {}", mailTextBuilder.getAD_Language());
		addLog("Using @IsHtml@: {}", mailTextBuilder.isHtml());

		//
		// Display results
		addLog("@Result@ ---------------------------------");
		addLog("@MailHeader@: {}", mailTextBuilder.getMailHeader());
		addLog("@MailText@ (full): {}", mailTextBuilder.getFullMailText());

		return MSG_OK;
	}

}
