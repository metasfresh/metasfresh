package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Services;
import org.compiere.model.I_AD_Issue;

import com.google.common.collect.ImmutableMap;

import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3ClientException;
import de.metas.vertical.pharma.vendor.gateway.mvs3.common.Msv3FaultInfoDataPersister;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung_Transaction;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAnteil;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortAuftrag;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwortPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungPosition;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
 * #%L
 * metasfresh-pharma.vendor.gateway.mvs3
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
public class MSV3PurchaseOrderTransaction
{
	@Getter
	private final Bestellung bestellung;

	/**
	 * The ordering of the {@link BestellungPosition} is important.<br>
	 * Also see {@link #extractBestellungAntwortPosition2PurchaseCandidateId()}
	 */
	private ImmutableMap<BestellungPosition, Integer> bestellungPosition2PurchaseCandidateId;

	private final int orgId;

	@Getter
	private BestellungAntwort bestellungAntwort;

	private Map<BestellungAnteil, Integer> bestellungAnteil2Id;

	private Msv3FaultInfo faultInfo;

	private Exception otherException;

	@Builder
	private MSV3PurchaseOrderTransaction(
			@NonNull final Bestellung bestellung,
			@NonNull final ImmutableMap<BestellungPosition, Integer> bestellungPosition2PurchaseCandidateId,
			final int orgId)
	{
		this.bestellungPosition2PurchaseCandidateId = bestellungPosition2PurchaseCandidateId;
		this.bestellung = bestellung;
		this.orgId = orgId;
	}

	public I_MSV3_Bestellung_Transaction store()
	{
		final MSV3PurchaseOrderRequestPersister purchaseOrderDataPersister = MSV3PurchaseOrderRequestPersister
				.createNewForOrgId(orgId, bestellungPosition2PurchaseCandidateId);

		final I_MSV3_Bestellung_Transaction transactionRecord = newInstance(I_MSV3_Bestellung_Transaction.class);
		transactionRecord.setAD_Org_ID(orgId);

		final I_MSV3_Bestellung bestellungRecord = //
				purchaseOrderDataPersister.storePurchaseOrderRequest(bestellung);
		transactionRecord.setMSV3_Bestellung(bestellungRecord);

		if (bestellungAntwort != null)
		{
			final ImmutableMap<BestellungAntwortPosition, Integer> bestellungAntwortPosition2PurchaseCandidateId = //
					extractBestellungAntwortPosition2PurchaseCandidateId();

			final MSV3PurchaseOrderResponsePersister purchaseOrderResponsePersister = //
					MSV3PurchaseOrderResponsePersister
							.createNewForOrgId(
									orgId,
									bestellungAntwortPosition2PurchaseCandidateId);

			final I_MSV3_BestellungAntwort bestellungAntwortRecord = //
					purchaseOrderResponsePersister.storePurchaseOrderResponse(bestellungAntwort);
			transactionRecord.setMSV3_BestellungAntwort(bestellungAntwortRecord);

			bestellungAnteil2Id = purchaseOrderDataPersister.getBestellungAnteil2Id();
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

	/**
	 * Takes advantage of the positions of the WSDL request having the same ordering as the response positions.
	 * @return
	 */
	private ImmutableMap<BestellungAntwortPosition, Integer> extractBestellungAntwortPosition2PurchaseCandidateId()
	{
		final ImmutableMap.Builder<BestellungAntwortPosition, Integer> bestellungAntwortPosition2PurchaseCandidateId = //
				ImmutableMap.builder();

		final Iterator<BestellungPosition> iterator = bestellungPosition2PurchaseCandidateId.keySet().iterator();
		final List<BestellungAntwortAuftrag> auftraege = bestellungAntwort.getAuftraege();
		for (int auftragIdx = 0; auftragIdx < auftraege.size(); auftragIdx++)
		{
			final BestellungAntwortAuftrag bestellungAntwortAuftrag = auftraege.get(auftragIdx);
			final List<BestellungAntwortPosition> positionen = bestellungAntwortAuftrag.getPositionen();
			for (int positionIdx = 0; positionIdx < positionen.size(); positionIdx++)
			{
				final BestellungAntwortPosition bestellungAntwortPosition = positionen.get(positionIdx);
				final BestellungPosition bestellungPosition = iterator.next();

				bestellungAntwortPosition2PurchaseCandidateId.put(
						bestellungAntwortPosition,
						bestellungPosition2PurchaseCandidateId.get(bestellungPosition));
			}
		}
		return bestellungAntwortPosition2PurchaseCandidateId.build();
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

	public int getIdOfBestellungAnteil(BestellungAnteil anteil)
	{
		return bestellungAnteil2Id.get(anteil);
	}
}
