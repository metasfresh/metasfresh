package de.metas.frontend_testing.masterdata.mobile_configuration;

import com.google.common.collect.ImmutableList;
import de.metas.frontend_testing.masterdata.Identifier;
import de.metas.frontend_testing.masterdata.MasterdataContext;
import de.metas.frontend_testing.masterdata.attribute.CreateAttributeCommand;
import de.metas.frontend_testing.masterdata.attribute.JsonCreateAttributeRequest;
import de.metas.manufacturing.config.MobileUIManufacturingConfigRepository;
import de.metas.user.UserId;
import org.adempiere.exceptions.AdempiereException;
import org.adempiere.mm.attributes.AttributeCode;
import org.adempiere.mm.attributes.AttributeId;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.service.ClientId;
import org.adempiere.test.AdempiereTestHelper;
import org.compiere.model.I_M_Attribute;
import org.compiere.util.Env;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/*
 * #%L
 * de.metas.frontend-testing
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

public class MobileConfigManufacturingCommandTest
{
	private MasterdataContext context;
	private MobileUIManufacturingConfigRepository configRepository;

	@BeforeEach
	public void init()
	{
		AdempiereTestHelper.get().init();

		// The command persists/reads the global config as ClientId.METASFRESH (as the real logged-in
		// FrontendTesting session does); make the test session's client match so POJO-created records land
		// under METASFRESH and are found on read-back.
		Env.setContext(Env.getCtx(), Env.CTXNAME_AD_Client_ID, MasterdataContext.CLIENT_ID.getRepoId());

		context = new MasterdataContext();
		configRepository = new MobileUIManufacturingConfigRepository();

		// The command resolves the login user from the context.
		context.putIdentifier(Identifier.ofString("loginUser"), UserId.ofRepoId(1234567));
	}

	private AttributeCode createAttribute(final String identifier, final String value)
	{
		CreateAttributeCommand.builder()
				.context(context)
				.request(JsonCreateAttributeRequest.builder().value(value).build())
				.identifier(Identifier.ofString(identifier))
				.build()
				.execute();

		final AttributeId attributeId = context.getId(Identifier.ofString(identifier), AttributeId.class);
		final I_M_Attribute record = InterfaceWrapperHelper.load(attributeId, I_M_Attribute.class);
		return AttributeCode.ofString(record.getValue());
	}

	private void execute(final JsonMobileConfigRequest.Manufacturing request)
	{
		MobileConfigManufacturingCommand.builder()
				.mobileManufacturingConfigRepository(configRepository)
				.context(context)
				.request(request)
				.build()
				.execute();
	}

	/**
	 * Reads back the persisted global editable-attribute list from DB ground truth via a FRESH repository
	 * instance. The command's own repository instance cached the (empty) global config during its initial
	 * {@code getConfig()} call, before the save; in a real running app a DB save invalidates that CCache, but
	 * in POJO unit-test mode it does not - so asserting on the command's response would read stale cache, not
	 * what was persisted.
	 */
	private List<AttributeCode> persistedEditableAttributes()
	{
		return new MobileUIManufacturingConfigRepository()
				.getGlobalConfig(ClientId.METASFRESH)
				.getEditableAttributeCodesInOrder();
	}

	@Test
	public void editableAttributes_resolvedByMasterdataIdentifier()
	{
		// given an attribute created earlier in the same request, whose actual Value differs from its
		// map-key identifier ("attr1" != the persisted Value)
		final AttributeCode actualCode = createAttribute("attr1", "SIZE_CM");

		// when configuring the editable-attribute list by the identifier "attr1"
		execute(JsonMobileConfigRequest.Manufacturing.builder()
				.editableAttributes(ImmutableList.of(AttributeCode.ofString("attr1")))
				.build());

		// then it must resolve to the attribute's ACTUAL code, not the literal "attr1"
		assertThat(persistedEditableAttributes()).containsExactly(actualCode);
	}

	@Test
	public void editableAttributes_literalCodeFallback()
	{
		// given an attribute whose persisted Value is a literal code, NOT registered under that string
		// as an identifier (identifier is "someKey", Value is "Lot-Nummer")
		final AttributeCode literalCode = createAttribute("someKey", "Lot-Nummer");

		// when configuring by the literal code
		execute(JsonMobileConfigRequest.Manufacturing.builder()
				.editableAttributes(ImmutableList.of(AttributeCode.ofString("Lot-Nummer")))
				.build());

		// then the literal code resolves as-is (backward compatibility)
		assertThat(persistedEditableAttributes()).containsExactly(literalCode);
	}

	@Test
	public void editableAttributes_neitherIdentifierNorExistingCode_failsLoudly()
	{
		// given an editable-attribute entry (a typo) that is NEITHER registered as a masterdata identifier
		// in the run context NOR the Value of any existing M_Attribute - no createAttribute call for it
		final AttributeCode bogusCode = AttributeCode.ofString("NoSuchAttribute_Typo");

		// when configuring the editable-attribute list with that unresolvable entry, then it must fail loudly
		// (resolveEditableAttributeCodes falls back to a literal code, which the save path then rejects via
		// IAttributeDAO#getAttributeIdByCode) - NOT be swallowed silently
		assertThatThrownBy(() -> execute(JsonMobileConfigRequest.Manufacturing.builder()
				.editableAttributes(ImmutableList.of(bogusCode))
				.build()))
				.isInstanceOf(AdempiereException.class)
				.hasMessageContaining("NoSuchAttribute_Typo");
	}
}
