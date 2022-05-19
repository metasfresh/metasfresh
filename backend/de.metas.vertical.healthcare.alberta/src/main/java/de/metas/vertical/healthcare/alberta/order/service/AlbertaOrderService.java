/*
 * #%L
 * de.metas.vertical.healthcare.alberta
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.vertical.healthcare.alberta.order.service;

import de.metas.util.Check;
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderInfo;
import de.metas.vertical.healthcare.alberta.order.dao.AlbertaOrderDAO;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class AlbertaOrderService
{
	private final AlbertaOrderDAO albertaOrderDAO;

	public AlbertaOrderService(final AlbertaOrderDAO albertaOrderDAO)
	{
		this.albertaOrderDAO = albertaOrderDAO;
	}

	public void saveAlbertaOrderInfo(@NonNull final AlbertaOrderInfo albertaOrderInfo)
	{
		if (Check.isNotBlank(albertaOrderInfo.getTherapy()))
		{
			albertaOrderDAO.createAlbertaOrderTherapy(albertaOrderInfo.getOlCandId(),
													  albertaOrderInfo.getTherapy(),
													  albertaOrderInfo.getOrgId());
		}

		if (!Check.isEmpty(albertaOrderInfo.getTherapyTypes()))
		{
			albertaOrderInfo.getTherapyTypes()
					.forEach(therapyType -> albertaOrderDAO
							.createAlbertaOrderTherapyType(albertaOrderInfo.getOlCandId(),
														   therapyType,
														   albertaOrderInfo.getOrgId()));
		}

		albertaOrderDAO.createAlbertaOrder(albertaOrderInfo);
	}
}
