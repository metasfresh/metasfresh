package de.metas.dunning.api.impl;

/*
 * #%L
 * de.metas.dunning
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


import java.util.List;

import org.adempiere.util.lang.EqualsBuilder;
import org.adempiere.util.lang.HashcodeBuilder;

import de.metas.dunning.api.IDunningCandidateQuery;
import de.metas.dunning.interfaces.I_C_DunningLevel;

public class DunningCandidateQuery implements IDunningCandidateQuery
{
	private int AD_Table_ID = -1;
	private int Record_ID = -1;
	private List<I_C_DunningLevel> C_DunningLevels = null;
	private boolean active = true;
	private boolean applyClientSecurity = true;
	private Boolean processed;
	private Boolean writeOff;
	private String additionalWhere;
	
	// 04766 the old code could only distinguish between RW and RO, the default was "not RW"
	private ApplyAccessFilter applyAccessFilter = ApplyAccessFilter.ACCESS_FILTER_RO; 

	@Override
	public String toString()
	{
		return "DunningCandidateQuery ["
				+ "AD_Table_ID=" + AD_Table_ID + ", Record_ID=" + Record_ID
				+ ", C_DunningLevels=" + C_DunningLevels
				+ ", active=" + active
				+ ", applyClientSecurity=" + applyClientSecurity
				+ ", applyAccessFilter=" + applyAccessFilter
				+ ", processed=" + processed
				+ ", writeOff=" + writeOff
				+ "]";
	}

	@Override
	public int hashCode()
	{
		return new HashcodeBuilder()
				.append(AD_Table_ID)
				.append(Record_ID)
				.append(C_DunningLevels)
				.append(active)
				.append(applyClientSecurity)
				.append(processed)
				.append(writeOff)
				.toHashcode();
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
		{
			return true;
		}

		final DunningCandidateQuery other = EqualsBuilder.getOther(this, obj);
		if (other == null)
		{
			return false;
		}

		return new EqualsBuilder()
				.append(AD_Table_ID, other.AD_Table_ID)
				.append(Record_ID, other.Record_ID)
				.append(C_DunningLevels, other.C_DunningLevels)
				.append(active, other.active)
				.append(applyClientSecurity, other.applyClientSecurity)
				.append(processed, this.processed)
				.append(writeOff, this.writeOff)
				.isEqual();
	}

	@Override
	public int getAD_Table_ID()
	{
		return AD_Table_ID;
	}

	public void setAD_Table_ID(int aD_Table_ID)
	{
		AD_Table_ID = aD_Table_ID;
	}

	@Override
	public int getRecord_ID()
	{
		return Record_ID;
	}

	public void setRecord_ID(int record_ID)
	{
		Record_ID = record_ID;
	}

	@Override
	public List<I_C_DunningLevel> getC_DunningLevels()
	{
		return C_DunningLevels;
	}

	public void setC_DunningLevels(List<I_C_DunningLevel> c_DunningLevels)
	{
		C_DunningLevels = c_DunningLevels;
	}

	@Override
	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	@Override
	public boolean isApplyClientSecurity()
	{
		return applyClientSecurity;
	}

	public void setApplyClientSecurity(boolean applyClientSecurity)
	{
		this.applyClientSecurity = applyClientSecurity;
	}

	@Override
	public Boolean getProcessed()
	{
		return processed;
	}

	public void setProcessed(Boolean processed)
	{
		this.processed = processed;
	}

	@Override
	public Boolean getWriteOff()
	{
		return writeOff;
	}

	public void setWriteOff(Boolean writeOff)
	{
		this.writeOff = writeOff;
	}

	@Override
	public String getAdditionalWhere()
	{
		return additionalWhere;
	}

	public void setAdditionalWhere(String additionalWhere)
	{
		this.additionalWhere = additionalWhere;
	}

	@Override
	public ApplyAccessFilter getApplyAccessFilter()
	{
		return applyAccessFilter;
	}

	public void setApplyAccessFilter(ApplyAccessFilter applyAccessFilter)
	{
		this.applyAccessFilter = applyAccessFilter;
	}
}
