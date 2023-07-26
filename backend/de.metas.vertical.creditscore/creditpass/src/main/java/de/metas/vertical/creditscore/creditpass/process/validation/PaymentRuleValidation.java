package de.metas.vertical.creditscore.creditpass.process.validation;

/*
 * #%L
 *  de.metas.vertical.creditscore.creditpass.process.validation
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

import com.google.common.collect.ImmutableSet;
import de.metas.bpartner.BPartnerId;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfig;
import de.metas.vertical.creditscore.creditpass.model.CreditPassConfigPaymentRule;
import de.metas.vertical.creditscore.creditpass.repository.CreditPassConfigRepository;
import org.adempiere.ad.validationRule.AbstractJavaValidationRule;
import org.adempiere.ad.validationRule.IValidationContext;
import org.apache.commons.lang3.StringUtils;
import org.compiere.Adempiere;
import org.compiere.model.I_C_BPartner;
import org.compiere.util.NamePair;

import java.util.Set;

public class PaymentRuleValidation extends AbstractJavaValidationRule
{
	private static final ImmutableSet<String> PARAMETERS = ImmutableSet.of(
			I_C_BPartner.COLUMNNAME_C_BPartner_ID);

	private final CreditPassConfigRepository creditPassConfigRepository = Adempiere.getBean(CreditPassConfigRepository.class);

	@Override public Set<String> getParameters()
	{
		return PARAMETERS;
	}

	@Override public boolean accept(IValidationContext evalCtx, NamePair item)
	{
		boolean accept = false;
		int bPartnerId = evalCtx.get_ValueAsInt(I_C_BPartner.COLUMNNAME_C_BPartner_ID, -1);

		if (bPartnerId > -1)
		{
			CreditPassConfig config = creditPassConfigRepository.getConfigByBPartnerId(BPartnerId.ofRepoId(bPartnerId));
			boolean hasMatch = config.getCreditPassConfigPaymentRuleList().stream()
					.map(CreditPassConfigPaymentRule::getPaymentRule)
					.anyMatch(pr -> StringUtils.equals(pr, item.getID()));
			if (hasMatch)
			{
				accept = true;
			}
		}

		return accept;
	}
}
