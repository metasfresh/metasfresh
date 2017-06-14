package de.metas.ui.web.i18n;

import java.util.Map;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_AD_Message;
import org.compiere.model.X_AD_Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;

/*
 * #%L
 * metasfresh-webui-api
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

@RestController
@RequestMapping(I18NRestController.ENDPOINT)
public class I18NRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/i18n";

	private static final String AD_MESSAGE_PREFIX = "webui.";

	private static final String ENTITY_TYPE = "de.metas.ui.web";

	@Autowired
	private UserSession userSession;

	@GetMapping("/messages")
	public Map<String, String> getMessages()
	{
		return Services.get(IMsgBL.class).getMsgMap(userSession.getAD_Language(), AD_MESSAGE_PREFIX, true /* removePrefix */);
	}

	@PostMapping("/messages")
	public void updateMessages(@RequestBody final Map<String, String> messagesToUpload)
	{
		userSession.assertLoggedInAsSysAdmin();

		if (messagesToUpload.isEmpty())
		{
			throw new AdempiereException("Nothing to upload");
		}

		Services.get(ITrxManager.class)
				.run(() -> {
					messagesToUpload.entrySet().forEach(this::importMessage);
				});

		Services.get(IMsgBL.class).cacheReset();
	}

	private final void importMessage(final Map.Entry<String, String> entry)
	{
		final String adMessageKey = AD_MESSAGE_PREFIX + entry.getKey();
		try
		{
			final String msgText = entry.getValue();

			I_AD_Message adMessage = Services.get(IQueryBL.class)
					.createQueryBuilder(I_AD_Message.class)
					.addEqualsFilter(I_AD_Message.COLUMNNAME_Value, adMessageKey)
					.create()
					.firstOnly(I_AD_Message.class);

			if (adMessage == null)
			{
				adMessage = InterfaceWrapperHelper.newInstance(I_AD_Message.class);
				adMessage.setValue(adMessageKey);
				adMessage.setMsgType(X_AD_Message.MSGTYPE_Information);
			}

			adMessage.setMsgText(msgText);
			adMessage.setIsActive(true);
			adMessage.setEntityType(ENTITY_TYPE);
			InterfaceWrapperHelper.save(adMessage);
		}
		catch (final Exception ex)
		{
			throw AdempiereException.wrapIfNeeded(ex)
					.setParameter("AD_Message", adMessageKey);
		}
	}
}
