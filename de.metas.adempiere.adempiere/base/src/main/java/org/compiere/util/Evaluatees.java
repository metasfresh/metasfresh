package org.compiere.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.util.Check;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;

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

/**
 * {@link Evaluatee} convenient factories.
 * 
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public final class Evaluatees
{
	private Evaluatees()
	{
		super();
	}

	public static final Evaluatee2 ofSingleton(final String variableName, final Object value)
	{
		return new SingletonEvaluatee(variableName, value);
	}

	public static final Evaluatee2 ofMap(final Map<String, ? extends Object> map)
	{
		return new MapEvaluatee(map);
	}

	public static final Evaluatee ofCtx(final Properties ctx, final int windowNo, final boolean onlyWindow)
	{
		return new EvaluateeCtx(ctx, windowNo, onlyWindow);
	}

	public static final Evaluatee2 compose(final Evaluatee... evaluatees)
	{
		Check.assumeNotEmpty(evaluatees, "evaluatees not empty");

		final CompositeEvaluatee composite = new CompositeEvaluatee(evaluatees[0]);
		for (int i = 1; i < evaluatees.length; i++)
		{
			composite.addEvaluatee(evaluatees[i]);
		}
		return composite;
	}

	/**
	 * Compose all evaluates which are not null
	 *
	 * @param evaluatees
	 * @return
	 */
	public static final Evaluatee2 composeNotNulls(final Evaluatee... evaluatees)
	{
		Check.assumeNotEmpty(evaluatees, "evaluatees not empty");

		CompositeEvaluatee composite = null;
		for (final Evaluatee evaluatee : evaluatees)
		{
			if (evaluatee == null)
			{
				continue;
			}

			if (composite == null)
			{
				composite = new CompositeEvaluatee(evaluatee);
			}
			else
			{
				composite.addEvaluatee(evaluatee);
			}
		}

		Check.assumeNotNull(composite, "At least one evaluatee shall be not null: {}", (Object)evaluatees);
		return composite;
	}

	/**
	 *
	 * @return a special instance that has no variables.
	 */
	public static final Evaluatee2 empty()
	{
		return EMPTY;
	}

	/**
	 * Empty
	 */
	private static final Evaluatee2 EMPTY = new Evaluatee2()
	{
		@Override
		public boolean has_Variable(final String variableName)
		{
			return false;
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return null;
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}
	};

	/**
	 * Map
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	private static final class MapEvaluatee implements Evaluatee2
	{
		private final Map<String, ? extends Object> map;

		private MapEvaluatee(final Map<String, ? extends Object> map)
		{
			super();
			Check.assumeNotNull(map, "map not null");
			this.map = map;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("map", map)
					.toString();
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			final Object value = map.get(variableName);
			return value == null ? null : value.toString();
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return map.containsKey(variableName);
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}
	}

	/**
	 * Wraps a given {@link Properties} context to {@link Evaluatee}
	 *
	 * @author tsa
	 *
	 */
	private static final class EvaluateeCtx implements Evaluatee
	{
		private final Properties ctx;
		private final int windowNo;
		private final boolean onlyWindow;

		/* package */ EvaluateeCtx(final Properties ctx, final int windowNo, final boolean onlyWindow)
		{
			Check.assumeNotNull(ctx, "ctx not null");
			this.ctx = ctx;
			this.windowNo = windowNo;
			this.onlyWindow = onlyWindow;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("windowNo", windowNo)
					.add("onlyWindow", onlyWindow)
					.add("ctx", ctx)
					.toString();
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return Env.getContext(ctx, windowNo, variableName, onlyWindow);
		}
	}

	/**
	 * Composite
	 * 
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@VisibleForTesting
	static final class CompositeEvaluatee implements Evaluatee2
	{
		private final List<Evaluatee> sources = new ArrayList<Evaluatee>();

		private CompositeEvaluatee(final Evaluatee source)
		{
			addEvaluatee(source);
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.addValue(sources)
					.toString();
		}

		public CompositeEvaluatee addEvaluatee(final Evaluatee source)
		{
			Check.assume(source != null, "source is null");

			sources.add(source);
			return this;
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			for (final Evaluatee source : sources)
			{
				final String value = source.get_ValueAsString(variableName);
				if (!Check.isEmpty(value))
				{
					return value;
				}
			}
			return null;
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			for (final Evaluatee source : sources)
			{
				if (Evaluator.hasVariable(source, variableName))
				{
					return true;
				}
			}

			return false;
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			//
			// This logic applies only to first Evaluatee source
			final Evaluatee source = sources.get(0);
			if (source instanceof Evaluatee2)
			{
				return ((Evaluatee2)source).get_ValueOldAsString(variableName);
			}

			return null;
		}

		@VisibleForTesting
		List<Evaluatee> getSources()
		{
			return ImmutableList.copyOf(sources);
		}
	}

	/**
	 * Singleton implementation of {@link Evaluatee2}
	 *
	 * @author tsa
	 *
	 */
	private static final class SingletonEvaluatee implements Evaluatee2
	{
		private final String variableName;
		private final String value;

		private SingletonEvaluatee(final String variableName, final Object value)
		{
			super();
			Check.assumeNotEmpty(variableName, "variableName not empty");
			this.variableName = variableName;
			this.value = value == null ? null : String.valueOf(value);
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (!this.variableName.equals(variableName))
			{
				return null;
			}
			return value;
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return this.variableName.equals(variableName);
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}

	}

}
