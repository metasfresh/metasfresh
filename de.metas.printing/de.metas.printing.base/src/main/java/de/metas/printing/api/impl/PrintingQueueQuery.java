package de.metas.printing.api.impl;

/*
 * #%L
 * de.metas.printing.base
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


import org.adempiere.ad.dao.ISqlQueryFilter;
import de.metas.printing.api.IPrintingQueueQuery;
import de.metas.process.PInstanceId;
import de.metas.security.permissions.Access;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
/* package */class PrintingQueueQuery implements IPrintingQueueQuery
{
	@Getter @Setter
	private Boolean filterByProcessedQueueItems = Boolean.FALSE;
	private PInstanceId onlyAD_PInstance_ID;
	private int AD_Client_ID = -1;
	private int AD_Org_ID = -1;
	private int AD_User_ID = -1;
	private int ignoreC_Printing_Queue_ID = -1;
	private ISqlQueryFilter filter;

	private Access requiredAccess = null;

	private Integer copies = null;

	private String aggregationKey; // task 09028

	private int modelTableId = -1;
	private int modelFromRecordId = -1;
	private int modelToRecordId = -1;
	private ISqlQueryFilter modelFilter;



	@Override
	public IPrintingQueueQuery copy()
	{
		final PrintingQueueQuery queryNew = new PrintingQueueQuery();
		queryNew.filterByProcessedQueueItems = filterByProcessedQueueItems;
		queryNew.onlyAD_PInstance_ID = onlyAD_PInstance_ID;
		queryNew.AD_Client_ID = AD_Client_ID;
		queryNew.AD_Org_ID = AD_Org_ID;
		queryNew.AD_User_ID = AD_User_ID;
		queryNew.ignoreC_Printing_Queue_ID = ignoreC_Printing_Queue_ID;
		queryNew.filter = filter;
		queryNew.modelTableId = modelTableId;
		queryNew.modelFromRecordId = modelFromRecordId;
		queryNew.modelToRecordId = modelToRecordId;
		queryNew.modelFilter = modelFilter;
		queryNew.requiredAccess = requiredAccess;
		return queryNew;
	}


	@Override
	public PInstanceId getOnlyAD_PInstance_ID()
	{
		return onlyAD_PInstance_ID;
	}

	@Override
	public void setOnlyAD_PInstance_ID(final PInstanceId onlyAD_PInstance_ID)
	{
		this.onlyAD_PInstance_ID = onlyAD_PInstance_ID;
	}

	@Override
	public int getAD_Client_ID()
	{
		return AD_Client_ID;
	}

	@Override
	public void setAD_Client_ID(final int aD_Client_ID)
	{
		AD_Client_ID = aD_Client_ID;
	}

	@Override
	public int getAD_Org_ID()
	{
		return AD_Org_ID;
	}

	@Override
	public void setAD_Org_ID(final int aD_Org_ID)
	{
		AD_Org_ID = aD_Org_ID;
	}

	@Override
	public int getAD_User_ID()
	{
		return AD_User_ID;
	}

	@Override
	public void setAD_User_ID(final int aD_User_ID)
	{
		AD_User_ID = aD_User_ID;
	}

	@Override
	public int getIgnoreC_Printing_Queue_ID()
	{
		return ignoreC_Printing_Queue_ID;
	}

	@Override
	public void setIgnoreC_Printing_Queue_ID(final int ignoreC_Printing_Queue_ID)
	{
		this.ignoreC_Printing_Queue_ID = ignoreC_Printing_Queue_ID;
	}

	@Override
	public int getModelTableId()
	{
		return modelTableId;
	}

	@Override
	public void setModelTableId(int modelTableId)
	{
		this.modelTableId = modelTableId;
	}

	@Override
	public int getModelFromRecordId()
	{
		return modelFromRecordId;
	}

	@Override
	public void setModelFromRecordId(int modelFromRecordId)
	{
		this.modelFromRecordId = modelFromRecordId;
	}

	@Override
	public int getModelToRecordId()
	{
		return modelToRecordId;
	}

	@Override
	public void setModelToRecordId(int modelToRecordId)
	{
		this.modelToRecordId = modelToRecordId;
	}

	@Override
	public ISqlQueryFilter getFilter()
	{
		return filter;
	}

	@Override
	public void setFilter(ISqlQueryFilter filter)
	{
		this.filter = filter;
	}

	@Override
	public ISqlQueryFilter getModelFilter()
	{
		return modelFilter;
	}

	@Override
	public void setModelFilter(ISqlQueryFilter modelFilter)
	{
		this.modelFilter = modelFilter;
	}


	@Override
	public Access getRequiredAccess()
	{
		return requiredAccess;
	}

	@Override
	public void setRequiredAccess(final Access requiredAccess)
	{
		this.requiredAccess = requiredAccess;
	}

	@Override
	public Integer getCopies()
	{
		return copies;
	}

	@Override
	public void setCopies(int copies)
	{
		this.copies = copies;
	}

	@Override
	public String getAggregationKey()
	{
		return aggregationKey;
	}

	@Override
	public void setAggregationKey(final String aggregationKey)
	{
		this.aggregationKey=aggregationKey;
	}
}
