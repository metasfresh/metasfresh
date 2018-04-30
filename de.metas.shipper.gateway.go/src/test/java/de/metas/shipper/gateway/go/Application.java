package de.metas.shipper.gateway.go;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Locale;

import org.adempiere.util.Check;
import org.junit.Ignore;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import de.metas.shipper.gateway.go.schema.GOPaidMode;
import de.metas.shipper.gateway.go.schema.GOSelfDelivery;
import de.metas.shipper.gateway.go.schema.GOSelfPickup;
import de.metas.shipper.gateway.go.schema.GOServiceType;
import de.metas.shipper.gateway.spi.model.Address;
import de.metas.shipper.gateway.spi.model.CountryCode;
import de.metas.shipper.gateway.spi.model.DeliveryOrder;
import de.metas.shipper.gateway.spi.model.DeliveryPosition;
import de.metas.shipper.gateway.spi.model.PackageLabel;
import de.metas.shipper.gateway.spi.model.PackageLabels;
import de.metas.shipper.gateway.spi.model.PickupDate;
import lombok.NonNull;

/*
 * #%L
 * de.metas.shipper.go
 * %%
 * Copyright (C) 2017 metas GmbH
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

@SpringBootApplication(scanBasePackages = "de.metas.shipper.gateway")
@Ignore
public class Application
{
	public static void main(final String[] args)
	{
		SpringApplication.run(Application.class, args);
	}

	@Bean
	CommandLineRunner test(final GOClient goClient)
	{
		System.out.println("Using: " + goClient);

		final CountryCodeFactory countryCodeFactory = new CountryCodeFactory();

		final DeliveryOrder deliveryOrderCreateRequest = DeliveryOrder.builder()
				.pickupAddress(Address.builder()
						.companyName1("from company")
						.street1("street 1")
						.houseNo("1")
						.zipCode("12345")
						.city("Bonn")
						.country(countryCodeFactory.getCountryCodeByAlpha2("DE"))
						.build())
				.pickupDate(PickupDate.builder()
						.date(LocalDate.of(2018, Month.JANUARY, 8))
						.build())
				.deliveryAddress(Address.builder()
						.companyName1("to company")
						.companyDepartment("leer")
						.street1("street 1")
						.houseNo("1")
						.zipCode("54321")
						.city("Koln")
						.country(countryCodeFactory.getCountryCodeByAlpha2("DE"))
						.build())
				.deliveryPosition(DeliveryPosition.builder()
						.numberOfPackages(5)
						.packageIds(ImmutableList.of(1, 2, 3, 4, 5))
						.grossWeightKg(1)
						.content("some products")
						.build())
				.customerReference("some info for customer")
				.serviceType(GOServiceType.Overnight)
				.paidMode(GOPaidMode.Prepaid)
				.selfDelivery(GOSelfDelivery.Pickup)
				.selfPickup(GOSelfPickup.Delivery)
				.receiptConfirmationPhoneNumber("+40-746-010203")
				.build();

		return args -> {
			final DeliveryOrder deliveryOrder = goClient.createDeliveryOrder(deliveryOrderCreateRequest);
			goClient.completeDeliveryOrder(deliveryOrder);

			final List<PackageLabels> packageLabels = goClient.getPackageLabelsList(deliveryOrder);
			System.out.println("Labels: " + packageLabels);
			savePDFs(packageLabels);
		};
	}

	private void savePDFs(final List<PackageLabels> packageLabelsList)
	{
		packageLabelsList.forEach(packageLabels -> savePDFs(packageLabels));
	}

	private void savePDFs(final PackageLabels packageLabels)
	{
		packageLabels.getLabelTypes()
				.stream()
				.map(packageLabels::getPackageLabel)
				.forEach(packageLabel -> savePDFs(packageLabel));
	}

	private void savePDFs(final PackageLabel packageLabel)
	{
		try
		{
			final File file = File.createTempFile("Label_" + packageLabel.getType() + "_", ".pdf");
			final FileOutputStream os = new FileOutputStream(file);
			os.write(packageLabel.getLabelData());
			os.close();

			System.out.println("Saved " + packageLabel + " to " + file);
		}
		catch (final IOException ex)
		{
			throw new RuntimeException("Failed saving " + packageLabel, ex);
		}
	}

	//
	//
	//
	private static class CountryCodeFactory
	{
		private final ImmutableMap<String, CountryCode> countryCodesByAlpha2;

		public CountryCodeFactory()
		{
			final ImmutableMap.Builder<String, CountryCode> countryCodesByAlpha2 = ImmutableMap.builder();
			// final ImmutableMap.Builder<String, CountryCode> countryCodesByAlpha3 = ImmutableMap.builder();

			for (final String countryCodeAlpha2 : Locale.getISOCountries())
			{
				final Locale locale = new Locale("", countryCodeAlpha2);
				final String countryCodeAlpha3 = locale.getISO3Country();
				final CountryCode countryCode = CountryCode.builder()
						.alpha2(countryCodeAlpha2)
						.alpha3(countryCodeAlpha3)
						.build();
				countryCodesByAlpha2.put(countryCodeAlpha2, countryCode);
				// countryCodesByAlpha3.put(countryCodeAlpha3, countryCode);
			}

			this.countryCodesByAlpha2 = countryCodesByAlpha2.build();
			// this.countryCodesByAlpha3 = countryCodesByAlpha3.build();
		}

		public CountryCode getCountryCodeByAlpha2(@NonNull final String countryCodeAlpha2)
		{
			final CountryCode countryCode = countryCodesByAlpha2.get(countryCodeAlpha2);
			Check.assumeNotNull(countryCode, "countyCode shall exist for '{}' (alpha2)", countryCodeAlpha2);
			return countryCode;
		}
	}
}
