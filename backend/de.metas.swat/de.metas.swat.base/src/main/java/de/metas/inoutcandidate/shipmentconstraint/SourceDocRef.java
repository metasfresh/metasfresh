/*
 * #%L
 * de.metas.swat.base
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

package de.metas.inoutcandidate.shipmentconstraint;

import lombok.NonNull;
import lombok.Value;
import org.adempiere.util.lang.impl.TableRecordReference;

/**
 * The {@code SourceDoc_Table_ID} / {@code SourceDoc_Record_ID} pair on
 * {@code M_Shipment_Constraint} — typed wrapper so callers no longer pass raw ints.
 */
@Value(staticConstructor = "of")
public class SourceDocRef
{
	@NonNull TableRecordReference ref;

	public int getAD_Table_ID()
	{
		return ref.getAD_Table_ID();
	}

	public int getRecord_ID()
	{
		return ref.getRecord_ID();
	}
}
