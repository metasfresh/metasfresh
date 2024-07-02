package de.metas.banking.payment.impl;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.save;
import static org.assertj.core.api.Assertions.assertThat;

import javax.annotation.Nullable;

import org.adempiere.test.AdempiereTestHelper;
import org.adempiere.test.AdempiereTestWatcher;
import org.compiere.model.I_C_PaySelection;
import org.compiere.model.I_C_PaySelectionLine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.google.common.collect.ImmutableSet;

import de.metas.banking.PaySelectionId;
import de.metas.banking.payment.IPaySelectionDAO;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.banking.base
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

@ExtendWith(AdempiereTestWatcher.class)
public class PaySelectionBLTest
{
	private PaySelectionBL paySelectionBL;
	private IPaySelectionDAO paySelectionDAO = Services.get(IPaySelectionDAO.class);

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		paySelectionBL = new PaySelectionBL();
	}

	@Nested
	public class updatePaySelectionReconciledStatus
	{
		private PaySelectionId createPaySelection()
		{
			final I_C_PaySelection paySelection = newInstance(I_C_PaySelection.class);
			save(paySelection);
			return PaySelectionId.ofRepoId(paySelection.getC_PaySelection_ID());
		}

		private I_C_PaySelectionLine createLine(@NonNull final PaySelectionId paySelectionId, @Nullable final BankStatementId bankStatementId)
		{
			final I_C_PaySelectionLine paySelectionLine = newInstance(I_C_PaySelectionLine.class);
			paySelectionLine.setC_PaySelection_ID(paySelectionId.getRepoId());
			paySelectionLine.setC_BankStatement_ID(bankStatementId != null ? bankStatementId.getRepoId() : -1);
			save(paySelectionLine);
			return paySelectionLine;
		}

		@Nested
		public class onePaySelection
		{
			private PaySelectionId paySelectionId;

			@BeforeEach
			public void beforeEach()
			{
				paySelectionId = createPaySelection();
			}

			private void updatePaySelectionReconciledStatus()
			{
				paySelectionBL.updatePaySelectionReconciledStatus(ImmutableSet.of(paySelectionId));
			}

			@Test
			public void noLines()
			{
				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
				assertThat(paySelection.isReconciled()).isTrue();
			}

			@Test
			public void oneReconciliedLine()
			{
				final BankStatementId someBankStatementId = BankStatementId.ofRepoId(1);

				final I_C_PaySelectionLine paySelectionLine = createLine(paySelectionId, someBankStatementId);
				assertThat(PaySelectionBL.isReconciled(paySelectionLine)).isTrue();

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
				assertThat(paySelection.isReconciled()).isTrue();
			}

			@Test
			public void oneNotReconciliedLine()
			{
				final I_C_PaySelectionLine paySelectionLine = createLine(paySelectionId, null);
				assertThat(PaySelectionBL.isReconciled(paySelectionLine)).isFalse();

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
				assertThat(paySelection.isReconciled()).isFalse();
			}

			@Test
			public void oneReconciliedLine_and_oneNotReconciledLine()
			{
				final BankStatementId someBankStatementId = BankStatementId.ofRepoId(1);

				final I_C_PaySelectionLine paySelectionLine1 = createLine(paySelectionId, someBankStatementId);
				assertThat(PaySelectionBL.isReconciled(paySelectionLine1)).isTrue();
				final I_C_PaySelectionLine paySelectionLine2 = createLine(paySelectionId, null);
				assertThat(PaySelectionBL.isReconciled(paySelectionLine2)).isFalse();

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection = paySelectionDAO.getById(paySelectionId).get();
				assertThat(paySelection.isReconciled()).isFalse();
			}
		}

		@Nested
		public class twoPaySelections
		{
			private PaySelectionId paySelectionId1;
			private PaySelectionId paySelectionId2;

			private void updatePaySelectionReconciledStatus()
			{
				paySelectionBL.updatePaySelectionReconciledStatus(ImmutableSet.of(paySelectionId1, paySelectionId2));
			}

			@BeforeEach
			public void beforeEach()
			{
				paySelectionId1 = createPaySelection();
				paySelectionId2 = createPaySelection();
			}

			@Test
			public void bothNotReconciled()
			{
				createLine(paySelectionId1, null);
				createLine(paySelectionId2, null);

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection1 = paySelectionDAO.getById(paySelectionId1).get();
				final I_C_PaySelection paySelection2 = paySelectionDAO.getById(paySelectionId2).get();
				assertThat(paySelection1.isReconciled()).isFalse();
				assertThat(paySelection2.isReconciled()).isFalse();
			}

			@Test
			public void bothReconciled()
			{
				final BankStatementId someBankStatementId = BankStatementId.ofRepoId(1);
				createLine(paySelectionId1, someBankStatementId);
				createLine(paySelectionId2, someBankStatementId);

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection1 = paySelectionDAO.getById(paySelectionId1).get();
				final I_C_PaySelection paySelection2 = paySelectionDAO.getById(paySelectionId2).get();
				assertThat(paySelection1.isReconciled()).isTrue();
				assertThat(paySelection2.isReconciled()).isTrue();
			}

			@Test
			public void oneNotReconciled_oneReconciled()
			{
				final BankStatementId someBankStatementId = BankStatementId.ofRepoId(1);
				createLine(paySelectionId1, null);
				createLine(paySelectionId2, someBankStatementId);

				updatePaySelectionReconciledStatus();

				final I_C_PaySelection paySelection1 = paySelectionDAO.getById(paySelectionId1).get();
				final I_C_PaySelection paySelection2 = paySelectionDAO.getById(paySelectionId2).get();
				assertThat(paySelection1.isReconciled()).isFalse();
				assertThat(paySelection2.isReconciled()).isTrue();
			}
		}
	}
}
