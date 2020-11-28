package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.IdentityHashMap;
import java.util.Map;

import org.compiere.model.I_C_BPartner;

import com.google.common.collect.ImmutableMap;

import de.metas.bpartner.service.IBPartnerDAO;
import de.metas.error.AdIssueId;
import de.metas.error.IErrorManager;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQuery;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityQueryItem;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponse;
import de.metas.vertical.pharma.msv3.protocol.stockAvailability.StockAvailabilityResponseItem;
import de.metas.vertical.pharma.msv3.protocol.types.BPartnerId;
import de.metas.vertical.pharma.msv3.protocol.types.FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Verfuegbarkeit_Transaction;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import lombok.Builder;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.msv3
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

@Setter
public class MSV3AvailabilityTransaction
{
	private final OrgId orgId;

	private final StockAvailabilityQuery query;

	private StockAvailabilityResponse response;

	private FaultInfo faultInfo;

	private Exception otherException;

	private final Map<StockAvailabilityQueryItem, MSV3ArtikelContextInfo> contextInfosByQueryItem = new IdentityHashMap<>();

	private final Map<StockAvailabilityResponseItem, MSV3ArtikelContextInfo> contextInfosByResponse = new IdentityHashMap<>();

	private final MSV3AvailabilityDataPersister availabilityDataPersister;

	@Builder
	private MSV3AvailabilityTransaction(
			@NonNull final BPartnerId vendorId,
			@NonNull final StockAvailabilityQuery query)
	{
		this.query = query;

		final I_C_BPartner vendor = Services.get(IBPartnerDAO.class).getById(vendorId.getBpartnerId());
		this.orgId = OrgId.ofRepoId(vendor.getAD_Org_ID());
		
		this.availabilityDataPersister = MSV3AvailabilityDataPersister.createNewInstance(orgId);
	}

	public void putContextInfos(final Map<StockAvailabilityQueryItem, MSV3ArtikelContextInfo> contextInfos)
	{
		contextInfosByQueryItem.putAll(contextInfos);
	}

	public void putContextInfo(
			@NonNull final StockAvailabilityQueryItem queryItem,
			@NonNull final MSV3ArtikelContextInfo contextInfo)
	{
		contextInfosByQueryItem.put(queryItem, contextInfo);
	}

	public void putContextInfo(
			@NonNull final StockAvailabilityResponseItem responseItem,
			@NonNull final MSV3ArtikelContextInfo contextInfo)
	{
		contextInfosByResponse.put(responseItem, contextInfo);
	}

	public I_MSV3_Verfuegbarkeit_Transaction store()
	{
		final I_MSV3_Verfuegbarkeit_Transaction transactionRecord = newInstance(I_MSV3_Verfuegbarkeit_Transaction.class);
		transactionRecord.setAD_Org_ID(orgId.getRepoId());

		final I_MSV3_VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelneRecord = //
				availabilityDataPersister.storeAvailabilityRequest(
						query,
						ImmutableMap.copyOf(contextInfosByQueryItem));
		transactionRecord.setMSV3_VerfuegbarkeitsanfrageEinzelne(verfuegbarkeitsanfrageEinzelneRecord);

		if (response != null)
		{
			final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
					availabilityDataPersister.storeAvailabilityResponse(
							response,
							ImmutableMap.copyOf(contextInfosByResponse));
			transactionRecord.setMSV3_VerfuegbarkeitsanfrageEinzelneAntwort(verfuegbarkeitsanfrageEinzelneAntwortRecord);
		}
		if (faultInfo != null)
		{
			final I_MSV3_FaultInfo faultInfoRecord = Msv3FaultInfoDataPersister
					.newInstanceWithOrgId(orgId)
					.storeMsv3FaultInfoOrNull(faultInfo);
			transactionRecord.setMSV3_FaultInfo(faultInfoRecord);
		}
		if (otherException != null)
		{
			final AdIssueId issueId = Services.get(IErrorManager.class).createIssue(otherException);
			transactionRecord.setAD_Issue_ID(issueId.getRepoId());
		}

		save(transactionRecord);
		return transactionRecord;
	}

	public RuntimeException getExceptionOrNull()
	{
		if (faultInfo == null && otherException == null)
		{
			return null;
		}
		return Msv3ClientException.builder().msv3FaultInfo(faultInfo)
				.cause(otherException).build();
	}
}
