package de.metas.adempiere.form;

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


import java.awt.Component;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;

public class PackingTreeCelRenderer extends DefaultTreeCellRenderer
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3989413870459510846L;

	public Component getTreeCellRendererComponent(final JTree tree, final Object value, final boolean selected,
			boolean expanded, final boolean leaf, final int row, final boolean hasFocus)
	{

		final Component c = super.getTreeCellRendererComponent(tree, value, selected, expanded, leaf, row, hasFocus);

		final PackingTreeModel model = (PackingTreeModel)tree.getModel();
		if (value == model.getUnPackedItems().getUserObject())
		{
			// TODO icon Fragezeichen o.ae.
		}
		else if (value == model.getUsedBins().getUserObject())
		{

		}
		else if (value == model.getAvailableBins().getUserObject())
		{

		}
		else if (value instanceof LegacyPackingItem)
		{

		}
		else if (value instanceof AvailableBins)
		{

		}
		else if (value instanceof UsedBin)
		{

		}

		return c;
	}
}
