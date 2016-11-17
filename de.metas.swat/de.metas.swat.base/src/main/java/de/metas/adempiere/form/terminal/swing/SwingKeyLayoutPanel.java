package de.metas.adempiere.form.terminal.swing;

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


import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.border.Border;
import javax.swing.plaf.metal.MetalLookAndFeel;

import org.adempiere.util.Check;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IExecuteBeforePainingSupport;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalScrollPane.ScrollPolicy;
import de.metas.adempiere.form.terminal.POSKey;
import de.metas.adempiere.form.terminal.POSKeyLayout;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/* package */class SwingKeyLayoutPanel
		extends de.metas.adempiere.form.terminal.TerminalKeyPanel
		implements IComponentSwing
{
	private static final String POSKEYBUTTON_Constraints = "sizegroup keyButton" // all key buttons shall have the same size
			+ ", grow 100 100" // fill the whole cell (horizontal, vertical); also this is required by "fill" container option
			+ ", ";
	//
	private static final int POSKEYBUTTON_DefaultSize = 40;

	private IContainer panel;
	private CardLayout cardLayout;

	/**
	 * Key Button border color.
	 *
	 * NOTE: this is not the selected button border color. It's the border color of all our keys.
	 */
	private final Color keyBorderColor = MetalLookAndFeel.getCurrentTheme().getControlDisabled();

	/**
	 * Key Button border to be used when it's not selected
	 */
	private Border keyBorderUnselected;

	/**
	 * Key Button border to be used when it's selected
	 */
	private Border keyBorderSelected;

	private class POSKeyboardDispacher implements KeyEventDispatcher
	{
		@Override
		public boolean dispatchKeyEvent(final KeyEvent e)
		{
			return onKeyboardPressed(e);
		}
	};

	private POSKeyboardDispacher keyboardDispacher;

	protected SwingKeyLayoutPanel(final ITerminalContext tc,
			final IKeyLayout keyLayout,
			final ITerminalKeyListener caller)
	{
		super(tc, keyLayout, caller);
	}

	protected SwingKeyLayoutPanel(final ITerminalContext tc,
			final IKeyLayout keyLayout,
			final ITerminalKeyListener caller,
			final String fixedButtonHeight,
			final String fixedButtonWidth)
	{
		super(tc, keyLayout, caller, fixedButtonHeight, fixedButtonWidth);
	}

	@Override
	protected void init()
	{
		cardLayout = new CardLayout();
		final IContainer c = getTerminalFactory().createContainer();
		SwingTerminalFactory.getUI(c).setLayout(cardLayout);
		panel = c;

		super.init();

		keyboardDispacher = new POSKeyboardDispacher();
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(keyboardDispacher);
	}

	@Override
	public void dispose()
	{
		KeyboardFocusManager.getCurrentKeyboardFocusManager().removeKeyEventDispatcher(keyboardDispacher);

		// panel = DisposableHelper.dispose(panel); disposed methods shall stay "local". 'panel' itself is disposed individually
		cardLayout = null;

		super.dispose();
	}

	@Override
	protected void addPOSKeyButtonEmpty(final IContainer content)
	{
		//
		// Create a dummy button (just to fill the gap)
		final ITerminalButton button = getTerminalFactory().createButton("");
		button.setFocusable(false);
		button.setEnabled(false);

		content.add(button, createSizeConstraints(POSKEYBUTTON_Constraints));
	}

	private String createSizeConstraints(final String constraints)
	{
		Check.assumeNotNull(constraints, "String not null");
		final StringBuilder result = new StringBuilder(constraints);

		//
		// Height
		final String heightFixed = getKeyFixedHeight();
		if (!Check.isEmpty(heightFixed, true))
		{
			result.append("h " + heightFixed + "::" + heightFixed + ",");
		}
		else
		{
			// No explicit height. Set to default minimum size
			result.append("h " + POSKEYBUTTON_DefaultSize + ":: ,");
		}

		//
		// Width
		final String widthFixed = getKeyFixedWidth();
		if (!Check.isEmpty(widthFixed, true))
		{
			result.append("w " + widthFixed + "::" + widthFixed + ",");
		}
		else
		{
			// No explicit weight. Set to default minimum size
			result.append("w " + POSKEYBUTTON_DefaultSize + ":: ,");
		}

		return result.toString();
	}

	@Override
	protected int addPOSKeyButton(final IContainer content, final ITerminalButton button, final ITerminalKey key)
	{
		String constraints = POSKEYBUTTON_Constraints;

		int size = 1;
		if (key.getSpanX() > 1)
		{
			constraints += "spanx " + key.getSpanX() + ",";
			size = key.getSpanX();
		}
		if (key.getSpanY() > 1)
		{
			constraints += "spany " + key.getSpanY() + ",";
			size = size * key.getSpanY();
		}

		unselectButtonUI(button);
		content.add(button, createSizeConstraints(constraints));

		return size;
	}

	@Override
	public Component getComponent()
	{
		return SwingTerminalFactory.getUI(panel);
	}

	@Override
	protected IContainer createPOSKeyContent(final int cols)
	{
		final ITerminalFactory terminalFactory = getTerminalFactory();

		final String constraints = "fill" // Claims all available space in the container for the columns and/or rows
				+ ", wrap " + Math.max(cols, 3) // auto-wrap after given number of columns
		// + ", debug" // enable debugging borders
		;
		return terminalFactory.createContainer(constraints);
	}

	@Override
	protected IComponent createPOSKeyComponent(final IContainer content)
	{
		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(content);
		scroll.setHorizontalScrollBarPolicy(ScrollPolicy.NEVER);
		scroll.setHorizontalStrechView(true); // stretch view horizontally if is smaller then the scroll pane component
		scroll.setVerticalScrollBarPolicy(ScrollPolicy.WHEN_NEEDED);
		scroll.setVerticalStrechView(true); // stretch view vertically if is smaller then the scroll pane component

		return scroll;
	}

	@Override
	protected void addPOSKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo)
	{
		final IKeyLayout keyLayout = keyLayoutInfo.getKeyLayout();

		final String keyLayoutId = keyLayout.getId();
		// Make sure the ID is not empty, else we will get an exception when adding to panel which has CardLayout
		Check.assumeNotEmpty(keyLayoutId, "keyLayoutId not empty");

		final IComponent keyLayoutComp = keyLayoutInfo.getKeyLayoutComponent();
		panel.add(keyLayoutComp, keyLayoutId);
	}

	@Override
	protected void removeKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo)
	{
		final IComponent keyLayoutComp = keyLayoutInfo.getKeyLayoutComponent();
		if (keyLayoutComp == null)
		{
			// no UI component? weird, but nothing to do
			return;
		}

		//
		// We destroy the inner container which contains all Keys
		// Assumptions:
		// * keyLayoutComp is a ITerminalScrollPanel (see createPOSKeyComponent)
		// * scroll's view is a container (see createPOSKeyComponent)
		// NOTE: because container's layout is MigLayout and MigLayout is EXTREMELLY slow when removing a lot of components
		// if we are not setting container's layout to null and then remove
		// then removing something like 1000 keys will take an eternity
		// NOTE2: setting layout to NULL is already happening in de.metas.adempiere.form.terminal.swing.SwingTerminalContainer.removeAll()
		final ITerminalScrollPane scroll = (ITerminalScrollPane)keyLayoutComp;
		final IContainer container = (IContainer)scroll.getViewport();
		if (container != null)
		{
			container.removeAll();
		}

		panel.remove(keyLayoutComp);
	}

	@Override
	protected void showKeyKayoutUI(final IKeyLayout keyLayout)
	{
		final Container panelComp = SwingTerminalFactory.getUI(panel);
		cardLayout.show(panelComp, keyLayout.getId());

		// NOTE: if we don't call validate() it's a big change to see "nothing" on screen
		panelComp.validate();
	}

	@Override
	public void setKeySelectionColor(final Color color)
	{
		super.setKeySelectionColor(color);
		keyBorderSelected = null;
	}

	private Border getKeyBorderUnselected()
	{
		if (keyBorderUnselected == null)
		{
			keyBorderUnselected = BorderFactory.createCompoundBorder(
					BorderFactory.createLineBorder(keyBorderColor, 1) // outsideBorder,
					, BorderFactory.createEmptyBorder(3, 3, 3, 3) // inside border
			);
		}
		return keyBorderUnselected;
	}

	private Border getKeyBorderSelected()
	{
		if (keyBorderSelected == null)
		{
			keyBorderSelected = BorderFactory.createLineBorder(getKeySelectionColor(), 4);
		}
		return keyBorderSelected;
	}

	@Override
	protected void selectButtonUI(final ITerminalButton button)
	{
		setBorder(button, getKeyBorderSelected());
	}

	@Override
	protected void unselectButtonUI(final ITerminalButton button)
	{
		setBorder(button, getKeyBorderUnselected());
	}

	/**
	 * Calls {@link JComponent#setBorder(Border)} via {@link IExecuteBeforePainingSupport}.
	 *
	 * @param keyInfo
	 */
	private void setBorder(final ITerminalButton button, final Border border)
	{
		final JComponent buttonComp = (JComponent)SwingTerminalFactory.getUI(button);
		updatePOSKeyButtonUI(button, new Runnable()
		{
			@Override
			public void run()
			{
				buttonComp.setBorder(border);
			}
		});
	}

	private boolean onKeyboardPressed(final KeyEvent e)
	{
		//
		// Make sure is a numeric or an alphanumeric keyboard
		final IKeyLayout keyLayout = getKeyLayout();
		if (!(keyLayout instanceof POSKeyLayout))
		{
			return false;
		}
		final POSKeyLayout posKeyLayout = (POSKeyLayout)keyLayout;
		if (!posKeyLayout.isText() && !posKeyLayout.isNumeric())
		{
			return false;
		}

		//
		// Handle only KEY_TYPED event
		final int type = e.getID();
		if (type != KeyEvent.KEY_TYPED)
		{
			return false;
		}

		//
		// Convert event's key char to POS Key text
		String keyEventText;
		if (e.getKeyChar() == KeyEvent.VK_BACK_SPACE)
		{
			keyEventText = POSKey.KEY_Backspace;
		}
		else
		{
			keyEventText = String.valueOf(e.getKeyChar()).toUpperCase();
		}

		//
		// Search for corresponding terminal key (if any)
		ITerminalKey key = null;
		for (final ITerminalKey k : posKeyLayout.getKeys())
		{
			final String keyText = k.getText();
			if (keyText == null)
			{
				continue;
			}

			if (keyText.equalsIgnoreCase(keyEventText))
			{
				key = k;
				break;
			}
		}
		if (key == null)
		{
			return false;
		}

		//
		// Forward event
		onPOSKeyEvent(key.getId());
		e.consume();

		return true;
	}
}
