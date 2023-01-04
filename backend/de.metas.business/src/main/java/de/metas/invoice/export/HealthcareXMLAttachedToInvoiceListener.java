package de.metas.invoice.export;

import com.google.common.collect.ImmutableList;
import de.metas.attachments.AttachmentEntry;
import de.metas.attachments.AttachmentEntryService;
import de.metas.attachments.AttachmentTags;
import de.metas.attachments.listener.AttachmentListener;
import de.metas.attachments.listener.AttachmentListenerConstants.ListenerWorkStatus;
import de.metas.bpartner.composite.BPartnerComposite;
import de.metas.bpartner.composite.BPartnerLocation;
import de.metas.bpartner.composite.repository.BPartnerCompositeRepository;
import de.metas.bpartner.service.BPartnerQuery;
import de.metas.invoice_gateway.spi.InvoiceExportClientFactory;
import de.metas.javaclasses.model.I_AD_JavaClass;
import de.metas.logging.LogManager;
import de.metas.logging.TableRecordMDC;
import de.metas.organization.OrgId;
import de.metas.util.Check;
import de.metas.util.collections.CollectionUtils;
import de.metas.util.lang.ExternalId;
import de.metas.util.xml.XmlIntrospectionUtil;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.CrossVersionServiceRegistry;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.base.HealthcareCHHelper;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.commons.ForumDatenaustauschChConstants;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.CrossVersionRequestConverter;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.XmlRequest;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlBiller;
import de.metas.vertical.healthcare_ch.forum_datenaustausch_ch.invoice_xversion.request.model.payload.body.tiers.XmlPatient;
import lombok.NonNull;
import org.adempiere.util.lang.impl.TableRecordReference;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_C_Invoice;
import org.compiere.util.MimeType;
import org.slf4j.Logger;
import org.slf4j.MDC.MDCCloseable;

import java.io.ByteArrayInputStream;

import static de.metas.common.util.CoalesceUtil.coalesceSuppliers;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

/*
 * #%L
 * vertical-healthcare_ch.forum_datenaustausch_ch.invoice_base
 * %%
 * Copyright (C) 2020 metas GmbH
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
 * Note that we use {@link BPartnerComposite} which is why this class needs to be in here as opposed being way down in the healthcare-ch modules.
 * <p>
 * Important: when renaming this class, please make sure to also update its {@link I_AD_JavaClass} record.
 */
public class HealthcareXMLAttachedToInvoiceListener implements AttachmentListener
{

	private final CrossVersionServiceRegistry crossVersionServiceRegistry = SpringContextHolder.instance.getBean(CrossVersionServiceRegistry.class);

	private final AttachmentEntryService attachmentEntryService = SpringContextHolder.instance.getBean(AttachmentEntryService.class);

	private final BPartnerCompositeRepository bpartnerCompositeRepository = SpringContextHolder.instance.getBean(BPartnerCompositeRepository.class);

	private final HealthcareXMLToInvoiceDetailPersister healthcareXMLToInvoiceDetailPersister = SpringContextHolder.instance.getBean(HealthcareXMLToInvoiceDetailPersister.class);

	private static final Logger logger = LogManager.getLogger(HealthcareXMLAttachedToInvoiceListener.class);

	@Override
	public ListenerWorkStatus afterRecordLinked(
			@NonNull final AttachmentEntry attachmentEntry,
			@NonNull final TableRecordReference tableRecordReference)
	{
		try (final MDCCloseable ignored = TableRecordMDC.putTableRecordReference(tableRecordReference))
		{
			if (!I_C_Invoice.Table_Name.equals(tableRecordReference.getTableName()))
			{
				logger.debug("tableRecord reference has tableName={}; -> doing nothing", tableRecordReference.getTableName());
				return ListenerWorkStatus.NOT_APPLIED;
			}
			if (!MimeType.TYPE_XML.equals(attachmentEntry.getMimeType()))
			{
				logger.debug("given attachmentEntry with id={} (filename={}, contentType={}) is not an {}; -> doing nothing",
							 attachmentEntry.getId(), attachmentEntry.getFilename(), attachmentEntry.getMimeType(), MimeType.TYPE_XML);
				return ListenerWorkStatus.NOT_APPLIED;
			}
			final AttachmentTags tags = attachmentEntry.getTags();
			if (tags.hasTagSetToTrue(AttachmentTags.TAGNAME_IS_DOCUMENT))
			{
				logger.debug("given attachmentEntry with id={} (filename={}) is tagged as document (exported from original-xml + invoice). We need the original XML; -> doing nothing",
						attachmentEntry.getId(), attachmentEntry.getFilename());
				return ListenerWorkStatus.NOT_APPLIED;
			}
			if (!tags.hasTagSetToString(
					InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER,
					ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID))
			{
				logger.debug("given attachmentEntry with id={} (filename={}) is not tagged as {}={}; -> doing nothing",
						attachmentEntry.getId(), attachmentEntry.getFilename(), InvoiceExportClientFactory.ATTACHMENT_TAGNAME_EXPORT_PROVIDER, ForumDatenaustauschChConstants.INVOICE_EXPORT_PROVIDER_ID);
				return ListenerWorkStatus.NOT_APPLIED;
			}

			final I_C_Invoice invoiceRecord = tableRecordReference.getModel(I_C_Invoice.class);

			final byte[] attachmentData = attachmentEntryService.retrieveData(attachmentEntry.getId());
			final String xsdName = Check.assumeNotNull(
					coalesceSuppliers(
							() -> tags.getTagValueOrNull(ForumDatenaustauschChConstants.XSD_NAME),
							() -> XmlIntrospectionUtil.extractXsdValueOrNull(attachmentData)),
					"Failed to extract xsdName from tags={} or attachmentData={}", tags, attachmentData);
			final CrossVersionRequestConverter requestConverter = crossVersionServiceRegistry.getRequestConverterForXsdName(xsdName);

			final XmlRequest xRequest = requestConverter.toCrossVersionRequest(new ByteArrayInputStream(attachmentData));

			extractBeneficiaryToInvoice(xRequest, invoiceRecord, attachmentEntry);

			healthcareXMLToInvoiceDetailPersister.extractInvoiceDetails(xRequest, invoiceRecord);

			return ListenerWorkStatus.SUCCESS;
		}
	}

	private void extractBeneficiaryToInvoice(
			@NonNull final XmlRequest xRequest,
			@NonNull final I_C_Invoice invoiceRecord,
			@NonNull final AttachmentEntry attachmentEntry /*needed for logging*/)
	{
		final XmlPatient patient = xRequest.getPayload().getBody().getTiers().getPatient();
		final XmlBiller biller = xRequest.getPayload().getBody().getTiers().getBiller();
		final ExternalId externalId = HealthcareCHHelper.createBPartnerExternalIdForPatient(biller.getEanParty(), patient.getSsn());
		if (externalId == null)
		{
			logger.debug("patient-XML data extracted from attachmentEntry with id={} (filename={}) has no patient-SSN or biller-EAN; -> doing nothing",
					attachmentEntry.getId(), attachmentEntry.getFilename());
			return;
		}

		final BPartnerQuery bPartnerQuery = BPartnerQuery.builder().externalId(externalId)
				.onlyOrgId(OrgId.ANY)
				.onlyOrgId(OrgId.ofRepoId(invoiceRecord.getAD_Org_ID())) // make sure that we don't run into "stale" customers from other orgs that happen to have the same orgId.
				.build();
		final ImmutableList<BPartnerComposite> bpartners = bpartnerCompositeRepository.getByQuery(bPartnerQuery);
		if (bpartners.isEmpty())
		{
			logger.debug("externalId={} of the patient-XML data extracted from attachmentEntry with id={} (filename={}) has no matching C_BPartner; -> doing nothing",
					externalId.getValue(), attachmentEntry.getId(), attachmentEntry.getFilename());
			return;
		}
		
		final BPartnerComposite bPartner = CollectionUtils.singleElement(bpartners);

		if (invoiceRecord.getBeneficiary_BPartner_ID() > 0)
		{
			logger.debug("C_Invoice with ID={} already has a Beneficiary_BPartner_ID > 0; -> doing nothing",
					invoiceRecord.getC_Invoice_ID());
			return;
		}

		final int beneficiaryRepoId = bPartner.getBpartner().getId().getRepoId();
		invoiceRecord.setBeneficiary_BPartner_ID(beneficiaryRepoId);
		logger.debug("set Beneficiary_BPartner_ID={} from the patient-XML data's SSN={} extracted from attachmentEntry with id={} (filename={})",
				beneficiaryRepoId, patient.getSsn(), attachmentEntry.getId(), attachmentEntry.getFilename());

		final BPartnerLocation location = bPartner
				.extractShipToLocation()
				.orElseGet(() -> bPartner.extractBillToLocation().orElse(null));
		if (location != null)
		{
			invoiceRecord.setBeneficiary_Location_ID(location.getId().getRepoId());
			logger.debug("set setBeneficiary_Location_ID={} from the patient-XML data's SSN={} extracted from attachmentEntry with id={} (filename={})",
					location.getId().getRepoId(), patient.getSsn(), attachmentEntry.getId(), attachmentEntry.getFilename());
		}
		saveRecord(invoiceRecord);
	}

}
