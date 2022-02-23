package de.metas.ordercandidate.api.impl;

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

import ch.qos.logback.classic.Level;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.logging.LogManager;
import de.metas.ordercandidate.api.OLCandUpdateResult;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.user.UserId;
import de.metas.util.Check;
import de.metas.util.Loggables;
import de.metas.util.Services;
import org.adempiere.ad.trx.processor.api.ITrxItemProcessorContext;
import org.adempiere.ad.trx.processor.spi.ITrxItemProcessor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.api.IParams;
import org.slf4j.Logger;

import javax.annotation.Nullable;
import java.sql.Timestamp;
import java.util.Map;

public final class OLCandUpdater implements ITrxItemProcessor<I_C_OLCand, OLCandUpdateResult>
{
	/**
	 * used to specify location overrides
	 */
	public static final String PARAM_C_BPARTNER_LOCATION_MAP = "C_BPARTNER_LOCATION_ID";
	private static final Logger logger = LogManager.getLogger(OLCandUpdater.class);

	private final IBPartnerDAO bpartnerDAO = Services.get(IBPartnerDAO.class);
	private final OLCandUpdateResult result = new OLCandUpdateResult();
	private ITrxItemProcessorContext processorCtx;

	public OLCandUpdater()
	{
	}

	@Override
	public void setTrxItemProcessorCtx(final ITrxItemProcessorContext processorCtx)
	{
		this.processorCtx = processorCtx;
	}

	@Override
	public void process(final I_C_OLCand olCand) throws Exception
	{
		if (olCand.isProcessed())
		{
			result.incSkipped();
			return;
		}

		final IParams params = processorCtx.getParams();
		Check.errorIf(params == null, "Given processorCtx {} needs to contain params", processorCtx);

		// Partner
		final int bpartnerId = params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BPartner_Override_ID, -1);
		olCand.setC_BPartner_Override_ID(bpartnerId);

		// Location
		final BPartnerLocationId bpartnerLocationId = getBPartnerLocationId(olCand, params, bpartnerId);
		olCand.setC_BP_Location_Override_ID(BPartnerLocationId.toRepoId(bpartnerLocationId));

		// DatePrommissed
		final Timestamp datePromissed = params.getParameterAsTimestamp(I_C_OLCand.COLUMNNAME_DatePromised_Override);
		olCand.setDatePromised_Override(datePromissed);

		//AD_User_ID
		final UserId userId = UserId.ofRepoIdOrNull(olCand.getAD_User_ID());
		final BPartnerLocationId bPartnerLocationId = BPartnerLocationId.ofRepoIdOrNull(bpartnerId, BPartnerLocationId.toRepoId(bpartnerLocationId));
		if (bPartnerLocationId != null && userId != null)
		{
			final boolean userIsValidForBpartner = bpartnerDAO.getUserIdsForBpartnerLocation(bPartnerLocationId).anyMatch(userId::equals);
			if (!userIsValidForBpartner)
			{
				Loggables.withLogger(logger, Level.INFO).addLog("For OLCand: {}, userId {} is not valid for locationId: {}. Setting to null", olCand.getC_OLCand_ID(), userId, bPartnerLocationId);
				olCand.setAD_User_ID(-1);
			}
		}

		InterfaceWrapperHelper.save(olCand);
		result.incUpdated();
	}

	@Nullable
	private BPartnerLocationId getBPartnerLocationId(final I_C_OLCand olCand, final IParams params, final int bpartnerId)
	{
		final BPartnerLocationId bpartnerLocationId;
		if (params.hasParameter(PARAM_C_BPARTNER_LOCATION_MAP))
		{
			final Map<BPartnerLocationId, BPartnerLocationId> oldToNewLocationIds = (Map<BPartnerLocationId, BPartnerLocationId>)params.getParameterAsObject(PARAM_C_BPARTNER_LOCATION_MAP);
			bpartnerLocationId = oldToNewLocationIds.get(BPartnerLocationId.ofRepoId(olCand.getC_BPartner_ID(), olCand.getC_BPartner_Location_ID()));
		}
		else
		{
			bpartnerLocationId = BPartnerLocationId.ofRepoId(bpartnerId, params.getParameterAsInt(I_C_OLCand.COLUMNNAME_C_BP_Location_Override_ID, -1));
		}
		return bpartnerLocationId;
	}

	@Override
	public OLCandUpdateResult getResult()
	{
		return result;
	}
}
