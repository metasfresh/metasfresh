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

package de.metas.common.bpartner.v1.request;

import de.metas.common.rest_api.common.JsonExternalId;
import de.metas.common.rest_api.v1.SyncAdvise;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import static de.metas.common.rest_api.v1.SwaggerDocConstants.PARENT_SYNC_ADVISE_DOC;

@Getter
@ToString
@EqualsAndHashCode
@Schema(description = "Locations can be inserted/updated, or just looked up. For lookup, metasfresh tries first the `externalId` and then the `gln`.")
public class JsonRequestLocation
{
	@Schema(description = "This translates to `C_BPartner_Location.ExternalId`.\n"
					+ "Needs to be unique over all business partners (not only the one this location belongs to). "
					+ "This is deprecated and will no longer be available with v2.")
	private JsonExternalId externalId;

	@Schema(hidden = true)
	private boolean externalIdSet;

	@Schema(description = "If not specified but required (e.g. because a new location is created), then `true` is assumed")
	private Boolean active;

	@Schema(hidden = true)
	private boolean activeSet;

	@Schema(description = "This translates to `C_BPartner_Location.Name`")
	private String name;

	@Schema(hidden = true)
	private boolean nameSet;

	@Schema(description = "This translates to `C_BPartner_Location.BPartnerName`")
	private String bpartnerName;

	@Schema(hidden = true)
	private boolean bpartnerNameSet;

	@Schema
	private String address1;

	@Schema(hidden = true)
	private boolean address1Set;

	@Schema
	private String address2;

	@Schema(hidden = true)
	private boolean address2Set;

	@Schema
	private String address3;

	@Schema(hidden = true)
	private boolean address3Set;

	@Schema
	private String address4;

	@Schema(hidden = true)
	private boolean address4Set;

	@Schema
	private String poBox;

	@Schema(hidden = true)
	private boolean poBoxSet;

	@Schema(description = "If specified, then metasfresh will attempt to lookup the `C_Postal` record.\n"
					+ "If there is one matching postal record, the system **will ignore** the following properties and instead use the postal record's values:\n"
					+ "* countryCode\n"
					+ "* city\n"
					+ "* region\n")
	private String postal;

	@Schema(hidden = true)
	private boolean postalSet;

	@Schema
	private String city;

	@Schema(hidden = true)
	private boolean citySet;

	@Schema(description = "If specified, then metasfresh will use this property (in addition to `postal`) as a filter criterion to look up `C_Postal` records.\n"
					+ "The property may be empty so a caller can explicitly tell metasfresh *not* to filter by district")
	private String district;

	@Schema(hidden = true)
	private boolean districtSet;

	@Schema
	private String region;

	@Schema(hidden = true)
	private boolean regionSet;

	@Schema
	private String countryCode;

	@Schema(hidden = true)
	private boolean countryCodeSet;

	@Schema(description = "This translates to `C_BPartner_Location.GLN`")
	private String gln;

	@Schema(hidden = true)
	private boolean glnSet;

	@Schema
	private Boolean shipTo;

	@Schema(hidden = true)
	private boolean shipToSet;

	@Schema(description = "Only one location per request may have `shipToDefault == true`.\n"
					+ "If `true`, then " //
					+ "* `shipTo` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh location might be set to `shipToDefault = false`, even if it is not specified in this request.")
	private Boolean shipToDefault;

	@Schema(hidden = true)
	private boolean shipToDefaultSet;

	@Schema
	private Boolean billTo;

	@Schema(hidden = true)
	private boolean billToSet;

	@Schema(description = "Only one location per request may have `billToDefault == true`.\n"
					+ "If `true`, then " //
					+ "* `billTo` is always be assumed to be `true` as well"
					+ "* another possibly exiting metasfresh location might be set to `billToDefault = false`, even if it is not specified in this request.")
	private Boolean billToDefault;

	@Schema(hidden = true)
	private boolean billToDefaultSet;

	@Schema(description = "Sync advise about this location's individual properties.\n"
					+ "IfExists is ignored on this level!\n" + PARENT_SYNC_ADVISE_DOC)
	private SyncAdvise syncAdvise;

	@Schema(hidden = true)
	private boolean syncAdviseSet;

	public void setExternalId(final JsonExternalId externalId)
	{
		this.externalId = externalId;
		this.externalIdSet = true;
	}

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

	public void setSyncAdvise(final SyncAdvise syncAdvise)
	{
		this.syncAdvise = syncAdvise;
		this.syncAdviseSet = true;
	}

}
