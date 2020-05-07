package de.metas.event.jmx;

import org.adempiere.util.jmx.IJMXNameAware;

import de.metas.event.EventBusConstants;
import de.metas.event.remote.IEventBusRemoteEndpoint;

public class JMXEventBusManager implements JMXEventBusManagerMBean, IJMXNameAware
{
	private final String jmxName;
	private final IEventBusRemoteEndpoint remoteEndpoint;

	public JMXEventBusManager(final IEventBusRemoteEndpoint remoteEndpoint)
	{
		super();
		this.jmxName = EventBusConstants.JMX_BASE_NAME + ":type=EventBusManager";
		this.remoteEndpoint = remoteEndpoint;
	}

	@Override
	public final String getJMXName()
	{
		return jmxName;
	}

	@Override
	public boolean isEnabled()
	{
		return EventBusConstants.isEnabled();
	}
	
	@Override
	public String getRemoteEndpointInfo()
	{
		return remoteEndpoint.toString();
	}
	
	@Override
	public boolean isRemoteEndpointConnected()
	{
		return remoteEndpoint.isConnected();
	}

	@Override
	public String getSenderId()
	{
		return EventBusConstants.getSenderId();
	}
}
