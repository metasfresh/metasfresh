package de.metas.bpartner.model.interceptor;

import com.google.common.collect.ImmutableList;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerPOCopyRecordSupport;
import de.metas.bpartner.service.IBPartnerBL;
import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater;
import de.metas.bpartner.service.IBPartnerStatisticsUpdater.BPartnerStatisticsUpdateRequest;
import de.metas.bpartner.service.IBPartnerStatsDAO;
import de.metas.copy_with_details.CopyRecordFactory;
import de.metas.interfaces.I_C_BPartner;
import de.metas.logging.LogManager;
import de.metas.security.Principal;
import de.metas.security.permissions.Access;
import de.metas.security.permissions.record_access.PermissionIssuer;
import de.metas.security.permissions.record_access.RecordAccessConfigService;
import de.metas.security.permissions.record_access.RecordAccessFeature;
import de.metas.security.permissions.record_access.RecordAccessGrantRequest;
import de.metas.security.permissions.record_access.RecordAccessService;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.callout.spi.IProgramaticCalloutProvider;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.modelvalidator.annotations.Init;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.adempiere.ad.ui.api.ITabCalloutFactory;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BP_BankAccount;
import org.compiere.model.I_C_BP_PrintFormat;
import org.compiere.model.I_C_BP_Withholding;
import org.compiere.model.I_C_BPartner_CreditLimit;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_BPartner_Product;
import org.compiere.model.I_C_BPartner_Product_Stats;
import org.compiere.model.I_C_BPartner_Stats;
import org.compiere.model.ModelValidator;
import org.compiere.util.Env;
import org.slf4j.Logger;

import java.util.List;

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
	private final IBPartnerDAO bPartnerDAO = Services.get(IBPartnerDAO.class);
	private final IBPartnerBL bPartnerBL = Services.get(IBPartnerBL.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);


	private final static transient Logger logger = LogManager.getLogger(C_BPartner.class);

	private final RecordAccessConfigService recordAccessConfigService = SpringContextHolder.instance.getBean(RecordAccessConfigService.class);
	private final RecordAccessService recordAccessService = SpringContextHolder.instance.getBean(RecordAccessService.class);

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
	 * <p>
	 * task https://github.com/metasfresh/metasfresh/issues/2121
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

		final List<BPartnerId> parentPath = bPartnerDAO.getParentsUpToTheTopInTrx(parentBPartnerId);
		final BPartnerId bpartnerId = BPartnerId.ofRepoId(bpartner.getC_BPartner_ID());
		if (parentPath.contains(bpartnerId))
		{
			final List<BPartnerId> path = ImmutableList.<BPartnerId>builder()
					.addAll(parentPath)
					.add(bpartnerId)
					.build();

			final String bpNames = String.join(" -> ", bPartnerDAO.getBPartnerNamesByIds(path));
			throw new AdempiereException("@" + MSG_CycleDetectedError + "@: " + bpNames)
					.markAsUserValidationError();
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_DELETE)
	public void deleteBPartnerLocations(@NonNull final I_C_BPartner bpartner)
	{
		int deleteCount = 0;
		deleteCount = queryBL.createQueryBuilder(I_C_BPartner_Location.class)
				.addEqualsFilter(I_C_BPartner_Location.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BPartner_Location records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BPartner_Product.class)
				.addEqualsFilter(I_C_BPartner_Product.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BPartner_Product records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BPartner_CreditLimit.class)
				.addEqualsFilter(I_C_BPartner_CreditLimit.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BPartner_CreditLimit records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BPartner_Product_Stats.class)
				.addEqualsFilter(I_C_BPartner_Product_Stats.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BPartner_Product_Stats records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BPartner_Stats.class)
				.addEqualsFilter(I_C_BPartner_Stats.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BPartner_Stats records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BP_BankAccount.class)
				.addEqualsFilter(I_C_BP_BankAccount.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BP_BankAccount records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BP_PrintFormat.class)
				.addEqualsFilter(I_C_BP_PrintFormat.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BP_PrintFormat records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_C_BP_Withholding.class)
				.addEqualsFilter(I_C_BP_Withholding.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} C_BP_Withholding records", deleteCount);

		deleteCount = queryBL.createQueryBuilder(I_AD_User.class)
				.addEqualsFilter(I_AD_User.COLUMNNAME_C_BPartner_ID, bpartner.getC_BPartner_ID())
				.create()
				.delete();
		logger.info("Deleted {} AD_User records", deleteCount);
	}

	@ModelChange(timings = ModelValidator.TYPE_BEFORE_CHANGE, ifColumnsChanged = { I_C_BPartner.COLUMNNAME_C_BPartner_SalesRep_ID, I_C_BPartner.COLUMNNAME_C_BPartner_ID })
	public void validateSalesRep(final I_C_BPartner partner)
	{
		final BPartnerId bPartnerId = BPartnerId.ofRepoId(partner.getC_BPartner_ID());
		final BPartnerId salesRepId = BPartnerId.ofRepoIdOrNull(partner.getC_BPartner_SalesRep_ID());
		bPartnerBL.validateSalesRep(bPartnerId, salesRepId);
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_NEW)
	public void afterSave(@NonNull final I_C_BPartner partner)
	{
		if (recordAccessConfigService.isFeatureEnabled(RecordAccessFeature.BPARTNER_HIERARCHY))
		{
			recordAccessService.grantAccess(
					RecordAccessGrantRequest.builder()
							.recordRef(TableRecordReference.of(I_C_BPartner.Table_Name, partner.getC_BPartner_ID()))
							.principal(Principal.userId(UserId.ofRepoId(partner.getCreatedBy())))
							.permission(Access.READ)
							.permission(Access.WRITE)
							.issuer(PermissionIssuer.AUTO_BP_HIERARCHY)
							.requestedBy(Env.getLoggedUserIdIfExists().orElse(UserId.SYSTEM))
							.description("grant permissions to creator")
							.build());
		}
	}

	@ModelChange(timings = ModelValidator.TYPE_AFTER_CHANGE, ifColumnsChanged = I_C_BPartner.COLUMNNAME_M_SectionCode_ID)
	public void updateStatsSectionCode(@NonNull final I_C_BPartner bpartner)
	{
		Services.get(IBPartnerStatisticsUpdater.class).updateBPartnerStatistics(BPartnerStatisticsUpdateRequest.builder()
																						.bpartnerId(bpartner.getC_BPartner_ID())
																						.build());
	}



}
