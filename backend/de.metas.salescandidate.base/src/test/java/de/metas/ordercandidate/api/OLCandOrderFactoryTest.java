/*
 * #%L
 * de.metas.salescandidate.base
 * %%
 * Copyright (C) 2021 metas GmbH
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

package de.metas.ordercandidate.api;

import de.metas.bpartner.BPartnerContactId;
import de.metas.bpartner.BPartnerId;
import de.metas.bpartner.BPartnerLocationId;
import de.metas.bpartner.service.impl.BPartnerBL;
import de.metas.document.location.DocumentLocation;
import de.metas.greeting.GreetingRepository;
import de.metas.location.CountryId;
import de.metas.location.LocationId;
import de.metas.order.BPartnerOrderParamsRepository;
import de.metas.order.compensationGroup.GroupCompensationLineCreateRequestFactory;
import de.metas.order.compensationGroup.OrderGroupRepository;
import de.metas.order.location.adapter.OrderDocumentLocationAdapterFactory;
import de.metas.ordercandidate.api.impl.OLCandBL;
import de.metas.ordercandidate.location.adapter.OLCandDocumentLocationAdapterFactory;
import de.metas.ordercandidate.model.I_C_OLCand;
import de.metas.ordercandidate.spi.NullOLCandListener;
import de.metas.product.ProductId;
import de.metas.product.ProductType;
import de.metas.uom.X12DE355;
import de.metas.user.UserRepository;
import lombok.Builder;
import lombok.NonNull;
import org.adempiere.ad.wrapper.POJOWrapper;
import org.adempiere.model.InterfaceWrapperHelper;
import org.adempiere.test.AdempiereTestHelper;
import org.assertj.core.api.Assertions;
import org.compiere.SpringContextHolder;
import org.compiere.model.I_AD_User;
import org.compiere.model.I_C_BPartner;
import org.compiere.model.I_C_BPartner_Location;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_UOM;
import org.compiere.model.I_M_Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.adempiere.model.InterfaceWrapperHelper.newInstance;
import static org.adempiere.model.InterfaceWrapperHelper.newInstanceOutOfTrx;
import static org.adempiere.model.InterfaceWrapperHelper.saveRecord;

class OLCandOrderFactoryTest
{
	private CountryId countryDE;
	private I_C_UOM uomKg;
	private ProductId productId;

	@BeforeEach
	void beforeEach()
	{
		AdempiereTestHelper.get().init();

		SpringContextHolder.registerJUnitBean(new GreetingRepository());

		SpringContextHolder.registerJUnitBean(new OrderGroupRepository(
				new GroupCompensationLineCreateRequestFactory(),
				Optional.empty()
		));

		final BPartnerBL bpartnerBL = new BPartnerBL(new UserRepository());
		SpringContextHolder.registerJUnitBean(
				IOLCandBL.class,
				new OLCandBL(
						bpartnerBL,
						new BPartnerOrderParamsRepository()
				)
		);

		countryDE = createCountry("DE", "@A1@ @CO@");
		uomKg = createUomKg();
		productId = createProduct("product");
	}

	public I_C_UOM createUomKg()
	{
		final I_C_UOM uom = newInstanceOutOfTrx(I_C_UOM.class);
		uom.setName(X12DE355.KILOGRAM.getCode());
		uom.setUOMSymbol(X12DE355.KILOGRAM.getCode());
		uom.setX12DE355(X12DE355.KILOGRAM.getCode());
		saveRecord(uom);
		return uom;
	}

	public ProductId createProduct(@NonNull final String name)
	{
		final I_M_Product product = newInstanceOutOfTrx(I_M_Product.class);
		POJOWrapper.setInstanceName(product, name);
		product.setValue(name);
		product.setName(name);
		product.setC_UOM_ID(uomKg.getC_UOM_ID());
		product.setProductType(ProductType.Item.getCode());
		product.setIsStocked(true);
		saveRecord(product);

		return ProductId.ofRepoId(product.getM_Product_ID());
	}

	public CountryId createCountry(@NonNull final String countryCode, final String addressFormat)
	{
		final I_C_Country record = newInstance(I_C_Country.class);
		record.setCountryCode(countryCode);
		record.setName(countryCode);
		record.setDisplaySequence(addressFormat);
		record.setDisplaySequenceLocal(addressFormat);
		POJOWrapper.setInstanceName(record, countryCode);
		saveRecord(record);
		return CountryId.ofRepoId(record.getC_Country_ID());
	}

	private LocationId createLocation(final CountryId countryId, final String address1)
	{
		final I_C_Location record = newInstance(I_C_Location.class);
		record.setC_Country_ID(countryId.getRepoId());
		record.setAddress1(address1);
		saveRecord(record);
		return LocationId.ofRepoId(record.getC_Location_ID());
	}

	@Builder(builderMethodName = "documentLocation", builderClassName = "$DocumentLocationBuilder")
	private DocumentLocation createDocumentLocation(CountryId countryId, String address1)
	{
		final I_C_BPartner bpartner = InterfaceWrapperHelper.newInstance(I_C_BPartner.class);
		InterfaceWrapperHelper.saveRecord(bpartner);

		final I_C_BPartner_Location bpLocation = newInstance(I_C_BPartner_Location.class);
		bpLocation.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		bpLocation.setC_Location_ID(createLocation(countryId, address1).getRepoId());
		InterfaceWrapperHelper.saveRecord(bpLocation);

		final I_AD_User user = InterfaceWrapperHelper.newInstance(I_AD_User.class);
		user.setC_BPartner_ID(bpartner.getC_BPartner_ID());
		InterfaceWrapperHelper.saveRecord(user);

		return DocumentLocation.builder()
				.bpartnerId(BPartnerId.ofRepoId(bpLocation.getC_BPartner_ID()))
				.bpartnerLocationId(BPartnerLocationId.ofRepoId(bpLocation.getC_BPartner_ID(), bpLocation.getC_BPartner_Location_ID()))
				.locationId(LocationId.ofRepoId(bpLocation.getC_Location_ID()))
				.contactId(BPartnerContactId.ofRepoId(user.getC_BPartner_ID(), user.getAD_User_ID()))
				.build();
	}

	private OLCandOrderFactory createFactory()
	{
		return OLCandOrderFactory.builder()
				.orderDefaults(OLCandOrderDefaults.builder().build())
				.olCandProcessorId(111)
				.olCandListeners(NullOLCandListener.instance)
				.build();
	}

	@Nested
	class DocumentLocationTests
	{
		private OLCand createOLCand(final DocumentLocation location)
		{
			final I_C_OLCand olCandRecord = InterfaceWrapperHelper.newInstance(I_C_OLCand.class);
			OLCandDocumentLocationAdapterFactory.bpartnerLocationAdapter(olCandRecord).setFrom(location);
			olCandRecord.setM_Product_ID(productId.getRepoId());
			olCandRecord.setC_UOM_ID(uomKg.getC_UOM_ID());
			olCandRecord.setApplySalesRepFrom(AssignSalesRepRule.CandidateFirst.getCode());
			InterfaceWrapperHelper.saveRecord(olCandRecord);

			return new OLCandFactory().toOLCand(olCandRecord);
		}

		@Test
		void straightForwardTest()
		{
			final DocumentLocation documentLocation = createDocumentLocation(countryDE, "addr1");
			final OLCand olCand = createOLCand(documentLocation);

			final OLCandOrderFactory factory = createFactory();
			factory.addOLCand(olCand);

			Assertions.assertThat(OrderDocumentLocationAdapterFactory.locationAdapter(factory.getOrder()).toDocumentLocation())
					.usingRecursiveComparison()
					.isEqualTo(documentLocation);
		}

		@Test
		void withDifferentCapturedLocation()
		{
			final DocumentLocation documentLocation = createDocumentLocation(countryDE, "addr1")
					.withLocationId(createLocation(countryDE, "addr2"));
			final OLCand olCand = createOLCand(documentLocation);

			final OLCandOrderFactory factory = createFactory();
			factory.addOLCand(olCand);

			Assertions.assertThat(OrderDocumentLocationAdapterFactory.locationAdapter(factory.getOrder()).toDocumentLocation())
					.usingRecursiveComparison()
					.isEqualTo(documentLocation);
		}

	}
}