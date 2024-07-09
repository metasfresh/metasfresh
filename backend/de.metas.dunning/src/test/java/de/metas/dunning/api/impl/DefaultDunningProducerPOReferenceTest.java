/*
 * #%L
 * de.metas.dunning
 * %%
 * Copyright (C) 2023 metas GmbH
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

package de.metas.dunning.api.impl;

import de.metas.dunning.model.I_C_Dunning_Candidate;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.model.I_AD_SysConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

public class DefaultDunningProducerPOReferenceTest
{
	public static final String PO_REFERENCE = "ABC123";
	public static final String PREFIXED_PO_REFERENCE = "AA_ABC123";
	public static final String DOCUMENT_NO = PREFIXED_PO_REFERENCE + "-12345";
	public static final String DOCUMENT_NO_NOT_MATCHING_POREF = "XYZ" + "-12345";
	private static I_C_Dunning_Candidate candidate;
	private static I_AD_SysConfig sysConfig;

	@BeforeAll
	public static void staticInit()
	{
		AdempiereTestHelper.get().staticInit();
		POJOWrapper.setDefaultStrictValues(true);

		createDunningCandidate();
		createSysConfig();
	}

	private static void createSysConfig()
	{
		sysConfig = newInstance(I_AD_SysConfig.class);
		sysConfig.setName(DefaultDunningProducer.SYS_CONFIG_DUNNING_USE_PREFIXED_PO_REFERENCE);
		sysConfig.setValue("Y");
		saveRecord(sysConfig);
	}

	private static void createDunningCandidate()
	{
		candidate = newInstance(I_C_Dunning_Candidate.class);
		candidate.setPOReference(PO_REFERENCE);
		candidate.setDocumentNo(DOCUMENT_NO);
		saveRecord(candidate);
	}

	@BeforeEach
	public final void beforeTest()
	{
		AdempiereTestHelper.get().init();
	}

	private void setDunningUsePrefixedPoReference(final boolean value)
	{
		sysConfig.setValue(value? "Y": "N");
		saveRecord(sysConfig);
	}

	@Test
	public void prefixed_prefix_matches()
	{
		setDunningUsePrefixedPoReference(true);
		setDocumentNo(DOCUMENT_NO);
		Assertions.assertThat(DefaultDunningProducer.getPOReferenceToUse(candidate)).isEqualTo(PREFIXED_PO_REFERENCE);
	}

	@Test
	public void notprefixed_prefix_matches()
	{
		setDunningUsePrefixedPoReference(true);
		setDocumentNo(DOCUMENT_NO_NOT_MATCHING_POREF);
		Assertions.assertThat(DefaultDunningProducer.getPOReferenceToUse(candidate)).isEqualTo(PO_REFERENCE);
	}
	@Test
	public void prefixed_prefix_does_not_match()
	{
		setDunningUsePrefixedPoReference(false);
		setDocumentNo(DOCUMENT_NO);
		Assertions.assertThat(DefaultDunningProducer.getPOReferenceToUse(candidate)).isEqualTo(PO_REFERENCE);
	}

	@Test
	public void notprefixed_prefix_does_not_match()
	{
		setDunningUsePrefixedPoReference(false);
		setDocumentNo(DOCUMENT_NO_NOT_MATCHING_POREF);
		Assertions.assertThat(DefaultDunningProducer.getPOReferenceToUse(candidate)).isEqualTo(PO_REFERENCE);
	}

	private static void setDocumentNo(final @NonNull String documentNo)
	{
		candidate.setDocumentNo(documentNo);
		InterfaceWrapperHelper.save(candidate);
	}

}
