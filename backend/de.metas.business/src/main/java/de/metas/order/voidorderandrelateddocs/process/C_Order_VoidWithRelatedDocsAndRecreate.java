package de.metas.order.voidorderandrelateddocs.process;

import java.util.List;

import org.adempiere.util.lang.IPair;
import org.adempiere.util.lang.ITableRecordReference;
import org.adempiere.util.lang.ImmutablePair;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.Adempiere;
import org.compiere.model.I_C_Order;

import com.google.common.collect.ImmutableList;

import de.metas.document.engine.DocStatus;
import de.metas.document.engine.IDocumentBL;
import de.metas.order.OrderId;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsHandler.RecordsToHandleKey;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsRequest;
import de.metas.order.voidorderandrelateddocs.VoidOrderAndRelatedDocsService;
import de.metas.process.IProcessPrecondition;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.JavaProcess;
import de.metas.process.Param;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.util.Services;

/*
 * #%L
 * de.metas.business
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

public class C_Order_VoidWithRelatedDocsAndRecreate
		extends JavaProcess
		implements IProcessPrecondition
{
	private final IDocumentBL documentBL = Services.get(IDocumentBL.class);
	private final VoidOrderAndRelatedDocsService orderVoidedHandlerRegistry = Adempiere.getBean(VoidOrderAndRelatedDocsService.class);

	@Param(mandatory = true, parameterName = "VoidedOrderDocumentNoPrefix")
	private String p_VoidedOrderDocumentNoPrefix;

	@Override
	public ProcessPreconditionsResolution checkPreconditionsApplicable(IProcessPreconditionsContext context)
	{
		if (context.isNoSelection())
		{
			return ProcessPreconditionsResolution.rejectBecauseNoSelection();
		}
		if (context.isMoreThanOneSelected())
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection();
		}

		final I_C_Order orderRecord = context.getSelectedModel(I_C_Order.class);
		if (!orderRecord.isSOTrx())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("Currently this process is only implemented for sales orders");
		}

		final DocStatus docStatus = DocStatus.ofNullableCodeOrUnknown(orderRecord.getDocStatus());
		if (!docStatus.isCompleted())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("selected C_Order_ID=" + orderRecord.getC_Order_ID() + " is not completed");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_C_Order orderRecord = getRecord(I_C_Order.class);
		final VoidOrderAndRelatedDocsRequest request = createRequest(orderRecord);

		orderVoidedHandlerRegistry.invokeHandlers(request);

		return MSG_OK;
	}

	private VoidOrderAndRelatedDocsRequest createRequest(final I_C_Order orderRecord)
	{
		final OrderId requestOrderId = OrderId.ofRepoId(orderRecord.getC_Order_ID());

		final IPair<RecordsToHandleKey, List<ITableRecordReference>>  //
		requestRecordsToHandle = ImmutablePair.of(
				RecordsToHandleKey.of(I_C_Order.Table_Name),
				ImmutableList.of(TableRecordReference.of(orderRecord)));

		final VoidOrderAndRelatedDocsRequest request = VoidOrderAndRelatedDocsRequest
				.builder()
				.orderId(requestOrderId)
				.recordsToHandle(requestRecordsToHandle)
				.voidedOrderDocumentNoPrefix(p_VoidedOrderDocumentNoPrefix)
				.build();
		return request;
	}

}
