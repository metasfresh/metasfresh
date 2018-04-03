package de.metas.i18n;

import java.util.List;
import java.util.Map;

/*
 * #%L
 * de.metas.util
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

import java.util.Properties;

import org.adempiere.util.ISingletonService;

public interface IMsgBL extends ISingletonService
{
	String getMsg(String adLanguage, String message);
	
	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param adLanguage AD_Language
	 * @param adMessage AD_Message
	 * @param params parameters
	 * @return translated text
	 */
	String getMsg(String adLanguage, String message, Object[] params);

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param adMessage AD_Message
	 * @return translated text
	 */
	String getMsg(Properties ctx, String adMessage);

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param adMessage AD_Message
	 * @param params
	 * @return translated text
	 */
	String getMsg(Properties ctx, String adMessage, Object[] params);
	
	String getMsg(final String adMessage, final List<Object> params);

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param adMessage Message Key
	 * @param getText if true only return Text, if false only return Tip
	 * @return translated text
	 */
	String getMsg(Properties ctx, String adMessage, boolean text);

	String parseTranslation(Properties ctx, String message);

	String parseTranslation(String adLanguage, String message);

	/**
	 * "Translate" text.
	 * 
	 * <ul>
	 * <li>Checks AD_Message.AD_Message, if exists returns AD_Message.MsgText
	 * <li>Checks AD_Element.ColumnName, if exists returns AD_Element.Name
	 * </ul>
	 * 
	 * @param ctx context, used to fetch the AD_Language and IsSOTrx flag
	 * @param text text to be translated
	 * @return translated text or original text if not found
	 */
	String translate(Properties ctx, String text);

	String translate(String adLanguage, String text);

	String translate(Properties ctx, String text, boolean isSOTrx);

	/**
	 * "Translate" text.
	 * 
	 * <ul>
	 * <li>Checks AD_Message.AD_Message, if exists returns AD_Message.MsgText
	 * <li>Checks AD_Element.ColumnName, if exists returns AD_Element.Name
	 * </ul>
	 * 
	 * @param text text to be translated
	 * @return translatable string
	 * @see #translate(Properties, String, boolean)
	 */
	ITranslatableString translatable(String text);

	/**
	 * @param adMessage AD_Message
	 * @param msgParameters optional AD_Message parameters
	 * @return AD_Message as translatable string
	 */
	ITranslatableString getTranslatableMsgText(String adMessage, Object... msgParameters);

	/**
	 * Gets AD_Language/message map
	 * 
	 * @param adLanguage
	 * @param prefix prefix used to match the AD_Messages (keys)
	 * @param removePrefix if true, the prefix will be cut out from the AD_Message keys that will be returned
	 * @return a map of "AD_Message" (might be with the prefix removed) to "translated message" pairs
	 */
	Map<String, String> getMsgMap(String adLanguage, String prefix, boolean removePrefix);

	void cacheReset();
}
