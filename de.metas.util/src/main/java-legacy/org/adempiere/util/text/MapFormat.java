// Source: http://www.java2s.com/Code/Java/I18N/AtextformatsimilartoMessageFormatbutusingstringratherthannumerickeys.htm
package org.adempiere.util.text;

/*
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright 1997-2007 Sun Microsystems, Inc. All rights reserved.
 *
 * The contents of this file are subject to the terms of either the GNU
 * General Public License Version 2 only ("GPL") or the Common
 * Development and Distribution License("CDDL") (collectively, the
 * "License"). You may not use this file except in compliance with the
 * License. You can obtain a copy of the License at
 * http://www.netbeans.org/cddl-gplv2.html
 * or nbbuild/licenses/CDDL-GPL-2-CP. See the License for the
 * specific language governing permissions and limitations under the
 * License. When distributing the software, include this License Header
 * Notice in each file and include the License file at
 * nbbuild/licenses/CDDL-GPL-2-CP. Sun designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Sun in the GPL Version 2 section of the License file that
 * accompanied this code. If applicable, add the following below the
 * License Header, with the fields enclosed by brackets [] replaced by
 * your own identifying information:
 * "Portions Copyrighted [year] [name of copyright owner]"
 *
 * Contributor(s):
 *
 * The Original Software is NetBeans. The Initial Developer of the Original
 * Software is Sun Microsystems, Inc. Portions Copyright 1997-2006 Sun
 * Microsystems, Inc. All Rights Reserved.
 *
 * If you wish your version of this file to be governed by only the CDDL
 * or only the GPL Version 2, indicate your decision by adding
 * "[Contributor] elects to include this software in this distribution
 * under the [CDDL or GPL Version 2] license." If you do not indicate a
 * single choice of license, a recipient has the option to distribute
 * your version of this file under either the CDDL, the GPL Version 2 or
 * to extend the choice of license to its licensees as provided above.
 * However, if you add GPL Version 2 code and therefore, elected the GPL
 * Version 2 license, then the option applies only if the new code is
 * made subject to such option by the copyright holder.
 */

import java.text.DateFormat;
import java.text.FieldPosition;
import java.text.Format;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableMap;

/**
 * A text format similar to <code>MessageFormat</code> but using string rather than numeric keys. You might use use this formatter like this:
 *
 * <pre>
 * MapFormat.format(&quot;Hello {name}&quot;, map);
 * </pre>
 *
 * Or to have more control over it:
 *
 * <pre>
 * Map m = new HashMap();
 * m.put(&quot;KEY&quot;, &quot;value&quot;);
 * MapFormat f = new MapFormat(m);
 * f.setLeftBrace(&quot;__&quot;);
 * f.setRightBrace(&quot;__&quot;);
 * String result = f.format(&quot;the __KEY__ here&quot;);
 * </pre>
 *
 * @author Slavek Psenicka
 * @author tsa
 * @see MessageFormat
 */
public class MapFormat extends Format
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2846158232274136688L;

	/**
	 * Designated method. It gets the string, initializes HashFormat object and returns converted string. It scans <code>pattern</code> for {} brackets, then parses enclosed string and replaces it
	 * with argument's <code>get()</code> value.
	 *
	 * @param pattern String to be parsed.
	 * @param arguments Map with key-value pairs to replace.
	 * @return Formatted string
	 */
	public static String format(final String pattern, final Map<String, ?> arguments)
	{
		return new MapFormat()
				.setArguments(arguments)
				.format(pattern);
	}

	/** Locale region settings used for number and date formatting */
	private final Locale locale = Locale.getDefault();

	/** Left delimiter */
	private String leftDelimiter = "{"; // NOI18N

	/** Right delimiter */
	private String rightDelimiter = "}"; // NOI18N

	/** Used formatting map */
	private Map<String, Object> _argumentsMap;

	/** Should be thrown an exception if key was not found? */
	private boolean throwEx = false;

	/** Exactly match brackets? */
	private boolean exactMatch = true;

	public MapFormat()
	{
		super();
	}

	/**
	 * Search for comments and quotation marks. Prepares internal structures.
	 *
	 * @param pattern String to be parsed.
	 * @param lmark Left mark of to-be-skipped block.
	 * @param rmark Right mark of to-be-skipped block or null if does not exist (// comment). private void process(String pattern, String lmark, String rmark) { int idx = 0; while (true) { int ridx =
	 *            -1, lidx = pattern.indexOf(lmark,idx); if (lidx >= 0) { if (rmark != null) { ridx = pattern.indexOf(rmark,lidx + lmark.length()); } else ridx = pattern.length(); } else break; if
	 *            (ridx >= 0) { skipped.put(new Range(lidx, ridx-lidx)); if (rmark != null) idx = ridx+rmark.length(); else break; } else break; } }
	 */
	/**
	 * Returns the value for given key. Subclass may define its own beahvior of this method. For example, if key is not defined, subclass can return <not defined> string.
	 *
	 * @param key Key.
	 * @return Value for this key.
	 */
	protected Object getArgumentValue(final String key)
	{
		return _argumentsMap.get(key);
	}

	protected Set<String> getArgumentNames()
	{
		return _argumentsMap.keySet();
	}

	/**
	 * Scans the pattern and prepares internal variables.
	 *
	 * @param patternStr String to be parsed.
	 * @exception IllegalArgumentException if number of arguments exceeds BUFSIZE or parser found unmatched brackets (this exception should be switched off using setExactMatch(false)).
	 */
	private final Pattern processPattern(final String patternStr) throws IllegalArgumentException
	{
		return new Pattern(patternStr, leftDelimiter, rightDelimiter, exactMatch);
	}

	/**
	 * Formats object.
	 *
	 * @param obj Object to be formatted into string
	 * @return Formatted object
	 */
	protected String formatObject(final Object obj)
	{
		if (obj == null)
		{
			return null;
		}

		if (obj instanceof Number)
		{
			return NumberFormat.getInstance(locale).format(obj); // fix
		}
		else if (obj instanceof Date)
		{
			return DateFormat.getDateTimeInstance(DateFormat.SHORT, DateFormat.SHORT, locale).format(obj); // fix
		}
		else if (obj instanceof String)
		{
			return (String)obj;
		}

		return obj.toString();
	}

	protected String formatText(final String text)
	{
		return text;
	}

	/**
	 * Formats the parsed string by inserting table's values.
	 *
	 * @param patternObj a string pattern
	 * @param result Buffer to be used for result.
	 * @param fpos position
	 * @return Formatted string
	 */
	@Override
	public final StringBuffer format(final Object patternObj, final StringBuffer result, final FieldPosition fpos_IGNORED)
	{
		final Pattern pattern = processPattern((String)patternObj);
		int lastOffsetIdx = 0;

		for (int i = 0; i <= pattern.getMaxOffset(); ++i)
		{
			final int offsetIdx = pattern.getOffsetIndex(i);
			result.append(formatText(pattern.substring(lastOffsetIdx, offsetIdx)));
			lastOffsetIdx = offsetIdx;

			final String key = pattern.getArgument(i);
			String obj;
			if (key.length() > 0)
			{
				obj = formatObject(getArgumentValue(key));
			}
			else
			{
				// else just copy the left and right braces
				result.append(leftDelimiter);
				result.append(rightDelimiter);
				continue;
			}

			if (obj == null)
			{
				// try less-greedy match; useful for e.g. PROP___PROPNAME__ where
				// 'PROPNAME' is a key and delims are both '__'
				// this does not solve all possible cases, surely, but it should catch
				// the most common ones
				final String lessgreedy = leftDelimiter + key;
				final int fromright = lessgreedy.lastIndexOf(leftDelimiter);

				if (fromright > 0)
				{
					final String newkey = lessgreedy.substring(fromright + leftDelimiter.length());
					final String newsubst = formatObject(getArgumentValue(newkey));

					if (newsubst != null)
					{
						obj = lessgreedy.substring(0, fromright) + newsubst;
					}
				}
			}

			if (obj == null)
			{
				if (throwEx)
				{
					throw new IllegalArgumentException("ObjectForKey");
				}
				else
				{
					obj = formatText(leftDelimiter + key + rightDelimiter);
				}
			}

			result.append(obj);
		}

		result.append(formatText(pattern.substring(lastOffsetIdx, pattern.length())));

		return result;
	}

	/**
	 * Parses the string. Does not yet handle recursion (where the substituted strings contain %n references.)
	 */
	@Override
	public Object parseObject(final String text, final ParsePosition status)
	{
		return parse(text);
	}

	/**
	 * Parses the string. Does not yet handle recursion (where the substituted strings contain {n} references.)
	 *
	 * @return New format.
	 */
	public final String parse(final String source)
	{
		final StringBuilder sbuf = new StringBuilder(source);
		final Iterator<String> argumentNames = getArgumentNames().iterator();

		while (argumentNames.hasNext())
		{
			final String argumentName = argumentNames.next();
			final String argumentValue = formatObject(getArgumentValue(argumentName));

			int argumentValueIdx = -1;
			do
			{
				argumentValueIdx = sbuf.toString().indexOf(argumentValue, ++argumentValueIdx);

				if (argumentValueIdx >= 0)
				{
					sbuf.replace(argumentValueIdx, argumentValueIdx + argumentValue.length(), leftDelimiter + argumentName + rightDelimiter);
				}
			}
			while (argumentValueIdx != -1);
		}

		return sbuf.toString();
	}

	/**
	 * Test whether formatter will throw exception if object for key was not found. If given map does not contain object for key specified, it could throw an exception. Returns true if throws. If not,
	 * key is left unchanged.
	 */
	public boolean willThrowExceptionIfKeyWasNotFound()
	{
		return throwEx;
	}

	/**
	 * Specify whether formatter will throw exception if object for key was not found. If given map does not contain object for key specified, it could throw an exception. If does not throw, key is
	 * left unchanged.
	 *
	 * @param throwEx If true, formatter throws IllegalArgumentException.
	 * @return
	 */
	public MapFormat setThrowExceptionIfKeyWasNotFound(final boolean throwEx)
	{
		this.throwEx = throwEx;
		return this;
	}

	/**
	 * Test whether both brackets are required in the expression. If not, use setExactMatch(false) and formatter will ignore missing right bracket. Advanced feature.
	 */
	public boolean isExactMatch()
	{
		return exactMatch;
	}

	/**
	 * Specify whether both brackets are required in the expression. If not, use setExactMatch(false) and formatter will ignore missing right bracket. Advanced feature.
	 *
	 * @param exactMatch If true, formatter will ignore missing right bracket (default = false)
	 * @return
	 */
	public MapFormat setExactMatch(final boolean exactMatch)
	{
		this.exactMatch = exactMatch;
		return this;
	}

	/** Returns string used as left brace */
	public String getLeftBrace()
	{
		return leftDelimiter;
	}

	/**
	 * Sets string used as left brace
	 *
	 * @param delimiter Left brace.
	 * @return
	 */
	public MapFormat setLeftBrace(final String delimiter)
	{
		leftDelimiter = delimiter;
		return this;
	}

	/** Returns string used as right brace */
	public String getRightBrace()
	{
		return rightDelimiter;
	}

	/**
	 * Sets string used as right brace
	 *
	 * @param delimiter Right brace.
	 * @return
	 */
	public MapFormat setRightBrace(final String delimiter)
	{
		this.rightDelimiter = delimiter;
		return this;
	}

	/**
	 * Sets argument map This map should contain key-value pairs with key values used in formatted string expression. If value for key was not found, formatter leave key unchanged (except if you've
	 * set setThrowExceptionIfKeyWasNotFound(true), then it fires IllegalArgumentException.
	 *
	 * @param map the argument map
	 * @return
	 */
	public MapFormat setArguments(final Map<String, ?> map)
	{
		if (map == null)
		{
			this._argumentsMap = ImmutableMap.of();
		}
		else
		{
			this._argumentsMap = new HashMap<>(map);
		}
		return this;
	}

	public MapFormat setArguments(final List<Object> list)
	{
		setArguments(toArgumentsMap(list));
		return this;
	}

	private static final Map<String, Object> toArgumentsMap(final List<Object> list)
	{
		if (list == null || list.isEmpty())
		{
			return ImmutableMap.of();
		}
		else
		{
			final int size = list.size();
			final Map<String, Object> map = new HashMap<>();
			for (int i = 0; i < size; i++)
			{
				map.put(String.valueOf(i), list.get(i));
			}
			return map;
		}
	}

	/** Pre-compiled pattern */
	private static final class Pattern
	{
		private static final int BUFSIZE = 255;

		/** Offsets to {} expressions */
		private int[] offsets;

		/** Keys enclosed by {} brackets */
		private String[] arguments;

		/** Max used offset */
		private int maxOffset;

		private final String patternPrepared;

		public Pattern(final String patternStr, final String leftDelimiter, final String rightDelimiter, final boolean exactMatch)
		{
			super();
			int idx = 0;
			int offnum = -1;
			final StringBuffer outpat = new StringBuffer();
			offsets = new int[BUFSIZE];
			arguments = new String[BUFSIZE];
			maxOffset = -1;

			while (true)
			{
				int rightDelimiterIdx = -1;
				final int leftDelimiterIdx = patternStr.indexOf(leftDelimiter, idx);

				if (leftDelimiterIdx >= 0)
				{
					rightDelimiterIdx = patternStr.indexOf(rightDelimiter, leftDelimiterIdx + leftDelimiter.length());
				}
				else
				{
					break;
				}

				if (++offnum >= BUFSIZE)
				{
					throw new IllegalArgumentException("TooManyArguments");
				}

				if (rightDelimiterIdx < 0)
				{
					if (exactMatch)
					{
						throw new IllegalArgumentException("UnmatchedBraces");
					}
					else
					{
						break;
					}
				}

				outpat.append(patternStr.substring(idx, leftDelimiterIdx));
				offsets[offnum] = outpat.length();
				arguments[offnum] = patternStr.substring(leftDelimiterIdx + leftDelimiter.length(), rightDelimiterIdx);
				idx = rightDelimiterIdx + rightDelimiter.length();
				maxOffset++;
			}

			outpat.append(patternStr.substring(idx));

			this.patternPrepared = outpat.toString();
		}

		public String substring(int beginIndex, int endIndex)
		{
			return patternPrepared.substring(beginIndex, endIndex);
		}

		public int length()
		{
			return patternPrepared.length();
		}

		public int getMaxOffset()
		{
			return maxOffset;
		}

		public int getOffsetIndex(final int i)
		{
			return offsets[i];
		}

		public String getArgument(final int i)
		{
			return arguments[i];
		}
	}
}
