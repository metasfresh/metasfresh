package de.metas.vertical.pharma.vendor.gateway.msv3.availability;

import static org.adempiere.model.InterfaceWrapperHelper.load;
import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.IdentityHashMap;
import java.util.Map;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Issue;
import org.compiere.model.I_C_BPartner;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.msv3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_Verfuegbarkeit_Transaction;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.model.I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.Msv3FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelne.Artikel;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsanfrageEinzelneAntwort;
import de.metas.vertical.pharma.vendor.gateway.msv3.schema.VerfuegbarkeitsantwortArtikel;
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
	private final int orgId;

	private final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne;

	private VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwort;

	private Msv3FaultInfo faultInfo;

	private Exception otherException;

	private final Map<Artikel, MSV3ArtikelContextInfo> requestArtikel2contextInfo //
			= new IdentityHashMap<>();

	private final Map<VerfuegbarkeitsantwortArtikel, MSV3ArtikelContextInfo> responseArtikel2contextInfo //
			= new IdentityHashMap<>();

	private final MSV3AvailabilityDataPersister availabilityDataPersister;

	@Builder
	private MSV3AvailabilityTransaction(
			final int vendorId,
			@NonNull final VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelne)
	{
		Check.errorIf(vendorId <= 0, "The given parameter vendorId needs to be > 0; vendorId={}", vendorId);
		this.verfuegbarkeitsanfrageEinzelne = verfuegbarkeitsanfrageEinzelne;

		final I_C_BPartner vendor = load(vendorId, I_C_BPartner.class);

		this.orgId = vendor.getAD_Org_ID();
		this.availabilityDataPersister = MSV3AvailabilityDataPersister.createNewInstance(orgId);
	}

	public void putContextInfo(
			@NonNull final Artikel artikel,
			@NonNull final MSV3ArtikelContextInfo availabilityRequestItem)
	{
		requestArtikel2contextInfo.put(artikel, availabilityRequestItem);
	}

	public void putContextInfo(
			@NonNull final VerfuegbarkeitsantwortArtikel artikel,
			@NonNull final MSV3ArtikelContextInfo availabilityRequestItem)
	{
		responseArtikel2contextInfo.put(artikel, availabilityRequestItem);
	}

	public I_MSV3_Verfuegbarkeit_Transaction store()
	{
		final I_MSV3_Verfuegbarkeit_Transaction transactionRecord = newInstance(I_MSV3_Verfuegbarkeit_Transaction.class);
		transactionRecord.setAD_Org_ID(orgId);

		final I_MSV3_VerfuegbarkeitsanfrageEinzelne verfuegbarkeitsanfrageEinzelneRecord = //
				availabilityDataPersister.storeAvailabilityRequest(
						verfuegbarkeitsanfrageEinzelne,
						ImmutableMap.copyOf(requestArtikel2contextInfo));
		transactionRecord.setMSV3_VerfuegbarkeitsanfrageEinzelne(verfuegbarkeitsanfrageEinzelneRecord);

		if (verfuegbarkeitsanfrageEinzelneAntwort != null)
		{
			final I_MSV3_VerfuegbarkeitsanfrageEinzelneAntwort verfuegbarkeitsanfrageEinzelneAntwortRecord = //
					availabilityDataPersister.storeAvailabilityResponse(
							verfuegbarkeitsanfrageEinzelneAntwort,
							ImmutableMap.copyOf(responseArtikel2contextInfo));
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
			final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(otherException);
			transactionRecord.setAD_Issue(issue);
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
