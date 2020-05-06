/**
 * 
 */
package de.metas.report.server;

/*
 * #%L
 * de.metas.report.jasper.commons
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


/**
 * Jasper report output type
 * 
 * @author tsa
 * 
 */
public enum OutputType
{
	PDF("application/pdf", false, "pdf"),
	HTML("text/html", false, "html"),
	XML("text/xml", false, "xml"),
	JasperPrint("application/octet-stream", false, "jasper"),
	XLS("application/vnd.ms-excel", true, "xls"),
	CSV("text/csv", true, "csv");

	private final String contentType;
	private final boolean tabular;
	private final String fileExtension;

	OutputType(final String contentType, final boolean tabular, final String fileExtension)
	{
		this.contentType = contentType;
		this.tabular = tabular;
		this.fileExtension = fileExtension;
	}

	/** @return MIME type */
	public String getContentType()
	{
		return contentType;
	}

	/** @return true if the format is about displaying tabular data (e.g. Excel, CSV etc). */
	public boolean isTabular()
	{
		return tabular;
	}

	/** @return file extension to be used for this format, without dot (e.g. xls, html, csv etc) */
	public String getFileExtension()
	{
		return fileExtension;
	}
}
