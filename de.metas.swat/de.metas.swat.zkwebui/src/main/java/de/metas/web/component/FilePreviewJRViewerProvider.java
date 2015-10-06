package de.metas.web.component;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import net.sf.jasperreports.engine.JRException;

import org.compiere.process.ProcessInfo;
import org.compiere.report.JRViewerProvider;

import de.metas.adempiere.report.jasper.OutputType;

/**
 * Jasper Report report preview which is opening a new browser window.
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03874_Warenauftrag_mit_Druckvorschau_%282013020810000055%29
 * 
 */
public class FilePreviewJRViewerProvider implements JRViewerProvider
{

	@Override
	public void openViewer(final byte[] data, final OutputType type, final String title, final ProcessInfo pi) throws JRException
	{
		final String contentType = "application/pdf"; // since getDesiredOutputType() is returning PDF, we can assume this is a PDF file
		final String filename = (title == null ? "report" : title) + ".pdf";
		FilePreviewWindow.openNewWindow(data, contentType, filename);
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return OutputType.PDF;
	}

}
