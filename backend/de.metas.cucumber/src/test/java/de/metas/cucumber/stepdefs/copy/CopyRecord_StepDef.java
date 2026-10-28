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

package de.metas.cucumber.stepdefs.copy;

import de.metas.copy_with_details.CopyRecordRequest;
import de.metas.copy_with_details.CopyRecordService;
import de.metas.cucumber.stepdefs.StepDefDataIdentifier;
import de.metas.cucumber.stepdefs.util.IdentifiersResolver;
import io.cucumber.java.en.When;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.PO;

/**
 * Generic record copy, usable for every table that has a StepDefData registered in {@link IdentifiersResolver}.
 */
public class CopyRecord_StepDef
{
	@NonNull private final IdentifiersResolver identifiersResolver;
	@NonNull private final CopyRecordService copyRecordService = SpringContextHolder.instance.getBean(CopyRecordService.class);

	public CopyRecord_StepDef(@NonNull final IdentifiersResolver identifiersResolver)
	{
		this.identifiersResolver = identifiersResolver;
	}

	@When("the record {string} is copied as {string}")
	public void copyRecord(@NonNull final String identifier, @NonNull final String copyIdentifier)
	{
		final TableRecordReference recordRef = identifiersResolver.getTableRecordReference(StepDefDataIdentifier.ofString(identifier));

		final PO copy = copyRecordService.copyRecord(CopyRecordRequest.builder()
				.tableRecordReference(recordRef)
				.build());

		identifiersResolver.putRecord(StepDefDataIdentifier.ofString(copyIdentifier), TableRecordReference.of(copy.get_TableName(), copy.get_ID()));
	}
}
