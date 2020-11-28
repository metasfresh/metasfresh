package org.adempiere.util.text;

/*
 * #%L
 * de.metas.util
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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.SystemUtils;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author tsa
 * @see http://stackoverflow.com/questions/3149951/java-tostring-tostringbuilder-not-sufficient-wont-traverse
 */
public class RecursiveIndentedMultilineToStringStyle extends ToStringStyle
{
	/**
	 *
	 */
	private static final long serialVersionUID = 916024963012763564L;

	public static final RecursiveIndentedMultilineToStringStyle instance = new RecursiveIndentedMultilineToStringStyle(20);

	private static final String NEWLINE_STRING = "\n";
	private static final Pattern NEWLINE_PATTERN = Pattern.compile("\n");

	public static String toString(final Object value)
	{
		final StringBuffer sb = new StringBuffer(512);
		final String fieldName = null;
		instance.appendDetail(sb, fieldName, value);
		return sb.toString();
	}

	private final int maxDepth;
	private final String tabs;

	// http://stackoverflow.com/a/16934373/603516
	private final ThreadLocal<MutableInteger> depth = new ThreadLocal<MutableInteger>()
	{
		@Override
		protected MutableInteger initialValue()
		{
			return new MutableInteger(0);
		}
	};

	protected RecursiveIndentedMultilineToStringStyle(final int maxDepth)
	{
		this.maxDepth = maxDepth;
		tabs = StringUtils.repeat("\t", maxDepth);

		setUseShortClassName(true); // NOTE: mandatory true, because we override getShortClassName

		setUseIdentityHashCode(false);
		setContentStart(" {");
		setContentEnd("}");

		// NOTE: don't print "{" and "}" for arrays because we are already printing them on content start/end
		setArrayStart("");
		setArrayEnd("");

		setFieldSeparator(SystemUtils.LINE_SEPARATOR);
		setFieldSeparatorAtStart(true);
		setFieldNameValueSeparator(" = ");

	}

	private int getDepth()
	{
		return depth.get().get();
	}

	private void padDepth(final StringBuffer buffer)
	{
		buffer.append(tabs, 0, getDepth());
	}

	private StringBuffer appendTabified(final StringBuffer buffer, final String value)
	{
		// return buffer.append(String.valueOf(value).replace("\n", "\n" + tabs.substring(0, getDepth())));
		final Matcher matcher = NEWLINE_PATTERN.matcher(value);
		final String replacement = NEWLINE_STRING + tabs.substring(0, getDepth());
		while (matcher.find())
		{
			matcher.appendReplacement(buffer, replacement);
		}
		matcher.appendTail(buffer);
		return buffer;
	}

	@Override
	protected void appendFieldSeparator(final StringBuffer buffer)
	{
		buffer.append(getFieldSeparator());
		padDepth(buffer);
	}

	@Override
	public void appendStart(final StringBuffer buffer, final Object object)
	{
		depth.get().increment();
		super.appendStart(buffer, object);
	}

	@Override
	public void appendEnd(final StringBuffer buffer, final Object object)
	{
		super.appendEnd(buffer, object);
		buffer.setLength(buffer.length() - getContentEnd().length());
		buffer.append(SystemUtils.LINE_SEPARATOR);
		depth.get().decrement();
		padDepth(buffer);
		appendContentEnd(buffer);
	}

	@Override
	protected void removeLastFieldSeparator(final StringBuffer buffer)
	{
		final int len = buffer.length();
		final int sepLen = getFieldSeparator().length() + getDepth();
		if (len > 0 && sepLen > 0 && len >= sepLen)
		{
			buffer.setLength(len - sepLen);
		}
	}

	private boolean noReflectionNeeded(final Object value)
	{
		try
		{
			return value != null &&
					(value.getClass().getName().startsWith("java.lang.")
					|| value.getClass().getMethod("toString").getDeclaringClass() != Object.class);
		}
		catch (final NoSuchMethodException e)
		{
			throw new IllegalStateException(e);
		}
	}

	@Override
	protected void appendDetail(final StringBuffer buffer, final String fieldName, final Object value)
	{
		if (getDepth() >= maxDepth || noReflectionNeeded(value))
		{
			appendTabified(buffer, String.valueOf(value));
		}
		else
		{
			new ExtendedReflectionToStringBuilder(value, this, buffer, null, false, false).toString();
		}
	}

	// another helpful method, for collections:
	@Override
	protected void appendDetail(final StringBuffer buffer, final String fieldName, final Collection<?> col)
	{
		appendSummarySize(buffer, fieldName, col.size());
		
		final ExtendedReflectionToStringBuilder builder = new ExtendedReflectionToStringBuilder(
				col.toArray(), // object
				this, // style,
				buffer, //buffer,
				null, //reflectUpToClass,
				true, // outputTransients,
				true // outputStatics
				);
		builder.toString();
	}

	@Override
	protected String getShortClassName(final Class<?> cls)
	{
		if (cls != null && cls.isArray() && getDepth() > 0)
		{
			return "";
		}

		return super.getShortClassName(cls);
	}

	static class MutableInteger
	{
		private int value;

		MutableInteger(final int value)
		{
			this.value = value;
		}

		public final int get()
		{
			return value;
		}

		public final void increment()
		{
			++value;
		}

		public final void decrement()
		{
			--value;
		}
	}
}
