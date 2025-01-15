/*
 * #%L
 * de.metas.business.rest-api-impl
 * %%
 * Copyright (C) 2025 metas GmbH
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

package de.metas.rest_api.v2.bpartner.bpartnercomposite.jsonpersister.helper;

import de.metas.bpartner.composite.BPartner;
import de.metas.common.bpartner.v2.request.JsonRequestBPartner;
import de.metas.util.web.exception.InvalidEntityException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BPartnerNameConsolidationHelperTest
{
	@Test
	public void givenEmptyNameAndEmptyCompanyName_whenConsolidate_thenThrowInvalidEntityException()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("");

		//when then
		assertThrows(InvalidEntityException.class, () -> {
			BPartnerNameConsolidationHelper.consolidate(requestBPartner, Mockito.mock(BPartner.class));
		});
	}

	@Test
	public void givenEmptyNameAndCompanyNameNotEmpty_whenConsolidate_thenThrowInvalidEntityException()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("");
		requestBPartner.setCompanyName("company");

		//when then
		assertThrows(InvalidEntityException.class, () -> {
			BPartnerNameConsolidationHelper.consolidate(requestBPartner, Mockito.mock(BPartner.class));
		});
	}

	@Test
	public void givenEmptyNameAndCompanyNameNotSet_whenConsolidate_thenThrowInvalidEntityException()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("");

		//when then
		assertThrows(InvalidEntityException.class, () -> {
			BPartnerNameConsolidationHelper.consolidate(requestBPartner, Mockito.mock(BPartner.class));
		});
	}

	@Test
	public void givenNameSetCompanyNameNotSetAndExistingPartnerIsCompany_whenConsolidate_thenUseNameForCompanyName()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("Name");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(true);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("Name");
		assertThat(result.getCompanyName()).isEqualTo("Name");
		assertThat(result.isCompany()).isEqualTo(true);
	}

	@Test
	public void givenNameSetCompanyNameNotSetAndExistingPartnerIsNotCompany_whenConsolidate_thenOnlyNameIsSet()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("Name");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(false);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("Name");
		assertThat(result.getCompanyName()).isNull();
		assertThat(result.isCompany()).isEqualTo(false);
	}

	@Test
	public void givenNameSetCompanyNameSetAndMatchingAndExistingPartnerIsCompany_whenConsolidate_thenUseNameForCompanyName()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("Name");
		requestBPartner.setCompanyName("Name");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(true);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("Name");
		assertThat(result.getCompanyName()).isEqualTo("Name");
		assertThat(result.isCompany()).isEqualTo(true);
	}

	@Test
	public void givenNameSetCompanyNameSetAndNotMatching_whenConsolidate_thenThrowInvalidEntityException()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("Name");
		requestBPartner.setCompanyName("CompanyName");

		//when then
		assertThrows(InvalidEntityException.class, () ->
				BPartnerNameConsolidationHelper.consolidate(requestBPartner, Mockito.mock(BPartner.class)));
	}

	@Test
	public void givenNameSetCompanyNameSetToNullAndExistingPartnerIsCompany_whenConsolidate_thenUseNameAndMakeIsCompanyFalse()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setName("Name");
		requestBPartner.setCompanyName("");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(true);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("Name");
		assertThat(result.getCompanyName()).isEqualTo(null);
		assertThat(result.isCompany()).isEqualTo(false);
	}

	@Test
	public void givenNameNotSpecifiedCompanyNameSetToNullAndExistingPartnerIsCompany_whenConsolidate_thenKeepExistingNameAndMakeIsCompanyFalse()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setCompanyName(null);

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(true);
		Mockito.when(existingPartner.getName()).thenReturn("ExistingName");

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("ExistingName");
		assertThat(result.getCompanyName()).isEqualTo(null);
		assertThat(result.isCompany()).isEqualTo(false);
	}

	@Test
	public void givenNameNotSpecifiedCompanyNameSetToNullAndExistingPartnerIsNotCompany_whenConsolidate_thenKeepExistingNameAndMakeIsCompanyFalse()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setCompanyName(null);

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(false);
		Mockito.when(existingPartner.getName()).thenReturn("ExistingName");

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("ExistingName");
		assertThat(result.getCompanyName()).isEqualTo(null);
		assertThat(result.isCompany()).isEqualTo(false);
	}

	@Test
	public void givenNameNotSpecifiedCompanyNameSetAndExistingPartnerIsCompany_whenConsolidate_thenUseCompanyNameAsName()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setCompanyName("CompanyName");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(true);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("CompanyName");
		assertThat(result.getCompanyName()).isEqualTo("CompanyName");
		assertThat(result.isCompany()).isEqualTo(true);
	}

	@Test
	public void givenNameNotSpecifiedCompanyNameSetAndExistingPartnerIsNotCompany_whenConsolidate_thenUseCompanyNameAsNameAndMakeIsCompanyTrue()
	{
		//given
		final JsonRequestBPartner requestBPartner = new JsonRequestBPartner();
		requestBPartner.setCompanyName("CompanyName");

		final BPartner existingPartner = Mockito.mock(BPartner.class);
		Mockito.when(existingPartner.isCompany()).thenReturn(false);

		//when
		final BPartnerNameConsolidationHelper.Result result = BPartnerNameConsolidationHelper
				.consolidate(requestBPartner, existingPartner);

		//then
		assertThat(result.getName()).isEqualTo("CompanyName");
		assertThat(result.getCompanyName()).isEqualTo("CompanyName");
		assertThat(result.isCompany()).isEqualTo(true);
	}
}
