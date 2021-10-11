package org.compiere.util;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import de.metas.util.Check;
import groovy.transform.ToString;
import lombok.NonNull;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.PlainContextAware;
import org.adempiere.util.api.IRangeAwareParams;
import org.adempiere.util.lang.ITableRecordReference;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.Optional;
import java.util.Properties;
import java.util.function.Supplier;
import java.util.stream.Stream;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
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

	public static Evaluatee2 ofSingleton(final String variableName, @Nullable final Object value)
	{
		return new SingletonEvaluatee(variableName, value);
	}

	public static Evaluatee ofSupplier(final String variableName, final Supplier<?> supplier)
	{
		return new SupplierEvaluatee(variableName, supplier);
	}

	public static Evaluatee2 ofMap(final Map<String, ?> map)
	{
		return new MapEvaluatee(map);
	}

	public static MapEvaluateeBuilder mapBuilder()
	{
		return new MapEvaluateeBuilder();
	}

	public static Evaluatee ofCtx(final Properties ctx, final int windowNo, final boolean onlyWindow)
	{
		return new EvaluateeCtx(ctx, windowNo, onlyWindow);
	}

	public static Evaluatee ofCtx(final Properties ctx)
	{
		final boolean onlyWindow = false;
		return new EvaluateeCtx(ctx, Env.WINDOW_None, onlyWindow);
	}

	public static Evaluatee ofTableRecordReference(final ITableRecordReference recordRef)
	{
		Check.assumeNotNull(recordRef, "Parameter recordRef is not null");
		final Object record = recordRef.getModel(PlainContextAware.newWithThreadInheritedTrx(Env.getCtx()));
		return InterfaceWrapperHelper.getEvaluatee(record);
	}

	public static Evaluatee2 ofRangeAwareParams(@NonNull final IRangeAwareParams params)
	{
		return new RangeAwareParamsAsEvaluatee(params);
	}

	public static Evaluatee2 compose(@NonNull final List<Evaluatee> evaluatees)
	{
		return new CompositeEvaluatee(evaluatees);
	}

	public static Evaluatee2 compose(@NonNull final Evaluatee... evaluatees)
	{
		return new CompositeEvaluatee(Arrays.asList(evaluatees));
	}

	/**
	 * Compose all evaluates which are not null
	 */
	public static Evaluatee2 composeNotNulls(@NonNull final Evaluatee... evaluatees)
	{
		final ImmutableList<Evaluatee> evaluateesFiltered = Stream.of(evaluatees).filter(Objects::nonNull).collect(ImmutableList.toImmutableList());
		Check.assumeNotEmpty(evaluateesFiltered, "At least one evaluatee shall be not null: {}", (Object)evaluatees);
		
		return new CompositeEvaluatee(evaluateesFiltered);
	}

	/**
	 *
	 * @return a special instance that has no variables.
	 */
	public static Evaluatee2 empty()
	{
		return EMPTY;
	}

	/**
	 * Empty
	 */
	private static final Evaluatee2 EMPTY = new Evaluatee2()
	{
		@Override
		public String toString()
		{
			return "EMPTY";
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return false;
		}

		@Nullable
		@Override
		public String get_ValueAsString(final String variableName)
		{
			return null;
		}

		@Nullable
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
		private final Map<String, Object> map;

		@SuppressWarnings("unchecked")
		private MapEvaluatee(@NonNull final Map<String, ?> map)
		{
			Check.assumeNotNull(map, "map not null");
			this.map = (Map<String, Object>)map;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("map", map)
					.toString();
		}

		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			@SuppressWarnings("unchecked")
			final T value = (T)map.get(variableName);
			return value;
		}

		@Nullable
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

		@Nullable
		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}
	}

	public static final class MapEvaluateeBuilder
	{
		private LinkedHashMap<String, Object> map;

		private MapEvaluateeBuilder()
		{
			super();
		}

		public Evaluatee build()
		{
			if (map == null || map.isEmpty())
			{
				return EMPTY;
			}
			else if (map.size() == 1)
			{
				final Entry<String, Object> entry = map.entrySet().iterator().next();
				return new SingletonEvaluatee(entry.getKey(), entry.getValue());
			}
			else
			{
				return new MapEvaluatee(ImmutableMap.copyOf(map));
			}
		}

		public MapEvaluateeBuilder put(final String variableName, final Object value)
		{
			if (map == null)
			{
				map = new LinkedHashMap<>();
			}
			map.put(variableName, value);
			return this;
		}

		public MapEvaluateeBuilder put(final CtxName name, final Object value)
		{
			put(name.getName(), value);
			return this;
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

		@Override
		public Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
		{
			return Env.getContextAsInt(ctx, windowNo, variableName, onlyWindow);
		}

		@Override
		public Date get_ValueAsDate(final String variableName, final Date defaultValue)
		{
			return Env.getContextAsDate(ctx, windowNo, variableName, onlyWindow);
		}
	}

	/**
	 * Composite
	 *
	 * @author metas-dev <dev@metasfresh.com>
	 *
	 */
	@VisibleForTesting
	@lombok.ToString
	static final class CompositeEvaluatee implements Evaluatee2
	{
		private final ImmutableList<Evaluatee> sources;

		private CompositeEvaluatee(final List<Evaluatee> sources)
		{
			Check.assumeNotEmpty(sources, "sources is not empty");
			this.sources = ImmutableList.copyOf(sources);
		}

		@Nullable
		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			for (final Evaluatee source : sources)
			{
				final Object value = source.get_ValueAsObject(variableName);
				if (value != null)
				{
					@SuppressWarnings("unchecked")
					final T valueCasted = (T)value;
					return valueCasted;
				}
			}
			return null;
		}

		@Nullable
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

		@Nullable
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
		private final Object value;
		private final String valueStr;

		private SingletonEvaluatee(final String variableName, @Nullable final Object value)
		{
			super();
			Check.assumeNotEmpty(variableName, "variableName not empty");
			this.variableName = variableName;
			this.value = value;
			valueStr = value == null ? null : String.valueOf(value); // precompute because it's the most used one
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add(variableName, value)
					.toString();
		}

		@Nullable
		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			if (!this.variableName.equals(variableName))
			{
				return null;
			}
			@SuppressWarnings("unchecked")
			final T valueCasted = (T)value;
			return valueCasted;
		}

		@Nullable
		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (!this.variableName.equals(variableName))
			{
				return null;
			}
			return valueStr;
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return this.variableName.equals(variableName);
		}

		@Nullable
		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return null;
		}
	}

	/**
	 * Supplier implementation of {@link Evaluatee2}
	 *
	 * @author tsa
	 *
	 */
	private static final class SupplierEvaluatee implements Evaluatee
	{
		private final String variableName;
		private final Supplier<?> supplier;

		private SupplierEvaluatee(final String variableName, final Supplier<?> supplier)
		{
			super();
			Check.assumeNotEmpty(variableName, "variableName not empty");
			Check.assumeNotNull(supplier, "Parameter supplier is not null");
			this.variableName = variableName;
			this.supplier = supplier;
		}

		@Nullable
		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (!this.variableName.equals(variableName))
			{
				return null;
			}
			final Object valueObj = supplier.get();
			return valueObj == null ? null : valueObj.toString();
		}

		@Nullable
		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			if (!this.variableName.equals(variableName))
			{
				return null;
			}

			final Object valueObj = supplier.get();

			@SuppressWarnings("unchecked")
			final T valueConv = (T)valueObj;
			return valueConv;
		}
	}

	/**
	 * Wraps given <code>evaluatee</code> but it will return <code>null</code> for the <code>excludeVariableName</code>.
	 */
	public static Evaluatee excludingVariables(final Evaluatee evaluatee, final String excludeVariableName)
	{
		return new ExcludingVariablesEvaluatee(evaluatee, excludeVariableName);
	}

	private static final class ExcludingVariablesEvaluatee implements Evaluatee
	{
		private final Evaluatee parent;
		private final String excludeVariableName;

		private ExcludingVariablesEvaluatee(final Evaluatee parent, final String excludeVariableName)
		{
			super();
			this.parent = parent;
			this.excludeVariableName = excludeVariableName;
		}

		@Override
		public String toString()
		{
			return MoreObjects.toStringHelper(this)
					.add("excludeVariableName", excludeVariableName)
					.add("parent", parent)
					.toString();
		}

		@Nullable
		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			if (excludeVariableName.equals(variableName))
			{
				return null;
			}
			return parent.get_ValueAsObject(variableName);
		}

		@Nullable
		@Override
		public String get_ValueAsString(final String variableName)
		{
			if (excludeVariableName.equals(variableName))
			{
				return null;
			}
			return parent.get_ValueAsString(variableName);
		}

		@Override
		public Optional<Object> get_ValueIfExists(final @NonNull String variableName, final @NonNull Class<?> targetType)
		{
			if (excludeVariableName.equals(variableName))
			{
				return Optional.empty();
			}
			return parent.get_ValueIfExists(variableName, targetType);
		}
	}

	@ToString
	private static class RangeAwareParamsAsEvaluatee implements Evaluatee2
	{
		private final IRangeAwareParams params;

		private RangeAwareParamsAsEvaluatee(@NonNull final IRangeAwareParams params)
		{
			this.params = params;
		}

		@Override
		public boolean has_Variable(final String variableName)
		{
			return params.hasParameter(variableName);
		}

		@SuppressWarnings("unchecked")
		@Override
		public <T> T get_ValueAsObject(final String variableName)
		{
			return (T)params.getParameterAsObject(variableName);
		}

		@Override
		public BigDecimal get_ValueAsBigDecimal(final String variableName, final BigDecimal defaultValue)
		{
			final BigDecimal value = params.getParameterAsBigDecimal(variableName);
			return value != null ? value : defaultValue;
		}

		@Override
		public Boolean get_ValueAsBoolean(final String variableName, final Boolean defaultValue_IGNORED)
		{
			return params.getParameterAsBool(variableName);
		}

		@Override
		public Date get_ValueAsDate(final String variableName, final Date defaultValue)
		{
			final Timestamp value = params.getParameterAsTimestamp(variableName);
			return value != null ? value : defaultValue;
		}

		@Override
		public Integer get_ValueAsInt(final String variableName, final Integer defaultValue)
		{
			final int defaultValueInt = defaultValue != null ? defaultValue : 0;
			return params.getParameterAsInt(variableName, defaultValueInt);
		}

		@Override
		public String get_ValueAsString(final String variableName)
		{
			return params.getParameterAsString(variableName);
		}

		@Override
		public String get_ValueOldAsString(final String variableName)
		{
			return get_ValueAsString(variableName);
		}
	}
}
