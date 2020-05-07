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


import java.util.List;

import org.compiere.apps.search.InfoQueryCriteriaGeneral;
import org.compiere.grid.ed.VCheckBox;

public class InfoQueryCriteriaAndOr extends InfoQueryCriteriaGeneral
{

	@Override
	protected void initComponents(String searchText)
	{
		final VCheckBox cb = new VCheckBox();
		cb.setText(label);
		cb.setSelected(true);
		cb.setFocusable(false);
		cb.setRequestFocusEnabled(false);
		cb.addVetoableChangeListener(fieldChangedListener);
		editor = cb;
		label = null;

		updateParent();
	}

	@Override
	public String[] getWhereClauses(List<Object> params)
	{
		return null;
	}

	@Override
	protected void onFieldChanged()
	{
		updateParent();
		super.onFieldChanged();
	}

	private void updateParent()
	{
		final boolean isAND = (Boolean)getParameterValue(0, false);
		getParent().setJoinClauseAnd(isAND);
	}
}
