package de.metas.dlm.coordinator.impl;

import java.sql.Timestamp;
import java.util.Optional;

import de.metas.common.util.time.SystemTime;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_AD_Column;
import org.compiere.util.TimeUtil;

import de.metas.dlm.coordinator.IRecordInspector;
import de.metas.dlm.migrator.IMigratorService;
import de.metas.util.Check;

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
 * Note: in this class we use the constant {@link I_AD_Column#COLUMNNAME_Updated} for the <code>Updated</code> column name, which is a kind of arbitrary choice.
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
	public int inspectRecord(final Object model)
	{
		final Timestamp value;
		final Optional<Timestamp> updated = InterfaceWrapperHelper.getValue(model, I_AD_Column.COLUMNNAME_Updated);
		if (updated.isPresent())
		{
			value = updated.get();
		}
		else
		{
			final Optional<Timestamp> created = InterfaceWrapperHelper.getValue(model, I_AD_Column.COLUMNNAME_Created);
			Check.errorUnless(created.isPresent(), "model={} does not have an {}-value ", model, I_AD_Column.COLUMNNAME_Created);
			value = created.get();
		}

		final boolean modelIsOld = TimeUtil.addMonths(value, 1).after(SystemTime.asDate());

		return modelIsOld ? IMigratorService.DLM_Level_ARCHIVE : IMigratorService.DLM_Level_LIVE;
	}

	/**
	 * Returns <code>true</code> if the given model has an <code>Updated</code> column.
	 */
	@Override
	public boolean isApplicableFor(final Object model)
	{
		return InterfaceWrapperHelper.hasModelColumnName(model, I_AD_Column.COLUMNNAME_Updated)
				|| InterfaceWrapperHelper.hasModelColumnName(model, I_AD_Column.COLUMNNAME_Created);
	}

}
