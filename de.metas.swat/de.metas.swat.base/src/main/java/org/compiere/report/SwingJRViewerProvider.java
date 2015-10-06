package org.compiere.report;

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

import org.adempiere.exceptions.AdempiereException;
import org.compiere.process.ProcessInfo;

import de.metas.adempiere.report.jasper.OutputType;
import de.metas.adempiere.report.jasper.client.JRClientUtil;

public class SwingJRViewerProvider implements JRViewerProvider
{
	@Override
	public void openViewer(final byte[] data, final OutputType type, final String title, final ProcessInfo pi) throws JRException
	{
		if (type != OutputType.JasperPrint)
		{
			throw new IllegalArgumentException("type is not " + OutputType.JasperPrint);
		}
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
		
		JasperReportViewerFrame jasperViewer = new JasperReportViewerFrame(jasperPrint, title, pi);

		jasperViewer.setExtendedState(jasperViewer.getExtendedState() | javax.swing.JFrame.MAXIMIZED_BOTH);
		jasperViewer.setVisible(true);
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return OutputType.JasperPrint;
	}

}
