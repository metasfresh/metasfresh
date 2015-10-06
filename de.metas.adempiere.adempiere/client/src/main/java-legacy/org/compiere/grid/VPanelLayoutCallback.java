package org.compiere.grid;

import java.awt.Component;
import java.util.logging.Level;

import javax.swing.JComponent;

import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.swing.MigLayout;

import org.adempiere.util.Check;
import org.compiere.model.GridField;
import org.compiere.swing.CLabel;
import org.compiere.util.CLogger;

/**
 * Use layout callback to implement size groups across panels and to shrink hidden components vertically.
 */
class VPanelLayoutCallback extends LayoutCallback
{
	private static final transient CLogger logger = CLogger.getCLogger(VPanelLayoutCallback.class);

	private static final String PROPERTYNAME_LayoutCallback = VPanelLayoutCallback.class.getName();

	/**
	 * Gets the layout callback for given container.
	 * 
	 * NOTE: it is assumed that the container is already using {@link MigLayout}, else this method will fail.
	 * 
	 * @param container
	 * @return {@link VPanelLayoutCallback} instance; never return null
	 * @see #setup(JComponent, VPanelLayoutCallback)
	 */
	public static final VPanelLayoutCallback forContainer(final JComponent container)
	{
		final VPanelLayoutCallback layoutCallback = (VPanelLayoutCallback)container.getClientProperty(PROPERTYNAME_LayoutCallback);
		Check.assumeNotNull(layoutCallback, "layoutCallback shall be configured for {0}", container);
		return layoutCallback;
	}

	/**
	 * Sets and configures the given layout callback to given container.
	 * 
	 * @param container
	 * @param layoutCallback
	 */
	public static final void setup(final JComponent container, final VPanelLayoutCallback layoutCallback)
	{
		Check.assumeNull(container.getClientProperty(PROPERTYNAME_LayoutCallback), "layoutCallback not already configured for {0}", container);
		final MigLayout layout = (MigLayout)container.getLayout();
		layout.addLayoutCallback(layoutCallback);
		container.putClientProperty(PROPERTYNAME_LayoutCallback, layoutCallback);
	}

	public static final VPanelLayoutCallback setup(final JComponent container)
	{
		final VPanelLayoutCallback layoutCallback = new VPanelLayoutCallback();
		setup(container, layoutCallback);
		return layoutCallback;
	}

	private int labelMinWidth = 0;
	private boolean enforceSameSizeOnAllLabels = true;
	private int fieldMinWidth = 0;

	VPanelLayoutCallback()
	{
		super();
		// logger.setLevel(Level.FINEST); // for debugging
	}

	/** Set the minimum and preferred sizes to match the largest component */
	@Override
	public BoundSize[] getSize(final ComponentWrapper comp)
	{
		if (comp.getComponent() instanceof CLabel)
		{
			if (enforceSameSizeOnAllLabels)
			{
				return getSize(comp, labelMinWidth);
			}
			else
			{
				return null;
			}
		}
		else
		{
			return getSize(comp, fieldMinWidth);
		}
	}

	private final BoundSize[] getSize(final ComponentWrapper comp, final int minWidth)
	{
		final Component c = (Component)comp.getComponent();
		final int w = minWidth;
		final int h = c.getPreferredSize().height;
		final BoundSize width = ConstraintParser.parseBoundSize(w + ":" + w, false, true);
		final BoundSize height = ConstraintParser.parseBoundSize(h + ":" + h, false, false);

		return new BoundSize[] { width, height };
	}

	@Override
	public void correctBounds(ComponentWrapper comp)
	{
		// no corrections
	}

	public void setEnforceSameSizeOnAllLabels(boolean enforceSameSizeOnAllLabels)
	{
		this.enforceSameSizeOnAllLabels = enforceSameSizeOnAllLabels;
	}

	public void updateMinWidthFrom(final CLabel label)
	{
		final int labelWidth = label.getPreferredSize().width;
		labelMinWidth = labelWidth > labelMinWidth ? labelWidth : labelMinWidth;

		logger.log(Level.FINEST, "LabelMinWidth={0} ({1})", new Object[] { labelMinWidth, label });
	}

	public void updateMinWidthFrom(final Component editorComp, final GridField gridField)
	{
		// Don't update the minimum width from long fields
		if (gridField.isLongField())
		{
			return;
		}

		final int editorCompWidth = editorComp.getPreferredSize().width;
		fieldMinWidth = editorCompWidth > fieldMinWidth ? editorCompWidth : fieldMinWidth;

		logger.log(Level.FINEST, "FieldMinWidth={0} ({1})", new Object[] { fieldMinWidth, editorComp });
	}
}
