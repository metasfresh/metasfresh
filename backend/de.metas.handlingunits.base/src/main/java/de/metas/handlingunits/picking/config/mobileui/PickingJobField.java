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

package de.metas.handlingunits.picking.config.mobileui;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.ITranslatableString;
import lombok.Builder;
import lombok.NonNull;
import lombok.Value;

import javax.annotation.Nullable;

@Value
@Builder
public class PickingJobField
{
	public static final ImmutableList<PickingJobField> DEFAULTS = ImmutableList.of(
			builder().seqNo(10).field(PickingJobFieldType.DOCUMENT_NO).isShowInDetailed(true).isShowInSummary(true).build(),
			builder().seqNo(20).field(PickingJobFieldType.CUSTOMER).isShowInDetailed(true).isShowInSummary(true).build(),
			builder().seqNo(30).field(PickingJobFieldType.PRODUCT_NAME).isShowInDetailed(true).isShowInSummary(true).build(),
			builder().seqNo(40).field(PickingJobFieldType.QTY_TO_DELIVER).isShowInDetailed(true).isShowInSummary(true).build()
	);
	
	@NonNull PickingJobFieldType field;
	int seqNo;
	boolean isShowInSummary;
	boolean isShowInDetailed;
	@Nullable String pattern;

	public ITranslatableString getCaption() {return field.getCaption();}
}
