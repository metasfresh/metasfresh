/******************************************************************************
 * Product: Adempiere ERP & CRM Smart Business Solution                       *
 * Copyright (C) 2007 Adempiere, Inc. All Rights Reserved.                    *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/
package org.compiere.swing;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.TableColumnModelEvent;
import javax.swing.event.TableColumnModelListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import org.jdesktop.swingx.VerticalLayout;
import org.jdesktop.swingx.action.AbstractActionExt;
import org.jdesktop.swingx.action.ActionContainerFactory;
import org.jdesktop.swingx.table.ColumnControlPopup;

/**
 * Code and description adapted from SwingX ColumnControlButton class.
 * 
 * A component to allow interactive customization of <code>CTable</code>'s columns. It's main purpose is to allow toggling of table columns' visibility. Additionally, arbitrary configuration actions
 * can be exposed.
 * <p>
 * 
 * This component is installed in the <code>CTable</code>'s trailing corner, if enabled:
 * 
 * <pre>
 * <code>
 * table.setColumnControlVisible(true);
 * </code>
 * </pre>
 * 
 * From the perspective of a <code>CTable</code>, the component's behaviour is opaque. Typically, the button's action is to popup a component for user interaction.
 * <p>
 * 
 * This class is responsible for handling/providing/updating the lists of actions and to keep all action's state in synch with Table-/Column state. The visible behaviour of the popup is delegated to a
 * <code>ColumnControlPopup</code>.
 * <p>
 * 
 * @see CTable#setColumnControl
 * 
 */
public class CColumnControlButton extends JButton
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3052540263336100861L;
	/** Marker to auto-recognize actions which should be added to the popup. */
	public static final String COLUMN_CONTROL_MARKER = "column.";
	private DefaultColumnControlPopup popup;
	private boolean popupStaled = true;
	// TODO: the table reference is a potential leak?
	/** The table which is controlled by this. */
	private CTable table;
	/** Listener for table property changes. */
	private PropertyChangeListener tablePropertyChangeListener;
	/** Listener for table's columnModel. */
	private TableColumnModelListener columnModelListener;
	/** the list of actions for column menuitems. */
	private List<ColumnVisibilityAction> columnVisibilityActions;

	/**
	 * Creates a column control button for the table. The button uses the given icon and has no text.
	 * 
	 * @param table the <code>JTable</code> controlled by this component
	 * @param icon the <code>Icon</code> to show
	 */
	/* package */CColumnControlButton(final CTable table, final Icon icon)
	{
		super();
		init();
		// JW: icon LF dependent?
		setAction(createControlAction(icon));
		installTable(table);
	}

	@Override
	public void updateUI()
	{
		super.updateUI();
		// JW: icon LF dependent?
		setMargin(new Insets(1, 2, 2, 1)); // Make this LAF-independent
		getColumnControlPopup().updateUI();
	}

	/**
	 * Toggles the popup component's visibility. This method is called by this control's default action.
	 * <p>
	 * 
	 * Here: delegates to getControlPopup().
	 */
	public void togglePopup()
	{
		togglePopup(this);
	}
	
	public void togglePopup(final JComponent owner)
	{
		final ColumnControlPopup popup = getColumnControlPopup();
		popup.toggleVisibility(owner);
	}

	@Override
	public void applyComponentOrientation(final ComponentOrientation o)
	{
		super.applyComponentOrientation(o);
		getColumnControlPopup().applyComponentOrientation(o);
	}

	// -------------------------- Action in synch with column properties
	/**
	 * A specialized <code>Action</code> which takes care of keeping in synch with TableColumn state.
	 * 
	 * NOTE: client must call releaseColumn if this action is no longer needed!
	 * 
	 */
	public class ColumnVisibilityAction extends AbstractActionExt
	{

		/**
		 * 
		 */
		private static final long serialVersionUID = -5843080042335689992L;

		private TableColumn column;

		private PropertyChangeListener columnListener;

		/**
		 * flag to distinguish selection changes triggered by column's property change from those triggered by user interaction. Hack around #212-swingx.
		 */
		private boolean fromColumn;

		/**
		 * Creates a action synched to the table column.
		 * 
		 * @param column the <code>TableColumn</code> to keep synched to.
		 */
		public ColumnVisibilityAction(final TableColumn column)
		{
			super((String)null);
			setStateAction();
			installColumn(column);
		}

		/**
		 * Releases all references to the synched <code>TableColumn</code>. Client code must call this method if the action is no longer needed. After calling this action must not be used any longer.
		 */
		public void releaseColumn()
		{
			column.removePropertyChangeListener(columnListener);
			column = null;
		}

		@Override
		public void itemStateChanged(final ItemEvent e)
		{
			if ((e.getStateChange() == ItemEvent.DESELECTED)
					// JW: guarding against 1 leads to #212-swingx: setting
					// column visibility programatically fails if
					// the current column is the second last visible
					// guarding against 0 leads to hiding all columns
					// by deselecting the menu item.
					&& (table.getColumnCount() <= 1)
					// JW Fixed #212: basically implemented Rob's idea to distinguish
					// event sources instead of unconditionally reselect
					// not entirely sure if the state transitions are completely
					// defined but all related tests are passing now.
					&& !fromColumn)
			{
				reselect();
			}
			else
			{
				setSelected(e.getStateChange() == ItemEvent.SELECTED);
			}
		}

		@Override
		public synchronized void setSelected(final boolean newValue)
		{
			super.setSelected(newValue);

			disableColumnModelChangeSyncAndRun(new Runnable()
			{
				@Override
				public void run()
				{
					table.setColumnVisibility(column, newValue);
				}
			});
		}

		/**
		 * Does nothing. Synch from action state to TableColumn state is done in itemStateChanged.
		 */
		@Override
		public void actionPerformed(ActionEvent e)
		{

		}

		/**
		 * Synchs selected property to visible. This is called on change of tablecolumn's <code>visible</code> property.
		 * 
		 * @param visible column visible state to synch to.
		 */
		private void updateFromColumnVisible(boolean visible)
		{
			fromColumn = true;
			try
			{
				setSelected(visible);
			}
			finally
			{
				fromColumn = false;
			}
		}

		/**
		 * Synchs name property to value. This is called on change of tableColumn's <code>headerValue</code> property.
		 * 
		 * @param value
		 */
		private void updateFromColumnHeader(Object value)
		{
			if (value == null)
			{
				this.setEnabled(false);
			}
			else
			{
				setName(String.valueOf(value));
				this.setEnabled(true);
			}
		}

		/**
		 * Enforces selected to <code>true</code>. Called if user interaction tried to de-select the last single visible column.
		 * 
		 */
		private void reselect()
		{
			firePropertyChange("selected", null, Boolean.TRUE);
		}

		// -------------- init
		private void installColumn(TableColumn column)
		{
			this.column = column;
			column.addPropertyChangeListener(getColumnListener());
			updateFromColumnHeader(column.getHeaderValue());
			// #429-swing: actionCommand must be string
			if (column.getIdentifier() != null)
			{
				setActionCommand(column.getIdentifier().toString());
			}
			boolean visible = table.isColumnVisible(column);
			updateFromColumnVisible(visible);
		}

		/**
		 * Returns the listener to column's property changes. The listener is created lazily if necessary.
		 * 
		 * @return the <code>PropertyChangeListener</code> listening to <code>TableColumn</code>'s property changes, guaranteed to be not <code>null</code>.
		 */
		protected PropertyChangeListener getColumnListener()
		{
			if (columnListener == null)
			{
				columnListener = createPropertyChangeListener();
			}
			return columnListener;
		}

		/**
		 * Creates and returns the listener to column's property changes. Subclasses are free to roll their own.
		 * <p>
		 * Implementation note: this listener reacts to column's <code>visible</code> and <code>headerValue</code> properties and calls the respective <code>updateFromXX</code> methodes.
		 * 
		 * @return the <code>PropertyChangeListener</code> to use with the column
		 */
		protected PropertyChangeListener createPropertyChangeListener()
		{
			return new PropertyChangeListener()
			{
				@Override
				public void propertyChange(PropertyChangeEvent evt)
				{
					if ("visible".equals(evt.getPropertyName()))
					{
						updateFromColumnVisible((Boolean)evt.getNewValue());
					}
					else if ("headerValue".equals(evt.getPropertyName()))
					{
						updateFromColumnHeader(evt.getNewValue());
						if (evt.getNewValue() == null)
						{
							markPopupStaled();
						}
					}
				}
			};
		}
	}

	// ---------------------- the popup

	/**
	 * A default implementation of ColumnControlPopup. It uses a JPopupMenu with MenuItems corresponding to the Actions as provided by the ColumnControlButton.
	 * 
	 * 
	 */
	public class DefaultColumnControlPopup implements ColumnControlPopup
	{
		private JPopupMenu popupMenu;
		private JScrollPane scroller;
		private JPanel panelMenus;

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void updateUI()
		{
			SwingUtilities.updateComponentTreeUI(getPopupMenu());
		}

		private JPanel getPanelMenu()
		{
			if (panelMenus == null)
			{
				panelMenus = new JPanel();
				panelMenus.setLayout(new VerticalLayout());
				panelMenus.setBackground(UIManager.getColor("MenuItem.background"));
				panelMenus.setBorder(BorderFactory.createEmptyBorder());
			}
			return panelMenus;
		}

		private JScrollPane getScroller()
		{
			if (scroller == null)
			{
				scroller = createScroller();
				scroller.getVerticalScrollBar().setFocusable(false);
				scroller.setViewportView(getPanelMenu());
			}
			return scroller;
		}
		
		public boolean isVisible()
		{
			return getPopupMenu().isVisible();
		}

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void toggleVisibility(final JComponent owner)
		{
			final JPopupMenu popupMenu = getPopupMenu();
			final JPanel panel = getPanelMenu();
			if (popupMenu.isVisible())
			{
				popupMenu.setVisible(false);
				return;
			}
			
			// Update popup only if it's staled
			populatePopup(false); // refresh=false

			// No components, nothing to display
			if (panel.getComponentCount() <= 0)
			{
				return;
			}

			final JScrollPane scroller = getScroller();
			panel.validate();

			Dimension pSize = table.getParent().getSize();
			Dimension size = panel.getPreferredSize();
			if (size.height >= pSize.height)
			{
				scroller.setPreferredSize(new Dimension(size.width, pSize.height - 30));
			}
			else
			{
				scroller.setPreferredSize(size);
			}
			popupMenu.setPopupSize(new Dimension(scroller.getPreferredSize().width + 20,
					scroller.getPreferredSize().height - 20));

			Dimension buttonSize = owner.getSize();
			int xPos = owner.getComponentOrientation().isLeftToRight() ? buttonSize.width
					- popupMenu.getPreferredSize().width
					: 0;

			popupMenu.show(owner, xPos, buttonSize.height);
		}

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void applyComponentOrientation(ComponentOrientation o)
		{
			getPopupMenu().applyComponentOrientation(o);
		}

		// -------------------- public methods to manipulate popup contents.

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void removeAll()
		{
			getPanelMenu().removeAll();
		}

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void addVisibilityActionItems(List<? extends AbstractActionExt> actions)
		{
			addItems(new ArrayList<Action>(actions));
		}

		/**
		 * @inheritDoc
		 * 
		 */
		@Override
		public void addAdditionalActionItems(List<? extends Action> actions)
		{
			if (actions.size() == 0)
				return;
			addSeparator();
			addItems(actions);
		}

		// --------------------------- internal helpers to manipulate popups content

		/**
		 * Here: creates and adds a menuItem to the popup for every Action in the list. Does nothing if if the list is empty.
		 * 
		 * PRE: actions != null.
		 * 
		 * @param actions a list containing the actions to add to the popup. Must not be null.
		 * 
		 */
		private void addItems(List<? extends Action> actions)
		{
			ActionContainerFactory factory = new ActionContainerFactory(null);
			for (Action action : actions)
			{
				AbstractActionExt a = (AbstractActionExt)action;
				if (action.isEnabled())
				{
					if (a.isStateAction())
						addItem(createCheckBox((AbstractActionExt)action));
					else
					{
						addItem(factory.createButton(action));
					}
				}
			}
		}

		private JCheckBox createCheckBox(AbstractActionExt action)
		{
			JCheckBox c = new JCheckBox(action);
			c.setSelected(action.isSelected());
			c.addItemListener(action);
			return c;
		}

		/**
		 * adds a separator to the popup.
		 * 
		 */
		private void addSeparator()
		{
			getPanelMenu().add(Box.createVerticalStrut(2));
			getPanelMenu().add(new JSeparator());
			getPanelMenu().add(Box.createVerticalStrut(2));
		}

		/**
		 * 
		 * @param item the menuItem to add to the popup.
		 */
		private void addItem(AbstractButton item)
		{
			getPanelMenu().add(item);
		}

		/**
		 * 
		 * @return the popup to add menuitems. Guaranteed to be != null.
		 */
		protected JPopupMenu getPopupMenu()
		{
			if (popupMenu == null)
			{
				popupMenu = new JPopupMenu();
				popupMenu.removeAll();
				popupMenu.setLayout(new BorderLayout());
				popupMenu.add(getScroller(), BorderLayout.CENTER);
			}
			return popupMenu;
		}

		/**
		 * Creates the scroll pane which houses the scrollable list.
		 */
		protected JScrollPane createScroller()
		{
			JScrollPane sp = new JScrollPane(null,
					ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
			sp.setHorizontalScrollBar(null);
			sp.setBorder(BorderFactory.createEmptyBorder());
			return sp;
		}
	}

	/**
	 * Returns to popup component for user interaction. Lazily creates the component if necessary.
	 * 
	 * @param create create popup if not exists
	 * @return the ColumnControlPopup for showing the items, guaranteed to be not <code>null</code>.
	 * @see #createColumnControlPopup()
	 */
	private DefaultColumnControlPopup getColumnControlPopup(final boolean create)
	{
		if (popup != null || !create)
		{
			return popup;
		}

		popup = createColumnControlPopup();
		return popup;
	}

	private DefaultColumnControlPopup getColumnControlPopup()
	{
		final boolean create = true;
		return getColumnControlPopup(create);
	}

	/**
	 * Factory method to return a <code>ColumnControlPopup</code>. Subclasses can override to hook custom implementations.
	 * 
	 * @return the <code>ColumnControlPopup</code> used.
	 */
	private DefaultColumnControlPopup createColumnControlPopup()
	{
		final DefaultColumnControlPopup popup = new DefaultColumnControlPopup();
		return popup;
	}

	// -------------------------- updates from table propertyChangelistnere

	/**
	 * Adjusts internal state after table's column model property has changed. Handles cleanup of listeners to the old/new columnModel (Note, that it listens to the column model only if it can control
	 * column visibility). Updates content of popup.
	 * 
	 * @param columnModelOld the old <code>TableColumnModel</code> we had been listening to.
	 */
	private void updateFromColumnModelChange(final TableColumnModel columnModelOld)
	{
		disableColumnModelChangeSyncAndRun(new Runnable()
		{
			@Override
			public void run()
			{
				final TableColumnModel columnModelNew = table.getColumnModel();

				//
				// Remove listener from old TableColumnModel
				if (columnModelOld != null
						&& columnModelOld != columnModelNew
						&& columnModelListener != null // we have a listener created
				)
				{
					columnModelOld.removeColumnModelListener(columnModelListener);
				}

				//
				// Update popup items
				markPopupStaled();

				//
				// Add listener to new TableColumnModel
				if (columnModelNew != null
						&& columnModelOld != columnModelNew)
				{
					columnModelNew.addColumnModelListener(getColumnModelListener());
				}
			}
		});
	}

	private void disableColumnModelChangeSyncAndRun(final Runnable runnable)
	{
		if (updateFromColumnModelChangeSync)
		{
			return;
		}

		updateFromColumnModelChangeSync = true;
		try
		{
			runnable.run();
		}
		finally
		{
			updateFromColumnModelChangeSync = false;
		}

	}

	private boolean updateFromColumnModelChangeSync = false;

	/**
	 * Synchs this button's enabled with table's enabled.
	 * 
	 */
	private void updateFromTableEnabledChanged()
	{
		getAction().setEnabled(table.isEnabled());
	}

	private void markPopupStaled()
	{
		clearAll();
		popupStaled = true;
		
		final DefaultColumnControlPopup popup = getColumnControlPopup(false);
		if (popup != null && popup.isVisible())
		{
			populatePopup(false);
		}
	}

	/**
	 * Populates the popup from scratch.
	 * 
	 * If applicable, creates and adds column visibility actions. Always adds additional actions.
	 */
	private void populatePopup(final boolean refresh)
	{
		if (!popupStaled && !refresh)
		{
			return;
		}

		clearAll();
		createVisibilityActions();
		addVisibilityActionItems();
		addAdditionalActionItems();
		
		popupStaled = false;
	}

	/**
	 * 
	 * removes all components from the popup, making sure to release all columnVisibility actions.
	 * 
	 */
	private void clearAll()
	{
		clearColumnVisibilityActions();

		final ColumnControlPopup popup = getColumnControlPopup(false);
		if (popup != null)
		{
			popup.removeAll();
		}
	}

	/**
	 * Releases actions and clears list of actions.
	 * 
	 */
	private void clearColumnVisibilityActions()
	{
		if (columnVisibilityActions == null)
		{
			return;
		}

		for (final ColumnVisibilityAction action : columnVisibilityActions)
		{
			action.releaseColumn();
		}
		columnVisibilityActions.clear();
	}

	/**
	 * Adds visibility actions into the popup view.
	 * 
	 * Here: delegates the list of actions to the DefaultColumnControlPopup.
	 * <p>
	 * PRE: columnVisibilityActions populated before calling this.
	 * 
	 */
	private void addVisibilityActionItems()
	{
		getColumnControlPopup().addVisibilityActionItems(Collections.unmodifiableList(getColumnVisibilityActions()));
	}

	/**
	 * Adds additional actions to the popup. Here: delegates the list of actions as returned by #getAdditionalActions() to the DefaultColumnControlPopup. Does nothing if #getColumnActions() is empty.
	 * 
	 */
	private void addAdditionalActionItems()
	{
		getColumnControlPopup().addAdditionalActionItems(Collections.unmodifiableList(getAdditionalActions()));
	}

	/**
	 * Creates and adds a ColumnVisiblityAction for every column that should be togglable via the column control.
	 * <p>
	 * 
	 * Here: all table columns contained in the <code>TableColumnModel</code> - visible and invisible columns - to <code>createColumnVisibilityAction</code> and adds all not <code>null</code> return
	 * values.
	 * 
	 * <p>
	 * PRE: canControl()
	 * 
	 * @see #createColumnVisibilityAction
	 */
	private void createVisibilityActions()
	{
		final Enumeration<TableColumn> columns = table.getColumnModel().getColumns();
		while (columns.hasMoreElements())
		{
			final TableColumn column = columns.nextElement();
			if (column.getHeaderValue() != null)
			{
				final ColumnVisibilityAction action = createColumnVisibilityAction(column);
				if (action != null)
				{
					getColumnVisibilityActions().add(action);
				}
			}
		}

	}

	/**
	 * Creates and returns a <code>ColumnVisibilityAction</code> for the given <code>TableColumn</code>. The return value might be null, f.i. if the column should not be allowed to be toggled.
	 * 
	 * @param column the <code>TableColumn</code> to use for the action
	 * @return a ColumnVisibilityAction to use for the given column, may be <code>null</code>.
	 */
	private ColumnVisibilityAction createColumnVisibilityAction(TableColumn column)
	{
		return new ColumnVisibilityAction(column);
	}

	/**
	 * Lazyly creates and returns the List of visibility actions.
	 * 
	 * @return the list of visibility actions, guaranteed to be != null.
	 */
	private List<ColumnVisibilityAction> getColumnVisibilityActions()
	{
		if (columnVisibilityActions == null)
		{
			columnVisibilityActions = new ArrayList<ColumnVisibilityAction>();
		}
		return columnVisibilityActions;
	}

	/**
	 * creates and returns a list of additional Actions to add to the popup. Here: the actions are looked up in the table's actionMap according to the keys as returned from
	 * #getColumnControlActionKeys();
	 * 
	 * @return a list containing all additional actions to include into the popup.
	 */
	private List<Action> getAdditionalActions()
	{
		@SuppressWarnings("unchecked")
		final List<Object> actionKeys = getColumnControlActionKeys();

		final List<Action> actions = new ArrayList<Action>();
		for (final Object key : actionKeys)
		{
			actions.add(table.getActionMap().get(key));
		}
		return actions;
	}

	/**
	 * Looks up and returns action keys to access actions in the table's actionMap which should be included into the popup.
	 * 
	 * Here: all keys with isColumnControlActionKey(key). The list is sorted by those keys.
	 * 
	 * @return the action keys of table's actionMap entries whose action should be included into the popup.
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List getColumnControlActionKeys()
	{
		final Object[] allKeys = table.getActionMap().allKeys();
		List columnKeys = new ArrayList();
		for (int i = 0; i < allKeys.length; i++)
		{
			if (isColumnControlActionKey(allKeys[i]))
			{
				columnKeys.add(allKeys[i]);
			}
		}
		// JW: this will blow for non-String keys!
		// so this method is less decoupled from the
		// decision method isControl than expected.
		Collections.sort(columnKeys);
		return columnKeys;
	}

	/**
	 * Here: true if a String key starts with #COLUMN_CONTROL_MARKER.
	 * 
	 * @param actionKey a key in the table's actionMap.
	 * @return a boolean to indicate whether the given actionKey maps to an action which should be included into the popup.
	 * 
	 */
	private boolean isColumnControlActionKey(Object actionKey)
	{
		return (actionKey instanceof String) &&
				((String)actionKey).startsWith(COLUMN_CONTROL_MARKER);
	}

	// --------------------------- init

	private void installTable(CTable table)
	{
		this.table = table;
		table.addPropertyChangeListener(getTablePropertyChangeListener());
		updateFromColumnModelChange(null);
		updateFromTableEnabledChanged();
	}

	/**
	 * Initialize the column control button's gui
	 */
	private void init()
	{
		setFocusPainted(false);
		setFocusable(false);
		// this is a trick to get hold of the client prop which
		// prevents closing of the popup
		final Object preventHideDefault = new JComboBox<Object>().getClientProperty("doNotCancelPopup");
		putClientProperty("doNotCancelPopup", preventHideDefault);
	}

	/**
	 * Creates and returns the default action for this button.
	 * 
	 * @param icon the Icon to use in the action.
	 * @return the default action.
	 */
	private Action createControlAction(Icon icon)
	{
		final Action control = new AbstractAction()
		{
			private static final long serialVersionUID = -6683797828219296451L;

			@Override
			public void actionPerformed(ActionEvent e)
			{
				togglePopup();
			}

		};
		control.putValue(Action.SMALL_ICON, icon);
		return control;
	}

	// -------------------------------- listeners

	/**
	 * Returns the listener to table's property changes. The listener is lazily created if necessary.
	 * 
	 * @return the <code>PropertyChangeListener</code> for use with the table, guaranteed to be not <code>null</code>.
	 */
	private PropertyChangeListener getTablePropertyChangeListener()
	{
		if (tablePropertyChangeListener == null)
		{
			tablePropertyChangeListener = createTablePropertyChangeListener();
		}
		return tablePropertyChangeListener;
	}

	/**
	 * Creates the listener to table's property changes. Subclasses are free to roll their own.
	 * <p>
	 * Implementation note: this listener reacts to table's <code>enabled</code> and <code>columnModel</code> properties and calls the respective <code>updateFromXX</code> methodes.
	 * 
	 * @return the <code>PropertyChangeListener</code> for use with the table.
	 */
	private PropertyChangeListener createTablePropertyChangeListener()
	{
		return new PropertyChangeListener()
		{
			@Override
			public void propertyChange(final PropertyChangeEvent evt)
			{
				final String propertyName = evt.getPropertyName();
				// Column Model Changed
				if (CTable.PROPERTY_ColumnModel.equals(propertyName))
				{
					final TableColumnModel oldModel = (TableColumnModel)evt.getOldValue();
					updateFromColumnModelChange(oldModel);
				}
				// Table Column settings were changed
				else if (CTable.PROPERTY_TableColumnChanged.equals(propertyName))
				{
					final TableColumnModel oldModel = table.getColumnModel();
					updateFromColumnModelChange(oldModel);
				}
				//
				else if ("enabled".equals(propertyName))
				{
					updateFromTableEnabledChanged();
				}
			}
		};
	}

	/**
	 * Returns the listener to table's column model. The listener is lazily created if necessary.
	 * 
	 * @return the <code>TableColumnModelListener</code> for use with the table's column model, guaranteed to be not <code>null</code>.
	 */
	private TableColumnModelListener getColumnModelListener()
	{
		if (columnModelListener == null)
		{
			columnModelListener = createColumnModelListener();
		}
		return columnModelListener;
	}

	/**
	 * Creates the listener to columnModel. Subclasses are free to roll their own.
	 * <p>
	 * Implementation note: this listener reacts to "real" columnRemoved/-Added by populating the popups content from scratch.
	 * 
	 * @return the <code>TableColumnModelListener</code> for use with the table's columnModel.
	 */
	private TableColumnModelListener createColumnModelListener()
	{
		return new TableColumnModelListener()
		{
			/** Tells listeners that a column was added to the model. */
			@Override
			public void columnAdded(TableColumnModelEvent e)
			{
				markPopupStaled();
			}

			/** Tells listeners that a column was removed from the model. */
			@Override
			public void columnRemoved(TableColumnModelEvent e)
			{
				markPopupStaled();
			}

			/** Tells listeners that a column was repositioned. */
			@Override
			public void columnMoved(TableColumnModelEvent e)
			{
				if (e.getFromIndex() == e.getToIndex())
				{
					// not actually a change
					return;
				}
				
				// mark popup stalled because we want to have the same ordering there as we have in table column
				markPopupStaled();
			}

			/** Tells listeners that a column was moved due to a margin change. */
			@Override
			public void columnMarginChanged(ChangeEvent e)
			{
				// nothing to do
			}

			/**
			 * Tells listeners that the selection model of the TableColumnModel changed.
			 */
			@Override
			public void columnSelectionChanged(ListSelectionEvent e)
			{
				// nothing to do
			}
		};
	}

} // end class ColumnControlButton
