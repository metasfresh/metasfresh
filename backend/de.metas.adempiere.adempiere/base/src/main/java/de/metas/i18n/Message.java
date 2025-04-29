/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.i18n;

import de.metas.util.StringUtils;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;
import org.compiere.util.Env;

import javax.annotation.Nullable;

/**
 * Internal translated message representation (immutable)
 */
@ToString
public final class Message
{
	/**
	 * Separator between Msg and optional Tip
	 */
	private static final String SEPARATOR = Env.NL + Env.NL;

	/**
	 * Empty message
	 */
	public static final Message EMPTY = new Message();
	private static final AdMessageKey EMPTY_AD_Message = AdMessageKey.of("$EMPTY$");

	/**
	 * @return instance for given message text and tip
	 */
	public static Message ofTextTipAndErrorCode(
			@NonNull final AdMessageId adMessageId,
			@NonNull final AdMessageKey adMessage,
			@NonNull final ImmutableTranslatableString msgText,
			@NonNull final ImmutableTranslatableString msgTip,
			@Nullable final String errorCode)
	{
		return new Message(adMessageId, adMessage, msgText, msgTip, false, errorCode);
	}

	public static Message ofMissingADMessage(@Nullable final String text)
	{
		final String textNorm = StringUtils.trimBlankToNull(text);
		if (textNorm == null)
		{
			return EMPTY;
		}

		return new Message(
				null,
				AdMessageKey.of(textNorm),
				ImmutableTranslatableString.ofDefaultValue(text),
				null,
				true,
				null);
	}

	@Nullable @Getter private final AdMessageId adMessageId;
	@NonNull @Getter private final AdMessageKey adMessage;
	@NonNull private final ITranslatableString msgText;
	@Nullable private final ITranslatableString msgTip;
	@NonNull private final ITranslatableString msgTextAndTip;
	@Getter private final boolean missing;
	@Getter @Nullable private String errorCode;

	private Message(
			@Nullable final AdMessageId adMessageId,
			@NonNull final AdMessageKey adMessage,
			@NonNull final ImmutableTranslatableString msgText,
			@Nullable final ImmutableTranslatableString msgTip,
			final boolean missing,
			@Nullable final String errorCode)
	{
		this.adMessageId = adMessageId;
		this.adMessage = adMessage;
		this.msgText = msgText;
		this.msgTip = msgTip;

		if (!TranslatableStrings.isBlank(this.msgTip))            // messageTip on next line, if exists
		{
			this.msgTextAndTip = TranslatableStrings.builder()
					.append(this.msgText)
					.append(SEPARATOR)
					.append(this.msgTip)
					.build();
		}
		else
		{
			this.msgTextAndTip = msgText;
		}

		this.missing = missing;
		this.errorCode = errorCode;
	}

	private Message()
	{
		this.adMessageId = null;
		this.adMessage = EMPTY_AD_Message;
		this.msgText = TranslatableStrings.empty();
		this.msgTip = null;
		this.msgTextAndTip = this.msgText;
		this.missing = true;
	}

	public String getMsgText(@NonNull final String adLanguage)
	{
		return msgText.translate(adLanguage);
	}

	public String getMsgTip(@NonNull final String adLanguage)
	{
		return msgTip != null ? msgTip.translate(adLanguage) : "";
	}

	public String getMsgTextAndTip(@NonNull final String adLanguage)
	{
		return msgTextAndTip.translate(adLanguage);
	}
}
