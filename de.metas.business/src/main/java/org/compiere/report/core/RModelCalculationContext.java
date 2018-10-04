package org.compiere.report.core;

import de.metas.util.Check;


public class RModelCalculationContext
{
	private final IRModelMetadata metadata;
	
	private int columnIndex = -1;
	private int groupColumnIndex = -1;
	private int level = -1;
	
	private Object[] groupLevel2Value;

	public RModelCalculationContext(final IRModelMetadata metadata)
	{
		super();
		
		Check.assumeNotNull(metadata, "metadata not null");
		this.metadata = metadata;
	}

	@Override
	public String toString()
	{
		return "RModelCalculationPosition ["
				+ "columnIndex=" + columnIndex
				+ ", groupColumnIndex=" + groupColumnIndex
				+ ", level=" + level
				+ "]";
	}
	
	public IRModelMetadata getMetadata()
	{
		return metadata;
	}

	public int getColumnIndex()
	{
		return columnIndex;
	}

	public void setColumnIndex(int columnIndex)
	{
		this.columnIndex = columnIndex;
	}

	/**
	 * @return column index of the column on which we are groupping now
	 */
	public int getGroupColumnIndex()
	{
		return groupColumnIndex;
	}

	public void setGroupColumnIndex(int groupColumnIndex)
	{
		this.groupColumnIndex = groupColumnIndex;
	}

	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}

	public Object[] getGroupLevel2Value()
	{
		return groupLevel2Value;
	}

	public void setGroupLevel2Value(Object[] groupLevel2Value)
	{
		this.groupLevel2Value = groupLevel2Value;
	}
}
