package de.metas.i18n.impl;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.Msg;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;
import org.compiere.util.Env;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 *
 * This implementation delegates to {@link Msg} and is therefore coupled with the database.
 *
 */
@SuppressWarnings("deprecation")
public class MsgBL implements IMsgBL
{
	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message)
	{
		return Msg.getMsg(adLanguage, message.toAD_Message());
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final Object[] params)
	{
		return Msg.getMsg(adLanguage, message.toAD_Message(), params);
	}

	@Override
	public String getMsg(final String adLanguage, @NonNull final AdMessageKey message, final List<Object> params)
	{
		return Msg.getMsg(
				adLanguage,
				message.toAD_Message(),
				params != null && !params.isEmpty() ? params.toArray() : null);
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message());
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, @Nullable final Object[] params)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message(), params);
	}

	@Override
	public String getMsg(final AdMessageKey adMessage, final List<Object> params)
	{
		return getMsg(Env.getCtx(), adMessage, params != null && !params.isEmpty() ? params.toArray() : null);
	}

	@Override
	public String getMsg(final Properties ctx, @NonNull final AdMessageKey adMessage, final boolean text)
	{
		return Msg.getMsg(ctx, adMessage.toAD_Message(), text);
	}

	@Override
	public Map<String, String> getMsgMap(final String adLanguage, final String prefix, final boolean removePrefix)
	{
		return Msg.getMsgMap(adLanguage, prefix, removePrefix);
	}

	@Override
	public String translate(final Properties ctx, final String text)
	{
		return Msg.translate(ctx, text);
	}

	@Override
	public String translate(final String adLanguage, final String text)
	{
		return Msg.translate(adLanguage, text);
	}

	@Override
	public String translate(final Properties ctx, final String text, final boolean isSOTrx)
	{
		return Msg.translate(ctx, text, isSOTrx);
	}

	@Override
	public ITranslatableString translatable(final String text)
	{
		if (Check.isEmpty(text, true))
		{
			return TranslatableStrings.constant(text);
		}
		return new ADElementOrADMessageTranslatableString(text);
	}

	@Override
	public String parseTranslation(final Properties ctx, final String message)
	{
		return Msg.parseTranslation(ctx, message);
	}

	@Override
	public String parseTranslation(final String adLanguage, final String message)
	{
		return Msg.parseTranslation(adLanguage, message);
	}

	@Override
	public ITranslatableString getTranslatableMsgText(@NonNull final AdMessageKey adMessage, final Object... msgParameters)
	{
		return new ADMessageTranslatableString(adMessage, msgParameters);
	}

	@Override
	public void cacheReset()
	{
		Msg.cacheReset();
	}
}
