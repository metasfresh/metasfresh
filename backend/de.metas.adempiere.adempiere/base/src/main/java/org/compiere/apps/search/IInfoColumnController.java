package org.compiere.apps.search;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
import java.util.Properties;
import java.util.Set;

import org.compiere.model.I_AD_InfoColumn;
import org.compiere.swing.CEditor;

/**
 * Info Column Controller and customizer. Declared in {@link I_AD_InfoColumn#COLUMNNAME_Classname}.
 */
public interface IInfoColumnController
{
	/**
	 * Customize info column after creation
	 *
	 * @param infoWindow
	 * @param infoColumn
	 */
	void customize(IInfoSimple infoWindow, Info_Column infoColumn);

	/**
	 * Called by API after all columns were created and customized.
	 *
	 * @param infoWindow
	 */
	void afterInfoWindowInit(IInfoSimple infoWindow);

	/**
	 * Fired by API when the value for this column changes (if editable) or the value of a column on which this one depends on.
	 *
	 * @param infoColumn column that has changed
	 * @param rowId row's record ID
	 * @param value
	 * @param value2
	 */
	void gridValueChanged(Info_Column infoColumn, int rowIndex, Object value);

	/**
	 * Called by API to know which are the columns that compose the propagation key.
	 *
	 * When a value is changed (by user), then it will calculate the propagation key for each row and it will copy that modified value to all rows that have same propagation key as current editing
	 * row.
	 *
	 * @return set of propagation key column names
	 */
	Set<String> getValuePropagationKeyColumnNames(Info_Column infoColumn);

	/**
	 *
	 * @return list of column names on which this column depends
	 */
	List<String> getDependsOnColumnNames();

	/**
	 * Fired by API when the value for this column was loaded from database but not yet set to table model.
	 *
	 * @param infoColumn
	 * @param rowIndexModel
	 * @param rowRecordId
	 * @param data original data to be converted
	 * @return converted value which will be set to table model
	 */
	Object gridConvertAfterLoad(final Info_Column infoColumn, final int rowIndexModel, final int rowRecordId, final Object value);

	/**
	 * Fired by API when user presses OK in Info Window
	 *
	 * @param ctx
	 * @param windowNo
	 * @param builders builders where we can add more our builder for new record
	 */
	void save(Properties ctx, int windowNo, IInfoWindowGridRowBuilders builders);

	/**
	 * Prepare editor before before it gets activated.
	 *
	 * @param editor
	 * @param value new value that will be set (but it's not set yet)
	 * @param rowIndexModel
	 * @param columnIndexModel
	 */
	void prepareEditor(CEditor editor, Object value, int rowIndexModel, int columnIndexModel);

	/**
	 * Returns where clause combination for product
	 * For example returns where clause combination for product and M_HU_PI_Item_Products
	 *
	 * @return
	 */
	String getProductCombinations();
}
