package de.metas.ui.web.i18n;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.AdMessagesTree;
import de.metas.i18n.AdMessagesTreeLoader;
import de.metas.i18n.IADMessageDAO;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.i18n.po.POTrlRepository;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.util.Services;
import de.metas.util.StringUtils;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.LinkedHashMap;
import java.util.Map;

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

	private final IADMessageDAO adMessageDAO = Services.get(IADMessageDAO.class);
	private final IMsgBL msgBL = Services.get(IMsgBL.class);
	private final ILanguageBL languageBL = Services.get(ILanguageBL.class);
	private final ITrxManager trxManager = Services.get(ITrxManager.class);
	private final UserSession userSession;

	private static final String AD_MESSAGE_PREFIX = "webui.";
	private static final String ENTITY_TYPE = "de.metas.ui.web";

	public I18NRestController(
			@NonNull final UserSession userSession)
	{
		this.userSession = userSession;
	}

	@GetMapping("/messages")
	public Map<String, Object> getMessages(@RequestParam(name = "filter", required = false) final String filterString,
										   @RequestParam(name = "lang", required = false) final String adLanguageParam)
	{
		final String adLanguage = StringUtils.trimBlankToOptional(adLanguageParam).orElseGet(userSession::getAD_Language);

		final AdMessagesTree messagesTree = msgBL.messagesTree()
				.adMessagePrefix(AD_MESSAGE_PREFIX)
				.filterAdMessageStartingWithIgnoringCase(filterString)
				.removePrefix(true)
				.load(adLanguage);

		return toJson(messagesTree);
	}

	private static Map<String, Object> toJson(@NonNull final AdMessagesTree messagesTree)
	{
		final LinkedHashMap<String, Object> json = new LinkedHashMap<>();
		json.put("_language", messagesTree.getAdLanguage());
		json.putAll(messagesTree.getMap());
		return json;
	}

	@PostMapping("/messages")
	public Map<String, Object> updateMessages(@RequestBody final Map<String, String> flatMessagesToUpload)
	{
		userSession.assertLoggedInAsSysAdmin();

		if (flatMessagesToUpload.isEmpty())
		{
			throw new AdempiereException("Nothing to upload");
		}

		trxManager.runInNewTrx(() -> flatMessagesToUpload.entrySet().forEach(this::importMessage));

		msgBL.cacheReset();

		//
		final LinkedHashMap<String, Object> result = new LinkedHashMap<>();
		result.put("_baseLanguage", Language.getBaseAD_Language());

		final AdMessagesTreeLoader messagesTreeLoader = msgBL.messagesTree()
				.adMessagePrefix(AD_MESSAGE_PREFIX)
				.filterAdMessagesBy(flatMessagesToUpload::containsKey)
				.build();

		for (final String adLanguage : languageBL.getAvailableLanguages().getAD_Languages())
		{
			final AdMessagesTree messagesTree = messagesTreeLoader.load(adLanguage);
			result.put(messagesTree.getAdLanguage(), messagesTree.getMap());
		}

		return result;
	}

	private void importMessage(final Map.Entry<String, String> entry)
	{
		final AdMessageKey adMessageKey = AdMessageKey.of(AD_MESSAGE_PREFIX + entry.getKey());
		final String msgText = entry.getValue();

		adMessageDAO.createUpdateMessage(adMessageKey, adMessage -> {
			adMessage.setMsgType(X_AD_Message.MSGTYPE_Information);
			adMessage.setMsgText(msgText);
			adMessage.setIsActive(true);
			adMessage.setEntityType(ENTITY_TYPE);

			POTrlRepository.instance.setTrlUpdateModeAsUpdateIdenticalTrls(adMessage, true);
		});
	}
}
