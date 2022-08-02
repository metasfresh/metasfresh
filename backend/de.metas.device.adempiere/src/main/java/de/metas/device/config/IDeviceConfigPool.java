package de.metas.device.config;

import org.adempiere.mm.attributes.AttributeCode;

import java.util.List;
import java.util.Set;

/*
 * #%L
 * de.metas.device.adempiere
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * {@link DeviceConfig} pool.
 * <p>
 * To get an instance, please use {@link DeviceConfigPoolFactory}.
 *
 * @author metas-dev <dev@metasfresh.com>
 */
public interface IDeviceConfigPool
{
	void addListener(IDeviceConfigPoolListener listener);

	Set<AttributeCode> getAllAttributeCodes();

	List<DeviceConfig> getDeviceConfigsForAttributeCode(AttributeCode attributeCode);
}
