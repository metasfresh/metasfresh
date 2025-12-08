package de.metas.camel.externalsystems.pcm.bpartner.model;

import lombok.Getter;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

@CsvRecord(separator = ";", skipField = true)
@Getter
@ToString
public class BPartnerRow
{
	@DataField(pos = 1)
	private String bpartnerIdentifier;

	@DataField(pos = 2)
	private String value;

	@DataField(pos = 3)
	private String name;

	@DataField(pos = 4)
	private String address1;

	@DataField(pos = 5)
	private String postalCode;

	@DataField(pos = 6)
	private String city;
}
