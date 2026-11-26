package de.metas.cucumber.stepdefs.order;

import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;
import org.junit.jupiter.api.Assertions;

import static org.assertj.core.api.Assertions.assertThat;

/** Asserts that an action is refused with the error of an expected AD_Message key. */
final class ExpectedErrorMessageKeyAssert
{
	private ExpectedErrorMessageKeyAssert() {}

	static void assertFailsWithMessageKey(@NonNull final String messageKey, @NonNull final Runnable action)
	{
		final AdMessageKey expectedKey = AdMessageKey.of(messageKey);
		final String expectedErrorCode = Services.get(IMsgBL.class).getErrorCode(expectedKey);
		final String expectedErrorCodeEffective = expectedErrorCode != null ? expectedErrorCode : expectedKey.toAD_Message();

		try
		{
			action.run();
			Assertions.fail("An exception with message key " + expectedKey + " should have been thrown");
		}
		catch (final AdempiereException exception)
		{
			assertThat(exception.getErrorCode()).isEqualTo(expectedErrorCodeEffective);
		}
	}
}
