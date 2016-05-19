package de.metas.ui.web.vaadin.report;

import java.io.ByteArrayInputStream;

import org.compiere.process.ProcessInfo;
import org.compiere.report.JRViewerProvider;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.server.StreamResource;
import com.vaadin.ui.BrowserFrame;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;

import de.metas.adempiere.report.jasper.OutputType;
import net.sf.jasperreports.engine.JRException;

/*
 * #%L
 * metasfresh-webui
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class VaadinJRViewerProvider implements JRViewerProvider
{
	public static final transient VaadinJRViewerProvider instance = new VaadinJRViewerProvider();

	private static final String STYLE = "mf-report-window";

	private VaadinJRViewerProvider()
	{
		super();
	}

	@Override
	public void openViewer(final byte[] data, final OutputType type, final String title, final ProcessInfo pi) throws JRException
	{
		final StreamResource pdfResource = new StreamResource(() -> new ByteArrayInputStream(data), "report.pdf");

		final Window window = new Window();
		window.setCaption(title);
		
		window.setStyleName(STYLE);
		window.center();
		window.setWidth(800, Unit.PIXELS);
		window.setHeight(600, Unit.PIXELS);

		final BrowserFrame pdfComp = new BrowserFrame();
		pdfComp.setPrimaryStyleName(STYLE + "-content");
		pdfComp.setSizeFull();
		pdfComp.setSource(pdfResource);
		window.setContent(pdfComp);
		
		UI.getCurrent().addWindow(window);
	}

	@Override
	public OutputType getDesiredOutputType()
	{
		return OutputType.PDF;
	}

}
