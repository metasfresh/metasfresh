package org.adempiere.ui.sideactions.model;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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


import java.util.Comparator;

/**
 * Side Action defintion.
 * 
 * A side action, is an item which can be part of a group of actions and can be displayed in a UI side bar.
 * The user can toggle a side action on/off or he can execute an side action.
 * 
 * Imagine this concept as a side panel in a window, where, based on context, some links or checkboxes are displayed and the user can run them.
 * That's a side action.
 * 
 * NOTE to developer: instead of implementing this interface, consider extending
 * <ul>
 * <li> {@link ToggableSideAction} in case you want a toggable side action (i.e. a checkbox)
 * <li> {@link ExecutableSideAction} in case you want a clickable side action (i.e. a button/link)
 * </ul>
 * 
 * @author tsa
 *
 */
public interface ISideAction
{
	/** Sort {@link ISideAction}s by {@link #getDisplayName()} */
	Comparator<ISideAction> COMPARATOR_ByDisplayName = new Comparator<ISideAction>()
	{
		@Override
		public int compare(final ISideAction action1, final ISideAction action2)
		{
			final String displayName1 = getDisplayName(action1);
			final String displayName2 = getDisplayName(action2);
			return displayName1.compareTo(displayName2);
		}

		private final String getDisplayName(final ISideAction action)
		{
			final String displayName = action == null ? "" : action.getDisplayName();
			return displayName == null ? "" : displayName;
		}
	};

	enum SideActionType
	{
		Toggle,
		ExecutableAction,
		Label,
	}

	String getId();

	/** @return user friendly name */
	String getDisplayName();

	/** @return side action type */
	SideActionType getType();

	/**
	 * In case this action is of type {@link SideActionType#Toggle},
	 * this method is called by API/UI to tell this action that it was toggled(activated) or untoggled(deactivated).
	 *
	 * @param toggled
	 */
	void setToggled(final boolean toggled);

	/**
	 * @return true if this side action is of type {@link SideActionType#Toggle} and it's toggled.
	 * @see #setToggled(boolean).
	 */
	boolean isToggled();

	/**
	 * Called by API when user clicks on UI component of this action.
	 */
	void execute();

}
