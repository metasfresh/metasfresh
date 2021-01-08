package de.metas.procurement.webui.model;

import com.google.common.base.MoreObjects;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.YearWeekUtil;
import lombok.Builder;
import lombok.NonNull;
import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.context.annotation.Lazy;
import org.threeten.extra.YearWeek;

import javax.annotation.Nullable;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import java.time.LocalDate;



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

@Entity
@Table(name = "week_supply" //
		, uniqueConstraints = @UniqueConstraint(name = "week_supply_uq", columnNames = { "bpartner_id", "product_id", "day" })     //
)
@SelectBeforeUpdate
public class WeekSupply extends AbstractSyncConfirmAwareEntity
{
	@ManyToOne
	@Lazy
	@NonNull
	private BPartner bpartner;

	@ManyToOne
	@NonNull
	private Product product;

	@NonNull
	private java.sql.Date day;

	@Nullable
	private String trend;

	protected WeekSupply()
	{
	}

	@Builder
	private WeekSupply(
			@NonNull final BPartner bpartner,
			@NonNull final Product product,
			@NonNull final LocalDate day)
	{
		this.bpartner = bpartner;
		this.product = product;
		this.day = DateUtils.toSqlDate(day);
	}

	@Override
	protected void toString(final MoreObjects.ToStringHelper toStringHelper)
	{
		toStringHelper
				.add("product", product)
				.add("bpartner", bpartner)
				.add("day", day)
				.add("trend", trend);
	}

	public Long getProductId()
	{
		return product.getId();
	}

	public String getProductIdAsString()
	{
		return product.getIdAsString();
	}

	public String getProductUUID()
	{
		return product.getUuid();
	}

	public String getBpartnerUUID()
	{
		return bpartner.getUuid();
	}

	public LocalDate getDay()
	{
		return day.toLocalDate();
	}

	public YearWeek getWeek()
	{
		return YearWeekUtil.ofLocalDate(day.toLocalDate());
	}

	@Nullable
	public Trend getTrend()
	{
		return Trend.ofNullableCode(trend);
	}

	@Nullable
	public String getTrendAsString()
	{
		return trend;
	}

	public void setTrend(@Nullable final Trend trend)
	{
		this.trend = trend != null ? trend.getCode() : null;
	}
}
