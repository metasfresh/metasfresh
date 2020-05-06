/**
 *  See <a href="http://dewiki908/mediawiki/index.php/De.metas.handlingunit-Overview">http://dewiki908/mediawiki/index.php/De.metas.handlingunit-Overview</a>
 *
 *  Notes
 *  <li>that the paradigm of this modules is that business logic shall not persist anything to DB directly. This is because of performance. Instead changes shall be recorded and persisted e.g. using {@link de.metas.handlingunits.attribute.storage.IAttributeStorageListener} implementations.
 *  <li>that we want to use immutable stuff, like {@link de.metas.handlingunits.allocation.IAllocationRequest}.
 */
package de.metas.handlingunits;

/*
 * #%L
 * de.metas.handlingunits.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */
