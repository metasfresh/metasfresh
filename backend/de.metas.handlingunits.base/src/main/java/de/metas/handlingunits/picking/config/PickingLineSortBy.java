/*
 * #%L
 * de.metas.handlingunits.base
 * %%
 * Copyright (C) 2024 metas GmbH
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

package de.metas.handlingunits.picking.config;

import com.google.common.collect.ImmutableList;
import de.metas.handlingunits.picking.job.model.PickingJobLine;
import de.metas.util.lang.ReferenceListAwareEnum;
import de.metas.util.lang.ReferenceListAwareEnums;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import org.adempiere.exceptions.AdempiereException;

import javax.annotation.Nullable;
import java.util.Comparator;
import java.util.List;

import static org.compiere.model.X_MobileUI_UserProfile_Picking.PICKINGLINESORTBY_ORDER_LINE_SEQ_NO;
import static org.compiere.model.X_MobileUI_UserProfile_Picking.PICKINGLINESORTBY_QTY_TO_PICK_ASC;
import static org.compiere.model.X_MobileUI_UserProfile_Picking.PICKINGLINESORTBY_QTY_TO_PICK_DESC;

@AllArgsConstructor
@Getter
public enum PickingLineSortBy implements ReferenceListAwareEnum
{
	ORDER_LINE_SEQ_NO(PICKINGLINESORTBY_ORDER_LINE_SEQ_NO),
	QTY_TO_PICK_ASC(PICKINGLINESORTBY_QTY_TO_PICK_ASC),
	QTY_TO_PICK_DESC(PICKINGLINESORTBY_QTY_TO_PICK_DESC);

	private static final ReferenceListAwareEnums.ValuesIndex<PickingLineSortBy> index = ReferenceListAwareEnums.index(values());

	@NonNull
	public static PickingLineSortBy ofCode(@NonNull final String code)
	{
		return index.ofCode(code);
	}

	@Nullable
	public static PickingLineSortBy ofNullableCode(@Nullable final String code)
	{
		return index.ofNullableCode(code);
	}

	private final String code;

	@NonNull
	public List<PickingJobLine> sort(@NonNull final List<PickingJobLine> lines)
	{
		return lines.stream()
				.sorted(getComparator())
				.collect(ImmutableList.toImmutableList());
	}

	@NonNull
	private Comparator<PickingJobLine> getComparator()
	{
		switch (this)
		{
			case ORDER_LINE_SEQ_NO:
				return Comparator.comparing(PickingJobLine::getOrderLineSeqNo);
			case QTY_TO_PICK_ASC:
				return Comparator.comparing(PickingJobLine::getQtyToPick);
			case QTY_TO_PICK_DESC:
				return Comparator.comparing(PickingJobLine::getQtyToPick).reversed();
			default:
				throw new AdempiereException("Unsupported value! this =" + this);
		}
	}
}
