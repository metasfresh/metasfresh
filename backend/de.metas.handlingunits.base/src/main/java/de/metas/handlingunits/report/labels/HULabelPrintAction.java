package de.metas.handlingunits.report.labels;

/**
 * A prepared HU label printing, see {@link HULabelService#preparePrint(HULabelPrintRequest)}.
 */
@FunctionalInterface
public interface HULabelPrintAction
{
	/** Does nothing; for the case that there is nothing to print. */
	HULabelPrintAction NOTHING = () -> {};

	/**
	 * Prints immediately.
	 *
	 * @throws org.adempiere.exceptions.AdempiereException if the printing fails
	 */
	void print();
}
