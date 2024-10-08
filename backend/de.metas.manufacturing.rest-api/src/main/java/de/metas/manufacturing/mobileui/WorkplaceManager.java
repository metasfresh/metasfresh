/*
 * #%L
 * de.metas.manufacturing.rest-api
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.manufacturing.mobileui;

import de.metas.workflow.rest_api.model.MobileApplicationId;
import de.metas.workflow.rest_api.service.MobileApplication;
import org.springframework.stereotype.Component;

@Component
public class WorkplaceManager implements MobileApplication
{
	public static final MobileApplicationId APPLICATION_ID = MobileApplicationId.ofString("workplaceManager");

	@Override
	public MobileApplicationId getApplicationId() {return APPLICATION_ID;}
}
