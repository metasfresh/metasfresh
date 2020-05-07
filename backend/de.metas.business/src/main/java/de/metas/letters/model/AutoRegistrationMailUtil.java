package de.metas.letters.model;

import java.util.Properties;
import java.util.StringTokenizer;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Client;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;

import de.metas.async.model.I_C_Async_Batch;
import de.metas.email.EMail;
import de.metas.email.EMailSentStatus;
import de.metas.email.IMailBL;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import lombok.experimental.UtilityClass;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * The code of this util's {@link #sendAutoregistrationMail(I_AD_User, int)} is moved from the legacy system's IUserBL service implementation.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@UtilityClass
public class AutoRegistrationMailUtil
{
	public void sendAutoregistrationMail(final I_AD_User user, final int boilerPlate_ID)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(user);

		final I_AD_Client client = InterfaceWrapperHelper.create(ctx, user.getAD_Client_ID(), I_AD_Client.class, ITrx.TRXNAME_None);

		final I_AD_BoilerPlate boilerPlate = InterfaceWrapperHelper.create(ctx, boilerPlate_ID, I_AD_BoilerPlate.class, ITrx.TRXNAME_None);
		final MADBoilerPlate text = InterfaceWrapperHelper.create(boilerPlate, MADBoilerPlate.class);
		if (text == null)
		{
			return; // nothing to send
		}

		MADBoilerPlate.sendEMail(new IEMailEditor()
		{
			@Override
			public Object getBaseObject()
			{
				return user;
			}

			@Override
			public int getAD_Table_ID()
			{
				return Services.get(IADTableDAO.class).retrieveTableId(I_C_Async_Batch.Table_Name);
			}

			@Override
			public int getRecord_ID()
			{
				return user.getAD_User_ID();
			}

			/**
			 * @param from
			 * @param toEmail comma or colon separated list of recipients; the first address is ignored and instead the mail address of {@code user} is used.
			 */
			@Override
			public EMail sendEMail(final I_AD_User from, final String toEmail, final String subject, final BoilerPlateContext attributes)
			{
				final I_C_BPartner partner = user.getC_BPartner();
				String language = "";
				if (partner != null && partner.getC_BPartner_ID() > 0)
				{
					language = partner.getAD_Language();
				}
				if (Check.isEmpty(language, true))
				{
					language = client.getAD_Language();
				}
				final BoilerPlateContext attributesToUse = attributes.toBuilder()
						.setAD_Language(language)
						.build();

				final String message = text.getTextSnippetParsed(attributesToUse);
				//
				if (Check.isEmpty(message, true))
					return null;
				//

				// prepare mail
				final StringTokenizer st = new StringTokenizer(toEmail, " ,;", false);
				st.nextToken();

				final IMailBL mailBL = Services.get(IMailBL.class);
				final EMail email = mailBL.createEMail(client, null, user.getEMail(), text.getSubject(), message, true);

				if (email == null)
				{
					throw new AdempiereException("Cannot create email. Check log.");
				}
				while (st.hasMoreTokens())
				{
					email.addTo(st.nextToken());
				}

				// now send mail
				final EMailSentStatus emailSentStatus = email.send();

				if (!emailSentStatus.isSentOK())
				{
					throw new AdempiereException(emailSentStatus.getSentMsg());
				}

				return email;
			}
		}, false);
	}
}
