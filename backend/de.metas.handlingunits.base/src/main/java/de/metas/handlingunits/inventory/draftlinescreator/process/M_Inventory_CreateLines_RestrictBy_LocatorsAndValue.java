package de.metas.handlingunits.inventory.draftlinescreator.process;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;

import org.compiere.SpringContextHolder;
import org.compiere.util.TimeUtil;

import de.metas.handlingunits.inventory.Inventory;
import de.metas.handlingunits.inventory.draftlinescreator.HUsForInventoryStrategies;
import de.metas.handlingunits.inventory.draftlinescreator.HuForInventoryLineFactory;
import de.metas.handlingunits.inventory.draftlinescreator.LeastRecentTransactionStrategy;
import de.metas.organization.IOrgDAO;
import de.metas.process.Param;
import de.metas.util.Services;
import lombok.NonNull;

/*
 * #%L
 * de.metas.handlingunits.base
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

public class M_Inventory_CreateLines_RestrictBy_LocatorsAndValue extends DraftInventoryBase
{
	private final HuForInventoryLineFactory huForInventoryLineFactory = SpringContextHolder.instance.getBean(HuForInventoryLineFactory.class);
	private final IOrgDAO orgDAO = Services.get(IOrgDAO.class);

	@Param(parameterName = "MinValueOfGoods")
	private BigDecimal minimumPrice;

	@Param(parameterName = "MaxNumberOfLocators")
	private int maxLocators;

	@Override
	protected LeastRecentTransactionStrategy createStrategy(@NonNull final Inventory inventory)
	{
		final ZoneId timeZone = orgDAO.getTimeZone(inventory.getOrgId());
		final LocalDate movementDate = TimeUtil.asLocalDate(inventory.getMovementDate(), timeZone);

		return HUsForInventoryStrategies.leastRecentTransaction()
				.maxLocators(maxLocators)
				.minimumPrice(minimumPrice)
				.movementDate(movementDate)
				.huForInventoryLineFactory(huForInventoryLineFactory)
				.build();
	}
}
