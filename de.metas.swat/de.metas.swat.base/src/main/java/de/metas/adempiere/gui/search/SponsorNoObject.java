package de.metas.adempiere.gui.search;

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


/**
 * @author metas GmbH, m.ostermann@metas.de
 */
public class SponsorNoObject
{
	private String sponsorNo;
	private String name;
	private int sponsorID;
	//
	private String stringRepresentation = null;

	public SponsorNoObject(int sponsorID, String sponsorNo, String name)
	{
		super();
		this.sponsorID = sponsorID;
		this.sponsorNo = sponsorNo;
		this.name = name;
	}

	public int getSponsorID()
	{
		return sponsorID;
	}

	public void setSponsorID(int sponsorID)
	{
		this.sponsorID = sponsorID;
	}

	public String getSponsorNo()
	{
		return sponsorNo;
	}

	public void setSponsorNo(String sponsorNo)
	{
		this.sponsorNo = sponsorNo;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getStringRepresentation()
	{
		return stringRepresentation;
	}

	public void setStringRepresentation(String stringRepresentation)
	{
		this.stringRepresentation = stringRepresentation;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (!(obj instanceof SponsorNoObject))
			return false;
		if (this == obj)
			return true;
		//
		SponsorNoObject sno = (SponsorNoObject)obj;
		return this.sponsorNo == sno.sponsorNo;
	}

	@Override
	public String toString()
	{
		String str = getStringRepresentation();
		if (str != null)
			return str;
		return sponsorNo + ", " + name;
	}
}
