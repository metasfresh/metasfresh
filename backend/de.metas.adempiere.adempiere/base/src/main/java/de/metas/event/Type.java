package de.metas.event;

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


/**
 * The type of the topic or event bus.<br>
 * Notes:
 * <ul>
 * <li>Two topics with the same name but different type are different and will <b>always</b> have different event busses. Therefore, events posted to one of of those topics won't be forwarded
 * subscribers of the other one.
 * <li>Creating an {@link IEventBus} for a remote {@link Topic} might still result in a local eventBus if the remote-forwarding feature is configured to be "off" for the given topic name or
 * login-user, or in general.
 * </ul>
 * 
 * @author ts
 *
 */
public enum Type
{
	/**
	 * If event are posed to a local topic, only local subscribers will be notified.
	 */
	LOCAL,

	/**
	 * If events are posted to a "remote" topic, not only subscribers on this machine, but also subscribers on other machines will be notified.
	 */
	REMOTE
}
