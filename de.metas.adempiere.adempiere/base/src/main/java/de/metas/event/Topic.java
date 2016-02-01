package de.metas.event;

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


import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;
import org.adempiere.util.lang.ObjectUtils;
import org.adempiere.util.text.annotation.ToStringBuilder;

/**
 *
 * @see IEventBusFactory#getEventBus(Topic)
 * @see Type
 */
public class Topic
{
	public static final Topic of(final String name, Type type)
	{
		return builder()
				.setName(name)
				.setType(type)
				.build();
	}

	public static final Builder builder()
	{
		return new Builder();
	}

	private final String name;
	private final Type type;
	private final String fullName;
	@ToStringBuilder(skip = true)
	private Integer _hashcode;

	private Topic(final Builder builder)
	{
		super();

		Check.assumeNotEmpty(builder.name, "builder.name not empty");
		name = builder.name;

		Check.assumeNotNull(builder.type, "builder.type not null");
		type = builder.type;

		fullName = type + "." + name;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = new HashcodeBuilder()
					.append(name)
					.append(type)
					.toHashcode();
		}
		return _hashcode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final Topic other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}
		return new EqualsBuilder()
				.append(name, other.name)
				.append(type, other.type)
				.isEqual();
	}

	public String getName()
	{
		return name;
	}

	public Type getType()
	{
		return type;
	}

	/**
	 * Returns a string representation that consists of the type and the actual name of this topic.
	 *
	 * @return
	 */
	public String getFullName()
	{
		return fullName;
	}

	public static final class Builder
	{
		private String name;
		private Type type = Type.REMOTE;

		public Builder setName(final String name)
		{
			this.name = name;
			return this;
		}

		/**
		 * Set the topic's type. If omitted, the type will be {@link Type#REMOTE}.
		 *
		 * @param type
		 * @return
		 */
		public Builder setType(final Type type)
		{
			this.type = type;
			return this;
		}

		public Topic build()
		{
			return new Topic(this);
		}
	}
}
