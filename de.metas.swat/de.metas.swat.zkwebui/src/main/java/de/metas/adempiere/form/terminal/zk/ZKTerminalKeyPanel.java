/**
 * 
 */
package de.metas.adempiere.form.terminal.zk;

/*
 * #%L
 * de.metas.swat.zkwebui
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


import java.awt.Color;

import org.adempiere.webui.component.Cardlayout;

import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.TerminalKeyPanel;
import de.metas.adempiere.form.terminal.context.ITerminalContext;

/**
 * @author tsa
 * 
 */
public class ZKTerminalKeyPanel
		extends TerminalKeyPanel
		implements IComponentZK
{
	private final String POSKEYBUTTON_Constraints = "h 50, w 50, growx, growy, sg button,";

	private IContainer panel;
	private Cardlayout cardLayout;

	// private Border keyBorderDefault = null;
	// private Border keyBorderSelected = null;

	protected ZKTerminalKeyPanel(final ITerminalContext tc, final IKeyLayout keyLayout, final ITerminalKeyListener caller)
	{
		super(tc, keyLayout, caller);
	}

	@Override
	protected void init()
	{
		final ITerminalFactory factory = getTerminalFactory();

		cardLayout = new Cardlayout();
		final IContainer c = getTerminalFactory().createContainer();
		// SwingTerminalFactory.getUI(c).setLayout(cardLayout);
		ZKTerminalFactory.getUI(c).appendChild(cardLayout);
		panel = factory.wrapContainer(cardLayout);

		// keyBorderDefault = BorderFactory.createEmptyBorder();

		super.init();
	}

	@Override
	protected IContainer createPOSKeyContent(final int cols)
	{
		return getTerminalFactory().createContainer("fill, wrap " + Math.max(cols, 3));
	}

	@Override
	protected IComponent createPOSKeyComponent(final IContainer content)
	{
		final ITerminalScrollPane scroll = getTerminalFactory().createScrollPane(content);
		// scroll.setPreferredSize(new Dimension( 600 - 20, 400-20));

		final IContainer card = getTerminalFactory().createContainer();
		// CPanel card = new CPanel();
		// card.setLayout(new MigLayout("fill, ins 0"));
		card.add(scroll, "growx, growy");

		return card;
	}

	@Override
	protected void addPOSKeyButtonEmpty(final IContainer content)
	{
		final ITerminalButton button = getTerminalFactory().createButton("");
		button.setFocusable(false);
		button.setEnabled(false);
		content.add(button, POSKEYBUTTON_Constraints);
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
		content.add(button, constraints);
		// System.out.println("Adding "+button+", "+constraints);
		// content.add(new TerminalLabel("button"), constraints);
		return size;
	}

	@Override
	public org.zkoss.zk.ui.Component getComponent()
	{
		return ZKTerminalFactory.getUI(panel);
	}

	@Override
	protected void addPOSKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo)
	{
		final IKeyLayout keyLayout = keyLayoutInfo.getKeyLayout();
		final String keyLayoutId = keyLayout.getId();
		final IComponent keyLayoutComp = keyLayoutInfo.getKeyLayoutComponent();
		panel.add(keyLayoutComp, keyLayoutId);
	}

	@Override
	protected void removeKeyLayoutUI(final KeyLayoutInfo keyLayoutInfo)
	{
		final IComponent keyLayoutComp = keyLayoutInfo.getKeyLayoutComponent();
		panel.remove(keyLayoutComp);
	}

	@Override
	protected void showKeyKayoutUI(final IKeyLayout keyLayout)
	{
		// cardLayout.show(SwingTerminalFactory.getUI(panel), keyLayout.getId());
	}

	@Override
	public void setKeySelectionColor(final Color color)
	{
		super.setKeySelectionColor(color);
		// keyBorderSelected = null;
	}

	// private Border getKeyBorderSelected()
	// {
	// if (keyBorderSelected == null)
	// keyBorderSelected = BorderFactory.createLineBorder(getKeySelectionColor(), 4);
	// return keyBorderSelected;
	// }

	@Override
	protected void selectButtonUI(final ITerminalButton button)
	{
		// setBorder(button, getKeyBorderSelected());
	}

	@Override
	protected void unselectButtonUI(final ITerminalButton button)
	{
		// setBorder(button, keyBorderDefault);
	}
	// private void setBorder(ITerminalButton button, Border border)
	// {
	// JComponent c = (JComponent)ZKTerminalFactory.getUI(button);
	// c.setBorder(border);
	// }
}
