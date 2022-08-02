/*
 * #%L
 * de-metas-common-bpartner
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

package de.metas.common.bpartner.v2.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.annotation.Nullable;

@Getter
@ToString
@EqualsAndHashCode
@ApiModel(description = "Locations can be inserted/updated, or just looked up. For lookup, metasfresh tries first the `externalId` and then the `gln`.")
public class JsonRequestLocation
{
	@ApiModelProperty(position = 20, //
			value = "If not specified but required (e.g. because a new location is created), then `true` is assumed")
	private Boolean active;

	@ApiModelProperty(hidden = true)
	private boolean activeSet;

	@ApiModelProperty(position = 30, //
			value = "This translates to `C_BPartner_Location.Name`")
	private String name;

	@ApiModelProperty(hidden = true)
	private boolean nameSet;

	@ApiModelProperty(position = 40, //
			value = "This translates to `C_BPartner_Location.BPartnerName`")
	private String bpartnerName;

	@ApiModelProperty(hidden = true)
	private boolean bpartnerNameSet;

	@ApiModelProperty(position = 50)
	private String address1;

	@ApiModelProperty(hidden = true)
	private boolean address1Set;

	@ApiModelProperty(position = 60)
	private String address2;

	@ApiModelProperty(hidden = true)
	private boolean address2Set;

	@ApiModelProperty(position = 70)
	private String address3;

	@ApiModelProperty(hidden = true)
	private boolean address3Set;

	@ApiModelProperty(position = 80)
	private String address4;

	@ApiModelProperty(hidden = true)
	private boolean address4Set;

	@ApiModelProperty(position = 90)
	private String poBox;

	@ApiModelProperty(hidden = true)
	private boolean poBoxSet;

	@ApiModelProperty(position = 100, //
			value = "If specified, then metasfresh will attempt to lookup the `C_Postal` record.\n"
					+ "If there is one matching postal record, the system **will ignore** the following properties and instead use the postal record's values:\n"
					+ "* countryCode\n"
					+ "* city\n"
					+ "* region\n")
	private String postal;

	@ApiModelProperty(hidden = true)
	private boolean postalSet;

	@ApiModelProperty(position = 110)
	private String city;

	@ApiModelProperty(hidden = true)
	private boolean citySet;

	@ApiModelProperty(position = 120, //
			value = "If specified, then metasfresh will use this property (in addition to `postal`) as a filter criterion to look up `C_Postal` records.\n"
					+ "The property may be empty so a caller can explicitly tell metasfresh *not* to filter by district")
	private String district;

	@ApiModelProperty(hidden = true)
	private boolean districtSet;

	@ApiModelProperty(position = 130)
	private String region;

	@ApiModelProperty(hidden = true)
	private boolean regionSet;

	@ApiModelProperty(position = 140)
	private String countryCode;

	@ApiModelProperty(hidden = true)
	private boolean countryCodeSet;

	@ApiModelProperty(position = 150, //
			value = "This translates to `C_BPartner_Location.GLN`")
	private String gln;

	@ApiModelProperty(hidden = true)
	private boolean glnSet;

	@ApiModelProperty(position = 160)
	private Boolean shipTo;

	@ApiModelProperty(hidden = true)
	private boolean shipToSet;

	@ApiModelProperty(position = 170, //
			value = "Only one location per request may have `shipToDefault == true`.\n"
					+ "If `true`, then " //
					+ "* `shipTo` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh location might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;

	@ApiModelProperty(hidden = true)
	private boolean shipToDefaultSet;

	@ApiModelProperty(position = 180)
	private Boolean billTo;

	@ApiModelProperty(hidden = true)
	private boolean billToSet;

	@ApiModelProperty(position = 190, //
			value = "Only one location per request may have `billToDefault == true`.\n"
					+ "If `true`, then " //
					+ "* `billTo` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh location might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;

	@ApiModelProperty(hidden = true)
	private boolean billToDefaultSet;

	@ApiModelProperty(position = 200, //
			value = "Translates to C_BPartner_Location.IsEphemeral")
	private boolean ephemeral;

	@ApiModelProperty(hidden = true)
	private boolean ephemeralSet;

	@ApiModelProperty(position = 210)
	@Nullable
	private String email;

	@ApiModelProperty(hidden = true)
	private boolean emailSet;

	@ApiModelProperty(position = 220)
	private String phone;

	@ApiModelProperty(hidden = true)
	private boolean phoneSet;

	@ApiModelProperty(position = 230, //
			value = "Translates to C_BPartner_Location.VisitorsAddress")
	private Boolean visitorsAddress;

	@ApiModelProperty(hidden = true)
	private boolean visitorsAddressSet;

	public void setActive(final Boolean active)
	{
		this.active = active;
		this.activeSet = true;
	}

	public void setName(final String name)
	{
		this.name = name;
		this.nameSet = true;
	}

	public void setBpartnerName(final String bpartnerName)
	{
		this.bpartnerName = bpartnerName;
		this.bpartnerNameSet = true;
	}

	public void setAddress1(final String address1)
	{
		this.address1 = address1;
		this.address1Set = true;
	}

	public void setAddress2(final String address2)
	{
		this.address2 = address2;
		this.address2Set = true;
	}

	public void setAddress3(final String address3)
	{
		this.address3 = address3;
		this.address3Set = true;
	}

	public void setAddress4(final String address4)
	{
		this.address4 = address4;
		this.address4Set = true;
	}

	public void setPoBox(final String poBox)
	{
		this.poBox = poBox;
		this.poBoxSet = true;
	}

	public void setPostal(final String postal)
	{
		this.postal = postal;
		this.postalSet = true;
	}

	public void setCity(final String city)
	{
		this.city = city;
		this.citySet = true;
	}

	public void setDistrict(final String district)
	{
		this.district = district;
		this.districtSet = true;
	}

	public void setRegion(final String region)
	{
		this.region = region;
		this.regionSet = true;
	}

	public void setCountryCode(final String countryCode)
	{
		this.countryCode = countryCode;
		this.countryCodeSet = true;
	}

	public void setGln(final String gln)
	{
		this.gln = gln;
		this.glnSet = true;
	}

	public void setShipTo(final Boolean shipTo)
	{
		this.shipTo = shipTo;
		this.shipToSet = true;
	}

	public void setShipToDefault(final Boolean shipToDefault)
	{
		this.shipToDefault = shipToDefault;
		this.shipToDefaultSet = true;
	}

	public void setBillTo(final Boolean billTo)
	{
		this.billTo = billTo;
		this.billToSet = true;
	}

	public void setBillToDefault(final Boolean billToDefault)
	{
		this.billToDefault = billToDefault;
		this.billToDefaultSet = true;
	}

	public void setEphemeral(final Boolean ephemeral)
	{
		this.ephemeral = ephemeral;
		this.ephemeralSet = true;
	}

	public void setEmail(@Nullable final String email)
	{
		this.email = email;
		this.emailSet = true;
	}

	public void setPhone(final String phone)
	{
		this.phone = phone;
		this.phoneSet = true;
	}

	public void setVisitorsAddress(final Boolean visitorsAddress)
	{
		this.visitorsAddress = visitorsAddress;
		this.visitorsAddressSet = true;
	}
}
