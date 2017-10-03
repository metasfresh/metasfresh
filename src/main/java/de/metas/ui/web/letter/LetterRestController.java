package de.metas.ui.web.letter;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.function.UnaryOperator;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.apache.commons.io.FileUtils;
import org.compiere.util.Env;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.common.collect.ImmutableSet;

import de.metas.i18n.IMsgBL;
import de.metas.letters.model.Letters;
import de.metas.letters.model.MADBoilerPlate;
import de.metas.letters.model.MADBoilerPlate.BoilerPlateContext;
import de.metas.ui.web.config.WebConfig;
import de.metas.ui.web.letter.WebuiLetter.WebuiLetterBuilder;
import de.metas.ui.web.letter.json.JSONLetter;
import de.metas.ui.web.letter.json.JSONLetterRequest;
import de.metas.ui.web.session.UserSession;
import de.metas.ui.web.window.datatypes.DocumentPath;
import de.metas.ui.web.window.datatypes.LookupValue;
import de.metas.ui.web.window.datatypes.json.JSONDocumentChangedEvent;
import de.metas.ui.web.window.datatypes.json.JSONDocumentPath;
import de.metas.ui.web.window.datatypes.json.JSONLookupValue;
import de.metas.ui.web.window.datatypes.json.JSONLookupValuesList;
import de.metas.ui.web.window.model.DocumentCollection;
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
@RequestMapping(LetterRestController.ENDPOINT)
@ApiModel("Letter endpoint")
public class LetterRestController
{
	public static final String ENDPOINT = WebConfig.ENDPOINT_ROOT + "/letter";

	@Autowired
	private UserSession userSession;

	@Autowired
	private WebuiLetterRepository lettersRepo;

	@Autowired
	private DocumentCollection documentCollection;

	private static final String PATCH_FIELD_Message = "message";
	private static final String PATCH_FIELD_TemplateId = "templateId";
	private static final Set<String> PATCH_FIELD_ALL = ImmutableSet.of(PATCH_FIELD_Message, PATCH_FIELD_TemplateId);

	private final void assertReadable(final WebuiLetter letter)
	{
		// Make sure current logged in user is the owner
		final int loggedUserId = userSession.getAD_User_ID();
		if (letter.getOwnerUserId() != loggedUserId)
		{
			throw new AdempiereException("No credentials to read the letter")
					.setParameter("letterId", letter.getLetterId())
					.setParameter("ownerUserId", letter.getOwnerUserId())
					.setParameter("loggedUserId", loggedUserId);
		}
	}

	private final void assertWritable(final WebuiLetter letter)
	{
		assertReadable(letter);

		// Make sure the letter was not already processed
		if (letter.isProcessed())
		{
			throw new AdempiereException("Cannot change an letter which was already processed")
					.setParameter("letterId", letter.getLetterId());
		}
	}

	@PostMapping
	@ApiOperation("Creates a new letter")
	public JSONLetter createNewLetter(@RequestBody final JSONLetterRequest request)
	{
		userSession.assertLoggedIn();

		final DocumentPath contextDocumentPath = JSONDocumentPath.toDocumentPathOrNull(request.getDocumentPath());
		final WebuiLetter letter = lettersRepo.createNewLetter(userSession.getAD_User_ID(), contextDocumentPath);

		return JSONLetter.of(letter);
	}

	@GetMapping("/{letterId}")
	@ApiOperation("Gets letter by ID")
	public JSONLetter getLetter(@PathVariable("letterId") final String letterId)
	{
		userSession.assertLoggedIn();
		final WebuiLetter letter = lettersRepo.getLetter(letterId);
		assertReadable(letter);
		return JSONLetter.of(letter);
	}

	private ResponseEntity<byte[]> createPDFResponseEntry(final byte[] pdfData)
	{
		final String pdfFilename = Services.get(IMsgBL.class).getMsg(Env.getCtx(), Letters.MSG_Letter);
		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_PDF);
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + pdfFilename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(pdfData, headers, HttpStatus.OK);
		return response;
	}

	@GetMapping("/{letterId}/printPreview")
	@ApiOperation("Returns letter's printable version (e.g. PDF)")
	public ResponseEntity<byte[]> getLetterPrintPreview(@PathVariable("letterId") final String letterId)
	{
		userSession.assertLoggedIn();

		//
		// Get the letter
		final WebuiLetter letter = lettersRepo.getLetter(letterId);
		assertReadable(letter);

		//
		// Create and return the printable letter
		final byte[] pdfData = lettersRepo.createPDFData(letter);
		return createPDFResponseEntry(pdfData);
	}

	@PostMapping("/{letterId}/complete")
	@ApiOperation("Completes the letter and returns it's printable version (e.g. PDF)")
	public ResponseEntity<byte[]> complete(@PathVariable("letterId") final String letterId)
	{
		userSession.assertLoggedIn();

		final WebuiLetterChangeResult result = changeLetter(letterId, letter -> {

			//
			// Create the printable letter
			final byte[] pdfData = lettersRepo.createPDFData(letter);

			final File pdfFile = createFile(pdfData); 
			//
			// create the Boilerplate context
			final BoilerPlateContext context = documentCollection.createBoilerPlateContext(letter.getContextDocumentPath());
			//
			// Create the request
			final TableRecordReference recordRef = documentCollection.getTableRecordReference(letter.getContextDocumentPath());
			MADBoilerPlate.createRequest(pdfFile, recordRef.getAD_Table_ID(), recordRef.getRecord_ID(), context);

			return letter.toBuilder()
					.processed(true)
					.temporaryPDFData(pdfData)
					.build();
		}); 

		//
		// Remove the letter
		lettersRepo.removeLetterById(letterId);

		//
		// Return the printable letter
		return createPDFResponseEntry(result.getLetter().getTemporaryPDFData());
	}
	
	private File createFile(final byte[] pdfData)
	{
		final String pdfFilenamePrefix = Services.get(IMsgBL.class).getMsg(Env.getCtx(), Letters.MSG_Letter);
		File pdfFile = null;
		try
		{
			pdfFile = File.createTempFile(pdfFilenamePrefix, ".pdf");
			FileUtils.writeByteArrayToFile(pdfFile, pdfData);
		}
		catch (IOException e)
		{
			throw AdempiereException.wrapIfNeeded(e);
		}

		return pdfFile;
	}

	@PatchMapping("/{letterId}")
	@ApiOperation("Changes the letter")
	public JSONLetter changeLetter(@PathVariable("letterId") final String letterId, @RequestBody final List<JSONDocumentChangedEvent> events)
	{
		userSession.assertLoggedIn();

		final WebuiLetterChangeResult result = changeLetter(letterId, letterOld -> changeLetter(letterOld, events));
		return JSONLetter.of(result.getLetter());
	}

	private WebuiLetterChangeResult changeLetter(final String letterId, final UnaryOperator<WebuiLetter> letterModifier)
	{
		final WebuiLetterChangeResult result = lettersRepo.changeLetter(letterId, letterOld -> {
			assertWritable(letterOld);
			return letterModifier.apply(letterOld);
		});

		return result;
	}

	private WebuiLetter changeLetter(final WebuiLetter letter, final List<JSONDocumentChangedEvent> events)
	{
		final WebuiLetterBuilder letterBuilder = letter.toBuilder();
		events.forEach(event -> changeLetter(letter, letterBuilder, event));
		return letterBuilder.build();
	}

	private void changeLetter(final WebuiLetter letter, final WebuiLetter.WebuiLetterBuilder newLetterBuilder, final JSONDocumentChangedEvent event)
	{
		if (!event.isReplace())
		{
			throw new AdempiereException("Unsupported event")
					.setParameter("event", event);
		}

		final String fieldName = event.getPath();
		if (PATCH_FIELD_Message.equals(fieldName))
		{
			final String message = event.getValueAsString(null);
			newLetterBuilder.content(message);
		}
		else if (PATCH_FIELD_TemplateId.equals(fieldName))
		{
			@SuppressWarnings("unchecked")
			final LookupValue templateId = JSONLookupValue.integerLookupValueFromJsonMap((Map<String, String>)event.getValue());
			applyTemplate(letter, newLetterBuilder, templateId);
		}
		else
		{
			throw new AdempiereException("Unsupported event path")
					.setParameter("event", event)
					.setParameter("fieldName", fieldName)
					.setParameter("availablePaths", PATCH_FIELD_ALL);
		}
	}

	@GetMapping("/templates")
	@ApiOperation("Available Email templates")
	public JSONLookupValuesList getTemplates()
	{
		return MADBoilerPlate.getAll(userSession.getCtx())
				.stream()
				.map(adBoilerPlate -> JSONLookupValue.of(adBoilerPlate.getAD_BoilerPlate_ID(), adBoilerPlate.getName()))
				.collect(JSONLookupValuesList.collect());
	}

	private void applyTemplate(final WebuiLetter letter, final WebuiLetterBuilder newLetterBuilder, final LookupValue templateId)
	{
		final Properties ctx = userSession.getCtx();
		final MADBoilerPlate boilerPlate = MADBoilerPlate.get(ctx, templateId.getIdAsInt());

		//
		// Attributes
		final BoilerPlateContext context = documentCollection.createBoilerPlateContext(letter.getContextDocumentPath());

		//
		// Content and subject
		newLetterBuilder.content(boilerPlate.getTextSnippetParsed(context));
		newLetterBuilder.subject(boilerPlate.getSubject());
	}

}
