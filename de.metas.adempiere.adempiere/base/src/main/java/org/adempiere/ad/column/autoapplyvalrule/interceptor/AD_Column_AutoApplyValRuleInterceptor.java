package org.adempiere.ad.column.autoapplyvalrule.interceptor;

import org.adempiere.ad.column.autoapplyvalrule.ValRuleAutoApplierService;
import org.adempiere.ad.modelvalidator.IModelInterceptor;
import org.adempiere.ad.modelvalidator.IModelValidationEngine;
import org.adempiere.ad.modelvalidator.ModelChangeType;
import org.compiere.model.I_AD_Client;

import lombok.NonNull;
import lombok.ToString;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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

/**
 * A model interceptor that can auto-apply validation rules to new records which
 * do not yet have a value set for the respective columns.
 */
@ToString(exclude = { "m_AD_Client_ID" })
public class AD_Column_AutoApplyValRuleInterceptor implements IModelInterceptor
{
	private int m_AD_Client_ID = -1;

	private final ValRuleAutoApplierService valRuleAutoApplierService;

	public AD_Column_AutoApplyValRuleInterceptor(@NonNull final ValRuleAutoApplierService valRuleAutoApplierService)
	{
		this.valRuleAutoApplierService = valRuleAutoApplierService;
	}

	@Override
	public void initialize(
			@NonNull final IModelValidationEngine engine,
			@NonNull final I_AD_Client client)
	{
		if (client != null)
		{
			m_AD_Client_ID = client.getAD_Client_ID();
		}
	}

	@Override
	public int getAD_Client_ID()
	{
		return m_AD_Client_ID;
	}

	@Override
	public void onModelChange(
			@NonNull final Object recordModel,
			@NonNull final ModelChangeType changeType)
	{
		if (!ModelChangeType.BEFORE_NEW.equals(changeType))
		{
			return;
		}
		valRuleAutoApplierService.invokeApplierFor(recordModel);
	}
}
