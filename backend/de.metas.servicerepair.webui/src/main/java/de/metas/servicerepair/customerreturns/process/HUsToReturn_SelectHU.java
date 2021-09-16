/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2020 metas GmbH
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

package de.metas.servicerepair.customerreturns.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.inout.InOutId;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.servicerepair.customerreturns.HUsToReturnViewContext;
import de.metas.servicerepair.customerreturns.RepairCustomerReturnsService;
import de.metas.ui.web.handlingunits.HUEditorRow;
import org.compiere.SpringContextHolder;

public class HUsToReturn_SelectHU extends HUsToReturnViewBasedProcess implements IProcessPrecondition
{
	private final RepairCustomerReturnsService repairCustomerReturnsService = SpringContextHolder.instance.getBean(RepairCustomerReturnsService.class);

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		final HUEditorRow row = getSingleSelectedRowOrNull();
		if (row == null)
		{
			return ProcessPreconditionsResolution.rejectBecauseNotSingleSelection().toInternal();
		}

		if (!row.isTopLevel())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not a top level HU");
		}
		final HUsToReturnViewContext viewContext = getHUsToReturnViewContext();

		final HuId huId = row.getHuId();
		final InOutId customerReturnsId = viewContext.getCustomerReturnsId();
		if (!isValidHuForInOut(customerReturnsId, huId))
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("already added");
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final HUEditorRow row = getSingleSelectedRow();
		final HUsToReturnViewContext viewContext = getHUsToReturnViewContext();

		final HuId huId = row.getHuId();
		final InOutId customerReturnsId = viewContext.getCustomerReturnsId();
		if (isValidHuForInOut(customerReturnsId, huId))
		{
			repairCustomerReturnsService.prepareCloneHUAndCreateCustomerReturnLine()
					.customerReturnId(customerReturnsId)
					.productId(row.getProductId())
					.qtyReturned(row.getQtyCUAsQuantity())
					.cloneFromHuId(huId)
					.build();
		}

		getResult().setCloseWebuiModalView(true);

		getView().removeHUIdsAndInvalidate(ImmutableList.of(huId));

		return MSG_OK;
	}

	private boolean isValidHuForInOut(final InOutId inOutId, final HuId huId)
	{
		return repairCustomerReturnsService.isValidHuForInOut(inOutId, huId);
	}

}
