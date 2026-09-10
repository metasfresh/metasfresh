package de.metas.ui.web.receiptSchedule;

import de.metas.handlingunits.model.I_M_ReceiptSchedule;
import de.metas.ui.web.handlingunits.HUEditorRowIsProcessedPredicate;
import de.metas.ui.web.handlingunits.HUEditorRowIsProcessedPredicates;
import de.metas.ui.web.handlingunits.HUEditorViewBuilder;
import de.metas.ui.web.handlingunits.HUEditorViewCustomizer;
import de.metas.ui.web.handlingunits.process.WEBUI_M_HU_Transform;
import lombok.NonNull;
import org.compiere.model.I_RV_ReceiptDisposition_DeliveryPlanning;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2017 metas GmbH
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
 * What the "HUs to receive" editor is, for EVERY window that launches it.
 * <p>
 * Keyed by REFERENCING TABLE NAME, which is how the editor varies by provenance
 * ({@code HUEditorViewFactoryTemplate#createView}), and instantiated once per launching window rather than
 * subclassed per window: the three answers below are properties of the receive editor itself, not of the window,
 * and a provenance with no customizer of its own silently gets read-only attributes
 * ({@code rowAttributesAlwaysReadonlyByReferencingTableName} defaults to {@code TRUE}) - i.e. an editor the
 * operator cannot edit in.
 */
final class HUsToReceiveHUEditorViewCustomizer implements HUEditorViewCustomizer
{
	/** Launched from the receipt-schedule window. */
	public static final transient HUsToReceiveHUEditorViewCustomizer forReceiptSchedule =
			new HUsToReceiveHUEditorViewCustomizer(I_M_ReceiptSchedule.Table_Name);

	/** Launched from the receipt-disposition delivery-planning window. */
	public static final transient HUsToReceiveHUEditorViewCustomizer forReceiptDispositionDeliveryPlanning =
			new HUsToReceiveHUEditorViewCustomizer(I_RV_ReceiptDisposition_DeliveryPlanning.Table_Name);

	private final String referencingTableNameToMatch;

	private HUsToReceiveHUEditorViewCustomizer(@NonNull final String referencingTableNameToMatch)
	{
		this.referencingTableNameToMatch = referencingTableNameToMatch;
	}

	@Override
	public String getReferencingTableNameToMatch()
	{
		return referencingTableNameToMatch;
	}

	@Override
	public HUEditorRowIsProcessedPredicate getHUEditorRowIsProcessedPredicate()
	{
		return HUEditorRowIsProcessedPredicates.IF_NOT_PLANNING_HUSTATUS;
	}

	@Override
	public Boolean isAttributesAlwaysReadonly()
	{
		return Boolean.FALSE;
	}

	@Override
	public void beforeCreate(final HUEditorViewBuilder viewBuilder)
	{
		viewBuilder.setParameter(WEBUI_M_HU_Transform.PARAM_CheckExistingHUsInsideView, true);
	}
}
