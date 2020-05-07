package org.compiere.grid.ed.menu;

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
import java.awt.Container;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;
import javax.swing.MenuElement;
import javax.swing.SwingUtilities;

import org.adempiere.images.Images;
import org.adempiere.ui.IContextMenuAction;
import org.adempiere.ui.IContextMenuActionContext;
import org.adempiere.ui.IContextMenuProvider;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.grid.ed.VEditor;
import org.compiere.swing.CMenuItem;
import org.compiere.util.Env;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.logging.LogManager;

/**
 * {@link VEditor}'s context menu
 * 
 * @author tsa
 * @task http://dewiki908/mediawiki/index.php/03137:_Adresse_neu_berechnen_k%C3%B6nnen_%282012081510000014%29
 */
public class EditorContextPopupMenu extends JPopupMenu
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 9080629474763433478L;

	/**
	 * To be called when {@link VEditor#getField()} is set.
	 * 
	 * @param editor
	 */
	public static final void onGridFieldSet(final VEditor editor)
	{
		final EditorContextPopupMenu popupMenu = getEditorContextPopupMenu(editor);
		if (popupMenu == null)
		{
			return;
		}

		popupMenu.onGridFieldSet();
	}

	private static final EditorContextPopupMenu getEditorContextPopupMenu(final VEditor editor)
	{
		if (editor instanceof JComponent)
		{
			JComponent comp = (JComponent)editor;
			final EditorContextPopupMenu popupMenu = (EditorContextPopupMenu)comp.getClientProperty(ATTR_EditorContextPopupMenu);
			return popupMenu;
		}

		return null;
	}

	private final transient Logger logger = LogManager.getLogger(getClass());

	private static final String ATTR_EditorContextPopupMenu = EditorContextPopupMenu.class.getName();
	private static final String ATTR_Action = IContextMenuAction.class.getCanonicalName();

	private final IContextMenuActionContext menuCtx;

	public EditorContextPopupMenu(final VEditor editor)
	{
		this(Services.get(IContextMenuProvider.class).createContext(editor));
	}

	public EditorContextPopupMenu(final IContextMenuActionContext menuCtx)
	{
		super();

		Check.assumeNotNull(menuCtx, "menuCtx is not null");
		this.menuCtx = menuCtx;

		bindToVEditor();
	}

	private final void bindToVEditor()
	{
		final VEditor editor = menuCtx.getEditor();

		if (editor instanceof JComponent)
		{
			final JComponent editorComp = (JComponent)editor;
			editorComp.addMouseListener(new MouseAdapter()
			{
				@Override
				public void mouseClicked(MouseEvent e)
				{
					if (SwingUtilities.isRightMouseButton(e))
					{
						EditorContextPopupMenu.this.show(editorComp, e.getX(), e.getY());
					}
				}
			});

			editorComp.putClientProperty(ATTR_EditorContextPopupMenu, EditorContextPopupMenu.this);
		}
		else
		{
			logger.warn("Cannot bind on " + editor + " because Component can't be identified.");
		}
	}
	
	private int getWindowNo()
	{
		//
		// Get WindowNo from editor
		final VEditor editor = menuCtx.getEditor();
		if(editor != null)
		{
			if(editor instanceof java.awt.Component)
			{
				return Env.getWindowNo((java.awt.Component)editor);
			}
		}

		// Fallback
		return Env.WINDOW_MAIN;
	}

	/** Called when {@link VEditor#getField()} was set */
	private final void onGridFieldSet()
	{
		// Make sure the grid field was really set
		final VEditor editor = menuCtx.getEditor();
		if (editor.getField() == null)
		{
			logger.warn("Skip popup context menu setup because GridField is not available: {}", editor);
			return;
		}

		// Create the root action.
		// We expect that actions are initialized (but not the UI).
		// In this way the underlying actions have the opportunity early customize the component on which they are binding.
		// Some of the actions really depend on this feature (e.g. Copy/paste actions which are adding actions to component's InputMap).
		getRootAction();
	}

	private boolean _initialized = false;

	private void initActions()
	{
		if (_initialized)
		{
			return;
		}

		final IContextMenuAction rootAction = getRootAction();
		createUI(this, rootAction);
		_initialized = true;
	}

	private final IContextMenuAction getRootAction()
	{
		if (_rootAction == null)
		{
			_rootAction = Services.get(IContextMenuProvider.class).getRootContextMenuAction(menuCtx);
		}
		return _rootAction;
	}

	private IContextMenuAction _rootAction;

	private boolean createUI(final MenuElement parent, final IContextMenuAction action)
	{
		if (!action.isAvailable())
		{
			return false;
		}

		// Action implements IEditorContextPopupMenuComposer, so let it compose by itself
		if (action instanceof IEditorContextPopupMenuComposer)
		{
			if (!(parent instanceof Container))
			{
				logger.warn("No Container found for " + parent);
				return false;
			}
			final Container parentContainer = (Container)parent;
			IEditorContextPopupMenuComposer actionComposer = (IEditorContextPopupMenuComposer)action;
			return actionComposer.createUI(parentContainer);
		}
		// Generic Composer
		else
		{
			return createUIGeneric(parent, action);
		}
	}

	private boolean createUIGeneric(final MenuElement parent, final IContextMenuAction action)
	{
		if (!action.isAvailable())
		{
			return false;
		}

		final ImageIcon icon = Check.isEmpty(action.getIcon(), true) ? null : Images.getImageIcon2(action.getIcon());
		final String label = action.getTitle();

		//
		// Simple Menu Item
		final List<IContextMenuAction> childActions = action.getChildren();
		if (childActions == null || childActions.isEmpty())
		{
			final CMenuItem item = new CMenuItem();
			item.setText(label);
			item.setIcon(icon);
			item.setAccelerator(action.getKeyStroke());
			item.putClientProperty(ATTR_Action, action);
			item.addActionListener(new ActionListener()
			{
				@Override
				public void actionPerformed(final ActionEvent event)
				{
					executeAction(item, action, event);
				}
			});
			addMenuElements(parent, item);
			return true;
		}
		//
		// Sub Menu
		else
		{
			final JMenu item = new JMenu();
			item.setText(label);
			item.setIcon(icon);
			item.putClientProperty(ATTR_Action, action);

			boolean haveChildren = false;
			for (IContextMenuAction childAction : childActions)
			{
				if (createUI(item, childAction))
				{
					haveChildren = true;
				}
			}
			if (!haveChildren)
			{
				return false;
			}

			final boolean addChildrenToParent = label == null && icon == null;
			if (addChildrenToParent)
			{
				addMenuElements(parent, getSubElements(item));
			}
			else
			{
				addMenuElements(parent, item);
			}
			return true;
		}
	}

	private static final MenuElement[] getSubElements(final MenuElement me)
	{
		if (me instanceof JMenu)
		{
			final JMenu menu = (JMenu)me;
			final int size = menu.getItemCount();
			final MenuElement[] result = new MenuElement[size];
			for (int i = 0; i < size; i++)
			{
				result[i] = menu.getItem(i);
			}
			return result;
		}
		else if (me instanceof JPopupMenu)
		{
			final JPopupMenu menu = (JPopupMenu)me;
			final int size = menu.getComponentCount();
			final List<MenuElement> result = new ArrayList<MenuElement>();
			for (int i = 0; i < size; i++)
			{
				final Component c = menu.getComponent(i);
				if (c instanceof MenuElement)
				{
					result.add((MenuElement)c);
				}
			}
			return result.toArray(new MenuElement[result.size()]);
		}
		else
		{
			final MenuElement[] subElements = me.getSubElements();
			if (subElements == null || subElements.length <= 0)
			{
				return new MenuElement[0];
			}
			final MenuElement[] subElementsCopy = new MenuElement[subElements.length];
			System.arraycopy(subElements, 0, subElementsCopy, 0, subElements.length);
			return subElementsCopy;
		}
	}

	private static final void addMenuElements(final MenuElement parent, final MenuElement... children)
	{
		if (children == null || children.length <= 0)
		{
			return;
		}

		//
		// Get the parent menu component, where we will add the children
		final Container parentComp = (Container)parent.getComponent();

		//
		// Add separator if we got more then one child
		final boolean moreThanOneChildren = children.length > 1;
		if (moreThanOneChildren)
		{
			addSeparator(parentComp);
		}

		//
		// Add children to parent
		for (MenuElement child : children)
		{
			if (child == null)
			{
				addSeparator(parentComp);
			}
			else
			{
				parentComp.add(child.getComponent());
			}
		}
	}

	private static final void addSeparator(final Container menuComp)
	{
		if (menuComp instanceof JMenu)
		{
			((JMenu)menuComp).addSeparator();
		}
		else if (menuComp instanceof EditorContextPopupMenu)
		{
			((EditorContextPopupMenu)menuComp).addSeparator();
		}
		else if (menuComp instanceof JPopupMenu)
		{
			((JPopupMenu)menuComp).addSeparator();
		}
		else
		{
			menuComp.add(new JPopupMenu.Separator());
		}
	}

	private void executeAction(final CMenuItem item, final IContextMenuAction action, final ActionEvent event)
	{
		final boolean longOperation = action.isLongOperation();
		item.setEnabled(false);
		try
		{
			if (longOperation)
			{
				item.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
			}

			// Execute the action as ActionListener
			if (action instanceof ActionListener)
			{
				((ActionListener)action).actionPerformed(event);
			}
			// Fallback: call the action's run() method
			else
			{
				action.run();
			}
		}
		catch (Exception e)
		{
			final int windowNo = getWindowNo();
			Services.get(IClientUI.class).error(windowNo, e);
		}
		finally
		{
			if (longOperation)
			{
				item.setCursor(Cursor.getDefaultCursor());
			}
			item.setEnabled(true);
		}
	}

	@Override
	public void show(Component invoker, int x, int y)
	{
		boolean available = checkAvailableActions();
		if (available)
		{
			super.show(invoker, x, y);
		}
		else
		{
			setVisible(false);
		}
	}

	/**
	 * Check and update all menu items if available
	 * 
	 * @param item
	 * @return true if ANY item is available
	 */
	private boolean checkAvailableActions()
	{
		initActions();
		final boolean available = checkAvailable(this);
		return available;
	}

	/**
	 * Check and update menu item if available
	 * 
	 * @param item
	 * @return true if item is available
	 */
	private boolean checkAvailable(MenuElement item)
	{
		final Component itemComp = item.getComponent();

		final boolean isRoot = item == this;

		if (!isRoot)
		{
			final IContextMenuAction action = getAction(item);
			if (action == null)
			{
				// not controlled by this editor, take it's status and return
				final boolean available = itemComp.isVisible();
				return available;
			}
			if (!action.isAvailable())
			{
				itemComp.setVisible(false);
				return false;
			}
			if (!action.isRunnable())
			{
				final boolean hideWhenNotRunnable = action.isHideWhenNotRunnable();
				itemComp.setVisible(!hideWhenNotRunnable);
				itemComp.setEnabled(false);
				return false;
			}
		}

		final MenuElement[] children = getSubElements(item);
		if (children != null && children.length > 0)
		{
			boolean submenuAvailable = false;
			for (MenuElement subitem : children)
			{
				final boolean subitemAvailable = checkAvailable(subitem);
				if (subitemAvailable)
				{
					submenuAvailable = true;
				}
			}
			itemComp.setVisible(submenuAvailable);
			return submenuAvailable;
		}
		else
		{
			itemComp.setVisible(true);
			itemComp.setEnabled(true);
		}

		return true;
	}

	private final IContextMenuAction getAction(MenuElement me)
	{
		final Component c = me.getComponent();
		if (c instanceof JComponent)
		{
			final JComponent jc = (JComponent)c;
			final IContextMenuAction action = (IContextMenuAction)jc.getClientProperty(ATTR_Action);
			return action;
		}

		return null;
	}

	public void add(final IContextMenuAction action)
	{
		Check.assumeNotNull(action, "action not null");

		// make sure actions are initialized and UI created before we are adding our new action
		initActions();

		createUI(this, action);
	}
}
