package de.metas.procurement.webui.ui.view;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.service.IProductSuppliesService;
import de.metas.procurement.webui.ui.model.ProductQtyReportContainer;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;
import de.metas.procurement.webui.util.ProductNameCaptionBuilder;

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

public class WeekProductQtyReport
{
	public static final WeekProductQtyReport of(final BPartner bpartner, final Product product, final DateRange week)
	{
		return new WeekProductQtyReport(bpartner, product, week);
	}

	public static final String PROPERY_ProductName = "productName";
	public static final String PROPERTY_Qty = "qty";
	public static final String PROPERTY_NextWeekTrend = "nextWeekTrend";

	@Autowired
    private I18N i18n;

	@Autowired
	private IProductSuppliesService productSuppliesService;

	private final Object id;
	private final BPartner bpartner;
	private final Product product;
	private final DateRange _week;
	private BigDecimal qty = BigDecimal.ZERO;
	private Trend nextWeekTrend = null;
	private final ProductQtyReportContainer dailyQtyReportContainer = new ProductQtyReportContainer();

	private String _caption;

	private WeekProductQtyReport(final BPartner bpartner, final Product product, final DateRange week)
	{
		super();
		Application.autowire(this);
		
		this.bpartner = Preconditions.checkNotNull(bpartner);
		this.product = Preconditions.checkNotNull(product);
		this._week = Preconditions.checkNotNull(week);
		this.id = "" + product.getId() + "-" + week.getDateFrom().getTime() + "-" + week.getDateTo().getTime();
		this.qty = Preconditions.checkNotNull(qty);
		this.nextWeekTrend = productSuppliesService.getNextWeekTrend(bpartner, product, _week);
	}

	@Override
	public String toString()
	{
		return Objects.toStringHelper(this)
				.add("product", product)
				.add("qty", qty)
				.add("week", _week)
				.add("nextWeekTrend", nextWeekTrend)
				.add("id", id)
				.toString();
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (id == null ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj)
	{
		if (this == obj)
		{
			return true;
		}
		if (obj == null)
		{
			return false;
		}
		if (getClass() != obj.getClass())
		{
			return false;
		}
		final WeekProductQtyReport other = (WeekProductQtyReport)obj;
		if (id == null)
		{
			if (other.id != null)
			{
				return false;
			}
		}
		else if (!id.equals(other.id))
		{
			return false;
		}
		return true;
	}

	public DateRange getWeek()
	{
		return _week;
	}

	public String getCaption()
	{
		if (_caption == null)
		{
			_caption = buildCaption();
		}
		return _caption;
	}

	private final String buildCaption()
	{
		final DateRange week = getWeek();
		return ProductNameCaptionBuilder.newBuilder()
				.setProductName(getProductName())
				.setProductPackingInfo(getProductPackingInfo())
				.setProductNameDetailShortInfo(DateUtils.formatWeekNumberWithPrefix(week.getDateFrom()))
				.build();
	}

	/** @return next week number formated string (i.e. KWxx) */
	public String getNextWeekString()
	{
		final DateRange week = getWeek();
		final DateRange nextWeek = week.getNextWeek();
		return DateUtils.formatWeekNumberWithPrefix(nextWeek.getDateFrom());
	}

	public String getProductName()
	{
		return product.getName(i18n.getLocale());
	}

	public String getProductPackingInfo()
	{
		return product.getPackingInfo(i18n.getLocale());
	}

	public Product getProduct()
	{
		return product;
	}

	public BigDecimal getQty()
	{
		return qty;
	}

	public void setQty(final BigDecimal qty)
	{
		this.qty = Preconditions.checkNotNull(qty);
	}

	public Trend getNextWeekTrend()
	{
		return nextWeekTrend;
	}

	public void setNextWeekTrend(final Trend nextWeekTrend)
	{
		if (this.nextWeekTrend == nextWeekTrend)
		{
			return;
		}
		
		Preconditions.checkNotNull(nextWeekTrend);
		this.nextWeekTrend = nextWeekTrend;
		
		productSuppliesService.setNextWeekTrend(bpartner, product, getWeek(), nextWeekTrend);
	}

	public ProductQtyReportContainer getDailyQtyReportContainer()
	{
		return dailyQtyReportContainer;
	}
}
