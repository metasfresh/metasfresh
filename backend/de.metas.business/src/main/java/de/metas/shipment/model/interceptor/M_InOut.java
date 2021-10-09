package de.metas.shipment.model.interceptor;

import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeBL;
import de.metas.i18n.AdMessageKey;
import de.metas.i18n.IMsgBL;
import de.metas.i18n.ITranslatableString;
import de.metas.inout.IInOutBL;
import de.metas.inout.InOutId;
import de.metas.logging.TableRecordMDC;
import de.metas.shipment.repo.ShipmentDeclarationRepository;
import de.metas.shipment.service.ShipmentDeclarationCreator;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.DocValidate;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.model.I_M_InOut;
import org.compiere.model.ModelValidator;
import org.slf4j.MDC.MDCCloseable;
import org.springframework.stereotype.Component;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

@Interceptor(I_M_InOut.class)
@Component
public class M_InOut
{
	private static final AdMessageKey ERR_ShipmentDeclaration = AdMessageKey.of("de.metas.shipment.model.interceptor.M_InOut.ERR_ShipmentDeclaration");

	private final ShipmentDeclarationCreator shipmentDeclarationCreator;
	private final ShipmentDeclarationRepository shipmentDeclarationRepo;
	private final IDocTypeBL docTypeBL = Services.get(IDocTypeBL.class);
	private final IInOutBL inOutBL = Services.get(IInOutBL.class);

	public M_InOut(@NonNull final ShipmentDeclarationCreator shipmentDeclarationCreator,
			@NonNull final ShipmentDeclarationRepository shipmentDeclarationRepo)
	{
		this.shipmentDeclarationCreator = shipmentDeclarationCreator;
		this.shipmentDeclarationRepo = shipmentDeclarationRepo;
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createShipmentDeclarationIfNeeded(final I_M_InOut inout)
	{
		try (final MDCCloseable inoutRecordMDC = TableRecordMDC.putTableRecordReference(inout))
		{
			if (!inout.isSOTrx())
			{
				// Only applies to shipments
				return;
			}

			final InOutId shipmentId = InOutId.ofRepoId(inout.getM_InOut_ID());

			shipmentDeclarationCreator.createShipmentDeclarationsIfNeeded(shipmentId);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_AFTER_COMPLETE })
	public void createRequestIfConfigured(final I_M_InOut inout)
	{
		if (docTypeBL.hasRequestType(DocTypeId.ofRepoId(inout.getC_DocType_ID())))
		{
			inOutBL.createRequestFromInOut(inout);
		}
	}

	@DocValidate(timings = { ModelValidator.TIMING_BEFORE_REACTIVATE })
	public void forbidReactivateOnCompletedShipmentDeclaration(final I_M_InOut inout)
	{
		try (final MDCCloseable inoutRecordMDC = TableRecordMDC.putTableRecordReference(inout))
		{
			if (!inout.isSOTrx())
			{
				// Only applies to shipments
				return;
			}

			final InOutId shipmentId = InOutId.ofRepoId(inout.getM_InOut_ID());

			if (shipmentDeclarationRepo.existCompletedShipmentDeclarationsForShipmentId(shipmentId))
			{
				final IMsgBL msgBL = Services.get(IMsgBL.class);

				final ITranslatableString msg = msgBL.getTranslatableMsgText(ERR_ShipmentDeclaration);
				throw new AdempiereException(msg).markAsUserValidationError();
			}
		}
	}
}
