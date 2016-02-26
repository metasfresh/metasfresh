package de.metas.notification.spi;

import org.adempiere.util.lang.ITableRecordReference;

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
	 * This method will validate if a given reference is suitable for the context provider.
	 * It can be implemented for any notification particular case, where extra info about the objects that were to be printed is needed.
	 * 
	 * @param referencedRecord
	 * @return true if the referenceRecord fits the filter, false otherwise
	 */
	boolean appliesFor(ITableRecordReference referencedRecord);

	/**
	 * The text message that will get into {@code I_AD_Note} as <code>TextMsg</code>
	 * 
	 * @param referencedRecord
	 * @return
	 */
	public String getTextMessageOrNull(ITableRecordReference referencedRecord);

	/**
	 * 
	 * THis method is created for flagging a context provider as default (to be used in case there is no other more particular context provider for the notification)
	 * 
	 * @return true if the context provider was flagged as default, false for the rest of the cases
	 */
	public boolean isDefault();

}
