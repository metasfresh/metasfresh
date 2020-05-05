/**
 * 
 */
package org.compiere.apps.search;

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


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.compiere.model.I_AD_InfoColumn;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import org.compiere.util.Env;

import de.metas.adempiere.util.Permutation;
import de.metas.i18n.Msg;

/**
 * @author cg
 *
 */
public abstract class InfoQueryCriteriaBPSearchAbstract implements IInfoQueryCriteria
{
	private final Logger log = LogManager.getLogger(getClass());
	
	private IInfoSimple parent;
	private I_AD_InfoColumn infoColumn;
	
	@Override
	public void init(IInfoSimple parent, I_AD_InfoColumn infoColumn, String searchText)
	{
		this.parent = parent;
		this.infoColumn = infoColumn;
	}

	@Override
	public I_AD_InfoColumn getAD_InfoColumn()
	{
		return infoColumn;
	}
	
	@Override
	public int getParameterCount()
	{
		return 1;
	}

	@Override
	public String getLabel(int index)
	{
		if (index == 0)
			return Msg.translate(Env.getCtx(), "search");
		return null;
	}

	@Override
	public Object getParameterToComponent(int index)
	{
		return null;
	}

	
	@Override
	public String[] getWhereClauses(List<Object> params)
	{
		final List<String> whereClauses = new ArrayList<String>();
		
		String search = getText();
		if (!(search.equals("") || search.equals("%")))
		{
			//
			search = search.trim();
			// metas: Permutationen ueber alle Search-Kombinationen
			// z.B. 3 Tokens ergeben 3! Moeglichkeiten als Ergebnis.
			if (search.contains(" "))
			{
				StringTokenizer st = new StringTokenizer(search, " ");
				int tokens = st.countTokens();
				String input[] = new String[tokens];
				Permutation perm = new Permutation();
				perm.setMaxIndex(tokens - 1);
				for (int token = 0; token < tokens; token++)
				{
					input[token] = st.nextToken();
				}
				perm.permute(input, tokens - 1);
				Iterator<String> itr = perm.getResult().iterator();
				while (itr.hasNext())
				{
					search = ("%" + itr.next() + "%").replace(" ", "%");
					whereClauses.add("UPPER(bpcs.Search) LIKE UPPER(?)");
					params.add(search);
					log.debug("Search: " + search);

				}
			}
			else
			{
				if (!search.startsWith("%"))
					search = "%" + search;
				// metas-2009_0021_AP1_CR064: end
				if (!search.endsWith("%"))
					search += "%";
				whereClauses.add("UPPER(bpcs.Search) LIKE UPPER(?)");
				params.add(search);
				log.debug("Search(2): " + search);
			}

			// list.add ("UPPER(bpcs.Search) LIKE ?");
			// metas ende
		}
		
		return whereClauses.toArray(new String[whereClauses.size()]);
	}

	public IInfoSimple getParent()
	{
		return parent;
	}

}
