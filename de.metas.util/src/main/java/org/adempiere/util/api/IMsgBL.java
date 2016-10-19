package org.adempiere.util.api;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;

import org.adempiere.util.ISingletonService;

import de.metas.i18n.ITranslatableString;

public interface IMsgBL extends ISingletonService
{
	/**
	 * Get translated text message for AD_Message
	 * 
	 * @param language AD_Language
	 * @param adMessage AD_Message
	 * @param params parameters
	 * @return translated text
	 */
	String getMsg(String language, String message, Object[] params);

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

	/**
	 * "Translate" text.
	 * 
	 * <pre>
	 * 	- Check AD_Message.AD_Message 	->	MsgText
	 * 	- Check AD_Element.ColumnName	->	Name
	 * </pre>
	 * 
	 * @param ctx Context
	 * @param text Text - MsgText or Element Name
	 * @return translated text or original text if not found
	 */
	String translate(Properties ctx, String text);

	String translate(Properties ctx, String text, boolean isSOTrx);

	/**
	 * @param adMessage AD_Message
	 * @param msgParameters optional AD_Message parameters
	 * @return AD_Message as translatable string
	 */
	ITranslatableString getTranslatableMsgText(String adMessage, Object... msgParameters);
}
