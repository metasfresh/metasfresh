package de.metas.notification.spi;

import org.adempiere.util.lang.ITableRecordReference;

import com.google.common.base.Optional;

/*
 * #%L
 * de.metas.printing.base
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

public interface INotificationCtxProvider
{
	/**
	 * Gets the text message that will get into {@code I_AD_Note} as <code>TextMsg</code>.
	 * 
	 * @param referencedRecord
	 * @return text message or {@link Optional#absent()} if it does not apply.
	 */
	public Optional<String> getTextMessageIfApplies(ITableRecordReference referencedRecord);
}
