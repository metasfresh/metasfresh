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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.compiere.util.Ini.IsNotSwingClient;
import org.springframework.context.annotation.Conditional;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Writer;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import de.metas.adempiere.report.jasper.JasperServerConstants;

@Conditional(IsNotSwingClient.class)
@WebServlet(value = JasperServerConstants.SERVLET_ROOT + "/BarcodeServlet", loadOnStartup = 1)
public class BarcodeServlet extends HttpServlet
{
	/**
	 *
	 */
	private static final long serialVersionUID = -2265392707215362607L;

	// constant parameter names
	private final String CONTENT = "Content";
	private final String FORMAT = "Format";
	private final String WIDTH = "Width";
	private final String HEIGHT = "Height";
	private final String ECL = "ecl"; // error correction level

	// Parameter values
	private String m_content;
	// private String m_ecl; // error correction level
	private BarcodeFormat m_format;
	private int m_width;
	private int m_height;
	Hashtable<EncodeHintType, ErrorCorrectionLevel> m_hints;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		doPost(req, resp);
	}

	// Test URL:
	// http://localhost:8080/adempiereJasper/BarcodeServlet?Content=Test&Format=QR_Code&Width=75&Height=75

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
	{
		// Get parameters and check for wrong values
		try
		{
			getParameters(req);
		}
		catch (IllegalArgumentException e)
		{
			throw new ServletException(e);
		}

		// Encode given content into given format
		Writer encoder = new MultiFormatWriter();
		BitMatrix matrix = null;
		try
		{
			matrix = encoder.encode(m_content, m_format, m_width, m_height, m_hints);
		}
		catch (WriterException e)
		{
			throw new ServletException(e);
		}

		// Create image from BitMatrix
		BufferedImage image = MatrixToImageWriter.toBufferedImage(matrix);

		// Convert BufferedImage into png and write it into the stream
		final OutputStream out = resp.getOutputStream(); // Get stream
		ImageIO.write(image, "png", out);
		out.close();
	}

	/**
	 * Gets all Parameters and checks if they're valid.
	 * <ul>
	 * <li>Content is not allowed to be empty or <code>null</code>
	 * <li>Format is not allowed to be <code>null</code> and has to be a format that is supported by zxing
	 * <li>Width and Height have to be positive integers. If <code>null</code>, they're set to 0
	 * <li>ecl (error correction level) is only for QR_Code and can be <code>null</code> or empty<br>
	 * </ul>
	 * <br>
	 * If a parameter is not valid, an exception is thrown.
	 *
	 * @param req
	 * @throws IllegalArgumentException
	 */
	private void getParameters(HttpServletRequest req) throws IllegalArgumentException
	{
		String value = null;

		// Get Content
		value = req.getParameter(CONTENT);
		if (value == null || value.equals(""))
			throw new IllegalArgumentException();
		m_content = value;

		// Get format
		value = req.getParameter(FORMAT);
		m_format = BarcodeFormat.valueOf(value); // throws an exception when value is empty or wrong

		// Get Width and Height
		try
		{
			value = req.getParameter(WIDTH);
			if (value == null)
				m_width = 0;
			else
				m_width = Integer.parseInt(value);

			value = req.getParameter(HEIGHT);
			if (value == null)
				m_height = 0;
			else
				m_height = Integer.parseInt(value);
		}
		catch (NumberFormatException e)
		{
			throw new IllegalArgumentException(e);
		}

		// Hints
		m_hints = new Hashtable<EncodeHintType, ErrorCorrectionLevel>();

		// get error correction level
		if (m_format == BarcodeFormat.QR_CODE)
		{
			value = req.getParameter(ECL);
			if (value != null && !value.equals(""))
			{
				ErrorCorrectionLevel ecl = null;
				for (int i = 0; i <= 3; i++)
				{
					ecl = ErrorCorrectionLevel.forBits(i);
					if (ecl.toString().equals(value))
					{
						break;
					}
				}
				m_hints.put(EncodeHintType.ERROR_CORRECTION, ecl);
			}
		}
	}
}
