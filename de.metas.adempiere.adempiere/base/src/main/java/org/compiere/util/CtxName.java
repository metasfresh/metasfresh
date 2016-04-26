package org.compiere.util;

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


import java.util.ArrayList;
import java.util.List;

import org.adempiere.util.Check;

import com.google.common.collect.ImmutableList;

/**
 * Immutable Context Name
 *
 * @author tsa
 *
 */
public final class CtxName
{
	public static final String NAME_Marker = "@";
	public static final String SEPARATOR = "/";
	public static final String MODIFIER_Old = "old";
	private static final List<String> MODIFIERS = ImmutableList.<String>builder()
			.add(MODIFIER_Old)
			.build();

	public static final String VALUE_NULL = null;

	public static CtxName parse(final String contextWithoutMarkers)
	{
		if (contextWithoutMarkers == null)
		{
			return null;
		}

		String name = null;

		final List<String> modifiers = new ArrayList<String>();
		final String[] tokens = contextWithoutMarkers.split(SEPARATOR);
		for (int i = 0; i < tokens.length; i++)
		{
			if (i == 0)
			{
				name = tokens[i];
			}
			else
			{
				modifiers.add(tokens[i]);
			}
		}

		final String defaultValue;
		if (!modifiers.isEmpty() && !isModifier(modifiers.get(modifiers.size() - 1)))
		{
			defaultValue = modifiers.remove(modifiers.size() - 1);
		}
		else
		{
			defaultValue = VALUE_NULL;
		}

		return new CtxName(name, modifiers, defaultValue);
	}
	
	/** Parse a given name, surrounded by {@value #NAME_Marker} */
	public static CtxName parseWithMarkers(final String contextWithMarkers)
	{
		if (contextWithMarkers == null)
		{
			return null;
		}
		
		String contextWithoutMarkers = contextWithMarkers.trim();
		if (contextWithoutMarkers.startsWith(NAME_Marker))
		{
			contextWithoutMarkers = contextWithoutMarkers.substring(1);
		}
		if (contextWithoutMarkers.endsWith(NAME_Marker))
		{
			contextWithoutMarkers = contextWithoutMarkers.substring(0, contextWithoutMarkers.length() - 1);
		}
		
		return parse(contextWithoutMarkers);
	}

	/**
	 *
	 * @param name
	 * @param expression expression with context variables (e.g. sql where clause)
	 * @return true if expression contains given name
	 */
	static boolean containsName(final String name, final String expression)
	{
		// FIXME: replace it with StringExpression
		if (name == null || name.isEmpty())
		{
			return false;
		}

		if (expression == null || expression.isEmpty())
		{
			return false;
		}

		final int idx = expression.indexOf(NAME_Marker + name);
		if (idx < 0)
		{
			return false;
		}

		final int idx2 = expression.indexOf(NAME_Marker, idx + 1);
		if (idx2 < 0)
		{
			return false;
		}

		final String nameStr = expression.substring(idx + 1, idx2);
		return name.equals(parse(nameStr).getName());
	}

	/**
	 *
	 * @param modifier
	 * @return true if given string is a registered modifier
	 */
	public static boolean isModifier(final String modifier)
	{
		if (Check.isEmpty(modifier))
		{
			return false;
		}
		return MODIFIERS.contains(modifier);
	}

	private final String name;
	private final List<String> modifiers;
	private final String defaultValue;
	private transient volatile String cachedToStringWithTagMarkers = null;
	private transient volatile String cachedToStringWithoutTagMarkers = null;

	// NOTE: package and not private because we want to test it
	/* package */CtxName(final String name, final List<String> modifiers, final String defaultValue)
	{
		super();
		if (Check.isEmpty(name))
		{
			throw new IllegalArgumentException("Name must be not empty");
		}

		this.name = name;
		if (modifiers != null && !modifiers.isEmpty())
		{
			this.modifiers = ImmutableList.copyOf(modifiers);
		}
		else
		{
			this.modifiers = null;
		}
		this.defaultValue = defaultValue;
	}

	public String getName()
	{
		return name;
	}

	public String getDefaultValue()
	{
		return defaultValue;
	}

	public boolean isOld()
	{
		return modifiers != null && modifiers.contains(MODIFIER_Old);
	}

	/**
	 * @return true if this context name is an explicit global variable (i.e. starts with # or $)
	 */
	public boolean isExplicitGlobal()
	{
		return name.startsWith("#") || name.startsWith("$");
	}

	/**
	 * Evaluates this context name and gets it's value from given source/context.
	 * 
	 * In case the source/context is <code>null</code> then {@link #getDefaultValue()} will be returned.
	 * 
	 * @param source evaluation context/source; <code>null</code> is accept
	 * @return {@link Evaluatee}'s variable value or {@link #VALUE_NULL}
	 */
	public String getValueAsString(final Evaluatee source)
	{
		if (source == null)
		{
			return getDefaultValue();
		}

		final String sourceResult;
		if (source instanceof Evaluatee2)
		{
			final Evaluatee2 source2 = (Evaluatee2)source;

			if (!source2.has_Variable(name))
			{
				return getDefaultValue();
			}

			if (!isOld())
			{
				sourceResult = source2.get_ValueAsString(name);
			}
			else
			{
				sourceResult = source2.get_ValueOldAsString(name);
			}
		}
		else
		{
			final String value = source.get_ValueAsString(name);
			if (Env.isPropertyValueNull(name, value))
			{
				return getDefaultValue();
			}
			else
			{
				sourceResult = value;
			}
		}

		if (sourceResult != null)
		{
			return sourceResult;
		}
		return getDefaultValue();
	}

	public String toString(final boolean includeTagMarkers)
	{
		return includeTagMarkers ? toStringWithMarkers() : toStringWithoutMarkers();
	}
	
	public String toStringWithMarkers()
	{
		if (cachedToStringWithTagMarkers == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(NAME_Marker).append(toStringWithoutMarkers()).append(NAME_Marker);
			cachedToStringWithTagMarkers = sb.toString();
		}
		return cachedToStringWithTagMarkers;
	}
	
	public String toStringWithoutMarkers()
	{
		if (cachedToStringWithoutTagMarkers == null)
		{
			final StringBuilder sb = new StringBuilder();
			sb.append(name);
			if (modifiers != null && !modifiers.isEmpty())
			{
				for (final String m : modifiers)
				{
					sb.append(SEPARATOR).append(m);
				}
			}
			if (!Check.isEmpty(defaultValue))
			{
				sb.append(SEPARATOR).append(defaultValue);
			}
			cachedToStringWithoutTagMarkers = sb.toString();
		}
		return cachedToStringWithoutTagMarkers;
	}

	@Override
	public String toString()
	{
		return toStringWithoutMarkers();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (defaultValue == null ? 0 : defaultValue.hashCode());
		result = prime * result + (modifiers == null ? 0 : modifiers.hashCode());
		result = prime * result + (name == null ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final CtxName other = (CtxName)obj;
		if (defaultValue == null)
		{
			if (other.defaultValue != null)
			{
				return false;
			}
		}
		else if (!defaultValue.equals(other.defaultValue))
		{
			return false;
		}
		if (modifiers == null)
		{
			if (other.modifiers != null)
			{
				return false;
			}
		}
		else if (!modifiers.equals(other.modifiers))
		{
			return false;
		}
		if (name == null)
		{
			if (other.name != null)
			{
				return false;
			}
		}
		else if (!name.equals(other.name))
		{
			return false;
		}
		return true;
	}
}
