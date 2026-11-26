package de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.export.invoice;

import com.google.common.collect.ImmutableMap;
import de.metas.bpartner.BPartnerId;
import de.metas.invoice.InvoiceId;
import de.metas.invoice_gateway.spi.InvoiceExportClient.InvoiceNotExportableException;
import de.metas.invoice_gateway.spi.model.BPartner;
import de.metas.invoice_gateway.spi.model.GLN;
import de.metas.invoice_gateway.spi.model.InvoiceAttachment;
import de.metas.invoice_gateway.spi.model.MetasfreshVersion;
import de.metas.invoice_gateway.spi.model.Money;
import de.metas.invoice_gateway.spi.model.export.InvoiceToExport;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.config.ExportConfig;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlMode;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.XmlVersion;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import org.compiere.util.MimeType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.time.Instant;
import java.util.GregorianCalendar;

import static java.math.BigDecimal.TEN;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
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
 * Covers the export applicability gate {@link InvoiceExportClientImpl#applies(InvoiceToExport)}.
 *
 * <p>Context: municipality ("Gemeinde", doc subtype GM) invoices have no
 * GLN and must NOT be exported electronically, exactly like patient ("Eigenanteil", EA) invoices.
 * Before the fix, a GM invoice carrying a convertible forum-datenaustausch XML attachment but no
 * recipient GLN raised {@link InvoiceNotExportableException} ("receiver needs a GLN") and its export
 * work package retried forever. After the fix, {@code applies()} returns {@code false} for GM, so no
 * export is attempted, mirroring EA.
 */
class InvoiceExportClientImpl_appliesTest
{
	private InvoiceExportClientImpl invoiceExportClient;

	/** registry stubbed so that any XML attachment resolves to a (non-null) request converter,
	 * i.e. {@code extractConverters(..)} is non-empty and the gate proceeds past the converter check. */
	@BeforeEach
	void beforeEach()
	{
		final CrossVersionServiceRegistry registry = mock(CrossVersionServiceRegistry.class);
		when(registry.getRequestConverterForXsdName(anyString()))
				.thenReturn(mock(CrossVersionRequestConverter.class));

		final ExportConfig exportConfig = ExportConfig.builder()
				.exportXmlVersion(XmlVersion.v450)
				.mode(XmlMode.TEST)
				.build();

		invoiceExportClient = new InvoiceExportClientImpl(registry, exportConfig);
	}

	@Test
	void applies_returnsFalse_forGM_evenWithConvertibleAttachmentAndNoGln()
	{
		// the real prod case: GM invoice, has a forum-datenaustausch XML attachment, recipient has no GLN.
		// before the fix this threw InvoiceNotExportableException; after the fix GM is excluded like EA.
		final InvoiceToExport gmInvoice = invoiceToExport("GM", /*recipientGln*/ null);

		assertThat(invoiceExportClient.applies(gmInvoice)).isFalse();
	}

	@Test
	void applies_returnsFalse_forEA()
	{
		// regression guard: EA stays excluded (the pattern GM is being aligned to).
		final InvoiceToExport eaInvoice = invoiceToExport("EA", /*recipientGln*/ null);

		assertThat(invoiceExportClient.applies(eaInvoice)).isFalse();
	}

	@Test
	void applies_throws_forKV_withConvertibleAttachmentAndNoGln()
	{
		// scoping guard: the GM exclusion must NOT leak to KV/KT. A KV invoice without a recipient GLN
		// must still raise the GLN error (i.e. it is NOT silently skipped).
		final InvoiceToExport kvInvoice = invoiceToExport("KV", /*recipientGln*/ null);

		assertThatThrownBy(() -> invoiceExportClient.applies(kvInvoice))
				.isInstanceOf(InvoiceNotExportableException.class);
	}

	private static InvoiceToExport invoiceToExport(
			@Nullable final String docSubType,
			@Nullable final String recipientGln)
	{
		final BPartner recipient = BPartner.builder()
				.id(BPartnerId.ofRepoId(20))
				.gln(recipientGln != null ? GLN.of(recipientGln) : null)
				.build();

		final BPartner biller = BPartner.builder()
				.id(BPartnerId.ofRepoId(30))
				.gln(GLN.of("3234567890123"))
				.build();

		final InvoiceAttachment xmlAttachment = InvoiceAttachment.builder()
				.fileName("invoice.xml")
				.mimeType(MimeType.TYPE_XML)
				.data(new byte[]{ 0 })
				.tags(ImmutableMap.of(ForumDatenaustauschChConstants.XSD_NAME, "any-registered-xsd"))
				.build();

		return InvoiceToExport.builder()
				.id(InvoiceId.ofRepoId(10))
				.docSubType(docSubType)
				.metasfreshVersion(MetasfreshVersion.builder().major(10).minor(20).fullVersion("fullVersion").build())
				.recipient(recipient)
				.biller(biller)
				.amount(Money.of(TEN, "CHF"))
				.alreadyPaidAmount(Money.of(TEN, "CHF"))
				.invoiceTimestamp(Instant.now())
				.invoiceDate(new GregorianCalendar(2026, 10, 12))
				.documentNumber("GM_123456")
				.invoiceAttachment(xmlAttachment)
				.build();
	}
}
