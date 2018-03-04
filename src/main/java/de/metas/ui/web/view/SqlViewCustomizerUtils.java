package de.metas.ui.web.view;

import java.util.Comparator;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.OrderUtils;

import lombok.NonNull;
import lombok.experimental.UtilityClass;

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

@UtilityClass
/* package */class SqlViewCustomizerUtils
{
	public static final Comparator<SqlViewCustomizer> ORDERED_COMPARATOR = Comparator.comparing(SqlViewCustomizerUtils::getOrder);

	private static final int getOrder(@NonNull final SqlViewCustomizer viewCustomizer)
	{
		if (viewCustomizer instanceof Ordered)
		{
			return ((Ordered)viewCustomizer).getOrder();
		}

		return OrderUtils.getOrder(viewCustomizer.getClass(), Ordered.LOWEST_PRECEDENCE);
	}
}
