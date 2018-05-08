package de.metas.marketing.model;

import java.util.List;

import lombok.Builder;
import lombok.Value;

/*
 * #%L
 * de.metas.marketing
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
public class ContactPerson
{
	Campaign list;

	String name;

	/** might be <= 0 */
	int adUserId;

	/** might be <= 0 */
	int cBpartnerId;

	/** Doesn't make sense to be empty; a contact person needs to have some means of contacting them. */
	ContactAddress address;

	/** may be empty */
	List<Label> labels;

	int repoId;
}
