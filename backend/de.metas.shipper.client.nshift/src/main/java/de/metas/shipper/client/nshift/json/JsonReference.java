package de.metas.shipper.client.nshift.json;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
public class JsonReference
{
	@JsonProperty("Kind")
	int kind; // see https://helpcenter.nshift.com/hc/en-us/articles/16926110939292-Objects-and-Fields ReferenceKind

	@JsonProperty("Value")
	String value;
}
