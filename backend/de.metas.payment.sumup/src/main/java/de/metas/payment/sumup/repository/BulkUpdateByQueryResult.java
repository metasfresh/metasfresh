package de.metas.payment.sumup.repository;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.TranslatableStrings;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BulkUpdateByQueryResult
{
	int countOK;
	int countError;

	public boolean isZero() {return countOK == 0 && countError == 0;}

	public ITranslatableString getSummary()
	{
		return TranslatableStrings.anyLanguage("Successfully updated " + countOK + " transactions. Got " + countError + " errors.");
	}
}
