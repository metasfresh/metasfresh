package de.metas.rest_api.bpartner.request;

import static de.metas.rest_api.bpartner.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

import de.metas.rest_api.common.JsonExternalId;
import de.metas.rest_api.common.SyncAdvise;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/*
 * #%L
 * de.metas.ordercandidate.rest-api
 * %%
 * Copyright (C) 2018 metas GmbH
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

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Locations can be inserted/updated, or just looked up. For lookup, metasfresh tries first the `externalId` and then the `gln`.")
public class JsonRequestLocation
{
	@ApiModelProperty(dataType = "java.lang.String", //
			value = "This translates to `C_BPartner_Location.ExternalId`.\n"
					+ "Needs to be unique over all business partners (not only the one this location belongs to).")
	private JsonExternalId externalId;
	private boolean externalIdSet;

	@ApiModelProperty("If not specified but required (e.g. because a new location is created), then `true` is assumed")
	private Boolean active;
	private boolean activeSet;

	@ApiModelProperty("This translates to `C_BPartner_Location.Name`")
	private String name;
	private boolean nameSet;

	@ApiModelProperty("This translates to `C_BPartner_Location.BPartnerName`")
	private String bpartnerName;
	private boolean bpartnerNameSet;

	private String address1;
	private boolean address1Set;

	private String address2;
	private boolean address2Set;

	private String address3;
	private boolean address3Set;

	private String address4;
	private boolean address4Set;

	private String poBox;
	private boolean poBoxSet;

	@ApiModelProperty("If specified, then metasfresh will attempt to lookup the `C_Postal` record.\n"
			+ "If there is one matching postal record, the system **will ignore** the following properties and instead use the postal record's values:\n"
			+ "* countryCode\n"
			+ "* city\n"
			+ "* region\n")
	private String postal;
	private boolean postalSet;

	private String city;
	private boolean citySet;

	@ApiModelProperty("If specified, then metasfresh will use this property (in addition to `postal`) as a filter criterion to look up `C_Postal` records.\n"
			+ "The property may be empty so a caller can explicitly tell metasfresh *not* to filter by district")
	private String district;
	private boolean districtSet;

	private String region;
	private boolean regionSet;

	private String countryCode;
	private boolean countryCodeSet;

	@ApiModelProperty("This translates to `C_BPartner_Location.GLN`")
	private String gln;
	private boolean glnSet;

	@ApiModelProperty(required = false)
	private Boolean shipTo;
	private boolean shipToSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `shipToDefault == true`.\n"
			+ "If `true`, then " //
			+ "* `shipTo` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh location might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;
	private boolean shipToDefaultSet;

	@ApiModelProperty(required = false)
	private Boolean billTo;
	private boolean billToSet;

	@ApiModelProperty(required = false, value = "Only one location per request may have `billToDefault == true`.\n"
			+ "If `true`, then " //
			+ "* `billTo` is always be assumed to be `true` as well"
			+ "* another possibly exiting metasfresh location might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;
	private boolean billToDefaultSet;

	@ApiModelProperty(position = 20, // shall be last
			value = "Sync advise about this location's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;
	private boolean syncAdviseSet;

	public void setExternalId(JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

	public void setActive(Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setName(String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setBpartnerName(String bpartnerName)
	{
		this.bpartnerName = bpartnerName;
		this.bpartnerNameSet = true;
	}

	public void setAddress1(String address1)
	{
		this.address1 = address1;
		this.address1Set = true;
	}

	public void setAddress2(String address2)
	{
		this.address2 = address2;
		this.address2Set = true;
	}

	public void setAddress3(String address3)
	{
		this.address3 = address3;
		this.address3Set = true;
	}

	public void setAddress4(String address4)
	{
		this.address4 = address4;
		this.address4Set = true;
	}

	public void setPoBox(String poBox)
	{
		this.poBox = poBox;
		this.poBoxSet = true;
	}

	public void setPostal(String postal)
	{
		this.postal = postal;
		this.postalSet = true;
	}

	public void setCity(String city)
	{
		this.city = city;
		this.citySet = true;
	}

	public void setDistrict(String district)
	{
		this.district = district;
		this.districtSet = true;
	}

	public void setRegion(String region)
	{
		this.region = region;
		this.regionSet = true;
	}

	public void setCountryCode(String countryCode)
	{
		this.countryCode = countryCode;
		this.countryCodeSet = true;
	}

	public void setGln(String gln)
	{
		this.gln = gln;
		this.glnSet = true;
	}

	public void setShipTo(Boolean shipTo)
	{
		this.shipTo = shipTo;
		this.shipToSet = true;
	}

	public void setShipToDefault(Boolean shipToDefault)
	{
		this.shipToDefault = shipToDefault;
		this.shipToDefaultSet = true;
	}

	public void setBillTo(Boolean billTo)
	{
		this.billTo = billTo;
		this.billToSet = true;
	}

	public void setBillToDefault(Boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		this.billToDefaultSet = true;
	}

	public void setSyncAdvise(SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

}
