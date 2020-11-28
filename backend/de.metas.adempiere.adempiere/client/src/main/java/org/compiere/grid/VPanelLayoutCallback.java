package org.compiere.grid;

/*
 * #%L
 * de.metas.adempiere.adempiere.client
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


import java.awt.Component;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.util.Check;
import de.metas.util.Services;

import javax.swing.JComponent;

import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.ComponentWrapper;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.LayoutCallback;
import net.miginfocom.swing.MigLayout;

import org.compiere.grid.ed.VEditor;
import org.compiere.grid.ed.api.ISwingEditorFactory;
import org.compiere.model.GridField;
import org.compiere.model.GridFieldLayoutConstraints;
import org.compiere.swing.CLabel;
import org.slf4j.Logger;
import de.metas.logging.LogManager;

/**
 * Use layout callback to implement size groups across panels and to shrink hidden components vertically.
 */
class VPanelLayoutCallback extends LayoutCallback
{
	// services
	private static final transient Logger logger = LogManager.getLogger(VPanelLayoutCallback.class);
	private final transient ISwingEditorFactory swingEditorFactory = Services.get(ISwingEditorFactory.class);

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
		Check.assumeNotNull(layoutCallback, "layoutCallback shall be configured for {}", container);
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
		Check.assumeNull(container.getClientProperty(PROPERTYNAME_LayoutCallback), "layoutCallback not already configured for {}", container);
		final MigLayout layout = (MigLayout)container.getLayout();
		layout.addLayoutCallback(layoutCallback);
		container.putClientProperty(PROPERTYNAME_LayoutCallback, layoutCallback);
	}

	private boolean enforceSameSizeOnAllLabels = true;
	private int labelMinWidth = 0;
	private int labelMaxWidth = 0;
	//
	private int fieldMinWidth = 0;
	private int fieldMaxWidth = 0;

	VPanelLayoutCallback()
	{
		super();
		// logger.setLevel(Level.FINEST); // for debugging
	}

	/** Set the minimum and preferred sizes to match the largest component */
	@Override
	public BoundSize[] getSize(final ComponentWrapper componentWrapper)
	{
		// Field label
		final Object comp = componentWrapper.getComponent();
		if (comp instanceof CLabel)
		{
			if (!enforceSameSizeOnAllLabels)
			{
				return null;
			}

			// Don't enforce minimum width if the label is empty.
			// Reason: if on a labels column there is one label which is empty (e.g. the label of a button or the label of a checkbox),
			// we shall not consume that space because it will look like a big gap without any reason.
			//
			// NOTE: commented out because atm this is how we want.... but in future i think we can make this configurable if needed.
			//
			// final CLabel label = (CLabel)comp;
			// final String labelText = label.getText();
			// if (Check.isEmpty(labelText))
			// {
			// return null;
			// }

			return getSize(componentWrapper, labelMinWidth, labelMaxWidth);
		}
		// Field editor
		else if (comp instanceof VEditor)
		{
			// Enforce the field max width ONLY if the editor is not a long field (i.e. it's not span over all columns)
			final VEditor editor = (VEditor)comp;
			final GridField gridField = editor.getField();
			final GridFieldLayoutConstraints layoutConstraints = gridField.getLayoutConstraints();
			final boolean longField = layoutConstraints != null && layoutConstraints.isLongField();
			final int fieldMaxWidthToEnforce = longField ? 0 : this.fieldMaxWidth;

			return getSize(componentWrapper, fieldMinWidth, fieldMaxWidthToEnforce);
		}
		// Included field group panel (or included tab)
		else if (comp instanceof VPanelTaskPane)
		{
			return null;
		}
		// Any other component
		else
		{
			// Enforce the field's minimum width
			return getSize(componentWrapper, fieldMinWidth, 0);
		}
	}

	private final BoundSize[] getSize(final ComponentWrapper comp, final int minWidth, final int maxWidth)
	{
		// Minimum width to enforce
		int minWidthActual = minWidth;
		// Make sure minimum width is not bigger the maximum width
		if (maxWidth > 0 && maxWidth < minWidth)
		{
			minWidthActual = maxWidth;
		}

		//
		// Build the weight bound size
		final String widthStr = minWidthActual + ":" + minWidthActual + (maxWidth > 0 ? ":" + maxWidth : "");
		final BoundSize width = ConstraintParser.parseBoundSize(widthStr, false, true);

		//
		// Build the height bound size
		final Component c = (Component)comp.getComponent();
		final int h = c.getPreferredSize().height; // keep the same height
		final BoundSize height = ConstraintParser.parseBoundSize(h + ":" + h, false, false);

		//
		// Return the width/height bounds
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

	public void setLabelMinWidth(final int labelMinWidth)
	{
		this.labelMinWidth = labelMinWidth;
	}

	public void setLabelMaxWidth(final int labelMaxWidth)
	{
		this.labelMaxWidth = labelMaxWidth;
	}

	public void setFieldMinWidth(final int fieldMinWidth)
	{
		this.fieldMinWidth = fieldMinWidth;
	}

	public void setFieldMaxWidth(final int fieldMaxWidth)
	{
		this.fieldMaxWidth = fieldMaxWidth;
	}

	public void updateMinWidthFrom(final CLabel label)
	{
		final int labelWidth = label.getPreferredSize().width;
		labelMinWidth = labelWidth > labelMinWidth ? labelWidth : labelMinWidth;

		logger.trace("LabelMinWidth={} ({})", new Object[] { labelMinWidth, label });
	}

	public void updateMinWidthFrom(final VEditor editor)
	{
		final JComponent editorComp = swingEditorFactory.getEditorComponent(editor);
		final int editorCompWidth = editorComp.getPreferredSize().width;
		if (editorCompWidth > fieldMinWidth)
		{
			fieldMinWidth = editorCompWidth;
		}

		logger.trace("FieldMinWidth={} ({})", new Object[] { fieldMinWidth, editorComp });
	}
}
