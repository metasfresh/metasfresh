package de.metas.customs.process;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.Adempiere;
import org.compiere.model.I_M_InOut;
import org.compiere.model.I_M_InOutLine;

import com.google.common.collect.ImmutableList;

import de.metas.bpartner.BPartnerLocationId;
import de.metas.customs.CustomsInvoiceService;
import de.metas.document.engine.IDocumentBL;
import de.metas.inout.IInOutBL;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.money.CurrencyId;
import de.metas.money.CurrencyRepository;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.product.ProductId;
import de.metas.user.UserId;
import de.metas.util.Services;
import lombok.NonNull;

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

public class M_InOut_Create_CustomsInvoice extends JavaProcess implements IProcessPrecondition
{
	private final CustomsInvoiceService customsInvoiceService = Adempiere.getBean(CustomsInvoiceService.class);
	private final CurrencyRepository currencyRepo = Adempiere.getBean(CurrencyRepository.class);

	@Param(parameterName = "C_BPartner_ID")
	private int p_C_BPartner_ID;

	@Param(parameterName = "C_BPartner_Location_ID")
	private int p_C_BPartner_Location_ID;

	@Param(parameterName = "AD_User_ID")
	private int p_AD_User_ID;

	@Param(parameterName = "DateTo")
	private LocalDate p_EndDate;


	@Override
	protected String doIt() throws Exception
	{

		final List<InOutAndLineId> linesToExport = retrieveLinesToExport();

		final Map<ProductId, List<InOutAndLineId>> linesToExportMap = linesToExport
				.stream()
				.collect(Collectors.groupingBy(inoutAndLineId -> getProductId(inoutAndLineId))); // we asserted earlier that each HU has a locator


		final BPartnerLocationId bpartnerAndLocationId = BPartnerLocationId.ofRepoId(p_C_BPartner_ID, p_C_BPartner_Location_ID);
		final UserId userId = UserId.ofRepoId(p_AD_User_ID);

		final CurrencyId currencyId = currencyRepo.getForBPartnerLocationId(bpartnerAndLocationId);

		customsInvoiceService.generateCustomsInvoice(bpartnerAndLocationId, userId, currencyId, linesToExportMap);

		return MSG_OK;

	}

	private ProductId getProductId(final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final InOutLineId shipmentLineId = inoutAndLineId.getInOutLineId();
		final I_M_InOutLine shipmentLineRecord = inOutDAO.getLineById(shipmentLineId);

		return ProductId.ofRepoId(shipmentLineRecord.getM_Product_ID());
	}

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{

		if (context.getSelectionSize() <= 0)
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		final boolean foundAtLeastOneUnregisteredShipment = context.getSelectedModels(I_M_InOut.class).stream()
				.map(shipmentRecord -> InOutId.ofRepoId(shipmentRecord.getM_InOut_ID()))
				.filter(this::isValidShipment)
				.findAny()
				.isPresent();

		return ProcessPreconditionsResolution.acceptIf(foundAtLeastOneUnregisteredShipment);
	}

	private boolean isValidShipment(final InOutId shipmentId)
	{
		final IDocumentBL documentBL = Services.get(IDocumentBL.class);

		final IInOutBL inOutBL = Services.get(IInOutBL.class);
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final I_M_InOut shipment = inOutDAO.getById(shipmentId);

		if (!shipment.isSOTrx())
		{
			return false;
		}

		if (inOutBL.isReversal(shipment))
		{
			return false;
		}

		if (!documentBL.isStatusCompletedOrClosedOrReversed(shipment.getDocStatus()))
		{
			return false;
		}

		if (shipment.isExportedToCustomsInvoice())
		{
			return false;
		}

		return true;
	}

	final List<InOutAndLineId> retrieveLinesToExport()
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final IQueryFilter<I_M_InOut> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ImmutableList<InOutId> shipmentsToExport = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOut.class)
				.filter(queryFilter)
				.create()
				.listIds(InOutId::ofRepoId)
				.stream()
				.filter(this::isValidShipment)
				.collect(ImmutableList.toImmutableList());

		List<InOutAndLineId> shipmentLines = new ArrayList<>();

		for (final InOutId shipmentId : shipmentsToExport)
		{
			shipmentLines.addAll(inOutDAO.retrieveLinesForInOutId(shipmentId));
		}

		return shipmentLines;
	}

}
