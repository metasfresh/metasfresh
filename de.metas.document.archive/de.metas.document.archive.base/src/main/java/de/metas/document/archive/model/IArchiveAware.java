package de.metas.document.archive.model;

/*
 * #%L
 * de.metas.document.archive.base
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


/**
 * Interface can be used with {@link org.adempiere.model.InterfaceWrapperHelper} to acquire a {@link I_AD_Archive} instance from a model that has an AD_Archive_ID property.
 * <p>
 * Usage example:
 * 
 * <pre>
 * InterfaceWrapperHelper.create(someGridTabOrPO, IArchiveAware.class).getMArchive().getBinaryData();
 * </pre>
 * 
 */
public interface IArchiveAware
{
    final String COLUMNNAME_AD_Archive_ID = "AD_Archive_ID";
    int getAD_Archive_ID();
	I_AD_Archive getAD_Archive();
}
