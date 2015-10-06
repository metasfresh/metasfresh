package org.adempiere.util;

/*
 * #%L
 * de.metas.swat.base
 * %%
 * Copyright (C) 2015 metas GmbH
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


import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.compiere.util.CLogger;

public final class ThreadDumper {

	private static CLogger log = CLogger.getCLogger(ThreadDumper.class);

	private static ThreadDumper instance = new ThreadDumper();

	private Timer timer;

	private ThreadDumper() {
	}

	public static ThreadDumper getInstance() {
		return instance;
	}

	public void startDumping(final int periodSecs) {
		if (timer != null) {
			stopDumping();
		}
		log.info("Starting dumper with one dump every " + periodSecs
				+ " seconds");
		timer = new Timer();

		TimerTask task = new TimerTask() {
			@Override
			public void run() {
				performThreadDump();
			}
		};
		timer.schedule(task, 100, periodSecs * 1000);
	}

	public void stopDumping() {
		log.info("Stopping dumper");
		timer.cancel();
		timer = null;
	}

	public void performThreadDump() {

		Map<Thread, StackTraceElement[]> allStackTraces = Thread
				.getAllStackTraces();

		StringBuffer sb = new StringBuffer();
		sb.append("Dumping information about our current threads");
		for (Thread t : allStackTraces.keySet()) {

			sb.append(dumpThreadInfo(t));
		}

		log.info(sb.toString());
	}

	public static String dumpThreadInfo(Thread t) {

		StringBuffer sb = new StringBuffer();

		sb
				.append("\n\t------------Beginn of single Tread info------------------------------------");

		sb.append("\n\tName: ");
		sb.append(t.getName());
		sb.append("\n\tState: ");
		sb.append(t.getState());
		sb.append("\n\tPriority: ");
		sb.append(t.getPriority());
		sb.append("\n\tStacktrace:");
		for (StackTraceElement stackTraceElement : t.getStackTrace()) {

			sb.append("\n\t\t");
			sb.append(stackTraceElement.toString());
		}
		sb
				.append("\n\t------------End of single Tread info---------------------------------------");

		return sb.toString();
	}
}
