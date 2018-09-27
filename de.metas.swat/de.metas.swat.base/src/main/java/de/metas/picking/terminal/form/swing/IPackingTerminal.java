/**
 *
 */
package de.metas.picking.terminal.form.swing;

import org.compiere.apps.form.FormFrame;

/**
 * Packing window (second window)
 */
public interface IPackingTerminal
{
	void init(int windowNo, FormFrame packingWindowFrame);

	void dispose();

	/**
	 * @return Picking Window Frame (first window)
	 */
	FormFrame getPickingFrame();

	/**
	 * @return Packing Window Frame (this) (second window)
	 */
	FormFrame getFrame();
}
