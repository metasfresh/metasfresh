package org.adempiere.exceptions;

import de.metas.ad_reference.ReferenceId;
import de.metas.document.DocBaseType;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import org.compiere.model.X_C_DocType;

import javax.annotation.Nullable;
import java.sql.Timestamp;

public class PeriodClosedException extends AdempiereException
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2798371272365454799L;

	/**
	 *
	 */
	public PeriodClosedException(final Timestamp dateAcct, final DocBaseType docBaseType)
	{
		super(buildMsg(dateAcct, docBaseType));
	}

	private static ITranslatableString buildMsg(@Nullable final Timestamp dateAcct, @Nullable final DocBaseType docBaseType)
	{
		final ITranslatableString dateAcctTrl = dateAcct != null
				? TranslatableStrings.dateAndTime(dateAcct)
				: TranslatableStrings.anyLanguage("?");

		final ITranslatableString docBaseTypeTrl = docBaseType != null
				? TranslatableStrings.adRefList(ReferenceId.ofRepoId(X_C_DocType.DOCBASETYPE_AD_Reference_ID), docBaseType.getCode())
				: TranslatableStrings.anyLanguage("?");

		return TranslatableStrings.builder()
				.appendADMessage(AdMessageKey.of("PeriodClosed"))
				.append(" ").appendADElement("DateAcct").append(": ").append(dateAcctTrl)
				.append(", ").appendADElement("DocBaseType").append(": ").append(docBaseTypeTrl)
				.build();
	}
}
