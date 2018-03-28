package de.metas.adempiere.report.jasper.servlet;

/*
 * #%L
 * de.metas.report.jasper.server.base
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import de.metas.Profiles;
import de.metas.adempiere.report.jasper.JasperServerConstants;

@RestController
@RequestMapping(value = BarcodeRestController.ENDPOINT)
@Profile(Profiles.PROFILE_JasperService)
public class BarcodeRestController
{
	public static final String ENDPOINT = JasperServerConstants.SERVLET_ROOT + "/BarcodeServlet";

	// constant parameter names
	private static final String CONTENT = "Content";
	private static final String FORMAT = "Format";
	private static final String WIDTH = "Width";
	private static final String HEIGHT = "Height";
	private static final String ECL = "ecl"; // error correction level

	// Test URL:
	// http://localhost:8080/adempiereJasper/BarcodeServlet?Content=Test&Format=QR_Code&Width=75&Height=75

	@GetMapping
	public ResponseEntity<byte[]> getBarcode( //
			@RequestParam(name = CONTENT) final String content //
			, @RequestParam(name = FORMAT) final String formatStr//
			, @RequestParam(name = WIDTH, required = false) final int width//
			, @RequestParam(name = HEIGHT, required = false) final int height//
			, @RequestParam(name = ECL, required = false) final String eclStr//
	)
	{
		//
		// Get parameters and check for wrong values
		final BarcodeFormat format = BarcodeFormat.valueOf(formatStr);
		final Map<EncodeHintType, ErrorCorrectionLevel> hints = extractHints(eclStr, format);

		//
		// Encode given content into given format
		final BitMatrix matrix;
		try
		{
			final Writer encoder = new MultiFormatWriter();
			matrix = encoder.encode(content, format, width, height, hints);
		}
		catch (final WriterException ex)
		{
			throw new AdempiereException("Failed creating barcode", ex);
		}

		final String barcodeFilename = "barcode.png";
		final byte[] barcodeData = toByteArray(matrix, "png");

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.IMAGE_PNG);
		headers.set(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + barcodeFilename + "\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		final ResponseEntity<byte[]> response = new ResponseEntity<>(barcodeData, headers, HttpStatus.OK);
		return response;
	}

	private static Map<EncodeHintType, ErrorCorrectionLevel> extractHints(final String eclStr, final BarcodeFormat format)
	{
		// Hints
		final Map<EncodeHintType, ErrorCorrectionLevel> hints = new HashMap<>();

		// get error correction level
		if (format == BarcodeFormat.QR_CODE)
		{
			if (!Check.isEmpty(eclStr, true))
			{
				ErrorCorrectionLevel ecl = null;
				for (int i = 0; i <= 3; i++)
				{
					ecl = ErrorCorrectionLevel.forBits(i);
					if (ecl.toString().equals(eclStr))
					{
						break;
					}
				}
				hints.put(EncodeHintType.ERROR_CORRECTION, ecl);
			}
		}

		return hints;
	}

	private static byte[] toByteArray(final BitMatrix matrix, final String imageFormat)
	{
		//
		// Create image from BitMatrix
		final BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

		// Convert BufferedImage to imageFormat and write it into the stream
		try
		{
			final ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write(image, imageFormat, out);
			return out.toByteArray();
		}
		catch (final IOException ex)
		{
			throw new AdempiereException("Failed creating " + imageFormat + " image", ex);
		}

	}
}
