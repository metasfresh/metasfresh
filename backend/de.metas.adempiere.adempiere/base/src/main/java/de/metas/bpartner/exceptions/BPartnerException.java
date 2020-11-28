package de.metas.bpartner.exceptions;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStringBuilder;
import de.metas.i18n.TranslatableStrings;
import de.metas.util.Check;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_C_BPartner;

import javax.annotation.Nullable;

/**
 * Thrown when an exception related to a BPartner happened.
 */
@SuppressWarnings("serial")
public abstract class BPartnerException extends AdempiereException
{
	BPartnerException(
			@NonNull final AdMessageKey adMessage,
			@Nullable final I_C_BPartner bpartner)
	{
		super(buildMsg(adMessage, extractBPartnerName(bpartner)));
	}

	BPartnerException(
			@NonNull final AdMessageKey adMessage,
			@Nullable final String bpartnerName)
	{
		super(buildMsg(adMessage, bpartnerName));
	}

	private static String extractBPartnerName(final I_C_BPartner bpartner)
	{
		return bpartner != null
				? bpartner.getValue() + "_" + bpartner.getName()
				: null;
	}

	private static ITranslatableString buildMsg(
			@NonNull final AdMessageKey adMessage,
			@Nullable final String bpartnerName)
	{
		final TranslatableStringBuilder builder = TranslatableStrings.builder()
				.appendADMessage(adMessage);

		if (Check.isNotBlank(bpartnerName))
		{
			builder.append(": ").append(bpartnerName);
		}

		return builder.build();
	}
}
