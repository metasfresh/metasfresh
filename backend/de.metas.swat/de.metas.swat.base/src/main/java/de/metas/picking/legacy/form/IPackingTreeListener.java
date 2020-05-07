package de.metas.picking.legacy.form;

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


import java.math.BigDecimal;

import javax.swing.tree.DefaultMutableTreeNode;

public interface IPackingTreeListener
{

	/**
	 * An available bin has been moved to the used bins parent that means that one or more used bin nodes should be
	 * created
	 * 
	 * @param availbinNode
	 */
	void usedBinAdded(DefaultMutableTreeNode availbinNode, int qty);

	/**
	 * A used bin has been moved back to the available bins.
	 * 
	 * @param usedBinNode
	 */
	void usedBinRemoved(DefaultMutableTreeNode usedBinNode);

	void packedItemRemoved(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode oldUsedBin, BigDecimal qty);

	void packedItemAdded(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode newUsedBin, BigDecimal qty);

	void packedItemMoved(DefaultMutableTreeNode packedItemNode, DefaultMutableTreeNode oldUsedBin,
			DefaultMutableTreeNode newUsedBin, BigDecimal qty);
}
