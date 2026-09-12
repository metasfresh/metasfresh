/*
 * #%L
 * metasfresh-webui-api
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

package de.metas.ui.web.receiptdisposition_deliveryplanning.process;

import com.google.common.collect.ImmutableList;
import de.metas.i18n.TranslatableStrings;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.process.RelatedProcessDescriptor.DisplayPlace;
import de.metas.ui.web.process.ProcessId;
import de.metas.ui.web.process.descriptor.WebuiRelatedProcessDescriptor;
import de.metas.ui.web.process.json.JSONDocumentAction;
import de.metas.ui.web.process.json.JSONDocumentActionsList;
import de.metas.ui.web.window.datatypes.json.JSONOptions;
import org.adempiere.test.AdempiereTestHelper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.function.Supplier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * What the WebUI does with the receipt-disposition delivery-planning window's reachability flags
 * (AD_Table_Process rows on AD_Table 542644) once a row's actual preconditions are known - pinned at the
 * PLATFORM seam ({@link WebuiRelatedProcessDescriptor} plus the two filters
 * {@code ViewRestController#getRowsQuickActions}/{@code #getRowsActions} apply).
 * <p>
 * What this test cannot see: it SUPPLIES the resolutions, so it says nothing about whether the platform ever
 * asks for them - that seam is pinned separately by {@link
 * ReceiptDispositionDeliveryPlanningPreconditionSeamTest}.
 * <p>
 * AC7b: where "HUs annehmen Voreinst."'s precondition rejects with an INTERNAL reason it must vanish from the
 * quick-actions array entirely, so the platform's comparator promotes "CUs annehmen" into the one-click slot.
 */
class ReceiptDispositionDeliveryPlanningQuickActionDefaultTest
{
	private static final String HUS_VOREINST = "HUs annehmen Voreinst.";
	private static final String HUS_CONFIG = "HUs annehmen";
	private static final String CUS = "CUs annehmen";
	private static final String CUS_WITH_PARAM = "CUs annehmen mit Menge";
	private static final String MULTI_ROW = "Wareneingangsdispo zu Wareneingang";

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
	}

	/** AD_Process 585658 - the only row with WEBUI_ViewQuickAction_Default='Y'. */
	private static WebuiRelatedProcessDescriptor huVoreinst(final Supplier<ProcessPreconditionsResolution> resolution)
	{
		return descriptor(585658, HUS_VOREINST, true, DisplayPlace.ViewQuickActions, DisplayPlace.ViewActionsMenu, resolution);
	}

	private static WebuiRelatedProcessDescriptor huConfig()
	{
		return descriptor(585659, HUS_CONFIG, false, DisplayPlace.ViewQuickActions, DisplayPlace.ViewActionsMenu, ProcessPreconditionsResolution::accept);
	}

	private static WebuiRelatedProcessDescriptor cus()
	{
		return descriptor(585660, CUS, false, DisplayPlace.ViewQuickActions, DisplayPlace.ViewActionsMenu, ProcessPreconditionsResolution::accept);
	}

	private static WebuiRelatedProcessDescriptor cusWithParam()
	{
		return descriptor(585661, CUS_WITH_PARAM, false, DisplayPlace.ViewQuickActions, DisplayPlace.ViewActionsMenu, ProcessPreconditionsResolution::accept);
	}

	/** AD_Process 585667 - the multi-row receive, WEBUI_ViewQuickAction='N' / WEBUI_ViewAction='Y': menu only. */
	private static WebuiRelatedProcessDescriptor multiRowReceive()
	{
		return descriptor(585667, MULTI_ROW, false, DisplayPlace.ViewActionsMenu, DisplayPlace.ViewActionsMenu, ProcessPreconditionsResolution::accept);
	}

	private static WebuiRelatedProcessDescriptor descriptor(
			final int adProcessId,
			final String caption,
			final boolean defaultQuickAction,
			final DisplayPlace firstDisplayPlace,
			final DisplayPlace secondDisplayPlace,
			final Supplier<ProcessPreconditionsResolution> resolution)
	{
		return WebuiRelatedProcessDescriptor.builder()
				.processId(ProcessId.ofAD_Process_ID(adProcessId))
				.processCaption(TranslatableStrings.parse(caption))
				.processDescription(TranslatableStrings.parse(caption))
				.displayPlace(firstDisplayPlace)
				.displayPlace(secondDisplayPlace)
				.defaultQuickAction(defaultQuickAction)
				.preconditionsResolutionSupplier(resolution)
				.build();
	}

	/** Reproduces {@code ViewRestController#getRowsQuickActions}'s two filters, in the same order. */
	private static List<JSONDocumentAction> quickActionsAsServedToTheFrontend(final List<WebuiRelatedProcessDescriptor> allFiveRows)
	{
		final ImmutableList<WebuiRelatedProcessDescriptor> filtered = allFiveRows.stream()
				.filter(descriptor -> descriptor.isDisplayedOn(DisplayPlace.ViewQuickActions))
				.filter(WebuiRelatedProcessDescriptor::isEnabledOrNotSilent)
				.collect(ImmutableList.toImmutableList());
		return JSONDocumentActionsList.ofList(filtered, JSONOptions.newInstance()).getActions();
	}

	/** Reproduces {@code ViewRestController#getRowsActions} (the action-menu request, non-{@code isAll} form). */
	private static List<JSONDocumentAction> actionMenuAsServedToTheFrontend(final List<WebuiRelatedProcessDescriptor> allFiveRows)
	{
		final ImmutableList<WebuiRelatedProcessDescriptor> filtered = allFiveRows.stream()
				.filter(descriptor -> descriptor.isDisplayedOn(DisplayPlace.ViewActionsMenu))
				.filter(WebuiRelatedProcessDescriptor::isEnabled)
				.collect(ImmutableList.toImmutableList());
		return JSONDocumentActionsList.ofList(filtered, JSONOptions.newInstance()).getActions();
	}

	private static ImmutableList<String> captions(final List<JSONDocumentAction> actions)
	{
		return actions.stream().map(JSONDocumentAction::getCaption).collect(ImmutableList.toImmutableList());
	}

	@Test
	@DisplayName("AC7a: a row WITH a resolvable packing instruction offers \"HUs annehmen Voreinst.\" as the one-click default")
	void rowWithPackingInstruction_defaultIsHUsVoreinst()
	{
		final List<WebuiRelatedProcessDescriptor> allFiveRows = ImmutableList.of(
				huVoreinst(ProcessPreconditionsResolution::accept), huConfig(), cus(), cusWithParam(), multiRowReceive());

		final List<JSONDocumentAction> quickActions = quickActionsAsServedToTheFrontend(allFiveRows);

		assertThat(captions(quickActions)).contains(HUS_VOREINST);
		assertThat(quickActions.get(0).getCaption())
				.as("actions[0] is what QuickActions.js#handleClick fires on a bare click")
				.isEqualTo(HUS_VOREINST);
		assertThat(quickActions.get(0).isDefaultQuickAction()).isTrue();
	}

	@Test
	@DisplayName("AC7b: a row WITHOUT a resolvable packing instruction still offers a one-click action - \"CUs annehmen\"")
	void rowWithoutPackingInstruction_fallbackIsCUs()
	{
		final List<WebuiRelatedProcessDescriptor> allFiveRows = ImmutableList.of(
				huVoreinst(() -> ProcessPreconditionsResolution.rejectWithInternalReason("no default LU/TU configuration")),
				huConfig(), cus(), cusWithParam(), multiRowReceive());

		final List<JSONDocumentAction> quickActions = quickActionsAsServedToTheFrontend(allFiveRows);

		assertThat(captions(quickActions))
				.as("the HU default genuinely hides itself - it must not appear even disabled")
				.doesNotContain(HUS_VOREINST)
				.containsExactlyInAnyOrder(HUS_CONFIG, CUS, CUS_WITH_PARAM);
		assertThat(quickActions.get(0).getCaption())
				.as("actions[0] is what QuickActions.js#handleClick fires on a bare click - the fallback default")
				.isEqualTo(CUS);
	}

	@Test
	@DisplayName("the multi-row receive is reachable from the action menu but never as a quick action")
	void multiRowReceive_isMenuOnly()
	{
		final List<WebuiRelatedProcessDescriptor> allFiveRows = ImmutableList.of(
				huVoreinst(ProcessPreconditionsResolution::accept), huConfig(), cus(), cusWithParam(), multiRowReceive());

		assertThat(captions(quickActionsAsServedToTheFrontend(allFiveRows)))
				.as("WEBUI_ViewQuickAction='N' on AD_Table_Process 541687")
				.doesNotContain(MULTI_ROW);
		assertThat(captions(actionMenuAsServedToTheFrontend(allFiveRows)))
				.as("WEBUI_ViewAction='Y' on AD_Table_Process 541687")
				.contains(MULTI_ROW);
	}

	/**
	 * MUTATION PROOF for AC7b: a NON-internal {@code reject(...)} leaves the disabled default IN the quick-actions
	 * array (merely sorted last), rather than making it vanish. "Genuinely hides itself" requires the INTERNAL
	 * form specifically.
	 */
	@Test
	@DisplayName("mutation check: a NON-internal rejection would leave \"HUs annehmen Voreinst.\" visible-but-disabled instead of hidden")
	void aNonInternalRejectionWouldLeaveTheDefaultVisibleButDisabled_MUTATION()
	{
		final List<WebuiRelatedProcessDescriptor> allFiveRowsWithTheWrongRejectionKind = ImmutableList.of(
				huVoreinst(() -> ProcessPreconditionsResolution.reject("no default LU/TU configuration")), // NOT rejectWithInternalReason
				huConfig(), cus(), cusWithParam(), multiRowReceive());

		final List<JSONDocumentAction> quickActions = quickActionsAsServedToTheFrontend(allFiveRowsWithTheWrongRejectionKind);

		assertThat(captions(quickActions))
				.as("a plain reject() is NOT silent - isEnabledOrNotSilent() keeps it in the array, merely disabled")
				.contains(HUS_VOREINST);

		final JSONDocumentAction huVoreinstAction = quickActions.stream()
				.filter(a -> a.getCaption().equals(HUS_VOREINST))
				.findFirst()
				.orElseThrow(() -> new AssertionError(HUS_VOREINST + " not found in " + quickActions));
		assertThat(huVoreinstAction.isDisabled()).isTrue();
		assertThat(quickActions.get(quickActions.size() - 1).getCaption())
				.as("a disabled action sorts LAST regardless of its default/quick-action flags")
				.isEqualTo(HUS_VOREINST);
	}
}
