package de.metas.ui.web.i18n;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.adempiere.ad.trx.api.ITrxManager;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.X_AD_Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.base.Splitter;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IADMessageDAO;
import de.metas.i18n.ILanguageBL;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.Language;
import de.metas.i18n.po.POTrlRepository;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.session.UserSession;
import de.metas.util.Services;
import lombok.NonNull;

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
	public Map<String, Object> getMessages(@RequestParam(name = "filter", required = false) final String filterString,
			@RequestParam(name = "lang", required = false) final String adLanguageParam)
	{
		final String adLanguage = Check.isEmpty(adLanguageParam, true) ? userSession.getAD_Language() : adLanguageParam;

		final ADMessageFilter filter;
		if (Check.isEmpty(filterString, true))
		{
			filter = null;
		}
		else
		{
			final String filterStringUC = filterString.trim().toUpperCase();
			filter = adMessageKey -> adMessageKey.trim().toUpperCase().startsWith(filterStringUC);
		}

		return MessagesTreeLoader.newInstance()
				.filter(filter)
				.load(adLanguage);
	}

	@PostMapping("/messages")
	public Map<String, Object> updateMessages(@RequestBody final Map<String, String> flatMessagesToUpload)
	{
		userSession.assertLoggedInAsSysAdmin();

		if (flatMessagesToUpload.isEmpty())
		{
			throw new AdempiereException("Nothing to upload");
		}

		Services.get(ITrxManager.class)
				.runInNewTrx(() -> {
					flatMessagesToUpload.entrySet().forEach(this::importMessage);
				});

		Services.get(IMsgBL.class).cacheReset();

		//

		return MessagesTreeLoader.newInstance()
				.filter(flatMessagesToUpload::containsKey)
				.loadAllLanguages();
	}

	private final void importMessage(final Map.Entry<String, String> entry)
	{
		final AdMessageKey adMessageKey = AdMessageKey.of(AD_MESSAGE_PREFIX + entry.getKey());
		final String msgText = entry.getValue();

		Services.get(IADMessageDAO.class).createUpdateMessage(adMessageKey, adMessage -> {
			adMessage.setMsgType(X_AD_Message.MSGTYPE_Information);
			adMessage.setMsgText(msgText);
			adMessage.setIsActive(true);
			adMessage.setEntityType(ENTITY_TYPE);

			POTrlRepository.instance.setTrlUpdateModeAsUpdateIdenticalTrls(adMessage, true);
		});
	}

	private interface ADMessageFilter
	{
		/**
		 * @param adMessageKey AD_Message before any keyMapper was applied
		 * @return true if accepted
		 */
		boolean acceptMessageKey(String adMessageKey);
	}

	private static final class MessagesTreeLoader
	{

		public static MessagesTreeLoader newInstance()
		{
			return new MessagesTreeLoader();
		}

		private ADMessageFilter filter;

		private MessagesTreeLoader()
		{
		}

		public Map<String, Object> loadAllLanguages()
		{
			final Set<String> adLanguages = Services.get(ILanguageBL.class).getAvailableLanguages().getAD_Languages();

			final LinkedHashMap<String, Object> tree = new LinkedHashMap<>();
			tree.put("_baseLanguage", Language.getBaseAD_Language());

			for (final String adLanguage : adLanguages)
			{
				loadInto(tree, adLanguage, adMessageKey -> adMessageKey + "." + adLanguage);
			}

			return tree;
		}

		public Map<String, Object> load(@NonNull final String adLanguage)
		{
			final LinkedHashMap<String, Object> tree = new LinkedHashMap<>();
			tree.put("_language", adLanguage);
			loadInto(tree, adLanguage, adMessageKey -> adMessageKey);
			return tree;
		}

		private void loadInto(Map<String, Object> tree, @NonNull final String adLanguage, final UnaryOperator<String> keyMapper)
		{
			final IMsgBL msgBL = Services.get(IMsgBL.class);
			msgBL.getMsgMap(adLanguage, AD_MESSAGE_PREFIX, true /* removePrefix */)
					.forEach((adMessageKey, msgText) -> {
						if (filter == null || filter.acceptMessageKey(adMessageKey))
						{
							addMessageToTree(tree, keyMapper.apply(adMessageKey), msgText);
						}
					});
		}

		@SuppressWarnings("unchecked")
		private static void addMessageToTree(final Map<String, Object> tree, final String key, final String value)
		{
			final List<String> keyParts = Splitter.on('.').splitToList(key);

			Map<String, Object> currentNode = tree;
			for (int i = 0; i < keyParts.size() - 1; i++)
			{
				final String keyPart = keyParts.get(i);
				final Object currentNodeObj = currentNode.get(keyPart);
				if (currentNodeObj == null)
				{
					final Map<String, Object> parentNode = currentNode;
					currentNode = new LinkedHashMap<>();
					parentNode.put(keyPart, currentNode);
				}
				else if (currentNodeObj instanceof Map)
				{
					currentNode = (Map<String, Object>)currentNodeObj;
				}
				else
				{
					// discarding the old value, shall not happen
					final Map<String, Object> parentNode = currentNode;
					currentNode = new LinkedHashMap<>();
					parentNode.put(keyPart, currentNode);
				}
			}

			final String keyPart = keyParts.get(keyParts.size() - 1);
			currentNode.put(keyPart, value);
		}

		public MessagesTreeLoader filter(ADMessageFilter filter)
		{
			this.filter = filter;
			return this;
		}
	}
}
