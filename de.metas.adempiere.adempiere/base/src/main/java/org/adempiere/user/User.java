package org.adempiere.user;

import javax.annotation.Nullable;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.Language;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Value
@Builder(toBuilder = true)
public class User
{
	/** can be null for not-yet-saved users */
	@Nullable
	UserId id;

	@Nullable
	BPartnerId bpartnerId;

	@NonNull
	String name;

	@Nullable
	String emailAddress;

	/**
	 * Changes are persisted by the repo!
	 */
	@Nullable
	Language userLanguage;

	/**
	 * Read-only; changes are <b>not</b> persisted by the repo!
	 */
	@Nullable
	Language bPartnerLanguage;

	/**
	 * Either the user's or bPartner's or context's or base language. Never {@code null}.
	 * Read-only; changes are <b>not</b> persisted by the repo!
	 */
	@NonNull
	Language language;
}
