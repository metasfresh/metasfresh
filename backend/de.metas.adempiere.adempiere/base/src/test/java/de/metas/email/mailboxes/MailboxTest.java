package de.metas.email.mailboxes;

import de.metas.email.EMailAddress;
import de.metas.i18n.ExplainedOptional;
import de.metas.user.UserId;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MailboxTest
{
	private static final EMailAddress SERVER_EMAIL = EMailAddress.ofString("server@example.com");
	private static final EMailAddress USER_EMAIL = EMailAddress.ofString("user@example.com");

	@Test
	void mergeFrom_microsoftGraph_setsFromAndReplyToToUserEmail()
	{
		final Mailbox serverMailbox = Mailbox.builder()
				.type(MailboxType.MICROSOFT_GRAPH)
				.email(SERVER_EMAIL)
				.microsoftGraphConfig(MicrosoftGraphConfig.builder()
						.clientId("client-id")
						.tenantId("tenant-id")
						.clientSecret("client-secret")
						.build())
				.build();

		final UserEMailConfig userConfig = UserEMailConfig.builder()
				.userId(UserId.ofRepoId(123))
				.email(USER_EMAIL)
				.build();

		final ExplainedOptional<Mailbox> result = serverMailbox.mergeFrom(userConfig);

		assertThat(result.isPresent()).isTrue();
		final Mailbox merged = result.get();
		assertThat(merged.getEmail()).isEqualTo(USER_EMAIL);
		assertThat(merged.getMicrosoftGraphConfigNotNull().getDefaultReplyTo()).isEqualTo(USER_EMAIL);
		// server-level Graph credentials must remain unchanged (single tenant-wide app)
		assertThat(merged.getMicrosoftGraphConfigNotNull().getClientId()).isEqualTo("client-id");
		assertThat(merged.getMicrosoftGraphConfigNotNull().getTenantId()).isEqualTo("tenant-id");
		assertThat(merged.getMicrosoftGraphConfigNotNull().getClientSecret()).isEqualTo("client-secret");
	}

	@Test
	void mergeFrom_microsoftGraph_userWithoutEmail_returnsEmpty()
	{
		final Mailbox serverMailbox = Mailbox.builder()
				.type(MailboxType.MICROSOFT_GRAPH)
				.email(SERVER_EMAIL)
				.microsoftGraphConfig(MicrosoftGraphConfig.builder()
						.clientId("client-id")
						.tenantId("tenant-id")
						.clientSecret("client-secret")
						.build())
				.build();

		final UserEMailConfig userConfig = UserEMailConfig.builder()
				.userId(UserId.ofRepoId(123))
				.email(null)
				.build();

		final ExplainedOptional<Mailbox> result = serverMailbox.mergeFrom(userConfig);

		assertThat(result.isPresent()).isFalse();
	}
}
