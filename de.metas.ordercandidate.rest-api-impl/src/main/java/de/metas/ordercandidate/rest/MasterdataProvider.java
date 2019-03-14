package de.metas.ordercandidate.rest;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Nullable;

import org.adempiere.service.IOrgDAO;
import org.adempiere.service.IOrgDAO.OrgQuery;
import org.adempiere.service.OrgId;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_Currency;
import org.compiere.util.Env;
import org.compiere.util.Util;

import com.google.common.annotations.VisibleForTesting;

import de.metas.currency.ICurrencyDAO;
import de.metas.document.DocTypeId;
import de.metas.document.DocTypeQuery;
import de.metas.document.IDocTypeDAO;
import de.metas.money.CurrencyId;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.rest.exceptions.MissingPropertyException;
import de.metas.pricing.PricingSystemId;
import de.metas.pricing.service.IPriceListDAO;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;

/*
 * #%L
 * de.metas.ordercandidate.rest-api-impl
 * %%
 * Copyright (C) 2018 metas GmbH
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

public final class MasterdataProvider
{
	private final IPriceListDAO priceListsRepo = Services.get(IPriceListDAO.class);

	private final IOrgDAO orgsRepo = Services.get(IOrgDAO.class);

	private final IDocTypeDAO docTypeDAO = Services.get(IDocTypeDAO.class);

	private final OrgId defaultOrgId;

	private final Map<String, OrgId> orgIdsByCode = new HashMap<>();

	private final PermissionService permissionService;

	@Getter
	private final BPartnerMasterDataProvider bPartnerMasterDataProvider;

	@Getter
	private final ProductMasterDataProvider productMasterDataProvider;

	@Builder
	private MasterdataProvider(
			@Nullable final Properties ctx,
			@Nullable final PermissionService permissionService,
			@Nullable final BPartnerMasterDataProvider bpartnerMasterDataProvider,
			@Nullable final ProductMasterDataProvider productMasterDataProvider)
	{
		final Properties ctxToUse = Util.coalesceSuppliers(() -> ctx, () -> Env.getCtx());

		this.defaultOrgId = OrgId.optionalOfRepoId(Env.getAD_Org_ID(ctxToUse)).orElse(OrgId.ANY);

		this.permissionService = Util.coalesce(permissionService, PermissionService.of(ctxToUse));
		this.bPartnerMasterDataProvider = Util.coalesce(bpartnerMasterDataProvider, BPartnerMasterDataProvider.of(ctxToUse, permissionService));
		this.productMasterDataProvider = Util.coalesce(productMasterDataProvider, ProductMasterDataProvider.of(ctxToUse, permissionService));
	}

	public void assertCanCreateNewOLCand(final OrgId orgId)
	{
		permissionService.assertCanCreateOrUpdateRecord(orgId, I_C_OLCand.class);
	}

	public PricingSystemId getPricingSystemIdByValue(final String pricingSystemCode)
	{
		if (Check.isEmpty(pricingSystemCode, true))
		{
			return null;
		}

		return priceListsRepo.getPricingSystemIdByValue(pricingSystemCode);
	}

	public OrgId getCreateOrgId(@Nullable final JsonOrganization json)
	{
		if (json == null)
		{
			return defaultOrgId;
		}

		return orgIdsByCode.compute(json.getCode(), (code, existingOrgId) -> createOrUpdateOrgId(json, existingOrgId));
	}

	@VisibleForTesting
	OrgId createOrUpdateOrgId(
			@NonNull final JsonOrganization json,
			@Nullable OrgId existingOrgId)
	{
		final SyncAdvise orgSyncAdvise = json.getSyncAdvise();

		if (existingOrgId == null)
		{
			final String code = json.getCode();
			if (Check.isEmpty(code, true))
			{
				throw new MissingPropertyException("Missing 'code' property; JsonOrganization={}", json);
			}

			final OrgQuery query = OrgQuery.builder()
					.orgValue(code)
					.failIfNotExists(orgSyncAdvise.isFailIfNotExists())
					.outOfTrx(orgSyncAdvise.isLoadReadOnly())
					.build();

			existingOrgId = orgsRepo
					.retrieveOrgIdBy(query)
					.orElse(null);
		}

		final I_AD_Org orgRecord;
		if (existingOrgId != null)
		{
			orgRecord = orgsRepo.getById(existingOrgId);
		}
		else
		{
			orgRecord = newInstance(I_AD_Org.class);
		}

		if (!orgSyncAdvise.isLoadReadOnly())
		{
			permissionService.assertCanCreateOrUpdate(orgRecord);
			updateOrgRecord(orgRecord, json);
			orgsRepo.save(orgRecord);
		}

		final OrgId orgId = OrgId.ofRepoId(orgRecord.getAD_Org_ID());
		if (json.getBpartner() != null)
		{
			bPartnerMasterDataProvider
					.getCreateOrgBPartnerInfo(
							json.getBpartner(),
							orgId);
		}
		return orgId;
	}

	private void updateOrgRecord(@NonNull final I_AD_Org orgRecord, @NonNull final JsonOrganization json)
	{
		orgRecord.setValue(json.getCode());
		orgRecord.setName(json.getName());
	}

	public JsonOrganization getJsonOrganizationById(final int orgId)
	{
		final I_AD_Org orgRecord = orgsRepo.retrieveOrg(orgId);
		if (orgRecord == null)
		{
			return null;
		}

		return JsonOrganization.builder()
				.code(orgRecord.getValue())
				.name(orgRecord.getName())
				.build();
	}

	public DocTypeId getDocTypeId(
			@NonNull final JsonDocTypeInfo invoiceDocType,
			@NonNull final OrgId orgId)
	{
		final String docSubType = Util.firstNotEmptyTrimmed(
				invoiceDocType.getDocSubType(),
				DocTypeQuery.DOCSUBTYPE_NONE);

		final I_AD_Org orgRecord = orgsRepo.retrieveOrg(orgId.getRepoId());

		final DocTypeQuery query = DocTypeQuery
				.builder()
				.docBaseType(invoiceDocType.getDocBaseType())
				.docSubType(docSubType)
				.adClientId(orgRecord.getAD_Client_ID())
				.adOrgId(orgRecord.getAD_Org_ID())
				.build();

		return docTypeDAO.getDocTypeId(query);
	}

	public CurrencyId getCurrencyId(@NonNull final String currencyCode)
	{
		if (Check.isEmpty(currencyCode))
		{
			return null;
		}
		final I_C_Currency currencyRecord = Services
				.get(ICurrencyDAO.class)
				.retrieveCurrencyByISOCode(Env.getCtx(), currencyCode);
		Check.errorIf(currencyRecord == null, "Unable to retrieve a C_Currency for ISO code={}", currencyCode);
		return CurrencyId.ofRepoId(currencyRecord.getC_Currency_ID());
	}

}
