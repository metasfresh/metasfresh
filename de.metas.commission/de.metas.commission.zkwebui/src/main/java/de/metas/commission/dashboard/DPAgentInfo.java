/**
 * 
 */
package de.metas.commission.dashboard;

/*
 * #%L
 * de.metas.commission.zkwebui
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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.model.POWrapper;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.webui.dashboard.DashboardPanel;
import org.compiere.model.MDiscountSchema;
import org.compiere.model.MUser;
import org.compiere.util.CLogger;
import org.compiere.util.Env;
import org.compiere.util.Msg;

import de.metas.commission.custom.type.ISalesRefFactCollector;
import de.metas.commission.interfaces.I_C_BPartner;
import de.metas.commission.model.I_C_AdvComSalesRepFact;
import de.metas.commission.model.I_C_AdvComSystem;
import de.metas.commission.model.I_C_AdvCommissionCondition;
import de.metas.commission.model.I_C_AdvCommissionSalaryGroup;
import de.metas.commission.model.I_C_Sponsor;
import de.metas.commission.model.MCAdvComRankForecast;
import de.metas.commission.model.X_C_AdvComSalesRepFact;
import de.metas.commission.service.ICommissionContext;
import de.metas.commission.service.ICommissionContextFactory;
import de.metas.commission.service.ISalesRepFactBL;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorDAO;
import de.metas.web.component.BeanInfoPanel;

/**
 * @author teo_sarca
 * 
 */
public class DPAgentInfo extends DashboardPanel
{

	static final String MSG_DATE_NOT_APPLICABLE = "Date_Not_Applicable";
	
	private static final CLogger logger = CLogger.getCLogger(DPAgentInfo.class);
	
	static final String ADV_COM_SALARY_GROUP_DATE_TO = "AdvComSalaryGroup_DateTo";

	static final String ADV_COM_SALARY_GROUP_DATE_FROM = "AdvComSalaryGroup_DateFrom";

	static final String ADV_COM_SALARY_GROUP_VALUE = "AdvComSalaryGroup_Value";

	static final String DISCOUNT = "Discount";

	private static final String BP_NAME = "BPName";

	private static final String BP_VALUE = "BPValue";

	/**
	 * 
	 */
	private static final long serialVersionUID = 7098086675577228278L;

	private final BeanInfoPanel beanInfoPanel = new BeanInfoPanel();

	public DPAgentInfo()
	{
		super();
		init();
		query();
	}

	private void init()
	{
		appendChild(beanInfoPanel);
		beanInfoPanel.addLine(BP_VALUE);
		beanInfoPanel.addLine(BP_NAME);
		beanInfoPanel.addLine(DISCOUNT);
		beanInfoPanel.addLine(ADV_COM_SALARY_GROUP_VALUE);
		beanInfoPanel.addLine(ADV_COM_SALARY_GROUP_DATE_FROM);
		beanInfoPanel.addLine(ADV_COM_SALARY_GROUP_DATE_TO);
	}

	private void query()
	{
		final Properties ctx = Env.getCtx();

		final I_C_BPartner bPartner = POWrapper.create(MUser.get(ctx, getAD_User_ID()).getC_BPartner(), I_C_BPartner.class);
		
		// setting bPartner values to panel
		beanInfoPanel.setValue(BP_VALUE, bPartner.getValue());
		beanInfoPanel.setValue(BP_NAME, bPartner.getName());
		
		final Timestamp loginDate = Env.getContextAsDate(ctx, "#Date");
		final I_C_Sponsor sponsor = Services.get(ISponsorDAO.class).retrieveForSalesRepAt(bPartner, loginDate).get(0);

		if (sponsor.isManualRank())
		{
			final I_C_AdvCommissionSalaryGroup rank = sponsor.getC_AdvCommissionSalaryGroup();
			
			beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_VALUE, rank.getName());
			
			final String dsName = restriveDiscountSchemaName(ctx, loginDate, sponsor, rank);
			beanInfoPanel.setValue(DISCOUNT, dsName);
			
			final String msg = Msg.getMsg(ctx, MSG_DATE_NOT_APPLICABLE);
			beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_DATE_FROM, msg);
			beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_DATE_TO, msg);
		}
		else
		{
			final ISalesRepFactBL srfBL = Services.get(ISalesRepFactBL.class);
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

			final I_C_AdvCommissionCondition contract = sponsorBL.retrieveContract(ctx, sponsor, loginDate, null);
			if (contract == null)
			{
				logger.warning(sponsor + " has no commission contract at " + loginDate);
				return;
			}

			//
			// get the forecast rank (relevant for discount)

			final I_C_AdvComSystem comSystem = contract.getC_AdvComSystem();

			final Map.Entry<I_C_AdvComSalesRepFact, I_C_AdvCommissionSalaryGroup> forecastSrfAndRank =
					srfBL.retrieveBestRank(sponsor, comSystem, loginDate, X_C_AdvComSalesRepFact.STATUS_Prognose, 11, true);

			final I_C_AdvCommissionSalaryGroup forecastRank;

			if (forecastSrfAndRank == null)
			{
				forecastRank = sponsorBL.retrieveRank(
						InterfaceWrapperHelper.getCtx(sponsor),
						sponsor, loginDate, X_C_AdvComSalesRepFact.STATUS_Prognose,
						InterfaceWrapperHelper.getTrxName(sponsor));
				Check.assume(forecastRank != null, sponsor + " has a forecast rank at date '" + loginDate + "'");
			}
			else
			{
				forecastRank = forecastSrfAndRank.getValue();
			}

			final String discount = restriveDiscountSchemaName(ctx, loginDate, sponsor, forecastRank);
			
			//FIXME Where do we take the commissionType and Product in this place?
			ICommissionContext commissionCtx = Services.get(ICommissionContextFactory.class).create(sponsor, loginDate,null, comSystem, null);

			final MCAdvComRankForecast rankForecast = sponsorBL.retrieveRankForecast(commissionCtx, false);
			if (rankForecast != null)
			{
				// format dates
				final DateFormat fmt = SimpleDateFormat.getDateInstance(DateFormat.LONG, Env.getLanguage(ctx).getLocale());

				final String rankSinceStr = fmt.format(rankForecast.getC_Period_Since().getEndDate());
				final String rankUntilStr = fmt.format(rankForecast.getC_Period_Until().getEndDate());

				beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_DATE_FROM, rankSinceStr);
				beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_DATE_TO, rankUntilStr);
			}

			beanInfoPanel.setValue(DISCOUNT, discount);
			beanInfoPanel.setValue(ADV_COM_SALARY_GROUP_VALUE, forecastRank.getName());
		}
	}

	/**
	 * Retrieve the name of the discount schema that is associated with the given rank at the given date.
	 * @param ctx
	 * @param date
	 * @param sponsor
	 * @param rank
	 * @return
	 */
	private String restriveDiscountSchemaName(
			final Properties ctx, 
			final Timestamp date, 
			final I_C_Sponsor sponsor, 
			final I_C_AdvCommissionSalaryGroup rank)
	{
		final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);
		
		final ISalesRefFactCollector srfCollector = sponsorBL.retrieveSalesRepFactCollector(ctx, sponsor, date, null);
		final MDiscountSchema newDs = srfCollector.retrieveDiscountSchema(rank, sponsor, date);
		
		return newDs.getName();
	}

	public int getAD_User_ID()
	{
		return Env.getAD_User_ID(Env.getCtx());
	}

	protected BeanInfoPanel getBeanInfoPanel()
	{
		return beanInfoPanel;
	}
}
