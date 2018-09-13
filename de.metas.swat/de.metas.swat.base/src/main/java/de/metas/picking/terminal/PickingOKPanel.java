/**
 *
 */
package de.metas.picking.terminal;

import java.time.LocalDate;
import java.util.Properties;
import java.util.Set;

import org.compiere.minigrid.IMiniTable;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.picking.legacy.form.PackingMd;

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
	Set<LocalDate> getSelectedDeliveryDates();

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
