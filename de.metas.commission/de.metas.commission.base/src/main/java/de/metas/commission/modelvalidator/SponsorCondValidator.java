package de.metas.commission.modelvalidator;

/*
 * #%L
 * de.metas.commission.base
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


import java.util.List;
import java.util.Properties;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ISysConfigBL;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.MClient;
import org.compiere.model.ModelValidationEngine;
import org.compiere.model.ModelValidator;
import org.compiere.model.PO;
import org.compiere.util.Msg;
import org.slf4j.Logger;

import de.metas.adempiere.form.IClientUI;
import de.metas.commission.model.I_C_Sponsor_Cond;
import de.metas.commission.model.I_C_Sponsor_CondLine;
import de.metas.commission.model.I_C_Sponsor_SalesRep;
import de.metas.commission.service.ISponsorBL;
import de.metas.commission.service.ISponsorCondition;
import de.metas.logging.LogManager;

public class SponsorCondValidator implements ModelValidator
{
	private static final String MSG_INVALID_HIERARCHY_CHANGE_WARN = "CommissionInvalidHierarchyChange";

	public static final String MSG_COMMISSION_RETROACTIVE_HIERARCHY_CHANGE_CANCELED = "CommissionRetroactiveHierarchyChange_Canceled";

	public static final String MSG_COMMISSION_RETROACTIVE_HIERARCHY_CHANGE_1P = "CommissionRetroactiveHierarchyChange_Forbidden_1P";

	private final Logger logger = LogManager.getLogger(SponsorCondValidator.class);

	private int ad_Client_ID = -1;

	@Override
	public int getAD_Client_ID()
	{
		return ad_Client_ID;
	}

	@Override
	public final void initialize(final ModelValidationEngine engine, final MClient client)
	{
		if (client != null)
		{
			ad_Client_ID = client.getAD_Client_ID();
		}
		engine.addDocValidate(I_C_Sponsor_Cond.Table_Name, this);
	}

	@Override
	public String login(final int AD_Org_ID, final int AD_Role_ID, final int AD_User_ID)
	{
		return null;
	}

	@Override
	public String modelChange(final PO po, final int type)
	{
		return null; // nothing to do
	}

	@Override
	public String docValidate(final PO po, final int timing)
	{
		if (timing == ModelValidator.TIMING_BEFORE_PREPARE)
		{
			final I_C_Sponsor_Cond cond = InterfaceWrapperHelper.create(po, I_C_Sponsor_Cond.class);
			final ISponsorCondition sponsorConditionBL = Services.get(ISponsorCondition.class);
			final List<I_C_Sponsor_CondLine> condLines = sponsorConditionBL.retrieveLines(cond);

			if (condLines.isEmpty())
			{
				return "@NoLines@";
			}
		}

		if (timing == ModelValidator.TIMING_AFTER_COMPLETE)
		{
			final I_C_Sponsor_Cond cond = InterfaceWrapperHelper.create(po, I_C_Sponsor_Cond.class);

			final ISponsorCondition sponsorConditionBL = Services.get(ISponsorCondition.class);
			final ISponsorBL sponsorBL = Services.get(ISponsorBL.class);

			final List<I_C_Sponsor_SalesRep> newSSRs = sponsorConditionBL.updateSSRs(cond);

			final Properties ctx = po.getCtx();

			for (final I_C_Sponsor_SalesRep ssr : newSSRs)
			{
				final String errors = sponsorBL.validateSSR(ssr);

				if (!Check.isEmpty(errors, true))
				{
					final ISysConfigBL sysConfigBL = Services.get(ISysConfigBL.class);
					final String value =
							sysConfigBL.getValue(
									SponsorValidator.SYSCFG_ON_INVALID_HIERARCHY_CHANGE,
									SponsorValidator.SYSCFG_ON_INVALID_HIERARCHY_CHANGE_WARN,
									po.getAD_Client_ID(), po.getAD_Org_ID());

					logger.warn(errors);
					logger.info(SponsorValidator.SYSCFG_ON_INVALID_HIERARCHY_CHANGE + "='" + value + "'");

					if (SponsorValidator.SYSCFG_ON_INVALID_HIERARCHY_CHANGE_WARN.equalsIgnoreCase(value))
					{
						if (!Services.get(IClientUI.class).ask(0, Msg.getMsg(ctx, SponsorCondValidator.MSG_INVALID_HIERARCHY_CHANGE_WARN) + "\n" + errors))
						{
							return Msg.getMsg(ctx, SponsorCondValidator.MSG_COMMISSION_RETROACTIVE_HIERARCHY_CHANGE_CANCELED);
						}
					}
					else if (SponsorValidator.SYSCFG_ON_INVALID_HIERARCHY_CHANGE_DENY.equalsIgnoreCase(value))
					{
						throw new AdempiereException(errors);
					}
				}
			}
		}
		return null;
	}
}
