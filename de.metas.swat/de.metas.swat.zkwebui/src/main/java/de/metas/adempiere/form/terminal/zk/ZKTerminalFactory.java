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


import java.awt.Font;
import java.util.Properties;

import org.compiere.apps.AppsAction;
import org.zkoss.zk.ui.Component;

import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IKeyLayout;
import de.metas.adempiere.form.terminal.IPropertiesPanel;
import de.metas.adempiere.form.terminal.ITerminalBasePanel;
import de.metas.adempiere.form.terminal.ITerminalButton;
import de.metas.adempiere.form.terminal.ITerminalButtonGroup;
import de.metas.adempiere.form.terminal.ITerminalCheckboxField;
import de.metas.adempiere.form.terminal.ITerminalComboboxField;
import de.metas.adempiere.form.terminal.ITerminalDateField;
import de.metas.adempiere.form.terminal.ITerminalDialog;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKeyDialog;
import de.metas.adempiere.form.terminal.ITerminalKeyListener;
import de.metas.adempiere.form.terminal.ITerminalKeyPanel;
import de.metas.adempiere.form.terminal.ITerminalLabel;
import de.metas.adempiere.form.terminal.ITerminalLoginDialog;
import de.metas.adempiere.form.terminal.ITerminalLookup;
import de.metas.adempiere.form.terminal.ITerminalLookupField;
import de.metas.adempiere.form.terminal.ITerminalNumericField;
import de.metas.adempiere.form.terminal.ITerminalPanel;
import de.metas.adempiere.form.terminal.ITerminalScrollPane;
import de.metas.adempiere.form.terminal.ITerminalSplitPane;
import de.metas.adempiere.form.terminal.ITerminalTable;
import de.metas.adempiere.form.terminal.ITerminalTextField;
import de.metas.adempiere.form.terminal.ITerminalTextPane;
import de.metas.adempiere.form.terminal.NullTerminalKeyListener;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;

/**
 * @author tsa
 *
 */
public class ZKTerminalFactory
		implements ITerminalFactory
{
	private final ITerminalContext tc;

	public ZKTerminalFactory(final ITerminalContext tc)
	{
		this.tc = tc;
	}

	@Override
	public ITerminalContext getTerminalContext()
	{
		return tc;
	}

	public Properties getCtx()
	{
		return tc.getCtx();
	}

	@Override
	public ITerminalButton createButton(final String text)
	{
		final org.adempiere.webui.component.Button button = new org.adempiere.webui.component.Button(text);
		return new ZKTerminalButtonWrapper(getTerminalContext(), button);
	}

	@Override
	public ITerminalButton createButtonAction(final AppsAction action)
	{
		// TODO Auto-generated method stub
		// also consider do change the API, in order to replace 'AppsAction' with something more generic.
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalButton createButtonAction(final String action)
	{
		// ITerminalButton button =
		// AppsAction act = new AppsAction(action, accelerator, false);
		// // act.setDelegate(listener);
		// CButton button = (CButton)act.getButton();
		//
		// button.setPreferredSize(new Dimension(BUTTON_WIDTH, BUTTON_HEIGHT));
		// // button.setMinimumSize(getPreferredSize());
		// // button.setMaximumSize(getPreferredSize());
		// button.setFocusable(false);
		// return new TerminalButtonWrapper(act);
		// TODO
		return null;
	}

	@Override
	public IContainer createContainer()
	{
		return new ZKTerminalContainer(getTerminalContext());
	}

	@Override
	public ITerminalLabel createLabel(final String label)
	{
		return new ZKTerminalLabel(getTerminalContext(), label);
	}

	@Override
	public ITerminalScrollPane createScrollPane(final IComponent content)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout, final ITerminalKeyListener listener)
	{
		return new ZKTerminalKeyPanel(getTerminalContext(), keylayout, listener);
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout)
	{
		return createTerminalKeyPanel(keylayout, NullTerminalKeyListener.instance);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seede.metas.adempiere.form.terminal.ITerminalFactory#createTerminalSubPanel(de.metas.adempiere.form.terminal. ITerminalBasePanel)
	 */
	@Override
	public ITerminalPanel createTerminalSubPanel(final ITerminalBasePanel basePanel)
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.adempiere.form.terminal.ITerminalFactory#createTerminalTable()
	 */
	@Override
	public ITerminalTable createTerminalTable()
	{
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.metas.adempiere.form.terminal.ITerminalFactory#getDefaultFieldFont()
	 */
	@Override
	public Font getDefaultFieldFont()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalLoginDialog createTerminalLoginDialog(final ITerminalBasePanel weightingTerminalPanel)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainer createContainer(final String constraints)
	{
		return new ZKTerminalContainer(getTerminalContext(), constraints);
	}

	@Override
	public IContainer createContainer(final String layoutConstraints, final String colConstraints)
	{
		// return new ZKTerminalContainer(getTerminalContext(), constraints);
		throw new UnsupportedOperationException();
	}

	@Override
	public IContainer createContainer(final String layoutConstraints, final String colConstraints, final String rowConstraints)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalKeyDialog createTerminalKeyDialog(final ITerminalTextField field)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalLookupField createTerminalLookupField(final String title, final ITerminalLookup lookup)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title, final int displayType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void showInfo(final IComponent component, final String title, final String message)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showWarning(final IComponent component, final String title, final Exception e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void showWarning(final IComponent component, final String title, final String message, final Exception e)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public IComponent wrapComponent(final Object component)
	{
		return new ZKTerminalComponentWrapper(getTerminalContext(), (Component)component);
	}

	@Override
	public IContainer wrapContainer(final Object container)
	{
		return new ZKTerminalContainer(getTerminalContext(), (Component)container);
	}

	public static void addChild(final IComponent parent, final IComponent child, final Object constraints)
	{
		final Component c = (Component)parent.getComponent();
		c.appendChild((Component)child.getComponent());
		// TODO: use constraints
	}

	public static void addChildAfter(final IComponent parent, final IComponent component, final IComponent componentBefore, final Object constraints)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Method not implemented");
	}

	public static void removeChild(final IComponent parent, final IComponent child)
	{
		final Component c = (Component)parent.getComponent();
		c.removeChild((Component)child.getComponent());
	}

	@Override
	public IConfirmPanel createConfirmPanel(final boolean withCancel, final String buttonSize)
	{
		// TODO Auto-generated method stub
		return null;
	}

	public static org.zkoss.zk.ui.Component getUI(final IComponent panel)
	{
		return (Component)panel.getComponent();
	}

	@Override
	public ITerminalTextPane createTextPane(final String text)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalSplitPane createSplitPane(final int orientation, final Object left, final Object right)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainer createListContainer()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IContainer createListContainer(final String constraints)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final boolean withButtons, final boolean withLabel)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final boolean withButtons, final boolean withLabel, final String constraints)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalLabel createLabel(final String label, final boolean translate)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title, final int displayType, final float fontSize)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final float fontSize, final boolean withButtons, final boolean withLabel, final String constraints)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ITerminalDialog createModalDialog(final IComponent parent, final String title, final IComponent content)
	{
		throw new UnsupportedOperationException();
	}

	@Override
	public IPropertiesPanel createPropertiesPanel()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout, final String fixedButtonHeight, final String fixedButtonWidth)
	{
		final ITerminalKeyListener listener = NullTerminalKeyListener.instance;
		return new ZKTerminalKeyPanel(getTerminalContext(), keylayout, listener);
	}

	@Override
	public <T> ITerminalTable2<T> createTerminalTable2(final Class<T> beanClass)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalComboboxField createTerminalCombobox(final String name)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalCheckboxField createTerminalCheckbox(final String name)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public int computeStringWidth(final String str, final Font font)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public <T> ITerminalButtonGroup<T> createButtonGroup()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void dispose()
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalFactory copy(final ITerminalContext terminalContext)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IAskDialogBuilder ask(final IComponent parentComponent)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public void executeLongOperation(final IComponent parent, final Runnable runnable)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public ITerminalDateField createTerminalDateField(String name)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}

	@Override
	public IPropertiesPanel createPropertiesPanel(String containerConstraints)
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
	}
}
