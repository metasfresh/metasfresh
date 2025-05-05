package de.metas.shipper.gateway.derkurier.process;

import de.metas.document.engine.IDocument;
import de.metas.document.engine.IDocumentBL;
import de.metas.email.EMailAddress;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.PInstanceId;
import de.metas.process.ProcessInfo;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import de.metas.shipper.gateway.derkurier.misc.DerKurierDeliveryOrderEmailer;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfig;
import de.metas.shipper.gateway.derkurier.misc.DerKurierShipperConfigRepository;
import de.metas.shipping.model.I_M_ShipperTransportation;
import de.metas.shipping.model.ShipperTransportationId;
import de.metas.util.Check;
import de.metas.util.Services;
import lombok.NonNull;
import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.Adempiere;
import org.compiere.SpringContextHolder;

/*
 * #%L
 * de.metas.shipper.gateway.derkurier
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

public class M_ShipperTransportation_SendDerKurierEMail
		extends JavaProcess
		implements IProcessPrecondition
{

	private final transient IQueryBL queryBL = Services.get(IQueryBL.class);
	private final transient IDocumentBL documentBL = Services.get(IDocumentBL.class);

	private final transient DerKurierDeliveryOrderEmailer //
			derKurierDeliveryOrderEmailer = SpringContextHolder.instance.getBean(DerKurierDeliveryOrderEmailer.class);

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		final SelectionSize selectionSize = context.getSelectionSize();
		if (selectionSize.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		if (selectionSize.isAllSelected() || selectionSize.getSize() > 500)
		{
			// Checking is too expensive; just assume that some selected records have an email address
			return ProcessPreconditionsResolution.accept();
		}

		final boolean atLeastOneRecordHasEmail = context
				.streamSelectedModels(I_M_ShipperTransportation.class)
				.filter(this::isCompleted)
				.anyMatch(this::hasDerKurierMailAddress);
		if (!atLeastOneRecordHasEmail)
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("No selected M_ShipperTransportation's shipper has a Der Kurier config");
		}
		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected final void prepare()
	{
		final ProcessInfo pi = getProcessInfo();

		final IQueryFilter<I_M_ShipperTransportation> filter = pi.getQueryFilterOrElse(ConstantQueryFilter.of(false));
		final PInstanceId pinstanceId = getPinstanceId();

		// Create selection for PInstance and make sure we're enqueuing something
		final int selectionCount = queryBL.createQueryBuilder(I_M_ShipperTransportation.class, this)
				.addOnlyActiveRecordsFilter()
				.filter(filter)
				.addEqualsFilter(I_M_ShipperTransportation.COLUMN_DocStatus, IDocument.STATUS_Completed)
				.create()
				.createSelection(pinstanceId);

		Check.errorIf(selectionCount <= 0, "No record matches the process info's selection filter; AD_PInstance_ID={}, filter={}", pinstanceId, filter);
	}

	@Override
	protected String doIt() throws Exception
	{
		queryBL
				.createQueryBuilder(I_M_ShipperTransportation.class)
				.setOnlySelection(getPinstanceId())
				.create()
				.iterateAndStream()
				.filter(this::isCompleted)
				.filter(this::hasDerKurierMailAddress)
				.map(I_M_ShipperTransportation::getM_ShipperTransportation_ID)
				.map(ShipperTransportationId::ofRepoId)
				.forEach(derKurierDeliveryOrderEmailer::sendShipperTransportationAsEmail);

		return MSG_OK;
	}

	private boolean isCompleted(@NonNull final I_M_ShipperTransportation shipperTransportationRecord)
	{
		return documentBL.isDocumentCompleted(shipperTransportationRecord);
	}

	private boolean hasDerKurierMailAddress(@NonNull final I_M_ShipperTransportation shipperTransportationRecord)
	{
		final DerKurierShipperConfigRepository derKurierShipperConfigRepository = Adempiere.getBean(DerKurierShipperConfigRepository.class);
		final int shipperId = shipperTransportationRecord.getM_Shipper_ID();

		final DerKurierShipperConfig config = derKurierShipperConfigRepository.retrieveConfigForShipperIdOrNull(shipperId);
		if (config == null)
		{
			return false;
		}
		final EMailAddress mailTo = config.getDeliveryOrderRecipientEmailOrNull();
		return mailTo != null;
	}
}
