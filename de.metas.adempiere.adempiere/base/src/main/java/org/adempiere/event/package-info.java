/**
 * This module provides the infrastructure to forward events between different part of the software, also if they run on different machines.
 * To get started using it, check out 
 * <ul>
 * <li>{@link org.adempiere.event.Topic#builder()}, 
 * <li>{@link org.adempiere.event.IEventBusFactory#getEventBus(Topic)},
 * <li>{@link org.adempiere.event.Event#builder()} and
 * <li>{@link org.adempiere.event.IEventBus#postEvent(Event)}.
 * </ul>
 * <p>Also see:
 * <ul>
 * <li><a href="http://dewiki908/mediawiki/index.php/Org.adempiere.event-Overview">Org.adempiere.event-Overview</a> for informations about how to configure.
 * <li>Task <a href="http://dewiki908/mediawiki/index.php/09292_Have_zk_like_Notification_for_swing_client_%28102960142537%29">09292 Have zk like Notification for swing client (102960142537)</a> for the use case that led to the actual implementation
 * <li>Task <a href="http://dewiki908/mediawiki/index.php/08547_Implement_distributed_event_framework_and_use_it_for_cache_invalidation_%28103388430502%29">08547 Implement distributed event framework and use it for cache invalidation (103388430502)</a> for additional ideas about how to use.
 * </ul>
 */
package org.adempiere.event;

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
