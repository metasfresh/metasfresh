package org.compiere.grid.ed;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.JComponent;
import javax.swing.JTextField;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VEditorDialogButtonAlign;
import org.adempiere.plaf.VEditorUI;

/**
 * Misc utilities for drawing and managing the {@link VEditor} implementations.
 * 
 * @author tsa
 *
 */
final class VEditorUtils
{
	/**
	 * Setup the dimensions of the {@link VEditor} component and of it's inner text component.
	 * 
	 * NOTE: we assume this is NOT a multi-line component
	 * 
	 * @param editorComponent
	 * @param textComponent
	 */
	public static final void setupVEditorDimensionFromInnerTextDimension(final JComponent editorComponent, final JComponent textComponent)
	{
		final Dimension textCompSizeOrig = textComponent.getPreferredSize();
		final int width = textCompSizeOrig.width;
		final int height = VEditorUI.getVEditorHeight();
		textComponent.setPreferredSize(new Dimension(width, height));

		setupVEditorDimension(editorComponent, width, height);
	}

	/**
	 * Setup the dimensions of the {@link VEditor} component. We assume there is NO inner text component.
	 * 
	 * NOTE: we assume this is NOT a multi-line component
	 * 
	 * @param editorComponent
	 * @param initialPreferredSize
	 */
	public static final void setupVEditorDimension(final JComponent editorComponent)
	{
		final Dimension sizeOrig = editorComponent.getPreferredSize();
		final int width = sizeOrig.width;
		final int height = VEditorUI.getVEditorHeight();

		setupVEditorDimension(editorComponent, width, height);
	}

	private static final void setupVEditorDimension(final JComponent editorComponent, final int width, final int height)
	{
		editorComponent.setPreferredSize(new Dimension(width, height)); // causes r/o to be the same length

		// Don't allow the component to shrink under given height
		editorComponent.setMinimumSize(new Dimension(30, height));

		// Don't allow the component to grow over given height
		// This applies to one line components (almost all of them).
		editorComponent.setMaximumSize(new Dimension(Integer.MAX_VALUE, height));
	}

	/**
	 * Creates the action button which is displayed on the right or left side (see {@link VEditorDialogButtonAlign}) of editor component.
	 * 
	 * @param iconName icon name to be displayed, without size suffix and without extension suffix.
	 * @param buttonSize button's size or 0 if not available
	 * @return action button
	 */
	public static final VEditorActionButton createActionButton(final String iconName, final JTextField textComponent)
	{
		return VEditorActionButton.build(iconName, textComponent);
	}

	/**
	 * Setup the UI properties of the inner text field of a {@link VEditor} component.
	 * 
	 * @param textField
	 */
	public static final void setupInnerTextComponentUI(final JTextField textField)
	{
		VEditorInnerTextComponentUI.instance.installUI(textField);
	}

	/**
	 * Class used to configure the UI of the inner text component of a {@link VEditor}.
	 * 
	 * Also, this class listens the "UI" property change (fired when the L&F is updated) and sets back the properties it customized.
	 * 
	 * @author tsa
	 *
	 */
	private static final class VEditorInnerTextComponentUI implements PropertyChangeListener
	{
		public static final transient VEditorInnerTextComponentUI instance = new VEditorInnerTextComponentUI();

		/** Component's UI property */
		private static final String PROPERTY_UI = "UI";

		private VEditorInnerTextComponentUI()
		{
			super();
		}

		public void installUI(final JComponent comp)
		{
			updateUI(comp);
			comp.addPropertyChangeListener("UI", this);
		}

		/** Actually sets the UI properties */
		private void updateUI(final JComponent comp)
		{
			comp.setBorder(null);
			comp.setFont(AdempierePLAF.getFont_Field());
			comp.setForeground(AdempierePLAF.getTextColor_Normal());
		}

		@Override
		public void propertyChange(PropertyChangeEvent evt)
		{
			if (PROPERTY_UI.equals(evt.getPropertyName()))
			{
				final JComponent comp = (JComponent)evt.getSource();
				updateUI(comp);
			}
		}
	}

	private VEditorUtils()
	{
		super();
	}
}
