package de.metas.event;

import java.util.List;

import de.metas.util.ISingletonService;

/**
 * Creates and manages {@link IEventBus}es.
 *
 * This is the main entry point for events module.
 *
 * @author tsa
 *
 */
public interface IEventBusFactory extends ISingletonService
{
	/**
	 * @return event bus; never returns <code>null</code>
	 */
	IEventBus getEventBus(Topic topic);

	/**
	 * @return event bus or null
	 */
	IEventBus getEventBusIfExists(Topic topic);

	List<IEventBus> getAllEventBusInstances();

	/**
	 * Create an remotely bind all {@link IEventBus}es for which we have global listeners registered.
	 *
	 * @see #registerGlobalEventListener(Topic, IEventListener)
	 */
	void initEventBussesWithGlobalListeners();

	/**
	 * Destroy all created event bus objects.
	 */
	void destroyAllEventBusses();

	/**
	 * Register a global {@link IEventListener}.
	 *
	 * Global event listeners will be automatically registered when the {@link IEventBus} is created. Also, the listener will be registered to event bus.
	 *
	 * The advantage of using this method instead of {@link IEventBus#subscribe(IEventListener)} is that those listeners will survive also after an reset (i.e. {@link #destroyAllEventBusses()}).
	 *
	 * @param topic
	 * @param listener
	 * @see #initEventBussesWithGlobalListeners()
	 */
	void registerGlobalEventListener(final Topic topic, final IEventListener listener);

	/**
	 * Adds a topic on which currently login user shall subscribe for UI notifications.
	 */
	void addAvailableUserNotificationsTopic(final Topic topic);

	void registerUserNotificationsListener(final IEventListener listener);

	void unregisterUserNotificationsListener(final IEventListener listener);

	/**
	 * Check remote endpoint connection status and send notifications in case it's down.
	 *
	 * @return true if remote endpoint connection is up
	 */
	boolean checkRemoteEndpointStatus();
}
