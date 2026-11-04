package de.metas.document.impl;

import de.metas.document.DocBaseAndSubType;
import de.metas.document.DocBaseType;
import de.metas.document.DocSubType;
import de.metas.document.DocTypeId;
import de.metas.document.IDocTypeDAO;
import de.metas.util.Services;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_C_DocType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;
import static org.assertj.core.api.Assertions.assertThat;

public class DocTypeDAO_getDocTypeIdsByDocBaseAndSubType_Test
{
	private IDocTypeDAO docTypeDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		docTypeDAO = Services.get(IDocTypeDAO.class);
	}

	private DocTypeId docType(final DocBaseType docBaseType, final DocSubType docSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocBaseType(docBaseType.getCode());
		docType.setDocSubType(docSubType.getCode());
		saveRecord(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	/** Returns exactly the document types of the given base type and sub type. */
	@Test
	void returnsOnlyTheDocTypesOfTheGivenBaseAndSubType()
	{
		final DocTypeId salesPrepay1 = docType(DocBaseType.SalesOrder, DocSubType.PrepayOrder);
		final DocTypeId salesPrepay2 = docType(DocBaseType.SalesOrder, DocSubType.PrepayOrder);
		docType(DocBaseType.SalesOrder, DocSubType.StandardOrder);
		docType(DocBaseType.PurchaseOrder, DocSubType.PrepayOrder);

		assertThat(docTypeDAO.getDocTypeIdsByDocBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.PrepayOrder)))
				.containsExactlyInAnyOrder(salesPrepay1, salesPrepay2);
		assertThat(docTypeDAO.getDocTypeIdsByDocBaseAndSubType(DocBaseAndSubType.of(DocBaseType.SalesOrder, DocSubType.Quotation))).isEmpty();
	}
}
