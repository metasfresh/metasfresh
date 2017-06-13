package de.metas.ui.web.mail;

import java.io.IOException;
import java.util.List;

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

import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.mail.json.JSONEmail;
import de.metas.ui.web.mail.json.JSONEmailRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
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
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/mail";

	@Autowired
	private UserSession userSession;

	@Autowired
	private WebuiMailRepository mailRepo;

	@PostMapping()
	@ApiOperation("Creates a new email")
	public JSONEmail createNewEmail(final JSONEmailRequest request)
	{
		userSession.assertLoggedIn();

		final WebuiEmail email = mailRepo.createNewEmail();
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

		mailRepo.changeEmailAndRemove(emailId, emailOld -> {
			throw new UnsupportedOperationException(); // TODO
		});
	}

	@PatchMapping("/{emailId}")
	@ApiOperation("Changes the email")
	public JSONEmail changeEmail(@PathVariable("emailId") final String emailId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final WebuiEmail email = mailRepo.changeEmail(emailId, emailOld -> {
			throw new UnsupportedOperationException(); // TODO
		});

		return JSONEmail.of(email);
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
	public void attachFile(@PathVariable("emailId") final String emailId, @RequestParam("file") final MultipartFile file) throws IOException
	{
		userSession.assertLoggedIn();
		throw new UnsupportedOperationException(); // TODO
	}
}
