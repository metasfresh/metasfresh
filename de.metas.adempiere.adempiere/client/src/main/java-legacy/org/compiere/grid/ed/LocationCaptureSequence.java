package org.compiere.grid.ed;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.StringTokenizer;

import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import com.google.common.collect.ImmutableMap;

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

/**
 * Immutable object for parsing and storing the Location capture sequence.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class LocationCaptureSequence
{
	public static LocationCaptureSequence fromString(final String captureSequenceStr)
	{
		return new LocationCaptureSequence(captureSequenceStr);
	}

	public static final String PART_Country = "CO";
	public static final String PART_Region = "R";
	public static final String PART_City = "C";
	public static final String PART_Address1 = "A1";
	public static final String PART_Address2 = "A2";
	public static final String PART_Address3 = "A3";
	public static final String PART_Address4 = "A4";
	public static final String PART_Postal = "P";
	public static final String PART_PostalAdd = "A";

	private final ImmutableMap<String, Part> parts;
	private Integer _hashcode = null;

	private LocationCaptureSequence(final String captureSequenceStr)
	{
		super();
		Check.assumeNotNull(captureSequenceStr, "captureSequenceStr not null");

		final LinkedHashMap<String, Part> parts = new LinkedHashMap<>();
		int index = 0;

		final StringTokenizer st = new StringTokenizer(captureSequenceStr, "@", false);
		while (st.hasMoreTokens())
		{
			final String s = st.nextToken();
			if (s.startsWith("CO"))
			{
				final boolean mandatory = true; // hardcoded: always mandatory
				parts.put(PART_Country, new Part(PART_Country, index++, mandatory));
			}
			else if (s.startsWith("A1"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Address1, new Part(PART_Address1, index++, mandatory));
			}
			else if (s.startsWith("A2"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Address2, new Part(PART_Address2, index++, mandatory));
			}
			else if (s.startsWith("A3"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Address3, new Part(PART_Address3, index++, mandatory));
			}
			else if (s.startsWith("A4"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Address4, new Part(PART_Address4, index++, mandatory));
			}
			else if (s.startsWith("C"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_City, new Part(PART_City, index++, mandatory));
			}
			else if (s.startsWith("P"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Postal, new Part(PART_Postal, index++, mandatory));
			}
			else if (s.startsWith("A"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_PostalAdd, new Part(PART_PostalAdd, index++, mandatory));
			}
			else if (s.startsWith("R"))
			{
				final boolean mandatory = s.endsWith("!");
				parts.put(PART_Region, new Part(PART_Region, index++, mandatory));
			}
		}

		//
		// Set
		this.parts = ImmutableMap.copyOf(parts);
	}

	@Override
	public int hashCode()
	{
		if (_hashcode == null)
		{
			_hashcode = new HashcodeBuilder()
					.append(parts)
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

		final LocationCaptureSequence other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(parts, other.parts)
				.isEqual();
	}

	public final Collection<Part> getParts()
	{
		return parts.values();
	}

	public boolean hasPart(final String partName)
	{
		return parts.containsKey(partName);
	}

	public boolean isPartMandatory(final String partName)
	{
		final Part part = parts.get(partName);
		return part != null && part.isMandatory();
	}

	/** Location capture part */
	public static final class Part
	{
		private final String name;
		private final int index;
		private final boolean mandatory;

		private Integer _hashcode;

		private Part(final String name, final int index, final boolean mandatory)
		{
			super();
			this.name = name;
			this.index = index;
			this.mandatory = mandatory;
		}

		@Override
		public String toString()
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(name);
			if (mandatory)
			{
				sb.append("!");
			}
			return sb.toString();
		}

		@Override
		public int hashCode()
		{
			if (_hashcode == null)
			{
				_hashcode = new HashcodeBuilder()
						.append(name)
						.append(index)
						.append(mandatory)
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

			final Part other = EqualsBuilder.getOther(this, obj);
			if (other == null)
			{
				return false;
			}

			return new EqualsBuilder()
					.append(name, other.name)
					.append(index, other.index)
					.append(mandatory, other.mandatory)
					.isEqual();
		}

		public String getName()
		{
			return name;
		}

		public boolean isMandatory()
		{
			return mandatory;
		}

		public int getIndex()
		{
			return index;
		}
	}
}
