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


import java.util.ArrayList;
import java.util.List;

import javax.swing.Box;
import javax.swing.JPanel;

import net.miginfocom.layout.AC;
import net.miginfocom.layout.BoundSize;
import net.miginfocom.layout.CC;
import net.miginfocom.layout.ConstraintParser;
import net.miginfocom.layout.DimConstraint;
import net.miginfocom.layout.LC;
import net.miginfocom.swing.MigLayout;

import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.plaf.VPanelUI;
import org.compiere.model.GridFieldLayoutConstraints;

/**
 * {@link VPanel}'s layout factory.
 * 
 * It used to create {@link MigLayout}, {@link VPanelLayoutCallback} etc for different layout modes (e.g. Standard window layout, custom form layout).
 * 
 * @author tsa
 *
 */
class VPanelLayoutFactory
{
	/**
	 * Creates a {@link VPanelLayoutFactory} pre-configured for a standard window layout.
	 * 
	 * @return layout factory
	 */
	public static final VPanelLayoutFactory newStandardWindowLayout()
	{
		final int columns = AdempierePLAF.getInt(VPanelUI.KEY_StandardWindow_FieldColumns, VPanelUI.DEFAULT_StandardWindow_FieldColumns);
		final int labelMinWidth = AdempierePLAF.getInt(VPanelUI.KEY_StandardWindow_LabelMinWidth, VPanelUI.DEFAULT_StandardWindow_LabelMinWidth);
		final int labelMaxWidth = AdempierePLAF.getInt(VPanelUI.KEY_StandardWindow_LabelMaxWidth, VPanelUI.DEFAULT_StandardWindow_LabelMaxWidth);
		return new VPanelLayoutFactory()
				.setColumns(columns)
				.setLabelMinWidth(labelMinWidth)
				.setLabelMaxWidth(labelMaxWidth);
	}

	/**
	 * Creates a {@link VPanelLayoutFactory} pre-configured for a custom form layout.
	 * 
	 * @return layout factory
	 */
	public static final VPanelLayoutFactory newCustomFormLayout()
	{
		return new VPanelLayoutFactory()
				.setColumns(0)
				.setFieldMaxWidth(0) // don't enforce the editor field maximum size because we want to let it grow
		;
	}

	private int columns = 0;
	//
	private int labelMinWidth = 0;
	private int labelMaxWidth = 0;
	private BoundSize labelWidthConstraint;
	private boolean labelWidthConstraintSet = false;
	//
	private int fieldMinWidth = 0;
	private int fieldMaxWidth = 0;
	private BoundSize fieldWidthConstraint;
	private boolean fieldWidthConstraintSet = false;

	private VPanelLayoutFactory()
	{
		super();
	}

	public VPanelLayoutFactory setColumns(final int columns)
	{
		this.columns = columns;
		return this;
	}

	public VPanelLayoutFactory setLabelMinWidth(int labelMinWidth)
	{
		this.labelMinWidth = labelMinWidth;
		this.labelWidthConstraintSet = false; // reset
		return this;
	}

	public VPanelLayoutFactory setLabelMaxWidth(final int labelMaxWidth)
	{
		this.labelMaxWidth = labelMaxWidth;
		this.labelWidthConstraintSet = false; // reset
		return this;
	}

	private BoundSize getLabelWidthConstraint()
	{
		if (!labelWidthConstraintSet)
		{
			labelWidthConstraint = buildSizeConstraint(labelMinWidth, labelMaxWidth);
			labelWidthConstraintSet = true;
		}
		return labelWidthConstraint;
	}

	private static final BoundSize buildSizeConstraint(final int min, final int max)
	{
		if (min <= 0 && max <= 0)
		{
			return null;
		}

		String sizeStr = "";
		// Minimum
		if (min > 0)
		{
			sizeStr += min;
		}
		// Preferred
		sizeStr += ":";
		// no preferred

		// Maximum
		sizeStr += ":";
		if (max > 0)
		{
			int maxToUse = max;
			if (min > 0 && min > max)
			{
				maxToUse = min;
			}
			sizeStr += maxToUse;
		}

		//
		// Build the BoundSize
		final boolean isGap = false;
		final boolean isHorizontal = true;
		return ConstraintParser.parseBoundSize(sizeStr, isGap, isHorizontal);
	}

	public VPanelLayoutFactory setFieldMinWidth(int fieldMinWidth)
	{
		this.fieldMinWidth = fieldMinWidth;
		this.fieldWidthConstraintSet = false; // reset
		return this;
	}

	public VPanelLayoutFactory setFieldMaxWidth(final int fieldMaxWidth)
	{
		this.fieldMaxWidth = fieldMaxWidth;
		this.fieldWidthConstraintSet = false; // reset
		return this;
	}

	private BoundSize getFieldWidthConstraint()
	{
		if (!fieldWidthConstraintSet)
		{
			fieldWidthConstraint = buildSizeConstraint(fieldMinWidth, fieldMaxWidth);
			fieldWidthConstraintSet = true;
		}
		return fieldWidthConstraint;
	}

	public JPanel createFieldsPanel(final boolean zeroInsets)
	{
		final JPanel panel = new JPanel();
		panel.setLayout(createFieldsPanelLayout(zeroInsets));
		panel.setBackground(AdempierePLAF.getFormBackground());

		//
		// If we do the layout on precise number of columns, we need to add a placeholder for each label and field column.
		// Not adding that would make miglayout to ignore a column if no fields were added inside and we don't want that.
		if (columns > 0)
		{
			for (int i = 0; i < columns; i++)
			{
				// NOTE: if you add here other components than Box.Filler, please check org.compiere.grid.VPanelFieldGroup.updateVisible() too
				panel.add(Box.createHorizontalGlue()); // label
				panel.add(Box.createHorizontalGlue()); // editor
			}
		}

		return panel;
	}

	/**
	 * Creates the layout to be used on fields panel.
	 * 
	 * @param zeroInsets if true, the container insets will be set to zero.
	 * @return layout
	 */
	public MigLayout createFieldsPanelLayout(final boolean zeroInsets)
	{
		final LC layoutConstraints = new LC()
				.hideMode(3)// hidden fields take no part in grid
				.fillX()// fill the whole available width in the container
		;
		// layoutConstraints.debug(1000);
		if (zeroInsets)
		{
			layoutConstraints.insets("0", "0", "n", "0"); // top, left, bottom right
		}

		final AC colConstraints = new AC();
		if (columns > 0)
		{
			final BoundSize labelWidth = getLabelWidthConstraint();
			final BoundSize fieldWidth = getFieldWidthConstraint();
			final List<DimConstraint> colConstraintsList = new ArrayList<>();
			for (int i = 0; i < columns; i++)
			{
				// Label column
				final DimConstraint ccLabel = new DimConstraint();
				ccLabel.setGrowPriority(1); // low priority
				ccLabel.setGrow(0F);
				if (labelWidth != null)
				{
					ccLabel.setSize(labelWidth);
				}
				colConstraintsList.add(ccLabel);

				// Field column
				final DimConstraint ccField = new DimConstraint();
				ccField.setGrow(100F);
				ccField.setGrowPriority(100); // normal priority
				if (fieldWidth != null)
				{
					ccField.setSize(fieldWidth);
				}
				colConstraintsList.add(ccField);
			}

			colConstraints.setConstaints(colConstraintsList.toArray(new DimConstraint[colConstraintsList.size()]));

			layoutConstraints.wrap(); // automatically wrap if the number of columns was exceeded
		}

		final AC rowConstraints = new AC();
		rowConstraints.fill();
		rowConstraints.align("top");

		return new MigLayout(layoutConstraints, colConstraints, rowConstraints);
	}

	/**
	 * Creates the layout callback to be used on fields panel.
	 * 
	 * Mainly, this callback will enforce label and fields minimum and maximum sizes. The callback can be shared between different panels in order to get the same aspect.
	 * 
	 * @return layout callback
	 */
	public VPanelLayoutCallback createFieldsPanelLayoutCallback()
	{
		final VPanelLayoutCallback layoutCallback = new VPanelLayoutCallback();
		layoutCallback.setEnforceSameSizeOnAllLabels(true); // all labels shall have the same size
		layoutCallback.setLabelMinWidth(labelMinWidth);
		layoutCallback.setLabelMaxWidth(labelMaxWidth);
		layoutCallback.setFieldMaxWidth(fieldMinWidth);
		layoutCallback.setFieldMaxWidth(fieldMaxWidth);
		return layoutCallback;
	}

	public CC createFieldLabelConstraints(final boolean sameLine)
	{
		final CC constraints = new CC()
				.alignX("trailing") // align label to right, near the editor field
		;
		if (!sameLine)
		{
			constraints.newline();
		}

		return constraints;
	}

	public CC createFieldEditorConstraints(final GridFieldLayoutConstraints gridFieldConstraints)
	{
		final CC constraints = new CC()
				.alignX("leading")
				.pushX()
				.growX();

		if (gridFieldConstraints.getSpanX() > 1)
		{
			final int spanX = gridFieldConstraints.getSpanX() * 2 - 1;
			constraints.setSpanX(spanX);
		}
		if (gridFieldConstraints.getSpanY() > 1)
		{
			constraints.setSpanY(gridFieldConstraints.getSpanY());
		}

		return constraints;
	}
}
