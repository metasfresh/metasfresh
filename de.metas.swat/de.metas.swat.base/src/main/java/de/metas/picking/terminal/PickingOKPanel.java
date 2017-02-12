/**
 *
 */
package de.metas.picking.terminal;

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


import java.util.Date;
import java.util.Properties;
import java.util.Set;

import org.compiere.minigrid.IMiniTable;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.PackingMd;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IDisposable;

/**
 * @author cg
 *
 */
public interface PickingOKPanel extends IDisposable
{
	PackingMd getModel();

	void setIsPos(boolean isPos);

	void setSelection(Set<Integer> selection);

	Set<Integer> getSelectedScheduleIds(IMiniTable miniTable);

	/**
	 * Retrieve BPartner IDs from selected picking lines
	 *
	 * @return BPartner IDs for empty set
	 */
	Set<KeyNamePair> getSelectedBPartners();

	/**
	 * Retrieve DeliveryDates from selected picking lines
	 * @return set of DeliveryDates; never return null
	 */
	Set<Date> getSelectedDeliveryDates();

	IMiniTable getMiniTable();

	void createPackingDetails(Properties ctx, int[] rows);

	int getSelectedRow();

	int[] getSelectedRows();

	Object getPackageFrame();

	Object getPickingFrame();

	IComponent getComponent();

	/**
	 * Refresh current lines (i.e. shipment schedule aggregated lines)
	 */
	void refresh();

	String getLabelData();

	/**
	 * Dispose existing form data (i.e on exit)
	 */
	@Override
	void dispose();

	void logout();
}
