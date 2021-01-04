package de.metas.procurement.webui.exceptions;

import com.google.gwt.thirdparty.guava.common.html.HtmlEscapers;
import com.vaadin.server.ErrorMessage;

/*
 * #%L
 * de.metas.procurement.webui
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

public class LoginFailedException extends RuntimeException implements ErrorMessage
{
	private static final long serialVersionUID = 1L;

	public LoginFailedException(final String email)
	{
		super("Invalid user or password"); // TODO i18n
	}

	@Override
	public ErrorLevel getErrorLevel()
	{
		return ErrorLevel.ERROR;
	}

	@Override
	public String getFormattedHtmlMessage()
	{
		return HtmlEscapers.htmlEscaper().escape(getLocalizedMessage());
	}
}
