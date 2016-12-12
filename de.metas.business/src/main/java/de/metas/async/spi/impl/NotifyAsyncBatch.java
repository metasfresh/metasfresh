/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

package de.metas.async.spi.impl;

import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.table.api.IADTableDAO;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.MClient;
import org.compiere.model.MUser;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.model.I_AD_User;
import de.metas.async.api.IAsyncBatchListeners;
import de.metas.async.model.I_C_Async_Batch;
import de.metas.async.model.I_C_Async_Batch_Type;
import de.metas.email.EMail;
import de.metas.letters.model.IEMailEditor;
import de.metas.letters.model.I_AD_BoilerPlate;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.spi.INotifyAsyncBatch;
import de.metas.logging.LogManager;
import de.metas.notification.INotificationBL;

public class NotifyAsyncBatch implements INotifyAsyncBatch
{
	protected final Logger logger = LogManager.getLogger(getClass());
	
	private final IAsyncBatchListeners asyncBatchListener = Services.get(IAsyncBatchListeners.class);
	final INotificationBL notificationBL = Services.get(INotificationBL.class);
	
	@Override
	public void sendNotifications(I_C_Async_Batch asyncBatch)
	{
		sendEMail(asyncBatch);
		sendNote(asyncBatch);
	}
	
	/***
	 * Send mail to the user who created the async batch with the result based the on boiler plate the ID of which is defined by {@value #AD_SYSCONFIG_ASYNC_BOILERPLATE_ID}. If there is no such
	 * AS_SysConfig or no <code>AD_BoilerPlate</code> record, then the method does nothing.
	 * 
	 * @param asyncBatch
	 * @see de.metas.letters.model.MADBoilerPlate#sendEMail(de.metas.letters.model.IEMailEditor, boolean)
	 */
	public void sendEMail(final I_C_Async_Batch asyncBatch)
	{
		final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
		Check.assumeNotNull(asyncBatchType, "Async Batch type should not be null for async batch! ", asyncBatch.getC_Async_Batch_ID());

		// do nothing is the flag for sending mail is not checked
		if (!asyncBatchType.isSendMail())
		{
			return;
		}

		final Properties ctx = InterfaceWrapperHelper.getCtx(asyncBatch);
		final String trxName = InterfaceWrapperHelper.getTrxName(asyncBatch);

		final I_AD_BoilerPlate boilerPlate = asyncBatchType.getAD_BoilerPlate();
		Check.assumeNotNull(boilerPlate, "Boiler plate should not be null for async batch type ! ", asyncBatchType.getC_Async_Batch_Type_ID());

		final MADBoilerPlate text = InterfaceWrapperHelper.create(boilerPlate, MADBoilerPlate.class);
		if (text == null)
		{
			return; // nothing to send
		}

		Boolean isSent = null;
		try
		{

			MADBoilerPlate.sendEMail(new IEMailEditor()
			{
				@Override
				public Object getBaseObject()
				{
					return InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, trxName);
				}

				@Override
				public int getAD_Table_ID()
				{
					return Services.get(IADTableDAO.class).retrieveTableId(I_C_Async_Batch.Table_Name);
				}

				@Override
				public int getRecord_ID()
				{
					return asyncBatch.getC_Async_Batch_ID();
				}

				@Override
				public EMail sendEMail(MUser from, String toEmail, String subject, Map<String, Object> variables)
				{
					final MClient client = MClient.get(ctx, Env.getAD_Client_ID(ctx));

					variables.put(MADBoilerPlate.VAR_UserPO, asyncBatch);

					// try to set language; take first from partner; if does not exists, take it from client
					final I_AD_User user = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, ITrx.TRXNAME_None);
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
					variables.put(MADBoilerPlate.VAR_AD_Language, language);
					//
					final String message = text.getTextSnippetParsed(variables);
					//
					if (Check.isEmpty(message, true))
						return null;
					//


					Check.assume(asyncBatch.getCreatedBy() > 0, "CreatedBy is not null!!!");
					final I_AD_User	to = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, trxName);
					
					notificationBL.notifyUser(to, text.getSubject(), message, TableRecordReference.of(asyncBatch));
					
					return null;
				}
			}, false);

			isSent = true;
		}
		catch (Exception e)
		{
			isSent = false;
		}
		finally
		{
			final int asyncBatch_id = asyncBatch.getC_Async_Batch_ID();
			final String toEmail = InterfaceWrapperHelper.create(ctx, asyncBatch.getCreatedBy(), I_AD_User.class, trxName).getEMail();

			if (isSent)
			{
				logger.warn("Async batch {} was notified by email {}", asyncBatch_id, toEmail);
			}
			else
			{
				logger.warn("Async batch {} was not notified by email {} " , asyncBatch_id, toEmail);
			}
		}
	}

	/**
	 * Send note to the user who created the async batch with the result
	 * 
	 * @param asyncBatch
	 */
	public void sendNote(final I_C_Async_Batch asyncBatch)
	{
		final I_C_Async_Batch_Type asyncBatchType = asyncBatch.getC_Async_Batch_Type();
		Check.assumeNotNull(asyncBatchType, "Async Batch type should not be null for async batch! ", asyncBatch.getC_Async_Batch_ID());

		// do nothing is the flag for sending notification is not checked
		if (!asyncBatchType.isSendNotification())
		{
			return;
		}

		asyncBatchListener.applyListener(asyncBatch);
	}
}
