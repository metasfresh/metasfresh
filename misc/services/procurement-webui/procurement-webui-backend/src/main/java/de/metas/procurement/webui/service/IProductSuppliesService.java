package de.metas.procurement.webui.service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.WeekSupply;
import de.metas.procurement.webui.util.DateRange;

/*
 * #%L
 * de.metas.procurement.webui
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

public interface IProductSuppliesService
{
	void reportSupply(final BPartner bpartner, final Product product, final ContractLine contractLine, final Date day, final BigDecimal qty);

	List<ProductSupply> getProductSupplies(final BPartner bpartner, final Date date);

	List<ProductSupply> getProductSupplies(long bpartner_id, long product_id, Date dayFrom, Date dayTo);

	List<Product> getUserFavoriteProducts(final User user);

	void addUserFavoriteProduct(final User user, final Product product);

	boolean removeUserFavoriteProduct(final User user, final Product product);

	List<Product> getAllProducts();

	List<Product> getAllSharedProducts();

	Trend getNextWeekTrend(BPartner bpartner, Product product, DateRange week);

	WeekSupply setNextWeekTrend(BPartner bpartner, Product product, DateRange week, Trend trend);

	List<WeekSupply> getWeeklySupplies(long bpartner_id, long product_id, Date dayFrom, Date dayTo);
}
