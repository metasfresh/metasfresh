/**
 * This module provides the infrastructure to forward events between different part of the software, also if they run on different machines.
 * To get started using it, check out 
 * <ul>
 * <li>{@link de.metas.event.Topic#builder()}, 
 * <li>{@link de.metas.event.IEventBusFactory#getEventBus(Topic)},
 * <li>{@link de.metas.event.Event#builder()} and
 * <li>{@link de.metas.event.IEventBus#postEvent(Event)}.
 * </ul>
 * <p>Also see:
 * <ul>
 * <li><a href="http://dewiki908/mediawiki/index.php/de.metas.event-Overview">de.metas.event-Overview</a> for informations about how to configure.
 * <li>Task <a href="http://dewiki908/mediawiki/index.php/09292_Have_zk_like_Notification_for_swing_client_%28102960142537%29">09292 Have zk like Notification for swing client (102960142537)</a> for the use case that led to the actual implementation
 * <li>Task <a href="http://dewiki908/mediawiki/index.php/08547_Implement_distributed_event_framework_and_use_it_for_cache_invalidation_%28103388430502%29">08547 Implement distributed event framework and use it for cache invalidation (103388430502)</a> for additional ideas about how to use.
 * </ul>
 */
package de.metas.event;