package de.metas.document.archive.mailrecipient;

import de.metas.i18n.Language;
import org.junit.jupiter.api.Test;

import static de.metas.i18n.Language.AD_Language_en_US;
import static de.metas.i18n.Language.asLanguage;
import static org.assertj.core.api.Assertions.assertThat;

class DocOutBoundRecipientTest
{
	@Test
	void ofEmailAddress_setsFieldsCorrectly()
	{
		final Language bpLanguage = asLanguage(AD_Language_en_US);
		final DocOutBoundRecipient recipient = DocOutBoundRecipient.ofEmailAddress("x@y.z", bpLanguage, true);

		assertThat(recipient.getId()).isNull();
		assertThat(recipient.getEmailAddress()).isEqualTo("x@y.z");
		assertThat(recipient.isEmailAddressSet()).isTrue();
		assertThat(recipient.isInvoiceAsEmail()).isTrue();
		assertThat(recipient.getBPartnerLanguage()).isEqualTo(bpLanguage);
		assertThat(recipient.getUserLanguage()).isNull();
	}
}
