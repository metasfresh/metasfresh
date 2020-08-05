package de.metas.bpartner.model.interceptor;

import java.util.List;
import java.util.stream.Collectors;

import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.CopyRecordFactory;
import org.compiere.model.ModelValidator;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerPOCopyRecordSupport;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.interfaces.I_C_BPartner;
import de.metas.location.ILocationBL;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2017 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program. If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Interceptor(I_C_BPartner.class)
public class C_BPartner
{
	private static final String MSG_CycleDetectedError = "CycleDetectedError";

	final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	final ILocationBL locationBL = Services.get(ILocationBL.class);
	final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);

	@Init
	public void init()
	{
		CopyRecordFactory.enableForTableName(I_C_BPartner.Table_Name);
		CopyRecordFactory.registerCopyRecordSupport(I_C_BPartner.Table_Name, BPartnerPOCopyRecordSupport.class);

		Services.get(ITabCalloutFactory.class)
				.registerTabCalloutForTable(I_C_BPartner.Table_Name, de.metas.bpartner.callout.C_BPartner_TabCallout.class);

		Services.get(IProgramaticCalloutProvider.class)
				.registerAnnotatedCallout(new de.metas.bpartner.callout.C_BPartner());

	}

	/**
	 * Makes sure that a new bPartner gets a C_BPartner_Stats record.
	 * We do this because there is at least one hard-coded inner join between the two (in CalloutOrder).
	 * Note that in the DB we have an FK-constraint with "on delete cascade".
	 *
	 * @param bpartner
	 * @task https://github.com/metasfresh/metasfresh/issues/2121
	 */
	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void createBPartnerStatsRecord(@NonNull final I_C_BPartner bpartner)
	{
		Services.get(IBPartnerStatsDAO.class).getCreateBPartnerStats(bpartner);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = I_C_BPartner.COLUMNNAME_C_BP_Group_ID)
	public void updateSO_CreditStatus(@NonNull final I_C_BPartner bpartner)
	{
		// make sure that the SO_CreditStatus is correct
		Services.get(IBPartnerStatisticsUpdater.class)
				.updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
						.bpartnerId(bpartner.getC_BPartner_ID())
						.alsoResetCreditStatusFromBPGroup(true)
						.build());
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner.COLUMNNAME_AD_Language)
	public void updateLocation(@NonNull final I_C_BPartner bpartner)
	{
		bPartnerBL.updateAllAddresses(bpartner);
	}

	@ModelChange(//
			timings = { ModelValidator.TYPE_AFTER_NEW/* needs to be after-new bc we need a C_BPartner_ID */, ModelValidator.TYPE_BEFORE_CHANGE }, //
			ifColumnsChanged = I_C_BPartner.COLUMNNAME_BPartner_Parent_ID)
	public void avoidBPartnerParentLoops(@NonNull final I_C_BPartner bpartner)
	{
		final BPartnerId parentBPartnerId = BPartnerId.ofRepoIdOrNull(bpartner.getBPartner_Parent_ID());
		if (parentBPartnerId == null)
		{
			return;
		}

		final IBPartnerDAO bpartnersRepo = Services.get(IBPartnerDAO.class);
		final List<BPartnerId> parentPath = bpartnersRepo.getParentsUpToTheTopInTrx(parentBPartnerId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		if (parentPath.contains(bpartnerId))
		{
			final List<BPartnerId> path = ImmutableList.<BPartnerId>builder()
					.addAll(parentPath)
					.add(bpartnerId)
					.build();

			final String bpNames = bpartnersRepo.getBPartnerNamesByIds(path)
					.stream()
					.collect(Collectors.joining(" -> "));
			throw new AdempiereException("@" + MSG_CycleDetectedError + "@: " + bpNames)
					.markAsUserValidationError();
		}
	}
}
