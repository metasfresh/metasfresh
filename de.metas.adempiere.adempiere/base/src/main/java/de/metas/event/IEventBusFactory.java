package de.metas.event;

import org.adempiere.util.ISingletonService;

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
	 * Get/create the event bus for the given <code>topic</code>.
	 *
	 * @param name
	 * @return event bus; never returns <code>null</code>
	 */
	public IEventBus getEventBus(Topic topic);

	/**
	 * Create an remotely bind all {@link IEventBus}es for which we have global listeners registered.
	 * 
	 * @see #registerGlobalEventListener(Topic, IEventListener)
	 */
	public void initEventBussesWithGlobalListeners();

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
	 * 
	 * @param topic
	 */
	void addAvailableUserNotificationsTopic(final Topic topic);

	void registerUserNotificationsListener(final IEventListener listener);

	void registerWeakUserNotificationsListener(IEventListener listener);

	/**
	 * Check remote endpoint connection status and send notifications in case it's down.
	 * 
	 * @return true if remote endpoint connection is up
	 */
	boolean checkRemoteEndpointStatus();

}
