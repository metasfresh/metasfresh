package de.metas.frontend_testing.masterdata.mailbox;

import com.google.common.collect.ImmutableMap;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.util.Services;
import de.metas.util.collections.CollectionUtils;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.compiere.model.I_AD_MailBox;
import org.compiere.model.I_AD_MailConfig;
import org.compiere.model.X_AD_MailBox;

import java.util.Map;

/**
 * Seeds a usable SMTP mailbox for the tenant so the WebUI email dialog can be opened.
 * <p>
 * {@code MailRestController.createNewEmail} asserts a resolvable mailbox via
 * {@code MailService.findMailbox}, which resolves through {@code AD_MailConfig} routing rows
 * (each pointing at an {@code AD_MailBox}) and falls back to the client's (empty) email config
 * otherwise. The generic e2e tenant ships neither, so the dialog cannot open. This command
 * get-or-creates BOTH:
 * <ul>
 *     <li>an {@code AD_MailBox} (SMTP, no authorization — no mail is sent), and</li>
 *     <li>a wildcard {@code AD_MailConfig} routing row referencing it (blank CustomType/DocBaseType).</li>
 * </ul>
 * Both are keyed on a stable value and reused if already present, so repeated runs against the
 * shared e2e DB never accumulate duplicates (which would also break {@code findMailbox}). No mail
 * is actually sent.
 */
@Builder
public class CreateMailboxCommand
{
	@NonNull private final IQueryBL queryBL = Services.get(IQueryBL.class);

	@NonNull private final MasterdataContext context;
	@NonNull private final Map<String, JsonMailboxRequest> requests;

	private static final String DEFAULT_EMAIL = "frontend-testing@metasfresh.com";
	private static final String DEFAULT_SMTP_HOST = "localhost";
	private static final int DEFAULT_SMTP_PORT = 587;

	public ImmutableMap<String, JsonMailboxResponse> execute()
	{
		return CollectionUtils.mapValues(ImmutableMap.copyOf(requests), this::createMailbox);
	}

	private JsonMailboxResponse createMailbox(final String identifierStr, final JsonMailboxRequest request)
	{
		final ClientId clientId = MasterdataContext.CLIENT_ID;
		final String email = request.getEmail() != null ? request.getEmail() : DEFAULT_EMAIL;

		final I_AD_MailBox mailbox = getOrCreateMailbox(clientId, email, request);
		getOrCreateRouting(clientId, mailbox.getAD_MailBox_ID());

		return JsonMailboxResponse.builder()
				.mailboxId(mailbox.getAD_MailBox_ID())
				.email(email)
				.build();
	}

	private I_AD_MailBox getOrCreateMailbox(@NonNull final ClientId clientId, @NonNull final String email, @NonNull final JsonMailboxRequest request)
	{
		final I_AD_MailBox existing = queryBL.createQueryBuilder(I_AD_MailBox.class)
				.addEqualsFilter(I_AD_MailBox.COLUMNNAME_AD_Client_ID, clientId.getRepoId())
				.addEqualsFilter(I_AD_MailBox.COLUMNNAME_EMail, email)
				.create()
				.firstOnlyOrNull(I_AD_MailBox.class);
		if (existing != null)
		{
			return existing;
		}

		final I_AD_MailBox mailbox = InterfaceWrapperHelper.newInstance(I_AD_MailBox.class);
		mailbox.setAD_Org_ID(0); // Any org
		mailbox.setEMail(email);
		mailbox.setType(X_AD_MailBox.TYPE_SMTP);
		mailbox.setSMTPHost(request.getSmtpHost() != null ? request.getSmtpHost() : DEFAULT_SMTP_HOST);
		mailbox.setSMTPPort(request.getSmtpPort() != null ? request.getSmtpPort() : DEFAULT_SMTP_PORT);
		mailbox.setIsSmtpAuthorization(false); // no SMTP auth (no mail is sent). NOTE: findMailbox still
		// merges the sending user's config and asserts a non-empty SMTP username regardless of this flag,
		// so the login user must carry EMailUser (set in LoginUserCommand) for the Email dialog to open.
		mailbox.setIsStartTLS(false);
		InterfaceWrapperHelper.save(mailbox);
		return mailbox;
	}

	private void getOrCreateRouting(@NonNull final ClientId clientId, final int mailboxId)
	{
		final boolean exists = queryBL.createQueryBuilder(I_AD_MailConfig.class)
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_Client_ID, clientId.getRepoId())
				.addEqualsFilter(I_AD_MailConfig.COLUMNNAME_AD_MailBox_ID, mailboxId)
				.create()
				.anyMatch();
		if (exists)
		{
			return;
		}

		final I_AD_MailConfig routing = InterfaceWrapperHelper.newInstance(I_AD_MailConfig.class);
		routing.setAD_Org_ID(0); // Any org
		routing.setAD_MailBox_ID(mailboxId);
		// CustomType / DocBaseType left null => wildcard routing that matches any mailbox query for this client
		InterfaceWrapperHelper.save(routing);
	}
}
