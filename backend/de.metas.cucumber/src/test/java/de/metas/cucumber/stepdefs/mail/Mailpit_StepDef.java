/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.mail;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.metas.cucumber.stepdefs.invoice.C_Invoice_StepDefData;
import de.metas.util.Check;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_Invoice;
import org.mustangproject.validator.EPart;
import org.mustangproject.validator.ESeverity;
import org.mustangproject.validator.ValidationContext;
import org.mustangproject.validator.ValidationResultItem;
import org.mustangproject.validator.ZUGFeRDValidator;

import javax.annotation.Nullable;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Step definitions that assert against the running Mailpit instance via its REST API.
 *
 * <p>Mailpit is the SMTP sink used by the local/CI infrastructure: the document-mail workpackage
 * processor sends the email over SMTP, Mailpit captures it, and these steps verify it actually
 * arrived (sender, attachment filename, attachment content).
 *
 * <p>The API base URL is read from the {@code TEST_MAILPIT_API_URL} environment variable (set in the
 * infrastructure env files), defaulting to Mailpit's default management port {@code http://localhost:8025} when absent.
 */
@RequiredArgsConstructor
public class Mailpit_StepDef
{
	private static final String DEFAULT_API_URL = "http://localhost:8025";
	// The mail is sent by an async workpackage that runs after the C_Doc_Outbound_Log appears and
	// after the (mocked) PDF archive is produced, so poll generously to absorb that lag.
	private static final int POLL_ATTEMPTS = 360;
	private static final long POLL_INTERVAL_MILLIS = 500L;

	@NonNull private final C_Invoice_StepDefData invoiceTable;

	private final ObjectMapper objectMapper = new ObjectMapper();

	private static String apiBaseUrl()
	{
		final String fromEnv = System.getProperty("TEST_MAILPIT_API_URL", System.getenv("TEST_MAILPIT_API_URL"));
		final String url = Check.isNotBlank(fromEnv) ? fromEnv.trim() : DEFAULT_API_URL;
		return url.endsWith("/") ? url.substring(0, url.length() - 1) : url;
	}

	/**
	 * Deletes all messages currently held by Mailpit, so a scenario starts from an empty inbox.
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * And mailpit inbox is cleared
	 * </pre>
	 */
	@Given("mailpit inbox is cleared")
	public void mailpit_inbox_is_cleared()
	{
		httpRequest("DELETE", apiBaseUrl() + "/api/v1/messages");
	}

	/**
	 * Polls Mailpit until at least one message arrives, then asserts the latest message:
	 * <ul>
	 *   <li>was sent from {@code fromAddress},</li>
	 *   <li>carries an attachment whose filename equals {@code attachmentFileName},</li>
	 *   <li>and that attachment's (UTF-8) content contains {@code contentMarker}.</li>
	 * </ul>
	 *
	 * @cucumber.stepdef
	 * @cucumber.example
	 * <pre>
	 * Then mailpit received an email from "billing@metasfresh.local" with attachment "RE-1_xrechnung.xml" containing "xeinkauf.de:kosit:xrechnung_3.0"
	 * </pre>
	 */
	@Then("mailpit received an email from {string} with attachment {string} containing {string}")
	public void mailpit_received_email(
			@NonNull final String fromAddress,
			@NonNull final String attachmentFileName,
			@NonNull final String contentMarker)
	{
		final String latestMessageId = pollForLatestMessageId();

		final JsonNode message = parse(httpGet(apiBaseUrl() + "/api/v1/message/" + latestMessageId));

		final String actualFrom = message.path("From").path("Address").asText("");
		if (!fromAddress.equals(actualFrom))
		{
			throw new AdempiereException("Mailpit message From mismatch — expected '" + fromAddress + "' but got '" + actualFrom + "'");
		}

		final String partId = findAttachmentPartId(message, attachmentFileName);
		if (partId == null)
		{
			throw new AdempiereException("Mailpit message has no attachment named '" + attachmentFileName + "'. Attachments: " + message.path("Attachments"));
		}

		final byte[] partBytes = httpGetBytes(apiBaseUrl() + "/api/v1/message/" + latestMessageId + "/part/" + partId);
		final String partContent = new String(partBytes, StandardCharsets.UTF_8);
		if (!partContent.contains(contentMarker))
		{
			throw new AdempiereException("Mailpit attachment '" + attachmentFileName + "' does not contain marker '" + contentMarker + "'."
					+ " First 500 bytes: " + partContent.substring(0, Math.min(500, partContent.length())));
		}
	}

	/**
	 * Like {@link #mailpit_received_email(String, String, String)} but resolves the expected
	 * attachment filename from the (completed) invoice's {@code DocumentNo}, since the XRechnung
	 * attachment the interceptor creates is named {@code <DocumentNo>_xrechnung.xml} and the
	 * DocumentNo is only assigned on completion.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then mailpit received an email from "billing@metasfresh.local" with the xrechnung attachment of invoice "invoice" containing "xeinkauf.de:kosit:xrechnung_3.0"
	 * </pre>
	 */
	@Then("mailpit received an email from {string} with the xrechnung attachment of invoice {string} containing {string}")
	public void mailpit_received_xrechnung_of_invoice(
			@NonNull final String fromAddress,
			@NonNull final String invoiceIdentifier,
			@NonNull final String contentMarker)
	{
		final I_C_Invoice invoice = invoiceTable.get(invoiceIdentifier);
		InterfaceWrapperHelper.refresh(invoice);
		final String attachmentFileName = invoice.getDocumentNo() + "_xrechnung.xml";
		mailpit_received_email(fromAddress, attachmentFileName, contentMarker);
	}

	/**
	 * Polls Mailpit until at least one message arrives, then:
	 * <ol>
	 *   <li>Asserts the message was sent from {@code fromAddress}.</li>
	 *   <li>Finds the PDF attachment whose filename ends with {@code .pdf} (the invoice archive PDF).</li>
	 *   <li>Validates the PDF with the Mustang {@link ZUGFeRDValidator} and asserts zero
	 *       {@code EPart.pdf} (PDF/A-3 conformance) and zero {@code EPart.fx} (Factur-X structural)
	 *       errors/fatals/exceptions — proving the attachment is a valid ZUGFeRD/Factur-X PDF.</li>
	 * </ol>
	 *
	 * <p>CII-content schematron parts ({@code ox}, {@code xr}) are explicitly excluded:
	 * the cucumber fixture uses a minimal CII that may not pass full schematron checks; the
	 * assembler's container correctness (PDF/A-3 conformance + Factur-X embedding) is what this
	 * step asserts.
	 *
	 * @cucumber.stepdef
	 * @cucumber.depends StepDefData: C_Invoice_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then mailpit received an email from "billing@metasfresh.local" with the zugferd pdf of invoice "invoice"
	 * </pre>
	 */
	@Then("mailpit received an email from {string} with the zugferd pdf of invoice {string}")
	public void mailpit_received_zugferd_pdf_of_invoice(
			@NonNull final String fromAddress,
			@NonNull final String invoiceIdentifier)
	{
		final String latestMessageId = pollForLatestMessageId();

		final JsonNode message = parse(httpGet(apiBaseUrl() + "/api/v1/message/" + latestMessageId));

		final String actualFrom = message.path("From").path("Address").asText("");
		if (!fromAddress.equals(actualFrom))
		{
			throw new AdempiereException("Mailpit message From mismatch — expected '" + fromAddress + "' but got '" + actualFrom + "'");
		}

		// Find the PDF attachment (invoice archive; filename ends with .pdf)
		final String pdfPartId = findPdfAttachmentPartId(message);
		if (pdfPartId == null)
		{
			throw new AdempiereException("Mailpit message has no PDF attachment (.pdf). Attachments: " + message.path("Attachments"));
		}

		final byte[] pdfBytes = httpGetBytes(apiBaseUrl() + "/api/v1/message/" + latestMessageId + "/part/" + pdfPartId);

		// Write to a temp file because ZUGFeRDValidator requires a filesystem path
		Path tmpPdf = null;
		try
		{
			tmpPdf = Files.createTempFile("zugferd-cucumber-", ".pdf");
			Files.write(tmpPdf, pdfBytes);

			final InspectableZUGFeRDValidator validator = new InspectableZUGFeRDValidator();
			validator.validate(tmpPdf.toAbsolutePath().toString());

			// Read the protected ValidationContext via a thin subclass (no reflection — matches the
			// pattern in ZugferdAssemblerTest and is resilient to future Mustang refactors).
			final ValidationContext ctx = validator.getValidationContext();

			if (ctx == null)
			{
				throw new AdempiereException("ZUGFeRDValidator returned null ValidationContext — validate() may not have run");
			}

			// Collect container-level errors: EPart.pdf (PDF/A-3 conformance) + EPart.fx (Factur-X structural)
			final List<ValidationResultItem> containerErrors = ctx.getResults().stream()
					.filter(item -> item.getSeverity() == ESeverity.error
							|| item.getSeverity() == ESeverity.fatal
							|| item.getSeverity() == ESeverity.exception)
					.filter(item -> item.getPart() == EPart.pdf || item.getPart() == EPart.fx)
					.collect(Collectors.toList());

			if (!containerErrors.isEmpty())
			{
				final String summary = containerErrors.stream()
						.map(item -> "[" + item.getPart() + "/" + item.getSeverity() + "] " + item.getMessage())
						.collect(Collectors.joining("; "));
				throw new AdempiereException("Invoice PDF in Mailpit is not a valid ZUGFeRD document. "
						+ containerErrors.size() + " container error(s): " + summary);
			}
		}
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("ZUGFeRD validation of Mailpit PDF attachment failed", e);
		}
		finally
		{
			if (tmpPdf != null)
			{
				try
				{
					Files.deleteIfExists(tmpPdf);
				}
				catch (final Exception ignored)
				{
				}
			}
		}
	}

	@Nullable
	private static String findAttachmentPartId(@NonNull final JsonNode message, @NonNull final String attachmentFileName)
	{
		final JsonNode attachments = message.path("Attachments");
		if (attachments.isArray())
		{
			for (final JsonNode attachment : attachments)
			{
				if (attachmentFileName.equals(attachment.path("FileName").asText(null)))
				{
					return attachment.path("PartID").asText(null);
				}
			}
		}
		return null;
	}

	/** Returns the PartID of the first attachment whose filename ends with {@code .pdf}, or {@code null} if none. */
	@Nullable
	private static String findPdfAttachmentPartId(@NonNull final JsonNode message)
	{
		final JsonNode attachments = message.path("Attachments");
		if (attachments.isArray())
		{
			for (final JsonNode attachment : attachments)
			{
				final String filename = attachment.path("FileName").asText("");
				if (filename.toLowerCase().endsWith(".pdf"))
				{
					return attachment.path("PartID").asText(null);
				}
			}
		}
		return null;
	}

	private String pollForLatestMessageId()
	{
		for (int attempt = 0; attempt < POLL_ATTEMPTS; attempt++)
		{
			final JsonNode messages = parse(httpGet(apiBaseUrl() + "/api/v1/messages")).path("messages");
			if (messages.isArray() && messages.size() > 0)
			{
				// Mailpit returns messages newest-first; the first element is the latest.
				return messages.get(0).path("ID").asText();
			}
			sleep();
		}
		throw new AdempiereException("Mailpit received no message within "
				+ (POLL_ATTEMPTS * POLL_INTERVAL_MILLIS) + "ms (api=" + apiBaseUrl() + ")");
	}

	private static void sleep()
	{
		try
		{
			Thread.sleep(POLL_INTERVAL_MILLIS);
		}
		catch (final InterruptedException e)
		{
			Thread.currentThread().interrupt();
			throw new AdempiereException("Interrupted while polling Mailpit", e);
		}
	}

	private JsonNode parse(@NonNull final String json)
	{
		try
		{
			return objectMapper.readTree(json);
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Cannot parse Mailpit JSON response: " + json, e);
		}
	}

	private static String httpGet(@NonNull final String url)
	{
		return new String(httpGetBytes(url), StandardCharsets.UTF_8);
	}

	private static byte[] httpGetBytes(@NonNull final String url)
	{
		return httpRequest("GET", url);
	}

	private static byte[] httpRequest(@NonNull final String method, @NonNull final String url)
	{
		HttpURLConnection connection = null;
		try
		{
			connection = (HttpURLConnection)new URL(url).openConnection();
			connection.setRequestMethod(method);
			connection.setConnectTimeout(5000);
			connection.setReadTimeout(5000);

			final int responseCode = connection.getResponseCode();
			if (responseCode < 200 || responseCode >= 300)
			{
				throw new AdempiereException("Mailpit " + method + " " + url + " returned HTTP " + responseCode);
			}

			try (final InputStream in = connection.getInputStream())
			{
				return readAllBytes(in);
			}
		}
		catch (final AdempiereException e)
		{
			throw e;
		}
		catch (final Exception e)
		{
			throw new AdempiereException("Mailpit " + method + " " + url + " failed", e);
		}
		finally
		{
			if (connection != null)
			{
				connection.disconnect();
			}
		}
	}

	private static byte[] readAllBytes(@NonNull final InputStream in) throws java.io.IOException
	{
		final ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		final byte[] chunk = new byte[8192];
		int read;
		while ((read = in.read(chunk)) != -1)
		{
			buffer.write(chunk, 0, read);
		}
		return buffer.toByteArray();
	}

	/**
	 * Thin subclass exposing {@link ZUGFeRDValidator}'s protected {@code context} field after
	 * {@code validate(...)} — avoids reflection. Mirrors the pattern used in ZugferdAssemblerTest.
	 */
	private static final class InspectableZUGFeRDValidator extends ZUGFeRDValidator
	{
		@Nullable
		ValidationContext getValidationContext()
		{
			return context;
		}
	}
}
