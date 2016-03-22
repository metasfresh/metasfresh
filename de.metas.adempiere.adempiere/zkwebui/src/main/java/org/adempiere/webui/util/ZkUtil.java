package org.adempiere.webui.util;

/*
 * #%L
 * de.metas.adempiere.adempiere.zkwebui
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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Services;
import org.adempiere.webui.LayoutUtils;
import org.adempiere.webui.component.Applet;
import org.compiere.util.Env;
import org.compiere.util.Util;
import org.slf4j.Logger;
import org.slf4j.Logger;
import de.metas.logging.LogManager;
import de.metas.logging.LogManager;
import org.zkforge.keylistener.Keylistener;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.WrongValueException;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.EventListener;
import org.zkoss.zk.ui.event.Events;
import org.zkoss.zk.ui.event.InputEvent;
import org.zkoss.zk.ui.event.SelectEvent;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;
import org.zkoss.zul.Constraint;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.Listbox;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import org.zkoss.zul.event.ListDataEvent;
import org.zkoss.zul.event.ListDataListener;
import org.zkoss.zul.impl.api.XulElement;

public final class ZkUtil
{
	private static final transient Logger logger = LogManager.getLogger(ZkUtil.class);

	private static final String ATTR_ValueToBeSet = ZkUtil.class.getCanonicalName() + "#ValueToBeSet";

	/**
	 * Attribute set on {@link Combobox} when it is in rendering stage
	 * 
	 * @see org.zkoss.zul.Combobox#onInitRender(Event)
	 */
	private static final String ATTR_Combobox_ON_INITRENDER = "zul.Combobox.ON_INITRENDER";

	public static final String EVENT_OnInitRenderLater = "onInitRenderLater";

	/**
	 * @deprecated Please use {@link #setSelectedValue(Listbox, Object)}
	 */
	@Deprecated
	public static <T> boolean setSelectedListitem(Listbox listbox, T value)
	{
		return setSelectedValue(listbox, value);
	}

	public static <T> boolean setSelectedValue(Listbox listbox, T value)
	{
		final Comparator<T> cmp = null;
		return setSelectedValue(listbox, value, cmp);
	}

	/**
	 * @deprecated Please use {@link #setSelectedValue(Listbox, Object, Comparator)}
	 */
	@Deprecated
	public static <T> boolean setSelectedListitem(Listbox listbox, T value, Comparator<T> cmp)
	{
		return setSelectedValue(listbox, value, cmp);
	}

	public static <T> boolean setSelectedValue(Listbox listbox, T value, Comparator<T> cmp)
	{
		if (value == null)
		{
			listbox.setSelectedIndex(-1);
			return false;
		}

		int count = listbox.getItemCount();
		for (int i = 0; i < count; i++)
		{
			final Listitem item = listbox.getItemAtIndex(i);
			listbox.renderItem(item); // make sure is rendered

			@SuppressWarnings("unchecked")
			T itemValue = (T)item.getValue();

			if (cmp != null)
			{
				if (cmp.compare(itemValue, value) == 0)
				{
					listbox.setSelectedIndex(i);
					return true;
				}
			}
			else
			{
				if ((itemValue == null && value == null) || (itemValue != null && itemValue.equals(value)))
				{
					listbox.setSelectedIndex(i);
					return true;
				}
			}
		}

		return false;
	}

	public static <T> T getSelectedValue(Listbox listbox)
	{
		Listitem item = listbox.getSelectedItem();
		if (item == null)
			return null;

		listbox.renderItem(item);

		@SuppressWarnings("unchecked")
		T value = (T)item.getValue();

		return value;
	}

	public static <T> T getSelectedValue(Combobox combobox)
	{
		if (isRenderScheduled(combobox))
		{
			// If render is scheduled, we cannot rely on combobox.getSelectedItem()
			// because even if we have an item selected, that result can be old
			@SuppressWarnings("unchecked")
			T value = (T)combobox.getAttribute(ATTR_ValueToBeSet);
			return value;
		}
		else
		{
			final Comboitem item = combobox.getSelectedItem();
			if (item == null)
			{
				return null;
			}

			@SuppressWarnings("unchecked")
			T value = (T)item.getValue();
			return value;
		}
	}

	public static <T> T getSelectedValue(SelectEvent event)
	{
		Set<?> items = event.getSelectedItems();
		if (items == null || items.isEmpty())
		{
			return null;
		}

		Object item = items.iterator().next();
		if (item == null)
		{
			return null;
		}
		else if (item instanceof org.zkoss.zul.api.Comboitem)
		{
			org.zkoss.zul.api.Comboitem ci = (org.zkoss.zul.api.Comboitem)item;

			@SuppressWarnings("unchecked")
			final T value = (T)ci.getValue();
			return value;
		}
		else
		{
			throw new AdempiereException("Item type not supported: " + item + " (class: " + item.getClass() + ")");
		}
	}

	public static boolean isRenderScheduled(Combobox combobox)
	{
		final Object o = combobox.getAttribute(ATTR_Combobox_ON_INITRENDER);
		return (o instanceof Boolean) && ((Boolean)o).booleanValue();
	}

	public static void setSelectedValue(final Combobox combobox, final Object value)
	{
		setSelectedValue(combobox, value, false);
	}

	public static void setSelectedValue(final Combobox combobox, final Object value, final boolean ignoreConstraints)
	{
		if (!isRenderScheduled(combobox))
		{
			setSelectedValueNow(combobox, value, ignoreConstraints);
			return;
		}

		combobox.setAttribute(ATTR_ValueToBeSet, value);
		combobox.addEventListener(EVENT_OnInitRenderLater, new EventListener()
		{

			@Override
			public void onEvent(Event event) throws Exception
			{
				combobox.removeEventListener(EVENT_OnInitRenderLater, this);
				setSelectedValueNow(combobox, value, ignoreConstraints);
				combobox.setAttribute(ATTR_ValueToBeSet, null);
			}
		});
	}

	private static void setSelectedValueNow(Combobox combobox, Object value, boolean ignoreConstraints)
	{
		Constraint constraint = null;
		try
		{
			if (ignoreConstraints)
			{
				constraint = combobox.getConstraint();
				combobox.setConstraint((Constraint)null);
			}

			if (value == null)
			{
				combobox.setSelectedIndex(-1);
				return;
			}

			for (Object itemObj : combobox.getItems())
			{
				final Comboitem item = (Comboitem)itemObj;
				final Object v = item.getValue();

				if (value == v || value.equals(v))
				{
					combobox.setSelectedItem(item);
					return;
				}
			}

			combobox.setSelectedIndex(-1);
		}
		finally
		{
			if (ignoreConstraints)
			{
				combobox.setConstraint(constraint);
			}
		}
	}

	public static <T> void setSelectedValues(Listbox listbox, List<T> values)
	{
		final Set<Listitem> selection = new HashSet<Listitem>();
		final Set<Listitem> selectionToRemove = new HashSet<Listitem>();

		for (int i = 0; i < listbox.getItemCount(); i++)
		{
			final Listitem item = listbox.getItemAtIndex(i);
			listbox.renderItem(item); // make sure is rendered

			final Object value = item.getValue();
			if (values != null && values.contains(value))
			{
				selection.add(item);
			}
			else
			{
				selectionToRemove.add(item);
			}
		}

		for (Listitem item : selectionToRemove)
		{
			listbox.removeItemFromSelection(item);
		}

		listbox.setSelectedItems(selection);
	}

	public static <T> List<T> getSelectedValues(Listbox listbox)
	{
		List<T> values = new ArrayList<T>();

		@SuppressWarnings("rawtypes")
		Set selection = listbox.getSelectedItems();

		for (Object o : selection)
		{
			Listitem item = (Listitem)o;
			listbox.renderItem(item); // make sure is rendered

			@SuppressWarnings("unchecked")
			T value = (T)item.getValue();

			values.add(value);
		}

		return values;
	}

	/**
	 * 
	 * @param cls
	 * @param target
	 * @see org.adempiere.webui.LayoutUtils
	 */
	public static void removeSclass(String cls, final org.zkoss.zk.ui.api.HtmlBasedComponent target)
	{
		if (cls == null || cls.isEmpty())
			return;

		String sclass = target.getSclass();
		if (sclass == null)
			return;

		cls = " " + cls + " ";
		sclass = " " + sclass + " ";
		sclass = sclass.replace(cls, " ").trim();
		target.setSclass(sclass);
	}

	public static void addSclass(String cls, org.zkoss.zk.ui.api.HtmlBasedComponent target)
	{
		LayoutUtils.addSclass(cls, target);
	}

	public static void removeAllChildren(Component comp)
	{
		if (comp == null)
			return;

		final List<?> children = comp.getChildren();
		if (children.isEmpty())
			return;

		// we need to do a copy because we get ConcurrentModificationException
		final List<?> list = new ArrayList<Object>(children);
		for (Object o : list)
		{
			Component c = (Component)o;
			c.detach();
		}
	}

	public static <T> T getParent(Component comp, Class<T> parentClass)
	{
		if (comp == null)
			return null;

		for (Component p = comp.getParent(); p != null; p = p.getParent())
		{
			if (parentClass.isAssignableFrom(p.getClass()))
			{
				@SuppressWarnings("unchecked")
				T parent = (T)p;
				return parent;
			}
		}

		return null;
	}

	public static boolean isZK363()
	{
		// TODO: need to get the actual ZK version
		return true;
	}

	/**
	 * Casts given listModel to {@link List}. If this is not possible (i.e. listModel does not implement {@link List} interface), a new unmodifiable list will be created.
	 * 
	 * @param listModel
	 * @return list
	 */
	public static <T> List<T> toList(ListModel listModel)
	{
		if (listModel == null)
			return null;

		if (listModel instanceof List)
		{
			@SuppressWarnings("unchecked")
			final List<T> list = (List<T>)listModel;
			return list;
		}

		List<T> list = new ArrayList<T>();
		for (int i = 0; i < listModel.getSize(); i++)
		{
			@SuppressWarnings("unchecked")
			final T item = (T)listModel.getElementAt(i);
			list.add(item);
		}
		return list;
	}

	/**
	 * 
	 * @param grid
	 * @param value
	 * @return Row which has exactly the same value as the one given by parameter
	 */
	public static Row getRowByValue(Grid grid, Object value)
	{
		if (value == null)
		{
			return null;
		}

		final Rows rows = grid.getRows();
		if (rows == null)
		{
			return null;
		}

		for (Object o : rows.getChildren())
		{
			if (!(o instanceof Row))
			{
				continue;
			}

			final Row row = (Row)o;

			if (Util.same(value, row.getValue()))
			{
				return row;
			}
		}

		return null;
	}

	public static WrongValueException toWrongValueException(Throwable t)
	{
		if (t instanceof WrongValueException)
		{
			return (WrongValueException)t;
		}

		return new WrongValueException(t.getLocalizedMessage(), t);
	}

	public static WrongValueException toWrongValueException(Component comp, Throwable t)
	{
		if (t instanceof WrongValueException)
		{
			WrongValueException wve = (WrongValueException)t;
			throw wve;
		}

		return new WrongValueException(comp, t.getLocalizedMessage());
	}

	public static void appendCtrlKeys(XulElement comp, String ctrlKeys)
	{
		if (Util.isEmpty(ctrlKeys, true))
		{
			return;
		}

		String keys = comp.getCtrlKeys();
		if (Util.isEmpty(keys, true))
		{
			keys = ctrlKeys;
		}
		else
		{
			keys = keys.trim() + ctrlKeys.trim();
		}
		comp.setCtrlKeys(keys);
	}

	public static void appendCtrlKeys(Keylistener keyListener, String ctrlKeys)
	{
		if (Util.isEmpty(ctrlKeys, true))
		{
			return;
		}

		String keys = keyListener.getCtrlKeys();
		if (Util.isEmpty(keys, true))
		{
			keys = ctrlKeys;
		}
		else
		{
			keys = keys.trim() + ctrlKeys.trim();
		}
		keyListener.setCtrlKeys(keys);
	}

	/**
	 * @see #resetSelectionOnFieldCleared(Combobox)
	 */
	private static final EventListener resetSelectionOnFieldClearedListener = new EventListener()
	{

		@Override
		public void onEvent(Event event) throws Exception
		{
			resetSelectionOnFieldCleared(event);
		}
	};

	/**
	 * Adds a new {@link EventListener} which will make the combobox to reset it's selection when the textbox is cleared
	 * 
	 * @param combo
	 */
	public static void resetSelectionOnFieldCleared(Combobox combo)
	{
		combo.addEventListener(Events.ON_CHANGE, resetSelectionOnFieldClearedListener);
	}

	/**
	 * 
	 * @param event
	 * @see #resetSelectionOnFieldCleared(Combobox)
	 */
	private static void resetSelectionOnFieldCleared(Event event)
	{
		if (!(event instanceof InputEvent))
		{
			// not an input event, skip
			return;
		}

		final InputEvent ie = (InputEvent)event;

		if (!Util.isEmpty(ie.getValue(), false))
		{
			// Combo's text box was not reseted (cleared), nothing to do
			return;
		}

		if (!(ie.getTarget() instanceof Combobox))
		{
			// not a combobox, nothing to do
			return;
		}
		final Combobox combo = (Combobox)ie.getTarget();

		combo.setSelectedItem(null);

		// even if is called from an event listener, make sure the selection will be propagated to model
		Events.postEvent(new SelectEvent("onSelect", combo, Collections.emptySet()));
	}

	/**
	 * Injects some listeners which ensure: if a {@link ListModel} was changed it validates if current selection is still valid and if not, it resets it
	 * 
	 * @param combobox
	 * @see #resetSelectionWhenModelNotMatchedNow(Combobox)
	 */
	public static void enableResetSelectionWhenModelNotMatched(final Combobox combobox)
	{
		final ListModel model = combobox.getModel();
		model.addListDataListener(new ListDataListener()
		{

			@Override
			public void onChange(ListDataEvent event)
			{
				if (!isRenderScheduled(combobox))
				{
					resetSelectionWhenModelNotMatchedNow(combobox);
				}
				else
				{
					combobox.addEventListener(EVENT_OnInitRenderLater, new EventListener()
					{

						@Override
						public void onEvent(Event event) throws Exception
						{
							combobox.removeEventListener(EVENT_OnInitRenderLater, this);
							resetSelectionWhenModelNotMatchedNow(combobox);
						}
					});
				}
			}
		});
	};

	/**
	 * Validate if current selection is still valid (it is present in {@link ListModel} and if not is reseting it as follows:
	 * <ul>
	 * <li>if model has at least one element, first one is choose
	 * <li>else NULL selection is set
	 * </ul>
	 * 
	 * @param combobox
	 */
	private static void resetSelectionWhenModelNotMatchedNow(final Combobox combobox)
	{
		final ListModel model = combobox.getModel();
		if (model == null)
		{
			return;
		}

		// NOTE: we assume that Combobox is rendered

		final Object selectedValue = getSelectedValue(combobox);

		final int len = combobox.getItemCount();
		for (int i = 0; i < len; i++)
		{
			final Comboitem item = combobox.getItemAtIndex(i);
			final Object value = item.getValue();
			if (Util.equals(value, selectedValue))
			{
				// selected value found, nothing to reset
				return;
			}
		}

		final Comboitem defaultItem = len > 0 ? combobox.getItemAtIndex(0) : null;
		final Object defaultValue = defaultItem == null ? null : defaultItem.getValue();
		setSelectedValue(combobox, defaultValue);

		// even if is called from an event listener, make sure the selection will be propagated to model
		final Set<Comboitem> selectedItems;
		if (defaultItem == null)
		{
			selectedItems = Collections.emptySet();
		}
		else
		{
			selectedItems = Collections.singleton(defaultItem);
		}
		Events.postEvent(new SelectEvent("onSelect", combobox, selectedItems));
	}

	/**
	 * Post <code>eventName<code> to first parent which supports that event.
	 * 
	 * @param comp
	 * @param eventName
	 * @param data
	 * @return true if event was posted; false if event was not posted
	 */
	public static boolean postEventToFirstParent(Component comp, String eventName, Object data)
	{
		if (comp == null)
		{
			return false;
		}

		Component parent = comp.getParent();
		while (parent != null)
		{
			if (parent.isListenerAvailable(eventName, false))
			{
				Events.postEvent(eventName, parent, data);
				return true;
			}
			parent = parent.getParent();
		}

		return false;
	}

	/**
	 * Create {@link Applet} based on SysConfig params.
	 * 
	 * For the given <code>sysconfigPrefix</code>, the method will retrive the following <code>AD_SysConfig</code> parameters and pass the values to the applet.
	 * <ul>
	 * <li>archive</li> not set if empty
	 * <li>code</li> not set if empty
	 * <li>codebase</li> not set if empty
	 * <li>width</li> set to 1px if empty
	 * <li>height</li> set to 1px if empty
	 * <li>all other paramters</li> are be passed to the applet via {@link Applet#setParam(String, String)}.
	 * </ul>
	 * 
	 * @param sysconfigPrefix
	 * @return applet
	 */
	public static Applet createApplet(String sysconfigPrefix)
	{
		logger.info("Creating applet for prefix '{}'", sysconfigPrefix);

		final Properties ctx = Env.getCtx();
		final int adClientId = Env.getAD_Client_ID(ctx);
		final int adOrgId = Env.getAD_Org_ID(ctx);

		final ISysConfigBL sysconfigBL = Services.get(ISysConfigBL.class);
		final Map<String, String> sysConfigParams = sysconfigBL.getValuesForPrefix(sysconfigPrefix, adClientId, adOrgId);

		final Applet applet = new Applet();

		final String paramValueArchive = sysConfigParams.remove(sysconfigPrefix + "archive");
		if (!Util.isEmpty(paramValueArchive, true))
		{
			applet.setArchive(paramValueArchive);
			logger.info("Set parameter: archive={}", paramValueArchive);
		}

		final String paramValueCode = sysConfigParams.remove(sysconfigPrefix + "code");
		if (!Util.isEmpty(paramValueCode, true))
		{
			applet.setCode(paramValueCode);
			logger.info("Set parameter: code={}", paramValueCode);
		}

		final String paramValueCodebase = sysConfigParams.remove(sysconfigPrefix + "codebase");
		if (!Util.isEmpty(paramValueCodebase, true))
		{
			applet.setCodebase(paramValueCodebase);
			logger.info("Set parameter: codebase={}", paramValueCodebase);
		}

		String paramValueWidth = sysConfigParams.remove(sysconfigPrefix + "width");
		if (Util.isEmpty(paramValueWidth, true))
		{
			paramValueWidth = "1px";
		}
		applet.setWidth(paramValueWidth);
		logger.info("Set parameter: width={}", paramValueWidth);

		String paramValueHeight = sysConfigParams.remove(sysconfigPrefix + "height");
		if (Util.isEmpty(paramValueHeight, true))
		{
			paramValueHeight = "1px";
		}
		applet.setHeight(paramValueHeight);
		logger.info("Set parameter: height={}", paramValueHeight);

		// give the current session ID to our applet, so ADempiere can apply user and role permission on incoming data
		applet.setParam("sessionId", Env.getContext(Env.getCtx(), Env.CTXNAME_AD_Session_ID));

		//
		// Additional parameters
		for (final Map.Entry<String, String> e : sysConfigParams.entrySet())
		{
			final String name = e.getKey();
			final String value = e.getValue();
			applet.setParam(name, value);
			logger.info("Set parameter: {}={}", new Object[] { name, value });
		}

		return applet;
	}

	/**
	 * Invoke later (in another execution) given <code>runnables</code>. Please not the execution sequence is GUARANTEED.
	 * 
	 * @param comp
	 * @param runnables
	 */
	public static void invokeLater(final Component comp, final Runnable... runnables)
	{
		if (runnables == null || runnables.length == 0)
		{
			return;
		}

		final Runnable currentRunnable = runnables[0];
		final Runnable[] nextRunnables;
		if (runnables.length > 1)
		{
			nextRunnables = new Runnable[runnables.length - 1];
			System.arraycopy(runnables, 1, nextRunnables, 0, nextRunnables.length);
		}
		else
		{
			nextRunnables = null;
		}

		final String eventName = "onInvokeLater" + UUID.randomUUID();
		comp.addEventListener(eventName, new EventListener()
		{
			@Override
			public void onEvent(Event event)
			{
				comp.removeEventListener(eventName, this);
				currentRunnable.run();
				if (nextRunnables != null && nextRunnables.length > 0)
				{
					invokeLater(comp, nextRunnables);
				}
			}
		});
		Events.echoEvent(eventName, comp, null);
	}
}
