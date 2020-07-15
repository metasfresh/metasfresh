package de.metas.i18n;

import java.util.Optional;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Properties;
import java.util.function.Consumer;

import org.compiere.model.I_AD_Message;
import org.compiere.util.Env;

import de.metas.util.ISingletonService;

public interface IADMessageDAO extends ISingletonService
{
	Optional<I_AD_Message> retrieveByValue(Properties ctx, AdMessageKey value);

	Optional<I_AD_Message> retrieveById(Properties ctx, AdMessageId adMessageId);

	default Optional<AdMessageKey> retrieveValueById(final AdMessageId adMessageId)
	{
		return retrieveById(Env.getCtx(), adMessageId)
				.map(message -> AdMessageKey.of(message.getValue()));
	}

	/**
	 * Gets AD_Message_ID
	 * 
	 * @param ctx
	 * @param value AD_Message.Value
	 * @return AD_Message_ID
	 */
	Optional<AdMessageId> retrieveIdByValue(Properties ctx, AdMessageKey value);

	default Optional<AdMessageId> retrieveIdByValue(final AdMessageKey value)
	{
		return retrieveIdByValue(Env.getCtx(), value);
	}

	boolean isMessageExists(AdMessageKey adMessage);

	void createUpdateMessage(AdMessageKey adMessage, Consumer<I_AD_Message> adMessageUpdater);
}
