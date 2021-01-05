package de.metas.procurement.webui.ui.model;

import java.math.BigDecimal;
import java.util.Date;

import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.data.Property;
import com.vaadin.data.util.BeanItem;
import com.vaadin.data.util.BeanItemContainer;

import de.metas.procurement.webui.model.BPartner;
import de.metas.procurement.webui.model.Product;
import de.metas.procurement.webui.model.Trend;
import de.metas.procurement.webui.ui.view.WeekProductQtyReport;
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

@SuppressWarnings("serial")
public class WeekProductQtyReportContainer extends BeanItemContainer<WeekProductQtyReport>
{
	private BPartner bpartner;
	private final DateRange week;

	public WeekProductQtyReportContainer(final BPartner bpartner, final DateRange week)
	{
		super(WeekProductQtyReport.class);
		this.bpartner = Preconditions.checkNotNull(bpartner);
		this.week = Preconditions.checkNotNull(week);
	}

	public void addDailyQtyReport(final BeanItem<ProductQtyReport> item)
	{
		final ProductQtyReport bean = item.getBean();
		final Date day = bean.getDay();
		if (!week.contains(day))
		{
			return;
		}

		final Product product = bean.getProduct();
		final BeanItem<WeekProductQtyReport> weekQtyReportItem = getCreateItemByProduct(product);
		addQty(weekQtyReportItem, bean.getQty());

		weekQtyReportItem.getBean().getDailyQtyReportContainer().addBeanItem(item);
	}

	private final void addQty(final BeanItem<WeekProductQtyReport> weekQtyReportItem, final BigDecimal qtyToAdd)
	{
		@SuppressWarnings("unchecked")
		final Property<BigDecimal> qtyProperty = weekQtyReportItem.getItemProperty(WeekProductQtyReport.PROPERTY_Qty);

		final BigDecimal qtyOld = qtyProperty.getValue();
		final BigDecimal qtyNew = qtyOld.add(qtyToAdd);
		qtyProperty.setValue(qtyNew);
	}

	private final void setQty(final BeanItem<WeekProductQtyReport> weekQtyReportItem, final BigDecimal qty)
	{
		@SuppressWarnings("unchecked")
		final Property<BigDecimal> qtyProperty = weekQtyReportItem.getItemProperty(WeekProductQtyReport.PROPERTY_Qty);
		qtyProperty.setValue(qty);
	}

	public void updateQtys()
	{
		for (final WeekProductQtyReport itemId : getAllItemIds())
		{
			final BeanItem<WeekProductQtyReport> item = getItem(itemId);
			updateQty(item);
		}
	}

	private void updateQty(final BeanItem<WeekProductQtyReport> item)
	{
		BigDecimal qtyTotal = BigDecimal.ZERO;
		for (final ProductQtyReport dailyReport : item.getBean().getDailyQtyReportContainer().getItemIds())
		{
			qtyTotal = qtyTotal.add(dailyReport.getQty());
		}

		setQty(item, qtyTotal);
	}

	public void setNextWeekTrend(final Product product, final Trend nextWeekTrend)
	{
		final BeanItem<WeekProductQtyReport> weekQtyReportItem = getCreateItemByProduct(product);
		@SuppressWarnings("unchecked")
		final Property<Trend> nextWeekTrendProperty = weekQtyReportItem.getItemProperty(WeekProductQtyReport.PROPERTY_NextWeekTrend);
		nextWeekTrendProperty.setValue(nextWeekTrend);
	}
	
	public boolean hasProduct(final Product product)
	{
		return getItemByProduct(product) != null;
	}

	private BeanItem<WeekProductQtyReport> getItemByProduct(final Product product)
	{
		Preconditions.checkNotNull(product);

		for (final WeekProductQtyReport itemId : getAllItemIds())
		{
			if (!product.equals(itemId.getProduct()))
			{
				continue;
			}

			final BeanItem<WeekProductQtyReport> item = getItem(itemId);
			return item;
		}

		return null;
	}

	private BeanItem<WeekProductQtyReport> getCreateItemByProduct(final Product product)
	{
		BeanItem<WeekProductQtyReport> item = getItemByProduct(product);
		if (item != null)
		{
			return item;
		}

		final WeekProductQtyReport bean = WeekProductQtyReport.of(bpartner, product, week);
		item = addBean(bean);
		sort();
		return item;
	}

	public void resetAllDailyReportings()
	{
		for (final WeekProductQtyReport itemId : getAllItemIds())
		{
			final BeanItem<WeekProductQtyReport> item = getItem(itemId);
			setQty(item, BigDecimal.ZERO);
			item.getBean().getDailyQtyReportContainer().removeAllItems();
		}
	}

	public void sort()
	{
		this.sort(new Object[] { WeekProductQtyReport.PROPERY_ProductName }, new boolean[] { true });
	}

	public boolean removeProductIfZeroQty(Product product)
	{
		final BeanItem<WeekProductQtyReport> item = getItemByProduct(product);
		if(item == null)
		{
			return false;
		}
		final WeekProductQtyReport bean = item.getBean();
		if (bean == null)
		{
			return false;
		}
		
		if (bean.getQty().signum() != 0)
		{
			return false;
		}
		
		final Object itemId = bean;
		if (!removeItem(itemId))
		{
			return false;
		}
		
		return true;
	}

}
