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

package de.metas.cucumber.stepdefs.bpartner;

import de.metas.bpartner.BPartnerId;
import de.metas.cucumber.stepdefs.C_BPartner_Location_StepDefData;
import de.metas.cucumber.stepdefs.C_BPartner_StepDefData;
import de.metas.cucumber.stepdefs.DataTableRow;
import de.metas.cucumber.stepdefs.DataTableRows;
import de.metas.cucumber.stepdefs.StepDefConstants;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.Given;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.model.InterfaceWrapperHelper;
import org.compiere.model.I_C_BP_Relation;
import org.compiere.model.I_C_BPartner;

/**
 * Responsible for creating {@code C_BP_Relation} (business-partner relation) records.
 */
@RequiredArgsConstructor
public class C_BP_Relation_StepDef
{
	@NonNull private final C_BP_Relation_StepDefData bpRelationTable;
	@NonNull private final C_BPartner_StepDefData bpartnerTable;
	@NonNull private final C_BPartner_Location_StepDefData bpartnerLocationTable;

	/**
	 * Creates {@code C_BP_Relation} records linking a source partner to a target (relation) partner.
	 *
	 * @cucumber.stepdef Creates {@code C_BP_Relation} records.
	 * @cucumber.columns
	 *   <b>Identifier</b> — (optional) alias for cross-step reference<br>
	 *   <b>C_BPartner_ID</b> — (required, identifier-ref) the source business partner<br>
	 *   <b>C_BPartnerRelation_ID</b> — (required, identifier-ref) the related (target) business partner<br>
	 *   <b>C_BPartnerRelation_Location_ID</b> — (optional, identifier-ref) the location of the related partner<br>
	 *   <b>IsBillTo</b> — (optional, default false) whether the relation is used for bill-to<br>
	 *   <b>Name</b> — (optional) relation name; auto-generated from the partner IDs when omitted<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData, C_BPartner_Location_StepDefData
	 * @cucumber.example
	 * <pre>
	 * And metasfresh contains C_BP_Relations:
	 *   | Identifier  | C_BPartner_ID | C_BPartnerRelation_ID | IsBillTo |
	 *   | rel_billTo  | memberBP      | memberBillToBP        | Y        |
	 * </pre>
	 */
	@Given("metasfresh contains C_BP_Relations:")
	public void createBPRelations(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable)
				.setAdditionalRowIdentifierColumnName(I_C_BP_Relation.COLUMNNAME_C_BP_Relation_ID)
				.forEach(this::createBPRelation);
	}

	private void createBPRelation(@NonNull final DataTableRow row)
	{
		final BPartnerId sourceBPartnerId = row.getAsIdentifier(I_C_BPartner.COLUMNNAME_C_BPartner_ID).lookupNotNullIdIn(bpartnerTable);
		final BPartnerId relationBPartnerId = row.getAsIdentifier(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_ID).lookupNotNullIdIn(bpartnerTable);

		final I_C_BP_Relation bpRelation = InterfaceWrapperHelper.newInstance(I_C_BP_Relation.class);
		bpRelation.setAD_Org_ID(StepDefConstants.ORG_ID.getRepoId());
		bpRelation.setC_BPartner_ID(sourceBPartnerId.getRepoId());
		bpRelation.setC_BPartnerRelation_ID(relationBPartnerId.getRepoId());
		bpRelation.setName(row.getAsOptionalString(I_C_BP_Relation.COLUMNNAME_Name)
				.orElseGet(() -> "BP relation " + sourceBPartnerId.getRepoId() + "-" + relationBPartnerId.getRepoId()));
		bpRelation.setIsActive(true);

		row.getAsOptionalIdentifier(I_C_BP_Relation.COLUMNNAME_C_BPartnerRelation_Location_ID)
				.map(bpartnerLocationTable::get)
				.ifPresent(loc -> bpRelation.setC_BPartnerRelation_Location_ID(loc.getC_BPartner_Location_ID()));

		bpRelation.setIsBillTo(row.getAsOptionalBoolean(I_C_BP_Relation.COLUMNNAME_IsBillTo).orElseFalse());

		InterfaceWrapperHelper.saveRecord(bpRelation);

		row.getAsOptionalIdentifier().ifPresent(identifier -> bpRelationTable.putOrReplace(identifier, bpRelation));
	}
}
