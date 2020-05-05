package de.metas.ui.web.handlingunits.process;

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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * #2144
 * HU editor: Move selected HUs to another warehouse
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 *         This process is completely similar with the basic structure for HU moving process.
 *
 */
public class WEBUI_M_HU_MoveToAnotherWarehouse_InclQuarantined extends WEBUI_M_HU_MoveToAnotherWarehouse_Helper
{

	@Override
	public void assertHUsEligible()
	{
		// Nothing to do. This process includes also quarantine HUs
	}

}
