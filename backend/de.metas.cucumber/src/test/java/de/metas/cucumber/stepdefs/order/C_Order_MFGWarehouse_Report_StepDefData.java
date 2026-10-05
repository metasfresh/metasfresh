/*
 * #%L
 * de.metas.cucumber
 * %%
 * Copyright (C) 2026 metas GmbH
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

package de.metas.cucumber.stepdefs.order;

import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import lombok.NonNull;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Stores the repo-ID of a generated {@code C_Order_MFGWarehouse_Report} row ("Bestellkontrolle") by
 * identifier, so a later step can reference that row as a print target through
 * {@code IdentifiersResolver} -- the same generic {@code TableRecordReference} resolution the "The jasper
 * process is run" / "AD_Archive exists" / "PDF archived" steps already use for every other document type.
 * <p>
 * Deliberately NOT typed on {@code I_C_Order_MFGWarehouse_Report}: that model class lives in
 * {@code de.metas.fresh.base}, and {@code de.metas.cucumber} does not, and should not, depend on it (see
 * {@link C_Order_StepDef#generateOrderCheckupReports}). The row is looked up via a scoped raw SQL query
 * instead (same table/column names as string literals, mirroring the AD_Process-by-Value convention used
 * for the process invocation itself), so this class only ever needs the ID as a plain {@code int}.
 */
public class C_Order_MFGWarehouse_Report_StepDefData
{
	/** The table's actual DB name, kept as a literal for the same reason: no fresh.base model reference. */
	public static final String TABLE_NAME = "C_Order_MFGWarehouse_Report";

	private final Map<StepDefDataIdentifier, Integer> idsByIdentifier = new ConcurrentHashMap<>();

	public void put(@NonNull final StepDefDataIdentifier identifier, final int recordId)
	{
		idsByIdentifier.put(identifier, recordId);
	}

	public Optional<Integer> getIdOptional(@NonNull final StepDefDataIdentifier identifier)
	{
		return Optional.ofNullable(idsByIdentifier.get(identifier));
	}
}
