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
import de.metas.vertical.healthcare.alberta.order.AlbertaOrderCompositeInfo;
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

	public void saveAlbertaOrderCompositeInfo(@NonNull final AlbertaOrderCompositeInfo albertaOrderCompositeInfo)
	{
		if (Check.isNotBlank(albertaOrderCompositeInfo.getTherapy()))
		{
			albertaOrderDAO.createAlbertaOrderTherapy(albertaOrderCompositeInfo.getOlCandId(),
													  albertaOrderCompositeInfo.getTherapy(),
													  albertaOrderCompositeInfo.getOrgId());
		}

		if (!Check.isEmpty(albertaOrderCompositeInfo.getTherapyTypes()))
		{
			albertaOrderCompositeInfo.getTherapyTypes()
					.forEach(therapyType -> albertaOrderDAO
							.createAlbertaOrderTherapyType(albertaOrderCompositeInfo.getOlCandId(),
														   therapyType,
														   albertaOrderCompositeInfo.getOrgId()));
		}

		albertaOrderDAO.createAlbertaOrder(albertaOrderCompositeInfo);
	}
}
