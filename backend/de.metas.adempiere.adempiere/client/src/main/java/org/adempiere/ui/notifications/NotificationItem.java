package org.adempiere.ui.notifications;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import org.adempiere.util.lang.ObjectUtils;

class NotificationItem
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final String summary;
	private final String detail;
	private final String senderName;

	private NotificationItem(final Builder builder)
	{
		super();
		summary = builder.summary;
		detail = builder.detail;
		senderName = builder.senderName;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	/** @return summary HTML */
	public String getSummary()
	{
		return summary;
	}

	/** @return detail HTML */
	public String getDetail()
	{
		return detail;
	}

	public String getSenderName()
	{
		return senderName;
	}

	public static final class Builder
	{
		private String summary;
		private String detail;
		private String senderName;

		private Builder()
		{
			super();
		}

		public NotificationItem build()
		{
			return new NotificationItem(this);
		}

		/**
		 * @param summary detail HTML text
		 */
		public Builder setSummary(final String summary)
		{
			this.summary = summary;
			return this;
		}

		/**
		 * @param detail detail HTML text
		 */
		public Builder setDetail(String detail)
		{
			this.detail = detail;
			return this;
		}

		public Builder setSenderName(String senderName)
		{
			this.senderName = senderName;
			return this;
		}
	}
}
