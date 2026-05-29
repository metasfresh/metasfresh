package de.metas.acct.api.impl;

import de.metas.acct.AccountConceptualName;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_Fact_Acct;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class FactAcctDAOTest
{
	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();
	}

	@Nested
	class setAccountConceptualName_extractAccountConceptualName
	{
		I_Fact_Acct factAcct;

		@BeforeEach
		void beforeEach()
		{
			factAcct = InterfaceWrapperHelper.newInstance(I_Fact_Acct.class);
		}

		@Test
		void nullValue()
		{
			FactAcctDAO.setAccountConceptualName(factAcct, null);
			AccountConceptualName accountConceptualName = FactAcctDAO.extractAccountConceptualName(factAcct);
			assertThat(accountConceptualName).isNull();
			assertThat(factAcct.getAccountConceptualName()).isEqualTo("NOTSET");
		}

		@Test
		void notNullValue()
		{
			FactAcctDAO.setAccountConceptualName(factAcct, AccountConceptualName.ofString("ConceptualName"));
			AccountConceptualName accountConceptualName = FactAcctDAO.extractAccountConceptualName(factAcct);
			assertThat(accountConceptualName).isEqualTo(AccountConceptualName.ofString("ConceptualName"));
			assertThat(factAcct.getAccountConceptualName()).isEqualTo("ConceptualName");
		}

	}
}