package de.metas.ui.web.doc_textlines.process;

import de.metas.doctextline.DocTextLineRepository;
import de.metas.process.IProcessPreconditionsContext;
import de.metas.process.ProcessPreconditionsResolution;
import de.metas.ui.web.doc_textlines.DocTextLinesViewFactory;
import de.metas.ui.web.view.IViewsRepository;
import de.metas.ui.web.window.model.lookup.LookupDataSourceFactory;
import de.metas.util.OptionalBoolean;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Order;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

/**
 * Pins the launcher's {@code checkPreconditionsApplicable} guard: only {@code CL} (Closed), {@code VO} (Voided)
 * and {@code RE} (Reversed) orders are refused -- every other {@link de.metas.document.engine.DocStatus} value
 * stays accepted. {@code rejectsClosedVoidedAndReversedOrders} and {@code acceptsEveryOtherOrderStatus} together
 * cover all twelve {@link de.metas.document.engine.DocStatus} codes, so a test that only checked the three
 * rejections would also pass an implementation that rejects everything.
 */
class WEBUI_Order_DocTextLines_LauncherTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		// the launcher's field initializers do eager SpringContextHolder.instance.getBean() lookups --
		// register exactly those two bean types so `new WEBUI_Order_DocTextLines_Launcher()` succeeds without a
		// full Spring context (same pattern as M_DeliveryPlanning_CreateAdditionalLinesClosedGuardTest)
		SpringContextHolder.registerJUnitBean(IViewsRepository.class, mock(IViewsRepository.class));
		SpringContextHolder.registerJUnitBean(DocTextLinesViewFactory.class,
				new DocTextLinesViewFactory(
						Mockito.mock(DocTextLineRepository.class),
						Mockito.mock(LookupDataSourceFactory.class)));
	}

	private static int order(final String docStatus)
	{
		final I_C_Order order = newInstance(I_C_Order.class);
		order.setDocStatus(docStatus);
		saveRecord(order);
		return order.getC_Order_ID();
	}

	/** {@code acceptIfSingleSelection()} is a default method: Mockito returns null for one unless told to run it. */
	private static IProcessPreconditionsContext contextWithSelection(final boolean noSelection, final boolean moreThanOne)
	{
		final IProcessPreconditionsContext context = mock(IProcessPreconditionsContext.class);
		when(context.acceptIfSingleSelection()).thenCallRealMethod();
		when(context.isNoSelection()).thenReturn(noSelection);
		when(context.isMoreThanOneSelected()).thenReturn(moreThanOne);
		return context;
	}

	private static IProcessPreconditionsContext contextSelecting(final int orderId)
	{
		final IProcessPreconditionsContext context = contextWithSelection(false, false);
		when(context.isExistingDocument()).thenReturn(OptionalBoolean.TRUE);
		when(context.getSingleSelectedRecordId()).thenReturn(orderId);
		return context;
	}

	@ParameterizedTest
	@ValueSource(strings = { "CL", "VO", "RE" })
	void rejectsClosedVoidedAndReversedOrders(final String docStatus)
	{
		final int orderId = order(docStatus);

		final ProcessPreconditionsResolution resolution =
				new WEBUI_Order_DocTextLines_Launcher().checkPreconditionsApplicable(contextSelecting(orderId));

		assertThat(resolution.isAccepted())
				.as("docStatus=%s must be rejected", docStatus)
				.isFalse();
	}

	@ParameterizedTest
	@ValueSource(strings = { "DR", "IP", "CO", "WP", "WC", "AP", "NA", "IN", "??" })
	void acceptsEveryOtherOrderStatus(final String docStatus)
	{
		final int orderId = order(docStatus);

		final ProcessPreconditionsResolution resolution =
				new WEBUI_Order_DocTextLines_Launcher().checkPreconditionsApplicable(contextSelecting(orderId));

		assertThat(resolution.isAccepted())
				.as("docStatus=%s must NOT be rejected -- only CL/VO/RE are", docStatus)
				.isTrue();
	}

	@Test
	void rejectsWhenMoreThanOneOrderIsSelected()
	{
		final IProcessPreconditionsContext context = contextWithSelection(false, true);

		final ProcessPreconditionsResolution resolution =
				new WEBUI_Order_DocTextLines_Launcher().checkPreconditionsApplicable(context);

		assertThat(resolution.isAccepted()).isFalse();
		assertThat(resolution.isInternal())
				.as("the reason must reach the user, not be swallowed as an internal one")
				.isFalse();
	}

	/** Asserting {@code isAccepted()} alone cannot tell the two selection shapes apart -- which is how one generic reason for both went unnoticed. */
	@Test
	void rejectsWhenNoOrderIsSelected_withADifferentReasonThanMoreThanOne()
	{
		final IProcessPreconditionsContext noSelection = contextWithSelection(true, false);
		final IProcessPreconditionsContext tooMany = contextWithSelection(false, true);

		final ProcessPreconditionsResolution noSelectionResolution =
				new WEBUI_Order_DocTextLines_Launcher().checkPreconditionsApplicable(noSelection);
		final ProcessPreconditionsResolution tooManyResolution =
				new WEBUI_Order_DocTextLines_Launcher().checkPreconditionsApplicable(tooMany);

		assertThat(noSelectionResolution.isAccepted()).isFalse();
		assertThat(noSelectionResolution.isInternal()).isFalse();
		assertThat(noSelectionResolution.getRejectReason().getDefaultValue())
				.isNotEqualTo(tooManyResolution.getRejectReason().getDefaultValue());
	}
}
