package de.metas.invoicecandidate.api;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.Check;
import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

/*
 * #%L
 * de.metas.swat.base
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
 * Immutable recompute tag used to tag invoice candidates.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class InvoiceCandRecomputeTag
{
	public static final InvoiceCandRecomputeTag NULL = new InvoiceCandRecomputeTag(-1);

	public static final InvoiceCandRecomputeTag ofAD_PInstance_ID(final int adPInstanceId)
	{
		Check.assume(adPInstanceId > 0, "adPInstanceId > 0");
		return new InvoiceCandRecomputeTag(adPInstanceId);
	}

	/**
	 * Creates an {@link InvoiceCandRecomputeTag} from a string created with {@link InvoiceCandRecomputeTag#asString()}.
	 * 
	 * @param recomputeTagStr
	 * @return
	 */
	public static final InvoiceCandRecomputeTag fromString(final String recomputeTagStr)
	{
		// NOTE: keep in sync with toString()
		try
		{
			final int adPInstanceId = Integer.parseInt(recomputeTagStr);
			return ofAD_PInstance_ID(adPInstanceId);
		}
		catch (NumberFormatException e)
		{
			throw new AdempiereException("Failed parsing the tag for string: " + recomputeTagStr, e);
		}
	}

	public static final boolean isNull(final InvoiceCandRecomputeTag recomputeTag)
	{
		return recomputeTag == null || recomputeTag == NULL;
	}

	/**
	 * @param recomputeTag
	 * @return AD_PInstance_ID or null if the tag {@link #isNull(InvoiceCandRecomputeTag)}.
	 */
	public static final Integer getAD_PInstance_ID_OrNull(final InvoiceCandRecomputeTag recomputeTag)
	{
		if (isNull(recomputeTag))
		{
			return null;
		}
		return recomputeTag.getAD_PInstance_ID();
	}

	private final int adPInstanceId;
	private Integer _hashCode;

	private InvoiceCandRecomputeTag(final int adPInstanceId)
	{
		super();
		this.adPInstanceId = adPInstanceId;
	}

	@Override
	public final String toString()
	{
		return asString();
	}

	/**
	 * @return tag as string
	 * @see #fromString(String)
	 */
	public final String asString()
	{
		// NOTE: keep in sync with fromString()
		return String.valueOf(adPInstanceId);
	}

	@Override
	public int hashCode()
	{
		if (_hashCode == null)
		{
			_hashCode = new HashcodeBuilder()
					.append(adPInstanceId)
					.toHashcode();
		}
		return _hashCode;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final InvoiceCandRecomputeTag other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(adPInstanceId, other.adPInstanceId)
				.isEqual();
	}

	public int getAD_PInstance_ID()
	{
		if (adPInstanceId <= 0)
		{
			throw new AdempiereException("Getting AD_PInstance_ID is not allowed for null/empty tags");
		}
		return adPInstanceId;
	}
}
