package de.metas.document.impl;

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

public class DocTypeDAO_getDocTypeIdsByDocSubType_Test
{
	private IDocTypeDAO docTypeDAO;

	@BeforeEach
	public void beforeEach()
	{
		AdempiereTestHelper.get().init();
		docTypeDAO = Services.get(IDocTypeDAO.class);
	}

	private DocTypeId docType(final DocSubType docSubType)
	{
		final I_C_DocType docType = newInstance(I_C_DocType.class);
		docType.setDocSubType(docSubType.getCode());
		saveRecord(docType);
		return DocTypeId.ofRepoId(docType.getC_DocType_ID());
	}

	/** Returns exactly the document types of the given sub type. */
	@Test
	void returnsOnlyTheDocTypesOfTheGivenSubType()
	{
		final DocTypeId prepay1 = docType(DocSubType.PrepayOrder);
		final DocTypeId prepay2 = docType(DocSubType.PrepayOrder);
		docType(DocSubType.StandardOrder);

		assertThat(docTypeDAO.getDocTypeIdsByDocSubType(DocSubType.PrepayOrder)).containsExactlyInAnyOrder(prepay1, prepay2);
		assertThat(docTypeDAO.getDocTypeIdsByDocSubType(DocSubType.Quotation)).isEmpty();
	}
}
