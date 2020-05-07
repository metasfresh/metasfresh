package org.compiere.apps.search;

import java.util.List;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/*package*/final class UserQuery implements IUserQuery
{
	public static final UserQuery of(final int id, final String caption, final int adUserId, final List<IUserQueryRestriction> segments)
	{
		return new UserQuery(id, caption, adUserId, segments);
	}

	private final int id;
	private final int adUserId;
	private final ITranslatableString caption;
	private final List<IUserQueryRestriction> restrictions;

	private UserQuery(final int id, final String caption, final int adUserId, final List<IUserQueryRestriction> restrictions)
	{
		super();
		this.id = id;
		this.adUserId = adUserId;
		this.caption = ImmutableTranslatableString.constant(caption);
		this.restrictions = restrictions;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("caption", caption)
				.add("adUserId", adUserId)
				.add("restrictions", restrictions)
				.toString();
	}

	@Override
	public int getId()
	{
		return id;
	}

	@Override
	public ITranslatableString getCaption()
	{
		return caption;
	}

	@Override
	public int getAD_User_ID()
	{
		return adUserId;
	}

	@Override
	public List<IUserQueryRestriction> getRestrictions()
	{
		return restrictions;
	}
}
