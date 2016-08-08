package de.metas.ui.web.window;

import java.io.Serializable;

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

@SuppressWarnings("serial")
public final class DocumentField implements Serializable
{
	public static final Builder builder()
	{
		return new Builder();
	}
	
	// TODO: WIP
	
//	private final String name;
//	private final String caption;
	
	private Object value;
	
	private boolean mandatory;
	private boolean readonly;
	private boolean displayed;
	
	private DocumentField(final Builder builder)
	{
		super();
	}
	
	public static final class Builder
	{
		private String name;
		private String caption;
	}
}
