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

package de.metas.cucumber.stepdefs;

import de.metas.bpartner.BPartnerId;
import de.metas.i18n.TranslatableStrings;
import de.metas.inoutcandidate.model.I_M_Shipment_Constraint;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintCreateCommand;
import de.metas.inoutcandidate.shipmentconstraint.ShipmentConstraintService;
import de.metas.inoutcandidate.shipmentconstraint.SourceDocRef;
import de.metas.organization.OrgId;
import de.metas.util.Services;
import org.compiere.SpringContextHolder;
import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.adempiere.ad.dao.IQueryBL;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.assertj.core.api.SoftAssertions;
import org.compiere.model.I_C_BPartner;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Step definitions for M_Shipment_Constraint, the table behind the manual + dunning-sourced
 * delivery / order stop feature (gh#28631). Allows scenarios to:
 * <ul>
 *   <li>Seed a system-sourced (IsManual=N) constraint that mimics a dunning trigger;</li>
 *   <li>Assert that an active constraint exists for a given C_BPartner with the expected
 *       IsManual / IsDeliveryStop / DeliveryStopReason values;</li>
 *   <li>Assert no active constraint exists for a given C_BPartner.</li>
 * </ul>
 */
@RequiredArgsConstructor
public class M_Shipment_Constraint_StepDef
{
	@NonNull private final C_BPartner_StepDefData bPartnerTable;
	@NonNull private final M_Shipment_Constraint_StepDefData constraintTable;

	private final ShipmentConstraintService shipmentConstraintService = SpringContextHolder.instance.getBean(ShipmentConstraintService.class);
	private final IQueryBL queryBL = Services.get(IQueryBL.class);

	/**
	 * Seeds a system-sourced delivery-stop constraint (IsManual=N) for the given C_BPartner.
	 * This is the same code path that {@code DunningBL.makeDeliveryStopIfNeeded} takes: it
	 * goes through {@link ShipmentConstraintService#createConstraint}, which records the source
	 * document. We use the C_BPartner record itself as the source doc — for the cucumber test
	 * the actual document type is irrelevant; what matters is that IsManual stays N so the
	 * later {@code deactivateManualDeliveryStop} call (triggered by toggling
	 * C_BPartner.IsDeliveryStop=N) does NOT remove this constraint.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Identifier</b> — (optional) alias for cross-step reference<br>
	 *   <b>Bill_BPartner_ID</b> — (required, identifier-ref) partner to block<br>
	 *   <b>DeliveryStopReason</b> — (optional) free-text reason; default: "Dunning level 3 reached"<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData, M_Shipment_Constraint_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Given metasfresh contains M_Shipment_Constraint sourced from dunning:
	 *   | Identifier        | Bill_BPartner_ID | DeliveryStopReason       |
	 *   | dunningConstraint | bpartner_1       | Dunning level 3 reached  |
	 * </pre>
	 */
	@Given("metasfresh contains M_Shipment_Constraint sourced from dunning:")
	public void seedDunningSourcedConstraint(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(this::createDunningSourcedConstraint);
	}

	private void createDunningSourcedConstraint(@NonNull final DataTableRow row)
	{
		final StepDefDataIdentifier bpIdentifier = row.getAsIdentifier(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID);
		final I_C_BPartner bPartner = bPartnerTable.get(bpIdentifier);
		final String reason = row.getAsOptionalString(I_M_Shipment_Constraint.COLUMNNAME_DeliveryStopReason)
				.orElse("Dunning level 3 reached");

		shipmentConstraintService.createConstraint(ShipmentConstraintCreateCommand.builder()
				.orgId(OrgId.ofRepoId(bPartner.getAD_Org_ID()))
				.billBPartnerId(BPartnerId.ofRepoId(bPartner.getC_BPartner_ID()))
				.sourceDocRef(SourceDocRef.of(TableRecordReference.of(I_C_BPartner.Table_Name, bPartner.getC_BPartner_ID())))
				.deliveryStop(true)
				.reason(TranslatableStrings.constant(reason))
				.build());

		// retrieve the just-created constraint so the test can later assert against it
		final I_M_Shipment_Constraint constraint = queryBL.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, false)
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMN_IsDeliveryStop, true)
				.orderByDescending(I_M_Shipment_Constraint.COLUMNNAME_M_Shipment_Constraint_ID)
				.create()
				.first(I_M_Shipment_Constraint.class);

		row.getAsOptionalIdentifier()
				.ifPresent(identifier -> constraintTable.putOrReplace(identifier, constraint));
	}

	/**
	 * Asserts that an active M_Shipment_Constraint exists for the given C_BPartner with the
	 * expected IsManual / IsDeliveryStop / DeliveryStopReason values. The constraint is the
	 * single active row for the partner; if more than one active row matches the bpartner +
	 * IsManual filter the step fails.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Bill_BPartner_ID</b> — (required, identifier-ref) partner to look up<br>
	 *   <b>IsManual</b> — (required) Y for manual (BPartner-sourced), N for system (dunning)<br>
	 *   <b>IsDeliveryStop</b> — (optional) expected delivery-stop flag; default: Y<br>
	 *   <b>DeliveryStopReason</b> — (optional) expected reason text<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then validate M_Shipment_Constraint:
	 *   | Bill_BPartner_ID | IsManual | IsDeliveryStop | DeliveryStopReason   |
	 *   | bpartner_1       | Y        | Y              | Customer asked to wait |
	 * </pre>
	 */
	@Then("validate M_Shipment_Constraint:")
	public void validate_M_Shipment_Constraint(@NonNull final DataTable dataTable)
	{
		final SoftAssertions softly = new SoftAssertions();
		DataTableRows.of(dataTable).forEach(row -> validateConstraint(row, softly));
		softly.assertAll();
	}

	private void validateConstraint(@NonNull final DataTableRow row, @NonNull final SoftAssertions softly)
	{
		final StepDefDataIdentifier bpIdentifier = row.getAsIdentifier(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID);
		final I_C_BPartner bPartner = bPartnerTable.get(bpIdentifier);
		final boolean isManual = row.getAsBoolean(I_M_Shipment_Constraint.COLUMNNAME_IsManual);

		final I_M_Shipment_Constraint constraint = queryBL.createQueryBuilder(I_M_Shipment_Constraint.class)
				.addOnlyActiveRecordsFilter()
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
				.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, isManual)
				.create()
				.firstOnly(I_M_Shipment_Constraint.class);

		softly.assertThat(constraint)
				.as("Active M_Shipment_Constraint for Bill_BPartner_ID.Identifier=%s with IsManual=%s", bpIdentifier, isManual)
				.isNotNull();

		if (constraint == null)
		{
			return;
		}

		row.getAsOptionalBoolean(I_M_Shipment_Constraint.COLUMNNAME_IsDeliveryStop)
				.ifPresent(expected -> softly.assertThat(constraint.isDeliveryStop())
						.as("IsDeliveryStop for M_Shipment_Constraint of Bill_BPartner_ID.Identifier=%s", bpIdentifier)
						.isEqualTo(expected));

		row.getAsOptionalString(I_M_Shipment_Constraint.COLUMNNAME_DeliveryStopReason)
				.ifPresent(expected -> softly.assertThat(constraint.getDeliveryStopReason())
						.as("DeliveryStopReason for M_Shipment_Constraint of Bill_BPartner_ID.Identifier=%s", bpIdentifier)
						.isEqualTo(expected));
	}

	/**
	 * Asserts that there is no active manual M_Shipment_Constraint for the given C_BPartner.
	 * Used after toggling C_BPartner.IsDeliveryStop=N to confirm the manual constraint was
	 * deactivated.
	 *
	 * @cucumber.stepdef
	 * @cucumber.columns
	 *   <b>Bill_BPartner_ID</b> — (required, identifier-ref) partner to look up<br>
	 * @cucumber.depends StepDefData: C_BPartner_StepDefData
	 * @cucumber.example
	 * <pre>
	 * Then there is no active manual M_Shipment_Constraint:
	 *   | Bill_BPartner_ID |
	 *   | bpartner_1       |
	 * </pre>
	 */
	@And("there is no active manual M_Shipment_Constraint:")
	public void there_is_no_active_manual_M_Shipment_Constraint(@NonNull final DataTable dataTable)
	{
		DataTableRows.of(dataTable).forEach(row -> {
			final StepDefDataIdentifier bpIdentifier = row.getAsIdentifier(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID);
			final I_C_BPartner bPartner = bPartnerTable.get(bpIdentifier);

			final I_M_Shipment_Constraint constraint = queryBL.createQueryBuilder(I_M_Shipment_Constraint.class)
					.addOnlyActiveRecordsFilter()
					.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_Bill_BPartner_ID, bPartner.getC_BPartner_ID())
					.addEqualsFilter(I_M_Shipment_Constraint.COLUMNNAME_IsManual, true)
					.create()
					.firstOnly(I_M_Shipment_Constraint.class);

			InterfaceWrapperHelper.refresh(InterfaceWrapperHelper.create(bPartner, I_C_BPartner.class));

			assertThat(constraint)
					.as("No active manual M_Shipment_Constraint expected for Bill_BPartner_ID.Identifier=%s", bpIdentifier)
					.isNull();
		});
	}
}
