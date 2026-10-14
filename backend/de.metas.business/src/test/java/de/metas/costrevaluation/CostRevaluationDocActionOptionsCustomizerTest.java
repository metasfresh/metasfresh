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

package de.metas.costrevaluation;

import com.google.common.collect.ImmutableSet;
import de.metas.document.engine.DocActionOptionsContext;
import de.metas.document.engine.IDocument;
import de.metas.lang.SOTrx;
import de.metas.security.RoleId;
import de.metas.security.UserRolePermissionsKey;
import de.metas.user.UserId;
import org.adempiere.ad.validationRule.IValidationContext;
import org.adempiere.service.ClientId;
import org.compiere.model.I_M_CostRevaluation;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class CostRevaluationDocActionOptionsCustomizerTest
{
	private static DocActionOptionsContext optionsContext(final String docStatus)
	{
		return DocActionOptionsContext.builder()
				.userRolePermissionsKey(UserRolePermissionsKey.of(RoleId.SYSTEM, UserId.SYSTEM, ClientId.SYSTEM, LocalDate.parse("2024-03-06")))
				.tableName(I_M_CostRevaluation.Table_Name)
				.docStatus(docStatus)
				.soTrx(SOTrx.PURCHASE)
				.docActions(ImmutableSet.of(IDocument.ACTION_ReActivate, IDocument.ACTION_Close))
				.validationContext(IValidationContext.DISABLED)
				.build();
	}

	@Test
	public void completed_offersVoid()
	{
		final DocActionOptionsContext optionsCtx = optionsContext(IDocument.STATUS_Completed);

		new CostRevaluationDocActionOptionsCustomizer().customizeValidActions(optionsCtx);

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_ReActivate, IDocument.ACTION_Close, IDocument.ACTION_Void);
	}

	@Test
	public void drafted_keepsTheDefaultActions()
	{
		final DocActionOptionsContext optionsCtx = optionsContext(IDocument.STATUS_Drafted);

		new CostRevaluationDocActionOptionsCustomizer().customizeValidActions(optionsCtx);

		assertThat(optionsCtx.getDocActions()).containsExactlyInAnyOrder(IDocument.ACTION_ReActivate, IDocument.ACTION_Close);
	}
}
