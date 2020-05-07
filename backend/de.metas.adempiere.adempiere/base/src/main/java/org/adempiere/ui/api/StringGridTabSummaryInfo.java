package org.adempiere.ui.api;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Properties;

import org.adempiere.util.Services;

import de.metas.i18n.IMsgBL;

/**
 * Immutable implementation of {@link IGridTabSummaryInfo} which wraps a {@link String}.
 * 
 * @author tsa
 *
 */
public final class StringGridTabSummaryInfo implements IGridTabSummaryInfo
{
	private static final long serialVersionUID = 1L;

	private final String message;
	private final boolean translated;

	/**
	 *
	 * @param message
	 * @param translated true if the message is already translated
	 */
	public StringGridTabSummaryInfo(final String message, final boolean translated)
	{
		super();
		this.message = message;
		this.translated = translated;
	}

	@Override
	public String getSummaryMessageTranslated(final Properties ctx)
	{
		if (translated)
		{
			return message;
		}

		return Services.get(IMsgBL.class).parseTranslation(ctx, message);
	}

}
