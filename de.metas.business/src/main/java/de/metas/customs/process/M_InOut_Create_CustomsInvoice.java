package de.metas.customs.process;

import java.time.LocalDate;
import java.util.List;
import java.util.function.Function;

import org.adempiere.ad.dao.ConstantQueryFilter;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.ad.dao.IQueryFilter;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_M_InOut;
import org.compiere.util.Env;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.currency.ICurrencyBL;
import de.metas.customs.CustomsInvoice;
import de.metas.customs.CustomsInvoiceRequest;
import de.metas.customs.CustomsInvoiceService;
import de.metas.customs.event.CustomsInvoiceUserNotificationsProducer;
import de.metas.document.DocTypeId;
import de.metas.document.IDocumentLocationBL;
import de.metas.document.model.impl.PlainDocumentLocation;
import de.metas.inout.IInOutDAO;
import de.metas.inout.InOutAndLineId;
import de.metas.inout.InOutId;
import de.metas.inout.InOutLineId;
import de.metas.inout.model.I_M_InOutLine;
import de.metas.money.CurrencyId;
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
	private final CustomsInvoiceService customsInvoiceService = SpringContextHolder.instance.getBean(CustomsInvoiceService.class);
	private final ShipmentLinesForCustomsInvoiceRepo shipmentLinesForCustomsInvoiceRepo = SpringContextHolder.instance.getBean(ShipmentLinesForCustomsInvoiceRepo.class);

	@Param(parameterName = "C_BPartner_ID")
	private BPartnerId p_BPartnerId;

	@Param(parameterName = "C_BPartner_Location_ID")
	private int p_C_BPartner_Location_ID;

	@Param(parameterName = "AD_User_ID")
	private UserId p_ContactId;

	@Param(parameterName = "IsComplete")
	private boolean p_IsComplete;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(@NonNull final IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ICurrencyBL currencyBL = Services.get(ICurrencyBL.class);

		final List<InOutAndLineId> linesToExport = retrieveLinesToExport();

		final ImmutableSetMultimap<ProductId, InOutAndLineId> linesToExportMap = linesToExport
				.stream()
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(
						this::getProductId, // keyFunction,
						Function.identity()));// valueFunction

		final BPartnerLocationId bpartnerAndLocationId = BPartnerLocationId.ofRepoId(p_BPartnerId, p_C_BPartner_Location_ID);

		final CurrencyId currencyId = currencyBL.getBaseCurrencyId(Env.getClientId(), Env.getOrgId());

		final LocalDate invoiceDate = Env.getLocalDate();

		final DocTypeId docTypeId = customsInvoiceService.retrieveCustomsInvoiceDocTypeId();

		final String documentNo = customsInvoiceService.reserveDocumentNo(docTypeId);

		final PlainDocumentLocation documentLocation = PlainDocumentLocation.builder()
				.bpartnerId(p_BPartnerId)
				.bpartnerLocationId(bpartnerAndLocationId)
				.contactId(p_ContactId)
				.build();

		Services.get(IDocumentLocationBL.class).setBPartnerAddress(documentLocation);

		final String bpartnerAddress = documentLocation.getBPartnerAddress();

		final CustomsInvoiceRequest customsInvoiceRequest = CustomsInvoiceRequest.builder()
				.bpartnerAndLocationId(bpartnerAndLocationId)
				.bpartnerAddress(bpartnerAddress)
				.userId(p_ContactId)
				.currencyId(currencyId)
				.linesToExportMap(linesToExportMap)
				.invoiceDate(invoiceDate)
				.documentNo(documentNo)
				.docTypeId(docTypeId)
				.build();

		final CustomsInvoice customsInvoice = customsInvoiceService.generateCustomsInvoice(customsInvoiceRequest);

		CustomsInvoiceUserNotificationsProducer.newInstance()
		.notifyGenerated(customsInvoice);

		if (p_IsComplete)
		{
			customsInvoiceService.completeCustomsInvoice(customsInvoice);
		}

		final ImmutableSet<InOutId> exportedShippmentIds = linesToExport.stream()
				.map(InOutAndLineId::getInOutId)
				.collect(ImmutableSet.toImmutableSet());

		customsInvoiceService.setCustomsInvoiceToShipments(exportedShippmentIds, customsInvoice.getId());

		customsInvoiceService.setCustomsInvoiceLineToShipmentLines(linesToExportMap, customsInvoice);

		return MSG_OK;

	}

	private ProductId getProductId(final InOutAndLineId inoutAndLineId)
	{
		final IInOutDAO inOutDAO = Services.get(IInOutDAO.class);

		final InOutLineId shipmentLineId = inoutAndLineId.getInOutLineId();
		final I_M_InOutLine shipmentLineRecord = inOutDAO.getLineById(shipmentLineId, I_M_InOutLine.class);

		return ProductId.ofRepoId(shipmentLineRecord.getM_Product_ID());
	}

	private List<InOutAndLineId> retrieveLinesToExport()
	{
		final IQueryFilter<I_M_InOut> queryFilter = getProcessInfo()
				.getQueryFilterOrElse(ConstantQueryFilter.of(false));

		final ImmutableList<InOutId> selectedShipments = Services.get(IQueryBL.class)
				.createQueryBuilder(I_M_InOut.class)
				.filter(queryFilter)
				.create()
				.listIds(InOutId::ofRepoId)
				.stream()
				.collect(ImmutableList.toImmutableList());

		List<InOutAndLineId> shipmentLinesToExport = shipmentLinesForCustomsInvoiceRepo.retrieveValidLinesToExport(selectedShipments);

		return shipmentLinesToExport;

	}

}
