/**
 * 
 */
package de.metas.commission.form.zk;

/*
 * #%L
 * de.metas.commission.zkwebui
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


import java.util.Comparator;

/**
 * @author teo_sarca
 *
 */
public class SponsorTableComparator implements Comparator
{

	private final int rowIndex;
	private final boolean ascending;
	private SponsorTableModel model = null;

	/**
	 * 
	 */
	public SponsorTableComparator(int rowIndex, boolean ascending)
	{
		this.rowIndex = rowIndex;
		this.ascending = ascending;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int compare(Object o1, Object o2)
	{
		int cmp = 0;
		
		if (o1 instanceof SponsorTreeNode && o2 instanceof SponsorTreeNode)
		{
			o1 = model.toArray((SponsorTreeNode)o1)[this.rowIndex];
			o2 = model.toArray((SponsorTreeNode)o2)[this.rowIndex];
		}
		
		if (o1 instanceof String && o2 instanceof String)
		{
			String s1 = o1.toString();
			String s2 = o2.toString();
			cmp = s1.compareTo(s2);
		}
		else if (o1 instanceof Comparable && o2 instanceof Comparable)
		{
			Comparable c1 = (Comparable)o1;
			Comparable c2 = (Comparable)o2;
			cmp = c1.compareTo(c2);
		}
		
		if (!ascending)
			cmp = cmp * -1;

		return cmp;
	}
	
	public void setSponsorTableModel(SponsorTableModel model)
	{
		this.model  = model;
	}

}
