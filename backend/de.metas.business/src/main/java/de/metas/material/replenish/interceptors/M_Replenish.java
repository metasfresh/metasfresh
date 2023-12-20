/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2022 metas GmbH
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

package de.metas.material.replenish.interceptors;

import de.metas.common.util.CoalesceUtil;
import lombok.NonNull;
import org.adempiere.ad.modelvalidator.annotations.Interceptor;
import org.adempiere.ad.modelvalidator.annotations.ModelChange;
import org.compiere.model.I_M_Replenish;
import org.compiere.model.ModelValidator;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Interceptor(I_M_Replenish.class)
@Component
public class M_Replenish
{
	@ModelChange(timings = { ModelValidator.TYPE_BEFORE_NEW, ModelValidator.TYPE_BEFORE_CHANGE },
			ifColumnsChanged = {I_M_Replenish.COLUMNNAME_Level_Min, I_M_Replenish.COLUMNNAME_Level_Max}
	)
	public void ensureValidLevel_Max(@NonNull final I_M_Replenish m_replenish)
	{
		final BigDecimal levelMinQty = CoalesceUtil.coalesceNotNull(m_replenish.getLevel_Min(), BigDecimal.ZERO);
		final BigDecimal levelMaxQty = CoalesceUtil.coalesceNotNull(m_replenish.getLevel_Max(), BigDecimal.ZERO);

		m_replenish.setLevel_Max(levelMaxQty.max(levelMinQty));
	}
}
