/*
 * #%L
 * procurement-webui-backend
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.procurement.webui.rest.rfq;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.impl.RfQService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(RfQRestController.ENDPOINT)
public class RfQRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/rfq";

	// TODO: implement RfQRestController

	private final ILoginService loginService;
	private final RfQService rfqService;

	public RfQRestController(
			@NonNull final ILoginService loginService,
			@NonNull final RfQService rfqService)
	{
		this.loginService = loginService;
		this.rfqService = rfqService;
	}

	public void get()
	{
	}

}
