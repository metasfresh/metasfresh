package de.metas.ui.web.handlingunits.process;

import org.adempiere.util.Check;
import org.adempiere.util.Services;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_M_InOut;

import de.metas.handlingunits.inout.IHUInOutBL;
import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.inoutcandidate.api.IReceiptScheduleBL;
import de.metas.process.JavaProcess;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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

/* package */ abstract class WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base extends JavaProcess
{
	// services
	private final transient IHUInOutBL huInOutBL = Services.get(IHUInOutBL.class);
	private final transient IReceiptScheduleBL receiptScheduleBL = Services.get(IReceiptScheduleBL.class);

	private final String _returnMovementType;
	private final int _targetWindowId;

	public WEBUI_M_ReceiptSchedule_CreateEmptiesReturns_Base(final String returnMovementType, int targetWindowId)
	{
		Check.assumeNotEmpty(returnMovementType, "returnMovementType is not empty");
		Check.assume(targetWindowId > 0, "targetWindowId > 0");

		_returnMovementType = returnMovementType;
		_targetWindowId = targetWindowId;

	}

	private String getReturnMovementType()
	{
		return _returnMovementType;
	}

	private int getTargetWindowId()
	{
		return _targetWindowId;
	}

	@Override
	protected String doIt() throws Exception
	{
		final I_M_ReceiptSchedule receiptSchedule = getRecord(I_M_ReceiptSchedule.class);

		final I_M_InOut inout = huInOutBL.createEmptiesInOutProducer(getCtx())
				.setC_BPartner(receiptScheduleBL.getC_BPartner_Effective(receiptSchedule))
				.setC_BPartner_Location(receiptScheduleBL.getC_BPartner_Location_Effective(receiptSchedule))
				.setMovementType(getReturnMovementType())
				.setM_Warehouse(receiptScheduleBL.getM_Warehouse_Effective(receiptSchedule))
				.setMovementDate(SystemTime.asDayTimestamp())
				.setC_Order(receiptSchedule.getC_Order())
				//
				.dontComplete()
				.create();

		final boolean gridView = false; // single document
		getResult().setRecordToOpen(TableRecordReference.of(inout), getTargetWindowId(), gridView);

		return MSG_OK;
	}
}
