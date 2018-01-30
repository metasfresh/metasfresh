package de.metas.vertical.pharma.vendor.gateway.mvs3.purchaseOrder;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;

import org.adempiere.ad.service.IErrorManager;
import org.adempiere.util.Services;
import org.compiere.Adempiere;
import org.compiere.model.I_AD_Issue;

import de.metas.vertical.pharma.vendor.gateway.mvs3.common.FaultInfoSaver;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_Bestellung_Transaction;
import de.metas.vertical.pharma.vendor.gateway.mvs3.model.I_MSV3_FaultInfo;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Bestellung;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.BestellungAntwort;
import de.metas.vertical.pharma.vendor.gateway.mvs3.schema.Msv3FaultInfo;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

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

@Value
@Builder
public class Msv3PurchaseOrderTransaction
{
	@NonNull
	Bestellung bestellung;

	BestellungAntwort bestellungAntwort;

	Msv3FaultInfo faultInfo;

	Exception otherException;

	public I_MSV3_Bestellung_Transaction store()
	{
		final Msv3PurchaseOrderSaver purchaseOrderPersistanceMapper = Adempiere.getBean(Msv3PurchaseOrderSaver.class);

		final I_MSV3_Bestellung_Transaction transactionRecord = newInstance(I_MSV3_Bestellung_Transaction.class);

		final I_MSV3_Bestellung bestellungRecord = purchaseOrderPersistanceMapper.storePurchaseOrder(bestellung);
		transactionRecord.setMSV3_Bestellung(bestellungRecord);

		if (bestellungAntwort != null)
		{
			final I_MSV3_BestellungAntwort bestellungAntwortRecord = purchaseOrderPersistanceMapper.storePurchaseOrderResponse(bestellungAntwort);
			transactionRecord.setMSV3_BestellungAntwort(bestellungAntwortRecord);
		}
		if (faultInfo != null)
		{
			final FaultInfoSaver faultInfoPersistanceMapper = Adempiere.getBean(FaultInfoSaver.class);
			final I_MSV3_FaultInfo faultInfoRecord = faultInfoPersistanceMapper.storeMsv3FaultInfoOrNull(faultInfo);
			transactionRecord.setMSV3_FaultInfo(faultInfoRecord);
		}
		if(otherException != null)
		{
			final I_AD_Issue issue = Services.get(IErrorManager.class).createIssue(otherException);
			transactionRecord.setAD_Issue(issue);
		}

		save(transactionRecord);
		return transactionRecord;
	}
}
