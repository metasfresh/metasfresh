package de.metas.ui.web.window;

import java.util.UUID;

import org.springframework.web.context.request.RequestAttributes;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
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

public class MockedRequestAttributes implements RequestAttributes
{
	private String sessionId = UUID.randomUUID().toString();
	private final Object sessionMutex = new Object();

	@Override
	public Object getAttribute(String name, int scope)
	{
		return null;
	}

	@Override
	public void setAttribute(String name, Object value, int scope)
	{
	}

	@Override
	public void removeAttribute(String name, int scope)
	{
	}

	@Override
	public String[] getAttributeNames(int scope)
	{
		return new String[]{};
	}

	@Override
	public void registerDestructionCallback(String name, Runnable callback, int scope)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object resolveReference(String key)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSessionId()
	{
		return sessionId;
	}

	@Override
	public Object getSessionMutex()
	{
		return sessionMutex;
	}

}
