package de.metas.relation.grid;

/*
 * #%L
 * de.metas.swat.base
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


public class ModelRelationTarget
{

	private int adTableTargetId;

	private String targetWhereClause;

	private String relationTypeName;

	private String relationTypeInternalName;

	private int adTableSourceId;

	private int recordSourceId;

	private int adWindowSourceId;

	private int adWindowTargetId;

	private boolean relationTypeDirected;

	public int getAdTableTargetId()
	{
		return adTableTargetId;
	}

	public void setAdTableTargetId(int adTableId)
	{
		this.adTableTargetId = adTableId;
	}

	public String getTargetWhereClause()
	{
		return targetWhereClause;
	}

	public void setTargetWhereClause(String whereClause)
	{
		this.targetWhereClause = whereClause;
	}

	public String getRelationTypeName()
	{
		return relationTypeName;
	}

	public void setRelationTypeName(String relationTypeName)
	{
		this.relationTypeName = relationTypeName;
	}

	public String getRelationTypeInternalName()
	{
		return relationTypeInternalName;
	}

	public void setRelationTypeInternalName(String relationTypeInternalName)
	{
		this.relationTypeInternalName = relationTypeInternalName;
	}

	public int getAdTableSourceId()
	{
		return adTableSourceId;
	}

	public void setAdTableSourceId(int adTableSourceId)
	{
		this.adTableSourceId = adTableSourceId;
	}

	public int getRecordSourceId()
	{
		return recordSourceId;
	}

	public void setRecordSourceId(int recordSourceId)
	{
		this.recordSourceId = recordSourceId;
	}

	public int getAdWindowSourceId()
	{
		return adWindowSourceId;
	}

	public void setAdWindowSourceId(int adWindowSourceId)
	{
		this.adWindowSourceId = adWindowSourceId;
	}

	public int getAdWindowTargetId()
	{
		return adWindowTargetId;
	}

	public void setAdWindowTargetId(int adWindowTargetId)
	{
		this.adWindowTargetId = adWindowTargetId;
	}

	public boolean isRelationTypeDirected()
	{
		return relationTypeDirected;
	}

	public void setRelationTypeDirected(boolean relationTypeDirected)
	{
		this.relationTypeDirected = relationTypeDirected;
	}
}
