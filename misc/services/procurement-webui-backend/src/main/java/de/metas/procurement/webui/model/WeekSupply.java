package de.metas.procurement.webui.model;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.google.common.base.MoreObjects;
import lombok.NonNull;

import org.hibernate.annotations.SelectBeforeUpdate;
import org.springframework.context.annotation.Lazy;



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
@SuppressWarnings("serial")
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
	private Date day;

	private String trend;

	public WeekSupply()
	{
		super();
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

	public BPartner getBpartner()
	{
		return bpartner;
	}

	public void setBpartner(BPartner bpartner)
	{
		this.bpartner = bpartner;
	}

	public Product getProduct()
	{
		return product;
	}

	public void setProduct(Product product)
	{
		this.product = product;
	}

	public Date getDay()
	{
		return day;
	}

	public void setDay(Date day)
	{
		this.day = day;
	}

	public String getTrend()
	{
		return trend;
	}

	public void setTrend(String trend)
	{
		this.trend = trend;
	}
}
