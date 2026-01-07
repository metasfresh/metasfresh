/*
 * #%L
 * de.metas.adempiere.adempiere.base
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.business_rule.descriptor.interceptor;

import de.metas.business_rule.descriptor.BusinessRuleRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_AD_BusinessRule_WarningTarget;
import org.springframework.stereotype.Component;



@Interceptor(I_AD_BusinessRule_WarningTarget.class)
@Component
@RequiredArgsConstructor
public class AD_BusinessRule_WarningTarget
{
	@NonNull private final BusinessRuleRepository ruleRepository;

	@ModelChange(types = { ModelChangeType.BEFORE_NEW, ModelChangeType.BEFORE_CHANGE })
	public void beforeSave(final I_AD_BusinessRule_WarningTarget record)
	{
		ruleRepository.validate(record);
	}
}
