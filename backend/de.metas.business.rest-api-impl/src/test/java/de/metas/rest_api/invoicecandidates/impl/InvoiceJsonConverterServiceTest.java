package de.metas.rest_api.invoicecandidates.impl;

import com.google.common.collect.ImmutableList;
import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v2.JsonDocTypeInfo;
import de.metas.document.DocTypeId;
import de.metas.invoicecandidate.api.InvoiceCandidateMultiQuery;
import de.metas.invoicecandidate.api.InvoiceCandidateQuery;
import de.metas.order.impl.DocTypeService;
import de.metas.organization.OrgId;
import de.metas.rest_api.invoicecandidates.request.JsonInvoiceCandidateReference;
import de.metas.rest_api.utils.JsonExternalIds;
import de.metas.util.lang.ExternalHeaderIdWithExternalLineIds;
import de.metas.util.web.exception.InvalidEntityException;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_AD_Org;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2019 metas GmbH
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

public class InvoiceJsonConverterServiceTest
{
	private static final JsonExternalId EXTERNAL_HEADER_ID1 = JsonExternalId.of("HEADER_1");
	private static final JsonExternalId EXTERNAL_LINE_ID1 = JsonExternalId.of("LINE_1");

	private static final JsonExternalId EXTERNAL_HEADER_ID2 = JsonExternalId.of("HEADER_2");
	private static final JsonExternalId EXTERNAL_LINE_ID2 = JsonExternalId.of("LINE_2");

	private static final String ORG_CODE = "ORG_001";
	private static final OrgId ORG_ID = OrgId.ofRepoId(100);
	private static final String ORDER_DOC_NO = "ORDER_123";
	private static final DocTypeId DOC_TYPE_ID = DocTypeId.ofRepoId(200);
	private InvoiceJsonConverters invoiceJsonConverters;

	@BeforeEach
	void setUp()
	{
		AdempiereTestHelper.get().init();
		invoiceJsonConverters = new InvoiceJsonConverters(new DocTypeService());
	}

	@Test
	void fromJson_withExternalHeaderAndLineIds_shouldCreateQueryWithAllExternalIds()
	{
		// given
		final JsonInvoiceCandidateReference jic1 = JsonInvoiceCandidateReference.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.build();

		final JsonInvoiceCandidateReference jic2 = JsonInvoiceCandidateReference.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID2)
				.externalLineId(EXTERNAL_LINE_ID1)
				.externalLineId(EXTERNAL_LINE_ID2)
				.build();

		// when
		final InvoiceCandidateMultiQuery invoiceCandidateMultiQuery = invoiceJsonConverters.fromJson(ImmutableList.of(jic1, jic2));

		// then
		final List<InvoiceCandidateQuery> queries = invoiceCandidateMultiQuery.getQueries();
		assertThat(queries).hasSize(2);
		final InvoiceCandidateQuery firstInvoiceCandidateQuery = queries.get(0);
		assertThat(firstInvoiceCandidateQuery).isNotNull();
		final ExternalHeaderIdWithExternalLineIds firstHeaderAndLineIds = firstInvoiceCandidateQuery.getExternalIds();
		assertThat(firstHeaderAndLineIds).isNotNull();
		assertThat(firstHeaderAndLineIds.getExternalHeaderId()).isEqualTo(JsonExternalIds.toExternalId(EXTERNAL_HEADER_ID1));
		assertThat(firstHeaderAndLineIds.getExternalLineIds()).isEmpty();
		assertThat(firstInvoiceCandidateQuery.getOrgId()).isNull();
		assertThat(firstInvoiceCandidateQuery.getOrderDocTypeId()).isNull();
		assertThat(firstInvoiceCandidateQuery.getOrderDocumentNo()).isNull();
		assertThat(firstInvoiceCandidateQuery.getOrderLines()).isEmpty();

		final InvoiceCandidateQuery secondInvoiceCandidateQuery = queries.get(1);
		assertThat(secondInvoiceCandidateQuery).isNotNull();
		final ExternalHeaderIdWithExternalLineIds secondHeaderAndLineIds = secondInvoiceCandidateQuery.getExternalIds();
		assertThat(secondHeaderAndLineIds).isNotNull();
		assertThat(secondHeaderAndLineIds.getExternalHeaderId()).isEqualTo(JsonExternalIds.toExternalId(EXTERNAL_HEADER_ID2));
		assertThat(secondHeaderAndLineIds.getExternalLineIds()).containsExactly(JsonExternalIds.toExternalId(EXTERNAL_LINE_ID1), JsonExternalIds.toExternalId(EXTERNAL_LINE_ID2));
		assertThat(secondInvoiceCandidateQuery.getOrgId()).isNull();
		assertThat(secondInvoiceCandidateQuery.getOrderDocTypeId()).isNull();
		assertThat(secondInvoiceCandidateQuery.getOrderDocumentNo()).isNull();
		assertThat(secondInvoiceCandidateQuery.getOrderLines()).isEmpty();
	}

	@Test
	void fromJson_withOrgCode_shouldResolveOrgIdAndCreateQuery()
	{
		// given
		createAdOrg();
		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orgCode(ORG_CODE)
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.build();

		// when
		final InvoiceCandidateMultiQuery result = invoiceJsonConverters.fromJson(ImmutableList.of(candidate));

		// then
		assertThat(result.getQueries()).hasSize(1);
		final InvoiceCandidateQuery invoiceCandidateQuery = result.getQueries().get(0);
		assertThat(invoiceCandidateQuery).isNotNull();
		final ExternalHeaderIdWithExternalLineIds headerAndLineIds = invoiceCandidateQuery.getExternalIds();
		assertThat(headerAndLineIds).isNotNull();
		assertThat(headerAndLineIds.getExternalHeaderId()).isEqualTo(JsonExternalIds.toExternalId(EXTERNAL_HEADER_ID1));
		assertThat(headerAndLineIds.getExternalLineIds()).isEmpty();
		assertThat(invoiceCandidateQuery.getOrderDocTypeId()).isNull();
		assertThat(invoiceCandidateQuery.getOrderDocumentNo()).isNull();
		assertThat(invoiceCandidateQuery.getOrderLines()).isEmpty();
		assertThat(invoiceCandidateQuery.getOrgId()).isEqualTo(ORG_ID);
	}

	@Test
	void fromJson_withOrderDocumentNo_shouldCreateQueryWithOrderInfo()
	{
		// given
		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orderDocumentNo(ORDER_DOC_NO)
				.build();

		// when
		final InvoiceCandidateMultiQuery result = invoiceJsonConverters.fromJson(ImmutableList.of(candidate));

		// then
		assertThat(result.getQueries()).hasSize(1);
		final InvoiceCandidateQuery invoiceCandidateQuery = result.getQueries().get(0);
		assertThat(invoiceCandidateQuery).isNotNull();
		final ExternalHeaderIdWithExternalLineIds headerAndLineIds = invoiceCandidateQuery.getExternalIds();
		assertThat(headerAndLineIds).isNull();
		assertThat(invoiceCandidateQuery.getOrderDocTypeId()).isNull();
		assertThat(invoiceCandidateQuery.getOrderDocumentNo()).isEqualTo(ORDER_DOC_NO);
		assertThat(invoiceCandidateQuery.getOrderLines()).isEmpty();
		assertThat(invoiceCandidateQuery.getOrgId()).isNull();
	}

	@Test
	void fromJson_withOrderDocumentType_shouldResolveDocTypeIdAndCreateQuery()
	{
		// given
		final JsonDocTypeInfo docTypeInfo = JsonDocTypeInfo.builder()
				.docBaseType("SOO")
				.docSubType("ON")
				.build();

		createAdOrg();
		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(docTypeInfo.getDocBaseType());
		docTypeRecord.setDocSubType(docTypeInfo.getDocSubType());
		docTypeRecord.setC_DocType_ID(DocTypeId.toRepoId(DOC_TYPE_ID));
		docTypeRecord.setAD_Org_ID(OrgId.toRepoId(ORG_ID));
		InterfaceWrapperHelper.saveRecord(docTypeRecord);

		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orderDocumentType(docTypeInfo)
				.orgCode(ORG_CODE)
				.build();

		// when
		final InvoiceCandidateMultiQuery result = invoiceJsonConverters.fromJson(ImmutableList.of(candidate));

		// then
		assertThat(result.getQueries()).hasSize(1);
		final InvoiceCandidateQuery invoiceCandidateQuery = result.getQueries().get(0);
		assertThat(invoiceCandidateQuery).isNotNull();
		assertThat(invoiceCandidateQuery.getOrderDocTypeId()).isEqualTo(DOC_TYPE_ID);
		assertThat(invoiceCandidateQuery.getOrderLines()).isEmpty();
		assertThat(invoiceCandidateQuery.getOrgId()).isEqualTo(ORG_ID);
	}

	@Test
	void fromJson_withOrderLines_shouldCreateQueryWithOrderLines()
	{
		// given
		createAdOrg();
		final JsonDocTypeInfo docTypeInfo = JsonDocTypeInfo.builder()
				.docBaseType("SOO")
				.docSubType("ON")
				.build();

		final I_C_DocType docTypeRecord = InterfaceWrapperHelper.newInstance(I_C_DocType.class);
		docTypeRecord.setDocBaseType(docTypeInfo.getDocBaseType());
		docTypeRecord.setDocSubType(docTypeInfo.getDocSubType());
		docTypeRecord.setC_DocType_ID(DocTypeId.toRepoId(DOC_TYPE_ID));
		docTypeRecord.setAD_Org_ID(OrgId.toRepoId(ORG_ID));
		InterfaceWrapperHelper.saveRecord(docTypeRecord);

		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orderDocumentType(docTypeInfo)
				.orderDocumentNo(ORDER_DOC_NO)
				.orgCode(ORG_CODE)
				.orderLine(10)
				.build();

		// when
		final InvoiceCandidateMultiQuery result = invoiceJsonConverters.fromJson(ImmutableList.of(candidate));

		// then
		assertThat(result.getQueries()).hasSize(1);
		final InvoiceCandidateQuery invoiceCandidateQuery = result.getQueries().get(0);
		assertThat(invoiceCandidateQuery).isNotNull();
		assertThat(invoiceCandidateQuery.getOrderDocTypeId()).isEqualTo(DOC_TYPE_ID);
		assertThat(invoiceCandidateQuery.getOrderDocumentNo()).isEqualTo(ORDER_DOC_NO);
		final Set<Integer> orderLines = invoiceCandidateQuery.getOrderLines();
		assertThat(orderLines).isNotNull();
		assertThat(orderLines).hasSize(1);
		final Integer orderLine = orderLines.iterator().next();
		assertThat(orderLine).isNotNull();
		assertThat(orderLine).isEqualTo(10);
		assertThat(invoiceCandidateQuery.getOrgId()).isEqualTo(ORG_ID);
	}

	@Test
	void fromJson_withExternalLineIdsButNoHeaderId_shouldThrowInvalidEntityException()
	{
		// given
		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.externalLineId(EXTERNAL_LINE_ID1)
				.orderDocumentNo(ORDER_DOC_NO)
				.build();

		// when + then
		assertThatThrownBy(() -> invoiceJsonConverters.fromJson(ImmutableList.of(candidate)))
				.isInstanceOf(InvalidEntityException.class)
				.hasMessageContaining("externalLineIds were provided, but externalHeaderId is missing");
	}

	@Test
	void fromJson_withOrderLinesButNoDocumentNo_shouldThrowInvalidEntityException()
	{
		// given
		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orderLine(10)
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.build();

		// when + then
		assertThatThrownBy(() -> invoiceJsonConverters.fromJson(ImmutableList.of(candidate)))
				.isInstanceOf(InvalidEntityException.class)
				.hasMessageContaining("orderLines were provided, but orderDocumentNo is missing");
	}

	@Test
	void fromJson_withBothExternalHeaderAndOrderDocNo_shouldThrowInvalidEntityException()
	{
		// given
		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.externalHeaderId(EXTERNAL_HEADER_ID1)
				.orderDocumentNo(ORDER_DOC_NO)
				.build();

		// when + then
		assertThatThrownBy(() -> invoiceJsonConverters.fromJson(ImmutableList.of(candidate)))
				.isInstanceOf(InvalidEntityException.class)
				.hasMessageContaining("Both externalHeaderId and orderDocumentNo are provided. Only one reference type should be used");
	}

	@Test
	void fromJson_orderDocumentTypeWithoutOrgCode_shouldThrowInvalidEntityException()
	{
		// given
		final JsonDocTypeInfo docTypeInfo = JsonDocTypeInfo.builder()
				.docBaseType("SOO")
				.docSubType("ON")
				.build();

		final JsonInvoiceCandidateReference candidate = JsonInvoiceCandidateReference.builder()
				.orderDocumentType(docTypeInfo)
				.build();

		// when + then
		assertThatThrownBy(() -> invoiceJsonConverters.fromJson(ImmutableList.of(candidate)))
				.isInstanceOf(InvalidEntityException.class)
				.hasMessageContaining("When specifying Order Document Type, the org code also has to be specified");
	}

	private static void createAdOrg()
	{
		final I_AD_Org org = InterfaceWrapperHelper.newInstance(I_AD_Org.class);
		org.setValue(ORG_CODE);
		org.setAD_Org_ID(OrgId.toRepoId(ORG_ID));
		InterfaceWrapperHelper.saveRecord(org);
	}
}
