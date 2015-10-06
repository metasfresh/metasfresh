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


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.compiere.util.Env;
import org.zkoss.zul.AbstractListModel;
import org.zkoss.zul.ListModelExt;
import org.zkoss.zul.event.ListDataEvent;

import de.metas.commission.model.I_C_AdvComSponsorPoints_v1;

/**
 * @author teo_sarca
 *
 */
public class SponsorTableModel extends AbstractListModel
implements ListModelExt
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8133912062057410085L;
	
	private final SponsorTreeModel treeModel;
	private List<SponsorTreeNode> list = null;
	
	public SponsorTableModel(SponsorTreeModel treeModel)
	{
		this.treeModel = treeModel;
	}

	@Override
	public Object getElementAt(int index)
	{
//		SponsorTreeNode node = (SponsorTreeNode)getElementAt(null, index, null);
		SponsorTreeNode node = getList().get(index);
		return node;
	}

	@Override
	public int getSize()
	{
		return getList().size();
//		return getSize(null);
	}
	
	private List<SponsorTreeNode> getList()
	{
		if (list != null)
			return list;
		list = new ArrayList<SponsorTreeNode>();
		buildList(null, list);
		return list;
	}
	
	private void buildList(Object parent, List<SponsorTreeNode> list)
	{
		if (parent == null)
		{
			parent = treeModel.getRoot();
		}
		
		int count = treeModel.getChildCount(parent);
		for (int i = 0; i < count; i++)
		{
			Object o = treeModel.getChild(parent, i);
			if (! (o instanceof SponsorTreeNode))
			{
				continue;
			}
			SponsorTreeNode node = (SponsorTreeNode)o;
			list.add(node);
			
			buildList(node, list);
		}
	}
	
	public static String[] getColumnNames()
	{
		final String[] colnames = new String[]{
				"Ebene",
				"Name",
				I_C_AdvComSponsorPoints_v1.COLUMNNAME_PersonalVolume,
				I_C_AdvComSponsorPoints_v1.COLUMNNAME_AbsolutePersonalVolume,
				I_C_AdvComSponsorPoints_v1.COLUMNNAME_AbsoluteDownlineVolume,
		};
		return colnames;
	}
	
	public Object[] toArray(SponsorTreeNode node)
	{
		int level = node.getLogicalLevel();
		
		String name = node.getBPName();
		if (name == null)
			name = "";
		
		BigDecimal pv = Env.ZERO;
		BigDecimal apv = Env.ZERO;
		BigDecimal adv = Env.ZERO;
		I_C_AdvComSponsorPoints_v1 points = node.getPoints();
		if (points != null)
		{
			pv = points.getPersonalVolume();
			apv = points.getAbsolutePersonalVolume();
			adv = points.getAbsoluteDownlineVolume();
		}
		if (pv.scale() != 2)
			pv = pv.setScale(2, RoundingMode.HALF_UP);
		if (apv.scale() != 2)
			apv = apv.setScale(2, RoundingMode.HALF_UP);
		if (adv.scale() != 2)
			adv = adv.setScale(2, RoundingMode.HALF_UP);
		//
		return new Object[]{
				level,
				name,
				pv,
				apv,
				adv,
		};
	}

	@SuppressWarnings("unchecked")
	@Override
	public void sort(Comparator cmp, boolean ascending)
	{
		if (! (cmp instanceof SponsorTableComparator))
			return;
		
		SponsorTableComparator stc = (SponsorTableComparator)cmp;
		stc.setSponsorTableModel(this);
		Collections.sort(this.list, stc);
		
		fireEvent(ListDataEvent.CONTENTS_CHANGED, -1, -1);
	}
	
	public int getIndex(SponsorTreeNode node)
	{
		if (node == null)
			return -1;
		
		List<SponsorTreeNode> list = getList();
		for (int i = 0; i < list.size(); i++)
		{
			if (list.get(i) == node)
				return i;
		}
		return -1;
	}
}
