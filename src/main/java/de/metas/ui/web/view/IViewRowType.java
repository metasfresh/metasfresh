package de.metas.ui.web.view;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * {@link IViewRow} record type
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface IViewRowType
{
	String getName();
	
	/**
	 * The name of the icon associated with this row type. It's the frontend's job to come up with the actual icon.
	 * 
	 * @return
	 */
	default String getIconName()
	{
		return getName();
	}

}
