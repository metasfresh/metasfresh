package de.metas.handlingunits.client.terminal.mmovement.view.impl;

/*
 * #%L
 * de.metas.handlingunits.client
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


import java.beans.PropertyChangeEvent;
import java.util.Collections;
import java.util.List;

import org.adempiere.util.Check;
import org.adempiere.util.collections.Predicate;

import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.event.UIPropertyChangeListener;
import de.metas.adempiere.form.terminal.swing.SwingTerminalFactory;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.HUJoinModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.HUMergeModel;
import de.metas.handlingunits.client.terminal.mmovement.model.join.impl.MergeType;

public final class HUJoinPanel extends AbstractLTCUPanel<HUJoinModel>
{
	private static final String ACTION_Merge = "Merge";

	private ITerminalButton bLUMerge;
	private ITerminalButton bTUMerge;

	public HUJoinPanel(final HUJoinModel model)
	{
		super(model);

		model.addPropertyChangeListener(HUJoinModel.PROPERTY_MergeableLUs, new UIPropertyChangeListener(this)
		{
			@Override
			protected void propertyChangeEx(final PropertyChangeEvent evt)
			{
				refreshLUMergeButton();
			}
		});

		model.addPropertyChangeListener(HUJoinModel.PROPERTY_MergeableTUs, new UIPropertyChangeListener(this)
		{
			@Override
			protected void propertyChangeEx(final PropertyChangeEvent evt)
			{
				refreshTUMergeButton();
			}
		});
	}

	private void refreshLUMergeButton()
	{
		if (model == null)
		{
			return;
		}

		if (bLUMerge != null)
		{
			bLUMerge.setEnabled(model.isMergeableLUs());
		}
	}

	private void refreshTUMergeButton()
	{
		if (model == null)
		{
			return;
		}

		if (bTUMerge != null)
		{
			bTUMerge.setEnabled(model.isMergeableTUs());
		}
	}

	@Override
	protected void initLayout(final ITerminalFactory factory)
	{
		initJoinPanelComponents();

		//
		// create LU-Lane
		final List<LayoutComponent> otherLUComponents;
		{
			Check.assumeNotNull(bLUMerge, "LU Merge Button initialized");
			final LayoutComponent luMergeComponent = new LayoutComponent(bLUMerge,
					"push"
							+ ", " + SwingTerminalFactory.BUTTON_Constraints // same size as Numberic Minus/Plus buttons
			);
			otherLUComponents = Collections.singletonList(luMergeComponent);
		}
		addLULane(false, otherLUComponents); // useQtyField=true

		//
		// create TU-Lane
		final List<LayoutComponent> otherTUComponents;
		{
			Check.assumeNotNull(bTUMerge, "TU Merge Button initialized");
			final LayoutComponent tuMergeComponent = new LayoutComponent(bTUMerge,
					"push"
							+ ", " + SwingTerminalFactory.BUTTON_Constraints // same size as Numberic Minus/Plus buttons
			);
			otherTUComponents = Collections.singletonList(tuMergeComponent);
		}
		addTULane(true, otherTUComponents); // useQtyField=true

		//
		// create CU-Lane
		addCULane(true); // useQtyField=false
	}

	private void initJoinPanelComponents()
	{
		final ITerminalContext terminalContext = model.getTerminalContext();
		final ITerminalFactory factory = terminalContext.getTerminalFactory();

		bLUMerge = factory.createButton(HUJoinPanel.ACTION_Merge);
		bLUMerge.setTextAndTranslate(HUJoinPanel.ACTION_Merge);
		bLUMerge.addListener(new UIPropertyChangeListener(factory, bLUMerge)
		{
			@Override
			public void propertyChangeEx(final PropertyChangeEvent evt)
			{
				doHUMerge(model, MergeType.LoadingUnit);
			}
		});

		bTUMerge = factory.createButton(HUJoinPanel.ACTION_Merge);
		bTUMerge.setTextAndTranslate(HUJoinPanel.ACTION_Merge);
		bTUMerge.addListener(new UIPropertyChangeListener(factory, bTUMerge)
		{
			@Override
			public void propertyChangeEx(final PropertyChangeEvent evt)
			{
				doHUMerge(model, MergeType.TradingUnit);
			}
		});

		//
		// After init, make sure the editable status is correct.
		refreshLUMergeButton();
		refreshTUMergeButton();
	}

	private void doHUMerge(final HUJoinModel model, final MergeType mergeType)
	{
		model.doHUMerge(new Predicate<HUMergeModel>()
		{
			@Override
			public boolean evaluate(final HUMergeModel mergeModel)
			{
				final HUMergePanel mergePanel = new HUMergePanel(mergeModel);
				final ITerminalFactory factory = getTerminalFactory();

				// the result is not disposable and is also not disposed together with the dialog
				// however we want to avoid memory problems and therefore dispose the merge model. This is why we maintain the references in model.doHUMerge()
				final ITerminalDialog mergeDialog = factory.createModalDialog(HUJoinPanel.this, HUJoinPanel.ACTION_Merge, mergePanel);

				//
				// Activate Merge Dialog and wait for user answer
				mergeDialog.activate();

				//
				// Return true if user really pressed OK
				final boolean edited = !mergeDialog.isCanceled();
				return edited;
			}
		}, mergeType);
	}

	@Override
	protected void doOnDialogCanceling(final ITerminalDialog dialog)
	{
		// nothing for now
	}
}
