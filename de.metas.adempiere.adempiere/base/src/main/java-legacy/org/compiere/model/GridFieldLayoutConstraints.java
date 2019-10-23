package org.compiere.model;

import org.adempiere.util.lang.ObjectUtils;

/**
 * {@link GridField}'s layout constraints
 *
 * @author tsa
 *
 */
public final class GridFieldLayoutConstraints
{
	public static final Builder builder()
	{
		return new Builder();
	}

	/** Max Display Length = 60 */
	public static final int MAXDISPLAY_LENGTH = 60;

	private int displayLength;
	private int columnDisplayLength;
	private boolean sameLine;
	private final int spanX;
	private final int spanY;

	/** Builder constructor */
	private GridFieldLayoutConstraints(final Builder from)
	{
		super();
		displayLength = from.displayLength;
		columnDisplayLength = from.columnDisplayLength;
		sameLine = from.sameLine;
		spanX = from.spanX > 0 ? from.spanX : 1;
		spanY = from.spanY > 0 ? from.spanY : 1;
	}

	/** Copy constructor */
	private GridFieldLayoutConstraints(final GridFieldLayoutConstraints from)
	{
		super();
		displayLength = from.displayLength;
		columnDisplayLength = from.columnDisplayLength;
		sameLine = from.sameLine;
		spanX = from.spanX;
		spanY = from.spanY;
	}

	@Override
	public String toString()
	{
		return ObjectUtils.toString(this);
	}

	public GridFieldLayoutConstraints copy()
	{
		return new GridFieldLayoutConstraints(this);
	}

	public int getDisplayLength()
	{
		return displayLength;
	}

	public void setDisplayLength(final int displayLength)
	{
		this.displayLength = displayLength;
	}

	public int getColumnDisplayLength()
	{
		return columnDisplayLength;
	}

	public void setColumnDisplayLength(final int columnDisplayLength)
	{
		this.columnDisplayLength = columnDisplayLength;
	}

	public boolean isSameLine()
	{
		return sameLine;
	}

	public void setSameLine(final boolean sameLine)
	{
		this.sameLine = sameLine;
	}

	/**
	 * Is this a long (string/text) field (over 60/2=30 characters)
	 *
	 * @return true if long field
	 */
	public boolean isLongField()
	{
		// if (m_vo.displayType == DisplayType.String
		// || m_vo.displayType == DisplayType.Text
		// || m_vo.displayType == DisplayType.Memo
		// || m_vo.displayType == DisplayType.TextLong
		// || m_vo.displayType == DisplayType.Image)
		return displayLength >= MAXDISPLAY_LENGTH / 2;
		// return false;
	}   // isLongField

	public int getSpanX()
	{
		return spanX;
	}

	public int getSpanY()
	{
		return spanY;
	}

	public static final class Builder
	{
		private int displayLength = 0;
		private int columnDisplayLength = 0;
		private boolean sameLine = false;
		private int spanX = 1;
		private int spanY = 1;

		private Builder()
		{
			super();
		}

		public GridFieldLayoutConstraints build()
		{
			return new GridFieldLayoutConstraints(this);
		}

		public Builder setDisplayLength(final int displayLength)
		{
			this.displayLength = displayLength;
			return this;
		}

		public Builder setColumnDisplayLength(final int columnDisplayLength)
		{
			this.columnDisplayLength = columnDisplayLength;
			return this;
		}

		public Builder setSameLine(final boolean sameLine)
		{
			this.sameLine = sameLine;
			return this;
		}

		public Builder setSpanX(final int spanX)
		{
			this.spanX = spanX;
			return this;
		}

		public Builder setSpanY(final int spanY)
		{
			this.spanY = spanY;
			return this;
		}
	}
}
