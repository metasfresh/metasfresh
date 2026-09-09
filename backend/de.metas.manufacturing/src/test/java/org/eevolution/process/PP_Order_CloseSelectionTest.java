/*
 * #%L
 * de.metas.manufacturing
 * %%
 * Copyright (C) 2026 metas GmbH
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

package org.eevolution.process;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocStatus;
import de.metas.i18n.AdMessageKey;
import de.metas.organization.OrgId;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.SelectionSize;
import lombok.NonNull;
import org.adempiere.ad.dao.IQueryFilter;
import org.adempiere.ad.element.api.AdTabId;
import org.adempiere.ad.element.api.AdWindowId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.util.Env;
import org.eevolution.model.I_PP_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Covers the precondition gate of {@link PP_Order_CloseSelection}: when the "close selection" action is
 * offered on a manufacturing-order selection.
 */
@ExtendWith(AdempiereTestWatcher.class)
class PP_Order_CloseSelectionTest
{
	private static final AdMessageKey MSG_NoCompletedOrderInSelection = AdMessageKey.of("org.eevolution.process.PP_Order_CloseSelection.NoCompletedOrderInSelection");

	private final ClientId clientId = ClientId.ofRepoId(1);
	private final OrgId orgId = OrgId.ofRepoId(0);

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		Env.setClientId(Env.getCtx(), clientId);
		Env.setOrgId(Env.getCtx(), orgId);
	}

	@Test
	void offered_whenTheSelectionHasACompletedOrder()
	{
		assertThat(checkPreconditions(ppOrder(DocStatus.Completed)).isRejected()).isFalse();
	}

	/** One closeable order is enough: the others are skipped by the selection query, not by this gate. */
	@Test
	void offered_whenOnlySomeOfTheSelectedOrdersAreCompleted()
	{
		assertThat(checkPreconditions(ppOrder(DocStatus.Closed), ppOrder(DocStatus.Completed), ppOrder(DocStatus.Drafted)).isRejected()).isFalse();
	}

	@Test
	void notOffered_whenEverySelectedOrderIsAlreadyClosed()
	{
		final ProcessPreconditionsResolution resolution = checkPreconditions(ppOrder(DocStatus.Closed), ppOrder(DocStatus.Closed));

		assertThat(resolution.isRejected()).isTrue();
		// the point of the gate: the user must be told WHY, so the reason has to be a translated message
		// that survives to the WebUI instead of being filtered out as internal
		assertThat(resolution.isInternal()).isFalse();
		// PlainMsgBL renders an un-parameterised AD_Message as its own key, so this pins the reason to the
		// AD_Message rather than to a hardcoded sentence
		assertThat(resolution.getRejectReason().getDefaultValue()).isEqualTo(MSG_NoCompletedOrderInSelection.toAD_Message());
	}

	@Test
	void notOffered_whenNoSelectedOrderIsCompleted()
	{
		assertThat(checkPreconditions(ppOrder(DocStatus.Drafted), ppOrder(DocStatus.InProgress)).isRejected()).isTrue();
	}

	@Test
	void notOffered_whenNothingIsSelected()
	{
		assertThat(checkPreconditions().isRejected()).isTrue();
	}

	private I_PP_Order ppOrder(@NonNull final DocStatus docStatus)
	{
		final I_PP_Order ppOrder = InterfaceWrapperHelper.newInstance(I_PP_Order.class);
		InterfaceWrapperHelper.setValue(ppOrder, I_PP_Order.COLUMNNAME_AD_Client_ID, clientId.getRepoId());
		ppOrder.setAD_Org_ID(orgId.getRepoId());
		ppOrder.setDocStatus(docStatus.getCode());
		InterfaceWrapperHelper.saveRecord(ppOrder);
		return ppOrder;
	}

	private ProcessPreconditionsResolution checkPreconditions(@NonNull final I_PP_Order... selectedOrders)
	{
		return new PP_Order_CloseSelection()
				.checkPreconditionsApplicable(new PreconditionsContext(Arrays.asList(selectedOrders)));
	}

	/** minimal stand-in for the WebUI's view context; only the selection is relevant to the gate */
	private static class PreconditionsContext implements IProcessPreconditionsContext
	{
		private final ImmutableList<I_PP_Order> selectedRecords;
		private final Set<Integer> selectedRecordIds;

		PreconditionsContext(@NonNull final List<I_PP_Order> selectedRecords)
		{
			this.selectedRecords = ImmutableList.copyOf(selectedRecords);
			this.selectedRecordIds = selectedRecords.stream().map(I_PP_Order::getPP_Order_ID).collect(ImmutableSet.toImmutableSet());
		}

		@Override
		public AdWindowId getAdWindowId() {return null;}

		@Override
		public AdTabId getAdTabId() {return null;}

		@Override
		public String getTableName() {return I_PP_Order.Table_Name;}

		@Override
		public <T> T getSelectedModel(final Class<T> modelClass) {throw new UnsupportedOperationException();}

		@Override
		public <T> List<T> getSelectedModels(final Class<T> modelClass) {throw new UnsupportedOperationException();}

		@NonNull
		@Override
		public <T> Stream<T> streamSelectedModels(@NonNull final Class<T> modelClass) {throw new UnsupportedOperationException();}

		@Override
		public int getSingleSelectedRecordId() {return selectedRecords.get(0).getPP_Order_ID();}

		@Override
		public SelectionSize getSelectionSize() {return SelectionSize.ofSize(selectedRecords.size());}

		/** the view's selection reaches the process as a filter, never as loaded models */
		@Override
		public <T> IQueryFilter<T> getQueryFilter(@NonNull final Class<T> recordClass)
		{
			return model -> selectedRecordIds.contains(InterfaceWrapperHelper.getId(model));
		}
	}
}
