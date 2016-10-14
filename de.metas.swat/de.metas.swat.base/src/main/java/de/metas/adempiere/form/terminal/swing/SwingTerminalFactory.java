/**
 *
 */
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.awt.Canvas;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Properties;

import javax.management.ObjectName;
import javax.swing.AbstractButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import org.adempiere.ad.security.IUserRolePermissions;
import org.adempiere.plaf.AdempierePLAF;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.adempiere.util.jmx.JMXRegistry;
import org.adempiere.util.jmx.JMXRegistry.OnJMXAlreadyExistsPolicy;
import org.adempiere.util.proxy.WeakWrapper;
import org.compiere.apps.AppsAction;
import org.compiere.swing.CButton;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;

import de.metas.adempiere.form.IAskDialogBuilder;
import de.metas.adempiere.form.IClientUI;
import de.metas.adempiere.form.terminal.DirectTerminalLoginDialog;
import de.metas.adempiere.form.terminal.IComponent;
import de.metas.adempiere.form.terminal.IConfirmPanel;
import de.metas.adempiere.form.terminal.IContainer;
import de.metas.adempiere.form.terminal.IDisposable;
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
import de.metas.adempiere.form.terminal.ITerminalField;
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
import de.metas.adempiere.form.terminal.TerminalException;
import de.metas.adempiere.form.terminal.WrongValueException;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.adempiere.form.terminal.swing.jmx.JMXSwingTerminalFactory;
import de.metas.adempiere.form.terminal.table.ITerminalTable2;
import de.metas.adempiere.form.terminal.table.swing.SwingTerminalTable2;
import de.metas.logging.LogManager;

/**
 * @author tsa
 *
 */
public class SwingTerminalFactory implements ITerminalFactory
{
	private static final transient Logger logger = LogManager.getLogger(SwingTerminalFactory.class);

	//
	// Services
	private final transient IClientUI clientUI = Services.get(IClientUI.class);

	/** Button Width = 50 */
	private static final int BUTTON_WIDTH = 50;
	/** Button Height = 50 */
	private static final int BUTTON_HEIGHT = 50;

	public static final String BUTTON_Constraints = "w 50!, h 50!,";

	public static final int SCROLL_Size = 30;

	// NOTE: because terminalContext is referencing this terminalFactory, and this terminalFactory is referencing the terminalContext
	// we store the terminalContext as weak reference to avoid memory leaks
	private final ITerminalContext terminalContext;

	private boolean migLayoutDebugEnabled = false;

	private ObjectName jmxObjectName;

	public SwingTerminalFactory(final ITerminalContext terminalContext)
	{
		super();

		Check.assumeNotNull(terminalContext, "terminalContext not null");
		this.terminalContext = WeakWrapper.asWeak(terminalContext, ITerminalContext.class);

		final JMXSwingTerminalFactory jmxBean = new JMXSwingTerminalFactory(this);
		jmxObjectName = JMXRegistry.get().registerJMX(jmxBean, OnJMXAlreadyExistsPolicy.Replace);
	}

	@Override
	public final ITerminalFactory copy(final ITerminalContext terminalContext)
	{
		final SwingTerminalFactory terminalFactoryNew = new SwingTerminalFactory(terminalContext);
		terminalFactoryNew.setMigLayoutDebugEnabled(isMigLayoutDebugEnabled());
		return terminalFactoryNew;
	}

	@Override
	protected void finalize() throws Throwable
	{
		dispose();
	}

	private final void unregisterJMX()
	{
		if (jmxObjectName != null)
		{
			JMXRegistry.get().unregisterJMX(jmxObjectName, false); // failOnError=false
			jmxObjectName = null;
		}
	}

	@Override
	public final ITerminalContext getTerminalContext()
	{
		return terminalContext;
	}

	public boolean isMigLayoutDebugEnabled()
	{
		return migLayoutDebugEnabled;
	}

	public void setMigLayoutDebugEnabled(final boolean migLayoutDebugEnabled)
	{
		this.migLayoutDebugEnabled = migLayoutDebugEnabled;
	}

	@Override
	public ITerminalButton createButton(final String text)
	{
		final AbstractButton buttonSwing = new SwingTerminalButton(text);
		buttonSwing.setText(text);
		final ITerminalButton button = new SwingTerminalButtonWrapper(getTerminalContext(), buttonSwing);

		addToDisposableComponents(button);
		return button;
	}

	@Override
	public <T> ITerminalButtonGroup<T> createButtonGroup()
	{
		final SwingTerminalButtonGroup<T> buttonGroup = new SwingTerminalButtonGroup<T>(this);

		// addToDisposableComponents(buttonGroup); done within the constructor
		return buttonGroup;
	}

	@Override
	public ITerminalButton createButtonAction(final String action)
	{
		final KeyStroke accelerator = null;
		return createButtonAction(action, accelerator);
	}

	@Override
	public ITerminalButton createButtonAction(final AppsAction act)
	{
		// act.setDelegate(listener);
		final CButton button = (CButton)act.getButton();
		button.setPreferredSize(new Dimension(SwingTerminalFactory.BUTTON_WIDTH, SwingTerminalFactory.BUTTON_HEIGHT));
		// button.setMinimumSize(getPreferredSize());
		// button.setMaximumSize(getPreferredSize());
		button.setFocusable(false);

		final SwingTerminalButtonWrapper buttonWrapper = new SwingTerminalButtonWrapper(getTerminalContext(), act);

		addToDisposableComponents(buttonWrapper);
		return buttonWrapper;
	} //

	public ITerminalButton createButtonAction(final String action, final KeyStroke accelerator)
	{
		final AppsAction act = AppsAction.builder()
				.setAction(action)
				.setAccelerator(accelerator)
				.build();

		// TODO: addToCreatedComponents(act);
		return createButtonAction(act);
	} // getButtonAction

	public static void addChild(final IComponent parent, final IComponent child, final Object constraints)
	{
		final Container c = (Container)parent.getComponent();
		c.add((Component)child.getComponent(), constraints);
	}

	public static void addChild(final IComponent parent, final IComponent child, final String name, final Object constraints)
	{
		final Container c = (Container)parent.getComponent();
		c.add((Component)child.getComponent(), constraints);
		c.setName(name);
	}

	public static void addChildAfter(final IComponent parent, final IComponent child, final IComponent childBefore, final Object constraints)
	{
		final Container parentComp = (Container)parent.getComponent();
		final Component childBeforeComp = (Component)childBefore.getComponent();
		final Component childComp = (Component)child.getComponent();

		final int compCount = parentComp.getComponentCount();

		// NOTE: we are iterating until the last component from "parent" (exclusive).
		// if "childBefore" it's the last component, we will add our "child" at the end of container anyway
		for (int i = 0; i < compCount - 1; i++)
		{
			final Component comp = parentComp.getComponent(i);
			if (Util.same(comp, childBeforeComp))
			{
				final int childBeforeIndex = i;
				final int childIndex = childBeforeIndex + 1;
				parentComp.add(childComp, constraints, childIndex);

				// Child was added, stop here are return
				return;
			}
		}

		//
		// childBefore component was not found or it's the last component
		// we are adding "child" as last component in the "parent"
		parentComp.add(childComp, constraints);
	}

	public static void removeChild(final IComponent parent, final IComponent child)
	{
		if (child == null)
		{
			return;
		}

		final Container parentComp = (Container)parent.getComponent();
		final Component childComp = getUI(child);

		// NOTE: we are doing this because in some cases (don't know when and how)
		// we end in an infinite loop at java.awt.EventQueue.removeSourceEvents(Object, boolean)
		// when: removeAllEvents=false
		// and following lines are executed:
		// prev = entry;
		// and then
		// entry = entry.next;
		childComp.removeNotify();

		parentComp.remove(childComp);

	}

	public static final Container getUI(final IContainer container)
	{
		return (Container)container.getComponent();
	}

	public static final Component getUI(final IComponent component)
	{
		return (Component)component.getComponent();
	}

	@Override
	public IContainer createContainer()
	{
		final SwingTerminalContainer container = new SwingTerminalContainer(this);

		addToDisposableComponents(container);
		return container;
	}

	@Override
	public IContainer createContainer(final String constraints)
	{
		final SwingTerminalContainer container = new SwingTerminalContainer(this, constraints);

		// addToDisposableComponents(container); already called by the constructor
		return container;
	}

	@Override
	public IContainer createContainer(final String layoutConstraints, final String colConstraints)
	{
		final SwingTerminalContainer container = new SwingTerminalContainer(this, layoutConstraints, colConstraints);

		// addToDisposableComponents(container); already called by the constructor
		return container;
	}

	@Override
	public IContainer createContainer(final String layoutConstraints, final String colConstraints, final String rowConstraints)
	{
		final SwingTerminalContainer container = new SwingTerminalContainer(this, layoutConstraints, colConstraints, rowConstraints);

		// addToDisposableComponents(container); already called by the constructor
		return container;
	}

	@Override
	public IContainer createListContainer()
	{
		final SwingListTerminalContainer container = new SwingListTerminalContainer(getTerminalContext());

		addToDisposableComponents(container);
		return container;
	}

	@Override
	public IContainer createListContainer(final String constraints)
	{
		final SwingListTerminalContainer container = new SwingListTerminalContainer(getTerminalContext(), constraints);

		// addToDisposableComponents(container); already done by constructor
		return container;
	}

	@Override
	public ITerminalLabel createLabel(final String label)
	{
		final ITerminalLabel terminalLabel = createLabel(label, true);

		addToDisposableComponents(terminalLabel);
		return terminalLabel;
	}

	@Override
	public ITerminalLabel createLabel(final String label, final boolean translate)
	{
		final SwingTerminalLabel terminalLabel = new SwingTerminalLabel(getTerminalContext(), label, translate);

		// addToDisposableComponents(terminalLabel); already done within the constructor
		return terminalLabel;
	}

	public Properties getCtx()
	{
		return getTerminalContext().getCtx();
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title)
	{
		final SwingTerminalTextField field = new SwingTerminalTextField(getTerminalContext(), title);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title, final int displayType)
	{
		final SwingTerminalTextField field = new SwingTerminalTextField(getTerminalContext(), title, displayType);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalTextField createTerminalTextField(final String title, final int displayType, final float fontSize)
	{
		final SwingTerminalTextField field = new SwingTerminalTextField(getTerminalContext(), title, displayType, fontSize);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout, final ITerminalKeyListener listener)
	{
		final de.metas.adempiere.form.terminal.swing.SwingKeyLayoutPanel keyLayoutPanel = new de.metas.adempiere.form.terminal.swing.SwingKeyLayoutPanel(getTerminalContext(), keylayout, listener);

		// addToDisposableComponents(keyLayoutPanel); already done within the constructor
		return keyLayoutPanel;
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout)
	{
		return createTerminalKeyPanel(keylayout, NullTerminalKeyListener.instance);
	}

	@Override
	public ITerminalKeyPanel createTerminalKeyPanel(final IKeyLayout keylayout, final String fixedButtonHeight, final String fixedButtonWidth)
	{
		final ITerminalKeyListener listener = NullTerminalKeyListener.instance;
		final de.metas.adempiere.form.terminal.swing.SwingKeyLayoutPanel keyLayoutPanel = new de.metas.adempiere.form.terminal.swing.SwingKeyLayoutPanel(getTerminalContext(), keylayout, listener,
				fixedButtonHeight, fixedButtonWidth);

		addToDisposableComponents(keyLayoutPanel);
		return keyLayoutPanel;
	}

	@Override
	public ITerminalScrollPane createScrollPane(final IComponent content)
	{
		final SwingTerminalScrollPane scrollPane = new SwingTerminalScrollPane(content);

		// addToDisposableComponents(scrollPane); done within the constructor
		return scrollPane;
	}

	@Override
	public Font getDefaultFieldFont()
	{
		final Font stdFont = AdempierePLAF.getFont_Field();
		return stdFont;
	}

	@Override
	public ITerminalLookupField createTerminalLookupField(final String title, final ITerminalLookup lookup)
	{
		final SwingTerminalLookupField field = new SwingTerminalLookupField(getTerminalContext(), title, lookup);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType)
	{
		return createTerminalNumericField(name, displayType, true, true);
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final boolean withButtons, final boolean withLabel)
	{
		final SwingTerminalNumericField field = new SwingTerminalNumericField(getTerminalContext(), name, displayType, withButtons, withLabel);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final boolean withButtons, final boolean withLabel, final String constraints)
	{
		final SwingTerminalNumericField field = new SwingTerminalNumericField(getTerminalContext(), name, displayType, withButtons, withLabel, constraints);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalNumericField createTerminalNumericField(final String name, final int displayType, final float fontSize, final boolean withButtons, final boolean withLabel, final String constraints)
	{
		final SwingTerminalNumericField field = new SwingTerminalNumericField(getTerminalContext(), name, displayType, fontSize, withButtons, withLabel, constraints);

		addToDisposableComponents(field);
		return field;
	}

	@Override
	public ITerminalKeyDialog createTerminalKeyDialog(final ITerminalTextField field)
	{
		final SwingTerminalKeyDialog dialog = new SwingTerminalKeyDialog(field);

		// addToDisposableComponents(dialog); already done within the constructor
		return dialog;
	}

	@Override
	public ITerminalTable createTerminalTable()
	{
		final TerminalTable terminalTable = new TerminalTable(getTerminalContext());

		// addToDisposableComponents(terminalTable); already done within the constructor
		return terminalTable;
	}

	@Override
	public ITerminalPanel createTerminalSubPanel(final ITerminalBasePanel basePanel)
	{
		final TerminalSubPanelAdapter terminalSubPanel = new TerminalSubPanelAdapter(basePanel);

		addToDisposableComponents(terminalSubPanel);
		return terminalSubPanel;
	}

	@Override
	public void showInfo(final IComponent component, final String title, final String message)
	{
		final int windowNo = getWindowNo(component);
		clientUI.info(windowNo, title, message);
	}

	private String toString(final Throwable e)
	{
		Check.assumeNotNull(e, "exception not null");

		final StringBuilder msg = new StringBuilder();

		if (e instanceof WrongValueException)
		{
			final WrongValueException wve = (WrongValueException)e;
			final ITerminalField<?> c = wve.getTerminalField();
			msg.append(Services.get(IMsgBL.class).translate(getCtx(), c.getName()));

			String emsg = e.getLocalizedMessage();
			if (emsg != null)
			{
				emsg = emsg.trim();
			}
			if (emsg != null && emsg.endsWith(":"))
			{
				emsg = emsg.substring(0, emsg.length() - 1);
			}

			msg.append(" - ").append(emsg);
		}
		else
		{
			String exceptionStr = e.getLocalizedMessage();
			if (exceptionStr == null || exceptionStr.length() <= 4)
			{
				// Case: exception message was null or returned string "null" => display the whole exception including it's classname.
				exceptionStr = e.toString();
			}

			logger.warn(exceptionStr, e);
			msg.append(exceptionStr);
		}

		return msg.toString();
	}

	@Override
	public void showWarning(final IComponent component, final String title, final Throwable e)
	{
		final String message = null;
		showWarning(component, title, message, e);
	}

	@Override
	public void showWarning(final IComponent component, final String title, final String message, final Throwable e)
	{
		final StringBuilder msg = new StringBuilder();
		if (e != null)
		{
			final String exceptionStr = toString(e);
			msg.append(exceptionStr);
		}

		if (!Check.isEmpty(message, true))
		{
			if (msg.length() > 0)
			{
				msg.append(" - ");
			}
			msg.append(message);
		}

		final int windowNo = getWindowNo(component);
		clientUI.warn(windowNo, "Error", msg.toString());
	}

	@Override
	public IAskDialogBuilder ask(final IComponent parentComponent)
	{
		final Container awtContainer = getAWTContainer(parentComponent);
		final int windowNo = Env.getWindowNo(awtContainer);
		return clientUI.ask()
				.setParentWindowNo(windowNo)
				.setParentComponent(awtContainer);
	}

	private final int getWindowNo(final IComponent component)
	{
		final Container awtContainer = getAWTContainer(component);
		final int windowNo = Env.getWindowNo(awtContainer);
		return windowNo;
	}

	/**
	 * Gets AWT {@link Container} for given component.
	 *
	 * @param c component
	 * @return AWT container or null
	 */
	public static Container getAWTContainer(final IComponent c)
	{
		if (c == null)
		{
			return null;
		}
		final Component component = getUI(c);
		return component.getParent();
	}

	public static JFrame getFrame(final IComponent c)
	{
		final Component component = getUI(c);
		return getFrame(component.getParent());
	}

	public static JFrame getFrame(final IContainer container)
	{
		return getFrame(container.getComponent());
	}

	public static JFrame getFrame(final Object container)
	{
		if (container instanceof Container)
		{
			return Env.getFrame((Container)container);
		}
		else
		{
			return null;
		}
	}

	@Override
	public ITerminalLoginDialog createTerminalLoginDialog(final ITerminalBasePanel parent)
	{
		final IUserRolePermissions role = Env.getUserRolePermissions();
		final ITerminalLoginDialog dialog;
		if (role.getStartup_AD_Form_ID() > 0)
		{
			// Case: user logged in and it has a form to be shown instead of standard main window (see AMenu)
			dialog = new DirectTerminalLoginDialog();
		}
		else
		{
			dialog = new SwingTerminalLoginDialog(parent);
		}

		addToDisposableComponents(dialog);
		return dialog;
	}

	@Override
	public SwingTerminalComponentWrapper wrapComponent(final Object component)
	{
		final SwingTerminalComponentWrapper componentWrapper = new SwingTerminalComponentWrapper(getTerminalContext(), (Component)component);

		// addToDisposableComponents(componentWrapper); already done in the constructor
		return componentWrapper;
	}

	@Override
	public IContainer wrapContainer(final Object container)
	{
		final JPanel containerSwing = SwingTerminalContainer.castPanelComponent(container);
		final SwingTerminalContainer terminalContainer = new SwingTerminalContainer(this, containerSwing);

		// addToDisposableComponents(terminalContainer); already called by the constructor
		return terminalContainer;
	}

	@Override
	public IConfirmPanel createConfirmPanel(final boolean withCancel, final String buttonSize)
	{
		final SwingConfirmPanel confirmPanel = new SwingConfirmPanel(getTerminalContext(), withCancel, buttonSize);

		addToDisposableComponents(confirmPanel);
		return confirmPanel;
	}

	@Override
	public ITerminalTextPane createTextPane(final String text)
	{
		final SwingTerminalTextPane textPane = new SwingTerminalTextPane(getTerminalContext(), text);

		// addToDisposableComponents(textPane); already done within the constructor
		return textPane;
	}

	@Override
	public ITerminalSplitPane createSplitPane(final int orientation, final Object left, final Object right)
	{
		final TerminalSplitPane splitPane = new TerminalSplitPane(getTerminalContext(), orientation, (Component)left, (Component)right);

		addToDisposableComponents(splitPane);
		return splitPane;
	}

	@Override
	public ITerminalDialog createModalDialog(
			final IComponent parent,
			final String title,
			final IComponent content)
	{
		final SwingTerminalDialog dialog = new SwingTerminalDialog(this, parent, content);
		dialog.setTitle(title);

		// only register if it does not maintain its own references, because otherwise, we would add 'dialog' to its own contextReferences which it just created in its constructor.
		// that would lead to a stack overflow on dispose. Also check out the SwingTerminalDialog constructor for details.
		addToDisposableComponents(dialog);

		return dialog;
	}

	@Override
	public IPropertiesPanel createPropertiesPanel()
	{
		final String containerConstraints = null;// use default constraints
		return createPropertiesPanel(containerConstraints);
	}

	@Override
	public IPropertiesPanel createPropertiesPanel(final String containerConstraints)
	{
		final SwingPropertiesPanel panel;
		if (containerConstraints == null)
		{
			panel = new SwingPropertiesPanel(getTerminalContext());
		}
		else
		{
			panel = new SwingPropertiesPanel(getTerminalContext(), containerConstraints);
		}

		// addToDisposableComponents(panel); already done within the constructor
		return panel;
	}

	/**
	 * NOTE: this is useful when after changing the component content the UI is not refreshed.
	 *
	 * It actually does:
	 * <ul>
	 * <li>re-validate the component (see {@link Component#invalidate()}, {@link Component#validate()})
	 * <li>force component repaint with a timeout of 50ms (see {@link Component#repaint(long)})
	 * </ul>
	 *
	 * @param comp
	 * @see http://stackoverflow.com/questions/9169350/dynamically-removing-component-from-jpanel
	 */
	public static void refresh(final IComponent comp)
	{
		if (comp == null)
		{
			return;
		}

		final Object compImpl = comp.getComponent();
		if (compImpl instanceof Component)
		{
			final Component compSwing = (Component)compImpl;
			compSwing.invalidate();
			compSwing.validate();
			compSwing.repaint(50L); // timeout before update (millis)
		}
	}

	@Override
	public <T> ITerminalTable2<T> createTerminalTable2(final Class<T> beanClass)
	{
		final SwingTerminalTable2<T> terminalTable = new SwingTerminalTable2<T>(getTerminalContext());

		addToDisposableComponents(terminalTable);
		return terminalTable;
	}

	@Override
	public ITerminalComboboxField createTerminalCombobox(final String name)
	{
		final SwingTerminalComboboxField comboboxField = new SwingTerminalComboboxField(getTerminalContext(), name);

		addToDisposableComponents(comboboxField);
		return comboboxField;
	}

	@Override
	public ITerminalCheckboxField createTerminalCheckbox(final String name)
	{
		final SwingTerminalCheckboxField checkboxField = new SwingTerminalCheckboxField(getTerminalContext(), name);

		addToDisposableComponents(checkboxField);
		return checkboxField;
	}

	/**
	 * Canvas component to be used when retrieving {@link FontMetrics}.
	 */
	private final Canvas fontMetricsProvider = new Canvas();

	@Override
	public int computeStringWidth(final String str, final Font font)
	{
		if (str == null || str.isEmpty())
		{
			return 0;
		}

		final FontMetrics fontMetrics = fontMetricsProvider.getFontMetrics(font);
		final int width = fontMetrics.stringWidth(str);
		return width;
	}

	/* package */final String createMigLayoutConstraints(final String layoutConstraints)
	{
		if (!isMigLayoutDebugEnabled())
		{
			return layoutConstraints;
		}

		if (layoutConstraints == null)
		{
			return "debug";
		}

		final StringBuilder layoutConstraintsNew = new StringBuilder(layoutConstraints.trim());
		// NOTE: MigLayout is smart enough to handle the case when "debug" option is specified multiple times or double, triple etc commas.
		layoutConstraintsNew.append(", debug, ");
		return layoutConstraintsNew.toString();
	}

	private final void addToDisposableComponents(final IDisposable comp)
	{
		if (comp == null)
		{
			return;
		}
		if (_disposed || _disposing)
		{
			throw new TerminalException("Cannot create a new component because factory is disposed");
		}

		getTerminalContext().addToDisposableComponents(comp);
	}

	private boolean _disposed = false;
	private boolean _disposing = false;

	@Override
	public final void dispose()
	{
		if (_disposed || _disposing)
		{
			return;
		}

		_disposing = true;
		try
		{

			//
			// Unregister JMX if any
			unregisterJMX();
		}
		finally
		{
			_disposing = false;
			_disposed = true;
		}
	}

	@Override
	public void executeLongOperation(final IComponent parent, final Runnable runnable)
	{
		final Component parentSwing = parent == null ? null : getUI(parent);
		clientUI.executeLongOperation(parentSwing, runnable);

	}

	@Override
	public ITerminalDateField createTerminalDateField(final String name)
	{
		final SwingTerminalDateField field = new SwingTerminalDateField(getTerminalContext(), name);

		addToDisposableComponents(field);
		return field;
	}
}
