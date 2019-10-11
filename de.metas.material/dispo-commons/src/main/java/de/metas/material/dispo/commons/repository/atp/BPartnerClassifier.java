package de.metas.material.dispo.commons.repository.atp;

import javax.annotation.Nullable;

import org.adempiere.exceptions.AdempiereException;

import de.metas.bpartner.BPartnerId;
import lombok.EqualsAndHashCode;
import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * metasfresh-material-dispo-commons
 * %%
 * Copyright (C) 2019 metas GmbH
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

@EqualsAndHashCode(doNotUseGetters = true)
@ToString(doNotUseGetters = true)
public final class BPartnerClassifier
{
	public static BPartnerClassifier any()
	{
		return ANY;
	}

	public static BPartnerClassifier none()
	{
		return NONE;
	}

	public static BPartnerClassifier specific(@NonNull final BPartnerId bpartnerId)
	{
		return new BPartnerClassifier(BPartnerClassifierType.SPECIFIC, bpartnerId);
	}

	public static BPartnerClassifier specificOrNone(@Nullable final BPartnerId bpartnerId)
	{
		return bpartnerId != null ? specific(bpartnerId) : none();
	}

	public static BPartnerClassifier specificOrAny(@Nullable final BPartnerId bpartnerId)
	{
		return bpartnerId != null ? specific(bpartnerId) : any();
	}

	private static final BPartnerClassifier ANY = new BPartnerClassifier(BPartnerClassifierType.ANY, null);
	private static final BPartnerClassifier NONE = new BPartnerClassifier(BPartnerClassifierType.NONE, null);

	private enum BPartnerClassifierType
	{
		ANY, NONE, SPECIFIC
	}

	private final BPartnerClassifierType type;
	private final BPartnerId bpartnerId;

	private BPartnerClassifier(@NonNull final BPartnerClassifierType type, final BPartnerId bpartnerId)
	{
		this.type = type;
		this.bpartnerId = bpartnerId;
	}

	public boolean isAny()
	{
		return type == BPartnerClassifierType.ANY;
	}

	public boolean isNone()
	{
		return type == BPartnerClassifierType.NONE;
	}

	public boolean isSpecificBPartner()
	{
		return type == BPartnerClassifierType.SPECIFIC;
	}

	public BPartnerId getBpartnerId()
	{
		if (type != BPartnerClassifierType.SPECIFIC)
		{
			throw new AdempiereException("Only specific types have bpartner ID but not " + this);
		}
		return bpartnerId;
	}

	public boolean isMatching(@NonNull final BPartnerClassifier other)
	{
		if (isAny())
		{
			return true;
		}
		else if (isNone())
		{
			return other.isAny() || other.isNone();
		}
		else if (isSpecificBPartner())
		{
			return other.isAny()
					|| this.equals(other);
		}
		else
		{
			throw new IllegalStateException("Case not handled: this=" + this + ", other=" + other);
		}
	}

}
