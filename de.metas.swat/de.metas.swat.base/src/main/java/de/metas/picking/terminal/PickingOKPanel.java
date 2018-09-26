/**
 *
 */
package de.metas.picking.terminal;

import java.time.LocalDate;
import java.util.Set;

import org.compiere.minigrid.IMiniTable;
import org.compiere.util.KeyNamePair;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.picking.legacy.form.PackingMd;
import de.metas.picking.legacy.form.RowIndexes;

/**
 * @author cg
 *
 */
public interface PickingOKPanel extends IDisposable
{
	PackingMd getModel();

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

	void createPackingDetails();

	int getSelectedRow();

	RowIndexes getSelectedRows();

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
