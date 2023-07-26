package de.metas;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/*
 * #%L
 * de.metas.adempiere.adempiere.serverRoot.base
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

@Controller
public class IndexController
{
	/**
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(path = { "/" })
	public String home(final Model model)
	{
		String productName = org.compiere.Adempiere.getName();
		if (productName == null || productName.isEmpty())
		{
			productName = "metasfresh";
		}
		String productURL = org.compiere.Adempiere.getURL();
		if (productURL == null || productURL.isEmpty())
		{
			productURL = "http://www.metasfresh.com/en";
		}


		model.addAttribute("productName", productName);
		model.addAttribute("productURL", productURL);

		return "index";
	}
}
