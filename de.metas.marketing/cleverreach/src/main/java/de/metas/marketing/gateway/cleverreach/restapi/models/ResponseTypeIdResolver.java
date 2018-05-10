package de.metas.marketing.gateway.cleverreach.restapi.models;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;
import com.fasterxml.jackson.databind.DatabindContext;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.jsontype.TypeIdResolver;

/*
 * #%L
 * marketing-cleverreach
 * %%
 * Copyright (C) 2018 metas GmbH
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

public class ResponseTypeIdResolver implements TypeIdResolver
{

	@Override
	public void init(JavaType baseType)
	{
		// TODO Auto-generated method stub

	}

	@Override
	public String idFromValue(Object value)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String idFromValueAndType(Object value, Class<?> suggestedType)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String idFromBaseType()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JavaType typeFromId(DatabindContext context, String id) throws IOException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getDescForKnownTypeIds()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Id getMechanism()
	{
		// TODO Auto-generated method stub
		return null;
	}

}
