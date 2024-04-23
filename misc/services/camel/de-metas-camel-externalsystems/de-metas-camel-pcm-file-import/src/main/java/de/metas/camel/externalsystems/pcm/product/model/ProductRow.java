package de.metas.camel.externalsystems.pcm.product.model;

import de.metas.camel.externalsystems.common.CamelProcessorUtil;
import lombok.Getter;
import lombok.ToString;
import org.apache.camel.dataformat.bindy.annotation.CsvRecord;
import org.apache.camel.dataformat.bindy.annotation.DataField;

import java.math.BigDecimal;

@CsvRecord(separator = ";", skipField = true)
@Getter
@ToString
public class ProductRow
{
	@DataField(pos = 1)
	private String bpartnerIdentifier;

	@DataField(pos = 2)
	private String productIdentifier;

	@DataField(pos = 3)
	private String value;

	@DataField(pos = 4)
	private String name;

	@DataField(pos = 5)
	private String description;

	@DataField(pos = 6)
	private String ean;

	@DataField(pos = 7)
	private String taxRate;

	public BigDecimal getTaxRate()
	{
		return CamelProcessorUtil.parseGermanNumberString(taxRate);
	}
}
