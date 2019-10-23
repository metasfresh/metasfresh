package de.metas.security.permissions.record_access;

import org.compiere.model.X_AD_Role_Record_Access_Config;

import lombok.NonNull;
import lombok.Value;

/*
 * #%L
 * de.metas.adempiere.adempiere.base
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
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */


@Value(staticConstructor = "of")
public final class RecordAccessFeature
{
	public static final RecordAccessFeature BPARTNER_HIERARCHY = new RecordAccessFeature(X_AD_Role_Record_Access_Config.TYPE_BusinessPartnerHierarchy);
	public static final RecordAccessFeature MANUAL_TABLE = new RecordAccessFeature(X_AD_Role_Record_Access_Config.TYPE_Table);

	@NonNull
	String name;
}