package org.adempiere.util.lang;

import org.adempiere.util.text.ExtendedReflectionToStringBuilder;
import org.adempiere.util.text.RecursiveIndentedMultilineToStringStyle;
import org.apache.commons.lang3.builder.RecursiveToStringStyle;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.google.common.annotations.VisibleForTesting;

import de.metas.util.Check;

/**
 * Common operations on {@link Object}.
 *
 * @author tsa
 * @see java.util.Objects
 * @see org.apache.commons.lang3.ObjectUtils
 * @see com.google.common.base.Objects
 */
public final class ObjectUtils
{

	/**
	 * Calls {@link #toString(Object, boolean)} with <code>multiLine == true</code>.
	 *
	 * @param obj
	 * @return
	 */
	public static final String toString(final Object obj)
	{
		return toString(obj, true);
	}

	/**
	 * To be used in {@link Object#toString()} implementations.
	 *
	 * Might arbitrarily throw exceptions. Therefore, Please consider to reimplement this, or to use e.g. {@link com.google.common.base.MoreObjects#toStringHelper(Object)}.
	 *
	 * @param obj the object to be printed
	 * @param multiLine if <code>true</code> then use {@link RecursiveIndentedMultilineToStringStyle} to display it in multiple lines.
	 * @return
	 *
	 */
	public static final String toString(final Object obj, final boolean multiLine)
	{
		if (obj == null)
		{
			return "<NULL>";
		}
		try
		{
			final ToStringStyle toStringStyle;
			if (multiLine)
			{
				toStringStyle = RecursiveIndentedMultilineToStringStyle.instance;
			}
			else
			{
				toStringStyle = RecursiveToStringStyle.SHORT_PREFIX_STYLE;
			}
			return new ExtendedReflectionToStringBuilder(obj, toStringStyle).toString();
		}
		catch (final Exception e)
		{
			// task 09493: catching possible errors

			// this doesn't work! still invokes the "high-level" toString() method
			// return ((Object)obj).toString() + " (WARNING: ExtendedReflectionToStringBuilder threw " + e + ")";
			return createFallBackMessage(obj, e);
		}
	}

	/**
	 *
	 * @param obj
	 * @param e
	 * @return
	 * @task http://dewiki908/mediawiki/index.php/09493_Error_Processing_Document_bei_Warenannahme_%28100785561740%29
	 */
	@VisibleForTesting
	/* package */static String createFallBackMessage(final Object obj, final Exception e)
	{
		return "!!! toString() for "
				+ obj.getClass().getName() + "@" + Integer.toHexString(obj.hashCode())
				+ ", (IdentityHashCode=" + System.identityHashCode(obj) + ") using ExtendedReflectionToStringBuilder threw " + e
				+ ") !!!";
	}

	/**
	 * Tests whether two objects are equals.
	 *
	 * <p>
	 * It takes care of the null case. Thus, it is helpful to implement Object.equals.
	 *
	 * <p>
	 * Notice: it uses compareTo if BigDecimal is found. So, in this case, a.equals(b) might not be the same as Objects.equals(a, b).
	 *
	 * <p>
	 * If both a and b are Object[], they are compared item-by-item.
	 *
	 * NOTE: this is a copy paste from org.zkoss.lang.Objects.equals(Object, Object)
	 *
	 * @see Check#equals(Object, Object)
	 */
	public boolean equals(final Object a, Object b)
	{
		return Check.equals(a, b);
	}

	private ObjectUtils()
	{
		super();
	}
}
