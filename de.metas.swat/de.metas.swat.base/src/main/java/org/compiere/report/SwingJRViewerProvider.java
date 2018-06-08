package org.compiere.report;

import java.io.File;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Services;
import org.compiere.print.JRReportViewerProvider;
import org.compiere.report.viewer.JasperReportViewerFrame;

import com.google.common.io.Files;

import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClientUtil;
import de.metas.process.ProcessInfo;

/*
 * #%L
 * de.metas.swat.base
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
import net.sf.jasperreports.engine.JasperPrint;

public class SwingJRViewerProvider implements JRReportViewerProvider
{
	@Override
	public void openViewer(final byte[] data, final OutputType type, final ProcessInfo pi) throws Exception
	{
		if (type == OutputType.JasperPrint)
		{
			openViewer_JasperPrint(data, pi);
		}
		else
		{
			final File file = File.createTempFile("report_", "." + type.getFileExtension());
			Files.write(data, file);
			Services.get(IClientUI.class).showURL(file.toURI().toString());
		}
	}

	public void openViewer_JasperPrint(final byte[] data, final ProcessInfo pi) throws JRException
	{
		final JasperPrint jasperPrint = JRClientUtil.toJasperPrint(data);

		//
		// If the jasper report has no pages, display this error right now and do nothing.
		// Else, the original JRViewer component will display the error several times which is very annoying for the user.
		if (jasperPrint == null
				|| jasperPrint.getPages() == null
				|| jasperPrint.getPages().size() == 0)
		{
			throw new AdempiereException("@NoPages@");
		}

		final JasperReportViewerFrame jasperViewer = new JasperReportViewerFrame(jasperPrint, pi.getTitle(), pi);
		jasperViewer.setExtendedState(jasperViewer.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
		jasperViewer.setVisible(true);
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return OutputType.JasperPrint;
	}

}
