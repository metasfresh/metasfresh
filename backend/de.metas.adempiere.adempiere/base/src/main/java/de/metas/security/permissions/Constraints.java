package de.metas.security.permissions;

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


import java.util.Collection;
import java.util.LinkedHashMap;

import org.compiere.util.Env;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import de.metas.util.Check;

/**
 * {@link Constraint}s collections.
 * 
 * @author tsa
 *
 */
public final class Constraints
{
	public static final Builder builder()
	{
		return new Builder();
	}

	private final ImmutableMap<Class<? extends Constraint>, Constraint> constraints;

	private Constraints(Builder builder)
	{
		super();
		constraints = builder.getConstraints();
	}

	@Override
	public String toString()
	{
		// NOTE: we are making it translateable friendly because it's displayed in Prefereces->Info->Rollen

		final String constraintsName = getClass().getSimpleName();
		final Collection<Constraint> constraintsList = constraints.values();

		final StringBuilder sb = new StringBuilder();
		sb.append(constraintsName).append(": ");
		if (constraintsList.isEmpty())
		{
			sb.append("@None@");
		}
		else
		{
			sb.append(Env.NL);
		}

		Joiner.on(Env.NL)
				.skipNulls()
				.appendTo(sb, constraintsList);

		return sb.toString();
	}

	public <T extends Constraint> Optional<T> getConstraint(final Class<T> constraintType)
	{
		@SuppressWarnings("unchecked")
		final T constraint = (T)constraints.get(constraintType);
		return Optional.fromNullable(constraint);
	}

	public static final class Builder
	{
		private final LinkedHashMap<Class<? extends Constraint>, Constraint> constraints = new LinkedHashMap<>();

		private Builder()
		{
			super();
		}

		public Constraints build()
		{
			return new Constraints(this);
		}

		private ImmutableMap<Class<? extends Constraint>, Constraint> getConstraints()
		{
			return ImmutableMap.copyOf(constraints);
		}

		public Builder addConstraint(final Constraint constraint)
		{
			Check.assumeNotNull(constraint, "constraint not null");
			constraints.put(constraint.getClass(), constraint);
			return this;
		}

		public Builder addConstraintIfNotEquals(final Constraint constraint, final Constraint constraintToExclude)
		{
			Check.assumeNotNull(constraint, "constraint not null");
			Check.assumeNotNull(constraintToExclude, "constraintToExclude not null");

			if (constraint.equals(constraintToExclude))
			{
				return this;
			}

			constraints.put(constraint.getClass(), constraint);
			return this;
		}

	}
}
