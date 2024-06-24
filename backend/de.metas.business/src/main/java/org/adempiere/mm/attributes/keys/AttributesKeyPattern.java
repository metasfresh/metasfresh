/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2023 metas GmbH
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

package org.adempiere.mm.attributes.keys;

import com.google.common.base.MoreObjects;
import com.google.common.base.MoreObjects.ToStringHelper;
import com.google.common.collect.ImmutableList;
import de.metas.material.event.commons.AttributesKey;
import de.metas.material.event.commons.AttributesKeyPart;
import de.metas.util.Check;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.mm.attributes.AttributeId;

import java.util.Collection;

@EqualsAndHashCode(doNotUseGetters = true)
public final class AttributesKeyPattern
{
	public static final AttributesKeyPattern ALL = new AttributesKeyPattern(AttributesKeyPartPattern.ALL);
	public static final AttributesKeyPattern OTHER = new AttributesKeyPattern(AttributesKeyPartPattern.OTHER);
	public static final AttributesKeyPattern NONE = new AttributesKeyPattern(AttributesKeyPartPattern.NONE);

	public static AttributesKeyPattern ofPart(@NonNull final AttributesKeyPartPattern partPattern)
	{
		if (AttributesKeyPartPattern.ALL.equals(partPattern))
		{
			return ALL;
		}
		else if (AttributesKeyPartPattern.OTHER.equals(partPattern))
		{
			return OTHER;
		}
		else if (AttributesKeyPartPattern.NONE.equals(partPattern))
		{
			return NONE;
		}
		else
		{
			return new AttributesKeyPattern(partPattern);
		}
	}

	public static AttributesKeyPattern ofParts(@NonNull final Collection<AttributesKeyPartPattern> partPatterns)
	{
		return !partPatterns.isEmpty()
				? new AttributesKeyPattern(partPatterns)
				: NONE;
	}

	public static AttributesKeyPattern attributeId(@NonNull final AttributeId attributeId)
	{
		return ofPart(AttributesKeyPartPattern.ofAttributeId(attributeId));
	}

	@Getter
	private final ImmutableList<AttributesKeyPartPattern> partPatterns;

	private String _sqlLikeString; // lazy

	private AttributesKeyPattern(@NonNull final Collection<AttributesKeyPartPattern> partPatterns)
	{
		Check.assumeNotEmpty(partPatterns, "partPatterns is not empty");

		this.partPatterns = partPatterns.stream()
				.sorted()
				.collect(ImmutableList.toImmutableList());
	}

	private AttributesKeyPattern(@NonNull final AttributesKeyPartPattern partPattern)
	{
		this.partPatterns = ImmutableList.of(partPattern);
	}

	@Override
	@Deprecated
	public String toString()
	{
		final ToStringHelper builder = MoreObjects.toStringHelper(this)
				.omitNullValues();
		
		if(isAll())
		{
			builder.addValue("ALL");
		}
		else if(isOther())
		{
			builder.addValue("OTHERS");
		}
		else if(isNone())
		{
			builder.addValue("NONE");
		}
		else //
		{
			builder.addValue(partPatterns);
		}
		
		return builder.toString();
	}

	public boolean isAll()
	{
		return ALL.equals(this);
	}

	public boolean isOther()
	{
		return OTHER.equals(this);
	}

	private boolean isNone()
	{
		return NONE.equals(this);
	}

	public boolean isSpecific()
	{
		return !isAll() && !isOther() && !isNone();
	}

	public String getSqlLikeString()
	{
		String sqlLikeString = _sqlLikeString;
		if (sqlLikeString == null)
		{
			sqlLikeString = _sqlLikeString = computeSqlLikeString();
		}
		return sqlLikeString;
	}

	private String computeSqlLikeString()
	{
		final StringBuilder sb = new StringBuilder();

		sb.append("%");
		boolean lastCharIsWildcard = true;

		for (final AttributesKeyPartPattern partPattern : partPatterns)
		{
			final String partSqlLike = partPattern.getSqlLikePart();
			if (lastCharIsWildcard)
			{
				if (partSqlLike.startsWith("%"))
				{
					sb.append(partSqlLike.substring(1));
				}
				else
				{
					sb.append(partSqlLike);
				}
			}
			else
			{
				if (partSqlLike.startsWith("%"))
				{
					sb.append(partSqlLike);
				}
				else
				{
					sb.append("%").append(partSqlLike);
				}
			}

			lastCharIsWildcard = partSqlLike.endsWith("%");
		}

		if (!lastCharIsWildcard)
		{
			sb.append("%");
		}

		return sb.toString();
	}

	public boolean matches(@NonNull final AttributesKey attributesKey)
	{
		for (final AttributesKeyPartPattern partPattern : partPatterns)
		{
			boolean partPatternMatched = false;

			if (AttributesKey.NONE.getAsString().equals(attributesKey.getAsString()))
			{
				partPatternMatched = partPattern.matches(AttributesKeyPart.NONE);
			}
			else
			{
				for (final AttributesKeyPart part : attributesKey.getParts())
				{
					if (partPattern.matches(part))
					{
						partPatternMatched = true;
						break;
					}
				}

			}

			if (!partPatternMatched)
			{
				return false;
			}
		}

		return true;
	}
}
