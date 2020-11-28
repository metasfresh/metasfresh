package de.metas.procurement.webui.ui.component;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.spring.i18n.I18N;

import com.google.gwt.thirdparty.guava.common.base.Objects;
import com.google.gwt.thirdparty.guava.common.base.Preconditions;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;

import de.metas.procurement.webui.Application;
import de.metas.procurement.webui.util.DateRange;
import de.metas.procurement.webui.util.DateUtils;

import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

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
public class DateNavigation extends HorizontalLayout
{
	private static final String STYLE = "date-panel";
	private static final String STYLE_DayLabel = "day-label";

	public static final String PROPERTY_Date = "date";
	private Date _date = null;
	private DateRange dateRange;

	public static final DateNavigationHandler DATEHANDLER_Day = new DayNavigationHandler();
	public static final DateNavigationHandler DATEHANDLER_Week = new WeekNavigationHandler();

	@Autowired
	private I18N i18n;

	private DateNavigationHandler dateHandler = DATEHANDLER_Day;

	private final Button nextDateButton;
	private final Button prevDateButton;
	private final Label dateLabel;

	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);

	public DateNavigation()
	{
		super();
		Application.autowire(this);

		setSizeFull();
		addStyleName(STYLE);

		prevDateButton = new Button();
		prevDateButton.setEnabled(false);
		prevDateButton.setIcon(FontAwesome.ARROW_LEFT);
		prevDateButton.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(final ClickEvent event)
			{
				onPrevDate();
			}
		});

		nextDateButton = new Button();
		nextDateButton.setEnabled(false);
		nextDateButton.setIcon(FontAwesome.ARROW_RIGHT);
		nextDateButton.addClickListener(new ClickListener()
		{
			@Override
			public void buttonClick(final ClickEvent event)
			{
				onNextDate();
			}
		});

		dateLabel = new Label();
		dateLabel.setSizeFull();
		dateLabel.addStyleName(STYLE_DayLabel);

		addComponents(prevDateButton, dateLabel, nextDateButton);
		setExpandRatio(dateLabel, 1.0f);
	}

	public void setDate(final Date date)
	{
		Preconditions.checkNotNull(date, "date");

		final Date dateOld = _date;
		final Date dateNew = dateHandler.normalize(date);
		if (Objects.equal(dateOld, dateNew))
		{
			return;
		}
		_date = dateNew;

		updateUI();

		//
		pcs.firePropertyChange(PROPERTY_Date, dateOld, dateNew);
	}

	public Date getDate()
	{
		if (_date == null)
		{
			return null;
		}
		return (Date)_date.clone();
	}

	public void setDateHandler(final DateNavigationHandler dateHandler)
	{
		if (Objects.equal(this.dateHandler, dateHandler))
		{
			return;
		}

		Preconditions.checkNotNull(dateHandler);
		this.dateHandler = dateHandler;

		updateUI();
	}

	public void setDateRange(final DateRange dateRange)
	{
		if (Objects.equal(dateHandler, dateRange))
		{
			return;
		}
		this.dateRange = dateRange;
		updateUI();
	}

	public void addDateChangedListener(final PropertyChangeListener listener)
	{
		pcs.addPropertyChangeListener(PROPERTY_Date, listener);
	}

	public void removeDateChangedListener(final PropertyChangeListener listener)
	{
		pcs.removePropertyChangeListener(PROPERTY_Date, listener);
	}

	/**
	 * Hide/Show navigation buttons.
	 * 
	 * If hidden, this will actually make this component readonly since the user has no posibility to change the date.
	 * 
	 * @param hideNavigationButtons
	 */
	public void setHideNavigationButtons(final boolean hideNavigationButtons)
	{
		final boolean visible = !hideNavigationButtons;
		prevDateButton.setVisible(visible);
		nextDateButton.setVisible(visible);
	}

	private void updateUI()
	{
		final Date date = _date;

		if (date == null)
		{
			dateLabel.setValue(null);
			prevDateButton.setEnabled(false);
			nextDateButton.setEnabled(false);
			return;
		}

		prevDateButton.setEnabled(dateRange == null || dateRange.contains(dateHandler.getPreviousDate(date)));
		nextDateButton.setEnabled(dateRange == null || dateRange.contains(dateHandler.getNextDate(date)));

		final String dateStr = dateHandler.toString(date, i18n);
		dateLabel.setValue(dateStr);
	}

	private void onPrevDate()
	{
		final Date prevDate = dateHandler.getPreviousDate(getDate());
		setDate(prevDate);
	}

	private void onNextDate()
	{
		final Date nextDate = dateHandler.getNextDate(getDate());
		setDate(nextDate);
	}

	public static interface DateNavigationHandler
	{
		Date normalize(final Date date);

		Date getNextDate(final Date date);

		Date getPreviousDate(final Date date);

		String toString(final Date date, final I18N i18n);
	}

	public static class DayNavigationHandler implements DateNavigationHandler
	{
		protected DayNavigationHandler()
		{
			super();
		}

		@Override
		public Date normalize(final Date date)
		{
			return DateUtils.truncToDay(date);
		}

		@Override
		public Date getNextDate(final Date date)
		{
			return DateUtils.addDays(date, +1);
		}

		@Override
		public Date getPreviousDate(final Date date)
		{
			return DateUtils.addDays(date, -1);
		}

		@Override
		public String toString(final Date date, final I18N i18n)
		{
			final DateFormat dateFormat = new SimpleDateFormat("EEEEEEE\n dd.MM.yyyy", i18n.getLocale());
			dateFormat.setLenient(false);

			return dateFormat.format(date);
		}
	}

	public static class WeekNavigationHandler implements DateNavigationHandler
	{
		protected WeekNavigationHandler()
		{
			super();
		}

		@Override
		public Date normalize(final Date date)
		{
			return DateUtils.truncToWeek(date);
		}

		@Override
		public Date getNextDate(final Date date)
		{
			return DateUtils.addDays(date, +7);
		}

		@Override
		public Date getPreviousDate(final Date date)
		{
			return DateUtils.addDays(date, -7);
		}

		@Override
		public String toString(final Date date, final I18N i18n)
		{
			final DateFormat dateFormat = new SimpleDateFormat("ww.yyyy", i18n.getLocale());
			dateFormat.setLenient(false);

			return i18n.getWithDefault("DateNavigation.Week", "Week")
					+ "\n" + dateFormat.format(date);
		}
	}
}
