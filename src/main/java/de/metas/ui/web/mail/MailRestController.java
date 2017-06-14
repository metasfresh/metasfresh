package de.metas.ui.web.mail;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

import org.adempiere.exceptions.AdempiereException;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableSet;

import de.metas.logging.LogManager;
import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.mail.WebuiEmail.WebuiEmailBuilder;
import de.metas.ui.web.mail.json.JSONEmail;
import de.metas.ui.web.mail.json.JSONEmailRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.datatypes.LookupValue.StringLookupValue;
import de.metas.ui.web.window.datatypes.LookupValuesList;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiOperation;

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

	@Autowired
	private UserSession userSession;

	@Autowired
	private WebuiMailRepository mailRepo;

	private static final String PATCH_FIELD_To = "to";
	private static final String PATCH_FIELD_Subject = "subject";
	private static final String PATCH_FIELD_Message = "message";
	private static final String PATCH_FIELD_Attachments = "attachments";
	private static final String PATCH_FIELD_TemplateId = "templateId";
	private static final Set<String> PATCH_FIELD_ALL = ImmutableSet.of(PATCH_FIELD_To, PATCH_FIELD_Subject, PATCH_FIELD_Message, PATCH_FIELD_Attachments, PATCH_FIELD_TemplateId);

	@PostMapping()
	@ApiOperation("Creates a new email")
	public JSONEmail createNewEmail(final JSONEmailRequest request)
	{
		userSession.assertLoggedIn();

		// TODO: check if the logged in user has a valid email address and can send emails (EMailUser, EMailUserPW)
		final IntegerLookupValue from = IntegerLookupValue.of(userSession.getAD_User_ID(), userSession.getUserFullname() + " <" + userSession.getUserEmail() + "> ");

		final WebuiEmail email = mailRepo.createNewEmail(from);
		return JSONEmail.of(email);
	}

	@GetMapping("/{emailId}")
	@ApiOperation("Gets email by ID")
	public JSONEmail getEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();
		return JSONEmail.of(mailRepo.getEmail(emailId));
	}

	@PostMapping("/{emailId}/send")
	@ApiOperation("Sends the email")
	public void sendEmail(@PathVariable("emailId") final String emailId)
	{
		userSession.assertLoggedIn();

		mailRepo.changeEmailAndRemove(emailId, emailOld -> sendEmail(emailOld));
	}

	private WebuiEmail sendEmail(final WebuiEmail email)
	{
		// TODO: actually send the email
		logger.warn("WARNING: email was not sent because this method is mocked: {}", email);

		return email.toBuilder().sent(true).build();
	}

	@PatchMapping("/{emailId}")
	@ApiOperation("Changes the email")
	public JSONEmail changeEmail(@PathVariable("emailId") final String emailId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final WebuiEmail email = mailRepo.changeEmail(emailId, emailOld -> changeEmail(emailOld, events));
		return JSONEmail.of(email);
	}

	private WebuiEmail changeEmail(final WebuiEmail email, final List<JSONDocumentChangedEvent> events)
	{
		final WebuiEmailBuilder emailBuilder = email.toBuilder();
		events.forEach(event -> changeEmail(emailBuilder, event));
		return emailBuilder.build();
	}

	private void changeEmail(final WebuiEmailBuilder emailBuilder, final JSONDocumentChangedEvent event)
	{
		if (!event.isReplace())
		{
			throw new AdempiereException("Unsupported event")
					.setParameter("event", event);
		}

		final String fieldName = event.getPath();
		if (PATCH_FIELD_To.equals(fieldName))
		{
			@SuppressWarnings("unchecked")
			final List<Object> jsonTo = (List<Object>)event.getValue();

			@SuppressWarnings("unchecked")
			final LookupValuesList to = jsonTo.stream()
					.map(mapObj -> (Map<String, String>)mapObj)
					.map(map -> JSONLookupValue.integerLookupValueFromJsonMap(map))
					.collect(LookupValuesList.collect());

			emailBuilder.to(to);
		}
		else if (PATCH_FIELD_Subject.equals(fieldName))
		{
			final String subject = (String)event.getValue();
			emailBuilder.subject(subject);
		}
		else if (PATCH_FIELD_Message.equals(fieldName))
		{
			final String message = (String)event.getValue();
			emailBuilder.message(message);
		}
		else if (PATCH_FIELD_Attachments.equals(fieldName))
		{
			@SuppressWarnings("unchecked")
			final List<Object> jsonAttachments = (List<Object>)event.getValue();

			@SuppressWarnings("unchecked")
			final LookupValuesList attachments = jsonAttachments.stream()
					.map(mapObj -> (Map<String, String>)mapObj)
					.map(map -> JSONLookupValue.stringLookupValueFromJsonMap(map))
					.collect(LookupValuesList.collect());

			emailBuilder.attachments(attachments);
		}
		else if (PATCH_FIELD_TemplateId.equals(fieldName))
		{
			@SuppressWarnings("unchecked")
			final LookupValue templateId = JSONLookupValue.integerLookupValueFromJsonMap((Map<String, String>)event.getValue());

			// TODO: really apply given template
			logger.warn("WARNING: skip applying templateId={} to {} because it's mocked", templateId, emailBuilder);
		}
		else
		{
			throw new AdempiereException("Unsupported event path")
					.setParameter("event", event)
					.setParameter("fieldName", fieldName)
					.setParameter("availablePaths", PATCH_FIELD_ALL);
		}

	}

	@GetMapping("/{emailId}/field/to/typeahead")
	@ApiOperation("Typeahead endpoint for any To field")
	public JSONLookupValuesList getToTypeahead(@PathVariable("emailId") final String emailId, @RequestParam("query") final String query)
	{
		userSession.assertLoggedIn();
		return JSONLookupValuesList.ofLookupValuesList(mailRepo.getToTypeahead(emailId, query));
	}

	@PostMapping("/{emailId}/field/attachments")
	@ApiOperation("Attaches a file to email")
	public JSONEmail attachFile(@PathVariable("emailId") final String emailId, @RequestParam("file") final MultipartFile file) throws IOException
	{
		userSession.assertLoggedIn();

		final LookupValue attachment = createAttachment(emailId, file);
		try
		{
			final WebuiEmail email = mailRepo.changeEmail(emailId, emailOld -> {
				final LookupValuesList attachmentsOld = emailOld.getAttachments();
				final LookupValuesList attachmentsNew = attachmentsOld.addIfAbsent(attachment);

				return emailOld.toBuilder().attachments(attachmentsNew).build();
			});

			return JSONEmail.of(email);
		}
		catch (final Throwable ex)
		{
			deleteAttachment(attachment);
			throw AdempiereException.wrapIfNeeded(ex);
		}
	}

	private LookupValue createAttachment(final String emailId, final MultipartFile file)
	{
		final String id = UUID.randomUUID().toString();
		String displayName = file.getOriginalFilename();
		if (Check.isEmpty(displayName, true))
		{
			displayName = file.getName();
		}
		if (Check.isEmpty(displayName, true))
		{
			throw new AdempiereException("Filename not provided");
		}

		// TODO: really upload the file
		logger.warn("WARNING: the actual attachment was not created because this is an mocked endpoint");

		return StringLookupValue.of(id, displayName);
	}

	private void deleteAttachment(final LookupValue attachment)
	{
		// TODO: really delete the attachment file
		logger.warn("WARNING: the attachment was not deleted because this is an mocked endpoint");
	}

	@GetMapping("/templates")
	@ApiOperation("Available Email templates")
	public JSONLookupValuesList getTemplates()
	{
		// TODO: retrieve & return the templates
		return Stream.<JSONLookupValue> empty().collect(JSONLookupValuesList.collect());
	}
}
