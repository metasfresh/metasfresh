/**
 * 
 */
package de.metas.adempiere.service.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.trx.api.ITrx;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Country_Sequence;
import org.compiere.util.CCache;

import de.metas.adempiere.service.ICountryDAO;
import de.metas.adempiere.service.ICountrySequenceBL;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class CountrySequenceBL implements ICountrySequenceBL
{

	private final CCache<Integer, Map<Integer, List<I_C_Country_Sequence>>> countrySequences = new CCache<>(I_C_Country_Sequence.Table_Name, 1);

	@Override
	public List<I_C_Country_Sequence> retrieveCountrySequence(final I_C_Country country, final I_AD_Org org, final String language)
	{
		final Properties ctx = InterfaceWrapperHelper.getCtx(org);

		 Services.get(IQueryBL.class)
				.createQueryBuilder(I_C_Country_Sequence.class, ctx, ITrx.TRXNAME_None)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_C_Country_Sequence.COLUMN_AD_Org_ID, org.getAD_Org_ID())
				.addEqualsFilter(I_C_Country_Sequence.COLUMN_C_Country_ID, country.getC_Country_ID())
				.addInArrayFilter(I_C_Country_Sequence.COLUMNNAME_AD_Language, language, "", null)
				//
				.orderBy()
				.addColumn(I_C_Country_Sequence.COLUMNNAME_AD_Language, false)
				.endOrderBy()
				//
				.create()
				.listImmutable(I_C_Country_Sequence.class);

		final Collection<List<I_C_Country_Sequence>> cSequences = countrySequences.getOrLoad(country.getC_Country_ID(), () -> Services.get(ICountryDAO.class).retrieveCountrySequence(ctx, country.getC_Country_ID())).values();
		
		final List<I_C_Country_Sequence> sequences = new ArrayList<>();
		cSequences.forEach( seqList -> sequences.addAll(seqList));
				
		final List<I_C_Country_Sequence> acceptedSequenceList = new ArrayList<>();
		sequences.forEach(sequence -> buildCountrySequenceList(acceptedSequenceList, sequence, org, language));

		return acceptedSequenceList;
	}

	private void buildCountrySequenceList(List<I_C_Country_Sequence> acceptedSequenceList, I_C_Country_Sequence sequence, final I_AD_Org org, final String language)
	{
		if (acceptCountrySequence(sequence, org, language) )
		{
			acceptedSequenceList.add(sequence);
		}
	}
	
	private boolean acceptCountrySequence(final I_C_Country_Sequence sequence, final I_AD_Org org, final String language)
	{
		if (sequence.getAD_Org_ID() != org.getAD_Org_ID())
		{
			return false;
		}
		
		if (!Check.isEmpty(sequence.getAD_Language(), true) && !sequence.getAD_Language().equals(language))
		{
			return false;
		}
			
		return true;
	}
	

}
