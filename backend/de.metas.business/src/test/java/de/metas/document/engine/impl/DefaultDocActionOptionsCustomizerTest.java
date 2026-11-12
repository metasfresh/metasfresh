/*
 * #%L
 * de.metas.business
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

package de.metas.document.engine.impl;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocument;
import de.metas.lang.SOTrx;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.user.UserId;
import org.adempiere.ad.validationRule.impl.PlainValidationContext;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Pins the doc actions offered by {@link DefaultDocActionOptionsCustomizer} per document table and status.
 * On a completed document of a table that offers Reverse-Correct, Void is not offered (it would run reverse-correct anyway).
 */
public class DefaultDocActionOptionsCustomizerTest
{
	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	private static Set<String> optionsContext(final String tableName, final String docStatus, final boolean processing)
	{
		final DocActionOptionsContext optionsCtx = DocActionOptionsContext.builder()
				.userRolePermissionsKey(UserRolePermissionsKey.of(RoleId.SYSTEM, UserId.SYSTEM, ClientId.SYSTEM, LocalDate.parse("2026-11-11")))
				.tableName(tableName)
				.docStatus(docStatus)
				.processing(processing)
				.soTrx(SOTrx.SALES)
				.docActions(ImmutableSet.of())
				.validationContext(new PlainValidationContext())
				.build();

		DefaultDocActionOptionsCustomizer.instance.customizeValidActions(optionsCtx);
		return optionsCtx.getDocActions();
	}

	@ParameterizedTest
	@ValueSource(strings = { "M_InOut", "C_Invoice", "C_Payment", "C_AllocationHdr", "M_Movement", "M_Inventory", "PP_Cost_Collector" })
	public void completed_reverseCorrectTables_offerRC_noVoid(final String tableName)
	{
		final Set<String> actions = optionsContext(tableName, IDocument.STATUS_Completed, false);

		assertThat(actions).contains(IDocument.ACTION_Reverse_Correct);
		assertThat(actions).doesNotContain(IDocument.ACTION_Void);
		if ("M_InOut".equals(tableName))
		{
			assertThat(actions).contains(IDocument.ACTION_Close);
		}
	}

	@ParameterizedTest
	@ValueSource(strings = { "C_Order", "PP_Order", "DD_Order", "C_Cash", "C_BankStatement", "HR_Process" })
	public void completed_voidOnlyTables_keepVoid(final String tableName)
	{
		final Set<String> actions = optionsContext(tableName, IDocument.STATUS_Completed, false);

		assertThat(actions).contains(IDocument.ACTION_Void);
		assertThat(actions).doesNotContain(IDocument.ACTION_Reverse_Correct);
	}

	@ParameterizedTest
	@CsvSource({
			"M_InOut,DR", "M_InOut,IN", "M_InOut,IP", "M_InOut,AP", "M_InOut,NA", "M_InOut,WP", "M_InOut,WC",
			"C_Invoice,DR", "C_Invoice,IN", "C_Invoice,IP", "C_Invoice,AP", "C_Invoice,NA", "C_Invoice,WP", "C_Invoice,WC",
			"C_Payment,DR", "C_Payment,IN", "C_Payment,IP", "C_Payment,AP", "C_Payment,NA", "C_Payment,WP", "C_Payment,WC",
			"C_AllocationHdr,DR", "C_AllocationHdr,IN", "C_AllocationHdr,IP", "C_AllocationHdr,AP", "C_AllocationHdr,NA", "C_AllocationHdr,WP", "C_AllocationHdr,WC",
			"M_Movement,DR", "M_Movement,IN", "M_Movement,IP", "M_Movement,AP", "M_Movement,NA", "M_Movement,WP", "M_Movement,WC",
			"M_Inventory,DR", "M_Inventory,IN", "M_Inventory,IP", "M_Inventory,AP", "M_Inventory,NA", "M_Inventory,WP", "M_Inventory,WC",
			"PP_Cost_Collector,DR", "PP_Cost_Collector,IN", "PP_Cost_Collector,IP", "PP_Cost_Collector,AP", "PP_Cost_Collector,NA", "PP_Cost_Collector,WP", "PP_Cost_Collector,WC"
	})
	public void notCompleted_reverseCorrectTables_unchanged(final String tableName, final String docStatus)
	{
		final Set<String> actions = optionsContext(tableName, docStatus, false);

		assertThat(actions).contains(IDocument.ACTION_Void);
		assertThat(actions).doesNotContain(IDocument.ACTION_Reverse_Correct);
	}

	@Test
	public void completed_locked_invoice_offersUnlockAndRC_noVoid()
	{
		final Set<String> actions = optionsContext("C_Invoice", IDocument.STATUS_Completed, true);

		assertThat(actions).contains(IDocument.ACTION_Unlock, IDocument.ACTION_Reverse_Correct);
		assertThat(actions).doesNotContain(IDocument.ACTION_Void);
	}

	@Test
	public void completed_glJournal_unchanged()
	{
		final Set<String> actions = optionsContext("GL_Journal", IDocument.STATUS_Completed, false);

		assertThat(actions).containsExactlyInAnyOrder(
				IDocument.ACTION_ReActivate,
				IDocument.ACTION_Close,
				IDocument.ACTION_Reverse_Correct,
				IDocument.ACTION_Reverse_Accrual);
	}
}
