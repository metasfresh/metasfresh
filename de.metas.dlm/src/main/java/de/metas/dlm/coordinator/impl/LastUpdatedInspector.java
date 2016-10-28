package de.metas.dlm.coordinator.impl;

import java.sql.Timestamp;

import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.Check;
import org.adempiere.util.time.SystemTime;
import org.compiere.model.I_AD_Column;
import org.compiere.util.TimeUtil;

import com.google.common.base.Optional;

import de.metas.dlm.coordinator.IRecordInspector;
import de.metas.dlm.migrator.IMigratorService;

/*
 * #%L
 * metasfresh-dlm
 * %%
 * Copyright (C) 2016 metas GmbH
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
 * Checks when records with an <code>Updated</code> column where last updated. Recently updated records shall not be archived.
 * <p>
 * Note: in this class we use the constant {@link I_AD_Column#COLUMNNAME_Updated} for the <code>Updated</code> column name, which is a kind of arbitrary choise.
 *
 * @author metas-dev <dev@metasfresh.com>
 *
 */
public class LastUpdatedInspector implements IRecordInspector
{
	public static LastUpdatedInspector INSTANCE = new LastUpdatedInspector();

	private LastUpdatedInspector()
	{
	}

	@Override
	public int inspectRecord(Object model)
	{
		final Optional<Timestamp> updated = InterfaceWrapperHelper.getValue(model, I_AD_Column.COLUMNNAME_Updated);
		Check.errorUnless(updated.isPresent(), "model={} does not have an {}-value ", model, I_AD_Column.COLUMNNAME_Updated);

		final boolean modelIsOld = TimeUtil.addMonths(updated.get(), 1).after(SystemTime.asDate());

		return modelIsOld ? IMigratorService.DLM_Level_ARCHIVE : IMigratorService.DLM_Level_LIVE;
	}

	/**
	 * Returns <code>true</code> if the given model has an <code>Updated</code> column.
	 */
	@Override
	public boolean isApplicableFor(Object model)
	{
		return InterfaceWrapperHelper.hasModelColumnName(model, I_AD_Column.COLUMNNAME_Updated);
	}

}
