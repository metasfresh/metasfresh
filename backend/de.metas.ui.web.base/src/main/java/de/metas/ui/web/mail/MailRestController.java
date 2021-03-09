package de.metas.ui.web.mail;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.email.EMail;
import de.metas.email.EMailAddress;
import de.metas.email.EMailAttachment;
import de.metas.email.EMailCustomType;
import de.metas.email.EMailSentStatus;
import de.metas.email.MailService;
import de.metas.email.mailboxes.ClientEMailConfig;
import de.metas.email.mailboxes.UserEMailConfig;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.report.DocumentReportFlavor;
import de.metas.report.ReportResultData;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.mail.WebuiEmail.WebuiEmailBuilder;
import de.metas.ui.web.mail.WebuiMailRepository.WebuiEmailRemovedEvent;
import de.metas.ui.web.mail.json.JSONEmail;
import de.metas.ui.web.mail.json.JSONEmailRequest;
import de.metas.ui.web.print.WebuiDocumentPrintRequest;
import de.metas.ui.web.print.WebuiDocumentPrintService;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.model.DocumentCollection;
import de.metas.user.UserId;
import de.metas.user.api.IUserBL;
import de.metas.user.api.IUserDAO;
import de.metas.util.Services;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.exceptions.FillMandatoryException;
import org.adempiere.service.IClientDAO;
import org.compiere.model.I_AD_User;
import org.compiere.util.Env;
import org.slf4j.Logger;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;
import java.util.stream.Stream;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

@RestController
@RequestMapping(MailRestController.ENDPOINT)
@ApiModel("Outbound email endpoint")
public class MailRestController
{
	private static final Logger logger = LogManager.getLogger(MailRestController.class);

	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/mail";

	private final IClientDAO clientsRepo = Services.get(IClientDAO.class);
	private final IUserBL usersService = Services.get(IUserBL.class);
	private final IUserDAO userDAO = Services.get(IUserDAO.class);
	private final UserSession userSession;
	private final WebuiMailRepository mailRepo;
	private final WebuiMailAttachmentsRepository mailAttachmentsRepo;
	private final MailService mailService;
	private final DocumentCollection documentCollection;
	private final WebuiDocumentPrintService documentPrintService;

	private static final String PATCH_FIELD_To = "to";
	private static final String PATCH_FIELD_Subject = "subject";
	private static final String PATCH_FIELD_Message = "message";
	private static final String PATCH_FIELD_Attachments = "attachments";
	private static final String PATCH_FIELD_TemplateId = "templateId";
	private static final Set<String> PATCH_FIELD_ALL = ImmutableSet.of(PATCH_FIELD_To, PATCH_FIELD_Subject, PATCH_FIELD_Message, PATCH_FIELD_Attachments, PATCH_FIELD_TemplateId);

	public MailRestController(
			@NonNull final UserSession userSession,
			@NonNull final WebuiMailRepository mailRepo,
			@NonNull final WebuiMailAttachmentsRepository mailAttachmentsRepo,
			@NonNull final MailService mailService,
			@NonNull final DocumentCollection documentCollection,
			@NonNull final WebuiDocumentPrintService documentPrintService)
	{
		this.userSession = userSession;
		this.mailRepo = mailRepo;
		this.mailAttachmentsRepo = mailAttachmentsRepo;
		this.mailService = mailService;
		this.documentCollection = documentCollection;
		this.documentPrintService = documentPrintService;
	}

	private void assertReadable(final WebuiEmail email)
	{
		// Make sure current logged in user is the owner
		if (!userSession.isLoggedInAs(email.getOwnerUserId()))
		{
			throw new AdempiereException("No credentials to change the email")
					.setParameter("emailId", email.getEmailId())
					.setParameter("ownerUserId", email.getOwnerUserId());
		}

	}

	private void assertWritable(final WebuiEmail email)
	{
		assertReadable(email);

		// Make sure the email was not already sent
		if (email.isSent())
		{
			throw new AdempiereException("Cannot change an email which was already sent")
					.setParameter("emailId", email.getEmailId());
		}
	}

	@PostMapping()
	@ApiOperation("Creates a new email")
	public JSONEmail createNewEmail(@RequestBody final JSONEmailRequest request)
	{
		userSession.assertLoggedIn();

		final UserId adUserId = userSession.getLoggedUserId();
		usersService.assertCanSendEMail(adUserId);

		final IntegerLookupValue from = IntegerLookupValue.of(adUserId.getRepoId(), userSession.getUserFullname() + " <" + userSession.getUserEmail() + "> ");
		final DocumentPath contextDocumentPath = JSONDocumentPath.toDocumentPathOrNull(request.getDocumentPath());

		final BoilerPlateContext attributes = documentCollection.createBoilerPlateContext(contextDocumentPath);
		final Integer toUserId = attributes.getAD_User_ID();
		final LookupValue to = mailRepo.getToByUserId(toUserId);

		final String emailId = mailRepo.createNewEmail(adUserId, from, to, contextDocumentPath).getEmailId();

		if (contextDocumentPath != null)
		{
			try
			{
				final ReportResultData contextDocumentPrint = documentPrintService.createDocumentPrint(WebuiDocumentPrintRequest.builder()
						.flavor(DocumentReportFlavor.EMAIL)
						.documentPath(contextDocumentPath)
						.userId(userSession.getLoggedUserId())
						.roleId(userSession.getLoggedRoleId())
						.build());

				attachFile(emailId, () -> mailAttachmentsRepo.createAttachment(emailId, contextDocumentPrint.getReportFilename(), contextDocumentPrint.getReportData()));
			}
			catch (final Exception ex)
			{
				logger.debug("Failed creating attachment from document print of {}", contextDocumentPath, ex);
			}
		}

		return JSONEmail.of(mailRepo.getEmail(emailId), userSession.getAD_Language());
	}

	@GetMapping("/{emailId}")
	@ApiOperation("Gets email by ID")
	public JSONEmail getEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();
		final WebuiEmail email = mailRepo.getEmail(emailId);
		assertReadable(email);
		return JSONEmail.of(email, userSession.getAD_Language());
	}

	@PostMapping("/{emailId}/send")
	@ApiOperation("Sends the email")
	public void sendEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();

		changeEmail(emailId, this::sendEmail);
		mailRepo.removeEmailById(emailId);
	}

	private WebuiEmail sendEmail(final WebuiEmail webuiEmail)
	{
		final String emailId = webuiEmail.getEmailId();

		//
		// Create the email object
		final ClientEMailConfig tenantEmailConfig = clientsRepo.getEMailConfigById(userSession.getClientId());
		final EMailCustomType mailCustomType = null;

		final UserId fromUserId = webuiEmail.getFrom().getIdAs(UserId::ofRepoId);
		final UserEMailConfig userEmailConfig = usersService.getEmailConfigById(fromUserId);

		final List<EMailAddress> toList = extractEMailAddreses(webuiEmail.getTo()).collect(ImmutableList.toImmutableList());
		if (toList.isEmpty())
		{
			throw new FillMandatoryException("To");
		}
		final EMailAddress to = toList.get(0);
		final String subject = webuiEmail.getSubject();
		final String message = webuiEmail.getMessage();
		final boolean html = false;
		final EMail email = mailService.createEMail(
				tenantEmailConfig,
				mailCustomType,
				userEmailConfig,
				to,
				subject,
				message,
				html);
		toList.stream().skip(1).forEach(email::addTo);

		webuiEmail.getAttachments()
				.stream()
				.map(webuiAttachment -> {
					final byte[] content = mailAttachmentsRepo.getAttachmentAsByteArray(emailId, webuiAttachment);
					return EMailAttachment.of(webuiAttachment.getDisplayName(), content);
				})
				.forEach(email::addAttachment);

		//
		// Actually send the email
		final EMailSentStatus sentStatus = email.send();
		if (!sentStatus.isSentOK())
		{
			throw new AdempiereException("Failed sending the email: " + sentStatus.getSentMsg());
		}

		//
		// Delete temporary attachments
		mailAttachmentsRepo.deleteAttachments(emailId, webuiEmail.getAttachments());

		// Mark the webui email as sent
		return webuiEmail.toBuilder().sent(true).build();
	}

	private Stream<EMailAddress> extractEMailAddreses(final LookupValuesList users)
	{

		return users.stream()
				.map(this::extractEMailAddress);
	}

	private EMailAddress extractEMailAddress(final LookupValue userLookupValue)
	{
		final UserId adUserId = userLookupValue.getIdAs(UserId::ofRepoIdOrNull);
		if (adUserId == null)
		{
			// consider the email as the DisplayName
			return EMailAddress.ofString(userLookupValue.getDisplayName());
		}
		else
		{
			final I_AD_User adUser = userDAO.getById(adUserId);
			final String email = adUser.getEMail();
			if (Check.isEmpty(email, true))
			{
				throw new AdempiereException("User " + adUser.getName() + " does not have email");
			}
			return EMailAddress.ofString(email);
		}
	}

	@PatchMapping("/{emailId}")
	@ApiOperation("Changes the email")
	public JSONEmail changeEmail(@PathVariable("emailId") final String emailId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final WebuiEmailChangeResult result = changeEmail(emailId, emailOld -> changeEmail(emailOld, events));
		return JSONEmail.of(result.getEmail(), userSession.getAD_Language());
	}

	private WebuiEmailChangeResult changeEmail(final String emailId, final UnaryOperator<WebuiEmail> emailModifier)
	{
		final WebuiEmailChangeResult result = mailRepo.changeEmail(emailId, emailOld -> {
			assertWritable(emailOld);
			return emailModifier.apply(emailOld);
		});

		// Delete the attachments which were removed from email
		final LookupValuesList attachmentsOld = result.getOriginalEmail().getAttachments();
		final LookupValuesList attachmentsNew = result.getEmail().getAttachments();
		final LookupValuesList attachmentsToRemove = attachmentsOld.removeAll(attachmentsNew);
		mailAttachmentsRepo.deleteAttachments(emailId, attachmentsToRemove);

		return result;
	}

	private WebuiEmail changeEmail(final WebuiEmail email, final List<JSONDocumentChangedEvent> events)
	{
		final WebuiEmailBuilder emailBuilder = email.toBuilder();
		events.forEach(event -> changeEmail(email, emailBuilder, event));
		return emailBuilder.build();
	}

	private void changeEmail(final WebuiEmail email, final WebuiEmailBuilder newEmailBuilder, final JSONDocumentChangedEvent event)
	{
		if (!event.isReplace())
		{
			throw new AdempiereException("Unsupported event")
					.setParameter("event", event);
		}

		final String fieldName = event.getPath();
		if (PATCH_FIELD_To.equals(fieldName))
		{
			@SuppressWarnings("unchecked") final List<Object> jsonTo = (List<Object>)event.getValue();

			@SuppressWarnings("unchecked") final LookupValuesList to = jsonTo.stream()
					.map(mapObj -> (Map<String, Object>)mapObj)
					.map(JSONLookupValue::integerLookupValueFromJsonMap)
					.collect(LookupValuesList.collect());

			newEmailBuilder.to(to);
		}
		else if (PATCH_FIELD_Subject.equals(fieldName))
		{
			final String subject = (String)event.getValue();
			newEmailBuilder.subject(subject);
		}
		else if (PATCH_FIELD_Message.equals(fieldName))
		{
			final String message = (String)event.getValue();
			newEmailBuilder.message(message);
		}
		else if (PATCH_FIELD_Attachments.equals(fieldName))
		{
			@SuppressWarnings("unchecked") final List<Object> jsonAttachments = (List<Object>)event.getValue();

			@SuppressWarnings("unchecked") final LookupValuesList attachments = jsonAttachments.stream()
					.map(mapObj -> (Map<String, Object>)mapObj)
					.map(JSONLookupValue::stringLookupValueFromJsonMap)
					.collect(LookupValuesList.collect());

			newEmailBuilder.attachments(attachments);
		}
		else if (PATCH_FIELD_TemplateId.equals(fieldName))
		{
			@SuppressWarnings("unchecked") final LookupValue templateId = JSONLookupValue.integerLookupValueFromJsonMap((Map<String, Object>)event.getValue());
			applyTemplate(email, newEmailBuilder, templateId);
		}
		else
		{
			throw new AdempiereException("Unsupported event path")
					.setParameter("event", event)
					.setParameter("fieldName", fieldName)
					.setParameter("availablePaths", PATCH_FIELD_ALL);
		}
	}

	@EventListener
	public void onWebuiMailRemovedFromRepository(final WebuiEmailRemovedEvent event)
	{
		logger.debug("Got event: {}", event);
		final WebuiEmail email = event.getEmail();
		mailAttachmentsRepo.deleteAttachments(email.getEmailId(), email.getAttachments());
	}

	@GetMapping("/{emailId}/field/to/typeahead")
	@ApiOperation("Typeahead endpoint for any To field")
	public JSONLookupValuesList getToTypeahead(@PathVariable("emailId") final String emailId, @RequestParam("query") final String query)
	{
		userSession.assertLoggedIn();
		return toJSONLookupValuesList(mailRepo.getToTypeahead(emailId, query));
	}

	private JSONLookupValuesList toJSONLookupValuesList(final LookupValuesList lookupValuesList)
	{
		return JSONLookupValuesList.ofLookupValuesList(lookupValuesList, userSession.getAD_Language());
	}

	@PostMapping("/{emailId}/field/attachments")
	@ApiOperation("Attaches a file to email")
	public JSONEmail attachFile(@PathVariable("emailId") final String emailId, @RequestParam("file") final MultipartFile file)
	{
		userSession.assertLoggedIn();

		final WebuiEmail email = attachFile(emailId, () -> mailAttachmentsRepo.createAttachment(emailId, file));
		return JSONEmail.of(email, userSession.getAD_Language());
	}

	private WebuiEmail attachFile(final String emailId, final Supplier<LookupValue> attachmentProducer)
	{
		// Ask the producer to create the attachment
		@NonNull final LookupValue attachment = attachmentProducer.get();

		try
		{
			final WebuiEmailChangeResult result = changeEmail(emailId, emailOld -> {
				final LookupValuesList attachmentsOld = emailOld.getAttachments();
				final LookupValuesList attachmentsNew = attachmentsOld.addIfAbsent(attachment);

				return emailOld.toBuilder().attachments(attachmentsNew).build();
			});

			return result.getEmail();
		}
		catch (final Throwable ex)
		{
			mailAttachmentsRepo.deleteAttachment(emailId, attachment);
			throw AdempiereException.wrapIfNeeded(ex);
		}

	}

	@GetMapping("/templates")
	@ApiOperation("Available Email templates")
	public JSONLookupValuesList getTemplates()
	{
		return MADBoilerPlate.getAll(Env.getCtx())
				.stream()
				.map(adBoilerPlate -> JSONLookupValue.of(adBoilerPlate.getAD_BoilerPlate_ID(), adBoilerPlate.getName()))
				.collect(JSONLookupValuesList.collect());
	}

	private void applyTemplate(final WebuiEmail email, final WebuiEmailBuilder newEmailBuilder, final LookupValue templateId)
	{
		final Properties ctx = Env.getCtx();
		final MADBoilerPlate boilerPlate = MADBoilerPlate.get(ctx, templateId.getIdAsInt());

		//
		// Attributes
		final BoilerPlateContext attributes = documentCollection.createBoilerPlateContext(email.getContextDocumentPath());

		//
		// Subject
		final String subject = MADBoilerPlate.parseText(ctx, boilerPlate.getSubject(), true, attributes, ITrx.TRXNAME_None);
		newEmailBuilder.subject(subject);

		// Message
		newEmailBuilder.message(boilerPlate.getTextSnippetParsed(attributes));
	}
}
