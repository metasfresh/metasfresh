package de.metas.procurement.webui.service;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.ContractLine;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.ProductSupply;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.model.User;
import de.metas.procurement.webui.model.WeekSupply;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;
import org.threeten.extra.YearWeek;

import javax.annotation.Nullable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

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

public interface IProductSuppliesService extends UserConfirmationHandler
{
	@Override
	void confirmUserEntries(User user);

	@Override
	long getCountUnconfirmed(User user);

	@Value
	@Builder
	class ReportDailySupplyRequest
	{
		@NonNull BPartner bpartner;
		ContractLine contractLine;

		long productId;
		@NonNull LocalDate date;
		@NonNull BigDecimal qty;

		@Builder.Default
		boolean qtyConfirmedByUser = false;
	}

	void reportSupply(ReportDailySupplyRequest request);

	List<ProductSupply> getProductSupplies(final BPartner bpartner, final LocalDate date);

	List<ProductSupply> getProductSupplies(long bpartner_id, long product_id, LocalDate dayFrom, LocalDate dayTo);

	ProductSupply getProductSupplyById(long product_supply_id);

	List<Product> getUserFavoriteProducts(final User user);

	void addUserFavoriteProduct(final User user, final Product product);

	void removeUserFavoriteProduct(final User user, final Product product);

	List<Product> getAllProducts();

	List<Product> getAllSharedProducts();

	@Nullable
	Trend getNextWeekTrend(BPartner bpartner, Product product, YearWeek week);

	WeekSupply setNextWeekTrend(BPartner bpartner, Product product, YearWeek week, Trend trend);

	List<WeekSupply> getWeeklySupplies(long bpartner_id, long product_id, LocalDate dayFrom, LocalDate dayTo);

	List<WeekSupply> getWeeklySupplies(BPartner bpartner, @Nullable Product product, YearWeek week);

	Product getProductById(long productId);

	@Value
	@Builder
	class ImportPlanningSupplyRequest
	{
		@NonNull BPartner bpartner;
		ContractLine contractLine;

		@NonNull String product_uuid;
		@NonNull LocalDate date;
		@NonNull BigDecimal qty;
	}

	void importPlanningSupply(ImportPlanningSupplyRequest request);
}
