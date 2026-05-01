/*
 * #%L
 * de.metas.business
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

package de.metas.invoice.interceptor;

import org.adempiere.exceptions.AdempiereException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import de.metas.adempiere.model.I_C_Invoice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for {@link C_Invoice} interceptor — {@code IsPartialInvoice} defaulting (AC #11)
 * and readonly-after-Complete enforcement (AC #14).
 * <p>
 * Tests exercise the static helpers {@link C_Invoice#defaultIsPartialInvoiceFromDocType}
 * and {@link C_Invoice#assertIsPartialInvoiceEditableForDocStatus} directly to avoid Spring-context
 * wiring. The interceptor's {@code @ModelChange} methods forward to those helpers; the helpers
 * carry all the logic.
 */
class C_Invoice_IsPartialInvoiceTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	// -------------------------------------------------------------------------
	// AC #11 — IsPartialInvoice defaults from C_DocType.IsPartialInvoice
	// -------------------------------------------------------------------------

	/**
	 * When the doctype has {@code IsPartialInvoice='Y'} and the invoice has no value yet
	 * (represented as {@code false} from the unset boolean default), the interceptor must copy
	 * the doctype flag to the invoice.
	 * <p>
	 * AC #11: default-direction safety — a new invoice without an explicit flag gets Partial.
	 */
	@Test
	void newInvoice_defaultsFromDocType_Y()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setIsPartialInvoice(true);
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		// IsPartialInvoice intentionally NOT set on the invoice — simulates a brand-new record

		C_Invoice.defaultIsPartialInvoiceFromDocType(invoice);

		assertThat(invoice.isPartialInvoice()).isTrue();
	}

	/**
	 * When the doctype has {@code IsPartialInvoice='N'}, the interceptor must propagate that
	 * to the new invoice.
	 */
	@Test
	void newInvoice_defaultsFromDocType_N()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setIsPartialInvoice(false);
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());

		C_Invoice.defaultIsPartialInvoiceFromDocType(invoice);

		assertThat(invoice.isPartialInvoice()).isFalse();
	}

	/**
	 * When the caller already set {@code IsPartialInvoice} explicitly (simulated by calling the
	 * setter before invoking the helper), the helper must NOT overwrite it — even if the doctype
	 * says something different.
	 *
	 * <p>Real-life case: Cucumber step def sets {@code IsPartialInvoice=true} and then calls
	 * {@code invoiceDAO.save()} which triggers BEFORE_NEW. Without this guard the BEFORE_NEW
	 * interceptor would overwrite the flag with the doctype's value ("Eingangsrechnung" = false).
	 */
	@Test
	void newInvoice_doesNotOverride_whenExplicitlySet()
	{
		final I_C_DocType docType = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docType.setIsPartialInvoice(false); // doctype says "Final"
		InterfaceWrapperHelper.saveRecord(docType);

		final I_C_Invoice invoice = InterfaceWrapperHelper.newInstance(I_C_Invoice.class);
		invoice.setC_DocType_ID(docType.getC_DocType_ID());
		invoice.setIsPartialInvoice(true); // caller explicitly set to "Partial"

		C_Invoice.defaultIsPartialInvoiceFromDocType(invoice);

		// must remain true (caller wins, doctype is ignored)
		assertThat(invoice.isPartialInvoice()).isTrue();
	}

	// -------------------------------------------------------------------------
	// AC #14 — IsPartialInvoice is editable while DR/IP, readonly otherwise
	// -------------------------------------------------------------------------

	/**
	 * A drafted invoice must allow {@code IsPartialInvoice} to be changed — no exception.
	 */
	@Test
	void editFlag_whileDraft_allowed()
	{
		assertThatCode(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus("DR"))
				.doesNotThrowAnyException();
	}

	/**
	 * An in-progress invoice must also allow the flag to be changed — no exception.
	 */
	@Test
	void editFlag_whileInProgress_allowed()
	{
		assertThatCode(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus("IP"))
				.doesNotThrowAnyException();
	}

	/**
	 * A completed invoice must reject any change to {@code IsPartialInvoice} with an
	 * {@link AdempiereException} whose message references the field name.
	 * <p>
	 * AC #14: readonly after Complete is enforced server-side.
	 */
	@Test
	void editFlag_afterComplete_rejected()
	{
		assertThatThrownBy(() -> C_Invoice.assertIsPartialInvoiceEditableForDocStatus("CO"))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("IsPartialInvoice");
	}
}
