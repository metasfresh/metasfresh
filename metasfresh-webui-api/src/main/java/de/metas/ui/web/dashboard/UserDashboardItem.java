package de.metas.ui.web.dashboard;

import javax.annotation.concurrent.Immutable;

import org.adempiere.util.Check;

import com.google.common.base.MoreObjects;

import de.metas.i18n.ITranslatableString;
import de.metas.i18n.ImmutableTranslatableString;

/*
 * #%L
 * metasfresh-webui-api
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

@Immutable
public final class UserDashboardItem
{

	public static final Builder builder()
	{
		return new Builder();
	}

	private final int id;
	private final ITranslatableString caption;
	private final String url;
	private final int seqNo;

	private UserDashboardItem(final Builder builder)
	{
		super();
		id = builder.id;
		caption = builder.caption;
		url = builder.url;
		seqNo = builder.seqNo;
	}

	@Override
	public String toString()
	{
		return MoreObjects.toStringHelper(this)
				.add("id", id)
				.add("caption", caption)
				.add("url", url)
				.add("seqNo", seqNo)
				.toString();
	}
	
	public int getId()
	{
		return id;
	}

	public ITranslatableString getCaption()
	{
		return caption;
	}

	public String getUrl()
	{
		return url;
	}

	public int getSeqNo()
	{
		return seqNo;
	}

	public static final class Builder
	{
		private Integer id;
		private ITranslatableString caption = ImmutableTranslatableString.empty();
		private String url;
		private int seqNo;

		private Builder()
		{
			super();
		}

		public UserDashboardItem build()
		{
			Check.assumeNotNull(id, "Parameter id is not null");
			return new UserDashboardItem(this);
		}
		
		public Builder setId(final int id)
		{
			this.id = id;
			return this;
		}

		public Builder setCaption(final ITranslatableString caption)
		{
			Check.assumeNotNull(caption, "Parameter caption is not null");
			this.caption = caption;
			return this;
		}

		public Builder setCaption(final String caption)
		{
			this.caption = ImmutableTranslatableString.constant(caption);
			return this;
		}

		public Builder setUrl(final String url)
		{
			this.url = url;
			return this;
		}

		public Builder setSeqNo(final int seqNo)
		{
			this.seqNo = seqNo;
			return this;
		}
	}

}
