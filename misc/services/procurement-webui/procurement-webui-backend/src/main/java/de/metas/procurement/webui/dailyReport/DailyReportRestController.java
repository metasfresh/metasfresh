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

package de.metas.procurement.webui.dailyReport;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.service.ILoginService;
import de.metas.procurement.webui.service.IProductSuppliesService;
import lombok.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping(DailyReportRestController.ENDPOINT)
public class DailyReportRestController
{
	static final String ENDPOINT = Application.ENDPOINT_ROOT + "/dailyReport";

	private final ILoginService loginService;
	private final IProductSuppliesService productSuppliesRepository;

	public DailyReportRestController(
			@NonNull final ILoginService loginService,
			@NonNull final IProductSuppliesService productSuppliesRepository)
	{
		this.loginService = loginService;
		this.productSuppliesRepository = productSuppliesRepository;
	}

	@GetMapping("/{date}")
	public void getDailyReport(@PathVariable("date") @NonNull final String dateStr)
	{
		loginService.assertLoggedIn();

		final LocalDate date = LocalDate.parse(dateStr);
		// productSuppliesRepository.get
	}

	@PostMapping
	public void postDailyReport()
	{
		// TODO
	}
}
