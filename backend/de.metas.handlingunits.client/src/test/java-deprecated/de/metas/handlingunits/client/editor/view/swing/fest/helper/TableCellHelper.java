package de.metas.handlingunits.client.editor.view.swing.fest.helper;

/*
 * #%L
 * de.metas.handlingunits.client
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


import javax.swing.tree.TreePath;

import org.adempiere.util.collections.FilterUtils;
import org.adempiere.util.collections.Predicate;
import org.compiere.model.I_M_Attribute;
import org.fest.swing.data.TableCell;
import org.jdesktop.swingx.JXTable;
import org.jdesktop.swingx.JXTreeTable;

import de.metas.handlingunits.client.editor.attr.model.HUAttributeSetModel;
import de.metas.handlingunits.client.editor.model.AbstractHUTreeTableModel;
import de.metas.handlingunits.client.editor.view.column.ITreeTableColumn;
import de.metas.handlingunits.tree.node.ITreeNode;

public final class TableCellHelper
{
	private TableCellHelper()
	{
		super();
	}

	public static <T extends ITreeNode<T>> TableCell findCell(final JXTreeTable treeTable, final AbstractHUTreeTableModel<T> model, final T node, final String columnName)
	{
		final int columnIndex = FilterUtils.indexOf(model.getColumnAdapters(), new Predicate<ITreeTableColumn<T>>()
		{
			@Override
			public boolean evaluate(final ITreeTableColumn<T> column)
			{
				return column.getColumnHeader().equals(columnName);
			}
		});

		return TableCell.row(treeTable.getRowForPath(new TreePath(model.getPathToRoot(node)))).column(columnIndex);
	}

	public static TableCell findAttributeCell(final JXTable table, final HUAttributeSetModel model, final String identifier)
	{
		int resultViewRowIndex = -1;
		for (int viewRowIndex = 0; viewRowIndex < table.getRowCount(); viewRowIndex++)
		{
			final int modelRowIndex = table.convertRowIndexToModel(viewRowIndex);

			final I_M_Attribute attribute = model.getM_Attribute(modelRowIndex);
			if (attribute.getName().equals(identifier))
			{
				resultViewRowIndex = viewRowIndex;
				break;
			}
		}

		return TableCell.row(resultViewRowIndex).column(HUAttributeSetModel.COLUMN_PropertyValue);
	}
}
