package de.metas.adempiere.form.terminal;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Font;

import org.compiere.apps.AppsAction;

import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;

/**
 * Whatever is created by an implementor will be added to the terminal context's disposable components.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ITerminalFactory
{
	String TITLE_INTERNAL_ERROR = "InternalError";
	String TITLE_ERROR = "Error";
	String TITLE_WARN = "Warning";

	IContainer createContainer();

	IContainer createContainer(String constraints);

	IContainer createContainer(String layoutConstraints, String colConstraints);

	IContainer createContainer(String layoutConstraints, String colConstraints, String rowConstraints);

	IContainer createListContainer();

	IContainer createListContainer(String constraints);

	ITerminalLabel createLabel(String label);

	/**
	 *
	 * @param label
	 * @param translate
	 * @param translate true if label needs to be translated (so it's not already translated)
	 */
	ITerminalLabel createLabel(String label, boolean translate);

	ITerminalTextPane createTextPane(String text);

	ITerminalSplitPane createSplitPane(int orientation, Object left, Object right);

	ITerminalTextField createTerminalTextField(String title);

	ITerminalTextField createTerminalTextField(String title, int displayType);

	ITerminalTextField createTerminalTextField(String title, int displayType, float fontSize);

	ITerminalButton createButton(String text);

	/**
	 * Creates an {@link AppsAction} for the given action command and calls {@link #createButtonAction(AppsAction)}.
	 *
	 * @param action
	 * @return
	 */
	ITerminalButton createButtonAction(String action);

	/**
	 * Creates a terminal botton for the given <code>action</code>.
	 *
	 * @param action
	 * @return
	 */
	ITerminalButton createButtonAction(AppsAction action);

	<T> ITerminalButtonGroup<T> createButtonGroup();

	ITerminalComboboxField createTerminalCombobox(String name);

	ITerminalCheckboxField createTerminalCheckbox(String name);

	/**
	 * Create a panel for the given <code>keylayout</code>.
	 *
	 * @param keylayout
	 * @param listener
	 * @return
	 */
	ITerminalKeyPanel createTerminalKeyPanel(IKeyLayout keylayout, ITerminalKeyListener listener);

	/**
	 * Analog to {@link #createTerminalKeyPanel(IKeyLayout, ITerminalKeyListener)}, but with a {@link NullTerminalKeyListener}.
	 *
	 * @param keylayout
	 * @return
	 */
	ITerminalKeyPanel createTerminalKeyPanel(IKeyLayout keylayout);

	ITerminalScrollPane createScrollPane(IComponent content);

	Font getDefaultFieldFont();

	ITerminalLookupField createTerminalLookupField(String title, ITerminalLookup lookup);

	ITerminalNumericField createTerminalNumericField(String name, int displayType);

	ITerminalNumericField createTerminalNumericField(String name, int displayType, boolean withButtons, boolean withLabel);

	ITerminalKeyDialog createTerminalKeyDialog(ITerminalTextField field);

	ITerminalTable createTerminalTable();

	ITerminalPanel createTerminalSubPanel(ITerminalBasePanel basePanel);

	void showInfo(IComponent component, String title, String message);

	void showWarning(IComponent component, String title, Throwable e);

	void showWarning(IComponent component, String title, String message, Throwable e);

	/**
	 * Starts asking user a question.
	 *
	 * @param parentComponent
	 * @return ask builder
	 */
	IAskDialogBuilder ask(final IComponent parentComponent);

	ITerminalLoginDialog createTerminalLoginDialog(ITerminalBasePanel weightingTerminalPanel);

	/**
	 * Wrap UI component
	 *
	 * @param component
	 * @return
	 */
	IComponent wrapComponent(Object component);

	IContainer wrapContainer(Object container);

	ITerminalContext getTerminalContext();

	IConfirmPanel createConfirmPanel(boolean withCancel, String buttonSize);

	/**
	 *
	 * @param name
	 * @param displayType
	 * @param withButtons display plus/minus buttons
	 * @param withLabel display field label above plus/minus buttons
	 * @param constraints plus/minus buttons constraints
	 * @return
	 */
	ITerminalNumericField createTerminalNumericField(String name, int displayType, boolean withButtons, boolean withLabel, String constraints);

	/**
	 *
	 * @param name
	 * @param displayType
	 * @param fontSize font size to be used
	 * @param withButtons display plus/minus buttons
	 * @param withLabel display field label above plus/minus buttons
	 * @param constraints plus/minus buttons constraints
	 * @return
	 */
	ITerminalNumericField createTerminalNumericField(String name, int displayType, float fontSize, boolean withButtons, boolean withLabel, String constraints);

	/**
	 * Creates a modal dialog for the given <code>content</code> component.
	 * <p>
	 * <b>IMPORTANT:</b> you will most probably want to put this invocation into a <code>try-with-resources</code> block with {@link ITerminalContext#newReferences()},
	 * and the given <code>content</code> as well as its model shall probably also be within that same block.
	 *
	 * @return
	 */
	ITerminalDialog createModalDialog(IComponent parent, String title, IComponent content);

	IPropertiesPanel createPropertiesPanel();

	IPropertiesPanel createPropertiesPanel(String containerConstraints);

	/**
	 * Creates terminal key panel with fixed button height/width. If only one is desired, set the other to -1.
	 *
	 * @param keylayout
	 * @param fixedButtonHeight
	 * @param fixedButtonWidth
	 * @return
	 */
	ITerminalKeyPanel createTerminalKeyPanel(IKeyLayout keylayout, String fixedButtonHeight, String fixedButtonWidth);

	<T> ITerminalTable2<T> createTerminalTable2(Class<T> beanClass);

	/**
	 * Returns the total advance width for showing the specified <code>String</code> in this <code>Font</code>. The advance is the distance from the leftmost point to the rightmost point on the
	 * string's baseline.
	 *
	 * @param str
	 * @param font
	 * @return the advance width of the specified <code>String</code> in the <code>Font</code> described by this
	 */
	int computeStringWidth(String str, Font font);

	void dispose();

	ITerminalFactory copy(ITerminalContext terminalContext);

	/**
	 * Execute <code>runnable</code> while displaying "loading" cursor. Implementations may open a separate thread for this.
	 *
	 * @param component
	 * @param runnable
	 */
	void executeLongOperation(IComponent parent, Runnable runnable);

	ITerminalDateField createTerminalDateField(String name);
}
