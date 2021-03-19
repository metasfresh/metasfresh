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

import javax.annotation.Nullable;

import de.metas.util.ISingletonService;
import lombok.NonNull;

public interface IMsgBL extends ISingletonService
{
	AdMessageKey MSG_Yes = AdMessageKey.of("Y");
	AdMessageKey MSG_No = AdMessageKey.of("N");

	String getMsg(String adLanguage, AdMessageKey message);

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @return translated text
	 */
	String getMsg(String adLanguage, AdMessageKey message, Object[] params);

	String getMsg(String adLanguage, AdMessageKey message, List<Object> params);

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param ctx Context to retrieve language
	 * @param adMessage AD_Message
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage));
	}

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage, Object[] params);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage, final Object[] params)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage), params);
	}

	String getMsg(final AdMessageKey adMessage, final List<Object> params);

	@Deprecated
	default String getMsg(final String adMessage, final List<Object> params)
	{
		return getMsg(AdMessageKey.of(adMessage), params);
	}

	/**
	 * Get translated text message for AD_Message
	 * 
	 * @return translated text
	 */
	String getMsg(Properties ctx, AdMessageKey adMessage, boolean text);

	@Deprecated
	default String getMsg(final Properties ctx, final String adMessage, final boolean text)
	{
		return getMsg(ctx, AdMessageKey.of(adMessage), text);
	}

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
	 * @return AD_Message as translatable string
	 * @see #translatable(String)
	 */
	ITranslatableString getTranslatableMsgText(AdMessageKey adMessage, @Nullable Object... msgParameters);

	@Deprecated
	default ITranslatableString getTranslatableMsgText(@NonNull final String adMessage, @Nullable final Object... msgParameters)
	{
		return getTranslatableMsgText(AdMessageKey.of(adMessage), msgParameters);
	}

	default ITranslatableString getTranslatableMsgText(final AdMessageKey adMessage, final List<Object> msgParameters)
	{
		final Object[] msgParametersArr = msgParameters != null ? msgParameters.toArray() : new Object[] {};
		return getTranslatableMsgText(adMessage, msgParametersArr);
	}

	@Deprecated
	default ITranslatableString getTranslatableMsgText(final String adMessage, final List<Object> msgParameters)
	{
		return getTranslatableMsgText(AdMessageKey.of(adMessage), msgParameters);
	}

	default ITranslatableString getTranslatableMsgText(final boolean booleanValue)
	{
		return getTranslatableMsgText(booleanValue ? MSG_Yes : MSG_No);
	}

	/**
	 * Gets AD_Language/message map
	 * 
	 * @param adLanguage language key
	 * @param prefix prefix used to match the AD_Messages (keys)
	 * @param removePrefix if true, the prefix will be cut out from the AD_Message keys that will be returned
	 * @return a map of "AD_Message" (might be with the prefix removed) to "translated message" pairs
	 */
	Map<String, String> getMsgMap(String adLanguage, String prefix, boolean removePrefix);

	void cacheReset();
}
