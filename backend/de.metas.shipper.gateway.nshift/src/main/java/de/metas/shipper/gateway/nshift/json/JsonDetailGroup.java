package de.metas.shipper.gateway.nshift.json;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, property = "GroupID")
@JsonSubTypes({
		@JsonSubTypes.Type(value = JsonUnknown.class, name = "0"),
		@JsonSubTypes.Type(value = JsonCustomsArticleInfo.class, name = "1"),
		@JsonSubTypes.Type(value = JsonCustomsInfo.class, name = "2"),
		@JsonSubTypes.Type(value = JsonFedExCustomsInformation.class, name = "4"),
		@JsonSubTypes.Type(value = JsonDHLFiling.class, name = "5"),
		@JsonSubTypes.Type(value = JsonOrderData.class, name = "6"),
})
@Getter
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode
@ToString
public abstract class JsonDetailGroup
{

}