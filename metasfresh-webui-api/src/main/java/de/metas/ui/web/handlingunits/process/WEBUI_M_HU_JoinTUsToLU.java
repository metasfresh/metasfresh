package de.metas.ui.web.handlingunits.process;

import org.springframework.context.annotation.Profile;

import de.metas.process.IProcessPrecondition;
import de.metas.ui.web.WebRestApiApplication;

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
 * Join selected TUs to a new LU or to an existing LU
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@Profile(value = WebRestApiApplication.PROFILE_Webui)
public class WEBUI_M_HU_JoinTUsToLU extends HUViewProcessTemplate implements IProcessPrecondition
{
	@Override
	protected String doIt() throws Exception
	{
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException();
		// return MSG_OK;
	}

}
