package de.metas.flatrate.process;

/*
 * #%L
 * de.metas.contracts
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


import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.adempiere.misc.service.IPOService;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Services;
import org.compiere.util.Msg;

import de.metas.contracts.subscription.ISubscriptionDAO;
import de.metas.flatrate.model.I_C_Flatrate_Term;
import de.metas.flatrate.model.I_C_SubscriptionProgress;
import de.metas.flatrate.model.X_C_SubscriptionProgress;
import de.metas.process.ProcessInfoParameter;
import de.metas.process.JavaProcess;

public class PauseSubscription extends JavaProcess {

	static final String DATE_TO = "DateTo";

	static final String DATE_FROM = "DateFrom";

	public static final String MSG_NO_SPS_AFTER_DATE_1P = "Subscription.NoSpsAfterDate_1P";
	
	private Timestamp dateFrom;

	private Timestamp dateTo;

	@Override
	protected String doIt() throws Exception {

		final ISubscriptionDAO subscriptionPA = Services.get(ISubscriptionDAO.class);

		final I_C_Flatrate_Term sc = InterfaceWrapperHelper.create(getCtx(), getRecord_ID(), I_C_Flatrate_Term.class, get_TrxName());

		final List<I_C_SubscriptionProgress> sps = subscriptionPA.retrieveNextSPs(sc, dateFrom);

		if (sps.isEmpty()) {
			addLog(Msg.getMsg(getCtx(), MSG_NO_SPS_AFTER_DATE_1P, new Object[]{dateFrom}));
//			addLog("There are no subscription progress entries for " + sc
//					+ " after " + dateFrom + ". Returning");
			return "";
		}

		final I_C_SubscriptionProgress nextSP = sps.get(0);

		final I_C_SubscriptionProgress pauseBegin = InterfaceWrapperHelper.create(getCtx(), I_C_SubscriptionProgress.class, get_TrxName());

		pauseBegin.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Beginn);
		pauseBegin.setC_Flatrate_Term(sc);
		pauseBegin.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
		pauseBegin.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Lieferpause);
		pauseBegin.setEventDate(dateFrom);
		pauseBegin.setSeqNo(nextSP.getSeqNo());
		InterfaceWrapperHelper.save(pauseBegin);
		
		final I_C_SubscriptionProgress pauseEnd = InterfaceWrapperHelper.create(getCtx(), I_C_SubscriptionProgress.class, get_TrxName());

		pauseEnd.setEventType(X_C_SubscriptionProgress.EVENTTYPE_Abopause_Ende);
		pauseBegin.setC_Flatrate_Term(sc);
		pauseEnd.setStatus(X_C_SubscriptionProgress.STATUS_Geplant);
		pauseEnd.setContractStatus(X_C_SubscriptionProgress.CONTRACTSTATUS_Laufend);
		pauseEnd.setEventDate(dateTo);
		pauseEnd.setSeqNo(nextSP.getSeqNo() + 1);
		InterfaceWrapperHelper.save(pauseEnd);

		//TODO "Created subscription pause
		
		int seqNo = nextSP.getSeqNo() + 2;

		final int days = (int) TimeUnit.DAYS.convert(dateTo.getTime() - nextSP.getEventDate().getTime(),
				TimeUnit.MILLISECONDS);

		// TODO "Because of this pause, all following subscription progress entries need to be shifted by "+days+" days."

		final IPOService poService = Services.get(IPOService.class);
		
		for (final I_C_SubscriptionProgress currentSP : sps) {

			currentSP.setSeqNo(seqNo);
			if (days > 0) {
				final Calendar eventDate = new GregorianCalendar();
				eventDate.setTime(currentSP.getEventDate());
				eventDate.add(Calendar.DAY_OF_MONTH, days);
				currentSP.setEventDate(new Timestamp(eventDate.getTimeInMillis()));
			}
			poService.save(currentSP, get_TrxName());
			seqNo += 1;
		}
		return "";
	}

	@Override
	protected void prepare() {
		final ProcessInfoParameter[] para = getParametersAsArray();

		for (int i = 0; i < para.length; i++) {

			final String name = para[i].getParameterName();
			if (para[i].getParameter() == null) {
				;
			} else if (name.equals(DATE_FROM)) {
				dateFrom = (Timestamp) para[i].getParameter();
			} else if (name.equals(DATE_TO)) {
				dateTo = (Timestamp) para[i].getParameter();
			} else {
				log.error("Unknown Parameter: " + name);
			}
		}

	}

}
