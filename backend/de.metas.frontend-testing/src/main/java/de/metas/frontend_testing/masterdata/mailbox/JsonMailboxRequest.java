package de.metas.frontend_testing.masterdata.mailbox;

import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

import javax.annotation.Nullable;

/**
 * Requests a usable SMTP mailbox for the tenant so the WebUI email dialog can be opened
 * (createEmail asserts a resolvable mailbox via MailService.findMailbox). All fields are
 * optional; sensible testing defaults are used when omitted. No mail is actually sent.
 */
@Value
@Builder
@Jacksonized
public class JsonMailboxRequest
{
	@Nullable String email;
	@Nullable String smtpHost;
	@Nullable Integer smtpPort;
}
