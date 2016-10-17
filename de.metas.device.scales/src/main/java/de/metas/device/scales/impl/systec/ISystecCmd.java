package de.metas.device.scales.impl.systec;

/*
 * #%L
 * de.metas.device.scales
 * %%
 * Copyright (C) 2015 metas GmbH
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

import java.util.LinkedHashMap;

import de.metas.device.scales.impl.ICmd;

/**
 * Base class for the commands that request a weight from a scale. Note that the Response message shall be the same for all different command subclasses.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public interface ISystecCmd extends ICmd
{

	LinkedHashMap<String, SystecResultStringElement> getResultElements();
}
