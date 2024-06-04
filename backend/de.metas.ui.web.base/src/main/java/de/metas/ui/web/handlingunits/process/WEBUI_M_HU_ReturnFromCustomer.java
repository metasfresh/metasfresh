package de.metas.ui.web.handlingunits.process;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.HuId;
import de.metas.handlingunits.inout.returns.ReturnsServiceFacade;
import de.metas.handlingunits.inout.returns.customer.MultiCustomerHUReturnsResult;
import de.metas.handlingunits.model.I_M_HU;
import de.metas.process.IProcessPrecondition;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.handlingunits.HUEditorProcessTemplate;
import de.metas.ui.web.handlingunits.HUEditorRowFilter.Select;
import de.metas.ui.web.handlingunits.WEBUI_HU_Constants;
import org.adempiere.exceptions.AdempiereException;
import org.compiere.SpringContextHolder;

import java.util.HashSet;

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

/**
 * Return the selected HUs back to customer.
 *
 * @author metas-dev <dev@metasfresh.com>
 * @implSpec <a href="https://github.com/metasfresh/metasfresh/issues/1306">issue</a>
 */
public class WEBUI_M_HU_ReturnFromCustomer extends HUEditorProcessTemplate implements IProcessPrecondition
{
	private final ReturnsServiceFacade returnsServiceFacade = SpringContextHolder.instance.getBean(ReturnsServiceFacade.class);

	private ImmutableList<I_M_HU> _selectedHUsToReturn = null;
	private MultiCustomerHUReturnsResult result;

	@Override
	protected ProcessPreconditionsResolution checkPreconditionsApplicable()
	{
		if (!isHUEditorView())
		{
			return ProcessPreconditionsResolution.rejectWithInternalReason("not the HU view");
		}

		if (!streamSelectedHUIds(Select.ONLY_TOPLEVEL).findAny().isPresent())
		{
			return ProcessPreconditionsResolution.reject(msgBL.getTranslatableMsgText(WEBUI_HU_Constants.MSG_WEBUI_ONLY_TOP_LEVEL_HU));
		}

		return ProcessPreconditionsResolution.accept();
	}

	@Override
	protected String doIt()
	{
		final ImmutableList<I_M_HU> husToReturn = getSelectedHUsToReturn();
		if (husToReturn.isEmpty())
		{
			throw new AdempiereException("@NoSelection@");
		}

		this.result = returnsServiceFacade.createCustomerReturnInOutForHUs(husToReturn);

		return MSG_OK;
	}

	private ImmutableList<I_M_HU> getSelectedHUsToReturn()
	{
		ImmutableList<I_M_HU> selectedHUsToReturn = this._selectedHUsToReturn;
		if (selectedHUsToReturn == null)
		{
			selectedHUsToReturn = this._selectedHUsToReturn = streamSelectedHUs(Select.ONLY_TOPLEVEL).collect(ImmutableList.toImmutableList());
		}
		return selectedHUsToReturn;
	}

	@Override
	protected void postProcess(final boolean success)
	{
		if (!success)
		{
			return;
		}

		final HashSet<HuId> huIdsToRefresh = new HashSet<>();
		getSelectedHUsToReturn().stream()
				.map(hu -> HuId.ofRepoId(hu.getM_HU_ID()))
				.forEach(huIdsToRefresh::add);

		if (result != null)
		{
			huIdsToRefresh.addAll(result.getReturnedHUIds());
		}

		addHUIdsAndInvalidateView(huIdsToRefresh);
	}
}
