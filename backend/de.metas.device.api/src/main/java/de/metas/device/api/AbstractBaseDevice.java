package de.metas.device.api;

import java.util.concurrent.ConcurrentHashMap;

import de.metas.device.api.request.DeviceRequestConfigureDevice;
import de.metas.device.api.request.DeviceRequestGetConfigParams;
import de.metas.device.api.request.IDeviceResponseGetConfigParams;

public abstract class AbstractBaseDevice implements IDevice
{
	/**
	 * The constructor registers a handler for the {@link DeviceRequestGetConfigParams} request. That handler returns the result of {@link #getRequiredConfigParams()}. This way each subclass can be
	 * queried for its config params and can be configured with after is was instantiated.
	 */
	public AbstractBaseDevice()
	{
		// registering the the handler that returns out required configuration info
		registerHandler(DeviceRequestGetConfigParams.class, request -> getRequiredConfigParams());

		registerHandler(DeviceRequestConfigureDevice.class, getConfigureDeviceHandler());
	}

	@SuppressWarnings("rawtypes")
	private final ConcurrentHashMap<Class<?>, IDeviceRequestHandler> requestType2Handler = new ConcurrentHashMap<>();

	protected final <O extends IDeviceResponse, I extends IDeviceRequest<O>, H extends IDeviceRequestHandler<I, O>> void registerHandler(final Class<I> requestType, final H handler)
	{
		requestType2Handler.put(requestType, handler);
	}

	@Override
	public final <O extends IDeviceResponse, I extends IDeviceRequest<O>> O accessDevice(final I input)
	{
		@SuppressWarnings("unchecked")
		final IDeviceRequestHandler<IDeviceRequest<IDeviceResponse>, IDeviceResponse> deviceRequestHandler = requestType2Handler.get(input.getClass());

		@SuppressWarnings("unchecked")
		final O response = (O)deviceRequestHandler.handleRequest((IDeviceRequest<IDeviceResponse>)input);

		return response;
	}

	/**
	 * Currently this method is called from the device's default constructor, so it must work without making any assumption about the device's initialization state.
	 */
	public abstract IDeviceResponseGetConfigParams getRequiredConfigParams();

	/**
	 * Returns that particular request handler which is in charge of configuring the device using parameters from the "outside world".
	 */
	public abstract IDeviceRequestHandler<DeviceRequestConfigureDevice, IDeviceResponse> getConfigureDeviceHandler();
}
