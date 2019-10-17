package de.metas.security.permissions.bpartner_hierarchy.handlers;

import java.util.stream.Stream;

import org.adempiere.util.lang.impl.TableRecordReference;

import de.metas.bpartner.BPartnerId;

/*
 * #%L
 * de.metas.business
 * %%
 * Copyright (C) 2019 metas GmbH
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

public interface BPartnerDependentDocumentHandler
{
	String getDocumentTableName();

	BPartnerDependentDocument extractBPartnerDependentDocumentFromDocumentObj(final Object documentObj);

	Stream<TableRecordReference> streamRelatedDocumentsByBPartnerId(BPartnerId bpartnerId);
}
