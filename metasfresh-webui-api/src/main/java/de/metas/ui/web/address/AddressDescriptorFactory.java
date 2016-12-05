package de.metas.ui.web.address;

import java.util.function.BiConsumer;
import java.util.function.Function;

import org.adempiere.util.Services;
import org.adempiere.util.api.IMsgBL;
import org.compiere.model.I_C_City;
import org.compiere.model.I_C_Country;
import org.compiere.model.I_C_Location;
import org.compiere.model.I_C_Region;
import org.compiere.util.CCache;
import org.springframework.stereotype.Component;

import de.metas.printing.esb.base.util.Check;
import de.metas.ui.web.window.datatypes.DocumentType;
import de.metas.ui.web.window.datatypes.LookupValue.IntegerLookupValue;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentEntityDataBindingDescriptor.DocumentEntityDataBindingDescriptorBuilder;
import de.metas.ui.web.window.descriptor.DocumentEntityDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDataBindingDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor;
import de.metas.ui.web.window.descriptor.DocumentFieldDescriptor.Characteristic;
import de.metas.ui.web.window.descriptor.DocumentFieldWidgetType;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementDescriptor;
import de.metas.ui.web.window.descriptor.DocumentLayoutElementFieldDescriptor;
import de.metas.ui.web.window.model.DocumentsRepository;
import de.metas.ui.web.window.model.IDocumentFieldView;

/*
 * #%L
 * metasfresh-webui-api
 * %%
 * Copyright (C) 2016 metas GmbH
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 2 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-2.0.html>.
 * #L%
 */

@Component
public class AddressDescriptorFactory
{
	private final CCache<Integer, AddressDescriptor> cache = CCache.newLRUCache("AddressDescriptor", 1, 0);

	public AddressDescriptor getAddressDescriptor()
	{
		final int key = 0; // some dummy key
		return cache.getOrLoad(key, () -> createAddressDescriptor());
	}

	private AddressDescriptor createAddressDescriptor()
	{
		final DocumentEntityDescriptor entityDescriptor = createAddressEntityDescriptor();
		final AddressLayout layout = createLayout(entityDescriptor);
		return AddressDescriptor.of(entityDescriptor, layout);
	}

	private DocumentEntityDescriptor createAddressEntityDescriptor()
	{
		final DocumentEntityDescriptor.Builder addressDescriptor = DocumentEntityDescriptor.builder()
				.setDocumentType(DocumentType.Address, 1) // we have only one descriptor for all addresses
				.setCaption(Services.get(IMsgBL.class).getTranslatableMsgText("C_Location_ID"))
				.setDataBinding(new AddressDataBindingDescriptorBuilder())
				.disableCallouts()
				// Defaults:
				.setDetailId(null)
				.setAD_Tab_ID(0)
				.setTableName(org.compiere.model.I_C_Location.Table_Name)
				.setIsSOTrx(true)
				//
				;

		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_Address1)
				.setValueClass(String.class)
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_Address1, false, I_C_Location::getAddress1, AddressFieldBinding::writeValue_Address1)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_Address2)
				.setValueClass(String.class)
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_Address2, false, I_C_Location::getAddress2, AddressFieldBinding::writeValue_Address2)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_Address3)
				.setValueClass(String.class)
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_Address3, false, I_C_Location::getAddress3, AddressFieldBinding::writeValue_Address3)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_Address4)
				.setValueClass(String.class)
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_Address4, false, I_C_Location::getAddress4, AddressFieldBinding::writeValue_Address4)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_City)
				.setValueClass(String.class)
				.setWidgetType(DocumentFieldWidgetType.Text)
				.setLookupDescriptorProvider(new AddressCityLookupDescriptor())
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_City, false, AddressFieldBinding::readValue_City, AddressFieldBinding::writeValue_City)));
		//
//		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_C_City_ID)
//				.setValueClass(IntegerLookupValue.class)
//				.setWidgetType(DocumentFieldWidgetType.Lookup)
//				.setLookupDescriptorProvider(new AddressCityLookupDescriptor())
//				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_C_City_ID, false, AddressFieldBinding::readValue_City, AddressFieldBinding::writeValue_C_City_ID)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_C_Region_ID)
				.setValueClass(IntegerLookupValue.class)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setLookupDescriptorProvider(new AddressRegionLookupDescriptor())
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_C_Region_ID, false, AddressFieldBinding::readValue_Region, AddressFieldBinding::writeValue_C_Region_ID)));
		//
		addressDescriptor.addField(buildFieldDescriptor(I_C_Location.COLUMNNAME_C_Country_ID)
				.setValueClass(IntegerLookupValue.class)
				.setWidgetType(DocumentFieldWidgetType.Lookup)
				.setMandatoryLogic(true)
				.setLookupDescriptorProvider(new AddressCountryLookupDescriptor())
				.setDataBinding(new AddressFieldBinding(I_C_Location.COLUMNNAME_C_Country_ID, false, AddressFieldBinding::readValue_Country, AddressFieldBinding::writeValue_C_Country_ID)));

		return addressDescriptor.build();
	}

	private DocumentFieldDescriptor.Builder buildFieldDescriptor(final String columnName)
	{
		return DocumentFieldDescriptor.builder(columnName)
				.setCaption(Services.get(IMsgBL.class).getTranslatableMsgText(columnName))
				//
				// .setValueClass()
				// .setWidgetType()
				.setLookupDescriptorProvider_None()
				//
				.setReadonlyLogic(false)
				.setDisplayLogic(true)
				.setMandatoryLogic(false)
				//
				.addCharacteristic(Characteristic.PublicField)
				//
				// .setDataBinding(new AddressFieldBinding(columnName, false, I_C_Location::get))
				//
		;

	}

	private static AddressLayout createLayout(final DocumentEntityDescriptor addressDescriptor)
	{
		final AddressLayout.Builder layout = AddressLayout.builder();

		addressDescriptor.getFields()
				.stream()
				.map(fieldDescriptor -> createLayoutElement(fieldDescriptor))
				.forEach(layoutElement -> layout.addElement(layoutElement));

		return layout.build();
	}

	private static DocumentLayoutElementDescriptor.Builder createLayoutElement(final DocumentFieldDescriptor fieldDescriptor)
	{
		return DocumentLayoutElementDescriptor.builder()
				.setCaption(fieldDescriptor.getCaption())
				.setWidgetType(fieldDescriptor.getWidgetType())
				.addField(DocumentLayoutElementFieldDescriptor.builder(fieldDescriptor.getFieldName())
						.setLookupSource(fieldDescriptor.getLookupSourceType())
						.setPublicField(true));
	}

	private static class AddressDataBindingDescriptorBuilder implements DocumentEntityDataBindingDescriptorBuilder
	{
		private final DocumentEntityDataBindingDescriptor dataBinding = new DocumentEntityDataBindingDescriptor()
		{
			@Override
			public DocumentsRepository getDocumentsRepository()
			{
				throw new IllegalStateException("No repository available for " + this);
			}
		};

		private AddressDataBindingDescriptorBuilder()
		{
			super();
		}

		@Override
		public DocumentEntityDataBindingDescriptor getOrBuild()
		{
			return dataBinding;
		}
	}

	public static final class AddressFieldBinding implements DocumentFieldDataBindingDescriptor
	{
		private final String columnName;
		private final boolean mandatory;
		private final Function<I_C_Location, Object> readMethod;
		private final BiConsumer<I_C_Location, IDocumentFieldView> writeMethod;

		private AddressFieldBinding(
				final String columnName //
				, final boolean mandatory //
				, final Function<I_C_Location, Object> readMethod //
				, final BiConsumer<I_C_Location, IDocumentFieldView> writeMethod //
		)
		{
			super();
			this.columnName = columnName;
			this.mandatory = mandatory;
			this.readMethod = readMethod;
			this.writeMethod = writeMethod;
		}

		@Override
		public String getColumnName()
		{
			return columnName;
		}

		@Override
		public boolean isMandatory()
		{
			return mandatory;
		}

		public Object readValue(final I_C_Location locationRecord)
		{
			return readMethod.apply(locationRecord);
		}

		private static Object readValue_City(final I_C_Location locationRecord)
		{
			final I_C_City city = locationRecord.getC_City();
			if (city != null && city.getC_City_ID() > 0)
			{
				return IntegerLookupValue.of(city.getC_City_ID(), city.getName());
			}

			final String cityName = locationRecord.getCity();
			if (!Check.isEmpty(cityName, true))
			{
				return IntegerLookupValue.of(-1, cityName);
			}

			return null;
		}

		private static Object readValue_Region(final I_C_Location locationRecord)
		{
			final I_C_Region region = locationRecord.getC_Region();
			if (region != null && region.getC_Region_ID() > 0)
			{
				return IntegerLookupValue.of(region.getC_Region_ID(), region.getName());
			}

			final String regionName = locationRecord.getRegionName();
			if (!Check.isEmpty(regionName, true))
			{
				return IntegerLookupValue.of(-1, regionName);
			}

			return null;
		}

		private static Object readValue_Country(final I_C_Location locationRecord)
		{
			final I_C_Country country = locationRecord.getC_Country();
			if (country != null && country.getC_Country_ID() > 0)
			{
				return IntegerLookupValue.of(country.getC_Country_ID(), country.getName());
			}

			return null;
		}

		public void writeValue(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			writeMethod.accept(toLocationRecord, fromField);
		}

		public static void writeValue_Address1(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			toLocationRecord.setAddress1(fromField.getValueAs(String.class));
		}

		public static void writeValue_Address2(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			toLocationRecord.setAddress2(fromField.getValueAs(String.class));
		}

		public static void writeValue_Address3(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			toLocationRecord.setAddress3(fromField.getValueAs(String.class));
		}

		public static void writeValue_Address4(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			toLocationRecord.setAddress4(fromField.getValueAs(String.class));
		}

		public static void writeValue_City(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			toLocationRecord.setCity(fromField.getValueAs(String.class));
		}

		public static void writeValue_C_City_ID(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			final IntegerLookupValue city = fromField.getValueAs(IntegerLookupValue.class);
			if (city == null)
			{
				toLocationRecord.setC_City_ID(-1);
			}
			else if (city.getIdAsInt() <= 0)
			{
				toLocationRecord.setC_City_ID(-1);
				toLocationRecord.setCity(city.getDisplayName());
			}
			else
			{
				toLocationRecord.setC_City_ID(city.getIdAsInt());
				toLocationRecord.setCity(city.getDisplayName());
			}
		}

		public static void writeValue_C_Region_ID(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			final IntegerLookupValue region = fromField.getValueAs(IntegerLookupValue.class);
			if (region == null)
			{
				toLocationRecord.setC_Region_ID(-1);
			}
			else if (region.getIdAsInt() <= 0)
			{
				toLocationRecord.setC_Region_ID(-1);
				toLocationRecord.setCity(region.getDisplayName());
			}
			else
			{
				toLocationRecord.setC_Region_ID(region.getIdAsInt());
				toLocationRecord.setRegionName(region.getDisplayName());
			}
		}

		public static void writeValue_C_Country_ID(final I_C_Location toLocationRecord, final IDocumentFieldView fromField)
		{
			final IntegerLookupValue country = fromField.getValueAs(IntegerLookupValue.class);
			if (country == null)
			{
				toLocationRecord.setC_Country_ID(-1);
			}
			else if (country.getIdAsInt() <= 0)
			{
				toLocationRecord.setC_Country_ID(-1);
			}
			else
			{
				toLocationRecord.setC_Country_ID(country.getIdAsInt());
			}
		}

	}
}
