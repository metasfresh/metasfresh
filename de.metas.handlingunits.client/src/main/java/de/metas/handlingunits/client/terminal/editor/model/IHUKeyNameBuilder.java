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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


import de.metas.handlingunits.attribute.IAttributeValue;

/**
 * Interface for helper classes used to assist HUKey name (label) building.
 *
 * Implementations are aware of content and they can rebuild only some parts of HU Key's name.
 *
 * @author tsa
 *
 */
public interface IHUKeyNameBuilder
{
	/**
	 * Gets the name which was last built
	 *
	 * @return last built name or null if {@link #build()} was never invoked before
	 */
	public String getLastBuiltName();

	/**
	 * Build {@link IHUKey}'s displayed name.
	 *
	 * @return displayed name
	 */
	public String build();

	/**
	 * Reset all cached parts for displayed name.
	 *
	 * This will also make our builder staled.
	 */
	public void reset();

	/**
	 *
	 * @return true if name is stale and a {@link #build()} is needed
	 */
	boolean isStale();

	/**
	 * Notify builder that an attribute value was changed and let it reset only those displayed name parts which are affected by this attribute.
	 *
	 * @param attributeValue
	 */
	public void notifyAttributeValueChanged(IAttributeValue attributeValue);

	/**
	 * Notify builder that a child was added/removed/changed.
	 *
	 * @param childKey
	 */
	void notifyChildChanged(IHUKey childKey);
}
