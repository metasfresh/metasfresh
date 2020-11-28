package de.metas;

import org.springframework.boot.autoconfigure.SpringBootApplication;

/*
 * #%L
 * de.metas.endcustomer.mf15.swingui
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

/**
 * Swing application starter.
 *
 * WARNING: please keep in sync with all other SwingUIApplications
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
@SpringBootApplication(scanBasePackages = { "de.metas", "org.adempiere" })
public class SwingUIApplication extends SwingUIApplicationTemplate
{
	public static void main(final String[] args)
	{
		main(SwingUIApplication.class, args);
	}
}
