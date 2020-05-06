package de.metas.handlingunits.client.terminal.editor.model;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

import java.util.Collection;
import java.util.List;

import de.metas.adempiere.form.terminal.IDisposable;
import de.metas.adempiere.form.terminal.ITerminalFactory;
import de.metas.adempiere.form.terminal.ITerminalKey;
import de.metas.adempiere.form.terminal.context.ITerminalContext;
import de.metas.handlingunits.attribute.IWeightable;
import de.metas.handlingunits.attribute.storage.IAttributeStorage;

public interface IHUKey extends ITerminalKey, IDisposable
{
	/**
	 * Notify parent that this grouping key is no longer necessary
	 */
	String ACTION_GroupingRemoved = IHUKey.class.getName() + "GroupingRemoved";
	String ACTION_ChildrenChanged = IHUKey.class.getName() + "#ChildrenChanged";
	String ACTION_ChildrenFilterChanged = IHUKey.class.getName() + "#ChildrenFilterChanged";

	/**
	 *
	 * @return {@link IHUKeyFactory} which created this key
	 */
	IHUKeyFactory getKeyFactory();

	/**
	 * @return the {@link IHUKeyFactory}'s terminal context
	 */
	ITerminalContext getTerminalContext();

	/**
	 * @return the {@link IHUKeyFactory}'s terminal factory of it's terminal context
	 */
	ITerminalFactory getTerminalFactory();

	/**
	 * Gets children. If they were not loaded, this method will load them first.
	 *
	 * @return children
	 */
	List<IHUKey> getChildren();

	/**
	 * Gets only children which were currently loaded.
	 *
	 * @return currently loaded children
	 */
	List<IHUKey> getChildrenNoLoad();

	/**
	 * @return <code>true</code> if has at least one child
	 */
	boolean hasChildren();

	/**
	 * @param child
	 * @return true if contains given child
	 */
	boolean hasChild(IHUKey child);

	/**
	 * @param children
	 * @return true if any of <code>children</code> is contained
	 */
	boolean hasAnyChildOf(Collection<? extends IHUKey> children);

	void addChild(IHUKey child);

	/**
	 *
	 * @param children
	 * @return list of {@link IHUKey}s which were actually added
	 */
	List<IHUKey> addChildren(Collection<IHUKey> children);

	/**
	 * Remove given child huKey. Do not call the child's dispose method. If this
	 *
	 * If child was actually removed then {@link #updateName()} is called automatically.
	 *
	 * @param child
	 */
	void removeChild(IHUKey child);

	IHUKey getParent();

	/**
	 * Sets node parent.
	 *
	 * NOTE: NEVER EVER call this method directly. Please use {@link #addChild(IHUKey)}, {@link #removeChild(IHUKey)}.
	 *
	 * @param parent
	 */
	void setParent(IHUKey parent);

	IAttributeStorage getAttributeSet();

	/**
	 *
	 * @return HU's {@link IWeightable} object or null if does not apply
	 */
	IWeightable getWeightOrNull();

	Object getAggregationKey();

	/**
	 *
	 * @return true if this is a grouping node used for grouping multiple nodes
	 */
	boolean isGrouping();

	/**
	 * @return {@code true} if this is a virtual handling unit (but NOT strictly a pure virtual one). However, return {@code false} if this is an aggregate HU!
	 */
	boolean isVirtualPI();

	boolean isReadonly();

	void setReadonly(boolean readonly);

	/**
	 * Checks if the {@link IHUKey} was flagged as destroyed and it shall not longer be part on ANY HU structure.
	 *
	 * NOTE: when implementing this method keep in mind this state is considered ireversible, so, once it's flaged as Destroyed there cannot be un-destroyed.
	 *
	 * @return true
	 */
	boolean isDestroyed();

	/**
	 *
	 * @return root key; never return null
	 */
	IHUKey getRoot();

	/**
	 * Updates key displayed text (i.e. name).
	 */
	void updateName();

	void fireStatusChanged();

	/**
	 * Iterate pre-order (i.e. node first, children last)
	 *
	 * @param visitor
	 * @return
	 */
	IHUKeyVisitor.VisitResult iterate(IHUKeyVisitor visitor);

	/**
	 * Called by API when user presses on this key
	 */
	void notifyKeyPressed();

	/**
	 * Refresh this key (display name, children etc).
	 *
	 * If the HU key {@link #isDestroyed()} it will be directly removed from it's parent.
	 */
	void refresh();

	/**
	 * Sets custom property.
	 *
	 * @param propertyName
	 * @param value
	 */
	void setProperty(String propertyName, Object value);

	/**
	 * Gets custom property.
	 *
	 * @param propertyName
	 * @return
	 */
	<T> T getProperty(String propertyName);

	/**
	 * Sets children filter to be used.
	 *
	 * Fires {@link #ACTION_ChildrenFilterChanged}.
	 *
	 * @param childrenFilter
	 */
	void setChildrenFilter(final IHUKeyChildrenFilter childrenFilter);

	IHUKeyChildrenFilter getChildrenFilter();
}
