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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Iterator;
import java.util.List;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.wrapper.POJOLookupMap;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;

import de.metas.dunning.api.IDunningCandidateQuery;
import de.metas.dunning.api.IDunningContext;
import de.metas.dunning.interfaces.I_C_Dunning;
import de.metas.dunning.interfaces.I_C_DunningLevel;
import de.metas.dunning.model.I_C_DunningDoc;
import de.metas.dunning.model.I_C_DunningDoc_Line_Source;
import de.metas.dunning.model.I_C_Dunning_Candidate;
import de.metas.util.collections.IteratorUtils;

public class PlainDunningDAO extends AbstractDunningDAO
{
	private final POJOLookupMap lookupMap = POJOLookupMap.get();

	public POJOLookupMap getDB()
	{
		return lookupMap;
	}

	@Override
	public <T> T newInstance(IDunningContext dunningContext, Class<T> interfaceClass)
	{
		return lookupMap.newInstance(interfaceClass);
	}

	@Override
	public void save(Object model)
	{
		lookupMap.save(model);
	}

	@Override
	public List<I_C_Dunning> retrieveDunnings()
	{
		return lookupMap.getRecords(I_C_Dunning.class);
	}

	@Override
	public Iterator<I_C_DunningDoc> retrieveNotProcessedDocumentsIterator(final IDunningContext dunningContext)
	{
		return lookupMap.getRecords(I_C_DunningDoc.class, new IQueryFilter<I_C_DunningDoc>()
		{
			@Override
			public boolean accept(I_C_DunningDoc pojo)
			{
				return !pojo.isProcessed();
			}
		}).iterator();
	}

	// private static final String FLAG_CandidateStaled = PlainDunningDAO.class.getName() + "#Staled";

	/**
	 * In Plain mode, staled (virtual column) is not supported. Always returning false.
	 *
	 * @return false
	 */
	@Override
	public boolean isStaled(final I_C_Dunning_Candidate candidate)
	{
		// final Boolean value = (Boolean)POJOWrapper.getWrapper(candidate).getValuesMap().get(FLAG_CandidateStaled);
		// return value != null && value.booleanValue();
		InterfaceWrapperHelper.refresh(candidate);
		return candidate.isStaled();
	}

	/**
	 * This method is <b>not</b> defined in the service interface. It is intended to be used by testing code only.
	 *
	 * @param candidate
	 * @param staled
	 */
	public void setStaled(final I_C_Dunning_Candidate candidate, boolean staled)
	{
		InterfaceWrapperHelper.save(candidate);
		InterfaceWrapperHelper.refresh(candidate);

		candidate.setIsStaled(staled);
		InterfaceWrapperHelper.save(candidate);
		// POJOWrapper.getWrapper(candidate).getValuesMap().put(FLAG_CandidateStaled, staled);
	}

	@Override
	protected List<I_C_Dunning_Candidate> retrieveDunningCandidates(final IDunningContext context, final IDunningCandidateQuery query)
	{
		return lookupMap.getRecords(I_C_Dunning_Candidate.class, new DunningCandidateQueryPOJOFilter(context, query));
	}

	@Override
	protected I_C_Dunning_Candidate retrieveDunningCandidate(IDunningContext context, IDunningCandidateQuery query)
	{
		return lookupMap.getFirstOnly(I_C_Dunning_Candidate.class, new DunningCandidateQueryPOJOFilter(context, query));
	}

	@Override
	protected Iterator<I_C_Dunning_Candidate> retrieveDunningCandidatesIterator(IDunningContext context, IDunningCandidateQuery query)
	{
		return retrieveDunningCandidates(context, query).iterator();
	}

	@Override
	public Iterator<I_C_DunningDoc_Line_Source> retrieveDunningDocLineSourcesToWriteOff(IDunningContext dunningContext)
	{
		final List<I_C_DunningDoc_Line_Source> list = lookupMap.getRecords(I_C_DunningDoc_Line_Source.class, new IQueryFilter<I_C_DunningDoc_Line_Source>()
		{
			@Override
			public boolean accept(final I_C_DunningDoc_Line_Source pojo)
			{
				if (!pojo.isProcessed())
				{
					return false;
				}
				if (!pojo.isWriteOff())
				{
					return false;
				}
				if (pojo.isWriteOffApplied())
				{
					return false;
				}
				return true;
			}
		});

		return list.iterator();
	}

	@Override
	public int deleteNotProcessedCandidates(final IDunningContext context, final I_C_DunningLevel dunningLevel)
	{
		int counter = 0;
		for (final I_C_Dunning_Candidate candToDelete : IteratorUtils.asIterable(retrieveNotProcessedCandidatesIteratorByLevel(context, dunningLevel)))
		{
			lookupMap.delete(candToDelete);
			counter++;
		}
		return counter;
	}

	@Override
	public List<I_C_Dunning_Candidate> retrieveProcessedDunningCandidatesForRecord(Properties ctx, int tableId, int recordId, String trxName)
	{
		throw new AdempiereException("Operation not supported");
	}

}
