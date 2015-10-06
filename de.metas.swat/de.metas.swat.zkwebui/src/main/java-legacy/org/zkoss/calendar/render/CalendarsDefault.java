/* CalendarsDefault.java

{{IS_NOTE
	Purpose:

	Description:

	History:
		Mar 11, 2009 9:29:01 AM , Created by jumperchen
}}IS_NOTE

Copyright (C) 2009 Potix Corporation. All Rights Reserved.

{{IS_RIGHT
}}IS_RIGHT
 */
package org.zkoss.calendar.render;

import java.io.IOException;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import org.zkoss.calendar.Calendars;
import org.zkoss.calendar.api.CalendarEvent;
import org.zkoss.calendar.api.DateFormatter;
import org.zkoss.calendar.api.EventRender;
import org.zkoss.util.Locales;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.render.ComponentRenderer;
import org.zkoss.zk.ui.render.Out;
import org.zkoss.zk.ui.render.SmartWriter;

/**
 * A renderer of {@link Calendars} renders the outline in both views, default and month.
 * 
 * @author jumperchen
 * 
 */
@SuppressWarnings("unchecked")
// metas
public class CalendarsDefault implements ComponentRenderer
{
	public static final boolean METAS_FIXED = true; // metas
	static double ONE_DAY = 24 * 60 * 60;

	@Override
	public void render(final Component comp, final Writer out) throws IOException
	{
		final SmartWriter wh = new SmartWriter(out);
		final Calendars self = (Calendars)comp;
		final String uuid = self.getUuid();

		// CSS ClassName
		final String zcls = self.getZclass();
		final String header = zcls + "-header";
		final String body = zcls + "-body";
		final String inner = zcls + "-inner";

		// round corner
		final String t1 = zcls + "-t1";
		final String t2 = zcls + "-t2";
		final String t3 = zcls + "-t3";
		final String b1 = zcls + "-b1";
		final String b2 = zcls + "-b2";
		final String b3 = zcls + "-b3";

		final boolean inMonth = "month".equals(self.getMold());
		final String zType = inMonth ? "\" z.type=\"calendar.calendars.CalendarsMonth\"" :
				"\" z.type=\"calendar.calendars.Calendars\"";
		wh.write("<div id=\"").write(uuid).write(zType)
				.write(self.getOuterAttrs()).write(self.getInnerAttrs()).write(">");

		if (self.getToolbar() != null)
		{
			wh.write("<div id=\"").write(uuid).write("!tb\" class=\"").write(header)
					.write("\">").write(self.getToolbar()).write("</div>");
		}

		wh.write("<div class=\"").write(t1).write("\"></div><div class=\"")
				.write(t2).write("\"><div class=\"").write(t3).write("\"></div></div>");

		wh.write("<div id=\"").write(uuid).write("!body\" z.skipdsc=\"true\" class=\"").write(body)
				.write("\"><div class=\"").write(inner).write("\">");

		// render content
		if (inMonth)
		{
			getMonthView(self, out);
		}
		else
		{
			getDayView(self, out);
		}

		wh.write("</div></div>");

		wh.write("<div class=\"").write(b2).write("\"><div class=\"").write(b3)
				.write("\"></div></div><div class=\"").write(b1).write("\"></div>");

		wh.write("<div id=\"").write(uuid).write("!sdw\" class=\"").write(zcls)
				.write("-fl\"><div class=\"").write(zcls).write("-fr\"><div class=\"")
				.write(zcls).write("-fm\"></div></div></div></div>");
	}

	@SuppressWarnings("unused")
	// metas
	private void getMonthView(final Calendars self, final Writer out) throws IOException
	{
		final SmartWriter wh = new SmartWriter(out);
		final Locale locale = Locales.getCurrent();
		final TimeZone timezone = self.getDefaultTimeZone();
		final Date begin = self.getBeginDate();
		final Date end = self.getEndDate();

		// day view
		final String uuid = self.getUuid();

		// CSS ClassNamefinal
		final String zcls = self.getZclass();
		final String month = zcls + "-month-cnt";
		final String inner = month + "-inner";

		final String month_header = zcls + "-month-header";
		final String month_body = zcls + "-month-body";
		final String month_week = zcls + "-month-week";

		final String month_date = zcls + "-month-date";
		final String month_date_off = zcls + "-month-date-off";
		final String month_date_cnt = month_date + "-cnt";
		final String month_date_evt = month_date + "-evt";

		final String week_weekend = zcls + "-week-weekend";
		final String week_today = zcls + "-week-today";

		final String day_of_week = zcls + "-day-of-week";
		final String day_of_month_bg = zcls + "-day-of-month-bg";
		final String day_of_month_body = zcls + "-day-of-month-body";

		wh.write("<div id=\"").write(uuid).write("!inner\" class=\"")
				.write(month).write("\">");

		wh.write("<div class=\"").write(inner).write("\">");

		// title
		wh.write("<table class=\"").write(month_header).write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>");

		final Calendar cal = Calendar.getInstance(timezone);
		final Calendar btmp = Calendar.getInstance(timezone);
		final Calendar etmp = Calendar.getInstance(timezone);
		cal.setTime(begin);

		final DateFormatter dfhandler = self.getDateFormatter();
		final int[] weekend = new int[] { -1, -1 };

		// day-of-week
		for (int index = 0, k = 0; k < 7; ++k)
		{
			final String content = dfhandler.getCaptionByDayOfWeek(cal.getTime(), locale, timezone);
			wh.write("<th title=\"").write(content).write("\" class=\"")
					.write(day_of_week);

			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			{
				weekend[index++] = k;
				wh.write(" ").write(week_weekend);
			}
			wh.write("\">").write(content).write("</th>");
			cal.add(Calendar.DAY_OF_MONTH, 1);
		}
		wh.write("</tr></tbody></table>");

		// events content
		wh.write("<div id=\"").write(uuid).write("!cnt\" class=\"").write(month_body)
				.write("\">");

		// calculate how many weeks we should display
		final int weeks = self.getWeekOfMonth();

		final float number = 100F / weeks;

		// reset date
		cal.setTime(begin);

		final Calendar current = Calendar.getInstance(timezone);
		final int today_year = current.get(Calendar.YEAR);
		final int today_day = current.get(Calendar.DAY_OF_YEAR);

		// current date
		current.setTime(self.getCurrentDate());
		final int curMonth = current.get(Calendar.MONTH);

		final EventRender render = self.getEventRender();

		final Map<String, List<CalendarEvent>> dayMap = new HashMap<String, List<CalendarEvent>>();

		for (int j = 0; j < weeks; j++)
		{
			wh.write("<div class=\"").write(month_week).write("\" style=\"top:")
					.write(number * j).write("%; height:").write(number).write("%;\">");

			// the background of day of month
			wh.write("<table class=\"").write(day_of_month_bg)
					.write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>");

			for (int i = 0; i < 7; i++)
			{
				wh.write("<td class=\"");

				if (weekend[0] == i || weekend[1] == i)
				{
					wh.write(" ").write(week_weekend);
				}

				if (today_year == cal.get(Calendar.YEAR) &&
						today_day == cal.get(Calendar.DAY_OF_YEAR))
				{
					wh.write(" ").write(week_today);
				}

				wh.write("\">&nbsp;</td>");
				cal.add(Calendar.DAY_OF_MONTH, 1);
			}

			wh.write("</tr></tbody></table>");

			// the body of day of month
			wh.write("<table class=\"").write(day_of_month_body)
					.write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody><tr>");

			// reset date
			cal.add(Calendar.DAY_OF_MONTH, -7);

			// the title of day of week
			for (int i = 0; i < 7; i++)
			{
				wh.write("<td class=\"").write(month_date);

				if (weekend[0] == i || weekend[1] == i)
				{
					wh.write(" ").write(week_weekend);
				}

				if (today_year == cal.get(Calendar.YEAR) &&
						today_day == cal.get(Calendar.DAY_OF_YEAR))
				{
					wh.write(" ").write(week_today);
				}

				if (curMonth != cal.get(Calendar.MONTH))
				{
					wh.write(" ").write(month_date_off);
				}

				cal.set(Calendar.MILLISECOND, 0);
				final Date d = cal.getTime();
				wh.write("\" z.bd=\"").write(String.valueOf(d.getTime())).write("\" z.pt=\"")
						.write(dfhandler.getCaptionByPopup(d, locale, timezone))
						.write("\"><span class=\"")
						.write(month_date_cnt).write("\">")
						.write(dfhandler.getCaptionByDateOfMonth(d, locale, timezone))
						.write("</span></td>");

				cal.add(Calendar.DAY_OF_MONTH, 1);
			}
			wh.write("</tr>");

			// the content of day of week
			// reset date
			cal.add(Calendar.DAY_OF_MONTH, -7);

			final SimpleDateFormat sdfKey = new SimpleDateFormat("yyyy/MM/dd");
			sdfKey.setTimeZone(timezone);

			// List daylongList = new LinkedList();
			final int[] daylongCnt = new int[7];

			// get current begin date of the week.
			cal.set(Calendar.MILLISECOND, 0);
			final Date beginDate = cal.getTime();
			current.setTime(beginDate);
			current.add(Calendar.DAY_OF_WEEK, 7);
			current.set(Calendar.MILLISECOND, 0);
			final Date endDate = current.getTime();

			for (int day = 0; day < 7; ++day)
			{
				cal.set(Calendar.MILLISECOND, 0);
				final Date curDate = cal.getTime();
				List<CalendarEvent> evts = self.getEvent(curDate);
				final String key = sdfKey.format(curDate);

				List<CalendarEvent> list = null;
				if (day + j * 7 < dayMap.size())
				{
					list = dayMap.get(key);
					if (list != null)
					{

						// due to the evts is an unmodifiable list.
						evts = new LinkedList<CalendarEvent>(evts);

						// the list has some cross events, we need to re-calculate it.
						evts.addAll(0, list);

						// reset the list
						list.clear();
					}
				}

				if (list == null)
				{
					list = new LinkedList<CalendarEvent>();
					dayMap.put(key, list);
				}
				final List<CalendarEvent> perDay = new LinkedList<CalendarEvent>();
				for (final CalendarEvent ce : evts)
				{
					final Date ed = ce.getEndDate();
					final double rests = (ed.getTime() - curDate.getTime()) / 1000;
					final Date bd = ce.getBeginDate();
					daylongCnt[day]++;
					btmp.setTime(bd);
					etmp.setTime(ed);
					if (bd.before(curDate) || btmp.get(Calendar.YEAR) != etmp.get(Calendar.YEAR)
							|| btmp.get(Calendar.DAY_OF_YEAR) != etmp.get(Calendar.DAY_OF_YEAR)
							&& (etmp.get(Calendar.HOUR_OF_DAY) != 0 || etmp.get(Calendar.MINUTE) != 0)
							|| ed.getTime() - bd.getTime() >= CalendarsDefault.ONE_DAY * 1000 &&
							rests >= CalendarsDefault.ONE_DAY)
					{
						for (double y = 1, x = rests / CalendarsDefault.ONE_DAY; y < x; y++)
						{
							if (y + day >= 7)
							{

								// in current view page
								if (ed.after(endDate))
								{
									final String nextkey = sdfKey.format(new Date(endDate.getTime() + (long)CalendarsDefault.ONE_DAY));
									List<CalendarEvent> nextList = dayMap.get(nextkey);

									if (nextList == null)
									{
										nextList = new LinkedList<CalendarEvent>();
										dayMap.put(nextkey, nextList);
									}

									nextList.add(ce);
								}
								break; // jump out the loop
							}
							final int size = day + (int)y;
							daylongCnt[size]++;
						}
						list.add(ce);
					}
					else
					{
						perDay.add(ce);
					}

				}
				list.addAll(perDay);
				cal.add(Calendar.DAY_OF_WEEK, 1);
			}

			int maxSize = 0;
			for (int i = 0; i < daylongCnt.length; i++)
			{
				if (maxSize < daylongCnt[i])
				{
					maxSize = daylongCnt[i];
				}
			}

			// fill the event of the daylong
			for (int r = 0; r < maxSize; r++)
			{

				// reset date
				current.setTime(beginDate);

				wh.write("<tr>");
				for (int total = 7, i = 0; i < 7; i++)
				{

					current.set(Calendar.MILLISECOND, 0);
					final Date curDate = current.getTime();
					final List<CalendarEvent> evts = dayMap.get(sdfKey.format(curDate));

					wh.write("<td class=\"").write(month_date_evt).write("\"");
					if (evts.size() > 0)
					{
						final CalendarEvent ce = evts.remove(0);
						final Date bd = ce.getBeginDate();
						final Date ed = ce.getEndDate();
						final double rests = (ed.getTime() - curDate.getTime()) / 1000 / CalendarsDefault.ONE_DAY;
						int colspan = 1;
						for (double y = 1; y < rests && y + i < 7; y++)
						{
							colspan++;
						}

						total -= colspan;
						if (total < 0)
						{
							colspan += total;
						}

						wh.write(" colspan=\"").write(colspan).write("\"").write(">");

						btmp.setTime(bd);
						etmp.setTime(ed);

						if (bd.before(curDate) || btmp.get(Calendar.YEAR) != etmp.get(Calendar.YEAR)
								|| btmp.get(Calendar.DAY_OF_YEAR) != etmp.get(Calendar.DAY_OF_YEAR)
								&& (etmp.get(Calendar.HOUR_OF_DAY) != 0 || etmp.get(Calendar.MINUTE) != 0)
								|| ed.getTime() - bd.getTime() >= CalendarsDefault.ONE_DAY * 1000 &&
								rests >= 1)
						{
							wh.write(render.drawAllDayByMonth(self, ce, self.getCalendarEventId(ce), beginDate, endDate));
						}
						else
						{
							wh.write(render.drawDayByMonth(self, ce, self.getCalendarEventId(ce)));
						}

						while (--colspan > 0 && ++i < 7)
						{
							current.add(Calendar.DAY_OF_WEEK, 1);
						}

					}
					else
					{
						total--;
						wh.write(">&nbsp;");
					}
					wh.write("</td>");

					current.add(Calendar.DAY_OF_WEEK, 1);
				}
				wh.write("</tr>");
			}

			// the end of the body
			wh.write("</tbody></table>");

			wh.write("</div>");
		}

		wh.write("</div></div></div>");
	}

	private void getDayView(final Calendars self, final Writer out) throws IOException
	{
		final SmartWriter wh = new SmartWriter(out);
		// day view
		final String uuid = self.getUuid();

		// CSS ClassName
		final String zcls = self.getZclass();
		final String week = zcls + "-week";
		final String tzone = zcls + "-timezone";
		final String tzone_end = zcls + "-timezone-end";
		final String week_header = zcls + "-week-header";
		final String week_header_cnt = week_header + "-cnt";
		final String week_header_arrow = week_header + "-arrow";

		final String week_body = zcls + "-week-body";
		final String week_cnt = zcls + "-week-cnt";
		final String week_day = zcls + "-week-day";
		final String week_today = zcls + "-week-today";
		final String week_day_cnt = week_day + "-cnt";
		final String week_weekend = zcls + "-week-weekend";

		final String day_header = zcls + "-day-header";

		final String day_of_week = zcls + "-day-of-week";
		final String day_of_week_inner = day_of_week + "-inner";
		final String day_of_week_cnt = day_of_week + "-cnt";
		final String day_of_week_end = day_of_week + "-end";

		final String daylong = zcls + "-daylong";
		final String daylong_body = daylong + "-body";
		final String daylong_inner = daylong + "-inner";
		final String daylong_cnt = daylong + "-cnt";
		final String daylong_evt = daylong + "-evt";
		final String daylong_more = daylong + "-more";
		final String daylong_end = daylong + "-end";

		final String hour = zcls + "-hour";
		final String hour_inner = zcls + "-hour-inner";
		final String hour_sep = hour + "-sep";
		final String hour_of_day = hour + "-of-day";

		final int days = self.getDays();
		final Locale locale = Locales.getCurrent();
		final TimeZone timezone = self.getDefaultTimeZone();
		final Date begin = self.getBeginDate();
		final EventRender render = self.getEventRender();
		final ICalendarRendererCtl rendererCtl = (ICalendarRendererCtl)self.getAttribute(ICalendarRendererCtl.ATTRIBUTE_Name); // metas: tsa

		wh.write("<div id=\"").write(uuid).write("!inner\" class=\"")
				.write(week).write("\">");

		// week header
		wh.write("<div class=\"").write(week_header).write("\"><table class=\"").write(week_header_cnt)
				.write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody>");

		// day's header
		wh.write("<tr class=\"").write(day_header).write("\">");

		final Map<TimeZone, String> zones = self.getTimeZones();
		for (final Iterator it = zones.entrySet().iterator(); it.hasNext();)
		{
			final Map.Entry me = (Map.Entry)it.next();
			wh.write("<th rowspan=\"3\" class=\"").write(tzone);
			if (!it.hasNext())
			{
				wh.write(" ").write(tzone_end);
			}

			wh.write("\">");
			new Out((String)me.getValue()).render(out);
			if (!it.hasNext())
			{
				wh.write("<div id=\"").write(uuid).write("!hdarrow\" class=\"")
						.write(week_header_arrow).write("\"></div>");
			}
			wh.write("</th>");
		}

		final Calendar cal = Calendar.getInstance(timezone);
		final Calendar current = Calendar.getInstance(timezone);
		final Calendar btmp = Calendar.getInstance(timezone);
		final Calendar etmp = Calendar.getInstance(timezone);
		final int today_year = current.get(Calendar.YEAR);
		final int today_day = current.get(Calendar.DAY_OF_YEAR);
		cal.setTime(begin);

		final DateFormatter dfhandler = self.getDateFormatter();
		final int[] weekend = new int[] { -1, -1, -1 };

		// day-of-week
		for (int index = 0, j = 0; j < days; ++j)
		{
			final String content = dfhandler.getCaptionByDate(cal.getTime(), locale, timezone);
			wh.write("<th title=\"").write(content).write("\" class=\"").write(day_of_week).write("\"><div class=\"")
					.write(day_of_week_inner);

			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY ||
					cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
			{
				weekend[index++] = j;
				wh.write(" ").write(week_weekend);
			}
			if (weekend[2] == -1 && today_year == cal.get(Calendar.YEAR) &&
					today_day == cal.get(Calendar.DAY_OF_YEAR))
			{
				weekend[2] = j;
				wh.write(" ").write(week_today);
			}

			wh.write("\"><span class=\"").write(day_of_week_cnt).write("\">")
					.write(content)
					.write("</span></div></th>");
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}
		wh.write("<th class=\"").write(day_of_week_end).write("\">&nbsp;</th>");
		wh.write("</tr>");

		// daylong
		wh.write("<tr class=\"").write(daylong).write("\">")
				.write("<td class=\"").write(daylong_body).write("\" colspan=\"")
				.write(days).write("\"><div id=\"").write(uuid).write("!daylong\" class=\"")
				.write(daylong_inner).write("\"><table class=\"").write(daylong_cnt)
				.write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody>");

		// separator date
		cal.setTime(begin);

		final SimpleDateFormat sdfKey = new SimpleDateFormat("yyyy/MM/dd");
		sdfKey.setTimeZone(timezone);
		final List<List<CalendarEvent>> daylongList = new LinkedList<List<CalendarEvent>>();
		final Map<String, List<CalendarEvent>> dayMap = new HashMap<String, List<CalendarEvent>>();
		final int[] daylongCnt = new int[days];
		for (int day = 0; day < days; ++day)
		{
			cal.set(Calendar.MILLISECOND, 0);
			final Date curDate = cal.getTime();
			final List<CalendarEvent> evts = self.getEvent(curDate);

			final List<CalendarEvent> list = new LinkedList<CalendarEvent>();
			for (final CalendarEvent ce : evts)
			{
				final Date bd = ce.getBeginDate();
				final Date ed = ce.getEndDate();
				final double rests = (ed.getTime() - curDate.getTime()) / 1000;
				btmp.setTime(bd);
				etmp.setTime(ed);
				if (bd.before(curDate) || btmp.get(Calendar.YEAR) != etmp.get(Calendar.YEAR)
						|| btmp.get(Calendar.DAY_OF_YEAR) != etmp.get(Calendar.DAY_OF_YEAR)
						&& (etmp.get(Calendar.HOUR_OF_DAY) != 0 || etmp.get(Calendar.MINUTE) != 0)
						|| ed.getTime() - bd.getTime() >= CalendarsDefault.ONE_DAY * 1000 &&
						rests >= CalendarsDefault.ONE_DAY
						|| rendererCtl != null && rendererCtl.isRenderDayLong(ce)// metas
				)
				{
					daylongCnt[day]++;
					for (double y = 1, x = rests / CalendarsDefault.ONE_DAY; y < x && y + day < days; y++)
					{
						final int size = day + (int)y;
						daylongCnt[size]++;
					}
					list.add(ce);
				}
				else
				{
					final String key = sdfKey.format(curDate);
					List<CalendarEvent> dayList = dayMap.get(key);
					if (dayList == null)
					{
						dayList = new LinkedList<CalendarEvent>();
						dayMap.put(key, dayList);
					}
					dayList.add(ce);
				}
			}
			daylongList.add(list);
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}

		int maxSize = 0;
		for (int i = 0; i < daylongCnt.length; i++)
		{
			if (maxSize < daylongCnt[i])
			{
				maxSize = daylongCnt[i];
			}
		}

		// metas: tsa: begin
		if (rendererCtl != null)
		{
			final Calendar curDateCal = Calendar.getInstance(timezone);
			curDateCal.setTime(begin);
			wh.write("<tr>");
			for (int i = 0; i < days; i++)
			{
				curDateCal.set(Calendar.MILLISECOND, 0);
				final Date curDate = cal.getTime();
				wh.write("<td class=\"").write(daylong).write("-summary ").write(daylong_evt).write("\">");
				wh.write(rendererCtl.renderDaySummary(self, i, curDate));
				wh.write("</td>");

				curDateCal.add(Calendar.DAY_OF_WEEK, 1);
			}
			wh.write("</tr>");
		}
		// metas: tsa: end

		// fill the event of the daylong
		for (int r = 0; r < maxSize; r++)
		{
			// reset date
			cal.setTime(begin);

			wh.write("<tr>");
			int c = 0, total = days;
			for (final Iterator it = daylongList.iterator(); it.hasNext(); c++)
			{
				final List<CalendarEvent> list = (List<CalendarEvent>)it.next();
				cal.set(Calendar.MILLISECOND, 0);
				final Date curDate = cal.getTime();
				wh.write("<td class=\"").write(daylong_evt).write("\"");
				if (list.size() > 0)
				{
					final CalendarEvent ce = list.remove(0);
					final double rests = (ce.getEndDate().getTime() - curDate.getTime()) / 1000;
					int colspan = 1;
					for (double y = 1, x = rests / CalendarsDefault.ONE_DAY; y < x && y + c < days; y++)
					{
						colspan++;
					}

					total -= colspan;
					if (total < 0)
					{
						colspan += total;
					}

					wh.write(" colspan=\"").write(colspan).write("\"").write(">");
					wh.write(render.drawAllDay(self, ce, self.getCalendarEventId(ce)));
					while (--colspan > 0 && it.hasNext())
					{
						it.next();
						cal.add(Calendar.DAY_OF_WEEK, 1);
					}
				}
				else
				{
					total--;
					wh.write(">&nbsp;");
				}

				cal.add(Calendar.DAY_OF_WEEK, 1);

				wh.write("</td>");
			}
			wh.write("</tr>");
		}

		// the end of daylong
		wh.write("<tr>");
		for (int j = 0; j < days; ++j)
		{
			wh.write("<td class=\"").write(daylong_evt).write(' ').write(daylong_more).write("\">&nbsp;</td>");
		}

		wh.write("</tr>");

		wh.write("</tbody></table></div>")
				.write("</td></tr>");

		// the end of daylong
		wh.write("<tr><td colspan=\"").write(days).write("\" class=\"").write(daylong_end).write("\">&nbsp;</td></tr>");

		// the end of week header
		wh.write("</tbody></table></div>");

		// week content
		wh.write("<div id=\"").write(uuid).write("!cnt\" class=\"").write(week_body)
				.write("\">");

		final boolean isIE = Executions.getCurrent().isBrowser("ie");

		if (isIE)
		{
			wh.write("<table cellpadding=\"0\" cellspacing=\"0\" style=\"table-layout:fixed;\"><tbody><tr><td>");
		}

		wh.write("<table class=\"").write(week_cnt)
				.write("\" cellpadding=\"0\" cellspacing=\"0\"><tbody>");

		// hours separator
		wh.write("<tr>");
		for (int i = 0; i < zones.size(); i++)
		{
			wh.write("<td class=\"").write(tzone);
			if (i + 1 == zones.size())
			{
				wh.write(" ").write(tzone_end);
			}
			wh.write("\"></td>");
		}

		wh.write("<td colspan=\"").write(days).write("\"><div class=\"")
				.write(hour).write("\"><div class=\"").write(hour_inner).write("\">");

		for (int k = 0; k < 24; k++)
		{
			wh.write("<div class=\"").write(hour_sep).write("\"></div>");
		}

		// the end of hours separator
		wh.write("</div></div></td></tr>");

		// the content of day
		wh.write("<tr>");

		current.set(Calendar.MINUTE, 0);
		for (final Iterator it = zones.entrySet().iterator(); it.hasNext();)
		{
			final Map.Entry me = (Map.Entry)it.next();
			wh.write("<td class=\"").write(tzone);

			if (!it.hasNext())
			{
				wh.write(" ").write(tzone_end);
			}

			wh.write("\">");
			for (int k = 0; k < 24; k++)
			{
				current.set(Calendar.HOUR_OF_DAY, k);
				wh.write("<div class=\"").write(hour_of_day).write("\"");
				wh.write(" id=\"").write(uuid).write("!hour").write(k).write("\">"); // metas: tsa
				wh.write(dfhandler.getCaptionByTimeOfDay(current.getTime(), locale, (TimeZone)me.getKey()));
				wh.write("</div>");
			}
			wh.write("</td>");
		}

		// reset date
		cal.setTime(begin);
		for (int j = 0; j < days; ++j)
		{
			wh.write("<td class=\"").write(week_day);
			if (weekend[0] == j || weekend[1] == j)
			{
				wh.write(" ").write(week_weekend);
			}
			if (weekend[2] == j)
			{
				wh.write(" ").write(week_today);
			}

			wh.write("\"><div class=\"").write(week_day_cnt).write("\">");

			// TODO fill the content of day
			cal.set(Calendar.MILLISECOND, 0);
			final Date curDate = cal.getTime();
			final List<CalendarEvent> evts = dayMap.get(sdfKey.format(curDate));
			if (evts != null && !evts.isEmpty())
			{
				Collections.sort(evts, getDefaultEndDateComparator());
				for (final CalendarEvent ce : evts)
				{
					wh.write(render.drawDay(self, ce, self.getCalendarEventId(ce)));
				}
			}
			wh.write("</div></td>");
			cal.add(Calendar.DAY_OF_WEEK, 1);
		}

		// the end of the content of day
		wh.write("</tr></tbody></table>");

		if (isIE)
		{
			wh.write("</td></tr></tbody></table>");
		}

		// the end of week content
		wh.write("</div>");

		wh.write("</div>");
	}

	private static final Comparator<CalendarEvent> getDefaultEndDateComparator()
	{
		return CalendarsDefault._defCompare;
	}

	private static final Comparator<CalendarEvent> _defCompare = new Comparator<CalendarEvent>()
	{
		@Override
		public int compare(final CalendarEvent arg0, final CalendarEvent arg1)
		{
			int v = arg0.getBeginDate().compareTo(arg1.getBeginDate());
			if (v == 0)
			{
				v = -arg0.getEndDate().compareTo(arg1.getEndDate());
			}
			return v;
		}
	};
}
