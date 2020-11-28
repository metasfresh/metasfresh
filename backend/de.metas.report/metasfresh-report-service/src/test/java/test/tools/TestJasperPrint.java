package test.tools;

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


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.net.URL;

import org.junit.Ignore;

import de.metas.report.server.OutputType;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

@Ignore
public class TestJasperPrint
{

	private static InputStream getJasperPrintStreamFromLocalServer() throws Exception
	{
		String jrServlet = "http://localhost:8180/adempiereJasper/ReportServlet";
		int AD_Process_ID = 540096;
		int AD_PInstance_ID = 3663122;
		String adLanguage = "de_DE";
		Object outputType = OutputType.JasperPrint;
		String urlStr = jrServlet + "?"
				+ "AD_Process_ID=" + AD_Process_ID
				+ "&AD_PInstance_ID=" + AD_PInstance_ID
				+ "&AD_Language=" + adLanguage
				+ "&output=" + outputType.toString();
		
		urlStr = "http://localhost:8180/adempiereJasper/ReportServlet?AD_Process_ID=540096&AD_PInstance_ID=3663130&AD_Language=en_US&output=JasperPrint";

		System.out.println("Calling URL " + urlStr);

		final InputStream in = new URL(urlStr).openStream();
		return in;
	}

	private static JasperPrint getJasperPrint(InputStream in) throws Exception
	{
		final ByteArrayOutputStream out = new ByteArrayOutputStream();

		final byte[] buf = new byte[4096];

		int len = -1;
		while ((len = in.read(buf)) > 0)
		{
			out.write(buf, 0, len);
		}
		in.close();

		byte[] data = out.toByteArray();
		ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data));
		JasperPrint jasperPrint = (JasperPrint)ois.readObject();
		System.out.println("Loaded jasperprint");
		writeBytes(new File("d:/test.jasperprint"), data);

		return jasperPrint;
	}
	
	private static JasperPrint getJasperPrintFromFile(File file) throws Exception
	{
		FileInputStream in = new FileInputStream(file);
		JasperPrint jasperPrint = getJasperPrint(in);
		System.out.println("Loaded jasperprint from "+file);
		return jasperPrint;
	}

	protected static void exportPDF(JasperPrint jasperPrint) throws Exception
	{
		File file = new File("D:/test.pdf");
		System.out.println("Exporting PDF to " + file);
		{
			JRPdfExporter exporter = new JRPdfExporter();
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
			exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, outputStream);
			exporter.setParameter(JRExporterParameter.CHARACTER_ENCODING, "UTF8");
			// JRProperties.setProperty(JRFont.DEFAULT_PDF_FONT_NAME, "arial.ttf");
			exporter.exportReport();
			writeBytes(file, outputStream.toByteArray());
		}
	}

	private static void writeBytes(File file, byte[] data) throws Exception
	{
		final FileOutputStream out = new FileOutputStream(file);
		out.write(data);
		out.flush();
		out.close();
		System.out.println("Wrote " + file);
	}

	/**
	 * @param args
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception
	{
		File jpFile = new File("d:/test.jasperprint");
		
		final JasperPrint jasperPrint;
		if (jpFile.exists())
		{
			jasperPrint = getJasperPrintFromFile(jpFile);
		}
		else
		{
			final InputStream in = getJasperPrintStreamFromLocalServer();
			jasperPrint = getJasperPrint(in);
		}

		exportPDF(jasperPrint);

		System.out.println("Done.");
	}

}
